package SnakeAndLadder.Model;

public class Player {
    private int id;
    private int currentPosition;

    public Player(int id){
        this.id = id;
        currentPosition = 0;
    }

    public int getId() {
        return id;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
