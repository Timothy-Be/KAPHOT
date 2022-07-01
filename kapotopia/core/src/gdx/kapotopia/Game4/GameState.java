package gdx.kapotopia.Game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.text.View;

import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.ScreenType;

public class GameState {

    private final String TAG = this.getClass().getSimpleName();

    private Kapotopia game;
    Screen screen;

    private int totalScore;

    private int snakeSize = 10;  //  10-15 squares square
    private int boardSize = 11;
    private int yOffset = 308;
    private int xOffset = 30;
    private int direction = 0;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Queue<BodyPart> mBody = new Queue<BodyPart>();
    private CopyOnWriteArrayList<Food> foods= new CopyOnWriteArrayList<Food>();
    private int snakeLength = 3;
    long prevtime = 0;
    private boolean isPaused;
    private boolean isFinish;
    private final Rectangle bounds;

    private float mTimer = 0;

    public GameState(Kapotopia game, Screen screen) {
        this.game = game;
        this.screen = screen;
        this.isPaused = false;
        this.bounds = new Rectangle(0,0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());

        this.totalScore = 0;

        mBody.addLast(new BodyPart(15,15, boardSize));
        mBody.addLast(new BodyPart(15,14, boardSize));
        mBody.addLast(new BodyPart(15,13, boardSize));
    }

    public void setDirection(int nextDirection){
        if (direction == 0 && nextDirection != 2) {
            this.direction = nextDirection;
        } else if (direction == 2 && nextDirection != 0) {
            this.direction = nextDirection;
        } else if (direction == 1 && nextDirection != 3){
            this.direction = nextDirection;
        } else if (direction == 3 && nextDirection != 1){
            this.direction = nextDirection;
        }
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public void updateOnPause() {
        isPaused = true;
    }

    public void updateOnResume() {
        isPaused = true;
    }

    public void resumeFromPause() {
        isPaused = false;
    }

    public void updateOnHide() {
        isPaused = true;
        Gdx.app.debug(TAG, "game hidden - isPaused is true");
    }

    public void update(float delta, Viewport viewport) {
        if (!isPaused()){
            mTimer += delta;
            long timestamp = System.currentTimeMillis() / 1000; // time in seconds
            if (timestamp % 4 == 0 && timestamp != prevtime) {
                foods.add(new Food(snakeSize));
                if (foods.size() == 11) {   //max 10 foods on screen
                    foods.remove(0);
                }
            }
            prevtime = timestamp;

            if (mTimer > 0.13f) { //change 0.13f to change snake speed
                mTimer = 0;
                advance();
            }
        }

    }

    private void advance() {
        float headX = mBody.first().getX();
        float headY = mBody.first().getY();
        switch(this.direction) {
            case 0: //up
                mBody.addFirst(new BodyPart(headX, headY+1, boardSize));
                break;
            case 1: //right
                mBody.addFirst(new BodyPart(headX+1, headY, boardSize));
                break;
            case 2: //down
                mBody.addFirst(new BodyPart(headX, headY-1, boardSize));
                break;
            case 3: //left
                mBody.addFirst(new BodyPart(headX-1, headY, boardSize));
                break;
            default://should never happen
                mBody.addFirst(new BodyPart(headX, headY+1, boardSize));
                break;
        }

        for (Food f: foods) {
            if (mBody.first().getX() == f.getX() && mBody.first().getY() == f.getY()) {
                if (f.getType() < 3) {
                    this.totalScore += 10;
                    snakeLength++;
                } else if (f.getType() == 3 || f.getType() == 4) {
                    snakeLength--;
                    this.totalScore -= 10;
                    if (snakeLength == 0) {
                        game.destroyScreen(screen);
                        game.changeScreen(ScreenType.WORLD2);
                    }
                } else { //should never happen
                    snakeLength++;
                }
                foods.remove(f);
            }
        }

        for (int i = 1; i < mBody.size; i++) {  //when dire reset to length 3
            if (mBody.get(i).getX() == mBody.first().getX() &&
                    mBody.get(i).getY() == mBody.first().getY()) {
                snakeLength = 3;
            }
        }

        while (mBody.size - 1 >= snakeLength) {
            mBody.removeLast();
        }
    }

    public void draw(float width, float height, OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //snake
        float scaleSnake = width/snakeSize;
        for (BodyPart bp : mBody) {
            shapeRenderer.rect(bp.getX()*scaleSnake + xOffset, bp.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake);
        }

        //Food
        for (Food f: foods) {
            if (f.getType() < 3) {
                shapeRenderer.setColor(1,0,0,1);
            } else if (f.getType() == 3) {
                shapeRenderer.setColor(0,1,0,1);
            } else if (f.getType() == 4){
                shapeRenderer.setColor(0,0,1,1);
            } else { //should never happen
                shapeRenderer.setColor(1,0,0,1);
            }
            shapeRenderer.rect(f.getX() * scaleSnake + xOffset, f.getY() * scaleSnake + yOffset, scaleSnake, scaleSnake);
        }

        shapeRenderer.setColor(1,1,1,1);


        shapeRenderer.end();

    }

    // GETTERS

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
