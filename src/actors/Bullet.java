package actors;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.image.ImageObserver;

import otherstuff.GameWorld;


public class Bullet extends Actor {

	private Color color;
	
	public Bullet(GameWorld gameWorld, float sX, float sY, float r, int team) {
		super(gameWorld, sX, sY,team,12,3,r,250);
		
		color = new Color(238,118,0);
	
	    int degrees = (int)(Math.random()*2.0);
	    if((int)(Math.random()*3) == 1)
	    	degrees *= -1;
	    setRotation((float)(getRotation()+Math.toRadians(degrees)));
		
	}
	public void draw(Graphics2D g2d, ImageObserver io) {
		Stroke stroke = g2d.getStroke();
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(getWidth()));
		
		float r = getRotation(),l = getHeight();
		
		float x = (float)(Math.cos(r)*l/2.0);
		float y = (float)(Math.sin(r)*l/2.0);
		
	    g2d.drawLine((int)(centerX-x+0.5), (int)(centerY-y+0.5), (int)(centerX+x+0.5), (int)(centerY+y+0.5));   
		g2d.setStroke(stroke);
	}
	
	public void moveForward()
	{
		float r = getRotation();
		float x = (float)(Math.cos(r)*10); //CONSTANT
		float y = (float)(Math.sin(r)*10); //CONSTANT
		
		centerX += x;
		centerY += y;
	}
	
	public boolean act() 
	{
		if(getLife()<=0 || this.didCrashedToGround())
			return false;
		
		moveForward();
		changeLife(-1);
		return true;	
	}
	
	//BS, repetive code
	public Line2D.Double getLineCoord()
	{
		float r = getRotation(),l = getHeight();
		float x = (float)(Math.cos(r)*l/2.0);
		float y = (float)(Math.sin(r)*l/2.0);
		return new Line2D.Double(centerX-x+0.5,centerY-y+0.5,centerX+x+0.5, centerY+y+0.5); 
	}

}
