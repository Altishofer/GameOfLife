package Board;

import static java.lang.Math.min;

public class Player implements Comparable {
    private String aPlayerName;
    private ColorType aPlayerColor;
    public void setPlayerName(String pName){aPlayerName = pName;}
    public String getPlayerName(){return aPlayerName;}
    public ColorType getPlayerColor(){return aPlayerColor;}
    public void setPlayerColor(ColorType pColor){aPlayerColor = pColor;}
    @Override
    public int compareTo(Object other) {
        if (other == null){
            throw new NullPointerException();
        }
        if (this.getClass() != other.getClass()){
            throw new ClassCastException();
        }

        Player otherPlayer = (Player) other;
        int lengthThis = this.getPlayerName().length();
        int lengthOther = otherPlayer.getPlayerName().length();
        int limit = min(lengthThis, lengthOther);

        for(int i = 0; i<limit; i++){
            char currPlayerChar = Character.toLowerCase(this.getPlayerName().charAt(i));
            char otherPlayerChar = Character.toLowerCase(otherPlayer.getPlayerName().charAt(i));
            if(currPlayerChar != otherPlayerChar){
                return  currPlayerChar-otherPlayerChar;
            }
        }

        return lengthThis-lengthOther;
    }

    @Override
    public int hashCode(){
        return aPlayerName.length();
    }
}
