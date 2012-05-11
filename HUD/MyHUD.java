package HUD;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import Testers.Tester;

@SuppressWarnings("serial")
public class MyHUD extends HUD
{

	public MyHUD() 
	{
		//Score label
		JLabel score = new JLabel();
		score.setFont(new Font("Serif", Font.PLAIN, 20));
		score.setForeground(Color.GREEN);
		score.setText("Score: " + Tester.score);
        score.setOpaque(false);
        score.setBounds(20, 20,100, 30);
        
        this.setBackground(Color.green);
        this.add(score);
	}

}
