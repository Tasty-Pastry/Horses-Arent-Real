package entity;

// Imports
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.Spritesheet;
import game.ID;

// All GameObjects have these methods and variables
public abstract class GameObject {

	// Position Vars
	protected int x;
	protected int y;

	// Movement Vars
	protected float velocityX = 0, velocityY = 0;

	// Handler Vars
	protected ID id;

	// Image Vars
	protected Spritesheet sheet;
	protected Spritesheet sheet2;

	// Item Vars
	protected String lore;
	protected String itemDesc;
	protected String name;
	protected boolean keyItem;
	protected boolean passive;
	public boolean vored;

	protected BufferedImage sprite;
	
	public int damage;

	public abstract BufferedImage getSprite();

	// Constructor
	public GameObject(int x, int y, ID id) {

		this.x = x;
		this.y = y;
		this.id = id;

	}

	// Abstract Classes
	public abstract void update(); // Updates the object

	public abstract void draw(Graphics g); // Draws the object

	public abstract Rectangle getBounds(); // For collision detection

	// Calculates the center of the object
	public double centroidX() {

		return (getBounds().x + getBounds().getWidth()) / 2;

	}

	public double centroidY() {

		return (getBounds().y + getBounds().getHeight()) / 2;

	}
	
	public void use() {
		
	}

	// Getters and Setters

	public String getName() {

		return name;

	}

	public String getLore() {

		return lore;

	}

	public String getDesc() {

		return itemDesc;

	}

	public int getX() {

		return x;

	}

	public void setX(int x) {

		this.x = x;

	}

	public int getY() {

		return y;

	}

	public void setY(int y) {

		this.y = y;

	}

	public float getVelocityX() {

		return velocityX;

	}

	public void setVelocityX(float velocityX) {

		this.velocityX = velocityX;

	}

	public float getVelocityY() {

		return velocityY;

	}

	public void setVelocityY(float velocityY) {

		this.velocityY = velocityY;

	}

	public ID getId() {

		return id;

	}

	public void setId(ID id) {

		this.id = id;

	}

	public boolean getVored() {
		
		return vored;
	}
	public void setVored(boolean vored) {
		
		this.vored = vored;
	}
	
}
