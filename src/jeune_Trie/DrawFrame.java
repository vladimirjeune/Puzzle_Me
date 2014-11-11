/**
 * 
 */
package jeune_Trie;

import java.awt.Container;

import javax.swing.JFrame;

/**
 * @author vladimirjeune
 *
 */
public class DrawFrame extends JFrame {
		
		public DrawFrame() {
			setTitle("DrawTest");
			setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			
			// ADD Panel to draw on.
			
			DrawPanel panel = new DrawPanel();
			Container contentPane = getContentPane();
			contentPane.add( panel );			
			
		}
		
		public static final int DEFAULT_WIDTH = 400;
		public static final int DEFAULT_HEIGHT = 400;

}
