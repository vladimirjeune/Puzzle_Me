/**
 * 
 */
package jeune_Trie;

/**
 * This Observer is for changes in Currents position on the board
 * @author vladimirjeune
 */

/**
 * CURRENTOBSERVER tells when the View should check on the position of current on the board
 * becuase it has changed.
 */
public interface CurrentObserver {
	void updateCurrent();               
}
