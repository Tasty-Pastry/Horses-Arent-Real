package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Animation;
import animation.Camera;
import animation.Spritesheet;
import game.Game;
import game.Handler;
import game.ID;
import game.Inventory;

public abstract class PlayerObject extends GameObject {

	// Engine vars
	Handler handler;

	// Physics Vars
	public static double vel = 0.56;
	protected float maxVel = (float) 9;
	protected float minVel = 0;

	// Player Vars

	protected boolean isRight;
	protected boolean isLeft;
	protected boolean isDown;
	protected boolean isUp;

	private static int lastDir; // 1 is Up, 2 is Right, 3 is Down, 4 is Left

	// Sprite Vars
	public static BufferedImage[] playerRight = new BufferedImage[3];
	public static BufferedImage[] playerLeft = new BufferedImage[3];
	public static BufferedImage[] playerDown = new BufferedImage[3];
	public static BufferedImage[] playerUp = new BufferedImage[3];

	public static BufferedImage[] playerRightAttack = new BufferedImage[2];
	public static BufferedImage[] playerLeftAttack = new BufferedImage[2];
	public static  BufferedImage[] playerDownAttack = new BufferedImage[2];
	public static BufferedImage[] playerUpAttack = new BufferedImage[2];

	// Animation Vars
	public static Animation playerRightAni;
	public static Animation playerLeftAni;
	public static Animation playerDownAni;
	public static Animation playerUpAni;

	public static Animation playerRightAttackAni;
	public static Animation playerLeftAttackAni;
	public static Animation playerDownAttackAni;
	public static Animation playerUpAttackAni;

	protected Inventory inv;

	// Enemy Var
	protected int[][] enemyArray;

	// Constructor
	public PlayerObject(int x, int y, ID id, Handler handler, Spritesheet sheet, Spritesheet healthBar, Inventory inv) {

		super(x, y, id);
		this.sheet = sheet;
		this.handler = handler;

		this.inv = inv;

		// Get Enemy Array
		enemyArray = Game.getEnemyArray();

		setLastDir(2); // The player is facing right

	}

