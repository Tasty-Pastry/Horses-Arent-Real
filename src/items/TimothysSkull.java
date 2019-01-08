package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class TimothysSkull extends GameObject {
	
	private BufferedImageLoader loader;

	public TimothysSkull(int x, int y, ID id) {

		super(x, y, id);

		name = "Timothy's Skull";
		
		lore="You feel a powerful energy emanating from this skull. A benevolent and potent soul rests within, carrying the memories of a thousand long lifetimes.";
		
		itemDesc="Timothy's Skull will shoot projectiles at enemies.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/TimothysSkull.png");
		
		keyItem = false;
		passive=true;

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
