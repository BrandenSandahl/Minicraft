package com.theironyard.minicraft;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by branden on 2/18/16 at 20:43.
 */
public class Zombie extends Character {
    final int WIDTH = 75;
    final int HEIGHT = 75;
    final float MAX_VELOCITY = 100;

    String name = "Patrick";
    long firstFrame;

    //write an onCollision method?

    //if (zombie xz == hero zx) zombie.talk "nobody puts baby in a corner";


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


        //i will have to change this logic here to figure out which direction Patrick is facing.
//        //grabbing a keystroke
//       Animation directionMove = directionAnimation; //this needs to be whatever it was before, in case user pushes....nothing.
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            this.yv = MAX_VELOCITY;
//            directionMove = this.walkUp;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            this.yv = MAX_VELOCITY * -1;
//            directionMove = this.walkDown;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            this.xv = MAX_VELOCITY;
//            directionMove = this.walkRight;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            this.xv = MAX_VELOCITY * -1;
//            directionMove = this.walkLeft;
//        }
        if (firstFrame != 0) {
            long currentFrame = Gdx.graphics.getFrameId();

            if (firstFrame + 60 == currentFrame) {  //how would i do this as a DeltaTime thing?
                this.x = (float) Math.random() * 50;
                this.y = (float) Math.random() * 50;
                firstFrame = currentFrame;
            } else {
                this.x = (Math.random() > .5) ? this.x + 1 : this.x - 1;  //this would be great if i was making a fucking bee.
                this.y = (Math.random() > .5) ? this.y + 1 : this.y -1;
            }
        } else {
            firstFrame = Gdx.graphics.getFrameId();
        }

//        this.y += this.yv * Gdx.graphics.getDeltaTime();
//        this.yv = decelerate(this.yv);
//        this.x += this.xv * Gdx.graphics.getDeltaTime();
//        this.xv = decelerate(this.xv);

        //return directionMove;
    }


}