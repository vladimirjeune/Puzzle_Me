/**
 * 
 */
package jeune_Trie;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author vladimirjeune
 *
 */
public class ModeInputPanel extends JPanel {

	ModeInputPanel() {

		String[] szList = { "5", "15", "19", "21", "23", "25"};      // JCombobBox only takes Objects
		
		// Create Borders
		Border lowetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleBdr = BorderFactory.createTitledBorder( lowetched, "Mode");

		// Create Sub Panels
		JPanel createPanel = new JPanel();
		JPanel playPanel = new JPanel();
		JPanel szPanel = new JPanel();
		JPanel lfPanel = new JPanel();
		JPanel rtPanel = new JPanel();
		
		// Create Components for this Panel
		JButton createBtn = new JButton( UseType.CREATE.toString());
		JButton playBtn = new JButton(UseType.PLAY.toString());
		final JComboBox szBox = new JComboBox( szList );              // Must be final, so can use in inner class.
		szBox.setEnabled( false );
		
		createPanel.add(createBtn);                                   // Flow
		playPanel.add(playBtn);                                       // Flow
		
		szPanel.setLayout(new BoxLayout(szPanel, BoxLayout.X_AXIS));  // Box
		szPanel.add(Box.createRigidArea(new Dimension(5,0)));         // Align CBox with Btn
		szPanel.add( szBox );                                   
		szPanel.add(Box.createHorizontalGlue());                      // Push CBox to Left
		
		// Actions
		createBtn.addActionListener( new
				ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) {
				_mode = UseType.CREATE;
				szBox.setEnabled(true);
			}
			
		});
		
		playBtn.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent evt ) {
				
				
				// Remove slashes before use.
				_mode = UseType.PLAY;
				
				// Get state from saved game.
				
				
				
				
				
				
				
				szBox.setEnabled(false);
			}
		}
		);
		
		szBox.addActionListener( new
				ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) {
			    
			        JComboBox cb = (JComboBox)evt.getSource();
			        String szStr = (String)cb.getSelectedItem();
			        _sideLen = Integer.parseInt(szStr);
			        			        
			    
			}
			
		});
		
		// Layout Managers for Boxes
		LayoutManager lfPanelManager = new BoxLayout(lfPanel, BoxLayout.Y_AXIS);    // Lay out Top to Botm
		LayoutManager rtPanelManager = new BoxLayout(rtPanel, BoxLayout.Y_AXIS);
		
		// Add Components to Side Panels
		lfPanel.setLayout(lfPanelManager);
		lfPanel.add(createPanel);
		lfPanel.add(szPanel);
		
		rtPanel.setLayout(rtPanelManager);
		rtPanel.add(playPanel);
		rtPanel.add(Box.createRigidArea(new Dimension(0,26)));     // Need more reliable #
		
		//add( createBtn );
		//add( playBtn );

		// Add Side Panels to this Panel
		add(lfPanel);
		add(rtPanel);
		
		// Set Title and Border
		setBorder( titleBdr);
		
	}
	
//	/**
//	 * GETMODE returns the Mode that the user wanted
//	 */
	public UseType getMode() {
		return _mode;
	}
	
	/**
	 * GETSIDELENGTH returns the size the user wanted if the Mode chosen was Create.
	 * Otherwise, it returns NULL.
	 * Preconditions:  NA
	 * Postconditions: If Create is the chosen mode, the user chosen length is returned; Otherwise, NULL is returned.
	 */
	public Integer getSideLength() {
		
		if ( UseType.CREATE.equals(_mode)) {          // There should only be a size if Create is the mode.
			return _sideLen;
		}
		return null;
	}
	
	UseType _mode ;
	Integer _sideLen ;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MockController mController = new MockController();
//		MockView mView = new MockView();
//		ModeInputDialog mid = new ModeInputDialog(null, mView, mController );
		ModeInputDialog mid = new ModeInputDialog(null, mController );
		mid.pack();
		mid.setVisible(true);
	}

}
