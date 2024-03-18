// Class: Item
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class implements an Item.  This Item will be drawn onto a graphics panel. 
// 
// If you modify this class you should add comments that describe how you modified the class.  

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import javax.swing.ImageIcon;

public class Item{
	
	// movement variables
	protected int x_coordinate;			// These ints will be used for drawing the png on the graphics panel.
	protected int y_coordinate;			// When the Item's move method is called you should update one or both
										// of these instance variables.  (0,0) is the top left hand corner of the
										// panel.  x increases as you move to the right, y increases as you move down.

	protected int x_direction;			// -2 walking left, -1 idle facing left
										// 1 idle facing right, 2 walking right

	protected int y_direction;			// 0 : not moving, - 1 : up, 1 : down
 
	protected ImageIcon image;			// The ImageIcon is what is actually drawn on the Panel

	// method: Default constructor - see packed constructors comments for a description of parameters.
	public Item(){
		this(200, 300, "images/forest/objects/Tree_2.png", 2);
	}

	// method: Item constructor
	// description: Initialize a new Item object.
	// parameters: x_coordinate - the initial x-coordinate for Character.
	//			   y_coordinate - the initial y-coordinate for Character.
	//             imageString - The image path for the image that you want to draw. Make sure that any images that
	//                           you would like to draw are in your project and that you give the full path relative to 
	//                           your project's src folder.
	public Item(int x_coordinate, int y_coordinate, String imageString){
		this(x_coordinate, y_coordinate, imageString, 2);
	}
	
	// method: Item constructor
	// description: Initialize a new Item object.
	// parameters: x_coordinate - the initial x-coordinate for Character.
	//			   y_coordinate - the initial y-coordinate for Character.
	//             imageString - The image path for the image that you want to draw. Make sure that any images that
	//                           you would like to draw are in your project and that you give the full path relative to 
	//                           your project's src folder.
	//             imageScale - imageScale is used to make the image bigger or smaller on the Panel. The bigger
	// 						    the imageScale, the smaller the image will be.
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
	// description: This method will return true if the Item collides with the parameter Item.
	// return: boolean - true if the two items intersect or collide with each other, otherwise false. 
	public void changeScale(int imageScale) {
		
		if(image.getIconWidth() > imageScale && image.getIconHeight() > imageScale) {
			Image scaled = image.getImage().getScaledInstance(image.getIconWidth() / imageScale, 
				image.getIconHeight() / imageScale, Image.SCALE_SMOOTH);
		
			image = new ImageIcon(scaled);
		}
	}
	
	// method: getBounds
	// description: This method will return the coordinates of a rectangle that would be drawn around the 
	// 				Item's png.  This rectangle can be used to check to see if the Item bumps into 
	//				another Item or Sprite on your panel. This method is called by the collision methods in Sprite 
	//              and Item. You probably won't call this method directly.
	// return: A Rectangle - This rectangle would be like drawing a rectangle around the Character's image.
	public Rectangle getBounds(){
		return new Rectangle(x_coordinate, y_coordinate, image.getIconWidth(), 
				image.getIconHeight());
	}

	// method: collision
	// description: This method will return true if the Item collides with the parameter Item.
	// return: boolean - true if the two items intersect or collide with each other, otherwise false. 
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
	// description:  This method will return the y-coordinate of the top left hand corner of the the image.
	// return: int - the y-coordinate of the top left hand corner of the the image.
	public int getY(){
		return y_coordinate;
	}

	
	// method: move
	// description: This method will modify the Item's x or y (or perhaps both) coordinates.  When the 
	//				graphics panel is repainted the Item will then be drawn in it's new location.
	// parameters: Component c - this is the Panel that the Item will be drawn on. You need this parameter so 
	//            that you can figure out the dimensions and not go off of the panel when it moves.		   
	public void move(Component c){
		// move to the right or left - speed will be positive
		if (((x_coordinate > 0 && x_direction == -2) || (x_coordinate < c.getWidth() && x_direction == 2 )))
			x_coordinate += (x_direction);
		// move up or down
		else if ((y_coordinate > 0 && y_direction == -1) || (y_coordinate < c.getHeight() && y_direction == 1 ))
			y_coordinate += (y_direction);
	}


	// methods that deal with horizontal movement. These functions don't actually move the Item, they set the direction.
	// actual movements will occur when the the object's move method is called.
	public void moveRight() {
		x_direction = 2;
	}
	public void moveLeft() {
		x_direction = -2;
	}

	public void stop() {
		x_direction = (x_direction < 0) ? -1 : 1;
	}

	// methods that deal with vertical movement. These functions don't actually move the Item, they set the direction.
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
	// description: This method is used to draw the image onto the GraphicsPanel.  You shouldn't need to 
	//				modify this method.
	// parameters: Graphics g - this object draw's the image.
	//			   Component c - this is the component that the image will be drawn onto.
	public void draw(Graphics g, Component c) {
		image.paintIcon(c, g, x_coordinate, y_coordinate);

	}

}
