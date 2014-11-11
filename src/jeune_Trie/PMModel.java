/**
 * 
 */
package jeune_Trie;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author vladimirjeune
 * This is the Data representation.  It will hold only the data and state with little connection
 * to anything else.
 */
public class PMModel implements PMModelInterface {

	/**
	 * 
	 */
	public PMModel() {
		init();
	}
	
	/**
	 * This is the default initializer.  This sets everything to an appropriate state so we can
	 * start up the game later.
	 * Preconditions:  NA
	 * Postconditions: This will be ready to start creating a puzzle.
	 */
	public void init() {
		_pmCreateBoard = null;                       // Creation will be done by user.
		_pmPlayBoard = null;                         // Will be assigned after board created
		_orientation = 0;                            // HORIZONTAL
		_size = 0;                                   // Board initially has no dimensions.
		_sizeAcross = 0;                             // Board initially has no dimensions.
		_sizeDown = 0;                               // Board initially has no dimensions.
		_isSetupDone = false;                        // Board initially has not been set up.
		_currPos = null; 							 // The current Position is not yet set
		_activeRegion = new ActiveRegion(); 		 // Will be set to 1 Across after bumper creation.
		_startPositions = new TreeSet<Position>();   // No Positions yet
		_acrossHints = new TreeMap<Position, String>();                         // No hints yet
		_downHints = new TreeMap<Position, String>();                         // No hints yet
		_theDictionary = new WordDictionary();                       // Later should look for one in local Dir
		_wordBank = new WordDictionary();                            // Will be added by user
		_useType = null;                             // Will be assigned CREATE or PLAY by user	
		_mirrorType = null;                          // Will be assigned by user
		_gameType = null;                            // Will be chosen by user
		_bumperList = new ArrayList<Position>();                          // Will be created at board finalization
	
		_CurrentObservers = new ArrayList<CurrentObserver>();              // List of Observers of the Current variable
		_RegionObservers = new ArrayList<RegionObserver>();                // List of Observers of the Region variable
		_BoardContentsObservers = new ArrayList<BoardContentsObserver>();  // List of Observers of the Board's data Content.
		_ClueObservers = new ArrayList<ClueObserver>();
		
	}
	
	/**
	 * SETNN will create a board that is of size NxN.
	 * Preconditions:  Should be used ONLY for the Creation of NxN boards.
	 * 				Subsequent uses of this function will have no effect.
	 * Postconditions: A board of dimension NxN will be created.
	 * @param aNum represents the size of the board.
	 */
	public void setSize( int aNum ) {

		if ( null == _pmCreateBoard ) {                   // If the board has not been created yet.
			_pmCreateBoard = new char[aNum][aNum];        // PLAY Board will be a copy of finished CREATE Board
			_size = aNum;
			_sizeAcross = aNum;
			_sizeDown = aNum;
		}
		
	}
	
	/**
	 * USERCLICK Sets Current to the inputted Position if move is valid.  If inputted Position is
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
	public boolean userClick( Position aPos ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();
		
		if ( ( null != theBoard ) && ( isValidSpace(aPos) ) ) {   // Checks to see if this is a Valid Space or a Bumper.

			if ( _currPos.match(aPos) ) {                         // If aPos matches Current's Position
				                                                  // Will not flip if not possible
				flipInPlace();                                    // Leave Current alone, flip board orientation, and Region

			} else {

				setCurr( aPos );                                  // We knew it is valid and not Curr

				if ( ! (isCurrInRegion()) ) {                     // If Current is NOT in Region anymore
					if ( ! ( setActiveRegion( aPos ) ) ) {
						flipOrientation();
						setActiveRegion(aPos);
					}
				} 

			}
			
			retVal = true;                                        // For testing purposes.
			
			System.out.println("USERCLICK() : " + _currPos );    // TMP
			// LATER:  Send Region Notification
			notifyRegionObservers();
			
		}
		
		return retVal;
	}
	
	/**
	 * CLUESETREGSTART Sets Current to the inputted position if move is valid with the current orientation.  
	 * The Position entered should be the start of a region
	 * If inputted Position is
	 * not in Region, new Region is set.  If inputted Position is in Region and not equal to 
	 * Current, then Current is repositioned.
	 * Preconditions:  The Board Must be Setup befor use.
	 * 				To be used to set Current to the start of regions.
	 * 				Inputted Position should not be null, and should be the start of a
	 * 				 Region on the board of the orientation that was passed in.
	 * Postconditions: The Position that was clicked on by the user will be the new Current Position.
	 * 				The Region will be reset if necessary so that it contains the Current Position and matches the orientation
	 * 				that was inputted into the function.
	 * 				Notifications may be sent if appropriate.
	 * @return aPos The new position entered by the user.
	 */
	public boolean clueSetRegStart( Position aPos, boolean isAcross ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();

		if ( ( null != theBoard ) && ( isValidSpace(aPos) ) ) {   // Checks to see if this is a Valid Space or a Bumper.

			if ( isAcross != isHorizontal() ) {                         // If aPos matches Current's Position

				flipOrientation();

			} 

			setCurr( aPos );                                  // We knew it is valid and not Curr
			setActiveRegion(aPos);
			retVal = true;                                        // For testing purposes.
			// LATER:  Send Region Notification
			
			System.out.println("CLUESETREGIONSTART() has set " + aPos);
			notifyRegionObservers();

		}

		return retVal;
	}
	
	/**
	 * USERTABENTER When the user presses a tab/enter, this function should go.
	 * It will go to the next Hint in the list of Across or Down hints depending on the orientation
	 * of the board.  When one list is done the orientation is flipped and the other list is used.
	 * Regions will move to the appropriate area, but Current will go to the 1st open area in a region,
	 * or if the region is filled, it will default to the 0th index in the Region.  
	 * This function should only be called if the board has already been set up.  If the board is 
	 * null it should do nothing.  If the board has not been set up and this is run action is undefined.
	 * Preconditions:  The board must be set up before use.
	 * Postconditions: The Region will be moved to the next Hint on the board depending on the board's 
	 * 				orientation at the time of the call.  Current will now occupy either the first open 
	 * 				space in the new Region, or, if the Region is filled, the first position in the new Region.
	 */
	public void userTabEnter() {
		char[][] theBoard = getBoard();
				
		if ( null != theBoard ) {

			int nextHintNum = (_activeRegion.getStartPos().getPlace() + 1);    // The current hint number.
			int maxHintNum = _startPositions.size();                     // The last allowable hint number.
			
			if ( nextHintNum > maxHintNum ) {                           // Flip Orientation, if we are at the end, restart at beginning.
				nextHintNum = 1;                                         // Reset to Start, with correct orientation.
				flipOrientation();
			} 

			nextGoodOrient( nextHintNum ) ;                              // Will set AR and Curr to the next hint properly.                        
			this.notifyRegionObservers();
		}
				
	}
	
	/**
	 * USERARROW Moves current when the user used the arrow keys to move it, and changes the place of 
	 * current on the board accordingly.  Should also move Active Region when appropriate, and flip
	 * orientation of the board when appropriate.  Board orientation will flip when Current ends
	 * up in a space where a Region cannot be formed, because there is not enough space for it at 
	 * the current Orientation.  A valid board should always have a way for a valid region to be 
	 * created.  If a situation exists where a valid region cannot be created around curr using 
	 * either orientation the board is not valid.  Bumpers and boundaries are respected, a user 
	 * cannot fall off the board, or enter an invalid space on the board.
	 * Preconditions:  The board must be set up before this function is called.
	 * Postconditions: The position of Current will be moved according to command entered by user.
	 * 				The board's orientation may be flipped in order to allow access to valid spaces
	 * 				on the board that would not inhabit a valid region in the current orientation.
	 * @param aMT Indicates the direction intended by the user.
	 * @return boolean Tells if the action was possible
	 */
	public boolean userArrow( MoveType aMT ) {
		boolean retVal = false;
		char[][] theBoard = getBoard() ;
		char aChar = getPlace_Created(_currPos);
		Position pokePos = poke( aMT );                                     // Poke in aMT's direction for on board spaces
		Position candPos = _currPos;
				
		if ( null != theBoard ) {                                                           // The board must exist, before use.

			if ( ( isHorizontal() || MoveType.isMoveHorizontal(aMT) ) 
					&& ( (!(isHorizontal())) || (!(MoveType.isMoveHorizontal(aMT))) ) ) {   // Discordant, Users move is against the current orientation
				// Discordant, MVMNT move to new region, possible flip orientation, if direction is constrained.
				//2 Types OPEN & CONSTRAINED.
				
				if ( (null != pokePos) && ( getPlace_Created(pokePos) != '@') ) {           // If move is valid and
					if ( -1 != findRegionLength( pokePos ) ) {                              // Normal Region, can create a viable region in current Orientation
						setRegionAndCurr( pokePos );
					} else {                                                                // Constrained Region, cannot make viable region in current Orientation
						flipOrientation();
						
						if ( 0 == aChar ) {                                                 // Came from blank space, just move to pokePos
							setActiveRegion( pokePos );
							setCurr( pokePos );
						} else {                                                            // Came from filled spc, move curr to empty space in Region.
							setRegionAndCurr( pokePos );
						}

					}
					retVal = true;

				} else if (  null != pokePos  ) {                                           // Poked a bumper, see if open area behind it.
					
					Position skipPos = skipWalls(pokePos, MoveType.isMoveHorizontal(aMT), MoveType.isMoveForward(aMT) ) ;                // If more open spaces in this orienation find them, else NULL.
					
					if ( null != skipPos ) {                                                // There are more spaces
						
						if ( -1 == findRegionLength( skipPos ) ) {                          // Entered a constrained Region, flip Orientation    
							flipOrientation();
						}         

						setRegionAndCurr( skipPos );                                        // Return true;
						
						retVal = true;
					}
				}
				  
			} else {                                                                        // Harmonious, Users move is with the boards orientaion.
				// Harmonious, MVMNT within Region, and go to next region
				
				// Forward/Backward.
				if ( MoveType.isMoveForward(aMT)) {       // Forward, no mater orient
					
					retVal = currNext();
					if ( ! retVal ) {                                                 // If we did not move forward, see if we can bypass obstruction
						// If poke == NUL, we are at a boundary, and return false.  Cannot move.
						// Else we are at a Bumpr, and can use skipWalls.  
						// If skipWalls returns NULL, we are at the end of the useable row, false.
						// Otherwise, return value of skipWalls is the nw curr, and must set Region, and curr.
						
						if ( null != pokePos ) {                                            // This Position is on the board.
							candPos = skipWalls(pokePos, MoveType.isMoveHorizontal(aMT), MoveType.isMoveForward(aMT) ) ;
							
							if ( null != candPos ) {                                        // There were on board Position behind the bumper(s);
								setRegionAndCurr(candPos);                                  // Set NW Region and Current
								retVal = true;
							}
						}
						
					}
					
				} else {                                                                    // Backward, no mater orient
					
					retVal = currPrev();
					if ( ! retVal ) {                                                 // If we did not move forward, see if we can bypass obstruction
						
						if ( null != pokePos ) {                                            // This Position is on the board.

							candPos = skipWalls(pokePos, MoveType.isMoveHorizontal(aMT), MoveType.isMoveForward(aMT) ) ;
							
							if ( null != candPos ) {                                        // There were on board Position behind the bumper(s);
								setRegionAndCurr(candPos);                                  // Set NW Region and Current
								retVal = true;
							}
						}
						
					}
					
				}                                                                           // End of Backwards Else
				
			}

		}		
		
		notifyRegionObservers();
		
		return retVal;
	}
	
	/**
	 * USERMODIFY will take a valid character and write it to the board.  If the character entered 
	 * is alpabetical, that character will be written to the board in the space now held by Curr.
	 * Then Curr will be moved to the next available space in the Region.  If we are at the last 
	 * space of the Region then all other write/clear modifications will be done from there.  
	 * Clear modifications will occur if a '0' is entered.  The space held by Current will be cleared
	 * of any data and Current will be moved to the previous space in the Region.  Modification operations
	 * are conained within the current Active Region, and will only occur in the Current position.
	 * Preconditions:  The board must be set up before use
	 * Postconditions: The Current Position will have data either inputted or erased, then Current
	 *				will move either to the next(write) or previous(clear) Position in the Active Region.
	 * @return boolean whether the operation can be carried out, and Current moved.
	 */
	public boolean userModify( char aChar ) {
		boolean retVal = false;
		
		if ( aChar == '0' ) {                     // User wants to erase what is at Current, and move Current backwards if possible
			retVal = clearMove();
		} else {
			retVal = writeMove(aChar);            // User wants to write aChar at Current and move Current forward if possible
		}
		
		notifyRegionObservers();
		
		return retVal;
	}
	
	/**
	 * FINDHINTPOSITION Returns the Position that corresponds with the number that was passed in.
	 * Numbers should be in range of the hints on the board.
	 * Preconditions:  The board must already be set up before this function is used.
	 * Postconditions: The Hint Position that relates to the number inputted will be returned.
	 * 				Or NULL if hintNum is not found
	 * @param hintNum The number of the hint position we are trying to find.
	 * @return Position The Position that corresponds to the number entered.
	 */
	public Position findHintPosition( int hintNum ) {
		char[][] theBoard = getBoard();

		if ( ( null != theBoard ) 
				&& ( hintNum > 0 ) 
				&& ( hintNum <= _startPositions.size() ) ) {            // If Board exists, and hints are within range.

			for ( Position pos : _startPositions ) {
				if ( pos.getPlace() == hintNum ) {
					return pos;
				}
			}
		}
		
		return null;                                                    // Should not get here
	}
	
	/**
	 * NEXTGOODORIENT Gives back the next Position that corresponds to a hint with the proper orientation 
	 * for a Tab or Enter movement.
	 * Preconditions:  The board must be set up before this function is called.
	 * Postconditions: The Region that is appropriate, depending on the current orientation will be returned.
	 * @param aNum The number to start from when looking for the next Region to occupy.
	 */
	public void nextGoodOrient( final int aNum ) {
		char[][] theBoard = getBoard();
		int nextNum = aNum;

		if ( null != theBoard ) {
			int maxSize = _startPositions.size();                        // Number of hints on board
			Position candPos = findHintPosition( nextNum );              // Find position corresponding to inputted number

			if ( isHorizontal() ) {                                      // Looking for HORIZONTAL Hints

				while ( (null != candPos) 
						&& ( ! (_acrossHints.containsKey( candPos ) ) ) 
						&& ( nextNum < ( maxSize + 1) ) ) {              // While inputd Pos does NOT match a hint

					candPos = findHintPosition( ++nextNum );             // Get diff Positions to test

				}

				if ( (null != candPos)
						&&( _acrossHints.containsKey( candPos ) ) ) {    // If we found something, set Region and Curr

					setRegionAndCurr( candPos );                         // Set Region and curr using found Position, will take board's current orientation..

				} else if ( null == candPos ) {                          // No more Hints w this Orient, try again after flipping
				
					flipOrientation();
					setRegionAndCurr( findHintPosition( 1 ) );           // Set with flipped Orientation to 1.

				}

			} else {                                                     // Looking for VERTICAL Hints

				while ( (null != candPos ) 
						&& ( ! (_downHints.containsKey( candPos ) ) )
						&& ( nextNum < ( maxSize + 1) ) ) {              // While inputd Pos does NOT match a hint

					candPos = findHintPosition( ++nextNum );             // Get diff Positions to test

				}

				if ( (null != candPos)
						&&( _downHints.containsKey( candPos ) ) ) {      // If we found something, set Region and Curr

					setRegionAndCurr( candPos );                         // Set Region and curr using found Position, will take board's current orientation..

				} else if ( null == candPos ) {                          // No more Hints w this Orient. Reset to start.

					flipOrientation();
					setRegionAndCurr( findHintPosition( 1 ) );           // Set with flipped Orientation to 1.

				}      

			}                                                            // End of VERTICAL else

		}                                                                // End of NULL board else

	}
	
