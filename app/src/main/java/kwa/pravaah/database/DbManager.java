package kwa.pravaah.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "KWA.db";
    public static final String TABLE_SMS = "sms_table";
    public static final String ID="id";
    public static final String MOBILE_NO = "phone";
    public static final String NAME = "name";
    public static final String POWER = "power";
    public static final String PUMP = "pump";
    public static final String PENDING_INTENT_ON="alarmidon1";
    public static final String PENDING_INTENT_OFF="alarmidoff1";
    public static final String TIME_ON = "time_on1";
    public static final String TIME_OFF = "time_off1";
    public static final String PENDING_INTENT_ON2="alarmidon2";
    public static final String PENDING_INTENT_OFF2="alarmidoff2";
    public static final String TIME_ON2 = "time_on2";
    public static final String TIME_OFF2 = "time_off2";

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_SMS + "(" + ID+" integer primary key,"
                + MOBILE_NO + " text,"
                + NAME + " text,"
                + POWER + " text,"
                + PUMP + " text,"
                + PENDING_INTENT_ON +" text,"
                + PENDING_INTENT_OFF +" text,"
                + TIME_ON +" text ,"
                + TIME_OFF +" text ,"
                + PENDING_INTENT_ON2 +" text,"
                + PENDING_INTENT_OFF2 +" text,"
                + TIME_ON2 +" text,"
                + TIME_OFF2 +" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int numOfRows() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int numOfRows = (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_SMS);
        return numOfRows;
    }
    public boolean insertUserDetails(String no,String name,String power, String pump,
                                     String pend_on_1,String pend_off_1,String time_on_1,String time_off1,
                                     String pend_on_2,String pend_off_2,String time_on_2,String time_off2) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MOBILE_NO, no);
        contentValues.put(NAME, name);
        contentValues.put(POWER, power);
        contentValues.put(PUMP, pump);
        contentValues.put(PENDING_INTENT_ON, pend_on_1);
        contentValues.put(PENDING_INTENT_OFF, pend_off_1);
        contentValues.put(TIME_ON, time_on_1);
        contentValues.put(TIME_OFF, time_off1);
        contentValues.put(PENDING_INTENT_ON2, pend_on_2);
        contentValues.put(PENDING_INTENT_OFF2, pend_off_2);
        contentValues.put(TIME_ON2, time_on_2);
        contentValues.put(TIME_OFF2, time_off2);


        db.insert(TABLE_SMS, null, contentValues);
        return true;
    }

    public boolean updateUserDetails(String no,String power, String pump) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(POWER, power);
        contentValues.put(PUMP, pump);


        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);
        return true;
    }
    public boolean getnumber(String no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_SMS + " where " + MOBILE_NO + " = " + "'" + no + "'" , null);
        if (res.getCount()==0)
        {
            return false;
        }else
        {
            return true;
        }

    }

    public Cursor getPowerStatus(String no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + POWER + " from " + TABLE_SMS + " where " + MOBILE_NO + " = " + "'" + no + "'" , null);
        return res;
    }


    public Cursor getPendingIntent(String no) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + PENDING_INTENT_ON +"," +PENDING_INTENT_OFF + " from " + TABLE_SMS + " where " + MOBILE_NO + " = " + "'" + no + "'" , null);
        return res;
    }



    public void addPendingIntent_ON(String no, String pending) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PENDING_INTENT_ON, pending);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void addPendingIntent_OFF(String no, String pending) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PENDING_INTENT_OFF, pending);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void addTime_ON(String no, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME_ON, time);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void addTime_OFF(String no, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME_OFF, time);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);
    }

    public void addPendingIntent_ON2(String no, String pending) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PENDING_INTENT_ON2, pending);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void addPendingIntent_OFF2(String no, String pending) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PENDING_INTENT_OFF2, pending);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void addTime_ON2(String no, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME_ON2, time);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);

    }

    public void addTime_OFF2(String no, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME_OFF2, time);
        db.update(TABLE_SMS,contentValues,MOBILE_NO+"="+no,null);
    }

    public void deleteRow(String number)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SMS+ " WHERE "+MOBILE_NO+"='"+number+"'");
        db.close();
    }


    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+NAME +","  +POWER +"," +PUMP +"," +TIME_ON +","
                +TIME_OFF +"," +TIME_ON2 +"," +TIME_OFF2 + " from " + TABLE_SMS , null);
        return res;
    }

}
