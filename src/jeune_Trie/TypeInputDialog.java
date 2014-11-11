/**
 * 
 */
package jeune_Trie;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author vladimirjeune
 *
 */
public class TypeInputDialog extends JDialog {

	public TypeInputDialog( JFrame anOwner ) {
		super(anOwner, "Choose Game Type", true ) ;
		Container contentPane = getContentPane();
		// Create Borders
		Border lowetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		TitledBorder titleBdr = BorderFactory.createTitledBorder( lowetched, "Game Type");
		Border mtBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);

		// Create Sub Panels
		JPanel holdPanel = new JPanel();
		JPanel btnsPanel = new JPanel();
		JPanel okPanel = new JPanel();

		LayoutManager btnsPanelManager = new BoxLayout(btnsPanel, BoxLayout.X_AXIS);    // Lay out Top to Botm
		LayoutManager okPanelManager = new BoxLayout(okPanel, BoxLayout.X_AXIS);    // Lay out Top to Botm
		btnsPanel.setLayout( btnsPanelManager );
		okPanel.setLayout(okPanelManager);
		
		holdPanel.setBorder( titleBdr );
		btnsPanel.setBorder( mtBorder );
				

		// Create Components for this Panel
		JButton nxnBtn = new JButton( GameType.NXN.toString());
		JButton ccBtn = new JButton( GameType.CRISSCROSS.toString());
		
		JButton okBtn = new JButton( "OK" );


		// ActionListeners
		nxnBtn.addActionListener( new
				ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) {
				_gType = GameType.NXN;
			}
			
		});
		
		ccBtn.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent evt ) {
				_gType = GameType.CRISSCROSS;
			}
		}
		);
				
		okBtn.addActionListener( new
				ActionListener() 
		{
			public void actionPerformed(ActionEvent evt) {

				setVisible(false);
				
			}
			
		});
		
		// Add buttons
		btnsPanel.add(nxnBtn);
		btnsPanel.add(Box.createRigidArea(new Dimension(10,25)));
		btnsPanel.add(ccBtn);
		
		holdPanel.add(btnsPanel);
		
		okPanel.add( Box.createHorizontalGlue() );
		okPanel.add( okBtn );
		okPanel.add( Box.createRigidArea(new Dimension(10,0)));
		
		contentPane.add( holdPanel, BorderLayout.CENTER );
		contentPane.add( okPanel, BorderLayout.SOUTH );
		
	}
	
	/**
	 * GETGAMETYPE retruns the game type that was chosen by the user.
	 * Preconditions: NA
	 * Postconditions: The user's chosen game type will be returned.
	 */
	public GameType getGameType() {
		return _gType;
	}
		
	private GameType _gType ;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TypeInputDialog mid = new TypeInputDialog( null );
		mid.pack();
		mid.setVisible(true);

	}

}
