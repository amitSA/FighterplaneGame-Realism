package actors;

public class Life 
{
	private int remainingLife;
	private int initHitPoints;
	
	public Life(int hP)
	{
		initHitPoints = hP;
		remainingLife = hP;
	}
	
	public int getLife()
	{
		return remainingLife;
	}
	/**
	 * i can be negetive or positive
	 */
	public void changeLife(int i)
	{
		remainingLife = Math.min(remainingLife,remainingLife+i);
	}
	
	public int getInitialLife()
	{
		return initHitPoints;
	}
	
	
}
