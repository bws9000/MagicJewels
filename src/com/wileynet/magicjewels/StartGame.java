package com.wileynet.magicjewels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class StartGame extends Table {

	final MagicJewels game;

	Asset graphics;

	public StartGame(MagicJewels gam) {

		this.game = gam;
		
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.draw(Asset.uiAssets[0], 0, 0); // n_background1
		batch.draw(Asset.uiAssets[9], 0, 0); // startgame
		
		
		if (Gdx.input.justTouched()) {
            Vector3 touchpoint = new Vector3();
            touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            MagicJewels.camera.unproject(touchpoint);
            
            System.out.println("X:"+touchpoint.x + " Y:"+touchpoint.y);
           	
            //start
            if( (touchpoint.x > 0) &&
            		(touchpoint.y > 335 && touchpoint.y < 470)){
            	
            	this.remove();
            	game.startGame();
          
            	
            }
            
            
            //more games
            if( (touchpoint.x > 0) &&
            		(touchpoint.y > 209 && touchpoint.y < 303)){
            	
            	System.out.println("more games");
            	if(MagicJewels.application_type == 1){
            		game.actionResolver.showOrLoadInterstital();
            	}
            	
            }
            
		}
		
		super.draw(batch, parentAlpha);

	}

}
