package AICommands;

import Core.AIChar;
import Core.GameObject;
import Core.PlayerChar;


public class ExampleAICommands implements AICommands{

	@Override
	public void doMoves(AIChar ai) 
	{
		chase(ai);
		
	}

	
	public void chase(AIChar ai)
	{
		if(ai.getHealth() > 0)
		{
			 PlayerChar target = ai.getTarget();
			 int x, y, x1, x2, y1, y2;
			 x1 = ai.getPositionX();
			 x2 = target.getPositionX();
			 y1 = ai.getPositionY();
			 y2 = target.getPositionY();
			 x = x2 - x1;
			 x = x / (ai.getSpeed() + 30);
			 y = y2 - y1;
			 y = y / (ai.getSpeed() + 30);
			 ai.setRise(y);
			 ai.setRun(x);
		}
	}


	@Override
	public void onCollision(AIChar ai, GameObject thatWhoHitMe) {
		if(ai.getHealth() > 0)
		{
			if(thatWhoHitMe.getExists() == 2)
			{
				thatWhoHitMe.setExists(-1);
				ai.setExists(-1);
				ai.getSound().playAudio();
			}
		}
		else
		{
			if(ai.getExists()!=0)
			{
				ai.setRun(0);
				ai.setRise(0);
				ai.setExists(0);
				ai.setGraphic("images\\deadLittleEnemy.png");
			}
		}
		
	}

}
