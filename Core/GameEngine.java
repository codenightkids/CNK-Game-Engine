package Core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GameEngine
{
	public static KeyListener EngineKeyListener = new EngineKeyListener();
	public static List<Integer> keysPressed = new ArrayList<Integer>();
	public static JFrame mainFrame = new JFrame();
	public static JPanel holder = new JPanel();
	public static Timer KeyEvents = null;
	public static boolean quitGame = false;
	public static Level curLevel = null;
	public static JPanel HUDPANE = new JPanel();
	public static int resX = 0;
	public static int resY = 0;     
	
	//Whenever the KeyListener raises an event, the approriate key pressed will be added to the pressedkeys. 
	//Numpressedkeys will reflect how many keys are currently pressed.
	public static int NUMPRESSEDKEYS = 0;
	public static boolean[] PRESSEDKEYS = new boolean[140];
	
    public static Graphics bufferGraphics; 
    public static Image offscreen; 
    static Dimension dim;
    
    //meant to act as a semaphore for the collision thread (only one collision checking should be done at a time).
    public static boolean CollisionRunning = false;
    
	public static void start()
	{
		/**
		 * PRESSEDKEYS
		 * --This is the array that olds what keys are hit. Each index in the array indicates what key has been pressed on the keyboard
		 */
		for(int x = 0; x < 140; x++)
		{
			GameEngine.PRESSEDKEYS[x] = false;
		}
		
		/**
		 * Init of the level
		 * --We check if the curLevel is there, then we set the resolution, the title, and add the components of the level to the mainFrame
		 */
		if(curLevel == null)
		{
			System.err.println("Error: Init the Level");
			System.exit(10);
		}
		if(resX == 0 && resY == 0)
		{
			System.err.println("You didn't set the resolution\nDefaulting to 800x600");
			resX = 800;
			resY = 600;
		}
		
		mainFrame.setTitle("CNK-Engine Build 2 Version... oh gawd, what version is this?!");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBackground(Color.pink);
		
		/**
		 * A lot of commented-out code from here on out is due to using hardware support 
		 * thinking about keeping old stuff in case we need it (web browser support?)
		 * 
		 **/
		//Position the window to the middle of the screen
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		//Point center  = ge.getCenterPoint();
		GraphicsDevice myDevice = ge.getDefaultScreenDevice();
		DisplayMode newDisplayMode = new DisplayMode(resX, resY, 32, 60);
		//mainFrame.setLocation(center);
		//mainFrame.setUndecorated(true);
		try 
		{
		    myDevice.setFullScreenWindow(mainFrame);
		    myDevice.setDisplayMode(newDisplayMode);
		    
		} 
		catch(Exception e) 
		{
		    System.err.println("There was an error trying to change the display mode, here's the details: \n" +e.toString());
		}
		
		//starting the level
		
		
		//------------------------------------------------------------//
		//--------------------HUD Stuff-------------------------------//
		//place the world onto the Frame (eventually, this should be the intro-video | main menu).
		/*
		 * A mainPane is set up here which is a jpanel that will essentially hold all the other jpanels, 
		 * game and HUD, setting them on top of one another. 
		 */
		
		JPanel mainPane = new JPanel();
		mainPane.setOpaque(false);
		mainPane.setBounds(0, 0, resX, resY);
		mainPane.setLayout(null);
		
		
		mainPane.add(curLevel.getWorld());
		mainPane.add(curLevel.getHUD());
        mainPane.setComponentZOrder(curLevel.getHUD(), 0);
        mainPane.setComponentZOrder(curLevel.getWorld(), 1);

        
		mainFrame.addKeyListener(EngineKeyListener);
        mainFrame.setContentPane(mainPane);
		//------------------------------------------------------------//
		//------------------------------------------------------------//
		

		dim = mainFrame.getSize();

        
		//Make the window viewable
		//mainFrame.createBufferStrategy(2);
		mainFrame.setVisible(true);
        

		//Make the exit button on the window close the program
	    //Game loop/threads
		KeyEvents = new Timer(1, new KeyEvents());
	    Timer RepaintLoop = new Timer(16, new painting()); // This is the repaint loop, it will repaint the JFrame every 1/60th of a second
	    Timer LogicLoop = new Timer(8, new AIloop()); // This is the logic loop, which gives twice the precision for Logic loops (like AI)
		Timer MoveLoop = new Timer(8, new Movement()); // This is the movement loop, which will move everything based on their velocity.
		Timer EventsLoop = new Timer(8, new GameEvents());

	    //Starting the threads/loops
		MoveLoop.start();
	    RepaintLoop.start();
	    LogicLoop.start();
	    EventsLoop.start();
	    
	    //Start the first level

	    
	    //checking the status of the game
	    while(!quitGame) //This checks to see if the game wants to quit every second. Figured thats more efficient than a constant while loop
	    {
	    	try
	    	{
	    		Thread.sleep(100); 
	    	}
	    	catch(Exception e)
	    	{
	    		System.err.println("Java screwed the pooch.");
	    	}
	    }
        mainFrame.setVisible(false);
	    System.out.println("Oh Wow, thank you.\n" +
	    		"From us here at CNK, go fuck yourself.");
        System.exit(0);

	}

	public static void setResolution(int resX, int resY)
	{
		GameEngine.resX = resX;
		GameEngine.resY = resY;
	}
	
	public static void setLevel(Level curLevel)
	{
		GameEngine.curLevel = curLevel;
	}
	
	public String getResolution()
	{
		return "" + GameEngine.resX + " x " +GameEngine.resY;
	}
	
	public static Level getCurLevel()
	{
		return GameEngine.curLevel;
	}
	
	//allows other classes to end the game
	public static void endGame()
	{
		GameEngine.quitGame = true;
	}
	
	/**
	 * This method is pretty confusing, let me try to explain it.
	 * It's premise is to decide whether or not the object being sent in will hit another object upon its given path. If it does,
	 * then it needs to stop before it gets there.
	 * 
	 * First, we check it's future horizontal displacement, then it's vertical. If both cases are true, then our passed in object is going to hit the other object.
	 * The math for this will need to be explained in person... it works tho.
	 * --Note, I switched the checkBounds method into two methods (Horizontal, and Vertical). I hope to separate this out further so we can detect where objects are being hit.
	 * 
	 *
	 * @param obj1 = the game object being sent in that will be tested
	 * @param obj2 = the game object that may be in the way
	 * @return
	 */
	public static boolean checkHorizontalBounds(GameObject obj1, GameObject obj2)
	{
		boolean HoriztonalCheck = true;	
		
		int obj1RightBound = obj1.positionX + obj1.sizeX;
		int obj1LeftBound = obj1.positionX;
		
		int obj1NextPositionX = obj1.positionX + obj1.getRun();
		int obj1NextRightBound = obj1.sizeX + obj1NextPositionX;
		int obj1NextLeftBound = obj1NextPositionX;

		int obj2RightBound = obj2.positionX + obj2.sizeX;
		int obj2LeftBound = obj2.positionX;

		//if obj1 is moving, check it's next move
		if(Math.abs(obj1.getRun())>0)
		{
			//If the next move collides with any object, or the previous location will collide with an object, then don't allow it to move.
			if((obj1NextRightBound >= obj2LeftBound && obj1NextLeftBound <= obj2RightBound) || (obj1RightBound >= obj2LeftBound && obj1LeftBound <= obj2RightBound))
			{
				//System.out.println("Obj1's RightBound should be trying to be more than Obj2's left bound");
				HoriztonalCheck = false;
			}
			if((obj1NextLeftBound <= obj2RightBound && obj1NextRightBound >= obj2LeftBound) || (obj1LeftBound <= obj2RightBound && obj1RightBound >= obj2LeftBound))
			{
				//System.out.println("obj1's LeftBound should be trying to be less than Obj2's right bound");
				HoriztonalCheck = false;
			}
		}
		//he's not moving, so that means it's moving vertically. Check to make sure it hasnt already collided beforehand.
		else
		{
			if(obj1RightBound >= obj2LeftBound && obj1LeftBound <= obj2RightBound)
			{
				//System.out.println("Obj1 is currently colliding horizontally with obj2");
				HoriztonalCheck = false;
			}
			if(obj1LeftBound <= obj2RightBound && obj1RightBound >= obj2LeftBound)
			{
				//System.out.println("Obj1 is currently colliding horizontally with obj2");
				HoriztonalCheck = false;
			}
		}
		return HoriztonalCheck;
		
	
	}
	
	public static boolean checkVerticalBounds(GameObject obj1, GameObject obj2)
	{
		boolean VerticalCheck = true;
		
		int obj1TopBound = obj1.positionY;
		int obj1BotBound = obj1.positionY + obj1.sizeY;
		
		int obj1NextPositionY= obj1.positionY + obj1.getRise();
		int obj1NextTopBound = obj1NextPositionY;
		int obj1NextBotBound = obj1NextPositionY + obj1.sizeY;
		
		int obj2TopBound = obj2.positionY;
		int obj2BotBound = obj2.positionY + obj2.sizeY;

		//if obj1 is trying to move up, check it's next move
		if(Math.abs(obj1.getRise()) > 0)
		{
			//If the next move collides with any object, or the previous location will collide with an object, then don't allow it to move.
			if((obj1NextTopBound <= obj2BotBound && obj1NextBotBound >= obj2TopBound) || (obj1TopBound <= obj2BotBound && obj1BotBound > obj2TopBound))
			{
				//System.out.println("Obj1's TopBound should be trying to be less than obj2's Botbound");
				VerticalCheck = false;
			}
			if((obj1NextBotBound >= obj2TopBound && obj1NextTopBound <= obj2BotBound) || (obj1BotBound >= obj2TopBound && obj1TopBound < obj2BotBound))
			{
				//System.out.println("Obj1's BotBound should be trying to be more than obj2's Topbound");
				VerticalCheck = false;
			}
		}
		//if obj1 is not trying to move up, then it's trying to move horizontally, so make sure it's not already horizontally colliding.
		else
		{
			if(obj1TopBound <= obj2BotBound && obj1BotBound > obj2TopBound)
			{
				//System.out.println("Obj1's is currently colliding vertically");
				VerticalCheck = false;
			}
			if(obj1BotBound >= obj2TopBound && obj1TopBound < obj2BotBound)
			{
				//System.out.println("Obj1's is currently colliding vertically");
				VerticalCheck = false;
			}
		}
		
		return VerticalCheck;
					
	}
	
}


