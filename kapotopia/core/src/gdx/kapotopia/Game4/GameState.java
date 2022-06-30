package gdx.kapotopia.Game4;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

public class GameState {

    private int boardSize = 30;  //  30 squares square
    private int yOffset = 400;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Queue<BodyPart> mBody = new Queue<BodyPart>();
    private Controls controls = new Controls();
    private Food mFood = new Food(boardSize);
    private int snakeLength = 3;

    private float mTimer = 0;

    public GameState() {
        mBody.addLast(new BodyPart(15,15, boardSize));
        mBody.addLast(new BodyPart(15,14, boardSize));
        mBody.addLast(new BodyPart(15,13, boardSize));
    }

    public void update(float delta, Viewport viewport) {
        mTimer += delta;
        controls.update(viewport);
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

        if (mBody.first().getX() == mFood.getX() && mBody.first().getY() == mFood.getY()) {
            snakeLength++;
            mFood.randomisePos(boardSize);
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

        //backgrounds
        shapeRenderer.setColor(1,1,1,1);

        //snake
        float scaleSnake = width/boardSize;
        for (BodyPart bp : mBody) {
            shapeRenderer.rect(bp.getX()*scaleSnake, bp.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake);
        }

        //Food
        shapeRenderer.rect(mFood.getX() * scaleSnake, mFood.getY()*scaleSnake + yOffset, scaleSnake, scaleSnake);

        //buttons
        shapeRenderer.rect(235, 265, 130, 135);
        shapeRenderer.rect(235, 0, 130, 135);
        shapeRenderer.rect(105,135,130,130);
        shapeRenderer.rect(365,135,130,130);

        shapeRenderer.end();

    }
}
