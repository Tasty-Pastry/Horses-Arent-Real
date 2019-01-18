package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import animation.Animation;
import animation.BufferedImageLoader;
import animation.Camera;
import animation.Spritesheet;
import entity.GameObject;

public class UI {

	private BufferedImage[] daanishHealthBar = new BufferedImage[30];
	private BufferedImage[] nickHealthBar = new BufferedImage[4];
	private BufferedImage[] namelessHealthBar = new BufferedImage[15];

	private BufferedImage[] daanishStartTransition = new BufferedImage[9];
	private BufferedImage[] daanishTransition = new BufferedImage[6];

	private BufferedImage[] nickStartTransition = new BufferedImage[10];
	private BufferedImage[] nickTransition = new BufferedImage[9];

	private BufferedImage[] namelessStartTransition = new BufferedImage[13];
	private BufferedImage[] namelessTransition = new BufferedImage[20];

	private Animation[] hpBarAni;
	private Animation[] transitionAni;

	private BufferedImage[] hpBarFill = new BufferedImage[3];

	private Spritesheet sheet;
	private Spritesheet hpBarSheet;

	private Spritesheet daanishTransitionSheet;
	private Spritesheet nickTransitionSheet;
	private Spritesheet namelessTransitionSheet;

	private BufferedImage EPBar;

	private Handler handler;

	private HashMap<String, AudioPlayer> SFX;

	private boolean transition;

	private boolean charSwitch;

	private boolean end;
	private boolean check;
	private boolean check2;

	public UI(Spritesheet sheet, Handler handler) {

		this.sheet = sheet;

		this.handler = handler;

		BufferedImageLoader loader = new BufferedImageLoader();

		hpBarSheet = new Spritesheet(loader.loadImage("/HealthFill.png"));

		EPBar = loader.loadImage("/EP Fill.png");

		SFX = new HashMap<String, AudioPlayer>();

		SFX.put("DishStartTransition", new AudioPlayer("/Dish Transition Start Explosion.wav", 1));
		SFX.put("DishTransition", new AudioPlayer("/Dish Transition Explosion.wav", 1));
		SFX.put("NickStartTransition", new AudioPlayer("/Nick Start Transition.wav", 1));
		SFX.put("NickTransition", new AudioPlayer("/Nick Transition.wav", 1));

		daanishTransitionSheet = new Spritesheet(loader.loadImage("/Dish Transition.png"));
		nickTransitionSheet = new Spritesheet(loader.loadImage("/Nick Transition.png"));
		namelessTransitionSheet = new Spritesheet(loader.loadImage("/Nameless Transition.png"));

		for (int i = 0; i < 33; i++) {

			if (i < 13) {

				namelessStartTransition[i] = namelessTransitionSheet.getImage(i + 1, 1, 225, 225, 225, 225);

			} else {

				namelessTransition[i - 13] = namelessTransitionSheet.getImage(i + 1, 1, 225, 225, 225, 225);

			}

			if (i < 30) {

				daanishHealthBar[i] = sheet.getImage(i + 1, 1, 340, 375, 340, 375);

			}

			if (i < 3) {

				hpBarFill[i] = hpBarSheet.getImage(i + 1, 1, 228, 79, 228, 79);

			}

			if (i < 4) {

				nickHealthBar[i] = sheet.getImage(i + 1, 2, 333, 157, 333, 375);

			}

			if (i < 15) {

				namelessHealthBar[i] = sheet.getImage(i + 1, 3, 375.5, 185, 375.5, 375);

				if (i < 9) {

					daanishStartTransition[i] = daanishTransitionSheet.getImage(i + 1, 1, 325, 325, 325, 325);

				} else {

					daanishTransition[i - 9] = daanishTransitionSheet.getImage(i + 1, 1, 325, 325, 325, 325);

				}

			}

			if (i < 19) {

				if (i < 10) {

					nickStartTransition[i] = nickTransitionSheet.getImage(i + 1, 1, 125, 232, 125, 232);

				} else {

					nickTransition[i - 10] = nickTransitionSheet.getImage(i + 1, 1, 125, 232, 125, 232);

				}

			}

		}

		hpBarAni = new Animation[] {

				new Animation(6, daanishHealthBar), new Animation(6, nickHealthBar), new Animation(6, namelessHealthBar)

		};

		transitionAni = new Animation[] {

				new Animation(3, daanishStartTransition), new Animation(3, daanishTransition),
				new Animation(3, nickStartTransition), new Animation(3, nickTransition),
				new Animation(3, namelessStartTransition), new Animation(3, namelessTransition),

		};

	}

