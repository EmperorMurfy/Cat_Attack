// Class: Background
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This is an abstract class that provides partial implementation for a Background. You can't
// 				create an instance of this class.
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class Background {

	protected ImageIcon image;
	protected int scale;
	protected int x;
	private String map;
	
	public Background(int x, String map) {
		ClassLoader cldr = this.getClass().getClassLoader();	// These five lines of code load the background picture.
		String imagePath =  map;	// Change this line if you want to use a different 
		URL imageURL = cldr.getResource(imagePath);				// background image.  The image should be saved in the
		scale = 2;
		
		image = new ImageIcon(imageURL);
		
		Image scaled = image.getImage().getScaledInstance(image.getIconWidth() / scale, 
				image.getIconHeight() / scale, Image.SCALE_SMOOTH);
		
		image = new ImageIcon(scaled);
		
		this.x = x;
	}
	
	public Background() {
		this(0,"background/dollHouse.jpg");
	}
	
	public void draw(Component c, Graphics g) {
		image.paintIcon(c, g, x, 0);
	}
	
	public int getHeight() {
		return image.getIconHeight();
	}
	
	public int getWidth() {
		return image.getIconWidth();
	}
	
	public int getX() {
		return x;
	}
	
	public void move(int spriteX, int direction) {
		if(direction > 1 && getX() <= image.getIconWidth() && spriteX >= image.getIconWidth() * 2 / 3)
			x--;
		
		if(direction > 1 && getX() < -image.getIconWidth())
			reset();
	}
	
	public void reset() {
		x = image.getIconWidth();
	}
	
	public ImageIcon getImage() {
		return image;
	}
	
}
