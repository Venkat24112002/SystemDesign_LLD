package TitacToe.Models;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;


public class Board {
    public int size;
    public PlayingPiece[][] board;

    public Board(int size){
        this.size = size;
        board = new PlayingPiece[size][size];
    }

    public boolean addPiece(int row,int column, PlayingPiece playingPiece){
        if(board[row][column] != null) return false;
        board[row][column] = playingPiece;
        return true;
    }

//    public void Testing(){
//        List<List<Pair<Integer,Integer>>> table = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            List<Pair<Integer,Integer>> eachRow = new ArrayList<>();
//            for(int j=0;j<10;j++){
//                Pair<Integer,Integer> p = new Pair<>(i,j);
//                eachRow.add(p);
//            }
//            table.add(eachRow);
//        }
//    }

    public List<Pair<Integer,Integer>> getFreeCells(){
        List<Pair<Integer,Integer>> freeCells = new ArrayList<>();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j] == null) {
                    Pair<Integer, Integer> p = Pair.of(i, j);
                    freeCells.add(p);
                }
            }
        }
        return freeCells;
    }

    public void printBoard(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){

                if(board[i][j]==null) System.out.print(" ");
                else System.out.print(board[i][j].getPieceType());
            }
            System.out.println();
        }
    }
}
