package Actions;

import java.util.ArrayList;

import Core.GameObject;


public class DownAction implements KeyAction 
{
	boolean pressed = false;
	@Override
	public void keyAction(GameObject thisObject, ArrayList<GameObject> allObjects) 
	{

		if(thisObject.getRise() == 0)
		{
			pressed = true;
			thisObject.setRise(thisObject.getSpeed());
		}
		
	}
	@Override
	public void keyReleased(GameObject thisObject, ArrayList<GameObject> allObjects) 
	{
		if(pressed)
		{
			pressed = false;
			thisObject.setRise(0);
		}
	}



}
