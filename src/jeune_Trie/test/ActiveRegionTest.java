/**
 * 
 */
package jeune_Trie.test;

import static org.junit.Assert.*;
import jeune_Trie.ActiveRegion;
import jeune_Trie.Position;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vladimirjeune
 *
 */
public class ActiveRegionTest {

	Position aStart1 ;
	ActiveRegion active1 ; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		aStart1 = new Position(1,1,3);
		active1 = new ActiveRegion(aStart1, 2, 2);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link jeune_Trie.ActiveRegion#ActiveRegion()}.
	 */
	@Test
	public void testActiveRegion() {
		ActiveRegion aRegion = new ActiveRegion();
		Position aPosition = new Position();
		
		assertEquals("This should contain a position equal to a default position", aPosition, aRegion.getStartPos());
		assertEquals("The length of a default Region should be 0: ", 0, aRegion.getLength());
		assertEquals("The orientation for a default Region should come out Horizontal: ", 0, ( (aRegion.getOrientation() % 2 == 0) ? 0:1));
	}

	/**
	 * Test method for {@link jeune_Trie.ActiveRegion#ActiveRegion(jeune_Trie.Position, int, int)}.
	 */
	@Test
	public void testActiveRegionPositionIntInt() {
		assertEquals("This region should have this Position: ", aStart1, active1.getStartPos());
		assertEquals("This region should have a length of 2: ", 2, active1.getLength() );
		assertEquals("This region should heve an orientation of Horizontal-0: ", 0, ( (active1.getOrientation() % 2 == 0) ? 0:1));
	}
	
	/**
	 * Tests setters and getters for ActiveRegion
	 * 
	 */
	@Test
	public void testSetsGets() {
		ActiveRegion aRegion = new ActiveRegion();
		Position aPos = new Position(5,5, 10);
		
		aRegion.setStartPos(aPos);
		aRegion.setLength(10);
		aRegion.setOrientation(5);                 // Vertical
		
		// Testing that the position was correctly set
		assertEquals("This should be equal: ", new Position(5,5,10), aRegion.getStartPos());
		
		// Testing that the length was correctly set
		assertEquals("This should have a length of 10: ", 10, aRegion.getLength());
				
		// Testing that the orientation was correctly set.
		assertEquals("This should equal 1, because the region should be vertical: ", 1, ((aRegion.getOrientation() % 2 == 0) ? 0:1));
		
	}
	
	/**
	 * Test method for {@link jeune_Trie.ActiveRegion#compareTo(java.lang.Object)}.
	 * Two ActiveRegions are the same if they have the same Position, and orientation
	 * If all other things are the same Horizontal is < Vertical.
	 */
	@Test
	public void testCompareTo() {
		ActiveRegion ar1 = new ActiveRegion(new Position(2,2,3), 5, 2);
		ActiveRegion ar2 = new ActiveRegion(new Position(2,2,3), 5, 5);
		ActiveRegion ar3 = new ActiveRegion(new Position(2,2,8), 5, 10);
		
		// ar1 is less than ar2 because it is horizontal
		assertTrue("The answer should be negative: ", (0 > ar1.compareTo(ar2)));
		
		// ar2 is greater than ar1 because it is vertical
		assertTrue("The answer should be positive: ", 0 < ar2.compareTo(ar1));
		
		// These should come out to be equal
		assertTrue("The answer should be 0: ", ( 0 == ar2.compareTo(ar2)));
		
		// Answer is greater because ar3 has a higher position
		assertTrue("The answer should be greater becuase ar3 has a Position with a higher place: ", ( 0 < ar3.compareTo(ar1)));
		
		// Answer is lesser because ar3 has a higher Position
		assertTrue("The answer should be lesser becuase ar3 has a Position with a higher place: ", ( 0 > ar1.compareTo(ar3)));
		
	}

	/**
	 * Test method for {@link jeune_Trie.ActiveRegion#toString()}.
	 * This method test the String representation of the ActiveRegion object.
	 */
	@Test
	public void testToString() {
		assertEquals("This should be the output: ", "jeune_Trie.ActiveRegion [ Start Position = jeune_Trie.Position [ Row = 1, Column = 1, StartPlace = 3 ], Length = 2, Orientation = HORIZONTAL ]", active1.toString());
	}
	
	/**
	 * Test method for {@link jeune_Trie.ActiveRegion#equals(java.lang.Object)}.
	 * This test the overriden equals method.
	 */
	@Test
	public void testEquals() {
		
		// See if the objects are identical
		assertTrue("This returns true becuase it is the same object: ", active1.equals(active1));
		
		// must return false if the explicit parameter is null
		assertFalse("This should return false because parameter is null: ", active1.equals(null));

		// if the classes don't match, they can't be equal
		assertFalse("This should return false because objects are not of the same class: ", active1.equals(aStart1));
		
		// test whether the fields have identical values.
		ActiveRegion test1 = new ActiveRegion(aStart1, 2, 2);
		assertTrue("Should return True, because has the same values for everything; ", test1.equals(active1));
		
	}
	
	/**
	 * tests the hash() for this object
	 */
	@Test
	public void testHashCode() {
		ActiveRegion test1 = new ActiveRegion( new Position(), 17, 37);
		
		// Equals Objects should have equal hashCodes
		assertEquals("These should return the same hashCode: ", active1.hashCode(), active1.hashCode() );
		
		// Unequal Objects should have unequal hashCodes.
		assertFalse("These should have different hashCodes: ", (active1.hashCode() == test1.hashCode()));
	}
	
}
