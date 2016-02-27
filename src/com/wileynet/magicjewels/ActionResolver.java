package com.wileynet.magicjewels;

//Android Interface
public interface ActionResolver {
	   
	   public void showOrLoadInterstital();
	
	   //INAPP BILLING METHODS///////////////////////////////////////////////////////
	   public void createConnection();
	   public void buyTokens();
	   public void callFinish();
	   
	
	   //ANDROID METHODS ////////////////////////////////////////////////////////////
	   public void openAdActivity(boolean isWifi);
	   public long getAndroidTime();
	   public void countdown();
	   
	   public void killMyProcess();
	   
	   public void showToast(CharSequence message, int toastDuration);
	   
	   public void showShortToast(CharSequence toastMessage);
	   
	   public void showLongToast(CharSequence toastMessage);
	   
	   public void showAlertBox(String alertBoxTitle, 
			   String alertBoxMessage, String alertBoxButtonText);
	   
	   public void showConfirmAlert(String alertBoxTitle, 
			   String alertBoxMessage, String positiveButton, String negativeButton);
	   
	   public void confirmexit();
	   
	   public void submitLevel();
	   
	   public void showMyList();
	   
	   public void openUri(String url);
	   
}
