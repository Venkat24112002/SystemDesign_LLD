package Chess.Models;

public class Rook extends Piece{
    public Rook(boolean isWhite) {
        super(isWhite, new RookMoveStrategy(),"R");
    }
}
