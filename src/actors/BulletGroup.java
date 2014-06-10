package actors;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;


public class BulletGroup implements Group{

	private ArrayList<Bullet> bullets;
	
	public BulletGroup()
	{
		bullets = new ArrayList<Bullet>();
	}
	
	public void add(DrawableActor a) {
		Bullet b = (Bullet) a;
		bullets.add(b);
	}

	public ArrayList<Bullet> getList() {
		return bullets;
	}
	
	public Class<Bullet> getRepresentingClass() { 
		try {
			return (Class<Bullet>) Class.forName("actors.Bullet");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void draw(Graphics2D g2d, ImageObserver io) {
		for(Bullet b:bullets)
			b.draw(g2d, io);
	}

	@Override
	public void act() {
		for(int i = 0;i<bullets.size();i++)
		{
			Bullet p = bullets.get(i);
			if(!p.act()){
				bullets.remove(i);
				i--;
			}
		}
	}

}
