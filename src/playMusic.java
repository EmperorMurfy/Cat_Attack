// Class: playMusic
// Written by: Cat Attack Developers
// Last Updated: May 19, 2024
// Description: Mainly intended for background music purposes, plays on loop 

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class playMusic {
	
	// in order to use this class, you must make a - private static playMusic player;
	// then, you should initiate it in a desired location. 
	// in my case, you should also insert filepath 
	// player = new playMusic("src/sounds/loop.wav"); 
	
	private String filePath;
	private Clip clip;

// no default constructor 
	public playMusic(String filePath) {
		this.filePath = filePath;	
	}

	// access run method to loop continuously
	public void run() {
		try {
			File url = new File(filePath);
			clip = AudioSystem.getClip();
			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY); // LOOP_CONTINUOUSLY until program terminates
			clip.start();

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
		// System.out.println("Music Playing");
	}
	
	// pauses music until run() is accessed 
	public void close() { 
		clip.close();
		// System.out.println("Music Stopped"); 
	}
}
