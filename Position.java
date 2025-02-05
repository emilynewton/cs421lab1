public class Position {
    //represents position, row, col, on board

    public int row; 
    public int col; 

    public Position(int row, int col) {
        this.row = row; 
        this.col = col; 
    }

    public Position (int row, int col, int onwardMoves) {
        this.row = row; 
        this.col = col; 
    }

    public int getRow() {
        return row; 
    }

    public int getCol() {
        return col; 
    }
}