package com.wileynet.magicjewels.score;

public class Score {
	
	int Purp = 10;
	int Yell = 15;
	int lBlu = 20;
	int Gree = 20;
	int Red = 25;
	int Oran = 20;
	int dBlu = 5;
	
	public Score(){}
	
	public Integer getScore(int s){
		
		int score = 0;
		
		switch(s){
		case 0:
			score = Purp;
			break;
		case 1:
			score = Yell;
			break;
		case 2:
			score = lBlu;
			break;
		case 3:
			score = Gree;
			break;
		case 4:
			score = Red;
			break;
		case 5:
			score = Oran;
			break;
		case 6:
			score = dBlu;
			break;
		}
		
		return score;
		
	}
	
}
