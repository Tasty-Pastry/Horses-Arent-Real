package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class EatingSugar extends GameObject {

	private BufferedImageLoader loader;
	private boolean vored;

	public EatingSugar(int x, int y, ID id) {

		super(x, y, id);

		name = "Eating Sugar?";
		
		lore="Killing horses? No, papa!";
		
		itemDesc="Eat this to gain 2 speed.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/EatingSugar.png");
		
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
