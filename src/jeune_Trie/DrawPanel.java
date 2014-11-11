///**
// * 
// */
//package jeune_Trie;
//
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.Rectangle2D;
//
//import javax.swing.JPanel;
//
///**
// * @author vladimirjeune
// *
// */
//public class DrawPanel extends JPanel {
//
//	public void paintComponent( Graphics gfx ) {
//		super.paintComponent( gfx );
//		Graphics2D gfx2 = (Graphics2D) gfx ;
//
//		// Draw Rect
//		double leftX = 100;
//		double topY = 100;
//		double width = 200;
//		double height = 150;
//
//		Rectangle2D rect = new Rectangle2D.Double( leftX, topY, width, height );
//		gfx2.draw(rect);
//
//
//
//	}
//}

package jeune_Trie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter; 
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class DrawPanel extends JPanel {

    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;
    private boolean isCreate = true;      // Buttn presses mean different things, for start of Creation.
    private boolean isNXN = true;         // Cannot do Bumpers for CRISSCROSS
    private boolean isReady = true;       // Called when Bumpers are in final Position, so no more created
    private int cellSz = 25;              // Can also be 25;
    private int maxSize = 525;            // Max amount of pixels
    private char letter = 'K';            // For Practice
    private int num1 = 1;
    private int num9 = 9;
    private int num10 = 10;
    private int num999 = 999;
    

    public DrawPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension( maxSize, maxSize ));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });
        
    }
    
    // Will disappear when resized, but OK, cause will in production, paint will represent state of Model, and will be updated.
    // You will call repaint 2X like you are supposed to.
    private void moveSquare(int x, int y) {
        int OFFSET = 1;
        if ((squareX!=x) || (squareY!=y)) {
//            repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);      // Call to repaint bg
//            squareX=x;
//            squareY=y;
//            repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);      // Call to repaint new area. Necessary, or will not remove last instance.
//            
        	int placeX = (int) ((x != 0 )? (x/cellSz):0);
        	int placeY = (int) ((y != 0 )? (y/cellSz):0);
        	
        	
        	squareX = (placeX * cellSz + 1);
        	squareY = (placeY * cellSz + 1);
        	squareW = cellSz - OFFSET;
        	squareH = cellSz - OFFSET;
        	System.out.println( squareX+" "+squareY +" "+ squareW +" "+ squareH );
        	// In real proj.  You will see if number is 0, if not /10 and int that, Put into a Pos or just have f(x,y)
        	// When that position is flipped repaint in the area in params below, will indicate the change in the model.
        	
        	repaint( squareX, squareY, squareW, squareH);
        	
        } 
    }
    

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }
    
    // To draw current state of board.
    protected void paintComponent(Graphics gfx) {
    	Graphics2D g = (Graphics2D) gfx;
        // Let UI Delegate paint first, which 
        // includes background filling since 
        // this component is opaque.
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
        
        if ( ( true == isCreate ) && ( false == isReady ) ) {
            drawBumper(g);
        } else {
        	drawCurrent(g);
        }
        
        // Draw Colors, special are Yellow, and Orange

        // Draw Numbers using hintMap, HashMap<Point2D, Integer> hintMap
        
        // Draw Letters
        
    }

	/**
	 * @param g
	 */
	private void drawGrid(Graphics2D g) {
		//int cellSize = 35;                   // inst
        int iPMSize = 5;                     // inst
        int totWidth = iPMSize * cellSz ;  // inst
        
        for ( int ctr = 0; ctr <= iPMSize; ctr++ ) {
        	int xyLine = ctr * cellSz ;
        	
        	// Row
        	g.drawLine(xyLine, 0, xyLine, totWidth);
        	
        	// Cols
        	g.drawLine(0, xyLine, totWidth, xyLine);
        }
        
        //drawBumper(g);
    
        
	}

	/**
	 * @param g
	 */
	private void drawBumper(Graphics2D g) {
		g.setColor(Color.BLACK);
		//g.fillRect(141,36,34,34);
		g.fillRect(squareX,squareY,squareW,squareH);
	}  
	
	/**
	 * DRAWCURRENT will handle drawing the all that is necessary for the current cell.
	 * That includes making the background blue.  In the version that will be used for real
	 * we will call it drawCell, and it will draw the background of regular cells blue,
	 * and the current cell brown.
	 * @param g
	 */
	private void drawCurrent(Graphics2D g) {
		int fontSz = ((25 < cellSz)?10:8);            // If cellSz <= 25, make font 8pt

		// Rendering Hints
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		drawBG(g);                                    // Draw Background  
		drawNum(g, fontSz);                           // Draw Hint number if applicable
		drawLetter(g, fontSz);                        // Draw Letter for this cell if applicable
		
	}

	/**
	 * Preconditions:  Board should be created
	 * Postconditions: The appropriate letter or no letter will be outputted, reflecting the state of the 
	 * 				current cell.
	 * @param g
	 * @param fontSz
	 */
	private void drawLetter(Graphics2D g, int fontSz) {
		Font letterFont = new Font("SansSerif", Font.PLAIN, (cellSz - 1) - fontSz);  // Font for letter

		g.setColor(Color.BLACK);                      // Setting g2d to BLK for Letter
		g.setFont( letterFont );
		g.drawString(new String(""+letter), (squareX + ((int)((cellSz - charWidth(g, letterFont))/2)) ),(squareY + (cellSz - 3)));
	}

	/**
	 * CHARWIDTH returns the width of the letter that we are going to render so it is in the
	 * Board should exists before use.
	 * Preconditions:  
	 * Postconditions:
	 * @param g
	 * @param letterFont
	 * @return
	 */
	private int charWidth(final Graphics2D g, final Font letterFont) {
		char[] chArr = new char[1];                             // Nxt 4 Lines to get Wdth of Letter, to center letter in box
		chArr[0] = letter;
		Rectangle2D letterBox = letterFont.getStringBounds(chArr, 0, 1, g.getFontRenderContext());

		return (int)letterBox.getWidth();
	}

	/**
	 * DRAWNUM will draw the number that represents the beginning of a hint.
	 * Board should exists before use.
	 * Preconditions:  Board should exists before use.
	 * Postconditions: The appropriate number representing a hint will be displayed if necessary.
	 * @param g
	 * @param fontSz
	 */
	private void drawNum(Graphics2D g, int fontSz) {
		Font numFont = new Font("SansSerif", Font.PLAIN, fontSz);       // Font for numbers

		g.setColor(Color.DARK_GRAY);                  // Color for numbers
		g.setFont( numFont );                         // Setting G2d to that font
		g.drawString(new String(""+num999), (squareX + 1),(squareY + fontSz));    // Draw # in top left
	}

	/**
	 * DRAWBG draws the background color of the cell, depending on whether this cell is the 
	 * current or not.
	 * Preconditions:  The board should exists before use.
	 * Postconditions: The appropriate background will be created for this cell.
	 * @param g
	 */
	private void drawBG(Graphics2D g) {
		g.setColor(Color.CYAN);                       // Set Color, maybe BGColor later
		g.fillRect(squareX,squareY,squareW,squareH);
	}
	
}
