package gdx.kapotopia.Game4;

public class BodyPart {
    private float x;
    private float y;

    public BodyPart(float x, float y, int boardSize) {
        this.x  = x % (boardSize - 3);
        if (this.x<0) {
            this.x += boardSize-3;
        }
        this.y  = y % boardSize;
        if (this.y<0) {
            this.y += boardSize;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
