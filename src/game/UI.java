package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animation.Animation;
import animation.Spritesheet;

public class UI {

	private BufferedImage[] daanishHealthBar = new BufferedImage[30];
	private BufferedImage[] nickHealthBar = new BufferedImage[4];
	private BufferedImage[] namelessHealthBar = new BufferedImage[15];

	private Animation daanishHealthAni;
	private Animation nickHealthAni;
	private Animation namelessHealthAni;

	private Spritesheet sheet;

	public UI(Spritesheet sheet) {

		this.sheet = sheet;

		for (int i = 0; i < 30; i++) {

			daanishHealthBar[i] = sheet.getImage(i + 1, 1, 340, 375, 340, 375);

			if (i < 4) {

				nickHealthBar[i] = sheet.getImage(i + 1, 2, 333, 157, 333, 375);

			}

			if (i < 15) {

				namelessHealthBar[i] = sheet.getImage(i + 1, 3, 375.5, 185, 375.5, 375);

			}

		}

		daanishHealthAni = new Animation(6, daanishHealthBar);
		nickHealthAni = new Animation(6, nickHealthBar);
		namelessHealthAni = new Animation(6, namelessHealthBar);

	}

	public void update() {

		if (Game.getCharacter() == 1) {

			daanishHealthAni.runAnimation();

		} else if (Game.getCharacter() == 2) {

			nickHealthAni.runAnimation();

		} else if (Game.getCharacter() == 3) {

			namelessHealthAni.runAnimation();

		}

	}

	public void draw(Graphics g) {

		if (Game.getCharacter() == 1) {

			daanishHealthAni.drawAnimation(g, 0, 100, 0);

		} else if (Game.getCharacter() == 2) {

			nickHealthAni.drawAnimation(g, 15, 490, 0);

		} else if (Game.getCharacter() == 3) {

			namelessHealthAni.drawAnimation(g, 0, 300, 0);

		}

	}

}
