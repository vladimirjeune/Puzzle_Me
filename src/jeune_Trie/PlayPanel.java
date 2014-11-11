/**
 * 
 */
package jeune_Trie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author vladimirjeune
 *
 */
public class PlayPanel extends JPanel implements  RegionObserver, CurrentObserver, BoardContentsObserver {

	// TODO You are going to first make the bumperPanel, 
	//      Then you are going to get the start positions from the model, becuase the controller sets things
	//        you are going to number all the start positions.
	//     You are going to create a test for the numbering that you drew,
	//		if it is wrong, FIXIT, 
	//     Create function to Color the cells in the ARegion, with Current being a special cell.
	
	//     GridLines, Bumpers, StartPositions are all redrawn when repaint is called.  They never change.
	//     HOWEVER; clicking on a cell does something differnt now, 
	// 	  	both the OLD, and NEW region will have to be redrawn.
	//      this will have to take into account the orientation, and length of the ARegion, and what is
	//      Current.   
	//      YOU WILL NEED an update function for this panel.
	//		There may be update functions for Current, Region, & BoardContents
	//      Each will have to redraw itself appropriately, OR the whole board will be updated.
	// Think.
	
	// AND PLACE NOTIFIES IN THE MODEL, OR NOTHING WILL WORK!!!!!!!!!!!!
	
	

	private PMModelInterface _model;
	private PMControllerInterface _controller;
    private Position _currPos = new Position(0,0);
    private ArrayList<Position> _bumperList;
    private Set<Position> _startSet;
    private int _currLength = 1;                  // Length of current region,
    private int _orientation = 0;                // mod2 == => HORIZONTAL, 1 => VERTICAL
	private ActiveRegion _aRegion;
	private ActiveRegion _oldRegion;              // The previous region
	private Position _oldCurrPos;                 // The previous Current
    
    
    private int _maxPixels = 625;               // Evenly divided by 25 
	
    private int _squareX ;
    private int _squareY ;
    private int _squareW ;
    private int _squareH ;
    //private boolean _isCreate = true;      // Buttn presses mean different things, for start of Creation.
    //private boolean _isNXN = false;         // Cannot do Bumpers for CRISSCROSS
    //private boolean _isReady = false;       // Called when Bumpers are in final Position, so no more created
    private int _cellSz = 25;                // Can also be 25;
    private int _numFontSz ;                         // If cellSz <= 25, make font 8pt
	private Font _numFont ;                          // Font for numbers
	private Font _letterFont ; //= new Font("SansSerif", Font.PLAIN, (cellSz - 1) - fontSz);  // Font for letter
	
