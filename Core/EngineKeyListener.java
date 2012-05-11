package Core;
/**
 * This class is meant to handle the actions of keyboard keys.
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class EngineKeyListener implements KeyListener
{

	static boolean pressedDown = false;
	static boolean pressedRight = false;
	static boolean pressedUp = false;
	static boolean pressedLeft = false;
	static boolean quit = false;
	public int test = 0;
	
	@Override
	public synchronized void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == 27)
			GameEngine.endGame();
		
		//if the pressed key isnt already pressed, add it.
		GameEngine.PRESSEDKEYS[e.getKeyCode()] = true;
		GameEngine.NUMPRESSEDKEYS++;
		GameEngine.KeyEvents.start();
	}



	@Override
	public synchronized void keyReleased(KeyEvent e) 
	{
		GameEngine.PRESSEDKEYS[e.getKeyCode()] = false;
		GameEngine.NUMPRESSEDKEYS--;		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
}

