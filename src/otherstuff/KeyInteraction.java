package otherstuff;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Timer;

import actors.UserPlane;






public class KeyInteraction implements KeyListener,ActionListener
{
	ArrayList<UserPlane> userPlanes;
	GamePanel gamePanel;
	Timer timer;
	
	public KeyInteraction(GamePanel panel,int delay) // for timer
	{
		userPlanes = new ArrayList<UserPlane>();
	//	ArrayList<Plane> planes = gamePanel.getGameWorld().getPlaneGroup().getPlaneList();
	//	this.updateUserPlanes(planes);
		
		this.gamePanel = panel;
		this.timer = new Timer(delay,this);
		this.timer.start();
	}

	//NOTE: SHOULD THE USER PLANES JUST TAKE IN E AND DO ALL THIS STUFF FOR US, that would make sense
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i = 0;i<userPlanes.size();i++)
		{
			UserPlane up = userPlanes.get(0);
			if(up.getUpKey()==key){
				up.setIsTurningUp(true);
				up.setIsTurningDown(false);
			}else if(up.getDownKey()==key){
				up.setIsTurningUp(false);
				up.setIsTurningDown(true);
			}else if (up.getShootKey()==key)
				up.setIsShooting(true);
		}
		if(KeyEvent.VK_P==key)
			this.gamePanel.togglePause();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		for(int i = 0;i<userPlanes.size();i++)
		{
			UserPlane up = userPlanes.get(0);
			if(up.getUpKey()==key)
				up.setIsTurningUp(false);
			else if(up.getDownKey()==key){
				up.setIsTurningDown(false);
			}else if(up.getShootKey()==key)
				up.setIsShooting(false);
				
			
		}
	}
	
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	
	public void updateUserPlanes()
	{
		userPlanes.clear();
		userPlanes.add(gamePanel.getGameWorld().getDrawableActorGroup().getPlaneGroup().getUserPlane());
	}

	
	public void actionPerformed(ActionEvent e) 
	{
		updateUserPlanes();
	}
	
	
	
}
