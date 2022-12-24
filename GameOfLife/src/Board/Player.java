package Board;

import static java.lang.Math.min;

public class Player implements Comparable{
    private String aPlayerName;
    private ColorType aPlayerColor;
    public void setPlayerName(String pName){aPlayerName = pName;}
    public String getPlayerName(){return aPlayerName;}
    public ColorType getPlayerColor(){return aPlayerColor;}
    public void setPlayerColor(ColorType pColor){aPlayerColor = pColor;}
    @Override
    public int compareTo(Object other) {
        if (other == null){return 0;}
        if (this.getClass() != other.getClass()){return 0;}
        Player otherPlayer = (Player) other;
        int minNameSize = min(this.getPlayerName().length(), otherPlayer.getPlayerName().length());
        for (int i = 0; i < minNameSize; i++) {
            char currCharThis = Character.toUpperCase(this.aPlayerName.charAt(i));
            char currCharOther = Character.toUpperCase(otherPlayer.aPlayerName.charAt(i));
            if(currCharThis < currCharOther){return -1;}
            if(currCharThis > currCharOther){return 1;}
        }
        if (this.aPlayerName.length() == minNameSize) {return -1;}
        else {return 1;}
    }

    @Override
    public int hashCode(){
        return aPlayerName.length();
    }
}
