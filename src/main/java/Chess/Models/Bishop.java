package Chess.Models;

public class Bishop extends Piece{
    public Bishop(boolean isWhite){
        super(isWhite, new BishopMoveStrategy(),"B");
    }
}
