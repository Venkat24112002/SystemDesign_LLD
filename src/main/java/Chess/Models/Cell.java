package Chess.Models;

public class Cell {
    int row;
    int col;
    Piece piece;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        piece = piece;
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

    public boolean isEmpty(){
        return piece == null;
    }
}
