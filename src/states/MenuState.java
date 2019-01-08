package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import animation.Animation;
import animation.Background;
import animation.BufferedImageLoader;
import animation.FontLoader;
import animation.Spritesheet;
import animation.Typewriter;
import game.Game;
import game.Handler;

public class MenuState extends GameState {

	private StateHandler sh;
	private Handler h;
	private int i;
	private int j;
	private BufferedImageLoader loader;

	private String[] strArray = { "The Horse Slayers", "Present" };

	// Menu vars
	private boolean select;

	private boolean typeNext = true;

	private int characterSelect;
	private boolean characterSelectBool;
	private boolean charUp;

	private Font introFont;
	private Font charFont;
	private Font dialogueFont;
	private Font charFont2;
	private float alpha;
	private Color color;
	private boolean countDown;

	private Rectangle line;
	private Rectangle charBox;
	private Rectangle daanishBox;
	private Rectangle nickBox;
	private Rectangle namelessBox;
	private Rectangle startBox;
	private Rectangle hellpBox;
	private Rectangle titleBox;

	private BufferedImage characterBox;
	private BufferedImage start;
	private BufferedImage hellp;
	private BufferedImage title;
	private Background bg;

	private Spritesheet buttonSheet;
	private BufferedImage buttonSpriteSheet = null;

	private Spritesheet charSheet;
	private BufferedImage charSpriteSheet = null;

	private BufferedImage[] startSheet = new BufferedImage[6];
	private BufferedImage[] hellpSheet = new BufferedImage[6];

	private BufferedImage[] daanishWalk = new BufferedImage[3];
	private BufferedImage[] nickWalk = new BufferedImage[3];
	private BufferedImage[] namelessWalk = new BufferedImage[3];

	private BufferedImage[] daanishCheer = new BufferedImage[2];
	private BufferedImage[] nickCheer = new BufferedImage[2];
	private BufferedImage[] namelessCheer = new BufferedImage[2];

	private Animation startAni;
	private Animation hellpAni;

	private Animation daanishWalkAni;
	private Animation nickWalkAni;
	private Animation namelessWalkAni;

	private Animation daanishCheerAni;
	private Animation nickCheerAni;
	private Animation namelessCheerAni;

	private Typewriter type;

	private FontLoader fl;

	public MenuState(StateHandler sh, Handler h) {

		this.sh = sh;
		this.h = h;

		j = 170;

		BufferedImageLoader loader = new BufferedImageLoader();

		characterBox = loader.loadImage("/Character Box.png");
		start = loader.loadImage("/Start.png");
		hellp = loader.loadImage("/Hellp.png");
		title = loader.loadImage("/Title.png");

		bg = new Background("/bg.png", 1);
		bg.setVector(-0.99, 0);

		buttonSpriteSheet = loader.loadImage("/Button Ani.png");
		charSpriteSheet = loader.loadImage("/PlayerSprites.png");

		buttonSheet = new Spritesheet(buttonSpriteSheet);
		charSheet = new Spritesheet(charSpriteSheet);

		for (int k = 0; k < 6; k++) {

			startSheet[k] = buttonSheet.getImage(k + 1, 1, 178, 130, 178, 130);
			hellpSheet[k] = buttonSheet.getImage(k + 1, 2, 178, 130, 178, 130);

			if (k == 0 || k == 1 || k == 2) {

				if (k != 2) {

					daanishCheer[k] = charSheet.getImage(k + 5, 2, 64, 64, 64, 64);
					nickCheer[k] = charSheet.getImage(k + 5, 6, 64, 64, 64, 64);
					namelessCheer[k] = charSheet.getImage(k + 5, 10, 64, 64, 64, 64);

				}

				daanishWalk[k] = charSheet.getImage(k + 1, 2, 64, 64, 64, 64);
				nickWalk[k] = charSheet.getImage(k + 1, 6, 64, 64, 64, 64);
				namelessWalk[k] = charSheet.getImage(k + 1, 10, 64, 64, 64, 64);

			}

		}

		startAni = new Animation(4, startSheet);
		hellpAni = new Animation(4, hellpSheet);

		daanishWalkAni = new Animation(15, daanishWalk);
		nickWalkAni = new Animation(15, nickWalk);
		namelessWalkAni = new Animation(15, namelessWalk);

		daanishCheerAni = new Animation(10, daanishCheer);
		nickCheerAni = new Animation(10, nickCheer);
		namelessCheerAni = new Animation(10, namelessCheer);

		fl = new FontLoader();

		fl.loadFont("res/Pixeled English Font.ttf");
		fl.loadFont("res/Alkhemikal.ttf");
		fl.loadFont("res/DTM-Mono.otf");

		introFont = new Font("Pixeled English Font", Font.PLAIN, 35);
		charFont = new Font("Alkhemikal", Font.PLAIN, 90);
		charFont2 = new Font("Alkhemikal", Font.PLAIN, 80);
		dialogueFont = new Font("Determination Mono", Font.PLAIN, 30);

		line = new Rectangle(0, 470 + j, 1024, 14); // y is 470
		charBox = new Rectangle(300, 484 + j, 424, 156); // y is 484
		daanishBox = new Rectangle(332, 534 + j, 56, 64); // y is 534
		nickBox = new Rectangle(484, 535 + j, 64, 64); // y is 535
		namelessBox = new Rectangle(628, 540 + j, 64, 56); // y is 540
		startBox = new Rectangle(207, 500, 170, 124); // Char up, 207, 314
		hellpBox = new Rectangle(638, 500, 170, 124); // Char up, 638, 314
		titleBox = new Rectangle(170, -59, 684, 209);

		type = new Typewriter("res/test.txt", 500, dialogueFont);

	}

