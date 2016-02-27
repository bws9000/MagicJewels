package com.wileynet.magicjewels.levelone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.equations.Bounce;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.wileynet.magicjewels.Asset;
import com.wileynet.magicjewels.GameScreen;
import com.wileynet.magicjewels.Jewel;
import com.wileynet.magicjewels.MagicJewels;
import com.wileynet.magicjewels.TwinkleLarge;
import com.wileynet.magicjewels.TwinkleSmall;
import com.wileynet.magicjewels.TwinkleSmaller;
import com.wileynet.magicjewels.util.JewelAccessor;
import com.wileynet.magicjewels.util.JewelMapGrid;
import com.wileynet.magicjewels.util.StageUtil;
import com.wileynet.magicjewels.util.Utils;

public class GamePlay {

	// CRAZYMODE SET IN terminate1

	final MagicJewels game;

	TwinkleSmaller smaller_t;
	TwinkleSmall small_t;
	TwinkleLarge large_t;
	Group twinkles;

	boolean delete_executed = false;
	float drop_delay = 0.1f;

	private Jewel s1;// swap
	private Jewel s2;// swap
	private int spot1;// swap
	private int spot2;// swap

	int s1row = MagicJewels.getRowFromSpot(spot1);// swap
	int s1col = getColumnFromSpot(spot1);// swap
	int s2row = MagicJewels.getRowFromSpot(spot2);// swap
	int s2col = getColumnFromSpot(spot2);// swap
	float s1x = (float) getXFromSpot(s1col);// swap
	float s1y = (float) getYFromSpot(s1row);// swap
	float s2x = (float) getXFromSpot(s2col);// swap
	float s2y = (float) getYFromSpot(s2row);// swap

	/*
	 * int s1row;//swap int s1col;//swap int s2row;//swap int s2col;//swap float
	 * s1x;//swap float s1y;//swap float s2x;//swap float s2y;//swap
	 */

	private LevelOne levelone;
	private StageUtil stageutil;
	private JewelMapGrid jmg;
	private Timeline cs;
	private Timeline cs2;
	private Timeline cs3;
	private Group group;
	private ArrayList<Integer> nullsunder = new ArrayList<Integer>();
	private HashMap<Integer, ArrayList<Integer>> nu = new HashMap<Integer, ArrayList<Integer>>();

	private ArrayList<Actor> jewels = new ArrayList<Actor>();
	private ArrayList<Integer> to_be_terminated = new ArrayList<Integer>();

	public GamePlay(Stage stage, JewelMapGrid jmg, MagicJewels gam) {
		this.game = gam;
		this.jmg = jmg;

		stageutil = new StageUtil();// returns stage this actor("levelone") is
									// in

		// get current jewels
		group = stageutil.getTableGroup("gridgroup");
		jewels = stageutil.getGroupActors(group);

	}

	public void killCluster(int spot) {
		
		
		
		cs = Timeline.createSequence();
		// cs.ensurePoolCapacity(100);

		cs2 = Timeline.createSequence();
		// cs2.ensurePoolCapacity(100);

		//ArrayList<ArrayList<Actor>> f = MagicJewels.finalclusters;
		// System.out.println("::"+f.size());

		Jewel j = (Jewel) jewels.get(spot);
		String spotName = j.getJewelName();

		for (int i = 0; i < MagicJewels.finalclusters.size(); i++) {
			if (MagicJewels.finalclusters.get(i).contains(j)) {

				ArrayList<Actor> a = MagicJewels.finalclusters.get(i);
				// System.out.println("++++ "+a.size());
				int asize = a.size();
				if (asize >= MagicJewels.group_terminate_number) {
					for (int k = 0; k < a.size(); k++) {
						Jewel j2 = (Jewel) a.get(k);
						int s = j2.getSpot();
						
						to_be_terminated.add(s);
						// terminate2(s);
						delete_executed = true;
					}

				}
			}
		}

		

		
		
		terminate1();
		
		
		
		
		
				if (MagicJewels.crazymode) {
					
					if(inArray(j)){
						
						Group g = stageutil.getTableGroup("gridgroup");
			
						// test
						for (int k = 0; k < MagicJewels.jewel_count; k++) {
							Jewel test = (Jewel) g.findActor("jewel" + k);
							if (test != null) {
			
								if (spotName == test.getJewelName()) {
									to_be_terminated.add(test.getSpot());
								}
							}
			
						}
					
						MagicJewels.crazymode_explode.play();
						MagicJewels.crazy_hits_count++;
						if(MagicJewels.crazy_hits_count > MagicJewels.max_crazy_hits){
							MagicJewels.crazymode = false;
							MagicJewels.crazycount = 0;
							MagicJewels.crazy_hits_count = 0;
						}
						
					}
						
				}
	
				
			
				game.removeBlinkAll();
				jmg = new JewelMapGrid(game,MagicJewels.stage);
				jmg.updateGridState();
				new GamePlay(MagicJewels.stage,jmg,game);
		
		

	}
	
