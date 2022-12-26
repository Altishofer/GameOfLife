package Board;

//import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    private static final int DIMENSION = 10;
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
                assertFalse(grid.isAlive(i, j));
            }
        }

        String returnValue = (String)privateMethod.invoke(grid);

        // Confirm that the aNextGrid grid has the same state as aGrid
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                assertTrue(grid.isAlive(i, j));
                assertEquals(ColorType.BLUE, grid.getColor(i, j));
            }
        }
    }

    @Test
    public void testCountNeighbors() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method privateMethod = Grid.class.getDeclaredMethod("countNeighbours", int.class, int.class);
        privateMethod.setAccessible(true);

        // Set the state of the aGrid grid to test
        grid.revive(0, 1, ColorType.BLUE);
        grid.revive(1, 0,ColorType.BLUE);
        grid.revive(1, 1,ColorType.BLUE);
        grid.revive(1, 2,ColorType.BLUE);
        grid.revive(2, 1,ColorType.BLUE);

        int count = (int)privateMethod.invoke(grid, 1, 1);
        assertEquals(4, count);
    }

    @Test
    public void testGetDominantColor() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method privateMethod = Grid.class.getDeclaredMethod("getDominantColor", int.class, int.class);
        privateMethod.setAccessible(true);

        // Set the state of the aGrid grid to test
        grid.revive(0, 1, ColorType.BLUE);
        grid.revive(1, 0, ColorType.BLUE);
        grid.revive(1, 1, ColorType.RED);
        grid.revive(1, 2, ColorType.BLUE);
        grid.revive(2, 1, ColorType.BLUE);

        ColorType dominantColor = (ColorType) privateMethod.invoke(grid, 1, 1);
        assertEquals(ColorType.BLUE, dominantColor);
    }


    @Test
    public void testKillACell() {
        grid.revive(5, 5, ColorType.BLUE);
        grid.killACell(5, 5);
        assertFalse(grid.isAlive(5, 5));
        assertEquals(ColorType.WHITE, grid.getColor(5, 5));
    }

    @Test
    public void testMirrorCell() {
        grid.mirrorCell(5, 5, ColorType.BLUE, ColorType.RED);
        assertTrue(grid.isAlive(5, 5));
        assertEquals(ColorType.BLUE, grid.getColor(5, 5));
        assertTrue(grid.isAlive(5, 4));
        assertEquals(ColorType.RED, grid.getColor(5, 4));
    }

    @Test
    public void testGetCellCount() {
        grid.revive(5, 5, ColorType.BLUE);
        grid.revive(5, 6, ColorType.BLUE);
        grid.revive(5, 7, ColorType.RED);
        assertEquals(2, grid.getCellCount(ColorType.BLUE));
    }

    @Test
    public void testReviveACell() {
        grid.revive(5, 5, ColorType.BLUE);
        assertTrue(grid.isAlive(5, 5));
        assertEquals(ColorType.BLUE, grid.getColor(5, 5));
    }


    @Test
    public void testCreateNextGeneration_3livingNeighbours() {
        // Test reviving a dead cell with 3 living neighbors
        grid.revive(5, 4, ColorType.BLUE);
        grid.revive(5, 5, ColorType.RED);
        grid.revive(5, 6, ColorType.RED);
        grid.createNextGeneration();
        assertTrue(grid.isAlive(4, 5));
        assertEquals(ColorType.RED, grid.getColor(4, 5));
    }

    @Test
    public void testCreateNextGeneration_1livingNeighbours() {
        // Test killing a living cell with less than 2 living neighbors
        grid.revive(5, 5, ColorType.RED);
        grid.revive(5, 6, ColorType.RED);
        grid.createNextGeneration();
        assertFalse(grid.isAlive(5, 5));
    }

    @Test
    public void testCreateNextGeneration_4livingNeighbours() {
        // Test killing a living cell with more than 3 living neighbors
        grid.revive(5, 5, ColorType.BLUE);
        grid.revive(5, 4, ColorType.BLUE);
        grid.revive(5, 6, ColorType.BLUE);
        grid.revive(4, 5, ColorType.BLUE);
        grid.revive(6, 5, ColorType.BLUE);
        grid.createNextGeneration();
        assertFalse(grid.isAlive(5, 5));
    }

    @Test
    public void testCreateNextGeneration_2livingNeighbours() {
        // Test not killing a living cell with 2 or 3 living neighbors
        grid.revive(5, 5, ColorType.BLUE);
        grid.revive(5, 4, ColorType.BLUE);
        grid.revive(5, 6, ColorType.BLUE);
        grid.createNextGeneration();
        assertTrue(grid.isAlive(5, 5));
    }


    @Test
    public void testReviveIllegalArgument(){
        // Test reviving a alive cell
        grid.revive(5, 4, ColorType.BLUE);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {grid.revive(5, 4, ColorType.BLUE);});
    }

    @Test
    public void testKillACellIllegalArgument(){
        // Test killing a dead cell
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {grid.killACell(5, 4);});
    }

    @Test
    public void testHasColor(){
        // Test reviving a alive cell
        grid.revive(5, 4, ColorType.BLUE);
        assertTrue(grid.cellHasColor(5,4,ColorType.BLUE));
    }




























}