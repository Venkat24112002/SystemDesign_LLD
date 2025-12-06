package Chess.Models;

public class QueenMoveStrategy implements MoveStrategy{
    private final RookMoveStrategy rook = new RookMoveStrategy();
    private final BishopMoveStrategy bishop = new BishopMoveStrategy();

    @Override
    public boolean canMove(Board board, Cell from, Cell to) {
        return rook.canMove(board, from, to) || bishop.canMove(board, from, to);
    }
}
