/** Copyright [2014] [Kiwavi Technologies]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. */
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