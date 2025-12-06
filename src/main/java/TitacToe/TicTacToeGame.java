package TitacToe;

import TitacToe.Models.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {

    Deque<Player> players;
    Board gameBoard;

    TicTacToeGame(){
        initializeGame();
    }

    public void initializeGame(){

        players = new LinkedList<>();
        PlayingPieceX crossPiece = new PlayingPieceX();
        Player player1 = new Player("player1", crossPiece);

        PlayingPieceO noughtPiece = new PlayingPieceO();
        Player player2 = new Player("player2", noughtPiece);

        players.add(player1);
        players.add(player2);
        gameBoard = new Board(3);
    }

    public String startGame(){
        boolean noWinner = true;

        while(noWinner) {

            Player playerTurn = players.removeFirst();
            gameBoard.printBoard();
            List<Pair<Integer,Integer>> freeCells = gameBoard.getFreeCells();
            if(freeCells.isEmpty()){
                noWinner = false;
                continue;
            }

            Scanner inputScanner = new Scanner(System.in);
            String s= inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);

            boolean pieceAdded = gameBoard.addPiece(inputRow,inputColumn,playerTurn.getPiece());
            if(!pieceAdded){
                System.out.println("inccorect position, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputRow,inputColumn,playerTurn.getPiece().getPieceType());
            if(winner) {
                return playerTurn.getName();
            }
        }
        return  "tie";
    }

    public boolean isThereWinner(int row, int column, PieceType pieceType){
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].getPieceType() != pieceType) {
                rowMatch = false;
            }
        }

        //need to check in column
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[i][column] == null || gameBoard.board[i][column].pieceType != pieceType) {
                columnMatch = false;
            }
        }

        //need to check diagonals
        for(int i=0, j=0; i<gameBoard.size;i++,j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                diagonalMatch = false;
            }
        }

        //need to check anti-diagonals
        for(int i=0, j=gameBoard.size-1; i<gameBoard.size;i++,j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                antiDiagonalMatch = false;
            }
        }

        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }

}


