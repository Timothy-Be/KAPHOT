package gdx.kapotopia.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Helpers.Builders.ImageButtonBuilder;
import gdx.kapotopia.Helpers.ChangeScreenListener;
import gdx.kapotopia.Helpers.StandardInputAdapter;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class ChoosingSTDScreen implements Screen {

    private Kapotopia game;
    private Stage stage;
    private OrthographicCamera camera;

    private Sound clic;
    private Sound clicBlockedSound;
    private Sound pauseSound;

    public ChoosingSTDScreen(Kapotopia game) {
        this.game = game;
        this.stage = new Stage(game.viewport);
        this.camera = new OrthographicCamera(game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        this.camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f,0);
        this.camera.update();

        this.clic = game.ass.get(AssetDescriptors.SOUND_CLICKED_BTN);
        this.clicBlockedSound = game.ass.get(AssetDescriptors.SOUND_HINT);
        this.pauseSound = game.ass.get(AssetDescriptors.SOUND_PAUSE);

        int screenHeight = game.viewport.getScreenHeight();
        int screenWidth = game.viewport.getScreenWidth();

        ImageButton VIH_square = new ImageButtonBuilder()
                .withBounds(0,4*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.YELLOW_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton HEPA_square = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,4*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.PALEBLUE_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton HEPB_square = new ImageButtonBuilder()
                .withBounds(0,3*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.PALEBLUE_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton HEPC_square = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,3*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.YELLOW_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton SYPH_square = new ImageButtonBuilder()
                .withBounds(0,2*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.YELLOW_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton HERP_square = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,2*screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.PALEBLUE_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton PAPIL_square = new ImageButtonBuilder()
                .withBounds(0,screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.PALEBLUE_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton CHLA_square = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,screenHeight/5f, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.YELLOW_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton GONO_square = new ImageButtonBuilder()
                .withBounds(0,0, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.YELLOW_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();
        ImageButton TRICH_square = new ImageButtonBuilder()
                .withBounds(screenWidth/2f,0, screenWidth/2f, screenHeight/5f)
                .withImageUp(game.ass.get(AssetDescriptors.PALEBLUE_BACK))
                .withListener(new ChangeScreenListener(game, ScreenType.GAME4))
                .build();

        stage.addActor(VIH_square);
        stage.addActor(HEPA_square);
        stage.addActor(HEPB_square);
        stage.addActor(HEPC_square);
        stage.addActor(SYPH_square);
        stage.addActor(HERP_square);
        stage.addActor(PAPIL_square);
        stage.addActor(CHLA_square);
        stage.addActor(GONO_square);
        stage.addActor(TRICH_square);
    }

    @Override
    public void show() {
        setUpInputProcessor();
    }

    /**
     * Set up the input processor with the StandardInputAdapter
     */
    protected void setUpInputProcessor() {
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new StandardInputAdapter(this, game));
        im.addProcessor(stage);
        Gdx.input.setInputProcessor(im);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        pauseSound.play();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
