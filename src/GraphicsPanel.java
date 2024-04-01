// GraphicsPanel.java


// Written by: Cat-Hadouken Developers
// Modified Date: March 12, 2024
// Main Class for Cat Hadoukens

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener{

	private Timer timer;					// The timer is used to move objects at a consistent time interval.

	//private boolean loss = false;

	private Background dollHouse; // dollHouse background object
	private Item dollHouseGround; // dollHouse 'ground' -  allows sprite to be placed in between the background & item
	private Background menuBackground; //kittenbrawlstarz background object for game menu 
	private Menu menu;
	private Item playButton;

	private Sprite sprite;
	private Sprite p2;

	private int attack1Count=0;
	private int attack2Count=0;

	private Item p1Attack;
	private Item p2Attack;
	private boolean p1Block = false;
	private boolean p2Block= false;

	private int blockCount1=0;
	private int blockCount2=0;

	private int wait1=0;
	private int wait2=0;


	private static playMusic player; // initiates player from playMusic class, to be changed later (testing purposes) 
	// create a Sprite object
	//	private Item item;						// This declares an Item object. You can make a Item display
	// pretty much any image that you would like by passing it
	// the path for the image.
	//	private ArrayList<Item> items;
	//	private int boxCounter;

	private enum STATE{
		MENU,
		GAME
	};
	private STATE State = STATE.MENU;

	public GraphicsPanel(){
		// main background & floor asset, order from back to front | dollHouse, sprite('s), dollHouseGround

		dollHouse = new Background("background/dollHouse.jpg");
		dollHouseGround = new Item(0, 0, "background/dollHouseFloor.png", 2);
		menuBackground = new Background("background/menuBackground.png");
		menu = new Menu();
		playButton = new Item(500,625, "background/playbutton.png", 10);
		this.addMouseListener((MouseListener) this);


		//	item = new Item(500, 200, "images/objects/Bush4.png", 1);  
		// The Item constructor has 4 parameters - the x coordinate, y coordinate
		// the path for the image, and the scale. The scale is used to make the
		// image smaller, so the bigger the scale, the smaller the image will be.
		//	items = new ArrayList<Item>();

		sprite = new Sprite(50, 550);
		p2 = new Sprite(900,550);
		// The Sprite constuctor has two parameter - - the x coordinate and y coordinate


		setPreferredSize(new Dimension(dollHouse.getImage().getIconWidth(),
				dollHouse.getImage().getIconHeight()));  
		// This line of code sets the dimension of the panel equal to the dimensions
		// of the background image.

		timer = new Timer(5, new ClockListener(this));   // This object will call the ClockListener's
		// action performed method every 5 milliseconds once the 
		// timer is started. You can change how frequently this
		// method is called by changing the first parameter.

		//	boxCounter = 0;
		timer.start();
		this.setFocusable(true);					     // for keylistener
		this.addKeyListener(this);
	}


	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//   			first rendered.  It can also be called by this.repaint(). You'll want to draw each of your objects.
	//				This is the only place that you can draw objects.
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;

		dollHouse.draw(this, g);

		//	item.draw(g2, this);
		sprite.draw(g2, this);
		p2.draw(g2, this);

		if(p2Attack !=null) {
			p2Attack.draw(g2, this);}
		if(p1Attack !=null) {
			p1Attack.draw(g2, this);}

		g2.setColor(Color.RED);
		Rectangle r = sprite.getBounds();
		g2.draw(r);
		Rectangle x = p2.getBounds();
		g2.draw(x);

		dollHouseGround.draw(g2, this); // should draw it in the correct order (have not checked)

		/*for(int i = 0; i < items.size(); i++) {
			items.get(i).draw(g2, this);}*/

		if(sprite.isDead) {
			g2.drawString("P2 Won", 450, 50);
		}
		if(p2.isDead) { 
			g2.drawString("P1 Won", 450, 50);
		}
		if(State == STATE.GAME) 
			dollHouse.draw(this,g);
		sprite.draw(g2, this);
		p2.draw(g2, this);

		if(p2Attack !=null) {
			p2Attack.draw(g2, this);}
		if(p1Attack !=null) {
			p1Attack.draw(g2, this);}

		g2.setColor(Color.RED);
		r = sprite.getBounds();
		g2.draw(r);
		x = p2.getBounds();
		g2.draw(x);

		dollHouseGround.draw(g2, this); // should draw it in the correct order (have not checked)

		/*for(int i = 0; i < items.size(); i++) {
			items.get(i).draw(g2, this);}*/

		if(sprite.isDead) {
			g2.drawString("P2 Won", 450, 50);
		}
		if(p2.isDead) { 
			g2.drawString("P1 Won", 450, 50);
		}
		else if(State == STATE.MENU) {
			menuBackground.draw(this, g);
			playButton.draw(g2, this);

		}

	}

	// method:clock
	// description: This method is called by the clocklistener every 5 milliseconds.  You should update the coordinates
	//				of one of your characters in this method so that it moves as time changes.  After you update the
	//				coordinates you should repaint the panel. 
	public void clock(){
		// You can move any of your objects by calling their move methods.
		sprite.move(this);
		p2.move(this);


		//	boxCounter++;
		//	if(boxCounter % 200 == 0)
		//		items.add(new Item(background1.getImage().getIconWidth() -100, 
		//				(int)(Math.random() * background1.getImage().getIconHeight()), "images/objects/Bush4.png", 1));

		// You can also check to see if two objects intersect like this. In this case if the sprite collides with the
		// item, the item will get smaller. 
		/*	if(sprite.collision(item) && sprite.getY() < item.getY()) {
			System.out.println("stop");
			sprite.stop_Vertical();
		} */



		/*	for(int i = 0; i < items.size(); i++)
			items.get(i).move(this);

		for(Item c: items) {
			if(sprite.collision(c)) {
				sprite.die();
			}
		}

		for(int i = items.size()-1;i>=0;i--) {
			if(items.get(i).getX()<10) {
				items.remove(i);
			}
		} 

		if(sprite.isDead) {
			for(int i = items.size()-1;i>=0;i--) {
				items.remove(i);
			}
		} */
		if(!p1Block) {

			if(p2Attack!=null&&sprite.collision(p2Attack)) {
				sprite.setHealth(sprite.getHealth()-1);
			}
		}
		else if(p2Attack!=null) {
			System.out.println("damge block");
		}
		if(!p2Block) {
			if(p1Attack!=null&&p2.collision(p1Attack)) {
				p2.setHealth(p2.getHealth()-1);
			}

		}
		else if(p1Attack!=null) {
			System.out.println("damge block");
		}
		if(sprite.getHealth()<=0) {
			sprite.die();
		}
		if(p2.getHealth()<=0) {
			p2.die();
		}

		this.repaint();

		if(attack2Count==10) {
			p2Attack = null;
			attack2Count=0;
		}
		else {
			attack2Count++;
		}
		if(attack1Count==10) {
			p1Attack = null;
			attack1Count=0;
		}
		else {
			attack1Count++;
		}

		if(wait1!=0) {
			wait1--;
		}
		if(wait2!=0) {
			wait2--;
		}
		if(p1Block) {
			blockCount1++;
			if(blockCount1==100) {
				p1Block=false;
				wait1=500;
				System.out.println("block off");
			}
		}
		if(p2Block) {
			blockCount2++;
			if(blockCount2==100) {
				p2Block=false;
				wait2=500;
				System.out.println("block off");
			}
		}




	}

	// method: keyPressed()
	// description: This method is called when a key is pressed. You can determine which key is pressed using the 
	//				KeyEvent object.  For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if
	//				the left key was pressed.
	// parameters: KeyEvent e
	@Override
	public void keyPressed(KeyEvent e) {

		if(!sprite.isDead&&!p2.isDead) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				sprite.walkRight();
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)
				sprite.walkLeft();
			//	else if(e.getKeyCode() == KeyEvent.VK_SHIFT)
			//		sprite.run();
			else if(e.getKeyCode() == KeyEvent.VK_UP)
				sprite.jump();
			else if(e.getKeyCode() == KeyEvent.VK_D)
				p2.walkRight();
			else if(e.getKeyCode() == KeyEvent.VK_A)
				p2.walkLeft();
			else if(e.getKeyCode() == KeyEvent.VK_W)
				p2.jump();
			else if(e.getKeyCode()== KeyEvent.VK_S)
				p2Attack = new Item(p2.x_coordinate+20, p2.y_coordinate, "images/objects/signArrow.png", 1);
			else if(e.getKeyCode()== KeyEvent.VK_DOWN)
				p1Attack = new Item(sprite.x_coordinate+240, sprite.y_coordinate, "images/objects/signArrow.png", 1);
			else if(e.getKeyCode()==KeyEvent.VK_SHIFT&&wait1==0) {
				p1Block=true;
				System.out.println("block on");
			}
			else if(e.getKeyCode()==KeyEvent.VK_TAB&&wait2==0) {
				p2Block=true;
				System.out.println("block on");
			}
			/*	else if(e.getKeyCode() == KeyEvent.VK_D) {
			playSound("src/sounds/bump.WAV");
			sprite.die();	
		} */

			// music player TEST code, to be deleted later 
			else if (e.getKeyCode() == KeyEvent.VK_R) {
				player = new playMusic("src/sounds/loop.wav"); // TEST OF THE CLASS, NOT FINALIZED LOCATION
				player.run(); //initiates player & begins playing at detection of key R
			}
			else if (e.getKeyCode() == KeyEvent.VK_E) {
				player.close();
			}

			else if(State== STATE.GAME) {

			}
		}			
	}


	// This function will play the sound "fileName".
	public static void playSound(String fileName) { // behaves similarly to playMusic, but plays once and is a local method
		try {
			File url = new File(fileName);
			Clip clip = AudioSystem.getClip();

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.start();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// method: keyTyped()
	// description: This method is called when a key is pressed and released. It basically combines the keyPressed and
	//              keyReleased functions.  You can determine which key is typed using the KeyEvent object.  
	//				For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if the left key was typed.
	//				You probably don't want to do much in this method, but instead want to implement the keyPresses and keyReleased methods.
	// parameters: KeyEvent e
	@Override
	public void keyTyped(KeyEvent e) {


	}

	// method: keyReleased()
	// description: This method is called when a key is released. You can determine which key is released using the 
	//				KeyEvent object.  For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if
	//				the left key was pressed.
	// parameters: KeyEvent e
	@Override
	public void keyReleased(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT)
			sprite.idle();
		else if(e.getKeyCode() ==  KeyEvent.VK_SPACE)
			sprite.slowDown();

		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A)
			p2.idle();
		else if(e.getKeyCode() ==  KeyEvent.VK_SPACE)
			p2.slowDown(); 

		if(e.getKeyCode()==KeyEvent.VK_SHIFT) {
			p1Block=false;
			System.out.println("block off");
		}
		if(e.getKeyCode()==KeyEvent.VK_TAB) {
			p1Block=false;
			System.out.println("block off");
		}

	}

	public void mouseClicked(MouseEvent e) {
		if(playButton.containsPoint(e.getX(),e.getY())) {
			System.out.println("Play Button Clicked");
			State = STATE.GAME;

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}