// Class: GraphicsMain
// Written by: Cat Attack Developers
// Last Updated: Apr 2, 2024
// Description: Run this class in order to run the game and call GraphicPanel

import javax.swing.JFrame;
import javax.swing.JPanel;
import javazoom.jl.player.*;


public class GraphicsMain extends JFrame{
	

	public static void main(String[] args) {
		System.out.println("READY PLAYER ONE");

		GraphicsMain window = new GraphicsMain();
		JPanel p = new JPanel();
		p.add(new GraphicsPanel());  
		window.setTitle("Cat Attack!");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(p);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}

}