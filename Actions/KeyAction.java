package Actions;

import java.util.ArrayList;

import Core.GameObject;


public interface KeyAction
{
	void keyAction(GameObject thisObject, ArrayList<GameObject> allObjects);

	void keyReleased(GameObject thisObject, ArrayList<GameObject> allObjects);
}