	/**
	 * GETSIDELENGTH should only be used with NxN boards, other boards will not make sense.
	 * Preconditions:  The use type of the board should be NxN.  Criss Cross boards do not 
	 * have a reliable definition for sidelength
	 * Preconditions:  NA
	 * Postconditions:  The side length of an NxN board will be returned.
	 */
	public int getSideLength() {
		return _size;
	}
	
	/**
	 * GETCURR will return the current active position on the board.  
	 * Preconditions:  NA
	 * Postconditions:  The current active position will be returned
	 */
	public Position getCurr() {
		return _currPos;
	}
	
	/**
	 * GETACTIVEREGION will return the region that is active on the board at the time
	 * of the function call.
	 * Preconditions:  NA
	 * Postconditinos: The current active region will be returned
	 */
	public ActiveRegion getActiveRegion() {
		return _activeRegion;
	}
	
	/**
	 * GETSTARTPOSITIONS returns all the starting positions on the board.
	 * Preconditions:  NA
	 * Postconditions: Returns all the start positions for words on the board.
	 */
	public Set<Position> getStartPositions() {
		return _startPositions;
	}
	
	/**
	 * GETACROSSHINTS will return a map of all start positions to the hints on the board, 
	 * that are going across.
	 * If the board has not been set up yet, it will return null.
	 * Preconditions:  NA
	 * Postconditions: A map of positions to hints that go across the board will be returned.
	 */
	public Map<Position, String> getAcrossHints() {
		return _acrossHints;
	}
	
	/**
	 * GETDOWNHINTS will return a map of all start positions to the hints on the board, that are 
	 * going down.
	 * If the board has not been set up yet, it will return null.
	 * Preconditions:  NA
	 * Postconditions: A map of positions to hints that go across the board will be returned.
	 */
	public Map<Position, String> getDownHints() {
		return _downHints;
	}
	
	/**
	 * GETDICTIONARY returns the dictionary that comes with this instance of the game.
	 * Preconditions:  NA
	 * Postconditions: The dictionary will be returned
	 */
	public WordDictionary getDictionary() {
		return _theDictionary;
	}
	
	/**
	 * GETWORDBANK returns the words that the user inputted to be used in certain types of 
	 * puzzles.
	 * Preconditions:  NA
	 * Postconditions: Returns that words in the word bank
	 */
	public WordDictionary getWordBank() {
		return _wordBank;
	}
		
	/**
	 * SETUSETYPE sets the use that we are currently using on the board.  Either Creating a puzzle
	 * or Playing a puzzle.
	 * Preconditions:  Set when application is first started.
	 * Postconditions: The use of this board will be set, and the appropriate functions will act accordingly.
	 * @param aUseType the use type that is wanted for the board.  Either Creation or Play.
	 */
	public void setUseType( UseType aUseType ) {
		_useType = aUseType;
	}
	
	/**
	 * GETUSETYPE get the use that the board is currently using.
	 * Preconditions:  NA
	 * Postconditions: The use of this board will be returned.  
	 */
	public UseType getUseType() {
		return _useType;
	}
	
	/**
	 * SETGAMETYPE sets the type of game that is being played.  This is either N x N, or Criss Cross.
	 * Preconditions:  NA
	 * Postconditions: The gametype of the board will be set.  Either N x N or Criss Cross
	 */
	public void setGameType( GameType aGameType ) {
		_gameType = aGameType;
	}
	
	/**
	 * GETGAMETYPE gets the game type that is being used, either N x N, or Criss Cross.
	 * Preconditions:  NA
	 * Postconditions: The game type of this instance is returned.
	 */
	public GameType getGameType() {
		return _gameType;
	}

	/**
	 * SETMIRRORTYPE sets the type of mirror that will be used for the bumpers during board creation
	 * Preconditions:  NA
	 * Postconditions: The Mirror type of the board will be set: FOURWAY, FLAT, FLATFLIP, UPHILL, DOWNHILL
	 */
	public void setMirrorType( MirrorType aMirrorType ) {
		_mirrorType = aMirrorType;
	}
	
	/**
	 * GETMIRRORTYPE returns the type of mirroring that was used in the creation of the game board
	 * Preconditions:  NA
	 * Postconditions: The mirror type of this instance of the game board will be returned
	 */
	public MirrorType getMirrorType() {
		return _mirrorType;
	}
	
	/**
	 * GETORIENTATION will return the current orientation setting the board is on.
	 * Preconditions:  NA
	 * Postconditions: The orientation will be returned
	 */
	public int getOrientation() {
		return _orientation;
	}
	
	/** 
	 * SETORIENTATION sets the orientation of the current region of the board.
	 * Preconditions:  NA
	 * Postconditions: The orientation for the board will be set.
	 */
	public void setOrientation( int anOrientation ) {
		_orientation = anOrientation;
	}
	
	/**
	 * FLIPORIENTATION flips the orienation of the board to the opposite of the current orientation
	 * Preconditions:  NA
	 * Postconditions: The orientation for the board will be flipped so that new regions are of that 
	 * 				orientation
 	 */
	public void flipOrientation() {
		_orientation++;
	}

	/**
	 * FINDALLSTARTPOSITIONS will find all the start positions for words laid out from Top to Bottom
	 * or Right to Left.  It returns the set of all start positions of the board.
	 * This function is only for NxN boards, and has no effect on other board Types.
	 * Preconditions:  The board must have the bumpers already placed on it before this is called.
	 * 				This function will only have an effect for NxN boards, during board creation.
	 * Postconditions: The set of all starting positions on the board will be returned, unless the
	 * 				board being used is not NxN.  In that case NULL will be returned.
	 */
	public void findAllStartPositions() {
		

		if ( UseType.CREATE.equals(_useType) && (GameType.NXN.equals(_gameType))) {
			for ( int itr = 0; itr < _size; itr++ ) {
				findRowStartPositions( new Position( itr, 0 ));            // Find all the starting positions in this row.
				findColStartPositions( new Position( 0, itr ));            // Find all the starting positions in this column.
			}
		}
		
	}
	
	/**
	 * NUMBERSTARTPOSITIONS numbers the starting positions that were found on the board after
	 * the bumpers were placed by the user.  Can be used by NxN and CRISSCROSS boards.
	 * Preconditions:  The board must have had bumpers placed on it if bumpers were wanted.  Otherwise
	 * 				numbering will occur without bumpers.  Only for use during board Creation.
	 * Postconditions: The starting positions will be numbered, and returned in a 
	 * 				Collection.  
	 */
	public Set<Position> numberStartPositions() {
		Set<Position> retVal = null;

		if ( UseType.CREATE.equals(_useType) && (GameType.NXN.equals(_gameType))) {
			findAllStartPositions();

			int cnt = 0;
			Set<Position> acrossSet = _acrossHints.keySet();
			Set<Position> downSet = _downHints.keySet();
			ArrayList<Position> allHintsArr = new ArrayList<Position>();     // Will hold all Across and Down Hints
			allHintsArr.addAll( acrossSet );                                 // Adding Across Hints
			allHintsArr.addAll( downSet );                                   // Adding Down Hints
			
			for ( Position pos : _startPositions ) {                         // _startPositions is a SortedSet

				pos.setPlace(++cnt);                                         // Numbering starts at 1.

				for ( Position place : allHintsArr ) {                       // Making sure down hints have same Place value as acrossHnts, if the same place is Across and Down.
					if ( pos.match(place)) {
						place.setPlace(cnt);                                 // Set to the same value.
					}
				}

			}

			return _startPositions;
		} else {
			// Stuff for CrissCross
		}

		return retVal;    // Fix when doing crisscross
	}
	
	/**
	 * FINDROWSTARTPOSITIONS all the row start positions on this board have been created.  
	 * It should be able to find all the across start positions that 
	 * are created by the bumper pattern that was entered by the user.
	 * After the creation of the bumpers for the board, any open spaces that are 
	 * greater than minimum word size going from left to right 
	 * should be properly marked by a starting position at its leftmost point.
	 * The board must have the bumpers already placed on it before this is called.
	 * Preconditions:  The board must have the bumpers already placed on it before this is called.
	 * 				Does not accept NULL.
	 * Postconditions: The start positions for all row words on the board will be found, properly stored
	 * 				and returned from this function as in a Collection  
	 * @param startPosition indicates the start of the row that should be searched for row start positions.
	 */
	public void findRowStartPositions( Position startPosition ) {
		int length = 0;

		Position startPos = skipWalls( startPosition, true );          // Skip any walls before starting

		if ( startPos != null ) {                                       // This is a clear position
			
			// HORIZONTAL FIRST
			final int rowNum = startPos.getRow();
			final int colNum = startPos.getCol();

			// ONLY WORKS FOR ONE LINE

			for ( int itr = startPos.getCol(); itr < _size;  ) {
				
				while (( itr < _size ) && ( _pmCreateBoard[rowNum][itr] != '@')){
                    length++;
					itr++;                              // incr
				}
				
				if (( itr == _size ) || ( (itr < _size ) && ( _pmCreateBoard[rowNum][itr] == '@' ) )) {    // If word goes to board boundaries, or Position is a bumper
					
					if ( length > (_minWordSize - 1) ) {                 // If valid word size, add start position
						Position nwPos = new Position(startPos.getRow(), startPos.getCol());           // No outside references
						_startPositions.add(nwPos);           // No outside references
						_acrossHints.put(nwPos, "");
						
					}
					
					if (( itr < _size ) && ( _pmCreateBoard[rowNum][itr] == '@' )) {         // We may be in middle of board
						startPos.setCol( itr );                         // Make startPos == to @ position
						startPos = skipWalls( startPos, true );         // Find next start candidate in row
						length = 0;                                     // Reset for next iteration
						
						if ( startPos != null ) {
							itr = startPos.getCol();                        // Needed for correct iteration
						} else {                                        // We may be at last bumper, should check
							itr = _size;
						}

					}
										
				}
				
			}
			
		}
				
	}
	
	/**
	 * FINDCOLSTARTPOSITIONS all the start positions on this board have been created.  
	 * It should be able to find all the down start positions that 
	 * are created by the bumper pattern that was entered by the user.
	 * After the creation of the bumpers for the board, any open spaces that are 
	 * greater than minimum word size going from from top to bottom
	 * should be properly marked by a starting position at its topmost point.
	 * The board must have the bumpers already placed on it before this is called.
	 * Preconditions:  The board must have the bumpers already placed on it before this is called.
	 * 				Does not accept NULL.
	 * Postconditions: The start positions for all words on the board will be found, properly stored
	 * 				and returned from this function in a Collection  
	 * @param startPosition the position indicating the start of the column to be searched for 
	 * 		starting positions.
	 */
	public void findColStartPositions( Position startPosition ) {
		int length = 0;

		Position startPos = skipWalls( startPosition, false );          // Skip any walls before starting

		if ( startPos != null ) {                                       // This is a clear position
			
			// HORIZONTAL FIRST
			final int rowNum = startPos.getRow();
			final int colNum = startPos.getCol();

			// ONLY WORKS FOR ONE LINE
			for ( int itr = startPos.getRow(); itr < _size;  ) {
				
				while (( itr < _size ) && ( _pmCreateBoard[itr][colNum] != '@')){
                    length++;
					itr++;                              // incr
				}
				
				if (( itr == _size ) || ( (itr < _size ) && ( _pmCreateBoard[itr][colNum] == '@' ) )) {    // If word goes to board boundaries, or Position is a bumper
					
					if ( length > (_minWordSize - 1) ) {                 // If valid word size, add start position
						Position nwPos = new Position(startPos.getRow(), startPos.getCol());           // No outside references
						_startPositions.add(nwPos);           // No outside references
						_downHints.put(nwPos, "");
					}
					
					if (( itr < _size ) && ( _pmCreateBoard[itr][colNum] == '@' )) {         // We may be in middle of board
						startPos.setRow( itr );                         // Make startPos == to @ position
						startPos = skipWalls( startPos, false );         // Find next start candidate in row
						length = 0;                                     // Reset for next iteration
						
						if ( startPos != null ) {
							itr = startPos.getRow();                        // Needed for correct iteration
						} else {                                        // We may be at last bumper, should check
							itr = _size;
						}

					}
										
				}
				
			}
			
		}
				
	}
	
	/**
	 * SKIPWALLS will skip wall characters when traversing the array, and return the first 
	 * clear position.  It will start from the position that was entered.  If that position
	 * was clear then that position will be returned.  The board can be traversed from Left to Right
	 * or from Top to Bottom, depending of the second parameter.  1 means Horizontal traversal, 0 means
	 * vertical.
	 * Preconditions:  The board must exists before calling.  This is only for use during board
	 * 				creation.  Does not accept NULL.
	 * Postconditions: A Position is returned of the first clear space that was found from
	 * 				iterating in the specified direction.  NULL is returned if we run out of 
	 * 				space before an empty position is found.  NULL is returned if this function is used during play.
	 * 				NULL is returned if the Position entered is not valid
	 * @param aLoc the location that we are to start skipping from
	 * @param isHorizontal boolean to tell how the board is to be traversed.
	 */
	public Position skipWalls( final Position aLoc, boolean isHorizontal ) {

		if (  ( _pmCreateBoard != null ) 
				&& ( null != aLoc )
				&& ( this.isPositionInBounds(aLoc))) {           // If we are in game creation, and there is a board, and location entered is valid
			final int rowNum = aLoc.getRow();
			final int colNum = aLoc.getCol();
			
			if ( _pmCreateBoard[rowNum][colNum] != '@' ) {       // If this is a space already
			
				return new Position(rowNum, colNum );            // Set to position entered

			} else {                                             // We must find a space

				if ( isHorizontal ) {                            // Go Left to Right
					int itr = colNum;

					while (( itr < _size ) && (_pmCreateBoard[rowNum][itr] == '@')) {
						itr++;                                   // incr
					}

					if ( itr != _size ) {                        // If have not reached end of line, there must be a start position
						
						return new Position(rowNum, itr);        // Set to new clear position

					}

				} else {                                         // Go Top to Bottom
					int itr = rowNum;

					while (( itr < _size ) && (_pmCreateBoard[itr][colNum] == '@')) {
						itr++;
					}

					if ( itr != _size ) {                         // If have not reached end of line, there must be a start position

						return new Position( itr, colNum);        // Set to new clear position

					}

				}

			}

		}

		return null;
	}

