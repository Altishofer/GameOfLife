package GameState;

import Board.ColorType;

public class Initialization implements GameState {

    private int aClickCount;
    Game aGame;
    public static final int MAX_INITIALIZATION = 5;

    public Initialization(Game newGame){
        aGame = newGame;
    }

    @Override
    public String getStateRule() {
        return "you are the chosen one and can select " + MAX_INITIALIZATION + " cells to start";
    }

    @Override
    public void clickedExistingCell(int y, int x, ColorType pColor) {
        System.out.println("NothingHappens: Cell already occupied");
    }

    @Override
    public void clickedEmptyCell(int y, int x, ColorType pColor) {
        aGame.mirrorCell(y, x);
        aClickCount++;
        if(initOver()){
            aGame.setState(aGame.getKill());
        }
    }

    @Override
    public boolean initOver() {
        return aClickCount >= MAX_INITIALIZATION;
    }
}
