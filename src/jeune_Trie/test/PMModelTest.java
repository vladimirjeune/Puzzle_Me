/**
 * 
 */
package jeune_Trie.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import jeune_Trie.ActiveRegion;
import jeune_Trie.GameType;
import jeune_Trie.MirrorType;
import jeune_Trie.MoveType;
import jeune_Trie.PMModel;
import jeune_Trie.Position;
import jeune_Trie.UseType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vladimirjeune
 *
 */
public class PMModelTest {

	PMModel _def_PM ;
	String _test_Board;
	String _test_Board2;
	
	public enum ModelType {
		
		/**
		 * This is a small regular crossword puzzle
		 */
		REGULAR,
		
		/**
		 * This is a Japanese style grid
		 */
		JAPANESE,
		
		/**
		 * This is a British style grid
		 */
		BRITISH,
		
		/**
		 * This is an American style grid
		 */
		AMERICAN, 
		
		/**
		 * This is a CRISS CROSS style grid (later)
		 */
		SPARSE
		
	}
	
	ModelType _modelType;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		_def_PM = new PMModel() ;
		_modelType = ModelType.REGULAR;             // Can be chaanged for different tests
		
		_test_Board = "  |0  |1  |2  |3  |4  |\n" +
		"______________________\n" +
		"0 |    |    |    |@@@|@@@|\n" +
		"______________________\n" +
		"1 |    |@@@|    |@@@|@@@|\n" +
		"______________________\n" +
		"2 |    |    |    |    |    |\n" +
		"______________________\n" +
		"3 |@@@|@@@|    |@@@|    |\n" +
		"______________________\n" +
		"4 |@@@|@@@|    |    |    |\n" +
		"______________________\n\n" +
		"Size - 5 BY 5\n" +
		"ActiveRegion = jeune_Trie.ActiveRegion [ Start Position = jeune_Trie.Position [ Row = 0, Column = 0, StartPlace = 5000 ], Length = 0, Orientation = HORIZONTAL ]\n" +
		"Current Position = null\n" +
		"Orientation = HORIZONTAL\n" +
		"Use Type is CREATE\n" +
		"Game Type is NXN\n" +
		"Mirror Type is FLATFLIP\n" +
		"Start positions are []\n" +
		"Across Places and Hints are {}\n" +
		"Down Places and Hints are {}\n";
		
