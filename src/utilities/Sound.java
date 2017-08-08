package utilities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import gui.GamePanel;

public class Sound {
    private static final int BUFFER_SIZE = 128000;
    private static AudioInputStream audioStream;
    private static AudioFormat audioFormat;
    private static SourceDataLine sourceLine;
    private static InputStream is;
    private static InputStream bufferedIn;

    /**
     * @param filename the name of the file that is going to be played
     */
    public static synchronized void playSound(String filename){
    	
    	switch(filename) {
    		case "correct": is = GamePanel.class.getClassLoader().getResourceAsStream("correctword.wav"); break;
    		case "incorrect": is = GamePanel.class.getClassLoader().getResourceAsStream("incorrectword.wav"); break;
    		case "oversymbol":is = GamePanel.class.getClassLoader().getResourceAsStream("oversymbol0.wav"); break;
    		case "overword": is = GamePanel.class.getClassLoader().getResourceAsStream("overword.wav"); break;
    		case "print": is = GamePanel.class.getClassLoader().getResourceAsStream("print.wav"); break;
    		default: is = GamePanel.class.getClassLoader().getResourceAsStream("oversymbol0.wav");
    	}
    	 
    	bufferedIn = new BufferedInputStream(is);
        
        try {
            audioStream = AudioSystem.getAudioInputStream(bufferedIn);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }
}
