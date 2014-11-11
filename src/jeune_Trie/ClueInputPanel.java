/**
 * 
 */
package jeune_Trie;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This Panel will hold the components for inputting the Questions that go to the 
 * answers on the board.  It will also tell the number we are on and whether we are 
 * going across or down.  DirectionPanel will hold the current orientation of the clue.
 * and CluePanel will hold the textbox where the user will input the clue that will 
 * go with the corresponding answer on the board.
 * @author vladimirjeune
 *
 */
public class ClueInputPanel extends JPanel  implements  RegionObserver, CurrentObserver, BoardContentsObserver {

	private PMModelInterface _model;
	private PMControllerInterface _controller;
    private Position _currPos = new Position(0,0);
    private Set<Position> _startSet;
    private Map<Position, String> _acrossClues;
    private Map<Position, String> _downClues;
    private int _currLength = 1;                  // Length of current region,
    private int _orientation = 0;                // mod2 == => HORIZONTAL, 1 => VERTICAL
	private ActiveRegion _aRegion;
	private ActiveRegion _oldRegion;              // The previous region
	private Position _oldCurrPos;                 // The previous Current
    
	private JLabel _topLabel ; 
	private JLabel _botmLabel ;
	private JTextField _botmText ;
	private String tLabelAcross ;
	private String tLabelDown ;
	private String tLabelPlace ;
	
