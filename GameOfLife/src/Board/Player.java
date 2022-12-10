package Board;

import static java.lang.Math.min;

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

    public Character getPlayerInitial(){return Character.toUpperCase(PLAYER_NAME.charAt(0));}

    @Override
    public int compareTo(Object other) {
        if (other == null){return 0;}
        if (this.getClass() != other.getClass()){return 0;}
        Player otherPlayer = (Player) other;
        int minNameSize = min(this.getPlayerName().length(), otherPlayer.getPlayerName().length());
        for (int i = 0; i < minNameSize; i++) {
            char currCharThis = Character.toUpperCase(this.PLAYER_NAME.charAt(i));
            char currCharOther = Character.toUpperCase(otherPlayer.PLAYER_NAME.charAt(i));
            if(currCharThis < currCharOther){return -1;}
            if(currCharThis > currCharOther){return 1;}
        }
        if (this.PLAYER_NAME.length() == minNameSize) {return -1;}
        else {return 1;}
        //if (this.getPlayerInitial() < otherPlayer.getPlayerInitial()){return -1;}
        //if (this.getPlayerInitial() > otherPlayer.getPlayerInitial()){return 1;}
        //return 0;
    }

    @Override
    public int hashCode(){
        return PLAYER_NAME.length();
    }
}
