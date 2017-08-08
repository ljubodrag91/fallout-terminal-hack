package utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import main.GameSettings;

public class Utility {
	private final static Color highlightColor = new Color(73,246,83);
	private static int lastCaretPosition; 
	private static int charPrintDelay = 5;
	private static int rowRemoveDelay = 100;
	private static int dStart;
	private static int dEnd;
	private static List<String> words = null;
	private static long lastTimePlayed;
	
	public static String getHoveredWordAndHighlightIt(String content, int caretPosition,JEditorPane currentPane,JEditorPane westPane,JEditorPane centerPane) {
		int charIndex = caretPosition;
		boolean foundStart = false;
		boolean foundEnd = false;
		int selectionEnd = 0;	
		int highlightStart = 0;
		int highlightEnd = 0;
		//if char at caret is not hex, or empty space
		if(caretPosition < content.length() && !isHex(caretPosition, content) && content.charAt(caretPosition) != ' ') {
			if(Character.isLetter(content.charAt(caretPosition))) {
				while(!foundStart) {
					if (Character.isLetter(content.charAt(caretPosition))){
						caretPosition--;
					}else {
						caretPosition++;
						highlightStart = caretPosition;			//start index of word in whole text
						foundStart = true;
						content = content.substring(caretPosition);
					}
				}
				int length = content.length();
				while(selectionEnd!=length && !foundEnd) {
					if (Character.isLetter(content.charAt(selectionEnd))){
						selectionEnd++;
					}else {
						foundEnd = true;
						content = content.substring(0,selectionEnd);
						highlightEnd = highlightStart + content.length();	//end index of word in whole text
					}
				}
				//highlighting words
				if(!(content.length() < 4)) {
					if(timedBoolean()& lastCaretPosition!=caretPosition) {
						new Thread(new Runnable() {
						     public void run() {
						    	 Sound.playSound("overword");
						     }
						}).start();
						lastCaretPosition = caretPosition;
					}
					 try{          
					 DefaultHighlighter.DefaultHighlightPainter highlightPainter = 
							 new DefaultHighlighter.DefaultHighlightPainter(highlightColor);
					 removeAllHighlights(westPane, centerPane);
					 currentPane.getHighlighter().addHighlight(highlightStart, highlightEnd,highlightPainter);
					 
					 }catch(Exception ex){
							 ex.printStackTrace();
							 System.exit(1);
					 }
				 }//highlighting letter characters
				 else {
					 if(timedBoolean()& lastCaretPosition!=caretPosition) {
						 new Thread(new Runnable() {
						     public void run() {
						    	 Sound.playSound("oversymbol");
						     }
						}).start();
							lastCaretPosition = caretPosition;
						}
					 try {
						 DefaultHighlighter.DefaultHighlightPainter highlightPainter = 
								 new DefaultHighlighter.DefaultHighlightPainter(highlightColor);
						removeAllHighlights(westPane, centerPane);
						currentPane.getHighlighter().addHighlight(charIndex, charIndex + 1,highlightPainter);
					} catch (BadLocationException e) {
							e.printStackTrace();
							System.exit(1);
					}
				 }
					return content;
			}//symbol characters
			else if(isDud(caretPosition, content)){
				 try {
					 if(timedBoolean() && lastCaretPosition!=caretPosition) {
							new Thread(new Runnable() {
							     public void run() {
							    	 Sound.playSound("overword");
							     }
							}).start();
							lastCaretPosition = caretPosition;
						}
					 DefaultHighlighter.DefaultHighlightPainter highlightPainter = 
							 new DefaultHighlighter.DefaultHighlightPainter(highlightColor);
					 removeAllHighlights(westPane, centerPane);
					 currentPane.getHighlighter().addHighlight(dStart, dEnd,highlightPainter);
				} catch (BadLocationException e) {
							e.printStackTrace();
							System.exit(1);
				}
				 return content.substring(dStart, dEnd);
			}else{
				if(timedBoolean() && lastCaretPosition!=caretPosition) {
					new Thread(new Runnable() {
					     public void run() {
					    	 Sound.playSound("oversymbol");
					     }
					}).start();
					lastCaretPosition = caretPosition;
				}
				 try {
					 DefaultHighlighter.DefaultHighlightPainter highlightPainter = 
							 new DefaultHighlighter.DefaultHighlightPainter(highlightColor);
					 removeAllHighlights(westPane, centerPane);
					 currentPane.getHighlighter().addHighlight(charIndex, charIndex + 1,highlightPainter);
				 	} catch (BadLocationException e) {
							e.printStackTrace();
							System.exit(1);
				 	}
				return "";
			}
		}else {
			removeAllHighlights(westPane, centerPane);
			return "";
		}
	}
	
