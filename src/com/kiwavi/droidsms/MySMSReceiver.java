package com.kiwavi.droidsms;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MySMSReceiver extends BroadcastReceiver 
{

  ActivityManager activityManager;
  Context incontext;
  String msglen = "";
	  
  private String[] getMessageFromIntent(Intent intent)
  {
    // array of length 2 containing originating number and message text
    String mymessage[] = new String[]{"",""};

    // the message text
    String message_text = "";

    // get the bundle containing the data (SMS Messages)
    Bundle bdl = intent.getExtras();
    try{

      // collection of PDU's containing each a 160 character sms message
      Object pdus[] = (Object [])bdl.get("pdus");

      SmsMessage retMsgs[] = new SmsMessage[pdus.length];
      if (retMsgs != null) 
      {
        for(int n=0; n < pdus.length; n++)
        {
          byte[] byteData = (byte[])pdus[n];
          retMsgs[n] = SmsMessage.createFromPdu(byteData);
          message_text += retMsgs[n].getMessageBody(); //  + " " + n + "  " + pdus.length;        
        }

        mymessage[0] = retMsgs[0].getOriginatingAddress();
        mymessage[1] = message_text; 
      }       
    }
    catch(Exception ex)
    {
	   System.out.println("GetMessagesfromPDU + fails " +  ex);
    }
    return mymessage;
  }  
	  
		 
	  public void onReceive(Context incomingContext, Intent intent) 
	  {

	    abortBroadcast();

	    try {
            // extract the sms message and sender address
	    	String msg[] = getMessageFromIntent(intent);
	    	
			if (msg == null) { return; }
			String number = "", messagetext="";
			
			System.out.println("MEssage received ...");
			// confirm a valid sms has been received
			if (msg.length >= 1 && !msg[0].equals("")) {
			  number = msg[0];
			  messagetext += msg[1];
			}
			else {
			  clearAbortBroadcast();
			  return;
			}
			System.out.println("MEssage received and filtered ...");		
			if (messagetext.startsWith("*&"))
			{
				
			  Intent newIntent = new Intent();
		      newIntent.putExtra("messagetext", messagetext);
		      newIntent.putExtra("sendernumber", number);
		      newIntent.setClassName("com.kiwavi.droidsms", "com.kiwavi.droidsms.DROIDSMSApp");
		      newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			  incomingContext.getApplicationContext().startActivity(newIntent);
			}
			else {
		      clearAbortBroadcast();
			}

			
		}
	    catch (Exception ex)
	    {
	      System.out.println(" Error at SMS Receive" + ex);
	    }
	  }
	  

	}
