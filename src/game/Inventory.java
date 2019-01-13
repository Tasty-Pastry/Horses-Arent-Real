package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
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

	private Rectangle[] rectArray = new Rectangle[12];
	private int row2;
	private int col2;
	private boolean clicked;

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

			row2++;

			if (i % 4 == 0 && i != 0) {

				col2++;

			}

			if (i % 4 == 0) {

				row2 = 0;

			}

			rectArray[i] = new Rectangle(64 + (row2 * 106), 64 + (col2 * 105), 94, 93);

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

			g.setColor(Color.red);

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

		for (int i = 0; i < 12; i++) {

			if (rectArray[i].intersects(new Rectangle(mouseX, mouseY, 10, 10))) {

				if (mainArray.size() > i) {

					if (mainArray.get(i) != null) {

						g.setColor(Color.red);

						g.drawString("Name: " + mainArray.get(i).getName(), 570, 439);
						g.drawString("Lore: " + mainArray.get(i).getLore(), 570, 469);
						g.drawString("Item Desc: " + mainArray.get(i).getDesc(), 570, 499);

					}

				} else {

					g.setColor(Color.red);

					g.drawString("Nothing is there!", 500, 500);

				}

			}

		}

	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public void mouseClicked(MouseEvent arg0) {

		clicked = true;

	}

	public void mouseReleased(MouseEvent arg0) {

		clicked = false;

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

		mouseX = e.getX();
		mouseY = e.getY();

	}

	public void mouseMoved(MouseEvent e) {

		mouseX = e.getX();
		mouseY = e.getY();

	}

}
