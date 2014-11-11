/**
 * 
 */
package jeune_Trie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author vladimirjeune
 */
public class WordDictionary {

	/**
	 * This is the Constructor.
	 */
	public WordDictionary() {
		init();
	}
	
	/**
	 * This function will be used to initialize the data structure.
	 */
	public void init () {
		_root = new TrieNode();       // This is the root for the tree.  It has no value of its own.
		_root.makeRoot();
		_size = 0;
	}
	
	/**
	 * This function will add a word to the Tree if it does not exist already as a delimited word.
	 * All letters that are inputted will be uppercased in the data structure.
	 * Preconditions:  This takes alphanumerics not punctuation.  EX: Don't => DONT.  Any punctuation 
	 * 				in the inutted string will be removed before being added to the Tree.
	 * 				This will remove spaces before placing words into the tree.
	 * Postconditions: True will be returned if the word is not already present in the Tree as a word.
	 * 				false will be returned otherwise.
	 * @return boolean
	 */
	public boolean addWord( String aWord ) {
		int index = 0;
		aWord = capsNoPunctNoSpaces( aWord );        // Removes spaces, and punctuation, now all caps

		String[] sArr = splitWord( aWord );

		return addWordTrav( sArr, index, _root );
	}
	
	/**
	 * This function will search for the place where this word is supposed to go.
	 * If the word already exists in its entirety as a sub-part of a larger word, it will
	 * be marked as a word.  Otherwise; if it does not already exist, or exists
	 * partially, then space will be created for the remaining characters.
	 * Preconditions:  There should be no punctuation in the string.  Any punctuation that is inputted
	 * 				into the string will be removed before the word is placed in the tree.
	 * 				There should be no spaces in the string that is inputted.
	 * Postconditions: The word in the parameter will be recognized as a word in the tree.
	 * 				True will be returned if the word does not already exists.
	 */
	public boolean addWordTrav( String[] anArray, int anIndex, TrieNode aNode ) {
		boolean retVal = false;
		
		if ( anIndex != anArray.length ) {            // As long as not at the end of the word.
			
			if ( aNode.hasNextLetter( anArray[ anIndex ])) {
				retVal = addWordTrav( anArray, (anIndex + 1), aNode.getNextLetter(anArray[anIndex]));
			} else {
				aNode.addToNextLetters(anArray[anIndex]);
				retVal = addWordTrav( anArray, (anIndex + 1), aNode.getNextLetter(anArray[anIndex]));
			}
						
		} else {     // We are at the end, make it a word.
			if ( ! aNode.isWord() ) {
				aNode.makeWord();
				_size++;
				retVal = true;
			}
		}
		
		return retVal;
	}
	
	
	/**
	 * This function will delete a word from the tree that is passed in as a value.
	 * Preconditions:  The word should exist in the tree.
	 * Postconditions: This word will no longer be a word in the tree.
	 */
	public boolean deleteWord( String aWord ) {
				
		// When at the end of a word you will unmark it as a word.
		// Then you will check for kids.  If there are kids set deleteTail to false, and will stay that way.
		// If there are no kids you will set deleteTail to true.  Then that should activate a piece
		// of code that will delete the previous node from nextLetters, until you reach someone who has kids,
		// which will set deleteTail to false.  After you set deleteTail to false, there is no way to make it 
		// true again.
		
		int index = 0;
		
		aWord = capsNoPunctNoSpaces( aWord );        // Removes spaces, and punctuation, now all caps
		String[] sArr = splitWord( aWord );
		Boolean deleteTail = false;                  // AutoBoxing
		
		return deleteWordTrav( sArr, index, _root, deleteTail );
	}
	
