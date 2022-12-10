package Board;

import Gui.SingletonGUI;
import com.sun.tools.javac.Main;

import java.awt.*;

public class Player implements Comparable{

    private final String PLAYER_NAME;
    private final ColorType PLAYER_COLOR;

    private int cellCnt;

    public Player (String pName, ColorType player_color, int pCellCnt, boolean isPlayer1){
        PLAYER_NAME = pName;
        PLAYER_COLOR = player_color;

        cellCnt = pCellCnt;
    }

    public String getPlayerName(){return PLAYER_NAME;}
    public ColorType getPlayerColor(){return PLAYER_COLOR;}
    public int getCellCnt(){return cellCnt;}

    public Character getPlayerInitial(){return PLAYER_NAME.charAt(0);}

    @Override
    public int compareTo(Object other) {
        if (other == null){return 0;}
        if (this.getClass() != other.getClass()){return 0;}
        Player otherPlayer = (Player) other;
        if (this.getPlayerInitial() < otherPlayer.getPlayerInitial()){return -1;}
        if (this.getPlayerInitial() > otherPlayer.getPlayerInitial()){return 1;}
        return 0;
    }

    @Override
    public int hashCode(){
        return PLAYER_NAME.length();
    }
}
