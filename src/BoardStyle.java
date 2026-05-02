import java.awt.*;

public interface BoardStyle {
	/**
	 * Draw a pit on the board.
	 * Precondition: none
	 * Postcondition: a pit is drawn on the mancala board
	 * 
	 * @param g				Graphics object
	 * @param width			the width of the pit
	 * @param height		the height of the pit
	 * @param stones		the amount of stones in the pit
	 */
	void drawPit(Graphics g, int width, int height, int stones);
	
	/**
	 * Draw a mancala on the board. (e.g. Mancala A, Mancala B. This does not draw the entire game Mancala)
	 * Precondition: none
	 * Postcondition: a mancala is drawn on the board
	 * 
	 * @param g				Graphics object
	 * @param width			the width of the Mancala
	 * @param height		the height of the Mancala
	 * @param stones		the amount of stones in the Mancala
	 */
	void drawMancala(Graphics g, int width, int height, int stones);
}
