package Chess.Models;

public class Board {
    public final static int size = 8;
    private Cell[][] grid;

    public Board() {
        grid = new Cell[size][size];
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                grid[i][j] = new Cell(i,j);
            }
        }
    }

    public Cell getCell(int r,int c) {
        if(r<0 || r>=size || c<0 || c>=size) return null;
        else return grid[r][c];
    }

    public void setPiece(Piece piece,int r,int c){
        grid[r][c].setPiece(piece);
    }

    public void initializeStandardSetup() {
        for (int c = 0; c < size; c++) {
            setPiece(new Pawn(true), 6, c);
            setPiece(new Pawn(false), 1, c);
        }
        setPiece(new Rook(true), 7, 0); setPiece(new Rook(true), 7, 7);
        setPiece(new Rook(false), 0, 0); setPiece(new Rook(false), 0, 7);
        setPiece(new Knight(true), 7, 1); setPiece(new Knight(true), 7, 6);
        setPiece(new Knight(false), 0, 1); setPiece(new Knight(false), 0, 6);
        setPiece(new Bishop(true), 7, 2); setPiece(new Bishop(true), 7, 5);
        setPiece(new Bishop(false), 0, 2); setPiece(new Bishop(false), 0, 5);
        setPiece(new Queen(true), 7, 3); setPiece(new Queen(false), 0, 3);
        setPiece(new King(true), 7, 4); setPiece(new King(false), 0, 4);
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
