package GameState;

import Board.Grid;
import Board.Player;
import Gui.SingletonGUI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitializationTest {
    Game game = new Game(new Player(),new Player(),new Grid(10),new SingletonGUI(),10);
    Initialization init = new Initialization(game, 20);

    @Test
    void getStateRule() {
    }

    @Test
    void clickedExistingCell() {
    }

    @Test
    void clickedEmptyCell() {
    }

    @Test
    void initOver() {

    }
}