		_test_Board2 = "  |0  |1  |2  |3  |4  |\n" +
		"______________________\n" +
		"0 |    |    |    |@@@|@@@|\n" +
		"______________________\n" +
		"1 |    |@@@|    |@@@|@@@|\n" +
		"______________________\n" +
		"2 |    |    |    |    |    |\n" +
		"______________________\n" +
		"3 |@@@|@@@|    |@@@|    |\n" +
		"______________________\n" +
		"4 |@@@|@@@|    |    |    |\n" +
		"______________________\n\n" +
		"Size - 5 BY 5\n" +
		"ActiveRegion = jeune_Trie.ActiveRegion [ Start Position = jeune_Trie.Position [ Row = 0, Column = 0, StartPlace = 1 ], Length = 3, Orientation = HORIZONTAL ]\n" +
		"Current Position = jeune_Trie.Position [ Row = 0, Column = 0, StartPlace = 1 ]\n" +
		"Orientation = HORIZONTAL\n" +
		"Use Type is CREATE\n" +
		"Game Type is NXN\n" +
		"Mirror Type is FLATFLIP\n" +
		"Start positions are [jeune_Trie.Position [ Row = 0, Column = 0, StartPlace = 1 ], jeune_Trie.Position [ Row = 0, Column = 2, StartPlace = 2 ], jeune_Trie.Position [ Row = 2, Column = 0, StartPlace = 3 ], jeune_Trie.Position [ Row = 2, Column = 4, StartPlace = 4 ], jeune_Trie.Position [ Row = 4, Column = 2, StartPlace = 5 ]]\n" +
		"Across Places and Hints are {jeune_Trie.Position [ Row = 0, Column = 0, StartPlace = 1 ]=, jeune_Trie.Position [ Row = 2, Column = 0, StartPlace = 3 ]=, jeune_Trie.Position [ Row = 4, Column = 2, StartPlace = 5 ]=}\n" + 
		"Down Places and Hints are {jeune_Trie.Position [ Row = 0, Column = 0, StartPlace = 1 ]=, jeune_Trie.Position [ Row = 0, Column = 2, StartPlace = 2 ]=, jeune_Trie.Position [ Row = 2, Column = 4, StartPlace = 4 ]=}\n";
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Test method for {@link jeune_Trie.PMModel#PMModel()}.
	 * Testing the constructor
	 */
	@Test
	public void testPMModel() {
		
		assertEquals("This should be set to null.", null, _def_PM.getBoard());
		assertEquals("This should be set to 0.", 0, _def_PM.getOrientation());
		assertEquals("This should be set to 0.", 0, _def_PM.getSideLength());
		assertEquals("This should be set to null.", null, _def_PM.getCurr());
		assertTrue("This should be equal to a new region.", _def_PM.getActiveRegion().equals(new ActiveRegion()));
		assertEquals("This should be set to null.", 0, _def_PM.getStartPositions().size());
		assertEquals("This should be set to null.", 0, _def_PM.getAcrossHints().size());
		assertEquals("This should be set to null.", 0, _def_PM.getDownHints().size());
		assertEquals("This should be set to null.", 0, _def_PM.getDictionary().numWords());  // Will figure out later
		assertEquals("This should be set to null.", 0, _def_PM.getWordBank().numWords());
		
		assertEquals("This should be set to null.", null, _def_PM.getGameType());
		assertEquals("This should be set to null.", null, _def_PM.getMirrorType());
		assertEquals("This should be set to null.", null, _def_PM.getUseType());
		assertEquals("This should be set to \'O\'.", '0', _def_PM.getPlace_Created(new Position(2, 2, 2)));
		
	}

	/**
	 * Test the setting up of the board.  This will test the separate functions that have to be called
	 * to set up the game and the board.  It will simulate what will happen when the actual game 
	 * is started, as it relates to the board.
	 */
	@Test
	public void testSetUpGameBoard() {
		UseType boardUseType = UseType.CREATE;
		GameType boardGameType = GameType.NXN;
		MirrorType boardMirrorType = MirrorType.FLATFLIP;
		Position pos1 = new Position(0, 3);
		Position pos2 = new Position(0, 4);
		Position pos3 = new Position(1, 1);
		Position pos4 = new Position(1, 3);
		Position pos5 = new Position(1, 4);
		
		int boardSize = 5;
		
		// Choose proper action, CREATE or PLAY, Use CREATE
		_def_PM.setUseType( boardUseType );
		assertTrue("The use of this board should be " + boardUseType +": ", _def_PM.getUseType().equals(boardUseType));
		
		// Choose use type, NxN, or CRISS CROSS, Use NXN
		_def_PM.setGameType( boardGameType );
		assertTrue("The game type at this point should be " + boardGameType + ": ", _def_PM.getGameType().equals(boardGameType));
		
		// Choose board size, size >= 3 && size < 32 
		_def_PM.setSize( boardSize );
		assertEquals("The board should be of size " + boardSize +": ", boardSize, _def_PM.getSideLength() );
		
		// Choose the type of mirror for the bumpers
		_def_PM.setMirrorType( boardMirrorType );
		assertTrue("The mirror type that was wanted for this board is " + boardMirrorType +": ", _def_PM.getMirrorType().equals(boardMirrorType));
		
		// Do Bumper placement 
		_def_PM.flipBumper( pos1 );
		_def_PM.flipBumper( pos2 );
		_def_PM.flipBumper( pos3 );
		_def_PM.flipBumper( pos4 );
		_def_PM.flipBumper( pos5 );

		// Make sure that we can see an ASCII representation of the board.
		assertEquals("This should match predefined output: ", _test_Board, _def_PM.printBoardStates());
		
		// Numeber all the start positions.
		_def_PM.numberStartPositions();
		String hintPositions = "[jeune_Trie.Position [ Row = 0, Column = 0, StartPlace = 1 ], jeune_Trie.Position [ Row = 0, Column = 2, StartPlace = 2 ], jeune_Trie.Position [ Row = 2, Column = 0, StartPlace = 3 ], jeune_Trie.Position [ Row = 2, Column = 4, StartPlace = 4 ], jeune_Trie.Position [ Row = 4, Column = 2, StartPlace = 5 ]]";
		assertEquals("These are the positions that should be found by the function : ", hintPositions, _def_PM.getStartPositions().toString());
		
		// Set Curr and ActiveRegion for start of Puzzle creation.
		Position position = new Position(0,0,1);
		ActiveRegion region = new ActiveRegion( position, 3, 0 );

		_def_PM.setRegionAndCurr( position );            // Should set the Region and Current.
		
		//System.out.println(_def_PM.printBoardStates());
		
		assertTrue("Active region should have a length of 3: ", region.equals( _def_PM.getActiveRegion() ) );
		assertTrue("Curr should be in the ActiveRegion, and should be in the upper left corner: ", _def_PM.getCurr().equals(position));

		// Test that the collection of the bumpers on the board goes smoothly.
		_def_PM.collectBumpers();
		ArrayList<Position> matchList = new ArrayList<Position>();
		ArrayList<Position> bList = _def_PM.getBumperList();

		matchList.add(pos1);                                                  // These will be tested against actual output later.
		matchList.add(pos2);
		matchList.add(pos3);
		matchList.add(pos4);
		matchList.add(pos5);
		matchList.add(new Position(3,0));
		matchList.add(new Position(3,1));
		matchList.add(new Position(3,3));
		matchList.add(new Position(4,0));
		matchList.add(new Position(4,1));
		
		// Loop thru and match all the bumpers that are supposed to be there
		for ( int itr = 0; itr < matchList.size(); itr++ ) {
			
			if ( ! (matchList.get(itr).equals(bList.get(itr))) ) {
				fail("There are 1 or more mismatches in bumper comparison.");
			}
			
		}

	}
	
	/**
	 * BOARDSSETUPSTART does a default setup of the game board that is inputted, up until bumper creation
	 */
	public void boardSetupStart( PMModel aModel ) {
		UseType boardUseType = UseType.CREATE;
		GameType boardGameType = GameType.NXN;
		MirrorType boardMirrorType = MirrorType.FLATFLIP;
		int boardSize = 5;
		
		// Choose proper action, CREATE or PLAY, Use CREATE
		aModel.setUseType( boardUseType );
		
		// Choose use type, NxN, or CRISS CROSS, Use NXN
		aModel.setGameType( boardGameType );
		
		// Choose board size, size >= 3 && size < 32 
		aModel.setSize( boardSize );
		
		// Choose the type of mirror for the bumpers
		aModel.setMirrorType( boardMirrorType );

	}
	
	/**
	 * BOARDSSETUPSTART does a default setup of the game board that is inputted, up until bumper creation
	 */
	public void boardSetupStart( PMModel aModel, UseType aUseType, GameType aGameType, MirrorType aMirrorType, int aSize ) {
				
		// Choose proper action, CREATE or PLAY, Use CREATE
		aModel.setUseType( aUseType );
		
		// Choose use type, NxN, or CRISS CROSS, Use NXN
		aModel.setGameType( aGameType );
		
		// Choose board size, size >= 3 && size < 32 
		aModel.setSize( aSize );
		
		// Choose the type of mirror for the bumpers
		aModel.setMirrorType( aMirrorType );
		
	}
	
	/**
	 * BOARDSETUPEND finishes the setup of the board; starting from after bumper creation.
	 */
	public void boardSetupEnd( PMModel aModel ) {
		aModel.finishBoardInit();
	}
	
	/**
	 * BOARDFACTORY holds a few different types of boards with set bumper placements, that can be tested.  All valid boards will
	 * be from ModelType.  However, all may not be implemented yet.
	 */
	public PMModel boardFactory( ModelType aType ) {
		PMModel retVal = new PMModel();
		
		switch ( aType ) {
		case REGULAR:
			boardSetupStart( retVal, UseType.CREATE, GameType.NXN, MirrorType.FLATFLIP, 5  );                    // Does a default board setup, see definition for set variables.
						
			retVal.flipBumper( new Position(0,3) );
			retVal.flipBumper( new Position(0,4) );
			retVal.flipBumper( new Position(1,1) );
			retVal.flipBumper( new Position(1,3) );
			retVal.flipBumper( new Position(1,4) );
			
			break;
			
		case JAPANESE:
			boardSetupStart( retVal, UseType.CREATE, GameType.NXN, MirrorType.FLATFLIP, 9  );                    // Does a default board setup, see definition for set variables.
			
			retVal.flipBumper( new Position(0,4) );
			retVal.flipBumper( new Position(1,3) );
			retVal.flipBumper( new Position(1,8) );
			retVal.flipBumper( new Position(2,2) );
			retVal.flipBumper( new Position(2,5) );
			retVal.flipBumper( new Position(2,7) );
			retVal.flipBumper( new Position(3,1) );
			retVal.flipBumper( new Position(3,6) );
			retVal.flipBumper( new Position(4,4) );
			
			break;
			
		case BRITISH:
			boardSetupStart( retVal, UseType.CREATE, GameType.NXN, MirrorType.FLATFLIP, 15  );                    // Does a default board setup, see definition for set variables.
			
			retVal.flipBumper( new Position(0,4) );
			retVal.flipBumper( new Position(1,0) );
			retVal.flipBumper( new Position(1,2) );
			retVal.flipBumper( new Position(1,4) );
			retVal.flipBumper( new Position(1,6) );
			retVal.flipBumper( new Position(1,7) );
			retVal.flipBumper( new Position(1,9) );
			retVal.flipBumper( new Position(1,11) );
			retVal.flipBumper( new Position(1,13) );			
			retVal.flipBumper( new Position(3,0) );			
			retVal.flipBumper( new Position(3,2) );			
			retVal.flipBumper( new Position(3,4) );			
			retVal.flipBumper( new Position(3,6) );			
			retVal.flipBumper( new Position(3,7) );			
			retVal.flipBumper( new Position(3,9) );			
			retVal.flipBumper( new Position(3,11) );			
			retVal.flipBumper( new Position(3,13) );			
			retVal.flipBumper( new Position(4,9) );			
			retVal.flipBumper( new Position(5,0) );			
			retVal.flipBumper( new Position(5,2) );			
			retVal.flipBumper( new Position(5,4) );			
			retVal.flipBumper( new Position(5,6) );			
			retVal.flipBumper( new Position(5,8) );			
			retVal.flipBumper( new Position(5,10) );			
			retVal.flipBumper( new Position(5,12) );			
			retVal.flipBumper( new Position(5,13) );			
			retVal.flipBumper( new Position(6,6) );			
			retVal.flipBumper( new Position(7,0) );			
			retVal.flipBumper( new Position(7,1) );			
			retVal.flipBumper( new Position(7,2) );			
			retVal.flipBumper( new Position(7,4) );			
			retVal.flipBumper( new Position(7,6) );			

			break;
		case AMERICAN:
			boardSetupStart( retVal, UseType.CREATE, GameType.NXN, MirrorType.FLATFLIP, 15  );                    // Does a default board setup, see definition for set variables.
			
			retVal.flipBumper( new Position(0,4) );
			retVal.flipBumper( new Position(0,10) );
			retVal.flipBumper( new Position(1,4) );
			retVal.flipBumper( new Position(1,10) );
			retVal.flipBumper( new Position(3,3) );
			retVal.flipBumper( new Position(3,7) );
			retVal.flipBumper( new Position(4,6) );
			retVal.flipBumper( new Position(4,12) );
			retVal.flipBumper( new Position(4,13) );			
			retVal.flipBumper( new Position(4,14) );			
			retVal.flipBumper( new Position(5,0) );			
			retVal.flipBumper( new Position(5,1) );			
			retVal.flipBumper( new Position(5,5) );			
			retVal.flipBumper( new Position(5,9) );			
			retVal.flipBumper( new Position(6,4) );			
			retVal.flipBumper( new Position(7,3) );			
			
			break;
		case SPARSE:
		default: // Do nothing
			System.out.println("You are not suppose to be here.");
			
		}
		
		boardSetupEnd( retVal );                       // finishes up board initialization from after bumper placement. 
		
		return retVal;
	}

	/**
	 * Tests FlipBumper()
	 */
	@Test 
	public void testFlipBumper() {
		
		// Test the REGULAR Board
		_def_PM = boardFactory( ModelType.REGULAR );

		// Each row corresponds to a binary number of flipped and unflipped bits
		final long[] byteRows1 = new long[ _def_PM.getSideLength() ];
		byteRows1[0] = 3;
		byteRows1[1] = 11;
		byteRows1[2] = 0;
		byteRows1[3] = 26;
		byteRows1[4] = 24;		
		
		//BINARY MAP BOARD UNDER TEST
		//  01234 RowNumber
		//  -----
		//0:00011 3
		//1:01011 11
		//2:01110 14
		//3:11010 26
		//4:11000 24
		
		assertTrue("One or more bumpers were not properly flipped : ",wasFlipGood( _def_PM, byteRows1, _def_PM.getSideLength(), _def_PM.getSideLength() ));

		// Test American board
		final PMModel pmm2 = boardFactory( ModelType.AMERICAN );
		
		//BINARY MAP BOARD UNDER TEST
		//                   RowNumber
		//  ---------------- 
		//0: 000010000010000 1040
		//1: 000010000010000 1040
		//2: 000000000000000 0
		//3: 000100010000000 2176
		//4: 000000100000111 263
		//5: 110001000100000 25120
		//6: 000010000000000 1024
		//7: 000100000001000 2056
		//8: 000000000010000 16
		//9: 000001000100011 547
		//10:111000001000000 28736
		//11:000000010001000 136
		//12:000000000000000 0
		//13:000010000010000 1040
		//14:000010000010000 1040

		final long[] byteRows2 = new long[ pmm2.getSideLength()];
		byteRows2[0] = 1040;
		byteRows2[1] = 1040;
		byteRows2[2] = 0;
		byteRows2[3] = 2176;
		byteRows2[4] = 263;
		byteRows2[5] = 25120;
		byteRows2[6] = 1024;
		byteRows2[7] = 2056;
		byteRows2[8] = 16;
		byteRows2[9] = 547;
		byteRows2[10] = 28736;
		byteRows2[11] = 136;
		byteRows2[12] = 0;
		byteRows2[13] = 1040;
		byteRows2[14] = 1040;

		assertTrue("One or more bumpers were not properly flipped : ",wasFlipGood( pmm2, byteRows2, pmm2.getSideLength(), pmm2.getSideLength() ));
		

		// Test British board's flipped bumpers.
		final PMModel pmm3 = boardFactory( ModelType.BRITISH );
		
		//BINARY MAP BOARD UNDER TEST
		//                   RowNumber
		//  ---------------- 
		//0: 000010000000000 1024
		//1: 101010110101010 21930
		//2: 000000000000000 0
		//3: 101010110101010 21930
		//4: 000000000100000 32
		//5: 101010101010110 21864
		//6: 000000100000000 256
		//7: 111010101010111 30039
		//8: 000000001000000 64
		//9: 011010101010101 13653
		//10:000001000000000 512
		//11:010101011010101 10965
		//12:000000000000000 0
		//13:010101011010101 10965
		//14:000000000010000 16

		final long[] byteRows3 = new long[ pmm2.getSideLength()];
		byteRows3[0] = 1024;
		byteRows3[1] = 21930;
		byteRows3[2] = 0;
		byteRows3[3] = 21930;
		byteRows3[4] = 32;
		byteRows3[5] = 21846;
		byteRows3[6] = 256;
		byteRows3[7] = 30039;
		byteRows3[8] = 64;
		byteRows3[9] = 13653;
		byteRows3[10] = 512;
		byteRows3[11] = 10965;
		byteRows3[12] = 0;
		byteRows3[13] = 10965;
		byteRows3[14] = 16;

		assertTrue("One or more bumpers were not properly flipped : ",wasFlipGood( pmm3, byteRows3, pmm3.getSideLength(), pmm3.getSideLength() ));
		
	}
	
	/**
	 * This function will take in a Board and an array of integers.  The integers represent
	 * the values that each row of the board should equal if the flipBumpers function 
	 * modified the board correctly.  The function returns true if the board matches the array
	 * and false otherwise.
	 * Preconditions:  The board must be set up 
	 * Postconditions: Whether the board was correctly set by the flipBumper function will be 
	 * 				evaluated.
	 */
	public boolean wasFlipGood( final PMModel pm, final long[] expectedRowAmt, final int aBoardRow, final int aBoardCol ) {
		boolean retVal = true;
		long[] rowResult = new long[ aBoardRow ];
		long rowManip = 0;
		long isOne = 0;
		
		for ( int row = 0; row < aBoardRow; row++ ) {                                        // Loop thru each row of board.
			
			isOne = 0;                                                                       // Reset isOne
			rowManip = 0;                                                                    // Reset value of Row currently being manipulated
			for ( int col = 0; col < aBoardCol; col++ ) {                                    // Loop thru row, getting number at the end.

				isOne = ( (( pm.getPlace_Created(new Position(row, col))) == 0 )? 0 : 1 );   // If this place corresponds with a bumper, it eqls 1

				if ( isOne == 1 ) {          // Place 1 at end
					rowManip |= 1;
				}
				
				rowManip <<= 1;              // Shift left once for next iteration

			}
			//System.out.println( rowManip );
			rowManip >>>= 1;                 // Unsigned right shift, to compensate for extra left shift at end
			rowResult[ row ] = rowManip;     // Put result in rowResult for this row.
			
		}
		
		for ( int itr = 0; itr < expectedRowAmt.length; itr++ ) {  // Loop through array of row amounts, see if we got similar results.
			
			//System.out.println("These should be equal  [" + expectedRowAmt[itr ] + "] --- [" + rowResult[itr] + "]\n" );
			
			if ( expectedRowAmt[ itr ] != rowResult[ itr ] ) {
				retVal = false;                                    // No match 
			}
		}
		
		return retVal;
	}
	
	
	
	/**
	 * Tests setCurr sets curr to 1st empty position in AR, if all filled set
	 * to 1st pos.  To be used when set up, and when user uses direction and Tab to get new
	 * region.  Not to be used by mouse clicks.
	 */
	@Test
	public void testSetCurr() {
		
		// Should return false becuase the board is not created yet.
		assertFalse(" The board is null, so setCurr should return false: ", _def_PM.setCurr(new Position()));
		
		// Board is setup and created.
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// This position is off the board
		assertFalse("This was an invalid position, should have returned false: ", _def_PM.setCurr(new Position(15, 15)));
		
		// Position entered is a bumper
		Position bumpPos = new Position(1,1);
		assertFalse("Bumpers are not valid positions, should have returned false: ", _def_PM.setCurr(bumpPos));
		
		// Position entered is valid for ModelType REGULAR
		Position vPos = new Position(0,0);
		assertTrue("Valid positions should return True: ", _def_PM.setCurr( vPos ));
		
	}
	
	/**
	 * Test for findStartRegion(), should find regions with specified orientation, and change
	 * the inputted Position to match the Region's startPosition.
	 */
	@Test
	public void testFintStartRegion() {
		int fiveK = 5000;
		Position badPos1 = new Position(1,1);
		_def_PM.findStartRegion(badPos1, true);
		
		// No Board
		assertFalse("This should return false; the board has not been set up : ", (badPos1.getPlace() < fiveK ) );
		
		// Create Board
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Invalid Position
		assertFalse("This should return false; the Position is NOT valid : ", (badPos1.getPlace() < fiveK ));
		
		// No Movement needed
		Position pos00 = new Position(0,0);
		_def_PM.findStartRegion(pos00, true);           // Horizontal
		assertTrue("This should have a place of 1 : ", (pos00.getPlace() == 1) );
		
		pos00.setPosition(0, 0);
		_def_PM.findStartRegion(pos00, false );         // Vertical
		assertTrue("This should have a place of 1 : ", (pos00.getPlace() == 1) );
		
		// Horizontal Movmt, NO Vertical Movmt
		Position pos01 = new Position(0,1);
		_def_PM.findStartRegion(pos01, true);           // Horizontal
		assertTrue("This should have a place of 1 : ", (pos01.getPlace() == 1) );
		
		pos01 = new Position(0,1);
		_def_PM.findStartRegion(pos01, false );         // Vertical
		assertTrue("This should not have moved becuase there is nowhere to go vertically : ", ( pos01.getPlace() == fiveK ));
		
		// Intersection, Horizontal, Vertical
		int hAns = 3;
		int vAns = 2;

		Position pos22 = new Position(2,2);
		_def_PM.findStartRegion(pos22, true);           // Horizontal
		assertTrue("This should be equal to 3 : ", ( pos22.getPlace() ==  hAns) );
		
		pos22.setPosition(2, 2);
		_def_PM.findStartRegion(pos22, false);          // Vertical
		assertTrue("This should be equal to " + vAns + " : ", ( pos22.getPlace() == vAns ) );
		
		// NO Horizontal Movmt, Vertical Movmt
		int vAns34 = 4;
		Position pos34 = new Position(3,4);
		_def_PM.findStartRegion(pos34, true);           // Horizontal
		assertTrue("This should not move, becuase it is constrained in the horizontal direction : ", ( pos34.getPlace() == fiveK ) );
		
		pos34.setPosition(3, 4);
		_def_PM.findStartRegion(pos34, false);          // Vertical
		assertTrue("This should be equal to hint #4 : ", (pos34.getPlace() == vAns34) );
		
		// Bottom corner
		int hAns44 = 5;
		int vAns44 = 4;

		Position pos44 = new Position(4,4);
		_def_PM.findStartRegion(pos44, true);           // Horizontal
		assertTrue("This should equal hitn #5", (pos44.getPlace() == hAns44) );
		
		pos44.setPosition(4, 4);
		_def_PM.findStartRegion(pos44, false);          // Vertical            
		assertTrue("This should equal hint #4 : ", (pos44.getPlace() == vAns44));
		
	}
	
	/**
	 * Tests whether all the start positions on this board have been found.  
	 * It should be able to find all the across and down start positions that 
	 * are created by the bumper pattern that was entered by the user.
	 * After the creation of the bumpers for the board, any open spaces that are 
	 * greater than minimum word size going from left to right or from top to bottom
	 * should be properly marked by a starting position.
	 * The board must have the bumpers already placed on it before this is called.
	 */
	@Test
	public void testFindStartPositions() {
		
		// Make the puzzle from the previous test available to all. So
		// we do not have to repeat the same thing here.
		boardSetupStart( _def_PM );
		
		// Create the bumpers
		Position pos1 = new Position(0, 3);
		Position pos2 = new Position(0, 4);
		Position pos3 = new Position(1, 1);
		Position pos4 = new Position(1, 3);
		Position pos5 = new Position(1, 4);
		
		// Create the positions for comparison
		Position start1 = new Position(0, 0, 1);
		Position start2= new Position(0, 2, 2);
		Position start3 = new Position(2, 0, 3);
		Position start4 =  new Position(2, 4, 4);
		Position start5 = new Position(4, 2, 5);
		
		Set<Position> startList = new TreeSet<Position>();
		startList.add(start1);
		startList.add(start2);
		startList.add(start3);
		startList.add(start4);
		startList.add(start5);
		
		_def_PM.flipBumper(pos1);
		_def_PM.flipBumper(pos2);
		_def_PM.flipBumper(pos3);
		_def_PM.flipBumper(pos4);
		_def_PM.flipBumper(pos5);
		
		Position[] startArr = {};
		startArr = startList.toArray( startArr );       // May not be correct
		
		// Count the number of positions that were returned.
		assertEquals("These should have the same length ", startList.size(), _def_PM.numberStartPositions().size());
		
		//  Make sure that they match the positions that you created at the top.
		boolean found = false;
		int numStarts = 5;
		Position[] retArr = {};
		retArr = _def_PM.getStartPositions().toArray( retArr );

		assertEquals("There should be 5 starting positions: ", numStarts, retArr.length );
		
		for ( int itr = 0; itr < startArr.length; itr++ ) {      // Find the matching positions int the returned list

			for ( int cnt = 0; cnt < retArr.length; cnt++ ) {
			
				if ( startArr[itr].equals(retArr[cnt])) {
					found = true;
				}

			}
			
			assertTrue("After the loop a match should be found", found );
			found = false;                                        // Reset boolean
			
		}
		
	}
	
	/**
	 * Test method for currNext(), a function may use output from this later for user's board navigation.
	 */
	@Test 
	public void testCurrNext() {
	
		// Make sure returns false for null boards
		assertFalse("Should return false, board is null: ", _def_PM.currNext() );
		
		_def_PM = boardFactory( ModelType.REGULAR );                 // Board is set
		
		// HORIZONTAL TESTING, must move Horizontal, and stop at the end of the board and at bumpers
		assertTrue("This move should be Horizontal, valid: ", _def_PM.currNext() );
		assertTrue("This move should be Horizontal, valid: ", _def_PM.currNext() );
				
		// Return false for invalid board space
		assertFalse("This move should be invalid because we should now be at a bumper: ", _def_PM.currNext() );
		
		Position borderPos = new Position(2,4);                           // Curr is set to last on board position
		_def_PM.setCurr( borderPos );
		
		// Return false for out of bounds positions
		assertFalse("Should return false becuase the board orientation is Horizontal, and we are at the end of the board: ", _def_PM.currNext() );
		
		// VERTICAL TESTING, must move Vertical, and stop at the end of the board and at bumpers.
		Position begPos = new Position();
		_def_PM.setCurr( begPos );                                  // Set Curr to top left.
		_def_PM.setOrientation(1);                                  // Set orientation to Vertical

		assertTrue("This move should be Vertical, valid: ", _def_PM.currNext() );
		assertTrue("This move should be Vertical, valid: ", _def_PM.currNext() );
				
		// Return false for invalid board space
		assertFalse("This move should be invalid because we should now be at a bumper: ", _def_PM.currNext() );
				
		// Return false for out of bounds positions
		Position borderPos2 = new Position(4,2);                           // Curr is set to last on board position
		_def_PM.setCurr( borderPos2 );
		assertFalse("Should return false becuase the board orientation is Horizontal, and we are at the end of the board: ", _def_PM.currNext() );
		
	}
	
	/**
	 * Test method for currPrev(), a function may use output from this later for user's board navigation.
	 */
	@Test 
	public void testCurrPrev() {
		
		// Make sure returns false for null boards
		Position pos = new Position(0,2);
		_def_PM.setCurr( pos );                                  // Set Curr space with 2 more spaces in front of it.
		assertFalse("Should return false, board is null: ", _def_PM.currPrev() );
		
		_def_PM = boardFactory( ModelType.REGULAR );                 // Board is set
		
		// HORIZONTAL TESTING, must move Horizontal, and stop at the end of the board and at bumpers
		Position endPos = new Position(0,2);
		_def_PM.setCurr(endPos);                                 // Set Curr space with 2 more spaces in front of it.
		assertTrue("This move should be Horizontal, valid: ", _def_PM.currPrev() );
		assertTrue("This move should be Horizontal, valid: ", _def_PM.currPrev() );
				
		// Return false for invalid board space
		assertFalse("This move should be invalid because we should now be at the edge of the board: ", _def_PM.currPrev() );
				
		// Return false for having a bumper previous to it.
		Position borderPos = new Position(4,2);                           // Curr is set to have a bumper previous to it.
		_def_PM.setCurr( borderPos );
		assertFalse("Should return false becuase the board orientation is Horizontal, and we are adjacent to a bumper: ", _def_PM.currPrev() );
		
		
		// VERTICAL TESTING, must move Vertical, and stop at the end of the board and at bumpers.
		Position begPos = new Position(2,0);
		_def_PM.setCurr( begPos );                                  // Set Curr to top left.
		_def_PM.setOrientation(1);                                  // Set orientation to Vertical
		assertTrue("This move should be Vertical, valid: ", _def_PM.currPrev() );
		assertTrue("This move should be Vertical, valid: ", _def_PM.currPrev() );
				
		// Return false for invalid board space
		assertFalse("This move should be invalid because we should now be at a bumper: ", _def_PM.currPrev() );
		
		// Return false for out of bounds positions
		Position borderPos2 = new Position(0,2);                           // Curr is set to last on board position
		_def_PM.setCurr( borderPos2 );
		assertFalse("Should return false becuase the board orientation is Horizontal, and we are at the end of the board: ", _def_PM.currPrev() );
		
	}
	
	/**
	 * Test method for writePosition()
	 */
	@Test
	public void testWriteMove() {
		final char letter = 'A';
		
		// Return false for NULL boards
		assertFalse("Function does not work for NULL boards: ", _def_PM.writeMove( letter ) );
		
		_def_PM = boardFactory( ModelType.REGULAR );

		// HORIZONTAL
		// Should be able to write to the current position.
		assertTrue("This should be a valid space: ", _def_PM.writeMove( letter ));

		// Should now be on the next position.
		Position okPos = new Position(0,1);
		assertTrue("Should be on board position [0,1]: ", _def_PM.getCurr().equals(okPos));
		
		// Should still be in Active Region
		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should be able to go twice more, then stop.
		assertTrue("This should be a valid space: ", _def_PM.writeMove( letter ));
		assertFalse("This should be a valid space: ",_def_PM.writeMove( letter ));
		
		Position fPos = new Position(0,2);
		assertEquals("This position should have been written to, even though it returned false: ", letter, _def_PM.getPlace_Created(fPos));
		
		assertFalse("Should not move because there is a bumper", _def_PM.writeMove(letter));

		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should not allow to go past boundary
		final Position borderPos = new Position(2,4);
		final ActiveRegion actRegion = new ActiveRegion( new Position(2, 0, 3), 5, 0);        // The position should correspond with the 3rd hint.
		_def_PM.setCurr(borderPos);                                                     // Set Curr to this position
		_def_PM.setActiveRegion(borderPos);                                             // Set the region so the writeMove will work.
		
		assertFalse("Should not allow to go past board boundary, but should still write : ", _def_PM.writeMove(letter));
		assertEquals( letter + " should have been written to " + borderPos + " : ", letter, _def_PM.getPlace_Created(borderPos));
		assertEquals("This should be the region that is occupied by the Active Region: ", actRegion, _def_PM.getActiveRegion());
		
		// VERTICAL
		_def_PM.setOrientation(1);                                                       // Orientation is now Vertical
		_def_PM.setActiveRegion(borderPos);                                              // Reset AR from borderPos's position, in the new orientation
		_def_PM.setCurr( borderPos);                                                     // Set curr to position within AR, should have a place of 4.

		char letter2 = 'B';
		
		// Should be able to write to the current position.
		assertTrue("This should be a valid space: ", _def_PM.writeMove( letter2 ));

		// Should now be on the next position.
		Position okPos2 = new Position(3,4);
		assertTrue("Should be on board position [3,4]: ", _def_PM.getCurr().equals(okPos2));
		
		// Should still be in Active Region
		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should be able to go twice more, then stop.
		assertTrue("This should be a valid move: ", _def_PM.writeMove( letter2 ));
		assertFalse("This should be an invalid move : ",_def_PM.writeMove( letter2 ));
		
		Position fPos2 = new Position(4, 4);
		assertEquals("This position should have been written to, even though it returned false: ", letter2, _def_PM.getPlace_Created(fPos2));
		
		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Check that bumpers still block when orientationis vertical.
		Position blockPos = new Position(2,0);
		Position startPos = new Position(1,0);
		ActiveRegion aReg = new ActiveRegion( new Position(0,0,1), 3, 1);
		char letter3 = 'C';
		
		_def_PM.setActiveRegion(blockPos);
		_def_PM.setRegionAndCurr(blockPos);                                    // Curr should be set to (1,0)

		// We should be in the first down region.
		assertTrue("We should be occupying this region : ", _def_PM.getActiveRegion().equals( aReg ));
		
		// Curr should be at 1,0
		assertTrue("Curr should be at (1,0) : ", _def_PM.getCurr().equals(startPos));
		
		assertTrue("Should have printed a letter to cell (1,0), and moved curr to (2,0) : ", _def_PM.writeMove(letter3));

		assertTrue("This should be at position (2,0): ", _def_PM.getCurr().equals(blockPos));

		assertFalse("Should print a letter to cell (2,0) and not be able to move any further: ", _def_PM.writeMove(letter3)  );
		assertFalse("Should print a letter at (2,0) and not be able to move: ", _def_PM.writeMove(letter2));

	}
	
	/**
	 * Test method for clearMove, that clears the current Position, then moves toward the beginning of the word.
	 */
	@Test
	public void testClearMove() {
		final char letter = 'A';
		
		assertFalse("Board not yet created : ", _def_PM.clearMove() );
		
		_def_PM = boardFactory( ModelType.REGULAR );

		// The first across region
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		Position pos2 = new Position(0,2);
		
		// Start hint 2's down region
		_def_PM.setOrientation(1);
		_def_PM.setRegionAndCurr( pos2 );

		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);

		// Start hint 5's region
		Position pos3 = new Position(4,2);
		_def_PM.setOrientation(0);
		_def_PM.setRegionAndCurr( pos3 ) ;        

		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		// Test and create functions that are to be used by Controller and View.
		
		Position curr1 = new Position(4,3);
		Position curr2 = new Position(3,2);
		Position curr3 = new Position(0,2);
		Position badPos = new Position(1,1);
		
		ActiveRegion aRegion1 = new ActiveRegion( new Position(4,2), 3, 0);
		ActiveRegion aRegion2 = new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion aRegion3 = new ActiveRegion( new Position(0,0), 3, 0);
		
		String ans1 = "  A";
		String ans2 = "     ";
		String ans3 = "   ";
		
		// Bottom Right HORIZONTAL
		_def_PM.setActiveRegion(curr1);                                            // Set Region
		_def_PM.setCurr(curr1);                                                    // Set Curr
		assertTrue("Curr can move so should return true: ", _def_PM.clearMove() );
		assertFalse("Curr cannot move, but should still erase : ", _def_PM.clearMove() );
		assertFalse("Curr cannot move, but should still erase : ", _def_PM.clearMove() );   // Extra, should still be false, and in the same place
		assertTrue("These positions should match : ", _def_PM.getCurr().match(new Position(4,2)));
		assertTrue("The string should be \'__A\' ", ans1.equals(_def_PM.stringInRegion(aRegion1)));
		
		// Mid VERTICAL
		_def_PM.setOrientation(1);                                                 // Set Orientation VERTICAL
		_def_PM.setActiveRegion(curr2);                                            // Set Region
		_def_PM.setCurr(curr2);                                                    // Set Curr
		assertTrue("Curr can move so should return true: ", _def_PM.clearMove() );
		assertTrue("Curr can move so should return true: ", _def_PM.clearMove() );
		assertTrue("Curr can move so should return true: ", _def_PM.clearMove() );
		assertFalse("Curr cannot move, but should still erase : ", _def_PM.clearMove() );
		assertFalse("Curr cannot move, but should still erase : ", _def_PM.clearMove() );  // Extra
		assertTrue("These positions should match : ", _def_PM.getCurr().match(new Position(0,2)));
		assertTrue("The string should be \'____\' ", ans2.equals(_def_PM.stringInRegion(aRegion2)));
		
		// Top HORIZONTAL
		_def_PM.setOrientation(0);                                                 // Set Orientation HORIZONTAL
		_def_PM.setActiveRegion(curr3);                                            // Set Region
		_def_PM.setCurr(curr3);                                                    // Set Curr
		assertTrue("Curr can move so should return true: ", _def_PM.clearMove() );
		assertTrue("Curr can move so should return true: ", _def_PM.clearMove() );
		assertFalse("Curr cannot move, but should still erase : ", _def_PM.clearMove() );
		assertTrue("These positions should match : ", _def_PM.getCurr().match(new Position(0,0)));
		assertTrue("The string should be \'___\' ", ans3.equals(_def_PM.stringInRegion(aRegion3)));
		
		// Test Curr out of region
		_def_PM.setCurr(curr1);                                                    // Set Curr outside of Region
		assertFalse("Curr cannot move, but should still erase : ", _def_PM.clearMove() );
		
		// Test Curr is invalid
		_def_PM.setCurr(badPos);                                                    // Set Curr outside of Region
		assertFalse("Curr cannot move, but should still erase : ", _def_PM.clearMove() );
		
	}
	
	
	/**
	 * Test method for getPlace
	 */
	
