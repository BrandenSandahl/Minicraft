package com.theironyard.minicraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by branden on 2/18/16 at 20:43.
 */
public class Zombie extends Character {
    final int WIDTH = 75;
    final int HEIGHT = 75;
    final float MAX_VELOCITY = 75;

    String name = "Patrick";
    long firstFrame;

    Random random = new Random(); //lets do a T or F on this

    boolean moveUp, moveRight, isOnX = true, isOnY = true;


    //write an onCollision method?

    //if (zombie xz == hero zx) zombie.talk "nobody puts baby in a corner";

    public void drawZombie() {
        TextureRegion[][] grid = TextureRegion.split(Minicraft.tiles, 16, 16);
        //Zombie...makaZombie
        this.stand = grid[6][6];
        this.leftStand = new TextureRegion(this.stand);
        this.leftStand.flip(true, false);
        this.down = grid[6][4];
        this.up = grid[6][5];
        this.right = grid[6][7];
        this.left = new TextureRegion(this.right);
        this.left.flip(true, false);
        //zombie animation
        this.walkUp = Minicraft.createAnimationFlip(this.up, true, false);
        this.walkDown = Minicraft.createAnimationFlip(this.down, true, false);
        this.walkLeft = new Animation(.25f, this.leftStand, this.left);
        this.walkRight = new Animation(.25f, this.stand, this.right);
    }


    public Zombie() {
        this.x = (float) Math.random() * 500;
        this.y = (float) Math.random() * 500;
    }

    public float decelerate(float velocity ) {

        float deceleration = 2f; //closer to 1, slower the decel
        velocity *= deceleration;
        if (Math.abs(velocity) < 1) {
            velocity = 0;
        }
        return velocity;
    }

    public void zombieWrap() {
        //these ifs wrap the hero around the world.
        if (this.y < (HEIGHT * -1)) {
            this.y = Gdx.graphics.getHeight();
        }
        if (this.y > Gdx.graphics.getHeight()) {
            this.y = (0 - HEIGHT);
        }
        if (this.x < (WIDTH * -1)) {
            this.x = Gdx.graphics.getWidth();
        }
        if (this.x > Gdx.graphics.getWidth()) {
            this.x = (0 - WIDTH);
        }
    }

    public void zombieMove() {

        //i want to:
        //pick a random direction
        //accelerate in that direction X amount of frames
        //pick a new direction.

        if (firstFrame != 0) {
            long currentFrame = Gdx.graphics.getFrameId();


            if (firstFrame + 200 == currentFrame) {  //if time has passed?
                firstFrame = currentFrame;
                //set his movement pattern
                moveUp = random.nextBoolean(); //T then +Y, F then -Y
                moveRight = random.nextBoolean(); //T then +X, F then -X
                //need a little better change for this than 50/50 i think
                isOnX = (random.nextFloat() > .32); //randomly turns X movement on and off
                isOnY = (random.nextFloat() > .49); //randomly turns Y movement on and off
            } else {
               if (isOnX) {x = (moveRight) ? x + .5f : x - .5f; }  //if X movement is turned on, then randmly move up or down
               if (isOnY) {y = (moveUp) ? y + .5f: y - .5f; } //if Y movement is turned on, then randomly move up or down

            }
        }

        else{
            firstFrame = Gdx.graphics.getFrameId();
        }
    }

//    public Animation directionAnimation() {
//        Animation directionAnimation;
//        directionAnimation = isOnX
//    }

    }


