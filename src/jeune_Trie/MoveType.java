/**
 * 
 */
package jeune_Trie;

/**
 * MOVETYPE indicates what direction was inputted by the user, when using the arrow keys.
 * it contains a function that indicates whether the movement was HORIZONTAL.
 *  
 * @author vladimirjeune
  */
public enum MoveType {

	/**
	 * LEFT indicates the user wanted to go left.
	 */
	LEFT,
	
	/**
	 * RIGHT indicates the user wanted to go right.
	 */
	RIGHT,
	
	/**
	 * UPindicates the user wanted to go up.
	 */
	UP,
	
	/**
	 * DOWN indicates the user wanted to go down.
	 */
	DOWN;
	
	/**
     * Is this move Horizontal
     *
     * @param Move to be asked whether it is Horizontal or not
     *
     * @return true if it is a Horizontal Move
     */
    public static boolean isMoveHorizontal( MoveType aMove )
        {

        switch ( aMove )
            {

            case UP:
            case DOWN:
            default:
                return false;

            case LEFT:
            case RIGHT:
                return true;
            }
        }
	
	/**
     * Is this move in a Forward direction
     *
     * @param Move to be asked whether it is moving Forward or not
     *
     * @return true if it is a Forward Move
     */
    public static boolean isMoveForward( MoveType aMove )
        {

        switch ( aMove )
            {

            case LEFT:
            case UP:
            default:
                return false;

            case RIGHT:
            case DOWN:
                return true;
            }
        }
    
}
