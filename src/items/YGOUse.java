package items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Camera;
import animation.Spritesheet;
import entity.Bullet;
import entity.GameObject;
import game.Game;
import game.Handler;
import game.ID;

public class YGOUse extends GameObject {

	private BufferedImageLoader loader;
	private Handler handler;

	public YGOUse(int x, int y, ID id, Handler handler) {

		super(x, y, id);
		
		this.handler=handler;

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/DannysSoul.png");

	}

	public void update() {

		if (Game.getCharacter() == 1) {

			x += velocityX;
			y += velocityY;

			for (int i = 0; i < handler.getObject().size(); i++) {

				// Creates a game object and sets it to the current handler object
				GameObject temp = handler.getObject().get(i);

				// If the object isnt null, and if the object is either a block, or a door

				if (temp != null) {

					if (temp.getId() == ID.Player) {

						int diffX = temp.getX() - x - (sprite.getWidth() / 2) - 20;
						int diffY = temp.getY() - y - (sprite.getHeight() / 2) - 20;

						float angle = (float) Math.atan2(diffY, diffX);

						velocityX = (float) (4.5 * Math.cos(angle));
						velocityY = (float) (4.5 * Math.sin(angle));

						if ((diffX <= 0) && (diffX >= -4)) {

							velocityX = 0;

						}

						if (diffY <= 2 && diffY >= -2) {

							velocityY = 0;

						}

					}

					if (temp.getId() == ID.Enemy) {

						// Checks if the player is in range
						if (getEvenBiggerBoiBounds().intersects(temp.getBounds())) {

							// Checks if the camera isnt moving, if the enemy is allowed to move and can
							// only shoot once every 25 frames
							if (Game.getCount() % 50 == 0 && !Camera.getCamMove()) {

								// Adds a bullet
								handler.getObject().add(new Bullet(getX() + 27, getY() + 64, ID.Bullet, handler,
										temp.getX(), temp.getY(), 10, Color.RED, null, 0, 5));

							}

						}

					}

				}

			}

		}

	}

	// Draw crate
	public void draw(Graphics g) {

		g.drawImage(sprite,x,y,null);

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
