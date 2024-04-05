// Class: ImageResources
// Written by: Cat Attack Developers
// Last Updated: May 22nd, 2024
// Description: Image Resource

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class ImageResource {

	private ImageIcon image;			// The ImageIcon will be used to hold the Character's png.
	private int imageCount;
	private int jumpCount;
	private int shieldCount; // shield count 
	private int attackCount;
	private int damageCount;
	private int imageMax;
	private int imageOffset;

	// These two variables are used so that the image doesn't refresh every time the the panel is redrawn.
	// Without these variables the images would change much too quickly.
	private int imageRefreshCounter = 0;
	private static final int IMAGE_REFRESH_MAX = 3;

	private static final int SCALE = 6; // scale, as number increases size decrease 

	private ImageIcon[] runningImages;
	private ImageIcon[] jumpingImages;

	// test
	private ImageIcon[] idleImages; // idling 
	private ImageIcon[] attackImages; // punch
	private ImageIcon[] shieldImages; // shield
	private ImageIcon[] damageImages; // damage
	private ImageIcon[] deadImages; // dead


	public ImageResource(String imagePath, int imageMax, int imageOffset) {
		runningImages = new ImageIcon[imageMax];
		jumpingImages = new ImageIcon[imageMax];

		//test values 
		idleImages = new ImageIcon[imageMax];
		attackImages = new ImageIcon[imageMax];
		shieldImages = new ImageIcon[imageMax];
		damageImages = new ImageIcon[imageMax];
		deadImages = new ImageIcon[imageMax];



		imageCount = 0;
		jumpCount = 0;
		shieldCount = 0;
		attackCount = 0;

		loadImages((imagePath + "Run ("), runningImages); // changed from run -> Run
		loadImages((imagePath + "Jump ("), jumpingImages); // changed from jump -> Jump 

		// test values
		loadImages((imagePath + "Idle ("), idleImages);
		loadImages((imagePath + "Punch ("), attackImages);
		loadImages((imagePath + "Shield ("), shieldImages);
		loadImages((imagePath + "Damage ("), damageImages);
		loadImages((imagePath + "Dead ("), deadImages);

		image = runningImages[imageCount];
		this.imageMax = imageMax;
		this.imageOffset = imageOffset;
	}

	private void loadImages(String imagePath, ImageIcon[] images) {

		ClassLoader cldr = this.getClass().getClassLoader();
		String newImagePath; 

		for(int i = 0; i < images.length; i++) {
			newImagePath = imagePath + (i + 1) + ").png";

			URL imageURL = cldr.getResource(newImagePath);				
			image = new ImageIcon(imageURL);	
			image.getImage();
			Image scaled = image.getImage().getScaledInstance(image.getIconWidth() / SCALE, 
					image.getIconHeight() / SCALE, image.getImage().SCALE_SMOOTH);
			images[i] = new ImageIcon(scaled);
		}
	}

	public void updateImage(int x_direction, boolean jumping, boolean isDead, boolean shield, boolean attack, boolean damage) {

		imageRefreshCounter++;

		if(imageRefreshCounter >= IMAGE_REFRESH_MAX && imageCount < imageMax - 1) {
			imageCount++;
			imageRefreshCounter = 0;
		}	
		else if(imageCount >= imageMax - 1 && !isDead) {
			imageCount = 0;
		}

		if(isDead) {
			image = deadImages[0];
		}
		
		else if(jumping) {
			jumpCount = (jumpCount < (imageMax * 6)-1) ? jumpCount+1 : 0;
			image = jumpingImages[0];
		}
		
		else if(shield) { // shield 
			shieldCount = (shieldCount < (imageMax*6)-1) ? shieldCount+1 :0;
			image = shieldImages[imageCount];
		}
		
		else if (attack) {
			attackCount = (attackCount < (imageMax*6)-1 ? attackCount+1 :0);
			image = attackImages[imageCount];
		}
		
		else if (damage) {
			damageCount = (damageCount < (imageMax*6)-1 ? damageCount+1 :0);
			image = damageImages[imageCount];
		}
		
		// idle 
		else if(Math.abs(x_direction) == 1){
			image = idleImages[imageCount];
		}
		
		// running or walking
		else {
			image = runningImages[imageCount];
		}

	}

	public ImageIcon getImage() {
		return image;
	}

	public int getImageOffset() {
		return imageOffset;
	}

}
