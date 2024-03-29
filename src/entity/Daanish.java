package entity;

// Imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import animation.Animation;
import animation.Camera;
import animation.Spritesheet;
import game.AudioPlayer;
import game.Game;
import game.Handler;
import game.ID;
import game.Inventory;
import game.UI;
import items.MothmanUse;
import items.TimothyUse;
import states.Level1State;

public class Daanish extends PlayerObject {

	private BufferedImage[] health = new BufferedImage[30];
	private Spritesheet mothmanSheet;
	private Spritesheet timothySheet;

	private HashMap<String, AudioPlayer> SFX;
	private static boolean special;

	private static Rectangle middleBox;
	private static Rectangle[] sideMiddleBoxes;
	private static Rectangle[] sideTopBoxes;
	private static Rectangle topBox;
	private static Rectangle bottomBox;
	private static Rectangle leftBox;
	private static Rectangle rightBox;

	private static int numberOfBoxes;

	private Animation dishMiddleAni;
	private static boolean initFinish;

	private Level1State l1s;

	// Constructor
	public Daanish(int x, int y, ID id, Handler handler, Spritesheet sheet, Spritesheet healthBar, Inventory inv,
			Spritesheet mothmanSheet, Spritesheet timothySheet, HashMap SFX, Animation dishMiddleAni, Level1State l1s) {

		super(x, y, id, handler, sheet, healthBar, inv);

		this.SFX = SFX;

		this.mothmanSheet = mothmanSheet;
		this.timothySheet = timothySheet;

		this.dishMiddleAni = dishMiddleAni;

		this.l1s = l1s;

		setMiddleBox(new Rectangle(0, 0, 64, 64));
		setBottomBox(new Rectangle(0, 0, 64, 64));
		setTopBox(new Rectangle(0, 0, 64, 64));
		setLeftBox(new Rectangle(0, 0, 64, 64));
		setRightBox(new Rectangle(0, 0, 64, 64));

		setSideMiddleBoxes(new Rectangle[5]);
		setSideTopBoxes(new Rectangle[5]);

		// Initializing Sprite Sheets
		for (int i = 0; i < 3; i++) {

			if (i == 0 || i == 1 || i == 2) {

				playerRight[i] = sheet.getImage(i + 1, 1, 100, 64, 100, 64);
				playerDown[i] = sheet.getImage(i + 1, 2, 100, 64, 100, 64);
				playerUp[i] = sheet.getImage(i + 1, 3, 100, 64, 100, 64);
				playerLeft[i] = sheet.getImage(i + 1, 4, 100, 64, 100, 64);

			}

			if (i == 0 || i == 1) {

				playerRightAttack[i] = sheet.getImage(i + 5, 1, 100, 64, 100, 64);
				playerLeftAttack[i] = sheet.getImage(i + 5, 4, 100, 64, 100, 64);
				playerDownAttack[i] = sheet.getImage(i + 5, 2, 100, 64, 100, 64);
				playerUpAttack[i] = sheet.getImage(i + 5, 3, 100, 64, 100, 64);

			}

		}

		// Initializing Animations
		playerRightAni = new Animation(6, playerRight);
		playerLeftAni = new Animation(6, playerLeft);
		playerDownAni = new Animation(6, playerDown);
		playerUpAni = new Animation(6, playerUp);

		playerRightAttackAni = new Animation(4, playerRightAttack);
		playerLeftAttackAni = new Animation(4, playerLeftAttack);
		playerUpAttackAni = new Animation(4, playerUpAttack);
		playerDownAttackAni = new Animation(4, playerDownAttack);

		setInitFinish(true);

	}