	/**
	 * This function will recursively go through the tree and see if the inputted word exists.
	 * If it does exists as a word then it will be removed from the tree.
	 * Preconditions:  NA
	 * Postconditions: Returns true if the inputted word exists and can be deleted from the tree.
	 * 				returns false otherwise.
	 */
	public boolean deleteWordTrav( String[] anArray, int anIndex, TrieNode aNode, Boolean deleteTail ) {
		boolean retVal = false;
		
		if ( anIndex != anArray.length ) {                          // While we are still in range
			if ( aNode.hasNextLetter(anArray[anIndex])) {
				
				retVal = deleteWordTrav( anArray, (anIndex + 1), aNode.getNextLetter(anArray[anIndex]), deleteTail);
				
				if ( deleteTail ) {
					
					aNode.removeFromNextLetters(anArray[anIndex]);  // Remove the one we just came from
					
					if ( aNode.sizeNextLetters() != 0 ) {
						deleteTail = false;                         // This node leads to a word, stop deleting tail.
					}
					
				}
				
			}
		} else {                                                    // We are at the last letter of the String
			if ( aNode.isWord() ) {
				
				if ( aNode.sizeNextLetters() == 0 ) {               // If this node does not lead to other word endings..
					deleteTail = true;
				}
				
				aNode.unMakeWord();                                 // These always occur.
				_size--;
				retVal = true;
				
			}
			
		}		
		
		return retVal ;
	}
	
	
	/**
	 * This function will tell whether an inputted word exists as a word in the tree
	 * Preconditions:  There must be data in the tree
	 * Postconditions: A boolean value will be returned indicating whether or not the 
	 * 				inputted word was found
	 */
	public boolean findWord( String aWord ) {
		int index = 0;
		
		aWord = capsNoPunctNoSpaces( aWord );        // Removes spaces, and punctuation, now all caps
		String[] sArr = splitWord( aWord );
		
		return findWordTrav( sArr, index, _root );
	}
	
	/**
	 * This function will recursively find the word in the parameter if it exists in this 
	 * structure.
	 * Preconditions:  To be used by findWord()
	 * Postconditions: A boolean value will be returned that will indicate if find was successful.
	 * @param anArray Represents that string on a pre character basis.
	 * @param anIndex Represents the index that we should be at in the String
	 * @param aNode Represents the current node in the tree.
	 */
	public boolean findWordTrav( String[] anArray, int anIndex, TrieNode aNode ) {
		boolean retVal = false;
		
		if ( anIndex != anArray.length ) {                   // If we are not at the end of the String
			if ( aNode.hasNextLetter(anArray[anIndex])) {    // If we are still matching
				retVal = findWordTrav( anArray, ( anIndex + 1 ), aNode.getNextLetter(anArray[anIndex]) );
			}
		} else {                                             // We are at end of String
			if ( aNode.isWord() ) {                          // If the String is a word
				retVal = true;
			}
		}

		return retVal;
	}
	
	
	
	/**
	 * This function will return a list of 0 - 25 Strings that match the criterions 
	 * in the parameter list.  
	 * EX:  Find a word with the letter 'a' in the 2nd position, of length 3.
	 * Preconditions:  The length of the String MUST be longer than the character position
	 * 				indicated.
	 * Postconditions: A list of 0 - 25 words will be returned that match the characeristics
	 * 				will be returned.
	 * @param aLetter Letter that results should match on
	 * @param anIndex Index that aLetter is from
	 * @param aLength Length of the words that we are looking for
	 */
	public ArrayList<String> suggestLetterMatch( String aLetter, int anIndex, int aLength ) {
		ArrayList<String> retVal = new ArrayList<String>();   // Later add a limitResultsNum parameter.
		String aWord = "";
		int travLength = 0;
		
		wordLogger.info("Index="+anIndex + "  length="+aLength);
		
		if ( aLength > anIndex ) { 
			suggestLetterMatchTrav( aWord, retVal, travLength, _root, aLetter, anIndex, aLength );
		}
		
		return retVal;
	}
	
