package Board;

import javax.naming.directory.InvalidAttributeValueException;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Grid {

    int size;
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
    }

    int countNeighbours(int x, int y) {
        int cnt = 0;
        for (int i = x-1; i <= x+1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                if (i == x && j == y){}
                else if (cellsMap[i][j] == 1 || cellsMap[i][j] == 2) cnt++;
        if (cellsMap[x][y] == 1 || cellsMap[x][y] == 2) cnt--;
        return cnt;
    }

    int countPlayerOneNeighbors(int x, int y) {
        int cnt = 0;
        for (int i = x-1; i <= x+1; i++)
            for (int j = y - 1; j <= y + 1; j++)
                if (i == x && j == y){}
                else if (cellsMap[i][j] == 1) cnt++;
        if (cellsMap[x][y] == 1) cnt--;
        return cnt;
    }

}
