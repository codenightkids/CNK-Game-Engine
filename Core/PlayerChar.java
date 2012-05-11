package Core;

import PlayerCommands.PlayerCommands;

public class PlayerChar extends Character
{
	private PlayerCommands commands = null;

	public PlayerChar(int sizeX, int sizeY, int posistionX, int posistionY, int health, String fileName, String characterName,int exist, int speed, PlayerCommands cmds) 
	{
		super(sizeX, sizeY, posistionX, posistionY, health, fileName, characterName, exist, speed);
		this.commands = cmds;
		cmds.KeyBindings(this);
	}
	
	public void collide(GameObject objHit)
	{
		this.commands.Collision(this, objHit);
	}
	public void interact(GameObject objInteract)
	{
		this.commands.Interact(this, objInteract);
	}
}

	


