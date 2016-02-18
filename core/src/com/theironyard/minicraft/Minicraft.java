package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;
    final float MAXVELOCITY = 200;

   // FitViewport view;


    SpriteBatch batch;
    TextureRegion down, up, right, left, directionTexture;
    Animation playerWalkUp, playerWalkDown, playerWalkRight, playerWalkLeft, directionAnimation;

    float x, y, xv, yv, time;

    @Override
    public void create () {
        batch = new SpriteBatch();
      //  view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //these calls get width and height

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        //player
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][3];
        left = new TextureRegion(right);
        left.flip(true, false);
        //player animations
        playerWalkUp = createAnimation(up, true, false);  //this is an animation for him walking 0.1 of a second
        playerWalkDown = createAnimation(down, true, false);
        playerWalkLeft = createAnimation(left, false, true);
        playerWalkRight = createAnimation(right, false, true);

        directionAnimation = playerWalkDown; //starting position
    }

    public Animation createAnimation(TextureRegion animationDirection, boolean x, boolean y) {
        TextureRegion temp = new TextureRegion(animationDirection);
        temp.flip(x, y);
        return new Animation(0.1f, animationDirection, temp);
    }

    @Override
    public void render () {
        time += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        directionAnimation = move();

        if (y < (HEIGHT * -1) ) {
            y = Gdx.graphics.getHeight();
        }
        if (y > Gdx.graphics.getHeight()) {
            y = (0 - HEIGHT);
        }
        if (x < (WIDTH * -1)) {
            x = Gdx.graphics.getWidth();
        }
        if ( x > Gdx.graphics.getWidth()) {
            x = (0 - WIDTH);
        }
        directionTexture = directionAnimation.getKeyFrame(time, true);
        batch.begin();
        batch.draw(this.directionTexture, x, y, WIDTH, HEIGHT);
        batch.end();
    }


    public float decelerate(float velocity ) {

        float deceleration = 0.8f; //closer to 1, slower the decel
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }

    public Animation move() {
        //grabbing a keystroke
        Animation directionMove = directionAnimation;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            yv = MAXVELOCITY;
            directionMove = playerWalkUp;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            yv = MAXVELOCITY * -1;
            directionMove = playerWalkDown;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xv = MAXVELOCITY;
            directionMove = playerWalkRight;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xv = MAXVELOCITY * -1;
            directionMove = playerWalkLeft;
        }

        y += yv * Gdx.graphics.getDeltaTime();
        yv = decelerate(yv);
        x += xv * Gdx.graphics.getDeltaTime();
        xv = decelerate(xv);

        return directionMove;
    }


}
