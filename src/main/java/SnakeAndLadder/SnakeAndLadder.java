package SnakeAndLadder;

import net.engineeringdigest.journalApp.CompositeDesignPattern.SnakeAndLadder.Model.Board;
import net.engineeringdigest.journalApp.CompositeDesignPattern.SnakeAndLadder.Model.Cell;
import net.engineeringdigest.journalApp.CompositeDesignPattern.SnakeAndLadder.Model.Dice;
import net.engineeringdigest.journalApp.CompositeDesignPattern.SnakeAndLadder.Model.Player;

import java.util.Deque;
import java.util.LinkedList;

public class SnakeAndLadder {
    private Dice dice;
    private Deque<Player> playerList;
    private Board gameBoard;
    Player Winner;

    public SnakeAndLadder(){
        Initialize();
    }

    public void Initialize(){
        dice = new Dice(1);
        Player player1 = new Player(1);
        Player player2 = new Player(2);

        playerList = new LinkedList<>();
        playerList.add(player1);playerList.add(player2);
        gameBoard = new Board(10,5,4);
        System.out.println("hiii");
    }

    public void startGame(){
        Winner = null;
        while(Winner == null){

            Player currentPlayer = playerList.getFirst();
            playerList.removeFirst();

            int currentPosition = currentPlayer.getCurrentPosition();
            int currDiceCount = dice.rollDice();
            int newPosition = jumpCheck(currentPosition+currDiceCount);

            if(gameBoard.getCell(currentPosition+currDiceCount).jump==null){
                newPosition = currentPosition + currDiceCount;
            }
            else {
                newPosition = gameBoard.getCell(currentPosition+currDiceCount).jump.end;
            }

            System.out.println("currentPlayer" + currentPlayer.getId() + "position is " + newPosition);
            currentPlayer.setCurrentPosition(newPosition);
            if(currentPlayer.getCurrentPosition() >= 10*10) {
                Winner = currentPlayer;
            }
            playerList.addLast(currentPlayer);
        }
        System.out.println("the winner is" + Winner);
    }

    private int jumpCheck (int playerNewPosition) {
        if(playerNewPosition > gameBoard.cells.length * gameBoard.cells.length-1 ){
            return playerNewPosition;
        }
        Cell cell = gameBoard.getCell(playerNewPosition);
        if(cell.jump != null && cell.jump.start == playerNewPosition) {
            String jumpBy = (cell.jump.start < cell.jump.end)? "ladder" : "snake";
            System.out.println("jump done by: " + jumpBy);
            return cell.jump.end;
        }
        return playerNewPosition;
    }


}