	/**
	 * SKIPWALLS will skip wall characters when traversing the array, and return the first 
	 * clear position.  It will start from the position that was entered.  If that position
	 * was clear then that position will be returned.  The board can be traversed from Left to Right
	 * or from Top to Bottom, depending of the second parameter.  1 means Horizontal traversal, 0 means
	 * vertical.
	 * Preconditions:  The board must exists before calling.  This is only for use during board
	 * 				creation.  Does not accept NULL.
	 * Postconditions: A Position is returned of the first clear space that was found from
	 * 				iterating in the specified direction.  NULL is returned if we run out of 
	 * 				space before an empty position is found.  NULL is returned if this function is used during play.
	 * 				NULL is returned if the Position entered is not valid
	 * @param aLoc the location that we are to start skipping from
	 * @param isHorizontal boolean to tell how the board is to be traversed.
	 */
	public Position skipWalls( final Position aLoc, boolean isHorizontal, boolean isForward ) {

		if (  ( _pmCreateBoard != null ) 
				&& ( null != aLoc )
				&& ( this.isPositionInBounds(aLoc))) {           // If we are in game creation, and there is a board, and location entered is valid
			final int rowNum = aLoc.getRow();
			final int colNum = aLoc.getCol();
			
			if ( _pmCreateBoard[rowNum][colNum] != '@' ) {       // If this is a space already
			
				return new Position(rowNum, colNum );            // Set to position entered

			} else {                                             // We must find a space

				if ( isHorizontal ) {                            // Go Left to Right
					int itr = colNum;

					while (( itr > -1 ) 
							&& ( itr < _size )
							&& (_pmCreateBoard[rowNum][itr] == '@')) {      ///  While in range for board

						itr = itr + ((isForward)? 1 : -1 );                 /// 1 for Forward, -1 for Backward
					}

					if ( ( itr != -1 ) && ( itr != _size ) ) {   // If have not reached end of line, there must be a start position
						
						return new Position(rowNum, itr);        // Set to new clear position

					}

				} else {                                         // Go Top to Bottom
					int itr = rowNum;

					while (( itr > -1 ) 
							&& ( itr < _size ) 
							&& (_pmCreateBoard[itr][colNum] == '@')) {      /// While in range for board

						itr = itr + ((isForward)? 1 : -1 );                 /// 1 for Forward, -1 for Backward
					}

					if ( ( itr != -1 ) && ( itr != _size ) ) {    // If have not reached end of line, there must be a start position

						return new Position( itr, colNum);        // Set to new clear position

					}

				}

			}

		}

		return null;
	}
	
	/**
	 * POKE will tell whether the current position can go in the direction indicated by the MoveType
	 * that was passed in.  If the poke() indicates that a direction is off the board, NULL will be returned.
	 * Otherwise, the Position that was poked will be returned.
	 * Preconditions:  The board must be set up before this function is called.
	 * Postconditions: The poked Position will be returned, or NULL.  Depending on whether there is 
	 * 				an on board space in the direction indicated by the MoveType entered.
	 * 				Bumpers are valid in this function.
	 * @param aMove is a direction for this function to poke.
	 * @return Either the Position that was poked, or NULL.
	 */
	public Position poke( MoveType aMove ) {
		char[][] theBoard = getBoard();
		Position retVal = null; 

		if ( null != theBoard ) {
			int rowNum = _currPos.getRow();
			int colNum = _currPos.getCol();

			if ( ! (MoveType.isMoveHorizontal(aMove)) ) {                  // VERTICAL

				if ( MoveType.DOWN.equals(aMove)) {                        // Incr rows
					rowNum++;
				} else {                                                   // Going UP decr rows
					rowNum--;
				}

			} else {                                                       // HORIZONTAL

				if ( MoveType.RIGHT.equals(aMove )) {                      // Incr Cols
					colNum++;                        
				} else {                                                   // Going LEFT decr cols
					colNum--;
				}

			}

			retVal = new Position( rowNum, colNum ) ;                      // New Pos with modified Rows or Cols 
		}
		return ( (this.isPositionInBounds(retVal))? retVal : null );       // Only return a Position if on the board.
	}
	
	/**
	 * SETREGIONANDCURR sets the region for the board from the position inputted.  Curr is set to 
	 * the first open position that is found in the board's Active Region.
	 * Preconditions:  The board must be present before use of this function.  Can be used for 
	 * 				CREATE and PLAY.
	 * Postconditions: A region will be set on the board according to the position «that was inputted
	 * 				and the current position will be set depending on the first open position on the region
	 * 				if the region is filled, the first position will made curr. 
	 */
	public void setRegionAndCurr( final Position aPos ) {
		char[][] theBoard = getBoard();
		
		if ( ( null != theBoard ) 
				&& ( null != aPos ) ) {                                       // If there is a board to work with
			final Position pos = aPos;

			if ( isValidSpace(pos) ) {
				setActiveRegion( pos );                                 // Will find the active region for this position.
				
				Position tmPos = firstSpaceInRegion(_activeRegion);     // Finding empty space if exists
				if (( -1 == tmPos.getCol() ) || ( -1 == tmPos.getRow() )) {
					setCurr( _activeRegion.getStartPos() );             // Not found, set curr to beginning of region
				} else {
					findStartPos( tmPos );                              // If matches a startPosition, will make its place equal to the start Position
					setCurr( tmPos );                                   // Set curr to first empty space
				}
			}

		}
		
		return;		
	}
	
	/**
	 * FIRSTSPACEINREGION finds the first open space in the region that was passed in; if it exists.
	 * If there is no space it returns a Position with a -1 in it.  This will be used when moving from 
	 * one hint to another using the arrow buttons instead of the mouse.
	 * Preconditions:  Should be used after having moved from one region to another using the keyboard.
	 * Postconditions: If no spaces found returns a Position with one index == -1.  Otherwise, Returns that 
	 * 				new currentPosition in the new region passed in.
	 */
	public Position firstSpaceInRegion( ActiveRegion aRegion ) {
		char[][] theBoard = getBoard(); 
		Position retVal = new Position();  // Needs to be set before returned.
		String line = "";
		int spcIndex = -1;
		int retIndex = -1;  // NEW
		
		if ( ( null != theBoard ) 
				&& ( null != aRegion ) ) {
			line = stringInRegion( aRegion );
			spcIndex = line.indexOf(_spcChar);

			if ( (aRegion.getOrientation() % 2) == 0 ) {
				retIndex = ( (spcIndex == -1) ? spcIndex : (spcIndex + aRegion.getStartPos().getCol()) );        // If spcIndex == -1 return it.  Else return index.
				retVal.setPosition(aRegion.getStartPos().getRow(), retIndex  );			
			} else {
				retIndex = ( (spcIndex == -1) ? spcIndex : (spcIndex + aRegion.getStartPos().getRow()) );
				retVal.setPosition( retIndex, aRegion.getStartPos().getCol() );			
			}
		} else {
			retVal.setRow(-1);
			retVal.setCol(-1);
		}
		
		return retVal;
	}
	
	/**
	 * STRINGINREGION takes a region and returns a string representing what is on the board in that
	 * region.
	 * Preconditions:  The board must exist.  Does not accept NULL Regions.
	 * 				Region entered must exists as a region on the board, otherwise results will be incorrect
	 * Postconditions: A String that represents the contents of the region passed in as it relates to 
	 * 				the game board.
	 * @param aRegion 
	 */
	public String stringInRegion( ActiveRegion aRegion ) {
		char[][] board = getBoard(); 
		int i = 0;
		int j = 0;
		char[] seq = new char[aRegion.getLength()];
		int row = aRegion.getStartPos().getRow();
		int col = aRegion.getStartPos().getCol();
		int orientation = aRegion.getOrientation();
		int length = aRegion.getLength();
		
		if ( null != board  ) {                                   // The board must exist
			
			if ( ( orientation % 2 ) == 0 ) {                    // Collect String Horizontal
				for ( i = 0, j = col; i < length; i++, j++) {   
					seq[ i ] = ( (board[row][j] == 0) ? ' ' : board[row][j]);
				}

			} else {                                                          // Collect String Vertical
				for ( i = 0, j = row; i < length; i++, j++) {   
					seq[ i ] = (( board[j][col] == 0) ? ' ' : board[j][col]);
				}			
			}
		}

		return new String( seq );                                         // Return String
	}
	
	
	/**
	 * SETCURR will set the current position to the place indicated in the parameter if it is valid.
	 * Otherwise false is returned and no action is taken.
	 * Preconditions:  The boards MUST already be available to be positioned.  	Bumpers should already 
	 * 				be on the board, since moving them invalidates all previously valid regions.  
	 * Postconditions: Curr will be set to the position in the parameter list if that is a valid place,
	 * 			 	meaning on the board and not a bumper.
	 * @param aPosition the place you want to set curr to.
	 */
	public boolean setCurr( Position aPosition ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();

		if ( ( null != theBoard) 
				&& ( null != aPosition )
				&& ( isValidSpace(aPosition)) ) {     // If a valid position
			_currPos = aPosition;                                      // Set current position
			retVal = true;                                             // passed
		}
		
		return retVal;
	}
	
	/**
	 * CURRNEXT will set current to the next position, according to the current orientation.  But, only if
	 * that would be a valid position.  Otherwise, it will do nothing.
	 * This means it is stopped by Bumpers, and Boundaries.
	 * Preconditions:   The boards MUST already be set up; with bumpers and starting positions.
	 * Postconditions: Curr will be moved to the right, or down, according to the current orientation.  But,
	 * 				only if that would be a valid position.  If not, no action is taken.
	 * @param boolean Whether Current can be moved.
	 */
	public boolean currNext() {
		boolean retVal = false;
		char[][] theBoard = getBoard();
		Position currentPos = null;

		if ( null != theBoard ) {                                                      // Is there a board
			int currRow = _currPos.getRow();
			int currCol = _currPos.getCol();
			
			if ( isValidSpace( _currPos )) {                                           // Is Curr valid
				
				if ( 0 == (_orientation % 2) ) {                                       // HORIZONTAL
					currentPos = new Position( currRow, (currCol + 1) ) ;
				} else {                                                               // VERTICAL
					currentPos = new Position( (currRow + 1), currCol );
				}

				if ( isValidSpace( currentPos ) ) {                                    // Does this new position exists on the board
					_currPos = currentPos;                                             // Assignment
					retVal = true;												       // Passed
				}
			}
		}
				
		return retVal;
	}
	
	/**
	 * CURRPREV will set current to the previous position, according to the current orientation.  But, only if
	 * that would be a valid position.  Otherwise, it will do nothing.
	 * This means it is stopped by Bumpers, and Boundaries.
	 * Preconditions:   The boards MUST already be set up; with bumpers and starting positions.
	 * Postconditions: Curr will be moved to the left, or up, according to the current orientation.  But,
	 * 				only if that would be a valid position.  If not, no action is taken.
	 * @param boolean Whether Current can be moved.
	 */
	public boolean currPrev() {
		boolean retVal = false;
		char[][] theBoard = getBoard();
		Position currentPos = null;

		if ( null != theBoard ) {                                                      // Is there a board
			int currRow = _currPos.getRow();
			int currCol = _currPos.getCol();
			
			if ( isValidSpace( _currPos )) {                                           // Is Curr valid
				
				if ( 0 == (_orientation % 2) ) {                                       // HORIZONTAL
					currentPos = new Position( currRow, (currCol - 1) ) ;
				} else {                                                               // VERTICAL
					currentPos = new Position( (currRow - 1), currCol );
				}

				if ( isValidSpace( currentPos ) ) {                                    // Does this new position exists on the board
					_currPos = currentPos;                                             // Assignment
					retVal = true;												       // Passed
				}
			}
		}
				
		return retVal;
	}
	
	/**
	 * FINISHBOARDINIT finishes the initialization of the board that was started and continued by
	 * the user.  This handles the last few steps before the board is set for the usre to start
	 * creating the puzzle.
	 * Preconditions:  The board must be present.
	 * 				This should only be done for board creation, currently only for NxN boards.
	 * 				Should only be done after the user has gone through board initialization up until
	 * 				bumper placement.
	 * Postconditions: The board should now be useable for Puzzle Creation.  Meaning the user can 
	 * 				start putting words and hints into the puzzle.
	 */
	public void finishBoardInit() {
		char[][] theBoard = getBoard();

		if ( null != theBoard ) {
			numberStartPositions();                                      // Number all start positions on the board
			setRegionAndCurr( (Position) _startPositions.toArray()[0] ); // The active region should be the one with the lowest placed position, and curr should be the lowest numbered place, position.
			collectBumpers();
			_isSetupDone = true;
		}

	}
	
	/**
	 * ISSETUP tells caller whether the board has finished setting up.  If so creation can go forward.
	 * Preconditions: NA
	 * Postconditions: Whether the board has finished setting up will be outputted.
	 */
	public boolean isSetup() {
		return _isSetupDone;
	}

	/**
	 * COLLECTBUMPERS will obtain the Position of all the bumpers on the board so that the View can have 
	 * quick acces to them.
	 * Preconditions:  The board must be present before use.
	 * Postconditions: The bumper positions on the board will be collected for later use.
	 * @param theBoard
	 */
	public void collectBumpers() {
		char[][] theBoard = getBoard();

		if ( null != theBoard ) {
			for ( int rowItr = 0; rowItr < getSideLength(); rowItr++ ) {

				for ( int colItr = 0; colItr < getSideLength(); colItr++ ) {
					if ( '@' == theBoard[rowItr][colItr] ) {
						_bumperList.add( new Position( rowItr, colItr) );
					}
				}
			}
		}
	}
	
	/**
	 * GETBUMPERLIST will get the list of bumpers that exist on the board.
	 * Can only be used AFTER the board has been set up, otherwise answers may be incorrect.
	 * Returned value should not be modified.
	 * Preconditions:  The board must be finished being set up, or answers will
	 * 				not be valid.
	 * Postconditions: The list of user chosen bumpers for this board will be returned.
	 */
	public ArrayList<Position> getBumperList() {
		return _bumperList;
	}
	
	/**
	 * FLIPBUMPER checks that the Position that was passed in is within the bounds of the board, then changes 
	 * it from a space to a bumper, and vice versa.
	 * Preconditions:  The board MUST have been created before this function is called.  
	 * 				Only to be used during board creation.
	 * 				Only to be used on NxN boards
	 * Postconditions: A bumper will either be erased or added at the position indicated.
	 * @param aPos the position that you want either to be a bumper, or returned to being a space.
	 * @return boolean Whether the bumper has been flipped.
	 */
	public boolean flipBumper( final Position aPos ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();
		char placeChar = ' ';

		if (( null != theBoard ) 
				&& ( null != aPos ) 
				&& ( GameType.NXN.equals(_gameType) ) ) {        // If board exists, and Game type is N x N                         // Does the board exists.
			int rowNum = aPos.getRow();
			int colNum = aPos.getCol();
															   
			if ( isPositionInBounds( aPos ) ) {                                   // Checking board bounds
				
				placeChar = ( _bumpChar == theBoard[rowNum][colNum] ) ? _spcChar : _bumpChar;   // Set the appropriate character
				retVal = flipPlaces( aPos, placeChar );

			}
			
		}
		
		return retVal;
	}
	
	/**
	 * FLIPPLACES flips the places that make sense for the current mirrorType.  
	 * EX:  If mirror type is FOURWAY, then the places that will be flipped will be 
	 * the place entered, and all its reflections along the X AND Y axes.
	 * Preconditions:  To be used by flipBumpers().
	 * Postconditions: True will be returned if the modification took place, false if not.
	 * @param aPos the position that is to be flipped and mirrored
	 * @param aChar the character that is to be mirrored.
	 */
	public boolean flipPlaces( final Position aPos, final char aChar ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();

		if ( ( null != theBoard )
				&& ( null != aPos ) ) {
			
			switch( _mirrorType ) {

			case FOURWAY:

				retVal = setPosFourWay( aPos, aChar );

				break;

			case FLAT:


				retVal = setPosFlat( aPos, aChar );

				break;


			case FLATFLIP:

				retVal = setPosFlatFlip( aPos, aChar );

				break;

			case UPHILL:

				retVal = setPosUpHill( aPos, aChar );

				break;

			case DOWNHILL:

				retVal = setPosDownHill( aPos, aChar );

				break;

			default:                                          // If new Enum Created

			retVal = false;

			break;

			}
		}
		return retVal;
	}
	
