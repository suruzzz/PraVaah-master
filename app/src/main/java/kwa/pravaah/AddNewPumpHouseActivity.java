package kwa.pravaah;

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

public class AddNewPumpHouseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String PhoneNo,Name;
    private static final int CONTACT_PICK = 1;
    EditText Ph;
    TextView tv_name;
    Button add;
    DbManager db;
    private String POWERON="ON";
    private String PUMPOFF="OFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_pump_house);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db=new DbManager(AddNewPumpHouseActivity.this);
        Ph=findViewById(R.id.ed_PhoneNo);
        tv_name=findViewById(R.id.tv_Name);
        add=findViewById(R.id.bt_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNo=Ph.getText().toString();
                if(!db.getnumber(PhoneNo)) {

                    db.insertUserDetails(PhoneNo, Name, POWERON, PUMPOFF, "", "", "", "",
                            "", "", "", "");

                    Toast.makeText(AddNewPumpHouseActivity.this, "Successfully Registered..!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(AddNewPumpHouseActivity.this, "Number already registered..!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            Intent i1 = new Intent(AddNewPumpHouseActivity.this,Home.class);
            startActivity(i1);
        } else if (id == R.id.nav_add) {
            Intent i1 = new Intent(AddNewPumpHouseActivity.this,AddNewPumpHouseActivity.class);
            startActivity(i1);
        } else if (id == R.id.nav_setalarm) {
            Intent i1 = new Intent(AddNewPumpHouseActivity.this,AddAlarm.class);
            startActivity(i1);
        } else if (id == R.id.nav_cancelalarm) {
            Intent i1 = new Intent(AddNewPumpHouseActivity.this,CancelAlarm.class);
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
            tv_name.setText(Name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
