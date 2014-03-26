package com.kiwavi.droidsms;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.telephony.SmsManager;
import android.view.Menu;

public class DROIDSMSApp extends Activity 
{

  AlertDialog alertView;
  AlertDialog.Builder builder; 
	  
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
	builder = new AlertDialog.Builder(this);
    alertView = builder.create();    
    setContentView(R.layout.main);
  }

  public void onNewIntent(Intent intent)
  {
	Bundle bdl = intent.getExtras();
    System.out.println("Bundle in is  " + bdl);
      
      if (bdl != null)
      {
      
  	    String sender = ""+bdl.get("sendernumber");
  	    String message = ""+bdl.get("messagetext");      
  	  
  	    // System.out.println("STARTUP Bundle data " + sender + " msg " + message);
    	  System.out.println("Message FROM RECEIVER is surely received from " + sender + "   " + message);  	    
  	    receiveMessage(sender, message);

      }
  }
  
  void showMessage(final String message)
  {
	runOnUiThread( new Runnable()
	{
	  public void run()
	  {
        // System.out.println(message);
        alertView.setMessage(message);
        alertView.show();
	  }
	});	  
  }
  
  void endApp()
  {
	finish();  
  }
  
  
  void receiveMessage(String sender, String message)
  {
	String numessage = message.substring(2).trim();
	
	showMessage("Wow, you want " + numessage.trim().toUpperCase());
	
    sendMessage(sender, "Your requested item : " + numessage);
  }
  
  synchronized void sendMessage(String address, String message)
  {
	System.out.println("SEND " + message + " to " + address);
    SmsManager sms = SmsManager.getDefault();
    sms.sendTextMessage(address, null, message, null, null);
    
  }  
}