	/** 
	 * SETMIRRORXPOSITION flips the location that is entered to the inputted state, if that is possible
	 * This function is to be used during the creation of the board.
	 * Preconditions:  This function is to be used during the creation of the board.  Does not check for valid positions
	 * Postconditions: A position will be returned that is the mirror of the one inputted, flipped over the X axis.
	 */
	public void setMirrorXPosition( final Position aPos, final char aChar ) {
		int length = _size - 1;

		if (null != aPos ) {
			int rowNum = aPos.getRow();
			int colNum = aPos.getCol();
			_pmCreateBoard[ rowNum ][ length - colNum ] = aChar;		// Place character, opposite the inputted Position on the X Axis.
		}
	}
	
	/** 
	 * SETMIRRORYPOSITION flips the location that is entered to the inputted state, if that is possible
	 * This function is to be used during the creation of the board.
	 * Preconditions:  This function is to be used during the creation of the board.  Does not check for valid positions
	 * Postconditions: A position will be returned that is the mirror of the one inputted, flipped over the Y axis.
	 */
	public void setMirrorYPosition( final Position aPos, final char aChar ) {
		int length = _size - 1;

		if ( null != aPos ) {
			int rowNum = aPos.getRow();
			int colNum = aPos.getCol();
			_pmCreateBoard[ length - rowNum ][ colNum ] = aChar;		// Place character, opposite the inputted Position on the Y Axis.
		}
	}	
	
	/** 
	 * SETMIRRORXYPOSITION flips the location that is entered to the inputted state, if that is possible
	 * This function is to be used during the creation of the board.
	 * Preconditions:  This function is to be used during the creation of the board.  Does not check for valid positions
	 * Postconditions: A position will be returned that is the mirror of the one inputted, flipped over the X then the Y axis.
	 */
	public void setMirrorXYPosition( final Position aPos, final char aChar ) {
		int length = _size - 1;

		if ( null != aPos ) {
			int rowNum = aPos.getRow();
			int colNum = aPos.getCol();
			_pmCreateBoard[ length - rowNum ][ length - colNum ] = aChar;		// Place character, opposite the inputted Position on the X then Y Axis.
		}
	}
	
	
	/**
	 * SETPOSFOURWAY sets the entered position in four places on the board, from the position entered
	 * it is reflected on the X, Y, and X=>Y axis.  Will return false is the current board configuratioin is not
	 * supported by this operation.  EX:  Odd NxN boards cannot be positioned four ways.
	 * Preconditions:  Should be used by flipBumper.  Only even N boards can be positioned four ways.
	 * Postconditions: Returns true if the board was modified, and false otherwise.
	 * @param aPos the position that is to be reflected
	 * @param aChar the character that is to be placed in the various positions.
	 */
	public boolean setPosFourWay( final Position aPos, final char aChar ) {
		boolean retVal = false;
		
		if ( ( (_size % 2) == 0 ) 
				&& ( null != aPos )
				&& ( GameType.NXN.equals(_gameType)) 
				&& ( MirrorType.FOURWAY.equals(_mirrorType )) ) {       // An Even, NxN board split four ways.
			int rowNum = aPos.getRow();
			int colNum = aPos.getCol();
			
			_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Quadrant 2
			setMirrorXPosition( aPos, aChar );                          // Set for Quadrant 1
			setMirrorYPosition( aPos, aChar );                          // Set for Quadrant 3
			setMirrorXYPosition( aPos, aChar );                         // Set for Quadrant 4
			
			retVal = true;
			
		}
		
		return retVal;
	}
	
	/**
	 * SETPOSFLAT sets the entered position in two places, from the position entered
	 * it is reflected on the Y axis.  
	 * Preconditions:  Should be used by flipBumper.  
	 * Postconditions: Returns true if the board was modified, and false otherwise.
	 * @param aPos the position that is to be reflected
	 * @param aChar the character that is to be placed in the various positions.
	 */
	public boolean setPosFlat( final Position aPos, final char aChar ) {
		boolean retVal = false;
		int length = _size - 1;

		if ( ( GameType.NXN.equals(_gameType)) && ( MirrorType.FLAT.equals(_mirrorType )) ){   // An Even, NxN board split in half across Y axis

			if ( null != aPos ) {
				int rowNum = aPos.getRow();
				int colNum = aPos.getCol();


				if (  (_size % 2) == 0 ) {                                      // Even sized board

					_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Top half
					setMirrorXPosition( aPos, aChar );                          // Set for Bottom half

					retVal = true;

				} else {                                                        // Odd sized board

					_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Top half

					if ( rowNum < ( (length) / 2 )) {                  // If we are below the midsection of the board
						setMirrorXPosition( aPos, aChar );                          // Set for Bottom half
						retVal = true;
					}
				}
			}
		}

		return retVal;
	}
	
	/**
	 * SETPOSFLATFLIP sets the entered position in two places, from the position entered
	 * it is reflected on the Y axis, then the X axis.  
	 * Preconditions:  Should be used by flipBumper.  
	 * Postconditions: Returns true if the board was modified, and false otherwise.
	 * @param aPos the position that is to be reflected
	 * @param aChar the character that is to be placed in the various positions.
	 */
	public boolean setPosFlatFlip( final Position aPos, final char aChar ) {
		boolean retVal = false;
		int length = _size - 1;

		if ( ( GameType.NXN.equals(_gameType)) && ( MirrorType.FLATFLIP.equals(_mirrorType )) ){   // An Even, NxN board split in half across Y axis

			if ( null != aPos ) {
				int rowNum = aPos.getRow();
				int colNum = aPos.getCol();

				if (  (_size % 2) == 0 ) {                                      // Even sized board

					_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Top half
					setMirrorXYPosition( aPos, aChar );                         // Set for Bottom half

					retVal = true;

				} else {                                                        // Odd sized board

					_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Top half

					if ( rowNum <= ( (length) / 2 )) {                  // If we are below the midsection of the board
						setMirrorXYPosition( aPos, aChar );                     // Set for Bottom half
						retVal = true;
					}

				}
			}
		}

		return retVal;
	}
	
	/**
	 * SETPOSUPHILL sets the entered position in two places, from the position entered
	 * it is reflected on the diagonal from the bottom left corner to the top right.
	 * Preconditions:  Should be used by flipBumper.  
	 * Postconditions: Returns true if the board was modified, and false otherwise.
	 * @param aPos the position that is to be reflected
	 * @param aChar the character that is to be placed in the various positions.
	 */
	public boolean setPosUpHill( final Position aPos, final char aChar ) {
		boolean retVal = false;
		int rowNum = aPos.getRow();
		int colNum = aPos.getCol();
		int length = _size - 1;

		if ( ( GameType.NXN.equals(_gameType)) && ( MirrorType.UPHILL.equals(_mirrorType )) ) {   // An Even, NxN board split in half diagonally.

			if (  (_size % 2) == 0 ) {                                      // Even sized board

				_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Top half
				setMirrorXYPosition( aPos, aChar );                         // Set for Bottom half

				retVal = true;

			} else {                                                        // Odd sized board

				_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Top half

				if ( (rowNum + colNum ) != length ) {                       // If we are below the midsection of the board diagonally
					setMirrorXPosition( aPos, aChar );                      // Set for Bottom half
					retVal = true;
				}

			}
		}

		return retVal;
	}
	
	/**
	 * SETPOSDOWNHILL sets the entered position in two places, from the position entered
	 * it is reflected on the diagonal from the top right to the bottom left.
	 * Preconditions:  Should be used by flipBumper.  
	 * Postconditions: Returns true if the board was modified, and false otherwise.
	 * @param aPos the position that is to be reflected
	 * @param aChar the character that is to be placed in the various positions.
	 */
	public boolean setPosDownHill( final Position aPos, final char aChar ) {
		boolean retVal = false;
		int rowNum = aPos.getRow();
		int colNum = aPos.getCol();
		
		if ( ( GameType.NXN.equals(_gameType)) && ( MirrorType.DOWNHILL.equals(_mirrorType )) ) {   // An Even, NxN board split in half diagonally.
			if (  (_size % 2) == 0 ) {                                      // Even sized board

				_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Top half
				setMirrorXYPosition( aPos, aChar );                         // Set for Bottom half

				retVal = true;

			} else {                                                        // Odd sized board

				_pmCreateBoard[ rowNum ][ colNum ] = aChar;                 // Set for Top half

				if ( rowNum != colNum ) {                       // If we are below the midsection of the board diagonally
					setMirrorXPosition( aPos, aChar );                      // Set for Bottom half
					retVal = true;
				}


			}
		}

		return retVal;
	}
	
	/**
	 * ISVALIDSPACE checks that the Position that was passed in is within the bounds of the board, and is not a bumper
	 * Preconditions:  The board MUST have been created before this function is called
	 * Postconditions: Whether or not the inputted position is on the board will be returned as a boolean
	 */
	public boolean isValidSpace( final Position aPos ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();

		if ( null != theBoard ) {                              // Does the board exists.
			int rowNum = aPos.getRow();
			int colNum = aPos.getCol();
															   // Checking board bounds, and for Bumper
			if ( ( ( rowNum > -1 ) && ( rowNum < _sizeAcross ) ) 
				&& ( (colNum > -1 ) && ( colNum < _sizeDown ) ) 
				&& (getBoard()[rowNum][colNum] != _bumpChar) ) {
				retVal = true;                                 // Passed
			}
			
		}
		
		return retVal;
	}
	
	/**
	 * ISPOSITIONINBOUNDS checks that the Position that was passed in is within the bounds of the board
	 * Preconditions:  The board MUST have been created before this function is called
	 * Postconditions: Whether or not the inputted position is on the board will be returned as a boolean
	 */
	public boolean isPositionInBounds( final Position aPos ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();

		if ( ( null != theBoard ) && ( null != aPos ) ) {                              // Does the board exists.
			int rowNum = aPos.getRow();
			int colNum = aPos.getCol();
															   // Checking board bounds, and for Bumper
			if ( ( ( rowNum > -1 ) && ( rowNum < _sizeAcross ) ) 
				&& ( (colNum > -1 ) && ( colNum < _sizeDown ) ) ) {
				retVal = true;                                 // Passed
			}
			
		}
		
		return retVal;
	}
	
	/**
	 * ISCURRINREGION tells whether Curr is in the Active Region or not.
	 * Preconditions:  The board must have been set before the function is called.
	 * Postconditions: A boolean value will indicate whether Curr is in the active region
	 */
	public boolean isCurrInRegion() {
		boolean retVal = false;
		char[][] theBoard = getBoard();

		if ( null != theBoard ) {
			Position activeStartPos = _activeRegion.getStartPos();
			int activeRow = activeStartPos.getRow();
			int activeCol = activeStartPos.getCol();

			int currRow = _currPos.getRow();
			int currCol = _currPos.getCol();
			
			if ( 0 == ( _orientation % 2 ) ) {
				if ( ( activeRow == currRow ) 
						&& ( ( currCol >= activeCol ) 
								&& ( ( currCol - activeCol ) < _activeRegion.getLength() ) ) ) {
					retVal = true;
				}
			} else {
				if ( ( activeCol == currCol )
						&& ( ( currRow >= activeRow ) 
								&& ( ( currRow - activeRow ) < _activeRegion.getLength() ) ) ) {
					retVal = true;
				}			
			}		
		}

		return retVal;
	}
		
	/**
	 * ISHORIZONTAL tells whether the orientation on the board is HORIZONTAL.
	 * Preconditions:  The board must be set up.
	 * Postconditions: Whether the board is HORIZONTAL or not will be returned as a boolean
	 */
	public boolean isHorizontal() {
		return ( 0 == ( _orientation % 2 ) ) ;
	}
	
	/**
	 * FINDREGIONLENGTH returns the length of the region that could be made from the inputted position onward.
	 * Preconditions:  Board must be present for this function to work.
	 * Postconditions: The length of the potential region will be outputted, or -1 if there is not viable region from 
	 * 				this position in the present orientation.
	 * 				The inputted position will not be modified.
	 */
	public int findRegionLength( final Position aPos ) {
		char[][] theBoard = getBoard();
		int retVal = 0;                                                   // Prepare int return value
		
			if ( ( null != theBoard ) && ( null != aPos ) ) {                                 // If board is present
				Position currentPos = new Position( aPos.getRow(), aPos.getCol() );
				
				if ( isHorizontal() ) {                              // HORIZONTAL
					
					setPosToRegionStart(currentPos);                 // Place at start of imagined region
					
					while( isValidSpace( currentPos )) {              // While not a bumper, or off board
						currentPos.setCol(currentPos.getCol() + 1);   // Move right
						retVal++;                                     // Increment
					}
					
				} else {                                              // VERTICAL
					
					setPosToRegionStart(currentPos);                 // Place at start of imagined region
					
					while( isValidSpace( currentPos )) {              // While not a bumper, or off board
						currentPos.setRow(currentPos.getRow() + 1);   // Move down
						retVal++;                                     // Increment
					}
					
				}
			}
		
		return ( ( retVal > ( _minWordSize  -1 ) ) ? retVal : -1 );   
	}

	/**
	 * SETPOSTOREGIONSTART will set the inputted Position to the start of the region it
	 * would belong to depending on the orientation of the board, and the place on the 
	 * board that was entered into the function.  The region is not set, and does not 
	 * have to exist as an Active Region before use.  Does not respond to data that is on 
	 * the board.
	 * Preconditions:  The board must exists before this function is called. 
	 * 				Check for Position validity before use.				
	 * Postconditions: There will be no change if the position entered is invalid for any reason.
	 * 				This function does not respond to data on the board, just the board itself.
	 * 				Otherwise, the position entered will be moved to the front of the region
	 * 				it would inhabit; based on its original coordinates, and the board's orientation.  
	 * 				Will work for any size region greater than 1.  If new Position value corresponds to 
	 * 				actual startPosition will have a low valued Position place.
	 * @param currentPos
	 */
	public void setPosToRegionStart(Position currentPos) {
		char[][] theBoard = getBoard();

		if ( (null != theBoard ) && ( null != currentPos ) ) {                                 // If the board exists.
			Position movePos = new Position( currentPos.getRow(), currentPos.getCol()) ; // Separate object

			if ( isHorizontal() ) {                              // If the orientation is HORIZONTAL

				while ( isValidSpace( movePos )) {               // Stop at invalid position.
					currentPos.setCol( movePos.getCol() );       // Set last valid position
					movePos.setCol(movePos.getCol() - 1 );       // Could be setting to invalid position
				}

			} else {                                       

				while ( isValidSpace( movePos )) {               // Stop at invalid position
					currentPos.setRow( movePos.getRow() );       // Set last valid position
					movePos.setRow(movePos.getRow() - 1 );       // Could be setting invalid position
				}

			}

			// Find the starting position, if one is found, so that the placement number is correct in the inputted position
			findStartPos(currentPos);

		}

		return;
	}
	
