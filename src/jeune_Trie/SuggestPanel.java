/**
 * 
 */
package jeune_Trie;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * SuggestionPanel holds a JList that can hold suggested answers for the current region
 * on the board.  
 * @author vladimirjeune
 */
public class SuggestPanel extends JPanel implements RegionObserver, CurrentObserver, BoardContentsObserver {
	
	private PMModelInterface _model;
	private PMControllerInterface _controller;
	private String _lenStr ;                    // Lwngth String
	private JLabel _lengthLabel ;               // Length Label
	private boolean _isClearing ;
	private boolean _hasSuggested ;
	private boolean _isHorizontal;
	private boolean _beenClicked;
	private boolean _panelSelected;
	private String _acrossWord;
	private String _downWord;
	private JList _suggestList ;                // The JList
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
	
	
	public SuggestPanel( PMModelInterface aModel, PMControllerInterface aController ) {
		
		// Assign variables
		_model = aModel;
		_controller = aController;
		_lenStr = new String("Length: ");
		_lengthLabel = new JLabel( _lenStr );                            // LENGTH: ## 
		_listModel = new DefaultListModel();                             // List Model
		_suggestList = new JList( _listModel );                          // JList
		_isClearing = false;
		_hasSuggested = false ;                                          // Suggestion was made 4 reg
		// Not to be used before Panel is started.
		_currPos = new Position();
		_oldCurrPos = new Position();
		_aRegion = new ActiveRegion();
		_oldRegion = new ActiveRegion();
		
		_suggestList.setPrototypeCellValue("Antidisestablishmentarianism");          // 28 letters long.
		
		// Register Observers
		_model.registerObserver((CurrentObserver)this);
		_model.registerObserver((RegionObserver)this);
		_model.registerObserver((BoardContentsObserver)this);
		
		JLabel sugLabel = new JLabel( "SUGGESTIONS" );    // SUGGESTIONS
		JButton sugButton = new JButton("SUGGEST");
		JPanel sugPanel = new JPanel();                   // Panel for Suggestion Labels
		JPanel btnPanel = new JPanel();                   // Panel for button
		JScrollPane scrollPane = new JScrollPane( _suggestList );
		JPanel scrollPanel = new JPanel();        // Will hold ScrollPane that holds JList
		
		
		_listModel.addListDataListener(
				new ListDataListener() {

					public void contentsChanged(ListDataEvent arg0) {
						// TODO Auto-generated method stub
						String newline = "\n";
			            System.out.println("contentsChanged: " + arg0.getIndex0() +
			                       ", " + arg0.getIndex1() + newline); 
			            //log.setCaretPosition(log.getDocument().getLength());
					}

					public void intervalAdded(ListDataEvent arg0) {
						// TODO Auto-generated method stub
						System.out.println("intervalAdded: " + arg0.getIndex0() +
			                       ", " + arg0.getIndex1() + "\n"); 
//			            log.setCaretPosition(log.getDocument().getLength());
					}

					public void intervalRemoved(ListDataEvent arg0) {
						// TODO Auto-generated method stub
			            System.out.println("intervalRemoved: " + arg0.getIndex0() +
			                       ", " + arg0.getIndex1() + "\n"); 
//			            log.setCaretPosition(log.getDocument().getLength());
					}
					
				});
		
		// JLIST STUFF
		_suggestList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		// Create a selection listener that will select appropriate region if that clue is selected in panel. 
		_suggestList.addListSelectionListener( 
				new ListSelectionListener() {

					public void valueChanged(ListSelectionEvent arg0) {
						
						// If something on list was selected, something must have been on the list.
						// Something must match the size and lettering of the current region.
						// Manually place letters on the board, or see if can do so using user functions without messing up.
						if ( false == arg0.getValueIsAdjusting() ) {
// JList.isSelectionEmpty
							// Trying to see if you can use the latter funct so USER never have SELECT 2X
							System.out.println("ISCLEARING : ISSELECTIONMT : " + _isClearing + " : " + _suggestList.isSelectionEmpty());
							
//							if ( false == _isClearing ) {                   // valuechanged fires when clearing.  Causes problem so shielded code.
							if ( ( false == _isClearing ) || ( (true == _isClearing) && ( false == _suggestList.isSelectionEmpty() ) ) ){                   // nw valuechanged fires when clearing.  Causes problem so shielded code.
								String selGuess = (String) _suggestList.getSelectedValue();

								// Set Curr to start of Region, and place matching word in Region.
								_controller.cClueSetRegStart(_aRegion.getStartPos(), ( ( (_model.getOrientation() % 2 ) == 0) ? true : false ) );
								for ( int itr = 0; itr < selGuess.length(); itr++ ) {
									_controller.cModify( selGuess.charAt(itr));            // Place whole word on board, should perfectly overlay on top of letters already there.
								}
								
//								if ( true == _isClearing ) {      // nw
//									_isClearing = false;             
//									System.out.println("ALT LIST should be done CLEARING");
//								}
								
							} else {
								_isClearing = false;                       // Remove clearing flag
								System.out.println("LIST should be done CLEARING");
							}
						}
						
					}
				});
				
		sugButton.addActionListener( new
				ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) {       // UPDATE REGION call will clear list, and Update aRegion.
				//Position startPt = _aRegion.getStartPos();       // ARegion should be updated by UpdateRegion when region changes.
				// If Area is empty then use wordLength
				// If not use our function to call suggestMatch using the controllerInterface.

				// The resulting arrayList will populate the JList.

				//_suggestList.clearSelection();
				_isClearing = true;                              // Tell SelectionListener, b4 clear 
				_listModel.clear();                              // Clear list.

				System.out.println("SUGGESTION WANTED");
				
				
//				String letterStr = "^(\\s)*(\\w)+(\\s)*$";       // If a letter is present then not empty.
				String letterStr = "^((\\s)*(\\w)+(\\s)*)+$";       // If a letter is present then not empty.
				String word = _model.stringInRegion(_aRegion);   // Get what is in the Region

				ArrayList<String> guessList ;                    // List that may be put in JList

				// Call one of the matching functions.
				if ( ! ( word.matches( letterStr )) ) {          // If no letters in the region.
					guessList = _controller.cSuggestWordsOfLengthDict(_aRegion.getLength());
				} else {
					word = word.replace(' ', '_');
					System.out.println("STRINGINREGION " + word );					
//					guessList = _controller.cSuggestMatchDict( word.replace(' ', '_') );       // Replace SPCS with UNDERSCORES. 
					guessList = _controller.cSuggestMatchDict( word ) ;                     // Replace SPCS with UNDERSCORES. 
				}

				System.out.println( guessList );
				
				for ( String guess : guessList ) {                // Add any found words to JList, if any.
					System.out.println("LIST Starts CLEARING");
					_isClearing = true;
					_listModel.addElement( guess );                          
				}
				
				//_suggestList.clearSelection();

			}

		});
		
		// Layout
		sugPanel.setLayout( new BoxLayout( sugPanel, BoxLayout.X_AXIS));
		scrollPanel.setLayout( new BorderLayout());
		btnPanel.setLayout( new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		
		// Place Components
		sugPanel.add(sugLabel);
		sugPanel.add( Box.createHorizontalGlue() );
		sugPanel.add( _lengthLabel );
		
		btnPanel.add( Box.createHorizontalGlue() );
		btnPanel.add( sugButton );
		
		scrollPanel.add( scrollPane, BorderLayout.CENTER );
		
		add( sugPanel );
		add( scrollPanel );
		add( btnPanel );
		
		// Setting Border
		setBorder( BorderFactory.createEmptyBorder(2,2,2,2));

	}
	
	/**
	 * STARTPANEL will get the information for the correct function of this panel.
	 */
	public void startPanel() {
		_currPos = _model.getCurr();                       // Get Current
		//_oldCurrPos = _currPos;
		_aRegion = _model.getActiveRegion();               // Get Active Region
		//_oldRegion = _aRegion;
		
//		_controller.cLoadDict(new File("testFile.lst"));   // Fill Dictionary, could take while.
		_controller.cLoadDict(new File("englishWords28K"));   // Fill Dictionary, could take while.
		
		setLengthLabel();
	}

	/**
	 * 
	 */
	private void setLengthLabel() {
		_lengthLabel.setText( _lenStr + padNumber(_aRegion.getLength()) );
	}
	
	/**
	 * PADNUMBER will output a number that is the same length as the one that was inputted, 
	 * but padded with a space.  All outputted strings will be 2 characters in length.
	 * Preconditions:  Number passed in should be > 0 and less than 100.
	 * Postconditions:  The number passed in will be passed out and padded with a space if 
	 * 				necessary.
	 * @param aNum the number to be passed in.
	 * @return String of padded number.
	 */
	private String padNumber( int aNum ) {
		String retVal = "##";

		if ( ( aNum > 0 )  ) {        // Accept only 1 and 2 digit numbers.//&& ( aNum < 100 ) nw
			if ( aNum < 10 ) {                         // Pad 1 digit numbers
				retVal = " ";
			}
			retVal = "" + aNum;                            // Return num as string
		}
		
		return retVal;
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 */
	public void updateRegion() {
		// TODO Auto-generated method stub

		System.out.println("OLD and NEW " + _oldRegion + " : " + _aRegion );
		
		_isClearing = true;                           // To cover next stmt nw
		_suggestList.clearSelection();                // Should now have no sideFX nw
		
		if ( ! (_oldRegion.match(_aRegion)) ) {             // If we are in a new region.
			
				while ( 0 != _listModel.getSize() ) {
					_isClearing = true;                 // So does not trip bad path in valueChanged
					System.out.println("LIST Starts CLEARING");
					System.out.println( "REMOVED FROM NON EMPTY LIST : " + _listModel.remove(0));
				}

			
			//_listModel.clear();
//			_isClearing = true;
		}
		
		// Retain old values for curr and region
//		_oldCurrPos = _currPos;
		_oldCurrPos.setPosition(_currPos.getRow(), _currPos.getCol(), _currPos.getPlace());
//		_oldRegion = _aRegion;
		_oldRegion.setActiveRegion(_aRegion.getStartPos(), _aRegion.getLength(), _aRegion.getOrientation());
		
		// Get new values for Curr and Region
		_currPos = _model.getCurr();
		_aRegion = _model.getActiveRegion();
		
		setLengthLabel();
		

	}

	public void updateCurrent() {
		// TODO Auto-generated method stub
		
	}

	public void updateBoardContents() {
		// TODO Auto-generated method stub
		
	}

}
