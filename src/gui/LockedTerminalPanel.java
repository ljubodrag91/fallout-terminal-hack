package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.GameSettings;
import utilities.ImageUtilities;

@SuppressWarnings("serial")
public class LockedTerminalPanel extends JPanel{
	   private BufferedImage image;  
	   private InputStream is = MainMenuPanel.class.getClassLoader().getResourceAsStream("fail.png");
	    
	    public LockedTerminalPanel() {
	    	
	       try {  
	           this.image = ImageUtilities.getScaledImage(ImageIO.read(is),MainFrame.screenSize.width, MainFrame.screenSize.height);
	       } catch (IOException ex) {
	            ex.printStackTrace();
	            System.exit(1);
	       }
	       this.setBorder(new EmptyBorder(5, 5, 5, 5));
	   	   this.setLayout(new GridLayout(0, 1, 0, 3)); 
	   	   
	   	JButton newGameBtn = new JButton("Retry");
	   	newGameBtn.setFont(new Font("Arial", Font.PLAIN, 20));
	   	newGameBtn.setPreferredSize(new Dimension(300, 80));
	   	newGameBtn.setOpaque(false);
	   	newGameBtn.setContentAreaFilled(false);
	   	newGameBtn.setForeground(GameSettings.getFontColor());
	   	newGameBtn.setBorder(BorderFactory.createLineBorder(GameSettings.getFontColor()));
	   	newGameBtn.setBorderPainted(false);
	   	newGameBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.changePanel(new GamePanel(MainFrame.frame));
			}
		});
	   	this.add(newGameBtn);
	       
		JButton mainMenuBtn = new JButton("Main Menu");
		mainMenuBtn.setFont(new Font("Arial", Font.PLAIN, 20));
		mainMenuBtn.setPreferredSize(new Dimension(300, 80));
		mainMenuBtn.setOpaque(false);
		mainMenuBtn.setContentAreaFilled(false);
		mainMenuBtn.setForeground(GameSettings.getFontColor());
		mainMenuBtn.setBorder(BorderFactory.createLineBorder(GameSettings.getFontColor()));
		mainMenuBtn.setBorderPainted(false);
		mainMenuBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.changePanel(new MainMenuPanel());
			}
		});
		this.add(mainMenuBtn);
	    }

	       @Override
	       protected void paintComponent(Graphics g) {
	           super.paintComponent(g);
	           g.drawImage(image, 0, 0, this);          
	       }	       
}