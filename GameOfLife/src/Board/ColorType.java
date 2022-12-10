package Board;

import java.awt.*;

public enum ColorType {
    WHITE(255,255,255),
    RED(247,52,43),
    BLUE(65,105,225),
    GREY(190,190,190);

    public final int aR;
    public final int aG;
    public final int aB;

    ColorType(int pR, int pG, int pB){
        this.aR = pR;
        this.aB = pB;
        this.aG = pG;
    }

    @Override
    public String toString(){
        return this.name().toLowerCase();
    }

    public Color toColor(){
        return new Color(this.aR, this.aG, this.aB);
    }
}
