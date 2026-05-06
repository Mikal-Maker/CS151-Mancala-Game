import java.awt.*;

public class Style1 implements BoardStyle {
	
	/**
	 * Draw a single pit.
	 * Precondition: none
	 * Postcondition: a pit is drawn
	 * 
	 * @param g				Graphics object
	 * @param w				width of the pit
	 * @param h				height of the pit
	 * @param stones		amount of stones in the pit
	 */
	public void drawPit(Graphics g, int w, int h, int stones) {
		Graphics2D g2 = (Graphics2D) g;
		int padding = 5;
		int size = Math.min(w,  h) - 2 * padding;
		
		int x = (w - size) / 2;
		int y = (h - size) / 2;
		
		g2.setColor(new Color(205, 133, 63));
		g2.fillOval(x,  y,  size, size);
		
		g2.setColor(Color.BLACK);
		g2.drawOval(x,  y, size, size);
		
		drawStones(g2, x, y, size, size, stones);
	}
	
	/**
	 * Draw a single mancala.
	 * Precondition: none
	 * Postcondition: a mancala is drawn
	 * 
	 * @param g				Graphics object
	 * @param w				width of the macala
	 * @param h				height of the mancala
	 * @param stones		amount of stones in the mancala
	 */
	public void drawMancala(Graphics g, int w, int h, int stones) {
		Graphics2D g2 = (Graphics2D) g;
		
		int padding = 5;
		
		int x = padding;
		int y = padding;
		int width = w - 2 * padding;
		int height = h - 2 * padding;
		
		g2.setColor(new Color(160, 82, 45));
		g2.fillRoundRect(x, y, width, height, 30, 30);
		
		g2.setColor(Color.BLACK);
		g2.drawRoundRect(x,  y, width, height, 30, 30);
		
		drawStones(g2, x, y, width, height, stones);
	}
	
	/**
	 * Draw a stone.
	 * Precondition: none
	 * Postcondition: a stone is drawn
	 * 
	 * @param g				Graphics object
	 * @param x				the x location of the stone
	 * @param y				the y location of the stone
	 * @param width			the width of the pit/mancala
	 * @param height		the height of the pit/mancala
	 * @param stones		the amount of stones to draw
	 */
	public void drawStones(Graphics g, int x, int y, int width, int height, int stones) {
		g.setColor(Color.BLACK);
		
		int radius = 8;
		int centerX = x + width / 2;
		int centerY = y + height / 2;
		
		int spacing = Math.min(width, height) / 3;
		
		for(int i = 0; i < stones; i++) {
			double angle = 2 * Math.PI * i / stones;
			
			int sx = centerX + (int)(Math.cos(angle) * spacing);
			int sy = centerY + (int)(Math.sin(angle) * spacing);
			
			g.fillOval(sx - radius/2, sy - radius/2, radius, radius);
		}
	}
}
