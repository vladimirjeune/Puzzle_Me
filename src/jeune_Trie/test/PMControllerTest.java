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

import jeune_Trie.ActiveRegion;
import jeune_Trie.GameType;
import jeune_Trie.MirrorType;
import jeune_Trie.MoveType;
import jeune_Trie.PMController;
import jeune_Trie.PMModel;
import jeune_Trie.PMModelInterface;
import jeune_Trie.Position;
import jeune_Trie.UseType;
import jeune_Trie.test.PMModelTest.ModelType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author vladimirjeune
 *
 */
public class PMControllerTest {

	PMModel _def_PM ;
	PMController _pmc ;
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
		_pmc = new PMController( _def_PM ); 
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
	//public void boardSetupStart( PMModel aModel, UseType aUseType, GameType aGameType, MirrorType aMirrorType, int aSize ) {
	public void boardSetupStart( PMController aController, UseType aUseType, GameType aGameType, MirrorType aMirrorType, int aSize ) {				
		// Choose proper action, CREATE or PLAY, Use CREATE
		aController.cSetBoardUse( aUseType );
		
		// Choose use type, NxN, or CRISS CROSS, Use NXN
		aController.cSetBoardGame( aGameType );
		
		// Choose board size, size >= 3 && size < 32 
		aController.cSetSize( aSize );
		
		// Choose the type of mirror for the bumpers
		aController.cSetMirrorType( aMirrorType );
		
	}
	
	/**
	 * BOARDSETUPEND finishes the setup of the board; starting from after bumper creation.
	 */
	//public void boardSetupEnd( PMModel aModel ) {
	public void boardSetupEnd( PMController aController ) {
		aController.cFinishBoardSetup();
	}
	
	/**
	 * BOARDFACTORY holds a few different types of boards with set bumper placements, that can be tested.  All valid boards will
	 * be from ModelType.  However, all may not be implemented yet.
	 */
	//public PMModel boardFactory( ModelType aType ) {
//	public PMModel boardFactory( ModelType aType ) {
	public PMController boardFactory( ModelType aType ) {
//		PMModel retVal = new PMModel();
//		PMController valu = new PMController( retVal );
		//PMModel retVal = new PMModel();
		PMController valu = new PMController( _def_PM );

		
		switch ( aType ) {
		case REGULAR:
			boardSetupStart( valu, UseType.CREATE, GameType.NXN, MirrorType.FLATFLIP, 5  );                    // Does a default board setup, see definition for set variables.
						
			valu.cFlipBumper( new Position(0,3) );
			valu.cFlipBumper( new Position(0,4) );
			valu.cFlipBumper( new Position(1,1) );
			valu.cFlipBumper( new Position(1,3) );
			valu.cFlipBumper( new Position(1,4) );
			
			break;
			
		case JAPANESE:
			boardSetupStart( valu, UseType.CREATE, GameType.NXN, MirrorType.FLATFLIP, 9  );                    // Does a default board setup, see definition for set variables.
			
			valu.cFlipBumper( new Position(0,4) );
			valu.cFlipBumper( new Position(1,3) );
			valu.cFlipBumper( new Position(1,8) );
			valu.cFlipBumper( new Position(2,2) );
			valu.cFlipBumper( new Position(2,5) );
			valu.cFlipBumper( new Position(2,7) );
			valu.cFlipBumper( new Position(3,1) );
			valu.cFlipBumper( new Position(3,6) );
			valu.cFlipBumper( new Position(4,4) );
			
			break;
			
		case BRITISH:
			boardSetupStart( valu, UseType.CREATE, GameType.NXN, MirrorType.FLATFLIP, 15  );                    // Does a default board setup, see definition for set variables.
			
			valu.cFlipBumper( new Position(0,4) );
			valu.cFlipBumper( new Position(1,0) );
			valu.cFlipBumper( new Position(1,2) );
			valu.cFlipBumper( new Position(1,4) );
			valu.cFlipBumper( new Position(1,6) );
			valu.cFlipBumper( new Position(1,7) );
			valu.cFlipBumper( new Position(1,9) );
			valu.cFlipBumper( new Position(1,11) );
			valu.cFlipBumper( new Position(1,13) );			
			valu.cFlipBumper( new Position(3,0) );			
			valu.cFlipBumper( new Position(3,2) );			
			valu.cFlipBumper( new Position(3,4) );			
			valu.cFlipBumper( new Position(3,6) );			
			valu.cFlipBumper( new Position(3,7) );			
			valu.cFlipBumper( new Position(3,9) );			
			valu.cFlipBumper( new Position(3,11) );			
			valu.cFlipBumper( new Position(3,13) );			
			valu.cFlipBumper( new Position(4,9) );			
			valu.cFlipBumper( new Position(5,0) );			
			valu.cFlipBumper( new Position(5,2) );			
			valu.cFlipBumper( new Position(5,4) );			
			valu.cFlipBumper( new Position(5,6) );			
			valu.cFlipBumper( new Position(5,8) );			
			valu.cFlipBumper( new Position(5,10) );			
			valu.cFlipBumper( new Position(5,12) );			
			valu.cFlipBumper( new Position(5,13) );			
			valu.cFlipBumper( new Position(6,6) );			
			valu.cFlipBumper( new Position(7,0) );			
			valu.cFlipBumper( new Position(7,1) );			
			valu.cFlipBumper( new Position(7,2) );			
			valu.cFlipBumper( new Position(7,4) );			
			valu.cFlipBumper( new Position(7,6) );			

			break;
		case AMERICAN:
			boardSetupStart( valu, UseType.CREATE, GameType.NXN, MirrorType.FLATFLIP, 15  );                    // Does a default board setup, see definition for set variables.
			
			valu.cFlipBumper( new Position(0,4) );
			valu.cFlipBumper( new Position(0,10) );
			valu.cFlipBumper( new Position(1,4) );
			valu.cFlipBumper( new Position(1,10) );
			valu.cFlipBumper( new Position(3,3) );
			valu.cFlipBumper( new Position(3,7) );
			valu.cFlipBumper( new Position(4,6) );
			valu.cFlipBumper( new Position(4,12) );
			valu.cFlipBumper( new Position(4,13) );			
			valu.cFlipBumper( new Position(4,14) );			
			valu.cFlipBumper( new Position(5,0) );			
			valu.cFlipBumper( new Position(5,1) );			
			valu.cFlipBumper( new Position(5,5) );			
			valu.cFlipBumper( new Position(5,9) );			
			valu.cFlipBumper( new Position(6,4) );			
			valu.cFlipBumper( new Position(7,3) );			
			
			break;
		case SPARSE:
		default: // Do nothing
			System.out.println("You are not suppose to be here.");
			
		}
		
		boardSetupEnd( valu );                       // finishes up board initialization from after bumper placement. 
		
		return valu;
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

			rowManip >>>= 1;                 // Unsigned right shift, to compensate for extra left shift at end
			rowResult[ row ] = rowManip;     // Put result in rowResult for this row.
			
		}
		
