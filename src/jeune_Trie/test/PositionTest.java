/**
 * 
 */
package jeune_Trie.test;


import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.TreeSet;

import jeune_Trie.Position;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vladimirjeune
 *
 */
public class PositionTest {

	Position defaultPos = null;
	Position pos1 = null;
	Position pos2 = null;
	Position pos3 = null;

	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		defaultPos = new Position();
		pos1 = new Position( 0, 0, 1 );
		pos2 = new Position( 1, 2, 3 );
		pos3 = new Position(22, 12 );
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * test method for the default constructor
	 */
	@Test
	public void testDefaultPosition() {
		
		assertEquals(" This should match default values: 0", 0, defaultPos.getRow() );
		assertEquals(" This should match default values: 0", 0, defaultPos.getCol() );
		assertEquals(" This should match default values: 5000", 5000, defaultPos.getPlace() );
		
	}
	
	/**
	 * test method for the default constructor
	 */
	@Test
	public void testPosition() {
		
		// Dealing with the normal constructor
		assertEquals(" This should match values: 1", 1, pos2.getRow() );
		assertEquals(" This should match values: 2", 2, pos2.getCol() );
		assertEquals(" This should match values: 3", 3, pos2.getPlace() );
		
		// Testing the position constructor that only deals with row and column
		assertEquals(" This should match values: 22", 22, pos3.getRow() );
		assertEquals(" This should match values: 12", 12, pos3.getCol() );
		assertEquals(" This should match values: 5000", 5000, pos3.getPlace() );
		
	}
	
	/**
	 * test method for compareTo
	 */
	@Test
	public void testCompareTo() {
		TreeSet<Position> pSet = new TreeSet<Position>();
		
		pSet.add(defaultPos);
		pSet.add(pos2);
		pSet.add(pos1);
		
		Iterator<Position> pIter = pSet.iterator();

		assertEquals( pos1 + " should be the 1st returned with a place of 1: ", pos1, pIter.next());
		assertEquals( pos2 + " should be the 2nd returned with a place of 3: ", pos2, pIter.next());
		assertEquals( defaultPos + " should be the last returned with a place of 5000: ", defaultPos, pIter.next());
		
	}
	
	/**
	 * test method for Setters/Getters
	 */
	public void testSetGet() {
		defaultPos.setPosition(17, 3, 5);
		
		assertEquals( "This row should be 17: ", 17, defaultPos.getRow() );
		assertEquals("This column should be 3: ", 3, defaultPos.getCol());
		assertEquals("This place should be 5: ", 5, defaultPos.getPlace());
		
		defaultPos.setRow( 12 );
		defaultPos.setCol( 5 );
		defaultPos.setPlace( 15 );
		
		assertEquals( "This row should be 12: ", 12, defaultPos.getRow() );
		assertEquals("This column should be 5: ", 5, defaultPos.getCol());
		assertEquals("This place should be 15: ", 15, defaultPos.getPlace());
		
	}
	
	/**
	 * test the overridden equals function for this object
	 */
	@Test
	public void testEquals() {
		Position pos1 = new Position( 3, 2, 1);
		Position pos2 = new Position( 3, 2, 1 );
		Position pos3 = new Position( 3, 2 );
		
		// Test for the same object
		assertTrue("These were supposed to be equal", pos1.equals(pos1));
		
		// Test that NULLS return false
		assertFalse("This should have returned false, since nothing is equal to null", pos1.equals(null));
		
		// Test that different classes are false
		assertFalse("Different classes cannot be considered the same", pos1.equals(new String()));
		
		// These are considered equal
		assertTrue("These should be considered exactly the same", pos1.equals(pos2));
		
		// These are different 
		assertFalse("These should be considered different", pos1.equals(pos3));
		
	}
	
	/**
	 * test the overridden match function for this object
	 */
	@Test
	public void testMatch() {
		Position pos1 = new Position( 3, 2, 1);
		Position pos2 = new Position( 3, 2, 1 );
		Position pos3 = new Position( 3, 2 );
		
		// Test for the same object
		assertTrue("These were supposed to be equal", pos1.match(pos1));
		
		// Test that NULLS return false
		assertFalse("This should have returned false, since nothing is equal to null", pos1.match(null));
		
		// Test that different classes are false
		assertFalse("Different classes cannot be considered the same", pos1.match(new String()));
		
		// These are considered equal
		assertTrue("These should be considered exactly the same", pos1.match(pos2));
		
		// These are different 
		assertTrue("These should be considered the same, since we are comparing only board placement", pos1.match(pos3));
		
	}
	
	/**
	 * tests the overriddent hash() for this object
	 */
	@Test
	public void testHashCode() {
		// Equals Objects should have equal hashCodes
		assertEquals("These should return the same hashCode: ", pos1.hashCode(), pos1.hashCode() );
		
		// Unequal Objects should have unequal hashCodes.
		assertFalse("These should have different hashCodes: ", (pos1.hashCode() == pos2.hashCode()));
	}
}
