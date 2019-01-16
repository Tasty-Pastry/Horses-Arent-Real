package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import entity.GameObject;
import game.ID;

public class YuGiOhCard extends GameObject {
	
	private BufferedImageLoader loader;

	public YuGiOhCard(int x, int y, ID id) {

		super(x, y, id);

		name = "Yu-Gi-Oh Card";
		
		lore="Wow, people still play this game?";
		
		itemDesc="Summons a monster to fight for you!";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/YuGiOhCard.png");
		
		keyItem = true;
		passive=false;

	}
	
	public void update() {

	}
	
	public void use() {
		
		
		
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
