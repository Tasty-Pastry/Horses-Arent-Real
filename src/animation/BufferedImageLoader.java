package animation;

// Imports
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

// Image Var
	private BufferedImage image;

	public BufferedImage loadImage(String path) {

		try {

// Get image from file
			image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(path));

		} catch (IOException e) {

			e.printStackTrace();

		}

// Returns image
		return image;

	}

}
