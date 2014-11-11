///**
// * 
// */
//package jeune_Trie;
//
//import javax.swing.JFrame;
//
///**
// * @author vladimirjeune
// *
// */
//public class DrawTest {
//
//	/**
//	 * @param args
//	 */
//	@SuppressWarnings("deprecation")
//	public static void main(String[] args) {
//
//		DrawFrame frame = new DrawFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.show();
////		frame.pack();
////        frame.setVisible(true);
//	}
//	
//}

package jeune_Trie;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class DrawTest {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.add(new DrawPanel());
        f.pack();
        f.setVisible(true);
    }
}
