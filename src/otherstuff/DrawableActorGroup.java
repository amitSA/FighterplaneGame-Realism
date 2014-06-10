package otherstuff;



import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import actors.Bullet;
import actors.BulletGroup;
import actors.DrawableActor;
import actors.Plane;
import actors.PlaneGroup;






public class DrawableActorGroup  
{

	PlaneGroup planeGroup;
	BulletGroup bulletGroup;
	//I TRIED PUTTING THEM INTO 1 GROUP ARRAY, but it did not work
	
	
	public DrawableActorGroup()
	{
		planeGroup = new PlaneGroup();
		bulletGroup = new BulletGroup();
	}
	public void add(DrawableActor a) 
	{
		if(a instanceof Plane)
			planeGroup.add(a);
		else if(a instanceof Bullet)
			bulletGroup.add(a);
	}
	
	public void draw(Graphics2D g2d,ImageObserver io)
	{
		planeGroup.draw(g2d, io);
		bulletGroup.draw(g2d,io);
	}
	public void act()
	{
		planeGroup.act();
		bulletGroup.act();
	}
	
	public PlaneGroup getPlaneGroup()
	{
		return planeGroup;
	}
	public BulletGroup getBulletGroup()
	{
		return bulletGroup;
	}
	
}
