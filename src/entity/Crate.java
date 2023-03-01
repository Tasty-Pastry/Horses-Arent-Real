package entity;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Spritesheet;
import game.ID;

public class Crate extends GameObject {

	// crate image
	private BufferedImage[] crates;
	
	// Constructor
	public Crate(int x, int y, ID id, BufferedImage[] crates) {

		super(x, y, id);

		this.crates = crates;
	}

	public void update() {

	}

	// Draw crate
	public void draw(Graphics g) {

		g.drawImage(crates[0], x, y,  null);

	}

	// Get boundaries of crate
	public Rectangle getBounds() {

		return new Rectangle(x-16, y, 29, 32);

	}
	
	// returns sprite name
	@Override
	public String getName() {

		return name;

	}

	// returns sprite
	@Override
	public BufferedImage getSprite() {

		return sprite;
	}

}
