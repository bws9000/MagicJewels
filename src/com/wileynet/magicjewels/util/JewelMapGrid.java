package com.wileynet.magicjewels.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.wileynet.magicjewels.Jewel;
import com.wileynet.magicjewels.MagicJewels;

//the playing "board", "grid" of jewels
public class JewelMapGrid {
	
	final Game game;
	
	protected StageUtil stageutil;
	
	public ArrayList<Actor> jewels = new ArrayList<Actor>();
	int jcount;
	
	protected Stage stage;
	protected Group group;
	
    //public ArrayList<ArrayList<Actor>> finalclusters = new ArrayList<ArrayList<Actor>>();
	public ArrayList<ArrayList<Actor>> rowclusters = new ArrayList<ArrayList<Actor>>();
	public ArrayList<ArrayList<Actor>> columnclusters = new ArrayList<ArrayList<Actor>>();
	
	public HashMap<String, ArrayList<Actor>> rows = new HashMap<String, ArrayList<Actor>>();
	public HashMap<String, ArrayList<Actor>> columns = new HashMap<String, ArrayList<Actor>>();
	
	public JewelMapGrid(final Game gam, Stage st) {
		this.game = gam;
		this.stage = st;
	}
	
	
	public void updateGridState(){
		
		MagicJewels.finalclusters = new ArrayList<ArrayList<Actor>>();
		
		//get grid info
		this.stageutil = new StageUtil();
		this.group = stageutil.getTableGroup("gridgroup");
		jewels = stageutil.getGroupActors(group);
		
		//find clusters
		if(!MagicJewels.end_the_game)
			update();
		
	}
	
	
	public void update(){
		
		/*
		MagicJewels.finalclusters.clear();
		columnclusters.clear();
		rowclusters.clear();
		rows.clear();
		columns.clear();
		*/
		
		//create index for rows/columns
		for(int i=0;i<MagicJewels.grid_rows;i++){
			rows.put("r"+i, new ArrayList<Actor>());
		}
		for(int i=0;i<MagicJewels.grid_columns;i++){
			columns.put("c"+i, new ArrayList<Actor>());
		}

		addJewelsToRow(0);
		
		addJewelsToColumn(0);
		
		createColumnClusters();
		
		createRowClusters();
    	
    	joinRowsColumns();
    	
    	
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
				     }
				     counter++;
				     addJewelsToRow(counter);
				     return;
			    }
			
			}
			
			//add jewels to row arrays
			private void addJewelsToColumn( int counter){
				
				if(counter == (columns.size())){
				     return;
				}else{  
					 
					 int start = counter;
					 ArrayList<Actor> b = columns.get("c"+counter);
					 
					 int k=start;
					 for(int i=start;i<(start +MagicJewels.grid_rows);i++){
						 b.add(jewels.get(k));
						 k = k + MagicJewels.grid_columns;
					 }
					 
					 counter++;
					 addJewelsToColumn(counter);
					 return;
				}
				
			}
			
			
			
	
	private void createColumnClusters(){
		
		for(int k=0;k<columns.size();k++){
			
			ArrayList<Actor> a = columns.get("c"+k);
			
			for(int i=0;i<a.size();i++){
					
				Jewel jewel = (Jewel)a.get(i);
				
				if((i+1)<MagicJewels.grid_rows){
					checkDown(jewel,a);
				}
				
			}
		}
		
		joinColumnJewels();

	}
	
	private void createRowClusters(){
		
		for(int k=0;k<rows.size();k++){
			
			ArrayList<Actor> a = rows.get("r"+k);
			
			for(int i=0;i<a.size();i++){
					
				Jewel jewel = (Jewel)a.get(i);
				
				if((i+1)<MagicJewels.grid_columns){
					checkRight(jewel,a);
				}
				
			}
		}
		
		joinRowJewels();

	}
	
	
	
	private void joinColumnJewels(){
    	
    	ArrayList<Actor> mergeto = null;
    	ArrayList<Actor> mergefrom = null;
    	
    	//store modified
    	ArrayList<ArrayList<Actor>> storageto = new ArrayList<ArrayList<Actor>>();
    	ArrayList<ArrayList<Actor>> storagefrom = new ArrayList<ArrayList<Actor>>();
    	
    	
    	if(!columnclusters.isEmpty() || columnclusters.size() > 0){
    		
    		
    		for(int i=0;i<columnclusters.size();i++){
    			
    			ArrayList<Actor> c = columnclusters.get(i);
    			
    			for(int j=0;j<c.size();j++){
    				
    				//get our test subject
    				Jewel testJewel = (Jewel)c.get(j);
    				
    				if(testJewel.getRow() != MagicJewels.grid_rows){ //if not last row, we're testing down
	    				
	    				
	    				//roll through all the other clusters looking for a match
    					for(int k=0;k<columnclusters.size();k++){
    						ArrayList<Actor> c2 = columnclusters.get(k);
	    					
    						if(c2.contains(testJewel)){
	    										
	    						//System.out.println("Match: " + testJewel.getName());
	    						//c.addAll(c2);
	    						//clusters.remove(entry2.getKey());
	    							    		
	    						mergeto = c;
	    						mergefrom = c2;
	    													
	    						storageto.add(mergeto);
	    						storagefrom.add(mergefrom);
	    										
	    					}
	    				}
	    								
	    			}
	    							
	    							
	    		}
	    	}
	    	    			
	    }
    	
    	
    	ArrayList<Actor> to = new ArrayList<Actor>();
		ArrayList<Actor> fr = new ArrayList<Actor>();
		
    	for(int i=0;i<storageto.size();i++){
    		to = storageto.get(i);
    		fr = storagefrom.get(i);
    		
    		//don't add dupes
    		Set<Actor> se = new HashSet<Actor>(fr);
			fr.clear();
			fr = new ArrayList<Actor>(se);
			
			//done
    		to.addAll(fr);
    		
    	}
    	
    	/*
    	for(int i=0;i<columnclusters.size();i++){
    		ArrayList<Actor> x = columnclusters.get(i);
    		if(x.isEmpty()){
    			//System.out.println(""+ i+" is empty");
    		}
    	}*/
    	
    	//storageto.clear();
    	//storagefrom.clear();
    	
    }
	
	
	private void joinRowJewels(){
    	
    	ArrayList<Actor> mergeto = null;
    	ArrayList<Actor> mergefrom = null;
    	
    	//store modified
    	ArrayList<ArrayList<Actor>> storageto2 = new ArrayList<ArrayList<Actor>>();
    	ArrayList<ArrayList<Actor>> storagefrom2 = new ArrayList<ArrayList<Actor>>();
    	
    	
    	if(!rowclusters.isEmpty() || rowclusters.size() > 0){
    		
    		
    		for(int i=0;i<rowclusters.size();i++){
    			
    			ArrayList<Actor> r = rowclusters.get(i);
    			
    			for(int j=0;j<r.size();j++){
    				
    				//get our test subject
    				Jewel testJewel = (Jewel)r.get(j);
    				
    				if(testJewel.getRow() != MagicJewels.grid_rows){ 
	    				
	    				
	    				//roll through all the other clusters looking for a match
    					for(int k=0;k<rowclusters.size();k++){
    						ArrayList<Actor> r2 = rowclusters.get(k);
	    					
    						if(r2.contains(testJewel)){
	    										
	    						//System.out.println("Match: " + testJewel.getName());
	    						//c.addAll(c2);
	    						//clusters.remove(entry2.getKey());
	    							    		
	    						mergeto = r;
	    						mergefrom = r2;
	    													
	    						storageto2.add(mergeto);
	    						storagefrom2.add(mergefrom);
	    										
	    					}
	    				}
	    								
	    			}
	    							
	    							
	    		}
	    	}
	    	    			
	    }
    	
    	ArrayList<Actor> to = new ArrayList<Actor>();
		ArrayList<Actor> fr = new ArrayList<Actor>();
    	
    	for(int i=0;i<storageto2.size();i++){
    		to = storageto2.get(i);
    		fr = storagefrom2.get(i);
    		
    		//don't add dupes
    		Set<Actor> se = new HashSet<Actor>(fr);
			fr.clear();
			fr = new ArrayList<Actor>(se);
			
			//done
    		to.addAll(fr);
    		
    	}
    	
    	/*
    	for(int i=0;i<rowclusters.size();i++){
    		ArrayList<Actor> x = rowclusters.get(i);
    		if(x.isEmpty()){
    			//System.out.println(""+ i+" is empty");
    		}
    	}*/
    	
    	//storageto2.clear();
    	//storagefrom2.clear();
    }
	
	
	private void joinRowsColumns(){
		
		ArrayList<ArrayList<Actor>> cc = new ArrayList<ArrayList<Actor>>();
		for(int i=0;i<columnclusters.size();i++){
			cc.add(columnclusters.get(i));
		}
		
		for(ArrayList<Actor> c : columnclusters){
			int cindex = columnclusters.indexOf(c);
			
			for(ArrayList<Actor> r : rowclusters){
				int rindex = rowclusters.indexOf(r);
				
				for(Actor row_actor : r){
					
					Jewel rj = (Jewel)row_actor;
						
						if(c.contains(rj)){
							//System.out.println("\nMatch  cindex:"+ cindex + "rindex:" + rindex);
							cc.get(cindex).addAll(rowclusters.get(rindex));// CC NOT COLUMNCLUSTERS
						}
					
				}
			}
				
		}
		
		//add final clusters
		for(ArrayList<Actor> c : cc){
			if(c.size() > 2){
				MagicJewels.finalclusters.add(c);
			}
		}
		
		removeEmpty(MagicJewels.finalclusters);
		removeDupes(MagicJewels.finalclusters);
		
		mergeFinalclusters();
		//removeDupes(MagicJewels.finalclusters);//again
	    cleanupRowColumnsFinish();
	    
	    mergeFinalclusters();
	    cleanupRowColumnsFinish();
		
	}
	
	
	private void removeEmpty(ArrayList<ArrayList<Actor>> cluster){
		
			//check for empty
			for ( int i = cluster.size() - 1;  i >= 0; i--){
				if (cluster.get(i).isEmpty() || cluster.get(i).size() < 1){
					cluster.remove(i);
				}
			}
		
	}
	private void removeDupes(ArrayList<ArrayList<Actor>> cluster){
		
		//check for dupes
		for ( int i=0; i<cluster.size();i++){
			ArrayList<Actor> al = cluster.get(i);
			Set<Actor> hs = new HashSet<Actor>();
			hs.addAll(al);
			al.clear();
			al.addAll(hs);
		}
	
	}
	private void mergeFinalclusters(){
			
		ArrayList<ArrayList<Actor>> f1 = MagicJewels.finalclusters;
		ArrayList<ArrayList<Actor>> f2 = MagicJewels.finalclusters;
		
		/*
		System.out.println("finalclusters before merge:");
		for(int i=0;i<f1.size();i++){
			ArrayList<Actor> a = f1.get(i);
			System.out.println(":"+i);
			for(int j=0;j<a.size();j++){
				System.out.println("::"+a.get(j).getName());
			}
		}*/

		//test
		for(int i=0;i<f1.size();i++){
			for(int k=0;k<f2.size();k++){
				
				//if(f1.get(i).size() != f1.get(k).size()){
					if(!Collections.disjoint(f1.get(i),f1.get(k)) && k != i){ // ! ! ! disjoint is my friend maybe
						//System.out.println(""+i+" -- "+k);
						f1.get(i).addAll(f1.get(k));
						MagicJewels.finalclusters.remove(k);
						break;
					}
				//}
				
			}
		}
		
		
	}
	private void cleanupRowColumnsFinish(){
		
		//if it's in finalclusters remove from columns and rows clusters
		//remove empties
		
		// F.Y.I
		//3 or more columns are being added to finalclusters
		//3 or more rows are not
		
		ArrayList<ArrayList<Actor>> f1 = MagicJewels.finalclusters;
		ArrayList<ArrayList<Actor>> cc = columnclusters;
		ArrayList<ArrayList<Actor>> rc = rowclusters;
		
		for(int i=0;i<f1.size();i++){
			for(int j=0;j<cc.size();j++){
				if(!Collections.disjoint(f1.get(i), cc.get(j))){
					columnclusters.remove(j);
					j--;
				}
			}
		}
		
		for(int i=0;i<f1.size();i++){
			for(int j=0;j<rc.size();j++){
				if(!Collections.disjoint(f1.get(i), rc.get(j))){
					rowclusters.remove(j);
					j--;
				}
			}
		}
		
		removeEmpty(columnclusters);
		removeEmpty(rowclusters);
		
		for(int i=0;i<cc.size();i++){
			MagicJewels.finalclusters.add(cc.get(i));
		}
		for(int i=0;i<rc.size();i++){
			MagicJewels.finalclusters.add(rc.get(i));
		}
		
	}
	
	
	private boolean isSubset(ArrayList<Actor> a1, ArrayList<Actor> a2) {
		   Set<Actor> s1 = new HashSet<Actor>(a1);
		   Set<Actor> s2 = new HashSet<Actor>(a2);
		   return s1.containsAll(s2);
	}
	
	
	private void checkDown(Jewel jewel, ArrayList<Actor> jarray){
		
		int position=0;
		Jewel jbot = null;
		
		if(jewel == null)
			return;
		else{
			
			//get position in array we are testing
			for(int i=0;i<jarray.size();i++){
				Jewel jtest = (Jewel)jarray.get(i);
				if(jewel.getSpot() == jtest.getSpot()){
					position = i;
				}
			}
			
			if((position+1)<MagicJewels.grid_rows){
				jbot = (Jewel)jarray.get(position+1);
			}
			
			if(jbot != null){
				
				if(jewel.getJewelName() == jbot.getJewelName()&& 
						jewel.getColumn() == jbot.getColumn()){
					
					
					ArrayList<Actor> cluster = new ArrayList<Actor>();
				    cluster.add(jewel);
					cluster.add(jbot);
					columnclusters.add(cluster);
					
					//this produces dupes to filter ...
							
				}
					
			}
				
				
			}
			
	}
	
	
	private void checkRight(Jewel jewel, ArrayList<Actor> jarray){
		
		int position=0;
		Jewel jbot = null;
		
		if(jewel == null)
			return;
		else{
			
			//get position in array we are testing
			for(int i=0;i<jarray.size();i++){
				Jewel jtest = (Jewel)jarray.get(i);
				if(jewel.getSpot() == jtest.getSpot()){
					position = i;
				}
			}
			
			if((position+1)<MagicJewels.grid_columns){
				jbot = (Jewel)jarray.get(position+1);
			}
			
			if(jbot != null){
				
				if(jewel.getJewelName() == jbot.getJewelName()&& 
						jewel.getRow() == jbot.getRow()){
					
					
					ArrayList<Actor> cluster = new ArrayList<Actor>();
				    cluster.add(jewel);
					cluster.add(jbot);
					rowclusters.add(cluster);
					
					//this produces dupes to filter ...
							
				}
					
			}
				
				
			}
			
	}
	
	
	
	

	
	private int jewelInCluster(Jewel jewel){
		
		int out = -1;
		
		for(int j=0;j<columnclusters.size();j++){
			ArrayList<Actor> a = columnclusters.get(j);
			for(int k=0;k<a.size();k++){
				Jewel j2 = (Jewel)a.get(k);
				if(a.contains(j2))out = j;
			}
		}

		return out;
		
	}
	
	
	private boolean ifClusterInColumnClusters(ArrayList<Actor> c){
		
		boolean out = false;
		
		for(int i=0;i<c.size();i++){
			Jewel j1 = (Jewel)c.get(i);
			for(int j=0;j<columnclusters.size();j++){
				ArrayList<Actor> a = columnclusters.get(j);
				for(int k=0;k<a.size();k++){
					Jewel j2 = (Jewel)a.get(k);
					if(j2.getSpot() == j1.getSpot()){
						out = true;
					}
				}
			}
		}

		return out;
		
	}
	
	
	

		
		public void clusterDebug(ArrayList<ArrayList<Actor>> cluster, String name){
			
			if(!cluster.isEmpty() || cluster.size() > 0){
	    		
	    		System.out.println("\n" + name + " size: " + cluster.size() + "\n");
	    		for(int i=0;i<cluster.size();i++){
	    			if(cluster.get(i) != null){
	    				System.out.println(name+": " + i);
	    				ArrayList<Actor> f = cluster.get(i);
	    				for(int j=0;j<f.size();j++){
	    					Jewel jewel = (Jewel)f.get(j);
	    					System.out.println(jewel.getName());
	    				}
	    				System.out.println("");
	    			}
	    		}	
	    	}
		}
		
		
		
		public void rowscolumnsDebug(){
			
			for (Entry<String, ArrayList<Actor>> entry : rows.entrySet()) {
			    
				String key = entry.getKey();
			    System.out.println("\nrow: " + key);
			    
			    ArrayList<Actor> value = entry.getValue();
			    for(int i=0;i<value.size();i++){
			    	System.out.println("actor: " + value.get(i).getName());
			    }
			    
			}
			
			System.out.println("==========");
			
			for (Entry<String, ArrayList<Actor>> entry : columns.entrySet()) {
			    
				String key = entry.getKey();
			    System.out.println("\ncolumn: " + key);
			    
			    ArrayList<Actor> value = entry.getValue();
			    for(int i=0;i<value.size();i++){
			    	System.out.println("actor: " + value.get(i).getName());
			    }
			    
			}
		}

}
