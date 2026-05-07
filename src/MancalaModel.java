import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Represents the model/game logic for the Mancala game.
 * This class stores the board state, handles player turns,
 * performs moves, supports undo, and notifies the view when
 * the board changes.
 *
 * Board index layout:
 * A side pits: 0, 1, 2, 3, 4, 5
 * A Mancala: 6
 * B side pits: 7, 8, 9, 10, 11, 12
 * B Mancala: 13
 *
 * @author Duc Nguyen
 */
public class MancalaModel {
    public static final int A_STORE = 6;
    public static final int B_STORE = 13;
    public static final int TOTAL_SPOTS = 14;

    private int[] board;
    private int[] previousBoard;

    private ArrayList<ChangeListener> listeners;

    private char currentPlayer;
    private char previousPlayer;

    private boolean canUndo;
    private int undoCountThisTurn;
    private boolean gameOver;

    /**
     * Constructs a MancalaModel with 4 stones in each regular pit.
     */
    public MancalaModel() {
        this(4);
    }

    /**
     * Constructs a MancalaModel with the given number of stones
     * in each regular pit.
     *
     * @param stonesPerPit number of stones placed in each regular pit
     */
    public MancalaModel(int stonesPerPit) {
        board = new int[TOTAL_SPOTS];
        previousBoard = new int[TOTAL_SPOTS];
        listeners = new ArrayList<>();

        currentPlayer = 'A';
        previousPlayer = 'A';
        canUndo = false;
        undoCountThisTurn = 0;
        gameOver = false;

        initializeBoard(stonesPerPit);
    }

    /**
     * Initializes the board with stones in the regular pits.
     * The two Mancalas start empty.
     *
     * @param stonesPerPit number of stones per regular pit
     */
    public void initializeBoard(int stonesPerPit) {
        if (stonesPerPit < 0) {
            stonesPerPit = 0;
        }

        for (int i = 0; i < TOTAL_SPOTS; i++) {
            if (i == A_STORE || i == B_STORE) {
                board[i] = 0;
            } else {
                board[i] = stonesPerPit;
            }
        }

        currentPlayer = 'A';
        previousPlayer = 'A';
        canUndo = false;
        undoCountThisTurn = 0;
        gameOver = false;

        notifyListeners();
    }

