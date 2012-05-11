package Core;
import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.*;
/*
 * Hey everyone, this is the basic audio operations class, 
 * You have the options of setting the audio file to play 
 * (currently only accepts AU, AIFF, and WAV), 
 * you can play pause and stop audio
 */
public class Sound 
{
	//Setup variables required to manipulate the Sound
	ArrayList<Clip> clips = new ArrayList<Clip>();
	AudioInputStream input = null;
	ArrayList<String> fileNames;
	ArrayList<Boolean> loops = new ArrayList<Boolean>();
	FloatControl gain = null;
	private Clip audio = null;
	
	//Constructor for a Sound object 
	public Sound(ArrayList<String> fileNames, ArrayList<Boolean> loop)
	{
		this.fileNames = fileNames;
		for(int i = 0; i < fileNames.size(); i++)
		{
			addSound(fileNames.get(i), loop.get(i));
		}
		
	}
	
	public Sound(String fileName, boolean loop)
	{
		addSound(fileName, loop);
		
	}
	
	public void addSound(String fileName, boolean loop)
	{
		try
		{
			input = AudioSystem.getAudioInputStream(new File(fileName));
			audio = AudioSystem.getClip();
			audio.open(input);
			clips.add(audio);
			loops.add(loop);
		}
		catch(IOException e)
		{
			System.out.println("Audio File Not Found");
		} 
		catch (UnsupportedAudioFileException e) 
		{
			System.out.println("Audio File Type Not Supported, brah");
		} catch (LineUnavailableException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Serious audio problem Line Unavailable");
		} 
		
	}
	
	public void setVolume(float volume)
	{
		gain = (FloatControl) audio.getControl(FloatControl.Type.MASTER_GAIN);
		gain.setValue(volume);
	}
	
	public void playAudio()
	{
		
			if(audio != null)
			{
				audio.stop();
				audio.setFramePosition(0);
				audio.start();
				if(loops.get(0) == true)
				{
					audio.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
	}
	
	public void pauseAudio()
	{
		audio.stop();
	}
	
	public void stopAudio()
	{
		pauseAudio();
		audio.setFramePosition(0);
	}

}
