package actors;










import java.awt.event.KeyEvent;

import otherstuff.GameWorld;
import otherstuff.SpriteImageLoader;



public class UserPlane extends FighterPlane
{

	private int upKey;
	private int downKey;
	private int shootKey;
	
	public UserPlane(SpriteImageLoader spriteLoader,String name ,GameWorld world,float sX,float sY,int team,float r,int hP)
	{
		super(spriteLoader,name,world,sX,sY,team,r,hP);
		
		upKey = KeyEvent.VK_UP;
		downKey = KeyEvent.VK_DOWN;
		shootKey = KeyEvent.VK_SPACE;
	}
	
	//COMBINE THESE METHODS SO AN INT IS PASSED IN, METHOD CHECKS IF  THE INT IS EQUAL TO ANY OF THE FIELD INTS
	public int getUpKey()
	{
		return upKey;
	}
	public int getDownKey()
	{
		return downKey;
	}
	public int getShootKey()
	{
		return shootKey;
	}
}
