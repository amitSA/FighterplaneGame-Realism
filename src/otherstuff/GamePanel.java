package otherstuff;



import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import actors.AIPlane;
import actors.FighterPlane;
import actors.UserPlane;





public class GamePanel extends JPanel implements ActionListener
{
	private GameWorld gameWorld; // it does make sense for the gamePanel to HAVE-A GameWorld
	private Timer timer;
	
	//THESE 2 ARE THE ONLY BAD-STYLE STATIC VARIABLES I MADE
	public static final int ASSUMED_DRAWING_WIDTH = 1534; //784, RIGHT NOW THE STATIC VARIABLE IS THE ACTUAL HEIGHT OF THE PANEL
	public static final int ASSUMED_DRAWING_HEIGHT = 761;  //561, RIGHT NOW THE STATIC VARIABLE IS THE REAL HEIGHT OF THE PANEL	
	//THESE 2 ARE THE ONLY BAD-STYLE STATIC VARIABLES I MADE
	
	SurvivalRules rules;
	
	private float ratioX;
	private float ratioY;
	
	
	
	public GamePanel()
	{
		gameWorld = new GameWorld(); //THE PLANE ARRAY WILL EXIST INSIDE THIS CLASS
		
		rules = new SurvivalRules(gameWorld);
		timer = new Timer(8,this);
		timer.start();
		
		 ComponentAdapter compAd = new ComponentAdapter()
		  {
			  public void componentResized(ComponentEvent e) 
			  	{   
			  		ratioX = (float)e.getComponent().getWidth()/ASSUMED_DRAWING_WIDTH;
			  		ratioY = (float)e.getComponent().getHeight()/ASSUMED_DRAWING_HEIGHT;	  		
			  	} 
		  };
		  addComponentListener(compAd);	
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.setBackground(new Color(224,224,224));
		Graphics2D g2d = (Graphics2D)(g);
	
			AffineTransform old = g2d.getTransform();
			g2d.scale(ratioX,ratioY);
			
			gameWorld.drawWorld(g2d,this);
			
			g2d.setTransform(old);
		
	}

	public void actionPerformed(ActionEvent e) 
	{
		//THERE SHOULD BE A CLASS WHICH DOES THIS	
		gameWorld.actWorld();
		rules.updateGame();
		repaint();
		
	}
	
	public void togglePause()
	{
		if(timer.isRunning())
			timer.stop();
		else
			timer.start();
	}
	
	public GameWorld getGameWorld()
	{
		return gameWorld;
	}
	
	
	
	
}
