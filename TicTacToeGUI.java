import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private boolean gameOver;
    private int xWins = 0;
    private int oWins = 0;
    private int draws = 0;
    private JLabel scoreLabel;

    public TicTacToeGUI() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 380);
        setLayout(new BorderLayout());

        scoreLabel = new JLabel(getScoreText(), SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameOver = false;

        // Create buttons and add action listeners
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!gameOver && buttons[row][col].getText().isEmpty() && currentPlayer == 'X') {
                            buttons[row][col].setText("X");
                            if (checkWin()) {
                                xWins++;
                                scoreLabel.setText(getScoreText());
                                JOptionPane.showMessageDialog(null, "Player X wins!");
                                gameOver = true;
                            } else if (checkDraw()) {
                                draws++;
                                scoreLabel.setText(getScoreText());
                                JOptionPane.showMessageDialog(null, "It's a draw!");
                                gameOver = true;
                            } else {
                                switchPlayer();
                                aiMove();
                            }
                        }
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void aiMove() {
        if (gameOver) return;
        // Collect empty cells
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        if (emptyCells.isEmpty()) return;
        // Choose random empty cell
        int[] move = emptyCells.get(new Random().nextInt(emptyCells.size()));
        buttons[move[0]][move[1]].setText("O");

        if (checkWin()) {
            oWins++;
            scoreLabel.setText(getScoreText());
            JOptionPane.showMessageDialog(null, "Player O (AI) wins!");
            gameOver = true;
        } else if (checkDraw()) {
            draws++;
            scoreLabel.setText(getScoreText());
            JOptionPane.showMessageDialog(null, "It's a draw!");
            gameOver = true;
        } else {
            switchPlayer();
        }
    }

    private boolean checkWin() {
        // Rows and columns
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().isEmpty() &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][0].getText().equals(buttons[i][2].getText()))
                return true;
            if (!buttons[0][i].getText().isEmpty() &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[0][i].getText().equals(buttons[2][i].getText()))
                return true;
        }
        // Diagonals
        if (!buttons[0][0].getText().isEmpty() &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[0][0].getText().equals(buttons[2][2].getText()))
            return true;
        if (!buttons[0][2].getText().isEmpty() &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[0][2].getText().equals(buttons[2][0].getText()))
            return true;
        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) return false;
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private String getScoreText() {
        return String.format("X Wins: %d   O Wins (AI): %d   Draws: %d", xWins, oWins, draws);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}
