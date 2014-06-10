package actors;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;




public interface Group
{
	public void add(DrawableActor a);
	
	public ArrayList<? extends DrawableActor> getList();
	
	public Class<? extends DrawableActor> getRepresentingClass();
	
	public void draw(Graphics2D g2d, ImageObserver io);
	
	public void act();
	
}
















/*import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;


public class Group<T extends DrawableActor> 
{
	private Class<T> classType;
	private ArrayList<T> list;
	
	public Group(Class<T> c)
	{
		classType = c;
		list = new ArrayList<T>();  //FORCE INITIAL AMOUNT?
	}
	
	public void add(DrawableActor a)
	{
		list.add((T) a);
	}
	
	public void act()
	{
		for(int i = 0;i<list.size();i++)
		{
			T a = list.get(i);
			if(!a.act()){
				list.remove(i);
				i--;
			}
		}
	}
	public void draw(Graphics2D g2d, ImageObserver io)
	{
		for(T a: list)
			a.draw(g2d, io);
	}
			
	
	public ArrayList<T> getList()
	{
		return list;
	}
	
	public Class<T> getRepresentingClass()
	{
		return classType;
	}
	
	
}
*/