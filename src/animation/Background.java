package animation;

// Imports
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.Game;

public class Background {

	// Images
	private BufferedImage image;

	// Movement and Position
	private double x;
	private double y;
	private double dx;
	private double dy;
	private boolean hasPlayedOnce;
	private double moveScale;

	// Load image
	public Background(String s, double ms) {

		// loads in the background image
		try {

			image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));
			moveScale = ms;

		} catch (Exception e) {

			e.printStackTrace(); // Print errors

		}

	}

	// Sets the instance of the background's x and y
	public void setPosition(double x, double y) {

		this.x = (x * moveScale) % Game.WIDTH;
		this.y = (y * moveScale) % Game.HEIGHT;

	}

	// Sets the instance of the backgrounds dx and dy
	public void setVector(double dx, double dy) {

		this.dx = dx;
		this.dy = dy;

	}

	public void update() {

		// Positioning
		x += dx % Game.WIDTH;
		y += dy;

		// Starts the image from the beginning if it has moved off the screen
		// (Wraparound)
		if (x < -1024) {

			x = 0;
			hasPlayedOnce = true;

		}

	}

	// Returns x variable
	public double getx() {

		return x;

	}

	// Returns playedOnce variable
	public boolean playedOnce() {

		return hasPlayedOnce;

	}

	public void draw(Graphics2D g) {

		// Draws the image
		g.drawImage(image, (int) x, (int) y, null);

		// Draws to the right if x is less than 0, and draws to the left if x is greater
		// than 0
		if (x < 0) {

			g.drawImage(image, (int) x + Game.WIDTH, (int) y, null);

		}

		if (x > 0) {

			g.drawImage(image, (int) x - Game.WIDTH, (int) y, null);

		}

	}

}
