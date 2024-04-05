// Class: GraphicsPanel
// Written by: Cat Attack Developers
// Last Updated: Apr 5, 2024
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

// game bug: doesn't accept inputs after victory screen? 
public class GraphicsPanel extends JPanel implements KeyListener, MouseListener{

	private Timer timer;					// The timer is used to move objects at a consistent time interval.

	// game background 
	private Background dollHouse; // dollHouse background object
	private Item dollHouseGround; // dollHouse 'ground' -  allows sprite to be placed in between the background & item

	// sprite + sprite condition variables
	private Sprite skinWalker;
	private Sprite katze; 

	// music 
	private playMusic player;

	// main menu
	private Background menuBackground;
	private Menu menu;
	private Item playButton;

	// game paused menu
	private Background pauseBackground;
	private int pauseCounter;

	private Item resumeButton;
	private Item restartButton;
	private Item controlsButton;

	// controls/keybind menu
	private Background controlsBackground;
	private Background controlMain;
	private Background controlKatze;
	private Background controlSkinWalker;

	private int backChoice;
	private int controlChoice = 0;

	private Item backButton;
	private Item identifierButton;

	private Item katzeSelect;
	private Item katzeSelected;

	private Item skinWalkerSelect;
	private Item skinWalkerSelected;

	// win/loss + end screen
	private int victoryTimer = 0;
	private Background victorySkinWalker; // victory screen - skinWalker
	private Background victoryKatze; // victory screen - Katze
	private Background gameOverMenu;
	private Item quitButton;

	// attack 
	private int attack1Count=0;
	private int attack2Count=0;
	private Item p1Attack;
	private Item p2Attack;

	// shield
	private boolean p1Block = false;
	private boolean p2Block= false;

	private int blockCount1=0;
	private int blockCount2=0;

	private int wait1=0;
	private int wait2=0;;

	// combo system
	private ArrayList<Character> p1Combo = new ArrayList<>();
	private ArrayList<Character> p2Combo = new ArrayList<>();
	private String player1Combo ="";
	private String player2Combo ="";
	private boolean play1ComboP =true;
	private boolean play2ComboP =true;

	// menu: STATE 
	private enum STATE{
		MENU,
		GAME,
		PAUSE,
		CONTROLSMAIN,
		CONTROLKATZE,
		CONTROLSKINWALKER,
		VICTORY,
	};

	private STATE State = STATE.MENU;

	// test variables - DO NOT REMOVE
	// profile test
	//private Item katzeProfile;
	//private Item skinWalkerProfile;

	public GraphicsPanel(){
		// sprite
		skinWalker = new Sprite("sprite/skinwalker/", 1000, 368,100,1,1);
		skinWalker.x_direction = -1; // name = new Sprite(x value, y value, health, speed, attack);
		katze = new Sprite("sprite/katze/", -80,368,100,1,1);

		// music
		player = new playMusic("src/sounds/ambient.wav"); 
		player.run();

		// background info				
		dollHouse = new Background("background/dollHouse.jpg", 2);
		dollHouseGround = new Item(0, 0, "background/dollHouseFloor.png", 2);

		// main menu 
		menu = new Menu();
		menuBackground = new Background("background/menuBackground.png", 2);
		playButton= new Item (500,625, "background/playbutton.png", 10);

		// game paused 
		pauseCounter = -1;

		pauseBackground = new Background("background/pauseBackground.png", 2);
		resumeButton = new Item(525, 440, "background/resumeButton.png", 10); 
		restartButton = new Item(520, 520, "background/restartButton.png", 10); 
		controlsButton = new Item(510, 360, "background/controlsButton.png", 10);

		// control/keybinds menu
		controlsBackground = new Background("background/controlsBackground.png", 2);

		controlMain = new Background("background/controlMain.png", 2);
		controlKatze = new Background("background/controlKatze.png", 2);
		controlSkinWalker = new Background("background/controlSkinWalker.png", 2);

		backButton = new Item(453, 560, "background/backButton.png", 20);
		identifierButton = new Item (600, 560, "background/identifierButton.png", 20); // to be implemented: when clicked, also print next to the character in question
		// i.e, if user is in katze controls, click on "?" katze sprite will have "?" next to character head to identify

		katzeSelect = new Item(508, 560, "background/katzeSelect.png", 20);
		katzeSelected = new Item(508, 560, "background/katzeSelected.png", 20);

		skinWalkerSelect = new Item(645, 560, "background/skinWalkerSelect.png", 20);
		skinWalkerSelected = new Item(645, 560, "background/skinWalkerSelected.png", 20);

		// gameOver menu
		gameOverMenu = new Background("background/gameOver.png", 2);
		quitButton = new Item(565, 500, "background/quitButton.png", 10);

		// victory screen info
		victorySkinWalker = new Background("background/victorySkinWalker.png", 2);
		victoryKatze = new Background("background/victoryKatze.png", 2);

		// scren dimensions
		setPreferredSize(new Dimension(dollHouse.getImage().getIconWidth(),
				dollHouse.getImage().getIconHeight()));  // dimensions of game


		// timer, keylistener, mouselistener
		timer = new Timer(5, new ClockListener(this));   
		timer.start();
		this.setFocusable(true);					     
		this.addKeyListener(this);
		this.addMouseListener((MouseListener) this);
	}

