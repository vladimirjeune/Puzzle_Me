/**
 * This is a node of the Tree.  It contains a Map of Strings to the other TreeNodes
 * The root node will be indicated by the boolean isRoot function returning true.
 * The rootNode will never be a word, so isWord will also return false.
 */
package jeune_Trie;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vladimirjeune
 *
 */
public class TrieNode implements Comparable {

	/**
	 * Default Constructor
	 */
	public TrieNode() {
		init();
	}
	
	/**
	 * Constructor
	 */
	public TrieNode( String aLetter ) {
		init( aLetter );
	}
	
	/**
	 * This function will initialize the datastructure.
	 */
	public void init() {
		_letter = "";
		_nextLetters = new TreeMap<String, TrieNode>();
		_isWord = false;
		_isRoot = false;
	}

	/**
	 * This function will initialize the data« structure with the inputted parameters 
	 */
	public void init( String aLetter ) {
		_letter = aLetter.toUpperCase();
		_nextLetters = new TreeMap<String, TrieNode>();
		_isWord = false;
		_isRoot = false;		
	}
	
	/**
	 * This function will return a boolean indicating whether or not this Node is the root.
	 * Preconditions:  NA
	 * Postconditions: Whether or not this node is the root is returned
	 */
	public boolean isRoot() {
		return _isRoot;
	}
	
	/**
	 * This function will label this node as the root of a tree.  There should only be one
	 * root for a tree.
	 * Preconditions:  This node will be a root for a tree.  You cannot make a root out of a 
	 * 				node that represents a letter.
	 * Postconditions: This node will be labeled as being a root. True returned if successful.
	 */
	public boolean makeRoot() {
		boolean retVal = false;
		if ( _letter.equals("")) {
			retVal = _isRoot = true;
		}
		return retVal;
	}
	
	/**
	 * This function removes the root label from the node that it is called upon.  There should
	 * be only 1 root at a time.
	 * Preconditions:  NA
	 * Postconditions: The root will not be labeled as a root.  If it was not a root in the 1st 
	 * 				place, nothing will happen.  Always returns true for symmetry with the makeRoot().
	 */
	public boolean unMakeRoot() {
		_isRoot = false;
		return true;         // The modification was successful.
	}
	
	/**
	 * This function will return whether or not this function is the end letter of a word.
	 * Preconditions:  NA
	 * Postconditions: This function will tell whether this function is an end letter or not.
	 * @return
	 */
	public boolean isWord() {
		return _isWord;
	}
	
	/**
	 * This function will label this node as as end end letter of a word.
	 * Preconditions:  This node will be labeled as an end letter for a word.
	 * 				You cannot make an object as a word if it does not represent a letter.
	 * Postconditions: The node will be labeled an end letter
	 */
	public boolean makeWord() {
		boolean retVal = false;
		
		if ( ! _letter.equals("")) {
			retVal = _isWord = true;
		}
		
		return retVal;		
	}
	
	/**
	 * This function will remvoe any word label that is present.  If no word label is
	 * present then the effect will leave the calling object unchanged.
	 * Preconditions:  NA
	 * Postconditions: Any Word label on this object will be removed
	 */
	public boolean unMakeWord() {
		_isWord = false;
		return true;
	}
	
	/**
	 * This function will set the letter for this node.
	 * Preconditions:  The letter that was passed in will be the letter for this node.
	 * Postconditions: The letter passed in will now represent this node.
	 */
	public void setLetter( String aLetter ) {
		_letter = aLetter.toUpperCase();
	}
	
	/**
	 * This function will return the letter that this node represents.
	 * Preconditions:  NA
	 * Postconditions: The letter that this node represents will be returned
	 */
	public String getLetter() {
		return _letter;
	}
	
	/**
	 * This function will add to the possible next letters that could come after this one.
	 * Preconditions:  If this letter already exist in the nextLetters, add will fail.
	 * 				There may already be letters after this one.
	 * Postconditions: A letter will be added to the possible next letters, if it does not
	 * 				already exists.
	 */
	public boolean addToNextLetters( String aCharacter ) { 
		boolean retVal = false;               // If a letter is already there false is returned. That OK.
		
		// Create functions to use in assertions that will be in most TrieNode functs that take
		// Chars from the outside.  Actually making sure all inputs are upCase and no puncts get 
		// in will be done in WordDictionary functions that take in words.  Assertions can be 
		// turned off later.
		
		// WordDictionary functs will upcase all words entered, and do a regex check for puncts.
		// If one of those puncts gets in the user should be notified that no puncts are allowed.
		
		// Follow the directions that you printed above.
		
		assert(noPunct( aCharacter ));
		
		String upChar = aCharacter.toUpperCase();
		
		if ( ! _nextLetters.containsKey( upChar ) ) {
			_nextLetters.put( upChar, new TrieNode( upChar ) );
			retVal = true;
		}
		
		return retVal;
	}
	
	/**
	 * This function will remove a letter from nextLetters if that letter is found and it does not 
	 * have any nextLetters of its own that leads to whole word completions.  
	 * EX: If it is asked to remove a letter that does not lead to a letter that ends a word.
	 * Preconditions:  The letter to be removed from next letters must not lead to a letter
	 * 				that ends a word.
	 * Postconditions: The inputted letter is removed from next letters or false is returned.   
	 */
	public boolean removeFromNextLetters( String aCharacter ) {
		boolean retVal = false;
		String upChar = aCharacter.toUpperCase();
		
		assert(noPunct( upChar ));
		
		TrieNode aNextTrie = _nextLetters.get(upChar);        // Does this letter exists in nextLetters
		
		if ( aNextTrie != null ) {                       
			if ( aNextTrie.sizeNextLetters() == 0) {         // Does that letter not lead to a word completion.
				_nextLetters.remove( upChar );               // If not remove it.  				
				retVal = true;
			}
		}
		
		return retVal;
	}
	
