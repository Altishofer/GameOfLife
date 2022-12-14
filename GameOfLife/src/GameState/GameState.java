package GameState;

import Board.ColorType;

public interface GameState {
    void clickedExistingCell(int y, int x, ColorType pCurrentPlayerColor);
    void clickedEmptyCell(int y, int x, ColorType pCurrentPlayerColor, ColorType pOtherPlayerColor);
    boolean initOver();
    String getStateRule();
}
