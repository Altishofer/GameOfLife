package Board;

import java.awt.*;

public class Grid {

    private final int aDimension;
    private final Cell[][] aGrid;
    private final Cell[][] aNextGrid;
    public Grid(int pDimension){
        aDimension = pDimension;
        aGrid = new Cell[aDimension][aDimension];
        aNextGrid = new Cell[aDimension][aDimension];
        this.initGrids();
    }

    public void killACell(int y, int x){
        Cell cell =  aGrid[y][x];
        if(!cell.isAlive() && cell.getColor()== ColorType.WHITE){
            throw new IllegalArgumentException("Please select a valid cell!");
        }
        cell.kill();
    }

    public void mirrorCell(int yCoor, int xCoor, ColorType pPlacingPlayerColor, ColorType pWaitingPlayerColor){
        revive(yCoor, xCoor, pPlacingPlayerColor);
        revive(yCoor, aDimension - xCoor-1, pWaitingPlayerColor);
    }

    public int getCellCount(ColorType pColor){
        int count = 0;
        for(int row = 0; row < aDimension; row++) {
            for(int col = 0; col < aDimension; col++) {
                if (aGrid[row][col].getColor() == pColor && aGrid[row][col].isAlive()) {
                    count++;
                }
            }
        }

        return count;
    }

    public void revive(int y, int x, ColorType pColor) throws IllegalArgumentException{
        if(aGrid[y][x].isAlive()){
            throw new IllegalArgumentException("Please select a dead cell!");
        }
        aGrid[y][x].revive(pColor);
    }

    public void createNextGeneration(){
        for(int i = 0; i < aDimension;i++) {
            for (int j = 0; j < aDimension;j++) {
                int numberOfNeighbors = countNeighbours(i, j);

                if (!aGrid[i][j].isAlive() && numberOfNeighbors == 3) {
                    ColorType dominator = getDominantColor(i, j);
                    aNextGrid[i][j].revive(dominator);
                } else if (aGrid[i][j].isAlive() && (numberOfNeighbors < 2 || numberOfNeighbors > 3)) {
                    aNextGrid[i][j].kill();
                } else {
                    aNextGrid[i][j].passData(aGrid[i][j]);
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

    public int countNeighbours(int x, int y) {
        int cnt = 0;
        for (int row = x - 1; row <= x + 1; row++) {
            for (int col = y - 1; col <= y + 1; col++) {
                if ((row == x && col == y) || (row < 0 || row > aDimension - 1) || (col < 0 || col > aDimension - 1)) {
                } else if (aGrid[row][col].isAlive()) {
                    cnt++;
                }
            }
        }
        return cnt;
    }


    private ColorType getDominantColor(int x, int y){
        int cntBlue = 0;
        int cntRed = 0;

        for (int row = x - 1; row <= x + 1; row++) {
            for (int col = y - 1; col <= y + 1; col++) {
                if ((row == x && col == y) || (row < 0 || row > aDimension - 1) || (col < 0 || col > aDimension - 1)) {
                } else if (aGrid[row][col].getColor() == ColorType.BLUE && aGrid[row][col].isAlive()) {
                    cntBlue++;
                } else if (aGrid[row][col].getColor() == ColorType.RED && aGrid[row][col].isAlive()) {
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

    public ColorType getColor(int y, int x){
        return aGrid[y][x].getColor();
    }

    public boolean isAlive(int y, int x){
        return aGrid[y][x].isAlive();
    }

    public boolean cellHasColor(int y, int x, ColorType color){
        return aGrid[y][x].getColor().equals(color);
    }

}