	public void update() {

		if (!Game.getIntroDone()) {

			intro();

		} else if (!Game.isSlideIn() && Game.getIntroDone() && !Game.isRunOnce()) {

			if (typeNext) {

				type.nextLine();
				typeNext = false;

			}

		} else if (Game.isSlideIn()) {

			bg.update();

			alpha = 1f;
			color = new Color(1, 0, 0, alpha);

			if (titleBox.y < 59) {

				titleBox.y += 5;

				if (titleBox.y > 59) {

					titleBox.y = 59;

				}

			}

			if (startBox.y > 366) {

				startBox.y = hellpBox.y -= 5;

				if (startBox.y < 366) {

					startBox.y = hellpBox.y = 366;

					Game.setSlideIn(false);

				}

			}

		} else {

			bg.update();

			if (charUp) {

				if (characterSelect == 1) {

					daanishWalkAni.toggleAnimation(true);
					nickWalkAni.toggleAnimation(false);
					namelessWalkAni.toggleAnimation(false);

					daanishCheerAni.toggleAnimation(false);
					nickCheerAni.toggleAnimation(true);
					namelessCheerAni.toggleAnimation(true);

					daanishCheerAni.runAnimation();

					nickWalkAni.runAnimation();
					namelessWalkAni.runAnimation();

				} else if (characterSelect == 2) {

					daanishWalkAni.toggleAnimation(false);
					nickWalkAni.toggleAnimation(true);
					namelessWalkAni.toggleAnimation(false);

					daanishCheerAni.toggleAnimation(true);
					nickCheerAni.toggleAnimation(false);
					namelessCheerAni.toggleAnimation(true);

					nickCheerAni.runAnimation();

					daanishWalkAni.runAnimation();
					namelessWalkAni.runAnimation();

				} else if (characterSelect == 3) {

					daanishWalkAni.toggleAnimation(false);
					nickWalkAni.toggleAnimation(false);
					namelessWalkAni.toggleAnimation(true);

					daanishCheerAni.toggleAnimation(true);
					nickCheerAni.toggleAnimation(true);
					namelessCheerAni.toggleAnimation(false);

					namelessCheerAni.runAnimation();

					daanishWalkAni.runAnimation();
					nickWalkAni.runAnimation();

				} else {

					daanishWalkAni.toggleAnimation(false);
					nickWalkAni.toggleAnimation(false);
					namelessWalkAni.toggleAnimation(false);

					daanishCheerAni.toggleAnimation(true);
					nickCheerAni.toggleAnimation(true);
					namelessCheerAni.toggleAnimation(true);

					daanishWalkAni.runAnimation();
					nickWalkAni.runAnimation();
					namelessWalkAni.runAnimation();

				}

				if (startBox.y > 314) {

					startBox.y = hellpBox.y -= 5;

				}

				if (characterSelect < 1 || characterSelect > 3) {

					hellpAni.toggleAnimation(true);
					startAni.toggleAnimation(false);
					startAni.runAnimation();

				}

				if (j >= 0) {

					j -= 10;
					line.y -= 10;
					charBox.y -= 10;
					daanishBox.y -= 10;
					nickBox.y -= 10;
					namelessBox.y -= 10;

				} else {

					line.y = 470;
					charBox.y = 484;
					daanishBox.y = 534;
					nickBox.y = 535;
					namelessBox.y = 540;

				}

			} else {

				if (startBox.y < 366) {

					startBox.y = hellpBox.y += 5;

				}

				if (!select) {

					hellpAni.toggleAnimation(true);
					startAni.toggleAnimation(false);
					startAni.runAnimation();

				} else {

					startAni.toggleAnimation(true);
					hellpAni.toggleAnimation(false);
					hellpAni.runAnimation();

				}

				if (j <= 170) {

					j += 15;
					line.y += 15;
					charBox.y += 15;
					daanishBox.y += 15;
					nickBox.y += 15;
					namelessBox.y += 15;

				} else {

					line.y = 470 + j;
					charBox.y = 484 + j;
					daanishBox.y = 534 + j;
					nickBox.y = 535 + j;
					namelessBox.y = 540 + j;

					daanishWalkAni.toggleAnimation(true);
					nickWalkAni.toggleAnimation(true);
					namelessWalkAni.toggleAnimation(true);

				}

			}

		}

	}

