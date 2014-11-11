/**
 * 
 */
package jeune_Trie.test;


import static org.junit.Assert.assertTrue;
import jeune_Trie.UseType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vladimirjeune
 *
 */
public class UseTypeTest {

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

		while( itr < UseType.values().length ) {

			switch( UseType.values()[ itr ] ) {

			case CREATE:
			case PLAY:
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

}
