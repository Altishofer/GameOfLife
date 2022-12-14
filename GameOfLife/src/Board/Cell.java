package Board;

public class Cell {

    private boolean aIsAlive;
    private ColorType aColor;

    public Cell(){
        aIsAlive = false;
        aColor = ColorType.WHITE;
    }

    public void passData(Cell pCell){
        this.aIsAlive = pCell.aIsAlive;
        this.aColor = pCell.aColor;
    }

    public ColorType getColor(){
        return this.aColor;
    }

    public boolean isAlive(){
        return aIsAlive;
    }

    public void kill(){
        aIsAlive = false;
        aColor = ColorType.WHITE;
    }

    public void revive(ColorType pColor){
        aIsAlive = true;
        aColor = pColor;
    }

    public boolean cellIsOwnedBy(Player player){
        return getColor().equals(player.getPlayerColor());
    }
}