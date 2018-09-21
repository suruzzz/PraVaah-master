package kwa.pravaah;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kwa.pravaah.database.DbManager;

public class CancelAlarm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button cancel;
    EditText Ph;
    DbManager db;
    String PhoneNo,Name;
    TextView tv;
    private static final int CONTACT_PICK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        db=new DbManager(CancelAlarm.this);
        Ph=findViewById(R.id.Number);
        tv=findViewById(R.id.tv_Name);


        cancel=findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (db.getnumber(PhoneNo))
                {
                    Cursor cursor=db.getPendingIntent(PhoneNo);
                    if(cursor.getCount()!=0) {
                        cursor.moveToFirst();
                        String Pending_intent_to_on = cursor.getString(cursor.getColumnIndex(db.PENDING_INTENT_ON));
                        String Pending_intent_to_off = cursor.getString(cursor.getColumnIndex(db.PENDING_INTENT_OFF));
                        String Pending_intent_to_on2 = cursor.getString(cursor.getColumnIndex(db.PENDING_INTENT_ON2));
                        String Pending_intent_to_off2 = cursor.getString(cursor.getColumnIndex(db.PENDING_INTENT_OFF2));

                        cancelAlarm(Pending_intent_to_on);
                        cancelAlarm(Pending_intent_to_off);
                        cancelAlarm(Pending_intent_to_on2);
                        cancelAlarm(Pending_intent_to_off2);

                        Toast.makeText(CancelAlarm.this, "Alarm cleared", Toast.LENGTH_SHORT).show();
                        String phone=Ph.getText().toString();

                        db.deleteRow(phone);

                        Ph.setText("");
                        tv.setText("");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "No alarms to clear...!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

      @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
          int id = item.getItemId();

          if (id == R.id.home) {
              Intent i1 = new Intent(CancelAlarm.this,Home.class);
              startActivity(i1);
          } else if (id == R.id.nav_add) {
              Intent i1 = new Intent(CancelAlarm.this,AddNewPumpHouseActivity.class);
              startActivity(i1);
          } else if (id == R.id.nav_setalarm) {
              Intent i1 = new Intent(CancelAlarm.this,AddAlarm.class);
              startActivity(i1);
          } else if (id == R.id.nav_cancelalarm) {
              Intent i1 = new Intent(CancelAlarm.this,CancelAlarm.class);
              startActivity(i1);
          } else if (id == R.id.nav_share) {

          }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void contactPickerOnClick(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICK);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case CONTACT_PICK:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }


    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            PhoneNo = cursor.getString(phoneIndex);
            Name = cursor.getString(nameIndex);
            // Set the value to the textviews
            //textView1.setText(name);
            if (PhoneNo.length() == 13)
                PhoneNo = PhoneNo.substring(3, 13);
            Ph.setText(PhoneNo);
            tv.setText(Name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAlarm(String pndIntent)
    {
        AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(),
                Integer.parseInt(pndIntent),intent,0);
        aManager.cancel(pIntent);
    }

}
