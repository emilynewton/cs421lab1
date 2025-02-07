import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KnightTour {

    // possible row and column moves
    public static int[] rowMoves = { 1, 2, 2, 1, -1, -2, -2, -1 };
    public static int[] colMoves = { 2, 1, -1, -2, -2, -1, 1, 2 };

    public int moveCount = 0;

    public static void main(String[] args) {

        if (args.length < 4) {
            return;
        }

        int searchMethod = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int row = Integer.parseInt(args[2]);
        int col = Integer.parseInt(args[3]);

        if (size < 3 || row < 0 || col < 0 || row >= size || col >= size) {
            System.out.println("Invalid board size or starting position.");
            return;
        }

        // creates an instance of the board given size argument
        KnightBoard board = new KnightBoard(size);
        board.board[row][col] = 0;
        if (searchMethod == 0) {

            if (!basicSearch(board, row, col, 1)) {
                System.out.println("No solution found");
            } else {
                System.out.println("\n Knight Tour Board: Basic Search");
                board.printBoard();
            }
        } else if (searchMethod == 1) {
            if (!heuristic1(board, row, col, 1)) {
                System.out.println("No solution found");
            } else {
                System.out.println("\n Knight Tour Board: Heuristic I");
                board.printBoard();
            }
        } else if (searchMethod == 2) {
            if (!heuristic2(board, row, col, 1)) {
                System.out.println("No solution found");
            } else {
                System.out.println("\n Knight Tour Board: Heuristic II");
                board.printBoard();
            }
        }
    }

    /**
     * Performs a basic search algorithm that moves clockwise through the board
     * 
     * @param row
     * @param col
     * @return true if there is a solution and prints the tour
     */
    public static boolean basicSearch(KnightBoard board, int row, int col, int moveCount) {
        // all spaces have been filled
        if (moveCount == board.n * board.n) {
            return true;
        }

        /// check this
        // setting the starting position based on given point
        // board.board[row][col] = moveCount;

        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i];
            int nextCol = col + colMoves[i];
            if (board.isAvailable(nextRow, nextCol)) {
                board.board[nextRow][nextCol] = moveCount;
                if (basicSearch(board, nextRow, nextCol, moveCount + 1)) {
                    return true;
                }
                // backtrack
                board.board[nextRow][nextCol] = -1;
            }
        }
        return false;
    }

    /**
     * Performs the Heuristic I approach where the next move is decided based
     * on the next move's distance from the border of the board
     * 
     * @param row
     * @param col
     * @return true if there is a solution and prints the tour
     */
    public static boolean heuristic1(KnightBoard board, int row, int col, int moveCount) {
        // all spaces have been filled
        if (moveCount == board.n * board.n) {
            return true;
        }

        // generate all valid knight moves from current position
        // calculate distance to border
        // store each move in Position object
        // sort list by border distance

        List<Position> distances = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i];
            int nextCol = col + colMoves[i];
            // if next move is available, distance to border is calculated
            if (board.isAvailable(nextRow, nextCol)) {
                int borderDistance = distanceToBorder(board, nextRow, nextCol);
                distances.add(new Position(nextRow, nextCol, borderDistance));
            }
        }

        // sort distance in ascending order
        distances.sort(null);

        for (Position move : distances) {
            int nextRow = move.getRow();
            int nextCol = move.getCol();
            board.board[nextRow][nextCol] = moveCount;

            if (heuristic1(board, nextRow, nextCol, moveCount + 1)) {
                return true;
            }

            board.board[nextRow][nextCol] = -1;
        }

        return false;
    }

    /**
     * Performs the Heuristic II approach where the next move is decided based
     * on how which move has the fewest number of onward moves
     * 
     * @param row
     * @param col
     * @return true if there is a solution and prints the tour
     */
    public static boolean heuristic2(KnightBoard board, int row, int col, int moveCount) {
        // all spaces have been filled
        if (moveCount == board.n * board.n) {
            return true;
        }

        ArrayList<Position> moves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i];
            int nextCol = col + colMoves[i];
            // if next move is available, number of onward moves is counted
            if (board.isAvailable(nextRow, nextCol)) {
                int onwardMoves = onwardMovesCount(board, nextRow, nextCol);
                moves.add(new Position(nextRow, nextCol, onwardMoves));
            }
        }

        // sorting list into ascending order
        moves.sort(null);

        for (Position move : moves) {
            int nextRow = move.getRow(); // method in position???
            int nextCol = move.getCol();
            board.board[nextRow][nextCol] = moveCount;
            if (heuristic2(board, nextRow, nextCol, moveCount + 1)) {
                return true;
            }
            board.board[nextRow][nextCol] = -1;
        }

        // No valid moves
        return false;
    }

    /**
     * Counts the number of onward moves there is for a move
     * Helper method to heuristic2
     * 
     * @param row
     * @param col
     * @return number of onward moves
     */
    public static int onwardMovesCount(KnightBoard board, int row, int col) {
        int count = 0;

        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i];
            int nextCol = col + colMoves[i];
            if (board.isAvailable(nextRow, nextCol)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the distance to the border through finding the mininum between
     * both distances on either side of the knight
     * 
     * @param row
     * @param col
     * @return the smallest distance the knight is from the border
     */
    public static int distanceToBorder(KnightBoard board, int row, int col) {
        int distance = 0;
        distance = Math.min(row, board.n - row) + Math.min(col, board.n - col);
        return distance;
    }

}