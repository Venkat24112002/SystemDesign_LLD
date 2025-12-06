package Chess.Models;

public class PawnMoveStrategy implements MoveStrategy {
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
