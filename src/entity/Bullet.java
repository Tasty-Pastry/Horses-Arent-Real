package entity;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Spritesheet;
import game.Handler;
import game.ID;

public class Bullet extends GameObject {

	// Engine Vars
	private Handler handler;

	// Bullet Vars
	private Color color;

	// Constructor
	public Bullet(int x, int y, ID id, Handler handler, int vx, int vy, int speed, Color color) {

		super(x, y, id);
		this.handler = handler;

		// Calulates velocity of bullet

		// Calulates distance between two points
		int dist = (int) Math.hypot((vx - x), (vy - y));

		// Divides it by the speed it should be going at
		dist /= speed;

		// Calulates veocity
		velocityX = (vx - x) / (dist + 1);
		velocityY = (vy - y) / (dist + 1);

		// Sets color of the bullet
		this.color = color;

	}

	public void update() {

		// Updates position
		x += velocityX;
		y += velocityY;

		// For loop that traverses through the handler
		for (int i = 0; i < handler.getObject().size(); i++) {

			// Creates a temp object and assigns it to the current handler object
			GameObject temp = handler.getObject().get(i);

			// Check if the current object is a door or block
			if (temp.getId() == ID.Block || temp.getId() == ID.DoorBottom || temp.getId() == ID.DoorSide) {

				// If a bullet collides with it, remove it
				if (getBounds().intersects(temp.getBounds())) {

					handler.removeObject(this);

				}

			}

		}

	}

	// Draw the bullet
	public void draw(Graphics g) {

		g.setColor(color);
		g.fillOval(x, y, 10, 10);

	}

	// Hitbox
	public Rectangle getBounds() {

		return new Rectangle(x, y, 8, 8);

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
