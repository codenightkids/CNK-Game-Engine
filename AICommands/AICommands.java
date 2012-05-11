package AICommands;
import Core.AIChar;
import Core.GameObject;
import AICommands.AICommands;


public interface AICommands 
{
	void doMoves(AIChar ai);
	void onCollision(AIChar ai, GameObject thatWhoHitMe);
}
