package com.wileynet.magicjewels.util;

import java.util.Random;

public class Utils {
	
	private Random r;
	
	public Utils(){
		//
	}
	
  	public float randFloat(float min, float max){
  		r = new Random();
  		float x = min + (max - min) * r.nextFloat();
    	return x;
  	}
  	public int randInt(int min, int max){
  		r = new Random();
  		return r.nextInt(max - min + 1) + min;
  	}
  	
}
