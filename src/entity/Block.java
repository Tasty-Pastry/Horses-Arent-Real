package entity;

// imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.ID;

public class Block extends GameObject {

	// loads im images
	private BufferedImage[] walls;

	// Constructor
	public Block(int x, int y, ID id, BufferedImage[] walls) {

		super(x, y, id);

		this.walls = walls;

	}

	public void update() {

	}

	// Draw black square
	public void draw(Graphics g) {

		if (getId() == ID.Block) {

			g.drawImage(walls[3], x, y, null);

		} else if (getId() == ID.TopBlock) {

			g.drawImage(walls[2], x, y, null);

		} else if (getId() == ID.TopLeftCornerBlock) {

			g.drawImage(walls[0], x, y, null);

		} else if (getId() == ID.TopRightCornerBlock) {

			g.drawImage(walls[1], x, y, null);

		} else if (getId() == ID.LeftBlock) {

			g.drawImage(walls[6], x, y, null);

		} else if (getId() == ID.RightBlock) {

			g.drawImage(walls[5], x, y, null);

		} else if (getId() == ID.BottomBlock) {

			g.drawImage(walls[7], x, y, null);

		} else if (getId() == ID.BottomLeftCornerBlock) {

			g.drawImage(walls[8], x, y, null);

		} else if (getId() == ID.BottomRightCornerBlock) {

			g.drawImage(walls[4], x, y, null);

		} else {

			// if there is nothing to draw draws a place holder black square
			g.setColor(Color.BLACK);
			g.fillRect(x, y, 64, 64);

		}

	}

	// Hitbox of 1 block entity
	public Rectangle getBounds() {
		
		if (getId() == ID.Block) {
			
			return new Rectangle(x-8, y-2, 50, 64);
		}
		
		return new Rectangle(x-4, y+22, 32, 42);
		

	}

	// gets the name of the black
	@Override
	public String getName() {

		return name;

	}

	// returns the actual sprite
	@Override
	public BufferedImage getSprite() {

		return sprite;
	}

}
