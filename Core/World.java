package Core;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;


/*This class basically takes data managed by Level
 * and draws it onto a JPanel which the GameEngine
 * can repaint and draw on a JFrame
 */
@SuppressWarnings("serial") //we'll want to look into serializing worlds, possibly *think different maps?*
public class World extends JPanel
{
	
	//Variables three array lists containing
	//The different types of in game objects
	//to be displayed, also includes the 
	//buffered image for the background
	public ArrayList<PlayerChar> players = null;
	public ArrayList<AIChar> aiPlayers = null;
	public ArrayList<EnvObject> envObjects = null;
	BufferedImage background;
	
	//Constructor, uses the data from level to draw world objects to the screen
	public World(ArrayList<PlayerChar> players, ArrayList<AIChar> aiPlayers, ArrayList<EnvObject> envObjects)
	{
		this.setBounds(0, 0, GameEngine.resX, GameEngine.resY);
		this.setOpaque(true);
		this.setLayout(null);
		this.players = players;
		this.aiPlayers = aiPlayers;
		this.envObjects = envObjects;
	}
	
	//Sets the background into a Buffered Image
	//can be used to draw in a paint method
	public void setBackgroundImage(String fileName)
	{
		this.background = null;
		
		try
		{
			this.background = ImageIO.read(new File(fileName));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}
	
	//Main paint component method allows drawing
	//on the JPanel
	public void paint(Graphics g)
	{

			PlayerChar player;
			AIChar aiPlayer;
			EnvObject envObject;

			//Draw the background
			g.drawImage(background, 0, 0, null);
			
			//Draw the environmental objects
			for(int i = 0; i < envObjects.size(); i++)
			{
				envObject = envObjects.get(i);
				
				//if the player exists and is on the map, draw it
				if(envObject.exists >= 0)
					g.drawImage(envObject.graphic, envObject.getPositionX(), envObject.getPositionY(), null);
			}	
			
			//Draw the aiPlayers
			for(int i = 0; i < aiPlayers.size(); i++)
			{
				aiPlayer = aiPlayers.get(i);
				
				//if the player exists and is on the map, draw it
				if(aiPlayer.exists >= 0)
					g.drawImage(aiPlayer.graphic, aiPlayer.getPositionX(), aiPlayer.getPositionY(), null);
			}	
			
			//Draw the players
			for(int i = 0; i < players.size(); i++)
			{
				player = players.get(i);
				
				//if the player exists and is on the map, draw it
				if(player.exists >= 0)
				{ 
	
					g.drawImage(player.getImage(), player.getPositionX(), player.getPositionY(), null);
				}
			}
		

	}
	
	//Sets and gets for the arraylists
	public void setPlayers(ArrayList<PlayerChar> players)
	{
		this.players = players;
	}
	
	public void setAIPlayers(ArrayList<AIChar> aiPlayers)
	{		
		this.aiPlayers = aiPlayers;
	}
	
	public void setEnvObjects(ArrayList<EnvObject> envObjects)
	{
		this.envObjects = envObjects;
	}
}