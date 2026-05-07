import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Represents a pit on the Mancala board.
 * @author Michael Hoshen
 */
public class MancalaPit extends JComponent {
    private int stones;
    private int idx;
    private boolean mancala;
    private BoardStyle style;
    
    private String label;

    /**
     * Constructs a MancalaPit with a given index and style.
     * @param idx the pits position on the board
     * @param style the BoardStyle for rendering
     */
    public MancalaPit(int idx, String label, BoardStyle style) {
        this.idx = idx;
        this.stones = 0;
        this.mancala = (idx == 6 || idx == 13);
        this.style = style;
        this.label = label;
    }

    /**
     * Sets the number of stones in the pit.
     * @param stones number of stones to set
     */
    public void setStones(int stones) {
        this.stones = stones;
        repaint();
    }

    /**
     * Returns the number of stones in the pit.
     * @return stone number in this pit
     */
    public int getStones() {
        return stones;
    }

    /**
     * Returns the index of the pit.
     * @return pit index
     */
    public int getIndex() {
        return idx;
    }

    /**
     * Sets the boards style.
     * @param style the new BoardStyle
     */
    public void setStyle(BoardStyle style) {
        this.style = style;
        repaint();
    }

    /**
     * Draws the pit using the BoardStyle.
     * @param g the graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        if (!mancala) {
            style.drawPit(g2, getWidth(), getHeight(), stones);
        } else {
            style.drawMancala(g2, getWidth(), getHeight(), stones);
        }
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        
        FontMetrics font = g2.getFontMetrics();
        
        int textWidth = font.stringWidth(label);
        
        g2.drawString(label, (getWidth() - textWidth) / 2, getHeight() - 10);
    }
}
