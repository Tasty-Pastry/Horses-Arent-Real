package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class HumanBone extends GameObject {
	
	private BufferedImageLoader loader;
	private boolean vored;

	public HumanBone(int x, int y, ID id) {

		super(x, y, id);

		name = "Human Bone";
		
		lore="Crunch.";
		
		itemDesc="Eat this to regain 10% of your HP.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/HumanBone.png");
		
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
