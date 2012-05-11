package Actions;
import java.util.ArrayList;

import Core.EnvObject;
import Core.GameEngine;
import Core.GameObject;


public class ShootAction implements KeyAction 
{
	boolean pressed = false;
	int bullet = 0;
	@Override
	public void keyAction(GameObject thisObject, ArrayList<GameObject> allObjects) 
	{
		if(!pressed)
		{
			pressed = true;
			String temp = "bullet" + bullet;
			bullet++;
			EnvObject bullet = new EnvObject(33, 33, thisObject.getPositionX()+40, thisObject.getPositionY()+40, 0, "images\\littleHero.png", temp, 2, 30);
			
			if(thisObject.getRise() > 0)
			{
				bullet.setPositionY(thisObject.getPositionY()+40);
				bullet.setRise(30);
			}
			else if(thisObject.getRise() < 0)
			{
				bullet.setRise(-30);
				bullet.setPositionY(thisObject.getPositionY()-40);
			}
			else if(thisObject.getRun() > 0)
			{
				bullet.setPositionX(thisObject.getPositionX()+40);
				bullet.setRun(30);
			}
			else if(thisObject.getRun()<0)
			{
				bullet.setPositionX(thisObject.getPositionX()-40);
				bullet.setRun(-30);
			}
			else
			{
				bullet.setPositionX(thisObject.getPositionX()+100);
				bullet.setRun(30);
			}
			
			
			
			GameEngine.getCurLevel().addEnvObject(bullet);
		}
	
	}
	@Override
	public void keyReleased(GameObject thisObject, ArrayList<GameObject> allObjects)
	{
		if(pressed)
		{
			pressed = false;
		}
		
	}

}
