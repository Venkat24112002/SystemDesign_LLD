package Chess.Models;

public abstract class Piece {
    private boolean isWhite;
    private MoveStrategy moveStrategy;
    boolean isKilled;
    String name;

    public Piece(boolean isWhite, MoveStrategy moveStrategy,String name) {
        this.isWhite = isWhite;
        this.moveStrategy = moveStrategy;
        isKilled = false;
        this.name = name;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public boolean isKilled() {
        return isKilled;
    }

    public void setKilled() {
        isKilled = true;
    }

    public MoveStrategy getStrategy() {
        return moveStrategy;
    }
}
