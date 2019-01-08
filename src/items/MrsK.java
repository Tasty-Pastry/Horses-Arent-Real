package items;

// Imports
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class MrsK extends GameObject {
	
	private BufferedImageLoader loader;
	private boolean vored;

	// Constructor
	public MrsK(int x, int y, ID id) {

		super(x, y, id);
		
		name = "Mrs. K";
		
		lore="A being that has reached perfect Adult.";
		
		itemDesc="Use this to increase your damage for 30 seconds.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/MrsK.png");
		
		keyItem = false;
		passive=false;
		vored=false;

	}

	public void update() {

	}

	// Draw crate
	public void draw(Graphics g) {

		g.drawImage(sprite,x,y,null);

	}

	// Get boundaries of crate
	public Rectangle getBounds() {

		return new Rectangle(x, y, 32, 32);

	}
	
	@Override
	public String getName() {

		return name;

	}

	@Override
	public BufferedImage getSprite() {

		return sprite;
	}

}
