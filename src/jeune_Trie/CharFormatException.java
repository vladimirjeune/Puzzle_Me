/**
 * 
 */
package jeune_Trie;

import java.io.IOException;

/**
 * @author vladimirjeune
 *
 */
public class CharFormatException extends IOException {

	/**
	 * This exception will be thrown if the wrong format is inputted into the Tree.
	 * Only single alphanumeric characters are allowed into a TrieNode.  No punctuation.
	 */
	public CharFormatException() {
	}

	/**
	 * @param gripe
	 */
	public CharFormatException(String gripe) {
		super(gripe);
	}

}
