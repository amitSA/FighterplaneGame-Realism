package otherstuff;



import javax.swing.JFrame;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 JFrame w = new JFrame("Simple Window");
		    w.setBounds(10, 25, 1550, 800);
		    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    GamePanel gamePanel = new GamePanel();
		    
		    KeyInteraction keyInteraction = new KeyInteraction(gamePanel,5);
		
		    w.addKeyListener(keyInteraction);
		    w.add(gamePanel);
		    w.setResizable(false);
		    w.setVisible(true);
	}
}
