package Testers;
import java.util.Random;

import Core.AIChar;
import Core.EnvObject;
import Core.GameEngine;
import Core.Level;
import Core.PlayerChar;
import Events.MyEvents;
import HUD.MyHUD;
import PlayerCommands.*;
import AICommands.*;


public class Tester 
{
	/**
	 * This the tester class, where we are making a "game" to test the engine with. It holds no actual purpose, other than to demonstrate, and test the engine.
	 * 
	 * -Explanation of the tester file thus far:
	 * First, we create a GameEngine object, which will be running everything (it's the engine, it moves things...).
	 * Second, we create the level, which we named "level1" (later on, you should be able to make many more levels).
	 * Thirdly, we set the resolution for the screen. I suggest changing this for your screen... mine is bit "big".
	 * Next, the back ground is set, which is just an image file (we are using .png's for this).
	 * After that, we create and add walls, players, and ai characters. It's pretty self explanatory after this point, except for...
	 * 
	 * ...The Ai uses a script, which like this class, is just for demonstration purposes.
	 * --The File "ExampleAI.java" is modifiable, in the same aspect that this class is. Go ahead and load it up, and play with it.
	 * 
	 * If you have any questions, feel free to ask!
	 */
	/**
	 * @param args
	 */
	public static int score = 50;
	
	public static void main(String[] args) 
	{
		int resX = 1024;
		int resY = 768;
		GameEngine.setResolution(resX, resY);
		
		Random randomGenerator = new Random();
		Level level = new Level("Level1");
		level.setBackground("images\\level1.png");
		level.setHUD(new MyHUD());
		level.setEvents(new MyEvents());
		level.setSound("wheresmorn.aiff", true);
		level.getSound().playAudio();

		
		//creating the walls
		EnvObject wall1 =new EnvObject(resX, 1, 0,0,       2,"wall", "top wall", 1, 20);
		EnvObject wall2 =new EnvObject(1, resY, 0,0,       2,"wall", "top wall", 1, 20);
		EnvObject wall3 =new EnvObject(resX, 1, 0,resY,    2,"wall", "top wall", 1, 20);
		EnvObject wall4 =new EnvObject(1, resY, resX,0,    2,"wall", "top wall", 1, 20);
		
		/**
		 * Creating player 1 (ai follows him it seems)
		 * -ExPlayerCommands is PlayerCommands class, where you set the actions for the player, his collisions, and interaction(not sure if we need that).
		 **/
		PlayerCommands ExPlayerCommands = new ExPlayerCommands();
		PlayerChar player = new PlayerChar(33,33, resX - 50, resY - 50, 100, "images\\littleHero.png", "Hero", 1, 40, ExPlayerCommands);
		

		/**Creating the AI's
		 * The Ai here is created in a for loop, with the same ai-script applied to each.
		 * Tried to give them some "random" placement, since I wanted to increase, and lower the number of AI's as we progressed with the engine.
		 * Note: we add the AI here, instead of with the other "level.add()" bits below. Simplicity was the reason why...
		 **/
		AICommands aiScript = new ExampleAICommands();
	    int placeRandomAix = 0;
	    int placeRandomAiy = 0;
	    randomGenerator = new Random();
		AIChar temp = null;
		String name = null;
		for(int j = 0; j < 50; j++)
		{
			placeRandomAix = randomGenerator.nextInt(resX-100);
			placeRandomAiy = randomGenerator.nextInt(resY-100);
			name = "" + j;
			temp = new AIChar(25, 25, placeRandomAix, placeRandomAiy, 100, "images\\littleEnemy.png", name, 1, 5, player);
			temp.setAI(aiScript);
			temp.setSound("asanmp3.wav", false);
			level.addAIChar(temp);
		}
		
		/**
		 * Adding the level elements to the level. 
		 **/
		//Adding the walls here
		level.addEnvObject(wall1);
		level.addEnvObject(wall2);
		level.addEnvObject(wall3);
		level.addEnvObject(wall4);
		//adding the player here
		level.addPlayer(player);

		/**
		 * Here we set the Level, and then start the game.
		 */
		GameEngine.setLevel(level);
		GameEngine.start();
		
	}
}
