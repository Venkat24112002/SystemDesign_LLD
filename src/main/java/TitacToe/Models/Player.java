package TitacToe.Models;

public class Player {
    String name;
    PlayingPiece piece;

    public Player(String name, PlayingPiece piece){
        this.name = name;
        this.piece = piece;
    }

    public String getName(){
        return name;
    }

    public PlayingPiece getPiece() {
        return piece;
    }
}
