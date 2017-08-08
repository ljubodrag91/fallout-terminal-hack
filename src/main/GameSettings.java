package main;

import java.awt.Color;

/*	difficulties
 * 0 -> 5 letters,	novice
 * 1 -> 6 letters,	advanced
 * 2 -> 7 letters,	expert	
 * 3 -> 8 letters,	master
 */

public class GameSettings {
	private static final Color fontColor = new Color(58,148,63);
	private static int attempts = 4;	
	private static int counter = 0;
	private static String target = "";
	private static int dificulty = 1;  
	private static boolean isScreenClickable= false;
	private static boolean isGameOver = false;
	private static String eastPaneText = "";
	private final static int numOfEastPaneCharInRow = 19;
	
	static {
		initEastPaneText();
	}
	public static void initEastPaneText() {
		for(int i = 0;i< numOfEastPaneCharInRow * 16 /*16 rows*/ ;i++) {
			if(i!=285)
				setEastPaneText(getEastPaneText() + " ");
			else
				setEastPaneText(getEastPaneText() + ">");
		}
	}
	
	public static Color getFontColor() {
		return fontColor;
	}
	public static int getAttempts() {
		return attempts;
	}
	public static void setAttempts(int attempts) {
		GameSettings.attempts = attempts;
	}
	public static int getCounter() {
		return counter;
	}
	public static void setCounter(int counter) {
		GameSettings.counter = counter;
	}
	public static String getTarget() {
		return target;
	}
	public static void setTarget(String target) {
		GameSettings.target = target;
	}
	public static int getDificulty() {
		return dificulty;
	}
	public static void setDificulty(int dificulty) {
		GameSettings.dificulty = dificulty;
	}
	public static boolean isGameOver() {
		return isGameOver;
	}
	public static void setGameOver(boolean isGameOver) {
		GameSettings.isGameOver = isGameOver;
	}
	public static String getEastPaneText() {
		return eastPaneText;
	}
	public static void setEastPaneText(String eastPaneText) {
		GameSettings.eastPaneText = eastPaneText;
	}
	public static int getNumOfEastPaneCharInRow() {
		return numOfEastPaneCharInRow;
	}
	public static String getNorthPaneText() {
		return "Welcome to ROBCO Industries (TM) Termlink\nPassword Required\nAttempts Remaining " +
				(GameSettings.getAttempts() - GameSettings.getCounter())+ "/" + GameSettings.getAttempts() +"";
	}
	public static boolean isScreenClickable() {
		return isScreenClickable;
	}
	public static void setScreenClickable(boolean isScreenClickable) {
		GameSettings.isScreenClickable = isScreenClickable;
	}	
}