	public boolean inArray(Jewel j){
		
		for(ArrayList<Actor> ja: MagicJewels.finalclusters){
			
			if(ja.contains(j)){
				if(ja.size() >= MagicJewels.group_terminate_number)
				return true;
			}
			
		}
		return false;
		
	}

	public void terminate1() {

		int offset = 25;

		Jewel j = null;

		Group g = stageutil.getTableGroup("gridgroup");

		twinkles = new Group();
		twinkles.setName("twinklegroup");

		for (int i = 0; i < to_be_terminated.size(); i++) {

			j = (Jewel) g.findActor("jewel" + to_be_terminated.get(i));
			j.setVisible(false);
			// terminate2(to_be_terminated.get(i));
			// delete_executed = true;

			// twinkle

			if (delete_executed) {

				smaller_t = new TwinkleSmaller(levelone, game);
				smaller_t.setHeight(8);
				smaller_t.setWidth(9);
				twinkles.addActor(smaller_t);

				smaller_t.x_start = (int) j.getX() - offset;
				smaller_t.x_to = (smaller_t.x_start + MagicJewels.jewel_width)
						- offset;
				smaller_t.y_start = (int) j.getY();
				smaller_t.y_to = (smaller_t.y_start + MagicJewels.jewel_height)
						+ offset;

				small_t = new TwinkleSmall(levelone, game);
				small_t.setHeight(18);
				small_t.setWidth(19);
				twinkles.addActor(small_t);

				small_t.x_start = (int) j.getX() - offset;
				small_t.x_to = (small_t.x_start + MagicJewels.jewel_width)
						- offset;
				small_t.y_start = (int) j.getY();
				small_t.y_to = (small_t.y_start + MagicJewels.jewel_height)
						+ offset;

				large_t = new TwinkleLarge(levelone, game);
				large_t.setHeight(25);
				large_t.setWidth(29);
				twinkles.addActor(large_t);

				large_t.x_start = (int) j.getX() - offset;
				large_t.x_to = (large_t.x_start + MagicJewels.jewel_width)
						- offset;
				large_t.y_start = (int) j.getY();
				large_t.y_to = (large_t.y_start + MagicJewels.jewel_height)
						+ offset;

				MagicJewels.stage.addActor(twinkles);

			}

		}

		if (delete_executed) {

			if (MagicJewels.crazymodecount) {
				MagicJewels.crazycount++;
			} else {
				MagicJewels.crazycount = 0;
				MagicJewels.crazymode = false;
			}

			System.out.println("crazy count: " + MagicJewels.crazycount);

			if (MagicJewels.crazycount < 5) {

				//MagicJewels.crazymode = false;

				switch (MagicJewels.crazycount) {
				case 0:
					MagicJewels.chimeup1.play();
					break;
				case 1:
					MagicJewels.chimeup1.play();
					break;
				case 2:
					MagicJewels.chimeup2.play();
					break;
				case 3:
					MagicJewels.chimeup3.play();
					break;
				case 4:
					MagicJewels.chimeup4.play();
					break;
				case 5:
					MagicJewels.chimeup5.play();
					break;

				}
			} else if (MagicJewels.crazycount > 5) {
				MagicJewels.crazymode = true;
			}

			// MagicJewels.player_moves = 0;
			//System.out.println(": " + MagicJewels.crazycount);
		}

		float delay = drop_delay; // seconds
		Timer.schedule(new Task() {
			@Override
			public void run() {
				for (int i = 0; i < to_be_terminated.size(); i++) {
					terminate2(to_be_terminated.get(i));
					delete_executed = true;
				}
				moveMap();

				cs.setCallback(jmovedAll);
				cs.setCallbackTriggers(
						TweenCallback.START | TweenCallback.COMPLETE).start(
						GameScreen.tweenManager);

				removeTwinkle();

			}
		}, delay);

	}

