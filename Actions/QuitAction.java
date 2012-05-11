package Actions;
import java.util.ArrayList;

import Core.GameEngine;
import Core.GameObject;
import Core.Level;


public class QuitAction implements KeyAction 
{
	boolean pressed = false;
	int bullet = 0;
	@Override
	public void keyAction(GameObject thisObject, ArrayList<GameObject> allObjects) 
	{
		Level endLevel = new Level("endlevel");
		endLevel.setBackground("images\\endlevel.png");
		//THIS WONT WORK, we need a switch level method.
		GameEngine.setLevel(endLevel);

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