	/**
	 * This function will find words that match the description given in the parameter list
	 * in the function findWordsToMatch
	 * Preconditions:  This should be used by findWordsToMatch
	 * Postconditions: aList will be modified to contain a list of words matching the criterion
	 * 				entered into the calling function.
	 * @param aWord String that represents all the letters that this function has traversed thus far.
	 * @param aList List that holds all the matches that we have found thus far.
	 * @param travLength Integer that represents the distance from the root we have traveled.
	 * @param aNode Node that represents the current node in the tree.
	 * @param aLetter Letter that results should match on
	 * @param anIndex Index that aLetter is from
	 * @param aLength Length of the words that we are looking for
	 */
	public void suggestLetterMatchTrav( String aWord, ArrayList<String> aList, int travLength, TrieNode aNode, String aLetter, int anIndex, int aLength ) {
		
		if ( ( aNode.sizeNextLetters() != 0 ) 
				&& ( travLength < aLength ) 
				&& ( ( anIndex != travLength ) || ( aNode.getLetter().equals(aLetter) ) ) ) {   
			
			String[] sArr = aNode.allNextLetters().split(" ");                          // Create a String of the nextLetters
			
			for ( int index = 0; index < aNode.sizeNextLetters(); index++ ) {
				suggestLetterMatchTrav( (aWord + aNode.getLetter()), aList, (travLength + 1), aNode.getNextLetter(sArr[ index ]), aLetter, anIndex, aLength );
			}

		}
		
		if ( (travLength == aLength) && aNode.isWord() ) {  // If this node has kids, and we are still < travLength, and not adding same word 2x
			if ( ( anIndex != travLength ) || (aNode.getLetter().equals(aLetter)) ) {    // If index eq travLength AND We have the correct letter, if index, and lastIndex same			
				aList.add(aWord + aNode.getLetter());
			}
		}
		
	}
	
	/**
	 * This function will find words of the specified length and return them in an arrayList.
	 * There should be between 0 - 25 words returned.  If no words that match your criterion
	 * are found nothing will be returned
	 * Preconditions:  NA
	 * Postconditions: Between 0 - 25 words will be returned matching your criterion.
	 * @param aLength Length of words that will be in the result set.
	 */
	public ArrayList<String> suggestWordsOfLength( int aLength ) {
		ArrayList<String> retVal = new ArrayList<String>();   // Later add a limitResultsNum parameter.
		int travLen = 0;	
		String aWord = "";

		if ( 0 != aLength ) {
			suggestWordsOfLengthTrav(aWord, retVal, aLength, travLen, _root );
		}
		
		return retVal;
		
	}
	
	/**
	 * This function will helps findWordsOfLength.  It takes an arrayList that will be  returned with
	 * the list of words that are of length n in the Tree.
	 * Preconditions:  Used by findWordsOfLength.
	 * Postconditions: The arrayList of n length words that were compiled by going thru the list 
	 * 				will be returned.
	 * @param aWord a String that starts out empty and contains the letters that have been traversed in this level of the tree.
	 * @param aList a List that holds all the words that match the criterion inputted into the function.
	 * @param theLength Length of word that we are looking for as answers.
	 * @param travLength an int that tells the level of the tree that we are currently on.
	 * @param aNode Node that represents the current node being observed in the tree.
	 */
	public void suggestWordsOfLengthTrav( String aWord, ArrayList<String> aList, int theLength, int travLength, TrieNode aNode ) {
		boolean tripped = false;
				
		if ( (aNode.sizeNextLetters() != 0 ) && ( travLength < theLength ) ) {   // If this node has kids, and we are still < travLength
			String[] sArr = aNode.allNextLetters().split(" ");

			if ( (travLength == theLength) && (aNode.isWord()) ) {               // We are at the correct length and this is a word.
				aList.add(aWord + aNode.getLetter());
				tripped = true;
			} else {                                                             // Not yet at correct length, loop thru children

				for ( int index = 0; index < aNode.sizeNextLetters(); index++ ) {
					suggestWordsOfLengthTrav( (aWord + aNode.getLetter()), aList, theLength, (travLength + 1), aNode.getNextLetter(sArr[ index ]) );
				}

			}

		}
		
		if ( (false == tripped ) && (travLength == theLength) && (aNode.isWord())) {  // If this node has kids, and we are still < travLength, and not adding same word 2x
			aList.add(aWord + aNode.getLetter());
		}
		
	}
	
