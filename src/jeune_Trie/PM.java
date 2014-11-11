/**
 * 
 */
package jeune_Trie;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * PuzzleMe is an attempt to aid in the creation of crossword puzzle generation.
 * At the moment only some nxn sized boards can be created.
 * @author vladimirjeune
 *
 */
public class PM {

	/**
	 * @param args
	 */
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
		PMModelInterface model = new PMModel();
		PMControllerInterface controller = new PMController( model );
	}

}