	/**
	 * Test for AR or for flipOrientInPlace, so that you cannot have AR in regions that are too small.  Look in notes.
	 * You can ONLY flip a region if the inputted Position is the matches the current position.
	 * Otherwise, AR does not change only current.
	 * Given a position will place an AR there is possible.  If there is already an AR there, it should flip it if ther 
	 * is any room to to so.  You cannot flip if there is not enough room for the minimum word length, AR should
	 * only be oriented in a way so that the valid region is hilited only.  When a board is accepted
	 * for play it should not contain segments that could only be invalid.
	 */
	@Test 
	public void testFlipInPlace() {
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Check position, AR Position, length and orientation.
		Position currPos1 = new Position();
		Position currPos2 = new Position(2,2);
		Position currPos3 = new Position(4,4);
		
		Position badPos = new Position(3,2);                                       // Should fail with VERT -> HORI flip    
				
		ActiveRegion aReg1 = new ActiveRegion( currPos1, 3, 0);                    // Top Left, Length 3, HORIZONTAL
		ActiveRegion aRegFlip1 = new ActiveRegion( currPos1, 3, 1);                // Top Left, Length 3, VERTICAL
		
		ActiveRegion aReg2 = new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion aRegFlip2 = new ActiveRegion( new Position(2,0), 5, 0);
		
		ActiveRegion aReg3 = new ActiveRegion( new Position(4,2), 3, 0);
		ActiveRegion aRegFlip3 = new ActiveRegion( new Position(2,4), 3, 1);
				
		// Flip Region starting from (0,0) 
		_def_PM.setActiveRegion( currPos1 );                                       // Set Curr and AR
		_def_PM.setCurr( currPos1 );
		assertTrue("This is a valid flip, should have returned true : ", _def_PM.flipInPlace());                                                     
		assertTrue("The position should not have changed : ", _def_PM.getCurr().equals( currPos1 ));
		assertTrue("These regions should be equal : ", _def_PM.getActiveRegion().match( aRegFlip1 ));
		assertTrue("The region should have flipped : ", ( (_def_PM.getOrientation() % 2) == 1 ));

		// Flip Region starting from (2,2)
		_def_PM.setActiveRegion( currPos2 );                                       // Set Curr and AR
		_def_PM.setCurr( currPos2 );
		assertTrue("This is a valid flip, should have returned true : ", _def_PM.flipInPlace());                                                     
		assertTrue("The position should not have changed : ", _def_PM.getCurr().equals( currPos2 ));
		assertTrue("These regions should be equal : ", _def_PM.getActiveRegion().match( aRegFlip2 ));
		assertTrue("The region should have flipped : ", ( (_def_PM.getOrientation() % 2) == 0 ));
		
		// Flip Region starting from (4,4)
		_def_PM.setActiveRegion( currPos3 );                                       // Set Curr and AR
		_def_PM.setCurr( currPos3 );
		assertTrue("This is a valid flip, should have returned true : ", _def_PM.flipInPlace());                                                     
		assertTrue("The position should not have changed : ", _def_PM.getCurr().equals( currPos3 ));
		assertTrue("These regions should be equal : ", _def_PM.getActiveRegion().match( aRegFlip3 ));
		assertTrue("The region should have flipped : ", ( (_def_PM.getOrientation() % 2) == 1 ));
		
		// Make sure does not work in spaces that are smaller than minimum word size.
		_def_PM.setActiveRegion( badPos );                                       // Set Curr and AR
		_def_PM.setCurr( badPos );
		assertFalse("This flip should not be valid, there is not enough room : ", _def_PM.flipInPlace() );                                                     
		assertTrue("The position should not have changed : ", _def_PM.getCurr().equals( badPos ));
		assertTrue("These regions should be equal : ", _def_PM.getActiveRegion().match( aReg2 ));
		assertTrue("The region should NOT have flipped : ", ( (_def_PM.getOrientation() % 2 ) == 1 ));
		
	}
	
	/**
	 * Test method for setActiveRegion()
	 */
	@Test
	public void testSetActiveRegion() {
		ActiveRegion horReg1 = new ActiveRegion( new Position(0,0), 3, 0);          // Horizontal
		ActiveRegion horReg2 = new ActiveRegion( new Position(2,0), 5, 0);
		ActiveRegion horReg3 = new ActiveRegion( new Position(4,2), 3, 0);
		
		ActiveRegion verReg1 = new ActiveRegion( new Position(0,0), 3, 1);          // Vertical
		ActiveRegion verReg2 = new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion verReg3 = new ActiveRegion( new Position(2,4), 3, 1);
		
		Position upLeftPos = new Position(0,0);
		Position midPos = new Position(2,2);
		Position botRightPos = new Position(4,4);
		Position badPos = new Position(1,1);
		Position offPos = new Position(5,5);
		
		// Board is NULL
		assertFalse("Should not pass, the board is NULL : ", _def_PM.setActiveRegion(midPos));
		
		// Invalid Positions
		assertFalse("Position is invalid, should be false: ", _def_PM.setActiveRegion( badPos ));
		assertFalse("Position is off the board, false : ", _def_PM.setActiveRegion( offPos ));
		
		// SET  UP BOARD
		_def_PM = boardFactory( ModelType.REGULAR );
	
		// Test HORIZONTAL
		_def_PM.setActiveRegion( upLeftPos );
		assertTrue("These regions should match : ", _def_PM.getActiveRegion().match(horReg1));
		
		_def_PM.setActiveRegion( midPos );
		assertTrue("These regions should match : ", _def_PM.getActiveRegion().match(horReg2));		
		
		_def_PM.setActiveRegion( botRightPos );
		assertTrue("These regions should match : ", _def_PM.getActiveRegion().match(horReg3));
		
		// Test VERTICAL
		_def_PM.setOrientation(1) ;                                                // Orientation is VERTICAL
		_def_PM.setActiveRegion( upLeftPos );
		assertTrue("These regions should match : ", _def_PM.getActiveRegion().match(verReg1));
		
		_def_PM.setActiveRegion( midPos );
		assertTrue("These regions should match : ", _def_PM.getActiveRegion().match(verReg2));		
		
		_def_PM.setActiveRegion( botRightPos );
		assertTrue("These regions should match : ", _def_PM.getActiveRegion().match(verReg3));
				
	}
	
