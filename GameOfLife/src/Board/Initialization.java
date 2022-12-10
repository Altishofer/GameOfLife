package Board;

public class Initialization implements GameState{

    private int clickCount;
    Game game;
    public static final int MAX_INITIALIZATION = 5;


    public Initialization(Game newGame){
        game = newGame;
    }

    @Override
    public void clickedExistingCell(int y, int x) {
        System.out.println("NothingHappens: Cell already occupied");
    }

    @Override
    public void clickedEmptyCell(int y, int x) {
        game.mirrorCell(y, x);
        clickCount++;
        game.setState(game.getKill());
    }

    @Override
    public boolean initOver() {
        return clickCount >= MAX_INITIALIZATION;
    }
}
