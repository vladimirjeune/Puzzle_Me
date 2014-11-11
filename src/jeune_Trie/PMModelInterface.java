/**
 * 
 */
package jeune_Trie;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * @author vladimirjeune
 * This is the Interface for the PMModel.  It will have all the functions that should be called 
 * by View and Controller.
 */
public interface PMModelInterface {

	// makeBoard
	
	// addBumpers
	
	// findWordStarts
	
	// setCurrPos
	
	// getCurrPos
	
	// findActiveRegion
	
	// addWordDict
	
	// deleteWordDict
	
	// You should have a place where you can store words you want to use and try to put them in
	
	// findWordDict
	
	
	// void startApp();
	// void endApp
	
	// MOSTLY USED BY CONTROLLER
	// Directional use
	boolean userArrow( MoveType aMove ) ;
	
	// Mouse clicked on Board
	boolean userClick( Position aPos ) ;
	
	// Write/Clear on board
	boolean userModify( char aChar ) ;
	
	// Tab or Enter used on board
	void userTabEnter() ;
	
	// Set to specific clue starting with Position pos, and orientation of input parameter.
	boolean clueSetRegStart( Position aPos, boolean isAcross ) ;
	
	// Flip Bumper during NxN Board Creation
	boolean flipBumper( final Position aPos ) ;
	
	// Interacts with internal mapping for clues
	boolean setAcrossOrDownHint( boolean isHorizontal, Position aStartPos, String aString );
	
	// Sets the entered position to the start of the region set by the Position entered and the orientatin entered and the board.
	void setPosToRegionStart(Position currentPos, final boolean isHorizontal ) ;
	
	// Set the Use of the board, Play OR Create
	void setUseType( UseType aUseType ) ;
	
	// Set the Game on the board, NxN or CrissCross
	void setGameType( GameType aGameType ) ;

	// Set the Mirror that will be used.
	void setMirrorType(MirrorType mirror);

	// Set the size of the Board for Creation only
	void setSize( int aNum ) ;
	
	// Finish Creating the board after the bumpers have been flipped
	void finishBoardInit() ;
	 
	
	// MOSTLY USED BY VIEW
	// Get the Current Location
	Position getCurr() ;
	
	// Get the Active Reigion that can be written in.
	ActiveRegion getActiveRegion() ;
	
	// Get the Start Positions for the Hints on the board
	Set<Position> getStartPositions() ;
	
	// Get the get the character that resides on the playable region of the board passed in.
	char getPlace_Created( final Position aPos ) ;
	char getPlace_Created( final int aRow, final int aCol  ) ;
	
	// Get the String associated with the inputted parameters if it exists, otherwise return null.
	String getAcrossOrDownHint( boolean isHorizontal, Position aStartPos ) ;
	
	// Get the start position to this region, if possible.  Otherwise, do not move place.
	void findStartRegion(Position currentPos, boolean isHorizontal ) ;

	// Find the Position that represents the beginning of the Hint passed in.
	Position findHintPosition( int hintNum ) ;
	
	// Find the Start position that matches the inputted position, if such a startPosition exits.  Else return null.
	void findStartPos(Position currentPos) ;
	
	// Get Length of the Region passed in.
	int findRegionLength( final Position aPos ) ;
	
	// Get the Hints that go Across the board.
	Map<Position, String> getAcrossHints() ;
	
	// Get the Hints that go Down.
	Map<Position, String> getDownHints() ;
	
	// Get the board, DO NOT MODIFY.
	char[][] getTheBoard() ;
	
	// Get the list of Bumpers on the board.
	ArrayList<Position> getBumperList();
	
	// Get the Dictionary for this Game.
	WordDictionary getDictionary() ;
	
	// Get the WordBank for this game.
	WordDictionary getWordBank() ;
	
	// Get the GameType
	GameType getGameType() ;
	
	// Get the UseType
	UseType getUseType() ;
	
	// Return the Hint Number of the coords passed in.  Only returns a real value if the coords match the beginning of a Hint; 5000 is returned otherwise.
	int getHintPlace( final int aRow, final int aCol ) ;
	
	// Get the Orientation of the baord
	int getOrientation() ;
	
	// Get the Size of an NxN board ONLY
	int getSideLength() ;
	
	// Is the board finished being set up
	boolean isSetup();
	
	// The next few are for the Dictionary.
	
	// Return a list of suggested word completions for incomplete Strings _em_te => remote.
	ArrayList<String> suggestMatchDict( String aString ) ;
	
	// Return words in the Dictionary of appropriate length.
	ArrayList<String> suggestWordsOfLengthDict( int aLength ) ;
	
	// Add a word to the Dictionary.
	boolean addWordDict( String aWord ) ;
	
	// Remove word from Dictionary.
	boolean deleteWordDict( String aWord ) ;
	
	// Returns how many words are in the Dictionary.
	int numWordsDict() ;
	
	// Output all Dictionary Contents.  Costly operation.
	public ArrayList<String> outputDict() ;
	
	// Load the tree with words from a File in the local file system.
	boolean loadDict( File aFile ) ;
	
	// This will read the string that is in the Active Region on the board.
	String stringInRegion( ActiveRegion aRegion ) ;

	// This will take input from a saved session and apply it to this model.
	// Model should not be set up yet.
	boolean inputModelState(String aFileName);

	// Thies will archive the state of this model to a text file for later use.
	void outputModelState(String fileName);

	// Register various Observers
	void registerObserver( CurrentObserver anOb );      
	void registerObserver( RegionObserver anOb );       
	void registerObserver( BoardContentsObserver anOb );
	void registerObserver( ClueObserver anOb );

	// Remove various Observers
	void removeObserver( CurrentObserver anOb );        
	void removeObserver( RegionObserver anOb );         
	void removeObserver( BoardContentsObserver anOb );
	void removeObserver( ClueObserver anOb );

	// More as necessary.  some will be public in other source files.
	
	
	
}
