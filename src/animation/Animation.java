package animation;

// Imports
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

	// Image vars
	private BufferedImage[] sprite;

	// Count Vars
	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;

	// Stop Vars
	private boolean stop;
	private boolean ranOnce;

	// Constructor
	public Animation(int speed, BufferedImage... sprite) {

		// Inits Vars
		frames = sprite.length;

		// Creates array that holds all the images in the spritesheet
		this.sprite = sprite;

		// Sets speed of animation
		this.speed = speed;

	}

	// resets animation
	public void reset() {

		index = 0;
		count = 0;

		ranOnce = false;

	}
	// runs animation
	public void runAnimation() {

		// Checks if the animation was stopped
		if (!stop) {

			// Increases index
			index++;

			// Checks if the index is greater than the speed
			if (index > speed) {

				// Resets index
				index = 0;

				// Traverses to the next frame
				nextFrame();

			}

		}

	}

	// starts or stops the animation
	public void toggleAnimation(boolean stop) {

		// Sets stop var
		this.stop = stop;

		// Resets ranOnce var if stop is false
		if (!stop) {

			ranOnce = false;

		}
	}

	public void nextFrame() {

		// Increases count
		count++;

		// Checks if the count exceeds the frame count
		if (count > frames - 1) {

			// Resets count
			count = 0;

			// Animation ran once
			ranOnce = true;

		}

	}

	public void drawAnimation(Graphics g, double x, double y, int offset) {

		// Draws the current sprite
		g.drawImage(sprite[count], (int) x - offset, (int) y, null);

	}

	// Getters and Setters
	public void setCount(int count) {

		this.count = count;

	}

	// gets which part of the animation cycle its on
	public int getCount() {

		return count;

	}

	// gets how fast the animation switching is 
	public int getSpeed() {

		return speed;

	}

	// changes the animation speed
	public void setSpeed(int speed) {

		this.speed = speed;

	}

	// runs the animation once
	public boolean getRanOnce() {

		return ranOnce;

	}
}