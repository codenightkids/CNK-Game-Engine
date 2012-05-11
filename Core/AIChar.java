package Core;
import AICommands.AICommands;

//import java.util.ArrayList;


public class AIChar extends Character
{
	//private ArrayList<Commands> AICommands = null;
	private PlayerChar target;
	//private int myCommand;
	private AICommands com = null;
	public AIChar(int sizeX, int sizeY, int x, int y, int health, String fileName, String characterName,int exist, int speed, PlayerChar t) 
	{
		super(sizeX, sizeY, x, y, health, fileName, characterName, exist, speed);
		this.target = t;
	}
	
	/**
	 *Do we still need this or the variables I commented out up top.
	 * 
	 * 
	  public void setCommand(String c)
	{
		if(c.toUpperCase().equals("CHASE"))// Chase = 1;
			myCommand = 1;
		else if(c.toUpperCase().equals("PATROL"))//
			myCommand = 2;
		else //No command assigned = -1
			myCommand = -1;
	}**/
	
	public void collide(GameObject objHit)
	{

			//System.out.println(objHit.getName() + " Hit Me! (my name is " + this.getName() +" and Im an AI)");
			this.com.onCollision(this, objHit);
	} 
	
	public void doMove()
	{
		if(com != null)
		{
			com.doMoves(this);
		}
		else
			System.out.println("Did not assign AI.");
	}
	
	public void setAI(AICommands obj)
	{
		this.com = obj;
	}
	/*
	public void shoot()
	{
		int rise, run, AIX, AIY, PX, PY;
		AIX = this.getPositionX();
		AIY = this.getPositionY();
		PX = target.getPositionX();
		PY = target.getPositionY();
		
		if(AIX < PX && AIY > PY)
		{
			rise = AIY - PY;
			run = AIX - PX;
		}
		else if(AIX < PX && AIY < PY)
		{
			rise = PY - AIY;
			run = PX - AIX;
		}
		else if(AIX > PX && AIY < PY)
		{
			rise = PY - AIY;
			run = AIX - PX;
		}
		else if(AIX > PX && AIY > PY)
		{
			rise = AIY - PY;
			run = PX - AIX;
		}
		;
	}*/
	public PlayerChar getTarget()
	{
		return target;
	}
	/*
	public void addCommand(Commands c)
	{
		AICommands.add(c);
	}
	
	public boolean execNextCommand()
	{
		if(AICommands == null || AICommands.size() <= 0)
		{
			int currCommand = AICommands.get(0).getCommand();
			return true;
		}
		else return false;
	}
	
	*/
}
