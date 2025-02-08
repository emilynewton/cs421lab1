/**
 * The Position class represents a position on the chessboard. 
 * Mainly used in heuristic-based approaches to prioritize different knight moves. 
 */
public class Position implements Comparable<Position> {
    private final int row; 
    private final int col; 
    private final int onwardMoves;

    /**
     * Constructor for the position class to initailize a point on the board
     * @param row
     * @param col
     */
    public Position(int row, int col) {
        this(row, col, 0);
    }

    /** 
     * Constructor for the position class used by heuristic approaches to assign a point on the board with a priority 
     */
    public Position(int row, int col, int onwardMoves) {
        this.row = row; 
        this.col = col; 
        this.onwardMoves = onwardMoves;
    }

    /**
     * Returns the row
     * @return
     */
    public int getRow() {
        return row; 
    }

    /**
     * Returns the column
     * @return
     */
    public int getCol() {
        return col; 
    }

    @Override
    public int compareTo(Position o) {
        return Integer.compare(this.onwardMoves, o.onwardMoves);
    }
}