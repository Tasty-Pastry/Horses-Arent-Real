package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;
import game.Game;

public class TeethButter extends GameObject {

	private BufferedImageLoader loader;
	private boolean vored;

	public TeethButter(int x, int y, ID id) {

		super(x, y, id);

		name = "Teeth Butter";
		
		lore="...Yikes.";
		
		itemDesc="Eat this to regain 15% of your HP.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/TeethButter.png");
		
		keyItem = false;
		passive=false;
		vored=false;

	}
	
	public void update() {

	}
	
	public void use() {
		
		if (Game.getCharacter()==1) {
			
			if (Game.daanishHealth>193)
				Game.daanishHealth=228;
			else
				Game.daanishHealth+=(int)(228*0.15);
			
		}
			
		else if (Game.getCharacter()==2) {
			
			if (Game.nickHealth>193)
				Game.nickHealth=228;
			else
				Game.nickHealth+=(int)(228*0.15);
			
		}
		
		else {
			
			if (Game.namelessHealth>193)
				Game.namelessHealth=228;
			else
				Game.namelessHealth+=(int)(228*0.15);
			
		}
		
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
