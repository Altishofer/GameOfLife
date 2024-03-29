package GameState;

import Board.ColorType;

public class Revive implements GameState {

    Game aGame;

    public Revive(Game newGame){
        aGame = newGame;
    }

    @Override
    public void clickedExistingCell(int y, int x, ColorType pColor) {
        System.out.print("NothingHappens: Cell already alive\n");
    }

    @Override
    public void clickedEmptyCell(int y, int x, ColorType pCurrentPlayerColor, ColorType pOtherPlayerColor) {
        aGame.reviveACell(y, x, pCurrentPlayerColor);
        aGame.setState(aGame.getKill());
        aGame.evolute();
    }

    @Override
    public boolean initOver() {
        return true;
    }

    @Override
    public String getStateRule() {
        return "select an empty cell to revive it";
    }
}
