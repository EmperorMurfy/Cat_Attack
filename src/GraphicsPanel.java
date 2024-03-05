// Graphics Panel


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.net.URL;
import javax.swing.ImageIcon;


public class GraphicsPanel extends JPanel implements KeyListener{
	
	
	private Timer t;

	// method: GraphicsPanel Constructor
	// description: This 'method' runs when a new instance of this class in instantiated.  It sets default values  
	// that are necessary to run this project.  You do not need to edit this method.
	public GraphicsPanel(){
		setPreferredSize(new Dimension(2560, 1600));
        this.setFocusable(true);			// for keylistener
		this.addKeyListener(this);
			
	}
	
	public void clock(){
		
		this.repaint();
	}
	
	// method: paintComponent
	// description: This method is called when the Panel is painted.  It contains code that draws shapes onto the panel.
	// parameters: Graphics g - this object is used to draw shapes onto the JPanel.
	// return: void
	public void paintComponent(Graphics g){

		Graphics2D g2 = (Graphics2D) g;
		
		// background
		g2.setColor(Color.gray);
		g2.fillRect(0, 0, 500, 600);
		ImageIcon image;


        ClassLoader cldr = this.getClass().getClassLoader();

        String imagePath = ""; // whatever picture you use, needs to be in the folder

        URL imageURL = cldr.getResource(imagePath);

        image = new ImageIcon(imageURL);


        image.paintIcon(this, g, 0, 0); // draws the image at the x 0 and y 0

    	
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
				
		
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