	public void update() {

		// Check if the player is colliding with an object
		collision();

		// Updates movement
		x += velocityX;
		y += velocityY;

		// Movement of Player Character
		if (handler.isUp()) {

			// Set animation
			isUp = true;
			isDown = false;
			isRight = false;
			isLeft = false;

			// Decreases Y velocity
			velocityY += -vel;

			// Caps Y velocity
			if (velocityY < -maxVel) {

				velocityY = -maxVel;
			}

			// Facing up
			setLastDir(1);

		} else if (!handler.isDown()) {

			// Increases Y velocity
			velocityY -= -vel;

			// Caps Y velocity
			if (velocityY > minVel) {

				velocityY = minVel;

			}

		}

		// Moving down
		if (handler.isDown()) {

			// Sets animation
			isUp = false;
			isDown = true;
			isRight = false;
			isLeft = false;

			// Increases Y velocity
			velocityY -= -vel;

			// Caps Y velocity
			if (velocityY > maxVel) {

				velocityY = maxVel;

			}

			// Sprite facing down
			setLastDir(3);

		} else if (!handler.isUp()) { // Slows down y vel when not moving down

			// Decreases y vel
			velocityY -= vel;

			// Caps vel
			if (velocityY < minVel) {

				velocityY = minVel;

			}

		}

		// Moving to the right
		if (handler.isRight()) {

			// Sets animation
			isLeft = false;
			isRight = true;
			isUp = false;
			isDown = false;

			// Increase x vel
			velocityX -= -vel;

			// Caps max vel
			if (velocityX > maxVel) {

				velocityX = maxVel;

			}

			// Sprite facing the right
			setLastDir(2);

		} else if (!handler.isLeft()) { // Decrease vel when the player is moving right but the right key isn't being
										// pressed

			// Decrease x vel
			velocityX -= vel;

			// Caps x vel
			if (velocityX < minVel) {

				velocityX = minVel;

			}

		}

		// Moving left
		if (handler.isLeft()) {

			// Sets animation direction
			isRight = false;
			isLeft = true;
			isDown = false;
			isUp = false;

			// Decrease x vel
			velocityX += -vel;

			// Caps x vel
			if (velocityX < -maxVel) {

				velocityX = -maxVel;

			}

			// Player sprite is facing left
			setLastDir(4);

		} else if (!handler.isRight()) { // Slows down

			// Increases vel
			velocityX -= -vel;

			// Caps vel
			if (velocityX > minVel) {

				velocityX = minVel;

			}

		}

		// Sets acceleration to 0 if the camera is transitioning
		if (Camera.getCamMove()) {

			velocityX = 0;
			velocityY = 0;

		}

		// Run the animations based on direction
		if (isRight && !isLeft) {

			playerRightAni.runAnimation();

		} else if (isLeft && !isRight) {

			playerLeftAni.runAnimation();

		}

		if (isUp && !isDown) {

			playerUpAni.runAnimation();

		} else if (isDown && !isUp) {

			playerDownAni.runAnimation();

		}

		// -------------------------------------------------------------------------------------------------------------------------------------

		// Animation for shooting
		if (Game.getShoot() && Game.getShootDir() == 1) {

			// Starts animation
			playerUpAttackAni.toggleAnimation(false);

			playerUpAttackAni.runAnimation();

			// Stops if it runs once
			if (playerUpAttackAni.getRanOnce()) {

				Game.setShoot(false, 0);

				setLastDir(1);

			}

		} else if (Game.getShoot() & Game.getShootDir() == 2) {

			// Starts animation
			playerRightAttackAni.toggleAnimation(false);

			playerRightAttackAni.runAnimation();

			// Stops if run once
			if (playerRightAttackAni.getRanOnce()) {

				Game.setShoot(false, 0);

				setLastDir(2);

			}

		} else if (Game.getShoot() && Game.getShootDir() == 3) {

			// Starts animation
			playerDownAttackAni.toggleAnimation(false);

			playerDownAttackAni.runAnimation();

			// Stops if run once
			if (playerDownAttackAni.getRanOnce()) {

				Game.setShoot(false, 0);

				setLastDir(3);

			}

		} else if (Game.getShoot() && Game.getShootDir() == 4) {

			// Starts animation
			playerLeftAttackAni.toggleAnimation(false);

			playerLeftAttackAni.runAnimation();

			// Stops if run once
			if (playerLeftAttackAni.getRanOnce()) {

				Game.setShoot(false, 0);

				setLastDir(4);

			}

		} else {

			// Stops all animations if not shooting
			playerDownAttackAni.toggleAnimation(true);
			playerUpAttackAni.toggleAnimation(true);
			playerLeftAttackAni.toggleAnimation(true);
			playerRightAttackAni.toggleAnimation(true);

			Game.setShoot(false, 0);

		}

		// ------------------------------------------------------------------------------------------------------------

	}

	public boolean slide(int x, int y, Rectangle rect1, Rectangle rect2) {

		// Collision checks
		rect1.x = x;
		rect1.y = y;

		if (rect1.intersects(rect2)) {

			return false;

		}

		return true;

	}

