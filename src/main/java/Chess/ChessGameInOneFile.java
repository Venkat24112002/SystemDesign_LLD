package Chess;

import java.util.Scanner;

// Move Strategy Interface
interface MoveStrategy {
    boolean canMove(Board board, Cell from, Cell to);
}

// Abstract Piece Class
abstract class Piece {
    private boolean isWhite;
    private MoveStrategy moveStrategy;
    boolean isKilled;
    String name;

    public Piece(boolean isWhite, MoveStrategy moveStrategy, String name) {
        this.isWhite = isWhite;
        this.moveStrategy = moveStrategy;
        isKilled = false;
        this.name = name;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public void setKilled() {
        isKilled = true;
    }

    public MoveStrategy getStrategy() {
        return moveStrategy;
    }
}

// Cell Class
class Cell {
    int row;
    int col;
    Piece piece;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.piece = null; // Fixed: was assigning piece to itself
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }
}

// Move Strategy Implementations
class PawnMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to) {
        if (to.getPiece() != null && to.getPiece().isWhite() == from.getPiece().isWhite()) {
            return false;
        }

        int dir = from.getPiece().isWhite() ? -1 : 1;
        int startRow = from.getPiece().isWhite() ? 6 : 1;

        int rowDiff = to.getRow() - from.getRow();
        int colDiff = Math.abs(to.getCol() - from.getCol());

        if (colDiff == 0) {
            if (rowDiff == dir && to.isEmpty()) return true;
            if (from.getRow() == startRow && rowDiff == 2 * dir &&
                    board.getCell(from.getRow() + dir, from.getCol()).isEmpty() && to.isEmpty())
                return true;
        }

        if (colDiff == 1 && rowDiff == dir && !to.isEmpty()) return true;

        return false;
    }
}

class RookMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to) {
        int r1 = from.getRow(), c1 = from.getCol();
        int r2 = to.getRow(), c2 = to.getCol();

        if (r1 != r2 && c1 != c2) return false;

        int dr = Integer.compare(r2, r1);
        int dc = Integer.compare(c2, c1);
        int r = r1 + dr, c = c1 + dc;

        while (r != r2 || c != c2) {
            if (!board.getCell(r, c).isEmpty()) return false;
            r += dr;
            c += dc;
        }

        return to.isEmpty() || to.getPiece().isWhite() != from.getPiece().isWhite();
    }
}

class BishopMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to) {
        int r1 = from.getRow(), c1 = from.getCol();
        int r2 = to.getRow(), c2 = to.getCol();
        if (Math.abs(r2 - r1) != Math.abs(c2 - c1)) return false;

        int dr = Integer.compare(r2, r1);
        int dc = Integer.compare(c2, c1);
        int r = r1 + dr, c = c1 + dc;

        while (r != r2 && c != c2) {
            if (!board.getCell(r, c).isEmpty()) return false;
            r += dr;
            c += dc;
        }

        return to.isEmpty() || to.getPiece().isWhite() != from.getPiece().isWhite();
    }
}

class QueenMoveStrategy implements MoveStrategy {
    private final RookMoveStrategy rook = new RookMoveStrategy();
    private final BishopMoveStrategy bishop = new BishopMoveStrategy();

    @Override
    public boolean canMove(Board board, Cell from, Cell to) {
        return rook.canMove(board, from, to) || bishop.canMove(board, from, to);
    }
}

class KingMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to) {
        int r = Math.abs(to.getRow() - from.getRow());
        int c = Math.abs(to.getCol() - from.getCol());
        if (r <= 1 && c <= 1) {
            Piece p = from.getPiece();
            return to.isEmpty() || to.getPiece().isWhite() != p.isWhite();
        }
        return false;
    }
}

class KnightMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Board board, Cell from, Cell to) {
        int r = Math.abs(to.getRow() - from.getRow());
        int c = Math.abs(to.getCol() - from.getCol());
        if ((r == 2 && c == 1) || (r == 1 && c == 2)) {
            Piece p = from.getPiece();
            return to.isEmpty() || to.getPiece().isWhite() != p.isWhite();
        }
        return false;
    }
}

// Piece Classes
class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite, new PawnMoveStrategy(), "P");
    }
}

class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite, new RookMoveStrategy(), "R");
    }
}

class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite, new BishopMoveStrategy(), "B");
    }
}

class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite, new QueenMoveStrategy(), "Q");
    }
}

class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite, new KingMoveStrategy(), "KI");
    }
}

class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite, new KnightMoveStrategy(), "K");
    }
}