	/** 
	 * This function will return a list of k words, where 0 <= k <= 25, that match the 
	 * inputted characteristics.
	 * Preconditions:  String length must follow, 0 < strlen <= length.  For a string to 
	 * 				be a prefix it must start from the beginning.  
	 * 				EX: |P|R|E| | | |.  PRE would be the prefix.  You would want suggestions of size 6
	 * Postconditions:  A list of words will be returned that will start with the prefix
	 * 				if any words with that prefix exists in the Tree and are of the correct
	 * 				length.
	 * @param prefix A list of characters starting at the beginning of the string
	 * @param length Length of the words that you would like to have suggested.
	 */
	public ArrayList<String> suggestPrefixMatch( String prefix, int aLength ) {   // Think of limiting resuls later.
		wordLogger.info("Entry SuggestWords");
		ArrayList<String> retVal = new ArrayList<String>();
		ArrayList<String> aList = new ArrayList<String>();
		String[] strList = new String[ prefix.length() ];
		String aWord = "";
		int travLength = 0;
		
		prefix.toUpperCase();
		
		if ( (prefix.length() != 0 ) && (aLength > prefix.length() ) ) {    // Word is longer than aLength
			suggestPrefixMatchTrav( aWord, aList, travLength, _root, strList, prefix, aLength );
			retVal = aList;
		} else if ( prefix.length() == aLength ) {                          // prefix is the only match
			retVal.add(prefix.toUpperCase());
		}
		wordLogger.info("Exit SuggestWords: " + retVal );
		
		return retVal;		
	}
	
	/**
	 * This function will help suggestWords().  This will traverse the tree finding words, if any, 
	 * that match the criterion implied in the parameter list.
	 * Preconditions:  To be used by suggestWord()
	 * Postconditions: A list of words starting with the, asked for, prefix and of the appropriate 
	 * 				length will be returned.
	 * @param aWord a String that starts out empty and contains the letters that have been traversed in this level of the tree.
	 * @param aList a List that holds all the words that match the criterion inputted into the function.
	 * @param travLength an int that tells the level of the tree that we are currently on.
	 * @param aNode Node that represents the current node being observed in the tree.
	 * @param strList 
	 * @param prefix String that represents the beginning characters of a word, all results will have prefix as its first few characters.
	 * @param aLength Length of word that we are looking for as answers.
	 */
	public void suggestPrefixMatchTrav( String aWord, ArrayList<String> aList, int travLength, TrieNode aNode, String[] strList, String prefix, int aLength ) {
		
		if ( travLength < prefix.length() ) {                                         // If we are not at the end of the prefix
			String prefChar = prefix.substring( travLength, (travLength + 1));

			if ( aNode.hasNextLetter(prefChar)) {                                     // If we are still matching
				suggestPrefixMatchTrav( aWord, aList, (travLength + 1), aNode.getNextLetter(prefChar), strList, prefix, aLength );
			}

		} else {                                                                      // We are at end of the prefix

			if ( (travLength == prefix.length() )  ) {                                // Start searching for completions.    
				aWord = prefix.substring(0, (prefix.length() - 1) ).toUpperCase();                  // Removing the last character, since findWordsOfLength() does not need it.
			}

			suggestWordsOfLengthTrav( aWord, aList, aLength, travLength, aNode );			
			
		}

	}
	
