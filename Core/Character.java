package Core;

/*Parent Class of:
 * PlayerChar
 * AiChar
 */

public abstract class Character extends GameObject
{

	
//----------------------------Constructor--------------------------//
	public Character(int sizeX, int sizeY, int posX, int posY, int health, String fileName, String characterName, int exist, int speed)
	{
		setPositionX(posX);
		setPositionY(posY);
		setSizeX(sizeX);
		setSizeY(sizeY);
		setHealth(health);
		setGraphic(fileName);
		setName(characterName);
		setExists(exist);
		setSpeed(speed);
	}
//------------------------------------------------------------------//
	
//-------------------------Character Actions-----------------------//

//-----------------------------------------------------------------//

	
	
}
