package game;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

// Imports
import javax.swing.JFrame;

public class Window {

	// Constructor
	public Window(String title, Game game) {

		// Creates new JFrame
		JFrame frame = new JFrame(title);

		// Adds Game to window
		frame.add(game);
		frame.setResizable(false);
		frame.pack();

		// Settings of Window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Terminated application on close
		frame.setLocationRelativeTo(null); // Opens the window in the middle of the screen
		frame.setVisible(true); // Allows the window to be seen

		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);

	}

}
