package com.wileynet.magicjewels.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.wileynet.magicjewels.Jewel;
import com.wileynet.magicjewels.MagicJewels;


public class GridState {
	
	protected StageUtil stageutil;
	public ArrayList<Actor> jewels = new ArrayList<Actor>();
	
	public HashMap<String,ArrayList<Actor>> clusters = new HashMap<String, ArrayList<Actor>>();
	ArrayList<Actor> cluster;
	int jcount;
	
	//public ArrayList<Actor> notgrouped = new ArrayList<Actor>();
	
	public HashMap<String, ArrayList<Actor>> rows = new HashMap<String, ArrayList<Actor>>();
	
	protected Stage stage;
	protected Group group;
	
	public GridState(Stage stage){
		this.stage = stage;
	}
	
	public void updateGridState(){
		
		this.stageutil = new StageUtil();
		this.group = stageutil.getTableGroup("gridgroup");
		jewels = stageutil.getGroupActors(group);
		
		test2();
	}
	
	
	public void test2(){
		
		
		for(int i=0;i<MagicJewels.grid_rows;i++){
			rows.put("r"+i, new ArrayList<Actor>());
		}
		
		//fill the rows with current state
		addJewelsToRow(0);
		
		//check row one test
		createRowClusters();
		
		//join related clusters
		joinRelatedClusters();
		
		//horizontal check (get horizontal of everything not already in a cluster
		for(int i=0;i<jewels.size();i++){
			Jewel j = (Jewel)jewels.get(i);
			int startspot = j.getSpot();
			horizontalCheckUp(startspot,j,true);
			horizontalCheckDown(startspot,j,true);
		}
		
		//join related clusters again
		joinRelatedClusters();
		
		
		//what jewels are not in clusters, add what's left
		for(int i=0;i<jewels.size();i++){
			Jewel j = (Jewel)jewels.get(i);
			//if(!clusterContains(j)) System.out.println(j.getName() + " not in clusters.");
			if(!clusterContains(j)) addWhatsLeft(j);
		}
		
		//sanity checks
    	if(!clusters.isEmpty() || clusters.size() > 0){
    		
    		System.out.println("\nCluster size: " + clusters.size() + "\n");
    		for(int i=0;i<jewels.size();i++){
    			if(clusters.get("c"+i) != null){
    				System.out.println("cluster: " + i);
    				ArrayList<Actor> c = clusters.get("c"+i);
    				for(int j=0;j<c.size();j++){
    					Jewel jewel = (Jewel)c.get(j);
    					System.out.println(jewel.getName());
    				}
    				System.out.println("");
    			}
    		}
    			
    	}
    	
    	//clear clusters
		clusters.clear();
		
		/*
		for(int i=0;i<rows.size();i++){
			ArrayList<Actor> a = rows.get("r"+i);
			System.out.println("Row " + i);
			for(int j=0;j<a.size();j++){
				Jewel jewel = (Jewel)a.get(j);
				System.out.println(jewel.getName() + " : " + jewel.getJewelName());
			}
			System.out.println("====");
		}
		*/
	}
	
	
	private void createRowClusters(){
		for(int k=0;k<rows.size();k++){
					
			ArrayList<Actor> a = rows.get("r"+k);
			for(int i=0;i<a.size();i++){
						
				Jewel jewel = (Jewel)a.get(i);
				Jewel testJ = null;
				if((i+1)<MagicJewels.grid_columns){
					testJ = (Jewel)a.get(i+1);
					//System.out.println("Testing Jewel: " + testJ.getJewelName());
					addToCluster(jewel.getSpot(),jewel,testJ,true);
					//System.out.println("");
				}
			}
		}
	}
	
	
	private void addToCluster(int startSpot, Jewel j, Jewel testJ, boolean result){
		
		if(!result)
			return;
		else{
			
			if(j.getJewelName() == testJ.getJewelName() && 
					j.getRow() == testJ.getRow()){
				
				//System.out.println(j.getName() + " --- " + testJ.getName());
				
					
					//Jewel newtestJ = (Jewel)jewels.get(testJ.getSpot()+1);
					
					
					Jewel newtestJ = ((testJ.getSpot()+1) == MagicJewels.jewel_count) ? 
								(Jewel)jewels.get(testJ.getSpot()) : (Jewel)jewels.get(testJ.getSpot()+1);
				    
				    	
				    ArrayList<Actor> cluster = new ArrayList<Actor>();
				    cluster.add(j);
				    cluster.add(testJ);
				    	
				    if(clusters.get("c"+startSpot) == null){
						if(!ifInCluster(cluster)){
							clusters.put("c"+startSpot, cluster);
						}
					}else{
						ArrayList<Actor> aa = clusters.get("c"+startSpot);
						aa.add(testJ);
					}
					
				    if((testJ.getSpot()+1) != MagicJewels.jewel_count){
				    	addToCluster(startSpot, testJ, newtestJ, true);
				    }
				
			}
			
		}
	}
	
	
	private boolean ifInCluster(ArrayList<Actor> c){
		
		boolean out = false;
		
		if(!clusters.isEmpty() || clusters.size() > 0){
			
				for(int j=0;j<jewels.size();j++){
					
					if(clusters.get("c"+j) != null){
						ArrayList<Actor> a1 = clusters.get("c"+j);

						for(int k=0;k<a1.size();k++){
							Jewel jewel = (Jewel)a1.get(k);
							int spot = jewel.getSpot();
							for(int z=0;z<c.size();z++){
								Jewel test = (Jewel)c.get(z);
								if(test.getSpot() == spot){
									out = true;
								}
							}
						}
					}
					
				}
		}
		return out;
		
	}
	
	
	//add jewels to row arrays
	private void addJewelsToRow( int counter){
	
	if(counter == (rows.size())){
	     return;
	}else{
	       
		       int start = counter * MagicJewels.grid_columns;
		       ArrayList<Actor> a = rows.get("r"+counter);

		       for(int i=start;i<(start +MagicJewels.grid_columns);i++){
		    	   a.add(jewels.get(i));
		    	   //System.out.println("r"+counter+" added jewel "+i+".");
		       }
		       counter++;
		       addJewelsToRow(counter);
		       return;
	       }
	
	}
	
