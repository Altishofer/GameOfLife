package Board;

import javax.naming.directory.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid {

    private int aSize;
    Cell[][] cellsMap;
    private int generation;

    public Grid(int pSize) {
        aSize = pSize;
        cellsMap = new Cell[aSize][aSize];
        for (int col = 0; col < aSize; col++){
            for (int row = 0; row < aSize; row++) {
                cellsMap[col][row] = new Cell(col, row, this);
            }
        }
    }

    public void evolveCells() {
        for (int col = 0; col < aSize; col++){
            for (int row = 0; row < aSize; row++) {
                cellsMap[col][row].updateNeighbours();
            }
        }
        for (int col = 0; col < aSize; col++){
            for (int row = 0; row < aSize; row++) {
                cellsMap[col][row].evolutionStep();
            }
        }
        generation++;
    }

    public int countNeighbours(int x, int y, int status) {
        int cnt = 0;
        for (int i = x-1; i <= x+1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                if ((i == x && j == y) || (i < 0 || i > aSize - 1)|| (j < 0 || j > aSize - 1)){}
                else if (cellsMap[i][j].getStatus() == status) {cnt++;}
        return cnt;
    }

    //TODO: Remove after testing
    public void paintCell(int xPlacement, int yPlacement, int status){
        cellsMap[xPlacement][yPlacement].setStatus(status);
    }

    public void printGrid(){
        for (int col = 0; col < aSize; col++){
            String stringTemp = "";
            for (int row = 0; row < aSize; row++) {
                stringTemp += cellsMap[col][row].getStatus();
            }
            System.out.print(stringTemp + "\n");
        }
    }

    public int getGeneration(){
        return generation;
    }


    /*


    private int size;
    private int iconSize = 10;
    Random rnd = new Random();
    int[][] cellsMap; // 0 for white, 1 for colour of player1, 2 for colour of player2
    Cell buttonArray[][];
    Color colorP1 = Color.BLUE;
    Color colorP2 = Color.RED;

    public Grid(int pSize) {
        size = pSize;
        cellsMap = new int[pSize][pSize]; // assumed square grid
    }

    public void generation() throws InvalidAttributeValueException {
        int[][] temp = new int[size][size];
        // checking generation
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int cnt = countNeighbours(i, j);
                if (cellsMap[i][j] == 1 || cellsMap[i][j] == 2)
                    if (cnt == 2 || cnt == 3)
                        temp[i][j] = cellsMap[i][j]; // remains the same
                    else if (cnt == 3)
                        if (countPlayerOneNeighbors(i, j) < 2) // mainly playerTwo neighbors
                            temp[i][j] = 2;
                        else
                            temp[i][j] = 1;
            }
        }
        cellsMap = temp;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cellsMap[i][j] == 0)
                    buttonArray[i][j].setBackground(Color.WHITE);
                else if (cellsMap[i][j] == 1)
                    buttonArray[i][j].setBackground(colorP1);
                else if (cellsMap[i][j] == 2)
                    buttonArray[i][j].setBackground(colorP2);
                else
                    throw new InvalidAttributeValueException();
            }
        }
    }*/


}
