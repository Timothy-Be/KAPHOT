package gdx.kapotopia.Game4;

import com.badlogic.gdx.math.MathUtils;

import org.graalvm.compiler.loop.MathUtil;

public class Food {
    private int x;
    private int y;
    private int type; //0-2,3,4 unsafe, safe, protection

    public Food(int boardsize) {
        randomisePos(boardsize);
    }

    public void randomisePos(int boardsize) {
        x = MathUtils.random(boardsize-1);
        y = MathUtils.random(boardsize-1);
        type = MathUtils.random(4);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }
}