	/**
	 * Tests isHorizontal()
	 */
	@Test
	public void testIsHorizontal() {
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Test Horizontal
		assertTrue("Should be Horizontal : ", _def_PM.isHorizontal() );
		
		// Test Vertical
		_def_PM.setOrientation(1);
		assertFalse("Should be Vertical : ", _def_PM.isHorizontal());
	}
	
	/**
	 * Tests isCurrInRegion.  Test for in Curr in region, out of region, and without board
	 */
	@Test
	public void testIsCurrInRegion() {
		
		// There is no board
		assertFalse("There is no board to run this on. : ", _def_PM.isCurrInRegion() );
		
		// CREATE BOARD
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Board is in Region
		Position inPos = new Position(4,3);
		Position outPos = new Position(2,0);
		
		// In bounds test
		_def_PM.setCurr(inPos);
		_def_PM.setActiveRegion( new Position(4,2));
		assertTrue("This should be in bounds : ", _def_PM.isCurrInRegion());
		
		// Out bounds test
		_def_PM.setCurr(outPos);
		assertFalse("This is out of bounds : ", _def_PM.isCurrInRegion());
	
		// Test on new board
		_def_PM = boardFactory( ModelType.JAPANESE );
		
		// Test for different regions in same orientation.
		Position goodPos = new Position(0,1);
		Position badPos = new Position(0,5);
		
		// Test to see if knows it is in region.
		assertTrue("This should be in the current region : ", _def_PM.isCurrInRegion()) ;
		_def_PM.setCurr(goodPos);
		assertTrue("This also is in the current region: ", _def_PM.isCurrInRegion());
		
		// Test to see that it does not say true when curr is in different regions on the same axis.
		_def_PM.setCurr(badPos);
		assertFalse("This should not be true : ", _def_PM.isCurrInRegion() );
		
		// Test vertically
		Position okPosition1 = new Position(1,6);
		Position okPosition2 = new Position(5,6);
		Position okPosition3 = new Position(7,6);
		
		// Testing in row 6
		_def_PM.flipOrientation();                     // Orientation now vertical
		_def_PM.setRegionAndCurr(okPosition1);         
		
		// Using the 1st region in this column
		assertTrue("This should be true : ", _def_PM.isCurrInRegion() );
		
		_def_PM.setCurr(okPosition2);
		assertFalse("This should be false, Position is not in the same region : ", _def_PM.isCurrInRegion());
		
		_def_PM.setCurr(okPosition3);
		assertFalse("This should be false, Position is not in the same region : ", _def_PM.isCurrInRegion());
		
		// Using the 2nd region in this column.
		_def_PM.setRegionAndCurr(okPosition2);
		assertTrue("This should be true : ", _def_PM.isCurrInRegion() );
		
		_def_PM.setCurr(okPosition1);
		assertFalse("This should be false, Position is not in the same region : ", _def_PM.isCurrInRegion());
		
		_def_PM.setCurr(okPosition3);
		assertFalse("This should be false, Position is not in the same region : ", _def_PM.isCurrInRegion());
		
	}
	
	/**
	 * Test isPositionInBounds().  
	 */
	@Test
	public void testIsPositionInBounds() {
		Position inPos= new Position();
		Position outPos = new Position(5,5);
		
		// There is no board
		assertFalse("The board does not exists : ", _def_PM.isPositionInBounds(new Position()));
		
		// CREATE BOARD
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// In bounds
		assertTrue("This should be in bounds : ", _def_PM.isPositionInBounds(inPos));
		
		// Out bounds
		assertFalse("This should be out of bounds : ", _def_PM.isPositionInBounds(outPos));
		
	}
	
	/**
	 * Test isValidSpace()
	 */
	@Test
	public void testIsValidSpace() {
		Position inPos= new Position();
		Position outPos = new Position(5,5);
		Position bumPos = new Position(1,1);
		
		
		// There is no board
		assertFalse("The board does not exists : ", _def_PM.isValidSpace(new Position()));
		
		// CREATE BOARD
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// In bounds
		assertTrue("This should be in bounds : ", _def_PM.isValidSpace(inPos));
		
		// Out bounds
		assertFalse("This should be out of bounds : ", _def_PM.isValidSpace(outPos));
		
		// Bumper
		assertFalse("This posiition is a bumper : ", _def_PM.isValidSpace( bumPos ));
		
	}
	
	/**
	 * Test skipWalls()
	 */
	@Test
	public void testSkipWalls() {
		
		// Board not created yet.
		assertFalse("Board not yet created : ", ( _def_PM.skipWalls(new Position(), false) != null ) );
		
		// Create Board
		_def_PM = boardFactory( ModelType.REGULAR );
		Position currPos = new Position();
		Position bumperPos = new Position(1,1);
		Position currPos2 = new Position(1,2);
		
		Position vertPos1 = new Position(0,1);
		Position vertPos2 = new Position(2,1);
		Position invalidPos = new Position(5,5);
		
		// Should return the same value
		assertTrue("We should get the same Position back : ", _def_PM.skipWalls(currPos, true).equals(currPos));
		
		// Should skip the bumper
		assertTrue("We should get the same Position back : ", _def_PM.skipWalls(bumperPos, true).equals(currPos2));

		// Should return the same value
		assertTrue("We should get the same Position back : ", _def_PM.skipWalls(vertPos1, false).equals(vertPos1));
		
		// Should skip the bumper
		assertTrue("We should get the same Position back : ", _def_PM.skipWalls(bumperPos, false).equals(vertPos2));

		// Invalid position
		assertFalse("This is not a valid position : ", ( _def_PM.skipWalls(invalidPos, false) != null ) );
		
	}
	
	/**
	 * Tests findRegionLength()
	 */
	@Test
	public void testRegionLength() {
		
		// Board not created yet.
		assertFalse("Board not yet created : ", ( _def_PM.skipWalls(new Position(), false) != null ) );
		
		// Create Board
		_def_PM = boardFactory( ModelType.REGULAR );
		Position horPos1 = new Position() ;
		Position horPos2= new Position(2,0);
		Position vertPos1= new Position(0,2);
		Position vertPos2 = new Position();
		
		// Horizontal should have length 3
		assertTrue("This should have a length of 3: ", (_def_PM.findRegionLength(horPos1) == 3 ));
		
		// Horizontal should have length 5
		assertTrue("This should have a length of 5: ", (_def_PM.findRegionLength(horPos2) == 5 ));
		
		// Vertical should have length 5
		_def_PM.setOrientation(1);
		assertTrue("This should have a length of 5: ", (_def_PM.findRegionLength(vertPos1) == 5 ));
		
		// Vertical should have length 3
		assertTrue("This should have a length of 3: ", (_def_PM.findRegionLength(vertPos2) == 3 ));
		
	}
	
	/**
	 * Test setRegionAndCurr() sets curr to 1st empty position in AR, if all filled set
	 * to 1st pos.  To be used when set up, and when user uses direction and Tab to get new
	 * region.  Not to be used by mouse clicks.
	 */
	@Test
	public void testSetRegionAndCurr() {
		char letter = 'A';
		
		// Create Board
		_def_PM = boardFactory( ModelType.REGULAR );

		
		// The first across region
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		Position pos2 = new Position(0,2);
		
		// Start hint 2's down region
		_def_PM.setOrientation(1);
		_def_PM.setRegionAndCurr( pos2 );

		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		// Start hint 5's region
		Position pos3 = new Position(4,2);
		_def_PM.setOrientation(0);
		_def_PM.setRegionAndCurr( pos3 ) ;        

		_def_PM.currNext();
		_def_PM.writeMove(letter);
		
		// Start hint 3 region
		Position pos4 = new Position(2,0);
		_def_PM.setRegionAndCurr( pos4 ) ;   
		
		_def_PM.currNext();
		_def_PM.currNext();
		_def_PM.currNext();
		_def_PM.writeMove(letter);
		
		// HORIZONTAL: Full Row Curr should be (0,0)
		Position h1APos = new Position();
		_def_PM.setRegionAndCurr( new Position(0,2) );
		assertTrue("Curr should be position (0,0) : ", _def_PM.getCurr().match( h1APos ));
		
		// Starts empty row, should be (2,0)
		Position h3APos = new Position(2,0);
		_def_PM.setRegionAndCurr( new Position(2,4) );
		assertTrue("Curr should be position (2,0) : ", _def_PM.getCurr().match( h3APos ));
		
		// Starts 1 empty space, should be (4,2)
		Position h5APos = new Position(4,2);
		_def_PM.setRegionAndCurr( new Position(4,3) );
		assertTrue("Curr should be position (4,2) : ", _def_PM.getCurr().match( h5APos ));
		
		// VERTICAL: Starts full, then empty.  Should be (1,0)
		_def_PM.setOrientation(1);                                        // Vertical
		Position h1DPos = new Position(1,0);
		_def_PM.setRegionAndCurr( new Position(2,0) );
		assertTrue("Curr should be position (1,0) : ", _def_PM.getCurr().match( h1DPos ));
		
		// Starts full, then empty.  Should be (3,2)
		Position h2DPos = new Position(3,2);
		_def_PM.setRegionAndCurr( new Position(1,2) );
		assertTrue("Curr should be position (3,2) : ", _def_PM.getCurr().match( h2DPos ));
		
		// Starts full, then empty.  Should be (2,4)
		Position h4DPos = new Position(2,4);
		_def_PM.setRegionAndCurr( new Position(3,4) );
		assertTrue("Curr should be position (2,4) : ", _def_PM.getCurr().match( h4DPos ));

	}
	
	/**
	 * Test stringInRegion().  Tests for no board, various strings with and without holes in them.
	 */
	@Test
	public void testStringInRegion() {
		String noStr = "\0\0\0";
		String emptyStr = "   ";
		String oneStr = "  A  ";
		String fullStr = "AAA";
		String endStr = "  AAA";
		String begStr = "A  ";
		
		char letter = 'A';
		
		ActiveRegion topFlat = new ActiveRegion(new Position(0,0), 3, 0);
		ActiveRegion midUp = new ActiveRegion(new Position(2,0), 5, 0);
		ActiveRegion botFlat = new ActiveRegion(new Position(4,2), 3, 0);
		
		// Should return a String with only end chars in it.
		assertEquals("The string should be empty becuase the board has not been created : ", noStr, _def_PM.stringInRegion( topFlat ));
		
		// Create Board
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Should return a string with 3 spaces
		assertEquals("The string should only have 3 spaces : ", emptyStr, _def_PM.stringInRegion( topFlat ) );
		
		// The first across region
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		Position pos2 = new Position(0,2);
		
		// Start hint 2's down region
		_def_PM.setOrientation(1);
		_def_PM.setRegionAndCurr( pos2 );

		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);

		// Vertical, there should be only one letter.
		assertEquals("There should only be one letter in the string : ", oneStr, _def_PM.stringInRegion(midUp));
		
		// Start hint 5's region
		Position pos3 = new Position(4,2);
		_def_PM.setOrientation(0);
		_def_PM.setRegionAndCurr( pos3 ) ;        

		// Horizontal, there should be only one letter at the beginning of the string.
		assertEquals("There should only be one letter in the string : ", begStr, _def_PM.stringInRegion(botFlat));

		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		// Horizontal, there should be three A's
		assertEquals("There should only be one letter in the string : ", fullStr, _def_PM.stringInRegion(botFlat));
		
