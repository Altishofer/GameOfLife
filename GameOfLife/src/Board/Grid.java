package Board;

import Board.Cell;
import Board.ColorType;
import Utils.InputUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    // TODO: mirrorGrid(), make mirroring on the flow: Pädi
    public Cell[][] aGrid;
    private Cell[][] aNextGrid;

    private final int aDimension;

    public Grid(int pDimension){
        aDimension = pDimension;
        aGrid = new Cell[aDimension][aDimension];
        aNextGrid = new Cell[aDimension][aDimension];
        this.initGrids();
    }

    //TODO: getColor from cell and player
    public void killACell(Cell pCell){
        if(!pCell.isAlive() && pCell.getColor()== ColorType.WHITE){
            throw new IllegalArgumentException("Please select a valid cell!");
        }
        pCell.kill();
    }

    public void reviveACell(Cell pCell){
        if(pCell.isAlive()){
            throw new IllegalArgumentException("Please select a dead cell!");
        }
        pCell.revive();
    }

    public void mirrorCell(String yx, Player placingPlayer){
        int[] coor = convert(InputUtils.cleanUpString(yx));

        int yCoor = coor[0];
        int xCoor = coor[1];

        reviveACell(yCoor, aDimension - xCoor);
    }

    private int[] convert(String string){
        ArrayList<String> coor = new ArrayList<>(Arrays.asList(string.split(":")[1].split(",")));
        int y = Integer.parseInt(coor.get(0));
        int x = Integer.parseInt(coor.get(1));
        return new int[]{y, x};
    }

    public void reviveACell(String yx){
        int[] coor = convert(InputUtils.cleanUpString(yx));

        int y = coor[0];
        int x = coor[1];
        if(aGrid[y][x].isAlive()){
            throw new IllegalArgumentException("Please select a dead cell!");
        }
        aGrid[y][x].revive();
    }

    public void reviveACell(int yCoor, int xCoor){
        if(aGrid[yCoor][xCoor].isAlive()){
            throw new IllegalArgumentException("Please select a dead cell!");
        }
        aGrid[yCoor][xCoor].revive();
    }


    // TODO: Try less branches
    // TODO: # of alive Cells from a specific player is not done yet
    public void createNextGeneration(){
        for(int i = 0; i < aDimension;i++) {
            for (int j = 0; j < aDimension;j++) {
                if(i == 0|| i == aDimension-1 || j == 0 || j == aDimension-1){
                    aNextGrid[i][j].passData(aGrid[i][j]);
                } else {
                    int numberOfNeighbors = countNeighbors(i, j);

                    if (!aGrid[i][j].isAlive() && numberOfNeighbors == 3) {
                        aNextGrid[i][j].revive();
                        ColorType dominator = getDominantColor(i, j);
                        aNextGrid[i][j].setColor(dominator);
                    } else if (aGrid[i][j].isAlive() && (numberOfNeighbors < 2 || numberOfNeighbors > 3)) {
                        aNextGrid[i][j].kill();
                    } else {
                        aNextGrid[i][j].passData(aGrid[i][j]);
                    }
                }
            }
        }
        this.makeGridsSame();
    }

    private void makeGridsSame(){

        for(int i = 0; i<aDimension;i++){
            for(int j = 0; j<aDimension;j++){
                aGrid[i][j].passData(aNextGrid[i][j]);
            }
        }
    }

    private int countNeighbors(int x, int y){
        int numberOfNeighbors = 0;

        for(int i = -1; i<2;i++) {
            for (int j = -1; j < 2; j++) {
                int col = (x+i) % aDimension;
                int row = (y+j) % aDimension;

                if(col != 0 && row != 0 && aGrid[col][row].isAlive()){
                    numberOfNeighbors++;
                }
            }
        }
        if(aGrid[x][y].isAlive()){
            numberOfNeighbors--;
        }
        return numberOfNeighbors;
    }

    private ColorType getDominantColor(int x, int y){
        int cntBlue = 0;
        int cntRed = 0;

        for(int i = -1; i < 2;i++) {
            for (int j = -1; j < 2; j++) {
                int col = (x+i) % aDimension;
                int row = (y+j) % aDimension;

                if(i!=0 && j != 0 && aGrid[col][row].getColor() == ColorType.BLUE && aGrid[col][row].isAlive()){
                    cntBlue++;
                }
                if(i!=0 && j != 0 && aGrid[col][row].getColor() == ColorType.RED && aGrid[col][row].isAlive()){
                    cntRed++;
                }
            }
        }

        if(cntRed>cntBlue){
            return ColorType.RED;
        }
        return ColorType.BLUE;
    }

    private void initGrids(){
        for(int i = 0; i<aDimension;i++){
            for(int j = 0; j<aDimension;j++){
                aGrid[i][j] = new Cell();
                aNextGrid[i][j] = new Cell();
            }
        }
    }

    // TODO: only for debugging
    private void printGrid(){

        for(int i = 0; i<aDimension;i++) {
            System.out.print("|");
            for (int j = 0; j < aDimension; j++) {
                if(aGrid[i][j].isAlive()){
                    if(aGrid[i][j].getColor() == ColorType.RED){
                        System.out.print("O|");
                    }else {
                        System.out.print("X|");
                    }
                }else {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
    }

    private void setupGrid(int x,int y, ColorType pColor){
        if(aGrid[x][y].isAlive()){
            throw new IllegalArgumentException("Cell is already alive");
        }
        aGrid[x][y].revive();
        aGrid[x][y].setColor(pColor);
    }

    public static void main(String[] args) {
        Grid myGrid = new Grid(10);
        myGrid.setupGrid(1,1,ColorType.RED);
        myGrid.setupGrid(2,1,ColorType.RED);
        myGrid.setupGrid(1,2,ColorType.RED);

        myGrid.setupGrid(4,7,ColorType.BLUE);
        myGrid.setupGrid(5,7,ColorType.BLUE);
        myGrid.setupGrid(6,7,ColorType.BLUE);

        myGrid.setupGrid(6,2,ColorType.RED);
        myGrid.setupGrid(7,1,ColorType.RED);
        myGrid.setupGrid(8,1,ColorType.RED);


        myGrid.printGrid();
        myGrid.createNextGeneration();
        System.out.println();
        myGrid.printGrid();
        myGrid.createNextGeneration();
        System.out.println();
        myGrid.printGrid();
    }

}

