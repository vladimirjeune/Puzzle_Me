/**
 * 
 */
package jeune_Trie;

/**
 * This class will be used to tell the what region of the board is being actively 
 * manipulated.  It defines the area of possible change.
 * @author vladimirjeune
 */
public class ActiveRegion implements Comparable {

	/**
	 * Default Constructor 
	 * Preconditions:  Must be set to an actual region before use.
	 * Postconditions: ActiveRegion initialized to no actual region.
	 */
	public ActiveRegion() {
		init();
	}
	
	/**
	 * Constructor
	 * Preconditions:  NA
	 * Postconditions: Object initialized
	 * @param aStartPosition The starting position for this region.
	 * @param aLength The number of positions this region encompasses.
	 * @param anOrientation This value mod 2 tells whether the region is Horizontal:0
	 * 		or Vertical:1.
	 */
	public ActiveRegion( Position aStartPosition, int aLength, int anOrientation ) {
		init( aStartPosition, aLength, anOrientation );
	}
	
	/**
	 * This function initializes the ActiveRegion with default values and is 
	 * used primarily by the constructor.  
	 * WARNING:  The default values do not apply to any region.  You must supply 
	 * 			valid values before the ActiveRegion is actually used.
	 * Preconditions:  You must supply valid values before the ActiveRegion is actually used.
	 * Postconditions: This object will be initialized with default values
	 */
	public void init() {
		_startPosition = new Position();
		_length = 0; 
		_orientation = 0;                            // This value % 2 == (0:Horizontal or 1:Vertical)
	}
	
	/**
	 * This function initializes the ActiveRegion with values that are inputted into the 
	 * parameter list.
	 * Preconditions:  NA
	 * Postconditions: This object will be initialized with the inputted values
	 * @param aStartPosition The starting position for this region.
	 * @param aLength The number of positions this region encompasses.
	 * @param anOrientation This value mod 2 tells whether the region is Horizontal:0
	 * 		or Vertical:1.
	 */
	public void init( Position aStartPosition, int aLength, int anOrientation ) {
		_startPosition = aStartPosition;
		_length = aLength;
		_orientation = anOrientation;
	}
	
	/**
	 * This function will set the Starting Position for this region.
	 * Preconditions:  NA
	 * Postconditions: The starting position of the region will be set.
	 * @param startPos The position that will now be the start position of this region.
	 */
	public void setStartPos( Position startPos ) {
		_startPosition = startPos;
	}
	
	/**
	 * This function will get the start position for this region.
	 * Preconditions:  NA
	 * Postconditions: A Position representing the start position will be returned
	 */
	public Position getStartPos() {                           // Still references original, do manually
		Position startPos = new Position();
		startPos = _startPosition;                                // Return object reference to copy.
		return startPos;
	}
	
	/**
	 * This functin will set the length of this region
	 * Preconditions:  NA
	 * Postconditions: The length of this region will be set
	 * @param aLength The length of this region
	 */
	public void setLength( int aLength ) {
		_length = aLength;
	}
	
	/**
	 * This function will set the Active Region to the inputted values.
	 * Preconditions:  NA
	 * Postconditions: Active Region will have all new values
	 */
	public void setActiveRegion( Position aStartPosition, int aLength, int anOrientation ) {
		init( aStartPosition, aLength, anOrientation );
	}
	
	/**
	 * This functin will set the length of this region
	 * Preconditions:  NA
	 * Postconditions: The length of this region will be returned
	 */
	public int getLength() {
		return _length;
	}
	
	/**
	 * This function will set the orientation for this region
	 * Preconditions:  NA
	 * Postconditions: The orientation for this region will be set.
	 * @param anOrienation An integer representing the orientation of this region.  
	 * 		Even means Horizontal, Odd means Vertical.
	 */
	public void setOrientation( int anOrientation ) {
		_orientation = anOrientation;
	}

	/**
	 * This function will set the orientation for this region
	 * Preconditions:  NA
	 * Postconditions: The orientation for this region will be set.
	 */
	public int getOrientation() {
		return _orientation;
	}
	
	/**
	 * This function will compare the Positions by their place ONLY, not their
	 * positions on the board.
	 * This will satisfy the Comparable Interface.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @param otherObject The object we are being compared to
	 */
	public final int compareTo(Object otherObject) {
		ActiveRegion other = (ActiveRegion) otherObject;
		                                                            // If startPositions are equal
		if ( _startPosition.getPlace() == other._startPosition.getPlace() ) {
			return ((_orientation % 2) - (other._orientation % 2)) ;        // Compare Orientations
		}
		
		return (_startPosition.getPlace() - other._startPosition.getPlace() );
	}
	
	/**
	 * This function overrides the equals().
	 * Preconditions:  Both objects must be the same class.
	 * Postconditions: A boolean reflecting the equalitiy of the two objects will be returned.
	 * 				
	 * @param otherObject The object we are being compared to
	 */
	@Override public  boolean equals( Object otherObject ) {
		
		// See if the objects are identical
		if ( this == otherObject ) {
			return true;
		}
		
		// must return false if the explicit parameter is null
		if (otherObject == null ) {
			return false;
		}
		
		// if the classes don't match, they can't be equal
		if (getClass() != otherObject.getClass()) {
			return false;
		}
		
		// now we know otherObject is a non-null TrieNode
		ActiveRegion other = (ActiveRegion)otherObject;
		
		// test whether the fields have identical values.
		return ( ( _startPosition.equals(other._startPosition) ) 
				&& ( _length == other._length) 
				&& ( _orientation == other._orientation ) );
		
	}

	/**
	 * This function overrides the equals().
	 * Preconditions:  Both objects must be the same class.
	 * Postconditions: A boolean reflecting the equalitiy of the two objects will be returned.
	 * 				
	 * @param otherObject The object we are being compared to
	 */
	public  boolean match( Object otherObject ) {
		
		// See if the objects are identical
		if ( this == otherObject ) {
			return true;
		}
		
		// must return false if the explicit parameter is null
		if (otherObject == null ) {
			return false;
		}
		
		// if the classes don't match, they can't be equal
		if (getClass() != otherObject.getClass()) {
			return false;
		}
		
		// now we know otherObject is a non-null TrieNode
		ActiveRegion other = (ActiveRegion)otherObject;
		
		// test whether the fields have identical values.
		return ( ( _startPosition.match(other._startPosition) ) 
				&& ( _length == other._length) 
				&& ( (_orientation % 2) == (other._orientation % 2) ) );
		
	}
	
	/**
	 * This is the overridden hash function
	 * Preconditions:  NA
	 * Postconditions: A hash value will be returned that allows for good distribution of values.
	 * 				Values used are those used in comparisons of these objects.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		
		result = 37 * result + _startPosition.hashCode();
		result = 37 * result + _length;
		result = 37 * result + _orientation;
		
		return result;
	}
	
	/**
	 * This function returns a String representation of the state of this object.
	 * This overrides java.lang.Object.toString();
	 * Preconditions:  NA
	 * Postconditions: The state of this object will be represented in String form
	 */
	@Override public String toString() {
		return getClass().getName() +
		" [ Start Position = " + _startPosition +
		", Length = " + _length +
		", Orientation = " + ( (_orientation % 2 == 0) ? "HORIZONTAL":"VERTICAL") +
		" ]";
	}
	
	

	Position _startPosition;                   // The start position that is the beginning or the region
	int _length;                                // The number of positions affected
	int _orientation;                           // % 0, means we go across, % 1, means we are vertical
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
