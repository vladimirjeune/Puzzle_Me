/**
 * 
 */
package jeune_Trie;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author vladimirjeune
 * Handles how the application will look to the user and nothing else.  All data gets passed 
 * through and look is updated when necessary.
 */
public class PMView implements RegionObserver, CurrentObserver,
		BoardContentsObserver, ActionListener,ClueObserver, PMViewInterface {

	public PMView( PMControllerInterface aController, PMModelInterface aModel ) {
		_controller = aController;
		_model = aModel;
		
		_mid = new ModeInputDialog( null, _controller );
		_tid = new TypeInputDialog( null );
		_bid = new BumperInputDialog( null, _model, _controller );
		_pid = new PlayInputDialog( null, _model, _controller );
		_lcd = new LastCheckDialog( null, _model, _controller );
				
		_viewFrame = new JFrame();
		_boardPanel = new PlayPanel( _model, _controller );
				
		// Registering for things that we should be keeping track of in the model.
		_model.registerObserver((CurrentObserver)this);
		_model.registerObserver((RegionObserver)this);
		_model.registerObserver((BoardContentsObserver)this);
	}
	
	/**
	 * CREATEVIEW makes view for Creation of puzzles.
	 */
	public void createView() {
		boolean acrossBool = true;
		boolean downBool = false;

		_clueInputPanel = new ClueInputPanel( _model, _controller );         // For board creation; where user inputs hints for regions.
		_cluesAcrossPanel = new CluePanel( _model, _controller, acrossBool );
		_cluesDownPanel = new CluePanel( _model, _controller, downBool );
		_suggestPanel = new SuggestPanel( _model, _controller );             // Suggest
		
		Container contentPane = _viewFrame.getContentPane();
		
		JPanel saveFinishPanel = makeSaveFinishPanel();
		
		JPanel cluesPanel = makeCluesPanel();                                // Make cluesPanel
		
		startPanels();                                                       // Start all panels
		
		contentPane.add( saveFinishPanel, BorderLayout.NORTH);        // Temp
		contentPane.add( _boardPanel, BorderLayout.CENTER );
		contentPane.add( _clueInputPanel, BorderLayout.SOUTH);
		contentPane.add( cluesPanel, BorderLayout.WEST);
		contentPane.add( _suggestPanel, BorderLayout.EAST);                 // Suggest
		
		_viewFrame.setTitle("Create Puzzle");
		_viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_viewFrame.pack();
		_viewFrame.setVisible(true);
		
	}

	/**
	 * MAKESAVEFINISHPANEL will make the panel that will handle the Save and Finish Buttons.
	 * This function will be used in the create view().
	 * @return
	 */
	private JPanel makeSaveFinishPanel() {
		JPanel saveFinishPanel = new JPanel();                        // Temp
		JButton saveBtn = new JButton("SAVE");
		JButton finishBtn = new JButton("FINISH");
		saveFinishPanel.setLayout( new BoxLayout( saveFinishPanel, BoxLayout.X_AXIS));
		saveFinishPanel.add( Box.createHorizontalGlue());
		saveFinishPanel.add(saveBtn);
		saveFinishPanel.add( Box.createHorizontalStrut(5));
		saveFinishPanel.add(finishBtn);
		saveFinishPanel.add( Box.createHorizontalStrut(5));
		
		saveBtn.addActionListener(
				new ActionListener() 
				{

					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						_controller.cOutputModelState("currentGame.lst");
					}
					
				});
		
		finishBtn.addActionListener( 
				new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						Map<Position, String> acsMap = _model.getAcrossHints();
						Map<Position, String> dwnMap = _model.getDownHints();
						
						_lcd = new LastCheckDialog(null, _model, _controller);        // Always up to date.
						_lcd.pack();
						_lcd.setVisible(true);
						
						System.out.println( _lcd.getTextClue(new Position(0,0,1), true));

						if ( _lcd.hasDecided() ) {                                          // Only make changes if user wanted to.
							// ACROSS
							int cnt = 0;
							for ( Map.Entry<Position, String> entry : acsMap.entrySet() ) {  // Build up String to put in Map.
								StringBuffer indexAcrossStr = new StringBuffer() ;                
								String theClue = _lcd.getTextClue(entry.getKey(), false);    // The Text
								int index = entry.getKey().getPlace();                       // Clue Num

								if ( index < 10 ) {                                          // Correct # of places
									indexAcrossStr.append("  ");
								} else if ( index < 100 ) {
									indexAcrossStr.append(" ");
								}

								indexAcrossStr.append( entry.getKey().getPlace() + ".) ") ;        // Append #

								indexAcrossStr.append( theClue );                                  // Appwnd Clue

								System.out.println( indexAcrossStr);

								acsMap.put(entry.getKey(), theClue );                         // Change Model
								_cluesAcrossPanel.setIndexPosition(cnt, indexAcrossStr.toString() );// Change Clue Panel
								cnt++;
							}

							// DOWN
							int itr = 0;
							for ( Map.Entry<Position, String> entry : dwnMap.entrySet() ) {  // Build up String to put in Map.
								StringBuffer indexDownStr = new StringBuffer() ;                
								String theClue = _lcd.getTextClue(entry.getKey(), true);    // The Text
								int index = entry.getKey().getPlace();                       // Clue Num

								if ( index < 10 ) {                                          // Correct # of places
									indexDownStr.append("  ");
								} else if ( index < 100 ) {
									indexDownStr.append(" ");
								}

								indexDownStr.append( entry.getKey().getPlace() + ".) ") ;        // Append #

								indexDownStr.append( theClue );                                  // Appwnd Clue

								System.out.println( indexDownStr);

								dwnMap.put(entry.getKey(), theClue );                         // Change Model
								_cluesDownPanel.setIndexPosition(itr, indexDownStr.toString() );// Change Clue Panel
								itr++;
							}
						}
						
//						// ACROSS
//						for ( Map.Entry<Position, String> entry : acsMap.entrySet() ) {
//							_controller.cSetAcrossOrDownHint(true, entry.getKey(), _lcd.getTextClue(entry.getKey(), false));
//						}
//						
//						// DOWN
//						for ( Map.Entry<Position, String> entry : dwnMap.entrySet() ) {
//							_controller.cSetAcrossOrDownHint(false, entry.getKey(), _lcd.getTextClue(entry.getKey(), true));
//						}

						
						
					}
					
				});
		
		return saveFinishPanel;
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
	private String getSpaces( int aNum ) {
		StringBuffer retVal = new StringBuffer();
		
		if ( aNum < 10 ) {
			retVal.append("  ");
		} else if ( aNum < 100 ) {
			retVal.append(" ");
		}
		
		return retVal.toString();
	}
	
	/**
	 * @return
	 */
	private JPanel makeCluesPanel() {
		JPanel cluesPanel = new JPanel();
		cluesPanel.setLayout( new BoxLayout( cluesPanel, BoxLayout.Y_AXIS));
		cluesPanel.add( _cluesAcrossPanel );
		cluesPanel.add( Box.createRigidArea(new Dimension(0,5)));
		cluesPanel.add( _cluesDownPanel );
		cluesPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		return cluesPanel;
	}

	/**
	 * 
	 */
	private void startPanels() {
		// Start Panels
		_boardPanel.startPuzzleCreation();
		_clueInputPanel.startPanel();
		_cluesAcrossPanel.startPanel();
		_cluesDownPanel.startPanel();
		_suggestPanel.startPanel();                                        // Suggest
	}
	
	/**
	 * USERINPUTPATH will start taking input about what the user wants to do.  
	 * It will ask what Mode the user wants to use, and depending on answers obtained 
	 * will take the user to the appropriate views, to enter more information or start
	 * creation of a puzzle or start playing a game.  
	 * This should follow a usage path that makes sense to start with.
	 * Preconditions: NA
	 * Postconditions: The user will end up where they want to be depending on answers
	 * 				that they give.
	 */
	public void userInputPath( final JFrame anOwner ) {

		// Ask User what mode he would like to use.
		startModeDialog(anOwner);
		
		if ( UseType.CREATE.equals(_model.getUseType())) {
			startTypeDialog(anOwner);
			
			if ( GameType.NXN.equals(_model.getGameType())) {
				_controller.cSetMirrorType(MirrorType.FLATFLIP);     // This is the Default Mirror.
				// TODO Call Flip Board have user create bumpers, then start creation.
				startBumperDialog( anOwner );
				
				// CreateView
				createView();
			} else if ( GameType.CRISSCROSS.equals(_model.getGameType())) {
				// TODO Create CrissCross scenario, where user can input words, and have
				// help crisscrossing them.
			}
			
		} else if ( UseType.PLAY.equals(_model.getUseType()) ) {        // For now is open
			// TODO Let user choose from list of saved games to play. 
			_controller.cInputModelState("currentGame.lst");           // Tmp until start play create.
			createView();                                              // ditto.
		}
	}
	
	/**
	 * STARTMODEDIALOG will start the Dialog that will ask the user which mode they want to use.
	 * @param owner the JFrame that this Dialog belongs
	 * @return 
	 */
	public void startModeDialog( JFrame owner ) {
		//ModeInputDialog _mid = new ModeInputDialog( owner, _controller );
		_mid.pack();
		_mid.setVisible(true);
		System.out.println("You got here");

	}
	
	/**
	 * STARTTYPEDIALOG will start the Dialog that will alk the user which Type of puzzle they want 
	 * to create.
	 * @param owner the JFrame that this Dialog belongs
	 */
	public void startTypeDialog( JFrame owner ) {
		//TypeInputDialog _mid = new TypeInputDialog( null );
		_tid.pack();
		_tid.setVisible(true);            // Will hold here until answered by user
		
		_controller.cSetBoardGame(_tid.getGameType());	
		
	}
	
	/**
	 * STARTBUMPERDIALOG starts the Dialog that will ask for bumper placement on the board, and
	 * finalize the board.
	 * @param owner the JFrame that this Dialog belongs
	*/
	public void startBumperDialog( JFrame owner ) {
		// Grey out disabled region before you make the board visible.
		_bid.setGreyedRegion();
		_bid.pack();
		_bid.setVisible(true);

	}
	
	/**
	 * UPDATEREGION Used by a notification.  Tells that the Region we are tracking 
	 * may have changed.
	 * @see jeune_Trie.RegionObserver#updateRegion()
	 */
	public void updateRegion() {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Not yet implemented.");
	}

	/**  
	 * UPDATECURRENT Used by a notification.  Called when the Current position we 
	 * are tracking has changed.
	 * @see jeune_Trie.CurrentObserver#updateCurrent()
	 */
	public void updateCurrent() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented.");

	}

	/**
	 * UPDATEBOARDCONTENTS Used by a notification.  Called when the user inputted values on the 
	 * board may have changed.  Therefore, we will have to visually update the board with the
	 * new data from the model.
	 * @see jeune_Trie.BoardContentsObserver#updateBoardContents()
	 */
	public void updateBoardContents() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not yet implemented.");
		
	}
	
	/**  
	 * UPDATECLUE Used by a notification.  Called when the Clue position we 
	 * are tracking has changed.
	 * @see jeune_Trie.CurrentObserver#updateCurrent()
	 */
	public void updateClue() {
		// TODO Auto-generated method stub
		
	}



	/** 
	 * Called when an Event has taken place in the UI.  We should act appropriately to 
	 * each event.  Either ignoring it or performing some action.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//throw new UnsupportedOperationException("Not yet implemented.");
	}

	
	private PMModelInterface _model;
	private PMControllerInterface _controller;
	
	private ModeInputDialog _mid ;                     // Input mode game is used in
	private TypeInputDialog _tid ;                     // Input type of game
	private BumperInputDialog _bid ;                   // Input bumpers for board
	private PlayInputDialog _pid;
	private LastCheckDialog _lcd;                      // Dialog for final lookover of game before making it playable.
	
	private JFrame _viewFrame;                         // Frame holding the game.
	private PlayPanel _boardPanel;                     // Panel that holds the board
	private ClueInputPanel _clueInputPanel;            // Panel where clues can be inputted
	private CluePanel _cluesAcrossPanel;               // Panel holding inputted across clues.
	private CluePanel _cluesDownPanel;                 // Panel holding inputted down clues.
	private SuggestPanel _suggestPanel;                // Panel where suggestions will be shown
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