		for ( int itr = 0; itr < expectedRowAmt.length; itr++ ) {  // Loop through array of row amounts, see if we got similar results.
			
			if ( expectedRowAmt[ itr ] != rowResult[ itr ] ) {
				retVal = false;                                    // No match 
			}
		}
		
		return retVal;
	}
	
	/**
	 * Test method for {@link jeune_Trie.PMController#PMController(jeune_Trie.PMModelInterface)}.
	 */
	@Test
	public void testPMController() {
		
		// Test that Model has been set.
		assertTrue("This should not be NULL : ", ( null != _pmc.getModel()));
		
		// Test that the View has been set.
		assertTrue("This should not be NULL : ", ( null != _pmc.getView()));
		
		// Test that the board has not been set up at this point.
		assertFalse("The board should not yet be created : ", _pmc.isSetup() );
		
	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cArrow(jeune_Trie.MoveType)}.
	 */
	@Test
	public void testCArrow() {
		// Test REGULAR, and JAPANESE and with some letters.
		ActiveRegion hReg1 = new ActiveRegion( new Position(), 3, 0);
		ActiveRegion hReg2 = new ActiveRegion( new Position(2,0), 5, 0);
		ActiveRegion hReg3 = new ActiveRegion( new Position(4,2), 3, 0);
		ActiveRegion vReg1 = new ActiveRegion( new Position(), 3, 1);
		ActiveRegion vReg2 = new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion vReg3 = new ActiveRegion( new Position(2,4), 3, 1);
		
		
		// Test that works only with a board present
		assertFalse("The board is not present : ", _pmc.cArrow( MoveType.RIGHT) );
		
		// CREATE 5x5 constrained board
		_pmc = boardFactory( ModelType.REGULAR );
		
		// Test that it respects boundaries
		assertFalse("Should not be able to go thru board boundaries : ", _pmc.cArrow( MoveType.LEFT));
		assertTrue("Current should not have been moved : ",_def_PM.getCurr().match( new Position()));
		assertTrue("Orientation should remain Horizontal : ", _def_PM.isHorizontal() );
		assertTrue("The Region should not have changed : ", _def_PM.getActiveRegion().match(hReg1));
		
		// When hits a wall does not immediately return false, it tries to see if there is 
		// something further along; and if there is it returns true.  Otherwise, the bumper is 
		// in front of a wall, and should return false for this row or column.
		// Free movement.
		
		System.out.println("cArrow start.  isSetup is : " + _pmc.isSetup() );
		assertTrue("This should move current to the next available space in this region : ", _pmc.cArrow(MoveType.RIGHT));
		assertTrue("Should have moved Right one space : ", _def_PM.getCurr().match(new Position(0,1)));

		assertTrue("This should move current to the next available space in this region : ", _pmc.cArrow(MoveType.RIGHT));
		assertTrue("Should have moved Right one space : ", _def_PM.getCurr().match(new Position(0,2)));

		// Does it respect Bumpers
		assertFalse("This should not have moved : ", _pmc.cArrow(MoveType.RIGHT));
		assertTrue("Should have stayed in the same place, there is no space behind the bumper at (0,3) : ", _def_PM.getCurr().match(new Position(0,2)));
		
		// Go back and repeatedly hit boundary ^^^
		assertTrue("", _pmc.cArrow(MoveType.LEFT) );
		assertTrue("", _def_PM.getCurr().equals(new Position(0,1)));
		assertTrue("", _pmc.cArrow(MoveType.LEFT) );
		assertTrue("", _def_PM.getCurr().equals(new Position(0,0)));
		assertFalse("", _pmc.cArrow(MoveType.LEFT) );
		assertTrue("", _def_PM.getCurr().equals(new Position(0,0)));
		assertFalse("", _pmc.cArrow(MoveType.LEFT) );
		assertTrue("", _def_PM.getCurr().equals(new Position(0,0)));
		
		assertTrue("Current should be at the head of the 1st Hint : ", _def_PM.getCurr().match(new Position()));
		assertTrue("The region should not have changed : ", _def_PM.getActiveRegion().match( hReg1 ));
		
		// Go to 5th Hint, respect the Right Wall.
		_def_PM.setRegionAndCurr(new Position(4,2));
		assertTrue("Should be able to move RIGHT from (4,2) : ", _pmc.cArrow(MoveType.RIGHT) );
		assertTrue("Should be at Position(4,3) : ", _def_PM.getCurr().equals(new Position(4,3)));
		assertTrue("Should be able to move from (4,3) : ", _pmc.cArrow(MoveType.RIGHT) );
		assertTrue("Should be at Position(4,4) : ", _def_PM.getCurr().equals(new Position(4,4)));
		assertFalse("Should not be able to move from Position(4,4) : ", _pmc.cArrow(MoveType.RIGHT) );
		assertTrue("Should be at Position(4,4) : ", _def_PM.getCurr().equals(new Position(4,4)));

		assertTrue("Should have respected the Right Wall : ", _def_PM.getCurr().match(new Position(4,4)));
		assertTrue("Region should be the 3rd Horizontal Region at the bottom : ", _def_PM.getActiveRegion().match( hReg3 ));
		
		// Same Region respect the Left Bumpers.
		assertTrue("Should be able to move from (4,4)", _pmc.cArrow(MoveType.LEFT) );
		assertTrue("Should be at Position(4,3) : ", _def_PM.getCurr().equals(new Position(4,3)));
		assertTrue("Should be able to move from (4,3)", _pmc.cArrow(MoveType.LEFT) );
		assertTrue("Should be at Position(4,2) : ", _def_PM.getCurr().equals(new Position(4,2)));
		assertFalse("Should NOT be able to move from (4,2)", _pmc.cArrow(MoveType.LEFT) );
		assertTrue("Should stay at Position(4,2) : ", _def_PM.getCurr().equals(new Position(4,2)));

		assertTrue("Current should be stuck on Left Bumper : ", _def_PM.getCurr().match(new Position(4,2)));
		assertTrue("Region should be the 3rd Horizontal Region at the bottom : ", _def_PM.getActiveRegion().match( hReg3 ));
		
		// Change Region to 1st Vertical Region, test Walls and Bumpers.
		_def_PM.setOrientation(1);
		_def_PM.setRegionAndCurr(new Position());
		assertTrue("Should have gone down one space : ", _pmc.cArrow(MoveType.DOWN));
		assertTrue("Should have gone down one space : ", _pmc.cArrow(MoveType.DOWN));

		assertFalse("Should not have moved, hit Bumper : ", _pmc.cArrow(MoveType.DOWN));
		
		assertFalse("The Region should NOT have changed orientation from VERTICAL : ", _def_PM.isHorizontal() );

		assertTrue("Should have gone up one space : ", _pmc.cArrow(MoveType.UP));
		assertTrue("Should have gone up one space : ", _pmc.cArrow(MoveType.UP));
		assertFalse("Should not have moved, hit Wall : ", _pmc.cArrow(MoveType.UP));

		assertFalse("The Region orientation should NOT have changed orientation from VERTICAL : ", _def_PM.isHorizontal() );
		assertTrue("The Region should be the 1st Vertical Region : ", _def_PM.getActiveRegion().match( vReg1 ));

		// Test Orientation flip in tight corners.  Move all the way RIGHT, then try to go down.
		_def_PM.setOrientation(0);
		_def_PM.setRegionAndCurr(new Position());
		
		_pmc.cArrow(MoveType.RIGHT);
		_pmc.cArrow(MoveType.RIGHT);

		
		// Call should change Orientation of board, and ActiveRegion, 
		// Curr should move to 2nd Position in new Regon, because Hint has no data.  This will require setting Region and Curr separately.
		_pmc.cArrow(MoveType.DOWN);
		assertFalse("The Orientation should have changed to Vertical : ", _def_PM.isHorizontal() );
		assertTrue("The Region should have changed to the 2nd Vertical Region : ", _def_PM.getActiveRegion().match( vReg2 ));
		assertTrue("Current should be in (1,2) : ", _def_PM.getCurr().match(new Position(1,2)));
		
		// Now change direction and end up in 3rd Hint, which is Horizontal
		_pmc.cArrow(MoveType.DOWN);
		assertFalse("The Orientation should remain Vertical : ", _def_PM.isHorizontal() );
		assertTrue("The Region should remain the 2nd Vertical Region : ", _def_PM.getActiveRegion().match( vReg2 ));
		assertTrue("Current should be in (2,2) : ", _def_PM.getCurr().match(new Position(2,2)));
		
		// Move Right into a constrained area.  Orientation and Region should change, and Curr should have moved 1 spc to the Right
		_pmc.cArrow(MoveType.RIGHT);
		assertTrue("The Orientation should now be Horizontal : ", _def_PM.isHorizontal() );
		assertTrue("The Region should now be the 2nd Horizontal Region : ", _def_PM.getActiveRegion().match( hReg2 ));
		assertTrue("Current should be in (2,3) : ", _def_PM.getCurr().match(new Position(2,3)));
		
		// Test Skip Internal Bumpers.  This should skip over Bumpers that are not connected to the Walls.
		_pmc.cArrow(MoveType.DOWN);
		assertTrue("The Orientation should still be Horizontal : ", _def_PM.isHorizontal() );
		assertTrue("The Region should now be the 3rd Horizontal Region : ", _def_PM.getActiveRegion().match( hReg3 ));
		assertTrue("Current should be in (4,2), because the Hint is empty : ", _def_PM.getCurr().match(new Position(4,2)));
		
		// Move to Position (3,2) 
		_pmc.cArrow(MoveType.UP);
		assertFalse("The Orientation should now be Vertical : ", _def_PM.isHorizontal() );
		assertTrue("The Region should now be the 2nd Vertical Region : ", _def_PM.getActiveRegion().match( vReg2 ));
		assertTrue("Current should be in (3,2) : ", _def_PM.getCurr().match(new Position(3,2)));

		// RIGHT Skip Bumper (3,3)
		_pmc.cArrow(MoveType.RIGHT);
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
		_pmc.cArrow(MoveType.DOWN);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 1st Vertical Region : ", _def_PM.getActiveRegion().match(vReg1));
		assertTrue("Current should be the 2nd space in this region, becuase that is the 1st empty space in it : ", _def_PM.getCurr().match(new Position(1,0)));
		
		_pmc.cArrow(MoveType.DOWN);
		assertTrue("Current should be (2,0) : ", _def_PM.getCurr().match(new Position(2,0)));
		
		// Move RIGHT into constrained region
		_pmc.cArrow(MoveType.RIGHT);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Horizontal Region : ", _def_PM.getActiveRegion().match(hReg2));
		assertTrue("Current should be the 2nd space in this region, because that is the 1st NEW empty space in it, and you left a blank spc : ", _def_PM.getCurr().match(new Position(2,1)));
		
		// Go RIGHT and then DOWN
		_pmc.cArrow(MoveType.RIGHT);
		assertTrue("Current should be the (2,2) : ", _def_PM.getCurr().match(new Position(2,2)));
		
		_pmc.cArrow(MoveType.DOWN);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Vertical Region : ", _def_PM.getActiveRegion().match(vReg2));
		assertTrue("Current should be the 4th spc in this region because where it came from was blank : ", _def_PM.getCurr().match(new Position(3,2)));
		
		_pmc.cArrow(MoveType.DOWN);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Vertical Region : ", _def_PM.getActiveRegion().match(vReg2));
		assertTrue("Current should be the (4,2) : ", _def_PM.getCurr().match(new Position(4,2)));
		
		// Go RIGHT into constrained Region that is partially full
		_pmc.cArrow(MoveType.RIGHT);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 3rd Horizontal Region : ", _def_PM.getActiveRegion().match(hReg3));
		assertTrue("Current should be the 3rd space in this region, because that is the 1st empty space in it : ", _def_PM.getCurr().match(new Position(4,4)));	
		
		// Go up to (2,4)
		_pmc.cArrow(MoveType.UP);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 3rd Vertical Region : ", _def_PM.getActiveRegion().match(vReg3));
		assertTrue("Current should be the (3,4) because just came from blank space : ", _def_PM.getCurr().match(new Position(3,4)));

		_pmc.cArrow(MoveType.UP);
		assertTrue("Current should be the (2,4) because just came from blank space : ", _def_PM.getCurr().match(new Position(2,4)));		
		
		// Go LEFT to (2,0)
		_pmc.cArrow(MoveType.LEFT);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Horizontal Region : ", _def_PM.getActiveRegion().match(hReg2));
		assertTrue("Current should be the 1st space in this region, because that is the 1st empty space in it and we came from a nonBlank space : ", _def_PM.getCurr().match(new Position(2,0)));	
		
		// Enter constrained Horizontal Region 1, from Vertical Region 2.
		_def_PM.setOrientation(1);
		_def_PM.setActiveRegion(new Position(0,2));
		_def_PM.setCurr(new Position(1,2));
		_pmc.cArrow(MoveType.UP);
		assertFalse("Orientation should be VERTICAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd VERTICAL Region : ", _def_PM.getActiveRegion().match(vReg2));
		assertTrue("Current should be the 1st space in this region, because we came from a blank space : ", _def_PM.getCurr().match(new Position(0,2)));			
		
		// Move Left one.
		_pmc.cArrow(MoveType.LEFT);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 1st Horizontal Region : ", _def_PM.getActiveRegion().match(hReg1));

		assertTrue("Current should be (0,0), because the Hint is full : ", _def_PM.getCurr().match(new Position(0,0)));
		_def_PM.currNext();
		assertTrue("Should move to Position(0,1) : ", _def_PM.getCurr().match(new Position(0,1)));
		
		// Skip bumper from current position.  Curr is always set to the 1st empty space when you skip bumpers.
		_pmc.cArrow(MoveType.DOWN);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 2nd Horizontal Region : ", _def_PM.getActiveRegion().match(hReg2));
		assertTrue("Current should be the 1st space in this region, we skipped and this is the 1st EMPTY spot : ", _def_PM.getCurr().match(new Position(2,0)));
		
		// Skip back up
		_pmc.cArrow(MoveType.RIGHT);
		assertTrue("Current should be (2,1) : ", _def_PM.getCurr().match(new Position(2,1)));
		_pmc.cArrow(MoveType.UP);
		assertTrue("Orientation should be HORIZONTAL : ", _def_PM.isHorizontal());
		assertTrue("The Region should be the 1st Horizontal Region : ", _def_PM.getActiveRegion().match(hReg1));
		assertTrue("Current should be the 1st space in this region, we skipped and this is the 1st spot in this full Hint: ", _def_PM.getCurr().match(new Position()));

	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cClick(jeune_Trie.Position)}.
	 */
	@Test
	public void testCClick() {
		
		
		// Test no board
		assertFalse("There is no board to work on : ", _pmc.cClick( new Position()));
		
		_pmc = boardFactory( ModelType.REGULAR );
		
		// Test invalid off of board
		assertFalse("This is an invalid Position : ", _pmc.cClick( new Position(5,5)));
		
		// Test invalid Bumper position
		assertFalse("This Position represents a bumper, and is invalid : ", _pmc.cClick(new Position(1,1)));
		
		Position pos1 = new Position(0,1);
		Position pos2 = new Position(0,2);
		Position pos3 = new Position(2,2);
		Position pos4 = new Position(4,3);
		
		ActiveRegion aRegion1 = new ActiveRegion( new Position(0,0), 3, 0);
		ActiveRegion aRegion2 = new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion aRegion3 = new ActiveRegion( new Position(2,0), 5, 0);
		ActiveRegion aRegion4 = new ActiveRegion( new Position(4,2), 3, 0);
		
		// Test in Region
		assertTrue("This should just move curr and be OK : ", _pmc.cClick( pos1 ) );
		assertTrue("This should just move curr and be OK : ", _pmc.cClick( pos2 ) );
		assertTrue("This should match the preselected Region : ", _def_PM.getActiveRegion().match(aRegion1));
		
		// Test same place, flip
		assertTrue("This should have flipped the orientation, and kept the same Position : ", _pmc.cClick( pos2 ));
		assertTrue("This should have flipped the orientation to VERTICAL : ", ( (_def_PM.getOrientation() % 2) == 1));
		assertTrue("The Position should have remained the same : ", _def_PM.getCurr().match( pos2 ));
		assertTrue("This should match the preselected Region : ", _def_PM.getActiveRegion().match(aRegion2));

		assertTrue("The Position is in the region so should remain the same, but with new Current : ", _pmc.cClick( pos3 ));
		assertTrue("This should have left the orientation to VERTICAL : ", ( ! (_def_PM.isHorizontal()) ));
		assertTrue("The Position should have remained the same : ", _def_PM.getCurr().match( pos3 ));
		assertTrue("The Region should have stayed the same : ", _def_PM.getActiveRegion().match(aRegion2));

		
		// An in region flip to HORIZONTAL
		assertTrue("The Region should flip becuase the same position is being clicked again : ", _pmc.cClick( pos3 ) );
		assertTrue("The orientation now should be HORIZONTAL : ", _def_PM.isHorizontal() );
		assertTrue("The Position should have remained the same : ", _def_PM.getCurr().match( pos3 ) );
		assertTrue("The Region should have flipped around the Position inputted into userClick() : ", _def_PM.getActiveRegion().match(aRegion3));
		
		// Test out of Region
		assertTrue("This should be true, and create a new region at the bottom of the board : ", _pmc.cClick( pos4 ));
		assertTrue("The Position should have remained the same as inputted : ", _def_PM.getCurr().match( pos4 ) );
		assertTrue("The Region should have changed : ", _def_PM.getActiveRegion().match(aRegion4));

	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cDone()}.
	 */
	@Test
	public void testCDone() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cFinishBoardSetup()}.
	 */
	@Test
	public void testCFinishBoardSetup() {
		
		// These functions call a PMController's functions to create the board.
		_pmc = boardFactory( ModelType.JAPANESE );
		
		// Test that Current has been set.  _def_PM is the model that is to be used for this controller for this test..
		assertTrue("Current should have been set to the 1st Empty position of the 1st Hint : ", ( null != _def_PM.getCurr()) );
		assertTrue("The place of Current should be 1, because that would be the place of the 1st position of the 1st Hint : ", ( 1 == _def_PM.getCurr().getPlace()));
		
		// Test that ActiveRegion has been set, and to the 1st Hint.
		assertTrue("Active Region should have been set : ", (null != _def_PM.getActiveRegion()));
		assertTrue("Active Region's start position should be #1, becuase it should represent the 1st Hint : ", ( 1 == _def_PM.getActiveRegion().getStartPos().getPlace()));

		// Test that the positions that start a Hint on the board have been numbered.
		assertTrue("There should be a positive amount of numbered start positions on the board : ", ( 0 < _def_PM.getStartPositions().size()));
		
	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cFlipBumper(jeune_Trie.Position)}.
	 */
	@Test
	public void testCFlipBumper() {
		
		// Test the REGULAR Board
		PMController pmc2 = boardFactory( ModelType.REGULAR ); 

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
		_def_PM = new PMModel();                   // Resetting Model
		final PMController pmc3 = boardFactory( ModelType.AMERICAN );
		
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

		final long[] byteRows2 = new long[ _def_PM.getSideLength() ];
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

		assertTrue("One or more bumpers were not properly flipped : ",wasFlipGood( _def_PM, byteRows2, _def_PM.getSideLength(), _def_PM.getSideLength() ));
		

		// Test British board's flipped bumpers.
		_def_PM = new PMModel();
		final PMController pmc4 = boardFactory( ModelType.BRITISH );
		
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

		final long[] byteRows3 = new long[ _def_PM.getSideLength()];		
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

		assertTrue("One or more bumpers were not properly flipped : ",wasFlipGood( _def_PM, byteRows3, _def_PM.getSideLength(), _def_PM.getSideLength() ));
		
	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cModify(char)}.
	 * Will require testing Writing and Erasing.
	 */
	@Test
	public void testCModify() {
		final char aChar = 'A';
		
		// Return false for NULL boards
		assertFalse("Function does not work for NULL boards: ", _pmc.cModify(aChar) );
		
		_pmc = boardFactory( ModelType.REGULAR );

		// HORIZONTAL
		// Should be able to write to the current position.
		assertTrue("This should be a valid space: ", _pmc.cModify(aChar));

		// Should now be on the next position.
		Position okPos = new Position(0,1);
		assertTrue("Should be on board position [0,1]: ", _def_PM.getCurr().equals(okPos));
		
		// Should still be in Active Region
		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should be able to go twice more, then stop.
		assertTrue("This should be a valid space: ", _pmc.cModify(aChar));
		assertFalse("This should be a valid space: ", _pmc.cModify(aChar));
		
		Position fPos = new Position(0,2);
		assertEquals("This position should have been written to, even though it returned false: ", aChar, _def_PM.getPlace_Created(fPos));
		
		assertFalse("Should not move because there is a bumper", _pmc.cModify(aChar));

		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should not allow to go past boundary
		final Position borderPos = new Position(2,4);
		final ActiveRegion actRegion = new ActiveRegion( new Position(2, 0, 3), 5, 0);        // The position should correspond with the 3rd hint.
		_def_PM.setCurr(borderPos);                                                     // Set Curr to this position
		_def_PM.setActiveRegion(borderPos);                                             // Set the region so the writeMove will work.
		
		assertFalse("Should not allow to go past board boundary, but should still write : ", _pmc.cModify(aChar));
		assertEquals( aChar + " should have been written to " + borderPos + " : ", aChar, _def_PM.getPlace_Created(borderPos));
		assertEquals("This should be the region that is occupied by the Active Region: ", actRegion, _def_PM.getActiveRegion());
		
		// VERTICAL
		_def_PM.setOrientation(1);                                                       // Orientation is now Vertical
		_def_PM.setActiveRegion(borderPos);                                              // Reset AR from borderPos's position, in the new orientation
		_def_PM.setCurr( borderPos);                                                     // Set curr to position within AR, should have a place of 4.

		char letter2 = 'B';
		
		// Should be able to write to the current position.
		assertTrue("This should be a valid space: ", _pmc.cModify( letter2 ));

		// Should now be on the next position.
		Position okPos2 = new Position(3,4);
		assertTrue("Should be on board position [3,4]: ", _def_PM.getCurr().equals(okPos2));
		
		// Should still be in Active Region
		assertTrue("Should still be in Active Region: ", _def_PM.isCurrInRegion());
		
		// Should be able to go twice more, then stop.
		assertTrue("This should be a valid move: ", _pmc.cModify( letter2 ));
		assertFalse("This should be an invalid move : ",_pmc.cModify( letter2 ));

		
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
		
		assertTrue("Should have printed a letter to cell (1,0), and moved curr to (2,0) : ", _pmc.cModify(letter3));

		assertTrue("This should be at position (2,0): ", _def_PM.getCurr().equals(blockPos));

		assertFalse("Should print a letter to cell (2,0) and not be able to move any further: ", _pmc.cModify(letter3) );
		assertFalse("Should print a letter at (2,0) and not be able to move: ", _pmc.cModify(letter2));

	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cSetBoardGame(jeune_Trie.UseType)}.
	 */
	@Test
	public void testCSetBoardGameType() {
		GameType gType = GameType.NXN;
		_pmc.cSetBoardGame( gType );
		assertTrue("Should have been set to " + gType + " : ", _def_PM.getGameType().equals(gType));

	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cSetBoardGame(jeune_Trie.GameType)}.
	 */
	@Test
	public void testCSetBoardUseType() {
		UseType uType = UseType.CREATE;
		
		_pmc.cSetBoardUse( uType );
		assertTrue("Should have been set to " + uType + " : ", _def_PM.getUseType().equals(uType));

	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cSetSize(int)}.
	 */
	@Test
	public void testCSetSize() {
		int sz = 5;
		
		_pmc.cSetSize( sz ) ;
		assertTrue("The size should be "+ sz + " : ", (_def_PM.getSideLength() == sz) );

	}

	/**
	 * Test method for {@link jeune_Trie.PMController#cTabEnter()}.
	 */
	@Test
	public void testCTabEnter() {
		
		ActiveRegion hTopRow = new ActiveRegion( new Position(), 3, 0);
		ActiveRegion hMidRow = new ActiveRegion( new Position(2,0), 5, 0);
		ActiveRegion hBotRow = new ActiveRegion( new Position(4,2), 3, 0);
		
		ActiveRegion vFirstCol= new ActiveRegion( new Position(0,0), 3, 1);
		ActiveRegion vMidCol= new ActiveRegion( new Position(0,2), 5, 1);
		ActiveRegion vLastCol= new ActiveRegion( new Position(2,4), 3, 1);
		
		// Should not work for NULL
		_pmc.cTabEnter();
		assertFalse("Cannot move board not set up yet : ", _def_PM.getActiveRegion().match(hMidRow));
		
		// CREATE BOARD
		PMController pmc2 = boardFactory( ModelType.REGULAR );
		
		// Go through across
		pmc2.cTabEnter();
		assertTrue("Should have moved to the second hint on the board : ", _def_PM.getActiveRegion().match(hMidRow));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		pmc2.cTabEnter();
		assertTrue("Should have moved to the third hint on the board : ", _def_PM.getActiveRegion().match(hBotRow));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		// Should now flip to VERTICAL and go through downs
		pmc2.cTabEnter();
		assertTrue("The orientation of the board should be VERTICAL now : ", ((_def_PM.getOrientation() % 2) == 1) );
		assertTrue("The region should match the first down hint : ", _def_PM.getActiveRegion().match(vFirstCol));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		pmc2.cTabEnter();
		assertTrue("Should have moved to the 2nd hint on the board : ", _def_PM.getActiveRegion().match(vMidCol));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		pmc2.cTabEnter();
		assertTrue("Should have moved to the 4th hint on the board : ", _def_PM.getActiveRegion().match(vLastCol));
		assertTrue("The current position should equal the head of this region : ", _def_PM.getCurr().match(_def_PM.getActiveRegion().getStartPos()));
		
		// Should now flip back to HORIZONTAL
		pmc2.cTabEnter();
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
		pmc2.cTabEnter();
		assertTrue("The orientation of the board should be VERTICAL now : ", ((_def_PM.getOrientation() % 2) == 1) );
		assertTrue("The region should match the first down hint : ", _def_PM.getActiveRegion().match(vFirstCol));
		assertTrue("The current position should equal to (1,0) : ", _def_PM.getCurr().match(v2ndHint1));
		
		// Should match 2nd Pos of 2nd Hint
		pmc2.cTabEnter();
		assertTrue("The region should match the 2nd down hint : ", _def_PM.getActiveRegion().match(vMidCol));
		assertTrue("The current position should equal to (1,2) : ", _def_PM.getCurr().match(v2ndHint2));
		
		// Should match 1st Pos 4th Hint
		pmc2.cTabEnter();
		assertTrue("The region should match the 4th down hint : ", _def_PM.getActiveRegion().match(vLastCol));
		assertTrue("The current position should equal to (2,4) : ", _def_PM.getCurr().match(v1stHint4));
		
		// Should become HORIZONTAL
		pmc2.cTabEnter();
		assertTrue("The orientation of the board should be HORIZONTAL now : ", ((_def_PM.getOrientation() % 2) == 0) );
		assertTrue("The region should match the first across hint : ", _def_PM.getActiveRegion().match(hTopRow));
		assertTrue("The current position should equal to (0,0) : ", _def_PM.getCurr().match(h1stHint1));
		
		// Go to the 3rd Hint
		pmc2.cTabEnter();
		assertTrue("The region should match the 3rd across hint : ", _def_PM.getActiveRegion().match(hMidRow));
		assertTrue("The current position should equal to (2,0) : ", _def_PM.getCurr().match(h1stHint3));
		
		// This should go the the 5th Hint
		pmc2.cTabEnter();
		assertTrue("The region should match the 5th across hint : ", _def_PM.getActiveRegion().match(hBotRow));
		assertTrue("The current position should equal to (4,2) : ", _def_PM.getCurr().match(h1stHint5));
		
		// Go from random points on the board.
		_def_PM.setRegionAndCurr(h1stHint3);
		pmc2.cTabEnter();
		assertTrue("The region should match the 5th across hint : ", _def_PM.getActiveRegion().match(hBotRow));
		assertTrue("The current position should equal to (4,2) : ", _def_PM.getCurr().match(h1stHint5));

	}
	
	// NEW
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
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _pmc.cAddWordDict(word1));
		assertEquals("This should hava 1 word in it, 1: ", 1, _def_PM.numWordsDict());
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _pmc.cAddWordDict(word2));
		assertFalse("This is a duplicate which is not allowed", _pmc.cAddWordDict(word2));
		assertEquals("This should have 2 words in it, 2: ", 2, _def_PM.numWordsDict());
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _pmc.cAddWordDict(word3));		
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _pmc.cAddWordDict(word4));		
		assertEquals("This should be allowed because this is not a duplicate, true: ", true, _pmc.cAddWordDict(word5));		
		assertEquals("This should have 5 words in it, 5: ", 5, _def_PM.numWordsDict());
		
		// Add non duplicates
		assertEquals( word6 + " is not a duplicate, true: ", true, _pmc.cAddWordDict(word6));
		assertEquals( word7 + " is not a duplicate, but is part of another word already in the tree, true: ", true, _pmc.cAddWordDict(word7));
		
		_pmc.cAddWordDict(badWord1);  // Check that this word has the puncuation removed.
		assertFalse("The word \""+ badWord2 + "\" should be in the tree: ", _pmc.cAddWordDict(badWord2));
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
		_pmc.cAddWordDict(find1);
		_pmc.cAddWordDict(find2);
		_pmc.cAddWordDict(find3);
		_pmc.cAddWordDict(find4);

		// Repeated removal, add cycles.
		assertEquals("This should be size "+ four +": ", four, _def_PM.numWordsDict() );
		assertTrue(find4 + " should be in the tree", _def_PM.findWordDict(find4));
		assertTrue("This should make " + find4 + " not a word in the tree: ", _pmc.cDeleteWordDict(find4));
		assertEquals("This should be size "+ three +": ", three, _def_PM.numWordsDict() );
		assertFalse(find4+" should not be found in the tree: ", _def_PM.findWordDict(find4));
		_pmc.cAddWordDict(find4);
		assertEquals("This should be size "+ four +": ", four, _def_PM.numWordsDict() );
		assertTrue(find4 + " should be in the tree", _def_PM.findWordDict(find4));
		assertTrue("This should make " + find4 + " not a word in the tree: ", _pmc.cDeleteWordDict(find4));
		assertEquals("This should be size "+ three +": ", three, _def_PM.numWordsDict() );
		assertFalse(find4+" should not be found in the tree: ", _def_PM.findWordDict(find4));
		
		// Delete words not in the tree
		assertFalse(noFind1 + " is in the tree, but is only part of a word", _pmc.cDeleteWordDict(noFind1));
		assertFalse(noFind2 + " is not in the tree", _pmc.cDeleteWordDict(noFind2));
		assertFalse(noFind2 + " is not in the tree", _pmc.cDeleteWordDict(noFind2));               // Repeat
		assertEquals("This should be size "+ three +": ", three, _def_PM.numWordsDict() );
		
		// 2x deletions
		assertFalse(find4 + " should have already been deleted: ", _pmc.cDeleteWordDict(find4));   // Second deletion
		assertTrue(find3 + " should be deleted: ", _pmc.cDeleteWordDict(find3));
		assertFalse(find3 + " has been previously deleted, cannot delete twice: ", _pmc.cDeleteWordDict(find3));
		
		// Add all words
		_pmc.cAddWordDict(find3);
		_pmc.cAddWordDict(find4);
		_pmc.cAddWordDict(noFind1);
		_pmc.cAddWordDict(noFind2);
		_pmc.cAddWordDict(noFind3);
		assertEquals("There should be " + seven + " words: ", seven, _def_PM.numWordsDict() );
		
		// Remove subWords then look to make sure removed.
		assertTrue(noFind1 + " should be allowed to be removed: ", _pmc.cDeleteWordDict(noFind1));
		assertTrue(noFind3 + " should be allowed to be removed: ", _pmc.cDeleteWordDict(noFind3));
		
		assertFalse(noFind1 + " has already been removed: ", _pmc.cDeleteWordDict(noFind1));
		assertFalse(noFind3 + " has already beeb removed: ", _pmc.cDeleteWordDict(noFind3));
		
		assertFalse(noFind1 + " should not be in the tree: ", _def_PM.findWordDict(noFind1));
		assertFalse(noFind3 + " should not be in the tree: ", _def_PM.findWordDict(noFind3));
		
		assertEquals("There should be " + five + " words: ", five, _def_PM.numWordsDict() );
		
		_pmc.cAddWordDict(noFind1);
		_pmc.cAddWordDict(noFind3);
		
		assertEquals("There should be " + seven + " words: ", seven, _def_PM.numWordsDict() );
		assertTrue(noFind1 + " should be in the tree: ", _def_PM.findWordDict(noFind1));
		assertTrue(noFind3 + " should be in the tree: ", _def_PM.findWordDict(noFind3));
		
	}
	
	/**
	 * This will tests the loadFile() to load files from the directory
	 */
	@Test 
	public void testLoadFile() {
		assertTrue("This should be true if the file was loaded correctly", _pmc.cLoadDict(new File("testFile.lst")));
		
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
		
		_pmc.cAddWordDict(find);
		_pmc.cAddWordDict(find1);
		_pmc.cAddWordDict(find2);
		_pmc.cAddWordDict(find3);
		_pmc.cAddWordDict(find4);
		_pmc.cAddWordDict(find5);
		_pmc.cAddWordDict(find6);

		_pmc.cAddWordDict(word1);
		_pmc.cAddWordDict(word2);
		_pmc.cAddWordDict(word3);
		_pmc.cAddWordDict(word4);
		_pmc.cAddWordDict(word5);
		_pmc.cAddWordDict(word6);
		_pmc.cAddWordDict(word7);
		
		ArrayList<String> testArr = new ArrayList<String>();
		
		// Suggestions for partial word: []re, len 7: == 0
		assertEquals("\"_re\" is not a prefix in this tree: ", 0, _pmc.cSuggestMatchDict("_re").size());
		
		// Suggestions for empty words give back empty lists
		assertEquals(" This should not return anything because an empty string is being inputted ", 0, _pmc.cSuggestMatchDict("").size());
		
		// Suggestions of whole words should just return the word.
		assertEquals( find1 + " is a whole word and should be the only word returned ", 1, _pmc.cSuggestMatchDict(find).size());
		assertEquals( find1 + " is a whole word and should be the only word returned ", find1, _pmc.cSuggestMatchDict(find1).get(0));
		
		// Suggestions for add_n_, should be addend, and adding
		String test = "add_n_";
		testArr.add(find4);
		testArr.add(find3);
		//for ( String item: testArr ) {
		for ( int index = 0; index < testArr.size(); index++ ) { 
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), _pmc.cSuggestMatchDict(test).get(index));
		}
		
		// Suggestions for _e__v__, should be removal, removed
		test = "_e__v__";
		testArr.clear();
		testArr.add(word1);
		testArr.add(word3);
		for ( int index = 0; index < testArr.size(); index++ ) {
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), _pmc.cSuggestMatchDict(test).get(index));
		}		
		
		// Suggestions for __d_n_, should be addend, adding, redone
		test = "__d_n_";
		testArr.clear();
		testArr.add(find4);
		testArr.add(find3);
		testArr.add(word4);
		for (int index = 0; index < testArr.size(); index++ ) {
			assertEquals( testArr.get(index) + " should be the word that is returned: ", testArr.get(index).toUpperCase(), _pmc.cSuggestMatchDict(test).get(index));
		}		
		
		// Suggestions for __m____, should be removal, removed, remodel
		test = "__m____";
		testArr.clear();
		testArr.add(word7);
		testArr.add(word1);
		testArr.add(word3);
		ArrayList<String> answer = _pmc.cSuggestMatchDict(test);
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
		
		_pmc.cAddWordDict(find);
		_pmc.cAddWordDict(find1);
		_pmc.cAddWordDict(find2);
		_pmc.cAddWordDict(find3);
		_pmc.cAddWordDict(find4);
		_pmc.cAddWordDict(find5);
		_pmc.cAddWordDict(find6);
		
		// Find words of length 1: False
		assertEquals("This should not return any results: ", 0, _pmc.cSuggestWordsOfLengthDict(1).size());

		// Find words of length 7: False
		assertEquals("This should not return any results: ", 0, _pmc.cSuggestWordsOfLengthDict(7).size());
		
		// Find words of length 17: False
		assertEquals("This should not return any results: ", 0, _pmc.cSuggestWordsOfLengthDict(18).size());
		
		// Find words of legnth 3
		assertEquals("This should find the word add: ", find5.toUpperCase(), _pmc.cSuggestWordsOfLengthDict(3).get(0));
		
		// Find words of length 8
		for ( int index = 0; index < 2; index++ ) {
			if ( index == 0 ) {
				assertEquals("This should find the word adderall: ", find2.toUpperCase(), _pmc.cSuggestWordsOfLengthDict(8).get(0));
			} else {
				assertEquals("This should find the word addition: ", find1.toUpperCase(), _pmc.cSuggestWordsOfLengthDict(8).get(1));			
			}
		}
		
		// Testing single letter words
		_pmc.cAddWordDict(find7);                                       
		_pmc.cAddWordDict(find8);
		
		ArrayList<String> treeAnsArr = _pmc.cSuggestWordsOfLengthDict(1);
		ArrayList<String> ansArr = new ArrayList<String>();
		
		ansArr.add(find7);
		ansArr.add(find8);
		
		for ( int ctr = 0; ctr < treeAnsArr.size(); ctr++ ) {
			assertEquals("This should find A: ", ansArr.get(ctr).toUpperCase(), treeAnsArr.get(ctr));
		}
			
	}

}
