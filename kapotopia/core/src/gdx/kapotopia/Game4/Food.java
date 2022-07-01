package gdx.kapotopia.Game4;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import org.graalvm.compiler.loop.MathUtil;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Screens.Game4;

public class Food {
    private float x;
    private float y;
    private int type; //0-2,3,4 unsafe, safe, protection
    private ImageButton sqr;
    private ImageButton logo;
    private Game4 screen;
    private float scaleSnake;
    private int xOffset;
    private int yOffset;
    private Kapotopia game;

    public Food(int boardSize, Game4 screen, float scaleSnake, int xOffset, int yOffset, Kapotopia game) {
        this.scaleSnake = scaleSnake;
        this.screen = screen;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.game = game;
        randomisePos(boardSize);
    }

    public void randomisePos(int boardSize) {
        x = MathUtils.random(boardSize-2);
        y = MathUtils.random(boardSize-1);
        type = MathUtils.random(4);
        ImageButton img = new ImageButtonBuilder()
                .withBounds(x * scaleSnake + xOffset, y * scaleSnake + yOffset, scaleSnake, scaleSnake)
                .withImageUp(game.ass.get(AssetDescriptors.ANULINGUS))
                .build();
        ImageButton sqr = new ImageButtonBuilder()
                .withBounds(x * scaleSnake + xOffset, y * scaleSnake + yOffset, scaleSnake, scaleSnake)
                .withImageUp(game.ass.get(AssetDescriptors.RED_SQUARE))
                .build();
        this.sqr = sqr;
        this.logo = img;
        screen.getStage().addActor(sqr);
        screen.getStage().addActor(img);
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

    public ImageButton getSqr() {
        return sqr;
    }

    public ImageButton getLogo() {
        return logo;
    }
}
