package Board;

public class Grid {

    private Cell[][] aGrid;
    private Cell[][] aNextGrid;
    private final int aDimension;

    public Grid(int pDimension){
        aDimension = pDimension;
        aGrid = new Cell[aDimension][aDimension];
        aNextGrid = new Cell[aDimension][aDimension];
        this.initGrid();
    }

    //TODO: getColor from cell and player
    public void killACell(Cell pCell){
        if(!pCell.isAlive() && pCell.getColor()==ColorType.WHITE){
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


    // TODO: Try less branches
    // TODO: # of alive Cells from a specific player is not done yet
    public void createNextGeneration(){
        for(int i = 0; i < aDimension;i++) {
            for (int j = 0; j < aDimension;j++) {
                if(i == 0|| i == aDimension-1 || j == 0 || j == aDimension-1){
                    aNextGrid[i][j] = aGrid[i][j];
                } else {
                    int numberOfNeighbors = countNeighbors(i, j);
                    ColorType dominator = getDominantColor(i, j);

                    if (!aGrid[i][j].isAlive() && numberOfNeighbors == 3) {
                        aNextGrid[i][j] = aGrid[i][j];
                        aNextGrid[i][j].revive();
                        aNextGrid[i][j].setColor(dominator);
                    } else if (aGrid[i][j].isAlive() && (numberOfNeighbors < 2 || numberOfNeighbors > 3)) {
                        aNextGrid[i][j] = aGrid[i][j];
                        aNextGrid[i][j].kill();
                    } else {
                        aNextGrid[i][j] = aGrid[i][j];
                    }
                }
            }
        }
        aGrid = aNextGrid;
    }

    private int countNeighbors(int x, int y){
        int numberOfNeighbors = 0;

        for(int i = -1; i<2;i++) {
            for (int j = -1; j < 2; j++) {
                int col = (x+i) % aDimension;
                int row = (y+j) % aDimension;

                if(col != 0 && row != 0 && aGrid[col][row].isAlive()){
                    numberOfNeighbors += 1;
                }
            }
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

                if(i!=0 && j != 0 && aGrid[col][row].getColor() == ColorType.ROYALBLUE){
                    cntBlue++;
                }
                if(i!=0 && j != 0 && aGrid[col][row].getColor() == ColorType.LAVARED){
                    cntRed++;
                }
            }
        }

        if(cntRed>cntBlue){
            return ColorType.LAVARED;
        }
        return ColorType.ROYALBLUE;
    }

    private void initGrid(){
        for(int i = 0; i<aDimension;i++){
            for(int j = 0; j<aDimension;j++){
                aGrid[i][j] = new Cell();
            }
        }
    }
}
