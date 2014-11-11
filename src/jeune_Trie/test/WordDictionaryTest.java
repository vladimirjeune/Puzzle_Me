/**
 * 
 */
package jeune_Trie.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import jeune_Trie.WordDictionary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vladimirjeune
 */
public class WordDictionaryTest {

	WordDictionary wTree ;
	String testTree;
	String testStr ;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		wTree = new WordDictionary();

		testTree = "                                        No*\n"+
		"                                   Oi \n"+
		"                              It \n"+
		"                         Ti \n"+
		"                              Gn*\n"+
		"                         Ni \n"+
		"                    Id \n"+
		"                                        Ll*\n"+
		"                                   La \n"+
		"                              Ar \n"+
		"                         Re \n"+
		"                              Dn*\n"+
		"                         Ne \n"+
		"                    Ed \n"+
		"               Dd \n"+
		"          Da \n"+
		"     A \n"+
		"";
		
		wTree.wordLogger.setLevel(Level.ALL);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * This will help to test the loadFile() test.
	 * @param aFile
	 * @return
	 */
	public ArrayList<String> listInput( File aFile ) {
		String title = "";
		ArrayList<String> returnThis = new ArrayList<String>();
		
		try {
			FileReader fReader = new FileReader (aFile);
			BufferedReader bin = new BufferedReader(fReader);

			while ( title != null ) {

				title = bin.readLine();
				
				if ( title != null ) {
					returnThis.add(title);
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException f) {
			f.printStackTrace();
		}

		return returnThis;
	}

	/**
	 * Test method for {@link jeune_Trie.WordDictionary#WordTrie()}.
	 */
	@Test
	public void testWordDictionary() {
		assertEquals("The size of the this object should be 0: ", 0, wTree.numWords() );
		assertNotNull("The root should be initialized with a blank TrieNode labeled as root, not NULL", wTree.testRoot() );
		assertEquals("This should be labeled as a root of the tree, true: ", true, wTree.testRoot().isRoot() );
		assertEquals("This should not be labeled as a word: ", false, wTree.testRoot().isWord() );
		assertEquals("This should not be a Word, \"\" ", "", wTree.testRoot().getLetter() );
	}

	/**
	 * This method tests adding to the tree, and any constraints for this function.
	 */
	@Test 
	public void testAdding() {
		String word1 = "A";
		String word2 = "AT";
		String word3 = "ATE";
		String word4 = "ATM";
		String word5 = "ATAVIstic";  // Should be capitalized.
		String word6 = "Fund";
		String word7 = "FuN";
		String badWord1 = "It's";    // Should throw an Exception
		String badWord2 = "ITS";
		
		// Test adding and counting of words in tree
		assertEquals("This should not have any words in it, 0: ", 0, wTree.numWords());
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, wTree.addWord(word1));
		assertEquals("This should hava 1 word in it, 1: ", 1, wTree.numWords());
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, wTree.addWord(word2));
		assertFalse("This is a duplicate which is not allowed", wTree.addWord(word2));
		assertEquals("This should have 2 words in it, 2: ", 2, wTree.numWords());
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, wTree.addWord(word3));		
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, wTree.addWord(word4));		
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, wTree.addWord(word5));		
		assertEquals("This should have 5 words in it, 5: ", 5, wTree.numWords());
		
		// Add non duplicates
		assertEquals( word6 + " is not a duplicate, true: ", true, wTree.addWord(word6));
		assertEquals( word7 + " is not a duplicate, but is part of another word already in the tree, true: ", true, wTree.addWord(word7));
		
		wTree.addWord(badWord1);  // Check that this word has the puncuation removed.
		assertTrue("The word \""+ badWord2 + "\" should be in the tree: ", wTree.findWord(badWord2));
		assertEquals("This should have 8 words in it, 8: ", 8, wTree.numWords());
		
	}
	
	/**
	 * This test the find()
	 */
	@Test 
	public void testFind() {
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";
		
		String noFind1 = "add";
		String noFind2 = "additive";
		
		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);
		
		// Find on items in tree
		assertTrue("This word should exists in the tree", wTree.findWord(find1));
		assertTrue("This word should exists in the tree", wTree.findWord(find2));
		assertTrue("This word should exists in the tree", wTree.findWord(find3));
		assertTrue("This word should exists in the tree", wTree.findWord(find4));
		
		// Find on items not in tree
		assertFalse("This word is in the list but is not labeled as complete: ", wTree.findWord(noFind1));
		assertFalse("This word is not int the list at all: ", wTree.findWord(noFind2));
		
	}
	
	/**
	 * This test Delete
	 */
	@Test 
	public void testDelete() {
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";       // delete something 2x, delete there/not 2x, delete/add/delete
		
		String noFind1 = "add";
		String noFind2 = "additive";
		String noFind3 = "addendum";
		
		int one = 1;
		int two = 2;
		int three = 3;
		int four = 4;
		int five = 5;
		int six = 6;
		int seven = 7;
		
		// Add 4 words
		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);

		// Repeated removal, add cycles.
		assertEquals("This should be size "+ four +": ", four, wTree.numWords() );
		assertTrue(find4 + " should be in the tree", wTree.findWord(find4));
		assertTrue("This should make " + find4 + " not a word in the tree: ", wTree.deleteWord(find4));
		assertEquals("This should be size "+ three +": ", three, wTree.numWords() );
		assertFalse(find4+" should not be found in the tree: ", wTree.findWord(find4));
		wTree.addWord(find4);
		assertEquals("This should be size "+ four +": ", four, wTree.numWords() );
		assertTrue(find4 + " should be in the tree", wTree.findWord(find4));
		assertTrue("This should make " + find4 + " not a word in the tree: ", wTree.deleteWord(find4));
		assertEquals("This should be size "+ three +": ", three, wTree.numWords() );
		assertFalse(find4+" should not be found in the tree: ", wTree.findWord(find4));
		
		// Delete words not in the tree
		assertFalse(noFind1 + " is in the tree, but is only part of a word", wTree.deleteWord(noFind1));
		assertFalse(noFind2 + " is not in the tree", wTree.deleteWord(noFind2));
		assertFalse(noFind2 + " is not in the tree", wTree.deleteWord(noFind2));               // Repeat
		assertEquals("This should be size "+ three +": ", three, wTree.numWords() );
		
		// 2x deletions
		assertFalse(find4 + " should have already been deleted: ", wTree.deleteWord(find4));   // Second deletion
		assertTrue(find3 + " should be deleted: ", wTree.deleteWord(find3));
		assertFalse(find3 + " has been previously deleted, cannot delete twice: ", wTree.deleteWord(find3));
		
		// Add all words
		wTree.addWord(find3);
		wTree.addWord(find4);
		wTree.addWord(noFind1);
		wTree.addWord(noFind2);
		wTree.addWord(noFind3);
		assertEquals("There should be " + seven + " words: ", seven, wTree.numWords() );
		
		// Remove subWords then look to make sure removed.
		assertTrue(noFind1 + " should be allowed to be removed: ", wTree.deleteWord(noFind1));
		assertTrue(noFind3 + " should be allowed to be removed: ", wTree.deleteWord(noFind3));
		
		assertFalse(noFind1 + " has already been removed: ", wTree.deleteWord(noFind1));
		assertFalse(noFind3 + " has already beeb removed: ", wTree.deleteWord(noFind3));
		
		assertFalse(noFind1 + " should not be in the tree: ", wTree.findWord(noFind1));
		assertFalse(noFind3 + " should not be in the tree: ", wTree.findWord(noFind3));
		
		assertEquals("There should be " + five + " words: ", five, wTree.numWords() );
		
		wTree.addWord(noFind1);
		wTree.addWord(noFind3);
		
		assertEquals("There should be " + seven + " words: ", seven, wTree.numWords() );
		assertTrue(noFind1 + " should be in the tree: ", wTree.findWord(noFind1));
		assertTrue(noFind3 + " should be in the tree: ", wTree.findWord(noFind3));
		
	}
	
	/**
	 * This test numWords
	 */
	@Test
	public void testNumWords() {
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";  
		
		// No words yet
		assertEquals("There should be no words in the tree at this point: ", 0, wTree.numWords());

		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);
		
		// Some words
		assertEquals("There should be four words: ", 4, wTree.numWords() );
				
		// Some removed
		wTree.deleteWord(find1);
		assertEquals("There should be three words: ", 3, wTree.numWords());
	}
	
	/**
	 * Test method for {@link jeune_Trie.WordDictionary#printTree()}.
	 */
	@Test
	public void testPrintTree() {
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";  
		
		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);
		
		assertEquals("This should match this string", testTree, wTree.printTree() );
	}
	
	/**
	 * This tests printTreeData, really just runs.
	 */
