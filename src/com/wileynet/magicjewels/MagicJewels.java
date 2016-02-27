package com.wileynet.magicjewels;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wileynet.magicjewels.levelone.GamePlay;
import com.wileynet.magicjewels.levelone.LevelOne;
import com.wileynet.magicjewels.util.JewelMapGrid;
import com.wileynet.magicjewels.util.StageUtil;

public class MagicJewels extends Game {
	
	public boolean red_active;
	
	public static Group group;
	public static boolean end_the_game=false;
	
	public static boolean updateDraw=false;
	public static int countDownOneTime = 5;
	public static boolean gameStarted = false;
	public GamePlay gp;
	
	public ActionResolver actionResolver;
	public static int screen_top = 799;
	
	static JewelMapGrid jmg;
	
	public static OrthographicCamera camera;
	
	//modes
    public static boolean crazymode=false;
    public static int max_crazy_hits=4;
    public static int crazy_hits_count=0;
    public static boolean crazymodecount=true;
    public static int crazycount=0;
	
	private static StageUtil stageutil;
	public static Stage stage;

	// game settings
	public final static int PORTRAIT_WIDTH = 480;
	public final static int PORTRAIT_HEIGHT = 800;
	// desktop or android
	public static int application_type = 0; // 0=Desktop,1=Android

	// jewels
	public static int jewel_width = 58;
	public static int jewel_height = 58;

	// game grid
	public static int grid_rows = 10;
	public static int grid_columns = 8;

	public static int grid_width = jewel_width * grid_columns;
	public static int grid_height = jewel_height * grid_rows;

	public static int grid_start_x = 33;
	//public static int grid_start_y = grid_height + 20;
	public static int grid_start_y = grid_height;

	public static int jewel_count = grid_rows * grid_columns;

	// other
	public static int group_terminate_number = 3;
	public static boolean permission_to_click = true;

	// score
	public static int player_score;
	public static int player_score_before;
	public static boolean animatescore = false;
	public static int player_max_moves = 2;
	public static int player_moves;

	// sounds
	public static Sound scoreBlip;
	public static Sound swipe;
	public static Sound deleteJewels;
	public static Sound bump;
	public static Sound backgr;
	
	public static Sound chimeup1;
	public static Sound chimeup2;
	public static Sound chimeup3;
	public static Sound chimeup4;
	public static Sound chimeup5;
	public static Sound crazymode_explode;

	public GameScreen gameScreen;
	public StartGame startScreen;
	public ScoreScreen scoreScreen;
	
	public static LevelOne levelone;
	public static BitmapFont scoreFont;

	public static ArrayList<ArrayList<Actor>> finalclusters;
	
	// timer
	public static int current_minute;
	public static int current_second;
	public static int since_last_killcluster = 0;
	
	//animation
	public static Animation blink_animation_Purp;
	public static Animation blink_animation_Yell;
	public static Animation blink_animation_lBlu;
	public static Animation blink_animation_Gree;
	public static Animation blink_animation__Red;
	public static Animation blink_animation_Oran;
	public static Animation blink_animation_dBlu;

	public static Preferences pref;
	//public static FileHandle hs;
	

	// Android HTML5 ...
	public MagicJewels(ActionResolver actionResolver, int app_type) {
		application_type = app_type;
		this.actionResolver = actionResolver;
	}

	// Desktop for now ...
	public MagicJewels(int app_type) {
		application_type = app_type;
	}

	@Override
	public void create() {
		
		Asset.load();
		
		pref = Gdx.app.getPreferences("statichighscore");
	    
		//hs = Gdx.files.internal("prefs/statichighscore.txt");
		
		// http://www.bfxr.net/
		// sounds
		scoreBlip = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/blip_lower.wav"));
		swipe = Gdx.audio
				.newSound(Gdx.files.internal("data/sounds/swipe3.wav"));
		
		deleteJewels = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/chimeup_lower.wav"));
		
		chimeup1 = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/chimeup1.wav"));
		
		chimeup2 = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/chimeup2.wav"));
		
		chimeup3 = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/chimeup3.wav"));
		
		chimeup4 = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/chimeup4.wav"));
		
		chimeup5 = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/chimeup5.wav"));
		
		crazymode_explode = Gdx.audio.newSound(Gdx.files
				.internal("data/sounds/explode_lower1.wav"));
		
		bump = Gdx.audio.newSound(Gdx.files.internal("data/sounds/bump2.wav"));
		backgr = Gdx.audio.newSound(Gdx.files.internal("data/sounds/backgr.wav"));

		// fonts
		scoreFont = new BitmapFont(
				Gdx.files.internal("data/fonts/goboldgreen38.fnt"),
				Gdx.files.internal("data/fonts/goboldgreen38.png"), false);
		
		
		MagicJewels.blink_animation_Purp = new Animation(1/15f, Asset.purpjewelanim.getRegions());
		MagicJewels.blink_animation_Yell = new Animation(1/15f, Asset.yellowjewelanim.getRegions());
		MagicJewels.blink_animation_lBlu = new Animation(1/15f, Asset.lbluejewelanim.getRegions());
		MagicJewels.blink_animation_Gree = new Animation(1/15f, Asset.greenjewelanim.getRegions());
		MagicJewels.blink_animation__Red = new Animation(1/15f, Asset.redjewelanim.getRegions());
		MagicJewels.blink_animation_Oran = new Animation(1/15f, Asset.oranjewelanim.getRegions());
		MagicJewels.blink_animation_dBlu = new Animation(1/15f, Asset.dbluejewelanim.getRegions());
		
		
		jmg = new JewelMapGrid(this,stage);
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);

		// load admob
		// actionResolver.showOrLoadInterstital();
	}
	
	public void cleanup(){
		
		end_the_game=true;
		backgr.stop();
	    crazy_hits_count=0;
	    crazymodecount=true;
	    crazycount=0;
	    player_moves=0;
	    red_active=false;
	}

	@Override
	public void dispose() {
		// Asset.dispose();
		// gameScreen.dispose();
	}

	public void pause() {
		//
	}

	public void resume() {
		
		if(gameStarted){
			if(!end_the_game){
				jmg = new JewelMapGrid(this,stage);
				jmg.updateGridState();
			}
		}
		
	}

	public void hide() {
		//
	}

	

	public void removeBlinkAll() {
		
		jmg = new JewelMapGrid(this, MagicJewels.stage);
		jmg.updateGridState();
		
		stageutil = new StageUtil();
		
		Group g = stageutil.getTableGroup("gridgroup");
		ArrayList<Actor> actors = stageutil.getGroupActors(g);
		
		for (Actor j : actors) {
			Jewel groupJewel = (Jewel)j;
			groupJewel.setBlinkOff();
		}
		

	}

	public static Integer getRowFromSpot(int spot) {

		int row = 0;
		if (spot < MagicJewels.grid_columns) {
			//
		} else {

			row = spot / MagicJewels.grid_columns;

		}
		return row;

	}
	
	public void startGame(){
		levelone = new LevelOne(jmg,this);
		MagicJewels.stage.addActor(levelone);
		jmg = new JewelMapGrid(this,MagicJewels.stage);
		jmg.updateGridState();
		gp = new GamePlay(MagicJewels.stage,jmg,this);
		gameStarted = true;
	}
	
	public void showScore(){
		scoreScreen = new ScoreScreen(this);
		MagicJewels.stage.addActor(scoreScreen);
	}

}
