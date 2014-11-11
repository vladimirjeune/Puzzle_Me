/**
 * 
 */
package jeune_Trie;

/**
 * This Observer is for modifications to the list of clues that are associated with the
 * regions on the board.  
 * @author vladimirjeune
 */

/**
 * CLUEOBSERVER tells when the View should check on the content of the ACROSS or DOWN hints
 * becuase it has changed.
 */
public interface ClueObserver {
	void updateClue();               
}


