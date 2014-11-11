/**
 * 
 */
package jeune_Trie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * This is the Panel that will hold the game board.  There could be another Panel for creation
 * of the game board, or it might be another instance of this Panel.  Have not decided.
 * @author vladimirjeune
 *
 */
public class BoardPanel extends JPanel {

	private PMModelInterface _model;
	private PMControllerInterface _controller;
    private Position _currPos = new Position(0,0);
	private int _maxPixels = 525;               // Evenly divided by 35 and 25 
	private Polygon _disabledRegionRight;
	private Polygon _disabledRegionLeft;
//	private boolean _beenClicked = false;
	
    private int _squareX ;
    private int _squareY ;
    private int _squareW ;
    private int _squareH ;
    private boolean _isCreate = true;      // Buttn presses mean different things, for start of Creation.
    private boolean _isNXN = false;         // Cannot do Bumpers for CRISSCROSS
    private boolean _isReady = false;       // Called when Bumpers are in final Position, so no more created
    private int _cellSz = 25;              // Can also be 25;
	
	/**
	 * 
	 */
	public BoardPanel() {
		// TODO Auto-generated constructor stub
		// Registering for things that we should be keeping track of in the model.
		_model.registerObserver((CurrentObserver)this);
		_model.registerObserver((RegionObserver)this);
		_model.registerObserver((BoardContentsObserver)this);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension( _maxPixels, _maxPixels ));
        //setMaximumSize(new Dimension( (_model.getSideLength() * _cellSz), (_model.getSideLength() * _cellSz) ));
        
