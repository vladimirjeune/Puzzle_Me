/**
 * 
 */
package jeune_Trie;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is just for testing.  The rest of the game will be using the panel that 
 * will be tested on this JDialog.  The dialog will be replaced by a JFrame.
 * @author vladimirjeune
 *
 */
public class PlayInputDialog extends JDialog {

	

	public PlayInputDialog( JFrame owner, PMModelInterface aModel, PMControllerInterface aController ) {
		super(owner, "Create Crossword", true );

		_model = aModel;
		_controller = aController;
		
		// ContentPane
		Container contentPane = getContentPane();
		
		// Panels
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

		_playPanel = new PlayPanel( _model, _controller );

		JPanel finishPanel = new JPanel();
		finishPanel.setLayout(new BoxLayout(finishPanel, BoxLayout.X_AXIS));

		// BumperPanel
		
		JLabel displayLabel = new JLabel("Type letters to create crossword puzzle. ");
		JButton finishBtn = new JButton("FINISH");
		
		// LabelPanel construction
		labelPanel.add( Box.createRigidArea(new Dimension(3,0)) );
		labelPanel.add(displayLabel);
		
		//outerBumperPanel.add(_model, _controller);
		
		// FinishPanel construction
		finishPanel.add(Box.createHorizontalGlue());
		finishPanel.add(finishBtn);
		finishPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		// ContentPane construction
		contentPane.add( labelPanel, BorderLayout.NORTH);
		contentPane.add( _playPanel, BorderLayout.CENTER);
		contentPane.add( finishPanel, BorderLayout.SOUTH);
		
		
		// Bumper Panel
		
	}
	
	/**
	 * STARTCREATION will be called when the data for the creation of the creation Panel is ready.
	 * It will collect the data necessary to start creation of a puzzle.
	 * Should be called before panel is made visible.
	 * Preconditions:  Should be called when all the necessary information has been entered into the model.
	 * 				Should be called before this is made visible.
	 * Postconditions: The board will be correctly set up to visually display the model's state.
	 */
	public void startCreation() {
		_playPanel.startPuzzleCreation();
	}
	
	
	
	private PMControllerInterface _controller;
	private PMModelInterface _model;
	private PlayPanel _playPanel;
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PMModelInterface theModel = new PMModel();
		PMControllerInterface theController = new PMController( theModel );
//		BumperInputDialog mid = new BumperInputDialog( null, theModel, theController );
//		
//		mid.pack();
//		mid.setVisible(true);
		
//		PlayInputDialog pid = new PlayInputDialog( null, theModel, theController );
//		pid.startCreation();
//		pid.pack();
//		pid.setVisible(true);
		
		PMModel aModel = (PMModel) theModel;
		System.out.println( aModel );          // Debug.

	}

}
