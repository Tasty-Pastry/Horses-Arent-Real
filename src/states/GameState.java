package states;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public abstract class GameState {

	protected StateHandler sh;

	public abstract void update();

	public abstract void draw(Graphics g);

	public abstract void keyPressed(int k);

	public abstract void keyReleased(int k);

	protected abstract void mouseClicked(MouseEvent arg0);

	protected abstract void mouseEntered(MouseEvent arg0);

	protected abstract void mouseExited(MouseEvent arg0);

	protected abstract void mousePressed(MouseEvent arg0);

	protected abstract void mouseReleased(MouseEvent arg0);

	protected abstract void mouseDragged(MouseEvent e);

	protected abstract void mouseMoved(MouseEvent e);

}