	// method: paintComponent
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		dollHouse.draw(this, g);

		// draw characters
		skinWalker.draw(g2, this);
		katze.draw(g2, this);


		// attack conditions
		if(p2Attack !=null) {
			p2Attack.draw(g2, this);
		}
		if(p1Attack !=null) {
			p1Attack.draw(g2, this);
		}

		// background floor - DO NOT CHANGE THE ORDER OF THIS CODE
		dollHouseGround.draw(g2, this);

		// health bar + background
		g2.setColor(Color.BLACK);
		g2.fillRect(100, 675, 300,50); // currently fixed length at 300, default character
		g2.fillRect(900, 675, 300,50); // to be updated based on unique hp of each character? 
		// Could fix this bug with an actual image as health BAR with indicators of each character HP level
		// idea: draw a custom health bar instead of a black bar. 
		// OR, put a transparent backed, lined bar, thick enough to cover the red as a border
		// then put a black background behind the red. 

		g2.setColor(Color.RED);
		g2.fillRect(100, 675,(int)katze.getHealth()*3,50);
		g2.fillRect(900, 675,(int)skinWalker.getHealth()*3,50);

		// victory condition + graphics
		if(skinWalker.isDead) {
			victoryKatze.draw(this, g2);
			player.close();
			if (victoryTimer == 45) { // victory sound play delay lines up with player reaction speed
				playSound("src/sounds/victoryKatze.wav");
			}
			if (victoryTimer == 800) {
				State = STATE.VICTORY;
			}
			victoryTimer++;
		}

		if(katze.isDead) {
			victorySkinWalker.draw(this, g2);
			player.close();
			if (victoryTimer == 45) {
				playSound("src/sounds/victorySkinWalker.wav");
			}
			if (victoryTimer == 800) {
				State = STATE.VICTORY;
			}
			victoryTimer++;
		}

		
		// GAME
		if(State == STATE.GAME) {
			dollHouse.draw(this, g);
			skinWalker.draw(g2, this);
			katze.draw(g2, this);
			
			if(p2Attack != null) {
				p2Attack.draw(g2, this);
			}
			if(p1Attack != null) {
				p1Attack.draw(g2, this);
			}
			
			dollHouseGround.draw(g2, this); // DO NOT MOVE THIS

			// health bar + background
			g2.setColor(Color.BLACK);
			g2.fillRect(100, 675, 300,50); 
			g2.fillRect(900, 675, 300,50); // potential improvement written above,
			// temporarily addressed with black bar behind red as described
			g2.setColor(Color.RED);
			g2.fillRect(100, 675,(int)katze.getHealth()*3,50);
			g2.fillRect(900, 675,(int)skinWalker.getHealth()*3,50);
		
			// unfinished! 
			//katzeProfile = new Item(1200, 750, "sprite/skinwalker/profile (1).png", 20); // images
			//katzeProfile.draw(g2, this); 
			// test for profile next to health bar, indicate different stats (AKA versions of character)
			// current test, one image - future: use imageResource loadImages() to create an array of images for different stats? different profile for each

		}	