	//play sound at min 600 ms difference
	public static boolean timedBoolean() {
		long now = System.currentTimeMillis();
		
		if (lastTimePlayed+600> now) {
            return false;
		}else {
			lastTimePlayed = now;
			return true;
		}
	}
	public static String getClickedWord(String content, int caretPosition,JEditorPane currentPane) {
		boolean foundStart = false;
		boolean foundEnd = false;
		int selectionEnd = 0;	
		StringBuilder text = new StringBuilder(content);
		if(text.toString().equals(currentPane.getText()))
		//if char at caret is symbol,ignore
		if(caretPosition < content.length() && Character.isLetter(content.charAt(caretPosition))) { 
		while(!foundStart) {
			if (Character.isLetter(content.charAt(caretPosition))){
				caretPosition--;
			}else {
				caretPosition++;
				foundStart = true;
				content = content.substring(caretPosition);
			}
		}
		int length = content.length();
		while(selectionEnd!=length && !foundEnd) {
			if (Character.isLetter(content.charAt(selectionEnd))){
				selectionEnd++;
			}else {
				foundEnd = true;
				content = content.substring(0,selectionEnd);
			}
		}
			return (content.length()<4)?"":content;
		}
		if(isDud(caretPosition, content)){
			String dot = "";
			for(int i = 0;i < dEnd-dStart;i++)
				dot+=".";
			text.delete(dStart, dEnd);
			text.insert(dStart, dot);
			currentPane.setText(text.toString());
			return content.substring(dStart,dEnd);
			
		}else {
			return "";
		}     
	}

	//check how many characters are at same index for target word and clicked word
	public static int compareTwoStrings(String target,String clicked) {
		
		int counter = 0;
		char[] targetArray = target.toCharArray();
		char[] checkArray = clicked.toCharArray();
		
		for(int i = 0;i<targetArray.length;i++) {
			if(String.valueOf(targetArray[i]).equals(String.valueOf(checkArray[i]))) {
				counter++;
			}
		}	
		return counter;
	}


