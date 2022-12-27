package GameState;

import Board.ColorType;
import Board.Grid;
import Gui.SingletonGUI;

import javax.swing.*;

public class Game implements GameState {
    private final GameState aInitialization;
    private final GameState aKill;
    private final GameState aRevive;
    private GameState aGameState;
    private SingletonGUI aGui;
    private final Grid aGrid;
    private int aEvolutionCount;

    public Game(Grid newGrid, SingletonGUI pGui, int pInitialCellCount){
        aGrid = newGrid;
        aGui = pGui;
        aInitialization = new Initialization(this, pInitialCellCount);
        aKill = new Kill(this);
        aRevive = new Revive(this);
        aGameState = aInitialization;
        Runnable r = SingletonGUI::getInstance;
        SwingUtilities.invokeLater(r);
        aEvolutionCount = 0;
    }

    public void mirrorCell(int y, int x, ColorType pCurrentPlayerColor, ColorType pOtherPlayerColor){
        aGrid.mirrorCell(y, x, pCurrentPlayerColor, pOtherPlayerColor);
    }

    public void reviveACell(int y, int x, ColorType pColor){
        aGrid.revive(y, x, pColor);
    }
    public void killACell(int y, int x){
        aGrid.killACell(y, x);
    }
    public void setState(GameState newState){
        aGameState = newState;
    }
    public boolean initOver(){return aGameState.initOver();}
    @Override
    public void clickedExistingCell(int y, int x, ColorType pColor){
        aGameState.clickedExistingCell(y, x, pColor);
    }
    @Override
    public void clickedEmptyCell(int y, int x, ColorType pCurrentPlayerColor, ColorType pOtherPlayerColor){
        aGameState.clickedEmptyCell(y, x, pCurrentPlayerColor, pOtherPlayerColor);
    }
    public void switchCurrentPlayer(){
        aGui.switchCurrentPlayer();
    }
    public GameState getKill(){return aKill;}
    public GameState getRevive(){return aRevive;}
    public void evolute(){
        aGrid.createNextGeneration();
        switchCurrentPlayer();
        aEvolutionCount++;
    }

    public int getEvolutionCount(){return aEvolutionCount;}

    @Override
    public String getStateRule() {
        setMessage(aGui.getCurrentPlayerName() + ": " + aGameState.getStateRule());
        return new String();
    }

    public void setMessage(String pMessage){
        aGui.setMessage(pMessage);
    }
}
