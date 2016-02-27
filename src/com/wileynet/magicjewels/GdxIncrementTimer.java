package com.wileynet.magicjewels;

import com.badlogic.gdx.utils.TimeUtils;

public class GdxIncrementTimer {
	
	private long startTime;
	
	public GdxIncrementTimer(long st){
		startTime = st;
	}
	
	public void startTimer(){
			
		if (TimeUtils.timeSinceNanos(startTime) > 1000000000) { // one second
			MagicJewels.since_last_killcluster++;
			startTime = TimeUtils.nanoTime();
		}
		
	}
}
