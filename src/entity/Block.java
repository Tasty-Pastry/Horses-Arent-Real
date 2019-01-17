package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.ID;

public class Block extends GameObject {

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

		} else {

			g.setColor(Color.BLACK);
			g.fillRect(x, y, 64, 64);

		}

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
