package main;
import java.awt.EventQueue;
import gui.MainFrame;
public class Main {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame.frame = new MainFrame();
					MainFrame.frame.pack();
					MainFrame.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
	} 
}