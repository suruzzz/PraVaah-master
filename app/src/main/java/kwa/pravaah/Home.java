package kwa.pravaah;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import kwa.pravaah.database.DbManager;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "KWATesting";
    private static final int PHONE_REQUEST = 101;
    private static final int SMS_REQUEST = 102;
    private static final int RECEIVE_SMS_REQUEST = 103;


    private TableLayout tableLayout;
    TabHost newRow;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        tableLayout = (TableLayout) findViewById(R.id.viewtable);
        db = new DbManager(Home.this);

        int numrows = db.numOfRows();
        Cursor cursor = db.viewData();

        if (numrows != 0) {
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {


                        // String number_str = cursor.getString(cursor.getColumnIndex(db.MOBILE_NO));
                        String name_str = cursor.getString(cursor.getColumnIndex(db.NAME));
                        String power_str = cursor.getString(cursor.getColumnIndex(db.POWER));
                        String pump_str = cursor.getString(cursor.getColumnIndex(db.PUMP));
                        String on_str = cursor.getString(cursor.getColumnIndex(db.TIME_ON));
                        String off_str = cursor.getString(cursor.getColumnIndex(db.TIME_OFF));
                        String on_str2 = cursor.getString(cursor.getColumnIndex(db.TIME_ON2));
                        String off_str2 = cursor.getString(cursor.getColumnIndex(db.TIME_OFF2));

                        View tableRow = LayoutInflater.from(this).inflate(R.layout.content_home, null, false);
                        //  TextView Num = (TextView) tableRow.findViewById(R.id.Number);
                        TextView Name = (TextView) tableRow.findViewById(R.id.name);
                        TextView Power = (TextView) tableRow.findViewById(R.id.power);
                        TextView Pump = (TextView) tableRow.findViewById(R.id.pump);
                        TextView ON = (TextView) tableRow.findViewById(R.id.ON1);
                        TextView OFF = (TextView) tableRow.findViewById(R.id.OFF1);
                        TextView ON2 = (TextView) tableRow.findViewById(R.id.ON2);
                        TextView OFF2 = (TextView) tableRow.findViewById(R.id.OFF2);

                        //  Num.setText(number_str.toString());
                        Name.setText(name_str.toString());
                        Power.setText(power_str.toString());
                        Pump.setText(pump_str.toString());
                        ON.setText(on_str.toString());
                        OFF.setText(off_str.toString());
                        ON2.setText(on_str2.toString());
                        OFF2.setText(off_str2.toString());
                        tableLayout.addView(tableRow);

                    }
                }
            }catch (Exception e){

            }



        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onCreate: No permission.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PHONE_REQUEST);
        } else {
            Log.i(TAG, "onCreate: Phone permitted");
        }


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onCreate: No permission.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, SMS_REQUEST);
        } else {
            Log.i(TAG, "onCreate: Messaging permitted");
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "onCreate: No permission.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS_REQUEST);
        } else {
            Log.i(TAG, "onCreate: Messaging permitted");
        }




    }
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PHONE_REQUEST) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: Permission granted");
                Toast.makeText(this, "Call permission Granted!!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No permission to use Phone. App won't work as expected", Toast.LENGTH_SHORT).show();

                Log.i(TAG, "onRequestPermissionsResult: Permission denied");
            }
        }
        if (requestCode == SMS_REQUEST) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: Permission granted");
                Toast.makeText(this, "SMS permission Granted!!!", Toast.LENGTH_SHORT).show();
               } else {
                Toast.makeText(this, "No permission to use SMS. App won't work as expected", Toast.LENGTH_SHORT).show();

                Log.i(TAG, "onRequestPermissionsResult: Permission denied");
            }
        }
        if (requestCode == RECEIVE_SMS_REQUEST) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: Permission granted");
                Toast.makeText(this, "SMS receive permission Granted!!!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "No permission to use Receive SMS. App won't work as expected", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onRequestPermissionsResult: Permission denied");
            }
        }

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
        Context context=getApplicationContext();

        if (id == R.id.home) {
            Intent i1 = new Intent(Home.this,Home.class);
            startActivity(i1);
        } else if (id == R.id.nav_add) {
            Intent i1 = new Intent(Home.this,AddNewPumpHouseActivity.class);
            startActivity(i1);
        } else if (id == R.id.nav_setalarm) {
            Intent i1 = new Intent(Home.this,AddAlarm.class);
            startActivity(i1);
        } else if (id == R.id.nav_cancelalarm) {
            Intent i1 = new Intent(Home.this,CancelAlarm.class);
            startActivity(i1);
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