	/**
	 * This function will return the size of nextLetters.  Acceptable numbers range: 0 <= a <= 26
	 * Preconditions:  NA
	 * Postconditions: The size of nextLetters will be returned
	 */
	public int sizeNextLetters() {
		return _nextLetters.size();
	}
	
	/**
	 * This function will return the TrieNode that corresponds to the letter that is inputted in 
	 * the parameter list.  Only single alphanumeric characters are allowed in the parameter list.
	 * This function is normally supposed to be private, so if anything other than a single char
	 * string is used an assertion error will be thrown.  If the inputted charString is not 
	 * represented in nextLetters, then "" is returned.
	 * Preconditions:  Only single alphanumeric characters are allowed in the input string.  Anything
	 * 				else throws an error.  
	 * Postconditions: The asked for TrieNode from nextLetters is returned if it exists in the list.
	 * 				Otherwise, "" is returned.
	 */
	public TrieNode getNextLetter( String aLetter ) {
		return _nextLetters.get(aLetter.toUpperCase());
	}
	
	/**
	 * This will tell whether or not the mentioned alphanumeric character exists in nextLetters.
	 * Preconditions:  NA
	 * Postconditions: Whether the asked for alphanumeric character String exists in nextLetters is returned
	 */
	public boolean hasNextLetter( String aLetter ) {
		return _nextLetters.containsKey( aLetter.toUpperCase());    // Must be upcased
	}
	
	
	/**
	 * This function returns a String that holds all the letters that exist in next letters
	 * Preconditions:  This function returns a string of all the letters in next letters.
	 * Postconditions: A String representing all the nextLetters will be returned.
	 */
	public String allNextLetters() {
		String retVal = "";
		Set<String> kSet =  _nextLetters.keySet();     // Should be sorted, came from TreeSet.
		
		for ( String letter : kSet ) {      
			retVal += letter + " ";
		}
				
		return retVal.trim();
	}
	
	/**
	 * This function returns a String representation of the state of this object.
	 * This overrides java.lang.Object.toString();
	 * Preconditions:  NA
	 * Postconditions: The state of this object will be represented in String form
	 */
	@Override public String toString() {
			
		return getClass().getName() 
			+ " [letter = " + _letter 
			+ ", isRoot = " + _isRoot
			+ ", isWord = " + _isWord 
			+ ", nextLetters = {" 
			+ allNextLetters() 
			+ "}]";

	}
	
	/**
	 * This function overrides the equals().
	 * Warning two separate objects with different states can be equal if the are the same 
	 * letter
	 * Preconditions:  Both objects must be the same class.
	 * Postconditions: A boolean reflecting the equalitiy of the two objects will be returned.
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
		TrieNode other = (TrieNode)otherObject;
		
		// test whether the fields have identical values.
		return _letter.equals(other.getLetter()) ;
		
	}
	
	/**
	 * This is the overridden hash function
	 * Preconditions:  NA
	 * Postconditions: A hash value will be returned that allows for good distribution of values.
	 * 				Values used are those used in comparisons of these objects.
	 */
	@Override
	public int hashCode() {
		return _letter.hashCode();
	}
	
	/** 
	 * This function will make sure that all inputted characters are in the correct format 
	 * or they will output false.  The correct format is a single letter or number with no
	 * punctuation.  If this format is not followed an Exception is thrown.
	 * Preconditions:  Only single letters and numbers are allowed in the parameters of the function.
	 * Postconditions: This will return an upcased version of what was passed in.  And throws an
	 * 				Exception if the wrong format is entered.  A calling function may want to handle
	 * 				the result.
	 * @throws CharFormatException 
	 */
	public String correctFormat( String aChar ) throws CharFormatException {
		boolean doesMatch = false;

		Pattern pFormat = Pattern.compile("[\\w]{1}");    // Only single alphanumeric characters allowed.
		Matcher matchFormat = pFormat.matcher( aChar );
		doesMatch = matchFormat.matches();

		if ( ! doesMatch ) {
			throw new CharFormatException("Only single alphanumeric characters are allowed.");
		}
		
		return aChar.toUpperCase();
	}
	
	public boolean noPunct( String aChar ) {
		
		Pattern pFormat = Pattern.compile("[\\w]{1}");    // Only single alphanumeric characters allowed.
		Matcher matchFormat = pFormat.matcher( aChar );
		return matchFormat.matches();

	}
	
	/**
	 * This function will compare to TrieNodes by letters they represents
	 * This will satisfy the Comparable Interface.
	 */
	public final int compareTo( Object otherObject ) {
		TrieNode other = (TrieNode)otherObject;
		return _letter.compareTo(other.getLetter());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	String           _letter;  // Set, get
	Map<String, TrieNode> _nextLetters;     // addLetter, rmLetter(if not word and no kids) used outside by rmWord for tree.
	boolean          _isWord;  // Make word
	boolean          _isRoot;  // Make root
	
}
