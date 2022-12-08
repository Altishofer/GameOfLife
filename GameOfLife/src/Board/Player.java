package Board;

public class Player implements Comparable{

    private final String PLAYER_NAME;

    public Player (String pName){
        PLAYER_NAME = pName;
    }

    public String getPlayerName(){return PLAYER_NAME;}

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
