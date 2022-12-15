package Board;

public class Grid {

    private final int aDimension;
    public final Cell[][] aGrid;
    private final Cell[][] aNextGrid;

    private final Player aPlayer1;

    private final Player aPlayer2;

    public Grid(int pDimension, Player pPlayer1, Player pPlayer2){
        aDimension = pDimension;
        aGrid = new Cell[aDimension][aDimension];
        aNextGrid = new Cell[aDimension][aDimension];
        aPlayer1 = pPlayer1;
        aPlayer2 = pPlayer2;
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
        reviveACell(yCoor, xCoor, pPlacingPlayerColor);
        reviveACell(yCoor, aDimension - xCoor-1, pWaitingPlayerColor);
    }

    public int getCellCount(ColorType pColor){
        int count = 0;
        for(int row = 0; row < aDimension; row++) {
            for(int col = 0; col < aDimension; col++) {
                if (aGrid[row][col].getColor() == pColor) {
                    count++;
                }
            }
        }

        return count;
    }

    public void reviveACell(int y, int x, ColorType pColor){
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
        aGrid[x][y].revive(pColor);
    }

    public static void main(String[] args) {
        Grid myGrid = new Grid(10, new Player(),new Player());
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

    public boolean cellIsAlive(int y, int x){
        return aGrid[y][x].isAlive();
    }

    public boolean cellhasColor(int y, int x, ColorType color){
        return aGrid[y][x].getColor().equals(color);
    }
}

