package Board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {


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




























}