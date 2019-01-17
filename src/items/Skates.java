package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class Skates extends GameObject {
	
	private BufferedImageLoader loader;

	public Skates(int x, int y, ID id) {

		super(x, y, id);

		name = "Skates";
		
		lore="A sharp piece of metal glued onto to some large boots. Perhaps... you could kick something with them on?";
		
		itemDesc="Grants Nicc the Thicc the ability to use his Nick Kick.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/Skates.png");
		
		keyItem = true;
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
