package otherstuff;



import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import actors.Actor;
import actors.Plane;





public class Landscape 
{
	Rectangle2D.Float visibleSpace;
	private StaticImage [] backImages;
	private Line2D.Float groundLine;
	
	public Landscape(Rectangle2D.Float vis) //worldRec
	{
		visibleSpace = vis;
		String bImage = "resources/savana3.png";  //CHANGE
		backImages = new StaticImage[3];  
		//seperating the line into 3 parts as well
		float x1 = vis.x-vis.width;
		float y1 = vis.y+vis.height - vis.height/29.0f;
		float x2 = vis.x+(vis.width*2);
		float y2 = y1;
		groundLine = new Line2D.Float(x1,y1,x2,y2);
		
		for(int i = -1;i<2;i++)
			backImages[i+1] = new StaticImage(bImage,(vis.x+vis.width*i),vis.height/3.0f*2.0f,vis.width,vis.height/3.0f);
	}
	
	public void draw(Graphics2D g2d, ImageObserver io)
	{
		for(StaticImage s:backImages)	
			s.draw(g2d, io);
	/*	Stroke k = g2d.getStroke();
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.BLACK);
		Line2D l = groundLine;
		g2d.drawLine((int)l.getX1(),(int)l.getY1(),(int)l.getX2(),(int)l.getY2());
		g2d.setStroke(k);*/
	}
	
	public StaticImage[] getBackgroundImages()
	{
		return backImages;
	}
	
	
	
	/** Method decides if the planes should be shifted 1 image to the left, or 1 image to the right based on the plane which was passed in
	 * @return an int denoting how much the planes should be shifted
	 */
	public void centerLandscapeToPoint(Point2D.Float c)
	{
		StaticImage [] im = backImages;
		float iW = im[0].width;  //the images should all be of the same length
		if(c.x < im[0].x+im[0].width)
			for(int i = 0;i<3;i++)
				im[i].x = im[i].x-iW;
		if(c.x > im[1].x+im[1].width)
			for(int i = 0;i<3;i++)
				im[i].x = im[i].x+iW;           
	}
	
	//SHOULD VISIBLE SPACE GO AS A FIELD
	public void castShadow(Graphics2D g2d,ImageObserver io,Actor a)
	{
		Rectangle2D.Float vis = visibleSpace;
		Point2D.Float c = a.getCenterPoint();		
		
		
		float cons = 0.5f; //CONSTANT
		float div = (c.y - vis.y)/vis.height  - cons;
		if(div>1 || div<0)return;                 //REMOVE REMOVE REMOVE
		
		float w = 60*(1-div),h = 15*(1-div);      //CONSTANTS
		
		Composite oldComp = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, div));
	    g2d.setColor(Color.BLACK);
	    g2d.fillOval((int)(c.x-w/2), (int)(groundLine.y2), (int)w, (int)h);
		
		
		g2d.setComposite(oldComp);
		
	}
	
	/**NOT SURE WHETHER THIS METHOD SHOULD
	 *  - remove the plain from the arrayList(of the group)
	 *  - return true/false
	 *  - or start the death animation of the plain
	 */ 
	public boolean crashedToGround(Actor a)
	{
		Point2D.Float c = a.getCenterPoint();
		if(c.y > groundLine.y2)
			return true;
		return false;
	}
	
	public Line2D.Float getGroundLine()
	{
		return groundLine;
	}
	
	
}

/*
//backImages[i] = new StaticImage(imageName,(int)(r.x+r.width/l*i+0.5),(int)(r.y+r.height/2+0.5),(int)(r.width/l+0.5),(int)(r.height/2+0.5));
			//backImages[i] = new StaticImage(imageName,0,0,500,760);
*/