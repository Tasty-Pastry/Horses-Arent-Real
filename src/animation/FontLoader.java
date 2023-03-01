package animation;

/// imports
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

// class jeader
public class FontLoader {

	// loads in requested font
	public void loadFont(String font) {

		try {

			// sets font to custom font
			Font customFont = Font.createFont(Font.TRUETYPE_FONT,
					ClassLoader.getSystemClassLoader().getResourceAsStream(font));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			ge.registerFont(customFont);

		} catch (IOException e) {

			e.printStackTrace();

		} catch (FontFormatException e) {

			e.printStackTrace();

		}

	}

}
