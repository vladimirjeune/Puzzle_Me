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
 *
 */
public class MockModel implements PMModelInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#findHintPosition(int)
	 */
	public Position findHintPosition(int hintNum) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#findRegionLength(jeune_Trie.Position)
	 */
	public int findRegionLength(Position aPos) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#finishBoardInit()
	 */
	public void finishBoardInit() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#flipBumper(jeune_Trie.Position)
	 */
	public boolean flipBumper(Position aPos) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getAcrossHints()
	 */
	public Map<Position, String> getAcrossHints() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getActiveRegion()
	 */
	public ActiveRegion getActiveRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getBumperList()
	 */
	public ArrayList<Position> getBumperList() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getCurr()
	 */
	public Position getCurr() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getDictionary()
	 */
	public WordDictionary getDictionary() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getDownHints()
	 */
	public Map<Position, String> getDownHints() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getGameType()
	 */
	public GameType getGameType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getHintPlace(int, int)
	 */
	public int getHintPlace(int aRow, int aCol) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getOrientation()
	 */
	public int getOrientation() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getPlace_Created(jeune_Trie.Position)
	 */
	public char getPlace_Created(Position aPos) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getPlace_Created(int, int)
	 */
	public char getPlace_Created(int aRow, int aCol) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getSideLength()
	 */
	public int getSideLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getStartPositions()
	 */
	public Set<Position> getStartPositions() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getTheBoard()
	 */
	public char[][] getTheBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getUseType()
	 */
	public UseType getUseType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#getWordBank()
	 */
	public WordDictionary getWordBank() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#registerObserver(jeune_Trie.CurrentObserver)
	 */
	public void registerObserver(CurrentObserver anOb) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#registerObserver(jeune_Trie.RegionObserver)
	 */
	public void registerObserver(RegionObserver anOb) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#registerObserver(jeune_Trie.BoardContentsObserver)
	 */
	public void registerObserver(BoardContentsObserver anOb) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#registerObserver(jeune_Trie.ClueObserver)
	 */
	public void registerObserver(ClueObserver anOb) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#removeObserver(jeune_Trie.CurrentObserver)
	 */
	public void removeObserver(CurrentObserver anOb) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#removeObserver(jeune_Trie.RegionObserver)
	 */
	public void removeObserver(RegionObserver anOb) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#removeObserver(jeune_Trie.BoardContentsObserver)
	 */
	public void removeObserver(BoardContentsObserver anOb) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#removeObserver(jeune_Trie.ClueObserver)
	 */
	public void removeObserver(ClueObserver anOb) {
		// TODO Auto-generated method stub
		
	}
	
	// Sets the entered position to the start of the region set by the Position entered and the orientatin entered and the board.
	public void setPosToRegionStart(Position currentPos, boolean isHorizontal) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#setGameType(jeune_Trie.GameType)
	 */
	public void setGameType(GameType aGameType) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#setMirrorType(jeune_Trie.MirrorType)
	 */
	public void setMirrorType(MirrorType mirror) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#setSize(int)
	 */
	public void setSize(int aNum) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#setUseType(jeune_Trie.UseType)
	 */
	public void setUseType(UseType aUseType) {
		// TODO Auto-generated method stub

	}
	
	// Return null if there is no string associated with the inputted values, meaning the values are incorrect.
	public String getAcrossOrDownHint(boolean isHorizontal, Position aStartPos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// Keeps track of region, clue mappings.
	public boolean setAcrossOrDownHint(boolean isHorizontal, Position aStartPos, String aString) {
		// TODO Auto-generated method stub
		return false;
	}

	// This function always returns false.  It is for testing use ONLY.
	public boolean isSetup() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#userArrow(jeune_Trie.MoveType)
	 */
	public boolean userArrow(MoveType aMove) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#userClick(jeune_Trie.Position)
	 */
	public boolean userClick(Position aPos) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#userModify(char)
	 */
	public boolean userModify(char aChar) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see jeune_Trie.PMModelInterface#userTabEnter()
	 */
	public void userTabEnter() {
		// TODO Auto-generated method stub

	}

	public boolean clueSetRegStart(Position aPos, boolean isAcross) {
		// TODO Auto-generated method stub
		return false;
	}




	public void findStartPos(Position currentPos) {
		// TODO Auto-generated method stub
		
	}

	public void findStartRegion(Position currentPos, boolean isHorizontal) {
		// TODO Auto-generated method stub
		
	}

	public boolean addWordDict(String aWord) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deleteWordDict(String aWord) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean loadDict(File aFile) {
		// TODO Auto-generated method stub
		return false;
	}

	public int numWordsDict() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<String> outputDict() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> suggestMatchDict(String aString) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> suggestWordsOfLengthDict(int aLength) {
		// TODO Auto-generated method stub
		return null;
	}

	public String stringInRegion(ActiveRegion aRegion) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean inputModelState(String aFileName) {
		// TODO Auto-generated method stub
		return false;
	}

	public void outputModelState(String fileName) {
		// TODO Auto-generated method stub
		
	}


}
