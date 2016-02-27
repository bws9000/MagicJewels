package com.wileynet.magicjewels;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wileynet.magicjewels.levelone.GamePlay;
import com.wileynet.magicjewels.levelone.LevelOne;
import com.wileynet.magicjewels.util.JewelAccessor;
import com.wileynet.magicjewels.util.JewelMapGrid;

public class GameScreen implements Screen {
	
	final MagicJewels game;
	
	//public Stage stage;
	public LevelOne levelone;
	public StartGame startGame;
	
	public JewelMapGrid jmg;
	
	
	//OrthographicCamera camera;
	public static TweenManager tweenManager;
	public static Timeline cs;
	
	public GameScreen(MagicJewels gam) {
		
		this.game = gam;
		
		Tween.registerAccessor(Jewel.class, new JewelAccessor());
		tweenManager = new TweenManager();
		//cs = Timeline.createSequence();
		
		//float w = Gdx.graphics.getWidth();
	    //float h = Gdx.graphics.getHeight();
	    
	    //System.out.println("W: " + w + " H: " + h);
	    //camera = new OrthographicCamera(1, h/w);
	    MagicJewels.camera = new OrthographicCamera();
        MagicJewels.camera.setToOrtho(false, 480, 800);
        
		MagicJewels.stage = new Stage();
		Gdx.input.setInputProcessor(MagicJewels.stage);//for click listener
		
		startGame = new StartGame(game);
		//ScoreScreen testScore = new ScoreScreen(game);
		MagicJewels.stage.addActor(startGame);

	}
	
	
	//Screen
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		MagicJewels.stage.act(delta);
		MagicJewels.stage.draw();
		tweenManager.update(delta);
		
	}
	
	@Override
	public void resize(int width, int height) {
		//float w = Gdx.graphics.getWidth();
	    //float h = Gdx.graphics.getHeight();
		//stage.getViewport().update((int)w, (int)h, true);
		//stage.getViewport().update(MagicJewels.PORTRAIT_WIDTH, MagicJewels.PORTRAIT_HEIGHT, true);
		MagicJewels.stage.getViewport().setCamera(MagicJewels.camera);
		MagicJewels.stage.getViewport().getCamera();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}
	

}
