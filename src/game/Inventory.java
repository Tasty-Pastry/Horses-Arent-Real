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
import entity.PlayerObject;

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

		countFont = new Font("Determination Mono", Font.PLAIN, 12);

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
		
		if (Game.getCharacter()==1) {
			
			g.drawString("Health: "+Game.daanishHealth, 687, 108);
			g.drawString("EP: "+Game.daanishEP, 807, 108);
			g.drawString("Speed: "+PlayerObject.maxVel, 687, 148);
			g.drawString("Damage: "+Game.daanishDmg, 807, 148);
			
		}
			
		else if (Game.getCharacter()==2) {
			
			g.drawString("Health: "+Game.nickHealth, 687, 108);
			g.drawString("EP: "+Game.nickEP, 807, 108);
			g.drawString("Speed: "+PlayerObject.maxVel, 687, 148);
			g.drawString("Damage: "+Game.nickDmg, 807, 148);
			
		}
		
		else {
			
			g.drawString("Health: "+Game.namelessHealth, 687, 108);
			g.drawString("EP: "+Game.namelessEP, 807, 108);
			g.drawString("Speed: "+PlayerObject.maxVel, 687, 148);
			g.drawString("Damage: "+Game.namelessDmg, 807, 148);
			
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
						
						g.setColor(new Color(49, 76, 182, 80));
						g.fillRect(rectArray[i].x+14, rectArray[i].y+13, 67, 67); //AAAAAAA

						g.setColor(Color.red);

						g.drawString("Name: " + mainArray.get(i).getName(), 570, 439);

						if (mainArray.get(i).getLore().length() < 44) {

							g.drawString("Lore: " + mainArray.get(i).getLore(), 570, 459);

							if (mainArray.get(i).getDesc().length() < 39)
								g.drawString("Item Desc: " + mainArray.get(i).getDesc(), 570, 479);

							else {

								g.drawString("Item Desc: " + mainArray.get(i).getDesc().substring(0, 39), 570, 479);
								g.drawString(mainArray.get(i).getDesc().substring(39), 570, 499);

							}

						}

						else {

							g.drawString("Lore: " + mainArray.get(i).getLore().substring(0, 44), 570, 459);
							g.drawString(mainArray.get(i).getLore().substring(44), 570, 479);

							if (mainArray.get(i).getDesc().length() < 37)
								g.drawString("Item Desc: " + mainArray.get(i).getDesc(), 570, 499);

							else {

								g.drawString("Item Desc: " + mainArray.get(i).getDesc().substring(0, 39), 570, 499);
								g.drawString(mainArray.get(i).getDesc().substring(39), 570, 519);

							}

						}
						
						if (clicked==true) {
							
							mainArray.get(i).use();
							if (mainArray.get(i).vored==true) {
								
								if (itemCount.get(i)>1)
									itemCount.set(i, itemCount.get(i) - 1);
								else {
									
									int owo=itemCount.get(i+1);
									itemCount.set(i, 0);						
									spriteArray.remove(mainArray.get(i).getSprite());
									mainArray.remove(mainArray.get(i));
									itemCount.set(i, owo);
									
								}
								
							}
							clicked=false;
							
						}

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
