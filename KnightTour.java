import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * KnightTour is the main driver class for solving the Knight's Tour problem. 
 * Different search strategies can be used and the chessboard is initialized. 
 * The board is represented as a 2D array where each cell stores the move order. 
 */
public class KnightTour {

    // possible row and column moves
    public static int[] colMoves = {  1,  2, 2, 1, -1, -2, -2, -1, };
    public static int[] rowMoves = { -2, -1, 1, 2,  2,  1, -1, -2, };

    public static int totalMoves = 1; 

    public static void main(String[] args) {

        if (args.length < 4) {
            System.out.println("Requires four arguments: java KnightTour <0/1/2 (no/heuristicI/heuristicII search)> <n> <x> <y>"); 
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
        board.board[row][col] = 1;
        if (searchMethod == 0) {
            if (!basicSearch(board, row, col, 2)) {
                System.out.println("No solution found");
            } else {
                System.out.println("\n Knight Tour Board: Basic Search");
                System.out.println(); 
                System.out.println("The total number of moves is " + totalMoves);
                board.printBoard();
            }
        } else if (searchMethod == 1) {
            if (!heuristic1(board, row, col, 2)) {
                System.out.println("No solution found");
            } else {
                System.out.println("\n Knight Tour Board: Heuristic I");
                System.out.println(); 
                System.out.println("The total number of moves is " + totalMoves);
                board.printBoard();
            }
        } else if (searchMethod == 2) {
            if (!heuristic2(board, row, col, 2)) {
                System.out.println("No solution found");
            } else {
                System.out.println("\n Knight Tour Board: Heuristic II");
                System.out.println(); 
                System.out.println("The total number of moves is " + totalMoves);
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
        if (moveCount == (board.n * board.n) + 1) {
            return true;
        }

        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i];
            int nextCol = col + colMoves[i];
            if (board.isAvailable(nextRow, nextCol)) {
                board.board[nextRow][nextCol] = moveCount;
                totalMoves++; 
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
        if (moveCount == (board.n * board.n) + 1) {
            return true;
        }

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
            totalMoves++;

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
        if (moveCount == (board.n * board.n) + 1) {
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
            int nextRow = move.getRow(); 
            int nextCol = move.getCol();
            board.board[nextRow][nextCol] = moveCount;
            totalMoves++; 
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
        distance = Math.min(row, (board.n - row) - 1) + Math.min(col, (board.n - col) - 1);
        return distance;
    }

}