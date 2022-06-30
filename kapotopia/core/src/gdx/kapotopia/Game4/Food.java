package gdx.kapotopia.Game4;

import com.badlogic.gdx.math.MathUtils;

import org.graalvm.compiler.loop.MathUtil;

public class Food {
    private float x;
    private float y;
    private int type; //0-2,3,4 unsafe, safe, protection

    public Food(int boardSize) {
        randomisePos(boardSize);
    }

    public void randomisePos(int boardSize) {
        x = MathUtils.random(boardSize-1);
        y = MathUtils.random(boardSize-1);
        type = MathUtils.random(4);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getType() {
        return type;
    }
}
