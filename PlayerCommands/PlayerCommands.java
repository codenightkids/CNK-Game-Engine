package PlayerCommands;

import Core.GameObject;
import Core.PlayerChar;

/**
 * PlayerCommands interface
 * ::The reason for this interface is to seperate Player/Character creation from the tester class. 
 * @author Seth
 *
 */
public interface PlayerCommands 
{
	void KeyBindings(PlayerChar me);
	void Collision(PlayerChar me, GameObject hitMe);
	void Interact(PlayerChar me, GameObject meTouch);
}
