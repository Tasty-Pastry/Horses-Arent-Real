package items;

// Imports
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Animation;
import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import entity.Daanish;
import entity.Nicc;
import entity.Nameless;
import game.Game;
import game.ID;

public class HorseEye extends GameObject {
	
	private BufferedImageLoader loader;

	// Constructor
	public HorseEye(int x, int y, ID id, Spritesheet sheet) {

		super(x, y, id);
		
		this.sheet=sheet;
		
		name = "Horse Eye";
		
		lore="This eye gives off an ominous aura… like a horse… aura? I wonder what would happen if you licked it.";
		
		itemDesc="...";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/HorseEye.png");
		
		keyItem = false;
		passive=false;
		vored=false;

	}

	public void update() {

	}
	
	public void use() {
		
		if (Game.getCharacter()==1) {
			
			for (int i = 0; i < 3; i++) {

				if (i == 0 || i == 1 || i == 2) {

					Daanish.playerRight[i] = sheet.getImage(i + 1, 1, 64, 64, 64, 64);
					Daanish.playerDown[i] = sheet.getImage(i + 1, 2, 64, 64, 64, 64);
					Daanish.playerUp[i] = sheet.getImage(i + 1, 3, 64, 64, 64, 64);
					Daanish.playerLeft[i] = sheet.getImage(i + 1, 4, 64, 64, 64, 64);

				}

				if (i == 0 || i == 1) {

					Daanish.playerRightAttack[i] = sheet.getImage(i + 1, 1, 64, 64, 64, 64);
					Daanish.playerLeftAttack[i] = sheet.getImage(i + 1, 4, 64, 64, 64, 64);
					Daanish.playerDownAttack[i] = sheet.getImage(i + 1, 2, 64, 64, 64, 64);
					Daanish.playerUpAttack[i] = sheet.getImage(i + 1, 3, 64, 64, 64, 64);

				}

			}

			// Initializing Animations
			Daanish.playerRightAni = new Animation(6, Daanish.playerRight);
			Daanish.playerLeftAni = new Animation(6, Daanish.playerLeft);
			Daanish.playerDownAni = new Animation(6, Daanish.playerDown);
			Daanish.playerUpAni = new Animation(6, Daanish.playerUp);

			Daanish.playerRightAttackAni = new Animation(4, Daanish.playerRightAttack);
			Daanish.playerLeftAttackAni = new Animation(4, Daanish.playerLeftAttack);
			Daanish.playerUpAttackAni = new Animation(4, Daanish.playerUpAttack);
			Daanish.playerDownAttackAni = new Animation(4, Daanish.playerDownAttack);
		
		}
			
		else if (Game.getCharacter()==2) {
			
			for (int i = 0; i < 3; i++) {

				if (i == 0 || i == 1 || i == 2) {

					Nicc.playerRight[i] = sheet.getImage(i + 1, 1, 64, 64, 64, 64);
					Nicc.playerDown[i] = sheet.getImage(i + 1, 2, 64, 64, 64, 64);
					Nicc.playerUp[i] = sheet.getImage(i + 1, 3, 64, 64, 64, 64);
					Nicc.playerLeft[i] = sheet.getImage(i + 1, 4, 64, 64, 64, 64);

				}

				if (i == 0 || i == 1) {

					Nicc.playerRightAttack[i] = sheet.getImage(i + 1, 1, 64, 64, 64, 64);
					Nicc.playerLeftAttack[i] = sheet.getImage(i + 1, 4, 64, 64, 64, 64);
					Nicc.playerDownAttack[i] = sheet.getImage(i + 1, 2, 64, 64, 64, 64);
					Nicc.playerUpAttack[i] = sheet.getImage(i + 1, 3, 64, 64, 64, 64);

				}

			}

			// Initializing Animations
			Nicc.playerRightAni = new Animation(6, Daanish.playerRight);
			Nicc.playerLeftAni = new Animation(6, Daanish.playerLeft);
			Nicc.playerDownAni = new Animation(6, Daanish.playerDown);
			Nicc.playerUpAni = new Animation(6, Daanish.playerUp);

			Nicc.playerRightAttackAni = new Animation(4, Daanish.playerRightAttack);
			Nicc.playerLeftAttackAni = new Animation(4, Daanish.playerLeftAttack);
			Nicc.playerUpAttackAni = new Animation(4, Daanish.playerUpAttack);
			Nicc.playerDownAttackAni = new Animation(4, Daanish.playerDownAttack);
			
		}
			
		else {
			
			for (int i = 0; i < 3; i++) {

				if (i == 0 || i == 1 || i == 2) {

					Nameless.playerRight[i] = sheet.getImage(i + 1, 1, 64, 64, 64, 64);
					Nameless.playerDown[i] = sheet.getImage(i + 1, 2, 64, 64, 64, 64);
					Nameless.playerUp[i] = sheet.getImage(i + 1, 3, 64, 64, 64, 64);
					Nameless.playerLeft[i] = sheet.getImage(i + 1, 4, 64, 64, 64, 64);

				}

				if (i == 0 || i == 1) {

					Nameless.playerRightAttack[i] = sheet.getImage(i + 1, 1, 64, 64, 64, 64);
					Nameless.playerLeftAttack[i] = sheet.getImage(i + 1, 4, 64, 64, 64, 64);
					Nameless.playerDownAttack[i] = sheet.getImage(i + 1, 2, 64, 64, 64, 64);
					Nameless.playerUpAttack[i] = sheet.getImage(i + 1, 3, 64, 64, 64, 64);

				}

			}

			// Initializing Animations
			Nameless.playerRightAni = new Animation(6, Daanish.playerRight);
			Nameless.playerLeftAni = new Animation(6, Daanish.playerLeft);
			Nameless.playerDownAni = new Animation(6, Daanish.playerDown);
			Nameless.playerUpAni = new Animation(6, Daanish.playerUp);

			Nameless.playerRightAttackAni = new Animation(4, Daanish.playerRightAttack);
			Nameless.playerLeftAttackAni = new Animation(4, Daanish.playerLeftAttack);
			Nameless.playerUpAttackAni = new Animation(4, Daanish.playerUpAttack);
			Nameless.playerDownAttackAni = new Animation(4, Daanish.playerDownAttack);
			
		}

		vored=true;
		
	}

	// Draw crate
	public void draw(Graphics g) {

		g.drawImage(sprite,x,y,null);

	}

	// Get boundaries of crate
	public Rectangle getBounds() {

		return new Rectangle(x, y, 32, 32);

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
