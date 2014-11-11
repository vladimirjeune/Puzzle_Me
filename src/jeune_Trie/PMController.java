/**
 * 
 */
package jeune_Trie;

import java.io.File;
import java.util.ArrayList;

/**
 * @author vladimirjeune
 *
 */
public class PMController implements PMControllerInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private PMModelInterface _model;                   // The model this controller interacts with.
	private PMView _view;                              // The view that goes with this controller
	
	/**
	 * Constructor
	 */
	public PMController( PMModelInterface aModel ) {
		//_isSetup = false;                       // Says the board has not been set up yet. May move to Model
		_model = aModel;                        // Got model from main
		_view = new PMView(this, _model );      // The UI
		
		
		// view calls if any in another function we will take in the values from the various pop ups.
		
		// LOGIC FOR INPUT SHOULD BE FUNCTIONALIZED!
//		_view.startModeDialog(null);
//		
//		if ( UseType.CREATE.equals(_model.getUseType())) {
//			_view.startTypeDialog(null);
//			
//			if ( GameType.NXN.equals(_model.getGameType())) {
//				// Call Flip Board
//			}
//			
//		}
		
		_view.userInputPath(null);
		
	}
	
	/**
	 * CARROW Tells what direction user inputted to the View
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cArrow(jeune_Trie.MoveType)
	 * @param aMove MoveType indicating the direction that the user indicated they wish to move Current
	 */
	public boolean cArrow(MoveType aMove) {
		boolean retVal = false;
		
		if ( true == isSetup() ) {
			retVal =  _model.userArrow(aMove);			
		}
		return retVal;
	}