	public void collision() {

		// For loop that iterates through the handler
		for (int i = 0; i < handler.getObject().size(); i++) {

			// Creates a temp game object and sets it to the current handler game object
			GameObject temp = handler.getObject().get(i);

			// Block collision check
			if (temp.getId() == ID.Block) {

				// Collision logic
				if (!slide((int) (x + velocityX), y, getBounds(), temp.getBounds())) {

					velocityX = 0;

				}

				if (!slide(x, (int) (y + velocityY), getBounds(), temp.getBounds())) {

					velocityY = 0;

				}

			}

			// Door collision
			if (temp.getId() == ID.DoorBottom) {

				// Checks if all the enemies in the room have been killed
				if (enemyArray[Game.playerYPos][Game.playerXPos] == Game.getKillCount()) {

					// Set the enemy room count to 0
					enemyArray[Game.playerYPos][Game.playerXPos] = 0;

					// Reset the player killcount
					Game.resetKillCount();

					// Checks if the player intersects with the door
					if (getBounds().intersects(temp.getBounds()) && velocityY > 0) {

						// Moves the camera down
						Camera.setMove(1);

						// Translates the player 128 pixels down
						y += 192;

						// Move the player location
						Game.playerYPos++;

					} else if (getBounds().intersects(temp.getBounds()) && velocityY < 0) {

						// Moves the camera up
						Camera.setMove(2);

						// Translated the player 128 pixels up
						y -= 192;

						// Move the player location
						Game.playerYPos--;

					}

				} else {

					// Collision logic
					if (!slide((int) (x + velocityX), y, getBounds(), temp.getBounds())) {

						velocityX = 0;

					}

					if (!slide(x, (int) (y + velocityY), getBounds(), temp.getBounds())) {

						velocityY = 0;

					}

				}

			}

			if (temp.getId() == ID.DoorSide) {

				if (enemyArray[Game.playerYPos][Game.playerXPos] == Game.getKillCount()) {

					// Sets the number of enemies in the room to 0
					enemyArray[Game.playerYPos][Game.playerXPos] = 0;

					// Resets the player kill count
					Game.resetKillCount();

					if (getBounds().intersects(temp.getBounds()) && velocityX > 0) {

						// Move the camera to the right
						Camera.setMove(3);

						// Translate the player 128 pixels right
						x += 192;

						// Update the players location
						Game.playerXPos++;

					} else if (getBounds().intersects(temp.getBounds()) && velocityX < 0) {

						// Move the camera to the left
						Camera.setMove(4);

						// Translate the player 128 pixels left
						x -= 192;

						// Updates player location
						Game.playerXPos--;

					}

				} else {

					// Collision logic
					if (!slide((int) (x + velocityX), y, getBounds(), temp.getBounds())) {

						velocityX = 0;

					}

					if (!slide(x, (int) (y + velocityY), getBounds(), temp.getBounds())) {

						velocityY = 0;

					}

				}

			}

			if (temp.getId() == ID.Crate) {

				// Pick up ammo if the player intersects with it
				if (getBounds().intersects(temp.getBounds())) {

					// Add 50 ammo
					Game.ammo += 50;

					// Remove object from handler
					handler.removeObject(temp);

				}

			}

			if (temp.getId() == ID.HorseEye) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.horseEye);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.MrsK) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.MrsK);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.DannysSoul) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.dannysSoul);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.EatingSugar) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.eatingSugar);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.Furniture) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.furniture);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.HumanBone) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.humanBone);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.MeguminStaff) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.meguminStaff);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.Mothman) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.mothman);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.PatricksBinder) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.patricksBinder);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.Skates) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.skates);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.StarCrossedScarf) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.starScarf);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.TeethButter) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.teethButter);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.TimothysSkull) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.timothysSkull);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

			if (temp.getId() == ID.YuGiOhCard) {

				if (getBounds().intersects(temp.getBounds())) {

					inv.insert(Game.yugiohCard);

					if (inv.isRemove()) {

						// Remove object from handler
						handler.removeObject(temp);

						inv.setRemove(false);

					}

				}

			}

		}

	}

	public void draw(Graphics g) {

		// Checks if the player is shooting
		if (Game.getShoot()) {

			// Checks the direction the player is shooting in (1 is up, 2 is right, 3 is
			// down, and 4 is left)
			if (Game.getShootDir() == 1) {

				// Draws the animation depending on direction
				playerUpAttackAni.drawAnimation(g, x, y, 0);

			} else if (Game.getShootDir() == 2) {

				playerRightAttackAni.drawAnimation(g, x, y, 0);

			} else if (Game.getShootDir() == 3) {

				playerDownAttackAni.drawAnimation(g, x, y, 0);

			} else if (Game.getShootDir() == 4) {

				playerLeftAttackAni.drawAnimation(g, x, y, 0);

			}

			// Checks if the player is moving
		} else if (velocityX <= 1.5 && velocityX >= -1.5 && velocityY <= 1.5 && velocityY >= -1.5) {

			// Checks the last direction the player was in
			if (getLastDir() == 1) {

				// Draws a static image of the player facing a direction
				g.drawImage(playerUp[0], x, y, null);

			} else if (getLastDir() == 2) {

				g.drawImage(playerRight[0], x, y, null);

			} else if (getLastDir() == 3) {

				g.drawImage(playerDown[0], x, y, null);

			} else if (getLastDir() == 4) {

				g.drawImage(playerLeft[0], x, y, null);

			}

		} else {

			// Checks if the player is moving in a certain direction
			if (isRight && !isLeft && !isDown && !isUp) {

				// Draws directional movement
				playerRightAni.drawAnimation(g, x, y, 0);

			} else if (isLeft && !isRight && !isDown && !isUp) {

				playerLeftAni.drawAnimation(g, x, y, 0);

			}

			if (isDown && !isUp && !isRight && !isLeft) {

				playerDownAni.drawAnimation(g, x, y, 0);

			} else if (isUp && !isDown && !isRight && !isLeft) {

				playerUpAni.drawAnimation(g, x, y, 0);

			}

		}

	}

	// Boundaries of sprite
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

	public static int getLastDir() {
		return lastDir;
	}

	public void setLastDir(int lastDir) {
		this.lastDir = lastDir;
	}

}
