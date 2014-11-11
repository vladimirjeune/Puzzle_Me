/**
 * This class will represent the position on the board.  Row and Column will be
 * represented, as well as, place in ordering.  These objects should be comparable
 * by their places.
 */
package jeune_Trie;

/**
 * @author vladimirjeune
 *
 */
public class Position implements Comparable {

	/**
	 * Default Constructor
	 *
	 */
	public Position() {
		init();
	}
	
	/**
	 * Constructor
	 * @param aRow Zero based index representing the row of this position
	 * @param aCol Zero based index representing the column of this position
	 * @param aPlace integer representing whether this is a Starting position or a
	 * 		regular position.  Starting positions start at 1; regular positions start
	 * 		at 5000.
	 */
	public Position( int aRow, int aCol, int aPlace ) {
		init(aRow, aCol, aPlace );
	}
	
	/**
	 * Constructor 
	 * @param aRow Zero based index representing the row of this position
	 * @param aCol Zero based index representing the column of this position
	 */
	public Position( int aRow, int aCol ) {
		init( aRow, aCol );
	}
	
	/**
	 * This is the initializer for the default constructor
	 * Everything is set to 0.  A place of 5000 indicates that this is a 
	 * normal position; not a starting position for a word.  Starting positions
	 * start at one.
	 */
	public void init() {
		_rowVal = 0;
		_colVal = 0;
		_startPlace = 5000;                  // This indicates that this is a normal position.
	}
	
	/**
	 * This is an init() for Postion.  Row, Column, and place are set.
	 * A place other than 5000
	 * @param aRow Zero based index representing the row of this position
	 * @param aCol Zero based index representing the column of this position
	 * @param aPlace integer representing whether this is a Starting position or a
	 * 		regular position.  Starting positions start at 1; regular positions start
	 * 		at 5000.
	 */
	public void init( int aRow, int aCol, int aPlace ) {
		_rowVal = aRow;
		_colVal = aCol;
		_startPlace = aPlace;
	}
	
	/**
	 * This is an init() for Postion.  Row, Column are set.
	 * The position's place is set to the default value of 5000.
	 * @param aRow Zero based index representing the row of this position
	 * @param aCol Zero based index representing the column of this position
	 */
	public void init( int aRow, int aCol ) {
		_rowVal = aRow;
		_colVal = aCol;
		_startPlace = 5000;                    // Default number
	}
	
	/**
	 * This function will set the Row for this position.
	 * Preconditions:  NA
	 * Postconditions: The Row will be set
	 */
	public void setRow( int aRow ) {
		_rowVal = aRow;
	}

	/**
	 * This function will set the Column for this position.
	 * Preconditions:  NA
	 * Postconditions: The Column will be set
	 */
	public void setCol( int aCol ) {
		_colVal = aCol;
	}

	/**
	 * This function will set the Place for this position.
	 * Preconditions:  NA
	 * Postconditions: The Place will be set
	 */
	public void setPlace( int place ) {
		_startPlace = place;
	}
	
	/**
	 * This function will allow the Row, Column, and Place to be set manually.
	 * Preconditions:  NA
	 * Postconditions: The data passed in will be the data for this node.
	 * @param aRow Zero based index representing the row of this position
	 * @param aCol Zero based index representing the column of this position
	 * @param aPlace integer representing whether this is a Starting position or a
	 * 		regular position.  Starting positions start at 1; regular positions start
	 * 		at 5000.
	 */
	public void setPosition( int aRow, int aCol, int aPlace ) {
		init( aRow, aCol, aPlace );
	}
	
	/**
	 * This function will allow the Row, and Column to be set manually.
	 * Preconditions:  NA
	 * Postconditions: The data passed in will be the data for this node.
	 * @param aRow Zero based index representing the row of this position
	 * @param aCol Zero based index representing the column of this position
	 */
	public void setPosition( int aRow, int aCol ) {
		init( aRow, aCol );
	}
	
	/**
	 * This function will return the Row that is represented in this Position
	 * Preconditions:  NA
	 * Postconditions: The Row data for this object will be returned
	 */
	public int getRow() {
		return _rowVal;
	}
	
	/**
	 * This function will return the Column that is represented in this Position
	 * Preconditions:  NA
	 * Postconditions: The Column data for this object will be returned
	 */
	public int getCol() {
		return _colVal;
	}
	
	/**
	 * This function will return the place represented by this Position.  A place
	 * greater than 5000 represents a normal position.  Starting Positions start at 1.
	 * Preconditions:  NA
	 * Postconditions: The Place data for this Position will be returned
	 */
	public int getPlace() {
		return _startPlace;
	}
		
	/**
	 * This function will compare Positions first by their positions on the board
	 * then by their row, and finally by their column.
	 * This will satisfy the Comparable Interface.
	 * @param otherObject The object we are being compared to
	 */
	public final int compareTo(Object otherObject) {
		Position other = (Position) otherObject;
		
		if ( _startPlace != other._startPlace ) {            // Compare positions
			return (_startPlace - other._startPlace);
		}
		
		if ( _rowVal != other._rowVal ) {                    // Compare row values
			return ( _rowVal - other._rowVal );
		}
		
		return (_colVal - other._colVal);                    // Compare column values
	}
	
	/**
	 * This function overrides the equals().
	 * Preconditions:  Both objects must be the same class.
	 * Postconditions: A boolean reflecting the equalitiy of the two objects will be returned.
	 * 				Objects are ONLY compared on their place, not their positions on the board.
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
		
		// now we know otherObject is a non-null Position
		Position other = (Position)otherObject;
		
		// test whether the fields have identical values.
		return ( ( _rowVal == other._rowVal) 
				&& ( _colVal == other._colVal ) 
				&& ( _startPlace == other._startPlace ) );
		
	}
	
	/**
	 * This function overrides the equals().
	 * Preconditions:  Both objects must be the same class.
	 * Postconditions: A boolean reflecting the equalitiy of the two objects will be returned.
	 * 				Objects are ONLY compared on their place, not their positions on the board.
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
		
		// now we know otherObject is a non-null Position
		Position other = (Position)otherObject;
		
		// test whether the fields have identical values, except for place.
		return ( ( _rowVal == other._rowVal) 
				&& ( _colVal == other._colVal ) );
		
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
		
		result = 37 * result + _rowVal;
		result = 37 * result + _colVal;
		result = 37 * result + _startPlace;
		
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
		" [ Row = " + _rowVal +
		", Column = " + _colVal +
		", StartPlace = " + _startPlace +
		" ]";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	int _rowVal ;
	int _colVal ;
	int _startPlace ;                // Non-action positions have a place of 0, meaning it is a normal position.
	
}