    private void joinRelatedClusters(){
    	
    	ArrayList<Actor> mergeto = null;
    	ArrayList<Actor> mergefrom = null;
    	String removecluster = "";
    	
    	//store modified
    	ArrayList<ArrayList<Actor>> storageto = new ArrayList<ArrayList<Actor>>();
    	ArrayList<ArrayList<Actor>> storagefrom = new ArrayList<ArrayList<Actor>>();
    	ArrayList<String> storagestring = new ArrayList<String>();
    	
    	
    	if(!clusters.isEmpty() || clusters.size() > 0){
    		
    		
    		for (Map.Entry<String, ArrayList<Actor>> entry : clusters.entrySet()) {
    		    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
    			ArrayList<Actor> c = entry.getValue();
    			
    			//System.out.println("\nCluster: " + entry.getKey());
    			
    			for(Actor actor : c ) {
    				
    				//get our test subject
    				Jewel testJewel = (Jewel)actor;
    				
    				if(testJewel.getRow() != MagicJewels.grid_rows){ //if not last row, we're testing down
	    				
	    				int testrow = testJewel.getRow();
	    				int testcolumn = testJewel.getColumn();
	    				String testName = testJewel.getJewelName();
	    				String thisKey = entry.getKey();
	    				
	    				
	    				//roll through all the other clusters looking for a match
	    				for (Map.Entry<String, ArrayList<Actor>> entry2 : clusters.entrySet()) {
	    					if(entry2.getKey() != thisKey){
	    						ArrayList<Actor> c2 = entry2.getValue();
	    						for(Actor actor2 : c2){
	    							
	    							Jewel next = (Jewel)actor2;
	    							if(next.getRow() == (testrow+1)){
	    								if(next.getColumn() == testcolumn){
	    									if(next.getJewelName() == testName){
	    										
	    										//System.out.println("Match: " + testJewel.getName());
	    										//c.addAll(c2);
	    										//clusters.remove(entry2.getKey());
	    							    		
	    										mergeto = c;
	    										mergefrom = c2;
	    										removecluster = entry2.getKey();
	    										
	    										
	    										storageto.add(mergeto);
	    										storagefrom.add(mergefrom);
	    										storagestring.add(removecluster);
	    										
	    									}
	    								}
	    								
	    							}
	    							
	    							
	    						}
	    					}
	    	    			
	    				}
    				}
    				
    			}
    			
    			
    			
    		}
    				
    				
    			
    	}
    	
    	
    	
    	for(int i=0;i<storageto.size();i++){
    		ArrayList<Actor> to = storageto.get(i);
    		ArrayList<Actor> fr = storagefrom.get(i);
    		String re = storagestring.get(i);
    		
    		//don't add dupes
    		Set<Actor> se = new HashSet<Actor>(fr);
			fr.clear();
			fr = new ArrayList<Actor>(se);
			
			//done
    		to.addAll(fr);
    		clusters.remove(re);
    		
    	}

    	
    	/*
    	if(mergeto != null && mergefrom != null &&
    			removecluster != ""){
    		
    		mergeto.addAll(mergefrom);
    		clusters.remove(removecluster);
    	}
    	*/
    	
    	
		
    	
    }
    
    
    //add any remaining jewels
    
