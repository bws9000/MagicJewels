package com.wileynet.magicjewels;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wileynet.magicjewels.util.JewelMapGrid;
import com.wileynet.magicjewels.util.StageUtil;
import com.wileynet.magicjewels.util.Utils;

public class Seed {
	
	//this may be usefull at some point .......
	
	Group group;
	StageUtil stageutil;
	Stage stage;
	JewelMapGrid jmg;
	Utils utils;
	private ArrayList<Actor> jewels = new ArrayList<Actor>();
	
	public Seed(JewelMapGrid jmg, Stage stage){
		this.jmg = jmg;
		this.stage = stage;
		stageutil = new StageUtil();
		this.group = stageutil.getTableGroup("gridgroup");
		jewels = stageutil.getGroupActors(group);
	}
	
	public void defaultseed(){
		
		/*
		Jewel j = (Jewel)g.findActor("jewel23");
		ArrayList<Integer> neighbors = getNeighbors(j);
		for(int i=0;i<neighbors.size();i++){
			System.out.println(""+neighbors.get(i));
		}*/
		
		//j.setAtlasRegion(0);
		//jmg.updateGridState(); // causes crash ...
		
		//viewFinalClusters();
	
	}
	
	public void viewFinalClusters(){
		ArrayList<ArrayList<Actor>> f = MagicJewels.finalclusters;
		
		System.out.println("\n\noooooooooo");
		for(int i=0;i<f.size();i++){
			ArrayList<Actor> a = f.get(i);
			System.out.println("---------");
			System.out.println("cluster: " + i);
			for(int k=0;k<a.size();k++){
				System.out.println(""+a.get(k));
			}
		}
	}
	
	
	public void addOneToTwo(Jewel jewel){

		ArrayList<ArrayList<Actor>> f = MagicJewels.finalclusters;
		for(int i=0;i<f.size();i++){
			ArrayList<Actor> a = f.get(i);
			for(int k=0;k<a.size();k++){
				Jewel j = (Jewel)a.get(k);
				if(!isInClusters(j.getName()) &&
						a.size() == 2){
					
					//
					
				}
			}
		}
		
	}
	
	public ArrayList<Integer> getNeighbors(Jewel jewel){
		
		ArrayList<Integer> out = new ArrayList<Integer>();
		
		int spot = jewel.getSpot();
		int row = jewel.getRow();
		
		int top = spot - MagicJewels.grid_columns;
		int bottom = spot + MagicJewels.grid_columns;
		int left = spot - 1;
		int right = spot + 1;
		
		if(top > -1){
			out.add(top);
		}
		
		if(bottom < MagicJewels.jewel_count &&
				bottom > -1){
			out.add(bottom);
		}
		
		if(getRowFromSpot(left) == row &&
				left > -1){
			out.add(left);
		}
		
		if(getRowFromSpot(right) == row &&
				getRowFromSpot(right) < MagicJewels.jewel_count){
			out.add(right);
		}
		
		
		return out;
		
	}
	
	private Integer getRowFromSpot(int spot){
		
		int row = 0;
		if (spot < MagicJewels.grid_rows){
			//
		}else{
					
			row = spot / MagicJewels.grid_rows;
					
		}
		return row;
				
	}
	
	
	public boolean isInClusters(String name){
		
		boolean out = false;
		
		ArrayList<ArrayList<Actor>> f = MagicJewels.finalclusters;
		for(int i=0;i<f.size();i++){
			ArrayList<Actor> a = f.get(i);
			for(int k=0;k<a.size();k++){
				Jewel j = (Jewel)a.get(k);
				if(j.getName().equals(name)){
					out = true;
				}
			}
			
		}
		return out;
		
	}
	
	
	/*
	public void addOneToTwo(Jewel j){
		
		boolean out = false;
		
		ArrayList<ArrayList<Actor>> f = jmg.finalclusters;
		for(int i=0;i<f.size();i++){
			ArrayList<Actor> a = f.get(i);
			for(int k=0;k<a.size();k++){
				Jewel j = (Jewel)a.get(k);
				
			}
			
		}
	}
	*/
	

}
