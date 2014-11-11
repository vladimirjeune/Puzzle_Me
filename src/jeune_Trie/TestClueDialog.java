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
import javax.swing.JPanel;

/**
 * @author vladimirjeune
 *
 */
public class TestClueDialog extends JDialog {

	public TestClueDialog( JFrame owner, PMModelInterface aModel, PMControllerInterface aController, boolean isAcross ) {
		super(owner, "Clue List", true );

		_model = aModel;
		_controller = aController;
		_isHorizontal = isAcross;
		
		// ContentPane
		Container contentPane = getContentPane();
		
		// Panels
		_cluePanel = new CluePanel( _model, _controller, _isHorizontal );

		JPanel finishPanel = new JPanel();
		finishPanel.setLayout(new BoxLayout(finishPanel, BoxLayout.X_AXIS));

		JButton finishBtn = new JButton("FINISH");
		
		// FinishPanel construction
		finishPanel.add(Box.createHorizontalGlue());
		finishPanel.add(finishBtn);
		finishPanel.add(Box.createRigidArea(new Dimension(10,0)));
		
		// ContentPane construction
		contentPane.add( _cluePanel, BorderLayout.CENTER);
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
		_cluePanel.startPanel();
	}
	
	private PMControllerInterface _controller;
	private PMModelInterface _model;
	private CluePanel _cluePanel;
	boolean _isHorizontal;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PMModelInterface theModel = new PMModel();
		PMControllerInterface theController = new PMController( theModel );
		
		try {
			Thread.sleep(45000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TestClueDialog cid = new TestClueDialog(null, theModel, theController, true );
		cid.startPanel();
		cid.pack();
		cid.setVisible(true);
				
		PMModel aModel = (PMModel) theModel;
		System.out.println( aModel );          // Debug.
		
		

	}

}