	/**
	 * SETPOSTOREGIONSTART will set the inputted Position to the start of the region it
	 * would belong to depending on the orientation of the board, and the place on the 
	 * board that was entered into the function.  The region is not set, and does not 
	 * have to exist as an Active Region before use.  Does not respond to data that is on 
	 * the board.
	 * Preconditions:  The board must exists before this function is called. 
	 * 				Check for Position validity before use, and after.				
	 * Postconditions: There will be no change if the position entered is invalid for any reason.
	 * 				This function does not respond to data on the board, just the board itself.
	 * 				Otherwise, the position entered will be moved to the front of the region
	 * 				it would inhabit; based on its original coordinates, and the inputted orientation.  
	 * 				Will work for any size region greater than 1.  If new Position value corresponds to 
	 * 				actual startPosition will have a low valued Position place.  Meaning < 5000.
	 * @param currentPos   The position that may be modified if there is a valid region at thie orientation.
	 * @param isHorizontal Inputted orientation to look for region starts using the inputted Position.
	 */
	public void setPosToRegionStart(Position currentPos, final boolean isHorizontal ) {
		char[][] theBoard = getBoard();

		if ( (null != theBoard ) && ( null != currentPos ) ) {                                 // If the board exists.
			Position movePos = new Position( currentPos.getRow(), currentPos.getCol()) ; // Separate object

			if ( isHorizontal ) {                              // If the orientation is HORIZONTAL

				while ( isValidSpace( movePos )) {               // Stop at invalid position.
					currentPos.setCol( movePos.getCol() );       // Set last valid position
					movePos.setCol(movePos.getCol() - 1 );       // Could be setting to invalid position
				}

			} else {                                       

				while ( isValidSpace( movePos )) {               // Stop at invalid position
					currentPos.setRow( movePos.getRow() );       // Set last valid position
					movePos.setRow(movePos.getRow() - 1 );       // Could be setting invalid position
				}

			}

			// Find the starting position, if one is found, so that the placement number is correct in the inputted position
			findStartPos(currentPos);

		}

		return;
	}

	/**
	 * FINDSTARTPOS	finds the start position for the active region so that the places match
	 * If the position inputted is not a starting positions place value will not be modified.
	 * Preconditions:  The board must exists.
	 * 				To be used by setPosToRegionStart
	 * Postconditions: The matching position for this region is found in the startPosition array
	 * 				so that the places match.  If the inputted position is not a starting positoin
	 * 				then the position's place is not modified.
	 * @param currentPos May be modifies.
	 */
	public void findStartPos(Position currentPos) {
		char[][] theBoard = getBoard();

		if ( ( null != theBoard ) && ( null != currentPos ) && ( this.isValidSpace(currentPos)) ) {
			for ( Position place : _startPositions ) {

				if ( ( place.getRow() == currentPos.getRow() ) && ( place.getCol() == currentPos.getCol()) )  {
					currentPos.setPlace( place.getPlace() );
				}

			}
		}
	}
	
	/**
	 * FINDSTARTREGION will find the start of the region that exists in the inputted orientation.
	 * If found Position does not correspond to a startPosition then currentPos is not changed.
	 * Otherwise, currentPos's Place will be changed to match the Region's startPosition's place
	 * Preconditions:  The board must exists.
	 * Postconditions: The inputted Position will be changed to match the start Position of 
	 * 				the Region that would match input, if that Region is valid.  Otherwise, the inputted
	 * 				Position will not be changed.
	 */
	public void findStartRegion(Position currentPos, boolean isHorizontal ) {
		char[][] theBoard = getBoard();

		if ( ( null != theBoard ) && ( null != currentPos ) && ( isValidSpace(currentPos)) ) {
			int theRow = currentPos.getRow();
			int theCol = currentPos.getCol();
			Position candPos = currentPos;
			
			if ( isHorizontal ) {                    // Find the start of a Horizontal region
				while ( isValidSpace(candPos)) {
					candPos.setCol(--theCol);
				}
				currentPos.setCol(++theCol);         // Make valid
			} else {                                 // Find the start of a Vertical region
				while( isValidSpace(candPos) ) {
					candPos.setRow(--theRow);
				}
				currentPos.setRow(++theRow);         //Make valid
			}
			
			findStartPos( currentPos );              // The place of currentPos will be changed to a low numbered place, if it is a startPosition now.
						
		}
	}
		
	/**
	 * SETACTIVEREGION set active region from the position that is in the parameter list, 
	 * as well as, the orientation of the board.  
	 * Preconditions:  Only valid positions should be entered.
	 * Postconditions: The board's Active Region will be set.  The starting point will be the position
	 * 				found to start the imagined region, with the orientation the board had when the function was called.  Only 
	 * 				regions with length longer than minimum word size will be allowed.  That is usually 2 or 3.
	 * @param aPos the position that you want to find a region for.
	 * @return region that was made.
	 */
	public boolean setActiveRegion( final Position nwPos ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();

		if ( ( null != theBoard ) && ( null != nwPos ) ) {
			Position aPos = new Position( nwPos.getRow(), nwPos.getCol());

			if ( isValidSpace( aPos ) ) {     // The board exists, position is valid
				int nwLength = findRegionLength( aPos ) ;                // Length for new region
				setPosToRegionStart( aPos );                            // Set aPos to the start of the imagined region

				if ( -1 != nwLength ) {                                  // If long enough to be a word.

					_activeRegion.setActiveRegion( aPos, nwLength, _orientation );    // Length and start of region found in findLength
					retVal = true;                                   // passed
					// Notify
				}
			}

		}

		return retVal; 
	}
	
	/**
	 * FLIPINPLACE will flip the orientation of the Active Region on the current position inside it.  
	 * Also you cannot flip into a region that would be smaller than the minimum allowabe word length of the board.
	 * In this case nothing should happen.
	 * Curr does not change ONLY the Active Region and Orientation change.
	 * Preconditions:  Board is created before use.
	 * 				Coming from valid region.
	 * Postconditions: The Active Region will be changed, as well as, board orientation.
	 * 				However, if the flipped region would be impossible or invalid, then there
	 * 				will be no change.
	 */
	public boolean flipInPlace() {
		boolean retVal = false;
		char[][] theBoard = getBoard();
		
		if ( null != theBoard ) {
			flipOrientation();                           // Flip Orientation to check for valid flipped region from curr

			if ( findRegionLength( _currPos ) != -1 ) {
				setActiveRegion( _currPos );             // Set new AR with this position, in a new Orientation
				retVal = true;
			} else {
				flipOrientation();                       // Flipped region was not valid.  Return to original orientation
			}

		}

		return retVal;
	}
	
	/**
	 * WRITEMOVE is used to write in the current position on the board you are presently using.
	 * Then it also move the current to the next position on the board moving forward.  It returns true if
	 * the position was written to and current could be move forward.  If current could not be moved
	 * forward for any reason it returns false, but the current position will still be written to.
	 * Preconditions:  The the appropriate board MUST be available for manipulation.  Curr must be set
	 * 				to a valid board position, and be within the active region.
	 * Postconditions: The appropriate board will be modified in the position asked for.  Either the
	 * 				PLAY or CREATE board will be modified, depending on the mode we are in.  Returns
	 * 				False if current cannot be moved forward; however, the current position will still
	 * 				be written to.
	 * @param aChar the character that you want written in this position.
	 * @return boolean
	 */
	public boolean writeMove( char aChar ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();
		
		if ( ( isValidSpace(_currPos)) && ( isCurrInRegion() ) && ( null != theBoard ) ) {   // Is this a valid space in the active region.
			int rowNum = _currPos.getRow();
			int colNum = _currPos.getCol();

			theBoard[rowNum][colNum] = aChar;                        // Write whether or not you can move.

			if ( currNext() ) {                                      // Next is position is now Curr: Will not move if next position for this orientation is invalid
				retVal = true;                                       // passed
			}
			
		}
		
		return retVal;                                               // False means cannot move any further, But will still write if space is valid, board is not null, and current is in the region                                               
		
	}
	
	/**
	 * CLEARMOVE is used to clear the current position in the active region and set the previous position
	 * to be current if that is a legal move.  If it is not a legal move, the current position will still
	 * be erased.
	 * Preconditions:  The the appropriate board MUST be available for manipulation.  Curr must be set
	 * 				to a valid board position, and be within the active region.
	 * Postconditions: The appropriate board will be modified in the position asked for.  Either the
	 * 				PLAY or CREATE board will be modified, depending on the mode we are in.  Returns
	 * 				False if current cannot be moved forward; however, the current position will still
	 * 				be written to.
	 */
	public boolean clearMove() {
		boolean retVal = false;
		char[][] theBoard = getBoard();
		
		if ( ( isValidSpace(_currPos)) && ( isCurrInRegion() ) && ( null != theBoard ) ) {   // Is this a valid space in the active region.
			int rowNum = _currPos.getRow();
			int colNum = _currPos.getCol();
			
			theBoard[rowNum][colNum] = 0;                            // Erase previous character
			
			if ( currPrev() ) {                                      // Next is position is now Curr: Will not move if next position for this orientation is invalid
				retVal = true;                                       // passed
			}			
			
		}
		
		return retVal;
	}
	
	/**
	 * GETPLACE_CREATED returns the character at the position indicated in the parameter list
	 * on the create board.
	 * Preconditions:  The board must be created first, else it returns '0'
	 * Postconditions: The character at the place specified on the board will be returned
	 * @param aPos the place on the board that you want to see its character
	 */
	public char getPlace_Created( final Position aPos ) {
		char retVal = '0';
		char[][] theBoard = getBoard();
		
		if ( ( null != theBoard ) && ( null != aPos ) && ( isPositionInBounds( aPos )) ) {     // The board is created, and position is on the board.                             // If the current baord is not null
			retVal = theBoard[ aPos.getRow()][ aPos.getCol()];
		}
		
		return retVal;
	}
	
	/**
	 * GETPLACE_CREATED returns the character at the position indicated in the parameter list
	 * on the create board.
	 * Preconditions:  The board must be created first, else it returns '0'
	 * Postconditions: The character at the place specified on the board will be returned
	 * @param aPos the place on the board that you want to see its character
	 */
	public char getPlace_Created( final int aRow, final int aCol  ) {
		Position aPos = new Position(aRow, aCol );
		char retVal = '0';
		char[][] theBoard = getBoard();
		
		if ( ( null != theBoard ) && ( isPositionInBounds( aPos )) ) {     // The board is created, and position is on the board.                             // If the current baord is not null
			retVal = theBoard[ aPos.getRow()][ aPos.getCol()];
		}
		
		return retVal;
	}
	
	/**
	 * GETBOARD returns the appropriate board, either the Create or Play boards.
	 * It will also be used for testing purposes; otherwise it should be private.
	 * Preconditions:  The game must already be set for Play or Creation.  Should be use internally
	 * 				and for testing.  Otherwise, this function should be private.
	 * Postconditions: Returns the board for the current mode CREATE, or PLAY. 
	 */
	public char[][] getBoard() {
		return ( UseType.CREATE.equals(_useType)?_pmCreateBoard:_pmPlayBoard);
	}
	
	/**
	 * GETTHEBOARD returns the appropriate board, either the Create or Play boards.
	 * It will also be used for testing purposes; otherwise it should be private.
	 * Preconditions:  The game must already be set for Play or Creation.  
	 * Postconditions: Returns the board for the current mode CREATE, or PLAY. 
	 */
	public final char[][] getTheBoard() {
		return ( UseType.CREATE.equals(_useType)?_pmCreateBoard:_pmPlayBoard);
	}
	
	/**
	 * BOARDSEQUAL compares the board entered to this, and tells whether they hold the same
	 * characters.   
	 * Preconditions:  This assumes only NxN boards.
	 * Postconditions: The two boards will be compared and a boolean value will indicate if
	 * 				they are equal.
	 * @param other the other PMMModel, so we can compare baords.
	 */
	public boolean boardsEqual( PMModel other ) {
		char[][] otherBoard = other.getBoard();
		char[][] theBoard = this.getBoard();
		
		if ( theBoard == otherBoard ) {                              // If they are the exact same board, return true.
			return true;
		}
                                                                     // If other exists, if our board exists, and they have the same sidelength
		if ( ( null != other ) 
				&& ( null != theBoard ) 
				&& ( _size == other.getSideLength() ) ) {
			
			for ( int cnt = 0; cnt < this._size; cnt++ ) {
				
				for ( int itr = 0; itr < this._size; itr++ ) {
					if ( theBoard[ cnt ][ itr ] != otherBoard[ cnt ][ itr ] ) {
						return false;                                // They do NOT match
					}
				}
				
			}
			return true;                                             // They DO match
		}
		
		return false;                                                // They do NOT match
	}
	
	/**
	 * OVERRIDE:EQUALS This function overrides the equals().
	 * Preconditions:  Both objects must be the same class.
	 * Postconditions: A boolean reflecting the equalitiy of the two objects will be returned.
	 * 				Objects are ONLY compared on their place, not their positions on the board.
	 * @param otherObject The object we are being compared to
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
		
		// now we know otherObject is a non-null Position
		PMModel other = (PMModel)otherObject;
		
		// test whether the fields have identical values.
		return ( ( this.boardsEqual(other) ) 
				&& ( this._size == other._size ) 
				&& ( this._activeRegion.equals(other._activeRegion) ) 
				&& ( this._currPos.equals(other._currPos) ) 
				&& ( this._orientation == other._orientation ) 
				&& ( this._useType.equals(other._useType) ) 
				&& ( this._gameType.equals(other._gameType) ) 
				&& ( this._mirrorType.equals(other._mirrorType ) )
				&& ( this._startPositions.equals(other._startPositions) )
//				&& ( this._acrossHints.equals(other.getAcrossHints()) )         // Make EQUALS() for these
//				&& ( this._downHints.equals(other.getDownHints()) )          
//				&& ( this._theDictionary.equals(other.getDictionary()) )
				);
		
	}
	
	/**
	 * PRINTBOARDSTATES will print out the board and the states of each of its positions
	 * Preconditions:  The board must be set up before use
	 * Postconditions: The board will be printed out in such a way as to show its current state
	 * @return String showing the state of the board in ASCII, as well as in text.
	 */
	public String printBoardStates() {
		String retVal = "";
		int rowNum = 0;
		String botm = "____";

		if ( _size > 0 ) {
			retVal += " ";

			retVal += printTopRow(rowNum);

			retVal += printLine(botm);

			for ( int row = 0; row < _size; row++ ) {            // Print Main part of Board
				retVal += row + ((row < 10 )? " " : "") ;

				for ( int col = 0; col < _size; col++ ) {

					char place = _pmCreateBoard[ row ][ col ];
					retVal += "|";
					if ( place != '@') {
						retVal += "" + place + (( place != 0 )? "  ": "   ");
					} else {
						retVal += "" + _bumpChar + _bumpChar + _bumpChar;
					}
				}

				retVal += "|\n";
				retVal += printLine(botm);

			}

			retVal += outputStates();
		}
		
		return retVal;
	}

	/**
	 * PRINTBOARDNUMBERING will print out the board and the numbering of each of its positions
	 * Preconditions:  The board must be set up before use
	 * Postconditions: The board will be printed out in such a way as to show its current state
	 * @return String showing the state of the board in ASCII, as well as in text.
	 * 		It shows the numbering of the Hints.
	 */
	public String printBoardNumbering() {
		String retVal = "";
		int rowNum = 0;
		String botm = "____";                                   // The floor for each cell

		if ( _size > 0 ) {
			retVal += " ";

			retVal += printTopRow(rowNum);                      // Prints out the top coordinates for board

			retVal += printLine(botm);                          // Prints out the floor of the entire row

			for ( int row = 0; row < _size; row++ ) {           // Print Main part of Board, including Left side coordinates
				retVal += row + ((row < 10 )? " " : "") ;       // Different margins for diff number sizes

				for ( int col = 0; col < _size; col++ ) {       // Print out data for each cell

					char place = _pmCreateBoard[ row ][ col ];
					int number = getHintPlace( row, col );
					retVal += "|";
					if ( place != '@') {                        // If place is not a bumper, print out data
					
						if ( number == 0 ) {                    // If there is no hint here, leave blank
							retVal += "   ";
						}
						else {                                  // Else print out data.
							retVal += ""+ ( number + (( number > 9 )? " ": "  ") );
						}
					
					} else {                                     // Indicate a bumper was present
						retVal += "" + _bumpChar + _bumpChar + _bumpChar;
					}
				}

				retVal += "|\n";                                 // Prepare for next line
				retVal += printLine(botm);

			}

			retVal += outputStates();
		}
		
		return retVal;
	}

	
	/**
	 * Outputs the state of the board.
	 * Preconditions:  NA
	 * Postconditions: The state of various values in this Model will be outputted as a String
	 * @return String The state of this board's various values.
	 */
	public String outputStates() {

		return "\nSize - " + _size + " BY " + _size  
		+ "\nActiveRegion = " + _activeRegion 
		+ "\nCurrent Position = " + _currPos 
		+ "\nOrientation = " + ((isHorizontal())?"HORIZONTAL":"VERTICAL") 
		+ "\nUse Type is " + _useType 
		+ "\nGame Type is " + _gameType 
		+ "\nMirror Type is " + _mirrorType 
		+ "\nStart positions are " + _startPositions 
		+ "\nAcross Places and Hints are " + _acrossHints 
		+ "\nDown Places and Hints are " + _downHints + "\n";
		
	}

