import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Square extends Shape{

	private int height;					
	
	// method: Default constructor - see packed constructors comments for a description of parameters.
	public Square(){
		this(0, 0, Color.red, 10);
	}

	// method: Shapes's packed constructor
	// description: Initialize a new Shape object.
	// parameters: 
	//			   x_coordinate - the initial x-coordinate for Character.
	//			   y_coordinate - the initial y-coordinate for Character.
	public Square(int x_coordinate, int y_coordinate, Color color, int height){
		super(x_coordinate, y_coordinate, color); 						// Initial coordinates for the Shape.
		this.height = height;  
	}
	

	// method: contains
	// description: This method should return true if a point with coordinates x,y is inside of the shape
	// return: boolean - true if the point is inside the shape, false otherwise.
	public boolean contains(int x, int y){
		return new Rectangle(getX(), getY(), height, height).contains(new Point(x, y));
	}

	// method: move
	// description: This method should modify the Shapes's x or y (or perhaps both) coordinates.  When the 
	//				graphics panel is repainted the Shape will then be drawn in it's new location.
	public void move() {
		setX(getX() + 2);
	}

	// override this function so that each shape will return a different value when clicked.
	// method: draw
	// description: This method is used to draw the image onto the GraphicsPanel.
	// parameters: Graphics g - this object draw's the image.
	//			   Component c - this is the component that the image will be drawn onto.
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.fillRect(getX(), getY(), height, height);
	}

	@Override
	public int clicked() {
		// TODO Auto-generated method stub
		return 0;
	}

		// TODO Auto-generated method stub
	
}