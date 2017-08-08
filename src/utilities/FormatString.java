package utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import main.GameSettings;

public class FormatString {
	
	//get formatted String for west and center pane
	public static String formatColumn(String[] words) {
		//25 special characters
		final char[] characters = {'.',',','>','<','?','/',';',':','[',']',
				 '}','{','(',')','!','@','#','$','%','^',
				 '&','*','+','-','|'};
		final int numberOfSpacesInRow = 12;
		StringBuilder finalString = new StringBuilder();
		StringBuilder row;
		int numberOfLettersInWord = words[0].length();
		int numberOfBlankSpaces = numberOfSpacesInRow - numberOfLettersInWord;
		//numbers of lines where words go
		int[] lineNumbers = getRandomNumbers(words.length);
		//line of format "0x9430 .![)$^+|#/=?\n"
		
		int nextWord = 0;
		for(int x = 0;x < 16;x++) {
			row = new StringBuilder();
			int currentValueOfX = x;
			boolean contains = IntStream.of(lineNumbers).anyMatch(y -> y == currentValueOfX);
			int randomNum = ThreadLocalRandom.current().nextInt(0, numberOfSpacesInRow-numberOfLettersInWord + 1);
			
			if(contains) {
				for(int i =0;i<numberOfBlankSpaces;i++)
					row.append(" ");
				row.insert(randomNum, words[nextWord]);
				nextWord++;
			}else {
				for(int i =0;i<numberOfSpacesInRow;i++)
					row.append(" ");
			}
			String rowS = row.toString();
				//replace empty spaces with characters
				for(int i = 0 ; i < row.length() ; i++) {
					int random = ThreadLocalRandom.current().nextInt(0, 24 + 1);
					if(!(random == 17)) {
						rowS = rowS.replaceFirst(" ", String.valueOf(characters[random]));
					}else {
						//if character is '$',escape it
						rowS = rowS.replaceFirst(" ", "\\"+String.valueOf(characters[random]));
					}
				}	
				finalString.append(formatRandomHex()).append(" ").append(rowS).append("\n");
		}
		return finalString.toString();
	}

	//get words form file
	public static List<String> getSimilarWords(){
		List<String> words = new ArrayList<>();
		int numberOfWords = 10;
		String target = getWord(); 
		GameSettings.setTarget(target);
		words.add(target);
		int counter = 0;
		while(words.size() < numberOfWords) {
			String temp = getWord();
				if(words.contains(temp))
					continue;
			int commonLetters = Utility.compareTwoStrings(target, temp);
			if(commonLetters>=1) {
				words.add(temp);
				counter = 0;
			}else if(counter >= 100){
				words.add(temp);
				counter = 0;
			}else {
				counter++;
			}
		}
		return words;
	}
	private static String getWord() {
		int numberOfWords = 10;
		String filePath;
		int max = 550;
		int min = 0;
		switch(GameSettings.getDificulty()) {
			case 0: filePath = "/f5words.txt"; break;
			case 1: filePath = "/f6words.txt"; break;
			case 2: filePath = "/f7words.txt"; break;
			case 3: filePath = "/f8words.txt"; break;
				default: filePath = "/f5words.txt"; break;
		}
		InputStream in = Utility.class.getResourceAsStream(filePath); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		Stream<String> lines = reader.lines();
		int randomNum = ThreadLocalRandom.current().nextInt(min, max/numberOfWords + 1);
		Optional<String> result = lines.skip(randomNum).findFirst();
		String word = result.get();
		return word;
	}
	//get random hex number as String
	private static String formatRandomHex() {
		final char[] characters = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		String hex = "0x94";
			for(int i = 0;i<2;i++) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, 16);
				hex += characters[randomNum];
			}	
		return hex;
	}
	//get random numbers from 1-16,for inserting words at those lines
	private static int[] getRandomNumbers(int numberOfWords) {
		int[] nums = new int[numberOfWords];	
		for(int i = 0;i < numberOfWords;i++) {
			int random = ThreadLocalRandom.current().nextInt(0, 16);	
			boolean contains = IntStream.of(nums).anyMatch(x -> x == random);	
			if(!contains) {
				nums[i] = random;
			}else {
				i--;
			}
		}
		Arrays.sort(nums);
		return nums;
	}
}