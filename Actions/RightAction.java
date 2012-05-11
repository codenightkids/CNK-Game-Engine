package Actions;

import java.util.ArrayList;

import Core.GameObject;


public class RightAction implements KeyAction 
{
	boolean pressed = false;
	@Override
	public void keyAction(GameObject thisObject, ArrayList<GameObject> allObjects) 
	{
		if(thisObject.getRun() ==0)
		{
			pressed = true;
			thisObject.setRun(thisObject.getSpeed());
		}
	}
	@Override
	public void keyReleased(GameObject thisObject, ArrayList<GameObject> allObjects)
	{
		if(pressed)
		{
			pressed = false;
			thisObject.setRun(0);
		}
	}

}
