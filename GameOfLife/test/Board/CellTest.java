package Board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {

    @Test
    public void testPassData() {
        Cell sourceCell = new Cell();
        sourceCell.revive(ColorType.BLUE);
        Cell targetCell = new Cell();
        targetCell.passData(sourceCell);
        assertTrue(targetCell.isAlive());
        assertEquals(ColorType.BLUE, targetCell.getColor());
    }

    @Test
    public void testGetColor() {
        Cell cell = new Cell();
        cell.revive(ColorType.BLUE);
        assertEquals(ColorType.BLUE, cell.getColor());
    }


    @Test
    public void testIsAlive() {
        Cell cell = new Cell();
        cell.revive(ColorType.BLUE);
        assertTrue(cell.isAlive());
    }


    @Test
    public void testKill() {
        Cell cell = new Cell();
        cell.revive(ColorType.BLUE);
        cell.kill();
        assertFalse(cell.isAlive());
        assertEquals(ColorType.WHITE, cell.getColor());
    }


    @Test
    public void testRevive() {
        Cell cell = new Cell();
        cell.revive(ColorType.BLUE);
        assertTrue(cell.isAlive());
        assertEquals(ColorType.BLUE, cell.getColor());
    }


















}