public class KnightBoard {
    
    //size of array 
    static int n = 8; 

    static boolean isAvailable(int row, int col, int solution[][]) {
        return (row >= 0 && row < n && col >= 0 && col < n && solution[row][col] == -1); 
    }

    static boolean searchBoard() {
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


    static boolean solveKT(int row, int col, int movei, int solution[][], int rowMoves[], int colMoves[]) {
        int k, next_row, next_col; 
        if (movei == n * n){
            return true; 
        }

        for (k = 0; k < 8; k++) {
            next_row = row + rowMoves[k]; 
            next_col = col + colMoves[k];
            if (isAvailable(next_row, next_col, solution)) {
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

    static void finalBoard(int solution[][]) {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                System.out.println(solution[row][col] + " "); 
            }
            System.out.println(); 
        }
    }

    public static void main(String args[]) {
        searchBoard(); 
    }
}
