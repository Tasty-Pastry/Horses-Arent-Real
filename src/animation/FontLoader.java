package animation;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontLoader {

	public void loadFont(String font) {

		try {

			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(font));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			ge.registerFont(customFont);

		} catch (IOException e) {

			e.printStackTrace();

		} catch (FontFormatException e) {

			e.printStackTrace();

		}

	}

}
