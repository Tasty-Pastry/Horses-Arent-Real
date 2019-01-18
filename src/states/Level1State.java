package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import animation.BufferedImageLoader;
import animation.Camera;
import animation.Spritesheet;
import entity.Block;
import entity.Bullet;
import entity.Crate;
import entity.Daanish;
import entity.Door;
import entity.Enemy;
import entity.GameObject;
import entity.Nameless;
import entity.Nicc;
import game.AudioPlayer;
import game.Game;
import game.Handler;
import game.ID;
import game.Inventory;
import game.UI;
import items.DannysSoul;
import items.EatingSugar;
import items.Furniture;
import items.HorseEye;
import items.HumanBone;
import items.MeguminStaff;
import items.Mothman;
import items.MrsK;
import items.PatricksBinder;
import items.Skates;
import items.StarCrossedScarf;
import items.TeethButter;
import items.TimothysSkull;
import items.YuGiOhCard;

public class Level1State extends GameState {

	// Serial ID to prevent warning
	private static final long serialVersionUID = 1L;

	// Animation Vars
	protected Spritesheet sheet;
	protected Spritesheet sheet2;
	protected Spritesheet sheet3;
	protected Spritesheet health;

	private BufferedImage level = null;
	private BufferedImage spriteSheet = null;
	private BufferedImage spriteSheet2 = null;
	private BufferedImage spriteSheet3 = null;
	private BufferedImage healthBars = null;

	private BufferedImage[] walls = new BufferedImage[9];

	private Spritesheet wallSheet;

	// Engine Vars
	private Handler handler;
	private Camera camera;
	private StateHandler sh;
	private UI ui;

	private int baseFrame;

	private Inventory inv;

	private boolean showInv;

	private boolean charSwitch;

	private Spritesheet dishBullet;
	private Spritesheet nickBullet;

	private BufferedImage[] dishBullets = new BufferedImage[4];
	private BufferedImage[] nickBullets = new BufferedImage[4];

	private HashMap<String, AudioPlayer> SFX;

	private Color floor;

	// Constructor
	public Level1State(StateHandler sh, Handler handler, Camera camera, Inventory inv) {

		this.sh = sh;
		this.camera = camera;
		this.handler = handler;
		this.inv = inv;

		// Initializes enemy array - largest possible map consists of 24 rooms
		Game.setEnemyArray(new int[6][4]);

		// Sets the room the player is in to the first room - top left corner
		Game.playerXPos = 0;
		Game.playerYPos = 0;

		BufferedImageLoader loader = new BufferedImageLoader();

		// Loads in Sprite
		spriteSheet = loader.loadImage("/PlayerSprites.png");
		spriteSheet2 = loader.loadImage("/Horse Gallop Black.png");
		spriteSheet3 = loader.loadImage("/OhNo.png");
		healthBars = loader.loadImage("/Health Bars.png");
		dishBullet = new Spritesheet(loader.loadImage("/Fire Projectile.png"));
		nickBullet = new Spritesheet(loader.loadImage("/Ice Projectile.png"));

		wallSheet = new Spritesheet(loader.loadImage("/Walls.png"));

		for (int i = 0; i < 9; i++) {

			if (i < 4) {

				dishBullets[i] = dishBullet.getImage(i + 1, 1, 52, 52, 52, 52);
				nickBullets[i] = nickBullet.getImage(i + 1, 1, 41, 41, 41, 41);

			}

			walls[i] = wallSheet.getImage(i + 1, 1, 64, 64, 64, 64);

		}

		sheet = new Spritesheet(spriteSheet);
		sheet2 = new Spritesheet(spriteSheet2);
		sheet3 = new Spritesheet(spriteSheet3);
		health = new Spritesheet(healthBars);

		ui = new UI(health, handler);

		SFX = new HashMap<String, AudioPlayer>();

		SFX.put("Dish Shoot", new AudioPlayer("/Dish Shoot.wav", 5));
		SFX.put("Nick Shoot", new AudioPlayer("/Nicc Shoot.wav", 5));
		SFX.put("Hit", new AudioPlayer("/Hit.wav", 10));

		floor = new Color(145, 176, 154);

		// Loads in Level
		level = loader.loadImage("/TestMap.png");

		loadLevel(level);

	}

