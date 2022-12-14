package GameState;

import Board.ColorType;

public interface GameState {
    void clickedExistingCell(int y, int x, ColorType pColor);
    void clickedEmptyCell(int y, int x, ColorType pColor);
    boolean initOver();
    String getStateRule();
}
