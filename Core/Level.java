package Core;
//import java.io.File;

import Events.*;
import java.util.ArrayList;
import HUD.HUD;




public class Level
{

	private World wrld;
	private HUD hud = new HUD();
	private Events levelEvents;
	private ArrayList <EnvObject> gObjects;
	private ArrayList <PlayerChar> pObjects;
	private ArrayList <AIChar> aiObjects;
	private ArrayList <GameObject> allObjects;
	private ArrayList <GameObject> existingObjects;
	private boolean semaColl=false;
	private String levelID;
	private Sound sound;
	
	
	/**
	 * Changes to Level
	 * -Done by Seth, Dan, Kenny
	 * 
	 * We removed the makeLevel, and startLevel.
	 * 
	 * Instead, the setBackground changes the image of the World, instead of the BackGround string in Level (which was also removed).
	 * 
	 * And the world is now constructed in the Default Constructor.
	 */
	public Level(String levelID)//Constructor
	{
		this.gObjects =new ArrayList<EnvObject>();
		this.pObjects =new ArrayList<PlayerChar>();
		this.aiObjects =new ArrayList<AIChar>();
		this.allObjects =new ArrayList<GameObject>();
		this.existingObjects = new ArrayList<GameObject>();
		this.levelID=levelID;
		this.wrld=new World(pObjects, aiObjects, gObjects);
	}
	
	
	public void setBackground(String fileName)
	{
		this.wrld.setBackgroundImage(fileName);
	}

	
	public void setHUD(HUD yourHUD)
	{
		this.hud = yourHUD;
		this.hud.setBounds(0,0, GameEngine.resX, GameEngine.resY);
		this.hud.setOpaque(false);
		this.hud.setLayout(null);
	}
	
	public void setEvents(Events yourEvents)
	{
		this.levelEvents = yourEvents;
	}
	
	public Events getEvents()
	{
		return levelEvents;
	}
	
	public HUD getHUD()
	{
		return this.hud;
	}

	
	public boolean getSema()
	{
		return this.semaColl;
	}
	
	public void setSema()
	{
		this.semaColl=!this.semaColl;
	}
	
	public void addPlayer(PlayerChar newPlayer)
	{
		this.pObjects.add(newPlayer);
		this.allObjects.add(newPlayer);
		if(newPlayer.getExists() > 0)
		{
			this.existingObjects.add(newPlayer);
		}
			
	}
	
	public void addAIChar(AIChar newAI)
	{
		this.aiObjects.add(newAI);
		this.allObjects.add(newAI);
		if(newAI.getExists() > 0)
		{
			this.existingObjects.add(newAI);
		}
	}
	
	public void addEnvObject(EnvObject newEnv)
	{
		this.gObjects.add(newEnv);
		this.allObjects.add(newEnv);
		if(newEnv.getExists() > 0)
		{
			this.existingObjects.add(newEnv);
		}
	}
	
	public ArrayList<EnvObject> getObjects()
	{
		return this.gObjects;
	}
	
	public ArrayList<PlayerChar> getPlayers()
	{
		return this.pObjects;
	}
	
	public ArrayList<AIChar> getAI()
	{
		return this.aiObjects;
	}
	
	public ArrayList<GameObject> getAllObjects()
	{
		return this.allObjects;
	}
	public ArrayList<GameObject> getExistingObjects()
	{
		return this.existingObjects;
	}
	public World getWorld()
	{
		return this.wrld;
	}
	
	public String getLevelID()
	{
		return this.levelID;
	}
	
	public Sound getSound()
	{
		return this.sound;
	}
	
	public void setSound(String filename, boolean loop)
	{
		this.sound = new Sound(filename, loop);
	}
	
	public void setSound(ArrayList<String> filenames, ArrayList<Boolean> loops)
	{
		this.sound = new Sound(filenames, loops);
	}
	
	//Game Object populator no longer used
	/*
	private void getGObjects(Scanner sc)
	{
		String input, name, img;
		int x, y, health, exists;
		Scanner in;
		
		PlayerChar newChar;
		AIChar newAI;
		EnvObject newGObject;
		this.cObjects= new ArrayList<PlayerChar>();
		this.aiObjects= new ArrayList<AIChar>();
		this.gObjects= new ArrayList<EnvObject>();
		while(sc.hasNext())
		{
			input=sc.nextLine();
			in= new Scanner(input);
			
			if(input.contains("map"));
			if(input.startsWith("character"))
			{
				x=in.nextInt();
				y=in.nextInt();
				health=in.nextInt();
				exists=in.nextInt();
				img= in.next();
				name=in.next();
				newChar= new PlayerChar(x, y, health, name, img, exists);
				this.cObjects.add(newChar);
			}
			if(input.startsWith("ai"))
			{
				x=in.nextInt();
				y=in.nextInt();
				health=in.nextInt();
				exists=in.nextInt();
				img= in.next();
				name=in.next();
				newAI= new AIChar(x, y, health, name, img, exists);
				this.aiObjects.add(newAI);
			}
			else
			{
				x=in.nextInt();
				y=in.nextInt();
				health=in.nextInt();
				exists=in.nextInt();
				img= in.next();private
				name=in.next();
				newGObject= new EnvObject(x, y, health, name, img, exists);
				this.gObjects.add(newGObject);
			}
			
		}
		
	}*/

}