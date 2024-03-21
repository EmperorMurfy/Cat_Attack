// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
//54-61, 207-235, 245-273 ,341-398 (replace previus keybinds), 405-409
public class GraphicsPanel extends JPanel implements KeyListener{

	private Timer timer;					// The timer is used to move objects at a consistent time interval.

	//private boolean loss = false;

	private Background background1;			// The background object will display a picture in the background.
	private Background background2;			// There has to be two background objects for scrolling.

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
	
	private ArrayList<Character> p1Combo = new ArrayList<>();
	private ArrayList<Character> p2Combo = new ArrayList<>();
	
	private String player1Combo ="";
	private String player2Combo ="";
	
	private boolean play1ComboP =true;
	private boolean play2ComboP =true;
	
	
	// create a Sprite object
	//	private Item item;						// This declares an Item object. You can make a Item display
	// pretty much any image that you would like by passing it
	// the path for the image.
	//	private ArrayList<Item> items;
	//	private int boxCounter;

	public GraphicsPanel(){
		background1 = new Background();	// You can set the background variable equal to an instance of any of  
		background2 = new Background(background1.getImage().getIconWidth(),"background/dollHouse.jpg");						

		//	item = new Item(500, 200, "images/objects/Bush4.png", 1);  
		// The Item constructor has 4 parameters - the x coordinate, y coordinate
		// the path for the image, and the scale. The scale is used to make the
		// image smaller, so the bigger the scale, the smaller the image will be.
		//	items = new ArrayList<Item>();

		sprite = new Sprite(1000, 550,100,1,1);
		p2 = new Sprite(50,550,100,1,1);
		// The Sprite constuctor has two parameter - - the x coordinate and y coordinate

		setPreferredSize(new Dimension(background1.getImage().getIconWidth(),
				background2.getImage().getIconHeight()));  
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

		background1.draw(this, g);
		background2.draw(this, g);

		//	item.draw(g2, this);
		sprite.draw(g2, this);
		p2.draw(g2, this);
		
		g2.drawString("P2",150,280);
		g2.drawString("P1",950,280);
		
		g2.drawString("P2",p2.x_coordinate+200,p2.y_coordinate-20);
		g2.drawString("P1",sprite.x_coordinate+200,sprite.y_coordinate-20);
		
		g2.setColor(Color.RED);
		g2.fillRect(100, 300,(int)p2.getHealth()*3,50);
		g2.fillRect(900, 300,(int)sprite.getHealth()*3,50);

		if(p2Attack !=null) {
			p2Attack.draw(g2, this);}
		if(p1Attack !=null) {
			p1Attack.draw(g2, this);}

		if(p1Block) {
			g2.setColor(Color.BLUE);
		}
		else g2.setColor(Color.RED);
		Rectangle r = sprite.getBounds();
		g2.draw(r);
		if(p2Block) {
			g2.setColor(Color.BLUE);}
		else g2.setColor(Color.RED);
		Rectangle x = p2.getBounds();
		g2.draw(x);

		/*for(int i = 0; i < items.size(); i++) {
			items.get(i).draw(g2, this);}*/

		if(sprite.isDead) {
			g2.setColor(Color.BLACK);
			g2.drawString("P2 Won", 450, 50);
		}
		if(p2.isDead) {
			g2.setColor(Color.BLACK);
			g2.drawString("P1 Won", 450, 50);
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
				if(p2Combo.size()>=4) {
					for(int i =p2Combo.size()-4;i<p2Combo.size();i++) {
						player2Combo += p2Combo.get(i);
					}
					if(play2ComboP&&player2Combo.equals("wwws")) {
						sprite.health=(int)sprite.health/2;
						play2ComboP =false;
					}
					if(play2ComboP&&player2Combo.equals("ddds")) {
						sprite.x_coordinate+=200;
						play2ComboP =false;
					}
					if(play2ComboP&&player2Combo.equals("aaas")) {
						sprite.x_coordinate-=200;
						play2ComboP =false;
					}
					if(play2ComboP&&player2Combo.equals("dsds")) {
						if(sprite.health<50)
							sprite.health=0;
						play2ComboP =false;
					}
					if(play2ComboP&&player2Combo.equals("asas")) {
						if(sprite.health<50)
						sprite.health=0;
						play2ComboP =false;
					}
				}
				player2Combo="";
				if(play2ComboP) {
				sprite.setHealth(sprite.getHealth()-p2.damage);
				System.out.println(sprite.getHealth()+"p1");}
			}
		}
		else if(p2Attack!=null) {
			System.out.println("damge blomk");
		}
		if(!p2Block) {
			if(p1Attack!=null&&p2.collision(p1Attack)) {
				if(p1Combo.size()>=4) {
					for(int i =p1Combo.size()-4;i<p1Combo.size();i++) {
						player1Combo += p1Combo.get(i);
					}
					if(play1ComboP&&player1Combo.equals("wwws")) {
						p2.health=(int)p2.health/2;
						play1ComboP =false;
					}
					if(play1ComboP&&player1Combo.equals("aaas")) {
						p2.x_coordinate-=200;
						play1ComboP =false;
					}
					if(play1ComboP&&player1Combo.equals("ddds")) {
						p2.x_coordinate+=200;
						play1ComboP =false;
					}
					if(play1ComboP&&player1Combo.equals("asas")) {
						if(p2.health<50)
						p2.health=0;
						play1ComboP =false;
					}
					if(play1ComboP&&player1Combo.equals("dsds")) {
						if(p2.health<50)
						p2.health=0;
						play1ComboP =false;
					}
				}
				player1Combo="";
				if(play1ComboP) {
				p2.setHealth(p2.getHealth()-sprite.damage);
				System.out.println(p2.getHealth()+"p2");}
			}

		}
		else if(p1Attack!=null) {
			System.out.println("damge blomk");
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
			if(blockCount1==300) {
				p1Block=false;
				wait1=500;
				blockCount1=0;
			}
		}
		if(p2Block) {
			blockCount2++;
			if(blockCount2==300) {
				p2Block=false;
				wait2=500;
				blockCount2=0;
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
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				sprite.walkRight();
				p1Combo.add('d');}
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				sprite.walkLeft();
				p1Combo.add('a');}
			//	else if(e.getKeyCode() == KeyEvent.VK_SHIFT)
			//		sprite.run();
			else if(e.getKeyCode() == KeyEvent.VK_UP) {
				sprite.jump();
				p1Combo.add('w');}
			else if(e.getKeyCode() == KeyEvent.VK_D) {
				p2.walkRight();
				p2Combo.add('d');}
			else if(e.getKeyCode() == KeyEvent.VK_A) {
				p2.walkLeft();
				p2Combo.add('a');}
			else if(e.getKeyCode() == KeyEvent.VK_W) {
				p2.jump();
				p2Combo.add('w');}
			else if(e.getKeyCode()== KeyEvent.VK_S) {
				if(p2.x_direction<0) {
					p2Attack = new Item(p2.x_coordinate, p2.y_coordinate, "images/objects/signArrow.png", 1);}
				else p2Attack = new Item(p2.x_coordinate+250, p2.y_coordinate, "images/objects/signArrow.png", 1);
				p2Combo.add('s');
				play2ComboP =true;}
			else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
				if(sprite.x_direction<0) {
					p1Attack = new Item(sprite.x_coordinate, sprite.y_coordinate, "images/objects/signArrow.png", 1);}
				else p1Attack = new Item(sprite.x_coordinate+250, sprite.y_coordinate, "images/objects/signArrow.png", 1);
				p1Combo.add('s');
				play1ComboP =true;}
			else if(e.getKeyCode()==KeyEvent.VK_SHIFT&&wait1==0) {
				p1Block=true;
				System.out.println("block on");
			}
			else if(e.getKeyCode()==KeyEvent.VK_Q&&wait2==0) {
				p2Block=true;
				System.out.println("block on");
			}
			else if(e.getKeyCode()==KeyEvent.VK_1) {
				p2 = new Sprite(50,550,50,2,2);
			}
			else if(e.getKeyCode()==KeyEvent.VK_2) {
				p2 = new Sprite(50,550,200,.5,1);
			}
			else if(e.getKeyCode()==KeyEvent.VK_3) {
				p2 = new Sprite(50,550,100,1,1);
			}
			else if(e.getKeyCode()==KeyEvent.VK_8) {
				sprite = new Sprite(1000, 550,50,2,2);
			}
			else if(e.getKeyCode()==KeyEvent.VK_9) {
				sprite = new Sprite(1000, 550,200,.5,1);
			}
			else if(e.getKeyCode()==KeyEvent.VK_0) {
				sprite = new Sprite(1000, 550,100,1,1);
			}
			/*	else if(e.getKeyCode() == KeyEvent.VK_D) {
			playSound("src/sounds/bump.WAV");
			sprite.die();	
		} */
		}
		else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
			sprite = new Sprite(1000, 550,100,1,1);
			p2 = new Sprite(50,550,100,1,1);
			
		}
	}

	// This function will play the sound "fileName".
	public static void playSound(String fileName) {
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
		

		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A)
			p2.idle();
		 

	/*	if(e.getKeyCode()==KeyEvent.VK_SHIFT) {
			p1Block=false;
			System.out.println("block off");
		}
		if(e.getKeyCode()==KeyEvent.VK_Q) {
			p2Block=false;
			System.out.println("block off");
		} */

	}

}
