 package Core;
/*game Object is the parent class of:
 * EnvObject
 * Character
 */
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Actions.KeyAction;


public abstract class GameObject
{
	//exists = 0 when the object exists but is not drawn on the screen
	//exists = 1 when it is drawn.
	//exists = -1 when the object has been removed from screen.
	int defSpeed = 0;
	private int rise = 0;
	private int run = 0;
	int exists;
	int positionX;
	int positionY;
	int health;
	int sizeX;
	int sizeY;
	Sound sound;
	
	ArrayList<Integer> BINDEDKEYS = new ArrayList<Integer>();
	KeyAction[] ACTIONS = new KeyAction[140];
	
	boolean shapeChange;
	private int rotation = 0;
	//private keyspressed = GameEngine.getKeysPressed();
	String name;
	BufferedImage graphic;
	
//--------------------Getter methods-----------------//
	public int getSpeed()
	{
		return defSpeed;
	}
	public int getExists()
	{
		return exists;
	}
	public int getPositionX()
	{
		return this.positionX;
	}
	public int getPositionY()
	{
		return this.positionY;
	}
	public String getName()
	{
		return name;
	}
	public int getHealth()
	{
		return health;
	}
	BufferedImage getImage()
	{
		return graphic;
	}
	public int getSizeX()
	{
		return this.sizeX;
	}
	public int getSizeY()
	{
		return this.sizeY;
	}
	public int getRotation()
	{
		return this.rotation;
	}
	public boolean getShapeChange()
	{
		return this.shapeChange;
	}
	public Sound getSound()
	{
		return this.sound;
	}
//---------------------------------------------------//
	
/**
	 * @param rise the rise to set
	 */
	public void setRise(int rise) {
		this.rise = rise;
	}
	/**
	 * @return the rise
	 */
	public int getRise() {
		return rise;
	}
	public void setRun(int run) {
		this.run = run;
	}
	public int getRun() {
		return run;
	}
	//------------------Setter methods-------------------//
	void setSpeed(int speed)
	{
		this.defSpeed = speed;
	}
	
	/*
	*1+ = different states of existence
	* 0 = painted on the map, but doesn't exist
	*-1 = doesn't paint on map, doesn't collide
	*/
	public void setExists(int newE)
	{
		if(newE <= 0)
		{
			GameEngine.getCurLevel().getExistingObjects().remove(this);
		}
		this.exists = newE;
	}
	public void setPositionX(int newX)
	{
		this.positionX = newX;
	}
	public void setPositionY(int newY)
	{
		this.positionY = newY;
	}
	void setName(String newN)
	{
		this.name = newN;
	}
	void setHealth(int newH)
	{
		this.health = newH;
	}
	void setImage(BufferedImage image)
	{
		this.graphic = image;
	}
	public void setGraphic(String fileName)
	{
		File f = new File(fileName);
		try
		{
			this.graphic = ImageIO.read(f);
		}
		catch(IOException e)
		{	
			if(!f.toString().equals("wall"))	
				System.err.println("No Image File Loaded");
		}
	}
	void setRotation(int degree)
	{
		AffineTransform tx = new AffineTransform();
		tx.rotate(Math.toRadians(degree), this.getImage().getWidth()/2, this.getImage().getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		this.setImage(op.filter(this.getImage(), null));
		this.rotation = degree;
	}
	void setShapeChange(boolean change)
	{
		this.shapeChange = change;
	}

	//---------------------------------------------------//
//-----------------------Character Movement----------------------//
	public void up(int y)//Moves the character up y pixels
	{
		this.setRise(y);
	}
	public void down(int y)//Moves the character down y pixels
	{
		this.setRise(-y);
	}
	public void left(int x)//Moves the character left x pixels
	{
		this.setRun(-x);
	}
	public void right(int x)//Moves the character right x pixels
	{
		this.setRun(x);
	}
	public void stopMovement()
	{
		this.setRise(0);
		this.setRun(0);
	}
	void setSizeX(int sizeX)
	{
		this.sizeX = sizeX;
	}
	void setSizeY(int sizeY)
	{
		this.sizeY = sizeY;
	}
	
	public void setSound(String filename, boolean loop)
	{
		this.sound = new Sound(filename, loop);
	}
	
	public void setSound(ArrayList<String> filenames, ArrayList<Boolean> loops)
	{
		this.sound = new Sound(filenames, loops);
	}
	/**
	 * If in the pressed keys, one of our keys are in there, then do the action assigned to that key.
	 */
	void KeyPressed()
	{
		for(int i = 0; i < this.BINDEDKEYS.size(); i++)
		{
			if(GameEngine.PRESSEDKEYS[this.BINDEDKEYS.get(i)])
			{
				this.ACTIONS[this.BINDEDKEYS.get(i)].keyAction(this, GameEngine.getCurLevel().getAllObjects());
			}
			else
			{
				this.ACTIONS[this.BINDEDKEYS.get(i)].keyReleased(this, GameEngine.getCurLevel().getAllObjects());
			}
		}
	}

	public void setKeyActionBind(int keyNumber, KeyAction action)
	{
		if(!this.BINDEDKEYS.contains(keyNumber))
		{
		 	this.BINDEDKEYS.add(keyNumber);	
		}
	 	ACTIONS[keyNumber] =  action;
	}
	
	
	
	
	public void collide(GameObject objHit)
	{
			//I don't know what other classes should do when they are hit... probably a script like the AI stuff.

	}
//-----------------------------------------------------------------//
//-------------------Damage to object----------------//
	void damageCaused(int amount)
	{
		setHealth(getHealth() - amount);
	}
//---------------------------------------------------//
}
