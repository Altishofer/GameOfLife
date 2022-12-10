package GameState;


public class Kill implements GameState {

    Game game;

    public Kill(Game newGame){
        game = newGame;
    }
    @Override
    public void clickedExistingCell(int y, int x) {
        game.killACell(y, x);
        game.setState(game.getRevive());
    }

    @Override
    public void clickedEmptyCell(int y, int x) {
        System.out.println("NothingHappens: Cell already dead");
    }

    @Override
    public boolean initOver() {
        return true;
    }
}
