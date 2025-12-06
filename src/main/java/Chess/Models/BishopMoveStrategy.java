package Chess.Models;

public class BishopMoveStrategy implements MoveStrategy{
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
            r += dr; c += dc;
        }

        return to.isEmpty() || to.getPiece().isWhite() != from.getPiece().isWhite();
    }
}