	public void intro() {

		if (alpha < 1 && !countDown) {

			color = new Color(1, 0, 0, alpha);
			alpha += 0.01f;

			if (alpha >= 1) {

				alpha = 1f;
				countDown = true;

			}

		} else if (countDown && alpha > 0) {

			color = new Color(1, 0, 0, alpha);
			alpha -= 0.01f;

			if (alpha <= 0) {

				alpha = 0f;

				if (i < strArray.length - 1) {

					i++;

				} else {

					Game.setIntroDone(true);
					alpha = 1f;
					color = new Color(1, 0, 0, alpha);

				}

				countDown = false;

			}

		}

	}

	public void draw(Graphics g) {

		g.setColor(Color.black);
		g.fillRect(0, 0, 1024, 640);
		g.setColor(color);
		g.setFont(introFont);

		if (!Game.getIntroDone()) {

			g.drawString(strArray[i], 512 - ((g.getFontMetrics(introFont).stringWidth(strArray[i])) / 2), 320);

		} else if (Game.getIntroDone() && !Game.isSlideIn() && !Game.isRunOnce()) {

			g.setColor(color.WHITE);
			g.setFont(dialogueFont);

		} else {

			bg.draw((Graphics2D) g);

			g.drawImage(characterBox, line.x, line.y, null);

			// Characters

			if (characterSelect == 1) {

				g.setFont(charFont);
				g.drawString("Dgen", 67, 590);
				g.drawString("Daanish", 740, 590);

				g.setColor(new Color(49, 76, 182));
				g.fillRect(daanishBox.x - 10, daanishBox.y - 8, 80, 80);

				daanishCheerAni.drawAnimation(g, daanishBox.x, daanishBox.y, 0);

				nickWalkAni.drawAnimation(g, nickBox.x, nickBox.y, 0);

				namelessWalkAni.drawAnimation(g, namelessBox.x, namelessBox.y, 0);

			} else if (characterSelect == 2) {

				g.setFont(charFont);
				g.drawString("Thicc", 60, 590);
				g.drawString("Nicc", 800, 590);

				g.setColor(new Color(49, 76, 182));
				g.fillRect(nickBox.x - 8, nickBox.y - 8, 80, 80);

				nickCheerAni.drawAnimation(g, nickBox.x, nickBox.y, 0);

				daanishWalkAni.drawAnimation(g, daanishBox.x, daanishBox.y, 0);

				namelessWalkAni.drawAnimation(g, namelessBox.x, namelessBox.y, 0);

			} else if (characterSelect == 3) {

				g.setFont(charFont);
				g.drawString("Nameless", 3, 590);
				g.drawString("Chaos", 787, 590);

				g.setColor(new Color(49, 76, 182));
				g.fillRect(namelessBox.x - 8, namelessBox.y - 8, 80, 80);

				namelessCheerAni.drawAnimation(g, namelessBox.x, namelessBox.y, 0);

				daanishWalkAni.drawAnimation(g, daanishBox.x, daanishBox.y, 0);

				nickWalkAni.drawAnimation(g, nickBox.x, nickBox.y, 0);

			} else {

				g.setFont(charFont2);

				g.drawString("Character", 3, charBox.y + 105);
				g.setFont(charFont);
				g.drawString("Select", 780, charBox.y + 105);

				daanishWalkAni.drawAnimation(g, daanishBox.x, daanishBox.y, 0);
				nickWalkAni.drawAnimation(g, nickBox.x, nickBox.y, 0);
				namelessWalkAni.drawAnimation(g, namelessBox.x, namelessBox.y, 0);

			}

			if ((!select && !Game.isSlideIn()) || characterSelectBool) {

				startAni.drawAnimation(g, startBox.x, startBox.y, 0);

				g.drawImage(hellp, hellpBox.x, hellpBox.y, null);

			} else if (!Game.isSlideIn()) {

				hellpAni.drawAnimation(g, hellpBox.x, hellpBox.y, 0);

				g.drawImage(start, startBox.x, startBox.y, null);

			} else {

				g.setColor(color);
				g.drawImage(hellp, hellpBox.x, hellpBox.y, null);

				g.drawImage(start, startBox.x, startBox.y, null);

			}

			// Title
			g.drawImage(title, titleBox.x, titleBox.y, null);

		}

		type.draw(g);

	}

