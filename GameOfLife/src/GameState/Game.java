package GameState;

import Board.ColorType;
import Board.Grid;
import Board.Player;
import Gui.SingletonGUI;

import javax.swing.*;

public class Game implements GameState {
    private GameState aInitialisation;
    private GameState aKill;
    private GameState aRevive;
    private GameState aGameState;
    private final Player aPlayer1;
    private final Player aPlayer2;
    private Grid aGrid;
    private boolean bothHavePlayed;
    private SingletonGUI aGui;

    public Game(Player newPlayer1, Player newPlayer2, Grid newGrid, SingletonGUI pGui){
        bothHavePlayed = false;
        aGrid = newGrid;
        aGui = pGui;
        aPlayer1 = newPlayer1;
        aPlayer2 = newPlayer2;
        aInitialisation = new Initialization(this);
        aKill = new Kill(this);
        aRevive = new Revive(this);
        aGameState = aInitialisation;
        Runnable r = () -> SingletonGUI.getInstance();
        SwingUtilities.invokeLater(r);
    }

    public void mirrorCell(int y, int x){
        aGrid.mirrorCell(y, x, aPlayer1, aPlayer2);
    }

    public void reviveACell(int y, int x, ColorType pColor){
        aGrid.reviveACell(y, x, pColor);
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
    public void clickedEmptyCell(int y, int x, ColorType pColor){
        aGameState.clickedEmptyCell(y, x, pColor);
    }

    public void switchCurrentPlayer(){
        aGui.switchCurrentPlayer();
    }

    public GameState getKill(){return aKill;}
    public GameState getRevive(){return aRevive;}

    public void evolute(){
        if (bothHavePlayed){
            aGrid.createNextGeneration();
            bothHavePlayed = false;
        }
        switchCurrentPlayer();
        bothHavePlayed = true;
    }

    public void setMessage(String pMessage){
        aGui.setMessage(pMessage);
    }
}
