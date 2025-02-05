import java.util.ArrayList; 
public class KnightBoard extends Position {
    //manages board, tracks visited positions, contains algorithm for searching
    //size of array 
    public int n;
    public int[][] board; 
    public int moveCount; 

    //possible row and column moves 
    public int[] rowMoves = {1, 2, 2, 1, -1, -2, -2, -1};
    public int[] colMoves = {2, 1, -1, -2, -2, -1, 1, 2};


    public KnightBoard(int size) {
        this.n = size; 
        this.board = new int[n][n];
        //sets all squares to available 
        for (int i = 0; i < n; i++ ) {
            for (int j = 0; j < n; j++) {
                board[i][j] = -1; 
            }
        }
        moveCount = 0; 
    }

    /**
     * Checks if the position on the board is available to land on. 
     * @param row
     * @param col
     * @return true if the space is not out of bounds or has not already been landed on
     */
    public boolean isAvailable(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n && board[row][col] == -1; 
    }

    /**
     * Performs a basic search algorithm that moves clockwise through the board
     * @param row
     * @param col
     * @return true if there is a solution and prints the tour 
     */
    public boolean basicSearch(int[][] board, int row, int col, int moveCount) {
        //all spaces have been filled
        if (moveCount == n * n) {
            return true; 
        }
        
        //setting the starting position based on given point
        board[row][col] = 0;

        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i]; 
            int nextCol = col + colMoves[i]; 
            if (isAvailable(nextRow, nextCol)) {
                board[nextRow][nextCol] = moveCount; 
                if (basicSearch(board, nextRow, nextCol, moveCount + 1)) {
                    return true; 
                } 
                //backtrack
                board[nextRow][nextCol] = -1; 
            }
        }
        return false; 
    }

    /**
     * Performs the Heuristic I approach where the next move is decided based
     * on the next move's distance from the border of the board
     * @param row
     * @param col
     * @return true if there is a solution and prints the tour
     */
    public boolean heuristic1(int[][] board, int row, int col, int moveCount) {
        //all spaces have been filled 
        if (moveCount == n * n) {
            return true; 
        }

        //generate all valid knight moves from current position
            //calculate distance to border 
            //store each move in Position object 
        //sort list by border distance 

        ArrayList<Integer> distances = new ArrayList<>(); 

        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i]; 
            int nextCol = col + colMoves[i]; 
            //if next move is available, distance to border is calculated
            if (isAvailable(nextRow, nextCol)) {
                int borderDistance = distanceToBorder(nextRow, nextCol);  
                distances.add(new Position(nextRow, nextCol, borderDistance)); 
            }
        }

        //sort distance in ascending order 
        distances.sort(null); 

        

        return false; 
    }

    /**
     * Performs the Heuristic II approach where the next move is decided based 
     * on how which move has the fewest number of onward moves
     * @param row
     * @param col
     * @return true if there is a solution and prints the tour
     */
    public boolean heuristic2(int[][] board, int row, int col, int moveCount) { 
        //all spaces have been filled 
        if (moveCount == n * n) {
            return true; 
        }

        ArrayList<Position> moves = new ArrayList<>(); 
        
        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i]; 
            int nextCol = col + colMoves[i]; 
            //if next move is available, number of onward moves is counted
            if (isAvailable(nextRow, nextCol)) {
                int onwardMoves = onwardMovesCount(nextRow, nextCol); 
                moves.add(new Position(nextRow, nextCol, onwardMoves)); 
            }
        }

        //sorting list into ascending order 
        moves.sort(null);

        for (Position move : moves) {
            int nextRow = move.getRow(); //method in position???
            int nextCol = move.getCol(); 
            board[nextRow][nextCol] = moveCount; 
            if (heuristic2(board, nextRow, nextCol, moveCount + 1)) {
                return true; 
            }
        }

        // No valid moves 
        return false; 
    }

    /**
     * Counts the number of onward moves there is for a move 
     * Helper method to heuristic2
     * @param row
     * @param col
     * @return number of onward moves 
     */
    public int onwardMovesCount(int row, int col) {
        int count = 0; 

        for (int i = 0; i < 8; i++) {
            int nextRow = row + rowMoves[i]; 
            int nextCol = col + colMoves[i]; 
            if (isAvailable(nextRow, nextCol)) {
                count++; 
            }
        }
        return count; 
    }

    /**
     * Calculates the distance to the border through finding the mininum between 
     * both distances on either side of the knight 
     * @param row
     * @param col
     * @return the smallest distance the knight is from the border 
     */
    public int distanceToBorder(int row, int col) {
        int distance = 0; 
        distance = Math.min(row, n - row) + Math.min(col, n - col); 
        return distance; 
    }

    public boolean searchBoard() {
        int solution[][] = new int[8][8]; 

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                solution[row][col] = -1; 
            }
        }

        int rowMoves[] = {2, 1, -1, -2, -2, -1, 1, 2};
        int colMoves[] = {1, 2, 2, 1, -1, -2, -2, -1};

        //starting position
        solution[0][0] = 0; 

        if (!solveKT(0, 0, 1, solution, rowMoves, colMoves)) {
            System.out.println("Solution does not exist"); 
            return false; 
        }else {
            finalBoard(solution); 
        }
        return true;
    }


    public boolean solveKT(int row, int col, int movei, int solution[][], int rowMoves[], int colMoves[]) {
        int k, next_row, next_col; 
        if (movei == n * n){
            return true; 
        }

        for (k = 0; k < 8; k++) {
            next_row = row + rowMoves[k]; 
            next_col = col + colMoves[k];
            if (isAvailable(next_row, next_col)) {
                solution[next_row][next_col] = movei; 
                if (solveKT(next_row, next_col, movei + 1, solution, rowMoves, colMoves)) {
                    return true; 
                } else {
                    solution[next_row][next_col] = -1;
                }
            }
        }
        return false; 
    }

    public void finalBoard(int solution[][]) {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                System.out.println(solution[row][col] + " "); 
            }
            System.out.println(); 
        }
    }


    public void main(String args[]) {
        searchBoard(); 
    }
}
