package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import entity.GameObject;
import game.ID;

public class MeguminStaff extends GameObject {

	private BufferedImageLoader loader;

	public MeguminStaff(int x, int y, ID id) {

		super(x, y, id);

		name = "The Megumin Staff";

		lore = "A weird looking staff that has seen some use. A destructive aura lures you to it...";

		itemDesc = "Grants Daanish the ability to use EKUSPROSION!";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("MeguminStaff.png");

		keyItem = true;
		passive = false;

	}

	public void update() {

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
