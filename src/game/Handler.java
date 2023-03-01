package game;

// Imports
import java.awt.Graphics;
import java.util.ArrayList;

import entity.GameObject;

public class Handler {

	// Create an array of Objects
	private ArrayList<GameObject> object = new ArrayList<GameObject>();

	// Direction Vars
	private boolean up, down, left, right;

	// Update the list
	public void update() {

		// Goes through the whole list and updates each object
		for (int i = 0; i < getObject().size(); i++) {

			// Creates a temp game object and sets it to the current handler object
			GameObject temp = getObject().get(i);

			// Simple error checking, if the object has been removed, don't update it
			if (temp != null) {

				temp.update();

			}

		}

	}

	// Draws objects
	public void draw(Graphics g) {

		// Goes through the whole list and draws each object
		for (int i = 0; i < getObject().size(); i++) {

			// Creates a temp game object and sets it to the current handler object
			GameObject temp = getObject().get(i);

			// Simple error checking, if the object has been removed, don't draw it
			if (temp != null) {

				temp.draw(g);

			}

		}

	}

	// Getters and Setters
	public boolean isUp() {

		return up;

	}

	public void setUp(boolean up) {

		this.up = up;

	}

	public boolean isDown() {

		return down;

	}

	public void setDown(boolean down) {

		this.down = down;

	}

	public boolean isLeft() {

		return left;

	}

	public void setLeft(boolean left) {

		this.left = left;

	}

	public boolean isRight() {

		return right;

	}

	public void setRight(boolean right) {

		this.right = right;

	}

	// Add object to list
	public void addObject(GameObject temp) {

		getObject().add(temp);

	}

	// Remove object from list
	public void removeObject(GameObject temp) {

		getObject().remove(temp);

	}

	public ArrayList<GameObject> getObject() {
		return object;
	}

	public void setObject(ArrayList<GameObject> object) {
		this.object = object;
	}

}
