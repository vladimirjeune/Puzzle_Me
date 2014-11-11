/**
 * 
 */
package jeune_Trie.test;

import static org.junit.Assert.*;
import jeune_Trie.CharFormatException;
import jeune_Trie.TrieNode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vladimirjeune
 *
 */
public class TrieNodeTest {

	TrieNode defaultNode = null;
	TrieNode paramNode = null;
	TrieNode extraNode = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		defaultNode = new TrieNode();
		paramNode = new TrieNode( "A" );
		extraNode = new TrieNode( "B" );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link jeune_Trie.TrieNode#TrieNode()}.
	 */
	@Test
	public void testTrieNode() {
		
		assertEquals("This should be the empty string: ", "", defaultNode.getLetter());
		assertEquals("This should not be the root, false: ", false, defaultNode.isRoot());
		assertEquals("This should not be a word, false: ", false , defaultNode.isWord());
		assertEquals("There should be nothing in here, 0: ", 0, defaultNode.sizeNextLetters());
	}

	/**
	 * Test method for {@link jeune_Trie.TrieNode#TrieNode(java.lang.String)}.
	 */
	@Test
	public void testTrieNodeString() {
		
		assertEquals("This should be \"A\": ", "A", paramNode.getLetter());
		assertEquals("This should not be the root, false: ", false, paramNode.isRoot());
		assertEquals("This should not be a word, false: ", false , paramNode.isWord());
		assertEquals("There should be nothing here, 0 ", 0, paramNode.sizeNextLetters());
	}
	
	/**
	 * Test method for setLetter()
	 */
	@Test
	public void testSetLetter() {
		String ans1 = "B";
		
		paramNode.setLetter("B");
		assertEquals("This should say \"B\"", ans1, paramNode.getLetter());
	}
	
	/**
	 * Tests the getLetter function
	 */
	@Test
	public void testGetLetter() {
		String ans1 = "A";
		assertEquals("This should say \"B\"", ans1, paramNode.getLetter());
	}
	
	/**
	 * Test the isRoot function, and the makeRoot function
	 */
	@Test
	public void testIsRoot() {
		defaultNode.makeRoot();
		assertEquals("This is not the root, false: ", false, paramNode.isRoot());
		assertEquals("This is the root, true; ", true, defaultNode.isRoot());
	}

	/**
	 * This tests the root modification functions
	 */
	@Test
	public void testRootLabelModification() {
		defaultNode.makeRoot();
		assertEquals("This is the root, true: ", true, defaultNode.makeRoot());
		assertEquals("This is the root, true: ", true, defaultNode.isRoot());
		assertEquals("This should always return true: ", true, defaultNode.unMakeRoot());
		assertEquals("This should no longer be the root, true: ", false, defaultNode.isRoot());
		
		assertEquals("This should not be allowed to happen, represents a letter, false;", false, paramNode.makeRoot());
		assertEquals("This cannot be the root, false: ", false, paramNode.isRoot());
		assertEquals("This should always return true;", true, paramNode.unMakeRoot());
	}
	
	/**
	 * Tests the word modification functions of this object, that tag it as the end letter
	 * of a word or not.
	 */
	@Test
	public void testWordLabelModification() {
		assertEquals("You cannot label an node as a word if it does not represent a letter, false: ", false, defaultNode.makeWord());
		assertEquals("This function should alway return true: ", true, defaultNode.unMakeWord());
		assertEquals("This should not be a word, false:" , false, defaultNode.isWord());
		
		assertEquals("This represents a letter so it should be true: ", true, paramNode.makeWord());
		assertEquals("This should now be a word, true: ", true, paramNode.isWord());
		assertEquals("This should always return true: ", true, paramNode.unMakeWord());
		assertEquals("This should now be false: ", false, paramNode.isWord());
		
	}
	
	/**
	 * This function should handle adding nextLetters to an object
	 */
	@Test
	public void testAddToNextLettersAndSize() {
		String ans1 = "A";
		String ans2 = "A M";
		String ans3 = "A M V";
		
		// Nothing has been added
		assertEquals("This has not been added to so it should be 0: ", 0, defaultNode.sizeNextLetters());
		
		// A has been added.
		assertEquals("This letter should be able to be added, true: ", true, defaultNode.addToNextLetters("A"));
		assertEquals("This has been added to so it should be 1: ", 1, defaultNode.sizeNextLetters());
		assertEquals("This should say \"" + ans1 + "\"", ans1, defaultNode.allNextLetters());
		
		// M has been added
		assertEquals("This letter should be able to be added, true: ", true, defaultNode.addToNextLetters("M"));
		assertEquals("This has been added to so it should be 2: ", 2, defaultNode.sizeNextLetters());
		assertEquals("This should say \"" + ans2 + "\"", ans2, defaultNode.allNextLetters());
		
		// M should not be added again, the count should not change.
		assertEquals("This letter should be able to be added, false: ", false, defaultNode.addToNextLetters("M"));
		assertEquals("This has been added to so it should be 2: ", 2, defaultNode.sizeNextLetters());
		assertEquals("This should say \"" + ans2 + "\"", ans2, defaultNode.allNextLetters());
		
		// V is being added size should go up to 3
		assertEquals("This letter should be able to be added, true: ", true, defaultNode.addToNextLetters("V"));
		assertEquals("This has been added to so it should be 3: ", 3, defaultNode.sizeNextLetters());
		assertEquals("This should say \"" + ans3 + "\"", ans3, defaultNode.allNextLetters());
		
	}
	