		// win/loss
		
		// skinwalker
		if(skinWalker.isDead) {
			victoryKatze.draw(this, g2);
			player.close();
			if (victoryTimer == 45) { // victory sound play delay lines up with player reaction speed
				playSound("src/sounds/victoryKatze.wav");
			}
			if (victoryTimer == 800) {
				State = STATE.VICTORY;
			}
			victoryTimer++;
		}

		// katze
		if(katze.isDead) {
			victorySkinWalker.draw(this, g2);
			player.close();
			if (victoryTimer == 45) {
				playSound("src/sounds/victorySkinWalker.wav");

			}
			if (victoryTimer == 800) {
				State = STATE.VICTORY;
			}
			victoryTimer++;
		}


		// MAIN MENU
		if(State == STATE.MENU) {
			g2.setColor(Color.BLACK);

			menuBackground.draw(this,g);
			playButton.draw(g2, this);
		}

		// GAME PAUSED 
		if (State == STATE.PAUSE) {
			restartButton = new Item(520, 520, "background/restartButton.png", 10);
			pauseBackground.draw(this,g);
			resumeButton.draw(g2, this);
			restartButton.draw(g2, this);
			controlsButton.draw(g2, this);
		}

		// WIN/LOSS MENU
		if (State == STATE.VICTORY) {
			restartButton = new Item(520, 410, "background/restartButton.png", 10);
			gameOverMenu.draw(this, g);
			restartButton.draw(g2, this);
			quitButton.draw(g2, this);
		}
		

		// Control Menu, includes CONTROLSMAIN + CONTROLSKINWALKER + CONTROLKATZE
		if (State == STATE.CONTROLSMAIN) {
			if (backChoice == 2) {
				menuBackground.draw(this,g);
				playButton.draw(g2, this);
			}
			
			controlsBackground.draw(this, g);
			katzeSelect.draw(g2, this);
			skinWalkerSelect.draw(g2, this);
			backButton.draw(g2, this);
			controlMain.draw(this, g);
		}

		if (State == STATE.CONTROLSKINWALKER) {
			if (backChoice == 2) {
				menuBackground.draw(this,g);
				playButton.draw(g2, this);
			}
			
			controlsBackground.draw(this, g);
			controlSkinWalker.draw(this, g);
			skinWalkerSelected.draw(g2, this);
			katzeSelect.draw(g2, this);
			backButton.draw(g2, this);

		}

