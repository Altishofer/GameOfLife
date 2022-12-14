package GameState;

import Board.ColorType;

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
    public void clickedEmptyCell(int y, int x, ColorType pColor) {
        game.reviveACell(y, x, pColor);
        game.setState(game.getKill());
        game.evolute();
    }

    @Override
    public boolean initOver() {
        return true;
    }
}
