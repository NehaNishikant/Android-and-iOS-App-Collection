package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * Created by Neha on 6/10/2017.
 */

public class Coin extends Actor {

    Sprite coin=new Sprite(new Texture("spritecoin/coin1.png"));

    Animation animation;
    TextureRegion textureRegion;
    TextureAtlas textureAtlas;

    Animation animation2;
    TextureRegion textureRegion2;
    TextureAtlas textureAtlas2;

    SequenceAction sequenceAction;
    MoveToAction moveToAction;

    float timeForStill = 0.0f;
    boolean toggle;

    int x;

    public Coin(){
        toggle=false;

        textureAtlas =new TextureAtlas(Gdx.files.internal("spritesheetmoney/money.atlas"));
        textureAtlas2=new TextureAtlas(Gdx.files.internal("spritesheetcoin/coin.atlas"));

        setPosition(0, 500);
        x=((int)(Math.random()*400)+10);
        setX(x);
        setY(800);
        setBounds(x, getY(), coin.getWidth(), coin.getHeight()); //x and y of actor are automatically at the bottom left at first.
        setOrigin(getWidth()/2, getHeight()/2);

        sequenceAction=new SequenceAction();
        moveToAction=new MoveToAction();
        moveToAction.setPosition(x, 0);
        moveToAction.setDuration(5f);
        sequenceAction.addAction(moveToAction);
        sequenceAction.addAction(Actions.removeActor());
        addAction(sequenceAction);

        setTouchable(Touchable.enabled);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggle=true;
                MyGdxGame.scoreint++;
                System.out.println(MyGdxGame.scoreint);
                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        timeForStill += Gdx.graphics.getDeltaTime();

        if (toggle==false) {
            animation2 = new Animation(1/6f,textureAtlas2.getRegions());
            textureRegion2 = (TextureRegion)animation2.getKeyFrame(timeForStill,true);
            coin.setRegion(textureRegion2);
        } else {
            animation = new Animation(1/1f,textureAtlas.getRegions());
            textureRegion = (TextureRegion)animation.getKeyFrame(timeForStill,true);
            coin.setRegion(textureRegion);
            if (animation.isAnimationFinished(timeForStill)){
                sequenceAction.reset();
                sequenceAction.addAction(Actions.removeActor());
                addAction(sequenceAction);
            }
        }

        coin.setPosition(getX(), getY()); //must also chang position of sprite. actor is just an invisible representation of information. the sprite is what you can eee
        coin.setBounds(getX(), getY(), coin.getWidth(), coin.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        coin.draw(batch);
    }

    public float getHeight(){
        return coin.getHeight();
    }

    public float getWidth(){
        return coin.getWidth();
    }

}
