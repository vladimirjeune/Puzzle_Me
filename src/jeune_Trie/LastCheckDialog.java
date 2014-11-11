/**
 * 
 */
package jeune_Trie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 * Ask the user to do a last check of the data on the board, before making it playable.
 * This allows them to check that the answers are actual words.
 * That answers match the clues.
 * That clues are filled in.
 * If there are obvious mistakes i.e. missing clues, spaces in answers,
 * then the puzzle cannot be finalized and made playable.
 * @author vladimirjeune
 */
public class LastCheckDialog extends JDialog {

	public LastCheckDialog( JFrame owner, PMModelInterface aModel, PMControllerInterface aController ) {
		super( owner, "Validate Puzzle", true );      // Set up parent constructor.
		
		_model = aModel;                              // Set internal variables
		_controller = aController;
		_hasDecided = false;
		_tmpAcsMap = new TreeMap<Position, String>(); // For retrieval of changes to Model's mappings
		_tmpDwnMap = new TreeMap<Position, String>();
		_acsPosTxtMap = new TreeMap<Position, JTextArea>();
		_dwnPosTxtMap = new TreeMap<Position, JTextArea>();
		_acsPosChkMap = new TreeMap<Position, JCheckBox>();
		_dwnPosChkMap = new TreeMap<Position, JCheckBox>();
//		_checkAllMap = new TreeMap<JCheckBox, Boolean>();
		_acsStrPosMap = new TreeMap<String, Position>();
		_dwnStrPosMap = new TreeMap<String, Position>();
		_acsPosLblMap = new TreeMap<Position, JLabel>();
		_dwnPosLblMap = new TreeMap<Position, JLabel>();
		
		// ContentPane
		Container contentPane = getContentPane();
		
		contentPane.setLayout( new BoxLayout( contentPane, BoxLayout.Y_AXIS ));
		
		JPanel wholePanel = makeWholePanel();
		
		contentPane.add( wholePanel );
		
	}

