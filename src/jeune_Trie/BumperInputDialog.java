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
 * @author vladimirjeune
 *
 */
public class BumperInputDialog extends JDialog {

	public BumperInputDialog( JFrame owner, PMModelInterface aModel, PMControllerInterface aController ) {
		super(owner, "Create Bumpers", true );
		//setLocationRelativeTo(null);           // Sorta centers the Dialog box, not really
		// Assignment
		_model = aModel;
		_controller = aController;
		
		// ContentPane
		Container contentPane = getContentPane();
		
		// Panels
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));

		_outerBumperPanel = new BoardPanel( _model, _controller );

		JPanel finishPanel = new JPanel();
		finishPanel.setLayout(new BoxLayout(finishPanel, BoxLayout.X_AXIS));

		// BumperPanel
		
		JLabel displayLabel = new JLabel("Click squares to Create/Remove Bumpers.");
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
		contentPane.add( _outerBumperPanel, BorderLayout.CENTER);
		contentPane.add( finishPanel, BorderLayout.SOUTH);
		
		
		// Bumper Panel
		
		// ActionListener
		finishBtn.addActionListener( new
				ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) {

				_controller.cFinishBoardSetup();
				setVisible(false);
				
			}
			
		});		
		
	}
	
	
	/**
	 * SETGREYEDREGION will grey out the disabled region of the board.  This indicates
	 * to the user that they cannot create bumpers here.
	 * Preconditions:  The Model should have the size already set before the call of this function.
	 * Postconditions: The disabled region of the board will be greyed out.
	 */
	public void setGreyedRegion() {
		_outerBumperPanel.setGreyedRegion();
	}
	
	private PMControllerInterface _controller;
	private PMModelInterface _model;
	private BoardPanel _outerBumperPanel;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		PMControllerInterface mockController = new MockController();
//		PMModelInterface mockModel = new MockModel();
//		BumperInputDialog mid = new BumperInputDialog(null, mockModel, mockController );
		PMModelInterface theModel = new PMModel();
		PMControllerInterface theController = new PMController( theModel );
		BumperInputDialog mid = new BumperInputDialog( null, theModel, theController );
////		
//		mid.pack();
//		mid.setVisible(true);
		
		PMModel aModel = (PMModel) theModel;
		System.out.println( aModel );          // Debug.
	}

}
