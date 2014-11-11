/**
 * 
 */
package jeune_Trie;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * There will be two of these at the same time.  One will hold the Across Clues and 
 * the other will hold the Down Clues.  When a user wants to set a Clue for a region
 * on the board, the Clue will end up in the appropriate panel.  Clicking on a Clue 
 * in one of the panels should bring that region up on the board.
 * @author vladimirjeune
 *
 */
public class CluePanel extends JPanel implements RegionObserver,
		CurrentObserver, BoardContentsObserver, ClueObserver {

	private PMModelInterface _model;
	private PMControllerInterface _controller;
	private boolean _isHorizontal;
	private boolean _beenClicked;
	private boolean _panelSelected;
	private String _acrossWord;
	private String _downWord;
	private JList _clueList ;
	private DefaultListModel _listModel;
	private Position _currPos = new Position(0,0);
	private Set<Position> _startSet;
	private Map<Position, String> _acrossClues;
	private Map<Position, String> _downClues;
	private int _currLength = 1;                  // Length of current region,
	private int _orientation = 0;                // mod2 == => HORIZONTAL, 1 => VERTICAL
	private ActiveRegion _aRegion;
	private ActiveRegion _oldRegion;              // The previous region
	private Position _oldCurrPos;                 // The previous Current

	public CluePanel( PMModelInterface aModel, PMControllerInterface aController, boolean isAcross ) {
		
		// Assign variables
		_model = aModel;
		_controller = aController;
		_listModel = new DefaultListModel();              // List Model
		_clueList = new JList( _listModel );                          // JList
		_isHorizontal = isAcross;
		_beenClicked = false;                             // Been clicked
		_panelSelected = false;                           // Been selected.
		_acrossWord = "ACROSS";
		_downWord = "DOWN";
		
		// Register Observers
		_model.registerObserver((CurrentObserver)this);
		_model.registerObserver((RegionObserver)this);
		_model.registerObserver((BoardContentsObserver)this);
		_model.registerObserver((ClueObserver)this);
		
		JPanel labelPanel = new JPanel();   // Will hold Orientation label
		JLabel dirLabel = new JLabel(( true == _isHorizontal ) ? _acrossWord : _downWord );
		JScrollPane scrollPane = new JScrollPane( _clueList );
		JPanel scrollPanel = new JPanel();        // Will hold ScrollPane that holds JList
		
		// JLIST STUFF
		_clueList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		// Create a selection listener that will select appropriate region if that clue is selected in panel. 
		_clueList.addListSelectionListener( 
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent arg0) {
						_panelSelected = true;
						if ( _beenClicked ) {          // BEENCLICKED nw

							System.out.println( ((_isHorizontal)?"WE ARE GOING ACROSS" : "WE ARE GOING DOWN" ));
							Integer hintNum = extractIntFromClue((String)_clueList.getSelectedValue());

							Position hintPos = _model.findHintPosition(hintNum);                  // Find that Start of Hint

							if ( (null != hintPos) 
									&& ( false == arg0.getValueIsAdjusting()) ) {                                              // If found the clue

								System.out.println("CLUEPANEL VALUECHANGED START : Setting to start of Clue " + hintPos );
								_model.clueSetRegStart(hintPos, _isHorizontal);

								System.out.println("CLUEPANEL VALUECHANGED END :Move region to " + hintPos );

							}

							_beenClicked = false;               // nw
							System.out.println( ( (_isHorizontal) ? "ACROSS " : "DOWN " ) + "BeenClicked now FALSE");    // nw
						}       // END OF BEENCLICKED nw, MOVE SO NO BLOCK ELSE
						_panelSelected = false;                      // Panel no longer selected.

					}
				});
		
		// Added a MouseListener to tell if this panel was clicked on, since updateRegion can trigger selections too.
		_clueList.addMouseListener( 
				new MouseAdapter() {

					public void mousePressed(MouseEvent arg0) {

			            int index = _clueList.locationToIndex(arg0.getPoint());         // Use location of event to find index in list.
			            System.out.println("" + (( _isHorizontal ) ? "ACROSS " : "DOWN ") + "MousePressed on Item " + index);
			            _beenClicked = true;   // nw
						
					}

				});
		
		// Layout
		labelPanel.setLayout( new BoxLayout( labelPanel, BoxLayout.X_AXIS));
		scrollPanel.setLayout( new BorderLayout());
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		
		// Place Components
		labelPanel.add( dirLabel );
		labelPanel.add( Box.createHorizontalGlue() );
		
		scrollPanel.add( scrollPane, BorderLayout.CENTER );

		add( labelPanel );
		add( scrollPanel );

	}
	
	/**
	 * STARTPANEL will get the information for the correct function of this panel.
	 */
	public void startPanel() {
		_currPos = _model.getCurr();                       // Get Current
		_oldCurrPos = _model.getCurr();
		_aRegion = _model.getActiveRegion();               // Get Active Region
		_oldRegion = _model.getActiveRegion();               // Hold odl Active Region
		_startSet = _model.getStartPositions();            // Get Start Positions
			
		addCluesToList();
		
	}

	/**
	 * ADDCLUESTOLIST will add the clues for the appropriate orientation and region to the list.
	 * This will depend on the orientation of this component as entered into the parameter list.
	 * Preconditions:  The board should be set up before use.
	 * Postconditions: The Region number and Clue associated with that number will be added to the 
	 * 				list, as long as, it is for the orientation that was inputted into the parameter list.
	 * 
	 */
	private void addCluesToList() {
		TreeMap<Position, String> clueMap = (TreeMap<Position, String>) ( ( true == _isHorizontal ) 
				? _model.getAcrossHints() 
						: _model.getDownHints());                               // Get correct map.
		
		for ( Map.Entry<Position, String> posList : clueMap.entrySet() ) {      // Use mappings to get list of clues.
			Position clueNum = posList.getKey();
			String clueStr = posList.getValue();
			
			_listModel.addElement( outputNumber(clueNum.getPlace()) + clueStr );
			
		}
		
	}
	
	/** (non-Javadoc)
	 * @see jeune_Trie.RegionObserver#updateRegion()
	 * This function should update this CluePanel's selected value, if it is not the same
	 * as the one that exists when this function was called.  If we are the same orientation 
	 * as the board, we should update ourselves to have the ActiveRegion as our selected value.
	 * If we have the opposite orientation as the board; then we should have as semi-selected
	 * the Clue that corresponds to the Region that would be formed if Current was in the opposite
	 * Orientation of what it is now.
	 */
	public void updateRegion() {
		
		System.out.print("CLUEPANEL UPDATEREGION " + ( (_isHorizontal)? "ACROSS ": "DOWN ") );
		// Get new values for Curr and Region
		_currPos = _model.getCurr();
		_aRegion = _model.getActiveRegion();
		
		// If the region has the same Orientation as this Panel.
//		if ( _isHorizontal == ( (_model.getOrientation() % 2) == 0 ) ) {
		if ( orientMatchesModel() ) {                                     // We are correct orientation so should have the clue region that triggered this call.
			
			if ( ! (_panelSelected ) ) {                                       // Region been selected not panel; force our selection to match model.
				
				int startNum = _aRegion.getStartPos().getPlace();              // What we should set the selectdValue to.
				int selIndex = 0;
				int currClueNum = 0;

//				for ( ; selIndex < _listModel.getSize(); selIndex++ ) {        // Find index of value to set to selected.
//					currClueNum = extractIntFromClue((String) _listModel.get(selIndex));
//
//					if ( currClueNum == startNum ) {                           // If found Clue corresponding to ActiveRegion
//				System.out.println("Selected Index " + selIndex );
//				_clueList.setSelectedIndex(selIndex);                  // Select appropriate index.
//				_clueList.ensureIndexIsVisible(selIndex);  // nw
//						selIndex = _listModel.getSize();                       // Break out of loop
//					}
//				}
				
				int foundIndex = findClueInList( startNum );             // Find index of value set to selected.
				//System.out.println("Selected Index " + selIndex );
				_clueList.setSelectedIndex(foundIndex);                  // Select appropriate index.
				_clueList.ensureIndexIsVisible(foundIndex);  // nw

			}
			
		} else {

			Map<Position, String> ourMap = getOurMap(); 
			Position candPos = new Position( _currPos.getRow(), _currPos.getCol());
			_model.findStartRegion(candPos, _isHorizontal );
			
			if ( ourMap.containsKey(candPos)) {                        // If this panel contains the current positions place.
		
				int clueNumber = candPos.getPlace();   // We know the position is a valid start position
				
				System.out.println("Selected Index for clue number " + clueNumber );
				int foundIndex = findClueInList(clueNumber); 
				_clueList.setSelectedIndex( foundIndex );                         // Set Transverse index
				_clueList.ensureIndexIsVisible( foundIndex );                      // Make sure that index is visible.
				
			}
			else {
				_clueList.clearSelection();                             // The other panel has the only selected region.
			}
			
		}
		
	}

	/**
	 * FINDCLUEINLIST will find the index in the JList for the clue number that is passed in, if
	 * it exists.
	 * Preconditions:  The input MUST be valid or -1 will be returned.
	 * 				Meaning inputted clue number MUST exist in this objects JList.
	 * Postconditions: The index in the JList of the clue number that was inputted will be returned.
	 * 				If the number is invalid for this list, -1 is returned.
	 * @param aClueNumber
	 * @return the appropriate index into this objects JList for the inputted clue, 
	 * 		otherwise -1 is returned.
	 */
	private int findClueInList(int aClueNumber) {
		int selectionIdx = 0;
		int currentClueNum = 0;
		int retVal = -1;
		
		for ( ; selectionIdx < _listModel.getSize(); selectionIdx++ ) {                 // Loop thru JList
			currentClueNum = extractIntFromClue( (String) _listModel.get(selectionIdx));
			if ( currentClueNum == aClueNumber ) {                                      // Found a match
				retVal = selectionIdx;
				selectionIdx = _listModel.getSize();
			}
		}
		return retVal;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.CurrentObserver#updateCurrent()
	 */
	public void updateCurrent() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.BoardContentsObserver#updateBoardContents()
	 */
	public void updateBoardContents() {
		// TODO Auto-generated method stub

	}

	/**
	 * UPDATECLUE should update the clue associated with the proper place on the board.
	 * Preconditions:  Board should be set up. 
	 * Postconditions: The Clue associated with the Region on the board that just had its
	 * 				Clue reset showed show that it has changed.
	 * @see jeune_Trie.BoardContentsObserver#updateClue()
	 */
	public void updateClue() {
		int selClue = _clueList.getSelectedIndex();

		if ( _isHorizontal == (((_model.getOrientation() % 2) == 0 ) ? true : false)  ) {   // If we match the orientation of the region whose clue was just modified.
			Map<Position, String> hintMap = getOurMap();

			_listModel.set(selClue, outputNumber( _aRegion.getStartPos().getPlace()) + hintMap.get(_aRegion.getStartPos()));      // Set the Clue to the Clue for this Region.

		}
	}

	/**
	 * SETLISTPOSITION will allow the inputted index in the list to be set to the inputted string.
	 * If the inputted position does not exist, nothing will happen
	 * Preconditions:  The inputted index should exists.  The inputted String should be in the 
	 * 				format: ###.) STRING
	 * 	            Index should be > 0.
	 * Postconditions: The inputted index's String will be changed to that of the passed in String.
	 */
	public void setIndexPosition( int anIndex, String aClue ) {
		
		if ( (anIndex < _listModel.getSize()) 
				&& ( anIndex >= 0 ) ) {                   // Index is in range for this list 
			_listModel.set(anIndex, aClue);               // Set aClue for this index.
		}
				
	}
	
	/**
	 * GETOURMAP returns the map that is associated with this instance of CluePanel, depending on
	 * what orientation we are set to, and the current orientation of the model.
	 * @return
	 */
	private Map<Position, String> getOurMap() {
		Map<Position, String> hintMap = ( ( _isHorizontal ) 
				?_model.getAcrossHints() 
						: _model.getDownHints() );                         // Pick mapping that matches this region.
		return hintMap;
	}
	
	/**
	 * EXTRACTINTFROMCLUE should extract integers from the beginning of the INPUTTED Clue string.
	 * It should work for ints from 1 - 3 characters in length.
	 * Preconditions:  The Clue String must be start with spaces or a number, in a format 
	 * 				similar to '###.) '.  Numbers are right justified and padded with spaces.
	 * Postconditions: The Number of the clue string that was passed in will be extracted and returned
	 * @return
	 */
	private Integer extractIntFromClue( String clueStr ) {
		StringBuffer hintNumBuf = new StringBuffer();                          // Will hold number	

			int cnt = 0;
			while ( (( clueStr.charAt(cnt) >= 48 ) 
					&& (clueStr.charAt(cnt) <= 57 )) 
					|| ( clueStr.charAt(cnt) == 32 ) ) {                            // Range of ASCII numbers
				if ( clueStr.charAt(cnt) != 32 ) {
					hintNumBuf.append(clueStr.charAt(cnt));                            
				}
				cnt++;                                                              // Increment
			}

			return Integer.valueOf(hintNumBuf.toString());                         // Return the extracted number.

	}
	
	/**
	 * OUTPUTNUMBER will output a String 6 characters in length that will act as numbering of 
	 * the Clues in this panel.  Depending on character length the actual numbers will be 
	 * padded with 2 or less space characters.  This will keep the clues lined up.
	 * Preconditions:  Numbering should only go up to 999.
	 * Postconditions: A 6 character string representing the number that was passed in will
	 * 				be passed out.
	 * @return Padded String representing the number passed in.
	 */
	private String outputNumber( int aNum ) {
		StringBuffer retVal = new StringBuffer();
		String oneSpc = " ";                                   // Padding
		String twoSpc = "  ";
		String rest = ".) ";
		
		if (( aNum > 0 ) && ( aNum < 10 ) ) {                  // Deciding on amount of padding
			retVal.append( twoSpc );
		} else if ( aNum < 100 ) {
			retVal.append( oneSpc );
		} 
		
		retVal.append("" + aNum + rest );
		
		return retVal.toString();
	}
	
	/**
	 * ORIENTMATCHESMODEL will tell if our orientation matches that of the Model.
	 * @return
	 */
	private boolean orientMatchesModel() {
		return _isHorizontal == ( ( _model.getOrientation() % 2 ) == 0 );
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PMModelInterface theModel = new PMModel();
		PMControllerInterface theController = new PMController( theModel );
		
//		TestBottomDialog cid = new TestBottomDialog(null, theModel, theController);
//		cid.startPanel();
//		cid.pack();
//		cid.setVisible(true);
		
		
		PMModel aModel = (PMModel) theModel;
		System.out.println( aModel );          // Debug.

	}



}
