package items;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Camera;
import game.ID;
import entity.Bullet;
import entity.GameObject;
import game.Game;
import game.Handler;

public class MothmanUse extends GameObject {

	private BufferedImageLoader loader;
	private Handler handler;

	public MothmanUse (int x, int y, ID id, Handler handler) {

		super(x, y, id);
		
		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/Mothman.png");
		
		this.handler = handler;
	
	}
	
	public void update() {
		
		for (int i = 0; i < handler.getObject().size(); i++) {

			// Creates a game object and sets it to the current handler object
			GameObject temp = handler.getObject().get(i);

			// If the object isnt null, and if the object is either a block, or a door

			if (temp != null) {

				if (temp.getId() == ID.Player) {
					
					x=temp.getX()-80;
					y=temp.getY()-20;
					
				}
				
				if (temp.getId() == ID.Enemy) {

					// Checks if the player is in range
					if (getEvenBiggerBoiBounds().intersects(temp.getBounds())) {

						// Checks if the camera isnt moving, if the enemy is allowed to move and can
						// only shoot once every 25 frames
						if (Game.getCount() % 50 == 0 && !Camera.getCamMove()) {

							// Adds a bullet
							handler.getObject().add(new Bullet(getX() + 27, getY() + 64, ID.Bullet, handler,
									temp.getX(), temp.getY(), 10, Color.RED));

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
