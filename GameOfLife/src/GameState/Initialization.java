package GameState;

import Board.ColorType;

public class Initialization implements GameState {

    private int aClickCount;
    Game aGame;
    public final int aMaxInitialization;

    public Initialization(Game newGame, int pMaxInitialization){
        aGame = newGame;
        aMaxInitialization = pMaxInitialization;
    }

    @Override
    public String getStateRule() {
        return "you are the chosen one and can select " + aMaxInitialization + " cells to start";
    }

    @Override
    public void clickedExistingCell(int y, int x, ColorType pColor) {
        System.out.println("NothingHappens: Cell already occupied");
    }

    @Override
    public void clickedEmptyCell(int y, int x, ColorType pCurrentPlayerColor, ColorType pOtherPlayerColor) {
        aGame.mirrorCell(y, x, pCurrentPlayerColor, pOtherPlayerColor);
        aClickCount++;
        if(initOver()){
            aGame.setState(aGame.getKill());
        }
    }

    @Override
    public boolean initOver() {
        return aClickCount >= aMaxInitialization;
    }
}
