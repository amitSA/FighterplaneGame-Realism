package otherstuff;



import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import actors.DrawableActor;
import actors.UserPlane;





/**Equivalence is the Level class in the rectangle
This class just represents the entire "world" of the game which is a large rectangle
**/
public class GameWorld extends Rectangle2D.Float 
{							
	
	
	
	private Rectangle2D.Float visibleSpace;
	
	private DrawableActorGroup actorGroup;
	private Landscape landscape;
	
	
	public GameWorld()
	{
		this.x = 0;
		this.y = 0;
		this.width = 2000;  //FOR NOW MAKE IT
		this.height = GamePanel.ASSUMED_DRAWING_HEIGHT; //FOR NOW MAKE IT
		
		visibleSpace = new Rectangle2D.Float(0,0,GamePanel.ASSUMED_DRAWING_WIDTH,GamePanel.ASSUMED_DRAWING_HEIGHT);  
		landscape = new Landscape(visibleSpace); //technically, only passing a Rectangle2D.Float
		
		actorGroup = new DrawableActorGroup();
	}
	
	public void drawWorld(Graphics2D g2d, ImageObserver io)
	{
		g2d.translate(-visibleSpace.x,-visibleSpace.y);
		gradientWorld(g2d,io);
		
		
		landscape.draw(g2d, io);
		actorGroup.draw(g2d, io);
	}
	private void gradientWorld(Graphics2D g2d,ImageObserver io)
	{
		Paint oldP = g2d.getPaint();
		Paint gradient = new GradientPaint(this.width/2f,this.height,new Color(240,248,255),this.width/2f,0,new Color(132,112,255));
		g2d.setPaint(gradient);
		g2d.fill(this);       //FILING VISIBLERECT MIGHT BE QUICKER
	}
	
	
	public void actWorld()
	{
		actorGroup.act();
	}
	
	public void slideWorldToActor(DrawableActor a)  //CAN CHANGE TO IMAGE LATER
	{
		float panelWidth = GamePanel.ASSUMED_DRAWING_WIDTH;
		float panelHeight = GamePanel.ASSUMED_DRAWING_HEIGHT;
		
		Point2D.Float c = a.getCenterPoint();
		visibleSpace.setRect(c.x-panelWidth/2.0,0,
				panelWidth,panelHeight);
		if((visibleSpace.x+visibleSpace.width)>(this.x + this.width))
			this.x = this.x + 200 + (visibleSpace.x + visibleSpace.width)-(x+width); // CONSTANT
		else if(visibleSpace.x < this.x)
			this.x = visibleSpace.x-200;
		
		landscape.centerLandscapeToPoint(c);
			
		
		//LANDSCAPE SHIFT AS WELL
	}
	
	public void addDrawableActor(DrawableActor a)
	{
		actorGroup.add(a);
		
	}
	public DrawableActorGroup getDrawableActorGroup()
	{
		return actorGroup;
	}
	
	/**This method returns the rectangle object denoting the visible portion of the screen
	 * @return a Rectangle2D.Float object holding the dimensions and cordinates of the visible portion of the screen in relation to the
	 * entire gameWorld
	 */
	public Rectangle2D.Float getVisibleRectangle()
	{
		return visibleSpace;
	}
	public Landscape getLandscape()
	{
		return landscape;
	}
	
}