		if (State == STATE.CONTROLKATZE ) { // katze
			if (backChoice == 2) {
				menuBackground.draw(this,g);
				playButton.draw(g2, this);
			}
			
			controlsBackground.draw(this, g);
			controlKatze.draw(this, g);
			katzeSelected.draw(g2, this);
			skinWalkerSelect.draw(g2, this);
			backButton.draw(g2, this);
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
				if(p2Combo.size()>=4) {
					for(int i =p2Combo.size()-4;i<p2Combo.size();i++) {
						player2Combo += p2Combo.get(i);
					}
					if(play2ComboP&&player2Combo.equals("wwws")) {
						skinWalker.health=(int)skinWalker.health/2;
						play2ComboP =false;
					}
					if(play2ComboP&&player2Combo.equals("ddds")) {
						skinWalker.x_coordinate+=200;
						play2ComboP =false;
					}
					if(play2ComboP&&player2Combo.equals("aaas")) {
						skinWalker.x_coordinate-=200;
						play2ComboP =false;
					}
					if(play2ComboP&&player2Combo.equals("dsds")) {
						if(skinWalker.health<50)
							skinWalker.health=0;
						play2ComboP =false;
					}
					if(play2ComboP&&player2Combo.equals("asas")) {
						if(skinWalker.health<50)
							skinWalker.health=0;
						play2ComboP =false;
					}
				}
				player2Combo="";
				if(play2ComboP) {
					skinWalker.setHealth(skinWalker.getHealth()-katze.damage);
					System.out.println(skinWalker.getHealth()+"p1");
				}
			}
		}

		if(!p2Block) {
			if(p1Attack!=null&&katze.collision(p1Attack)) {
				if(p1Combo.size()>=4) {
					for(int i =p1Combo.size()-4;i<p1Combo.size();i++) {
						player1Combo += p1Combo.get(i);
					}
					if(play1ComboP&&player1Combo.equals("wwws")) {
						katze.health=(int)katze.health/2;
						play1ComboP =false;
					}
					if(play1ComboP&&player1Combo.equals("aaas")) {
						katze.x_coordinate-=200;
						play1ComboP =false;
					}
					if(play1ComboP&&player1Combo.equals("ddds")) {
						katze.x_coordinate+=200;
						play1ComboP =false;
					}
					if(play1ComboP&&player1Combo.equals("asas")) {
						if(katze.health<50)
							katze.health=0;
						play1ComboP =false;
					}
					if(play1ComboP&&player1Combo.equals("dsds")) {
						if(katze.health<50)
							katze.health=0;
						play1ComboP =false;
					}
				}
				player1Combo="";
				if(play1ComboP) {
					katze.setHealth(katze.getHealth()-skinWalker.damage);
					System.out.println(katze.getHealth()+"p2");
				}
			}
		}

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

		// shield timer
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
		if (State == STATE.MENU && e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			State = STATE.CONTROLSMAIN;
			backChoice = 2;
		}
		if(!skinWalker.isDead&&!katze.isDead) {
			if (State == STATE.GAME) {

				// skinWalker controls - player left
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					skinWalker.walkRight();
					p1Combo.add('d');
				}

				else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					skinWalker.walkLeft();
					p1Combo.add('a');

				}

				else if(e.getKeyCode() == KeyEvent.VK_UP) {
					skinWalker.jump();
					p1Combo.add('w');
					playSound("src/sounds/jump.wav");
				}

				else if(e.getKeyCode()== KeyEvent.VK_DOWN) {
					if(skinWalker.x_direction<0) {
						p1Attack = new Item(skinWalker.x_coordinate, skinWalker.y_coordinate, "images/objects/invis.png", 1);} // images
					else p1Attack = new Item(skinWalker.x_coordinate+450, skinWalker.y_coordinate, "images/objects/invis.png", 1); // images
					skinWalker.attack(); 
					p1Combo.add('s');
					play1ComboP =true;
					playSound("src/sounds/skinWalkerPunch.wav");
				}

				else if(e.getKeyCode()==KeyEvent.VK_SHIFT&&wait1==0&&p1Block==false&&e.getKeyLocation()==KeyEvent.KEY_LOCATION_RIGHT) { // bug detected: both left and right shift trigger VK_SHIFT
					p1Block=true;
					skinWalker.shield(); // shield doesn't come off visually
					playSound("src/sounds/shield.wav");
					System.out.println("block on");


				}


				// katze controls - player right
				else if(e.getKeyCode() == KeyEvent.VK_D) {
					katze.walkRight();
					p2Combo.add('d');
				}

				else if(e.getKeyCode() == KeyEvent.VK_A) {
					katze.walkLeft();
					p2Combo.add('a');
				}

				else if(e.getKeyCode() == KeyEvent.VK_W) {
					katze.jump();
					p2Combo.add('w');
					playSound("src/sounds/jump.wav"); // sound effect
				}

				else if(e.getKeyCode()== KeyEvent.VK_S) { // attack
					if(katze.x_direction<0) {
						p2Attack = new Item(katze.x_coordinate-20, katze.y_coordinate, "images/objects/invis.png", 1); // invis refers to an image with the same size as 
					} // images
					else { 
						p2Attack = new Item(katze.x_coordinate+450, katze.y_coordinate, "images/objects/invis.png", 1); // signArrow.png, but just erased (maybe rename?)
					} // images 
					katze.attack(); 
					p2Combo.add('s');
					play2ComboP =true;
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
					katze = new Sprite("sprite/katze/", -80,368,50,2,3);
				}
				else if(e.getKeyCode()==KeyEvent.VK_2) {
					katze = new Sprite("sprite/katze/", -80,368,150,.5,2);
				}
				else if(e.getKeyCode()==KeyEvent.VK_3) {
					katze = new Sprite("sprite/katze/", -80,368,100,1,2);
				}
				else if(e.getKeyCode()==KeyEvent.VK_8) {
					skinWalker = new Sprite("sprite/skinwalker/", 1000,368,50,2,3);
					skinWalker.x_direction = -1;
				}
				else if(e.getKeyCode()==KeyEvent.VK_9) {
					skinWalker = new Sprite("sprite/skinwalker/", 1000,368,150,.5,2);
					skinWalker.x_direction = -1;
				}
				else if(e.getKeyCode()==KeyEvent.VK_0) {
					skinWalker = new Sprite("sprite/skinwalker/", 1000,368,100,1,2);
					skinWalker.x_direction = -1;
				}
			}


			// allows the toggle on and off of game pause 
			if (State == STATE.GAME || State == STATE.PAUSE) {
				if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					pauseCounter*=-1;
					if (pauseCounter == 1) {
						State = STATE.PAUSE;
						player.close();
						// player = new playMusic("src/sounds/ambient.wav"); 
						// player.run();
					}
					else if (pauseCounter == -1) {
						State = STATE.GAME;
						player.close();
						player = new playMusic("src/sounds/loop.wav"); 
						player.run();
					}
				}
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
	public void mouseClicked(MouseEvent e) { // will set state to game 
		if (State == STATE.MENU) {
			if(playButton.containsPoint(e.getX(),e.getY())) {
				System.out.println("Play Button Clicked");
				playSound("src/sounds/click.wav");
				player.close();
				State = STATE.GAME;
				player = new playMusic("src/sounds/loop.wav"); 
				player.run();
			}
		}

		if (State == STATE.PAUSE) {
			if (resumeButton.containsPoint(e.getX(), e.getY())) {
				System.out.println("Resume Button Clicked");
				playSound("src/sounds/click.wav");
				player.close();
				State = State.GAME; 
				player = new playMusic("src/sounds/loop.wav");
				player.run();
			}

			if (controlsButton.containsPoint(e.getX(), e.getY())) {
				System.out.println("Controls Button Clicked");
				playSound("src/sounds/click.wav");
				State = STATE.CONTROLSMAIN;
				backChoice = 1;

			}
		}

		if (State == STATE.VICTORY) {
			if(quitButton.containsPoint(e.getX(), e.getY())) { 
				System.exit(0);
				// System.out.println("System Exit");
			}	
		}

		if (State == STATE.VICTORY || State == STATE.PAUSE ) {
			if(restartButton.containsPoint(e.getX(),e.getY())) { 
				System.out.println("Restart Button Clicked"); 

				playSound("src/sounds/click.wav");
				player = new playMusic("src/sounds/ambient.wav"); 
				player.run();

				// reset character to default
				skinWalker = new Sprite("sprite/skinwalker/", 1000, 368,100,1,1);
				skinWalker.x_direction = -1;// name = new Sprite(x value, y value, health, speed, attack);
				katze = new Sprite("sprite/katze/", 50,368,100,1,1);
				victoryTimer = 0;



				State = State.MENU;
			}
		}

		if (State == STATE.CONTROLSMAIN || State == STATE.CONTROLSKINWALKER || State == STATE.CONTROLKATZE) {
			if (skinWalkerSelect.containsPoint(e.getX(), e.getY())) {
				playSound("src/sounds/click.wav");
				State = STATE.CONTROLSKINWALKER;

			}

			if (katzeSelect.containsPoint(e.getX(), e.getY())) {
				playSound("src/sounds/click.wav");
				State = STATE.CONTROLKATZE;
			}


			if (backButton.containsPoint(e.getX(), e.getY())) {
				playSound("src/sounds/click.wav");
				if (backChoice == 1) { // pause
					State = STATE.PAUSE;
				}

				if (backChoice == 2) { // menu
					State = STATE.MENU;

				}
			}
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
