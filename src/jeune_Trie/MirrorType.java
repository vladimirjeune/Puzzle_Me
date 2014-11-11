/**
 * 
 */
package jeune_Trie;

/**
 * Indicates what to do with the Bumpers that will be inputted during the creation of the board.
 * Bumpers can be mirrored on the board in different ways.
 * 
 * @author vladimirjeune
 */
public enum MirrorType {
	
	/**
	 * For use on boards with EVEN N.  Makes bumpers placed in one quadrant have mirrors in 
	 * the other four quadrants of the grid.
	 */
	FOURWAY,
	
	/**
	 * For use on boards with EVEN or ODD N.  Makes bumbers reflect along the Y axis, midway through
	 * the board.
	 */
	FLAT,
	
	/**
	 * For use on boards with EVEN or ODD N.  Makes bumpers reflect along the Y axis, midway through 
	 * the board, and then flips the reflected image along the Y axis.  
	 */
	FLATFLIP,
	
	/**
	 * For use on boards with ODD N.  Makes bumpers that reflect along the diagonal.  
	 * Reflect line, starts at the Bottom Left and ends at the Top Right.
	 */
	UPHILL,
	
	/**
	 * For use on boards with ODD N.  Makes bumpers that reflect along the diagonal.
	 * Reflect line, starts at the Top Left and ends at the Bottom Right.
	 */
	DOWNHILL;	
	
}
