/**
 * 
 */
package jeune_Trie;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author vladimirjeune
 *
 */
public class ContentPanel extends JPanel  implements  RegionObserver, CurrentObserver, BoardContentsObserver {

	
	
	
//	public ContentPanel( JFrame owner, PMModelInterface aModel, PMControllerInterface aController ) {
//		super( );
//
//		_model = aModel;
//		_controller = aController;
//		
//		// ContentPane
////		Container contentPane = getContentPane();
//		
//		// Panels
//		JPanel labelPanel = new JPanel();
//		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
//
//		_playPanel = new PlayPanel( _model, _controller );
//
//		JPanel finishPanel = new JPanel();
//		finishPanel.setLayout(new BoxLayout(finishPanel, BoxLayout.X_AXIS));
//
//		// BumperPanel
//		
//		JLabel displayLabel = new JLabel("Type letters to create crossword puzzle. ");
//		JButton finishBtn = new JButton("FINISH");
//		
//		// LabelPanel construction
//		labelPanel.add( Box.createRigidArea(new Dimension(3,0)) );
//		labelPanel.add(displayLabel);
//		
//		//outerBumperPanel.add(_model, _controller);
//		
//		// FinishPanel construction
//		finishPanel.add(Box.createHorizontalGlue());
//		finishPanel.add(finishBtn);
//		finishPanel.add(Box.createRigidArea(new Dimension(10,0)));
//		
//		// ContentPane construction
//		contentPane.add( labelPanel, BorderLayout.NORTH);
//		contentPane.add( _playPanel, BorderLayout.CENTER);
//		contentPane.add( finishPanel, BorderLayout.SOUTH);
//		
//		
//		// Bumper Panel
//		
//	}
//	
//	/**
//	 * STARTCREATION will be called when the data for the creation of the creation Panel is ready.
//	 * It will collect the data necessary to start creation of a puzzle.
//	 * Should be called before panel is made visible.
//	 * Preconditions:  Should be called when all the necessary information has been entered into the model.
//	 * 				Should be called before this is made visible.
//	 * Postconditions: The board will be correctly set up to visually display the model's state.
//	 */
//	public void startCreation() {
//		_playPanel.startPuzzleCreation();
//	}
//	
//	
//	
//	private PMControllerInterface _controller;
//	private PMModelInterface _model;
//	private PlayPanel _playPanel;
//	
//	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void updateRegion() {
		// TODO Auto-generated method stub
		
	}

	public void updateCurrent() {
		// TODO Auto-generated method stub
		
	}

	public void updateBoardContents() {
		// TODO Auto-generated method stub
		
	}

}
