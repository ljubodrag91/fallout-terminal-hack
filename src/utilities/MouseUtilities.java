package utilities;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JEditorPane;
import gui.LockedTerminalPanel;
import gui.MainFrame;
import gui.UnlockedTerminalPanel;
import main.GameSettings;

public class MouseUtilities {
	private static boolean livesRefreshed = false;
	public static String howeredWord = "";
	
	public static void handleMouseHover(MouseEvent e,JEditorPane pane,JEditorPane westPane,JEditorPane centerPane,JEditorPane eastPane) {
		JEditorPane editor = (JEditorPane) e.getSource();
		Point pt = new Point(e.getX(), e.getY());
		howeredWord = Utility.getHoveredWordAndHighlightIt(pane.getText(),editor.viewToModel(pt),pane,westPane,centerPane);	
		StringBuilder sb = new StringBuilder(GameSettings.getEastPaneText());
		sb.delete(GameSettings.getEastPaneText().length()-GameSettings.getNumOfEastPaneCharInRow(), GameSettings.getEastPaneText().length());
		String empty = "";
		int numberOfEmptySpaces = GameSettings.getNumOfEastPaneCharInRow()-1 - howeredWord.length();
			for(int i = 0;i<numberOfEmptySpaces;i++)
					empty+=" ";
		sb.append(">" + howeredWord + empty);
		GameSettings.setEastPaneText(sb.toString());
		eastPane.setText(GameSettings.getEastPaneText());
	}

	public static void handleMouseClick(MouseEvent e,JEditorPane northPane,JEditorPane eastPane,JEditorPane westPane,JEditorPane centerPane,JEditorPane currentPane) {
			boolean isCorrectWord; 
			JEditorPane editor = (JEditorPane) e.getSource();
			Point pt = new Point(e.getX(), e.getY());
			String clickedWord = Utility.getClickedWord(currentPane.getText(), editor.viewToModel(pt),currentPane);	
			StringBuilder sb = new StringBuilder(GameSettings.getEastPaneText());
			if(clickedWord.length()>4 && Character.isLetter(clickedWord.charAt(0)))
				isCorrectWord = clickedWord.length() == Utility.compareTwoStrings(GameSettings.getTarget(), clickedWord);
			else
				isCorrectWord = false;
				if(!clickedWord.isEmpty() && !Character.isLetter(clickedWord.charAt(0))) {
					sb.delete(GameSettings.getEastPaneText().length()-GameSettings.getNumOfEastPaneCharInRow(), GameSettings.getEastPaneText().length());
					sb.delete(0, GameSettings.getNumOfEastPaneCharInRow() * 2);
					String empty = "";
					int numberOfEmptySpaces = GameSettings.getNumOfEastPaneCharInRow()-2 - clickedWord.length();
						for(int i = 0;i<numberOfEmptySpaces;i++)
								empty+=" ";
					//add clicked word with number of remaining free spaces
					sb.append(">" + clickedWord + empty + " ");
					
					
					if(GameSettings.getCounter() > 0 && !livesRefreshed) {
						GameSettings.setCounter(0);
						livesRefreshed = !livesRefreshed;
						sb.append(">Tries Reset       ");
					}
					else {
						Utility.removeDud(westPane, centerPane);
						sb.append(">Dud Removed       ");
					}
					sb.append(">                  ");
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Sound.playSound("overword");
						}
					}).start();
				}else
				if(!clickedWord.isEmpty()) {
					//if correct word is clicked,play sound
					if(isCorrectWord) {
						if(Utility.timedBoolean()) {
							Sound.playSound("correct");
						}
						//decrease number of attempts left
						GameSettings.setCounter(GameSettings.getCounter()+1);
					}else {
						//if incorrect word is clicked,play sound
						if(Utility.timedBoolean()) {
							Sound.playSound("incorrect");
						}
						//decrease number of attempts left
						GameSettings.setCounter(GameSettings.getCounter()+1);
					}
					//19-number of characters in eastPane row
					sb.delete(GameSettings.getEastPaneText().length()-GameSettings.getNumOfEastPaneCharInRow(), GameSettings.getEastPaneText().length());
					//remove first three rows
					if(!isCorrectWord) 
						sb.delete(0, GameSettings.getNumOfEastPaneCharInRow() * 3);
					//remove first two rows
					else
						sb.delete(0, GameSettings.getNumOfEastPaneCharInRow() * 2);
					String empty = "";
					int numberOfEmptySpaces = GameSettings.getNumOfEastPaneCharInRow()-2 - clickedWord.length();
						for(int i = 0;i<numberOfEmptySpaces;i++)
								empty+=" ";
					//add clicked word with number of remaining free spaces
					sb.append(">" + clickedWord + empty + " ");
					if(!isCorrectWord) {
						sb.append(">Entry denied" +"      ");
						if(GameSettings.getCounter() < 4)
							sb.append(">Likeness = " + Utility.compareTwoStrings(GameSettings.getTarget(), clickedWord) +"      ");
						else
							sb.append(">Init Lockout      ");
					}else {
						sb.append(">Access granted" +"    ");
					}
					sb.append(">                  ");
				}
					GameSettings.setEastPaneText(sb.toString());
					eastPane.setText(GameSettings.getEastPaneText());
					northPane.setText(GameSettings.getNorthPaneText());
					howeredWord="";
		if(GameSettings.getCounter() >= GameSettings.getAttempts() && !isCorrectWord) {
			//TERMINAL LOCKED
			GameSettings.setGameOver(true);
			GameSettings.setScreenClickable(false);
			Utility.removeAllHighlights(westPane, centerPane);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Utility.initTerminalLockout(northPane, westPane, centerPane, eastPane);
						Utility.resetGame();
						MainFrame.frame.changePanel(new LockedTerminalPanel());
					} catch (InterruptedException e1) {
						e1.printStackTrace();
						System.exit(1);
					}
				}
			}).start();
				
			}else if(isCorrectWord) {
				//SUCCESSFULL UNLOCK
				GameSettings.setGameOver(true);
				GameSettings.setScreenClickable(false);
				Utility.removeAllHighlights(westPane, centerPane);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Utility.initTerminalLockout(northPane, westPane, centerPane, eastPane);
							Utility.resetGame();
							MainFrame.frame.changePanel(new UnlockedTerminalPanel());
						} catch (InterruptedException e1) {
							e1.printStackTrace();
							System.exit(1);
						}
					}
				}).start();	
			}
	}
}