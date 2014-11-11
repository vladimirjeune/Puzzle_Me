/**
 * 
 */
package jeune_Trie.test;

import static org.junit.Assert.*;
import jeune_Trie.MoveType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vladimirjeune
 *
 */
public class MoveTypeTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This is a test that makes sure that no new elements were added to this Enum.
	 * If there were the test will fail
	 */
	public boolean noNewEnums() {
		boolean retVal = true;
		int itr = 0;
		
		while( itr < MoveType.values().length ) {

			switch( MoveType.values()[ itr ] ) {
			
		case UP:
		case DOWN:
		case LEFT:
		case RIGHT:
			break;                                // The Original Enums default to true;
		default:
			retVal = false;	                      // Another Enum has been added
			break;
						
		}
			
			
			itr++;
		}
		
		return retVal;
	}
	
	/**
	 * Test method to make sure that the original Enums are being used.
	 */
	@Test
	public void testOriginalEnums() {
		
		assertTrue("There have been new Enums added : ", noNewEnums());
		
	}
	
	/**
	 * Test method for {@link jeune_Trie.MoveType#isMoveHorizontal(jeune_Trie.MoveType)}.
	 */
	@Test
	public void testIsMoveHorizontal() {
		MoveType mtLeft = MoveType.LEFT;
		MoveType mtRight= MoveType.RIGHT;
		MoveType mtUp= MoveType.UP;
		MoveType mtDown= MoveType.DOWN;
		
		assertTrue("This Enum is supposed to say it is Horizontal : ", MoveType.isMoveHorizontal(mtLeft));
		assertTrue("This Enum is supposed to say it is Horizontal : ", MoveType.isMoveHorizontal(mtRight));

		assertFalse("This Enum is supposed to be Vertical : ", MoveType.isMoveHorizontal(mtUp));
		assertFalse("This Enum is supposed to be Vertical : ", MoveType.isMoveHorizontal(mtDown));
				
	}
	
	/**
	 * Test method for {@link jeune_Trie.MoveType#isMoveForward(jeune_Trie.MoveType)}.
	 */
	@Test
	public void testIsMoveForward() {
		MoveType mtRight= MoveType.RIGHT;
		MoveType mtUp= MoveType.UP;
		MoveType mtLeft = MoveType.LEFT;
		MoveType mtDown= MoveType.DOWN;
		
		assertTrue("This Enum is supposed to say it is Forward : ", MoveType.isMoveForward(mtRight));
		assertTrue("This Enum is supposed to say it is Forward : ", MoveType.isMoveForward(mtDown));

		assertFalse("This Enum is supposed to be BackWard: ", MoveType.isMoveForward(mtUp));
		assertFalse("This Enum is supposed to be Backward : ", MoveType.isMoveForward(mtLeft));
				
	}

}
