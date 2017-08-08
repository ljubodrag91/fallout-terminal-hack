package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import main.GameSettings;
import utilities.ImageUtilities;

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel{
	   private BufferedImage image;  
	   private InputStream is = MainMenuPanel.class.getClassLoader().getResourceAsStream("settings.png");
	   private JPanel difficulty;
	   private JPanel glue;
	   private JRadioButton novice;
	   private JRadioButton advanced;
	   private JRadioButton expert;
	   private JRadioButton master;
	   private ButtonGroup bG;
	   private JButton back;
	   private static SettingsPanel settingsPanel;
	   
	   private SettingsPanel() {
	       try {  
	           this.image = ImageUtilities.getScaledImage(ImageIO.read(is),MainFrame.screenSize.width, MainFrame.screenSize.height);
	       } catch (IOException ex) {
	            ex.printStackTrace();
	       }
	       this.setBorder(new EmptyBorder(5, 5, 5, 5));
	       this.setLayout(new BorderLayout());
	       
	       novice = new JRadioButton("Novice");
	       novice.setOpaque(false);
	       novice.setForeground(Color.WHITE);
	       novice.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent e) {
	    	    	GameSettings.setDificulty(0);
			}
	        });
	       advanced = new JRadioButton("Advanced");
	       advanced.setOpaque(false);
	       advanced.setForeground(Color.WHITE);
	       advanced.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GameSettings.setDificulty(1);
				}
			});
	       expert = new JRadioButton("Expert");
	       expert.setOpaque(false);
	       expert.setForeground(Color.WHITE);
	       expert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GameSettings.setDificulty(2);
				}
			});
	       master = new JRadioButton("Master");
	       master.setOpaque(false);
	       master.setForeground(Color.WHITE);
	       master.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GameSettings.setDificulty(3);
				}
			});
	       
	       bG = new ButtonGroup();
	       bG.add(novice);
	       bG.add(advanced);
	       bG.add(expert);
	       bG.add(master);

	       advanced.setSelected(true);
	       
	       difficulty = new JPanel();
	       difficulty.setSize(new Dimension(50, 480));
	       difficulty.setLayout(new GridLayout(0, 1, 0, 80));
	       difficulty.setOpaque(false);
	       difficulty.add(novice);
	       difficulty.add(advanced);
	       difficulty.add(expert);
	       difficulty.add(master);
	       
	       glue = new JPanel();
	       glue.setLayout(null);
	       glue.setSize(new Dimension(590, 480));
	       glue.setOpaque(false);
	       back = new JButton("Back");
	       back.setForeground(Color.WHITE);
	       back.setBounds(160, 370, 160, 30);
	       back.setOpaque(false);
	       back.setForeground(Color.WHITE);
	       back.setContentAreaFilled(false);
	       back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.frame.changePanel(new MainMenuPanel());
			}
		});
	       glue.add(back);
	       this.add(difficulty,BorderLayout.WEST);
	       this.add(glue,BorderLayout.CENTER);
	    }
	   
	   public static SettingsPanel getSettingsPanel() {
		   if(settingsPanel == null){
			   settingsPanel = new SettingsPanel();
			   return settingsPanel;
		   }else {
			   return settingsPanel;
		   }
	   }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);          
    }
}
