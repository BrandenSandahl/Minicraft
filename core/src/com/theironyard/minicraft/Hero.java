package com.theironyard.minicraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by branden on 2/18/16 at 20:40.
 */
public class Hero extends Character {

    Animation directionAnimation;  //need this to keep track of which way the player is facing
    String name = "Dalton";
    final int WIDTH = 75;
    final int HEIGHT = 75;
    final float MAX_VELOCITY = 200;


   // Texture tiles = new Texture("tiles.png");


    public void drawHero() {
        TextureRegion[][] grid = TextureRegion.split(Minicraft.tiles, 16, 16);
        //player
        this.stand = grid[6][2];
        this.leftStand = new TextureRegion(this.stand);
        this.leftStand.flip(true, false);
        this.down = grid[6][0];
        this.up = grid[6][1];
        this.right = grid[6][3];
        this.left = new TextureRegion(this.right);
        this.left.flip(true, false);
        //player animations
        this.walkUp = Minicraft.createAnimationFlip(this.up, true, false);
        this.walkDown = Minicraft.createAnimationFlip(this.down, true, false);
        this.walkLeft = new Animation(0.25f, this.leftStand, this.left);
        this.walkRight = new Animation(0.25f, this.stand, this.right);
    }




    public float decelerate(float velocity ) {

        float deceleration = 0.7f; //closer to 1, slower the decel
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }

    public Animation heroMove() {
        //grabbing a keystroke
        Animation directionMove = directionAnimation; //this needs to be whatever it was before, in case user pushes....nothing.
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.yv = MAX_VELOCITY;
            directionMove = this.walkUp;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.yv = MAX_VELOCITY * -1;
            directionMove = this.walkDown;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.xv = MAX_VELOCITY;
            directionMove = this.walkRight;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.xv = MAX_VELOCITY * -1;
            directionMove = this.walkLeft;
        }

        this.y += this.yv * Gdx.graphics.getDeltaTime();
        this.yv = decelerate(this.yv);
        this.x += this.xv * Gdx.graphics.getDeltaTime();
        this.xv = decelerate(this.xv);

        return directionMove;
    }

    public void heroWrap() {
        //these ifs wrap the hero around the world.
        if (this.y < (HEIGHT * -1) ) {
            this.y = Gdx.graphics.getHeight();
        }
        if (this.y > Gdx.graphics.getHeight()) {
            this.y = (0 - HEIGHT);
        }
        if (this.x < (WIDTH * -1)) {
            this.x = Gdx.graphics.getWidth();
        }
        if ( this.x > Gdx.graphics.getWidth()) {
            this.x = (0 - WIDTH);
        }
    }


}