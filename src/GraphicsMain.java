// GraphicsMain.java


// Written by: Mason Z.
// Modified Date: June 8th, 2023
// Main Class for a simple java program with graphics

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicsMain extends JFrame{
			
		public static void main(String[] args) 
		{	
			GraphicsMain window = new GraphicsMain();
	        JPanel p = new JPanel();
	        p.add(new GraphicsPanel());  
	        window.setTitle("Cat Hadoukens!");
	        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        window.setContentPane(p);
	        
	        window.pack();
	        window.setLocationRelativeTo(null);
	        window.setVisible(true);
	  
		}

}