	public void removeTwinkle() {

		MagicJewels.stage.getRoot().removeActor(twinkles);
		if (MagicJewels.player_moves <= MagicJewels.player_max_moves)
			MagicJewels.animatescore = true;

	}

	public void swapJewels(int spot1, int spot2) {
		
		
			MagicJewels.swipe.play();
	
			this.spot1 = spot1;
			this.spot2 = spot2;
	
			cs3 = Timeline.createSequence();
	
			Group g = stageutil.getTableGroup("gridgroup");
	
			if (g != null) {
				s1 = (Jewel) g.findActor("jewel" + spot1);
				s2 = (Jewel) g.findActor("jewel" + spot2);
	
				// get true positions
				// s1row = MagicJewels.getRowFromSpot(spot1);
				s1row = s1.getRow();
				// s1col = getColumnFromSpot(spot1);
				s1col = s1.getColumn();
				// s2row = MagicJewels.getRowFromSpot(spot2);
				s2row = s2.getRow();
				// s2col = getColumnFromSpot(spot2);
				s2col = s2.getColumn();
	
				s1x = (float) getXFromSpot(s1col);
				// s1x = (float) s1.getX();
				s1y = (float) getYFromSpot(s1row);
				// s1y = (float) s1.getY();
				s2x = (float) getXFromSpot(s2col);
				// s2x = (float) s2.getX();
				s2y = (float) getYFromSpot(s2row);
				// s2y = (float) s2.getY();
	
				// spot1 spot clicked and moved
				// spot2 swap
	
				cs3.beginParallel()
						.push(Tween.to(s1, JewelAccessor.POS_XY, 0.6f)
						// .setCallback(movedone)
								.ease(Bounce.OUT)
								// .setCallbackTriggers(TweenCallback.START |
								// TweenCallback.COMPLETE)
								.target(s2x, s2y))
	
						.push(Tween
								.to(s2, JewelAccessor.POS_XY, 0.6f)
								.setCallback(movedone)
								.ease(Bounce.OUT)
								.setCallbackTriggers(
										TweenCallback.START
												| TweenCallback.COMPLETE)
								.target(s1x, s1y))
	
						.setCallback(movedboth)
						.setCallbackTriggers(
								TweenCallback.START | TweenCallback.COMPLETE)
						.start(GameScreen.tweenManager);
	
			}
	
			
			
		
			
		

		// killcluster interval timer reset
		// MagicJewels.since_last_killcluster=0;
		
		
	}

