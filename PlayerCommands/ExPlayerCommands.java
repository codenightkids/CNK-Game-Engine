package PlayerCommands;

import java.awt.event.KeyEvent;

import Actions.*;
import Core.GameObject;
import Core.PlayerChar;

public class ExPlayerCommands implements PlayerCommands 
{

	@Override
	public void Collision(PlayerChar me, GameObject hitMe) 
	{
		me.setGraphic("images\\littleHeroUp.png");
	}

	@Override
	public void Interact(PlayerChar me, GameObject meTouch) 
	{
		System.out.println("My name is " + me.getName() + ". How do you do " + meTouch.getName() + "?");
	}

	@Override
	public void KeyBindings(PlayerChar me) 
	{
		me.setKeyActionBind(KeyEvent.VK_W, new UpAction());
		me.setKeyActionBind(KeyEvent.VK_S, new DownAction());
		me.setKeyActionBind(KeyEvent.VK_A, new LeftAction());
		me.setKeyActionBind(KeyEvent.VK_D, new RightAction());	
		me.setKeyActionBind(KeyEvent.VK_SPACE, new ShootAction());
	}

}
