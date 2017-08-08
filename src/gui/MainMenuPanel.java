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
import main.GameSettings;
import utilities.ImageUtilities;
 
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel{ 
    private BufferedImage image;  
    private InputStream is = MainMenuPanel.class.getClassLoader().getResourceAsStream("main.jpg");
    private JButton newGameBtn;
    private JButton difficultyBtn;
    private JButton exitBtn;
    
    public MainMenuPanel(){
       try {  
           this.image = ImageUtilities.getScaledImage(ImageIO.read(is),MainFrame.screenSize.width, MainFrame.screenSize.height);
       } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
       }
       
   	   this.setLayout(new GridLayout(0, 1, 0, 100)); 
   	   
   	newGameBtn = new JButton("New Game");
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
   	
   	difficultyBtn = new JButton("Difficulty");
   	difficultyBtn.setFont(new Font("Arial", Font.PLAIN, 20));
   	difficultyBtn.setPreferredSize(new Dimension(300, 80));
   	difficultyBtn.setOpaque(false);
   	difficultyBtn.setContentAreaFilled(false);
   	difficultyBtn.setForeground(GameSettings.getFontColor());
   	difficultyBtn.setBorder(BorderFactory.createLineBorder(GameSettings.getFontColor()));
   	difficultyBtn.setBorderPainted(false);
   	difficultyBtn.addActionListener(new ActionListener() {	
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFrame.frame.changePanel(SettingsPanel.getSettingsPanel());
		}
	});
   	this.add(difficultyBtn);
   	
   	exitBtn = new JButton("Exit");
   	exitBtn.setFont(new Font("Arial", Font.PLAIN, 20));
   	exitBtn.setPreferredSize(new Dimension(300, 80));
   	exitBtn.setOpaque(false);
   	exitBtn.setContentAreaFilled(false);
   	exitBtn.setForeground(GameSettings.getFontColor());
   	exitBtn.setBorder(BorderFactory.createLineBorder(GameSettings.getFontColor()));
   	exitBtn.setBorderPainted(false);
   	exitBtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	});
   	this.add(exitBtn);
    
    }  
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);          
    }
}