    /**
     * Adds a ChangeListener so views can be notified when the model changes.
     *
     * @param l listener to add
     */
    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }

    /**
     * Returns a copy of the board array.
     *
     * @return copy of the current board state
     */
    public int[] getBoard() {
        int[] copy = new int[board.length];

        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i];
        }

        return copy;
    }

    /**
     * Returns the current player.
     *
     * @return 'A' if it is player A's turn, otherwise 'B'
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns whether the game is over.
     *
     * @return true if the game is over, otherwise false
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Makes a move from the selected pit index.
     *
     * @param idx selected pit index
     */
    public void move(int idx) {
        if (gameOver || !isValidMove(idx)) {
            return;
        }

        savePreviousState();

        int stones = board[idx];
        board[idx] = 0;

        int currentIndex = idx;

        while (stones > 0) {
            currentIndex = (currentIndex + 1) % TOTAL_SPOTS;

            if (shouldSkip(currentIndex)) {
                continue;
            }

            board[currentIndex]++;
            stones--;
        }

        handleCapture(currentIndex);

        boolean extraTurn = isOwnStore(currentIndex);

        checkGameOver();

        if (!gameOver && !extraTurn) {
            switchPlayer();
            undoCountThisTurn = 0;
        }

        canUndo = true;
        notifyListeners();
    }

    /**
     * Undoes the most recent move if undo is currently allowed.
     * A player cannot undo multiple times in a row.
     */
    public void undo() {
        if (!canUndo || undoCountThisTurn >= 3) {
            return;
        }

        for (int i = 0; i < board.length; i++) {
            board[i] = previousBoard[i];
        }

        currentPlayer = previousPlayer;
        gameOver = false;
        canUndo = false;
        undoCountThisTurn++;

        notifyListeners();
    }

    /**
     * Checks whether a selected pit is a valid move.
     *
     * @param idx selected pit index
     * @return true if the move is valid, otherwise false
     */
    private boolean isValidMove(int idx) {
        if (idx < 0 || idx >= TOTAL_SPOTS) {
            return false;
        }

        if (idx == A_STORE || idx == B_STORE) {
            return false;
        }

        if (board[idx] == 0) {
            return false;
        }

        if (currentPlayer == 'A') {
            return idx >= 0 && idx <= 5;
        } else {
            return idx >= 7 && idx <= 12;
        }
    }

    /**
     * Saves the current board and player before a move.
     */
    private void savePreviousState() {
        for (int i = 0; i < board.length; i++) {
            previousBoard[i] = board[i];
        }

        previousPlayer = currentPlayer;
    }

    /**
     * Checks whether the current player should skip a Mancala.
     *
     * @param idx board index
     * @return true if the index should be skipped
     */
    private boolean shouldSkip(int idx) {
        if (currentPlayer == 'A' && idx == B_STORE) {
            return true;
        }

        if (currentPlayer == 'B' && idx == A_STORE) {
            return true;
        }

        return false;
    }

    /**
     * Checks whether the last stone landed in the current player's Mancala.
     *
     * @param idx board index where the last stone landed
     * @return true if the last stone landed in the current player's Mancala
     */
    private boolean isOwnStore(int idx) {
        return (currentPlayer == 'A' && idx == A_STORE)
                || (currentPlayer == 'B' && idx == B_STORE);
    }

    /**
     * Handles the capture rule.
     * If the last stone lands in an empty pit on the current player's side,
     * that stone and the stones in the opposite pit are moved to the
     * current player's Mancala.
     *
     * @param lastIndex index where the last stone landed
     */
    private void handleCapture(int lastIndex) {
        if (!isOwnPit(lastIndex)) {
            return;
        }

        if (board[lastIndex] != 1) {
            return;
        }

        int oppositeIndex = getOppositePit(lastIndex);

        if (oppositeIndex < 0 || board[oppositeIndex] == 0) {
            return;
        }

        int storeIndex = getCurrentPlayerStore();

        board[storeIndex] += board[lastIndex] + board[oppositeIndex];
        board[lastIndex] = 0;
        board[oppositeIndex] = 0;
    }

    /**
     * Checks whether the given index is a regular pit owned by the current player.
     *
     * @param idx board index
     * @return true if the pit belongs to the current player
     */
    private boolean isOwnPit(int idx) {
        if (currentPlayer == 'A') {
            return idx >= 0 && idx <= 5;
        } else {
            return idx >= 7 && idx <= 12;
        }
    }

    /**
     * Gets the opposite pit for capture.
     *
     * @param idx pit index
     * @return opposite pit index
     */
    private int getOppositePit(int idx) {
        if (idx >= 0 && idx <= 5) {
            return 12 - idx;
        }

        if (idx >= 7 && idx <= 12) {
            return 12 - idx;
        }

        return -1;
    }

    /**
     * Returns the current player's Mancala index.
     *
     * @return current player's Mancala index
     */
    private int getCurrentPlayerStore() {
        if (currentPlayer == 'A') {
            return A_STORE;
        } else {
            return B_STORE;
        }
    }

    /**
     * Switches the current player.
     */
    private void switchPlayer() {
        if (currentPlayer == 'A') {
            currentPlayer = 'B';
        } else {
            currentPlayer = 'A';
        }
    }

    /**
     * Checks if the game is over.
     * If one side is empty, the remaining stones on the other side
     * are moved into that player's Mancala.
     */
    private void checkGameOver() {
        boolean aSideEmpty = true;
        boolean bSideEmpty = true;

        for (int i = 0; i <= 5; i++) {
            if (board[i] != 0) {
                aSideEmpty = false;
                break;
            }
        }

        for (int i = 7; i <= 12; i++) {
            if (board[i] != 0) {
                bSideEmpty = false;
                break;
            }
        }

        if (aSideEmpty || bSideEmpty) {
            collectRemainingStones();
            gameOver = true;
        }
    }

    /**
     * Moves all remaining stones into the correct Mancala when the game ends.
     */
    private void collectRemainingStones() {
        for (int i = 0; i <= 5; i++) {
            board[A_STORE] += board[i];
            board[i] = 0;
        }

        for (int i = 7; i <= 12; i++) {
            board[B_STORE] += board[i];
            board[i] = 0;
        }
    }

    /**
     * Returns the winner of the game.
     *
     * @return 'A' if player A wins, 'B' if player B wins, or 'T' for tie
     */
    public char getWinner() {
        if (board[A_STORE] > board[B_STORE]) {
            return 'A';
        } else if (board[B_STORE] > board[A_STORE]) {
            return 'B';
        } else {
            return 'T';
        }
    }

    /**
     * Notifies all listeners that the model has changed.
     */
    private void notifyListeners() {
        ChangeEvent event = new ChangeEvent(this);

        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);
        }
    }
}
