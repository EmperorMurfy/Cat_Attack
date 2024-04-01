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

public class GraphicsPanel extends JPanel implements KeyListener{

	private Timer timer;					// The timer is used to move objects at a consistent time interval.

	//private boolean loss = false;
	


	private Background dollHouse; // dollHouse background object
	private Item dollHouseGround; // dollHouse 'ground' -  allows sprite to be placed in between the background & item

	private Background victorySkinWalker; // victory screen - skinWalker
	private Background victoryKatze; // victory screen - Katze
	
	private playMusic player;

	private Sprite skinWalker;
	private Sprite katze; // p2 replaced as katze

	private int attack1loop = 1;
	private int attack2loop = 1;
	
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
	
	private int victoryMusicChecker = 0;
	
	
	// create a skinWalker object
	//	private Item item;						// This declares an Item object. You can make a Item display
	// pretty much any image that you would like by passing it
	// the path for the image.
	//	private ArrayList<Item> items;
	//	private int boxCounter;

	public GraphicsPanel(){
		// music
		player = new playMusic("src/sounds/loop.wav"); 
		player.run();
		
		// background info				
		dollHouse = new Background("background/dollHouse.jpg", 2);
		dollHouseGround = new Item(0, 0, "background/dollHouseFloor.png", 2);
		
		// victory screen info
		victorySkinWalker = new Background("background/victorySkinWalker.png", 2);
		victoryKatze = new Background("background/victoryKatze.png", 2);

		//	item = new Item(500, 200, "images/objects/Bush4.png", 1);  
		// The Item constructor has 4 parameters - the x coordinate, y coordinate
		// the path for the image, and the scale. The scale is used to make the
		// image smaller, so the bigger the scale, the smaller the image will be.
		//	items = new ArrayList<Item>();

		skinWalker = new Sprite("sprite/skinwalker/", 1000, 368,100,1,1); // name = new Sprite(x value, y value, health, speed, attack);
		katze = new Sprite("sprite/katze/", 50,368,100,1,1); // change file path


		setPreferredSize(new Dimension(dollHouse.getImage().getIconWidth(),
				dollHouse.getImage().getIconHeight()));  // dimensions of game
		

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
		//background1.draw(this, g);
		//background2.draw(this, g);

		//	item.draw(g2, this);
		
		// draw characters
		skinWalker.draw(g2, this);
		katze.draw(g2, this);
	
		g2.drawString("P2",150,280);
		g2.drawString("P1",950,280);
		
		g2.drawString("P2",katze.x_coordinate+200,katze.y_coordinate-20); // player 1 and player two identifier
		g2.drawString("P1",skinWalker.x_coordinate+200,skinWalker.y_coordinate-20);
		
		// health bar
		g2.setColor(Color.RED);
		g2.fillRect(100, 300,(int)katze.getHealth()*3,50);
		g2.fillRect(900, 300,(int)skinWalker.getHealth()*3,50);

		// attack conditions
		if(p2Attack !=null) {
			p2Attack.draw(g2, this);}
		if(p1Attack !=null) {
			p1Attack.draw(g2, this);}

		if(p1Block) {
			g2.setColor(Color.BLUE);
		}
		else g2.setColor(Color.RED);
		Rectangle r = skinWalker.getBounds();
		g2.draw(r);
		if(p2Block) {
			g2.setColor(Color.BLUE);}
		else g2.setColor(Color.RED);
		Rectangle x = katze.getBounds();
		g2.draw(x);

		/*for(int i = 0; i < items.size(); i++) {
			items.get(i).draw(g2, this);}*/
		dollHouseGround.draw(g2, this);
		
		
		if(skinWalker.isDead) {
			g2.setColor(Color.BLACK);
			g2.drawString("Katze has Won!", 450, 50);
			
			
			victoryKatze.draw(this, g2);
			player.close();
			if (victoryMusicChecker == 45) { // victory sound play delay lines up with player reaction speed
				playSound("src/sounds/victoryKatze.wav");
			}
			victoryMusicChecker++;

		}
		if(katze.isDead) {
			g2.setColor(Color.BLACK);
			g2.drawString("Skinwalker has Won!", 450, 50);
			victorySkinWalker.draw(this, g2);
			player.close();
			if (victoryMusicChecker == 45) {
				playSound("src/sounds/victoryKatze.wav");
			}
			victoryMusicChecker++;
		}
		
	



	}