//	/**
//	 * CARROW Tells what direction user inputted to the View
//	 * Preconditions:
//	 * Postconditions: 
//	 * @see jeune_Trie.PMControllerInterface#cArrow(jeune_Trie.MoveType)
//	 * @param aMove MoveType indicating the direction that the user indicated they wish to move Current
//	 */
//	public boolean cArrow(int moveNum) {
//		boolean retVal = false;
//		
//		if ( 0 == moveNum ) {
//			
//		} else if ( 1 == moveNum ) {
//			
//		} else if ( 2 == moveNum ) {
//			
//		} else {
//			
//		}
//
//		if ( true == isSetup() ) {
//			retVal =  _model.userArrow(aMove);			
//		}
//		return retVal;
//	}
	
	/**
	 * CCLICK tells what position user clicked.
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cClick(jeune_Trie.Position)
	 * @param aPos Position indicating where on the board the user clicked.
	 */
	public boolean cClick(Position aPos) {
		boolean retVal = false;
		if ( true == isSetup() ) {
			retVal = _model.userClick(aPos);
			
		}
		return retVal;
	}

	/**
	 * CTABENTER, tells that the user wanted to move to the next Region on the board with this orientation
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cTabEnter()
	 */
	public void cTabEnter() {

		if ( true == isSetup() ) {
			_model.userTabEnter();			
		}

	}
	
	/**
	 * CMODIFY, tells that the user has modified board data
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cModify(char)
	 * @param aChar letter that the user wanted written at Current, or '0' if what is at Current is to be erased.
	 */
	public boolean cModify(char aChar) {
		boolean retVal = false;
		
		if ( true == isSetup() ) {
			return _model.userModify(aChar);
			
		}
		
		return retVal;
	}

	/** 
	 * CFINISHBOARDSETUP, When OK pressed on flip panel after picked bumpers, will do this, and draw board, and game for Creation.
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cFinishBoardSetup()
	 */
	public void cFinishBoardSetup() {

		if ( false == isSetup() ) {
			_model.finishBoardInit();                      // Finishing up board creation.
// done in previous function			_isSetup = true;                               // Board is now ready for creation

		}
		
	}

	/**
	 * CFLIPBUMPER, View will handle look when get update msg from Model, after a bumper has been flipped.
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cFlipBumper(jeune_Trie.Position)
	 * @param aPos Indicates Position on the board that should be flipped to be a bumper or not.
	 */
	public void cFlipBumper(Position aPos) {

		if ( false == isSetup() ) {
			_model.flipBumper(aPos);
		}
		
	}

	/**
	 * CSETBOARDUSE, when get value, get rid window, put up the other.
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cSetBoardGame(jeune_Trie.UseType)
	 * @param aUse Tells whether we are Creating or Playing
	 */
	public void cSetBoardUse(UseType aUse) {
		
		if ( false == isSetup() ) {
			_model.setUseType( aUse );	
		}
		
	}

	/**
	 * CSETBOARDGAME, when get value, enable size dropdown
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cSetBoardGame(jeune_Trie.GameType)
	 * @param aGame Tells whether we are doing NxN or Criss Cross
	 */
	public void cSetBoardGame(GameType aGame) {

		if ( false == isSetup() ) {
			_model.setGameType( aGame );

		}

	}

	/**
	 * CSETMIRROR, when get value, enable size dropdown
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cSetBoardGame(jeune_Trie.GameType)
	 * @param aGame Tells whether we are doing NxN or Criss Cross
	 */
	public void cSetMirrorType(MirrorType aMirror ) {

		if ( false == isSetup() ) {
			_model.setMirrorType( aMirror );
		}

	}
	
	/**
	 * CSETSIZE, when get value or OK then value, get rid of window, put up other.
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cSetSize(int)
	 * @param anInt The size board that we want to create.
	 */
	public void cSetSize(int anInt) {

		if ( false == isSetup() ) {
			_model.setSize(anInt);
		}

	}

	/**
	 * CDONE, does what is needed to turn game off, Saves, Says bye, etc...  May take many more parameters.
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cDone()
	 */
	public void cDone() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("This function is not yet implemented");
	}
	
	/**
	 * GETVIEW is for testing purposes only; otherwise, it will be private
	 * Preconditions:  NA
	 * Postconditions: A reference to the view will be given
	 */
	public PMView getView() {
		return _view;
	}
	
	/**
	 * GETMODEL is for testing purposes only; otherwise, it will be private
	 * Preconditions:  NA
	 * Postconditions: A reference to the model will be given
	 */
	public PMModelInterface getModel() {
		return _model;
	}
	
	/**
	 * ISSETUP returns a boolean telling whether the board has finished setup yet.
	 * Preconditions:  NA
	 * Postconditions: A boolean will be returned telling if the board has been set up already
	 */
	public boolean isSetup() {
//		return _isSetup;
		return _model.isSetup();
	}
	
	/**
	 * CCLUESETREGSTART, Set to specific clue starting with Position pos, and orientation of input parameter.
	 * Preconditions:
	 * Postconditions: 
	 * @see jeune_Trie.PMControllerInterface#cTabEntercClueSetRegStart()
	 */
	public boolean cClueSetRegStart( Position aPos, boolean isAcross )  {
		boolean retVal = false;
		
		if ( true == isSetup() ) {
			retVal = _model.clueSetRegStart(aPos, isAcross) ;
		}
		return retVal;
	}
	
	
	/**
	 * CADDWORDDICT will add a word to the dictionary, it must not have spaces or punctuation.
	 */
	public boolean cAddWordDict( String aWord ) {
		boolean retVal = false;
		
		if ( true == isSetup() ) {
			retVal = _model.addWordDict(aWord);
		}
		
		return retVal;
	}
	
	/**
	 * CDELETEWORDDICT will delete a word from the Dictionary.
	 */
	public boolean cDeleteWordDict( String aWord ) {
		boolean retVal = false;
		
		if ( true == isSetup() ) {
			retVal = _model.deleteWordDict(aWord);
		}
		
		return retVal;
	}
	
	/**
	 * CLOADDICT will load the Dictionary of this model with words from a local file.
	 */
	public boolean cLoadDict( File aFile ) {
		boolean retVal = false;
		
		if ( true == isSetup() ) {
			retVal = _model.loadDict( aFile );
		}
		
		return retVal;
	}
	
	/**
	 * CINPUTMODELSTATE will load this model with the values from an archived session.
	 */
	public boolean cInputModelState( String aFileName ) {
		boolean retVal = false;
		
		if ( false == isSetup() ) {
			retVal = _model.inputModelState( aFileName );
		}
		
		return retVal;
	}
	
	/**
	 * COUTPUTMODELSTATE will archive the state of this model for later use.
	 */
	public void cOutputModelState( String aFileName ) {
		if ( true == isSetup() ) {
			_model.outputModelState( aFileName );
		}
	}
	
	/**
	 * CSUGGESTMATCHDICT will suggest a match to the incomplete word that is inputted.
	 * Words are incompleted by '_' NOT SPACES.
	 */
	public ArrayList<String> cSuggestMatchDict( String aString ) {
		ArrayList<String> retVal = new ArrayList<String>();
		
		if ( true == isSetup() ) {
			retVal = _model.suggestMatchDict(aString);
		}
		
		return retVal;
	}
	
	/**
	 * CSUGGESTMATCHDICT will suggest a match to the incomplete word that is inputted.
	 * Words are incompleted by '_' NOT SPACES.
	 */
	public ArrayList<String> cSuggestWordsOfLengthDict( int aLength ) {
		ArrayList<String> retVal = new ArrayList<String>();
		
		if ( true == isSetup() ) {
			retVal = _model.suggestWordsOfLengthDict(aLength);
		}
		
		return retVal;
	}

//	PMModelInterface _model;                   // The model this controller interacts with.
//	PMView _view;                              // The view that goes with this controller
	//boolean _isSetup;                          // Tells if the board is setup already
	// The view

	/**
	 * CSETACROSSORDOWNHINT will set the hint associated with the inputted variable.  
	 * Preconditions:  The board should be set up before use.
 	 * 				The Start Position from the region should be used, since it contains the correct place for matching.
	 * Postconditions: The hint for the region starting with aStartPos and of the inputted orientation, will
	 * 				be set.  A notification will be sent to ClueObservers, if any.
	 * @param isHorizontal whether the clue is for a horizontal region or not.
	 * @param aStartPos    The position that indicates the start of the region.
	 * @param aString      The Clue the user wants to represent the region that matches the input parameters.
	 * @return boolean tells whether the set was successful.
	 */
	public boolean cSetAcrossOrDownHint(boolean isHorizontal, Position aStartPos, String aString) {
		boolean retVal = false;
		
		if ( true == isSetup() ) {
			retVal = _model.setAcrossOrDownHint(isHorizontal, aStartPos, aString);
		}
		
		return retVal;
	}

}
