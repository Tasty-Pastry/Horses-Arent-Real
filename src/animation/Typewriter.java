package animation;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.Timer;

import game.Game;

public class Typewriter implements ActionListener {

	private List<String> stringArray;

	private String string;

	private Scanner scanner;

	private Timer t;

	private Font font;

	private int y;

	private boolean timerStart;

	private boolean endString;

	private int stringCount;
	private int stringTraverse;

	public Typewriter(String file, int y, Font font) {

		stringArray = new ArrayList<String>();

		string = "";

		this.y = y;

		this.font = font;

		timerStart = true;

		endString = true;

		try {

			scanner = new Scanner(new File(file));

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		while (scanner.hasNext()) {

			stringArray.add(scanner.nextLine());

		}

		scanner.close();

		t = new Timer(60, this);

	}

	public void nextLine() {

		if (!Game.isRunOnce() && endString) {

			setStringCount(0);
			setStringTraverse(getStringTraverse() + 1);
			string = "";
			timerStart = true;
			endString = false;
			t.start();

		} else if (!endString) {

			setStringCount(stringArray.get(getStringTraverse()).length());

		}

	}

	public void actionPerformed(ActionEvent arg0) {

		if (timerStart) {

			if (getStringTraverse() < stringArray.size() - 1) {

				if (getStringCount() > stringArray.get(getStringTraverse()).length()) {

					setStringCount(0);
					timerStart = false;
					endString = true;
					t.stop();

				} else {

					string = stringArray.get(getStringTraverse()).substring(0, getStringCount());

					setStringCount(getStringCount() + 1);

				}

			} else {

				Game.setRunOnce(true);
				Game.setIntroDone(true);
				Game.setSlideIn(false);
				Game.setFade(true);

				setStringCount(0);
				timerStart = false;
				t.stop();

			}

		}

	}

	public void draw(Graphics g) {

		if (!Game.isRunOnce()) {

			g.drawString(string, 512 - ((g.getFontMetrics(font).stringWidth(stringArray.get(getStringTraverse())) / 2)), y);

			if (Game.getIntroDone()) {

				g.drawString("(Press Space)", 700, 600);

			}
		}
	}

	public String getArrayContent(int s) {

		return stringArray.get(s);

	}

	public List<String> getArray() {

		return stringArray;

	}

	public int getStringCount() {
		return stringCount;
	}

	public void setStringCount(int stringCount) {
		this.stringCount = stringCount;
	}

	public int getStringTraverse() {
		return stringTraverse;
	}

	public void setStringTraverse(int stringTraverse) {
		this.stringTraverse = stringTraverse;
	}

}
