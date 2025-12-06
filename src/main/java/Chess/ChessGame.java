package Chess;

public class ChessGame {


     public static void main(String args[]) {
         Board board = new Board();
         board.initializeStandardSetup();
         board.printBoard();

         Cell from = board.getCell(6, 4); // white pawn e2
         Cell to = board.getCell(4, 4);   // move to e4
         System.out.println("Move e2-e4: " + board.movePiece(from, to));
         board.printBoard();

         Cell from2 = board.getCell(1, 3); // black pawn d7
         Cell to2 = board.getCell(3, 3);   // move to d5
         System.out.println("Move d7-d5: " + board.movePiece(from2, to2));
         board.printBoard();

         // Capture example
         Cell from3 = board.getCell(4, 4); // white pawn e4
         Cell to3 = board.getCell(3, 3);   // capture d5
         System.out.println("Capture e4xd5: " + board.movePiece(from3, to3));
         board.printBoard();
     }

//    Scanner sc = new Scanner(System.in);
//
//    Player white = new Player("White", true);
//    Player black = new Player("Black", false);
//
//    Board board = new Board();
//        board.initializeStandardSetup();
//
//    Player currentPlayer = white;
//        while (true) {
//        board.printBoard();
//        System.out.println(currentPlayer.getName() + "'s turn (format: e2 e4):");
//        String line = sc.nextLine();
//        if (line.equalsIgnoreCase("exit")) break;
//
//        String[] parts = line.split(" ");
//        if (parts.length != 2) {
//            System.out.println("Invalid input, try again.");
//            continue;
//        }
//
//        Cell from = convertInputToCell(parts[0], board);
//        Cell to = convertInputToCell(parts[1], board);
//
//        if (from == null || to == null || from.isEmpty() || from.getPiece().isWhite() != currentPlayer.isWhite()) {
//            System.out.println("Invalid move. Try again.");
//            continue;
//        }
//
//        boolean moved = board.movePiece(from, to);
//        if (!moved) {
//            System.out.println("Move not allowed by rules. Try again.");
//            continue;
//        }
//
//        // alternate turn
//        currentPlayer = currentPlayer == white ? black : white;
//    }
//
//        sc.close();
//    }
//
//    private static Cell convertInputToCell(String input, Board board) {
//    if (input.length() != 2) return null;
//    char file = input.charAt(0);
//    char rank = input.charAt(1);
//    int col = file - 'a';
//    int row = 8 - (rank - '0');
//    return board.getCell(row, col);
//    }

}