	// method:clock
	// description: This method is called by the clocklistener every 5 milliseconds.  You should update the coordinates
	//				of one of your characters in this method so that it moves as time changes.  After you update the
	//				coordinates you should repaint the panel. 
	public void clock(){
		// You can move any of your objects by calling their move methods.
		skinWalker.move(this);
		katze.move(this);


		//	boxCounter++;
		//	if(boxCounter % 200 == 0)
		//		items.add(new Item(background1.getImage().getIconWidth() -100, 
		//				(int)(Math.random() * background1.getImage().getIconHeight()), "images/objects/Bush4.png", 1));

		// You can also check to see if two objects intersect like this. In this case if the skinWalker collides with the
		// item, the item will get smaller. 
		/*	if(skinWalker.collision(item) && skinWalker.getY() < item.getY()) {
			System.out.println("stop");
			skinWalker.stop_Vertical();
		} */



		/*	for(int i = 0; i < items.size(); i++)
			items.get(i).move(this);

		for(Item c: items) {
			if(skinWalker.collision(c)) {
				skinWalker.die();
			}
		}

		for(int i = items.size()-1;i>=0;i--) {
			if(items.get(i).getX()<10) {
				items.remove(i);
			}
		} 

		if(skinWalker.isDead) {
			for(int i = items.size()-1;i>=0;i--) {
				items.remove(i);
			}
		} */
		
	
		if(!p1Block) {
			if(p2Attack!=null&&skinWalker.collision(p2Attack)) {
				skinWalker.setHealth(skinWalker.getHealth()-katze.damage);
				skinWalker.damage();
				System.out.println(skinWalker.getHealth()+"p1");
				
				
			}
		}
		else if(p2Attack!=null) {
			System.out.println("damge blomk");
		}
		if(!p2Block) {
			if(p1Attack!=null&&katze.collision(p1Attack)) {
				katze.setHealth(katze.getHealth()-skinWalker.damage);
				katze.damage();
				System.out.println(katze.getHealth()+"p2");
				
			}

		}
		else if(p1Attack!=null) {
			System.out.println("damge blomk");
		}
		if(skinWalker.getHealth()<=0) {
			skinWalker.die();
		}
		if(katze.getHealth()<=0) {
			katze.die();
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
				skinWalker.shield();
				wait1=500;
				blockCount1=0;
			}
		}
		if(p2Block) {
			blockCount2++;
			if(blockCount2==300) {
				p2Block=false;
				katze.shield();
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
		if(!skinWalker.isDead&&!katze.isDead) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				skinWalker.walkRight();
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)
				skinWalker.walkLeft();
			//	else if(e.getKeyCode() == KeyEvent.VK_SHIFT)
			//		skinWalker.run();
			else if(e.getKeyCode() == KeyEvent.VK_UP)
				skinWalker.jump();
			else if(e.getKeyCode() == KeyEvent.VK_D)
				katze.walkRight();
			else if(e.getKeyCode() == KeyEvent.VK_A)
				katze.walkLeft();
			else if(e.getKeyCode() == KeyEvent.VK_W)
				katze.jump();
			else if(e.getKeyCode()== KeyEvent.VK_S) {// attack
				if(katze.x_direction<0) {
					p2Attack = new Item(katze.x_coordinate, katze.y_coordinate, "images/objects/signArrow.png", 1);
					} // images
				else { 
					p2Attack = new Item(katze.x_coordinate+350, katze.y_coordinate, "images/objects/signArrow.png", 1);
					} // images 
				katze.attack(); 
			}
	
			
			else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
				if(skinWalker.x_direction<0) {
					p1Attack = new Item(skinWalker.x_coordinate, skinWalker.y_coordinate, "images/objects/signArrow.png", 1);} // images
				else p1Attack = new Item(skinWalker.x_coordinate+450, skinWalker.y_coordinate, "images/objects/signArrow.png", 1); // images
				skinWalker.attack(); 
			}
			else if(e.getKeyCode()==KeyEvent.VK_SHIFT&&wait1==0) {
				p1Block=true;
				skinWalker.shield(); // shield doesn't come off visually
				System.out.println("block on");
			}
			else if(e.getKeyCode()==KeyEvent.VK_Q&&wait2==0) {
				p2Block=true;
				katze.shield(); 
				System.out.println("block on");
			}
			else if(e.getKeyCode()==KeyEvent.VK_1) {
				katze = new Sprite("sprite/skinwalker/", 50,368,50,2,3);
			}
			else if(e.getKeyCode()==KeyEvent.VK_2) {
				katze = new Sprite("sprite/skinwalker/", 50,368,150,.5,2);
			}
			else if(e.getKeyCode()==KeyEvent.VK_3) {
				katze = new Sprite("sprite/skinwalker/", 50,368,100,1,2);
			}
			else if(e.getKeyCode()==KeyEvent.VK_8) {
				skinWalker = new Sprite("sprite/skinwalker/", 1000,368,50,2,3);
			}
			else if(e.getKeyCode()==KeyEvent.VK_9) {
				skinWalker = new Sprite("sprite/skinwalker/", 1000,368,150,.5,2);
			}
			else if(e.getKeyCode()==KeyEvent.VK_0) {
				skinWalker = new Sprite("sprite/skinwalker/", 1000,368,100,1,2);
			}
			/*	else if(e.getKeyCode() == KeyEvent.VK_D) {
			playSound("src/sounds/bump.WAV");
			skinWalker.die();	
		} */
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

		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
			skinWalker.idle();
			if (katze.attackCounter == 1) {
				katze.attack();
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A) {
			katze.idle();
			if (skinWalker.attackCounter == 1) {
				skinWalker.attack();
			}
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_S)  
			katze.attack(); 
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			skinWalker.attack();
		}
			
		 
		
		

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
