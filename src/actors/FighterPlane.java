package actors;




import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;

import otherstuff.GameWorld;
import otherstuff.SpriteImageLoader;



	

public class FighterPlane extends Plane
{
	private boolean isShooting;
	private int coolDown;
	private int count;
	
	public FighterPlane(SpriteImageLoader spriteLoader,String name ,GameWorld world,float sX,float sY,int team,float r,int hP)
	{
		super(spriteLoader,name,world,sX,sY,team,r,hP);
		isShooting = false;
		
		count = 5;
		coolDown = 5;
	}
	
	public void shoot()
	{
		if(count >= coolDown)
		{
			GameWorld world = this.getGameWorld();
			Bullet b = new Bullet(world,centerX,centerY,getRotation(),getTeam());
			world.addDrawableActor(b);
			count = 0;
		}
		else
		{
			count++;
		}
	}
	public void isInCoolDown()
	{
		if(count < coolDown)
			isShooting = false;
		else if(isShooting)
			count = 0;
		count++;
	}
	
	public boolean act()
	{
		if(isShooting)
			shoot();
		return super.act();
	}
	public boolean getIsShooting()
	{
		return isShooting;
	}
	public void setIsShooting(boolean bool)
	{
		isShooting = bool;
	}
}

