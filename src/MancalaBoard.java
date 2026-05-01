import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Represents the Mancala game board as a view and controller.
 * Renders pits, handles mouse input, and listens for model updates.
 * @author Michael Hoshen
 */
public class MancalaBoard extends JPanel implements ChangeListener {
    private MancalaModel model;
    private MancalaPit[] pits;
    private BoardStyle style;

    /**
     * Constructs the MancalaBoard with a model and style.
     * @param model the MancalaModel
     * @param style the BoardStyle strategy
     */
    public MancalaBoard(MancalaModel model, BoardStyle stye) {
        this.model = model;
        this.style = stye;
        this.pits = new MancalaPit[14];
        model.addChangeListener(this);
        setLayout(new BorderLayout());
        initializeBoard();
        initializeControls();
    }

    /**
     * Builds the pit grid layout and adds listeners.
     */
    public void initializeBoard() {
        JPanel boardPanel = new JPanel(new GridLayout(2, 10));

        for (int i = 0; i < pits.length; i++) {
            pits[i] = new MancalaPit(i, style);
            int idx = i;

            pits[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    model.move(idx);
                }
            });
            boardPanel.add(pits[i]);
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    /**
     * Builds the control panel with undo button.
     */
    public void initializeControls() {
        undo = new JButton("Undo");
        undo.addActionListener(e -> model.undo());
        JPanel controlPanel = new JPanel();
        controlPanel.add(undo);
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets the board style and repaints pits.
     * @param style the new BoardStyle strategy
     */
    public void setStyle(BoardStyle style) {
        this.style = style;
        for (MancalaPit pit : pits) {
            pit.setStyle(style);
        }
    }

    /**
     * Updates the pit stone counts and repaints the board.
     * @param e the ChangeEvent from the model
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        int board[] = model.getBoard();
        for (int i = 0; i < board.length; i++) {
            pits[i].setStones(board[i]);
        }
        repaint();
    }
}