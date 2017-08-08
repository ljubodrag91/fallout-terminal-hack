package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.GameSettings;
import utilities.ImageUtilities;
import utilities.MouseUtilities;
import utilities.Utility;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	private static JEditorPane northPane;
	private static JEditorPane westPane;
	private static JEditorPane centerPane;
	private static JEditorPane eastPane;
	
    private BufferedImage image;
    private InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("terminalblack.png");
    
    
    public GamePanel(JFrame frame) {
    	this.setBorder(new EmptyBorder(5, 5, 5, 5));
    	this.setLayout(new BorderLayout(0, 0));
       try {                
          image = ImageUtilities.getScaledImage(ImageIO.read(is), MainFrame.screenSize.width, MainFrame.screenSize.height);
       } catch (IOException ex) {
            ex.printStackTrace(); 
       }

		westPane = new JEditorPane();		//left pane
		westPane.setFont( new Font("Monospaced", Font.BOLD,16));
		westPane.setForeground(GameSettings.getFontColor());
		westPane.setSize(200, 120);		
		westPane.setOpaque(false);
		westPane.setEditable(false);
		this.add(westPane, BorderLayout.WEST);
		
		centerPane = new JEditorPane();		//center pane
		centerPane.setFont( new Font("Monospaced", Font.BOLD,16));
		centerPane.setEditable(false);
		centerPane.setForeground(GameSettings.getFontColor());
		centerPane.setSize(200, 120);
		centerPane.setOpaque(false);
		this.add(centerPane, BorderLayout.CENTER);
		
		eastPane = new JEditorPane();		//right pane
		eastPane.setFont( new Font("Monospaced", Font.BOLD,16));
		eastPane.setForeground(GameSettings.getFontColor());
		eastPane.setSize(200, 120);
		eastPane.setOpaque(false);
		eastPane.setEditable(false);
		this.add(eastPane, BorderLayout.EAST);
			
		northPane = new JEditorPane();		//top pane
		northPane.setFont( new Font("Monospaced", Font.BOLD,16));
		northPane.setForeground(GameSettings.getFontColor());
		northPane.setSize(440, 100);
		northPane.setOpaque(false);
		northPane.setEditable(false);
		this.add(northPane, BorderLayout.NORTH);
		
		
		
		westPane.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(GameSettings.isScreenClickable())
					Utility.removeAllHighlights(westPane, centerPane);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(GameSettings.isScreenClickable())
					MouseUtilities.handleMouseClick(e, northPane, eastPane,westPane,centerPane,westPane);
			}
		});
		westPane.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(GameSettings.isScreenClickable())
					MouseUtilities.handleMouseHover(e, westPane,westPane,centerPane,eastPane);
			}		
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		
		centerPane.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(GameSettings.isScreenClickable())
					Utility.removeAllHighlights(westPane, centerPane);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(GameSettings.isScreenClickable())
					MouseUtilities.handleMouseClick(e, northPane, eastPane,westPane,centerPane,centerPane);
			}
		});
		centerPane.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(GameSettings.isScreenClickable())
					MouseUtilities.handleMouseHover(e, centerPane,westPane,centerPane,eastPane);
			}		
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Utility.setScreenText(northPane, westPane, centerPane,eastPane,frame);
			}
		}).start();
    }  
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);          
    }
}