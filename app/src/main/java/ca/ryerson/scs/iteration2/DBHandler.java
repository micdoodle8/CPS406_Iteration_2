//package com.example.eugene.testing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MEM";

    String[] creators = new String[]{ CREATE_ATTENDEE_TABLE,
            CREATE_CUSTOMER_TABLE,
            CREATE_MEETING_TABLE,
            CREATE_PAYMENT_TABLE,
            CREATE_HALL_TABLE,
            CREATE_COACH_TABLE};

    String[] deletors = new String[]{DELETE_ATTENDEE_TABLE,
            DELETE_CUSTOMER_TABLE,
            DELETE_MEETING_TABLE,
            DELETE_PAYMENT_TABLE,
            DELETE_HALL_TABLE,
            DELETE_COACH_TABLE};

    private static final String CREATE_ATTENDEE_TABLE =
            "CREATE TABLE " + MEMContract.Attendee.TABLE_NAME + " (" +
                MEMContract.Attendee._ID + " INTEGER PRIMARY KEY," +
                MEMContract.Attendee.CUSTOMER_ID + " INTEGER," +
                MEMContract.Attendee.MEETING_ID + " INTEGER," +
                MEMContract.Attendee.DISCOUNT_PERCENT + " REAL)";

    private static final String DELETE_ATTENDEE_TABLE =
            "DROP TABLE IF EXISTS " + MEMContract.Attendee.TABLE_NAME;

    private static final String CREATE_CUSTOMER_TABLE =
            "CREATE TABLE " + MEMContract.Customer.TABLE_NAME + " (" +
                    MEMContract.Customer._ID + " INTEGER PRIMARY KEY," +
                    MEMContract.Customer.NAME + " TEXT," +
                    MEMContract.Customer.CONSECUTIVE_PAYMENT + " TEXT)";

    private static final String DELETE_CUSTOMER_TABLE =
            "DROP TABLE IF EXISTS " + MEMContract.Customer.TABLE_NAME;

    private static final String CREATE_MEETING_TABLE =
            "CREATE TABLE " + MEMContract.Meeting.TABLE_NAME + " (" +
                    MEMContract.Meeting._ID + " INTEGER PRIMARY KEY," +
                    MEMContract.Meeting.DATE + " TEXT," +
                    MEMContract.Meeting.ORGANIZER + " TEXT," +
                    MEMContract.Meeting.RATE + " REAL," +
                    MEMContract.Meeting.HALL_ID + " INTEGER)";

    private static final String DELETE_MEETING_TABLE =
            "DROP TABLE IF EXISTS " + MEMContract.Meeting.TABLE_NAME;

    private static final String CREATE_PAYMENT_TABLE =
            "CREATE TABLE " + MEMContract.Payment.TABLE_NAME + " (" +
                    MEMContract.Payment._ID + " INTEGER PRIMARY KEY," +
                    MEMContract.Payment.ATTENDEE_ID + " INTEGER," +
                    MEMContract.Payment.DATE + " TEXT," +
                    MEMContract.Payment.AMOUNT + " REAL)";

    private static final String DELETE_PAYMENT_TABLE =
            "DROP TABLE IF EXISTS " + MEMContract.Payment.TABLE_NAME;

    private static final String CREATE_HALL_TABLE =
            "CREATE TABLE " + MEMContract.Payment.TABLE_NAME + " (" +
                    MEMContract.Hall._ID + " INTEGER PRIMARY KEY," +
                    MEMContract.Hall.NAME + " TEXT," +
                    MEMContract.Hall.RATE + " REAL)";

    private static final String DELETE_HALL_TABLE =
            "DROP TABLE IF EXISTS " + MEMContract.Hall.TABLE_NAME;

    private static final String CREATE_COACH_TABLE =
            "CREATE TABLE " + MEMContract.Coach.TABLE_NAME + " (" +
                    MEMContract.Coach._ID + " INTEGER PRIMARY KEY," +
                    MEMContract.Coach.NAME + " TEXT," +
                    MEMContract.Coach.RATE + " REAL)";

    private static final String DELETE_COACH_TABLE =
            "DROP TABLE IF EXISTS " + MEMContract.Coach.TABLE_NAME;


    public DBHandler(Context contex) {
        super(contex, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(String sql : creators) db.execSQL(sql);

        populateDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String sql : deletors) db.execSQL(sql);

        onCreate(db);
    }


    public void populateDummyData(SQLiteDatabase db){

        db.execSQL("INSERT INTO CUSTOMER (NAME, CONSECUTIVE_PAYMENT) VALUES('John Smith', '01-04-2018') ");
        db.execSQL("INSERT INTO CUSTOMER (NAME, CONSECUTIVE_PAYMENT) VALUES('Xi Jin Ping', '20-03-2018') ");

        db.execSQL("INSERT INTO HALL (NAME, RATE) VALUES('Main practice hall', 12) ");
        db.execSQL("INSERT INTO HALL (NAME, RATE) VALUES('East wing gym', 11) ");


        db.execSQL("INSERT INTO MEETING (DATE, ORGANIZER, RATE, HALL_ID) VALUES('01-04-2018', 'Coach', 12, 1) ");
        db.execSQL("INSERT INTO MEETING (DATE, ORGANIZER, RATE, HALL_ID) VALUES('08-04-2018', 'Coach', 10, 2) ");


        db.execSQL("INSERT INTO ATTENDEE (CUSTOMER_ID, MEETING_ID, DISCOUNT_PERCENT) VALUES(1, 1, 0) ");
        db.execSQL("INSERT INTO ATTENDEE (CUSTOMER_ID, MEETING_ID, DISCOUNT_PERCENT) VALUES(2, 2, 10) ");


        db.execSQL("INSERT INTO PAYMENT (ATTENDEE_ID, DATE, AMOUNT) VALUES(1, '01-04-2018', 24) ");


        db.execSQL("INSERT INTO COACH (ATTENDEE_ID, DATE, AMOUNT) VALUES('Tom Coughlin', 14) ");
        db.execSQL("INSERT INTO COACH (NAME, RATE) VALUES('Bobby Bowden', 13) ");

    }


    public void executeSQL(String sql){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }



    public void addNewCustomer(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMContract.Customer.NAME, name);
        values.put(MEMContract.Customer.CONSECUTIVE_PAYMENT, "");

        db.insert(MEMContract.Customer.TABLE_NAME, null, values);
        db.close();
    }


    public boolean updateCustomer(int id, String name, String consecutivePayment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMContract.Customer.NAME, name);
        values.put(MEMContract.Customer.CONSECUTIVE_PAYMENT, consecutivePayment);

        return db.update(MEMContract.Customer.TABLE_NAME, values, MEMContract.Customer._ID + "=" + id, null) > 0;
    }


    public boolean deleteCustomer(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(MEMContract.Customer.TABLE_NAME, MEMContract.Customer._ID + "=" + id, null) > 0;
    }


}