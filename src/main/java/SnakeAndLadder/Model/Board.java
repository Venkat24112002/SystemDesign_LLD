package SnakeAndLadder.Model;

import java.util.concurrent.ThreadLocalRandom;

public class Board {
    public Cell[][] cells;

    public Board(int n,int snakes,int ladders){
        cells = new Cell[n][n];
        Initialize(n,snakes,ladders);
    }

    public void Initialize(int n,int snakes,int ladders){
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                cells[i][j] = new Cell();
            }
        }
        while(snakes>0){

            int randomNumber1 = ThreadLocalRandom.current().nextInt(1,n*n);
            int randomNumber2 = ThreadLocalRandom.current().nextInt(1,n*n);
            if(randomNumber1 > randomNumber2){
                Cell cell = getCell(randomNumber1);
                if(cell.jump == null) {
                    cell.jump = new Jump(randomNumber1, randomNumber2);
                    snakes--;
                }
            }
        }
        while(ladders>0) {
            int randomNumber1 = ThreadLocalRandom.current().nextInt(1,n*n);
            int randomNumber2 = ThreadLocalRandom.current().nextInt(1,n*n);

            if(randomNumber1 < randomNumber2){
                int row1 = randomNumber1/n;
                int col1 = randomNumber1%n;
                if(cells[row1][col1].jump == null) {
                    cells[row1][col1].jump = new Jump(randomNumber1, randomNumber2);
                    ladders--;
                }
            }
        }
    }

    public Cell getCell(int position){
        int row = position/ cells.length;
        int col = position/cells[0].length;
        return cells[row][col];
    }

}
