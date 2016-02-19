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

    Hero player = new Hero();
    Zombie zombie = new Zombie();


    SpriteBatch batch;
    TextureRegion playerDown, playerUp, playerRight, playerLeft, directionTexture, standPlayer, standLeftPlayer;
    TextureRegion zombieDown, zombieUp, zombieRight, zombieLeft, standZombie, standLeftZombie;
    Animation playerWalkUp, playerWalkDown, playerWalkRight, playerWalkLeft, directionAnimation;

    float x, y, xv, yv, time;

    @Override
    public void create () {
        batch = new SpriteBatch();
      //  view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //these calls get width and height

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        //player
        player.stand = grid[6][2];
        player.leftStand = new TextureRegion(player.stand);
        player.leftStand.flip(true, false);
        player.down = grid[6][0];
        player.up = grid[6][1];
        player.right = grid[6][3];
        player.left = new TextureRegion(player.right);
        player.left.flip(true, false);
        //player animations
        player.walkUp = createAnimationFlip(player.up, true, false);
        player.walkDown = createAnimationFlip(player.down, true, false);
        player.walkLeft = new Animation(0.25f, player.leftStand, player.left);  //not sure why this one is not working? Ask Zach.
        player.walkRight = new Animation(0.25f, player.stand, player.right);

        player.directionAnimation = player.walkDown; //starting position
    }


    public Animation createAnimationFlip(TextureRegion animationDirection, boolean x, boolean y) {
        TextureRegion temp = new TextureRegion(animationDirection);
        temp.flip(x, y);
        return new Animation(0.25f, animationDirection, temp);
    }

    @Override
    public void render () {
        time += Gdx.graphics.getDeltaTime();  //this is required to make animations work
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        directionAnimation = player.heroMove();  //calls a method to calculate movement, returns a direction the Char is facing.

        player.heroWrap(); //wraps hero around the world

        //I have an animation issue. Little guy wants to MOVE. Even when he should not.
        //if he is not moving, turn off the animation.
        directionTexture = (player.yv == 0 && player.xv == 0) ? directionAnimation.getKeyFrame(time) : directionAnimation.getKeyFrame(time, true);

        batch.begin();
        batch.draw(this.directionTexture, player.x, player.y, player.WIDTH, player.HEIGHT);
        batch.end();
    }



}
