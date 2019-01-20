package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import animation.Animation;
import animation.BufferedImageLoader;
import animation.FontLoader;
import animation.Spritesheet;
import animation.Typewriter;
import game.AudioPlayer;
import game.Game;
import game.Handler;

public class LoseState extends GameState {

	private StateHandler sh;
	private Handler handler;

	private Animation fadeInAni;
	private Spritesheet fadeInSheet;
	private BufferedImage[] fadeIn = new BufferedImage[13];

	private Typewriter type;
	private FontLoader fl;

	private BufferedImageLoader loader;
	private Font dialogueFont;
	private boolean typeNext = true;

	private HashMap<String, AudioPlayer> SFX;

	public LoseState(StateHandler sh, Handler handler) {

		Game.setRunOnce(false);

		this.sh = sh;
		this.handler = handler;

		fl = new FontLoader();
		fl.loadFont("res/DTM-Mono.otf");

		dialogueFont = new Font("Determination Mono", Font.PLAIN, 30);

		type = new Typewriter("res/testLoss.txt", 500, dialogueFont, false);

		loader = new BufferedImageLoader();

		fadeInSheet = new Spritesheet(loader.loadImage("/Nameless Special.png"));

		for (int i = 0; i < 13; i++) {

			fadeIn[i] = fadeInSheet.getImage(i + 1, 1, 1024, 640, 1024, 640);

		}

		fadeInAni = new Animation(8, fadeIn);

		SFX = new HashMap<String, AudioPlayer>();

		SFX.put("Neigh", new AudioPlayer("/Horse.wav", 5));

	}

	@Override
	public void update() {

		MenuState.getIntroMusic().stopAll();

		fadeInAni.runAnimation();

		if (fadeInAni.getRanOnce()) {

			fadeInAni.toggleAnimation(true);

			if (typeNext) {

				type.nextLine();
				typeNext = false;

				if (type.getStringTraverse() > (type.getArray().size() - 6)) {

					if (!SFX.get("Neigh").clip[0].isRunning() || !SFX.get("Neigh").clip[1].isRunning()
							|| !SFX.get("Neigh").clip[2].isRunning()) {

						SFX.get("Neigh").play(true);

					}

				}

			}

		}

	}

	@Override
	public void draw(Graphics g) {

		if (!fadeInAni.getRanOnce()) {

			fadeInAni.drawAnimation(g, 0, 0, 0);

		}

		if (fadeInAni.getRanOnce()) {

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1024, 640);

			g.setColor(Color.white);
			g.setFont(dialogueFont);

			type.draw(g);

		}

	}

	@Override
	public void keyPressed(int k) {

		if (sh.getState() == 2) {

			if (k == KeyEvent.VK_SPACE) {

				typeNext = true;

			}

		}

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

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