	/**
	 * MAKEWHOLEPANEL creates the panel that holds all the subcomponents that make up this Dialog.
	 * @return
	 */
	private JPanel makeWholePanel() {
		JPanel aListPanel = createOrientList(false);
		JPanel dListPanel = createOrientList(true);
		aListPanel.setBorder( BorderFactory.createEmptyBorder(2, 0, 2, 0));
		dListPanel.setBorder( BorderFactory.createEmptyBorder(2, 0, 2, 0));
		
		// Bottom Button Panel
		JPanel decidePanel = makeDecidePanel();
		
		// ScrollPane holding the moveable parts.
		JPanel gluePanel = new JPanel();
		gluePanel.add( aListPanel ) ;                          // Hold Across
		gluePanel.add( new JSeparator() );
		gluePanel.add( dListPanel ) ;                          // Hold Down
		gluePanel.setLayout( new BoxLayout( gluePanel, BoxLayout.Y_AXIS ));
		JScrollPane wholeScroll = new JScrollPane( gluePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
		wholeScroll.setPreferredSize( new Dimension( 800, 300 ));

		JPanel wholePanel = new JPanel();                      // Will hold everything.
		wholePanel.setLayout( new BoxLayout( wholePanel, BoxLayout.Y_AXIS));
		wholePanel.add( wholeScroll );
		wholePanel.add( new JSeparator() );
		wholePanel.add( decidePanel );
		return wholePanel;
	}

	/**
	 * MAKEDECIDEPANEL makes the panel that contains the CANCEL, FINISH buttons for the Dialog.
	 * @return
	 */
	private JPanel makeDecidePanel() {
		String cancelStr = "CANCEL";
		String finishStr = "FINISH";
		JPanel decidePanel = new JPanel();

		// Sub components 
		JButton cancelBtn = new JButton(cancelStr);
		JButton finishBtn = new JButton(finishStr);
		
		// Button Actions
		// TODO: Cancel, will either destroy this dialog, or make invisible
		// TODO: Finish will make invisible.  So we can get data out later.
		finishBtn.addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub

						setVisible( false );           // Make invisible
						_hasDecided = true;            // OK, to take changed values.
					}

				}

		);
		
		cancelBtn.addActionListener( 
				new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						setVisible( false );
					}

				});
		
		

		// Set layout
		decidePanel.setLayout( new BoxLayout( decidePanel, BoxLayout.X_AXIS ));
		
		// Construct Panel
		decidePanel.add( Box.createHorizontalGlue() );
		decidePanel.add( cancelBtn );
		decidePanel.add( Box.createHorizontalStrut(10) );
		decidePanel.add( finishBtn );
		decidePanel.add( Box.createHorizontalStrut(10));

		return decidePanel;
	}
	
	/**
	 * PADNUMBER will output a number that is the same length as the one that was inputted, 
	 * but padded with spaces.  All outputted strings will be 3 characters in length.
	 * Preconditions:  Number passed in should be > 0 and less than 1000.
	 * Postconditions:  The number passed in will be passed out and padded with spaces if 
	 * 				necessary.
	 * @param aNum the number n where 0 < n < 1000
	 * @return String of padded number.
	 */
	private String padNumber( int aNum ) {
		String retVal = "##";

		if ( ( aNum > 0 )  ) {                         // 0 < aNum < 1000
			
			if ( aNum < 10 ) {                         // Pad 1 digit numbers
				retVal = "  ";
			} else if ( aNum < 100 ) {                 // Pad 2 digit numbers.
				retVal = " ";
			}				
			
			retVal += aNum;                        // Return num as string
			
		}

		return retVal;
		
	}
	
	/**
	 * CREATEORIENTLIST will create the list of all the relevant data for a specific orientation.
	 * It will return a panel that holds all the information for the orientation indicated by the 
	 * inputted parameters.  The CheckBox components will be enabled or disabled depending on
	 * whether that row is valid.  An invalid row should disable the outer components OK button
	 * until it is fixed. 
	 * ORIENT
	 *        CLUE        ANSWER      VALIDATE
	 *    #). ~~~~~~~~~~  ----------- chkBx
	 *    .              WHITE
	 *    .              GRAY
	 *    .              WHITE
	 *                                chkAllBtn
	 * Preconditions:  
	 * Postconditions:  A list of panels should be returned that are colored either white, grey.
	 * 				The panels that hold invalid values will be red, and contain a tool tip telling
	 * 				what went wrong.  
	 * 				Invalid rows will have disabled check boxes.
	 * 				Panel returned should be placed in a JScrollPane, because of many components
	 * 					and possible changing size of list becuase of changing components.
	 *              Will have a header panel indicating orienation, 
	 *              and a button that can check all boxes as long as they are valid.
	 *              All components in returned component should follow constraints.
	 */
	private JPanel createOrientList( boolean isVertical ) {
		JPanel retListPanel = new JPanel();
		Map<Position, String> orientMap = ( ( true == isVertical ) ? _model.getDownHints() : _model.getAcrossHints() );
		System.out.println( _model.toString());
		JPanel orientLabelPanel = makeOrientPanel(isVertical);          // Panel holding orientation.
		JPanel rowPanel = new JPanel();
		JPanel checkAllPanel = makeCheckAllPanel( isVertical );
		
		// Header Panel
		
		// Layout
		retListPanel.setLayout( new BoxLayout( retListPanel, BoxLayout.Y_AXIS )); // List going down.

		// Orientation Panel.
		retListPanel.add( orientLabelPanel);
		
		// Loop
		int cnt = 0;                                                             // Counter determines alternating colors.
		for ( Map.Entry<Position, String> entry : orientMap.entrySet() ) {       // Loop thru clues for this orientation.
			
//			_tmpMap.put( entry.getKey(), entry.getValue());                      // L8r if value change, will be reflected here.                                    
			rowPanel = makeRowPanel( entry.getKey(), entry.getValue(), cnt, isVertical ) ;   // Make panel for this
			retListPanel.add(rowPanel);                                          // Add created panel to list
			cnt++;                                                               // Incr counter.
			
		}
		
		// Chk All Panel
		retListPanel.add( checkAllPanel );
		
		return retListPanel;
	}

	/**
	 * MAKECHECKALLPANEL will hold the check all button that will check all the checkboxes in an
	 * oriented list.  There should only ever be 2 made.
	 * Preconditions:  Should only be used in the creation one of the 2 list, so that it can 
	 * 				be attached to all that lists check boxes.
	 * Postconditions: The button panel will be returned, and pushing the button will check all
	 * 				the boxes in the listPanel of the correct orientation.
	 * @param isVertical Indicates what orientation this checkbox should control
	 * @return JPanel holding the Check All Button.
	 */
	private JPanel makeCheckAllPanel( boolean isVertical ) {
		JPanel retCheckAllPanel = new JPanel();
		String nameStr = ( ( true == isVertical ) ? _dwnStr : _acsStr ) ;
		
		JCheckBox checkAllChkBx = new JCheckBox("CHECK ALL " + nameStr + " " );                  // Make checkbox.
		checkAllChkBx.setName( nameStr );                                                        // Name used to find later in ItemEvent.
		checkAllChkBx.setHorizontalTextPosition( SwingConstants.LEFT);                           // Text appears on left side
		
		// Listener for checkbox.
		checkAllChkBx.addItemListener( 
				new ItemListener() {

					public void itemStateChanged(ItemEvent arg0) {                               // Item was Selected or Deselected.
						boolean staySelected = true;                                               
						JCheckBox ourChkBx = (JCheckBox) arg0.getItem();                     // The JCheckbox that was seleceted
						
						if ( ( ItemEvent.SELECTED == arg0.getStateChange() )
								|| (ItemEvent.DESELECTED == arg0.getStateChange() ) ) {          // SELECTED OR DESELECTED

//							JCheckBox ourChkBx = (JCheckBox) arg0.getItem();                     // The JCheckbox that was seleceted

							if ( ourChkBx.getName().equals( _dwnStr )) {                         // If this is the DOWN ChkBox							
								Collection<JCheckBox> dwnChkArr =  _dwnPosChkMap.values();       // Get and loop thru the DOWN CheckBoxes.
								for ( JCheckBox chkBox : dwnChkArr ) {
									chkBox.setSelected( ( (ItemEvent.SELECTED == arg0.getStateChange()) ? true : false ) );
									if ( (ItemEvent.SELECTED == arg0.getStateChange()) && (false == chkBox.isSelected()) ) {   //nw
										staySelected = false;
									}
								}
							} else {
								Collection<JCheckBox> acsChkArr =  _acsPosChkMap.values();        // Get and loop thru the ACROSS CheckBoxes.
								for ( JCheckBox chkBox : acsChkArr ) {
									chkBox.setSelected( ( (ItemEvent.SELECTED == arg0.getStateChange()) ? true : false ) );
									if ( (ItemEvent.SELECTED == arg0.getStateChange()) && (false == chkBox.isSelected()) ) {   //nw
										staySelected = false;
									}
								}								
							}

						}

						if ( false == staySelected ) {          //nw
							ourChkBx.setSelected(false);
						}
						
					}
					
				});
		

		// Set Layout
		retCheckAllPanel.setLayout( new BoxLayout( retCheckAllPanel, BoxLayout.X_AXIS ));
		
		// Set Components
		retCheckAllPanel.add( Box.createHorizontalGlue() );
		retCheckAllPanel.add( checkAllChkBx );
		retCheckAllPanel.add( Box.createHorizontalStrut(10));
		
		return retCheckAllPanel;
	}

	/**
	 * MAKEROWPANEL will take the input and return a panel that will make up a row in the
	 * list of panels for validation by the user.
	 * @param aPos the Position of the start of the answer to the clue on the board
	 * @param aString the text of the clue.
	 * @return
	 */
	private JPanel makeRowPanel(final Position aPos, String aString, int count, boolean isVertical ) {
		// TODO Auto-generated method stub
		
		JPanel rowPanel = new JPanel();
		String tTipStr = "Can check boxes only if Clues AND answers have been filled.";
		rowPanel.setToolTipText( tTipStr );
		String numRest = ".) ";
		
		Color evenIdxClr = Color.GRAY;
		Color oddIdxClr = Color.WHITE;
		JLabel numLabel = new JLabel( padNumber( aPos.getPlace()) + numRest );            // Holds Clue Number
		JLabel answerLabel = new JLabel( findString( aPos, isVertical ) );         // Will hold the answer.
		JCheckBox valChkBx = new JCheckBox();      // Will hold the Validation Checkbox.
		JPanel numPanel = new JPanel();
		JPanel cluePanel = new JPanel();
		JPanel answerPanel = new JPanel();
		JPanel chkBxPanel = new JPanel();
		
		// Details
		numLabel.setFont( new Font("SansSerif", Font.BOLD, 14 ));
		answerLabel.setFont( new Font( "SansSerif", Font.PLAIN, 14 ));
		
		// Size
		answerLabel.setPreferredSize( new Dimension(300, 20));
		answerLabel.setMinimumSize( new Dimension( 300, 20));
		answerLabel.setMaximumSize( new Dimension(300, 20));
		
		if ( 0 == ( count % 2 ) ) {                            
			rowPanel.setBackground( evenIdxClr );
			numPanel.setBackground( evenIdxClr );
			cluePanel.setBackground( evenIdxClr );
			answerPanel.setBackground( evenIdxClr );
			chkBxPanel.setBackground( evenIdxClr );
			valChkBx.setBackground( evenIdxClr );
		} else {
			rowPanel.setBackground( oddIdxClr );
			numPanel.setBackground( oddIdxClr );
			cluePanel.setBackground( oddIdxClr );
			answerPanel.setBackground( oddIdxClr );
			chkBxPanel.setBackground( oddIdxClr );
			valChkBx.setBackground( oddIdxClr );
		}
		
		// Make background red
//		if ( constraint() ) {
//			
//		}
		
		// Layout
		numPanel.setLayout( new BoxLayout( numPanel, BoxLayout.Y_AXIS));        // Layout of subPanels
		answerPanel.setLayout( new BoxLayout( answerPanel, BoxLayout.Y_AXIS));
		chkBxPanel.setLayout( new BoxLayout( chkBxPanel, BoxLayout.Y_AXIS));		
		rowPanel.setLayout( new BoxLayout( rowPanel, BoxLayout.X_AXIS ) );      // Layout of whole row.
	
		// Setting up TextArea
		JTextArea tArea = new JTextArea( aString );                            
		tArea.setColumns( 30 );
		
		// Setting up Text Area behavior
		tArea.setFont(new Font("SansSerif", Font.ITALIC, 14));
		tArea.setLineWrap(true);
		tArea.setWrapStyleWord(true);
		tArea.setBackground( Color.LIGHT_GRAY);
		
		// Setting up behavior of CheckBox
		valChkBx.addItemListener( 
				new ItemListener() {

					public void itemStateChanged(ItemEvent arg0) {                               // Item was Selected or Deselected.

						if ( ItemEvent.SELECTED == arg0.getStateChange() ) {          // SELECTED OR DESELECTED

							JCheckBox ourChkBx = (JCheckBox) arg0.getItem();                     // The JCheckbox that was seleceted
							Position ourPos = new Position();
							String ourClue = "";
							String ourAns = "";
							
							if ( ourChkBx.getName().contains(_acsStr )) {
								ourPos = _acsStrPosMap.get( ourChkBx.getName() );           // Get the position that goes with this CheckBox
								ourClue = _acsPosTxtMap.get( ourPos ).getText();              // Get Clue associated with the CheckBox.
								ourAns = _acsPosLblMap.get( ourPos ).getText();               // Get Answer assoc with CheckBox
							} else {
								ourPos = _dwnStrPosMap.get( ourChkBx.getName() );           // Get the position that goes with this CheckBox
								ourClue = _dwnPosTxtMap.get( ourPos ).getText();              // Get Clue associated with the CheckBox.
								ourAns = _dwnPosLblMap.get( ourPos ).getText();               // Get Answer assoc with CheckBox
							}
							
//							String regex = "^((\\s)*(\\w)+(\\s)*)+$";                            // Looks for a letter or number anywhere in the string
							String regexClue = "^((\\s)*(\\S)+(\\s)*)+$";
							String regexAns = "^((\\s)*([a-zA-Z0-9])+(\\s)*)+$";
							// NO LETTERS, then no checkmork.
							
							
							System.out.println( "[" + ourClue + "]");
							System.out.println( "[" + ourAns + "]");
							System.out.println("Clue : Answer   " + ourClue.matches(regexClue) + " : " + ourAns.matches(regexAns) );
							if ( (!(ourClue.matches(regexClue)) ) 
									|| (!( ourAns.matches(regexAns))) ) {                              // Do not allow selection if clue or answer empty.
								ourChkBx.setSelected(false);                                     // Cannot select, not valid
							}
							
						}

					}
					
				});
		
		
		// Settup Lookup Mappings
		if ( isVertical ) {
			_dwnPosTxtMap.put(aPos, tArea);                                     // Save appropriate TextArea for retrieval
			_dwnPosChkMap.put(aPos, valChkBx);                                  // Save correct ChkBx
			_dwnPosLblMap.put(aPos, answerLabel);                               // Save the answer that goes with thie Position.
			valChkBx.setName( Integer.valueOf( aPos.getPlace()) + _dwnStr );    // Will either say #DOWN
			_dwnStrPosMap.put( valChkBx.getName(), aPos);                   // Will bring up the chechBox with this name
			
		} else {
			_acsPosTxtMap.put(aPos, tArea);                                     // Save appropriate TextArea for retrieval
			_acsPosChkMap.put(aPos, valChkBx);                                  // Save correct ChkBx
			_acsPosLblMap.put(aPos, answerLabel);                               // Save the answer that goes with this Position
			valChkBx.setName(Integer.valueOf( aPos.getPlace()) + _acsStr );     // Will either say #ACROSS
			_acsStrPosMap.put( valChkBx.getName(), aPos);                   // Will bring up the chechBox with this name
		}
		
		// Add components to subPanels.
		numPanel.add( numLabel );
		numPanel.add( Box.createVerticalGlue() );
		
		cluePanel.add( tArea, BorderLayout.CENTER);
		
		answerPanel.add( answerLabel );
		answerPanel.add( Box.createVerticalGlue() );
		
		chkBxPanel.add( valChkBx );
		chkBxPanel.add( Box.createVerticalGlue() );
		
		// Fill Component.
		rowPanel.add( Box.createHorizontalStrut(2) );
		rowPanel.add( numPanel );
		rowPanel.add( Box.createHorizontalStrut(5) );
		rowPanel.add(cluePanel );
		rowPanel.add( Box.createHorizontalStrut(5) );
		rowPanel.add( answerPanel );
		rowPanel.add( Box.createHorizontalStrut(5) );
		rowPanel.add( chkBxPanel );
		rowPanel.add( Box.createHorizontalStrut(10) );
		
		
		return rowPanel;
	}
	
	/**
	 * GETTEXTCLUE	will get the text area that was asssociated with the Position that is entered in the
	 * parameter list.
	 * Preconditions:  The LastCheckDialog was previously run.  A clue matching the inputted 
	 * 				Position and orientation MUST exist in the model.  If not will return NULL.
	 * 				Should be used by createView, to get values out of LastCheckDialog.
	 * Postconditions: The value in the TextArea associated with the inputted Position will be will be 
	 * 				returned as a String.
	 */
	public String getTextClue( Position aCluePos, boolean isVertical ) {
		String retVal = "";
		
//		if ( ! (isVertical) ) {                                // If Horizontal
//			JTextArea ta = _acsPosTxtMap.get( aCluePos );
//			retVal = ( ( null == ta ) ? null : ta.getText() ); // If not NULL, means mapping exists at this orientation return String held here
//		} else {
//			JTextArea ta = _dwnPosTxtMap.get( aCluePos );
//			retVal = ( ( null == ta ) ? null : ta.getText() ); // If not NULL return String held here
//		}
		
		if ( ! (isVertical) ) {                                // If Horizontal
			JTextArea ta = _acsPosTxtMap.get( aCluePos );     
			
			if ( null == ta ) {                                // If mapping no exists.
				retVal = null;
			} else {                                           // If mapping exists.
				String nwStr = ta.getText();
				String oldStr = _model.getAcrossHints().get( aCluePos );
				
				if ( ( ! ( nwStr.equals( oldStr )) ) 
						&& ( _acsPosChkMap.get( aCluePos ).isSelected() ) ) {           // If new string is different than old, and has been validated.
					retVal = nwStr;
				} else {                                       // If new and old are the same string.
					retVal = oldStr;
				}
				
			}
			
		} else {                                               // If Vertical
			JTextArea ta = _dwnPosTxtMap.get( aCluePos );     
			
			if ( null == ta ) {                                // If no mapping exists, return null
				retVal = null;
			} else {                                           // IF mapping exists return correct string
				String nwStr = ta.getText();
				String oldStr = _model.getDownHints().get( aCluePos );
				
				System.out.println( "New String is: " + nwStr + ",\nOld String is: " + oldStr + ",\nCheckBox is selected: " + _dwnPosChkMap.get( aCluePos ).isSelected() );
				
				if ( (! ( nwStr.equals( oldStr)) ) 
						&& ( _dwnPosChkMap.get( aCluePos ).isSelected() )) {  // If ChkBx assoc. w Clue is checked.
					retVal = nwStr;
				} else {
					retVal = oldStr;
				}
				
			}
			
		}
		
		return retVal;
	}
	
	/**
	 * FINDSTRING will take a position from the start of a region, and an orientation and return
	 * the data that exists in that region.  It replaces empty regions with spaces for human readability.
	 * Preconditions:  Assumes that the Position passed in is the beginning of a valid region
	 * 				for the inputted orientation.
	 * 				Position should not be null.
	 * 				Position should be a valid position on the board.  Therefore, not a bumper,
	 * 				or off the board.
	 * Postconditions:  The data in the region whose starting position has been inputted, and is of the
	 * 				inputted orientation will be returned as a string.  The string will treat null characters
	 * 				as spaces for output.
	 * @param aPos The start of a region.
	 * @param isVertical tells whether the region is Vertical and Horizontal
	 * @return The compiled string of what was in the region indicated by the parameters.
	 */
	private String findString( final Position aPos, boolean isVertical ) {
		// TODO 
		StringBuffer retVal = new StringBuffer();
		int size = _model.getSideLength();
		int pRow = aPos.getRow();
		int pCol = aPos.getCol();
		int move = ((isVertical) ? aPos.getRow() : aPos.getCol() );
		
		char letter = '\0';
		while ( (move < size) && ( (letter = _model.getPlace_Created(pRow, pCol)) != '@') ) {
			
			if ( ( pRow < size ) 
					&& ( pCol < size ) ) {   // If not a bumper, or off board.
				
				if ( '\0' == letter ) {
					retVal.append( '_' );   // Put a space for nothing
				} else {
					retVal.append( letter );  // Put the letter that was found.
				}
				
			} else {                          // Was a bumper or off the board
				move = size;                            // End loop
			}

			if ( isVertical ) {               // If Vertical increase row
				pRow++ ; 
			} else {
				pCol++;
			}
			
			move++;                           // Incr move
			
		}
				
		return retVal.toString();              // Return string that was created.
	}

	/**
	 * MAKEORIENTPANEL will make the panel that will hold the orientation that is 
	 * indicated in the parameters.
	 * @param isVertical
	 */
	private JPanel makeOrientPanel(boolean isVertical) {
		JPanel orientLabelPanel = new JPanel();
		String orientStr = ( ( isVertical ) ? "DOWN" : "ACROSS" );
		
		JLabel orientLabel = new JLabel( orientStr );
		orientLabel.setFont( new Font("SansSerif", Font.BOLD, 16 ));
		
		orientLabelPanel.setLayout( new BoxLayout( orientLabelPanel, BoxLayout.X_AXIS ));
		orientLabelPanel.add( Box.createHorizontalStrut(2) );
		orientLabelPanel.add( orientLabel );
		orientLabelPanel.add( Box.createHorizontalGlue());
		
		return orientLabelPanel;
	}
	
	/**
	 * HASDECIDED will tell whether the user has exited the Dialog with the finish button or some 
	 * other means.  If the finish button was used then then the values that were changed will affect
	 * the game.  Otherwise, those values will not be picked up.
	 */
	public boolean hasDecided() {
		return _hasDecided;
	}
	
	final private String _acsStr = "ACROSS";
	final private String _dwnStr = "DOWN";
	private boolean _hasDecided ;
	private PMModelInterface _model;                 // Used to get PMModel state.
	private PMControllerInterface _controller;       // Used to set PMModel state.
	private Map<Position, String> _tmpAcsMap ;       // Used to get the Across mappings for later retrieval
	private Map<Position, String> _tmpDwnMap ;       // Used to get the Down mappings for later retrieval
	private Map<Position, JTextArea> _acsPosTxtMap ; // Used to get the TextAreas for the Down Map.
	private Map<Position, JTextArea> _dwnPosTxtMap ; // Used to get the TextAreas for the Down Map.
	private Map<Position, JCheckBox> _acsPosChkMap ; // Used to get the CheckBox for this Across Row
	private Map<Position, JCheckBox> _dwnPosChkMap ; // Used to get the CheckBox for this Across Row
	private Map<Position, JLabel> _acsPosLblMap ;    // Maps the Positions to the labels they represent.
	private Map<Position, JLabel> _dwnPosLblMap ;    // Maps the Positions to the labels they represent.
	private Map<String, Position> _acsStrPosMap ;
	private Map<String, Position> _dwnStrPosMap ;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PMModelInterface theModel = new PMModel();
		PMControllerInterface theController = new PMController( theModel );
		
		LastCheckDialog cid = new LastCheckDialog(null, theModel, theController);
//		cid.startPanel();
		cid.pack();
		System.out.println( cid.getPreferredSize());
		cid.setVisible(true);
		
		
		PMModel aModel = (PMModel) theModel;
		System.out.println( aModel );          // Debug.
		
		
	}

}