//-------------These are the "threads" to be made-------------------//
/**
 * This the AILoop thread, which is just the loop that goes through the AI scripts for each AI.
 * Pretty simple, you just go to the curLevel, get the AI that are there, and for each AI, process it's AI script.
 */
class AIloop implements ActionListener //The AI controller "task"
{
	public void actionPerformed(ActionEvent e) {
	    
		for(int i = 0; i < GameEngine.curLevel.getAI().size(); i++)
		{
			GameEngine.curLevel.getAI().get(i).doMove();
		}
	  }
}

/**
 * This the paint-loop, or the "FPS" controller. This will repaint the entire screen every so often, creating the illusion of movement.
 * --Should eventually be optimized, where the only things getting repainted are objects that need to be... (ie. moving, or has effects, or exists).
 */
class painting implements ActionListener //The Painting controller "task"
{
	public void actionPerformed(ActionEvent e) 
	  {

		  GameEngine.mainFrame.repaint();

	  }
}

/**
 * This class is the movement thread. It runs all the time, but it's not that demanding, unless something is moving...
 * 
 * UPDATED IN BUILD 2 -- look at build 1 for old, crappy code
 *
 * The way this works now, is to see if an object is capable of moving (curObj). It does this by going through all the objects on the screen, and seeing if it has horizontal,
 * and vertical collision with those objects. If it does, then the objects the curObj is hitting should be added to the collidedObjects array.
 * 
 * After all that, we then go through each collided object, to see if we are hitting the objects from the left, right, top, or bottom. Depending on these cases allows the curObj to move in
 * it's un-blocked direction. Example: If your space ship hits an asteroid from the left, it should still be able to move up and down. If it's it from the top, then it should be able to move 
 * left or right.
 * 
 * There is a bug you may notice, which needs to be fixed. My guess is that it's a thread/resource conflict with another thread, causing the moving object to move inside 
 * of it's colliding object/s. If you can recreate this bug, please try to fix it, or note it in an email to everyone. It doesn't show up often, and I actually had to create multiple AI's
 * to pull it out enough to exam.
 */
