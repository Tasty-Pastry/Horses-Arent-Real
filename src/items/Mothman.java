package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class Mothman extends GameObject {

	private BufferedImageLoader loader;

	public Mothman (int x, int y, ID id) {

		super(x, y, id);

		name = "Mothman";
		
		lore="A friend and a moth, he will die for you if you pledge to do the same.";
		
		itemDesc="Mothman will shoot projectiles at enemies.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/Mothman.png");
		
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
