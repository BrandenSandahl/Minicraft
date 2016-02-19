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


   // FitViewport view;

    static Texture tiles;

    Hero player = new Hero();
    Zombie zombie = new Zombie();


    SpriteBatch batch, zombieBatch;
    TextureRegion  directionTexture;
    Animation directionAnimation;


    float time;

    @Override
    public void create () {
        batch = new SpriteBatch();
        zombieBatch = new SpriteBatch();
      //  view = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //these calls get width and height


        tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);

        //draw up my hero
        player.drawHero();
        player.directionAnimation = player.walkDown; //starting position




    }


    public static Animation createAnimationFlip(TextureRegion animationDirection, boolean x, boolean y) {
        TextureRegion temp = new TextureRegion(animationDirection);
        temp.flip(x, y);
        return new Animation(0.25f, animationDirection, temp);
    }

    @Override
    public void render () {
        time += Gdx.graphics.getDeltaTime();  //this is required to make animations work
        Gdx.gl.glClearColor(0.3f, 0.5f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //hero stuff
        directionAnimation = player.heroMove();  //calls a method to calculate movement, returns a direction the Char is facing.
        player.heroWrap(); //wraps hero around the world
        //if hero is not moving, turn off the animation.
        directionTexture = (player.yv == 0 && player.xv == 0) ? directionAnimation.getKeyFrame(time) : directionAnimation.getKeyFrame(time, true);

        //zombie stuff
        zombie.drawZombie();
        zombie.zombieMove();
        zombie.zombieWrap();

        //zombie animation setting next time just use -1 0 1
        Animation zombieDirectionAnimation = null;
        if (zombie.moveRight && zombie.isOnX) { zombieDirectionAnimation = zombie.walkRight; }
        else if (!zombie.moveRight && zombie.isOnX) { zombieDirectionAnimation = zombie.walkLeft;}
        else if (zombie.moveUp) {zombieDirectionAnimation = zombie.walkUp; }
        else if (!zombie.moveUp) {zombieDirectionAnimation = zombie.walkDown; }





        TextureRegion zombieDirectionTexture = zombieDirectionAnimation.getKeyFrame(time, true);

        //hero batch
        batch.begin();
        batch.draw(this.directionTexture, player.x, player.y, player.WIDTH, player.HEIGHT);
        batch.end();

        //zombie batch
        zombieBatch.begin();
        zombieBatch.draw(zombieDirectionTexture, zombie.x, zombie.y, zombie.WIDTH, zombie.HEIGHT);
        zombieBatch.end();
    }



}
