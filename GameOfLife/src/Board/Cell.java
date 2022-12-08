package Board;

public class Cell {

    private boolean aIsAlive;
    private ColorType aColor;

    public Cell(){
        aIsAlive = false;
        aColor = ColorType.WHITE;
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

    public void revive(){
        aIsAlive = true;
    }
}