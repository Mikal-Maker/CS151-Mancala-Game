import javax.swing.*;

/**
 * Test class for starting the Mancala game.
 * This class creates the model, asks the user for the starting
 * number of stones and board style, then displays the GUI.
 */
public class MancalaTest {

    /**
     * Starts the Mancala game.
     *
     * @param args command line arguments, not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BoardStyle selectedStyle = chooseStyle();
            int stonesPerPit = chooseStonesPerPit();

            MancalaModel model = new MancalaModel(stonesPerPit);
            MancalaBoard board = new MancalaBoard(model, selectedStyle);

            JFrame frame = new JFrame("Mancala Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(board);
            frame.setSize(1000, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * Allows the user to choose between two board styles.
     *
     * @return the selected BoardStyle
     */
    private static BoardStyle chooseStyle() {
        String[] options = {"Style 1", "Style 2"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose a board style:",
                "Mancala Style Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 1) {
            return new Style2();
        }

        return new Style1();
    }

    /**
     * Allows the user to choose the starting number of stones per pit.
     * The project allows either 3 or 4 stones per pit.
     *
     * @return the number of stones per pit
     */
    private static int chooseStonesPerPit() {
        String[] options = {"3", "4"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose the number of stones per pit:",
                "Starting Stones",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 1) {
            return 4;
        }

        return 3;
    }
}
