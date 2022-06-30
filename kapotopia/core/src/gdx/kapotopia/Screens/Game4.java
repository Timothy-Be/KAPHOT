package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Game4.GameState;
import gdx.kapotopia.Kapotopia;

import com.badlogic.gdx.scenes.scene2d.Stage;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.ScreenType;


public class Game4 implements Screen {

    private Kapotopia game;
    private Stage stage;
    private Localisation loc;

    private float screenWidth;
    private float screenHeight;

    private final String TAG = this.getClass().getSimpleName();
    private GameState gameState;

    private OrthographicCamera camera;

    public Game4(final Kapotopia game) {
        this.game = game;
        this.loc = game.loc;
        gameState = new GameState(game, this);

        screenWidth = game.viewport.getWorldWidth();
        screenHeight = game.viewport.getWorldHeight();

        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        game.viewport.setCamera(camera);

        game.getSettings().setIntro_4_skip(true);

        loadAssets();

        Image background = new Image(game.ass.get(AssetDescriptors.BACKGROUND_GAME4));

        this.stage = new Stage(game.viewport);
        this.stage.addActor(background);
    }

    private void loadAssets() {
        long startTime = TimeUtils.millis();

        game.ass.finishLoading();
        Gdx.app.log(TAG, game.ass.getDiagnostics());
        Gdx.app.log(TAG, "Elapsed time for loading assets : " + TimeUtils.timeSinceMillis(startTime) + " ms");
    }

    @Override
    public void show() {
        InputMultiplexer iM = new InputMultiplexer();
        iM.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.destroyScreen(ScreenType.GAME3);
                    game.changeScreen(ScreenType.WORLD2);
                    return true;
                }
                return false;
            }
        });

        Gdx.input.setInputProcessor(iM);
        //Gdx.input.setInputProcessor(stage);
        game.getMusicControl().playMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        gameState.update(delta, game.viewport);
        gameState.draw(screenWidth, screenHeight, camera);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height);
    }

    @Override
    public void pause() {
        game.getMusicControl().pauseMusic();
    }

    @Override
    public void resume() {
        game.getMusicControl().playMusic();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
