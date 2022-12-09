package Board;

public enum ColorType {
    WHITE(255,255,255),
    LAVARED(247,52,43),
    ROYALBLUE(65,105,225);

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
}