	public void keyPressed(int k) {

		if (sh.getState() == 0) {

			if (k == KeyEvent.VK_ENTER) {

				if (characterSelect == 1) {

					Game.setCharacter(1);
					sh.setState(1);

				} else if (characterSelect == 2) {

					Game.setCharacter(2);
					sh.setState(1);

				} else if (characterSelect == 3) {

					Game.setCharacter(3);
					sh.setState(1);

				}
			}

			if (k == KeyEvent.VK_SPACE) {

				typeNext = true;

			}

			if (k == KeyEvent.VK_ESCAPE && !Game.getIntroDone() && !Game.isSlideIn()) {

				Game.setIntroDone(true);
				Game.setSlideIn(true);
				Game.setRunOnce(true);

			}

			if ((k == KeyEvent.VK_LEFT || k == KeyEvent.VK_RIGHT) && !charUp && Game.getIntroDone()
					&& !Game.isSlideIn()) {

				select = !select;

			}

			if (k == KeyEvent.VK_ENTER && Game.getIntroDone() && !select && (characterSelect < 1 || characterSelect > 3)
					&& !Game.isSlideIn()) {

				charUp = !charUp;

			}

			if (k == KeyEvent.VK_DOWN && charUp && (characterSelect < 1 || characterSelect > 3)) {

				characterSelect = 1;
				characterSelectBool = true;

			}

			if (k == KeyEvent.VK_UP && charUp) {

				characterSelect = 5;
				characterSelectBool = false;

			}

			if (k == KeyEvent.VK_LEFT && charUp && characterSelectBool) {

				if (characterSelect == 1) {

					characterSelect = 3;

				} else {

					characterSelect--;

				}

			}

			if (k == KeyEvent.VK_RIGHT && charUp && characterSelectBool) {

				if (characterSelect == 3) {

					characterSelect = 1;

				} else {

					characterSelect++;

				}

			}

		}

	}

	public void keyReleased(int k) {
	}

	@Override
	protected void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
