package actors;







import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import otherstuff.GameWorld;
import otherstuff.SpriteImageLoader;





public abstract class Plane extends Actor implements ActionListener
{
	private Rectangle [] spriteRects;         /*represents the coordinates of a plane on the spritesheet, the width and height is proportional to the
	                                           actual width and height*/
	private BufferedImage spriteSheet;
	private int pos;       //this is the index to be used for the spriteRects array
	
	private boolean isTurningUp,isTurningDown; //ISSHOOTING AS WELL
	
	private float scale;
	//MOVE
	Timer timer;
	

		//SHOULDNT TAKE IN AN IMAGE IF i HARD CODE THE IMAGE VALUES
	public Plane(SpriteImageLoader loader,String imageName,GameWorld world,float sX,float sY,int team, float rot, int hP)
	{
		
		super(world,sX,sY,team,0,0,rot,hP);
		
		spriteRects = loader.getSpriteRect(imageName);
		spriteSheet = loader.getSpriteImage(imageName);
		
		pos = spriteRects.length/2;
		
		isTurningUp = isTurningDown = false;
		
		scale = 0.13f;
		setWidth(spriteRects[pos].width*scale);
		setHeight(spriteRects[pos].height*scale);
		
		//MOVE
		timer = new Timer(100,this);
		timer.start();
	}
	
	public boolean act()
	{
		moveForward();
		if(isTurningUp)
			tiltUp();
		else if(isTurningDown)
			tiltDown();
		
	
		intersectWithBullets();
		return !isDead() && !this.didCrashedToGround();
	}
	
	//YOU NEED TO MAKE ANOTHER CLASS WHICH COMPUTES THE DISTANCE IT SHOULD MOVE IN RELATION TO THE TIMER FOR THE ENGINE
	public void moveForward()
	{
		float r = getRotation();
		float xDisp = (float) (Math.cos(r)*2.0);
		float yDisp = (float) (Math.sin(r)*2.0);
		centerX += xDisp;
		centerY += yDisp;
	}
	
	//COMBINE THESE TO METHODS
	public void tiltDown()
	{
		//PRE CALCULATE THIS VALUE
		setRotation((float)(getRotation()+Math.toRadians(1.5)));
		//change the sprite image
	}
	
	public void tiltUp()
	{
		setRotation((float)(getRotation()-Math.toRadians(1.5)));
	}
	
	public void draw(Graphics2D g2d, ImageObserver io)
	{
		AffineTransform old = g2d.getTransform();
		g2d.translate((int)(centerX+0.5),(int)(centerY+0.5));
		Rectangle r = spriteRects[pos];
	    g2d.rotate(getRotation());
	    
	    float w = getWidth(),h=getHeight();
		int destX1 = -(int)(w/2.0 + 0.5);
		int destY1 = -(int)(h/2.0+0.5);
		int destX2 = (int)(w/2.0 +0.5);
		int destY2 = (int)(h/2.0 + 0.5);
		
		//BufferedImage subImage = spriteSheet.getSubimage(r.x, r.y, r.width, r.height);
		g2d.drawImage(spriteSheet,destX1,destY1,destX2,destY2,r.x,r.y,r.width+r.x,r.height+r.y,io);
		//g2d.drawImage(subImage,destX1,destY1,io);																					
		g2d.setTransform(old);
		this.getGameWorld().getLandscape().castShadow(g2d, io, this);
	}
	
	
	public void intersectWithBullets()
	{
		Point2D.Double cornerP1=null,cornerP2=null,cornerP3=null,cornerP4=null;
		float width = getWidth(),height = getHeight(),radians = getRotation();
		// first set of Points
		 double diagHalfLength = Math.sqrt(width*width + height*height)/2;
		 double innerHalfAngle = Math.atan(height/width);
		 double innerAngle = radians-innerHalfAngle;
		 
		 double yAdd = Math.sin(innerAngle) * diagHalfLength;
		 double xAdd = Math.cos(innerAngle) * diagHalfLength;
		 
		 double startX1 = centerX + xAdd;
		 double startY1 = centerY + yAdd;
		 cornerP1 = new Point2D.Double(startX1,startY1);
		 		 
		 //second set of Points
		 innerAngle = radians + innerHalfAngle;
		 
		 yAdd = Math.sin(innerAngle) * diagHalfLength;
		 xAdd = Math.cos(innerAngle) * diagHalfLength;
		 
		 double endX1 = centerX + xAdd;
		 double endY1 = centerY + yAdd;
		 cornerP2 = new Point2D.Double(endX1,endY1);
		
		 
		          // CHECKING FOR INTERSECT IN SIDE 2
		 // first set of Points
		 innerAngle = radians + Math.toRadians(180)-innerHalfAngle;
		 yAdd = Math.sin(innerAngle) * diagHalfLength;
		 xAdd = Math.cos(innerAngle) * diagHalfLength;
		 
		 double startX2 = centerX + xAdd;
		 double startY2 = centerY + yAdd;
		 cornerP3 = new Point2D.Double(startX2,startY2);
		 
					 //second set of Points
		 innerAngle = radians + Math.toRadians(180)+innerHalfAngle;
		 
		 yAdd = Math.sin(innerAngle) * diagHalfLength;
		 xAdd = Math.cos(innerAngle) * diagHalfLength;
		 
		 double endX2 = centerX + xAdd;
		 double endY2 = centerY + yAdd;
		 cornerP4 = new Point2D.Double(endX2,endY2);
			 
		ArrayList<Bullet> bullets = getGameWorld().getDrawableActorGroup().getBulletGroup().getList();
		
		for(int i = 0;i<bullets.size();i++)
		{
			if(bullets.get(i).getTeam()==getTeam())
				continue;	
			 Line2D.Double line = bullets.get(i).getLineCoord();
			 if(line.intersectsLine(cornerP1.getX(),cornerP1.getY(),cornerP2.getX(),cornerP2.getY()))     
			 {
				 bullets.remove(i);
				 i--;
				 changeLife(-1);
				 continue;
			 }
			 if(line.intersectsLine(cornerP3.getX(),cornerP3.getY(),cornerP4.getX(),cornerP4.getY()))
			 {
				 bullets.remove(i);
				 i--;
				 changeLife(-1);
				 continue;
			 }
			 		
			 if(line.intersectsLine(cornerP1.getX(),cornerP1.getY(),cornerP4.getX(),cornerP4.getY()))
			 {
				 bullets.remove(i);
				 i--;
				 changeLife(-1);
				 continue;
			 }
			 		
			 if(line.intersectsLine(cornerP2.getX(),cornerP2.getY(),cornerP3.getX(),cornerP3.getY()))
			 {
				 bullets.remove(i);
				 i--;
				 changeLife(-1);
				 continue;
			 }
		}
		
		
	}
	
