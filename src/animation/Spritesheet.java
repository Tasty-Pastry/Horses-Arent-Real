package animation;

// Imports
import java.awt.image.BufferedImage;

public class Spritesheet {

	// Sprite sheet variable
	private BufferedImage spriteSheet;

	// Constructor
	public Spritesheet(BufferedImage spriteSheet) {

		this.setSpriteSheet(spriteSheet);

	}

	// Gets the subimage of the spritesheet
	public BufferedImage getImage(int col, int row, double width, int height, double w, int h) {

		// Returns a snippet of the spritesheet according to the specifications,
		// everything is in 64 x 64
		return getSpriteSheet().getSubimage((int) ((col * w) - w), (row * h) - h, (int) width, height);

	}

	// returns the sprite sheet
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	// changes the sprite sheet
	public void setSpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}

}