	/**
	 *	CONSTRUCTOR 
	 */
	public PlayPanel( PMModelInterface aModel, PMControllerInterface aController) {
		_model = aModel;
		_controller = aController;

		_numFontSz = ((_cellSz > 25)?10:8);                              // If cellSz <= 25, make font 8pt
		//System.out.println( "numFontSz " + _numFontSz );
		_numFont = new Font("SansSerif", Font.PLAIN, _numFontSz);       // Font for numbers
		_letterFont = new Font("SansSerif", Font.PLAIN, (_cellSz - 1) - _numFontSz);  // Font for letter
		_oldRegion = new ActiveRegion();
		_oldCurrPos = new Position();
		
		// Registering for changes on the model.
		_model.registerObserver((CurrentObserver)this);
		_model.registerObserver((RegionObserver)this);
		_model.registerObserver((BoardContentsObserver)this);

		// Set up Panel
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension( _maxPixels, _maxPixels ));
        setFocusable(true);                                              // Allow to take key input.

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                onSquare(e.getX(),e.getY());
            }
        });
        
        addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				getTyped( arg0 );
			}
			
			public void keyPressed( KeyEvent arg0 ) {
				getPressed(arg0);
			}
        });
    	
	}

	/**
	 * STARTPUZZLECREATION will get the start and bumper list from the model, allow drawing.
	 * This should be called before this panel is made visibel so that the board appears ready
	 * for puzzle creation from the start.
	 */
	public void startPuzzleCreation() {
		_currPos = _model.getCurr();                       // Get Current
		_aRegion = _model.getActiveRegion();               // Get Active Region
		_bumperList = _model.getBumperList();              // Get Bumper List
		_startSet = _model.getStartPositions();            // Get Start Positions
	}
	
    /**
     * ONSQUARE takes the inputted x and y coordinates and does something appropriate to the situation
     * to the square those coordinates happen to reside in.  During board creation will take input and
     * change the color of appropriate square.  During Puzzle creation will change the background color 
     * of the appropriate square to indicate that it is the current square.
     */
    private void onSquare(int x, int y) {
        int OFFSET = 1;
        
        if ((_squareX!=x) || (_squareY!=y)) {
//            repaint(_squareX,_squareY,_squareW+OFFSET,_squareH+OFFSET);      // Call to repaint bg
//            _squareX=x;
//            _squareY=y;
//            repaint(_squareX,_squareY,_squareW+OFFSET,_squareH+OFFSET);      // Call to repaint new area. Necessary, or will not remove last instance.

        	setCoordToTopLeft(x, y, OFFSET);

        	_controller.cClick(_currPos);
        	
        	System.out.println( _squareX+" "+_squareY +" "+ _squareW +" "+ _squareH );
        	//System.out.println("This is what is passed to flip : " + _currPos );
        	
        	//repaint( _squareX, _squareY, _squareW, _squareH);
        	//repaint();
        	
        	//drawBumperPlace();
        	
        } 
    }
    
    /**
     * GETTYPED will tell what key was typed on the board.  But will only accept alphanumeric
     * characters.
     * Preconditions:  The board must be ready to use before use.
     * Postconditions: The character entered by the user will be place in the Current place on the
     * 				board.
     */
    private void getTyped( KeyEvent event ) {
    	char keyChar = event.getKeyChar();  
    	String theChar = new String( ""+ keyChar );                  

    	if ( theChar.matches("[a-zA-Z]") ) {                         // Only accept letters
    		String upper = theChar.toUpperCase();                    // Make sure only capital letters.
    		System.out.println( "This is: " + upper.charAt(0) );
    		_model.userModify( upper.charAt(0));                     // Modify Model
    	}
    	    	
    }
    
    /**
     * GETPRESSED gets the key pressed event that does not correspond to a letter.
     * Such as, Enter.  
     * Preconditions:  The board must be ready to use before use.
     * Postconditions: The pressed button will be registered in the model.
     */
    public void getPressed( KeyEvent event ) {
    	int keyCode = event.getKeyCode();
    	
    	System.out.println("Pressed : " + keyCode );
    	
    	if ( KeyEvent.VK_ENTER == keyCode ) {                    // Move to next hint.
    		_controller.cTabEnter();
    	} else if ( ( KeyEvent.VK_UP == keyCode )            
    			|| ( KeyEvent.VK_DOWN == keyCode ) 
    			|| ( KeyEvent.VK_LEFT == keyCode ) 
    			|| ( KeyEvent.VK_RIGHT == keyCode ) ) {          // Move up, down, left, or right
    		
    		keyDirection(keyCode);                               // Interpret user inputted direction.
    		
    	} else if ( KeyEvent.VK_BACK_SPACE == keyCode ) {        // Backup and erase current cell.
    		_controller.cModify('0');
    	}
    }

	/**
	 * KEYDIRECTION interprets the directional input supplied by the user.
	 * @param keyCode
	 */
	private void keyDirection(int keyCode) {
		if ( KeyEvent.VK_UP == keyCode )  {
			_controller.cArrow( MoveType.UP );
		} else if ( KeyEvent.VK_RIGHT == keyCode  )  {
			_controller.cArrow(MoveType.RIGHT);
		} else if ( KeyEvent.VK_LEFT == keyCode ) {
			_controller.cArrow(MoveType.LEFT);
		} else {  // Down
			_controller.cArrow(MoveType.DOWN);
		}
	}

	/**
	 * SETCOORDTOTOPLEFT takes the x and y input coordinates and converts them to the closest
	 * top left corner.
	 * Board should be created before use.
	 * Preconditions:  Board should be created before use.
	 * Postconditions: Inputted X, Y coordinates will be transformed to the coordinates for the 
	 * 				nearest top left corner.  And will set Current Position.
	 * @param x
	 * @param y
	 * @param OFFSET
	 */
	private void setCoordToTopLeft(int x, int y, int OFFSET) {
		int placeX = (int) ((x != 0 )? (x/_cellSz):0);	                 // Convert coords to places on board.
		int placeY = (int) ((y != 0 )? (y/_cellSz):0);
		
		_squareX = (placeX * _cellSz + 1);                               // Convert places on board top left coordinates.
		_squareY = (placeY * _cellSz + 1);
		_squareW = _cellSz - OFFSET;
		_squareH = _cellSz - OFFSET;
		
		_currPos = new Position( placeY, placeX);                         // Set current using translated coordinates.
		
	}

    /** 
     * To draw current state of board.
     */
    protected void paintComponent(Graphics gfx) {
    	Graphics2D g = (Graphics2D) gfx;
        super.paintComponent(g);       
        
        // Draw grid
        drawGrid(g);
        drawRegion(g);
        // Draw Bumpers from Blist ArrayList<Position> bList
        //        System.out.println("isCreate, and isReady : " + _isCreate + " " + _isReady );
        //if ( ( true == _isCreate ) 
        	//	&& ( false == _isReady ) ){ 
        		//&& ( true == _beenClicked)) {
        	//System.out.println("I'm spos to draw.");
        	//drawBumper(g);
        	drawBumpers(g);
        	drawNum(g);
        	drawLetter(g);
//        } 
        
        // Draw Colors, special are Yellow, and Orange

        // Draw Numbers using hintMap, HashMap<Point2D, Integer> hintMap
        
        // Draw Letters
        
    }

	/**
	 * DRAWGRID will draw the background grid for the puzzle.
	 * Preconditions:  Should only be used during for NxN baords,
	 * 				after the size of the board has been inputted by the user.
	 * Postconditions: The grid will be drawn
	 * @param g
	 */
	private void drawGrid(Graphics2D g) {
        int iPMSize = _model.getSideLength();                     // inst
        int totWidth = iPMSize * _cellSz ;  // inst
        
        for ( int ctr = 0; ctr <= iPMSize; ctr++ ) {
        	int xyLine = ctr * _cellSz ;
        	
        	// Row
        	g.drawLine(xyLine, 0, xyLine, totWidth);
        	
        	// Cols
        	g.drawLine(0, xyLine, totWidth, xyLine);
        }
        
	}
    
	/**
	 * GETPREFERREDSIZE overrides the getPreferredSize() of the JPanel.
	 */
    public Dimension getPreferredSize() {
    	int sideLength = _model.getSideLength() * 25;
    	return new Dimension( sideLength, sideLength);
        
    }
    
	/**
	 * DRAWBUMPERS will draw all the bumpers that exists in the model; on the board.
	 * Preconditions:  StartPuzzleCreation() should be called first, or bumpers may not have been inputted
	 * 				at time of call.
	 * Postconditions: Bumpers that are in the model will be properly drawn on the Panel.
	 */
    public void drawBumpers( Graphics2D g ) {
    	int OFFSET = 1;
    	
    	for ( Position place : _bumperList ) {
    		Point2D p2Pt = pos2Pt( place.getRow(), place.getCol() );
			_squareX = (int) p2Pt.getX();
			_squareY = (int) p2Pt.getY();
			_squareW = _cellSz - OFFSET;
			_squareH = _cellSz - OFFSET;

			g.setColor(Color.BLACK);
			g.fillRect(_squareX,_squareY,_squareW,_squareH);

    	}

    }
    
    
	/**
	 * DRAWNUM will draw the number that represents the beginning of a hint.
	 * Board should exists before use.
	 * Preconditions:  Board should exists before use.
	 * Postconditions: The appropriate number representing a hint will be displayed if necessary.
	 * @param g
	 */
	private void drawNum(Graphics2D g) {

		g.setColor(Color.DARK_GRAY);                                                    // Color for numbers
		g.setFont( _numFont );                                                          // Setting G2d to that font
		
		for ( Position place : _startSet ) {
    		Point2D p2Pt = pos2Pt( place.getRow(), place.getCol() );
			_squareX = (int) p2Pt.getX();
			_squareY = (int) p2Pt.getY();
//			System.out.println("X and Y and numFontSz: "+ _squareX+" "+ _squareY +" : "+ _numFontSz);

			g.drawString(new String(""+ place.getPlace()), (_squareX + 1),(_squareY + _numFontSz ));    // Draw # in top left
		}

	}
	
	/**
	 * Preconditions:  Board should be created
	 * Postconditions: The appropriate letter or no letter will be outputted, reflecting the state of the 
	 * 				current cell.
	 * @param g
	 * @param fontSz
	 */
	private void drawLetter( Graphics2D g ) {
		
		g.setColor(Color.BLACK);                      // Setting g2d to BLK for Letter
		g.setFont( _letterFont );
		
		drawRegionLetters(g);
				
	}
    
	/**
	 * DRAWREGIONLETTERS will draw all the letters in a region up until a bumper or the boundary is met.
	 * @param g
	 * @param keyPos
	 * @param isVertical  
	 */
	private void drawRegionLetters(Graphics2D g ) {
		for ( int itr = 0; itr < _model.getSideLength(); itr++ ) {
			
			for ( int cnt = 0; cnt < _model.getSideLength(); cnt++ ) {
				char cValue = _model.getPlace_Created( itr, cnt );                 // Should be the start of a region.				
				
				if ( ( cValue != '@' ) 
						&& (cValue != '0' ) ) {
					Point2D xAndY = pos2Pt( itr, cnt );        // Get X and Y Posiitons for Letter placement
					int theX = (int) xAndY.getX();
					int theY = (int) xAndY.getY();	
//					System.out.println("The point : " + xAndY );
					g.drawString(new String(""+ cValue ), ( theX + ((int)((_cellSz - charWidth(g, cValue))/2)) ),( theY + (_cellSz - 3)));
				}
				
			}
		}
		
	}
	
	/**
	 * CHARWIDTH returns the width of the letter that we are going to render so it is in the
	 * Board should exists before use.
	 * Preconditions:  
	 * Postconditions:
	 * @param g
	 * @param aletter The letter whose width we want.
	 * @return the width of this letter.
	 */
	private int charWidth(final Graphics2D g, char aLetter ) {
		char[] chArr = new char[1];                             // Nxt 4 Lines to get Wdth of Letter, to center letter in box
		chArr[0] = aLetter;
		Rectangle2D letterBox = _letterFont.getStringBounds(chArr, 0, 1, g.getFontRenderContext());

		return (int)letterBox.getWidth();
	}
	
	/**
	 * POS2PT changes the positions that we get into Points, containing X,Y coordinates.
	 * This is done because Points are usually reference X,Y; while Positions on the board are 
	 * referenced Row(Y), Col(X).  So things need to be translated.
	 */
	public Point2D pos2Pt( Position aPos ) {
		Point2D retVal = new Point2D.Double();
		int theX =  (aPos.getCol() * _cellSz + 1);                               // Convert places on board top left coordinates.
		int theY = (aPos.getRow() * _cellSz + 1);                               // Convert places on board top left coordinates.
		
		retVal.setLocation( theX, theY); 
				
		return retVal;
	}

	/**
	 * POS2PT changes the positions that we get into Points, containing X,Y coordinates.
	 * This is done because Points are usually reference X,Y; while Positions on the board are 
	 * referenced Row(Y), Col(X).  So things need to be translated.
	 */
	public Point2D pos2Pt( int aRow, int aCol ) {
		Point2D retVal = new Point2D.Double();
		
		int theX =  (aCol * _cellSz + 1);                               // Convert places on board top left coordinates.
		int theY = (aRow * _cellSz + 1);                               // Convert places on board top left coordinates.
		
		retVal.setLocation( theX, theY); 
				
		return retVal;
	}
	
	/**
	 * PT2POS takes x and y coordinates and returns a Position that would correspond to a position on the 
	 * board.
	 * Preconditions:  Board must be set up before use.
	 * Postconditions: The Position corresponding to the inputted X and Y coordinates will be returned.
	 * 
	 */
	private Position pt2Pos( int theX, int theY ) {
		int placeX = (int) ((theX != 0 )? (theX/_cellSz):0);	                 // Convert coords to places on board.
		int placeY = (int) ((theY != 0 )? (theY/_cellSz):0);
		
		return new Position( placeY, placeX);                         // Set current using translated coordinates.
		
	}
    
	public void updateRegion() {
		// TODO Auto-generated method stub
		
		// 
		_oldCurrPos = _currPos;
		_oldRegion = _aRegion;
		
		_currPos = _model.getCurr();
		_aRegion = _model.getActiveRegion();
		
		repaint();
		
		System.out.println("Updated region");
		System.out.println("Region : " + _aRegion );
		System.out.println("Current : " + _currPos );
		
		// Repaint old cells of correct length and orientation, and current
		
		
		// Set AR to match the model.
		
		// Repaint new cells of corect length and orientation, and current
		
	}


	/**
	 * REPAINTOLDREGION 
	 */
	public void visualRegionUpdate() {
		
		if ( _aRegion.match(_model.getActiveRegion()) ) {                 // If AR has not changed.
			// repaint same region, in appropriate colors.
		} else {
			// repaint old area in white.
			// repaint new area appropriately.
			// remember computer only calls it once.
		}
		
	}
	
	/** 
	 * CLEARREGION 	will clear a region by redrawing it without the colors indicating that
	 * it is an ActiveRegion.  Should be used when the user's actions cause the current Region 
	 * to no longer be the Active Region.
	 * Preconditions:  NA
	 * Postconditions: The region that formally was the Active Region will no longer be indicated
	 * 				to be so.  The coloring that indicates the region is active will no longer be
	 * 				present.
	 * @param aRegion The region to be cleared.
	 */ 
	public void clearRegion( ActiveRegion aRegion ) {
		
		
		
	}
	
	/** 
	 * DRAWREGION 	will draw a region by redrawing it with the colors indicating that
	 * it is an ActiveRegion.  Should be used when the user's actions cause the current Region 
	 * to be created.
	 * Preconditions:  Board must be set up before use.  Meaning all relevant data for presentation
	 * 				is available.
	 * Postconditions: The region that Current exists in will be colored as the active region.
	 * @param aRegion The region to be drawn.
	 */ 
	public void drawRegion( Graphics2D g) {
		Position startPos = _aRegion.getStartPos();          // Update should set Region and Curr, as well as, old region and curr.
		int OFFSET = 1;
		boolean doClear = false;
		
		Point2D xAndY = pos2Pt( startPos.getRow(), startPos.getCol() );
		int xCoord = (int) xAndY.getX() ;                          // X Coords for top left of this cell.
		int yCoord = (int) xAndY.getY() ;                          // Y Coords for top left of this cell.
		
		for ( int itr = 0; itr < _aRegion.getLength(); itr++ ) {   // Draw BG for each place covered by region.

			drawBGsInRegion(g, OFFSET, xCoord, yCoord, doClear );
			
			if ( (_aRegion.getOrientation() % 2) == 0 ) {          // Increment along row, or column.
				xCoord += _cellSz;
			} else {
				yCoord += _cellSz;
			}
			
		}
		
		// If Old Region has been assigned to, and is different than the new one.  
		// Erase the old Region, first.
		if ( ( _oldRegion.getLength() != 0 ) 
				&& ( ! (_oldRegion.match(_aRegion) ) ) ) {
		
			Position oldStartPos = _oldRegion.getStartPos();          // Update should set Region and Curr, as well as, old region and curr.
			doClear = false;
			
			Point2D oldXAndY = pos2Pt( oldStartPos.getRow(), oldStartPos.getCol() );
			int oldXCoord = (int) oldXAndY.getX() ;                          // X Coords for top left of this cell.
			int oldYCoord = (int) oldXAndY.getY() ;                          // Y Coords for top left of this cell.
			
			
			doClear = true;
			
			for ( int itr = 0; itr < _oldRegion.getLength(); itr++ ) {   // Draw BG for each place covered by region.

				drawBGsInRegion(g, OFFSET, oldXCoord, oldYCoord, doClear );
				
				if ( (_oldRegion.getOrientation() % 2) == 0 ) {          // Increment along row, or column.
					oldXCoord += _cellSz;
				} else {
					oldYCoord += _cellSz;
				}
				
			}
						
		}
		
	}

	/**
	 * @param g
	 * @param OFFSET
	 * @param xCoord
	 * @param yCoord
	 * @param doClear tells if this is a coloring or clearing call.
	 */
	private void drawBGsInRegion(Graphics2D g, int OFFSET, int xCoord, int yCoord, boolean doClear ) {

		if ( false == doClear ) {                                // Color

			if ( _currPos.match( pt2Pos( xCoord, yCoord )) ) {
				g.setColor(Color.ORANGE);
			} else {
				g.setColor(Color.CYAN);
			}
		
		} else {                                                 // Clear
			g.setColor(Color.WHITE);
		}

		g.fillRect( xCoord, yCoord, _cellSz - OFFSET, _cellSz - OFFSET);      // Actual drawing.

	}
	
	public void updateCurrent() {
		// TODO Auto-generated method stub
		
	}





	public void updateBoardContents() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
