/**
 * 
 */
package jeune_Trie;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * @author vladimirjeune
 *
 */
public class ModeInputDialog extends JDialog {

	// Remove controller, L8r.
//	public ModeInputDialog( JFrame anOwner, PMViewInterface aView,PMControllerInterface aController ) {
	public ModeInputDialog( JFrame anOwner, PMControllerInterface aController ) {
		super(anOwner, "Choose Mode", true ) ;
		
		_controller = aController;
//		_view = aView;

		Container contentPane = getContentPane();
		Border bufEdge = BorderFactory.createEmptyBorder(3,3,0,3);
				
		// Putting Mode Button Panel on.
		_miPanel = new ModeInputPanel();
		JPanel bufPanel = new JPanel( new BorderLayout() );
		bufPanel.setBorder( bufEdge );
		
		bufPanel.add(_miPanel);
		
		// Panel for OK button
		JPanel okPanel = new JPanel();
		okPanel.setLayout(new BoxLayout(okPanel, BoxLayout.X_AXIS));
		JButton okButton = new JButton("OK");
		
		okButton.addActionListener( new
				ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) {
				_useType = _miPanel.getMode();
				_sideLen = _miPanel.getSideLength();

				_controller.cSetBoardUse( _useType );
				
				if ( null != _sideLen ) {
					_controller.cSetSize(_sideLen);
				}
				
				setVisible(false);
				//_view.startTypeDialog(null);       // This will have 2b removed.  INFLEXIBLE.
				
			}
			
		});
		
		contentPane.add(bufPanel, BorderLayout.CENTER);
		okPanel.add(Box.createHorizontalGlue());
		okPanel.add(okButton);
		okPanel.add(Box.createRigidArea(new Dimension(10,0)));
		contentPane.add(okPanel, BorderLayout.SOUTH);
		
		setSize(250,150);
		
	}
	
	/**
	 * GETMODE will get the mode that the user finally picked from the choices offered in the 
	 * Dialog.
	 * Preconditions: NA
	 * Postconditions: The mode the user chose will be returned.
	 */
	public UseType getMode() {
		return _useType;
	}
	
	/**
	 * GETSIDELENGTH returns the board size that was chosen by the user.
	 * Preconditions: NA
	 * Postconditions: If Create was chosen the size will be returned. Otherwise, NULL
	 * 				returned.
	 */
	public Integer getSideLength() {
		return _sideLen;
	}
	
	
	
	PMControllerInterface _controller;
	PMViewInterface _view;
	ModeInputPanel _miPanel;
	UseType _useType = null;
	Integer _sideLen = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PMControllerInterface mockController = new MockController();
//		PMViewInterface mockView = new MockView();
//		ModeInputDialog mid = new ModeInputDialog(null, mockView, mockController );
		ModeInputDialog mid = new ModeInputDialog(null, mockController );
		mid.pack();
		mid.setVisible(true);
	}

}
