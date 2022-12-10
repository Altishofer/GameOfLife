package Board;

public interface GameState {
    public void clickedExistingCell(int y, int x);
    public void clickedEmptyCell(int y, int x);

    public boolean initOver();
}
