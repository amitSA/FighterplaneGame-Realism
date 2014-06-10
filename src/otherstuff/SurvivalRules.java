package otherstuff;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import actors.AIPlane;
import actors.Plane;
import actors.PlaneGroup;
import actors.UserPlane;

public class SurvivalRules implements Rules 
{
	private GameWorld gameWorld;
	private SpriteImageLoader spriteLoader;
	private int level;
	

    public static final int ALLY_TEAM = 1;
	public static final int ENEMY_TEAM = 2;
		
	
	public SurvivalRules(GameWorld w)
	{
		this.gameWorld = w;
		spriteLoader = new SpriteImageLoader();
		
		gameWorld.addDrawableActor(makeUserPlane());
		gameWorld.addDrawableActor(makeEnemyAIPlane());
		level = 1;
	}
	
	
	public void updateGame()
	{
		//System.out.println(gameWorld.getDrawableActorGroup().getBulletGroup().getList().size());
		PlaneGroup planeGroup = gameWorld.getDrawableActorGroup().getPlaneGroup();
		UserPlane up = planeGroup.getUserPlane();
		if(up == null)
		{
			//you have lost the game, restart?
		}
		else
		{
			gameWorld.slideWorldToActor(up);	
			ArrayList<Plane> planes = planeGroup.getList();                    //THIS SHOULD BE A METHOD
			boolean flag = true;
			for(int i = 0;i<planes.size();i++)
			{
				if(planes.get(i).getTeam()!=up.getTeam()){
					flag = false;
					break;
				}
			}
			if(flag)
			{
				up.changeLife(up.getInitiallife()); //restoring health
				level++;
				for(int i = 1;i<level+1;i++)
				{
					gameWorld.addDrawableActor(makeEnemyAIPlane());
					if(i%2==1)
						gameWorld.addDrawableActor(makeAllyAIPlane());
				}
					
			}
				
		}
	}
	
	private float getRandomYVal()
	{
		Rectangle2D.Float rect = gameWorld.getVisibleRectangle();
		Line2D.Float line = gameWorld.getLandscape().getGroundLine();
		float yVal = (float)(Math.random()*(line.y2-rect.y) + rect.y);
		return yVal;
	}
	
	private AIPlane makeEnemyAIPlane()
	{
		String name = "resources/AIPlanes.png";
		Rectangle2D.Float rect = gameWorld.getVisibleRectangle();
		float yVal = getRandomYVal();
		AIPlane ap = new AIPlane(spriteLoader,name,gameWorld,rect.x+rect.width/4.0f,yVal,ENEMY_TEAM,(float)(Math.PI),60 );
		return ap;
	}
	
	private UserPlane makeUserPlane()
	{
		String name = "resources/fighterplane-user.png";
		Rectangle2D.Float rect = gameWorld.getVisibleRectangle();
		float yVal = getRandomYVal();
		UserPlane up = new UserPlane(spriteLoader,name,gameWorld,rect.x,yVal,ALLY_TEAM,0,100);
		return up;
	}
	
	private AIPlane makeAllyAIPlane()
	{
		String name = "resources/fighterplane-user.png";              //CHANGE
 		Rectangle2D.Float rect = gameWorld.getVisibleRectangle();
 		float yVal = getRandomYVal();
		AIPlane ap = new AIPlane(spriteLoader,name,gameWorld,rect.x,yVal,ALLY_TEAM,0,75  );
		return ap; //CHANGE
	}
	
	
	
	
}