	/**
	 * This function will take partially filled out strings and return a list of words that 
	 * fill in the voids left in the original string.  
	 * EX:  "_OR_" => ["CORK", "DORK", "GORE", "PORE", "TORT",...]
	 * Preconditions:  Blanks in the string MUST be represented by '_'s.  Spaces are not allowed.
	 * 			  The string entered should be > 0.  Entering whole words without blanks, will return that word.
	 * Postconditions: A list of the words that match the original string lettering will be returned
	 * @param aString A string with blanks represented by '_' that will be matched against words in the data structure.
	 */
	public ArrayList<String> suggestMatch( String aString ) {
		ArrayList<String> retVal = new ArrayList<String>();
		String aWord = "";
		String spc = "_";
		int travLength = 0;
		
		if ( 0 != aString.length() ) {                                          // If string is not empty
//			if ( -1 != aString.indexOf(spc)) {                                  // If word is not whole
				suggestMatchTrav( aWord, retVal, travLength, _root, aString );  
//			}
//			else {                                                              // If whole string entered return it.
//				retVal.add(aString);
//			}
		}
		
		return retVal;
	}
	
	/**
	 * This function will be used by suggestMatch.  It will traverse the tree and gather all
	 * words that match the string passed in.
	 * Preconditions:  To be used by suggestMatch(), spaces in the string will be indicated by '_'
	 * Postconditions: All words that can fill in the empty spaces will be added to the list
	 * @param aWord The contents of this string represents the path taken, a finished word that matches 
	 * 				criterion is placed in the list.
	 * @param aList The list that will hold the words that were found to match the criterion in the parameter in
	 * 				the calling function.
	 * @param travLength The distance we have traveled from the root.
	 * @param aNode Represents the current node
	 * @param aString The string that we want to find matches for in the data structure.
	 */
	public void suggestMatchTrav( String aWord, ArrayList<String> aList, int travLength, TrieNode aNode, final String aString) {
		String spc = "_";

		if ( (0  != aNode.sizeNextLetters()) && ( travLength < aString.length()) ) {
			String letterInString = new String ( "" + aString.charAt(travLength));
			String[] sArr = aNode.allNextLetters().split(" ");                            // Array of next letters
			
			if ( letterInString.equals(spc) ) {                                                // If there is a blank 
				for ( int index = 0; index < aNode.sizeNextLetters(); index++ ) {         // Blank; see what path can fill it.
					suggestMatchTrav( (aWord + aNode.getLetter()), aList, (travLength + 1), aNode.getNextLetter(sArr[ index ]), aString );
				}
			} else {
				if ( aNode.hasNextLetter(letterInString)) {                                // If a nextLetter equals the current letter in the string
					suggestMatchTrav( (aWord + aNode.getLetter()), aList, (travLength + 1), aNode.getNextLetter(letterInString), aString);
				}
			}
		}

																						   // If this is a word, we are at end of aString, and we have a letter
																						   // or a blank in aString at this position.
		if ( (travLength == aString.length()) 
				&& ((aNode.getLetter().equals( "" + aString.charAt(travLength - 1))) || (aString.charAt(travLength - 1) == spc.charAt(0)) ) 
				&& (aNode.isWord()) ) {
			aList.add(aWord + aNode.getLetter());
		}
		
	}
	
	/**
	 * This function will can be used to load a lot of words into the Tree.
	 * This function should be used when the tree has just been created and 
	 * needs to be loaded with words from a File.  This could be a costly operation,
	 * so should only be done when this would not be an issue.
	 * Preconditions:  This should be used when the time it takes to perform would not 
	 * 				be an issue.
	 * Postconditions: The words in the File will be inputted into the Tree.
	 * @param aFile The file that contains initial the word list that will be used.	
	 */
	public boolean loadTree( File aFile ) {
		boolean retVal = false;
		String aWord = "";
		
		try {
			FileReader fReader = new FileReader (aFile);
			BufferedReader bin = new BufferedReader(fReader);

			while ( aWord != null ) {

				aWord = bin.readLine();
				
				if ( aWord != null ) {                     // If not at end of file
					addWord( aWord );                      // Add to word to the tree.
				}
				
			}
			
			retVal = true;                                 // If an Exception is not thrown goes here.ŧ
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();  
		} catch (IOException f) {
			f.printStackTrace();
		}

		return retVal;
	}

