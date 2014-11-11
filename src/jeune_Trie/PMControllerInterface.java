/**
 * 
 */
package jeune_Trie;

import java.io.File;
import java.util.ArrayList;

/**
 * @author vladimirjeune
 * This is the interface for the PMController.  It will have functions that are considered
 * necesarry for use by a View or Model. 
 */
public interface PMControllerInterface {

	/**
	 * These 5 are mostly for Creation.  Play would take some other path, like showing a file chooser to choose from then bringing up Playboard.
	 */
	// cSetBoardUse, when get value, get rid window, put up the other.
	void cSetBoardUse( UseType aUse ) ;
	
	// cSetBoardGame, when get value, enable size dropdown
	void cSetBoardGame( GameType aGame );
	
	// Sets the type of mirror used in the game.  (Default: FLATFLIP)
	public void cSetMirrorType(MirrorType aMirror ) ;

	// cSetSize, when get value or OK then value, get rid of window, put up other.
	void cSetSize( int anInt );
	
	// cFlipBumper, View will handle look when get update msg from Model, after a bumper has been flipped.
	void cFlipBumper( Position aPos ) ;
	
	// cFinishBoardSetup, When OK pressed on flip panel after picked bumpers, will do this, and draw board, and game for Creation.
	void cFinishBoardSetup();

	// cIsSetup, tells when the board has finished being set up.
	boolean isSetup();
	
	// Tells what direction user inputted
	boolean cArrow( MoveType aMove );
	
	// Tells what position user clicked.
	boolean cClick( Position aPos );
	
	// Tells that the user has modified board data
	boolean cModify( char aChar );
	
	// Tells that the user wanted to move to the next Region on the board with this orientation
	void cTabEnter();
	
	/**
	 * CCLUESETREGSTART, Set to specific clue starting with Position pos, and orientation of input parameter.
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cTabEntercClueSetRegStart()
	 */
	boolean cClueSetRegStart( Position aPos, boolean isAcross )  ;
	
	/**
	 * SETACROSSORDOWNHINT will set the hint associated with the inputted variable, with this orientation
	 * if it exist..
	 */  
	boolean cSetAcrossOrDownHint( boolean isHorizontal, Position aStartPos, String aString );

	
	/**
	 * CADDWORDDICT will add a word to the dictionary, it must not have spaces or punctuation.
	 */
	boolean cAddWordDict( String aWord ) ;
		
	/**
	 * CDELETEWORDDICT will delete a word from the Dictionary.
	 */
	boolean cDeleteWordDict( String aWord ) ;
	
	/**
	 * CLOADDICT will load the Dictionary of this model with words from a local file.
	 */
	boolean cLoadDict( File aFile ); 
	
	/**
	 * CSUGGESTMATCHDICT will suggest a match to the incomplete word that is inputted.
	 * Words are incompleted by '_' NOT SPACES.
	 */
	ArrayList<String> cSuggestMatchDict( String aString ) ;
	
	/**
	 * CSUGGESTMATCHDICT will suggest a match to the incomplete word that is inputted.
	 * Words are incompleted by '_' NOT SPACES.
	 */
	ArrayList<String> cSuggestWordsOfLengthDict( int aLength ) ;

	/**
	 * CINPUTMODELSTATE will load this model with the values from an archived session.
	 */
	boolean cInputModelState( String aFileName ) ;
	
	/**
	 * COUTPUTMODELSTATE will archive the state of this model for later use.
	 */
	void cOutputModelState( String aFileName ) ;
	
	// Does what is needed to turn game off, Saves, Says bye, etc...  May take many more parameters.
	void cDone() ;
	
}