	public ClueInputPanel( PMModelInterface aModel, PMControllerInterface aController ) {
		
		_model = aModel;
		_controller = aController;
		tLabelAcross = "ACROSS";
		tLabelDown = "DOWN";	
		_acrossClues = _model.getAcrossHints();         // Careful
		_downClues = _model.getDownHints();

		_model.registerObserver((CurrentObserver)this);
		_model.registerObserver((RegionObserver)this);
		_model.registerObserver((BoardContentsObserver)this);
		
		
		// Panels
		JPanel topPanel = new JPanel();
		JPanel botmPanel = new JPanel();
		JPanel botmLabelPanel = new JPanel();
		JPanel botmTextPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JButton setBtn = new JButton("SET");
		
		// Components for Panels
		_topLabel = new JLabel("ACROSS");  // Figure out how to change labels on fly
		_botmLabel = new JLabel("  1.) ");
		_botmText = new JTextField("", 50 );
		_botmText.setToolTipText("Type in Clue for this row/column; then press Enter or the SET button.");
		
		// ActionListener
		SetClueAction scAction = new SetClueAction();              // This actions sets the clue in the text box to the model
		_botmText.addActionListener( scAction );                   // Action fires when user presses Enter
		setBtn.addActionListener(scAction);                        // Action fires whene button pressed.
		
		// Set fonts
		_topLabel.setFont( new Font("SansSerif", Font.BOLD, 16 ));
		_botmLabel.setFont(new Font("SansSerif", Font.BOLD, 14 ));

		// Layout
		topPanel.setLayout(new BoxLayout( topPanel, BoxLayout.X_AXIS));
		botmPanel.setLayout( new BoxLayout(botmPanel, BoxLayout.X_AXIS));
		botmLabelPanel.setLayout( new BoxLayout( botmLabelPanel, BoxLayout.Y_AXIS));
		btnPanel.setLayout( new BoxLayout( btnPanel, BoxLayout.X_AXIS));
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));                 // this Panel
		
		// Package it

		// Add to this panel
		add( topPanel );
		add( botmPanel );
		add( btnPanel );
				
		// Add subcomponents to components
		botmTextPanel.add(_botmText);
		botmLabelPanel.add(_botmLabel);

		// Add Button  to ButtonPanel
		btnPanel.add( Box.createGlue());
		btnPanel.add( setBtn );
		btnPanel.add( Box.createHorizontalStrut(10));

		// Add to bottm panel
		botmPanel.add( Box.createHorizontalStrut(5));
		botmPanel.add(botmLabelPanel);
		botmPanel.add(botmTextPanel);
		
		// Add to top panel
		topPanel.add( Box.createHorizontalStrut(3));
		topPanel.add( _topLabel );
		topPanel.add( Box.createGlue() );
		
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
		
		_acrossClues = _model.getAcrossHints();
		_downClues = _model.getDownHints();
		setLabelOrientationToModel();
		setLabelPlace();
	}
	
	/**
	 * GETSPACES will return the spaces that should be printed in front of a number depending
	 * on its length in characters.
	 * Preconditions:  The board should be set up before this function is called.  Does not take 
	 * 				negative numbers.
	 * Postconditions: A string will be returned with the appropriate number of blank spaces
	 * 				depending on the char length of the input.
	 * @return String of blank spaces or "".
	 */
	private String setOutputSpaces( int aNum ) {
		StringBuffer retVal = new StringBuffer();
		
		if ( aNum < 10 ) {
			retVal.append("  ");
		} else if ( aNum < 100 ) {
			retVal.append(" ");
		}
		
		return retVal.toString();
	}
	
	/**
	 * SETLABELORIENTATIONTOMODEL will return a string that will say "ACROSS" or "DOWN", depending on 
	 * the orientation of the model when this function is called.
	 * Preconditions:  The board should be created before use.
	 * Postconditions: The Label will state whether the current clue is oriented ACROSS or DOWN.
	 */
	public void setLabelOrientationToModel() {
		
		if ( null != _model ) {
			_topLabel.setText( (((_model.getOrientation() % 2) == 0 )? tLabelAcross : tLabelDown ) );
		}
		
	}
	
	/**
	 * SETLABELPLACE will set the place of the label to match current in the model.
	 * Preconditions:  The board should be ready before this is used.
	 * Postconditions: The lable that shows the place of the current clue will be updated.
	 */
	public void setLabelPlace() {
		
		if ( null != _model ) {
			String theRest = ".) ";
			Position startPos = _aRegion.getStartPos();  //_model.getCurr();
			_botmLabel.setText(  setOutputSpaces( startPos.getPlace() ) + startPos.getPlace() + theRest );
		}
		
	}
	
	/**
	 * SETCLUE will set the clue for the previous Region.
	 * Preconditions:  The board should be set up before use.  Will be called when update is called
	 * 				becuase the current or region was changed by the users actions.
	 * Postconditions: The Clue that was inputted by the user will be placed in the model.
	 */
	public void setClue() {
		
		if ( (_model.getOrientation() % 2) == 0 ) {
			_model.setAcrossOrDownHint( true, _aRegion.getStartPos(), _botmText.getText());
		} else {
			_model.setAcrossOrDownHint(false, _aRegion.getStartPos(), _botmText.getText());
		}
		
	}
			
	/**
	 * UPDATEREGION will be called by the model when the model has changed.
	 * This function must update the appropriate values, so this panel displays the
	 * correct information.  And any clues the user inputted into the Panel should be 
	 * saved.
	 */
	public void updateRegion() {

		// Retain old values for curr and region
		_oldCurrPos = _currPos;
		_oldRegion = _aRegion;
		
		// Get new values for Curr and Region
		_currPos = _model.getCurr();
		_aRegion = _model.getActiveRegion();
		
		// Reset component faces
		setLabelOrientationToModel();
		setLabelPlace();

		// Bring up correct text for current region.
//		if ( (_aRegion.getOrientation() % 2 ) == 0 ) {
//			_botmText.setText( _acrossClues.get( _aRegion.getStartPos() ));
//		} else {
//			_botmText.setText( _downClues.get( _aRegion.getStartPos() ));
//		}
		
		if ( (_aRegion.getOrientation() % 2 ) == 0 ) {
			_botmText.setText( _model.getAcrossOrDownHint(true, _aRegion.getStartPos()));
		} else {
			_botmText.setText( _model.getAcrossOrDownHint(false, _aRegion.getStartPos()));
		}
		
	}
	
	public void updateCurrent() {
		// TODO Auto-generated method stub
		
	}
	
	public void updateBoardContents() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * PRIVATE CLASS: SETCLUEACTION
	 * @author vladimirjeune
	 *
	 */
	private class SetClueAction implements ActionListener {

		public SetClueAction() {
		}

		public void actionPerformed(ActionEvent e) {
			setClue();
		}		
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PMModelInterface theModel = new PMModel();
		PMControllerInterface theController = new PMController( theModel );
//		BumperInputDialog mid = new BumperInputDialog( null, theModel, theController );
//		
//		mid.pack();
//		mid.setVisible(true);
		
//		PlayInputDialog pid = new PlayInputDialog( null, theModel, theController );
//		pid.startCreation();
//		pid.pack();
//		pid.setVisible(true);
		
		TestBottomDialog cid = new TestBottomDialog(null, theModel, theController);
		cid.startPanel();
		cid.pack();
		cid.setVisible(true);
		
		
		PMModel aModel = (PMModel) theModel;
		System.out.println( aModel );          // Debug.
		
		
	}

}
