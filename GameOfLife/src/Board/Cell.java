package Board;

import javax.swing.*;
import java.awt.*;


public class Cell extends JButton{

    private int status;
    private int xCoordinate;
    private int yCoordinate;
    private int oneNeighbours;
    private int twoNeighbours;
    Grid aGrid;

    Color colorP0 = Color.WHITE;
    Color colorP1 = Color.BLUE;
    Color colorP2 = Color.RED;

    public Cell(int xPlacement, int yPlacement, Grid pGrid) {
        xCoordinate = xPlacement;
        yCoordinate = yPlacement;
        status = 0;
        aGrid = pGrid;
    }

    public void updateNeighbours() {
        oneNeighbours = aGrid.countNeighbours(xCoordinate, yCoordinate, 1);
        twoNeighbours = aGrid.countNeighbours(xCoordinate, yCoordinate, 2);
    }
    public void evolutionStep(){
        if (!(oneNeighbours == 3 && twoNeighbours == 3)){
            if (oneNeighbours == 3){
                status = 1;
                return;}
            if (twoNeighbours == 3){
                status = 2;
                return;}
        }
        if (status == 1){
            if (oneNeighbours < 2 || oneNeighbours > 3){
                status = 0;
            }
        }
        if (status == 2){
            if (twoNeighbours < 2 || twoNeighbours > 3){
                status = 0;
            }
        }
    }

    public Color getColor(){
        switch(status){
            case 0: return colorP0;
            case 1: return colorP1;
            case 2: return colorP2;
            default: return null;
        }
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int change) {
        status = change;
    }
}