	//remove highlights from words
	public static void removeAllHighlights(JEditorPane westPane,JEditorPane centerPane) {
		 westPane.getHighlighter().removeAllHighlights();
		 centerPane.getHighlighter().removeAllHighlights();
	}
	public static void setScreenText(JEditorPane northPane,JEditorPane westPane,JEditorPane centerPane,JEditorPane eastPane,JFrame frame) {
		
		boolean flag = true;
		words = FormatString.getSimilarWords();	//List of words
		List<String> westPaneWords = new ArrayList<>();	
		List<String> centerPaneWords = new ArrayList<>();
		
		//Split word list in two arrays,for left and right pane
		for(int i = 0;i<words.size();i++) {
			if(flag) {
				westPaneWords.add(words.get(i));
				flag = false;
			}else {
				centerPaneWords.add(words.get(i));
				flag = true;
			}
		}
		
		String[] westArray = westPaneWords.toArray( new String[westPaneWords.size()]);
		String[] centerArray = centerPaneWords.toArray( new String[centerPaneWords.size()]);
		String northString = GameSettings.getNorthPaneText();
		String westString = FormatString.formatColumn(westArray).toUpperCase();
		String centerString = FormatString.formatColumn(centerArray).toUpperCase();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Sound.playSound("print");
			}
		}).start();
		
		for (Character c : northString.toCharArray()) {
			northPane.setText(northPane.getText()+String.valueOf(c));
				try {
					Thread.sleep(charPrintDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(1);
				}
		}
		
		int rowIndex = 0;
		int rowIndexLimit = 20;
		boolean timeForLeft = true;
		while(centerString.length()>0) {
			if(timeForLeft) {
				for (Character c : westString.toCharArray()) {
					if(rowIndex<rowIndexLimit) {
						westPane.setText(westPane.getText()+String.valueOf(c));
						rowIndex++;
						try {
							Thread.sleep(charPrintDelay);
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.exit(1);
						}
					}
				}
				westString = westString.substring(rowIndexLimit);
				timeForLeft = false;
				rowIndex=0;
			}
			if(!timeForLeft) {
				for (Character c : centerString.toCharArray()) {
					if(rowIndex<rowIndexLimit) {
						centerPane.setText(centerPane.getText()+String.valueOf(c));
						rowIndex++;
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.exit(1);
						}
					}
				}
				centerString = centerString.substring(rowIndexLimit);
				timeForLeft = true;
				rowIndex=0;
			}
		}
		eastPane.setText(GameSettings.getEastPaneText());
		GameSettings.setScreenClickable(true);

		blinkingCursor(eastPane);
		
	}

	public static void initTerminalLockout(JEditorPane northPane,JEditorPane westPane,JEditorPane centerPane,JEditorPane eastPane) throws InterruptedException {
		StringBuilder north = new StringBuilder(GameSettings.getNorthPaneText());
		StringBuilder west = new StringBuilder(westPane.getText());
		StringBuilder center = new StringBuilder(centerPane.getText());
		StringBuilder east = new StringBuilder(eastPane.getText());
		north.delete(0,41);
		northPane.setText(north.toString());
		Thread.sleep(rowRemoveDelay);
		north.delete(0,18);
		northPane.setText(north.toString());
		Thread.sleep(rowRemoveDelay);
		north.delete(0,23);
		northPane.setText(north.toString());
		Thread.sleep(rowRemoveDelay);
		for(int i =0;i<16;i++) {
			west.delete(0, 20);
			westPane.setText(west.toString());
			
			center.delete(0, 20);
			centerPane.setText(center.toString());
			
			east.delete(0, 19);
			eastPane.setText(east.toString());
			Thread.sleep(rowRemoveDelay);
		}
	}
	public static void resetGame() {
		GameSettings.setScreenClickable(false);
		GameSettings.setGameOver(false);
		GameSettings.setCounter(0);
		GameSettings.setEastPaneText("");
		GameSettings.initEastPaneText();
	}
	public static void removeDud(JEditorPane westPane,JEditorPane centerPane) {
		boolean found = false;
		int i = 0;
		List<String> availableWords = words;
		String wordToRemove = "";
		while(!found) {
			wordToRemove = availableWords.get(i);
			if(!wordToRemove.equals(GameSettings.getTarget())) {
				found = true;
				availableWords.remove(i);
			}else {
				i++;
				continue;
			}
		}
		String dot = "";
		for(int x = 0;x<wordToRemove.length();x++)
			dot+=".";
		int westIndex = westPane.getText().indexOf(wordToRemove);
		int centerIndex = centerPane.getText().indexOf(wordToRemove);
		
		if(westIndex>=0) {
			StringBuilder text = new StringBuilder(westPane.getText());
			text.delete(westIndex, westIndex+wordToRemove.length());
			text.insert(westIndex, dot);
			westPane.setText(text.toString());
		}else {
			StringBuilder text = new StringBuilder(centerPane.getText());
			text.delete(centerIndex, centerIndex+wordToRemove.length());
			text.insert(centerIndex, dot);
			centerPane.setText(text.toString());
		}
		
	}
	private static boolean isDud(int caretPosition,String content) {
		int start = caretPosition;
		boolean isDud = false;
		boolean foundStart = false;	
		
		Character[] brackets = {'(','{','[','<'};
		
		Character current = content.charAt(caretPosition);
		boolean isBracket = Arrays.asList(brackets).contains(current);
		Character target = ' ';
		
		if(isBracket) {
			switch(current) {
			case '(' : target = ')';  break;
			case '{' : target = '}';  break;
			case '[' : target = ']';  break;
			case '<' : target = '>';  break;
			}
				while(!foundStart) {
					if (caretPosition!=content.length() && !Character.isLetter(content.charAt(caretPosition))){
						if(content.charAt(caretPosition) == target) {
							dStart = start;
							dEnd = caretPosition + 1;
							isDud = true;
							foundStart= true;
						}
						caretPosition++;
					}else {
						isDud = false;
						foundStart= true;
					}
			   }
		}
		return isDud;
	}
	//check if clicked word is of hex pattern
	private static boolean isHex(int caretPosition,String content) {
		boolean foundStart = false;
		boolean foundEnd = false;
		int selectionEnd = 0;	
		if(caretPosition < content.length() && (Character.isLetter(content.charAt(caretPosition)) || Character.isDigit(content.charAt(caretPosition)))) {
			while(!foundStart) {
				if (caretPosition!=-1 && (Character.isLetter(content.charAt(caretPosition)) || Character.isDigit(content.charAt(caretPosition))) && !(content.charAt(caretPosition) == '\n' || content.charAt(caretPosition) == '\r' )){
					caretPosition--;
				}else {
					caretPosition++;
					foundStart = true;
					content = content.substring(caretPosition);
				}
			}
			int length = content.length();
			while(selectionEnd!=length && !foundEnd) {
				if ((Character.isLetter(content.charAt(selectionEnd)) || Character.isDigit(content.charAt(selectionEnd))) && !(content.charAt(selectionEnd) == '\n' || content.charAt(selectionEnd) == '\r' )){
					selectionEnd++;
				}else {
					foundEnd = true;
					content = content.substring(0,selectionEnd);
				}
			}	
		}
		Pattern pattern = Pattern.compile("^(0x|0X)?[a-fA-F0-9]+$");
		Matcher matcher = pattern.matcher(content);
		return matcher.matches();
	}
	private static void blinkingCursor(JEditorPane eastPane) {
		DefaultHighlighter.DefaultHighlightPainter highlightPainter = 
				 new DefaultHighlighter.DefaultHighlightPainter(highlightColor);
		while(!GameSettings.isGameOver()){
			try {
				int index = GameSettings.getEastPaneText().length() - GameSettings.getNumOfEastPaneCharInRow() + MouseUtilities.howeredWord.length();
				eastPane.getHighlighter().addHighlight(index+1, index + 2,highlightPainter);
				Thread.sleep(300);
				eastPane.getHighlighter().removeAllHighlights();
				Thread.sleep(300);
			} catch (BadLocationException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}