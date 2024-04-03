// Class: GraphicsPanel
// Written by: Cat Attack Developers
// Last Updated: Apr 2, 2024
// Description: Main class 

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
import java.net.URL;
import java.util.ArrayList;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener{

	private Timer timer;					// The timer is used to move objects at a consistent time interval.

	private Background dollHouse; // dollHouse background object
	private Item dollHouseGround; // dollHouse 'ground' -  allows sprite to be placed in between the background & item
	private Background menuBackground;
	private Background victorySkinWalker; // victory screen - skinWalker
	private Background victoryKatze; // victory screen - Katze
	private Menu menu;
	private Item playButton;
	private playMusic player;

	private Sprite skinWalker;
	private Sprite katze; // p2 replaced as katze

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
	
	private enum STATE{
		MENU,
		GAME
	};
	private STATE State = STATE.MENU;


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
		menuBackground = new Background("background/menuBackground.png", 2);
		menu = new Menu();
		playButton= new Item(500,625, "background/playbutton.png", 10);
		this.addMouseListener((MouseListener) this);
		// victory screen info
		victorySkinWalker = new Background("background/victorySkinWalker.png", 2);
		victoryKatze = new Background("background/victoryKatze.png", 2);

		//	item = new Item(500, 200, "images/objects/Bush4.png", 1);  
		// The Item constructor has 4 parameters - the x coordinate, y coordinate
		// the path for the image, and the scale. The scale is used to make the
		// image smaller, so the bigger the scale, the smaller the image will be.
		//	items = new ArrayList<Item>();

		skinWalker = new Sprite("sprite/skinwalker/", 1000, 368,100,1,1); // name = new Sprite(x value, y value, health, speed, attack);
		katze = new Sprite("sprite/katze/", 50,368,100,1,1);


		setPreferredSize(new Dimension(dollHouse.getImage().getIconWidth(),
				dollHouse.getImage().getIconHeight()));  // dimensions of game


		timer = new Timer(5, new ClockListener(this));   
		timer.start();
		this.setFocusable(true);					     // for keylistener
		this.addKeyListener(this);
	}

	// method: paintComponent
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		dollHouse.draw(this, g);

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
			p2Attack.draw(g2, this);
		}
		if(p1Attack !=null) {
			p1Attack.draw(g2, this);
		}

		if(p1Block) {
			g2.setColor(Color.BLUE);
		}
		else {
			g2.setColor(Color.RED);
			Rectangle r = skinWalker.getBounds();
			g2.draw(r);
		}
		if(p2Block) {
			g2.setColor(Color.BLUE);
		}
		else {
			g2.setColor(Color.RED);
			Rectangle x = katze.getBounds();
			g2.draw(x);
		}

		// background floor - DO NOT CHANGE THE ORDER OF THIS CODE
		dollHouseGround.draw(g2, this);


		// victory condition + graphics
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
		if(State == STATE.GAME) {
			dollHouse.draw(this, g);
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
		
			if(p2Attack != null) {
				p2Attack.draw(g2, this);
			}
			if(p1Attack != null) {
				p1Attack.draw(g2, this);
			}
				g2.setColor(Color.RED);
				Shape r = skinWalker.getBounds();
				g2.draw(r);
				Shape x = katze.getBounds();
				g2.draw(x);
				
				dollHouseGround.draw(g2, this);
		}	
		
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
		else if(State == STATE.MENU) {
			menuBackground.draw(this,g);
			playButton.draw(g2, this);
		}
		
		
	}

	// method:clock
	// note: this method is called by the clocklistener every 5 milliseconds.  
	public void clock(){
		// You can move any of your objects by calling their move methods.
		skinWalker.move(this);
		katze.move(this);


		// damage conditions + graphics
		if(!p1Block) {
			if(p2Attack!=null&&skinWalker.collision(p2Attack)) {
				skinWalker.setHealth(skinWalker.getHealth()-katze.damage);
				// 	skinWalker.damage();
				System.out.println(skinWalker.getHealth()+"p1");
			}
		}

		if(!p2Block) {
			if(p1Attack!=null&&katze.collision(p1Attack)) {
				katze.setHealth(katze.getHealth()-skinWalker.damage);
				// katze.damage(); - removed for visual error
				System.out.println(katze.getHealth()+"p2");
			}
		}

		//else if(p1Attack!=null) {
		//	System.out.println("damge blomk"); 
		//}


		// death conditions - checks health to determine death 
		if(skinWalker.getHealth()<=0) {
			skinWalker.die();
		}

		if(katze.getHealth()<=0) {
			katze.die();
		}

		this.repaint();


		// attack conditions
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


		// shield <-> block 
		if(p1Block) {
			blockCount1++;
			if(blockCount1==300) {
				p1Block=false;
				if (skinWalker.shieldCounter == 1) {
					skinWalker.shield();
				}
				wait1=500;
				blockCount1=0;
			}
		}

		if(p2Block) {
			blockCount2++;
			if(blockCount2==300) {
				p2Block=false;
				if (katze.shieldCounter == 1) {
					katze.shield();
				}
				wait2=500;
				blockCount2=0;
			}
		}
	}

	// method: keyPressed()
	@Override
	public void keyPressed(KeyEvent e) {
		if(!skinWalker.isDead&&!katze.isDead) {

			// skinWalker controls - player left
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				skinWalker.walkRight();
			}

			else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				skinWalker.walkLeft();

			}

			else if(e.getKeyCode() == KeyEvent.VK_UP) {
				skinWalker.jump();
				playSound("src/sounds/jump.wav");
			}

			else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
				if(skinWalker.x_direction<0) {
					p1Attack = new Item(skinWalker.x_coordinate, skinWalker.y_coordinate, "images/objects/invis.png", 1);} // images
				else p1Attack = new Item(skinWalker.x_coordinate+450, skinWalker.y_coordinate, "images/objects/invis.png", 1); // images
				skinWalker.attack(); 
				playSound("src/sounds/skinWalkerPunch.wav");
			}

			else if(e.getKeyCode()==KeyEvent.VK_SHIFT&&wait1==0&&p1Block==false) { // bug detected: both left and right shift trigger VK_SHIFT
				p1Block=true;
				skinWalker.shield(); // shield doesn't come off visually
				playSound("src/sounds/shield.wav");
				System.out.println("block on");
			}


			// katze controls - player right
			else if(e.getKeyCode() == KeyEvent.VK_D) {
				katze.walkRight();
			}

			else if(e.getKeyCode() == KeyEvent.VK_A) {
				katze.walkLeft();
			}

			else if(e.getKeyCode() == KeyEvent.VK_W) {
				katze.jump();
				playSound("src/sounds/jump.wav"); // sound effect
			}

			else if(e.getKeyCode()== KeyEvent.VK_S) { // attack
				if(katze.x_direction<0) {
					p2Attack = new Item(katze.x_coordinate, katze.y_coordinate, "images/objects/invis.png", 1); // invis refers to an image with the same size as 
				} // images
				else { 
					p2Attack = new Item(katze.x_coordinate+350, katze.y_coordinate, "images/objects/invis.png", 1); // signArrow.png, but just erased (maybe rename?)
				} // images 
				katze.attack(); 
				playSound("src/sounds/katzePunch.wav");
			}

			else if(e.getKeyCode()==KeyEvent.VK_Q&&wait2==0&&p2Block==false) {
				p2Block=true;
				katze.shield(); 
				playSound("src/sounds/shield.wav");
				System.out.println("block on");
			}


			// switching characters 
			else if(e.getKeyCode()==KeyEvent.VK_1) {
				katze = new Sprite("sprite/katze/", 50,368,50,2,3);
			}
			else if(e.getKeyCode()==KeyEvent.VK_2) {
				katze = new Sprite("sprite/katze/", 50,368,150,.5,2);
			}
			else if(e.getKeyCode()==KeyEvent.VK_3) {
				katze = new Sprite("sprite/katze/", 50,368,100,1,2);
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

			else if(State == STATE.GAME) {
				
			}
		}
	}

	// method: keyTyped()
	@Override
	public void keyTyped(KeyEvent e) {}

	// method: keyReleased()
	@Override
	public void keyReleased(KeyEvent e) {

		// skinWalker
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
			skinWalker.idle();
		}

		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			skinWalker.attack();
		}

		// katze
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A) {
			katze.idle();
		}

		if(e.getKeyCode() == KeyEvent.VK_S)  {
			katze.attack(); 
		}
	}
	
	//method: mouseClicked()
	//note: calling this function in the program will identify if the button is clicked or not 
	public void mouseClicked(MouseEvent e) {
		if(playButton.containsPoint(e.getX(),e.getY())) {
			System.out.println("Play Button Clicked");
			State = STATE.GAME;
		}
	}

	// method: playSound() 
	// note: calling this function will play the sound with "filePath" once
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
