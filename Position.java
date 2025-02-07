public class Position implements Comparable<Position> {
    private final int row; 
    private final int col; 
    private final int onwardMoves;

    public Position(int row, int col) {
        this(row, col, 0);
    }

    public Position(int row, int col, int onwardMoves) {
        this.row = row; 
        this.col = col; 
        this.onwardMoves = onwardMoves;
    }

    public int getRow() {
        return row; 
    }

    public int getCol() {
        return col; 
    }

    @Override
    public int compareTo(Position o) {
        return Integer.compare(this.onwardMoves, o.onwardMoves);
    }
}