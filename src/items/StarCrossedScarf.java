package items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import animation.BufferedImageLoader;
import animation.Spritesheet;
import entity.GameObject;
import game.ID;

public class StarCrossedScarf extends GameObject {
	
	private BufferedImageLoader loader;

	public StarCrossedScarf(int x, int y, ID id) {

		super(x, y, id);

		name = "Star-Crossed Scarf";
		
		lore="Is... that a scarf? As you get close to it, your mind is flooded with images of a man biting down on a horse tongue. Putting it on certainly wouldn't be a good idea.";
		
		itemDesc="Grants Nameless Chaos the ability to use Edgy Darkness.";

		loader = new BufferedImageLoader();

		sprite = loader.loadImage("/StarCrossedScarf.png");
		
		keyItem = true;
		passive=true;

	}
	
	public void update() {

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
