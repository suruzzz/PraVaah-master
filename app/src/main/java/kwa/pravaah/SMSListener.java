package kwa.pravaah;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import kwa.pravaah.database.AsyncResponse;
import kwa.pravaah.database.DbManager;
import kwa.pravaah.database.Pushdata;
import kwa.pravaah.model.message;

public class SMSListener extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");


        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        msgBody = msgBody.replace("EVENT HRS", "EVENTHRS");
                        msgBody = msgBody.replace("\n", "\",\"");
                        msgBody = msgBody.replace(" ", "\"");
                        msgBody = "{\"" + msgBody + "\"}";
                        JSONObject msgJson = new JSONObject(msgBody);
                        Log.e("Incoming message", msgJson.toString());

                        Gson gson = new Gson();
                        message message = gson.fromJson(msgBody, message.class);
                        if (msg_from != null && msg_from.length() > 0) {
                            message.setSender(msg_from);
                        }
                        Log.e("Incoming message", msgJson.toString());
                        Toast.makeText(context, "message received", Toast.LENGTH_SHORT).show();

                        DbManager dbManager = new DbManager(context);
                        try {
                            String no = message.getSender();
                            if (no.length() == 13)
                                no = no.substring(3, 13);
                            else if (no.length() == 12)
                                 no = no.substring(2,12);

                         /*   boolean isInserted = dbManager.insertUserDetails(message.getSender(), "", message.getPower(), message.getPump(),
                                    "", "", message.getErr(), message.getEventHrs(), message.getAuto(),
                                    message.getTm(), "", "", "");*/
                            boolean isInserted = dbManager.updateUserDetails(no, message.getPower(), message.getPump());

                            String[] params = {message.getSender(), message.getPower(), message.getPump(), message.getErr(),
                                    message.getEventHrs(), message.getAuto()};
                            //new Pushdata().execute(params);
                            Pushdata asyncTask = new Pushdata(new AsyncResponse() {
                                @Override
                                public void processFinish(Object output) {

                                    if ((String)output == "Success")
                                    {
                                        Toast.makeText(context, "Sheet Updated Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(context,"Error in Sheet Updation", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                            asyncTask.execute(params);


                            if(isInserted){
                                Log.e("inserted", "successfully");
                                Toast.makeText(context, "message inserted to db", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