	//Getters and Setters
	public boolean isTurningDown()
	{
		return isTurningDown;
	}
	public boolean isTurningUp()
	{
		return isTurningUp;
	}
	public void setIsTurningUp(boolean bool)
	{
		isTurningUp = bool;
	}
	public void setIsTurningDown(boolean bool)
	{
		isTurningDown = bool;
	}

	
	//CHANGE WIDTH AND HEIGHT AS WELL
	public void actionPerformed(ActionEvent e) 
	{
		if(isTurningUp)
			pos = Math.min(spriteRects.length-1, pos+1);
		else if(isTurningDown)
			pos = Math.max(0,pos-1);
		else{
			if(pos>spriteRects.length/2)
				pos--;
			else if(pos<spriteRects.length/2)
				pos++;
		}
		setWidth(spriteRects[pos].width*scale);
		setHeight(spriteRects[pos].height*scale);
			
	}



	
	
	
	
}


/*each sprite sheet will have dimensions of its own, and hit detection will be calculated
  assuming each plane is a rectangle but...
      -each sprite sheet will have its own set of widths and heights for a plane
      -the center point of the plane should remain the same  
 */

/*
int destX2 = (int)(destX1+r.width);
int destY2 = (int)(destY1+r.height);
g2d.drawImage(spriteSheet,destX1,destY1,destX2,destY2,r.y,r.x,r.x+r.width,r.y+r.height,io); //width and height have to be defined
*/


/*
for (int y = 0; y < subImage.getHeight(); ++y) 
{		
    for (int x = 0; x < subImage.getWidth(); ++x) 
    {
         int argb = subImage.getRGB(x, y);
         int bl = argb&255;
         int gr = (argb>>8)&255;
         int red = (argb>>16)&255;
         if(bl==255&&gr==255&&red==255)
        	 subImage.setRGB(x, y,0);
         else if(bl==0&&gr==0&&red==0)
        	 subImage.setRGB(x, y,7);
        	 
      //   Color color = new Color(argb);
      //   System.out.println(color);
      //   if (argb == -1)
      //   {   
      //  	 subImage.setRGB(x, y, 0);
      //   }
    }
}*/

/*ArrayList<Bullet> bulList = getGameWorld().getDrawableActorGroup().getBulletGroup().getList();
Path2D.Float thisPath = getPath(this);*/
/*for(int i = 0;i<bulList.size();i++)
{
	Bullet b = bulList.get(i);
	if(b.getTeam()!=this.getTeam())
	{
		Path2D.Float bulPath = getPath(b);
		if(thisPath.)
	}
}*/

/*private Path2D.Float getPath(Actor a)
{
float w = a.getWidth(), h = a.getHeight();
Rectangle2D.Float r = new Rectangle2D.Float(centerX-w/2.0f,centerY-h/2.0f,w,h);
Path2D.Float path = new Path2D.Float();
path.append(r, false);
AffineTransform t = new AffineTransform();
t.rotate(a.getRotation());
path.transform(t);
return path;
}*/


/*
	//RECTANGLES ALL HAVE TO BE INITIALIZED
		//USE THE SPRITEIMAGE CLASS
	//	Rectangle [] temp = {new Rectangle(695,70,)                                //-5
				                                             //-4
				                                             //-3 
				                                             //-2
	//			             new Rectangle(81,45,508,184),   //-1
	//			             new Rectangle(52,376,509,164)}; //0
		                                                     //1
		                                                     //2
		                                                     //3
		                                                     //4
		                                                     //5

*/