		// Write on the middle Horizontal Region, 2 A's
		Position pos4 = new Position(2,0);
		_def_PM.setRegionAndCurr( pos4 ) ;  
		Position midPos  = new Position(2,3);
		_def_PM.setCurr( midPos );
		
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		// There should be 3 A's in the middle row.
		assertEquals("There should only be one letter in the string : ", endStr, _def_PM.stringInRegion(midUp));
		
	}
	
	/**
	 * Tests firstSpaceInRegion
	 */
	@Test
	public void testFirstTestInRegion() {
		char letter = 'A';
		Position invalidPos = new Position(-1,-1);
		
		assertTrue("This should return an invalid position, the board is not ready : ", invalidPos.match( _def_PM.firstSpaceInRegion(new ActiveRegion(new Position(), 3, 0))));
		
		// Create Board
		_def_PM = boardFactory( ModelType.REGULAR );

		
		// The first across region
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		Position pos2 = new Position(0,2);
		
		// Start hint 2's down region
		_def_PM.setOrientation(1);
		_def_PM.setRegionAndCurr( pos2 );

		_def_PM.writeMove(letter);
		_def_PM.writeMove(letter);
		
		// Start hint 5's region
		Position pos3 = new Position(4,2);
		_def_PM.setOrientation(0);
		_def_PM.setRegionAndCurr( pos3 ) ;        

		_def_PM.currNext();
		_def_PM.writeMove(letter);
		
		// Start hint 3 region
		Position pos4 = new Position(2,0);
		_def_PM.setRegionAndCurr( pos4 ) ;   
		
		_def_PM.currNext();
		_def_PM.currNext();
		_def_PM.currNext();
		_def_PM.writeMove(letter);
		
		// HORIZONTAL: Full Row should return an invalid Position
		ActiveRegion h1AReg = new ActiveRegion( new Position(), 3, 0);
		Position pValu = _def_PM.firstSpaceInRegion(h1AReg);
		int row = pValu.getRow();
		int col = pValu.getCol();
		assertTrue("Curr should be position (0,0) : ", ( ( row == -1) || ( col == -1) ));
		
		// Starts empty row, should be (2,0)
		ActiveRegion h3AReg = new ActiveRegion( new Position(2,0), 5, 0);
		Position pValu2 = _def_PM.firstSpaceInRegion(h3AReg);
		int row2 = pValu2.getRow();
		int col2 = pValu2.getCol();
		assertTrue("Curr should be position (2,0) : ", ( ( row2 == 2) && ( col2 == 0) ));
		
		// Starts 1 empty space, should be (4,2)
		ActiveRegion h5AReg = new ActiveRegion( new Position(4,2), 3, 0);
		Position pValu3 = _def_PM.firstSpaceInRegion(h5AReg);
		int row3 = pValu3.getRow();
		int col3 = pValu3.getCol();
		assertTrue("Curr should be position (4,2) : ", ( ( row3 == 4) && ( col3 == 2) ));
		
		// VERTICAL: Starts full, then empty.  Should be (1,0)
		_def_PM.setOrientation(1);                                        // Vertical
		ActiveRegion h1DReg = new ActiveRegion( new Position(), 3, 1);
		Position pValu4 = _def_PM.firstSpaceInRegion(h1DReg);
		int row4 = pValu4.getRow();
		int col4 = pValu4.getCol();
		assertTrue("Curr should be position (1,0) : ", ( ( row4 == 1) && ( col4 == 0) ));
		
		// Starts full, then empty.  Should be (3,2)
		ActiveRegion h2DReg = new ActiveRegion( new Position(0,2), 5, 1);
		Position pValu5 = _def_PM.firstSpaceInRegion(h2DReg);
		int row5 = pValu5.getRow();
		int col5 = pValu5.getCol();
		assertTrue("Curr should be position (3,2) : ", ( ( row5 == 3) && ( col5 == 2) ));
		
		// Starts full, then empty.  Should be (2,4)
		ActiveRegion h4DReg = new ActiveRegion( new Position(2,4), 3, 1);
		Position pValu6 = _def_PM.firstSpaceInRegion(h4DReg);
		int row6 = pValu6.getRow();
		int col6 = pValu6.getCol();
		assertTrue("Curr should be position (2,4) : ", ( ( row6 == 2) && ( col6 == 4) ));
		
	}
	
	/**
	 * Test setPosToRegionStart()
	 */
	@Test
	public void testSetPosToRegionStart() {
		
		Position badPos = new Position(0,1);
		Position invalidPos = new Position(5,5);
		
		// Board not created
		_def_PM.setPosToRegionStart(badPos);
		assertTrue("There is no board, so should return the same value: ", badPos.match(new Position(0,1)));
		
		// Create Board
		_def_PM = boardFactory( ModelType.REGULAR );

		// Invalid Position entered
		_def_PM.setPosToRegionStart(invalidPos);
		assertTrue("There is an invalid value, so should return the same value: ", invalidPos.match(invalidPos));
		
		// HORIZONTAL, Position would only make a 1 width word
		Position awkwardPos = new Position(1,2);
		_def_PM.setPosToRegionStart(awkwardPos);
		assertTrue("There is a 1 space length value, so should return the same value: ", awkwardPos.match(awkwardPos));		
		
		// This is in the middle of the board, Horizontal
		Position longPos = new Position(2,4);
		_def_PM.setPosToRegionStart(longPos);
		assertTrue("This should return value (2,0): ", longPos.match(new Position(2,0)));
		
		// VERTICAL
		_def_PM.setOrientation(1);                                                // VERTICAL
		Position longUpPos = new Position(3,2);
		_def_PM.setPosToRegionStart(longUpPos);
		assertTrue("This should return value (0,2): ", longUpPos.match(new Position(0,2)));
		
	}
	
	/**
	 * Test setPosToRegionStart()
	 */
	@Test
	public void testSetPosToRegionStart2() {
		
		Position badPos = new Position(0,1);
		Position invalidPos = new Position(5,5);
		
		// Board not created
		_def_PM.setPosToRegionStart(badPos, true );
		assertTrue("There is no board, so should return the same value: ", badPos.match(new Position(0,1)));
		
		// Create Board
		_def_PM = boardFactory( ModelType.REGULAR );

		// Invalid Position entered
		_def_PM.setPosToRegionStart(invalidPos, false );
		assertTrue("There is an invalid value, so should return the same value: ", invalidPos.match(invalidPos));
		
		// HORIZONTAL, Position would only make a 1 width word
		Position awkwardPos = new Position(1,2);
		_def_PM.setPosToRegionStart(awkwardPos, true );
		assertTrue("There is a 1 space length value, so should return the same value: ", awkwardPos.match(awkwardPos));		
		
		// This is in the middle of the board, Horizontal
		Position longPos = new Position(2,4);
		_def_PM.setPosToRegionStart(longPos, true );
		assertTrue("This should return value (2,0): ", longPos.match(new Position(2,0)));
		
		// VERTICAL
		//_def_PM.setOrientation(1);                                                // VERTICAL
		Position longUpPos = new Position(3,2);
		_def_PM.setPosToRegionStart(longUpPos, false );
		assertTrue("This should return value (0,2): ", longUpPos.match(new Position(0,2)));
		
	}
	
	/**
	 * Test method for isSetup
	 */
	@Test
	public void testIsSetup() {
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Test that the game board says that it is finished setting up.
		assertTrue("It should say that the board has finished setting up : ", _def_PM.isSetup());
		
	}
	
	/**
	 * Test method for userClick().
	 * * USERCLICK Sets Current to the inputted Position if move is valid.  If inputted Position is
	 * not in Region, new Region is set.  If inputted Position is same as Current, Orientation of 
	 * board is reset, and Region is reset.  If inputted Position is in Region and not equal to 
	 * Current, then Current is repositioned.
	 * Preconditions:  The Board Must be Setup befor use.
	 * 				To be used when the user clicks on a new Position on the board.
	 * 				Inputted Position should not be null.
	 * Postconditions: The Position that was clicked on by the user will be the new Current Position.
	 * 				The Region will be reset if necessary so that it contains the Current Position and
	 * 				will be flipped if the User clicked a Position that corresponds to Current.
	 * 				Notifications may be sent if appropriate.
	 * @return aPos The new position entered by the user.
	 */
	@Test
	public void testUserClick() {
		
		// Test no board
		assertFalse("There is no board to work on : ", _def_PM.userClick( new Position()));
		
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Test invalid off of board
		assertFalse("This is an invalid Position : ", _def_PM.userClick( new Position(5,5)));
		
		// Test invalid Bumper position
		assertFalse("This Position represents a bumper, and is invalid : ", _def_PM.userClick(new Position(1,1)));
		
		Position pos1 = new Position(0,1);
		Position pos2 = new Position(0,2);
		Position pos3 = new Position(2,2);
		Position pos4 = new Position(4,3);
		
		ActiveRegion aRegion1 = new ActiveRegion( new Position(0,0), 3, 0);
		ActiveRegion aRegion2 = new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion aRegion3 = new ActiveRegion( new Position(2,0), 5, 0);
		ActiveRegion aRegion4 = new ActiveRegion( new Position(4,2), 3, 0);
		
		// Test in Region
		assertTrue("This should just move curr and be OK : ", _def_PM.userClick( pos1 ) );
		//System.out.println( _def_PM.printBoardStates());
		assertTrue("This should just move curr and be OK : ", _def_PM.userClick( pos2 ) );
		assertTrue("This should match the preselected Region : ", _def_PM.getActiveRegion().match(aRegion1));
		//System.out.println( _def_PM.printBoardStates());		
		
		// Test same place, flip
		assertTrue("This should have flipped the orientation, and kept the same Position : ", _def_PM.userClick( pos2 ));
		assertTrue("This should have flipped the orientation to VERTICAL : ", ( (_def_PM.getOrientation() % 2) == 1));
		assertTrue("The Position should have remained the same : ", _def_PM.getCurr().match( pos2 ));
		assertTrue("This should match the preselected Region : ", _def_PM.getActiveRegion().match(aRegion2));
		//System.out.println( _def_PM.printBoardStates());
		assertTrue("The Position is in the region so should remain the same, but with new Current : ", _def_PM.userClick( pos3 ));
		assertTrue("This should have left the orientation to VERTICAL : ", ( ! (_def_PM.isHorizontal()) ));
		assertTrue("The Position should have remained the same : ", _def_PM.getCurr().match( pos3 ));
		assertTrue("The Region should have stayed the same : ", _def_PM.getActiveRegion().match(aRegion2));
		//System.out.println( _def_PM.printBoardStates());
		
		// An in region flip to HORIZONTAL
		assertTrue("The Region should flip becuase the same position is being clicked again : ", _def_PM.userClick( pos3 ) );
		assertTrue("The orientation now should be HORIZONTAL : ", _def_PM.isHorizontal() );
		assertTrue("The Position should have remained the same : ", _def_PM.getCurr().match( pos3 ) );
		assertTrue("The Region should have flipped around the Position inputted into userClick() : ", _def_PM.getActiveRegion().match(aRegion3));
		//System.out.println( _def_PM.printBoardStates());
		
		// Test out of Region
		assertTrue("This should be true, and create a new region at the bottom of the board : ", _def_PM.userClick( pos4 ));
		assertTrue("The Position should have remained the same as inputted : ", _def_PM.getCurr().match( pos4 ) );
		assertTrue("The Region should have changed : ", _def_PM.getActiveRegion().match(aRegion4));
		//System.out.println( _def_PM.printBoardStates());	
		
		// Now test with a British board to show when you click on a space that is constrained in 
		// the current orientation, orientation will be changed, and the you can change regions in 
		// the same orientation.
		_def_PM = boardFactory( ModelType.BRITISH );
		
		Position clickPosRegX1 = new Position(0,1);
		Position clickPosRegX2 = new Position(0,10);
		Position clickPosVert = new Position(1,14);
		Position clickPosHori = new Position(8,14);
		
		ActiveRegion regionX1 = new ActiveRegion( new Position(), 4, 0);
		ActiveRegion regionX2 = new ActiveRegion( new Position(0,5), 10, 0);
		ActiveRegion regionY1 = new ActiveRegion( new Position(0,14), 7, 1);
		ActiveRegion regionX3 = new ActiveRegion( new Position(8,9), 6, 0);
		
		// Click 1st region
		_def_PM.userClick(clickPosRegX1);
		assertTrue("Current should equal what was pointed to by the user : ", _def_PM.getCurr().match(clickPosRegX1));
		assertTrue("This should have been the correct region : ", _def_PM.getActiveRegion().match(regionX1));
		assertFalse("This is not in the current region, it should have returned false : ", _def_PM.getActiveRegion().match(regionX2));
		
		// Click 2nd region
		_def_PM.userClick(clickPosRegX2);
		assertTrue("Current should equal what was pointed to by the user : ", _def_PM.getCurr().match(clickPosRegX2));
		assertTrue("This should have been the correct region : ", _def_PM.getActiveRegion().match(regionX2));
		assertFalse("This is not in the current region, it should have returned false : ", _def_PM.getActiveRegion().match(regionX1));
		
		// Click 1st region
		_def_PM.userClick(clickPosRegX1);
		assertTrue("Current should equal what was pointed to by the user : ", _def_PM.getCurr().match(clickPosRegX1));
		assertTrue("This should have been the correct region : ", _def_PM.getActiveRegion().match(regionX1));
		assertFalse("This is not in the current region, it should have returned false : ", _def_PM.getActiveRegion().match(regionX2));
		
		// Test Region flip for pointing at constrained Vertical place[1,14].
		_def_PM.userClick(clickPosVert);
		assertTrue("Current should equal what was pointed to by the user : ", _def_PM.getCurr().match(clickPosVert));
		assertTrue("This should have been the correct region : ", _def_PM.getActiveRegion().match(regionY1));
		
		// This flip should be horizontal using constrained region
		_def_PM.userClick(clickPosHori);
		assertTrue("Current should equal what was pointed to by the user : ", _def_PM.getCurr().match(clickPosHori));
		assertTrue("This should have been the correct region : ", _def_PM.getActiveRegion().match(regionX3));

	}
	
	/**
	 * CLUESETREGSTART Sets Current to the inputted position if move is valid with the current orientation.  
	 * If inputted Position is
	 * not in Region, new Region is set.  If inputted Position is in Region and not equal to 
	 * Current, then Current is repositioned.
	 * Preconditions:  The Board Must be Setup befor use.
	 * 				To be used when the user clicks on a new Position on the board.
	 * 				Inputted Position should not be null.
	 * Postconditions: The Position that was clicked on by the user will be the new Current Position.
	 * 				The Region will be reset if necessary so that it contains the Current Position and matches the orientation
	 * 				that was inputted into the function.
	 * 				Notifications may be sent if appropriate.
	 * @return aPos The new position entered by the user.
	 */
	@Test
	public void testClueSetRegStart() {
		
		// Test no board
		assertFalse("There is no board to work on : ", _def_PM.clueSetRegStart( new Position(), true));
		
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Test invalid off of board
		assertFalse("This is an invalid Position : ", _def_PM.clueSetRegStart( new Position(5,5), true));
		
		// Test invalid Bumper position
		assertFalse("This Position represents a bumper, and is invalid : ", _def_PM.clueSetRegStart(new Position(1,1), true));
		
		Position pos1 = new Position(0,1);
		Position pos2 = new Position(0,2);
		Position pos3 = new Position(2,2);
		Position pos4 = new Position(4,3);
		
		ActiveRegion aRegion1 = new ActiveRegion( new Position(0,0), 3, 0);
		ActiveRegion aRegion2 = new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion aRegion3 = new ActiveRegion( new Position(2,0), 5, 0);
		ActiveRegion aRegion4 = new ActiveRegion( new Position(4,2), 3, 0);
		
		// Testing Horizontal in same region as Active Region
		assertTrue("This should return true : ", _def_PM.clueSetRegStart(new Position(), true ));
		assertTrue("Curr was not moved, so should have stayed int the same place : ", _def_PM.getCurr().match(new Position()));
		assertTrue("This should leave the Region and Current untouched : ", _def_PM.getActiveRegion().match(aRegion1));
		
		assertTrue("This should return true : ", _def_PM.clueSetRegStart(new Position(), true ));
		assertTrue("Curr was not moved, so should have stayed int the same place : ", _def_PM.getCurr().match(new Position()));
		assertTrue("This should still leave the Region and Current untouched : ", _def_PM.getActiveRegion().match(aRegion1));
		
		// Test a Position that has both Across and Down.  VERTICAL
		ActiveRegion vertRegion1 = new ActiveRegion( new Position(0,0), 3, 1);
		assertTrue("The board should now reflect a vertical region starting at (0,0) : ", _def_PM.clueSetRegStart(new Position(0,0), false));
		assertTrue("Current should remain the same : ", _def_PM.getCurr().match(new Position(0,0)));
		assertTrue("The Region should match : ", _def_PM.getActiveRegion().match(vertRegion1));
		
		// Change orientation of input again HORIZONTAL and see if we end up w/ correct regions and current.
		Position hPos2 = new Position(2,0);
		assertTrue("This should be allowed : ", _def_PM.clueSetRegStart( hPos2, true));
		assertTrue("Current should remain the same : ", _def_PM.getCurr().match( hPos2));
		assertTrue("This should be the new Region " + aRegion3 + " : ", _def_PM.getActiveRegion().match(aRegion3));
		
		// Change orientation of input again to VERTICAL and see if we end up w/ correct regions and current.
		Position vPos3 = new Position(2,4);
		ActiveRegion vRegion3 = new ActiveRegion( vPos3, 3 ,1);
		assertTrue("This should be allowed : ", _def_PM.clueSetRegStart(vPos3, false));
		assertTrue("Current should be " + vPos3 + ": ", _def_PM.getCurr().match(vPos3));
		assertTrue("Active Region should be " + vRegion3 + ": ", _def_PM.getActiveRegion().match(vRegion3));
		
		// Change orientaion to HORIZONTAL
		Position hPos1 = new Position(0,0);
		assertTrue("This should return true : ", _def_PM.clueSetRegStart( hPos1, true ));
		assertTrue("Curr was not moved, so should have stayed int the same place : ", _def_PM.getCurr().match(hPos1));
		assertTrue("This should still leave the Region and Current untouched : ", _def_PM.getActiveRegion().match(aRegion1));
		
	}
	
	/**
	 * Test for boolean userTabEnter( Position )
	 * When the user presses a tab/enter, this function should go.
	 * It will go to the next Hint in the list of Across or Down hints depending on the orientation
	 * of the board.  When one list is done the orientation is flipped and the other list is used.
	 * Regions will move to the appropriate area, but Current will go to the 1st open area in a region,
	 * or if the region is filled, it will default to the 0th index in the Region.  
	 * This function should only be called if the board has already been set up.  If the board is 
	 * null it should do nothing.  If the board has not been set up and this is run action is undefined.
	 */
	@Test
	public void testUserTabEnter() {
		ActiveRegion hTopRow = new ActiveRegion( new Position(), 3, 0);
		ActiveRegion hMidRow = new ActiveRegion( new Position(2,0), 5, 0);
		ActiveRegion hBotRow = new ActiveRegion( new Position(4,2), 3, 0);
		
		ActiveRegion vFirstCol= new ActiveRegion( new Position(0,0), 3, 1);
		ActiveRegion vMidCol= new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion vLastCol= new ActiveRegion( new Position(2,4), 3, 1);
		
		// Should not work for NULL
		_def_PM.userTabEnter();
		assertFalse("Cannot move board not set up yet : ", _def_PM.getActiveRegion().match(hMidRow));
		
		// CREATE BOARD
		_def_PM = this.boardFactory( ModelType.REGULAR );
		
		// Go through across
		_def_PM.userTabEnter();
		assertTrue("Should have moved to the second hint on the board : ", _def_PM.getActiveRegion().match(hMidRow));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		_def_PM.userTabEnter();
		assertTrue("Should have moved to the third hint on the board : ", _def_PM.getActiveRegion().match(hBotRow));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		// Should now flip to VERTICAL and go through downs
		_def_PM.userTabEnter();
		assertTrue("The orientation of the board should be VERTICAL now : ", ((_def_PM.getOrientation() % 2) == 1) );
		assertTrue("The region should match the first down hint : ", _def_PM.getActiveRegion().match(vFirstCol));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		_def_PM.userTabEnter();
		assertTrue("Should have moved to the 2nd hint on the board : ", _def_PM.getActiveRegion().match(vMidCol));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		_def_PM.userTabEnter();
		assertTrue("Should have moved to the 4th hint on the board : ", _def_PM.getActiveRegion().match(vLastCol));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		// Should now flip back to HORIZONTAL
		_def_PM.userTabEnter();
		assertTrue("The orientation of the board should be HORIZONTAL now : ", ((_def_PM.getOrientation() % 2) == 0) );
		assertTrue("The region should be the same as the 1st hint on the board : ", _def_PM.getActiveRegion().match(hTopRow));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		// Work after things have been written to the board.
		Position startPos = new Position(4,3);
		Position v2ndHint1 = new Position(1,0);
		Position v2ndHint2 = new Position(1,2);
		Position v1stHint4 = new Position(2,4);
		Position v1stHint1 = new Position();
		Position v1stHint3 = new Position(2,0);
		Position h1stHint1 = new Position(0,0);
		Position h1stHint3 = new Position(2,0);
		Position h1stHint5 = new Position(4,2);

		char aChar = 'A';
		
		// Write in 1st hint
		_def_PM.setRegionAndCurr( v1stHint1 );
		_def_PM.writeMove( aChar );
		_def_PM.writeMove( aChar );
		_def_PM.writeMove( aChar );
		
		// Write one letter in the middle of the middle hint.
		_def_PM.setRegionAndCurr( v1stHint3 );
		_def_PM.currNext();
		_def_PM.currNext();
		_def_PM.writeMove(aChar);
		
		// Start HORIZONTAL BOTM Hint, and tab thru some Horizontal and Vertical hints
		_def_PM.setRegionAndCurr( startPos );
		
		// Should be VERTICAL after this.
		_def_PM.userTabEnter();
		assertTrue("The orientation of the board should be VERTICAL now : ", ((_def_PM.getOrientation() % 2) == 1) );
		assertTrue("The region should match the first down hint : ", _def_PM.getActiveRegion().match(vFirstCol));
		assertTrue("The current position should equal to (1,0) : ", _def_PM.getCurr().match(v2ndHint1));
		
		// Should match 2nd Pos of 2nd Hint
		_def_PM.userTabEnter();
		assertTrue("The region should match the 2nd down hint : ", _def_PM.getActiveRegion().match(vMidCol));
		assertTrue("The current position should equal to (1,2) : ", _def_PM.getCurr().match(v2ndHint2));
		
		// Should match 1st Pos 4th Hint
		_def_PM.userTabEnter();
		assertTrue("The region should match the 4th down hint : ", _def_PM.getActiveRegion().match(vLastCol));
		assertTrue("The current position should equal to (2,4) : ", _def_PM.getCurr().match(v1stHint4));
		
		// Should become HORIZONTAL
		_def_PM.userTabEnter();
		assertTrue("The orientation of the board should be HORIZONTAL now : ", ((_def_PM.getOrientation() % 2) == 0) );
		assertTrue("The region should match the first across hint : ", _def_PM.getActiveRegion().match(hTopRow));
		assertTrue("The current position should equal to (0,0) : ", _def_PM.getCurr().match(h1stHint1));
		
		// Go to the 3rd Hint
		_def_PM.userTabEnter();
		assertTrue("The region should match the 3rd across hint : ", _def_PM.getActiveRegion().match(hMidRow));
		assertTrue("The current position should equal to (2,0) : ", _def_PM.getCurr().match(h1stHint3));
		
		// This should go the the 5th Hint
		_def_PM.userTabEnter();
		assertTrue("The region should match the 5th across hint : ", _def_PM.getActiveRegion().match(hBotRow));
		assertTrue("The current position should equal to (4,2) : ", _def_PM.getCurr().match(h1stHint5));
		
		// Go from random points on the board.
		_def_PM.setRegionAndCurr(h1stHint3);
		_def_PM.userTabEnter();                       
		assertTrue("The region should match the 5th across hint : ", _def_PM.getActiveRegion().match(hBotRow));
		assertTrue("The current position should equal to (4,2) : ", _def_PM.getCurr().match(h1stHint5));

	}
	
	/**
	 * Test for boolean userArrow( Either an FORWARD OR BACKWARD ENUM, OR POSITION) NOT DECIDED YET.
	 */
	@Test
	public void testUserArrow() {
		// Test REGULAR, and JAPANESE and with some letters.
		ActiveRegion hReg1 = new ActiveRegion( new Position(), 3, 0);
		ActiveRegion hReg2 = new ActiveRegion( new Position(2,0), 5, 0);
		ActiveRegion hReg3 = new ActiveRegion( new Position(4,2), 3, 0);
		ActiveRegion vReg1 = new ActiveRegion( new Position(), 3, 1);
		ActiveRegion vReg2 = new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion vReg3 = new ActiveRegion( new Position(2,4), 3, 1);
		
		
		// Test that works only with a board present
		assertFalse("The board is not present : ", _def_PM.userArrow( MoveType.RIGHT) );
		
		// CREATE 5x5 constrained board
//		_def_PM = boardFactory( ModelType.REGULAR, _def_PM );
		_def_PM = boardFactory( ModelType.REGULAR );
		
		// Test that it respects boundaries
		assertFalse("Should not be able to go thru board boundaries : ", _def_PM.userArrow( MoveType.LEFT));
		assertTrue("Current should not have been moved : ",_def_PM.getCurr().match( new Position()));
		assertTrue("Orientation should remain Horizontal : ", _def_PM.isHorizontal() );
		assertTrue("The Region should not have changed : ", _def_PM.getActiveRegion().match(hReg1));
		
		// When hits a wall does not immediately return false, it tries to see if there is 
		// something further along; and if there is it returns true.  Otherwise, the bumper is 
		// in front of a wall, and should return false for this row or column.
		// Free movement.
		
		assertTrue("This should move current to the next available space in this region : ", _def_PM.userArrow(MoveType.RIGHT));
		assertTrue("Should have moved Right one space : ", _def_PM.getCurr().match(new Position(0,1)));
				
		assertTrue("This should move current to the next available space in this region : ", _def_PM.userArrow(MoveType.RIGHT));
		assertTrue("Should have moved Right one space : ", _def_PM.getCurr().match(new Position(0,2)));

		// Does it respect Bumpers
		assertFalse("This should not have moved : ", _def_PM.userArrow(MoveType.RIGHT));
		assertTrue("Should have stayed in the same place, there is no space behind the bumper at (0,3) : ", _def_PM.getCurr().match(new Position(0,2)));
		
		// Go back and repeatedly hit boundary
		assertTrue("", _def_PM.userArrow(MoveType.LEFT) );
		assertTrue("", _def_PM.getCurr().equals(new Position(0,1)));
		assertTrue("", _def_PM.userArrow(MoveType.LEFT) );
		assertTrue("", _def_PM.getCurr().equals(new Position(0,0)));
		assertFalse("", _def_PM.userArrow(MoveType.LEFT) );
		assertTrue("", _def_PM.getCurr().equals(new Position(0,0)));
		assertFalse("", _def_PM.userArrow(MoveType.LEFT) );
		assertTrue("", _def_PM.getCurr().equals(new Position(0,0)));
		
		assertTrue("Current should be at the head of the 1st Hint : ", _def_PM.getCurr().match(new Position()));
		assertTrue("The region should not have changed : ", _def_PM.getActiveRegion().match( hReg1 ));
		
		// Go to 5th Hint, respect the Right Wall.
		_def_PM.setRegionAndCurr(new Position(4,2));
		assertTrue("Should be able to move RIGHT from (4,2) : ", _def_PM.userArrow(MoveType.RIGHT) );
		assertTrue("Should be at Position(4,3) : ", _def_PM.getCurr().equals(new Position(4,3)));
		assertTrue("Should be able to move from (4,3) : ", _def_PM.userArrow(MoveType.RIGHT) );
		assertTrue("Should be at Position(4,4) : ", _def_PM.getCurr().equals(new Position(4,4)));
		assertFalse("Should not be able to move from Position(4,4) : ", _def_PM.userArrow(MoveType.RIGHT) );
		assertTrue("Should be at Position(4,4) : ", _def_PM.getCurr().equals(new Position(4,4)));

		assertTrue("Should have respected the Right Wall : ", _def_PM.getCurr().match(new Position(4,4)));
		assertTrue("Region should be the 3rd Horizontal Region at the bottom : ", _def_PM.getActiveRegion().match( hReg3 ));
		
		// Same Region respect the Left Bumpers.
		assertTrue("Should be able to move from (4,4)", _def_PM.userArrow(MoveType.LEFT) );
		assertTrue("Should be at Position(4,3) : ", _def_PM.getCurr().equals(new Position(4,3)));
		assertTrue("Should be able to move from (4,3)", _def_PM.userArrow(MoveType.LEFT) );
		assertTrue("Should be at Position(4,2) : ", _def_PM.getCurr().equals(new Position(4,2)));
		assertFalse("Should NOT be able to move from (4,2)", _def_PM.userArrow(MoveType.LEFT) );
		assertTrue("Should stay at Position(4,2) : ", _def_PM.getCurr().equals(new Position(4,2)));

		assertTrue("Current should be stuck on Left Bumper : ", _def_PM.getCurr().match(new Position(4,2)));
		assertTrue("Region should be the 3rd Horizontal Region at the bottom : ", _def_PM.getActiveRegion().match( hReg3 ));
		
		// Change Region to 1st Vertical Region, test Walls and Bumpers.
		_def_PM.setOrientation(1);
		_def_PM.setRegionAndCurr(new Position());
		assertTrue("Should have gone down one space : ", _def_PM.userArrow(MoveType.DOWN));
		assertTrue("Should have gone down one space : ", _def_PM.userArrow(MoveType.DOWN));

		assertFalse("Should not have moved, hit Bumper : ", _def_PM.userArrow(MoveType.DOWN));
		
		assertFalse("The Region should NOT have changed orientation from VERTICAL : ", _def_PM.isHorizontal() );

		assertTrue("Should have gone up one space : ", _def_PM.userArrow(MoveType.UP));
		assertTrue("Should have gone up one space : ", _def_PM.userArrow(MoveType.UP));
		assertFalse("Should not have moved, hit Wall : ", _def_PM.userArrow(MoveType.UP));
		assertFalse("The Region orientation should NOT have changed orientation from VERTICAL : ", _def_PM.isHorizontal() );
		assertTrue("The Region should be the 1st Vertical Region : ", _def_PM.getActiveRegion().match( vReg1 ));

		// Test Orientation flip in tight corners.  Move all the way RIGHT, then try to go down.
		_def_PM.setOrientation(0);
		_def_PM.setRegionAndCurr(new Position());
		
		_def_PM.userArrow(MoveType.RIGHT);
		_def_PM.userArrow(MoveType.RIGHT);
		
		// Call should change Orientation of board, and ActiveRegion, 
		// Curr should move to 2nd Position in new Regon, because Hint has no data.  This will require setting Region and Curr separately.
		_def_PM.userArrow(MoveType.DOWN);
		assertFalse("The Orientation should have changed to Vertical : ", _def_PM.isHorizontal() );
		assertTrue("The Region should have changed to the 2nd Vertical Region : ", _def_PM.getActiveRegion().match( vReg2 ));
		assertTrue("Current should be in (1,2) : ", _def_PM.getCurr().match(new Position(1,2)));
		
		// Now change direction and end up in 3rd Hint, which is Horizontal
		_def_PM.userArrow(MoveType.DOWN);
		assertFalse("The Orientation should remain Vertical : ", _def_PM.isHorizontal() );
		assertTrue("The Region should remain the 2nd Vertical Region : ", _def_PM.getActiveRegion().match( vReg2 ));
		assertTrue("Current should be in (2,2) : ", _def_PM.getCurr().match(new Position(2,2)));
		
		// Move Right into a constrained area.  Orientation and Region should change, and Curr should have moved 1 spc to the Right
		_def_PM.userArrow(MoveType.RIGHT);
		assertTrue("The Orientation should now be Horizontal : ", _def_PM.isHorizontal() );
		assertTrue("The Region should now be the 2nd Horizontal Region : ", _def_PM.getActiveRegion().match( hReg2 ));
		assertTrue("Current should be in (2,3) : ", _def_PM.getCurr().match(new Position(2,3)));
		
		// Test Skip Internal Bumpers.  This should skip over Bumpers that are not connected to the Walls.
		_def_PM.userArrow(MoveType.DOWN);
		assertTrue("The Orientation should still be Horizontal : ", _def_PM.isHorizontal() );
		assertTrue("The Region should now be the 3rd Horizontal Region : ", _def_PM.getActiveRegion().match( hReg3 ));
		assertTrue("Current should be in (4,2), because the Hint is empty : ", _def_PM.getCurr().match(new Position(4,2)));
		
		// Move to Position (3,2) 
		_def_PM.userArrow(MoveType.UP);
		assertFalse("The Orientation should now be Vertical : ", _def_PM.isHorizontal() );
		assertTrue("The Region should now be the 2nd Vertical Region : ", _def_PM.getActiveRegion().match( vReg2 ));
		assertTrue("Current should be in (3,2) : ", _def_PM.getCurr().match(new Position(3,2)));

		// RIGHT Skip Bumper (3,3)
		_def_PM.userArrow(MoveType.RIGHT);
		assertFalse("The Orientation should still be Vertical : ", _def_PM.isHorizontal() );
		assertTrue("The Region should now be the 3rd Vertical Region : ", _def_PM.getActiveRegion().match( vReg3 ));
		assertTrue("Current should be in (2,4), because Hint 4 is empty : ", _def_PM.getCurr().match(new Position(2,4)));
		
		// Now test with Writing on the board.
		//AAA@@  
		//_@_@@
		//____A
		//@@_@_
		//@@AA_
		char aChar = 'A';
		_def_PM.setOrientation(0);
		_def_PM.setRegionAndCurr( new Position());
		_def_PM.writeMove(aChar);
		_def_PM.writeMove(aChar);
		_def_PM.writeMove(aChar);
		
		_def_PM.setRegionAndCurr( new Position(2,0));
		_def_PM.currNext();
		_def_PM.currNext();
		_def_PM.currNext();
		_def_PM.currNext();
		_def_PM.writeMove(aChar);
		
		_def_PM.setActiveRegion( new Position(4,2));
		_def_PM.setCurr( new Position(4,2));
		_def_PM.writeMove(aChar);
		_def_PM.writeMove(aChar);
		
		// Flip Orientation, and go down
		// Should make the Region the 1st Vertical Region, Curr should be the second space in that region.
		_def_PM.setRegionAndCurr(new Position());
		_def_PM.userArrow(MoveType.DOWN);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 1st Vertical Region : ", _def_PM.getActiveRegion().match(vReg1));
		assertTrue("Current should be the 2nd space in this region, becuase that is the 1st empty space in it : ", _def_PM.getCurr().match(new Position(1,0)));
		
		_def_PM.userArrow(MoveType.DOWN);
		assertTrue("Current should be (2,0) : ", _def_PM.getCurr().match(new Position(2,0)));
		
		// Move RIGHT into constrained region
		_def_PM.userArrow(MoveType.RIGHT);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Horizontal Region : ", _def_PM.getActiveRegion().match(hReg2));
		assertTrue("Current should be the 2nd space in this region, because that is the 1st NEW empty space in it, and you left a blank spc : ", _def_PM.getCurr().match(new Position(2,1)));
		
		// Go RIGHT and then DOWN
		_def_PM.userArrow(MoveType.RIGHT);
		assertTrue("Current should be the (2,2) : ", _def_PM.getCurr().match(new Position(2,2)));
		
		_def_PM.userArrow(MoveType.DOWN);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Vertical Region : ", _def_PM.getActiveRegion().match(vReg2));
		assertTrue("Current should be the 4th spc in this region because where it came from was blank : ", _def_PM.getCurr().match(new Position(3,2)));
		
		_def_PM.userArrow(MoveType.DOWN);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Vertical Region : ", _def_PM.getActiveRegion().match(vReg2));
		assertTrue("Current should be the (4,2) : ", _def_PM.getCurr().match(new Position(4,2)));
		
		// Go RIGHT into constrained Region that is partially full
		_def_PM.userArrow(MoveType.RIGHT);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 3rd Horizontal Region : ", _def_PM.getActiveRegion().match(hReg3));
		assertTrue("Current should be the 3rd space in this region, because that is the 1st empty space in it : ", _def_PM.getCurr().match(new Position(4,4)));	
		
		// Go up to (2,4)
		_def_PM.userArrow(MoveType.UP);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 3rd Vertical Region : ", _def_PM.getActiveRegion().match(vReg3));
		assertTrue("Current should be the (3,4) because just came from blank space : ", _def_PM.getCurr().match(new Position(3,4)));

		_def_PM.userArrow(MoveType.UP);
		assertTrue("Current should be the (2,4) because just came from blank space : ", _def_PM.getCurr().match(new Position(2,4)));		
		
		// Go LEFT to (2,0)
		_def_PM.userArrow(MoveType.LEFT);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Horizontal Region : ", _def_PM.getActiveRegion().match(hReg2));
		assertTrue("Current should be the 1st space in this region, because that is the 1st empty space in it and we came from a nonBlank space : ", _def_PM.getCurr().match(new Position(2,0)));	
		
		// Enter constrained Horizontal Region 1, from Vertical Region 2.
		_def_PM.setOrientation(1);
		_def_PM.setActiveRegion(new Position(0,2));
		_def_PM.setCurr(new Position(1,2));
		_def_PM.userArrow(MoveType.UP);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd VERTICAL Region : ", _def_PM.getActiveRegion().match(vReg2));
		assertTrue("Current should be the 1st space in this region, because we came from a blank space : ", _def_PM.getCurr().match(new Position(0,2)));			
		
		// Move Left one.
		_def_PM.userArrow(MoveType.LEFT);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 1st Horizontal Region : ", _def_PM.getActiveRegion().match(hReg1));

		assertTrue("Current should be (0,0), because the Hint is full : ", _def_PM.getCurr().match(new Position(0,0)));
		_def_PM.currNext();
		assertTrue("Should move to Position(0,1) : ", _def_PM.getCurr().match(new Position(0,1)));
		
		// Skip bumper from current position.  Curr is always set to the 1st empty space when you skip bumpers.
		_def_PM.userArrow(MoveType.DOWN);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Horizontal Region : ", _def_PM.getActiveRegion().match(hReg2));
		assertTrue("Current should be the 1st space in this region, we skipped and this is the 1st EMPTY spot : ", _def_PM.getCurr().match(new Position(2,0)));
		
		// Skip back up
		_def_PM.userArrow(MoveType.RIGHT);
		assertTrue("Current should be (2,1) : ", _def_PM.getCurr().match(new Position(2,1)));
		_def_PM.userArrow(MoveType.UP);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 1st Horizontal Region : ", _def_PM.getActiveRegion().match(hReg1));
		assertTrue("Current should be the 1st space in this region, we skipped and this is the 1st spot in this full Hint: ", _def_PM.getCurr().match(new Position()));

	}
	
	
	/**
	 * Test for boolean userModify(Position)
	 */
	@Test
	public void testUserModify() {
		final char aChar = 'A';
		
		// Return false for NULL boards
		assertFalse("Function does not work for NULL boards: ", _def_PM.userModify( aChar ) );
		
		_def_PM = boardFactory( ModelType.REGULAR );

		// HORIZONTAL
		// Should be able to write to the current position.
		assertTrue("This should be a valid space: ", _def_PM.userModify(aChar));

		// Should now be on the next position.
		Position okPos = new Position(0,1);
		assertTrue("Should be on board position [0,1]: ", _def_PM.getCurr().equals(okPos));
		
		// Should still be in Active Region
		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should be able to go twice more, then stop.
		assertTrue("This should be a valid space: ", _def_PM.userModify(aChar));
		assertFalse("This should be a valid space: ",_def_PM.userModify(aChar));
		
		Position fPos = new Position(0,2);
		assertEquals("This position should have been written to, even though it returned false: ", aChar, _def_PM.getPlace_Created(fPos));
		
		assertFalse("Should not move because there is a bumper", _def_PM.userModify(aChar));

		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should not allow to go past boundary
		final Position borderPos = new Position(2,4);
		final ActiveRegion actRegion = new ActiveRegion( new Position(2, 0, 3), 5, 0);        // The position should correspond with the 3rd hint.
		_def_PM.setCurr(borderPos);                                                     // Set Curr to this position
		_def_PM.setActiveRegion(borderPos);                                             // Set the region so the writeMove will work.
		
		assertFalse("Should not allow to go past board boundary, but should still write : ", _def_PM.userModify(aChar));
		assertEquals( aChar + " should have been written to " + borderPos + " : ", aChar, _def_PM.getPlace_Created(borderPos));
		assertEquals("This should be the region that is occupied by the Active Region: ", actRegion, _def_PM.getActiveRegion());
		
		// VERTICAL
		_def_PM.setOrientation(1);                                                       // Orientation is now Vertical
		_def_PM.setActiveRegion(borderPos);                                              // Reset AR from borderPos's position, in the new orientation
		_def_PM.setCurr( borderPos);                                                     // Set curr to position within AR, should have a place of 4.

		char letter2 = 'B';
		
		// Should be able to write to the current position.
		assertTrue("This should be a valid space: ", _def_PM.userModify( letter2 ));

		// Should now be on the next position.
		Position okPos2 = new Position(3,4);
		assertTrue("Should be on board position [3,4]: ", _def_PM.getCurr().equals(okPos2));
		
		// Should still be in Active Region
		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should be able to go twice more, then stop.
		assertTrue("This should be a valid move: ", _def_PM.userModify( letter2 ));
		assertFalse("This should be an invalid move : ",_def_PM.userModify( letter2 ));
		
		Position fPos2 = new Position(4, 4);
		assertEquals("This position should have been written to, even though it returned false: ", letter2, _def_PM.getPlace_Created(fPos2));
		
		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Check that bumpers still block when orientationis vertical.
		Position blockPos = new Position(2,0);
		Position startPos = new Position(1,0);
		ActiveRegion aReg = new ActiveRegion( new Position(0,0,1), 3, 1);
		char letter3 = 'C';
		
		_def_PM.setActiveRegion(blockPos);
		_def_PM.setRegionAndCurr(blockPos);                                    // Curr should be set to (1,0)

		// We should be in the first down region.
		assertTrue("We should be occupying this region : ", _def_PM.getActiveRegion().equals( aReg ));
		
		// Curr should be at 1,0
		assertTrue("Curr should be at (1,0) : ", _def_PM.getCurr().equals(startPos));
		
		assertTrue("Should have printed a letter to cell (1,0), and moved curr to (2,0) : ", _def_PM.userModify(letter3));

		assertTrue("This should be at position (2,0): ", _def_PM.getCurr().equals(blockPos));

		assertFalse("Should print a letter to cell (2,0) and not be able to move any further: ", _def_PM.userModify(letter3)  );
		assertFalse("Should print a letter at (2,0) and not be able to move: ", _def_PM.userModify(letter2));

	}
	
	/**
	 * Test method for getAcrossOrDownHint()
	 * Will test one of the methods that encapsulates the Maps
	 */
	@Test
	public void testGetAcrossOrDownHint() {
		boolean isHorizontal = true;
		Position hPos1 = new Position(0,0,1);     
		Position hPos2 = new Position(2,0,3);
		Position hPos3 = new Position(4,2,5);
		Position vPos1 = new Position(0,0,1);
		Position vPos2= new Position(0,2,2);
		Position vPos3= new Position(2,4,4);
		
		String hStr1 = "Like running but slower.";
		String hStr2 = "To be unsteady";
		String hStr3 = "Empty ones can be used for parking.";
		String vStr1 = "Your teeth are there.";
		String vStr2 = "You need this to maintain order in the court";
		String vStr3 = "If your late you may have to do this.";

		
		// Make sure nothing is returned if no board
		assertNull("Function does not work for NULL boards: ", _def_PM.getAcrossOrDownHint( isHorizontal, hPos1 ) );
		
		PMModel pmm1 = boardFactory( ModelType.REGULAR );

		// Mapping 
		Map<Position, String> acrossMap = pmm1.getAcrossHints();
		Map<Position, String> downMap = pmm1.getDownHints();

		// Set the Mappings to values.
		acrossMap.put(hPos1, hStr1 );
		acrossMap.put(hPos2, hStr2 );
		acrossMap.put(hPos3, hStr3 );
		
		downMap.put(vPos1, vStr1);
		downMap.put(vPos2, vStr2);
		downMap.put(vPos3, vStr3);
		
		// Test ACROSS
		assertNull("Should return NULL if a NON start position is entered : ", pmm1.getAcrossOrDownHint(isHorizontal, new Position(0,1)));
		assertTrue("The Strings should match : ", hStr1.equals(pmm1.getAcrossOrDownHint(isHorizontal, hPos1)));
		assertTrue("The Strings should match : ", hStr3.equals(pmm1.getAcrossOrDownHint(isHorizontal, hPos3)));
		
		// Switch orientation and test DOWN
		isHorizontal = false;

		// Test DOWN
		assertNull("Should return NULL if a NON start position is entered : ", pmm1.getAcrossOrDownHint(isHorizontal, new Position(2,1)));
		assertTrue("The Strings should match : ", vStr1.equals(pmm1.getAcrossOrDownHint(isHorizontal, vPos1)));
		assertTrue("The Strings should match : ", vStr3.equals(pmm1.getAcrossOrDownHint(isHorizontal, vPos3)));
		
	}
	
	/**
	 * Test method for setAcrossOrDownHint
	 */
	@Test
	public void testSetAcrossOrDownHint() {
		boolean isHorizontal = true;
		Position hPos1 = new Position(0,0,1);
		Position hPos2 = new Position(2,0,3);
		Position hPos3 = new Position(4,2,5);
		Position vPos1 = new Position(0,0,1);
		Position vPos2= new Position(0,2,2);
		Position vPos3= new Position(2,4,4);
		
		String hStr1 = "Like running but slower.";
		String hStr2 = "To be unsteady";
		String hStr3 = "Empty ones can be used for parking.";
		String vStr1 = "Your teeth are there.";
		String vStr2 = "You need this to maintain order in the court";
		String vStr3 = "If your late you may have to do this.";
		String extraStr = "___ Forrest ___!";

		
		// Make sure nothing is returned if no board
		assertFalse("Function does not work for NULL boards: ", _def_PM.setAcrossOrDownHint( isHorizontal, hPos1, hStr1 ) );
		
		PMModel pmm1 = boardFactory( ModelType.REGULAR );

		// Bad Position should not work.
		assertFalse("This should return false : ", pmm1.setAcrossOrDownHint( isHorizontal, new Position(0,1), hStr1 ) );
		
		assertTrue("This should return true : ", pmm1.setAcrossOrDownHint( isHorizontal, hPos1, hStr1 ) );
		assertTrue("This should return true : ", pmm1.setAcrossOrDownHint( isHorizontal, hPos2, hStr2 ) );
		assertTrue("This should return true : ", pmm1.setAcrossOrDownHint( isHorizontal, hPos3, hStr3 ) );
		
		// CHANGE TO VERTICAL
		isHorizontal = false;
		
		assertFalse("This should return false : ", pmm1.setAcrossOrDownHint( isHorizontal, new Position(2,1), hStr1 ) );
		assertTrue("This should return true : ", pmm1.setAcrossOrDownHint( isHorizontal, vPos1, vStr1 ) );
		assertTrue("This should return true : ", pmm1.setAcrossOrDownHint( isHorizontal, vPos2, vStr2 ) );
		assertTrue("This should return true : ", pmm1.setAcrossOrDownHint( isHorizontal, vPos3, vStr3 ) );

		// Change to HORIZONTAL
		isHorizontal = true;
		
//		 Match positions.
		assertTrue("These Strings should match if all went well. : ", hStr1.equals(pmm1.getAcrossOrDownHint( isHorizontal, hPos1)));
		assertTrue("These Strings should match if all went well. : ", hStr2.equals(pmm1.getAcrossOrDownHint( isHorizontal, hPos2)));
		assertTrue("These Strings should match if all went well. : ", hStr3.equals(pmm1.getAcrossOrDownHint( isHorizontal, hPos3)));
		
		// Change to VERTICAL
		isHorizontal = false;
		
//		 Match positions.
		assertTrue("These Strings should match if all went well. : ", vStr1.equals(pmm1.getAcrossOrDownHint( isHorizontal, vPos1)));
		assertTrue("These Strings should match if all went well. : ", vStr2.equals(pmm1.getAcrossOrDownHint( isHorizontal, vPos2)));
		assertTrue("These Strings should match if all went well. : ", vStr3.equals(pmm1.getAcrossOrDownHint( isHorizontal, vPos3)));
		
		// Change some and see if change is reflected.
		assertTrue("This should return true : ", pmm1.setAcrossOrDownHint( isHorizontal, vPos3, extraStr ) );
		assertTrue("These Strings should match if all went well. : ", extraStr.equals(pmm1.getAcrossOrDownHint( isHorizontal, vPos3)));
				
	}
	
	// Main Dictionary Tests.
	
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
		assertEquals("This should not have any words in it, 0: ", 0, _def_PM.numWordsDict());
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _def_PM.addWordDict(word1));
		assertEquals("This should hava 1 word in it, 1: ", 1, _def_PM.numWordsDict());
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _def_PM.addWordDict(word2));
		assertFalse("This is a duplicate which is not allowed", _def_PM.addWordDict(word2));
		assertEquals("This should have 2 words in it, 2: ", 2, _def_PM.numWordsDict());
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _def_PM.addWordDict(word3));		
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _def_PM.addWordDict(word4));		
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _def_PM.addWordDict(word5));		
		assertEquals("This should have 5 words in it, 5: ", 5, _def_PM.numWordsDict());
		
		// Add non duplicates
		assertEquals( word6 + " is not a duplicate, true: ", true, _def_PM.addWordDict(word6));
		assertEquals( word7 + " is not a duplicate, but is part of another word already in the tree, true: ", true, _def_PM.addWordDict(word7));
		
		_def_PM.addWordDict(badWord1);  // Check that this word has the puncuation removed.
		assertFalse("The word \""+ badWord2 + "\" should be in the tree: ", _def_PM.addWordDict(badWord2));
		assertEquals("This should have 8 words in it, 8: ", 8, _def_PM.numWordsDict());
		
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
		_def_PM.addWordDict(find1);
		_def_PM.addWordDict(find2);
		_def_PM.addWordDict(find3);
		_def_PM.addWordDict(find4);

		// Repeated removal, add cycles.
		assertEquals("This should be size "+ four +": ", four, _def_PM.numWordsDict() );
		assertTrue(find4 + " should be in the tree", _def_PM.findWordDict(find4));
		assertTrue("This should make " + find4 + " not a word in the tree: ", _def_PM.deleteWordDict(find4));
		assertEquals("This should be size "+ three +": ", three, _def_PM.numWordsDict() );
		assertFalse(find4+" should not be found in the tree: ", _def_PM.findWordDict(find4));
		_def_PM.addWordDict(find4);
		assertEquals("This should be size "+ four +": ", four, _def_PM.numWordsDict() );
		assertTrue(find4 + " should be in the tree", _def_PM.findWordDict(find4));
		assertTrue("This should make " + find4 + " not a word in the tree: ", _def_PM.deleteWordDict(find4));
		assertEquals("This should be size "+ three +": ", three, _def_PM.numWordsDict() );
		assertFalse(find4+" should not be found in the tree: ", _def_PM.findWordDict(find4));
		
		// Delete words not in the tree
		assertFalse(noFind1 + " is in the tree, but is only part of a word", _def_PM.deleteWordDict(noFind1));
		assertFalse(noFind2 + " is not in the tree", _def_PM.deleteWordDict(noFind2));
		assertFalse(noFind2 + " is not in the tree", _def_PM.deleteWordDict(noFind2));               // Repeat
		assertEquals("This should be size "+ three +": ", three, _def_PM.numWordsDict() );
		
		// 2x deletions
		assertFalse(find4 + " should have already been deleted: ", _def_PM.deleteWordDict(find4));   // Second deletion
		assertTrue(find3 + " should be deleted: ", _def_PM.deleteWordDict(find3));
		assertFalse(find3 + " has been previously deleted, cannot delete twice: ", _def_PM.deleteWordDict(find3));
		
		// Add all words
		_def_PM.addWordDict(find3);
		_def_PM.addWordDict(find4);
		_def_PM.addWordDict(noFind1);
		_def_PM.addWordDict(noFind2);
		_def_PM.addWordDict(noFind3);
		assertEquals("There should be " + seven + " words: ", seven, _def_PM.numWordsDict() );
		
		// Remove subWords then look to make sure removed.
		assertTrue(noFind1 + " should be allowed to be removed: ", _def_PM.deleteWordDict(noFind1));
		assertTrue(noFind3 + " should be allowed to be removed: ", _def_PM.deleteWordDict(noFind3));
		
		assertFalse(noFind1 + " has already been removed: ", _def_PM.deleteWordDict(noFind1));
		assertFalse(noFind3 + " has already beeb removed: ", _def_PM.deleteWordDict(noFind3));
		
		assertFalse(noFind1 + " should not be in the tree: ", _def_PM.findWordDict(noFind1));
		assertFalse(noFind3 + " should not be in the tree: ", _def_PM.findWordDict(noFind3));
		
		assertEquals("There should be " + five + " words: ", five, _def_PM.numWordsDict() );
		
		_def_PM.addWordDict(noFind1);
		_def_PM.addWordDict(noFind3);
		
		assertEquals("There should be " + seven + " words: ", seven, _def_PM.numWordsDict() );
		assertTrue(noFind1 + " should be in the tree: ", _def_PM.findWordDict(noFind1));
		assertTrue(noFind3 + " should be in the tree: ", _def_PM.findWordDict(noFind3));
		
	}
	
	/**
	 * This will tests the loadFile() to load files from the directory
	 */
	@Test 
	public void testLoadFile() {
		assertTrue("This should be true if the file was loaded correctly", _def_PM.loadDict(new File("testFile.lst")));
		
		ArrayList<String> fromHD = listInput(new File("testFile.lst"));
		ArrayList<String> fromTree = _def_PM.outputDict();
		
		if ( fromHD.size() != fromTree.size() ) {
			fail("The number of words do not match");
		}
		
		for ( int index = 0; index < fromHD.size(); index++ ) {
			 assertEquals("These should say the same thing", fromHD.get(index).toUpperCase().replaceAll(" ", ""), fromTree.get(index));
		}
		
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
		assertEquals("There should be no words in the tree at this point: ", 0, _def_PM.numWordsDict());

		_def_PM.addWordDict(find1);
		_def_PM.addWordDict(find2);
		_def_PM.addWordDict(find3);
		_def_PM.addWordDict(find4);
		
		// Some words
		assertEquals("There should be four words: ", 4, _def_PM.numWordsDict() );
				
		// Some removed
		_def_PM.deleteWordDict(find1);
		assertEquals("There should be three words: ", 3, _def_PM.numWordsDict());
	}
	
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
		
		_def_PM.addWordDict(find);
		_def_PM.addWordDict(find1);
		_def_PM.addWordDict(find2);
		_def_PM.addWordDict(find3);
		_def_PM.addWordDict(find4);
		_def_PM.addWordDict(find5);
		_def_PM.addWordDict(find6);
		
		ArrayList<String> sArr = new ArrayList<String>();
		sArr.add( find6 );
		sArr.add( find5 );
		sArr.add( find4 );
		sArr.add( find );
		sArr.add( find2 );
		sArr.add( find3 );
		sArr.add( find1 );
		
		ArrayList<String> testArr = _def_PM.outputDict();
		
		if ( sArr.size() != testArr.size() ) {
			fail("The sizes are not the same");
		}
		
		for ( int index = 0; index < testArr.size(); index++) {
			assertTrue("These are supposed to be the same", sArr.get(index).equalsIgnoreCase(testArr.get(index)));
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
		
		_def_PM.addWordDict(find);
		_def_PM.addWordDict(find1);
		_def_PM.addWordDict(find2);
		_def_PM.addWordDict(find3);
		_def_PM.addWordDict(find4);
		_def_PM.addWordDict(find5);
		_def_PM.addWordDict(find6);

		_def_PM.addWordDict(word1);
		_def_PM.addWordDict(word2);
		_def_PM.addWordDict(word3);
		_def_PM.addWordDict(word4);
		_def_PM.addWordDict(word5);
		_def_PM.addWordDict(word6);
		_def_PM.addWordDict(word7);
		
		ArrayList<String> testArr = new ArrayList<String>();
		
		// Suggestions for partial word: []re, len 7: == 0
		assertEquals("\"_re\" is not a prefix in this tree: ", 0, _def_PM.suggestMatchDict("_re").size());
		
		// Suggestions for empty words give back empty lists
		assertEquals(" This should not return anything because an empty string is being inputted ", 0, _def_PM.suggestMatchDict("").size());
		
		// Suggestions of whole words should just return the word.
		assertEquals( find1.toUpperCase() + " is a whole word and should be the only word returned ", 1, _def_PM.suggestMatchDict(find.toUpperCase()).size());
		assertEquals( find1.toUpperCase() + " is a whole word and should be the only word returned ", find1.toUpperCase(), _def_PM.suggestMatchDict(find1.toUpperCase()).get(0));
		
		// Suggestions for add_n_, should be addend, and adding
		String test = "add_n_";
		testArr.add(find4);
		testArr.add(find3);
		//for ( String item: testArr ) {
		for ( int index = 0; index < testArr.size(); index++ ) { 
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), _def_PM.suggestMatchDict(test).get(index));
		}
		
		// Suggestions for _e__v__, should be removal, removed
		test = "_e__v__";
		testArr.clear();
		testArr.add(word1);
		testArr.add(word3);
		for ( int index = 0; index < testArr.size(); index++ ) {
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), _def_PM.suggestMatchDict(test).get(index));
		}		
		
		// Suggestions for __d_n_, should be addend, adding, redone
		test = "__d_n_";
		testArr.clear();
		testArr.add(find4);
		testArr.add(find3);
		testArr.add(word4);
		for (int index = 0; index < testArr.size(); index++ ) {
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), _def_PM.suggestMatchDict(test).get(index));
		}		
		
		// Suggestions for __m____, should be removal, removed, remodel
		test = "__m____";
		testArr.clear();
		testArr.add(word7);
		testArr.add(word1);
		testArr.add(word3);
		ArrayList<String> answer = _def_PM.suggestMatchDict(test);
		for ( int index = 0; index < testArr.size(); index++ ) {
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), answer.get(index));
		}
		
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
		
		_def_PM.addWordDict(find);
		_def_PM.addWordDict(find1);
		_def_PM.addWordDict(find2);
		_def_PM.addWordDict(find3);
		_def_PM.addWordDict(find4);
		_def_PM.addWordDict(find5);
		_def_PM.addWordDict(find6);
		
		// Find words of length 1: False
		assertEquals("This should not return any results: ", 0, _def_PM.suggestWordsOfLengthDict(1).size());

		// Find words of length 7: False
		assertEquals("This should not return any results: ", 0, _def_PM.suggestWordsOfLengthDict(7).size());
		
		// Find words of length 17: False
		assertEquals("This should not return any results: ", 0, _def_PM.suggestWordsOfLengthDict(18).size());
		
		// Find words of legnth 3
		assertEquals("This should find the word add: ", find5.toUpperCase(), _def_PM.suggestWordsOfLengthDict(3).get(0));
		
		// Find words of length 8
		for ( int index = 0; index < 2; index++ ) {
			if ( index == 0 ) {
				assertEquals("This should find the word adderall: ", find2.toUpperCase(), _def_PM.suggestWordsOfLengthDict(8).get(0));
			} else {
				assertEquals("This should find the word addition: ", find1.toUpperCase(), _def_PM.suggestWordsOfLengthDict(8).get(1));			
			}
		}
		
		// Testing single letter words
		_def_PM.addWordDict(find7);                                       
		_def_PM.addWordDict(find8);
		
		ArrayList<String> treeAnsArr = _def_PM.suggestWordsOfLengthDict(1);
		ArrayList<String> ansArr = new ArrayList<String>();
		
		ansArr.add(find7);
		ansArr.add(find8);
		
		for ( int ctr = 0; ctr < treeAnsArr.size(); ctr++ ) {
			assertEquals("This should find A: ", ansArr.get(ctr).toUpperCase(), treeAnsArr.get(ctr));
		}
			
	}
	
	/**
	 * Test that the state of the model can be outputted correctly.
	 */
	@Test
	public void testOutputModelState() {
		
		_def_PM = boardFactory( ModelType.REGULAR );
		
		_def_PM.outputModelState("latestFile.lst");
		

		
	}
	
	/**
	 * Test method for inputModelState
	 */
	@Test
	public void testInputModelState() {
		_def_PM = boardFactory( ModelType.REGULAR );
		_def_PM.loadDict(new File("testFile.lst"));
		ArrayList<String> oldDict = _def_PM.outputDict();
		_def_PM.outputModelState("latestFile.lst");
		
		PMModel unArchivedModel = new PMModel();                            // This will be unarchived.
		
		// Check that there were no errors in operation.  Like bad files, or bad input reading.
		assertTrue("There must be something wrong with how it is being read : ", unArchivedModel.inputModelState("latestFile.lst"));
		
		System.out.println(unArchivedModel.outputStates());
		ArrayList<String> nwDict = unArchivedModel.outputDict();              // So you can check the Dictionary
		System.out.println(nwDict);
		
		// Make sure that sizes of the dictionaries match.
		assertEquals("These should be the same size : ", oldDict.size(), nwDict.size());
		
		// Make sure that archived and unarchived have the same dictionary.
		for ( int cnt = 0; cnt < oldDict.size(); cnt++ ) {
			assertTrue("These should be the same words : ", nwDict.get(cnt).equals(oldDict.get(cnt)));
		}
		
		// Make sure that archive/unarchived have the same size board.
		assertEquals("The side length should be equal for these nxn boards : ", _def_PM.getSideLength(), unArchivedModel.getSideLength());
		
////		_def_PM.printBoardNumbering();
////		unArchivedModel.printBoardNumbering();
//		for ( int itr = 0; itr < unArchivedModel.getSideLength(); itr++ ) {
//			for ( int trn = 0; trn < unArchivedModel.getSideLength(); trn++ ) {
//				System.out.print("" + (( 0 == unArchivedModel.getPlace_Created(itr, trn)) ? ' ' : '@'));
//			}
//			System.out.println();
//		}
		
		// Make sure archived and unarchived files have the same values for the board.
		for ( int itr = 0; itr < unArchivedModel.getSideLength(); itr++ ) {
			for ( int trn = 0; trn < unArchivedModel.getSideLength(); trn++ ) {
				assertEquals("These should have the same values: ", _def_PM.getPlace_Created(itr, trn), unArchivedModel.getPlace_Created(itr, trn));
			}
		}
		
	}
	
	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		
		PMModel pmm1 = boardFactory( ModelType.REGULAR );
		PMModel pmm2 = boardFactory( ModelType.REGULAR );

		pmm2.writeMove('A');
		
		// Equals Objects should have equal hashCodes
		assertEquals("These should return the same hashCode: ", pmm1.hashCode(), pmm1.hashCode() );
		
		// Unequal Objects should have unequal hashCodes.
		assertFalse("These should have different hashCodes: ", (pmm1.hashCode() == pmm2.hashCode()));
		
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		
		// ** NOT Finished:  Make EQUALS for Hints, and Dictionary
		
		PMModel pmm1 = boardFactory( ModelType.REGULAR );
		PMModel pmm2 = boardFactory( ModelType.REGULAR );
		PMModel pmm3 = boardFactory( ModelType.REGULAR );
		
		// Test equal with these.
		pmm3.writeMove('A');
//		pmm3.setRegionAndCurr( new Position(2,2));
		
		// Test equals with these
//		pmm3.setOrientation(1);
//		pmm3.setRegionAndCurr( new Position(2,2));
		
		// Test for the same object
		assertTrue("These were supposed to be equal", pmm1.equals(pmm1));
		
		// Test that NULLS return false
		assertFalse("This should have returned false, since nothing is equal to null", pmm1.equals(null));
		
		// Test that different classes are false
		assertFalse("Different classes cannot be considered the same", pmm1.equals(new String()));
		
		//System.out.println( "\n\n"+ pmm1.printBoardStates() );
		//System.out.println( pmm2.printBoardStates() );
		
		// These are considered equal
		assertTrue("These should be considered exactly the same", pmm1.equals(pmm2));
		
		// These are different 
		assertFalse("These should be considered different", pmm1.equals(pmm3));
		
	}

	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	public void testToString() {
		_def_PM = boardFactory( ModelType.REGULAR);
		assertEquals("This should match precreated output", _test_Board2, _def_PM.toString());
	}

}
