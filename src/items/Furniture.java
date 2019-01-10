package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class Furniture extends GameObject {

	private BufferedImageLoader loader;
	private boolean vored;

	public Furniture(int x, int y, ID id) {

		super(x, y, id);

		name = "Furniture?";
		
		lore="This furniture seems strangely alive...";
		
		itemDesc="Break it open for a surprise.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/Furniture.png");
		
		keyItem = true;
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
