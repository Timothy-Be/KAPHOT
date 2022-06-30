package gdx.kapotopia.Game4;

public class BodyPart {
    private int x;
    private int y;

    public BodyPart(int x, int y, int boardsize) {
        this.x  = x % boardsize;
        if (this.x<0) {
            this.x += boardsize;
        }
        this.y  = y % boardsize;
        if (this.y<0) {
            this.y += boardsize;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
