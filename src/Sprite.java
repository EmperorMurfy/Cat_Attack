// Class: Sprite
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class implements an Item.  This Item will be drawn onto a graphics panel. 
// 
// If you modify this class you should add comments that describe when and how you modified the class.  

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Sprite {

	// movement variables
	protected int x_coordinate;			// These ints will be used for drawing the png on the graphics panel.
	protected int y_coordinate;			// When the Sprite's move method is called you should update one or both
	// of these instance variables.  (0,0) is the top left hand corner of the
	// panel.  x increases as you move to the right, y increases as you move down.

	protected int x_direction;			// -5 running left, -2 walking left, -1 idle facing left
	// 1 idle facing right, 2 walking right, 5 running right

	protected int y_direction;			// 0 : not moving
	// - 1 : up
	// 1 : down

	protected int jumpCounter;			// jumping animation takes several frames. This counter is used to keep track
	// of this process. If the Sprite isn't jumping this should be set to -1.
	
	protected int shieldCounter;
	protected int attackCounter;
	protected int damageCounter;
	
	
	protected boolean isDead;			// as the name implies, this boolean is set to true of the character dies :(
	protected ImageResource imageResource; // This object holds all of the images that will be used to draw the Sprite.
	
	protected int health;
	protected double speed;
	protected int damage;
	
	protected String filePath;
	

	// method: Sprite's packed constructor
	// description: Initialize a new Sprite object.
	// parameters: x_coordinate - the initial x-coordinate for Sprite.
	//			   y_coordinate - the initial y-coordinate for Sprite.
	public Sprite(String filePath, int x_coordinate, int y_coordinate,int health, double speed, int damage){
		this.filePath = filePath; // added filePath
		this.x_coordinate = x_coordinate;		// Initial coordinates for the Sprite.
		this.y_coordinate = y_coordinate; 
		
		this.damage=damage;
		this.speed=speed;
		this.health=health;

		x_direction = 1;						// Initial directions - 1 for x means that the Sprite is facing to
		// the right but no moving.
		y_direction = 0;						// 0 for y direction means it's not moving vertically.

		imageResource = new ImageResource(filePath, 2, 80); // imageResources: filePath is src/sprite.skinwalker but that would be sprite/skinwalker/, scale int
		jumpCounter = -1;
		shieldCounter = -1;
		attackCounter = -1;
		damageCounter = -1;

	}

	public boolean collision(Sprite otherSprite) {
		return getBounds().intersects(otherSprite.getBounds());

	}

	public boolean collision(Item item) {
		return getBounds().intersects(item.getBounds());
	}

	// method: getBounds
	// description: This method will return the coordinates of a rectangle that would be drawn around the 
	// 				Sprite's png.  This rectangle can be used to check to see if the Item bumps into 
	//				another Item or Sprite on your panel. This method is called by the collision methods in Sprite 
	//              and Item. You probably won't call this method directly.
	// return: A Rectangle - This rectangle would be like drawing a rectangle around the Character's image.
	public Rectangle getBounds(){
		if(x_direction < 0)
			return new Rectangle(x_coordinate + imageResource.getImageOffset(), y_coordinate, 
					imageResource.getImage().getIconWidth() - imageResource.getImageOffset(), 
					imageResource.getImage().getIconHeight());
		else
			return new Rectangle(x_coordinate + 2 * imageResource.getImageOffset() + 120, y_coordinate, // fixed bounds 
					imageResource.getImage().getIconWidth() - imageResource.getImageOffset(), 
					imageResource.getImage().getIconHeight());
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
	
	public void setHealth(int health) {
		this.health=health;
	}
	
	public int getHealth() {
		return health;
	}

	// method: move
	// description: This method should modify the Sprite's x or y (or perhaps both) coordinates.  When the 
	//				graphics panel is repainted the Sprite will then be drawn in it's new location.
	// parameters: int direction - This parameter should represent the direction that you want to move
	//			   the Sprite,.
	public void move(Component c){
		// move to the right or left - speed will be positive
		if(!isDead) {
		if (((x_coordinate > - (2*imageResource.getImageOffset()) && x_direction == -2 || x_direction == -5) ||
				(x_coordinate + imageResource.getImage().getIconWidth() + imageResource.getImageOffset() < c.getWidth() && (x_direction == 2 || x_direction == 5) ))) {
				x_coordinate += (x_direction)*speed;
			}
		
		// jump
		else if ((y_coordinate > 0 && y_direction == -1) || 
				(y_coordinate + imageResource.getImage().getIconWidth() < c.getHeight()+75 && y_direction == 1 ))
			y_coordinate += (y_direction);

		if(jumpCounter >= 0 && jumpCounter < 300) {
			jumpCounter+=5;
			y_coordinate-=5;
		}
		else if(jumpCounter >= 300 && jumpCounter < 600){
			jumpCounter+=4;
			y_coordinate+=4;
		}
		else {
			jumpCounter = -1;
		}

		imageResource.updateImage(x_direction + y_direction, jumpCounter >= 0, isDead, shieldCounter >= 0, attackCounter >=0, damageCounter >=0);
	}
	}

	// Methods that deal with horizontal movement. These functions don't actually move the Item, they set the direction.
	// actual movements will occur when the the object's move method is called.
	public void walkRight() {
		x_direction = 2;
	}
	public void walkLeft() {
		x_direction = -2;
	}
	public void run() {
		x_direction = (x_direction < 0) ? -5 : 5;
	}

	// Methods that deal with vertical movement. These functions don't actually move the Sprite, they set the direction.
	public void slowDown() {
		x_direction = (x_direction < 0) ? -2 : 2;
	}
	public void idle() {
		x_direction = (x_direction < 0) ? -1 : 1;
	}
	
	public void shield() {
		shieldCounter = shieldCounter * -1;
	}
	
	public void attack() {
		attackCounter = attackCounter * -1;
	}
	
	public void damage() {
		damageCounter = damageCounter * -1;
	}

	public void jump() {
		if(jumpCounter == -1) 
			jumpCounter = 0;
	}

	// As the method implies, calling this function makes the character die.
	public void die() {
		isDead = true;
	}
	// As the method implies, calling this function makes the character come back to life.
	public void resurrect() {
		isDead = false;
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

	// method: draw
	// description: This method is used to draw the image onto the GraphicsPanel.  You shouldn't need to 
	//				modify this method.
	// parameters: Graphics g - this object draw's the image.
	//			   Component c - this is the component that the image will be drawn onto.
	public void draw(Graphics g, Component c) {
		Graphics2D g2 = (Graphics2D)g;

		if(x_direction < 0)
			g2.drawImage(imageResource.getImage().getImage(), x_coordinate + imageResource.getImage().getIconWidth() + imageResource.getImageOffset()/2, 
					y_coordinate, -imageResource.getImage().getIconWidth(), imageResource.getImage().getIconHeight(), null);
		else
			g2.drawImage(imageResource.getImage().getImage(), x_coordinate + imageResource.getImage().getIconWidth(), 
					y_coordinate, imageResource.getImage().getIconWidth(), imageResource.getImage().getIconHeight(), null);
	}
}
