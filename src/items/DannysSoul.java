package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class DannysSoul extends GameObject {
	
	private BufferedImageLoader loader;
	
	private boolean vored;

	public DannysSoul(int x, int y, ID id) {

		super(x, y, id);

		name = "Daniel's Soul";
		
		lore = "The horses may have killed his body, but his soul is immortal…";
				
		itemDesc = "Eat this to gain 10 damage.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/DannysSoul.png");
		
		keyItem = false;
		passive = false;
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
