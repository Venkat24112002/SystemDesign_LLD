package Chess.Models;

public class KnightMoveStrategy implements MoveStrategy{
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
