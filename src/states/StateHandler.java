package states;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import animation.Camera;
import game.Handler;
import game.Inventory;

public class StateHandler {

	private GameState[] states;
	private int currentState;
	protected int character;

	private Handler handler;
	private Camera camera;
	private Inventory inv;

	private static final int NUMOFSTATES = 3;
	private static final int MENU = 0;
	private static final int LEVEL1 = 1;
	private static final int LOSE = 2;

	public StateHandler(Handler handler, Camera camera, Inventory inv) {

		this.handler = handler;

		this.camera = camera;

		this.inv = inv;

		states = new GameState[NUMOFSTATES];

		currentState = MENU;

		loadState(currentState);

	}

	public void loadState(int state) {

		if (currentState == MENU) {

			states[currentState] = new MenuState(this, handler);

		}

		if (state == LEVEL1) {

			states[currentState] = new Level1State(this, handler, camera, inv);

		}

		if (state == LOSE) {

			states[currentState] = new LoseState(this, handler);

		}

	}

	private void unloadState(int state) {

		// Sets the state to null to unload and free space
		states[state] = null;

	}

	// Unloads the state and sets the new state
	public void setState(int state) {

		unloadState(currentState);
		currentState = state;
		loadState(currentState);

	}

	public void update() {

		// Calls the state update method
		if (states[currentState] != null) {

			states[currentState].update();

		}

	}

	public void draw(Graphics g) {

		// Calls the state draw method
		if (states[currentState] != null) {

			states[currentState].draw(g);

		}

	}

	public int getState() {

		return currentState;

	}

	public void keyPressed(int k) {

		// Passes the keycode to the respective state method
		states[currentState].keyPressed(k);

	}

	public void keyReleased(int k) {

		// Passes the keycode to the respective state method
		states[currentState].keyReleased(k);

	}

	public void mouseClicked(MouseEvent arg0) {

		states[currentState].mouseClicked(arg0);

	}

	public void mouseEntered(MouseEvent arg0) {

		states[currentState].mouseEntered(arg0);

	}

	public void mouseExited(MouseEvent arg0) {

		states[currentState].mouseExited(arg0);

	}

	public void mousePressed(MouseEvent arg0) {

		states[currentState].mousePressed(arg0);

	}

	public void mouseReleased(MouseEvent arg0) {

		states[currentState].mouseReleased(arg0);
	}

	public void mouseDragged(MouseEvent e) {

		states[currentState].mouseDragged(e);

	}

	public void mouseMoved(MouseEvent e) {

		states[currentState].mouseMoved(e);

	}

}
