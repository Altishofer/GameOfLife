package GameState;

import Board.ColorType;
import Board.Grid;
import Board.Player;
import Gui.SingletonGUI;

import javax.swing.*;

public class Game implements GameState {
    private final Initialization aInitialisation;
    private final GameState aKill;
    private final GameState aRevive;
    private GameState aGameState;
    private final Player aPlayer1;
    private final Player aPlayer2;
    private SingletonGUI aGui;
    private final Grid aGrid;

    public Game(Player newPlayer1, Player newPlayer2, Grid newGrid, SingletonGUI pGui, int pInitialCellCount){
        aGrid = newGrid;
        aGui = pGui;
        aPlayer1 = newPlayer1;
        aPlayer2 = newPlayer2;
        aInitialisation = new Initialization(this, pInitialCellCount);
        aKill = new Kill(this);
        aRevive = new Revive(this);
        aGameState = aInitialisation;
        Runnable r = SingletonGUI::getInstance;
        SwingUtilities.invokeLater(r);
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
    }

    @Override
    public String getStateRule() {
        setMessage(aGui.getCurrentPlayerName() + ": " + aGameState.getStateRule());
        return new String();
    }

    public void setMessage(String pMessage){
        aGui.setMessage(pMessage);
    }
}