	/**
	 * This will test the remove and size functions.  A letter should be removed only if it
	 * does not have any children.  Letters that are the ends of words can be removed using 
	 * this.  The word A - T - E is formed in this test.  R and E can be removed.  But, 
	 * A should not be able to be removed since it is a word, and marked as such.  
	 * This function should be made public for testing purposes.
	 */
	@Test 
	public void testRemoveFromNextLetterAndSize() {
		TrieNode downA = new TrieNode();
		TrieNode downT = new TrieNode();
		TrieNode downE = new TrieNode();
		
		
		defaultNode.addToNextLetters("A");
		defaultNode.addToNextLetters("r");
		downA = defaultNode.getNextLetter( "A" );
			if ( ! downA.equals("")) {     // GetNextLetter returns "" if no such nextLetter.
				downA.addToNextLetters("T");   // This sets the letter for that node as well.
				downA.makeWord();
				
				
				downT = downA.getNextLetter("T");
				
				if ( ! downT.equals("")) {
					downT.addToNextLetters("e");
					downT.makeWord();
					
					downE = downT.getNextLetter("E");
					downE.makeWord();
				}
				
			}
		
		assertEquals("This will remove R because it has no children, leaving A, True: ", true, defaultNode.removeFromNextLetters("r"));
		assertEquals("This should be A: ", "A", defaultNode.allNextLetters());
		
		assertEquals("This will retun false because A has children, false: ", false, defaultNode.removeFromNextLetters("A"));
		assertEquals("This should be A: ", "A", defaultNode.allNextLetters());
		
		assertEquals("This node should have 1 child, 1", 1, downT.sizeNextLetters());
		assertTrue("This node will be removed because it has no children", downT.removeFromNextLetters("E"));
		assertEquals("This node should have no children since the previous line removed it, 0", 0, downT.sizeNextLetters());
		
	}
	
	/**
	 * This test hasNextLetter.  It should return true if the next letter asked for can be found.  And 
	 * false otherwise.
	 */
	@Test
	public void testHasNextLetter() {
		assertFalse("This should not have any children at all: ", defaultNode.hasNextLetter("A"));
		defaultNode.addToNextLetters("B");
		assertFalse("This should not have any children at all: ", defaultNode.hasNextLetter("A"));
		assertTrue("This should have 1 child, and it is B: ", defaultNode.hasNextLetter("B"));
	}
	
	/**
	 * This tests equals if the object represent the same letter, even if they do not have the same state.
	 */
	@Test 
	public void testEquals() {
		assertEquals("This is the same object so it should return true: ", true, defaultNode.equals(defaultNode));
		assertEquals("This should return false: ", false, defaultNode.equals(null));
		assertEquals("The parameter is of the wrong class, false: ", false, defaultNode.equals( new Object() ));
		assertEquals("These do not represent the same letters, false: ", false, defaultNode.equals(paramNode));
		defaultNode.setLetter("A");
		assertEquals("These represent the same letter, true: ", true, defaultNode.equals(paramNode));
	}
	
	/**
	 * This tests compareTo for use with the Comparable interface.
	 */
	@Test
	public void testCompareTo() {
		defaultNode.setLetter("A");
		
		assertEquals("These are equal, so there is no difference, 0 : ", 0, defaultNode.compareTo(paramNode));
		assertEquals("\"A\" is less than \"B\"", -1, defaultNode.compareTo(extraNode));
		assertEquals("\"B\" is greaterthan \"A\"", 1, extraNode.compareTo(defaultNode));
		
	}
	
	/**
	 * This tests the correct format function.
	 */
	@Test 
	public void testCorrectFormat() {
		String good1 = "A";
		String good2 = "c";
		String good3 = "9";
		
		String bad1 = "!";
		String bad2 = "ABBA";
		
		TrieNode tNode = new TrieNode();
		try {
			assertEquals("This should return \"" + good1 + "\": ", good1, tNode.correctFormat(good1));
			assertEquals("This should return \"" + good2.toUpperCase() + "\": ", good2.toUpperCase(), tNode.correctFormat(good2));
			assertEquals("This should return \"" + good3 + "\": ", good3, tNode.correctFormat(good3));
			
		} catch (CharFormatException e) {
			fail("None of these assertions should have tripped the Exception.");
		}
		
		try {
			tNode.correctFormat(bad1);
			fail("This was supposed to throw an exception for input" + bad1 );
		} catch (CharFormatException e) {
			// PASSED
		}
		
		try {
			tNode.correctFormat(bad2);
			fail("This was supposed to throw an exception for input" + bad2 );
		} catch (CharFormatException e) {
			// PASSED
		}

		
	}
	
	/**
	 * Testing toString method overridden from object
	 */
	@Test
	public void testToString() {
		String ans1 = "A B C T";
		String wholeString = "jeune_Trie.TrieNode [letter = , isRoot = false, isWord = false, nextLetters = {A B C T}]";
		defaultNode.addToNextLetters("A");
		defaultNode.addToNextLetters("T");
		defaultNode.addToNextLetters("B");
		defaultNode.addToNextLetters("C");

		assertEquals("These should be equal: ", wholeString, defaultNode.toString());
		
	}
	
	/**
	 * tests the hash() for this object
	 */
	@Test
	public void testHashCode() {
		// Equals Objects should have equal hashCodes
		assertEquals("These should return the same hashCode: ", defaultNode.hashCode(), defaultNode.hashCode() );
		
		// Unequal Objects should have unequal hashCodes.
		assertFalse("These should have different hashCodes: ", (defaultNode.hashCode() == paramNode.hashCode()));
	}
	
}
