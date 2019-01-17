package items;

// Imports
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import entity.GameObject;
import game.ID;

public class HorseEye extends GameObject {
	
	private BufferedImageLoader loader;

	// Constructor
	public HorseEye(int x, int y, ID id) {

		super(x, y, id);
		
		name = "Horse Eye";
		
		lore="This eye gives off an ominous aura... like a...a... horse aura??? I... wonder what would happen if you licked it.";
		
		itemDesc="...";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/HorseEye.png");
		
		keyItem = false;
		passive=false;
		vored=false;

	}

	public void update() {

	}
	
	public void use() {
		
		
		
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
