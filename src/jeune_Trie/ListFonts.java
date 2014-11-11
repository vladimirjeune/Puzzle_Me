/**
 * 
 */
package jeune_Trie;

import java.awt.GraphicsEnvironment;

/**
 * Finds out which fonts are available on this computer.
 * Just an interesting class.
 * @author Core Java : Volume I, page 262
 *
 */
public class ListFonts {

	/**
	 * 
	 */
	public ListFonts() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// To find out which fonts are available on a paricular computer, call getAvailableFontFamilyNames method of the GraphicsEnvironment class.
		// To obtain an instance of the GraphicsEnvironment class that describes the graphice environment of the user's system, use the static getLocalGriphicsEnvironment method.
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		for ( int i = 0; i < fontNames.length; i++ ) {
			System.out.println( fontNames[i]);
		}
		
	}

}
