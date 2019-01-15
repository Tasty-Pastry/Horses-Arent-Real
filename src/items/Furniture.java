package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import entity.GameObject;
import game.Game;
import game.Handler;
import game.ID;

public class Furniture extends GameObject {

	private BufferedImageLoader loader;
	private Handler handler;

	public Furniture(int x, int y, ID id) {

		super(x, y, id);

		name = "Furniture?";
		
		lore="This furniture seems strangely alive...";
		
		itemDesc="Break it open for a surprise.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/Furniture.png");
		
		keyItem = true;
		passive=false;
		vored=false;

	}
	
	public void update() {

	}
	
	public void use() {
		
		if (Game.getCharacter()==1)
			Game.daanishHealth=228;

		else if (Game.getCharacter()==2) 
			Game.nickHealth=228;

		
		else
			Game.namelessHealth=228;

		vored=true;
		
	}

	// Draw crate
	public void draw(Graphics g) {

		g.drawImage(sprite,x,y,null);

	}

	// Get boundaries of crate
	public Rectangle getBounds() {

		return new Rectangle(x, y, 32, 32);

	}


	@Override
	public String getName() {

		return name;

	}

	@Override
	public BufferedImage getSprite() {

		return sprite;
	}
	
}
