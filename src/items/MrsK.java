package items;

// Imports
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import entity.GameObject;
import game.Game;
import game.ID;

public class MrsK extends GameObject {
	
	private BufferedImageLoader loader;
	
	// Constructor
	public MrsK(int x, int y, ID id) {

		super(x, y, id);
		
		name = "Mrs. K";
		
		lore="A being that has reached perfect Adult.";
		
		itemDesc="Use this to increase your damage by some amount.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/MrsK.png");
		
		keyItem = false;
		passive=false;
		vored=false;

	}

	public void update() {

	}
	
	public void use() {
		
		if (Game.getCharacter()==1)
			Game.daanishDmg=50;
			
		else if (Game.getCharacter()==2)
			Game.nickDmg=100;
		
		else 
			Game.namelessDmg=50;

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