class PieceFactory {
    public static Piece createPiece(String pieceType, boolean isWhite) {
        switch (pieceType.toUpperCase()) {
            case "PAWN":
                return new Pawn(isWhite);
            case "ROOK":
                return new Rook(isWhite);
            case "KNIGHT":
                return new Knight(isWhite);
            case "BISHOP":
                return new Bishop(isWhite);
            case "QUEEN":
                return new Queen(isWhite);
            case "KING":
                return new King(isWhite);
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}

// Board Class
class Board {
    public final static int size = 8;
    private Cell[][] grid;

    public Board() {
        grid = new Cell[size][size];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell getCell(int r, int c) {
        if (r < 0 || r >= size || c < 0 || c >= size) return null;
        else return grid[r][c];
    }

    public void setPiece(Piece piece, int r, int c) {
        grid[r][c].setPiece(piece);
    }

    public void initializeStandardSetup() {
        for (int c = 0; c < size; c++) {
            setPiece(PieceFactory.createPiece("PAWN", true), 6, c);
            setPiece(PieceFactory.createPiece("PAWN", false), 1, c);
        }
        setPiece(PieceFactory.createPiece("ROOK", true), 7, 0);
        setPiece(PieceFactory.createPiece("ROOK", true), 7, 7);
        setPiece(PieceFactory.createPiece("ROOK", false), 0, 0);
        setPiece(PieceFactory.createPiece("ROOK", false), 0, 7);
        setPiece(PieceFactory.createPiece("KNIGHT", true), 7, 1);
        setPiece(PieceFactory.createPiece("KNIGHT", true), 7, 6);
        setPiece(PieceFactory.createPiece("KNIGHT", false), 0, 1);
        setPiece(PieceFactory.createPiece("KNIGHT", false), 0, 6);
        setPiece(PieceFactory.createPiece("BISHOP", true), 7, 2);
        setPiece(PieceFactory.createPiece("BISHOP", true), 7, 5);
        setPiece(PieceFactory.createPiece("BISHOP", false), 0, 2);
        setPiece(PieceFactory.createPiece("BISHOP", false), 0, 5);
        setPiece(PieceFactory.createPiece("QUEEN", true), 7, 3);
        setPiece(PieceFactory.createPiece("QUEEN", false), 0, 3);
        setPiece(PieceFactory.createPiece("KING", true), 7, 4);
        setPiece(PieceFactory.createPiece("KING", false), 0, 4);
    }

    public boolean movePiece(Cell from, Cell to) {
        if (from == null || to == null || from.isEmpty()) return false;

        Piece movingPiece = from.getPiece();
        if (movingPiece.isKilled()) return false;

        if (!movingPiece.getStrategy().canMove(this, from, to)) return false;

        // Capture
        if (!to.isEmpty()) to.getPiece().setKilled();

        to.setPiece(movingPiece);
        from.setPiece(null);
        return true;
    }

    public void printBoard() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Piece p = grid[r][c].getPiece();
                System.out.print((p == null || p.isKilled() ? "." : p.name) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

// Player Class (for interactive gameplay)
class Player {
    private String name;
    private boolean isWhite;

    public Player(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
    }

    public String getName() {
        return name;
    }

    public boolean isWhite() {
        return isWhite;
    }
}

// Main Chess Game Class
public class ChessGameInOneFile {

    public static void main(String args[]) {
        Board board = new Board();
        board.initializeStandardSetup();
        board.printBoard();

        // Demo moves
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

        // Uncomment below for interactive gameplay
        // playInteractiveGame();
    }

    // Interactive gameplay method
    public static void playInteractiveGame() {
        Scanner sc = new Scanner(System.in);

        Player white = new Player("White", true);
        Player black = new Player("Black", false);

        Board board = new Board();
        board.initializeStandardSetup();

        Player currentPlayer = white;
        while (true) {
            board.printBoard();
            System.out.println(currentPlayer.getName() + "'s turn (format: e2 e4, or 'exit' to quit):");
            String line = sc.nextLine();
            if (line.equalsIgnoreCase("exit")) break;

            String[] parts = line.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid input, try again.");
                continue;
            }

            Cell from = convertInputToCell(parts[0], board);
            Cell to = convertInputToCell(parts[1], board);

            if (from == null || to == null || from.isEmpty() || from.getPiece().isWhite() != currentPlayer.isWhite()) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            boolean moved = board.movePiece(from, to);
            if (!moved) {
                System.out.println("Move not allowed by rules. Try again.");
                continue;
            }

            // alternate turn
            currentPlayer = currentPlayer == white ? black : white;
        }

        sc.close();
    }

    private static Cell convertInputToCell(String input, Board board) {
        if (input.length() != 2) return null;
        char file = input.charAt(0);
        char rank = input.charAt(1);
        int col = file - 'a';
        int row = 8 - (rank - '0');
        return board.getCell(row, col);
    }
}