	public void update() {

		if (isTransition()) {

			if (Game.getCharacter() == 1) {

				if (end) {

					transitionAni[0].toggleAnimation(false);
					transitionAni[0].reset();

					end = false;

				}

				transitionAni[0].runAnimation();

				System.out.println(!check);

				if (!check) {

					SFX.get("DishStartTransition").play(false);

					check = true;

				}

				if (transitionAni[0].getRanOnce()) {

					setCharSwitch(true);

					transitionAni[1].toggleAnimation(false);
					transitionAni[1].runAnimation();

					if (!check2) {

						SFX.get("DishTransition").play(false);

						check2 = true;

					}

					if (transitionAni[1].getRanOnce()) {

						setTransition(false);

						transitionAni[0].toggleAnimation(true);
						transitionAni[1].toggleAnimation(true);

						end = true;

						check = false;
						check2 = false;

					}

				}

			} else if (Game.getCharacter() == 2) {

				if (end) {

					transitionAni[2].toggleAnimation(false);
					transitionAni[2].reset();

					end = false;

				}

				transitionAni[2].runAnimation();

				if (!check) {

					SFX.get("NickStartTransition").play(false);

					check = true;

				}

				if (transitionAni[2].getCount() == 9 && !check2) {

					SFX.get("NickTransition").play(false);

					check2 = true;

				}

				if (transitionAni[2].getRanOnce()) {

					setCharSwitch(true);

					transitionAni[3].toggleAnimation(false);
					transitionAni[3].runAnimation();

					if (transitionAni[3].getRanOnce()) {

						setTransition(false);

						transitionAni[2].toggleAnimation(true);
						transitionAni[3].toggleAnimation(true);

						end = true;

						check = false;
						check2 = false;

					}

				}

			} else if (Game.getCharacter() == 3) {

				if (end) {

					transitionAni[4].toggleAnimation(false);
					transitionAni[4].reset();

					end = false;

				}

				transitionAni[4].runAnimation();

				if (transitionAni[4].getRanOnce()) {

					setCharSwitch(true);

					transitionAni[5].toggleAnimation(false);
					transitionAni[5].runAnimation();

					if (transitionAni[5].getRanOnce()) {

						setTransition(false);

						transitionAni[4].toggleAnimation(true);
						transitionAni[5].toggleAnimation(true);

						end = true;

					}

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

		for (int i = 0; i < handler.getObject().size(); i++) {

			GameObject temp = handler.getObject().get(i);

			if (isTransition() && temp.getId() == ID.Player) {

				if (Game.getCharacter() == 1) {

					if (!isCharSwitch()) {

						transitionAni[0].drawAnimation(g,
								(temp.getX() - (daanishStartTransition[0].getWidth() / 2) + 30) - Camera.getX(),
								(temp.getY() - (daanishStartTransition[0].getHeight() / 2) + 25) - Camera.getY(), 0);

					} else {

						transitionAni[1].drawAnimation(g,
								(temp.getX() - (daanishTransition[0].getWidth() / 2) + 30) - Camera.getX(),
								(temp.getY() - (daanishTransition[0].getHeight() / 2) + 25) - Camera.getY(), 0);

					}

				} else if (Game.getCharacter() == 2) {

					if (!isCharSwitch()) {

						transitionAni[2].drawAnimation(g,
								(temp.getX() - (nickStartTransition[0].getWidth() / 2) + 32) - Camera.getX(),
								(temp.getY() - (nickStartTransition[0].getHeight() / 2)) - Camera.getY(), 0);

					} else {

						transitionAni[3].drawAnimation(g,
								(temp.getX() - (nickTransition[0].getWidth() / 2) + 32) - Camera.getX(),
								(temp.getY() - (nickTransition[0].getHeight() / 2)) - Camera.getY(), 0);

					}

				} else if (Game.getCharacter() == 3) {

					if (!isCharSwitch()) {

						transitionAni[4].drawAnimation(g,
								(temp.getX() - (namelessStartTransition[0].getWidth() / 2) + 18) - Camera.getX(),
								(temp.getY() - (namelessStartTransition[0].getHeight() / 2) - 19) - Camera.getY(), 0);

					} else {

						transitionAni[5].drawAnimation(g,
								(temp.getX() - (namelessTransition[0].getWidth() / 2) + 18) - Camera.getX(),
								(temp.getY() - (namelessTransition[0].getHeight() / 2) - 19) - Camera.getY(), 0);

					}

				}

			}

		}

		if (Game.getCharacter() == 1) {

			hpBarAni[0].drawAnimation(g, 8, 254, 0);
			g.drawImage(hpBarFill[0].getSubimage(0, 0, 228 - (228 - Game.daanishHealth), 79), 113, 531, null);

			g.drawImage(EPBar.getSubimage(0, 0, 234 - (234 - Game.daanishEP), 15), 103, 596, null);

		} else if (Game.getCharacter() == 2) {

			hpBarAni[1].drawAnimation(g, 15, 490, 0);
			g.drawImage(hpBarFill[1].getSubimage(0, 0, 228 - (228 - Game.nickHealth), 79), 114, 531, null);

			g.drawImage(EPBar.getSubimage(0, 0, 234 - (234 - Game.nickEP), 15), 103, 596, null);

		} else if (Game.getCharacter() == 3) {

			hpBarAni[2].drawAnimation(g, -27, 466, 0);
			g.drawImage(hpBarFill[2].getSubimage(0, 0, 228 - (228 - Game.namelessHealth), 79), 113, 532, null);

			g.drawImage(EPBar.getSubimage(0, 0, 234 - (234 - Game.namelessEP), 15), 103, 596, null);

		}

	}

	public boolean isTransition() {
		return transition;
	}

	public void setTransition(boolean transition) {
		this.transition = transition;
	}

	public boolean isCharSwitch() {
		return charSwitch;
	}

	public void setCharSwitch(boolean charSwitch) {
		this.charSwitch = charSwitch;
	}

}
