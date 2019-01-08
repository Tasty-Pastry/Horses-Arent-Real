package entity;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Spritesheet;
import game.ID;

public class Block extends GameObject {

	// Constructor
	public Block(int x, int y, ID id) {

		super(x, y, id);

	}

	public void update() {

	}

	// Draw black square
	public void draw(Graphics g) {

		g.setColor(Color.black);
		g.fillRect(x, y, 64, 64);

	}

	// Hitbox
	public Rectangle getBounds() {

		return new Rectangle(x, y, 64, 64);

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
