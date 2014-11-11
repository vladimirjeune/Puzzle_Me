/**
 * 
 */
package jeune_Trie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * The user will input bumpers into this panel.
 * @author vladimirjeune
 */
public class BumperPanel extends JPanel {

    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;
    private boolean isCreate = true;      // Buttn presses mean different things, for start of Creation.
    private boolean isNXN = true;         // Cannot do Bumpers for CRISSCROSS
    private boolean isReady = true;       // Called when Bumpers are in final Position, so no more created
    private int cellSz = 35;              // Can also be 25;
    private int maxSize = 525;            // Max amount of pixels
    
	/**
	 * 
	 */
	public BumperPanel( PMModelInterface aModel, PMControllerInterface aController ) {
		_controller = aController;
		_model = aModel;
		
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension( maxSize, maxSize ));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
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
    
    // To draw current state of board.
    protected void paintComponent(Graphics gfx) {
    	Graphics2D g = (Graphics2D) gfx;
        super.paintComponent(g);       
        
        // Draw grid
        drawGrid(g);
        
        // Draw Bumpers from Blist ArrayList<Position> bList
        
        if ( ( true == isCreate ) && ( false == isReady ) ) {
            drawBumper(g);
        } 
        
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
        
        //drawBumper(g);   // May want to make f() that will search whole board
    
        
	}
	
	/**
	 * @param g
	 */
	private void drawBumper(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(squareX,squareY,squareW,squareH);
	}  

	private PMControllerInterface _controller ;
	private PMModelInterface _model;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
