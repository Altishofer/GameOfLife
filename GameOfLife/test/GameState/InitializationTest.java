package GameState;

import Board.ColorType;
import Board.Grid;
import Board.Player;
import Gui.SingletonGUI;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class InitializationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final StubGame sGame = new StubGame(20);
    private Initialization aInitialization = new Initialization(sGame, 20);

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
            System.out.print("mirrorCell executed");
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

    //TODO: Get access to aClickCount via metaprogramming
    @Test
    void clickedEmptyCellTest() {
        String expected = "mirrorCell executed";
        aInitialization.clickedEmptyCell(2, 2, null, null);
        String actual = outContent.toString();
        actual.replaceAll("\\s+", "");
        assertEquals(expected, actual);
    }
}