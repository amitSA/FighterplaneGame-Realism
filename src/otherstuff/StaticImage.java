package otherstuff;



import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class StaticImage extends Rectangle2D.Float 
{
	private BufferedImage image;
	
	/**-The reason why static images have heights and widths and planes do not is that the plane width and height 
	 * will vary depending on sprite, while this width and height is constant also...
	 * -Every plane has exactly 1 sprite related to it, while different staticImage objects can have images that are different
	 */
	public StaticImage(String imageName,float x, float y,float w,float h)
	{
		super(x,y,w,h);
		try {
			image = ImageIO.read(new File(imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2d, ImageObserver io) 
	{
		g2d.drawImage(image, (int)(this.x+0.5), (int)(this.y+0.5), (int)(this.width+0.5), (int)(this.height+0.5), io);
	}
	
	
	public void act() 
	{
		//do nothing
	}
	

}