	public void update() {

		// Check if the player is colliding with an object
		collision();

		if (!special) {

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

			} else if (!handler.isLeft()) { // Decrease vel when the player is moving right but the right key isn't
											// being
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

		} else {

			velocityX = 0;
			velocityY = 0;

			if (Game.daanishLevel % 2 == 0) {

				setNumberOfBoxes(Game.daanishLevel);

			} else {

				setNumberOfBoxes(Game.daanishLevel - 1);

			}

			setSideMiddleBoxes(new Rectangle[getNumberOfBoxes() * 2]);
			setSideTopBoxes(new Rectangle[getNumberOfBoxes() * 2]);

			if (getNumberOfBoxes() > 1) {

				for (int i = 0; i < getNumberOfBoxes(); i++) {

					if (i <= (getNumberOfBoxes() / 2) - 1) {

						getSideMiddleBoxes()[i] = new Rectangle((x + 15) - (64 * i) - 64, y, 64, 64);

						getSideTopBoxes()[i] = new Rectangle(x + 15, y - (64 * i) - 64, 64, 64);

					} else {

						getSideMiddleBoxes()[i] = new Rectangle((x + 15) + (64 * i) - (32 * getNumberOfBoxes() - 64), y,
								64, 64);

						getSideTopBoxes()[i] = new Rectangle(x + 15, y + (64 * i) - (32 * getNumberOfBoxes() - 64), 64,
								64);

					}

				}

			}

			getMiddleBox().x = x + 15;
			getMiddleBox().y = y;

			getTopBox().x = x + 15;
			getTopBox().y = y - (64 * (getNumberOfBoxes() - ((getNumberOfBoxes() / 2) - 1)));

			getBottomBox().x = x + 15;
			getBottomBox().y = y + (64 * (getNumberOfBoxes() - ((getNumberOfBoxes() / 2) - 1)));

			getLeftBox().x = (x + 15) - (64 * (getNumberOfBoxes() - ((getNumberOfBoxes() / 2) - 1)));
			getLeftBox().y = y;

			getRightBox().x = (x + 15) + (64 * (getNumberOfBoxes() - ((getNumberOfBoxes() / 2) - 1)));
			getRightBox().y = y;

			if (UI.getDishCutIn().getRanOnce()) {

				dishMiddleAni.runAnimation();

				if (dishMiddleAni.getRanOnce()) {

					dishMiddleAni.reset();

				}

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
			if (temp.getId() == ID.Block || temp.getId() == ID.BottomBlock || temp.getId() == ID.BottomLeftCornerBlock
					|| temp.getId() == ID.BottomRightCornerBlock || temp.getId() == ID.TopBlock
					|| temp.getId() == ID.TopLeftCornerBlock || temp.getId() == ID.TopRightCornerBlock
					|| temp.getId() == ID.LeftBlock || temp.getId() == ID.RightBlock) {

				// Collision logic
				if (!slide((int) (x + velocityX), y, getBounds(), temp.getBounds())) {

					if (x > temp.x)
						x += 1;
					else
						x -= 1;
					velocityX = 0;

				}

				if (!slide(x, (int) (y + velocityY), getBounds(), temp.getBounds())) {

					if (y > temp.y)
						y += 1;
					else
						y -= 1;
					velocityY = 0;

				}

			}

			// Door collision
			if (temp.getId() == ID.DoorBottom) {

				// Checks if all the enemies in the room have been killed
				if (enemyArray[Game.playerYPos][Game.playerXPos] != Game.getKillCount()
						|| enemyArray[Game.playerYPos][Game.playerXPos] == Game.getKillCount()) {

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

						if (x > temp.x)
							x += 1;
						else
							x -= 1;
						velocityX = 0;

					}

					if (!slide(x, (int) (y + velocityY), getBounds(), temp.getBounds())) {

						if (y > temp.y)
							y += 1;
						else
							y -= 1;
						velocityY = 0;

					}

				}

			}

			if (temp.getId() == ID.DoorSide) {

				if (enemyArray[Game.playerYPos][Game.playerXPos] != Game.getKillCount()
						|| enemyArray[Game.playerYPos][Game.playerXPos] == Game.getKillCount()) {

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

						if (x > temp.x)
							x += 1;
						else
							x -= 1;
						velocityX = 0;

					}

					if (!slide(x, (int) (y + velocityY), getBounds(), temp.getBounds())) {

						if (y > temp.y)
							y += 1;
						else
							y -= 1;
						velocityY = 0;

					}

				}

			}

			if (temp.getId() == ID.EnemyBullet) {

				// Checks if an enemy bullet hit the player
				if (getBounds().intersects(temp.getBounds())) {

					// Subtract 5 hp
					Game.daanishHealth -= 5;

					velocityX += temp.getVelocityX();
					velocityY += temp.getVelocityY();

					if (x <= Camera.getX() + 64) {

						x = (int) (Camera.getX() + 70);

						velocityX = 0;

					}

					if (x >= Camera.getX() + 960) {

						x = (int) Camera.getX() + 954;
						velocityX = 0;

					}

					if (y <= Camera.getY() + 64) {

						y = (int) (Camera.getY() + 70);
						velocityY = 0;

					}

					if (y >= Camera.getY() + 576) {

						y = (int) (Camera.getY() + 570);
						velocityY = 0;

					}

					SFX.get("Hit").play(false);

					// Remove the bullet from the handler
					handler.removeObject(temp);

				}

				// Remove all enemy bullets if the camera is moving
				if (Camera.getCamMove()) {

					handler.removeObject(temp);

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

						handler.addObject(new MothmanUse(x + 15, y, ID.MothmanUse, handler, mothmanSheet));

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

						handler.addObject(new TimothyUse(x, y, ID.TimothyUse, handler, timothySheet));

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

			// Remove player from handler if they die
			if (Game.daanishHealth <= 0) {

				Game.daanishHealth = 1;
				Game.dishAlive = false;

				l1s.switchCharacter();

				handler.removeObject(this);

			}

		}

	}

	public void draw(Graphics g) {

		if (special) {

			if (UI.getDishCutIn().getRanOnce()) {

				dishMiddleAni.drawAnimation(g, Daanish.getMiddleBox().x, Daanish.getMiddleBox().y, 0);

			}

		}

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

		// Basic GUI displaying health and ammo
		g.setColor(Color.RED);

		g.drawString("Ammo: " + Game.ammo, (int) (100 + Camera.getX()), (int) (120 + Camera.getY()));

	}

	// Boundaries of sprite
	public Rectangle getBounds() {

		return new Rectangle(x, y, 62, 62);

	}

	@Override
	public String getName() {

		return name;

	}

	@Override
	public BufferedImage getSprite() {

		return sprite;
	}

	public static void setSpecialMove(boolean specials) {

		setSpecial(specials);

	}

	public static boolean isSpecial() {
		return special;
	}

	public static void setSpecial(boolean special) {
		Daanish.special = special;
	}

	public static Rectangle getTopBox() {
		return topBox;
	}

	public void setTopBox(Rectangle topBox) {
		this.topBox = topBox;
	}

	public static int getNumberOfBoxes() {
		return numberOfBoxes;
	}

	public void setNumberOfBoxes(int numberOfBoxes) {
		this.numberOfBoxes = numberOfBoxes;
	}

	public static Rectangle getBottomBox() {
		return bottomBox;
	}

	public void setBottomBox(Rectangle bottomBox) {
		this.bottomBox = bottomBox;
	}

	public static Rectangle getLeftBox() {
		return leftBox;
	}

	public void setLeftBox(Rectangle leftBox) {
		this.leftBox = leftBox;
	}

	public static Rectangle getRightBox() {
		return rightBox;
	}

	public void setRightBox(Rectangle rightBox) {
		this.rightBox = rightBox;
	}

	public static Rectangle[] getSideTopBoxes() {
		return sideTopBoxes;
	}

	public void setSideTopBoxes(Rectangle[] sideTopBoxes) {
		this.sideTopBoxes = sideTopBoxes;
	}

	public static Rectangle[] getSideMiddleBoxes() {
		return sideMiddleBoxes;
	}

	public void setSideMiddleBoxes(Rectangle[] sideMiddleBoxes) {
		this.sideMiddleBoxes = sideMiddleBoxes;
	}

	public static Rectangle getMiddleBox() {
		return middleBox;
	}

	public void setMiddleBox(Rectangle middleBox) {
		this.middleBox = middleBox;
	}

	public static boolean isInitFinish() {
		return initFinish;
	}

	public void setInitFinish(boolean initFinish) {
		this.initFinish = initFinish;
	}

}
