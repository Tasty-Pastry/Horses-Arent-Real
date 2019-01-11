package items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Animation;
import animation.BufferedImageLoader;
import animation.Camera;
import animation.Spritesheet;
import entity.Bullet;
import entity.GameObject;
import game.Game;
import game.Handler;
import game.ID;

public class MothmanUse extends GameObject {

	private BufferedImageLoader loader;

	private BufferedImage[] mothman = new BufferedImage[24];

	private Animation mothmanAni;

	private Spritesheet sheet;

	private Handler handler;

	private int circleX;

	private int circleY;

	private double theta;

	public MothmanUse(int x, int y, ID id, Handler handler, Spritesheet sheet) {

		super(x, y, id);

		loader = new BufferedImageLoader();

		this.sheet = sheet;

		for (int i = 0; i < 24; i++) {

			mothman[i] = sheet.getImage(i + 1, 1, 116, 93, 116, 93);

		}

		this.handler = handler;

		mothmanAni = new Animation(3, mothman);

	}

	public void update() {

		if (Game.getCharacter() == 3) {

			theta += 0.01;

			circleX += 2.5 * Math.cos(theta * Math.PI);
			circleY += 2.5 * Math.sin(theta * Math.PI);

			mothmanAni.runAnimation();

			for (int i = 0; i < handler.getObject().size(); i++) {

				// Creates a game object and sets it to the current handler object
				GameObject temp = handler.getObject().get(i);

				// If the object isnt null, and if the object is either a block, or a door

				if (temp != null) {

					if (temp.getId() == ID.Player) {

						x = temp.getX() + circleX - 20;
						y = temp.getY() + circleY - 60;

					}

					if (temp.getId() == ID.Enemy) {

						// Checks if the player is in range
						if (getEvenBiggerBoiBounds().intersects(temp.getBounds())) {

							// Checks if the camera isnt moving, if the enemy is allowed to move and can
							// only shoot once every 25 frames
							if (Game.getCount() % 50 == 0 && !Camera.getCamMove()) {

								// Adds a bullet
								handler.getObject().add(new Bullet(getX() + 27, getY() + 64, ID.Bullet, handler,
										temp.getX(), temp.getY(), 10, Color.RED, null));

							}

						}

					}

				}

			}

		}

	}

	// Draw crate
	public void draw(Graphics g) {

		if (Game.getCharacter() == 3) {

			mothmanAni.drawAnimation(g, x, y, 0);

		}

	}

	// Get boundaries of crate
	public Rectangle getBounds() {

		return new Rectangle(x, y, 32, 32);

	}

	public Rectangle getEvenBiggerBoiBounds() {

		return new Rectangle(x - 256, y - 256, 576, 576);

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
