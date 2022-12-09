package Board;

import javax.naming.directory.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid{

    private int iconSize = 10;
    Cell buttonArray[][];
    private int aSize;
    Cell[][] cellsMap;
    private int generation;

    public Grid(int pSize) {
        aSize = pSize;
        cellsMap = new Cell[aSize][aSize];
        for (int col = 0; col < aSize; col++) {
            for (int row = 0; row < aSize; row++) {
                cellsMap[col][row] = new Cell(col, row, this);
            }
        }
    }

    public void evolveCells() {
        for (int col = 0; col < aSize; col++) {
            for (int row = 0; row < aSize; row++) {
                cellsMap[col][row].updateNeighbours();
            }
        }
        for (int col = 0; col < aSize; col++) {
            for (int row = 0; row < aSize; row++) {
                cellsMap[col][row].evolutionStep();
            }
        }
        generation++;
    }

    public int countNeighbours(int x, int y, int status) {
        int cnt = 0;
        for (int row = x - 1; row <= x + 1; row++)
            for (int col = y - 1; col <= y + 1; col++)
                if ((row == x && col == y) || (row < 0 || row > aSize - 1) || (col < 0 || col > aSize - 1)) {
                } else if (cellsMap[row][col].getStatus() == status) {
                    cnt++;
                }
        return cnt;
    }

    public int getGeneration() {
        return generation;
    }

    public void paintCell(int xPlacement, int yPlacement, int status) {
        cellsMap[xPlacement][yPlacement].setStatus(status);
    }

    //TODO: Debugg - Remove after testing
    public void printGrid() {
        for (int col = 0; col < aSize; col++) {
            String stringTemp = "";
            for (int row = 0; row < aSize; row++) {
                stringTemp += cellsMap[col][row].getStatus();
            }
            System.out.print(stringTemp + "\n");
        }
    }
}