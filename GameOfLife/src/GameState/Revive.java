package GameState;

public class Revive implements GameState {

    Game game;

    public Revive(Game newGame){
        game = newGame;
    }

    @Override
    public void clickedExistingCell(int y, int x) {
        System.out.println("NothingHappens: Cell already alive");
    }

    @Override
    public void clickedEmptyCell(int y, int x) {
        game.reviveACell(y, x);
        game.setState(game.getKill());
        game.evolute();
    }

    @Override
    public boolean initOver() {
        return true;
    }
}
