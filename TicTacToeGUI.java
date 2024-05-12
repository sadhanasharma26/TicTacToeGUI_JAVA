import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private boolean gameOver;

    public TicTacToeGUI() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));
        
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
                        if (!gameOver && buttons[row][col].getText().isEmpty()) {
                            buttons[row][col].setText(Character.toString(currentPlayer));
                            if (checkWin() || checkDraw()) {
                                gameOver = true;
                                if (checkWin()) {
                                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                                } else {
                                    JOptionPane.showMessageDialog(null, "It's a draw!");
                                }
                            } else {
                                switchPlayer();
                            }
                        }
                    }
                });
                add(buttons[i][j]);
            }
        }
        setVisible(true);
    }

    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().isEmpty() && buttons[i][0].getText().equals(buttons[i][1].getText())
                    && buttons[i][0].getText().equals(buttons[i][2].getText()))
                return true;
            if (!buttons[0][i].getText().isEmpty() && buttons[0][i].getText().equals(buttons[1][i].getText())
                    && buttons[0][i].getText().equals(buttons[2][i].getText()))
                return true;
        }
        // Check diagonals
        if (!buttons[0][0].getText().isEmpty() && buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[0][0].getText().equals(buttons[2][2].getText()))
            return true;
        if (!buttons[0][2].getText().isEmpty() && buttons[0][2].getText().equals(buttons[1][1].getText())
                && buttons[0][2].getText().equals(buttons[2][0].getText()))
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}
