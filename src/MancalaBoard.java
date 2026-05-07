
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
    private JLabel turnLabel;

    /**
     * Constructs the MancalaBoard with a model and style.
     * @param model the MancalaModel
     * @param style the BoardStyle strategy
     */
    public MancalaBoard(MancalaModel model, BoardStyle style) {
        this.model = model;
        this.style = style;
        this.pits = new MancalaPit[14];
        model.addChangeListener(this);
        setLayout(new BorderLayout());
        initializeBoard();
        initializeControls();
        stateChanged(new ChangeEvent(model));
    }

    /**
     * Builds the pit grid layout and adds listeners.
     */
    public void initializeBoard() {
    	JPanel boardCenter = new JPanel(new GridLayout(2, 6, 10, 10));
    	
    	//create pit for the mancala on the left
    	pits[13] = new MancalaPit(13, "B", style);
    	pits[13].setPreferredSize(new Dimension(100, 250));
    	
    	//create pit for the mancala on the right
    	pits[6] = new MancalaPit(6, "A", style);
    	pits[6].setPreferredSize(new Dimension(100, 250));
    	
    	//create top row of the pits starting from B6 until B1
    	for(int i = 12; i >= 7; i--) {
    		//calculate the label numbers for pits
    		String label = "B" + (i - 6);
    		
    		//create pits
    		pits[i] = new MancalaPit(i, label, style);
    		
    		//this variable is used below in addMouseListener, since it needs to be (effectively) final,
    		//I created a variable for it instead of using i directly since that will cause an error.
    		int idx = i;
    		
    		//add mouseListener to the pits
    		pits[i].addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				model.move(idx);
    			}
    		});
    		
    		//add pits to the board
    		boardCenter.add(pits[i]);
    	}
    	
    	//create bottom row of the pits starting from A1 until A6
    	for(int i = 0; i <= 5; i++) {
    		//the code in here is the same as the for loop above, look at the above loop for explanations
    		String label = "A" + (i + 1);

    		pits[i] = new MancalaPit(i, label, style);
    		
    		
    		int idx = i;
    		
    		pits[i].addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e) {
    				model.move(idx);
    			}
    		});
    		
    		boardCenter.add(pits[i]);
    	}
    	
    	//add mancala (left) to the left of the board
    	add(pits[13], BorderLayout.WEST);
    	//add the pits to the center of the board
    	add(boardCenter, BorderLayout.CENTER);
    	//add mancala(right) to the right of the board
    	add(pits[6], BorderLayout.EAST);
    	/*
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
        */
    }

    /**
     * Builds the control panel with undo button.
     */
    public void initializeControls() {
        JButton undo = new JButton("Undo");
        undo.addActionListener(e -> model.undo());
        turnLabel = new JLabel("Select a pit to decide the turn order");
        JPanel controlPanel = new JPanel();
        controlPanel.add(turnLabel, BorderLayout.WEST);
        controlPanel.add(undo, BorderLayout.EAST);
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
    	if(model.isGameOver()) {
    		char winner = model.getWinner();
    		
    		if(winner == 'T') {
    			turnLabel.setText("Tie. No Winners");
    		} else {
    			turnLabel.setText("Player " + winner + " wins.");
    		}
    	} else {
    		turnLabel.setText("Player " + model.getCurrentPlayer() + "'s turn");
    	}
    	
        int board[] = model.getBoard();
        for (int i = 0; i < board.length; i++) {
            pits[i].setStones(board[i]);
        }
        repaint();
    }
}
