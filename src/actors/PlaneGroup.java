package actors;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;


public class PlaneGroup implements Group 
{
	private ArrayList<Plane> planes;
	
	public PlaneGroup()
	{
		planes = new ArrayList<Plane>();
	}

	
	public void add(DrawableActor a) 
	{
		Plane p = (Plane) a;
		
		//ONLY 1 USER PLANE IS ALLOWED TO BE ADDED??!!!
		planes.add(p);
	}
	
	public Class<Plane> getRepresentingClass() 
	{
		Class<Plane> repClass = null; 
		try {
			repClass = (Class<Plane>) Class.forName("actors.Plane");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return repClass;
	}
	
	public ArrayList<Plane> getList()
	{
		return planes;
	}


	public void draw(Graphics2D g2d, ImageObserver io) {
		for(Plane p: planes)
			p.draw(g2d, io);
	}

	public void act() {
		for(int i = 0;i<planes.size();i++)
		{
			Plane p = planes.get(i);
			if(!p.act()){
				planes.remove(i);
				i--;
			}
		}
	}
	
	public UserPlane getUserPlane()
	{
		for(int i = 0;i<planes.size();i++)
		{
			Plane p = planes.get(i);
			if(p instanceof UserPlane)
				return (UserPlane)p;
		}
		return null;
	}
}
