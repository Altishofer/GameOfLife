package GameState;

import Board.ColorType;

public interface GameState {
    public void clickedExistingCell(int y, int x, ColorType pColor);
    public void clickedEmptyCell(int y, int x, ColorType pColor);
    public boolean initOver();
}
