package com.wileynet.magicjewels.util;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.wileynet.magicjewels.Jewel;
import com.wileynet.magicjewels.MagicJewels;

public class StageUtil {
	
	Array<Actor> stage_actors;
	Table table;
	//Stage stage = null;
	
	//public StageUtil( Stage stage ){
	public StageUtil(){
		//this.stage = stage;
		//if(stage != null){
			stage_actors = MagicJewels.stage.getActors();
			table = (Table)stage_actors.first();//only one now
		//}
	}

	public Array<Actor> getActors(){
		return stage_actors;
	}
	
	public Table getTable(){
		return table;
	}
	
	/*
	public Stage getStage(){
		return stage;
	}*/
	
	public Group getTableGroup(String groupname){ 
		
		Actor item = null;
		
		//if(this.table != null){
			SnapshotArray<Actor> array = this.table.getChildren();
			Actor[] items = array.begin();
			
			for(int i = 0, n = array.size; i < n; i++) {  
			    if(items[i].getName() == groupname){
			    	item = items[i];//one for now "gridgroup"
			    }
			}
			array.end();
		//}
		
		Group g = (Group)item;
		return g;
		 
	}
	
	public ArrayList<Actor> getGroupActors(Group g){
		
		ArrayList<Actor> out = new ArrayList<Actor>();
		
		//if(g != null){
			SnapshotArray<Actor> array = g.getChildren();
			Actor item = null;
			Actor[] items = array.begin();
			
	    	//System.out.println("");
	    	//System.out.println("");
			for(int i = 0, n = array.size; i < n; i++) {  
			    	item = items[i];
			    	out.add(item);
			    	
			    	//Jewel j = (Jewel)item;
			    	//System.out.println("name: " + j.getName() + " jname: " + j.jewelName + " spot: " + j.getSpot());
			}
			array.end();
		//}
		
		return out;
	}
	
	
	
}
