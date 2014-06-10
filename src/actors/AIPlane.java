package actors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import javax.swing.Timer;

import otherstuff.GameWorld;
import otherstuff.SpriteImageLoader;

public class AIPlane extends FighterPlane{
	
	private Computer comp;
	
	public AIPlane(SpriteImageLoader spriteLoader, String name,GameWorld world, float sX, float sY, int team, float r, int hP) 
	{
		super(spriteLoader, name, world, sX, sY, team, r, hP);
		comp = new Computer();
	}
	
	public boolean act()
	{
		setProperties();
		return super.act();
	}
	
	private void setProperties()
	{
		//do stuff here
		Point2D.Float pCent = getCenterPoint();
		Point2D.Float pDest = comp.locatePoint();
		//transalate
		
		float dist = (float)(pCent.distance(pDest));
		float addAngle = -getRotation();  //CHECK NEGETIVE?
		
		addAngle = (float)Math.toDegrees(addAngle)%360;
		addAngle = convertAngle(addAngle);
		float getAngle = (float)Math.toDegrees(getAngle(dist,pDest.x-pCent.x,pDest.y-pCent.y));
		float newAngle = addAngle + getAngle ;
		newAngle = convertAngle(newAngle);
		if(Math.abs(newAngle) < 3 || Math.abs(newAngle-360) < 3)     //CONSTANTS(5)
		{
			setIsTurningUp(false);
			setIsTurningDown(false);
			if(comp.getMode()==Computer.FIND_MODE)
				this.setIsShooting(true);
		}
		else 
		{
			this.setIsShooting(false);
			this.setIsTurningUp(newAngle<0);
			this.setIsTurningDown(newAngle>0);
		}
		
	}
	private float convertAngle(float angle)
	{
		if(angle > 180)
			angle = angle-360;
		else if(angle<-180)
			angle = angle+360;
		return angle;
	}
	
	private float getAngle(float hyp, float cX,float cY)
	{
		double angle = Math.acos(cX/hyp);
		if(cY < 0 && cX > 0)
			angle = -angle;
		else if(cY < 0 && cY < 0)
			angle = -angle;
		else if(cX == 0 && cY < 0)
			angle = -angle;
		return (float)(angle);
	}
	
	public void draw(Graphics2D g2d,ImageObserver io)
	{
		Color c = g2d.getColor();
		g2d.setColor(Color.BLACK);
		int w = 10,h=10;
		g2d.fillOval((int)(comp.point.x-w/2), (int)(comp.point.y-h/2), w, h);
		Point2D.Float p = getCenterPoint();
		
		g2d.setColor(c);
		super.draw(g2d, io);
	}
	
	class Computer
	{
		public static final int RANDOM_MODE = 1;
		public static final int FIND_MODE = 2;
		public int currentMode;
		public Point2D.Float point;
		public int time;
		public Timer timer;
		public int inc;
		public Actor target;
		
		public Computer() 
		{
			currentMode = RANDOM_MODE;
			Point2D.Float temp = AIPlane.this.getCenterPoint();
			point = new Point2D.Float(temp.x,temp.y);
			
			time = 0;
			inc = 1;
			target = null;
			//timer = new Timer(inc,this);
			//timer.start();
			
		}
		
		
		
		//MAKE ERROR CHECKING IF THE POINT IS OUTSIDE VISBLESPACE
		private void getRandomPoint()
		{
			AIPlane thisP = AIPlane.this;
			GameWorld world = thisP.getGameWorld();
			Point2D.Float p = thisP.getCenterPoint();
			
			Rectangle2D.Float rect = world.getVisibleRectangle();
			
			Line2D.Float line = world.getLandscape().getGroundLine();
			float x,y;						
											
			float radius = (int)(Math.random()*300)+250;  //CONSTANTS
			float deg = (float)(Math.random()*Math.PI*2);
			x = (float)(Math.cos(deg)*radius)+p.x;
			y = (float)(Math.sin(deg)*radius)+p.y;
			float v = line.y2-rect.height/20.0f;
			if(y>v)										//CONSTANT(kind of)
				y = v;          			
			if(y<rect.y) 					
				y = rect.y+20; //  CONSTANT 
			point.setLocation(x, y);
		}
		private void locateNewTarget()
		{
			AIPlane thisP = AIPlane.this;
			GameWorld world = thisP.getGameWorld();
			ArrayList<Plane> plane = world.getDrawableActorGroup().getPlaneGroup().getList();
			for(int i = 0;i<plane.size();i++)
			{
				Plane p = plane.get(i);
				if(p.getTeam()!=thisP.getTeam())
				{
					target = p;
				}
			}
		}
		
		
		public Point2D.Float locatePoint()
		{
			
			final int TIME_UP = 600;
			AIPlane thisP = AIPlane.this;
			GameWorld world = thisP.getGameWorld();
			Point2D.Float p = thisP.getCenterPoint();
			if(currentMode == RANDOM_MODE)
			{
				if(point.distance(p) < 2.0) //CONSTANT
				{
					currentMode = FIND_MODE;
					time = 0;
				}
			}
			if(currentMode == FIND_MODE)
			{
				if(time > TIME_UP){
					currentMode = RANDOM_MODE;
					getRandomPoint();
				}
				else
				{
					if(target == null || target.isDead())
					{
						this.locateNewTarget();
					}
					point = target.getCenterPoint();
					time +=inc;
				}
			}
			return point;
		}
		
		public int getMode()
		{
			return currentMode;
		}
		
		
	}
}


/*if(distUp < distDown)            //ADD STABALIZER HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
{
	this.setIsTurningUp(true);
	this.setIsTurningDown(false);
}*/