	/*
	 * public void swapJewels(int spot1, int spot2){
	 * 
	 * MagicJewels.swipe.play();
	 * 
	 * this.spot1 = spot1; this.spot2 = spot2;
	 * 
	 * cs3 = Timeline.createSequence();
	 * 
	 * Group g = stageutil.getTableGroup("gridgroup");
	 * 
	 * if(g != null){ s1 = (Jewel)g.findActor("jewel"+spot1); s2 =
	 * (Jewel)g.findActor("jewel"+spot2);
	 * 
	 * //get true positions s1row = s1.getRow(); s1col = s1.getColumn(); s2row =
	 * s2.getRow(); s2col = s2.getColumn();
	 * 
	 * s1x = (float)getXFromSpot(s1col); s1y = (float)getYFromSpot(s1row); s2x =
	 * (float)getXFromSpot(s2col); s2y = (float)getYFromSpot(s2row);
	 * 
	 * //spot1 spot clicked and moved //spot2 swap
	 * 
	 * cs3.beginParallel() .push(Tween.to(s1, JewelAccessor.POS_XY, 0.6f)
	 * .setCallback(movedone) .ease(Bounce.OUT)
	 * .setCallbackTriggers(TweenCallback.START | TweenCallback.COMPLETE)
	 * .target(s2x,s2y))
	 * 
	 * .push(Tween.to(s2, JewelAccessor.POS_XY, 0.6f) .setCallback(movedone)
	 * .ease(Bounce.OUT) .setCallbackTriggers(TweenCallback.START |
	 * TweenCallback.COMPLETE) .target(s1x,s1y))
	 * 
	 * 
	 * .setCallback(movedboth) .setCallbackTriggers(TweenCallback.START |
	 * TweenCallback.COMPLETE) .start(GameScreen.tweenManager);
	 * 
	 * }
	 * 
	 * 
	 * System.gc();
	 * 
	 * 
	 * }
	 */

	private void addJewels() {

		Group g = stageutil.getTableGroup("gridgroup");
		ArrayList<Integer> nulls = new ArrayList<Integer>();
		HashMap<Integer, Integer> jewelsPerColumn = new HashMap<Integer, Integer>();

		for (int k = 0; k < MagicJewels.jewel_count; k++) {
			Jewel test = (Jewel) g.findActor("jewel" + k);
			if (test == null) {

				/*
				 * System.out.println("nulls left: jewel" + k + " column:" +
				 * getColumnFromSpot(k) + " row:" + getRowFromSpot(k) + " x:" +
				 * getXFromSpot(getColumnFromSpot(k)) + " y:" +
				 * getYFromSpot(getRowFromSpot(k)));
				 */

				// drop new jewel
				// nulls.add(k);
				int spot = k;
				int row = MagicJewels.getRowFromSpot(k);
				int col = getColumnFromSpot(k);
				int x = getXFromSpot(col);
				int y = getYFromSpot(row);

				addnull(spot, row, col, x, y);

			}

		}

		/*
		 * for(int i=0;i<nulls.size();i++){
		 * 
		 * int spot = nulls.get(i); System.out.println(spot + " column: " +
		 * getColumnFromSpot(spot)); }
		 */

	}

	private Integer columnNullCheck(int spot) {

		Group g = stageutil.getTableGroup("gridgroup");
		ArrayList<Integer> nulls = new ArrayList<Integer>();

		for (int i = 0; i < MagicJewels.grid_rows; i++) {

			int newspot = spot + (i * MagicJewels.grid_columns);

			if (newspot < MagicJewels.jewel_count) {
				Jewel jewel = (Jewel) g.findActor("jewel" + newspot);
				if (jewel == null) {
					nulls.add(i);
				}
			}
		}
		int out = nulls.size();
		return out;

	}

	public HashMap<Integer, ArrayList<Integer>> findEmpty2() {

		Group g = stageutil.getTableGroup("gridgroup");

		for (int i = 0; i < MagicJewels.jewel_count; i++) {
			Jewel j = (Jewel) g.findActor("jewel" + i);
			if (j != null) {
				getNullsUnder(true, j.getSpot());
				if (nullsunder.size() > 0) {
					ArrayList<Integer> newnull = new ArrayList<Integer>();
					for (int k = 0; k < nullsunder.size(); k++) {
						newnull.add(nullsunder.get(k));
					}
					nu.put(j.getSpot(), newnull);
					nullsunder.clear();
				}
			}
		}

		return nu;
	}

