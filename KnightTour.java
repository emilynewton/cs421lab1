public class KnightTour {
    public static void main(String[] args) {
        //creates an instance of the board given size argument 
        KnightBoard board = new KnightBoard(Integer.parseInt(args[1])); 

        if (Integer.parseInt(args[0]) == 0) {
            board.basicSearch(board.board, Integer.parseInt(args[2]), Integer.parseInt(args[3]), 0); 
        } else if (Integer.parseInt(args[0]) == 1) {
            board.heuristic1(board.board, Integer.parseInt(args[2]), Integer.parseInt(args[3]), 0);
        } else if (Integer.parseInt(args[0]) == 2) {
            board.heuristic2(board.board, Integer.parseInt(args[2]), Integer.parseInt(args[3]), 0);
        }
    }
}