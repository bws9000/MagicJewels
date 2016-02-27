package com.wileynet.magicjewels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ScoreScreen extends Table {

	final MagicJewels game;
	//String statichighscore = MagicJewels.hs.readString();
	String statichighscore = MagicJewels.pref.getString("statichighscore");
	String currentscore="";

	Asset graphics;

	public ScoreScreen(MagicJewels gam) {

		this.game = gam;
		
		if(statichighscore.equals("")){
			
			MagicJewels.pref.putString("statichighscore",Integer.toString(MagicJewels.player_score));
			//FileHandle file = Gdx.files.local("prefs/statichighscore.txt");
			//file.writeString(Integer.toString(MagicJewels.player_score), false);
			statichighscore = ""+MagicJewels.player_score;
			currentscore = ""+MagicJewels.player_score;
			
		}else{
			long s = Long.valueOf(MagicJewels.player_score);
			long hs = Long.valueOf(statichighscore);
			if(hs > s){
				statichighscore = ""+hs;
				currentscore = ""+MagicJewels.player_score; 
			}
			if(hs < s){
				statichighscore = ""+MagicJewels.player_score;
				currentscore = ""+MagicJewels.player_score;
			}
			MagicJewels.pref.putString("statichighscore",statichighscore);
			MagicJewels.pref.flush();
			//FileHandle file = Gdx.files.local("prefs/statichighscore.txt");
			//file.writeString(statichighscore, false);
		}
		
		
		
		MagicJewels.player_score=0;
	    MagicJewels.player_score_before=0;
	}
	
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		batch.draw(Asset.uiAssets[0], 0, 0); // n_background1
		batch.draw(Asset.uiAssets[10], 0, 0); // startgame
		
		MagicJewels.scoreFont.draw(batch, "" + currentscore, 49, 586);
		MagicJewels.scoreFont.draw(batch, statichighscore , 49, 505);
		
	    MagicJewels.player_score=0;
	    MagicJewels.player_score_before=0;
		
		
		if (Gdx.input.justTouched()) {
            Vector3 touchpoint = new Vector3();
            touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            MagicJewels.camera.unproject(touchpoint);
            
            System.out.println("X:"+touchpoint.x + " Y:"+touchpoint.y);
           	
            //play again
            if( (touchpoint.x > 0) &&
            		(touchpoint.y > 317 && touchpoint.y < 446)){
            	
            	
            	this.remove();
            	MagicJewels.end_the_game=false;
            	game.startGame();
            	
            	
            	
            }
            
            
            //more games
            if( (touchpoint.x > 0) &&
            		(touchpoint.y > 209 && touchpoint.y < 303)){
            	
            	
            	System.out.println("bottom:");
            	
            	if(MagicJewels.application_type == 1){
            		game.actionResolver.showOrLoadInterstital();
            	}
            	
            	
            }
            
		}
		
		super.draw(batch, parentAlpha);

	}

}