	/**
	 * This function will print all the words that are in the tree.  This will most likely
	 * be an expensive operation in terms of time.
	 * Preconditions:  There should be data already in the tree.
	 * Postconditions: The data in the tree will be outputted.  Because of the structure of the 
	 * 				tree it will be outputted in alphanumeric order.
	 */
	public ArrayList<String> outputTree() {
		ArrayList<String> retVal = new ArrayList<String>();
		String aWord = "";
		
		outputTreeTrav( aWord, retVal, _root );
		
		return retVal;		
	}
	
	/**
	 * This function will traverse the tree and output the words that it finds to the 
	 * ArrayList in the parameter list.
	 * Preconditions:  This is to be used by outputTree()
	 * Postconditions: The ArrayList in the parameter list will be modified to hold all the 
	 * 				words in the tree.
	 * @param aWord Characters in this String represents the current place in the tree, if a certain
	 * 		character arrangement makes a word, that word will be outputted.
	 * @param aList Words that are found will be put in this list to be outputted.
	 * @param aNode Represents the current node in the list.
	 */
	public void outputTreeTrav( String aWord, ArrayList<String> aList, TrieNode aNode ) {
		String[] nextLetters = null;
		boolean tripped = false;

		if ( aNode.sizeNextLetters() != 0) {                      // As long as lettrs come after this

			if ( aNode.isWord() ) {                               // Printing this out here makes lexicographical sense.
				tripped = true;
				aList.add( aWord + aNode.getLetter() );           // Word placed in arraylist
			}

			nextLetters = aNode.allNextLetters().split(" ");      // An array of all the nextLetters

			for ( int index = 0; index < nextLetters.length; index++ ) {    // Loop thru all branches
				outputTreeTrav( (aWord + aNode.getLetter()), aList, aNode.getNextLetter(nextLetters[index]));
			}

		}

		if ( (aNode.isWord()) && (! tripped ) ) {                 // If you didn't get this word before             
			aList.add( aWord + aNode.getLetter() );           // Word placed in arraylist
		}

	}
	
	/**
	 * This function will print the contents of the tree to standard output
	 * Preconditions: NA
	 * Postconditions: The data in the tree will be outputted.
	 */
	public void printTreeData() {
		String aWord = "";
		
		printTreeDataTrav( aWord, _root );
		
	}
	
	/**
	 * This function will traverse the tree outputting words found to the output stream
	 * Preconditions:  Should be used by printTreeData
	 * Postconditions: All the words in the tree will be outputted.
	 * @param aWord Characters in this String will represent the place we are at in the tree.
	 * @param aNode Represents the current node.
	 */
	public void printTreeDataTrav( String aWord, TrieNode aNode ) {
		String[] nextLetters = null;
		boolean tripped = false;

		if ( aNode.sizeNextLetters() != 0) {                      // As long as lettrs come after this

			if ( aNode.isWord() ) {                               // Printing this out here makes lexicographical sense.
				tripped = true;
				System.out.println( aWord + aNode.getLetter() );  // Outputting to standard output.				
			}

			nextLetters = aNode.allNextLetters().split(" ");      // An array of all the nextLetters

			for ( int index = 0; index < nextLetters.length; index++ ) {    // Loop thru all branches
				printTreeDataTrav( (aWord + aNode.getLetter()), aNode.getNextLetter(nextLetters[index]));
			}

		}

		if ( (aNode.isWord()) && (! tripped ) ) {                 // If you didn't get this word before             
			System.out.println( aWord + aNode.getLetter() );      // Outputting to standard output.
		}

	}
	
