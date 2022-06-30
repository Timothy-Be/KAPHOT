package gdx.kapotopia.Game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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
    private int boardSize = 30;  //  30 squares square
    private int yOffset = 400;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Queue<BodyPart> mBody = new Queue<BodyPart>();
    private Controls controls = new Controls();
    private CopyOnWriteArrayList<Food> foods= new CopyOnWriteArrayList<Food>();
    private int snakeLength = 3;
    long prevtime = 0;

    private float mTimer = 0;

    public GameState(Kapotopia game, Screen screen) {
        this.game = game;
        this.screen = screen;
        mBody.addLast(new BodyPart(15,15, boardSize));
        mBody.addLast(new BodyPart(15,14, boardSize));
        mBody.addLast(new BodyPart(15,13, boardSize));
    }

    public void update(float delta, Viewport viewport) {
        mTimer += delta;
        controls.update(viewport);
        long timestamp = System.currentTimeMillis() / 1000; // time in seconds
        if (timestamp % 4 == 0 && timestamp != prevtime) {
            foods.add(new Food(boardSize));
            if (foods.size() == 16) {   //max 15 foods on screen
                foods.remove(0);
            }
        }
        prevtime = timestamp;

        if (mTimer > 0.13f) { //change 0.13f to change snake speed
            mTimer = 0;
            advance();
        }
    }

    private void advance() {
        int headX = mBody.first().getX();
        int headY = mBody.first().getY();
        switch(controls.getDirection()) {
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
                    snakeLength++;
                } else if (f.getType() == 3 || f.getType() == 4) {
                    snakeLength--;
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

    public void draw(int width, int height, OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(1,1,1,1);
        shapeRenderer.rect(0, yOffset, width, width);

        shapeRenderer.setColor(0,0,0,1);
        shapeRenderer.rect(5, yOffset+5, width-5*2, width-5*2);

        shapeRenderer.setColor(1,1,1,1);

        //snake
        float scaleSnake = width/boardSize;
        for (BodyPart bp : mBody) {
            shapeRenderer.rect(bp.getX()*scaleSnake, bp.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake);
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
            shapeRenderer.rect(f.getX() * scaleSnake, f.getY() * scaleSnake + yOffset, scaleSnake, scaleSnake);
        }

        shapeRenderer.setColor(1,1,1,1);

        //buttons
        shapeRenderer.rect(235, 265, 130, 135);
        shapeRenderer.rect(235, 0, 130, 135);
        shapeRenderer.rect(105,135,130,130);
        shapeRenderer.rect(365,135,130,130);

        shapeRenderer.end();

    }
}
