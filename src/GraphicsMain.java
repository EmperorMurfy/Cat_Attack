// Class: GraphicsMain
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class contains the main method for this project. You shouldn't modify this class.
//              This class must be selected when you run your project.
import javax.swing.JFrame;
import javax.swing.JPanel;
import javazoom.jl.player.*;


public class GraphicsMain extends JFrame{
	

	public static void main(String[] args) {
		System.out.println("print");
		



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