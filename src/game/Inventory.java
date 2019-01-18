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
	
	private List<GameObject> dishArray;
	private List<GameObject> niccArray;
	private List<GameObject> chaosArray;
	private List<Integer> dishCount;
	private List<Integer> niccCount;
	private List<Integer> chaosCount;
	private List<BufferedImage> dishspriteArray;
	private List<BufferedImage> niccspriteArray;
	private List<BufferedImage> chaosspriteArray;

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
	private Rectangle[] keyArray = new Rectangle[12];
	private int row2;
	private int col2;
	private boolean clicked;

	public Inventory() {

		mainArray = new ArrayList<GameObject>();
		itemCount = new ArrayList<Integer>();
		itemCheck = new int[12];
		spriteArray = new ArrayList<BufferedImage>();
		
		dishArray = new ArrayList<GameObject>();
		niccArray = new ArrayList<GameObject>();
		chaosArray = new ArrayList<GameObject>();
		dishCount = new ArrayList<Integer>();
		niccCount = new ArrayList<Integer>();
		chaosCount = new ArrayList<Integer>();
		dishspriteArray = new ArrayList<BufferedImage>();
		niccspriteArray = new ArrayList<BufferedImage>();
		chaosspriteArray = new ArrayList<BufferedImage>();

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
		
		keyArray[0] = new Rectangle(700, 232, 94, 93);
		keyArray[1] = new Rectangle(805, 232, 94, 93);


	}

	public void insert(GameObject item) {
		
		if (item.getId()==ID.MeguminStaff||item.getId()==ID.YuGiOhCard) {
			
			if (dishArray.contains(item)) {

				for (int i = 0; i < dishArray.size(); i++) {

					if (item.equals(dishArray.get(i)) && dishCount.get(i) < fill) {

						dishCount.set(i, dishCount.get(i) + 1);

						if (dishCount.get(i) == fill) {

							itemCheck[i] = 1;

						} else {

							itemCheck[i] = 0;

						}

						found = true;

						setRemove(true);

						break;

					}

				}

				if (!found && dishArray.size() < 2) {

					dishArray.add(item);
					dishspriteArray.add(item.getSprite());
					dishCount.add(1);

					setRemove(true);

				}

				found = false;

			}
			
			else {
				
				dishArray.add(item);
				dishCount.add(1);
				dishspriteArray.add(item.getSprite());
	
				setRemove(true);
				
			}
			
		}
		
		else if (item.getId()==ID.Furniture||item.getId()==ID.Skates) {
			
			if (niccArray.contains(item)) {

				for (int i = 0; i < niccArray.size(); i++) {

					if (item.equals(niccArray.get(i)) && niccCount.get(i) < fill) {

						niccCount.set(i, niccCount.get(i) + 1);

						if (niccCount.get(i) == fill) {

							itemCheck[i] = 1;

						} else {

							itemCheck[i] = 0;

						}

						found = true;

						setRemove(true);

						break;

					}

				}

				if (!found && niccArray.size() < 2) {

					niccArray.add(item);
					niccspriteArray.add(item.getSprite());
					niccCount.add(1);

					setRemove(true);

				}

				found = false;

			}
			
			else {
				
				niccArray.add(item);
				niccCount.add(1);
				niccspriteArray.add(item.getSprite());
	
				setRemove(true);
				
			}
			
		}
		
		else if (item.getId()==ID.Mothman||item.getId()==ID.StarCrossedScarf) {
			
			if (chaosArray.contains(item)) {

				for (int i = 0; i < chaosArray.size(); i++) {

					if (item.equals(chaosArray.get(i)) && chaosCount.get(i) < fill) {

						chaosCount.set(i, chaosCount.get(i) + 1);

						if (chaosCount.get(i) == fill) {

							itemCheck[i] = 1;

						} else {

							itemCheck[i] = 0;

						}

						found = true;

						setRemove(true);

						break;

					}

				}

				if (!found && chaosArray.size() < 2) {

					chaosArray.add(item);
					chaosspriteArray.add(item.getSprite());
					chaosCount.add(1);

					setRemove(true);

				}

				found = false;

			}
			
			else {
				
				chaosArray.add(item);
				chaosCount.add(1);
				chaosspriteArray.add(item.getSprite());
	
				setRemove(true);
				
			}
			
		}

		else if ((mainArray.size() < 12 && IntStream.of(itemCheck).anyMatch(x -> x == 0)) || mainArray.contains(item)) {

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
			
			for (int i = 0; i < dishspriteArray.size(); i++) {
				
				g.drawImage(dishspriteArray.get(i), 715+i*105, 246, null); //715 246 820
				g.drawString(String.valueOf(dishCount.get(i)),
					771 + (i*105)
							- ((g.getFontMetrics(countFont).stringWidth(String.valueOf(dishCount.get(i)))) / 2),
					308 + (col * 105));
				
			}
			
		}
			
		else if (Game.getCharacter()==2) {
			
			g.drawString("Health: "+Game.nickHealth, 687, 108);
			g.drawString("EP: "+Game.nickEP, 807, 108);
			g.drawString("Speed: "+PlayerObject.maxVel, 687, 148);
			g.drawString("Damage: "+Game.nickDmg, 807, 148);
			
			for (int i = 0; i < niccspriteArray.size(); i++) {
				
				g.drawImage(niccspriteArray.get(i), 715+i*105, 246, null); //715 246 820
				g.drawString(String.valueOf(niccCount.get(i)),
					771 + (i*105)
							- ((g.getFontMetrics(countFont).stringWidth(String.valueOf(niccCount.get(i)))) / 2),
					308 + (col * 105));
				
			}
			
		}
		
		else {
			
			g.drawString("Health: "+Game.namelessHealth, 687, 108);
			g.drawString("EP: "+Game.namelessEP, 807, 108);
			g.drawString("Speed: "+PlayerObject.maxVel, 687, 148);
			g.drawString("Damage: "+Game.namelessDmg, 807, 148);
			
			for (int i = 0; i < chaosspriteArray.size(); i++) {
				
				g.drawImage(chaosspriteArray.get(i), 715+i*105, 246, null); //715 246 820
				g.drawString(String.valueOf(chaosCount.get(i)),
					771 + (i*105)
							- ((g.getFontMetrics(countFont).stringWidth(String.valueOf(chaosCount.get(i)))) / 2),
					308 + (col * 105));
				
			}
			
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
								else if (itemCount.size()>i+1) {
									
									int owo=itemCount.get(i+1);
									itemCount.set(i, 0);						
									spriteArray.remove(mainArray.get(i).getSprite());
									mainArray.remove(mainArray.get(i));
									itemCount.set(i, owo);
									
								} else {
									
									itemCount.set(i, 0);
									spriteArray.remove(mainArray.get(i).getSprite());
									mainArray.remove(mainArray.get(i));
									
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
		
		for (int i = 0; i < 2; i++) { //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
			
			if (Game.getCharacter()==1) {

				if (keyArray[i].intersects(new Rectangle(mouseX, mouseY, 10, 10))) {
	
					if (dishArray.size() > i) {
	
						if (dishArray.get(i) != null) {
							
							g.setColor(new Color(49, 76, 182, 80));
							g.fillRect(keyArray[i].x+14, keyArray[i].y+13, 67, 67); //AAAAAAA
	
							g.setColor(Color.red);
	
							g.drawString("Name: " + dishArray.get(i).getName(), 570, 439);
	
							if (dishArray.get(i).getLore().length() < 44) {
	
								g.drawString("Lore: " + dishArray.get(i).getLore(), 570, 459);
	
								if (dishArray.get(i).getDesc().length() < 39)
									g.drawString("Item Desc: " + dishArray.get(i).getDesc(), 570, 479);
	
								else {
	
									g.drawString("Item Desc: " + dishArray.get(i).getDesc().substring(0, 39), 570, 479);
									g.drawString(dishArray.get(i).getDesc().substring(39), 570, 499);
	
								}
	
							}
	
							else {
	
								g.drawString("Lore: " + dishArray.get(i).getLore().substring(0, 44), 570, 459);
								g.drawString(dishArray.get(i).getLore().substring(44), 570, 479);
	
								if (dishArray.get(i).getDesc().length() < 37)
									g.drawString("Item Desc: " + dishArray.get(i).getDesc(), 570, 499);
	
								else {
	
									g.drawString("Item Desc: " + dishArray.get(i).getDesc().substring(0, 39), 570, 499);
									g.drawString(dishArray.get(i).getDesc().substring(39), 570, 519);
	
								}
	
							}
							
							if (clicked==true) {
								
								dishArray.get(i).use();
								if (dishArray.get(i).vored==true) {
									
									if (dishCount.get(i)>1)
										dishCount.set(i, dishCount.get(i) - 1);
									else if (dishCount.size()>i+1) {
										
										int owo=dishCount.get(i+1);
										dishCount.set(i, 0);						
										dishspriteArray.remove(dishArray.get(i).getSprite());
										dishArray.remove(dishArray.get(i));
										dishCount.set(i, owo);
										
									} else {
										
										dishCount.set(i, 0);						
										dishspriteArray.remove(dishArray.get(i).getSprite());
										dishArray.remove(dishArray.get(i));
										
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
			
			else if (Game.getCharacter()==2) {
				
				if (keyArray[i].intersects(new Rectangle(mouseX, mouseY, 10, 10))) {
					
					if (niccArray.size() > i) {
	
						if (niccArray.get(i) != null) {
							
							g.setColor(new Color(49, 76, 182, 80));
							g.fillRect(keyArray[i].x+14, keyArray[i].y+13, 67, 67); //AAAAAAA
	
							g.setColor(Color.red);
	
							g.drawString("Name: " + niccArray.get(i).getName(), 570, 439);
	
							if (niccArray.get(i).getLore().length() < 44) {
	
								g.drawString("Lore: " + niccArray.get(i).getLore(), 570, 459);
	
								if (niccArray.get(i).getDesc().length() < 39)
									g.drawString("Item Desc: " + niccArray.get(i).getDesc(), 570, 479);
	
								else {
	
									g.drawString("Item Desc: " + niccArray.get(i).getDesc().substring(0, 39), 570, 479);
									g.drawString(niccArray.get(i).getDesc().substring(39), 570, 499);
	
								}
	
							}
	
							else {
	
								g.drawString("Lore: " + niccArray.get(i).getLore().substring(0, 44), 570, 459);
								g.drawString(niccArray.get(i).getLore().substring(44), 570, 479);
	
								if (niccArray.get(i).getDesc().length() < 37)
									g.drawString("Item Desc: " + niccArray.get(i).getDesc(), 570, 499);
	
								else {
	
									g.drawString("Item Desc: " + niccArray.get(i).getDesc().substring(0, 39), 570, 499);
									g.drawString(niccArray.get(i).getDesc().substring(39), 570, 519);
	
								}
	
							}
							
							if (clicked==true) {
								
								niccArray.get(i).use();
								if (niccArray.get(i).vored==true) {
									
									if (niccCount.get(i)>1)
										niccCount.set(i, niccCount.get(i) - 1);
									else if (niccCount.size()>i+1) {
										
										int owo=niccCount.get(i+1);
										niccCount.set(i, 0);						
										niccspriteArray.remove(niccArray.get(i).getSprite());
										niccArray.remove(niccArray.get(i));
										niccCount.set(i, owo);
										
									} else {
										
										niccCount.set(i, 0);						
										niccspriteArray.remove(niccArray.get(i).getSprite());
										niccArray.remove(niccArray.get(i));
										
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
				
			} else {
				
				if (keyArray[i].intersects(new Rectangle(mouseX, mouseY, 10, 10))) {
					
					if (chaosArray.size() > i) {
	
						if (chaosArray.get(i) != null) {
							
							g.setColor(new Color(49, 76, 182, 80));
							g.fillRect(keyArray[i].x+14, keyArray[i].y+13, 67, 67); //AAAAAAA
	
							g.setColor(Color.red);
	
							g.drawString("Name: " + chaosArray.get(i).getName(), 570, 439);
	
							if (chaosArray.get(i).getLore().length() < 44) {
	
								g.drawString("Lore: " + chaosArray.get(i).getLore(), 570, 459);
	
								if (chaosArray.get(i).getDesc().length() < 39)
									g.drawString("Item Desc: " + chaosArray.get(i).getDesc(), 570, 479);
	
								else {
	
									g.drawString("Item Desc: " + chaosArray.get(i).getDesc().substring(0, 39), 570, 479);
									g.drawString(chaosArray.get(i).getDesc().substring(39), 570, 499);
	
								}
	
							}
	
							else {
	
								g.drawString("Lore: " + chaosArray.get(i).getLore().substring(0, 44), 570, 459);
								g.drawString(chaosArray.get(i).getLore().substring(44), 570, 479);
	
								if (chaosArray.get(i).getDesc().length() < 37)
									g.drawString("Item Desc: " + chaosArray.get(i).getDesc(), 570, 499);
	
								else {
	
									g.drawString("Item Desc: " + chaosArray.get(i).getDesc().substring(0, 39), 570, 499);
									g.drawString(chaosArray.get(i).getDesc().substring(39), 570, 519);
	
								}
	
							}
							
							if (clicked==true) {
								
								chaosArray.get(i).use();
								if (chaosArray.get(i).vored==true) {
									
									if (chaosCount.get(i)>1)
										chaosCount.set(i, chaosCount.get(i) - 1);
									else if (niccCount.size()>i+1) {
										
										int owo=chaosCount.get(i+1);
										chaosCount.set(i, 0);						
										chaosspriteArray.remove(chaosArray.get(i).getSprite());
										chaosArray.remove(chaosArray.get(i));
										chaosCount.set(i, owo);
										
									} else {
										
										chaosCount.set(i, 0);						
										chaosspriteArray.remove(chaosArray.get(i).getSprite());
										chaosArray.remove(chaosArray.get(i));
										
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