	private void moveMap() {

		Group g = stageutil.getTableGroup("gridgroup");

		findEmpty2();

		/*
		 * for (Integer key : nu.keySet()) { ArrayList<Integer> nulls =
		 * nu.get(key); System.out.println("jewel" + key + " :"); for(int
		 * k=0;k<nulls.size();k++){ System.out.println("-"+nulls.get(k)); } }
		 */

		boolean empties_found = false;

		for (Integer key : nu.keySet()) {

			ArrayList<Integer> a = nu.get(key);
			Jewel jewel = (Jewel) g.findActor("jewel" + key);

			float jx = jewel.getX();

			// float jy = jewel.getY(); !!!!!! DROVE ME NUTS
			float jy = MagicJewels.grid_start_y
					- (jewel.getRow() * MagicJewels.jewel_height);

			// if(a.size() > 0){
			// nulls under were found

			empties_found = true;
			int asize = a.size();
			float diff = (float) MagicJewels.jewel_height * asize;

			float xx = jx;
			float yy = jy - diff;

			/*
			 * jewel.addAction(Actions.moveTo(xx, yy,
			 * 0.1f,Interpolation.bounceOut));
			 */
			cs.beginParallel().push(
					Tween.to(jewel, JewelAccessor.POS_XY, 0.3f)
							// .delay(0.5f)
							.ease(Bounce.OUT)
							.setCallback(jmoved)
							.setCallbackTriggers(
									TweenCallback.START
											| TweenCallback.COMPLETE)
							.target(xx, yy));

			// reset jewel info

			jewel.setBounds(jewel.getX(), jewel.getY(),
					MagicJewels.jewel_width, MagicJewels.jewel_height);
			jewel.setSpot(jewel.getSpot() + MagicJewels.grid_columns * asize);
			jewel.setName("jewel" + jewel.getSpot());
			jewel.setRow(jewel.getRow() + asize);

			// jewel.setY();
			// jewel.setPosition(xx, yy);

			// }

			// check if new jewel has top null if so add it

		}

		if (empties_found) {
			nu.clear();
			moveMap();
		}

	}

	// check top add, sorry getting tired of camelcase
	private void addnull(int spot, int row, int column, int x, int y) {

		Group g = stageutil.getTableGroup("gridgroup");

		Utils u = new Utils();

		int rr = u.randInt(0, 6);// random AtlasRegion;

		Jewel jewel = new Jewel(levelone, x, MagicJewels.grid_start_y, spot,
				row, column, rr, rr, game);

		cs2.beginParallel().push(
				Tween.to(jewel, JewelAccessor.POS_XY, 0.1f)
						// .delay(0.5f)
						.ease(Bounce.OUT)
						.setCallback(addtopdelay)
						.setCallbackTriggers(
								TweenCallback.START | TweenCallback.COMPLETE)
						.target(x, y));

		// System.out.println(">new jewel name:"+jewel.getName());

		Jewel after = (Jewel) g.findActor("jewel" + (spot - 1));

		g.addActorAfter(after, jewel);
		// g.addActorAt(spot, jewel);

	}

