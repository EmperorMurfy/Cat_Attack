// Class: Item 
// Written by: Cat Attack Developers
// Date: April 2, 2024
// Description: This class implements an Item.  This Item will be drawn onto a graphics panel. 

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import javax.swing.ImageIcon;

public class Item{

	// movement variables
	protected int x_coordinate;			
	protected int y_coordinate;			
	protected int x_direction;			
		// -2 walking left, -1 idle facing left
		// 1 idle facing right, 2 walking right

	protected int y_direction;			// 0 : not moving, - 1 : up, 1 : down

	protected ImageIcon image;			

	// method: Default constructor - 
	public Item(){
		this(200, 300, "images/forest/objects/.png", 2);
	}

	// method: Item constructor
	public Item(int x_coordinate, int y_coordinate, String imageString){
		this(x_coordinate, y_coordinate, imageString, 2);
	}

	// method: Item constructor
	public Item(int x_coordinate, int y_coordinate, String imageString, int imageScale){

		this.x_coordinate = x_coordinate;						// Initial coordinates for the Item.
		this.y_coordinate = y_coordinate; 

		x_direction = -2;
		y_direction = 0;

		ClassLoader cldr = this.getClass().getClassLoader();	// These lines of code load the picture.
		String imagePath = imageString;							
		URL imageURL = cldr.getResource(imagePath);				

		image = new ImageIcon(imageURL);

		Image scaled = image.getImage().getScaledInstance(image.getIconWidth() / imageScale, 
				image.getIconHeight() / imageScale, Image.SCALE_SMOOTH);

		image = new ImageIcon(scaled);	
	}

	// method: collision
	public void changeScale(int imageScale) {

		if(image.getIconWidth() > imageScale && image.getIconHeight() > imageScale) {
			Image scaled = image.getImage().getScaledInstance(image.getIconWidth() / imageScale, 
					image.getIconHeight() / imageScale, Image.SCALE_SMOOTH);

			image = new ImageIcon(scaled);
		}
	}

	// method: getBounds
	public Rectangle getBounds(){
		return new Rectangle(x_coordinate, y_coordinate, image.getIconWidth(), 
				image.getIconHeight());
	}

	// method: collision
	public boolean collision(Item otherItem) {
		return getBounds().intersects(otherItem.getBounds());
	}

	// method: getX
	// description:  This method will return the x-coordinate of the top left hand corner of the the image.
	// return: int - the x-coordinate of the top left hand corner of the the image.
	public int getX(){
		return x_coordinate;
	}

	// method: getY
	public int getY(){
		return y_coordinate;
	}


	// method: move		   
	public void move(Component c){
		// move to the right or left - speed will be positive
		if (((x_coordinate > 0 && x_direction == -2) || (x_coordinate < c.getWidth() && x_direction == 2 )))
			x_coordinate += (x_direction);
		// move up or down
		else if ((y_coordinate > 0 && y_direction == -1) || (y_coordinate < c.getHeight() && y_direction == 1 ))
			y_coordinate += (y_direction);
	}
	
	public void moveRight() {
		x_direction = 2;
	}
	public void moveLeft() {
		x_direction = -2;
	}

	public void stop() {
		x_direction = (x_direction < 0) ? -1 : 1;
	}

	public void stop_Vertical() {
		y_direction = 0;
	}

	public void moveUp() {
		y_direction = -1;
	}

	public void moveDown() {
		y_direction = 1;
	}

	public void moveRandom() {
		x_coordinate = (int)(Math.random() * 1); //width of panel)
		y_coordinate = (int)(Math.random() * 1); //height of panel)
	}
	// method: draw
	public void draw(Graphics g, Component c) {
		image.paintIcon(c, g, x_coordinate, y_coordinate);

	}

	//method: containsPoint
	//description: this method is used to identify the bounds of the x and y coordinates of the button in the Graphics Panel.
	//parameters: int x and int y
	public boolean containsPoint(int x, int y) {
		// TODO Auto-generated method stub
		return getBounds().contains(x,y);
	}

}
