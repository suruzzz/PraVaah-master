package kwa.pravaah;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import kwa.pravaah.database.DbManager;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "KWATest: AlarmReceiver";
    private String POWER="ON";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");

            DbManager db=new DbManager(context);
            String power;

            Bundle b = intent.getExtras();
            assert b != null;
            String No=b.getString("Number");

            Log.i(TAG, "onReceive: Making call");
            Toast.makeText(context, "ALARM", Toast.LENGTH_LONG).show();
            Toast.makeText(context, No, Toast.LENGTH_SHORT).show();
            // AppUtils.makeCall(context,No);


            String number=No.substring(0,10);         Cursor cursor=db.getPowerStatus(number);
            if(cursor.getCount()!=0) {
                cursor.moveToFirst();
                power = cursor.getString(cursor.getColumnIndex(db.POWER));
                //if (power.equalsIgnoreCase(POWER)) {
                    Toast.makeText(context, "Pump ON", Toast.LENGTH_SHORT).show();

                    AppUtils.dial(No, context);

               // }


            }


        }




    }

