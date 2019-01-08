package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animation.Animation;
import animation.BufferedImageLoader;
import animation.Camera;
import animation.Spritesheet;
import entity.GameObject;

public class UI {

	private BufferedImage[] daanishHealthBar = new BufferedImage[30];
	private BufferedImage[] nickHealthBar = new BufferedImage[4];
	private BufferedImage[] namelessHealthBar = new BufferedImage[15];

	private BufferedImage[] daanishTransition = new BufferedImage[15];
	private BufferedImage[] nickTransition = new BufferedImage[19];

	private Animation[] hpBarAni;
	private Animation[] transitionAni;

	private BufferedImage[] hpBarFill = new BufferedImage[3];

	private Spritesheet sheet;
	private Spritesheet hpBarSheet;

	private Spritesheet daanishTransitionSheet;
	private Spritesheet nickTransitionSheet;

	private Handler handler;

	private boolean transition;

	public UI(Spritesheet sheet, Handler handler) {

		this.sheet = sheet;

		this.handler = handler;

		BufferedImageLoader loader = new BufferedImageLoader();

		hpBarSheet = new Spritesheet(loader.loadImage("/HealthFill.png"));

		daanishTransitionSheet = new Spritesheet(loader.loadImage("/Dish Transition.png"));
		nickTransitionSheet = new Spritesheet(loader.loadImage("/Nick Transition.png"));

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

				daanishTransition[i] = daanishTransitionSheet.getImage(i + 1, 1, 325, 325, 325, 325);

			}

			if (i < 19) {

				nickTransition[i] = nickTransitionSheet.getImage(i + 1, 1, 125, 232, 125, 232);

			}

		}

		hpBarAni = new Animation[] {

				new Animation(6, daanishHealthBar), new Animation(6, nickHealthBar), new Animation(6, namelessHealthBar)

		};

		transitionAni = new Animation[] {

				new Animation(3, daanishTransition), new Animation(3, nickTransition)

		};

	}

	public void update() {

		if (isTransition()) {

			if (Game.getCharacter() == 1) {

				transitionAni[0].toggleAnimation(false);

				transitionAni[0].runAnimation();

				if (transitionAni[0].getRanOnce()) {

					setTransition(false);

					transitionAni[0].toggleAnimation(true);

				}

			} else if (Game.getCharacter() == 2) {

				transitionAni[1].toggleAnimation(false);

				transitionAni[1].runAnimation();

				if (transitionAni[1].getRanOnce()) {

					setTransition(false);

					transitionAni[1].toggleAnimation(true);

				} else if (Game.getCharacter() == 3) {

					// transitionAni[3].runAnimation();

					// if (transitionAni[3].getRanOnce()) {

					setTransition(false);

					// transitionAni[3].toggleAnimation(true);

					// }

				}

			}

		}

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

		for (int i = 0; i < handler.getObject().size(); i++) {

			GameObject temp = handler.getObject().get(i);

			if (isTransition() && temp.getId() == ID.Player) {

				if (Game.getCharacter() == 1) {

					transitionAni[0].drawAnimation(g,
							(temp.getX() - (daanishTransition[0].getWidth() / 2) + 30) - Camera.getX(),
							(temp.getY() - (daanishTransition[0].getHeight() / 2) + 25) - Camera.getY(), 0);

				} else if (Game.getCharacter() == 2) {

					transitionAni[1].drawAnimation(g,
							(temp.getX() - (nickTransition[0].getWidth() / 2) + 32) - Camera.getX(),
							(temp.getY() - (nickTransition[0].getHeight() / 2)) - Camera.getY(), 0);

				}

			}

		}

	}

	public boolean isTransition() {
		return transition;
	}

	public void setTransition(boolean transition) {
		this.transition = transition;
	}

}
