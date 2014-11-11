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
public class TestBottomDialog extends JDialog {

	
	
	
	public TestBottomDialog( JFrame owner, PMModelInterface aModel, PMControllerInterface aController ) {
		super(owner, "Clue Input", true );

		_model = aModel;
		_controller = aController;
		
		// ContentPane
		Container contentPane = getContentPane();
		
		// Panels
		_clueInputPanel = new ClueInputPanel( _model, _controller );

		JPanel finishPanel = new JPanel();
		finishPanel.setLayout(new BoxLayout(finishPanel, BoxLayout.X_AXIS));

		JButton finishBtn = new JButton("FINISH");
		
		// FinishPanel construction
		finishPanel.add(Box.createHorizontalGlue());
		finishPanel.add(finishBtn);
		finishPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		// ContentPane construction
		contentPane.add( _clueInputPanel, BorderLayout.CENTER);
		contentPane.add( finishPanel, BorderLayout.SOUTH);
		
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
	 * StartPanel
	 */
	public void startPanel() {
		_clueInputPanel.startPanel();
	}
	
	private PMControllerInterface _controller;
	private PMModelInterface _model;
	private ClueInputPanel _clueInputPanel;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PMModelInterface theModel = new PMModel();
		PMControllerInterface theController = new PMController( theModel );
		TestBottomDialog cid = new TestBottomDialog(null, theModel, theController);
		cid.startPanel();
		cid.pack();
		cid.setVisible(true);
				
		PMModel aModel = (PMModel) theModel;
		System.out.println( aModel );          // Debug.
		
		

	}

}
