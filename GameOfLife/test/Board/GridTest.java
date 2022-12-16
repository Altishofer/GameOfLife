package Board;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    private static final int DIMENSION = 5;
    Grid grid = new Grid(DIMENSION);

    @Test
    public void testMakeGridsSame() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {

        Method privateMethod = Grid.class.getDeclaredMethod("makeGridsSame");
        privateMethod.setAccessible(true);

        Field privateStringField = Grid.class.getDeclaredField("aNextGrid");
        privateStringField.setAccessible(true);
        Cell[][] nextGrid = (Cell[][]) privateStringField.get(grid);


        // Set the aNextGrid grid to have a different state than aGrid
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                nextGrid[i][j].revive(ColorType.BLUE);
                assertFalse(grid.aGrid[i][j].isAlive());
            }
        }

        String returnValue = (String)privateMethod.invoke(grid);

        // Confirm that the aNextGrid grid has the same state as aGrid
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                assertTrue(grid.aGrid[i][j].isAlive());
                assertEquals(ColorType.BLUE, grid.aGrid[i][j].getColor());
            }
        }
    }

    @Test
    public void testCountNeighbors() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method privateMethod = Grid.class.getDeclaredMethod("countNeighbours", int.class, int.class);
        privateMethod.setAccessible(true);

        // Set the state of the aGrid grid to test
        grid.aGrid[0][1].revive(ColorType.BLUE);
        grid.aGrid[1][0].revive(ColorType.BLUE);
        grid.aGrid[1][1].revive(ColorType.BLUE);
        grid.aGrid[1][2].revive(ColorType.BLUE);
        grid.aGrid[2][1].revive(ColorType.BLUE);

        int count = (int)privateMethod.invoke(grid, 1, 1);
        assertEquals(4, count);
    }

    @Test
    public void testGetDominantColor() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method privateMethod = Grid.class.getDeclaredMethod("getDominantColor", int.class, int.class);
        privateMethod.setAccessible(true);

        // Set the state of the aGrid grid to test
        grid.aGrid[0][1].revive(ColorType.BLUE);
        grid.aGrid[1][0].revive(ColorType.BLUE);
        grid.aGrid[1][1].revive(ColorType.RED);
        grid.aGrid[1][2].revive(ColorType.BLUE);
        grid.aGrid[2][1].revive(ColorType.BLUE);

        ColorType dominantColor = (ColorType) privateMethod.invoke(grid, 1, 1);
        assertEquals(ColorType.BLUE, dominantColor);
    }


    @Test
    public void testKillACell() {
        Grid grid = new Grid(10);
        grid.reviveACell(5, 5, ColorType.BLUE);
        grid.killACell(5, 5);
        assertFalse(grid.aGrid[5][5].isAlive());
        assertEquals(ColorType.WHITE, grid.aGrid[5][5].getColor());
    }

    @Test
    public void testMirrorCell() {
        Grid grid = new Grid(10);
        grid.mirrorCell(5, 5, ColorType.BLUE, ColorType.RED);
        assertTrue(grid.aGrid[5][5].isAlive());
        assertEquals(ColorType.BLUE, grid.aGrid[5][5].getColor());
        assertTrue(grid.aGrid[5][4].isAlive());
        assertEquals(ColorType.RED, grid.aGrid[5][4].getColor());
    }

    @Test
    public void testGetCellCount() {
        Grid grid = new Grid(10);
        grid.reviveACell(5, 5, ColorType.BLUE);
        grid.reviveACell(5, 6, ColorType.BLUE);
        grid.reviveACell(5, 7, ColorType.RED);
        assertEquals(2, grid.getCellCount(ColorType.BLUE));
    }

    @Test
    public void testReviveACell() {
        Grid grid = new Grid(10);
        grid.reviveACell(5, 5, ColorType.BLUE);
        assertTrue(grid.aGrid[5][5].isAlive());
        assertEquals(ColorType.BLUE, grid.aGrid[5][5].getColor());
    }


    @Test
    public void testCreateNextGeneration() {
        Grid grid = new Grid(10);

        // Test reviving a dead cell with 3 living neighbors
        grid.reviveACell(5, 4, ColorType.BLUE);
        grid.reviveACell(5, 5, ColorType.RED);
        grid.reviveACell(5, 6, ColorType.RED);
        grid.createNextGeneration();
        assertTrue(grid.aGrid[5][5].isAlive());
        assertEquals(ColorType.RED, grid.aGrid[5][5].getColor());

        // Test killing a living cell with less than 2 living neighbors
        grid.reviveACell(4, 5, ColorType.BLUE);
        grid.createNextGeneration();
        assertFalse(grid.aGrid[5][5].isAlive());

        // Test killing a living cell with more than 3 living neighbors
        grid.reviveACell(5, 5, ColorType.BLUE);
        grid.reviveACell(5, 4, ColorType.BLUE);
        grid.reviveACell(5, 6, ColorType.BLUE);
        grid.reviveACell(4, 5, ColorType.BLUE);
        grid.reviveACell(6, 5, ColorType.BLUE);
        grid.createNextGeneration();
        assertFalse(grid.aGrid[5][5].isAlive());

        // Test not killing a living cell with 2 or 3 living neighbors
        grid.reviveACell(5, 5, ColorType.BLUE);
        grid.reviveACell(5, 4, ColorType.BLUE);
        grid.reviveACell(5, 6, ColorType.BLUE);
        grid.createNextGeneration();
        assertTrue(grid.aGrid[5][5].isAlive());
    }




























}