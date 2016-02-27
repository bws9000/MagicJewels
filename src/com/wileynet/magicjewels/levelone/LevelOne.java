package com.wileynet.magicjewels.levelone;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.wileynet.magicjewels.Asset;
import com.wileynet.magicjewels.GdxCountdownTimer;
import com.wileynet.magicjewels.GdxIncrementTimer;
import com.wileynet.magicjewels.Jewel;
import com.wileynet.magicjewels.MagicJewels;
import com.wileynet.magicjewels.util.JewelMapGrid;
import com.wileynet.magicjewels.util.StageUtil;

public class LevelOne extends Table {

	final MagicJewels game;
	boolean set_blink_once = true;
	

	// countdown timer
	static Timer timer;
	long startTime;
	GdxCountdownTimer countdownTime;

	// new timer
	static Timer timer2;
	long st;
	GdxIncrementTimer sinceLastKill;

	private ArrayList<Jewel> jewels;

	JewelArray ja;
	StageUtil util;
	Group group;
	StageUtil stageutil;
	JewelMapGrid jmg;
	Asset graphics;

	private int score;

	public LevelOne(JewelMapGrid jmg, MagicJewels gam) {

		// TIMER countdown
		timer = new Timer();
		startTime = TimeUtils.nanoTime();
		countdownTime = new GdxCountdownTimer(startTime, MagicJewels.countDownOneTime);

		// new timer
		timer2 = new Timer();
		st = TimeUtils.nanoTime();
		sinceLastKill = new GdxIncrementTimer(st);

		score = MagicJewels.player_score;

		// setBounds(0, 0, 480, 800);
		this.game = gam;
		this.jmg = jmg;

		ja = new JewelArray(game);
		ja.buildGrid();
		jewels = ja.getJewels();
		// new GridState();

		// create grid

		group = new Group();
		group.setName("gridgroup");

		group.setBounds(MagicJewels.grid_start_x - MagicJewels.jewel_width,
				MagicJewels.grid_start_y - MagicJewels.grid_height,
				MagicJewels.grid_width, MagicJewels.grid_height);

		// add initial jewels to grid(stage/group), clear array
		populateGroup();
		
		//MagicJewels.backgr.loop();
		MagicJewels.backgr.loop(0.3f);

		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		
	
		
		
			batch.draw(Asset.uiAssets[0], 0, 0); // n_background1
			batch.draw(Asset.uiAssets[5], 0, 417); // box 1
			batch.draw(Asset.uiAssets[6], 0, 35); // box 1
			
			batch.draw(Asset.uiAssets[2], 0, 624); // other
			batch.draw(Asset.uiAssets[3], 0, 692); // scoreback
			batch.draw(Asset.uiAssets[8], 0, 744); // mj
			batch.draw(Asset.uiAssets[4], 353, 700, 125, 100); //peeler1
			//batch.draw(, 220, 627); // peeler1
			
			
			// TIMER countdown
			countdownTime.countdown();
			MagicJewels.current_minute = countdownTime.currentMinute;
			MagicJewels.current_second = countdownTime.currentSecond;
			MagicJewels.scoreFont.draw(batch, countdownTime.formattedTime, 399, 698);
			
			if (countdownTime.stop) {
				this.remove();
				game.showScore();
				game.cleanup();
				
				
				
			}
			
			
			//end game
			if(game.red_active){
				
				
				if(MagicJewels.finalclusters.size() < 1){
					this.remove();
					game.showScore();
					game.cleanup();
					
				}
				
				boolean endit=true;
				for(int i=0;i<MagicJewels.finalclusters.size();i++){
					ArrayList<Actor> aj = MagicJewels.finalclusters.get(i);
					if(aj.size() >= MagicJewels.group_terminate_number){
						endit=false;
					}
				}
				if(endit){
					this.remove();
					game.showScore();
					game.cleanup();
					
				}
				
				
			}
			
			
	
			// TIMER new timer
			sinceLastKill.startTimer();
	
			// add blinky
			MagicJewels.crazymodecount = true;
			if (MagicJewels.since_last_killcluster > 4) {
				if (MagicJewels.permission_to_click) {
					
					
					game.removeBlinkAll();
					this.addOneBlink(group);
					
					MagicJewels.since_last_killcluster = 0;
					MagicJewels.crazymodecount = false;
					MagicJewels.crazycount = 0;
					MagicJewels.crazy_hits_count=0;
					
	
				}
			}
			// crazy count
			MagicJewels.crazymodecount = true;
			if (MagicJewels.since_last_killcluster > 1) {
				MagicJewels.crazymodecount = false;
				MagicJewels.crazycount = 0;
			}
			
	
			if (MagicJewels.animatescore)
				animateScore();
			MagicJewels.scoreFont.draw(batch, "" + score, 15, 736);
	
			// green light red light
			int green_light_x = 10;
			game.red_active=false;
			for (int i = 0; i < MagicJewels.player_moves; i++) {
				
				int circle_y = 648;
				if (MagicJewels.player_moves <= MagicJewels.player_max_moves) {
					if (i != 0)
						green_light_x += 40;
					batch.draw(Asset.assets[18], green_light_x, circle_y);
				} else {
					batch.draw(Asset.assets[21], 11, circle_y);
					batch.draw(Asset.assets[21], 51, circle_y);
					batch.draw(Asset.assets[21], 91, circle_y);
					game.red_active=true;
				}
	
			}
			
			
			
			
			if (Gdx.input.justTouched()) {
	            Vector3 touchpoint = new Vector3();
	            touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	            MagicJewels.camera.unproject(touchpoint);
	            
	            //System.out.println("X:"+touchpoint.x + " Y:"+touchpoint.y);
	           	
	            if( (touchpoint.x > 390) &&
	            		(touchpoint.y > 725 && touchpoint.y < MagicJewels.screen_top)){
	            	
	            	if(MagicJewels.application_type == 1){
	            		game.actionResolver.showOrLoadInterstital();
	            	}
	            	
	            }
	            
			}
			
			
			
			if(MagicJewels.updateDraw){
				// DOESNT DO JACK SHIT
				jmg = new JewelMapGrid(game,MagicJewels.stage);
				jmg.updateGridState();
				MagicJewels.updateDraw=false;
			}
		
	
		super.draw(batch, parentAlpha);

	}

