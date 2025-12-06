package Chess.Models;

public class Knight extends Piece{
    public Knight(boolean isWhite) {
        super(isWhite, new KnightMoveStrategy(),"K");
    }
}
