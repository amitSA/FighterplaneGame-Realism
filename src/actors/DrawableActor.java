package actors;




import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.ImageObserver;


public interface DrawableActor 
{
	public void draw(Graphics2D g2d, ImageObserver io);
	
	public boolean act();
	
	public Point2D.Float getCenterPoint();
	
}
