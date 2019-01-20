package entity;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Spritesheet;
import game.Handler;
import game.ID;

public class Door extends GameObject {

	// Engine vars
	private Handler handler;

	// Constructor
	public Door(int x, int y, ID id, Handler handler) {

		super(x, y, id);
		this.handler = handler;

	}

	public void update() {

	}

	public void draw(Graphics g) {

		g.setColor(Color.pink);
		g.fillRect(x, y, 64, 64);

	}

	// Gets the middle of the door x
	public double centroidX() {

		return (getBounds().x + getBounds().getWidth()) / 2;

	}

	// gets middle of door y
	public double centroidY() {

		return (getBounds().y + getBounds().getHeight()) / 2;

	}

	// Bounds of the door
	public Rectangle getBounds() {

		return new Rectangle(x, y, 64, 64);

	}
	
	// door name
	@Override
	public String getName() {

		return name;

	}

	// door sprite
	@Override
	public BufferedImage getSprite() {

		return sprite;
	}

}
