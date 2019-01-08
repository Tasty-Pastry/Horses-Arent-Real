package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animation.Animation;
import animation.BufferedImageLoader;
import animation.Spritesheet;

public class UI {

	private BufferedImage[] daanishHealthBar = new BufferedImage[30];
	private BufferedImage[] nickHealthBar = new BufferedImage[4];
	private BufferedImage[] namelessHealthBar = new BufferedImage[15];

	private Animation[] hpBarAni;

	private BufferedImage[] hpBarFill = new BufferedImage[3];

	private Spritesheet sheet;
	private Spritesheet hpBarSheet;

	public UI(Spritesheet sheet) {

		this.sheet = sheet;

		BufferedImageLoader loader = new BufferedImageLoader();

		hpBarSheet = new Spritesheet(loader.loadImage("/HealthFill.png"));

		for (int i = 0; i < 30; i++) {

			daanishHealthBar[i] = sheet.getImage(i + 1, 1, 340, 375, 340, 375);

			if (i < 3) {

				hpBarFill[i] = hpBarSheet.getImage(i + 1, 1, 228, 79, 228, 79);

			}

			if (i < 4) {

				nickHealthBar[i] = sheet.getImage(i + 1, 2, 333, 157, 333, 375);

			}

			if (i < 15) {

				namelessHealthBar[i] = sheet.getImage(i + 1, 3, 375.5, 185, 375.5, 375);

			}

		}

		hpBarAni = new Animation[] { new Animation(6, daanishHealthBar), new Animation(6, nickHealthBar),
				new Animation(6, namelessHealthBar) };

	}

	public void update() {

		if (Game.getCharacter() == 1) {

			hpBarAni[0].runAnimation();

		} else if (Game.getCharacter() == 2) {

			hpBarAni[1].runAnimation();

		} else if (Game.getCharacter() == 3) {

			hpBarAni[2].runAnimation();

		}

	}

	public void draw(Graphics g) {

		if (Game.getCharacter() == 1) {

			hpBarAni[0].drawAnimation(g, 8, 254, 0);
			g.drawImage(hpBarFill[0].getSubimage(0, 0, 228 - (228 - Game.daanishHealth), 79), 113, 531, null);

		} else if (Game.getCharacter() == 2) {

			hpBarAni[1].drawAnimation(g, 15, 490, 0);
			g.drawImage(hpBarFill[1].getSubimage(0, 0, 228 - (228 - Game.nickHealth), 79), 114, 531, null);

		} else if (Game.getCharacter() == 3) {

			hpBarAni[2].drawAnimation(g, -27, 466, 0);
			g.drawImage(hpBarFill[2].getSubimage(0, 0, 228 - (228 - Game.namelessHealth), 79), 113, 532, null);

		}

	}

}