	public void update() {

		handler.update();

		ui.update();

		if (camera.getCamMove()) {

			camera.update();

		}

		for (int i = 0; i < handler.getObject().size(); i++) {

			GameObject temp = handler.getObject().get(i);

			if (temp.getId() == ID.Player) {

				if (ui.isCharSwitch() && Game.getCharacter() == 1) {

					handler.addObject(new Daanish(temp.getX(), temp.getY(), ID.Player, handler, sheet, health, inv,
							Game.mothmanSheet, Game.timothySheet, SFX));

					handler.removeObject(temp);

					if (!ui.isTransition()) {

						ui.setCharSwitch(false);
						charSwitch = false;

					}

				} else if (ui.isCharSwitch() && Game.getCharacter() == 2) {

					handler.addObject(new Nicc(temp.getX(), temp.getY(), ID.Player, handler, sheet, health, inv,
							Game.mothmanSheet, Game.timothySheet, SFX));

					handler.removeObject(temp);

					if (!ui.isTransition()) {

						ui.setCharSwitch(false);
						charSwitch = false;

					}

				} else if (ui.isCharSwitch() && Game.getCharacter() == 3) {

					handler.addObject(new Nameless(temp.getX(), temp.getY(), ID.Player, handler, sheet, health, inv,
							Game.mothmanSheet, Game.timothySheet, SFX));

					handler.removeObject(temp);

					if (!ui.isTransition()) {

						ui.setCharSwitch(false);
						charSwitch = false;

					}

				}

			}

		}

	}

	public void draw(Graphics g) {

		// Create Graphics

		Graphics2D g2 = (Graphics2D) g;

		// Background
		g.setColor(floor);
		g.fillRect(0, 0, 1024, 640);

		// Scaling for bigger rooms
		// g2.scale(2, 2);

		// Updates Camera
		g2.translate(-camera.getX(), -camera.getY());

		// Updates all other objects in handler
		handler.draw(g);

		// Updates Camera
		g2.translate(camera.getX(), camera.getY());

		ui.draw(g);

		if (showInv)
			inv.draw(g);

		// Remove frame from queue
		g.dispose();

	}

	private void loadLevel(BufferedImage image) {

		// Gets the dimensions of the map image
		int w = image.getWidth();
		int h = image.getHeight();
		int[] added = new int[15];

		// Double for loop that traverses through each pixel in the map
		for (int x2 = 0; x2 < w; x2++) {

			for (int y2 = 0; y2 < h; y2++) {

				// Gets the RBG of the pixel
				int pixel = image.getRGB(x2, y2);

				// Converts the RBG into three seperate color values
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				// Adds a block if the color is red
				if (red == 255 && blue == 0 && green == 0) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.Block, walls));

				}

