package com.wileynet.magicjewels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.wileynet.magicjewels.levelone.GamePlay;
import com.wileynet.magicjewels.levelone.LevelOne;
import com.wileynet.magicjewels.score.Score;
import com.wileynet.magicjewels.util.JewelMapGrid;

public class Jewel extends Actor {

	final MagicJewels game;

	// private LevelOne levelone;
	private Rectangle bounds = new Rectangle();
	private AtlasRegion atlasregion;
	private int ar_index;
	private int active_ar_index;
	private Score score = new Score();

	// animation
	private float elapsedTime = 0;
	private boolean animate_blink;
	private TextureRegion anim_region;

	Stage stage;
	LevelOne levelone;
	JewelMapGrid jmg;

	private GamePlay gp;

	private int spot;
	private int row;
	private int column;

	private boolean dragging;

	public boolean terminate;
	public String jewelName;
	public Integer jewelScore;

	/*
	 * public Jewel(LevelOne levelone,int x,int y, final int spot, final int
	 * row, final int column, AtlasRegion atlasregion) {
	 */

	public Jewel(LevelOne levelone, int x, int y, final int spot,
			final int row, final int column, int ar, int rr, MagicJewels gam) {

		this.game = gam;

		this.setAtlasRegion(ar);
		this.ar_index = ar;
		this.active_ar_index = ar + 7;

		this.terminate = false;
		this.levelone = levelone;
		this.jewelName = "JewelName";

		setWidth(MagicJewels.jewel_width);
		setHeight(MagicJewels.jewel_height);
		setPosition(x, y);

		// other
		this.row = row;
		this.column = column;

		setSpot(spot);
		// setNeighbors();//spot neighbor

		setRow(row);
		setColumn(column);
		setName("jewel" + spot);
		setJewelName(rr);
		setJewelScore(rr);

		this.addListener(new InputListener() {

			int spot;

			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {

				/*
				 * System.out.println("spot "+getSpot());
				 * System.out.println("row "+getRow());
				 * System.out.println("column " + getColumn());
				 * System.out.println(getName());
				 * System.out.println(getJewelName());
				 * System.out.println("-------------");
				 */

				

				//stage = event.getStage();
				//game.removeBlinkAll();
				
				return true;

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// Gdx.app.log("Example", "touch done at (" + x + ", " + y +
				// ")");
				// Gdx.app.log("Touchup","spot: " + getSpot());
				spot = getSpot();
				gamePlay(spot);

			}

		});

		this.addListener((new DragListener() {

			private int spot = 0;
			private int spotrow = 0;
			private int moveto = -1;

			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {

				dragging = true; // keep this
				spot = getSpot();

				spotrow = MagicJewels.getRowFromSpot(spot);

				if (x > MagicJewels.jewel_width) {

					int mrow = MagicJewels.getRowFromSpot(spot + 1);
					if (mrow == spotrow
							&& spot != (MagicJewels.jewel_count - 1)) {
						moveto = spot + 1;
						// System.out.println("MOVE RIGHT FROM " + spot + " to "
						// + moveto);
						dragging = false;
						stage = event.getStage();
						gamePlaySwap(spot, moveto);
					}

				}

				if (x < 0) {

					int mrow = MagicJewels.getRowFromSpot(spot - 1);
					if (mrow == spotrow && spot != 0) {
						moveto = spot - 1;
						// System.out.println("MOVE LEFT " + spot);
						dragging = false;
						gamePlaySwap(spot, moveto);
					}
				}

				if (y > MagicJewels.jewel_height) {

					if (spot > (MagicJewels.grid_columns - 1)) {
						moveto = spot - MagicJewels.grid_columns;
						// System.out.println("MOVE UP " + spot);
						dragging = false;
						gamePlaySwap(spot, moveto);
					}

				}

				if (y < 0) {

					if (spot < (MagicJewels.jewel_count - MagicJewels.grid_columns)) {
						moveto = spot + MagicJewels.grid_columns;
						// System.out.println("MOVE DOWN " + spot);
						dragging = false;
						gamePlaySwap(spot, moveto);
					}
				}

				if (moveto != -1) {

				}

			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {

				if (dragging)
					dragStop(event, x, y, pointer);
				cancel();
			}

			public boolean isDragging() {
				return dragging;
			}

		}));

	}

	public final void gamePlay(int spot) {
		
		if(!game.end_the_game){
			if (MagicJewels.permission_to_click) {
				MagicJewels.permission_to_click = false;
	
				jmg = new JewelMapGrid(game,stage);
				jmg.updateGridState();
				gp = new GamePlay(stage, jmg, game);
	
				gp.killCluster(spot);
				
				//SINCE LAST KILL CLUSTER
				MagicJewels.since_last_killcluster = 0;
				
			}
		}

	}

	public final void gamePlaySwap(int spot1, int spot2) {
		
		if(!game.end_the_game){
			
			
			
			if(MagicJewels.player_moves <= MagicJewels.player_max_moves){
			if (!dragging) {
	
				if (MagicJewels.permission_to_click) {
	
					MagicJewels.permission_to_click = false;
	
					jmg = new JewelMapGrid(game,stage);
					gp = new GamePlay(stage, jmg, game);
					
					
						gp.swapJewels(spot1, spot2);
					
	
					MagicJewels.player_moves++;
					// System.out.println("player moves: " +
					// MagicJewels.player_moves);
					/*
					 * System.out.println("============");
					 * System.out.println("from: "+spot1);
					 * System.out.println("to  : "+spot2);
					 * System.out.println("============");
					 */
				}
				dragging = true;
				
			}
			
			//SINCE LAST KILL CLUSTER
			MagicJewels.since_last_killcluster = 0;
			
			}
		}

	}

	public void draw(Batch batch, float parentAlpha) {
		// batch.draw(atlasregion,
		// getX(), getY(), getWidth()/2,
		// getHeight()/2, getWidth(),
		// getHeight(), 1, 1, getRotation());

		if (!animate_blink) {
			batch.draw(atlasregion, getX(), getY(), getWidth() / 2,
					getHeight() / 2, getWidth(), getHeight(), 1, 1,
					getRotation());
		} else {

			elapsedTime += Gdx.graphics.getDeltaTime();
			if (this.jewelName.equals("_Red")) {
				anim_region = MagicJewels.blink_animation__Red.getKeyFrame(
						elapsedTime, true);
				batch.draw(anim_region, getX(), getY(), getWidth() / 2,
						getHeight() / 2, getWidth(), getHeight(), 1, 1,
						getRotation());
			}
			if (this.jewelName.equals("Yell")) {
				anim_region = MagicJewels.blink_animation_Yell.getKeyFrame(elapsedTime,
						true);
				batch.draw(anim_region, getX(), getY(), getWidth() / 2,
						getHeight() / 2, getWidth(), getHeight(), 1, 1,
						getRotation());
			}
			if (this.jewelName.equals("Gree")) {
				anim_region = MagicJewels.blink_animation_Gree.getKeyFrame(elapsedTime,
						true);
				batch.draw(anim_region, getX(), getY(), getWidth() / 2,
						getHeight() / 2, getWidth(), getHeight(), 1, 1,
						getRotation());
			}
			if (this.jewelName.equals("Purp")) {
				anim_region = MagicJewels.blink_animation_Purp.getKeyFrame(elapsedTime,
						true);
				batch.draw(anim_region, getX(), getY(), getWidth() / 2,
						getHeight() / 2, getWidth(), getHeight(), 1, 1,
						getRotation());
			}
			if (this.jewelName.equals("Oran")) {
				anim_region = MagicJewels.blink_animation_Oran.getKeyFrame(elapsedTime,
						true);
				batch.draw(anim_region, getX(), getY(), getWidth() / 2,
						getHeight() / 2, getWidth(), getHeight(), 1, 1,
						getRotation());
			}
			if (this.jewelName.equals("lBlu")) {
				anim_region = MagicJewels.blink_animation_lBlu.getKeyFrame(elapsedTime,
						true);
				batch.draw(anim_region, getX(), getY(), getWidth() / 2,
						getHeight() / 2, getWidth(), getHeight(), 1, 1,
						getRotation());
			}
			if (this.jewelName.equals("dBlu")) {
				anim_region = MagicJewels.blink_animation_dBlu.getKeyFrame(elapsedTime,
						true);
				batch.draw(anim_region, getX(), getY(), getWidth() / 2,
						getHeight() / 2, getWidth(), getHeight(), 1, 1,
						getRotation());
			}

		}

	}

	// atlas region
	public void setAtlasRegion(int r) {
		this.atlasregion = Asset.assets[r];
		this.ar_index = r;
	}

	public int getAtlasRegion() {
		return this.ar_index;
	}

	public void setActiveAtlasRegion(int ar) {
		this.atlasregion = Asset.assets[ar];
		this.active_ar_index = ar;
	}

	public int getActiveAtlasRegion() {
		return this.active_ar_index;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		updateBounds();
	}

	private void updateBounds() {
		bounds.set(getX(), getY(), getWidth(), getHeight());
	}

	public Rectangle getBounds() {
		return bounds;
	}

	// left right up down spot
	public int getRightSpot() {
		int out = this.spot + 1;
		if (out < MagicJewels.jewel_count - 1) {
			return out;
		} else {
			return -1;
		}
	}

	public int getLeftSpot() {
		int out = this.spot - 1;
		if (out < 0) {
			return -1;
		} else {
			return out;
		}
	}

	public int getTopSpot() {
		int out = this.spot - MagicJewels.grid_columns;
		if (out < 0) {
			return -1;
		} else {
			return out;
		}

	}

	public int getBottomSpot() {
		int out = this.spot + MagicJewels.grid_columns;
		if (out > MagicJewels.jewel_count - 1) {
			return -1;
		} else {
			return out;
		}

	}

	// other
	public void setSpot(int spot) {
		this.spot = spot;
	}

	public int getSpot() {
		return this.spot;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return this.row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColumn() {
		return this.column;
	}

	public void setJewelName(int rr) {
		switch (rr) {
		case 0:
			this.jewelName = "Purp";
			break;
		case 1:
			this.jewelName = "Yell";
			break;
		case 2:
			this.jewelName = "lBlu";
			break;
		case 3:
			this.jewelName = "Gree";
			break;
		case 4:
			this.jewelName = "_Red";
			break;
		case 5:
			this.jewelName = "Oran";
			break;
		case 6:
			this.jewelName = "dBlu";
			break;
		}
	}

	public void setJewelScore(int rr) {

		switch (rr) {
		case 0:
			this.jewelScore = score.getScore(0);
			break;
		case 1:
			this.jewelScore = score.getScore(1);
			break;
		case 2:
			this.jewelScore = score.getScore(2);
			break;
		case 3:
			this.jewelScore = score.getScore(3);
			break;
		case 4:
			this.jewelScore = score.getScore(4);
			break;
		case 5:
			this.jewelScore = score.getScore(5);
			break;
		case 6:
			this.jewelScore = score.getScore(6);
			break;
		}

	}

	public String getJewelName() {
		return this.jewelName;
	}

	public Integer getJewelScore() {
		return this.jewelScore;
	}

	public void setBlinkOn() {
		this.animate_blink = true;
	}

	public void setBlinkOff() {
		this.animate_blink = false;
	}

	public void dispose() {
		//
	}

}
