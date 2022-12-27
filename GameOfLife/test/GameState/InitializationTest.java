package GameState;

import Board.ColorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class InitializationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final StubGame sGame = new StubGame(20);
    private final Initialization aInitialization = new Initialization(sGame, 20);

    @BeforeEach
    public void setStreams() {
        System.setOut(new PrintStream(outContent));
    }

    public static class StubGame extends Game{
        public StubGame(int pInitialCellCount) {
            super(null, null, null, null, pInitialCellCount);
        }

        @Override
        public void mirrorCell(int y, int x, ColorType pCurrentPlayerColor, ColorType pOtherPlayerColor){
            System.out.print("mirrorCell executed\n");
        }

        @Override
        public void setState(GameState newState) {
            System.out.print("setState executed\n");
        }
    }

    @Test
    void getStateRuleTest() {
        String expected = "you are the chosen one and can select 20 cells to start";
        String actual = aInitialization.getStateRule();
        assertEquals(expected, actual);
    }

    @Test
    void clickedExistingCellTest() {
        String expected = "NothingHappens: Cell already occupied\n";
        aInitialization.clickedExistingCell(0, 0, null);
        String actual = outContent.toString();
        actual.replaceAll("\\s+", "");
        assertEquals(expected, actual);
    }

    @Test
    void clickedEmptyCellNotOver() {
        String expected = "mirrorCell executed\n";
        aInitialization.clickedEmptyCell(2, 2, null, null);
        String actual = outContent.toString();
        actual.replaceAll("\\s+", "");
        assertEquals(expected, actual);
    }

    @Test
    void clickedEmptyCellOver() {
        try{
            Field field = Initialization.class.getDeclaredField("aClickCount");
            field.setAccessible(true);
            field.setInt(aInitialization,50);

            aInitialization.clickedEmptyCell(2, 2, null, null);
            String expected = "mirrorCell executed\nsetState executed\n";
            String actual = outContent.toString();
            actual.replaceAll("\\s+", "");

            assertEquals(expected, actual);
        } catch(ReflectiveOperationException e){
            e.printStackTrace();
        }
    }
}