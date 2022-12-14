package GameState;

import Board.ColorType;
import GameState.GameState;
import Board.Grid;
import Board.Player;

public class Game implements GameState {
    private GameState initialisation;
    private GameState kill;
    private GameState revive;
    private GameState gameState;
    private final Player player1;
    private final Player player2;

    private Grid aGrid;

    public Game(Player newPlayer1, Player newPlayer2, Grid newGrid){
        aGrid = newGrid;
        player1 = newPlayer1;
        player2 = newPlayer2;
        initialisation = new Initialization(this);
        kill = new Kill(this);
        revive = new Revive(this);
        gameState = initialisation;
    }

    public void mirrorCell(int y, int x){
        aGrid.mirrorCell(y, x, player1, player2);
    }

    public void reviveACell(int y, int x, ColorType pColor){
        aGrid.reviveACell(y, x, pColor);
    }
    public void killACell(int y, int x){
        aGrid.killACell(y, x);
    }
    public void setState(GameState newState){
        gameState = newState;
    }
    public boolean initOver(){return gameState.initOver();}
    @Override
    public void clickedExistingCell(int y, int x, ColorType pColor){
        gameState.clickedExistingCell(y, x, pColor);
    }
    @Override
    public void clickedEmptyCell(int y, int x, ColorType pColor){
        gameState.clickedEmptyCell(y, x, pColor);
    }

    public GameState getInitialisation(){return initialisation;}
    public GameState getKill(){return kill;}
    public GameState getRevive(){return revive;}
    public void evolute(){
        aGrid.createNextGeneration();
    }
}
