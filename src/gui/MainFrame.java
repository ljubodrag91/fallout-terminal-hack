package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utilities.ImageUtilities;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	public static MainFrame frame ;
	private Dimension dimension;
	public static final Dimension screenSize = new Dimension(640, 480);
	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(screenSize);
		setPreferredSize(new Dimension(screenSize));
		setIconImage(ImageUtilities.getIcon());
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dimension.width/2-getSize().width/2, dimension.height/2-getSize().height/2);
		setContentPane(new MainMenuPanel());
		setResizable(false);
	}
	/**
	 * Change panels on the frame.
	 */
	public void changePanel(JPanel panel){
		this.getContentPane().removeAll();
		this.getContentPane().add(panel);
		this.revalidate();
		this.repaint();
    }
}