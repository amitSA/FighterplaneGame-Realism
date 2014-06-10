package otherstuff;




import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


/**this class is responsible for loading the the spriteImages for all the planes
 * the class reads the image into a file, and stores that image file into a map
 * which has class files
 * 
 * this sprite image for a plane should have the same name as its class(remember inheritance)
 */
public class SpriteImageLoader 
{
	private HashMap<String,Wrapper<Rectangle[],BufferedImage>> map;
	
	public SpriteImageLoader()
	{
		map = new HashMap<String, Wrapper<Rectangle[],BufferedImage>>();
		
		loadFileName("resources/fighterplane-user.png",540);
		loadFileName("resources/AIPlanes.png",540);
	}
	
	public void loadFileName(String name, int approxLength)
	{
		BufferedImage buf = null;
		try {
			buf = ImageIO.read(new File(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Rectangle> temp = new ArrayList<Rectangle>();
		int appL =  approxLength, height = buf.getHeight(),width=buf.getWidth(); //508
		
		here1:
			for(int c = 0;c<width;c++){          //RESET COLS, CFOURTH < WIDTH AS WELL
				for(int r = 0;r<height;r++){
					if(buf.getRGB(c, r)!=0)
					{
						int startX = c;
						for(int rSec = 0;rSec<height;rSec++)
						{
							for(int cSec=c;cSec<c+appL;cSec++)
							{
								if(buf.getRGB(cSec,rSec)!=0)
								{
									int startY = rSec;
									here3:
									for(int cThird = c;cThird<c+appL;cThird++)
									{
										for(int rThird = startY;rThird<height;rThird++)
										{
											if(buf.getRGB(cThird,rThird)!=0)
												continue here3;
										}
										//if the code reaches here
										int widthP = cThird-c;
										here4:
										for(int rFourth = r;rFourth<height;rFourth++)    //START X AND C?
										{
											for(int cFourth = c;cFourth<c+appL;cFourth++)
											{
												if(buf.getRGB(cFourth,rFourth)!=0)
													continue here4;
											}
											int heightP = rFourth-startY;
											//if the code reaches here
											temp.add(new Rectangle(startX,startY,widthP,heightP));                            //use inc++
											//System.out.println(temp[inc-1]);
											c=startX+widthP;
											continue here1;
										}
									}
								}
							}
						}
					}
				}
			}
		
		Rectangle[] ar = new Rectangle[temp.size()];
		for(int i = 0;i<temp.size();i++)
			ar[i] = temp.get(i);
		map.put(name, new Wrapper<Rectangle[],BufferedImage>(ar,buf));
	}
	
	public Rectangle [] getSpriteRect(String fileName)
	{
		return map.get(fileName).getFirst();
	}
	public BufferedImage getSpriteImage(String fileName)
	{
		return map.get(fileName).getSecond();
	}
	
	//I used generics for fun
	class Wrapper<T,P>
	{
		T t;
		P p;
		public Wrapper(T temp, P pemp)
		{
			t = temp;
			p = pemp;
		}
		public T getFirst()
		{
			return t;
		}
		public P getSecond()
		{
			return p;
		}		
	}
}

/*try {
for(int i = 0;i<classNames.length;i++)
{
	Class<FighterPlane>c = (Class<FighterPlane>) Class.forName(classNames[i]); //CHANGE IF NECESSARY
	BufferedImage pic = ImageIO.read(new File(classNames[i]+".png"));
	map.put(c, pic);
}
} catch (IOException e) {
e.printStackTrace();
} catch (ClassNotFoundException e) {
e.printStackTrace();
}*/
/*Map<Class<FighterPlane>,BufferedImage> map;

public SpriteImageLoader()  //these are the class names to load, class names are the same as their image names
{
	map = new HashMap<Class<FighterPlane>,BufferedImage>();

}

//CAN ONLY BE A FIGHTERPLANE
**Returns a sprite image for the class which name was passed in
 *   - more specifically, it returns the image associated with that class or any of its subclasses
 * @pre an image file for this class or any of its super classes should exist in RAM
 *
public BufferedImage getSpriteForClass(String className)
{
	BufferedImage pic = null;
	try {
		pic = map.get(Class.forName(className));
		if(pic==null){
			Class<Actor> c = (Class<Actor>) Class.forName(className);
			for(File f = new File(c.getName()+".png");c!=null&&pic==null;f=new File(c.getName()+".png")) //HARDCODED VALUES
			{
				if(f.exists())
					pic = ImageIO.read(f);
				c = (Class<Actor>) c.getSuperclass();
				if(c==null)
					break;
			}
		}
			
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(pic==null)
		System.out.println("EXCEPTION IN SPRITEIMAGELOADER, GETSPRITEFORCLASS");
	return pic;
}*/