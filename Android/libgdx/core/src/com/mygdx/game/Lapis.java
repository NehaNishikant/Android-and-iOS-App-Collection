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

public class Lapis  extends Actor { //just like gridworld lol

    Sprite lapis=new Sprite(new Texture("spritemagic/magic1.png"));

    Rectangle bounds;
    boolean direction=true; //true for right (x++), false for left (x--)
    int changer;

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


    public Lapis(){
        toggle=false;

        textureAtlas =new TextureAtlas(Gdx.files.internal("spritesheetcoin/coin.atlas"));
        textureAtlas2=new TextureAtlas(Gdx.files.internal("spritesheetmagic/magic.atlas"));

        setPosition(0, 500);
        x=((int)(Math.random()*400)+10);
        setX(x);
        setY(800);
        setBounds(x, getY(), lapis.getWidth(), lapis.getHeight()); //x and y of actor are automatically at the bottom left at first.
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
                if (!toggle) {
                    MyGdxGame.scoreint++;
                }
                toggle=true;
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
            animation2 = new Animation(1/1f,textureAtlas2.getRegions());
            textureRegion2 = (TextureRegion)animation2.getKeyFrame(timeForStill,true);
            lapis.setRegion(textureRegion2);
        } else {
            animation = new Animation(1/6f,textureAtlas.getRegions());
            textureRegion = (TextureRegion)animation.getKeyFrame(timeForStill,true);
            lapis.setRegion(textureRegion);
            /*if (animation.isAnimationFinished(timeForStill)){
                sequenceAction.reset();
                sequenceAction.addAction(Actions.removeActor());
                addAction(sequenceAction);
            }*/
        }

        lapis.setPosition(getX(), getY()); //must also chang position of sprite. actor is just an invisible representation of information. the sprite is what you can eee
        lapis.setBounds(getX(), getY(), lapis.getWidth(), lapis.getHeight());

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        lapis.draw(batch);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public float getWidth(){
        return lapis.getWidth();
    }

    public float getHeight(){
        return lapis.getHeight();
    }
}


/*import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

public class Lapis  extends Actor { //just like gridworld lol

    Sprite lapis=new Sprite(new Texture("spritesheet/lapis.png")); //graphicalRepresentationOfChocolateObject

    public Lapis(){
        setBounds(getX(), getY(), lapis.getWidth(), lapis.getHeight()); //x and y of actor are automatically at the bottom left at first.
        setOrigin(getWidth()/2, getHeight()/2); //so actor expands from middle, not corner
        setTouchable(Touchable.enabled);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("hello");
                MoveToAction moveToAction=new MoveToAction();
                moveToAction.setPosition(300, 300); //where 2 go?
                moveToAction.setDuration(3f); //3 seconds, f just indicates that it is a float
                ScaleToAction scaleToAction=new ScaleToAction();
                scaleToAction.setScale(0.5f, 0.5f);
                scaleToAction.setDuration(3f);
                addAction(moveToAction);
                addAction(scaleToAction);
                //addAction(Actions.removeActor()); //must remove this way, or may get null pointer exception
                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        lapis.setPosition(getX(), getY()); //must also chang position of sprite. actor is just an invisible representation of information. the sprite is what you can eee
        lapis.setScale(getScaleX(), getScaleY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        lapis.draw(batch);
    }
}*/


/*package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

//Created by 10010422 on 5/26/2017.


public class Chocolate extends Actor { //just like gridworld lol

   Sprite chocolate=new Sprite(new Texture("spritesheet/chocolate.png")); //graphicalRepresentationOfChocolateObject

   public Chocolate(){
       setBounds(getX(), getY(), chocolate.getWidth(), chocolate.getHeight()); //x and y of actor are automatically at the bottom left at first.
       setOrigin(getWidth()/2, getHeight()/2); //so actor expands from middle, not corner
       setTouchable(Touchable.enabled);
       addListener(new InputListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               System.out.println("hello");
               MoveToAction moveToAction=new MoveToAction();
               moveToAction.setPosition(300, 300); //where 2 go?
               moveToAction.setDuration(3f); //3 seconds, f just indicates that it is a float
               ScaleToAction scaleToAction=new ScaleToAction();
               scaleToAction.setScale(0.5f, 0.5f);
               scaleToAction.setDuration(3f);
               addAction(moveToAction);
               addAction(scaleToAction);
               //addAction(Actions.removeActor()); //must remove this way, or may get null pointer exception
               return true;
           }
       });
   }

   @Override
   public void act(float delta) {
       super.act(delta);
       chocolate.setPosition(getX(), getY()); //must also chang position of sprite. actor is just an invisible representation of information. the sprite is what you can eee
       chocolate.setScale(getScaleX(), getScaleY());
   }

   @Override
   public void draw(Batch batch, float parentAlpha) {
       chocolate.draw(batch);
   }
}
*/

