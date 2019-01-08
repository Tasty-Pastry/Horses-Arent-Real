package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import animation.BufferedImageLoader;
import animation.FontLoader;
import entity.GameObject;

public class Inventory {

	private List<GameObject> mainArray;
	private List<Integer> itemCount;
	private int[] itemCheck;
	private List<BufferedImage> spriteArray;

	private int column;
	private int row;

	private int fill;

	private boolean found;

	private BufferedImage inv;

	private BufferedImage cursor;

	private BufferedImageLoader loader;

	private FontLoader fl;

	private Font countFont;
	private boolean remove;
	private boolean drawCursor;
	private int mouseX;
	private int mouseY;
	private boolean full;

	public Inventory() {

		mainArray = new ArrayList<GameObject>();
		itemCount = new ArrayList<Integer>();
		itemCheck = new int[12];
		spriteArray = new ArrayList<BufferedImage>();

		loader = new BufferedImageLoader();
		fl = new FontLoader();

		fill = 10;

		inv = loader.loadImage("/Inventory.png");
		cursor = loader.loadImage("/Cursor.png");

		fl.loadFont("res/DTM-Mono.otf");

		countFont = new Font("Determination Mono", Font.PLAIN, 15);

		for (int i = 0; i < 12; i++) {

			itemCheck[i] = 0;

		}

	}

	public void insert(GameObject item) {

		if ((mainArray.size() < 12 && IntStream.of(itemCheck).anyMatch(x -> x == 0)) || mainArray.contains(item)) {

			if (mainArray.contains(item)) {

				for (int i = 0; i < mainArray.size(); i++) {

					if (item.equals(mainArray.get(i)) && itemCount.get(i) < fill) {

						itemCount.set(i, itemCount.get(i) + 1);

						if (itemCount.get(i) == fill) {

							itemCheck[i] = 1;

						} else {

							itemCheck[i] = 0;

						}

						found = true;

						setRemove(true);

						break;

					}

				}

				if (!found && mainArray.size() < 12) {

					mainArray.add(item);
					spriteArray.add(item.getSprite());
					itemCount.add(1);

					setRemove(true);

				}

				found = false;

			} else if (mainArray.size() < 12) {

				mainArray.add(item);
				spriteArray.add(item.getSprite());
				itemCount.add(1);

				setRemove(true);

			}

		} else {

			full = true;

		}

	}

	public void draw(Graphics g) {

		g.drawImage(inv, 0, 0, null);

		int col = 0;

		int row = 0;

		for (int i = 0; i < spriteArray.size(); i++) {

			row++;

			if (i % 4 == 0 && i != 0) {

				col++;

			}

			if (i % 4 == 0) {

				row = 0;

			}

			g.drawImage(spriteArray.get(i), 79 + (row * 106), 78 + (col * 105), null);

			g.setFont(countFont);
			g.setColor(Color.RED);
			g.drawString(String.valueOf(itemCount.get(i)),
					135 + (row * 106)
							- ((g.getFontMetrics(countFont).stringWidth(String.valueOf(itemCount.get(i)))) / 2),
					140 + (col * 105));

		}

		if (drawCursor) {

			g.drawImage(cursor, mouseX - (cursor.getWidth() / 2), mouseY - (cursor.getHeight() / 2), null);

		} else {

			g.drawImage(cursor, 512, 320, null);

		}

	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public void mouseClicked(MouseEvent arg0) {

		if (arg0.getX() == 540) {

			System.out.println();

		}

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

		drawCursor = false;

	}

	public void mouseEntered(MouseEvent arg0) {

		drawCursor = true;

	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();

	}

}
