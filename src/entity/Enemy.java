package entity;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import animation.Animation;
import animation.Camera;
import animation.Spritesheet;
import game.Game;
import game.Handler;
import game.ID;

public class Enemy extends GameObject {

	// Engine Vars
	private Handler handler;

	// AI Vars
	private Random r = new Random();
	private int choose = 0;
	private boolean move;
	private boolean dontUpdate;

	// Enemy Vars
	private int hp = 100;
	private boolean hit;

	private int enemyXPos;
	private int enemyYPos;

	// Sprite Vars
	private BufferedImage[] horseRight = new BufferedImage[6];
	private BufferedImage[] horseLeft = new BufferedImage[6];
	private BufferedImage[] horseDown = new BufferedImage[6];
	private BufferedImage[] horseUp = new BufferedImage[6];

	// Animation Vars
	private Animation horseRightAni;
	private Animation horseLeftAni;
	private Animation horseDownAni;
	private Animation horseUpAni;

	// Constructor
	public Enemy(int x, int y, int enemyXPos, int enemyYPos, ID id, Handler handler, Spritesheet sheet) {

		super(x, y, id);
		this.sheet = sheet;
		this.handler = handler;

		this.enemyXPos = enemyXPos;
		this.enemyYPos = enemyYPos;

		for (int i = 0; i < 6; i++) {

			horseRight[i] = sheet.getImage(i + 1, 1, 64, 64, 64, 64);
			horseDown[i] = sheet.getImage(i + 1, 2, 64, 64, 64, 64);
			horseUp[i] = sheet.getImage(i + 1, 3, 64, 64, 64, 64);
			horseLeft[i] = sheet.getImage(i + 1, 4, 64, 64, 64, 64);

		}

		// Initializing Animations
		horseRightAni = new Animation(6, horseRight);
		horseLeftAni = new Animation(6, horseLeft);
		horseDownAni = new Animation(6, horseDown);
		horseUpAni = new Animation(6, horseUp);

		velocityX = (r.nextInt(4) + -4);
		velocityY = (r.nextInt(4) + -4);

	}

	// Updates the Enemy
	public void update() {

		// Update velocity only when the camera isn't moving and the enemy is in the
		// same room as the player
		if (!Camera.getCamMove() && enemyXPos == Game.playerXPos && enemyYPos == Game.playerYPos) {

			x += velocityX;
			y += velocityY;

		}

		// Choose a random number between 0 and 10
		choose = r.nextInt(10);

		// For loop that iterates through the handler
		for (int i = 0; i < handler.getObject().size(); i++) {

			// Creates a game object and sets it to the current handler object
			GameObject temp = handler.getObject().get(i);

			// If the object isnt null, and if the object is either a block, or a door
			// collision check

			if (temp != null) {

				if (temp.getId() == ID.Player) {

					if (hp > 50) {

						int diffX = temp.getX() - x;
						int diffY = temp.getY() - y;

						float angle = (float) Math.atan2(diffY, diffX);

						velocityX = 4 * (float) Math.cos(angle);
						velocityY = 4 * (float) Math.sin(angle);

					}

				}

				if (temp.getId() == ID.Block || temp.getId() == ID.DoorBottom || temp.getId() == ID.DoorSide) {

					// Self explanatory, really
					if (goingToCrashIntoEachOther(temp.getBounds()) && !Camera.getCamMove()) {

						dont(temp);

					}

				}

				if (temp.getId() == ID.Bullet) {

					// Checks if a bullet intersects with the enemy
					if (goingToCrashIntoEachOther(temp.getBounds())) {

						if (hp > 50) {

							velocityX += temp.getVelocityX();
							velocityY += temp.getVelocityY();

						} else {

							velocityX += temp.getVelocityX() / 2;
							velocityY += temp.getVelocityY() / 2;

						}

						hp -= 25; // Remove 50 hp
						handler.removeObject(temp); // Remove the bullet
						hit = true; // Sets hit to true

					}

				}

				if (temp.getId() == ID.Player) {

					// Checks if the player is in range
					if (getEvenBiggerBoiBounds().intersects(temp.getBounds())) {

						// Checks if the camera isnt moving, if the enemy is allowed to move and can
						// only shoot once every 25 frames
						if (Game.getCount() % 25 == 0 && !Camera.getCamMove() && move) {

							// Adds a bullet
							handler.getObject().add(new Bullet(getX() + 27, getY() + 64, ID.EnemyBullet, handler,
									temp.getX(), temp.getY(), 10, Color.MAGENTA));

						}

					}

				}

			}

		}

		// Checks if the enemy is in the same room as the player
		if (enemyXPos == Game.playerXPos && enemyYPos == Game.playerYPos)

		{

			move = true;

		}

		// Removes the enemy from the handler if it dies
		if (hp <= 0) {

			Game.addKillCount();

			handler.removeObject(this);

		}

		if (velocityX > 0) {

			horseRightAni.runAnimation();

		} else if (velocityX < 0) {

			horseLeftAni.runAnimation();

		}

		if (velocityY > 0) {

			horseUpAni.runAnimation();

		} else if (velocityY < 0) {

			horseDownAni.runAnimation();

		}

	}

	public void draw(Graphics g) {

		// Checks if move is true
		if (move) {

			if (velocityX > 0) {

				// Draws directional movement
				horseRightAni.drawAnimation(g, x, y, 0);

			} else if (velocityX < 0) {

				horseLeftAni.drawAnimation(g, x, y, 0);

			}

			else if (velocityY < 0) {

				horseDownAni.drawAnimation(g, x, y, 0);

			} else if (velocityY > 0) {

				horseUpAni.drawAnimation(g, x, y, 0);

			}

		}

	}

	private boolean goingToCrashIntoEachOther(Rectangle object) {

		// Checks if the two rectangles intersect
		return getBigBoiBounds().intersects(object.getBounds());

	}

	private void dont(GameObject temp) {

		// Change the velocity if it's going to intersect with a block
		if (!slide((int) (x + velocityY), y, getBounds(), temp.getBounds())) {

			x += velocityX * -1;
			velocityX *= -1;

		}

		// Change the velocity if it's going to intersect with a block
		if (!slide(x, (int) (y + velocityY), getBounds(), temp.getBounds())) {

			y += velocityY * -1;
			velocityY *= -1;

		}

		if (hp <= 50) {

			if (new Rectangle((int) (x + velocityX), (int) (y + velocityY), 64, 64).intersects(temp.getBounds())) {

				// Change the velocity to a random number between +-4
				velocityX = (r.nextInt(4) + -4);
				velocityY = (r.nextInt(4) + -4);

			}

		}

	}

	public boolean slide(int x, int y, Rectangle rect1, Rectangle rect2) {

		rect1.x = x;
		rect1.y = y;

		// Checks if the rects intersect
		if (rect1.intersects(rect2)) {

			return false;

		}

		return true;

	}

	// Bounds of the actual enemy
	public Rectangle getBounds() {

		return new Rectangle(x, y, 64, 64);

	}

	// Collision bounds
	public Rectangle getBigBoiBounds() {

		return new Rectangle(x - 16, y - 16, 96, 96);

	}

	// Shoot range
	public Rectangle getEvenBiggerBoiBounds() {

		return new Rectangle(x - 256, y - 256, 576, 576);

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

}
