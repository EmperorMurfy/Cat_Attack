import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageResource {

	private ImageIcon image;			// The ImageIcon will be used to hold the Character's png.
	private int imageCount;
	private int jumpCount;
	private int imageMax;
	private int imageOffset;
	
	// These two variables are used so that the image doesn't refresh every time the the panel is redrawn.
	// Without these variables the images would change much too quickly.
	private int imageRefreshCounter = 0;
	private static final int IMAGE_REFRESH_MAX = 3;

	private static final int SCALE = 4;

	private ImageIcon[] runningImages;
	private ImageIcon[] jumpingImages;
	
	public ImageResource(String imagePath, int imageMax, int imageOffset) {
		runningImages = new ImageIcon[imageMax];
		jumpingImages = new ImageIcon[imageMax];
		imageCount = 0;
		jumpCount = 0;
		
		loadImages((imagePath + "run ("), runningImages);
		loadImages((imagePath + "jump ("), jumpingImages);
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

	public void updateImage(int x_direction, boolean jumping, boolean isDead) {

		imageRefreshCounter++;

		if(imageRefreshCounter >= IMAGE_REFRESH_MAX && imageCount < imageMax - 1) {
			imageCount++;
			imageRefreshCounter = 0;
		}	
		else if(imageCount >= imageMax - 1 && !isDead) {
			imageCount = 0;
		}

		if(isDead) {
			image = runningImages[0];
		}
		else if(jumping) {
			jumpCount = (jumpCount < (imageMax * 6)-1) ? jumpCount+1 : 0;
			image = jumpingImages[jumpCount/6];
		}
		// idle 
		else if(Math.abs(x_direction) == 1){
			image = runningImages[0];
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