    private boolean clusterContains(Jewel j){
    	// *** a better method
    	
    	//first let get every jewel object that is currently not in a cluster
    	boolean out = false;
    		
    	for (Map.Entry<String, ArrayList<Actor>> entry : clusters.entrySet()) {
    			ArrayList<Actor> c = entry.getValue();
    		
    		if(c.contains(j)){
    			out = true;
    		}
    		
    		
    	}
    	return out;	
    	
    }
    
    private void addWhatsLeft(Jewel j){
    	
    	ArrayList<ArrayList<Actor>> mergetotop = new ArrayList<ArrayList<Actor>>();
    	ArrayList<Jewel> jewelmtop = new ArrayList<Jewel>();
    	
    	ArrayList<ArrayList<Actor>> mergetobottom = new ArrayList<ArrayList<Actor>>();
    	ArrayList<Jewel> jewelmbottom = new ArrayList<Jewel>();
    	
    		
    	for (Map.Entry<String, ArrayList<Actor>> entry : clusters.entrySet()) {
    			
    		    ArrayList<Actor> c = entry.getValue();
    		
    			for(Actor actor : c ) {
    				
    				Jewel testJewel = (Jewel)actor;
    				
    				//on top
    				if(testJewel.getRow() != 0){
    					if(j.getRow() == testJewel.getRow()-1){
    						if(j.getColumn() == testJewel.getColumn()){
    							if(j.getJewelName() == testJewel.getJewelName()){
    								//c.add(j);
    								mergetotop.add(c);
    								jewelmtop.add(j);
    							}
    						}
    					}
    				}
    				
    				//on bottom
    				if(testJewel.getRow() != MagicJewels.grid_rows){
    					if(j.getRow() == testJewel.getRow()+1){
    						if(j.getColumn() == testJewel.getColumn()){
    							if(j.getJewelName() == testJewel.getJewelName()){
    								//c.add(j);
    								mergetobottom.add(c);
    								jewelmbottom.add(j);
    							}
    						}
    					}
    				}
    				
    				
    			}
    	}
    	
    	for(int i=0;i<jewelmtop.size();i++){
    		mergetotop.get(i).add(jewelmtop.get(i));
    	}
    	for(int i=0;i<jewelmbottom.size();i++){
    		mergetobottom.get(i).add(jewelmbottom.get(i));
    	}
    		
    	
    }
    
    
    
    //horizontal
    private void horizontalCheckUp(int startspot, Jewel j,boolean match){
    	
    	if(!match){
   	     	return;
    	}else{
   			int spot = j.getSpot();
   			
   			if((spot - MagicJewels.grid_columns) > -1){
   				
   				Jewel jewel = (Jewel) jewels.get(spot - MagicJewels.grid_columns);
   				
   				if(!clusterContains(jewel) && !clusterContains(j)){
   					if(j.getColumn() == jewel.getColumn()){
   						
   						if(j.getJewelName() == jewel.getJewelName()){
   							
   							if(clusters.get("c"+j.getSpot()) == null){
   								
   								ArrayList<Actor> cluster = new ArrayList<Actor>();
   								cluster.add(j);
   								cluster.add(jewel);
   								clusters.put("c"+j.getSpot(), cluster);
   								
   							}else{
   								ArrayList<Actor> aa = clusters.get("c"+j.getSpot());
   								aa.add(jewel);
   							}
   							
   							horizontalCheckUp(j.getSpot(),jewel,true);
   						
   						
   						}else{
   							horizontalCheckUp(-1,jewel,false);
   						}
   						
   						
   					}
   				}
   				
   			}
    	}
    	
    }
    
    
    
    private void horizontalCheckDown(int startspot, Jewel j,boolean match){
    	
    	if(!match){
   	     	return;
    	}else{
   			int spot = j.getSpot();
   			
   			if((spot + MagicJewels.grid_columns) < (MagicJewels.jewel_count -1)){
   				
   				Jewel jewel = (Jewel) jewels.get(spot + MagicJewels.grid_columns);
   				
   				if(!clusterContains(jewel) && !clusterContains(j)){
   					if(j.getColumn() == jewel.getColumn()){
   						
   						if(j.getJewelName() == jewel.getJewelName()){
   							
   							if(clusters.get("c"+j.getSpot()) == null){
   								
   								ArrayList<Actor> cluster = new ArrayList<Actor>();
   								cluster.add(j);
   								cluster.add(jewel);
   								clusters.put("c"+j.getSpot(), cluster);
   								
   							}else{
   								ArrayList<Actor> aa = clusters.get("c"+j.getSpot());
   								aa.add(jewel);
   							}
   							
   							horizontalCheckDown(j.getSpot(),jewel,true);
   						
   						
   						}else{
   							horizontalCheckDown(-1,jewel,false);
   						}
   						
   						
   					}
   				}
   				
   			}
    	}
    	
    }
    
	
	
}