//	@Test
//	public void testPrintTreeData() {
//		String find = "adder";
//		String find1 = "addition";
//		String find2 = "adderall";
//		String find3 = "adding";
//		String find4 = "addend";  
//		String find5 = "add";
//		String find6 = "ad";
//		
	
//		wTree.addWord(find);
//		wTree.addWord(find1);
//		wTree.addWord(find2);
//		wTree.addWord(find3);
//		wTree.addWord(find4);
//		wTree.addWord(find5);
//		wTree.addWord(find6);
//		
//		ArrayList<String> sArr = new ArrayList<String>();
//		sArr.add( find4 );
//		sArr.add( find2 );
//		sArr.add( find3 );
//		sArr.add( find1 );
//		
//		// This function prints to standard output
//		
//	}
	
	/**
	 * This tests the outputTreeData(), uses the same algorithm as printTreeData
	 */
	@Test
	public void testOutputTree() {
		String find = "adder";
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";  
		String find5 = "add";
		String find6 = "ad";
		
		wTree.addWord(find);
		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);
		wTree.addWord(find5);
		wTree.addWord(find6);
		
		ArrayList<String> sArr = new ArrayList<String>();
		sArr.add( find6 );
		sArr.add( find5 );
		sArr.add( find4 );
		sArr.add( find );
		sArr.add( find2 );
		sArr.add( find3 );
		sArr.add( find1 );
		
		ArrayList<String> testArr = wTree.outputTree();
		
		if ( sArr.size() != testArr.size() ) {
			fail("The sizes are not the same");
		}
		
		for ( int index = 0; index < testArr.size(); index++) {
			assertTrue("These are supposed to be the same", sArr.get(index).equalsIgnoreCase(testArr.get(index)));
		}
				
	}

	/**
	 * This will tests the loadFile() to load files from the directory
	 */
	@Test 
	public void testLoadFile() {
		assertTrue("This should be true if the file was loaded correctly", wTree.loadTree(new File("testFile.lst")));
		
		ArrayList<String> fromHD = listInput(new File("testFile.lst"));
		ArrayList<String> fromTree = wTree.outputTree();
		
		if ( fromHD.size() != fromTree.size() ) {
			fail("The number of words do not match");
		}
		
		for ( int index = 0; index < fromHD.size(); index++ ) {
			 assertEquals("These should say the same thing", fromHD.get(index).toUpperCase().replaceAll(" ", ""), fromTree.get(index));
		}
		
	}
	
	/**
	 * This will test the suggestLetterMatch()
	 */
	@Test
	public void testSuggestLetterMatch() {
		String find = "adder";
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";  
		String find5 = "add";
		String find6 = "ad";
		
		wTree.addWord(find);
		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);
		wTree.addWord(find5);
		wTree.addWord(find6);
		
		// Find words that have 'n' in the 5th position and are of length 6.
		assertEquals("There should be 2 word(s) that match: ", 2, wTree.suggestLetterMatch("N", 5, 6).size() );
		assertEquals("This should say addend: ", find4.toUpperCase(), wTree.suggestLetterMatch("N", 5, 6).get(0) );
		assertEquals("This should say adding: ", find3.toUpperCase(), wTree.suggestLetterMatch("N", 5, 6).get(1) );
		
		// Find words that have 'd' in the 3rd position and are of length 2: False
		assertEquals("There should be 0 word(s) that match: ", 0, wTree.suggestLetterMatch("d", 3, 2).size() );
		
		// Find words that have 'i' in the 4th position and are of length 8
		assertEquals("There should be 1 word(s) that match: ", 1, wTree.suggestLetterMatch("I", 4, 8).size() );
		assertEquals("This should say " + find1 + ": ", find1.toUpperCase(), wTree.suggestLetterMatch("I", 4, 8).get(0) );
		
		// Find words that have 'a' in the 1st position and are of length 2
		assertEquals("There should be 1 word(s) that match: ", 1, wTree.suggestLetterMatch("A", 1, 2).size() );
		assertEquals("This should say addend " + find6 + ": ", find6.toUpperCase(), wTree.suggestLetterMatch("A", 1, 2).get(0) );
		
		// Find words that have 'r' in the 3rd position and are of lenght 3: False
		assertEquals("There should be 0 word(s) that match: ", 0, wTree.suggestLetterMatch("r", 1, 3).size() );
				
	}
	
	/** 
	 * This will tests findWordsOfLength()
	 */
	@Test
	public void testSuggestWordsOfLength() {
		String find = "adder";
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";  
		String find5 = "add";
		String find6 = "ad";
		String find7 = "a";
		String find8 = "I";
		
		wTree.addWord(find);
		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);
		wTree.addWord(find5);
		wTree.addWord(find6);
		
		// Find words of length 1: False
		assertEquals("This should not return any results: ", 0, wTree.suggestWordsOfLength(1).size());

		// Find words of length 7: False
		assertEquals("This should not return any results: ", 0, wTree.suggestWordsOfLength(7).size());
		
		// Find words of length 17: False
		assertEquals("This should not return any results: ", 0, wTree.suggestWordsOfLength(18).size());
		
		// Find words of legnth 3
		assertEquals("This should find the word add: ", find5.toUpperCase(), wTree.suggestWordsOfLength(3).get(0));
		
		// Find words of length 8
		for ( int index = 0; index < 2; index++ ) {
			if ( index == 0 ) {
				assertEquals("This should find the word adderall: ", find2.toUpperCase(), wTree.suggestWordsOfLength(8).get(0));
			} else {
				assertEquals("This should find the word addition: ", find1.toUpperCase(), wTree.suggestWordsOfLength(8).get(1));			
			}
		}
		
		// Testing single letter words
		wTree.addWord(find7);                                       
		wTree.addWord(find8);
		
		ArrayList<String> treeAnsArr = wTree.suggestWordsOfLength(1);
		ArrayList<String> ansArr = new ArrayList<String>();
		
		ansArr.add(find7);
		ansArr.add(find8);
		
		for ( int ctr = 0; ctr < treeAnsArr.size(); ctr++ ) {
			assertEquals("This should find A: ", ansArr.get(ctr).toUpperCase(), treeAnsArr.get(ctr));
		}
			
	}
	
	/**
	 * This tests the suggestWords()
	 */
	@Test
	public void testSuggestPrefixMatch() {
		String find = "adder";
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";  
		String find5 = "add";
		String find6 = "ad";
		
		String word1 = "removal";
		String word2 = "remove";
		String word3 = "removed";
		String word4 = "redone";
		String word5 = "red";
		String word6 = "redo";
		String word7 = "remodel"; 
		
		wTree.addWord(find);
		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);
		wTree.addWord(find5);
		wTree.addWord(find6);

		wTree.addWord(word1);
		wTree.addWord(word2);
		wTree.addWord(word3);
		wTree.addWord(word4);
		wTree.addWord(word5);
		wTree.addWord(word6);
		wTree.addWord(word7);
		
		// Suggestions for prefix: pre, len 8: == 0
		assertEquals("\"pre\" is not a prefix in this tree: ", 0, wTree.suggestPrefixMatch("pre", 8).size());
		
		// Suggestions for prefix: red, len 4: == 1
		assertEquals( word6 + " should match this criterion: ", word6.toUpperCase(), wTree.suggestPrefixMatch("red", 4).get(0));
		
		// Suggestions for prefix: ad, len 3: == 1
		assertEquals( find5 +" should match this criterion: ", find5.toUpperCase(), wTree.suggestPrefixMatch("ad", 3).get(0));
		
		// Suggestions for prefix: add, len 5: == 1
		assertEquals( find + " should match this criterion: ", find.toUpperCase(), wTree.suggestPrefixMatch(find5, 5).get(0));
		
		// Suggestions for prefix: remove, len 7: == 1
		assertEquals( word3 + " should match this criterion: ", word3.toUpperCase(), wTree.suggestPrefixMatch(word2, 7).get(0));
		
		// Suggestions for prefix: removed, len7: == 1
		assertEquals( word3 + " should match this criterion: ", word3.toUpperCase(), wTree.suggestPrefixMatch(word3, 7).get(0));
		
		// Suggestions for prefix: re, length 7: should be removed, removal, remodel
		ArrayList<String> testArr = new ArrayList<String>();
		ArrayList<String> compArr = wTree.suggestPrefixMatch("re", 7);
		testArr.add(word7);
		testArr.add(word1);
		testArr.add(word3);
		
		assertEquals("These should have the same amount of items: ", testArr.size(), compArr.size());
		for ( int index = 0; index < compArr.size(); index++ ) {
			assertEquals("These should match exactly: ", compArr.get(index), testArr.get(index).toUpperCase());
		}
		
	}
	
	/**
	 * Tests suggestMatch(), which finds words in the Dictionary that match partial words of a certain length
	 */
	@Test
	public void testSuggestMatch() {
		String find = "adder";
		String find1 = "addition";
		String find2 = "adderall";
		String find3 = "adding";
		String find4 = "addend";  
		String find5 = "add";
		String find6 = "ad";
		
		String word1 = "removal";
		String word2 = "remove";
		String word3 = "removed";
		String word4 = "redone";
		String word5 = "red";
		String word6 = "redo";
		String word7 = "remodel"; 
		
		wTree.addWord(find);
		wTree.addWord(find1);
		wTree.addWord(find2);
		wTree.addWord(find3);
		wTree.addWord(find4);
		wTree.addWord(find5);
		wTree.addWord(find6);

		wTree.addWord(word1);
		wTree.addWord(word2);
		wTree.addWord(word3);
		wTree.addWord(word4);
		wTree.addWord(word5);
		wTree.addWord(word6);
		wTree.addWord(word7);
		
		ArrayList<String> testArr = new ArrayList<String>();
		
		// Suggestions for partial word: []re, len 7: == 0
		assertEquals("\"_re\" is not a prefix in this tree: ", 0, wTree.suggestMatch("_re").size());
		
		// Suggestions for empty words give back empty lists
		assertEquals(" This should not return anything because an empty string is being inputted ", 0, wTree.suggestMatch("").size());
		
		// Suggestions of whole words should just return the word.
		assertEquals( find1 + " is a whole word and should be the only word returned from dictionary ", 1, wTree.suggestMatch(find1.toUpperCase()).size());
		assertEquals( find1.toUpperCase() + " is a whole word and should be the only word returned ", find1.toUpperCase(), wTree.suggestMatch(find1.toUpperCase()).get(0));
		
		// Suggestions for add_n_, should be addend, and adding
		String test = "add_n_";
		testArr.add(find4);
		testArr.add(find3);
		//for ( String item: testArr ) {
		for ( int index = 0; index < testArr.size(); index++ ) { 
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), wTree.suggestMatch(test).get(index));
		}
		
		// Suggestions for _e__v__, should be removal, removed
		test = "_e__v__";
		testArr.clear();
		testArr.add(word1);
		testArr.add(word3);
		for ( int index = 0; index < testArr.size(); index++ ) {
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), wTree.suggestMatch(test).get(index));
		}		
		
		// Suggestions for __d_n_, should be addend, adding, redone
		test = "__d_n_";
		testArr.clear();
		testArr.add(find4);
		testArr.add(find3);
		testArr.add(word4);
		for (int index = 0; index < testArr.size(); index++ ) {
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), wTree.suggestMatch(test).get(index));
		}		
		
		// Suggestions for __m____, should be removal, removed, remodel
		test = "__m____";
		testArr.clear();
		testArr.add(word7);
		testArr.add(word1);
		testArr.add(word3);
		ArrayList<String> answer = wTree.suggestMatch(test);
		for ( int index = 0; index < testArr.size(); index++ ) {
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), answer.get(index));
		}
		
	}
	
	/**
	 * Test method for {@link jeune_Trie.WordDictionary#toString()}.
	 */
	@Test
	public void testToString() {
		String result = "jeune_Trie.WordDictionary [root = jeune_Trie.TrieNode [letter = , isRoot = true, isWord = false, nextLetters = {}], size = 0}]";
		assertEquals("These should match", result, wTree.toString());
	}

}
