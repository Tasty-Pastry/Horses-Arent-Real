package animation;

// imports
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.Timer;

import game.AudioPlayer;
import game.Game;

// class header
public class Typewriter implements ActionListener {

	// array of typed variables
	private List<String> stringArray;

	// variables
	private String string;

	private Scanner scanner;

	private Timer t;

	private Font font;

	private int y;

	private boolean timerStart;

	private boolean endString;

	private int stringCount;
	private int stringTraverse;

	private boolean check;

	private HashMap<String, AudioPlayer> SFX;

	public Typewriter(String file, int y, Font font, boolean check) {

		stringArray = new ArrayList<String>();

		string = "";

		this.y = y;

		this.check = check;

		this.font = font;

		timerStart = true;

		endString = true;

		SFX = new HashMap<String, AudioPlayer>();
		SFX.put("Blip", new AudioPlayer("Blip.wav", 10));

		scanner = new Scanner(ClassLoader.getSystemClassLoader().getResourceAsStream(file));

		// continues typing until null
		while (scanner.hasNext()) {

			stringArray.add(scanner.nextLine());

		}

		scanner.close();

		// gets timer
		t = new Timer(60, this);

	}

	// moves to next line to type
	public void nextLine() {

		// outputs a line
		if (!Game.isRunOnce() && endString) {

			setStringCount(0);
			setStringTraverse(getStringTraverse() + 1);
			string = "";
			timerStart = true;
			endString = false;
			t.start();

			// stops if there are no more lines
		} else if (!endString) {

			setStringCount(stringArray.get(getStringTraverse()).length());

		}

	}

	// outputs sound for typing
	public void actionPerformed(ActionEvent arg0) {

		// If the timer is counting
		if (timerStart) {

			if (getStringTraverse() < stringArray.size() - 1) {

				if (getStringCount() > stringArray.get(getStringTraverse()).length()) {

					setStringCount(0);
					timerStart = false;
					endString = true;
					t.stop();

				} else {

					// if not outputing string and button pressed make sound
					SFX.get("Blip").play(true);
					string = stringArray.get(getStringTraverse()).substring(0, getStringCount());

					setStringCount(getStringCount() + 1);

				}

			} else {

				// if not start the game
				Game.setRunOnce(true);
				Game.setIntroDone(true);
				Game.setSlideIn(false);
				Game.setFade(true);

				// stops timer
				setStringCount(0);
				timerStart = false;
				t.stop();

			}

		}

	}

	// draws string
	public void draw(Graphics g) {

		if (!Game.isRunOnce()) {

			// find place to draw current letter
			g.drawString(string, 512 - ((g.getFontMetrics(font).stringWidth(stringArray.get(getStringTraverse())) / 2)),
					y);

			// if done drawing tell them to press space to continue
			if (Game.getIntroDone()) {

				g.drawString("(Press Space)", 700, 600);

			}
		}
	}

	// gets string
	public String getArrayContent(int s) {

		return stringArray.get(s);

	}

	// gets string as a as a list
	public List<String> getArray() {

		return stringArray;

	}

	// gets string size
	public int getStringCount() {
		return stringCount;
	}

	// sets the string count
	public void setStringCount(int stringCount) {
		this.stringCount = stringCount;
	}

	// gets current spot in string
	public int getStringTraverse() {
		return stringTraverse;
	}

	// transveres string
	public void setStringTraverse(int stringTraverse) {
		this.stringTraverse = stringTraverse;
	}

}
