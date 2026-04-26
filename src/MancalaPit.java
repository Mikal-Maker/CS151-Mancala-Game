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

    /**
     * Constructs a MancalaPit with a given index and style.
     * @param idx the pits position on the board
     * @param style the BoardStyle for rendering
     */
    public MancalaPit(int inx, BoardStyle style) {
        this.idx = idx;
        this.stones = 0;
        this.mancala = (index == 6 || index == 13);
        this.style = style;
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
        return index;
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
        super.paintComponent(g);
        if (!mancala) {
            style.drawPit(g, getWidth(), getHeight(), stones);
        } else {
            style.drawMancala(g, getWidth(), getHeight(), stones);
        }
    }
}

