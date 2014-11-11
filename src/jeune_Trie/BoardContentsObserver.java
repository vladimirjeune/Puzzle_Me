/**
 * 
 */
package jeune_Trie;

/**
 * Indicates to Observers that the letters that are on the board have changed
 * @author vladimirjeune
 */

/**
 * BOARDCONTENTSOBSERVER tell Observer that the board's contents, as it relates
 * to user inputted characters, has changed.
 */
public interface BoardContentsObserver {
	void updateBoardContents();
}
