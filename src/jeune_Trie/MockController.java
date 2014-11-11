/**
 * 
 */
package jeune_Trie;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * @author vladimirjeune
 *
 */
public class MockController implements PMControllerInterface {
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * This is the fake constructor
	 */
	public MockController() {
		// Nothing
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cArrow(jeune_Trie.MoveType)
	 */
	public boolean cArrow(MoveType aMove) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cClick(jeune_Trie.Position)
	 */
	public boolean cClick(Position aPos) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cDone()
	 */
	public void cDone() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cFinishBoardSetup()
	 */
	public void cFinishBoardSetup() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cFlipBumper(jeune_Trie.Position)
	 */
	public void cFlipBumper(Position aPos) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cFlipBumper(jeune_Trie.Position)
	 */
	public boolean isSetup() {
		return false;

	}

	
	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cModify(char)
	 */
	public boolean cModify(char aChar) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cSetBoardGame(jeune_Trie.GameType)
	 */
	public void cSetBoardGame(GameType aGame) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cSetBoardUse(jeune_Trie.UseType)
	 */
	public void cSetBoardUse(UseType aUse) {
		if ( UseType.CREATE.equals(aUse)) {
			System.out.println( UseType.CREATE);
		} else {
			System.out.println( UseType.PLAY);
		}
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cSetSize(int)
	 */
	public void cSetSize(int anInt) {
		System.out.println( anInt );

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cTabEnter()
	 */
	public void cTabEnter() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMControllerInterface#cSetMirrorType()
	 */
	public void cSetMirrorType(MirrorType aMirror) {
		// TODO Auto-generated method stub
		
	}

	public boolean cAddWordDict(String aWord) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean cClueSetRegStart(Position aPos, boolean isAcross) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean cDeleteWordDict(String aWord) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean cLoadDict(File aFile) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<String> cSuggestMatchDict(String aString) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> cSuggestWordsOfLengthDict(int aLength) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean cInputModelState(String aFileName) {
		// TODO Auto-generated method stub
		return false;
	}

	public void cOutputModelState(String aFileName) {
		// TODO Auto-generated method stub
		
	}

	public boolean cSetAcrossOrDownHint(boolean isHorizontal, Position aStartPos, String aString) {
		// TODO Auto-generated method stub
		return false;
	}



}
