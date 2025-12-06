package Chess.Models;

public class Queen extends Piece{
    public Queen(boolean isWhite){
        super(isWhite, new QueenMoveStrategy(),"Q");
    }
}
