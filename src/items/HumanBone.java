package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import entity.GameObject;
import game.Game;
import game.ID;

public class HumanBone extends GameObject {
	
	private BufferedImageLoader loader;

	public HumanBone(int x, int y, ID id) {

		super(x, y, id);

		name = "Human Bone";
		
		lore="Crunch.";
		
		itemDesc="Consuming a bone will cause you to gain back 10% of your hp.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/HumanBone.png");
		
		keyItem = false;
		passive=false;
		vored=false;

	}
	
	public void update() {

	}
	
	public void use() {
		
		if (Game.getCharacter()==1) {
			
			if (Game.daanishHealth>205)
				Game.daanishHealth=228;
			else
				Game.daanishHealth+=(int)(228*0.1);
			
		}
			
		else if (Game.getCharacter()==2) {
			
			if (Game.nickHealth>205)
				Game.nickHealth=228;
			else
				Game.nickHealth+=(int)(228*0.1);
			
		}
		
		else {
			
			if (Game.namelessHealth>205)
				Game.namelessHealth=228;
			else
				Game.namelessHealth+=(int)(228*0.1);
			
		}
		
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
