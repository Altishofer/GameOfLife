package GameState;


import Board.ColorType;

public class Kill implements GameState {

    Game game;

    public Kill(Game newGame){
        game = newGame;
    }
    @Override
    public void clickedExistingCell(int y, int x, ColorType pColor) {
        game.killACell(y, x);
        game.setState(game.getRevive());
    }

    @Override
    public void clickedEmptyCell(int y, int x, ColorType pColor) {
        System.out.println("NothingHappens: Cell already dead");
    }

    @Override
    public boolean initOver() {
        return false;
    }
}
