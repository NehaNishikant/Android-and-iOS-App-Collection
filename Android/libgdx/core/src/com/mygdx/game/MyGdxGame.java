package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Stage stage;
    //Lapis lapis;
    Lapis lapis;
    static int scoreint=0;
    Label score;

    @Override
    public void create () {

        stage=new Stage();
        Gdx.input.setInputProcessor(stage);

        if (lapis==null) {
            lapis = new Lapis();
            stage.addActor(lapis);
        }

        if (stage.getActors().size<1){
            lapis=new Lapis();
            stage.addActor(lapis);
        }

        score=new Label("score: "+scoreint, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score.setText("score: "+scoreint);
        score.setPosition(0f, 700f);

        stage.addActor(score);

        stage.act();
        stage.draw();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (lapis==null){
            lapis=new Lapis();
            stage.addActor(lapis);
        }

        score.setText("score: "+scoreint);

        int x=0;
        for (int i=0; i<stage.getActors().size; i++){
            if (stage.getActors().get(i) instanceof Lapis){
                x++;
            }
        }
        if (x<1){
            lapis=new Lapis();
            stage.addActor(lapis);
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MyGdxGame extends ApplicationAdapter {
	Stage stage;


	@Override
	public void create () {
		stage=new Stage();
		Gdx.input.setInputProcessor(stage);
		//stage.addActor(new Chocolate());

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 1); //r, g, b, alpha(transparancy)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose () {
	}
}*/

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MyGdxGame extends ApplicationAdapter {

  Stage stage;


  @Override
  public void create () {
     stage=new Stage();
     Gdx.input.setInputProcessor(stage);
     stage.addActor(new Lapis());

  }

  @Override
  public void render () {
     Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 1); //r, g, b, alpha(transparancy)
     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     stage.act(Gdx.graphics.getDeltaTime());
     stage.draw();
  }

  @Override
  public void dispose () {
  }
}
*/

/*
sprite animation
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
  SpriteBatch batch;
  Texture img;

  TextureAtlas textureAtlas;
  TextureRegion textureRegion;
  Sprite sprite;
  int frame=1;

  @Override
  public void create () {
     batch = new SpriteBatch();
     img = new Texture("badlogic.jpg");

     textureAtlas=new TextureAtlas(Gdx.files.internal("spritesheet/lapis.atlas"));
     textureRegion=textureAtlas.findRegion("lapis1");
     sprite=new Sprite(textureRegion);
     sprite.setPosition(0, 0);
  }

  @Override
  public void render () {
     Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 1); //r, g, b, alpha(transparancy)
     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     batch.begin();
     batch.draw(img, 0, 0);
     sprite.setRegion(textureAtlas.findRegion("lapis"+frame));
     sprite.draw(batch);
     frame++;
     if (frame>2){
        frame=1;
     }
     batch.end();
  }

  @Override
  public void dispose () {
     batch.dispose();
     img.dispose();
  }
}
*/