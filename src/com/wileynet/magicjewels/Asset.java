package com.wileynet.magicjewels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Asset {
	
	public static AtlasRegion[] assets;
	
	public static TextureAtlas jewel_atlas;
	public static TextureAtlas ui_atlas;
	
    public static AtlasRegion one,two,three,four,five,six,seven;
    public static AtlasRegion one_ripe,two_ripe,three_ripe,four_ripe,five_ripe,six_ripe,seven_ripe;
    
    public static AtlasRegion background1;
    
    public static AtlasRegion sparkle_small,sparkle_smaller,sparkle_large;
    public static AtlasRegion green_light_one,green_light_two,green_light_three,
    						  red_light_one,red_light_two,red_light_three;
    
    
    //new ui atlas
    public static AtlasRegion[] uiAssets;
    
    public static AtlasRegion n_background1;
    public static AtlasRegion n_mj;
    public static AtlasRegion n_other;
    public static AtlasRegion n_scoreback;
    public static AtlasRegion peeler1;
    public static AtlasRegion n_box1;
    public static AtlasRegion n_box2;
    public static AtlasRegion peeler3;
    public static AtlasRegion mjheader2;
    public static AtlasRegion startgame;
    public static AtlasRegion playagain;
    
    
    
    
    //red jewel animation
    public static TextureAtlas redjewelanim;
    public static TextureAtlas dbluejewelanim;
    public static TextureAtlas lbluejewelanim;
    public static TextureAtlas yellowjewelanim;
    public static TextureAtlas oranjewelanim;
    public static TextureAtlas purpjewelanim;
    public static TextureAtlas greenjewelanim;
    
    
	public static void load(){
		
		
		
		jewel_atlas = new TextureAtlas(Gdx.files.internal("data/jewels_packed/jewels.atlas"));
		ui_atlas = new TextureAtlas(Gdx.files.internal("data/ui_packed/ui.atlas"));
		
		one = jewel_atlas.findRegion("purple");
		two = jewel_atlas.findRegion("star");
		three = jewel_atlas.findRegion("light_blue");
		four = jewel_atlas.findRegion("green");
		five = jewel_atlas.findRegion("red");
		six = jewel_atlas.findRegion("orange");
		seven = jewel_atlas.findRegion("dark_blue");
		
		one_ripe = jewel_atlas.findRegion("purple_active");
		two_ripe = jewel_atlas.findRegion("star_active");
		three_ripe = jewel_atlas.findRegion("light_blue_active");
		four_ripe = jewel_atlas.findRegion("green_active");
		five_ripe = jewel_atlas.findRegion("red_active");
		six_ripe = jewel_atlas.findRegion("orange_active");
		seven_ripe = jewel_atlas.findRegion("dark_blue_active");
		
		background1 = jewel_atlas.findRegion("background1");
		
		sparkle_small = jewel_atlas.findRegion("sparkle_small");
		sparkle_smaller = jewel_atlas.findRegion("sparkle_smaller");
		sparkle_large = jewel_atlas.findRegion("sparkle_large");
		
		green_light_one = jewel_atlas.findRegion("green_light");
		green_light_two = jewel_atlas.findRegion("green_light");
		green_light_three = jewel_atlas.findRegion("green_light");
		
		red_light_one = jewel_atlas.findRegion("red_light");
		red_light_two = jewel_atlas.findRegion("red_light");
		red_light_three = jewel_atlas.findRegion("red_light");
		
		assets = new AtlasRegion[]{one,
								   two,
								   three,
								   four,
								   five,
								   six,
								   seven,
								   one_ripe,
								   two_ripe,
								   three_ripe,
								   four_ripe,
								   five_ripe,
								   six_ripe,
								   seven_ripe,
								   background1,//14
								   sparkle_small,//15
								   sparkle_smaller,//16
								   sparkle_large,//17
								   green_light_one,//18
								   green_light_two,//19
								   green_light_three,//20
								   red_light_one,//21
								   red_light_two,//22
								   red_light_three};//23
		
		
		//red jewel animation
		redjewelanim = new TextureAtlas(Gdx.files.internal("data/redjewelanim_packed/redjewelanim.atlas"));
		dbluejewelanim = new TextureAtlas(Gdx.files.internal("data/dbluejewelanim_packed/dbluejewelanim.atlas"));
		lbluejewelanim = new TextureAtlas(Gdx.files.internal("data/lbluejewelanim_packed/lbluejewelanim.atlas"));
		yellowjewelanim = new TextureAtlas(Gdx.files.internal("data/yellowjewelanim_packed/yellowjewelanim.atlas"));
		oranjewelanim = new TextureAtlas(Gdx.files.internal("data/oranjewelanim_packed/oranjewelanim.atlas"));
		purpjewelanim = new TextureAtlas(Gdx.files.internal("data/purpjewelanim_packed/purpjewelanim.atlas"));
		greenjewelanim = new TextureAtlas(Gdx.files.internal("data/greenjewelanim_packed/greenjewelanim.atlas"));
		
		
		// new ui
		n_background1 = ui_atlas.findRegion("n_background1");
	    n_mj = ui_atlas.findRegion("n_mj");
	    n_other = ui_atlas.findRegion("n_other");
	    n_scoreback = ui_atlas.findRegion("n_scoreback");
	    peeler1 = ui_atlas.findRegion("peeler1");
	    
	    n_box1 = ui_atlas.findRegion("n_box1");
	    n_box2 = ui_atlas.findRegion("n_box2");
	    
	    peeler3 = ui_atlas.findRegion("peeler3");
	    mjheader2 = ui_atlas.findRegion("mjheader2");
	    startgame = ui_atlas.findRegion("startgame");
	    playagain = ui_atlas.findRegion("playagain");
	    
		uiAssets = new AtlasRegion[]{
				
				n_background1, //0
			    n_mj,//1
			    n_other,//2
			    n_scoreback,//3
			    peeler1,//4
			    n_box1,//5
			    n_box2,//6
			    
			    peeler3,//7
			    mjheader2,//8
			    startgame,// 9
			    playagain,// 10
				
		};
		
		
		
	}

	public static void dispose(){
		//jewel_atlas.dispose();
	}
	
}
