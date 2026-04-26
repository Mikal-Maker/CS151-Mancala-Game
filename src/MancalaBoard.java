import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MancalaBoard extends JPanel implements ChangeListener {
    private MancalaModel model;
    private MancalaPit[] pits;
    private BoardStyle style;

    public MancalaBoard(MancalaModel model, BoardStyle stye) {
        this.model = model;
        this.style = stye;
        this.pits = new MancalaPit[14];
        model.addChangeListener(this);
        setLayout(new BorderLayout());
        initializeBoard();
        initializeControls();
    }

    public void initializeBoard() {
        JPanel boardPanel = new JPanel(new GridLayout(2, 10));

        for (int i = 0; i < pits.length; i++) {
            pits[i] = new MancalaPit(i, style);
            int idx = i;

            pits[i].addMouseListener();
        }
    }

    public void initializeControls() {
        undo = new JButton("Undo");
        undo.addActionListener(e -> model.undo());
        JPanel controlPanel = new JPanel();
        controlPanel.add(undo);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void setStyle(BoardStyle style) {
        this.style = style;
        // update pits 
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // loop through board and set stones for each pit, repaint
    }

}