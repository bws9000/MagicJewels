package com.wileynet.magicjewels.levelone;

import java.util.ArrayList;
import com.wileynet.magicjewels.*;
import com.wileynet.magicjewels.util.Utils;

public class JewelArray {
	
	final MagicJewels game;
	
	public static ArrayList<Jewel>jewels_start = new ArrayList<Jewel>();
	
	private Jewel jewel;
	private LevelOne levelone;
	private boolean test_mode = false;
	
	public JewelArray(MagicJewels gam){
		this.game = gam;
		//this.levelone = levelone;
		//gridstate = GridState.gridstate;
	}
	
	public void buildGrid(){
		
		int x = MagicJewels.grid_start_x;
		//int y = MagicJewels.grid_start_y;
		int spot_count =0;
		Utils u = new Utils();
		
		
		for(int i=0;i<MagicJewels.grid_rows;i++){
			int y = MagicJewels.grid_start_y - (i * MagicJewels.jewel_height);
			for(int j=0;j<MagicJewels.grid_columns;j++){
				
				int rr = u.randInt(0, 6);//random AtlasRegion
				x = MagicJewels.grid_start_x + (j * MagicJewels.jewel_width);
				//jewel = new Jewel(levelone,x,y,spot_count,i,j,rr,rr);
				jewel = createJewel(levelone,x,y,spot_count,i,j,rr,rr);
				jewels_start.add(jewel);
				
				spot_count++;
			}
			
		}
		
		if(test_mode){
			
			for(int k=0;k<c_shape1().size();k++){
				jewels_start.get(c_shape1().get(k)).setJewelName(0);
				jewels_start.get(c_shape1().get(k)).setAtlasRegion(0);
			}
			
		}
		
		System.out.println("Initial Grid Build Complete");
		
	}
	
	
	private Jewel createJewel(LevelOne levelone, int x, int y,
				 final int spot_count, final int i, final int j, int ar,int rr){
		
		jewel = new Jewel(levelone,x,y,spot_count,i,j,ar,rr,game);
	    
		return jewel;
		
	}
	//test shapes
	private ArrayList<Integer> c_shape1(){
		
		ArrayList<Integer> cshape = new ArrayList<Integer>();
		
		cshape.add(31);
		cshape.add(14);
		cshape.add(12);
		cshape.add(30);
		cshape.add(29);
		cshape.add(13);
		cshape.add(28);
		cshape.add(22);
		
		return cshape;
		
	}
	
	public ArrayList<Jewel> getJewels(){
			return jewels_start;
	}
}
