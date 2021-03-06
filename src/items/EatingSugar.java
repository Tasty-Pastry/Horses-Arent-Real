package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import entity.GameObject;
import entity.PlayerObject;
import game.ID;

public class EatingSugar extends GameObject {

	private BufferedImageLoader loader;

	public EatingSugar(int x, int y, ID id) {

		super(x, y, id);

		name = "Eating Sugar?";
		
		itemDesc="Eat this and the speed of all characters will increase.";
		lore = "Killing horses? No, papa!";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/EatingSugar.png");

		keyItem = false;
		passive = false;
		vored = false;

	}

	public void update() {

	}

	public void use() {

		PlayerObject.maxVel += 1.5;
		PlayerObject.vel += 4.5;
		vored = true;

	}

	// Draw crate
	public void draw(Graphics g) {

		g.drawImage(sprite, x, y, null);

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