	/**
	 * Outputs the top row indicators for the board.
	 * @param rowNum
	 */
	private String printTopRow(int rowNum) {
		//String retVal = "";
		String retVal = " ";

		if ( ( rowNum > -1 ) && ( _size > -1 ) ) {
			while( rowNum < _size ) {                     // Top row of numbers.
				//retVal += "|" + rowNum + "  ";
				retVal += "|" + rowNum + (( rowNum < 10 )? "  " : " ");
				rowNum++;
			}

			retVal += "|\n";
		}
		return retVal;
	}

	/**
	 * @param botm
	 */
	private String printLine(String botm) {
		String retVal = "";
		int rowNum;

		if ( _size > -1 ) {
			//retVal += "_";
			retVal += "__";
			rowNum = 1;                    // Needs to be a function
			//while( rowNum < _size ) {
			while( rowNum <= _size ) {
				retVal += botm;
				rowNum++;
			}

			retVal += "\n";
		}

		return retVal; 
	}
	
	/**
	 * GETHINTPLACE Gets the place of the Position in the Start Positions list
	 * Preconditions:  The board must be set up before use.
	 * Postconditions: The place of Position entered will be returned.
	 * 				If it corresponds to a start position for a region that number will be returned.
	 * 				Otherwise, 0 will be returned.
	 * @param aRow row number of the position you are looking for 
	 * @param aCol col number of the position you are looking for
	 * @return the hint number of the position you were looking for, if it exists as a start position
	 */
	public int getHintPlace( final int aRow, final int aCol ) {
		Position toMatch = new Position( aRow, aCol );
		
		for ( Position pos : _startPositions ) {             // Search thru to find a match
			if ( toMatch.match(pos)) {
				return pos.getPlace();                       // Any number < 5000, was for the board
			}
		}
		
		return 0;                                            // If NOT found return 0
	}
	
	/**
	 * GETACROSSORDOWNHINT will get the hint associated with the inputted start position with 
	 * the inputted orientation.
	 * Preconditions:  The board should be set up before this function is called.
	 * 				The Start Position from the region should be used, since it contains the correct place for matching.
	 * Postconditions: The hint associated with the inputted orientation and position will be outputted
	 * @param isHorizontal whether the clue is for a horizontal region or not.
	 * @param aStartPos    The position that indicates the start of the region.
	 * @return String The String associated with the inputted data, or NULL if the inputted 
	 * 		data does not correspond to the beginning of a region on the board.  Or if the
	 * 		board is not available yet.
	 */
	public String getAcrossOrDownHint( boolean isHorizontal, Position aStartPos ) {
		String retVal = null;
		char[][] theBoard = getBoard();
		Map<Position, String> theMap = ( ( true == isHorizontal ) ? _acrossHints : _downHints );   // Pick a Mapping ACROSS OR DOWN
		
		if ( ( null != theBoard ) && ( theMap.containsKey(aStartPos)) ) {        // If the board has been created, and the map has this key
			retVal = theMap.get(aStartPos);                                      // Get that string.
		}
		
		return retVal;
	}
	
	/**
	 * SETACROSSORDOWNHINT will set the hint associated with the inputted variable.  
	 * Preconditions:  The board should be set up before use.
 	 * 				The Start Position from the region should be used, since it contains the correct place for matching.
	 * Postconditions: The hint for the region starting with aStartPos and of the inputted orientation, will
	 * 				be set.  A notification will be sent to ClueObservers, if any.
	 * @param isHorizontal whether the clue is for a horizontal region or not.
	 * @param aStartPos    The position that indicates the start of the region.
	 * @param aString      The Clue the user wants to represent the region that matches the input parameters.
	 * @return boolean tells whether the set was successful.
	 */
	public boolean setAcrossOrDownHint( boolean isHorizontal, Position aStartPos, String aString ) {
		boolean retVal = false;
		char[][] theBoard = getBoard();
		Map<Position, String> theMap = ( ( true == isHorizontal )? _acrossHints : _downHints );
		
		if ( ( null != theBoard ) && ( theMap.containsKey(aStartPos)) ) {
			theMap.put(aStartPos, aString);                               // associate this region with the inputted string
			retVal = true;
			notifyClueObservers();                                        // Notify that the Clues have been changed.
		}
				
		return retVal;
	}
	
	/**
	 * This function returns a String representation of the state of this object.
	 * This overrides java.lang.Object.toString();
	 * Preconditions:  NA
	 * Postconditions: The state of this object will be represented in String form
	 */
	@Override public String toString() {
			
		return printBoardStates();

	}
	
	/**
	 * REGISTEROBSERVER Register this Object, implementing the appropriate interface, to receive
	 * change notifications on the appropriate topic
	 * Preconditions:  Object must implement the appropriate interface
	 * Postconditions: Object registered for change notifications on the appropriate topic.
	 */
	public void registerObserver(CurrentObserver cOb ) {
		_CurrentObservers.add(cOb);
	}
	
	/**
	 * REGISTEROBSERVER Register this Object, implementing the appropriate interface, to receive
	 * change notifications on the appropriate topic
	 * Preconditions:  Object must implement the appropriate interface
	 * Postconditions: Object registered for change notifications on the appropriate topic.
	 */
	public void registerObserver( RegionObserver rOb ) {
		_RegionObservers.add(rOb);
	}

	/**
	 * REGISTEROBSERVER Register this Object, implementing the appropriate interface, to receive
	 * change notifications on the appropriate topic
	 * Preconditions:  Object must implement the appropriate interface
	 * Postconditions: Object registered for change notifications on the appropriate topic.
	 */
	public void registerObserver( BoardContentsObserver bcOb ) {
		_BoardContentsObservers.add(bcOb);
	}
	
	/**
	 * REGISTEROBSERVER Register this Object, implementing the appropriate interface, to receive
	 * change notifications on the appropriate topic
	 * Preconditions:  Object must implement the appropriate interface
	 * Postconditions: Object registered for change notifications on the appropriate topic.
	 */
	public void registerObserver( ClueObserver clOb) {
		_ClueObservers.add(clOb);
	}

	
	/**
	 * NOTIFYCURRENTOBSERVERS Notifies all Observers of Current that Current may have changed.
	 * They should take appropriate actions
	 * Preconditions:  Should be registered to get notifications
	 * Postconditions: Registered objects will be notified.
	 */
	public  void notifyCurrentObservers() {
		for ( CurrentObserver cOb : _CurrentObservers ) {
			cOb.updateCurrent();
		}
	}

	/**
	 * NOTIFYREGIONOBSERVERS Notifies all Observers of Current that Current may have changed.
	 * They should take appropriate actions
	 * Preconditions:  Should be registered to get notifications
	 * Postconditions: Registered objects will be notified.
	 */
	public  void notifyRegionObservers() {
		for ( RegionObserver rOb : _RegionObservers ) {
			rOb.updateRegion();
		}
	}
	
	/**
	 * NOTIFYBOARDCONTENTSOBSERVERS Notifies all Observers of Current that Current may have changed.
	 * They should take appropriate actions
	 * Preconditions:  Should be registered to get notifications
	 * Postconditions: Registered objects will be notified.
	 */
	public  void notifyBoardContentsObservers() {
		for ( BoardContentsObserver bcOb : _BoardContentsObservers ) {
			bcOb.updateBoardContents();
		}
	}
	
	/**
	 * NOTIFYCLUEOBSERVERS Notifies all Observers of Current that Current may have changed.
	 * They should take appropriate actions
	 * Preconditions:  Should be registered to get notifications
	 * Postconditions: Registered objects will be notified.
	 */
	public  void notifyClueObservers() {
		for ( ClueObserver clOb : _ClueObservers ) {
			clOb.updateClue();
		}
	}
	
	/**
	 * REMOVEOBSERVER Remove this object from the list of Observers for this action.
	 * Preconditions:  Object must be in the list of Observers.
	 * Postconditions: Object will be removed from the list of Observers.
	 */
	public void removeObserver( CurrentObserver cOb ) {
		int rmNum = _CurrentObservers.indexOf( cOb );
		
		if ( rmNum > 0 ) {
			_CurrentObservers.remove(rmNum);
		}
	}

	/**
	 * REMOVEOBSERVER Remove this object from the list of Observers for this action.
	 * Preconditions:  Object must be in the list of Observers.
	 * Postconditions: Object will be removed from the list of Observers.
	 */
	public void removeObserver( RegionObserver rOb ) {
		int rmNum = _RegionObservers.indexOf( rOb );
		
		if ( rmNum > 0 ) {
			_RegionObservers.remove(rmNum);
		}
	}
	
	/**
	 * REMOVEOBSERVER Remove this object from the list of Observers for this action.
	 * Preconditions:  Object must be in the list of Observers.
	 * Postconditions: Object will be removed from the list of Observers.
	 */
	public void removeObserver( BoardContentsObserver bcOb ) {
		int rmNum = _BoardContentsObservers.indexOf( bcOb );
		
		if ( rmNum > 0 ) {
			_BoardContentsObservers.remove(rmNum);
		}
	}
	
	/**
	 * REMOVEOBSERVER Remove this object from the list of Observers for this action.
	 * Preconditions:  Object must be in the list of Observers.
	 * Postconditions: Object will be removed from the list of Observers.
	 */
	public void removeObserver(ClueObserver clOb) {
		int rmNum = _ClueObservers.indexOf(clOb);
	
		if ( rmNum > 0 ) {
			_ClueObservers.remove(rmNum);
		}
	}

	/**
	 * ADDWORDDICT will add the inputted word to the Dictionary.
	 * If it already does not exist in some form.
	 * Preconditions:  This takes alphanumerics not punctuation.  EX: Don't => DONT.  Any punctuation 
	 * 				in the inutted string will be removed before being added to the Tree.
	 * 				This will remove spaces before placing words into the tree.
	 * Postconditions: True will be returned if the word is not already present in the Tree as a word.
	 * 				false will be returned otherwise.
	 * @return boolean
	 */
	public boolean addWordDict( String aWord ) {
		return _theDictionary.addWord(aWord);
	}
	
	/**
	 * DELETEWORDDICT will delete a word from the dictionary.
	 * Preconditions:  The word should exist in the tree.
	 * Postconditions: This word will no longer be a word in the tree.
	 */
	public boolean deleteWordDict( String aWord ) {
		return _theDictionary.deleteWord(aWord);
	}
	
	/**
	 * LOADDICT This function will be used to load a lot of words into the Tree.
	 * This function should be used when the tree has just been created and 
	 * needs to be loaded with words from a File.  This could be a costly operation,
	 * so should only be done when this would not be an issue.
	 * Preconditions:  This should be used when the time it takes to perform would not 
	 * 				be an issue.
	 * Postconditions: The words in the File will be inputted into the Tree.
	 * @param aFile The file that contains initial the word list that will be used.	
	 */
	public boolean loadDict( File aFile ) {
		return _theDictionary.loadTree(aFile);
	}
	
	/**
	 * SUGGESTMATCHDICT This function will take partially filled out strings and return a list of words that 
	 * fill in the voids left in the original string.  
	 * EX:  "_OR_" => ["CORK", "DORK", "GORE", "PORE", "TORT",...]
	 * Preconditions:  Blanks in the string MUST be represented by '_'s.  Spaces are not allowed.
	 * 			  The string entered should be > 0.  Entering whole words without blanks, will return that word.
	 * Postconditions: A list of the words that match the original string lettering will be returned
	 * @param aString A string with blanks represented by '_' that will be matched against words in the data structure.
	 */
	public ArrayList<String> suggestMatchDict( String aString ) {
		return _theDictionary.suggestMatch(aString);
	}
	
	/**
	 * SUGGESTWORDSOFLENGTHDICT This function will find words of the specified length and return them in an arrayList.
	 * There should be between 0 - 25 words returned.  If no words that match your criterion
	 * are found nothing will be returned
	 * Preconditions:  NA
	 * Postconditions: Between 0 - 25 words will be returned matching your criterion.
	 * @param aLength Length of words that will be in the result set.
	 */
	public ArrayList<String> suggestWordsOfLengthDict( int aLength ) {
		return _theDictionary.suggestWordsOfLength(aLength);
	}
	
	/**
	 * NUMWORDSDICT This function will return the number of words that are stored in this Data Structure
	 * Preconditions:  NA
	 * Postconditions: The number of words that the Trie represents will be outputted.
	 */
	public int numWordsDict() {
		return _theDictionary.numWords();
	}
	
	/**
	 * OUTPUTDICT This function will print all the words that are in the tree.  This will most likely
	 * be an expensive operation in terms of time.
	 * Preconditions:  There should be data already in the tree.
	 * Postconditions: The data in the tree will be outputted.  Because of the structure of the 
	 * 				tree it will be outputted in alphanumeric order.
	 */
	public ArrayList<String> outputDict() {
		return _theDictionary.outputTree();
	}
	
	/**
	 * FINDWORDDICT This function will tell whether an inputted word exists as a word in the tree
	 * Preconditions:  There must be data in the tree
	 * Postconditions: A boolean value will be returned indicating whether or not the 
	 * 				inputted word was found
	 */
	public boolean findWordDict( String aWord) {
		return _theDictionary.findWord(aWord);
	}
	
	/**
	 * POS2FILE will handle writing a Position to a text file.
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Position will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhatStr String representing what this Type was originally for in the Model.
	 * @param aPos Position that we want saved in File
	 * @throws IOException 
	 */
	private void pos2File( BufferedWriter bOut, String aWhatStr, final Position aPos ) throws IOException {
		String posString = "POSITION:";
		String oneSpc = " ";
		
		bOut.write(posString);                          // What is it.
		bOut.newLine();

		if ( ! (aWhatStr.equals("")) ) {                // If it is in another Object, do not say what goes to 
			bOut.write(aWhatStr);
			bOut.newLine();
		}
		
		bOut.write("" + aPos.getRow() + oneSpc + aPos.getCol() + oneSpc + aPos.getPlace() );    // Position info
		bOut.newLine();
		
	}
	
	/**
	 * AR2File will handle writing an ActiveRegion to a text file.
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aReg ActiveRegion that we want saved in File
	 * @throws IOException 
	 */
	private void ar2File( BufferedWriter bOut, String aWhatStr, final ActiveRegion aReg ) throws IOException {
		String arString = "ACTIVEREGION:";
		String oneSpc = " ";
		
		bOut.write(arString);                                // What Type
		bOut.newLine();
		bOut.write(aWhatStr);                                // What use in Model
		bOut.newLine();
		pos2File( bOut, new String(), aReg.getStartPos() );  // Start Position \n
		bOut.write( Integer.toString(aReg.getLength()) );                       // Length
		bOut.newLine();
		bOut.write( (( (aReg.getOrientation() % 2) == 0)? "false" : "true" ) );     // 0 or 1
		bOut.newLine();		                                 // New line
		
	}
	
