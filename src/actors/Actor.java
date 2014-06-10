package actors;

import java.awt.geom.Point2D;

import otherstuff.GameWorld;

public abstract class Actor implements DrawableActor
{
	private  GameWorld gameWorld;
	private int team;
	
	protected float centerX;
	protected float centerY;
	
	private float width;
	private float height;
	private float rotation;
	
	private Life life;
	
	//width and height, what would they be? should they be passed in?
	public Actor(GameWorld gameWorld, float sX, float sY,int team,float h,float w, float r, int hp)
	{
		this.gameWorld = gameWorld;
		this.centerX = sX;
		this.centerY = sY;
		this.team = team;
		this.width = w;
		this.height = h;
		this.rotation = r;
		this.life = new Life(hp);
	}
	
	public Point2D.Float getCenterPoint()
	{
		return new Point2D.Float(centerX,centerY);
	}
	
	public GameWorld getGameWorld()
	{
		return gameWorld;
	}
	
	public int getTeam()
	{
		return team;
	}
	
	public float getWidth()
	{
		return width;
	}
	public float getHeight()
	{
		return height;
	}
	public void setWidth(float w)
	{
		width = w;
	}
	public void setHeight(float h)
	{
		height = h;
	}
	public float getRotation()
	{
		return rotation;
	}
	public void setRotation(float r)
	{
		rotation = r;
	}
	public int getLife()
	{
		return life.getLife();
	}
	public int getInitiallife()
	{
		return life.getInitialLife();
	}
	public void changeLife(int i )
	{
		life.changeLife(i);
	}
	public boolean didCrashedToGround()
	{
		return this.getGameWorld().getLandscape().crashedToGround(this);
	}
	public boolean isDead()
	{
		return getLife()<0;
	}
	
}
