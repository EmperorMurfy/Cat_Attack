
import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {

	// (0,0) is the top left hand corner of the
	// panel.  x increases as you move to the right, y increases as you move
	// down.
	private int x_coordinate;			
	private int y_coordinate;
	private Color color;
	

	// method: Default constructor - see packed constructors comments for a description of parameters.
	public Shape(){
		this(0, 0, Color.red);
	}

	// method: Shapes's packed constructor
	// description: Initialize a new Shape object.
	// parameters: 
	//			   x_coordinate - the initial x-coordinate for Character.
	//			   y_coordinate - the initial y-coordinate for Character.
	public Shape(int x_coordinate, int y_coordinate, Color color){
		this.x_coordinate = x_coordinate;						// Initial coordinates for the Shape.
		this.y_coordinate = y_coordinate; 
		this.setColor(color);
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
	
	public void setX(int x_coordinate) {
		this.x_coordinate = x_coordinate;
	}
	
	public void setY(int y_coordinate) {
		this.y_coordinate = y_coordinate;
	}


	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	// method: move
	// description: This method should modify the Shapes's x or y (or perhaps both) coordinates.  When the 
	//				graphics panel is repainted the Shape will then be drawn in it's new location.
	public abstract void move();

	// override this function so that each shape will return a different value when clicked.
	public abstract int clicked();

	// method: draw
	// description: This method is used to draw the image onto the GraphicsPanel.
	// parameters: Graphics g - this object draw's the image.
	//			   Component c - this is the component that the image will be drawn onto.
	public abstract void draw(Graphics g);

	// method: contains
	// description: This method should return true if a point with coordinates x,y is inside of the shape
	// return: boolean - true if the point is inside the shape, false otherwise.
	public abstract boolean contains(int x, int y);
}