				if (red == 255 && blue == 0 && green == 106) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.TopRightCornerBlock, walls));

				}

				if (red == 255 && blue == 110 && green == 0) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.BottomLeftCornerBlock, walls));

				}

				if (red == 255 && blue == 220 && green == 0) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.BottomRightCornerBlock, walls));

				}

				if (red == 160 && blue == 160 && green == 160) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.TopBlock, walls));

				}

				if (red == 255 && blue == 255 && green == 255) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.BottomBlock, walls));

				}

				if (red == 255 && blue == 127 && green == 127) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.LeftBlock, walls));

				}

				if (red == 0 && blue == 255 && green == 148) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.RightBlock, walls));

				}

				// If the color is maroon, add a block and search the current room for enemies,
				// if found add it to the enemyArray
				if (red == 76 && blue == 0 && green == 0) {

					handler.addObject(new Block(x2 * 64, y2 * 64, ID.TopLeftCornerBlock, walls));

					int count = 0;

					// Double for loop to look for enemies in room
					for (int x3 = 0; x3 < 16; x3++) {

						for (int y3 = 0; y3 < 10; y3++) {

							// Gets pixel RGB
							int pixel2 = image.getRGB(x3 + x2, y3 + y2);

							// Splits pixel RGB into 3 seperate channels
							int red2 = (pixel2 >> 16) & 0xff;
							int green2 = (pixel2 >> 8) & 0xff;
							int blue2 = (pixel2) & 0xff;

							// If green, up the count
							if (green2 == 255 && red2 == 0 && blue2 == 0) {

								count++;

								// Add the enemy to the handler
								handler.addObject(new Enemy((x2 + x3) * 64, (y2 + y3) * 64, x2 / 16, y2 / 10, ID.Enemy,
										handler, sheet2));

							}

						}

					}

					// Add the count to the enemy array
					Game.getEnemyArray()[y2 / 10][x2 / 16] = count;

				}

				// If blue, add the player to the handler
				if (blue == 255 && red == 0 && green == 0) {

					if (Game.getCharacter() == 1)
						handler.addObject(new Daanish(x2 * 64, y2 * 64, ID.Player, handler, sheet, health, inv,
								Game.mothmanSheet, Game.timothySheet, SFX));
					if (Game.getCharacter() == 2)
						handler.addObject(new Nicc(x2 * 64, y2 * 64, ID.Player, handler, sheet, health, inv,
								Game.mothmanSheet, Game.timothySheet, SFX));
					if (Game.getCharacter() == 3)
						handler.addObject(new Nameless(x2 * 64, y2 * 64, ID.Player, handler, sheet, health, inv,
								Game.mothmanSheet, Game.timothySheet, SFX));

					// Update player pos to the room its in
					Game.playerXPos = x2 / 16;
					Game.playerYPos = y2 / 10;

				}

				// If purple, add a bottom door
				if (red == 255 && blue == 255 && green == 0) {

					handler.addObject(new Door(x2 * 64, y2 * 64, ID.DoorBottom, handler));

				}

				// If pink, add a side door
				if (red == 79 && blue == 79 && green == 0) {

					handler.addObject(new Door(x2 * 64, y2 * 64, ID.DoorSide, handler));

				}

				// If yellow, add a crate to the handler
				if (green == 255 && red == 255 && blue == 0) {

					handler.addObject(new Crate(x2 * 64, y2 * 64, ID.Crate));

				}

				// If cyan, add an item to the handler
				if (green == 255 && red == 0 && blue == 255) {

					boolean inGame = false;

					switch ((int) (Math.random() * 14) + 1) {

					case 1:
						handler.addObject(new MrsK(x2 * 64, y2 * 64, ID.MrsK));
						break;
					case 2:
						handler.addObject(new HorseEye(x2 * 64, y2 * 64, ID.HorseEye, sheet3));
						break;
					case 3:
						handler.addObject(new DannysSoul(x2 * 64, y2 * 64, ID.DannysSoul));
						break;
					case 4:
						handler.addObject(new EatingSugar(x2 * 64, y2 * 64, ID.EatingSugar));
						break;
					case 5:
						handler.addObject(new Furniture(x2 * 64, y2 * 64, ID.Furniture));
						break;
					case 6:
						handler.addObject(new HumanBone(x2 * 64, y2 * 64, ID.HumanBone));
						break;
					case 7:
						if (added[7] == 7)
							inGame = true;
						added[7] = 7;
						if (!inGame)
							handler.addObject(new MeguminStaff(x2 * 64, y2 * 64, ID.MeguminStaff));
						else
							handler.addObject(new HumanBone(x2 * 64, y2 * 64, ID.HumanBone));
						break;
					case 8:
						if (added[8] == 8)
							inGame = true;
						added[8] = 8;
						if (!inGame)
							handler.addObject(new Mothman(x2 * 64, y2 * 64, ID.Mothman));
						else
							handler.addObject(new HumanBone(x2 * 64, y2 * 64, ID.HumanBone));
						break;

					case 9:
						handler.addObject(new PatricksBinder(x2 * 64, y2 * 64, ID.PatricksBinder));
						break;
					case 10:

						if (added[10] == 10)
							inGame = true;
						added[10] = 10;
						if (!inGame)
							handler.addObject(new Skates(x2 * 64, y2 * 64, ID.Skates));
						else
							handler.addObject(new HumanBone(x2 * 64, y2 * 64, ID.HumanBone));
						break;
					case 11:
						if (added[11] == 11)
							inGame = true;
						added[11] = 11;
						if (!inGame)
							handler.addObject(new StarCrossedScarf(x2 * 64, y2 * 64, ID.StarCrossedScarf));
						else
							handler.addObject(new HumanBone(x2 * 64, y2 * 64, ID.HumanBone));

						if (added[10] == 10)
							inGame = true;
						added[10] = 10;
						if (!inGame)
							handler.addObject(new Skates(x2 * 64, y2 * 64, ID.Skates));
						else
							handler.addObject(new HumanBone(x2 * 64, y2 * 64, ID.HumanBone));
						break;

					case 12:
						handler.addObject(new TeethButter(x2 * 64, y2 * 64, ID.TeethButter));
						break;
					case 13:

						if (added[13] == 13)
							inGame = true;
						added[13] = 13;
						if (!inGame)
							handler.addObject(new TimothysSkull(x2 * 64, y2 * 64, ID.TimothysSkull));
						else
							handler.addObject(new HumanBone(x2 * 64, y2 * 64, ID.HumanBone));

						break;
					case 14:
						handler.addObject(new YuGiOhCard(x2 * 64, y2 * 64, ID.YuGiOhCard));
						break;

					default:
						break;

					}

				}

			}

		}

	}

	public void keyPressed(int k) {

		if (sh.getState() == 1) {

			// For loop to traverse the handler
			for (int i = 0; i < handler.getObject().size(); i++) {

				// Creates a temporary game object and sets it to the current handler object
				GameObject temp = handler.getObject().get(i);

				// If the object is a player, and the camera isn't moving, check if any of the
				// WASD keys are being pressed

				if (temp.getId() == ID.Player && !Camera.getCamMove() && !charSwitch) {

					// Set movement based on the key pressed
					if (k == KeyEvent.VK_W)
						handler.setUp(true);
					if (k == KeyEvent.VK_S)
						handler.setDown(true);
					if (k == KeyEvent.VK_A)
						handler.setLeft(true);
					if (k == KeyEvent.VK_D)
						handler.setRight(true);

					if (k == KeyEvent.VK_F) {

						showInv = !showInv;

					}

					if (k == KeyEvent.VK_E) {

						Game.setCharacter(Game.getCharacter() + 1);

						if (Game.getCharacter() > 3) {

							Game.setCharacter(1);

						}

						if (Game.getCharacter() == 1) {

							temp.setVelocityX(0);
							temp.setVelocityY(0);

							handler.setDown(false);
							handler.setUp(false);
							handler.setLeft(false);
							handler.setRight(false);

							ui.setTransition(true);

							charSwitch = true;

						}

						if (Game.getCharacter() == 2) {

							temp.setVelocityX(0);
							temp.setVelocityY(0);

							handler.setDown(false);
							handler.setUp(false);
							handler.setLeft(false);
							handler.setRight(false);

							ui.setTransition(true);

							charSwitch = true;

						}

						if (Game.getCharacter() == 3) {

							temp.setVelocityX(0);
							temp.setVelocityY(0);

							handler.setDown(false);
							handler.setUp(false);
							handler.setLeft(false);
							handler.setRight(false);

							ui.setTransition(true);

							charSwitch = true;

						}

					}

					// If the current count is 25 frames more than the baseFrame, and the player
					// still has ammo left, add a bullet to the handler
					if (baseFrame <= Game.getCount() - 15 && Game.ammo >= 1) {

						// Adds a bullet to the handler, direction depends on the arrow keypress
						if (k == KeyEvent.VK_DOWN) {

							if (Game.getCharacter() == 1) {

								handler.addObject(
										new Bullet(temp.getX() + 60, temp.getY() + 64, ID.Bullet, handler, temp.getX(),
												temp.getY() + 5000, 20, Color.BLUE, dishBullets, 4, Game.daanishDmg));

								SFX.get("Dish Shoot").play(true);

							} else if (Game.getCharacter() == 2) {

								handler.addObject(new Bullet(temp.getX() + 27, temp.getY() + 64, ID.Bullet, handler,
										temp.getX(), temp.getY() + 5000, 20, Color.BLUE, nickBullets, 4, Game.nickDmg));

								SFX.get("Nick Shoot").play(true);

							} else {

								handler.addObject(new Bullet(temp.getX() + 27, temp.getY() + 64, ID.Bullet, handler,
										temp.getX(), temp.getY() + 5000, 20, Color.BLUE, null, 4, Game.namelessDmg));

							}

							baseFrame = Game.getCount(); // Sets baseFrame to the current count
							Game.ammo--; // Subtracts ammo from the count
							Game.setShoot(true, 3); // Sets direction of shoot sprite

						} else if (k == KeyEvent.VK_UP) {

							if (Game.getCharacter() == 1) {

								handler.addObject(
										new Bullet(temp.getX() + 60, temp.getY() - 12, ID.Bullet, handler, temp.getX(),
												temp.getY() - 5000, 20, Color.BLUE, dishBullets, 3, Game.daanishDmg));

								SFX.get("Dish Shoot").play(true);

							} else if (Game.getCharacter() == 2) {

								handler.addObject(new Bullet(temp.getX() + 60, temp.getY() - 12, ID.Bullet, handler,
										temp.getX(), temp.getY() - 5000, 20, Color.BLUE, nickBullets, 3, Game.nickDmg));

								SFX.get("Nick Shoot").play(true);

							} else {

								handler.addObject(new Bullet(temp.getX() + 60, temp.getY() - 12, ID.Bullet, handler,
										temp.getX(), temp.getY() - 5000, 20, Color.BLUE, null, 3, Game.namelessDmg));

							}

							baseFrame = Game.getCount(); // Sets baseFrame to the current count
							Game.ammo--; // Subtracts ammo from the count
							Game.setShoot(true, 1); // Sets direction of shoot sprite

						} else if (k == KeyEvent.VK_LEFT) {

							if (Game.getCharacter() == 1) {

								handler.addObject(new Bullet(temp.getX() - 12, temp.getY() + 26, ID.Bullet, handler,

										temp.getX() - 5000, temp.getY(), 20, Color.BLUE, dishBullets, 2,
										Game.daanishDmg));

								SFX.get("Dish Shoot").play(true);

							} else if (Game.getCharacter() == 2) {

								handler.addObject(new Bullet(temp.getX() - 12, temp.getY() + 26, ID.Bullet, handler,

										temp.getX() - 5000, temp.getY(), 20, Color.BLUE, nickBullets, 2, Game.nickDmg));

								SFX.get("Nick Shoot").play(true);

							} else {

								handler.addObject(new Bullet(temp.getX() - 12, temp.getY() + 26, ID.Bullet, handler,

										temp.getX() - 5000, temp.getY(), 20, Color.BLUE, null, 2, Game.namelessDmg));

							}

							baseFrame = Game.getCount(); // Sets baseFrame to the current count
							Game.ammo--; // Subtracts ammo from the count
							Game.setShoot(true, 4); // Sets direction of shoot sprite

						} else if (k == KeyEvent.VK_RIGHT) {

							if (Game.getCharacter() == 1) {

								handler.addObject(new Bullet(temp.getX() + 64, temp.getY() + 26, ID.Bullet, handler,

										temp.getX() + 5000, temp.getY(), 20, Color.BLUE, dishBullets, 1,
										Game.daanishDmg));

								SFX.get("Dish Shoot").play(true);

							} else if (Game.getCharacter() == 2) {

								handler.addObject(new Bullet(temp.getX() + 64, temp.getY() + 26, ID.Bullet, handler,

										temp.getX() + 5000, temp.getY(), 20, Color.BLUE, nickBullets, 1, Game.nickDmg));

								SFX.get("Nick Shoot").play(true);

							} else {

								handler.addObject(new Bullet(temp.getX() + 64, temp.getY() + 26, ID.Bullet, handler,

										temp.getX() + 5000, temp.getY(), 20, Color.BLUE, null, 1, Game.namelessDmg));

							}

							baseFrame = Game.getCount(); // Sets baseFrame to the current count
							Game.ammo--; // Subtracts ammo from the count
							Game.setShoot(true, 2); // Sets direction of shoot sprite

						}

					}
					// Character specials
					if (k == KeyEvent.VK_SHIFT) {

						// 1 = Daanish 2 = Nicc 3 = Nameless
						int character = Game.getCharacter();

						if (character == 1) {

						} else if (character == 2) {

							if (handler.isUp()) {

								System.out.println("up");

							} else if (handler.isDown()) {

								System.out.println("Down");

							} else if (handler.isRight()) {

								System.out.println("Right");

							} else if (handler.isLeft()) {

								System.out.println("Left");

							} else {

								System.out.println(Nicc.getLastDir());

							}

							Game.nickEP -= 20;

						} else {

						}

					}

				}

			}

		}
	}

	public void keyReleased(int k) {

		if (sh.getState() == 1)
			// For loop to traverse the handler
			for (int i = 0; i < handler.getObject().size(); i++) {

				// Creates temporary game object and sets it to the current handler object
				GameObject temp = handler.getObject().get(i);

				// Sets the movement to false depending on the key released
				if (temp.getId() == ID.Player) {

					if (k == KeyEvent.VK_W)
						handler.setUp(false);
					if (k == KeyEvent.VK_S)
						handler.setDown(false);
					if (k == KeyEvent.VK_A)
						handler.setLeft(false);
					if (k == KeyEvent.VK_D)
						handler.setRight(false);

				}

			}

	}

	@Override
	protected void mouseClicked(MouseEvent arg0) {

		inv.mouseClicked(arg0);

	}

	@Override
	protected void mouseEntered(MouseEvent arg0) {

		inv.mouseEntered(arg0);

	}

	@Override
	protected void mouseExited(MouseEvent arg0) {

		inv.mouseExited(arg0);

	}

	@Override
	protected void mousePressed(MouseEvent arg0) {

		inv.mousePressed(arg0);

	}

	@Override
	protected void mouseReleased(MouseEvent arg0) {

		inv.mouseReleased(arg0);

	}

	@Override
	protected void mouseDragged(MouseEvent e) {

		inv.mouseDragged(e);

	}

	@Override
	protected void mouseMoved(MouseEvent e) {

		inv.mouseMoved(e);

	}

}