	/**
	 * MAP2FILE	will handle writing an Map to a text file.  Specifically a Map<Position,String>
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aMap Map that we want saved in File
	 * @throws IOException 
	 */
	private void map2File( BufferedWriter bOut, String aWhatStr, final Map<Position, String> aMap ) throws IOException {
		String mapString = "MAP<Position, String>:";
		
		bOut.write(mapString);                                            // What is it.
		bOut.newLine();
		bOut.write(aWhatStr);                                             // Where it comes from in Model
		bOut.newLine();
		
		for ( Map.Entry<Position, String> mapping : aMap.entrySet() ) {   // Output mappings to file
			pos2File( bOut, new String(), mapping.getKey() );             // Has newline in it
			bOut.write( mapping.getValue() );
			bOut.newLine();
		}
		
//		bOut.newLine();
		
	}

	/**
	 * STRING2FILE will handle writing o String to a text file.  Specifically a String.
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aString Strin that we want saved in File
	 * @throws IOException 
	 */
	private void string2File( BufferedWriter bOut, String aWhatStr, String aString ) throws IOException {
		String strString = "STRING:";

		bOut.write( strString );
		bOut.newLine();	

		if ( ! (aWhatStr.equals("")) ) {                           // Say where it came from.
			bOut.write(aWhatStr);
			bOut.newLine();
		}

		bOut.write( aString );                                    // Send String to file
		bOut.newLine();
		
	}
	
	/**
	 * BOOL2FILE will handle writing o Boolean to a text file.  
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aBool Boolean that we want saved in File
	 * @throws IOException 
	 */
	private void bool2File( BufferedWriter bOut, String aWhatStr, boolean aBool ) throws IOException {
		String boolString = "BOOLEAN:";

		bOut.write( boolString );                            // Say what it is for parser.
		bOut.newLine();	

		if ( ! (aWhatStr.equals("")) ) {                     // Say where it came from in Model
			bOut.write(aWhatStr);
			bOut.newLine();
		}

		bOut.write( String.valueOf(aBool) );                 // Save T/F to file
		bOut.newLine();
		
	}
	
	/**
	 * TWODCHARARR2FILE will handle writing o char[][] to a text file.  Specifically a char[][].
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * 				aRow, and aCol should be the correct bounds for the 2D array.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param chArr char[][] that we want saved in File
	 * @throws IOException 
	 */
	private void twoDCharArr2File( BufferedWriter bOut, String aWhatStr, int aRow, int aCol, char[][] chArr ) throws IOException {
		String chArrString = "CHAR[][]:";
		String oneStr = " ";
		
		bOut.write(chArrString);                              // Type
		bOut.newLine();
		
		if ( ! (aWhatStr.equals("")) ) {                      // Where it came from in Model
			bOut.write(aWhatStr);
			bOut.newLine();
		}		
		
		
		if ( null == chArr ) {
			bOut.write("NULL");
			bOut.newLine();
		} else {

			bOut.write( aRow + oneStr + aCol );                   // Save size of sides for Parser
			bOut.newLine();

			for ( int itr = 0; itr < aRow ; itr++ ) {             // Save 2D array in File
				for ( int cnt = 0; cnt < aCol ; cnt++ ) {

					bOut.write( ((chArr[ itr ][ cnt] == 0 ) ? '.' : chArr[itr][cnt]) ) ;          // Save character in line

					if ( cnt < (cnt - 1) ) {
						bOut.write(oneStr);					      // Separate with space
					}

				}

				bOut.newLine();                                   // End of line new line char
			}
		}
	}
	
	/**
	 * DICT2FILE will handle writing o WordDictionary to a text file.  
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * 				Dictionaries can obtain new words from user, so they are saved as well.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aDict WordDictionary that will hold all the words that are in the Dictionary for this puzzle.
	 * @throws IOException 
	 */
	private void dict2File( BufferedWriter bOut, String aWhatStr, WordDictionary aDict ) throws IOException {
		String dictString = "WORDDICTIONARY:";
		
		bOut.write( dictString);
		bOut.newLine();
		
		if ( ! (aWhatStr.equals("")) ) {                      // Where it came from in Model
			bOut.write(aWhatStr);
			bOut.newLine();
		}		
		
		ArrayList<String> list = aDict.outputTree();          // Dictionary Words
		
		for ( String word : list ) {                          // Saving words to file.
			bOut.write(word);
			bOut.newLine();
		}
		
	}
	
	/**
	 * GAMETYPE2FILE will handle writing o GameType to a text file.  
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aGameType GameType that holds the game type information for the model
	 * @throws IOException 
	 */
	private void gameType2File( BufferedWriter bOut, String aWhatStr, GameType aGT ) throws IOException {
		String gtString = "GAMETYPE:";
		
		bOut.write(gtString);                            // Type
		bOut.newLine();
		
		bOut.write(aWhatStr);                            // What this did in the model
		bOut.newLine();
		
		bOut.write( aGT.toString() );                    // What type of game this was                
		bOut.newLine();
		
	}
	
	/**
	 * USETYPE2FILE will handle writing o UseType to a text file.  
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aUseType UseType that holds the use type information for the model
	 * @throws IOException 
	 */
	private void useType2File( BufferedWriter bOut, String aWhatStr, UseType aUT ) throws IOException {
		String utString = "USETYPE:";
		
		bOut.write(utString);                            // Type
		bOut.newLine();
		
		bOut.write(aWhatStr);                            // What this did in the model
		bOut.newLine();
		
		bOut.write( aUT.toString() );                    // What we are using this as                
		bOut.newLine();
		
	}
	
	/**
	 * MIRRORTYPE2FILE will handle writing o MirrorType to a text file.  
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aMirrorType MirrorType that holds the game type information for the model
	 * @throws IOException 
	 */
	private void mirrorType2File( BufferedWriter bOut, String aWhatStr, MirrorType aMT ) throws IOException {
		String mtString = "MIRRORTYPE:";
		
		bOut.write(mtString);                            // Type
		bOut.newLine();
		
		bOut.write(aWhatStr);                            // What this did in the model
		bOut.newLine();
		
		bOut.write( aMT.toString() );                    // What type of game this was                
		bOut.newLine();
		
	}
	
	/**
	 * APOSSETT2FILE will handle writing an Set<Position> to a text file.
	 * Preconditions:  Should be called when saving the state of the Model for future use.
	 * Postconditions: The state of a passed in Object will be outputted to the inputted output stream.
	 * @param bOut BufferedWriter holding to file we are writing to.
	 * @param aWhat String representing what this Type was originally for in the Model.
	 * @param aSet Set<Position> that holds information for the model
	 * @throws IOException 
	 */
	private void aPosSet2File( BufferedWriter bOut, String aWhatStr, Set<Position> aSet ) throws IOException {
		String aListString = "SET<POSITION>:";
		
		bOut.write(aListString);                              // What is it.
		bOut.newLine();
		
		if ( ! (aWhatStr.equals("")) ) {                      // Where it came from in Model
			bOut.write(aWhatStr);
			bOut.newLine();
		}		
		
		for ( Position pos : aSet ) {                        // Loop thru Positions in list.
			pos2File(bOut, new String(), pos );
		}
				
	}
	
