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
		int padding = 15;
		int size = Math.min(w,  h) - 2 * padding;
		
		int x = (w - size) / 2;
		int y = (h - size) / 2;
		
		g2.setColor(new Color(205, 133, 63));
		g2.fillOval(x,  y,  size, size);
		
		g2.setColor(Color.BLACK);
		g2.drawOval(x,  y, size, size);
		
		drawStones(g2, x, y, size, stones);
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
		g.setColor(new Color(160, 82, 45));
		g.fillRoundRect(5, 5, w-10, h-10, 20, 20);
		
		
	}
	
	/**
	 * Draw a stone.
	 * Precondition: none
	 * Postcondition: a stone is drawn
	 * 
	 * @param g				Graphics object
	 * @param x				the x location of the stone
	 * @param y				the y location of the stone
	 * @param size			the size of the stone
	 * @param stones		the amount of stones to draw
	 */
	public void drawStones(Graphics g, int x, int y, int size, int stones) {
		g.setColor(Color.BLACK);
		
		int radius = 8;
		int centerX = x + size / 2;
		int centerY = y + size / 2;
		
		int spacing = 18;
		
		for(int i = 0; i < stones; i++) {
			double angle = 2 * Math.PI * i / stones;
			
			int sx = centerX + (int)(Math.cos(angle) * spacing);
			int sy = centerY + (int)(Math.sin(angle) * spacing);
			
			g.fillOval(sx - radius/2, sy - radius/2, radius, radius);
		}
	}
}