	/**
	 * This function prints the data of the Tree in a backwards preorder traversal.
	 * In this way a simulated graphical representation of the structure can be created.
	 * Preconditianns:  NA
	 * Postconditions: The data in the tree will be outputted in such a way as to represent
	 * 				the structure of the tree.
	 */
	public String printTree() {

		StringBuilder output = new StringBuilder();
		int level = 0;
		String prevChar = "";
		
		printTreeTrav( output, _root, level, prevChar );     // You can modify the StringBuilder
		
		return output.toString();
		
	}
	
	/**
	 * This function is a helper function to the printTree().  It traverses the tree.
	 * Preconditions:  To be used by printTree()
	 * Postconditions: The structure of the data will be outputted using text.
	 */
	public void printTreeTrav(StringBuilder retVal, TrieNode curr, int level, String prevChar) {
		String[] nextLetters = null;
		StringBuilder line = null;
		int index = -1;
		
		if ( curr.sizeNextLetters() != 0 ) {                                    // If you have nextLetters, continue
			nextLetters = curr.allNextLetters().split(" ");                     // An array of the nextLetters
			
			for ( index = (nextLetters.length - 1) ; index > -1; index-- ) {    // Going in reverse
				
				printTreeTrav( retVal, curr.getNextLetter( nextLetters[index]), (level+1), curr.getLetter() );
				
			}
			
		}
		
		if ( ! curr.isRoot() ) {                                                 // The root has no data
			line = new StringBuilder();
			int spc = level;
			
			while ( spc > 0) {
				line.append("     ");
				spc--;
			}                                                                     // * indicates that this is a word ending.
			line.append("" + curr.getLetter() + prevChar.toLowerCase() + ( curr.isWord() ? "*":" ") + "\n");
			retVal.append(line);
		}
		
	}
	
	/**
	 * This function will return the number of words that are stored in this Data Structure
	 * Preconditions:  NA
	 * Postconditions: The number of words that the Trie represents will be outputted.
	 */
	public int numWords() {
		return _size;
	}
	
	/**
	 * This function will be used for testing and is not suposed to be used by the outside.
	 * It will return a reference to the root node.
	 * Preconditions:  To be used for testing purposes.
	 * Postconditions: The root node will be returned.
	 */
	public TrieNode testRoot() {
		return _root;
	}
	
	/**
	 * This function will make sure that the inputted word has no punctuation, 
	 * is in ALL caps,
	 * and contains no spaces.
	 * Preconditions:  NA
	 * Postconditions: All occurences of punctuation and spaces will be removed from the input
	 * 				string.  Furthermore, the string will be capitalized.
	 * @param aWard String to be processed
	 */
	public String capsNoPunctNoSpaces( String aWord ) {
		aWord = aWord.toUpperCase();
		return aWord = aWord.replaceAll("\\W", "");         // Remove punctuation and spaces
	}
	
	/**
	 * This function will split words into single character Strings and then place them in an 
	 * array.
	 * Preconditions:  NA
	 * Postconditions: A String array containing the characters of the original String will 
	 * 				be returned.
	 * @param aWord Word to be split.
	 */
	public String[] splitWord( String aWord ) {
		return aWord.split("\\B");          // Splits on non-word boundaries
	}
	
	/** 
	 * This function overrides the toString method, returns the state of the object.
	 * To print out all the words in the tree use outputTree()
	 */
	public String toString() {
		
		return getClass().getName() 
			+ " [root = " + _root 
			+ ", size = " + _size
			+ "}]";

	}
	
	/**
	 * This is a minimal hash function.  It will Ask for the Hash of the TriNode, and take our size.
	 */
	
	TrieNode _root;      // Root of the tree
	int _size;           // Must keep track of successful add/delete(s)
	public static Logger wordLogger = Logger.getLogger("jeune_Trie");
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		wordLogger.setLevel(Level.FINER);
	}

}
