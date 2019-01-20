package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import animation.Animation;
import animation.BufferedImageLoader;
import animation.Camera;
import animation.Spritesheet;
import entity.Daanish;
import entity.GameObject;
import entity.Nicc;

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
	private Spritesheet nickSpecialSheet;

	private BufferedImage[] nickTop = new BufferedImage[6];
	private BufferedImage[] nickBottom = new BufferedImage[6];
	private BufferedImage[] nickLeft = new BufferedImage[6];
	private BufferedImage[] nickRight = new BufferedImage[6];

	private BufferedImage EPBar;

	private Handler handler;

	private HashMap<String, AudioPlayer> SFX;

	private boolean transition;

	private boolean charSwitch;

	private boolean end;
	private boolean check;
	private boolean check2;

	private static Animation dishCutIn;
	private static Animation nickCutIn;
	private Animation[] dishSpecialAni;
	private Animation[] nickSpecialAni;
	private boolean check3;
	private Font font;
	private boolean check4;

	public UI(Spritesheet sheet, Handler handler, Animation dishCutIn, Animation[] dishSpecialAnimation,
			Animation nickCutIn) {

		this.sheet = sheet;

		this.handler = handler;

		this.setDishCutIn(dishCutIn);

		this.setNickCutIn(nickCutIn);

		this.setDishSpecialAni(dishSpecialAnimation);

		BufferedImageLoader loader = new BufferedImageLoader();

		hpBarSheet = new Spritesheet(loader.loadImage("/HealthFill.png"));

		EPBar = loader.loadImage("/EP Fill.png");

		font = new Font("Determination Mono", Font.PLAIN, 30);

		SFX = new HashMap<String, AudioPlayer>();

		SFX.put("DishStartTransition", new AudioPlayer("/Dish Transition Start Explosion.wav", 1));
		SFX.put("DishTransition", new AudioPlayer("/Dish Transition Explosion.wav", 1));
		SFX.put("NickStartTransition", new AudioPlayer("/Nick Start Transition.wav", 1));
		SFX.put("NickTransition", new AudioPlayer("/Nick Transition.wav", 1));
		SFX.put("MasonStartTransition", new AudioPlayer("/Mason Start Transition.wav", 1));
		SFX.put("MasonTransition", new AudioPlayer("/Mason Transition.wav", 1));
		SFX.put("Cut In", new AudioPlayer("/Cut In.wav", 2));
		SFX.put("Dish Special", new AudioPlayer("/Dish Special.wav", 1));
		SFX.put("Nick Special", new AudioPlayer("/Nick Special.wav", 1));

		daanishTransitionSheet = new Spritesheet(loader.loadImage("/Dish Transition.png"));
		nickTransitionSheet = new Spritesheet(loader.loadImage("/Nick Transition.png"));
		namelessTransitionSheet = new Spritesheet(loader.loadImage("/Nameless Transition.png"));

		nickSpecialSheet = new Spritesheet(loader.loadImage("/Nick Special Attack Animation.png"));

		for (int i = 0; i < 33; i++) {

			if (i < 6) {

				nickTop[i] = nickSpecialSheet.getImage(i + 1, 1, 337, 337, 337, 337);
				nickBottom[i] = nickSpecialSheet.getImage(i + 1, 2, 337, 337, 337, 337);
				nickLeft[i] = nickSpecialSheet.getImage(i + 1, 3, 337, 337, 337, 337);
				nickRight[i] = nickSpecialSheet.getImage(i + 1, 4, 337, 337, 337, 337);

			}

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

		nickSpecialAni = new Animation[] {

				new Animation(3, nickTop), new Animation(3, nickBottom), new Animation(3, nickLeft),
				new Animation(3, nickRight),

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

				if (!check) {

					SFX.get("MasonStartTransition").play(false);

					check = true;

				}

				if (transitionAni[4].getRanOnce()) {

					setCharSwitch(true);

					transitionAni[5].toggleAnimation(false);
					transitionAni[5].runAnimation();

					if (!check2) {

						SFX.get("MasonTransition").play(false);

						check2 = true;

					}

					if (transitionAni[5].getRanOnce()) {

						setTransition(false);

						transitionAni[4].toggleAnimation(true);
						transitionAni[5].toggleAnimation(true);

						end = true;

						check = false;
						check2 = false;

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

		if (Nicc.isSpecial()) {

			getNickCutIn().runAnimation();

			if (!check3) {

				SFX.get("Cut In").setVolume(2);

				SFX.get("Cut In").play(false);

				check3 = true;

			}

			if (getNickCutIn().getRanOnce()) {

				if (!SFX.get("Nick Special").clip[0].isRunning() && !check4) {

					SFX.get("Nick Special").play(false);

					check4 = true;

				}

				getNickCutIn().toggleAnimation(true);

				if (Nicc.getDirection() == 1) {

					nickSpecialAni[0].runAnimation();

				} else if (Nicc.getDirection() == 2) {

					nickSpecialAni[3].runAnimation();

				} else if (Nicc.getDirection() == 3) {

					nickSpecialAni[1].runAnimation();

				} else if (Nicc.getDirection() == 4) {

					nickSpecialAni[2].runAnimation();

				}

				if (nickSpecialAni[0].getRanOnce() || nickSpecialAni[1].getRanOnce() || nickSpecialAni[2].getRanOnce()
						|| nickSpecialAni[3].getRanOnce()) {

					for (int i = 0; i < 4; i++) {

						nickSpecialAni[i].reset();

					}

					getNickCutIn().reset();

					getNickCutIn().toggleAnimation(false);

					Nicc.setSpecial(false);

					check3 = false;
					check4 = false;

				}

			}

		}

		if (Daanish.isSpecial()) {

			getDishCutIn().runAnimation();

			if (!check3) {

				SFX.get("Cut In").setVolume(2);

				SFX.get("Cut In").play(false);

				check3 = true;

			}

			if (getDishCutIn().getRanOnce()) {

				if (!SFX.get("Dish Special").clip[0].isRunning()) {

					SFX.get("Dish Special").play(false);

				}

				getDishCutIn().toggleAnimation(true);

				getDishSpecialAni()[1].runAnimation();
				getDishSpecialAni()[2].runAnimation();
				getDishSpecialAni()[3].runAnimation();
				getDishSpecialAni()[4].runAnimation();
				getDishSpecialAni()[5].runAnimation();
				getDishSpecialAni()[6].runAnimation();

				if (getDishSpecialAni()[1].getRanOnce()) {

					for (int i = 0; i < 7; i++) {

						getDishSpecialAni()[i].reset();

					}

					getDishCutIn().reset();

					getDishCutIn().toggleAnimation(false);

					Daanish.setSpecial(false);

					check3 = false;

				}

			}

		}

	}

	public void draw(Graphics g) {

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

		for (int i = 0; i < handler.getObject().size(); i++) {

			GameObject temp = handler.getObject().get(i);

			if (isTransition() && temp.getId() == ID.Player) {

				if (Game.getCharacter() == 1) {

					if (!isCharSwitch()) {

						transitionAni[0].drawAnimation(g,
								(temp.getX() - (daanishStartTransition[0].getWidth() / 2) + 30) - Camera.getX() + 13,
								(temp.getY() - (daanishStartTransition[0].getHeight() / 2) + 25) - Camera.getY(), 0);

					} else {

						transitionAni[1].drawAnimation(g,
								(temp.getX() - (daanishTransition[0].getWidth() / 2) + 30) - Camera.getX() + 13,
								(temp.getY() - (daanishTransition[0].getHeight() / 2) + 25) - Camera.getY(), 0);

					}

				} else if (Game.getCharacter() == 2) {

					if (!isCharSwitch()) {

						transitionAni[2].drawAnimation(g,
								(temp.getX() - (nickStartTransition[0].getWidth() / 2) + 32) - Camera.getX() + 13,
								(temp.getY() - (nickStartTransition[0].getHeight() / 2)) - Camera.getY(), 0);

					} else {

						transitionAni[3].drawAnimation(g,
								(temp.getX() - (nickTransition[0].getWidth() / 2) + 32) - Camera.getX() + 13,
								(temp.getY() - (nickTransition[0].getHeight() / 2)) - Camera.getY(), 0);

					}

				} else if (Game.getCharacter() == 3) {

					if (!isCharSwitch()) {

						transitionAni[4].drawAnimation(g,
								(temp.getX() - (namelessStartTransition[0].getWidth() / 2) + 18) - Camera.getX() + 13,
								(temp.getY() - (namelessStartTransition[0].getHeight() / 2) - 19) - Camera.getY(), 0);

					} else {

						transitionAni[5].drawAnimation(g,
								(temp.getX() - (namelessTransition[0].getWidth() / 2) + 18) - Camera.getX() + 13,
								(temp.getY() - (namelessTransition[0].getHeight() / 2) - 19) - Camera.getY(), 0);

					}

				}

			}

			if (temp.getId() == ID.Player) {

				if (Game.getCharacter() == 1) {

					g.setColor(Color.white);
					g.setFont(font);

					g.drawString(String.valueOf(Game.daanishLevel), 77, 575);

				} else if (Game.getCharacter() == 2) {

					g.setColor(Color.white);
					g.setFont(font);

					g.drawString(String.valueOf(Game.nickLevel), 77, 575);

				} else if (Game.getCharacter() == 3) {

					g.setColor(Color.white);
					g.setFont(font);

					g.drawString(String.valueOf(Game.namelessLevel), 77, 575);

				}

			}

		}

		if (Nicc.isSpecial()) {

			if (!getNickCutIn().getRanOnce()) {

				getNickCutIn().drawAnimation(g, 0, 0, 0);

			}

			if (getNickCutIn().getRanOnce()) {

				if (Nicc.getDirection() == 1) {

					nickSpecialAni[0].drawAnimation(g, Nicc.getSpecialBox().x - 50 - Camera.getX(),
							Nicc.getSpecialBox().y - 80 - Camera.getY(), 0);

				} else if (Nicc.getDirection() == 2) {

					nickSpecialAni[3].drawAnimation(g,
							Nicc.getSpecialBox().x - (nickRight[0].getWidth() / 2) - 10 - Camera.getX(),
							Nicc.getSpecialBox().y - 45 - Camera.getY(), 0);

				} else if (Nicc.getDirection() == 3) {

					nickSpecialAni[1].drawAnimation(g,
							Nicc.getSpecialBox().x - (nickBottom[0].getWidth() / 2) - Camera.getX() + 60,
							Nicc.getSpecialBox().y - (nickBottom[0].getHeight() / 2) - Camera.getY() - 30, 0);

				} else if (Nicc.getDirection() == 4) {

					nickSpecialAni[2].drawAnimation(g,
							Nicc.getSpecialBox().x - (nickLeft[0].getWidth() / 2) - Camera.getX() + 60,
							Nicc.getSpecialBox().y - (nickLeft[0].getHeight() / 2) - Camera.getY() + 60, 0);

				}

			}

		}

		if (Daanish.isSpecial()) {

			if (!getDishCutIn().getRanOnce()) {

				getDishCutIn().drawAnimation(g, 0, 0, 0);

			}

			if (getDishCutIn().getRanOnce()) {

				getDishSpecialAni()[3].drawAnimation(g, Daanish.getLeftBox().x - Camera.getX(),
						Daanish.getLeftBox().y - Camera.getY(), 0);
				getDishSpecialAni()[4].drawAnimation(g, Daanish.getRightBox().x - Camera.getX(),
						Daanish.getRightBox().y - Camera.getY(), 0);
				getDishSpecialAni()[6].drawAnimation(g, Daanish.getTopBox().x - Camera.getX(),
						Daanish.getTopBox().y - Camera.getY(), 0);
				getDishSpecialAni()[5].drawAnimation(g, Daanish.getBottomBox().x - Camera.getX(),
						Daanish.getBottomBox().y - Camera.getY(), 0);

				if (Game.daanishLevel > 1) {

					for (int i = 0; i < Daanish.getNumberOfBoxes(); i++) {

						getDishSpecialAni()[2].drawAnimation(g, Daanish.getSideMiddleBoxes()[i].x - Camera.getX(),
								Daanish.getSideMiddleBoxes()[i].y - Camera.getY(), 0);

						getDishSpecialAni()[1].drawAnimation(g, Daanish.getSideTopBoxes()[i].x - Camera.getX(),
								Daanish.getSideTopBoxes()[i].y - Camera.getY(), 0);

					}

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

	public boolean isCharSwitch() {
		return charSwitch;
	}

	public void setCharSwitch(boolean charSwitch) {
		this.charSwitch = charSwitch;
	}

	public Animation[] getDishSpecialAni() {
		return dishSpecialAni;
	}

	public void setDishSpecialAni(Animation[] dishSpecialAni) {
		this.dishSpecialAni = dishSpecialAni;
	}

	public static Animation getDishCutIn() {
		return dishCutIn;
	}

	public void setDishCutIn(Animation dishCutIn) {
		this.dishCutIn = dishCutIn;
	}

	public static Animation getNickCutIn() {
		return nickCutIn;
	}

	public void setNickCutIn(Animation nickCutIn) {
		this.nickCutIn = nickCutIn;
	}

}