class Movement implements ActionListener
{
	public void moveOrCollide()
	{
		//if there is no collision thread already running, run the code! -- else...
		if(!GameEngine.CollisionRunning)
		{
			GameEngine.CollisionRunning = true;
			
			GameObject curObj = null; 
			GameObject checkObj = null;
			GameObject innerCheck = null;
			ArrayList<GameObject> collidedObjects = new ArrayList<GameObject>();
			boolean noHorizontalCollision = false;
			boolean noVerticalCollision = false;
			boolean check = false;
			boolean canRise = false;
			boolean canRun = false;
			int curLeftBound = 0;
			int curRightBound = 0;
			int curTopBound = 0;
			int curBotBound = 0;
			int otherLeftBound = 0;
			int otherRightBound = 0;
			int otherTopBound = 0;
			int otherBotBound = 0;
			ArrayList<GameObject> existingObjects = new ArrayList<GameObject>();
			//int leftcoll = 0;
			
			//Grab all the objects that are currently existing (that is, objects that are above 0 in existence).
			existingObjects = GameEngine.getCurLevel().getExistingObjects();
			
			//This for loop will handle movement of all objects
			for(int x = 0; x < existingObjects.size(); x++)
			{
				collidedObjects.clear();

				noHorizontalCollision = true;
				noVerticalCollision = true;
				canRise = true;
				canRun = true;
				curObj = existingObjects.get(x);
				
				//if the object is moving, check its collision
				if(Math.abs(curObj.getRise()) > 0 || Math.abs(curObj.getRun()) > 0)
				{
						for(int j = 0; j < existingObjects.size(); j++)
						{
							checkObj =  existingObjects.get(j);
							
							//if the object we are checking against isn't the exact same one...
							if(!curObj.equals(checkObj))
							{
								//Get the collision for both horizontal and vertical directions
								noHorizontalCollision = GameEngine.checkHorizontalBounds(curObj, checkObj);
								noVerticalCollision = GameEngine.checkVerticalBounds(curObj, checkObj);;
								
								//check to see if there was collision for horizontal and vertical detection
								check = ((!noHorizontalCollision) && (!noVerticalCollision));
								if(check)
								{
										//add the collided object to the list of all collided objects with the curObj
										collidedObjects.add(checkObj);
								}
							}
						}
					
					/*Go through all the objects that curObj is colliding with, and determine whether or not it can rise, or run*/
					//if curObj collided with anything...
					if(collidedObjects.size()>0)
					{
									for(int z =0; z < collidedObjects.size(); z++)
									{
										//grab one of the objects that curObj hit
										innerCheck = collidedObjects.get(z);
		
										//setup some useful variables.
										curLeftBound = curObj.getPositionX();
										curRightBound = curObj.getPositionX() + curObj.getSizeX();
										curTopBound =  curObj.getPositionY();
										curBotBound = curObj.getPositionY() + curObj.getSizeY();
										
										otherLeftBound = innerCheck.getPositionX();
										otherRightBound = innerCheck.getPositionX() + innerCheck.getSizeX();
										otherTopBound = innerCheck.getPositionY();
										otherBotBound = innerCheck.getPositionY() + innerCheck.getSizeY();
										
										//IMPORTANT ACTION -- alert the collided objects that they were hit by the curObj
										innerCheck.collide(curObj);
										//IMPORTANT ACTION -- alert the curObk that it hit innerCheck 
										//WAS BUGGING OUT, DISABLED IT!
										//curObj.collide(innerCheck); 
										
										/*check how the objs were collided into. This allows for moving along objects even if you are still pushing into them*/
										/**NOTE: THIS IS PROBABLY A DUMB THING, I NEED TO MAKE THIS MORE EFICENT CAUSE IT'S REUSING CODE!!! HERP!!!**/
										//collided with an obj from the left
										if(otherLeftBound >= curRightBound && otherRightBound >= curLeftBound && curObj.getRun() > 0)
										{
											
											canRun=false;
											
										}
										//collided with an obj from the right
										else if(otherRightBound <= curLeftBound && otherLeftBound <= curRightBound && curObj.getRun() < 0)
										{
				
											canRun=false;
						
										}
										//collided with an obj from the top
										if(otherTopBound >= curBotBound && otherBotBound >= curTopBound && curObj.getRise() > 0)
										{
				
											canRise=false;
											
										}
										//collided with an obj from the bottom
										else if(otherBotBound <= curTopBound && otherTopBound <= curBotBound && curObj.getRise() < 0)
										{
											canRise=false;
										}	
									}
									/*if the current object canRise or canRun, let it.*/
									if(canRise || canRun)
									{				
											if(canRise)
											{
												
												curObj.setPositionY(curObj.getPositionY() + curObj.getRise());
											}
								
											if(canRun)
											{

												curObj.setPositionX(curObj.getPositionX() + curObj.getRun());
											}
											
									}
					}					
					else
					{
							curObj.setPositionY(curObj.getPositionY() + curObj.getRise());
							curObj.setPositionX(curObj.getPositionX() + curObj.getRun());
					}
				}
			}	
			GameEngine.CollisionRunning = false;
		}
		//...wait till collisionrunning is over.
		else
		{

				System.err.println("collision thread already running");
			
		}
			
	
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		moveOrCollide();
		
	}
} 



//Here is where KeyEvents are handled. Right now, only directions are being handled. Would like to make this modular too.
//I know this seems odd, and it is. Pressing keys should cause an event, instead of using a loop to go through a stack of pressed keys. I hope to make this more intuitive. 
//IF YOU WANT TO TAKE A SHOT AT IMPROVING THIS, GO AHEAD! Just email us first :3
class KeyEvents implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
		for(int j = 0; j < GameEngine.curLevel.getAllObjects().size(); j++)
		{
			GameObject curObject = GameEngine.curLevel.getAllObjects().get(j);
			curObject.KeyPressed();
		}
		
		//stops the thread when no keys are being pushed (why not?)
		if(GameEngine.NUMPRESSEDKEYS == 0)
		{
			GameEngine.KeyEvents.stop();
		}
		
	}
	
}
class GameEvents implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		GameEngine.getCurLevel().getEvents().CheckEvents(GameEngine.getCurLevel());	
	}
	
}
//-----!!-----------End of the thread stuff-----------------------//