	public void populateGroup() {
		for (int i = 0; i < jewels.size(); i++) {

			// jewels.get(i).setTouchable(Touchable.enabled);
			// group.addAction(parallel(moveTo(200,0,5),rotateBy(90,5)));
			// EXAMPLE

			Jewel jewel = jewels.get(i);
			float x = jewel.getX();// set in JewelArray
			float y = jewel.getY();

			jewels.get(i).setBounds(x, y, MagicJewels.jewel_width,
					MagicJewels.jewel_height);
			group.addActor(jewel);

			// addActor(jewels.get(i));

		}

		// clear jewel array actors are drawn , ready for click
		addActor(group);
		JewelArray.jewels_start.clear();
	}

	public void animateScore(){
		 
		 if(MagicJewels.player_score_before < MagicJewels.player_score){
			 
				 MagicJewels.player_score_before = MagicJewels.player_score_before + 3;
			
			
			 score = MagicJewels.player_score_before; 
			 } 
    }
	
	
	
	public void addOneBlink(Group g) {

		ArrayList<ArrayList<Actor>> filter = new ArrayList<ArrayList<Actor>>();
		ArrayList<ArrayList<Actor>> one_blink = new ArrayList<ArrayList<Actor>>();

		for (ArrayList<Actor> ja : MagicJewels.finalclusters) {
			if (ja.size() >= MagicJewels.group_terminate_number){
				for (Actor j : ja) {
					Jewel jewl =  (Jewel)j;
					//int spot = jewl.getSpot();
					//Jewel groupJewel = (Jewel) g.findActor("jewel" + spot);
					jewl.setBlinkOn();
				}
			}
		}
		
		/*
		// add blink if exists
		for (ArrayList<Actor> f : filter) {
			if (f.size() >= MagicJewels.group_terminate_number) {

				one_blink.add(f);

			}
		}

		// modify jewel state
		for (ArrayList<Actor> ob : one_blink) {

			for (Actor j : ob) {
				Jewel jewl =  (Jewel)j;
				int spot = jewl.getSpot();
				Jewel groupJewel = (Jewel) g.findActor("jewel" + spot);
				groupJewel.setBlinkOn();
			}
		}

		filter.clear();
		one_blink.clear();
		*/
		
	}


}
