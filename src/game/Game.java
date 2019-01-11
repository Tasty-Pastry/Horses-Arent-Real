package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import animation.BufferedImageLoader;
import animation.Camera;
import animation.Spritesheet;
import states.StateHandler;

public class Game extends Canvas implements Runnable, KeyListener, MouseMotionListener, MouseListener {

	private Spritesheet sheet;

	// Counter Vars
	private boolean isRunning;
	private Thread thread;
	private static int count;

	// Screen Vars
	private final int width = 1024;
	private final int height = 640;

	// Menu Vars
	private static boolean runOnce;
	private static boolean slideIn;
	private static boolean introDone;

	// Engine Vars
	private StateHandler sh;
	private Handler handler;
	private Camera camera;
	private Inventory inv;
	private BufferedImageLoader loader;

	// Player Vars
	private static int killCount;

	public static int playerXPos; // Keeps track of which
	public static int playerYPos; // room the player is in

	public static int ammo = 150; // Keeps track of ammo
	private static boolean shoot; // Keeps track of if the player is shooting
	private static int shootDir; // Keeps track of which direction the player is shooting in

	public static int daanishHealth = 228;
	public static int nickHealth = 228;
	public static int namelessHealth = 228;

	private static int character = 1;

	public static items.HorseEye horseEye;
	public static items.MrsK MrsK;
	public static items.YuGiOhCard yugiohCard;
	public static items.TimothysSkull timothysSkull;
	public static items.TeethButter teethButter;
	public static items.StarCrossedScarf starScarf;
	public static items.Skates skates;
	public static items.PatricksBinder patricksBinder;
	public static items.Mothman mothman;
	public static items.MeguminStaff meguminStaff;
	public static items.HumanBone humanBone;
	public static items.Furniture furniture;
	public static items.EatingSugar eatingSugar;
	public static items.DannysSoul dannysSoul;
	public static items.MothmanUse mothmanU;

	public static Spritesheet mothmanSheet;
	public static Spritesheet timothySheet;

	// Enemy Vars
	private static int[][] enemyArray; // Initializes the enemyArray - keeps track of how many enemies are in each room

	public Game() {

		// Create new window
		Window window = new Window("Horses Aren't Real", this);

		loader = new BufferedImageLoader();

		// Creates a new Handler and Camera object
		handler = new Handler();
		camera = new Camera(0, 0);
		inv = new Inventory();

		mothmanSheet = new Spritesheet(loader.loadImage("/Mothman Ani.png"));
		timothySheet = new Spritesheet(loader.loadImage("/Timothy Animation.png"));

		horseEye = new items.HorseEye(64, 64, ID.HorseEye);
		MrsK = new items.MrsK(64, 64, ID.MrsK);
		yugiohCard = new items.YuGiOhCard(64, 64, ID.YuGiOhCard);
		timothysSkull = new items.TimothysSkull(64, 64, ID.TimothysSkull);
		teethButter = new items.TeethButter(64, 64, ID.TeethButter);
		starScarf = new items.StarCrossedScarf(64, 64, ID.StarCrossedScarf);
		skates = new items.Skates(64, 64, ID.Skates);
		patricksBinder = new items.PatricksBinder(64, 64, ID.PatricksBinder);
		mothman = new items.Mothman(64, 64, ID.Mothman);
		meguminStaff = new items.MeguminStaff(64, 64, ID.MeguminStaff);
		humanBone = new items.HumanBone(64, 64, ID.HumanBone);
		furniture = new items.Furniture(64, 64, ID.Furniture);
		eatingSugar = new items.EatingSugar(64, 64, ID.EatingSugar);
		dannysSoul = new items.DannysSoul(64, 64, ID.DannysSoul);
		mothmanU = new items.MothmanUse(64, 64, ID.Mothman, handler, mothmanSheet);

		sh = new StateHandler(handler, camera, inv);

		start();

	}

	public static void main(String args[]) {

		// Creates new game
		new Game();

	}

	private void start() {

		// Starts thread
		isRunning = true;
		thread = new Thread(this);
		addKeyListener(this); // Adds the specified key listener to receive key events from this component
		addMouseListener(this);
		addMouseMotionListener(this);
		thread.start();

	}

	private void stop() {

		// Stops thread
		isRunning = false;

		try {

			thread.join();

		} catch (InterruptedException e) {

			e.printStackTrace();

		}

	}

	// Manages the game, allows for controllable FPS on all computers
	public void run() {

		// Requests focus
		this.requestFocus();

		// Timer Variables
		long lastTime = System.nanoTime(); // Gets the current time from the pc
		double ticks = 60.0;
		double ns = 1 * Math.pow(10, 9) / ticks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (isRunning) {

			// Updates timer
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {

				update();
				delta--;

			}

			draw();
			frames++;
			count++;

			if (System.currentTimeMillis() - timer > 1000) {

				timer += 1000;
				frames = 0;

			}

		}

		stop();

	}

	private void update() {

		sh.update();

	}

	private void draw() {

		// Creates a new BufferStrategy
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {

			// This allows the game to preload 3 frames in order to prevent choppy framerate
			this.createBufferStrategy(3);

			return;

		}

		Graphics g = bs.getDrawGraphics();

		sh.draw(g);
		bs.show();

	}

	// Getters and setters

	@Override
	public Dimension getPreferredSize() {

		return new Dimension(width, height);

	}

	public static int getCount() {

		return count;

	}

	public static int getKillCount() {

		return killCount;

	}

	public static void addKillCount() {

		killCount++;

	}

	public static void resetKillCount() {

		killCount = 0;

	}

	public static int[][] getEnemyArray() {
		return enemyArray;
	}

	public static void setEnemyArray(int[][] enemyArray2) {
		enemyArray = enemyArray2;
	}

	public static boolean isRunOnce() {
		return runOnce;
	}

	public static void setRunOnce(boolean runOnce) {
		Game.runOnce = runOnce;
	}

	public static boolean isSlideIn() {
		return slideIn;
	}

	public static void setSlideIn(boolean slideIn) {
		Game.slideIn = slideIn;
	}

	public static void setIntroDone(boolean done) {

		introDone = done;

	}

	public static boolean getIntroDone() {

		return introDone;

	}

	public static void setShoot(boolean shoot2, int shootDir2) {

		shoot = shoot2;
		shootDir = shootDir2;

	}

	public static boolean getShoot() {

		return shoot;

	}

	public static int getShootDir() {

		return shootDir;

	}

	public void keyTyped(KeyEvent key) {

	}

	public void keyPressed(KeyEvent key) {

		sh.keyPressed(key.getKeyCode()); // Calls the Organizer keyPressed Method and passes the key code of the key
											// pressed

	}

	public void keyReleased(KeyEvent key) {

		sh.keyReleased(key.getKeyCode()); // Calls the Organizer keyReleased Method and passes the key code of the key
											// released

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		sh.mouseClicked(arg0);

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		sh.mouseEntered(arg0);

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		sh.mouseExited(arg0);

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

		sh.mousePressed(arg0);

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		sh.mouseReleased(arg0);

	}

	@Override
	public void mouseDragged(MouseEvent e) {

		sh.mouseDragged(e);

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		sh.mouseMoved(e);

	}

	public static int getCharacter() {
		return character;
	}

	public static void setCharacter(int chara) {
		character = chara;
	}

}
