import java.util.ArrayList;

public class KnightBoard {
    // manages board, tracks visited positions

    public int n;
    public int[][] board;

    /**
     * Constructor for the KnightBoard class.
     * Initaializes board by setting all squares to available
     * 
     * @param size
     */
    public KnightBoard(int size) {
        // sets all squares to available
        this.n = size;
        this.board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = -1;
            }
        }
    }

    /**
     * Checks if the position on the board is available to land on.
     * 
     * @param row
     * @param col
     * @return true if the space is not out of bounds or has not already been landed
     *         on
     */
    public boolean isAvailable(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n && board[row][col] == -1;
    }

    public void printBoard() {
        int length = Integer.toString(n * n - 1).length();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%" + length + "d ", board[i][j]);
            }
            System.out.println();
        }
    }

}