	/**
	 * OUTPUTMODELSTATE will output the state of most of the model's variables to a file.  This way
	 * the user can continue playing or creating a puzzle.
	 * Preconditions:  The game must be set up before this function is called.
	 * Postconditions: The state of the model for the game will be sent to a text file, for reading
	 * 				at a later time.
	 */
	public void outputModelState( String aFileName ) {
		File file = new File(aFileName);
		try {
			BufferedWriter bWriter = new BufferedWriter( new FileWriter( file )) ;

			try {
				// UseType
				String useTypeStr = "_useType";
				useType2File( bWriter, useTypeStr, _useType );
				bWriter.newLine();

				// GameType
				String gameTypeStr = "_gameType";
				gameType2File( bWriter, gameTypeStr, _gameType );
				bWriter.newLine();

				// MirrorType
				String mirrorTypeStr = "_mirrorType";
				mirrorType2File(bWriter, mirrorTypeStr, _mirrorType);
				bWriter.newLine();

				// StartPoses
				String startPositionsStr = "_startPositions";
				aPosSet2File(bWriter, startPositionsStr, _startPositions );
				bWriter.newLine();

				// Char[][]:  Parser gets bumperlist and Size from CREATE, PLAY is just taken in.  For NXN
				String charArrStr = "_pmCreateBoard";
				twoDCharArr2File(bWriter, charArrStr, _size, _size, _pmCreateBoard);
				bWriter.newLine();

				// Char[][]: Inputs contents of the PLAY board to a text file.
				String chArrStr = "_pmPlayBoard";
				twoDCharArr2File(bWriter, chArrStr, _size, _size, _pmPlayBoard);
				bWriter.newLine();

				// CurrPos
				String currStr = "_currPos";
				pos2File(bWriter, currStr, _currPos);
				bWriter.newLine();

				// ActiveRegion
				String arStr = "_activeRegion";
				ar2File(bWriter, arStr, _activeRegion);
				bWriter.newLine();

				// Boolean:isSetUp
				String isSetupDoneStr = "_isSetupDone";
				bool2File(bWriter, isSetupDoneStr, _isSetupDone);
				bWriter.newLine();

				// Map Across
				String mapAcrossStr = "_acrossHints";
				map2File(bWriter, mapAcrossStr, _acrossHints);
				bWriter.newLine();

				// Map Down
				String mapDownStr = "_downHints";
				map2File(bWriter, mapDownStr, _downHints);
				bWriter.newLine();

				// Dict 1
				String dictStr = "_theDictionary";
				dict2File(bWriter, dictStr, _theDictionary);
				bWriter.newLine();

				// WordBank
				String bankStr = "_wordBank";
				dict2File(bWriter, bankStr, _wordBank);
				bWriter.newLine();
			} finally {
				bWriter.close();                             // Make sure Close BufferedWriter();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * FILE2USETYPE will take in the values that occur after a user type indicator is found in 
	 * the input when reading from a file that was saved previously.
	 * Preconditions:  A file with all the information for the Model must be being read.  Meaning
	 * 				a file must be saved to be read from.
	 * Postconditions: The values in the file relating to userType will be assigned to right variable
	 * @param bIn BufferedReader that we are getting info from.
	 * @throws IOException If input is has incorrect format, or value read is not valid for this type.
	 */
	private void file2UseType( BufferedReader bIn ) throws IOException {
		bIn.readLine();                                                     // Skip varName becuase only one UseType in Model.
		String useTypeStr = bIn.readLine();                                    // Get Value                
		
		if ( ( useTypeStr != null ) && 
				(( useTypeStr.equals(UseType.CREATE.toString())) 
						|| (useTypeStr.equals(UseType.PLAY.toString()))) ) {   // If Value is valid and NOT NULL         
			_useType = UseType.valueOf( useTypeStr );                          // Apply to variable
		} else {
			throw new IOException("FILE2USETYPE: Incorrect UseType passed in.");          // Input is incorrect.
		}
		
	}
	
	/**
	 * FILE2GAMETYPE will take in the values that occur after a GameType indicator is found in 
	 * the input when reading from a file that was saved previously.
	 * Preconditions:  A file with all the information for the Model must be being read.  Meaning
	 * 				a file must have been saved to be read from.
	 * Postconditions: The values in the file relating to gameType will be assigned to the appropriate
	 * 				variable. 
	 * @param bIn BufferedReader that we are getting info from.
	 * @throws IOException If input is has incorrect format, or value read is not valid for this type.
	 */
	private void file2GameType( BufferedReader bIn ) throws IOException {
		bIn.readLine();                                                     // Skip varName becuase only one UseType in Model.
		String gameTypeStr = bIn.readLine();                                    // Get Value                
		
		if ( ( gameTypeStr != null ) && 
				(( gameTypeStr.equals(GameType.NXN.toString())) 
						|| (gameTypeStr.equals(GameType.CRISSCROSS.toString()))) ) {   // If Value is valid and NOT NULL         
			_gameType = GameType.valueOf( gameTypeStr );                          // Apply to variable
		} else {
			throw new IOException("FILE2GAMETYPE: Incorrect GameType passed in.");          // Input is incorrect.
		}
		
	}

	/**
	 * FILE2MIRRORTYPE will take in the values that occur after a MirrorType indicator is found in 
	 * the input when reading from a file that was saved previously.
	 * Preconditions:  A file with all the information for the Model must be being read.  Meaning
	 * 				a file must have been saved to be read from.
	 * Postconditions: The values in the file relating to mirrorType will be assigned to the appropriate
	 * 				variable. 
	 * @param bIn BufferedReader that we are getting info from.
	 * @throws IOException If input is has incorrect format, or value read is not valid for this type.
	 */
	private void file2MirrorType( BufferedReader bIn ) throws IOException {
		bIn.readLine();                                                     // Skip varName becuase only one UseType in Model.
		String mirrorTypeStr = bIn.readLine();                                    // Get Value                
		
		if ( ( mirrorTypeStr != null ) && 
				(( mirrorTypeStr.equals(MirrorType.DOWNHILL.toString())) 
						|| ( mirrorTypeStr.equals(MirrorType.FLATFLIP.toString()))
						|| ( mirrorTypeStr.equals(MirrorType.FOURWAY.toString()))
						|| ( mirrorTypeStr.equals(MirrorType.UPHILL.toString()))
						|| (mirrorTypeStr.equals(MirrorType.FLAT.toString()))) ) {   // If Value is valid and NOT NULL         
			_mirrorType = MirrorType.valueOf( mirrorTypeStr );                          // Apply to variable
		} else {
			throw new IOException("FILE2MIRRORTYPE: Incorrect MirrorType passed in.");          // Input is incorrect.
		}
		
	}
	
	/**
	 * FILE2POSITION will take values that come after "POSITION:" in a text file from a previously
	 * saved session.  
	 * Preconditions:  A file with all the information for the Model must be being read.  Meaning
	 * 				a file must have been saved to be read from.  Should ONLY be used on free Positions
	 * 				NOT Positions in other dataStructures, since their structure in a file is different.
	 * Postconditions: The values of a Position will be read from the text file and inputted into
	 * 				the appropriate Position in the Model, if possible.
	 * @param bIn BufferedReader that we are getting info from.
	 * @throws IOException 
	 */
	private void file2Position( BufferedReader bIn ) throws IOException {
		String hold = bIn.readLine();                                               // There is only 1 free Position, eat varName
		
		String posValuesStr = bIn.readLine();                         // Read values.  ALWAYS SHOULD BE HERE
		String[] posValues = posValuesStr.split(" ");                 // Split on the space between values
		
		if ( 3 != posValues.length ) {                                // If value does not split into 3 equal parts, something is wrong with file format.
			throw new IOException("FILE2POSITION: Incorrect Position values passed in, " + posValuesStr );
		} else {
			_currPos = new Position(Integer.valueOf(posValues[0]), Integer.valueOf(posValues[1]), Integer.valueOf(posValues[2]));      // Set our _currPos to previously saved value.
		}
		
	}
		
	/**
	 * FILE2POSSET will take the values that come after SET<POSITION>: in a previously save text file for 
	 * model state.  
	 * Preconditions:  A file with all the information for the Model must be being read.  Meaning
	 * 				a file must have been saved to be read from by this model.
	 * Postconditions: The values for this variable will be read in from the text file.
	 * @param bIn BufferedReader that we are getting info from.
	 * @throws IOException 
	 */
	private void file2PosSet( BufferedReader bIn ) throws IOException {
		bIn.readLine();                                           // Skip variable name, since there is only 1 Set<Position>

		String nxtLine = bIn.readLine();
		while ( nxtLine.equals("POSITION:") ) {                   // Loop if see "POSITION:"
			String[] posVals = bIn.readLine().split(" ");         // Split up NEXT LINE, so the values so can be set in Positions

			if ( 3 == posVals.length ) {
				Position aPos = new Position( Integer.valueOf(posVals[0]), Integer.valueOf(posVals[1]), Integer.valueOf(posVals[2]) );
				_startPositions.add(aPos);                        // Add Position to variable
			} else {
				throw new IOException("FILE2POSSET: Incorrect format.");
			}
			
			nxtLine = bIn.readLine();                             // Read in Next Line, either POSITION: or BLANK   
		}

	}
	
	/**
	 * FILE2TWODCHAR will take the values that come up after CHAR[][]: in a previously saved text file.
	 * Preconditions:  A file with all the information for the Model must be being read.  Meaning
	 * 				a file must have been saved to be read from by this model.
	 * Postconditions: The values for this variable will be read in from the text file.  Furthermore,
	 *  				bumper placement, and size will be deteremined.  Function works for the CREATE and PLAY boards. 
	 * @param bIn BufferedReader that we are getting info from.	 
	 * @throws IOException 
	 */
	private void file2TwoDChar( BufferedReader bIn ) throws IOException {
		String cString = "_pmCreateBoard";
		String pString = "_pmPlayBoard";
		boolean isCreateBoard = true;
		String[] splitLine = null;
		String line = bIn.readLine();                                        // Read name of variable
		char[][] aBoard = null;
		
		if ( line.equals(cString) ) {                                        // Which char[][] variable is it?
//			aBoard = _pmCreateBoard ;
			isCreateBoard = true;
		} else if ( line.equals(pString) ) {
//			aBoard = _pmPlayBoard;
			isCreateBoard = false;
		} else {
			throw new IOException("CHAR[][]: File format is incorrect.");
		}
		
//		splitLine = bIn.readLine().split(" ");                               // Split on single space to get sizes.
		line = bIn.readLine();                               // Either NULL or 2 nums.

//		_size = _sizeAcross = Integer.valueOf(splitLine[0]);                 // Right now only square boards allowed.
//		_sizeDown = Integer.valueOf(splitLine[1]);                           // Right now only square boards allowed.
//
//		aBoard = new char[_size][_size];                                     // Assign char[][]
		
//		if ( ! (splitLine[0].equals("NULL")) ) {
		if ( ! (line.equals("NULL")) ) {
			
			splitLine = line.split(" ");                                         // Split on single space.
			_size = _sizeAcross = Integer.valueOf(splitLine[0]);                 // Right now only square boards allowed.
			_sizeDown = Integer.valueOf(splitLine[1]);                           // Right now only square boards allowed.
			aBoard = new char[_size][_size];                                     // Assign char[][]
			
			int cnt = 0;                                                         // Will loop till equals _size
			while ( cnt < _size ) {
				line = bIn.readLine();                                           // Read Row of 2DArr

				for ( int itr = 0; itr < _size; itr++ ) {			             // Loop till _size 
					char aChar = line.charAt(itr);                               // Hold char for analysis

					if ( '@' == aChar ) {                                        // Add to Bumper list
						_bumperList.add(new Position(cnt, itr ));
					} else if ( '.' == aChar ) {                                 // Turn dots into char0's
						aChar = 0;
					}

					aBoard[cnt][itr] = aChar ;                                   // Make char[cnt][itr] = appropriate char
				}

				cnt++;                                                           // Increment Row
			}
			
			if ( true == isCreateBoard ) {                                        // Which char[][] variable is it?
				_pmCreateBoard = aBoard;
			} else {
				_pmPlayBoard = aBoard;
			}
			
		} else {                                                                 //
//			if ( true == isCreateBoard ) {                                        // Which char[][] variable is it?
//				_pmCreateBoard = aBoard;
//			} else {
//				_pmPlayBoard = aBoard;
//			} 
			
		}
		
	}
	
	/**
	 * FILE2AR will take a file that was saved from a previous model and input that model's 
	 * state into the present model.
	 * Preconditions:  This model MUST be new.  Otherwise, behavior of this function is uncertain.  
	 * 				The inputted file must be from a previous save of a model's state.
	 * Postconditions: The state of this part of the model will now match the state of the one that was
	 * 				previously saved.
	 * @param bIn BufferedReader that we are getting info from.	 
	 * @throws IOException 
	 */
	private void file2Ar( BufferedReader bIn ) throws IOException {
		bIn.readLine();                                           // Skip variable name, since there is only 1 ActiveRegion
		String nxtLine = "";
		String posName = "POSITION:";
		Position arPos = new Position();
		int arLength = 0;
		int arOrient = 0;
		
		nxtLine = bIn.readLine();                                                      
		if ( nxtLine.equals(posName) ) {                                                     // Must say "POSITION:"
			String[] posSplit = bIn.readLine().split(" ");
			
			if ( 3 == posSplit.length ) {                                                    // Must be made up of 3 nums separated by single spc
				
				arPos.setPosition(Integer.valueOf(posSplit[0]), Integer.valueOf(posSplit[1]), Integer.valueOf(posSplit[2]));
				
				nxtLine = bIn.readLine();
				if ( nxtLine.matches("[0-9]") ) {                                            // Must be a number
					arLength = Integer.valueOf(nxtLine);
					
					nxtLine = bIn.readLine();
					if ( nxtLine.matches("^(false|true)$") ) {                               // Must be either T/F        
						arOrient = ( ( nxtLine.equals("false") ) ? 0 : 1 );                  // 0 for false, 1 for true
						
						_activeRegion.setActiveRegion(arPos, arLength, arOrient);            // Set the variable
						
					} else {
						throw new IOException("FILE2AR: Incorrect format for input, input should have been \"(true|false)\".");
					}
					
				} else {
					throw new IOException("FILE2AR: Incorrect format for input, input should have been \"#\".");
				}
				
			} else {
				throw new IOException("FILE2AR: Incorrect format for input, input should have been \"# # #\".");
			}
			
		} else {
			throw new IOException("FILE2AR: Incorrect format for input, \"POSITION:\" not read.");
		}
		
	}
	
	/**
	 * FILE2MAP will take a file that was saved from a previous model and input that model's 
	 * state into the present model.
	 * Preconditions:  This model MUST be new.  Otherwise, behavior of this function is uncertain.  
	 * 				The inputted file must be from a previous save of a model's state.
	 * Postconditions: The state of this part of the model will now match the state of the one that was
	 * 				previously saved.
	 * @param bIn BufferedReader that we are getting info from.	
	 * @throws IOException 
	 */
	private void file2Map( BufferedReader bIn ) throws IOException {
		String nxtLine = "";
		String acrossVar = "_acrossHints";
		String downVar = "_downHints";
		Map<Position, String> theMap = null;
				
		nxtLine = bIn.readLine();                              // Read name of variable                                           
		if ( ( nxtLine.equals(acrossVar)) 
				|| ( nxtLine.equals(downVar)) ) {              // Check format of input
			
			if ( nxtLine.equals(acrossVar) ) {                 // Choose correct variable
				theMap = _acrossHints;
			} else {
				theMap = _downHints;
			}
			
			while ( (nxtLine = bIn.readLine()).equals("POSITION:") ) {     // Read "POSITION:"				
				
				String[] posList = bIn.readLine().split(" ");  // Read Position values
				Position thePos = new Position();
				String theStr = "";
				
				if ( 3 != posList.length ) {                   // Make sure format is correct
					throw new IOException("FILE2MAP: Incorrect format.");
				} else {
					thePos.setPosition(Integer.valueOf(posList[0]), Integer.valueOf(posList[1]), Integer.valueOf(posList[2]));     // Read in Pos info
					theStr = bIn.readLine();                   // Read in the String, may be ""
					theMap.put(thePos, theStr);                // Add value to variable
				}
				
			}
			
		} else {
			throw new IOException("FILE2MAP: Incorrect format.");
		}
		
	}
	
	/**
	 * FILE2DICT will take a file that was saved from a previous model and input that model's 
	 * state into the present model.
	 * Preconditions:  This model MUST be new.  Otherwise, behavior of this function is uncertain.  
	 * 				The inputted file must be from a previous save of a model's state.
	 * Postconditions: The state of this part of the model will now match the state of the one that was
	 * 				previously saved.
	 * @param bIn BufferedReader that we are getting info from.	
	 * @throws IOException 
	 */
	private void file2Dict( BufferedReader bIn ) throws IOException {
		String dVar = "_theDictionary";
		String wVar = "_wordBank";
		
		String nxtWord = "";
		WordDictionary theDict = null;
		
		String nxtLine = bIn.readLine();                        // Read in variable name               
		
		if ( (nxtLine.equals(dVar) ) 
				|| (nxtLine.equals(wVar)) ) {
			
			if ( nxtLine.equals(dVar) ) {
				theDict = _theDictionary;
			} else {
				theDict = _wordBank;
			}
			
			boolean iterate = true;                             // Controls the loop.
			while ( true == iterate ) {
				nxtLine = bIn.readLine();                       // Read next word, or blank, or EOF.

//				if ( ( null == ( nxtLine = bIn.readLine() ) ) 
				if ( ( null == ( nxtLine ) ) 
						|| ( 0 == nxtLine.length() ) ) {        // If end of input for Dictionary or File stop loop.
					iterate = false;
				} else {
					theDict.addWord(nxtLine);                   // Add word to Dictionary variable.
				}
				
			}

		} else {
			throw new IOException("FILE2DICT: Incorrect format.");
		}
		
	}
	
	/**
	 * FILE2BOOL will take a file that was saved from a previous model and input that model's 
	 * state into the present model.
	 * Preconditions:  This model MUST be new.  Otherwise, behavior of this function is uncertain.  
	 * 				The inputted file must be from a previous save of a model's state.
	 * Postconditions: The state of this part of the model will now match the state of the one that was
	 * 				previously saved.
	 * @param bIn BufferedReader that we are getting info from.	
	 * @throws IOException 
	 */
	private void file2Bool( BufferedReader bIn ) throws IOException {
		String tf = "";
		bIn.readLine();                              // Skip varName, only one BOOLEAN in Model
		
		tf = bIn.readLine();
		if ( ( tf.equals("true") ) 
			|| (tf.equals("false")) ) {
			_isSetupDone = Boolean.valueOf(tf);      // Set the variable.
		} else {
			throw new IOException("FILE2BOOL: Incorrect format.");
		}
	}
	
	/**
	 * INPUTMODELSTATE will take a file that was saved from a previous model and input that model's 
	 * state into the present model.
	 * Preconditions:  This model MUST be new.  Otherwise, behavior of this function is uncertain.  
	 * 				The inputted file must be from a previous save of a model's state.
	 * Postconditions: The state of this model will now match the state of the one that was
	 * 				previously saved.  T/F will be returned indicating whether file operations 
	 * 				succeeded.
	 * @param aFileName Name for the file we are looking to unarchive.
	 * @return T/F telling whether this operation succeeded.
	 */
	public boolean inputModelState( String aFileName ) {
		File savedFile = new File( aFileName );
		boolean retVal = true;

		if ( (savedFile.exists())  ) {            // If File Exists

			try {
				BufferedReader bRead = new BufferedReader( new FileReader( savedFile ));
				try {
					String line = "";

					while ( null != (line = bRead.readLine()) ) {         // While not at end of File.

						if ( 0 != line.length() ) {
							System.out.println("We are on: " + line );
							
							if ( line.equals("USETYPE:") ) {
								file2UseType( bRead );                        // Assign _useType to saved UseType
							} else if ( line.equals("GAMETYPE:")) {
								file2GameType( bRead );
							} else if ( line.equals("MIRRORTYPE:")) {
								file2MirrorType( bRead );
							} else if ( line.equals("SET<POSITION>:") ) {
								file2PosSet( bRead );
							} else if ( line.equals("CHAR[][]:") ) {
								file2TwoDChar( bRead );
							} else if ( line.equals("POSITION:") ) {
								file2Position( bRead );
							} else if ( line.equals("ACTIVEREGION:") ) {
								file2Ar( bRead );
							} else if ( line.equals("BOOLEAN:") ) {
								file2Bool( bRead );
							} else if ( line.equals("MAP<Position, String>:") ) {
								file2Map( bRead );
							} else if ( line.equals("WORDDICTIONARY:") ) {
								file2Dict( bRead );
							} else {
								throw new IOException("UNKNOWN TYPE IN FILE: " + line );
							}
						}

					}


				} finally {                                 // Make sure to close BufferedReader
					bRead.close();                          // In here becuase close() throws IOEXCEPTION
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(e.getMessage());
				retVal = false;                              // Something wrong with format.
			}

		} else {
			retVal = false;
		}

		return retVal;
	}
	
	int _orientation ;                  // % 0 means HORIZONTAL, % 1 means VERTICAL
	char[][] _pmCreateBoard ;           // ASCII representation of the board.  Size must be know b4 creation.
	char[][] _pmPlayBoard ;             // ASCII representation of the board.  This is for playing, pmCreateBoard is used for checking.
	int _size ;                         // Returns -1 if we are doing Criss-cross and not Square boards 
	int _sizeAcross ;                   // Returns -1 if we are doing Criss-cross and not Square boards 
	int _sizeDown ;                     // Returns -1 if we are doing Criss-cross and not Square boards
	boolean _isSetupDone ;                  // Tells if the board has finished setting up for creation.                    
	final int _normPlace = 5000 ;       // The normal place for Positions is 5000
	final char _bumpChar = '@';         // This character represents the Bumpers.
	final char _spcChar = ' ';          // This character represents the Bumpers.
	final int _minWordSize = 2;         // The smallest word length allowed.
	Position _currPos ;                 // This is the current active position on the board
	ActiveRegion _activeRegion ;        // This represents curr active region, start, length, orient.
	Map<Position, String> _acrossHints; // This associates Starting Positions with Hints
	Map<Position, String> _downHints;   // This associates Starting Positions with Hints
	Set<Position> _startPositions;      // This is an array with all the starting positions on the board.
	WordDictionary _theDictionary ;     // Later to be replaced by SQL Backend.  Dictionary of 1K words.
	WordDictionary _wordBank ;          // Later to be replaced by SQL Backend.  Bank of user entered words for use in puzzle.
	UseType _useType ;                  // There are functions that are used during creation and play.  They should work correctly depending on which mode the model is in.
	MirrorType _mirrorType ;            // This will tell the bumpers where to go.
	GameType _gameType ;                // This decides what kind of board is used for the game.
	ArrayList<CurrentObserver> _CurrentObservers;  // List of observers of Current's position
	ArrayList<RegionObserver> _RegionObservers;    // List of observers of the current Active Region
	ArrayList<BoardContentsObserver> _BoardContentsObservers;           // List of observers of the board's content
	ArrayList<ClueObserver> _ClueObservers;

	
	// Make appropriate and Tested functions for these.
	//Map<Point2D, Integer> _hintNumbers;         // Allows View to quickly get the Hint Number of a place on the board.
	ArrayList<Position> _bumperList ;            // Allows View to quickly get the position of all the bumpers on the board.
	public static Logger _modelLogger = Logger.getLogger("jeune_Trie");  // Logger
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		_modelLogger.setLevel(Level.FINER);
		//                                      -------------------
		//             |0   |1   |2   |
		//            -----------------
		//            0|D   |O   |G   |
		//            -----------------
		//            1|@@@@|G   |@@@@|
		//            -----------------
		//            2|A   |G   |E   |
		//            -----------------
	
	}



}
