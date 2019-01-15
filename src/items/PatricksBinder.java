package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import entity.GameObject;
import game.ID;

public class PatricksBinder extends GameObject {
	
	private BufferedImageLoader loader;

	public PatricksBinder(int x, int y, ID id) {

		super(x, y, id);

		name = "Patrick's Binder";
		
		lore="The pages are faded and hard to make out, but it seems to hold invaluable knowledge. It feels as if you give it power, it will give something in return…";
		
		itemDesc="Use this to refill your EP.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/PatricksBinder.png");
		
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
