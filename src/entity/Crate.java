package entity;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Spritesheet;
import game.ID;

public class Crate extends GameObject {

	// Constructor
	public Crate(int x, int y, ID id) {

		super(x, y, id);

	}

	public void update() {

	}

	// Draw crate
	public void draw(Graphics g) {

		g.setColor(Color.yellow);

		g.fillRect(x, y, 32, 32);

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
