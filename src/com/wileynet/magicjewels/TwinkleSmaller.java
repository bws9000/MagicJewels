package com.wileynet.magicjewels;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wileynet.magicjewels.levelone.LevelOne;
import com.wileynet.magicjewels.util.Utils;

public class TwinkleSmaller extends Actor{
	
	final MagicJewels game;
	
	private Rectangle bounds = new Rectangle();
	private AtlasRegion atlasregion;
	
	public int x_start = 0;
	public int x_to = 0;
	
	public int y_start = 0;
	public int y_to = 0;
	
	Stage stage;
	LevelOne levelone;
		
	public TwinkleSmaller(LevelOne levelone,MagicJewels gam) {
		
		this.game = gam;
		this.setName("twinklesmaller");
		atlasregion = Asset.assets[15];
	}
	
	public void draw(Batch batch,float parentAlpha) {
		batch.draw(atlasregion, getX(), getY(), 
				getWidth()/2, getHeight()/2, 
				getWidth(), getHeight(), 1, 1, getRotation());
	}
			
	@Override
	public void act(float delta){
		super.act(delta);
		
		
		
		Utils utils = new Utils();
		
		int tx4 = utils.randInt(x_start, x_to);
		int ty4 = utils.randInt(y_start, y_to);
		
		int tx5 = utils.randInt(x_start, x_to);
		int ty5 = utils.randInt(y_start, y_to);
		
		int tx6 = utils.randInt(x_start, x_to);
		int ty6 = utils.randInt(y_start, y_to);
		
		this.setPosition(tx4, ty4);
		this.setPosition(tx5, ty5);
		this.setPosition(tx6, ty6);
		
		updateBounds();
	}
		
	private void updateBounds() {
		bounds.set(getX(), getY(), getWidth(), getHeight());
	}
		
	public Rectangle getBounds() {
		return bounds;
	}
		
	public void dispose(){
		this.remove();
	}

}
