package gdx.kapotopia.Game4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gdx.kapotopia.GlobalVariables;

public class Main extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    int x = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
    }
}
