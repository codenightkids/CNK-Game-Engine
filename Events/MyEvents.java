package Events;
import javax.swing.JLabel;

import Testers.Tester;
import Core.Level;

public class MyEvents implements Events 
{


	@Override
	public void CheckEvents(Level CurrentLevel) 
	{
		JLabel score;
		score = (JLabel)CurrentLevel.getHUD().getComponent(0);
		Tester.score = 0;
		for(int j = 0; j < CurrentLevel.getAI().size(); j++)
		{
			if(CurrentLevel.getAI().get(j).getExists()==-1)
			Tester.score++;
		}
		score.setText("Score:" + Tester.score);
		
		if(Tester.score == 3)
		{
			CurrentLevel.setBackground("images\\endlevel.png");
			score.setText("You Win!");
		}
	}

}
