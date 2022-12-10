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

    public void setColor(ColorType x){
        aColor = x;
    }

    public ColorType getColor(){
        return this.aColor;
    }

    public boolean isAlive(){
        return aIsAlive;
    }

    public void kill(){
        aIsAlive = false;
    }

    //TODO: Revive Cell with right Color, not generally RED
    public void revive(){
        aIsAlive = true;
        aColor = ColorType.RED;
    }

    public boolean cellIsOwnedBy(Player player){
        return getColor().equals(player.getPlayerColor());
    }
}