	private final TweenCallback jmoved = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source) {

			switch (type) {
			case START:
				//
				break;
			case COMPLETE:
				MagicJewels.player_moves=0;
				break;
			}
		}

	};

	private final TweenCallback addtopdelay = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source) {

			switch (type) {
			case START:
				//
				break;
			case COMPLETE:
				Group g = stageutil.getTableGroup("gridgroup");
				break;
			}
		}

	};

	private final TweenCallback jmovedAll = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source) {

			switch (type) {
			case START:
				//
				break;
			case COMPLETE:

				addJewels();

				Group g = stageutil.getTableGroup("gridgroup");
				jewels = stageutil.getGroupActors(group);

				// jmg.updateGridState();
				// System.out.println("------------");
				for (int i = 0; i < jewels.size(); i++) {
					Jewel j = (Jewel) jewels.get(i);
					if (j.getSpot() != i) {

						// System.out.println("remove actor: " + j.getName());
						// System.out.println("move to: "+j.getSpot());

						// !IMPORTANT!
						g.removeActor(j);
						g.addActorAt(j.getSpot(), j);
					}

				}

				jewels = stageutil.getGroupActors(group);

				// System.out.println("------------");
				for (int i = 0; i < jewels.size(); i++) {
					Jewel j = (Jewel) jewels.get(i);
					// System.out.println("**"+j.getName() + " spot:" +
					// j.getSpot());
				}

				cs2.setCallback(topjmoved);
				cs2.setCallbackTriggers(
						TweenCallback.START | TweenCallback.COMPLETE).start(
						GameScreen.tweenManager);

				cs.free();
				
				game.removeBlinkAll();
				jmg = new JewelMapGrid(game,MagicJewels.stage);
				jmg.updateGridState();
				new GamePlay(MagicJewels.stage,jmg,game);
				
				
				break;
			}
		}

	};

	private final TweenCallback topjmoved = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source) {

			switch (type) {
			case START:
				if (delete_executed) {
					MagicJewels.bump.play();
				}
				break;
			case COMPLETE:

				// System.out.println("top moved all");

				MagicJewels.permission_to_click = true;
				
				if(!MagicJewels.end_the_game){
					jmg = new JewelMapGrid(game,MagicJewels.stage);
					jmg.updateGridState();
					new GamePlay(MagicJewels.stage,jmg,game);
	
					//MagicJewels.updateDraw=true;
					
					game.removeBlinkAll();
				}

				cs2.free();
				break;
			}
		}

	};

	private final TweenCallback movedone = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source) {

			switch (type) {
			case START:
				//
				break;
			case COMPLETE:
				//
				break;
			}
		}

	};

	private final TweenCallback movedboth = new TweenCallback() {
		@Override
		public void onEvent(int type, BaseTween<?> source) {

			switch (type) {
			case START:
				// MagicJewels.swipe.play();
				break;
			case COMPLETE:

				s1.setBounds(s2x, s2y, MagicJewels.jewel_width,
						MagicJewels.jewel_height);
				s2.setBounds(s1x, s1y, MagicJewels.jewel_width,
						MagicJewels.jewel_height);

				s1.setSpot(spot2);
				s2.setSpot(spot1);
				s1.setName("jewel" + spot2);
				s2.setName("jewel" + spot1);

				s1.setRow(s2row);
				s2.setRow(s1row);

				s1.setColumn(s2col);
				s2.setColumn(s1col);

				Group g = stageutil.getTableGroup("gridgroup");
				g.removeActor(s1);
				s1.remove();
				g.addActorAt(spot2, s1);
				g.removeActor(s2);
				s2.remove();
				g.addActorAt(spot1, s2);

				/*
				 * s1.setBounds(s2x, s2y, MagicJewels.jewel_width,
				 * MagicJewels.jewel_height); s2.setBounds(s1x, s1y,
				 * MagicJewels.jewel_width, MagicJewels.jewel_height);
				 */

				// jmg.updateGridState();

				/*
				 * ArrayList<ArrayList<Actor>> fc = jmg.finalclusters;
				 * if(!fc.isEmpty() || fc.size() > 0){
				 * 
				 * System.out.println("\nfinalclusters" + fc.size() + "\n");
				 * for(int i=0;i<fc.size();i++){ if(fc.get(i) != null){
				 * System.out.println("finalclusters: " + i); ArrayList<Actor> f
				 * = fc.get(i); for(int j=0;j<f.size();j++){ Jewel jewel =
				 * (Jewel)f.get(j); System.out.println(jewel.getName()); }
				 * System.out.println(""); } } }
				 */

				/*
				 * jewels = stageutil.getGroupActors(g); for(int
				 * i=0;i<jewels.size();i++){ Jewel j = (Jewel)jewels.get(i);
				 * System.out.println("**"+j.getName() + " spot:" + j.getSpot()
				 * + "-row: " + j.getRow() + "-col: " + j.getColumn()); }
				 */

				/*
				 * jewels = stageutil.getGroupActors(group); for(int
				 * i=0;i<jewels.size();i++){ Jewel j = (Jewel)jewels.get(i);
				 * if(j.getSpot() != i){ g.removeActor(j);
				 * g.addActorAt(j.getSpot(), j); } }
				 */

				MagicJewels.permission_to_click = true;
				
				if(!MagicJewels.end_the_game){
					jmg = new JewelMapGrid(game,MagicJewels.stage);
					jmg.updateGridState();
					new GamePlay(MagicJewels.stage,jmg,game);
					game.removeBlinkAll();
				}

				cs3.free();
				break;
			}
		}

	};

	// recursive finds nulls under each jewel within rows

	private void getNullsUnder(boolean nullfound, int spot) {

		Group g = stageutil.getTableGroup("gridgroup");

		int under = spot + MagicJewels.grid_columns;
		if (under < MagicJewels.jewel_count) {
			Jewel testjunder = (Jewel) g.findActor("jewel" + under);
			if (testjunder == null) {
				nullsunder.add(under);
				getNullsUnder(true, under);
			}

		}

	}

	public void terminate2(int spot) {
		MagicJewels.player_moves=0;
		// remove clicked
		Group g = stageutil.getTableGroup("gridgroup");
		Jewel j = (Jewel) g.findActor("jewel" + spot);

		if (j != null) {
			// System.out.println("null: jewel" + spot);
			MagicJewels.player_score += j.getJewelScore();

			// delete
			g.removeActor(j);
			j.remove();
		}

	}

	private HashMap<Integer, Integer> findHolesInRows(ArrayList<Actor> a) {

		HashMap<Integer, Integer> empties = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> columnemptycount = new HashMap<Integer, Integer>();

		for (int i = 0; i < a.size(); i++) {
			Jewel j = (Jewel) a.get(i);
			int col = j.getColumn();
			int spot = j.getSpot();
			empties.put(spot, col);
		}

		for (int i = 0; i < MagicJewels.grid_rows; i++) {
			int count = Collections.frequency(empties.values(), i);
			columnemptycount.put(i, count);
		}

		return columnemptycount;

	}

	private int getMaxSpot(ArrayList<Actor> a) {

		HashMap<String, Integer> cluster = new HashMap<String, Integer>();

		for (Actor aa : a) {
			Jewel a1 = (Jewel) aa;
			cluster.put(a1.getName(), a1.getSpot());
		}

		int ms = Collections.max(cluster.values());
		// System.out.println("Max Spot: "+ ms);

		return ms;

	}

	private int getMinSpot(ArrayList<Actor> a) {

		HashMap<String, Integer> cluster = new HashMap<String, Integer>();

		for (Actor aa : a) {
			Jewel a1 = (Jewel) aa;
			cluster.put(a1.getName(), a1.getSpot());
		}

		int ms = Collections.min(cluster.values());
		// System.out.println("Max Spot: "+ ms);

		return ms;

	}

	private Integer getColumnFromSpot(int spot) {

		int column = 0;
		if (spot % MagicJewels.grid_columns == 0) {
			//
		} else {

			column = spot % MagicJewels.grid_columns;

		}
		return column;

	}

	// NEEDS TO BE RENAMED
	private Integer getXFromSpot(int col) {

		float start = MagicJewels.grid_start_x;
		int x = (int) start + (col * MagicJewels.jewel_width);

		return x;

	}

	// NEEDS TO BE RENAMED
	private Integer getYFromSpot(int row) {

		float start = MagicJewels.grid_start_y;
		int y = (int) start - (row * MagicJewels.jewel_height);

		return y;

	}

}
