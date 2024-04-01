// GraphicsMain.java


// Written by: Cat-Hadouken Developers
// Modified Date: March 12, 2024
// Main Class for Cat Hadoukens with Graphics


import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphicsMain extends JFrame{
			
		public static void main(String[] args) 
		{	
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