        // CREATE RECTANGLE
        
        
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                onSquare(e.getX(),e.getY());
            }
        });
        
	}
	
	/**
	 *	CONSTRUCTOR 
	 */
	public BoardPanel( PMModelInterface aModel, PMControllerInterface aController) {
		_model = aModel;
		_controller = aController;
		
		//setGreyedRegion();
		
		// Registering for things that we should be keeping track of in the model.
		// Registering for things that we should be keeping track of in the model.
		//_model.registerObserver((CurrentObserver)this);
		//_model.registerObserver((RegionObserver)this);
		//_model.registerObserver((BoardContentsObserver)this);

		// Set up Panel
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension( _maxPixels, _maxPixels ));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                onSquare(e.getX(),e.getY());
            }
        });
        
	}

	/**
	 * 
	 */
	public void setGreyedRegion() {
		int half = ( (int) Math.ceil(_model.getSideLength()/2) * 25 );  // Works only on odd numbered boards.
		int halfPlusOne = ( (int) Math.ceil((_model.getSideLength()/2) + 1) * 25 );  // Works only on odd numbered boards.
		int side = _model.getSideLength() * 25;

//		int[] xPointsLeft = {0,           0,    side, side, halfPlusOne, halfPlusOne };
//		int[] yPointsLeft = {halfPlusOne, side, side, half, half,        halfPlusOne };
		int[] xPointsLeft = {0,           0,    (halfPlusOne - 1), (halfPlusOne - 1) };
		int[] yPointsLeft = {halfPlusOne, side, side,        halfPlusOne };

		int[] xPointsRight = {halfPlusOne, halfPlusOne, side, side };
		int[] yPointsRight = {half,        side,        side, half };
		
		//_disabledRegion = new Polygon( xPoints, yPoints, 6 );
		_disabledRegionLeft  = new Polygon( xPointsLeft, yPointsLeft, 4 );
		_disabledRegionRight = new Polygon( xPointsRight, yPointsRight, 4 );
	}
	
    public Dimension getPreferredSize() {
        //return new Dimension(250,200);
    	int sideLength = _model.getSideLength() * 25;
    	return new Dimension( sideLength, sideLength);
        
    }

    // Will disappear when resized, but OK, cause will in production, paint will represent state of Model, and will be updated.
    // You will call repaint 2X like you are supposed to.
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

        	// Filters the input to only allow places 1 more than the 
        	if ( boardInputFilter() ) {
        		_controller.cFlipBumper(_currPos);
        	}

        	System.out.println( _squareX+" "+_squareY +" "+ _squareW +" "+ _squareH );
        	System.out.println("This is what is passed to flip : " + _currPos );
        	
        	//repaint( _squareX, _squareY, _squareW, _squareH);
        	repaint();
        	
        	drawBumperPlace();
        	
        } 
    }

	/**
	 * BOARDINPUTFILTER will allow input only into the space occupying the top left of the board plus one space.
	 * Or it will allow input into the top left.  Will disallow input into the bottom half of the board.minus one.
	 * or input into the bottom right half of the board.
	 * Preconditions: NA
	 * Postconditions: T/F will be returned depending on whether the current position is allowed.
	 */
	private boolean boardInputFilter() {
		boolean retVal = false;
		int halfBoard = (int) Math.ceil(_model.getSideLength()/2);  // Works only on odd numbered boards.
		
		// Allow input into the space occupying a little bigger than the top left of the board, or allow input into the top right.
		if ( (( _currPos.getRow() <= halfBoard ) && ( (_currPos.getCol() <= halfBoard ))  ) 
				|| ( _currPos.getRow() < halfBoard ) ) {
			retVal = true;
		}
		return retVal;
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
		
//		_currPos = new Position( placeX, placeY);                         // Set current using translated coordinates.
		_currPos = new Position( placeY, placeX);                         // Set current using translated coordinates.
		
	}
    
    // To draw current state of board.
    protected void paintComponent(Graphics gfx) {
    	Graphics2D g = (Graphics2D) gfx;
        super.paintComponent(g);       
        
        // Draw red square.
//      g.drawString("This is my custom Panel!",10,20);
//        g.setColor(Color.RED);
//        g.fillRect(squareX,squareY,squareW,squareH);
//        g.setColor(Color.BLACK);
//        g.drawRect(squareX,squareY,squareW,squareH);
        
        // Stuff that has to be painted when screen is damaged.
        // Draw grid
        drawGrid(g);
        
        // Draw Bumpers from Blist ArrayList<Position> bList
        System.out.println("isCreate, and isReady : " + _isCreate + " " + _isReady );
        if ( ( true == _isCreate ) 
        		&& ( false == _isReady ) ){ 
        		//&& ( true == _beenClicked)) {
        	System.out.println("I'm spos to draw.");
        	//drawBumper(g);
        	drawBumpers(g);
        } 
        
        // Draw Colors, special are Yellow, and Orange
        drawDisabledRegion( g );
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
		//int cellSize = 35;                   // inst
        int iPMSize = _model.getSideLength();                     // inst
        int totWidth = iPMSize * _cellSz ;  // inst
        
        for ( int ctr = 0; ctr <= iPMSize; ctr++ ) {
        	int xyLine = ctr * _cellSz ;
        	
        	// Row
        	g.drawLine(xyLine, 0, xyLine, totWidth);
        	
        	// Cols
        	g.drawLine(0, xyLine, totWidth, xyLine);
        }
        
        //drawBumper(g);   
        
	}
	
	
	/**
	 * DRAWBUMPER will only draw the black squares if there was a bumper placed there.
	 * Otherwise nothing happens.  When repaint is called on the appropriate section
	 * if there is no bumper there, the background color should be painted.
	 * @param g
	 */
	private void drawBumper(Graphics2D g) {
		System.out.println("I am in drawBumper : " + _model.getPlace_Created(_currPos) + " at " + _currPos );
		if ( _model.getPlace_Created(_currPos) == '@' ) {
			System.out.println("This is spos to be a bumper yall");
			g.setColor(Color.BLACK);
			g.fillRect(_squareX,_squareY,_squareW,_squareH);
		} 
				
	}
	
	/**
	 * DRAWBUMPERS will draw all the bumpers that exists on the board.
	 */
	public void drawBumpers( Graphics2D g ) {
		char[][] theBoard = _model.getTheBoard();
		int OFFSET = 1;

		if ( ( true == _isCreate ) 
				&& ( false == _isReady ) ){ 

			for (int row = 0; row < _model.getSideLength(); row++ ) {
				for ( int col = 0; col < _model.getSideLength(); col++ ) {
					if ( '@' == theBoard[ row ][ col ] ) {
						Point2D p2d = pos2Pt( row, col );	
						System.out.println( p2d );
						_squareX = (int) p2d.getX();
						_squareY = (int) p2d.getY();
						_squareW = _cellSz - OFFSET;
						_squareH = _cellSz - OFFSET;

						g.setColor(Color.BLACK);
						g.fillRect(_squareX,_squareY,_squareW,_squareH);


					}
				}
			}
		}
	}
	
	/**
	 * POS2PT changes the positions that we get into Points, containing X,Y coordinates.
	 * This is done because Points are usually reference X,Y; while Positions on the board are 
	 * referenced Row(Y), Col(X).  So things need to be translated.
	 */
	public Point2D pos2Pt( Position aPos ) {
		Point2D retVal = new Point2D.Double();
		int theX =  (aPos.getRow() * _cellSz + 1);                               // Convert places on board top left coordinates.
		int theY = (aPos.getCol() * _cellSz + 1);                               // Convert places on board top left coordinates.
		
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
		
//		int theX =  (aRow * _cellSz + 1);                               // Convert places on board top left coordinates.
//		int theY = (aCol * _cellSz + 1);                               // Convert places on board top left coordinates.
		int theX =  (aCol * _cellSz + 1);                               // Convert places on board top left coordinates.
		int theY = (aRow * _cellSz + 1);                               // Convert places on board top left coordinates.
		
		retVal.setLocation( theX, theY); 
				
		return retVal;
	}
	
	/**
	 * DRAWDISABLEDREGION will draw the disabled region of the board that indicates to the user
	 * that they cannot input data.
	 * Preconditions:  NA
	 * Postconditions: The lower portion of the board, which is already disabled, will be greyed out.
	 */
	private void drawDisabledRegion( Graphics2D g ) {
		g.setColor(new Color(0.3F, 0.3F, 0.3F, 0.5F));
		g.fillPolygon(_disabledRegionLeft);
		g.fillPolygon(_disabledRegionRight);

	}
	
	/**
	 * DRAWBUMPERPLACE draws an ASCII representation of the board for debugging.
	 */
	private void drawBumperPlace() {
		char[][] theBoard = _model.getTheBoard();
		
		for ( int i = 0; i < _model.getSideLength(); i++ ) {
			
			for ( int c = 0; c < _model.getSideLength(); c++ ) {
				System.out.print( ('@' == theBoard[i][c] ) ? '@' : '_' ) ;
			}
			System.out.println();
		}
	}
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
