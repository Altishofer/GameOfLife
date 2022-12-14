package GameState;


import Board.ColorType;

public class Kill implements GameState {

    Game aGame;

    public Kill(Game newGame){
        aGame = newGame;
    }
    @Override
    public void clickedExistingCell(int y, int x, ColorType pColor) {
        aGame.killACell(y, x);
        aGame.setState(aGame.getRevive());
    }

    @Override
    public void clickedEmptyCell(int y, int x, ColorType pColor) {
        aGame.setMessage("NothingHappens: Cell already dead");
    }

    @Override
    public boolean initOver() {
        return false;
    }
}
