package com.wileynet.magicjewels;

import com.badlogic.gdx.utils.TimeUtils;

public class GdxCountdownTimer {
	
	private long startTime;
	public int startseconds;
	public String formattedTime;
	public boolean stop = false;
	public int currentMinute;
	public int currentSecond;
	
	public GdxCountdownTimer(long st, int minutes){
		startTime = st;
		startseconds = minutes * 60;
		formattedTime=minutes+":00";
	}
	
	public void countdown(){
		
		if(!formattedTime.equals("0:00")){
			
			if (TimeUtils.timeSinceNanos(startTime) > 1000000000) { // one second
				decrement();
				startTime = TimeUtils.nanoTime();
			}
			
		}else{
			stop = true;
		}
		
	}
	
	private void decrement(){
		startseconds--;
		convertToString();
	}
	
	private void convertToString(){
		
		int m = startseconds / 60;
		currentMinute = m;
		int s = startseconds % 60;
		currentSecond = s;
		
		String minutes = m+":";
		String seconds = ""+s;
		
		if(s < 10){
			seconds = "0"+s;
		}
		
		formattedTime = minutes + seconds;
		
	}
}
