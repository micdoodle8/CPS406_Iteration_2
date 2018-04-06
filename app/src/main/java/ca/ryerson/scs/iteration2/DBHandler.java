package ca.ryerson.scs.iteration2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MEM";
    private static DBHandler instance = null;

    String[] creators = new String[]{ CREATE_ATTENDEE_TABLE,
            CREATE_CUSTOMER_TABLE,
            CREATE_MEETING_TABLE,
            CREATE_PAYMENT_TABLE,
            CREATE_HALL_TABLE,
            CREATE_COACH_TABLE,
            CREATE_USER_TABLE};

    String[] deletors = new String[]{DELETE_ATTENDEE_TABLE,
            DELETE_CUSTOMER_TABLE,
            DELETE_MEETING_TABLE,
            DELETE_PAYMENT_TABLE,
            DELETE_HALL_TABLE,
            DELETE_COACH_TABLE,
            DELETE_USER_TABLE};

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
                    MEMContract.Customer.EMAIL + " TEXT," +
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
            "CREATE TABLE " + MEMContract.Hall.TABLE_NAME + " (" +
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

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + MEMContract.User.TABLE_NAME + " (" +
                    MEMContract.User._ID + " INTEGER PRIMARY KEY," +
                    MEMContract.User.EMAIL + " TEXT," +
                    MEMContract.User.PASSWORD + " TEXT," +
                    MEMContract.User.ROLE + " TEXT," +
                    MEMContract.User.ASSOCIATED_ID + " INTEGER)";

    private static final String DELETE_USER_TABLE =
            "DROP TABLE IF EXISTS " + MEMContract.User.TABLE_NAME;

    public DBHandler(Context contex) { super(contex, DATABASE_NAME, null, DATABASE_VERSION); }

    /**
     * Get the handle instance
     *
     * @param context The app context. Try getApplicationContext() from activity instance
     */
    public static DBHandler getInstance(Context context)
    {
        if (instance == null)
        {
            // Create handler
            context.deleteDatabase(DATABASE_NAME);
            instance = new DBHandler(context);
            instance.populateDummyData();
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(String sql : creators) db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String sql : deletors) db.execSQL(sql);
        onCreate(db);
    }


    public void populateDummyData(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("INSERT INTO CUSTOMER (NAME, EMAIL, CONSECUTIVE_PAYMENT) VALUES('John Smith', 'jsmith@mail.com', '2018-01-04 00:00:00') ");
        db.execSQL("INSERT INTO CUSTOMER (NAME, EMAIL, CONSECUTIVE_PAYMENT) VALUES('Xi Jin Ping', 'xi@mail.com', '2018-03-20 00:00:00') ");
        db.execSQL("INSERT INTO CUSTOMER (NAME, EMAIL, CONSECUTIVE_PAYMENT) VALUES('Peter Griffin', 'peter@mail.com', '2018-03-15 00:00:00') ");

        db.execSQL("INSERT INTO HALL (NAME, RATE) VALUES('Main practice hall', 12) ");
        db.execSQL("INSERT INTO HALL (NAME, RATE) VALUES('East wing gym', 11) ");


        db.execSQL("INSERT INTO MEETING (DATE, ORGANIZER, RATE, HALL_ID) VALUES('2018-04-01 00:00:00', 'Coach', 12, 1) ");
        db.execSQL("INSERT INTO MEETING (DATE, ORGANIZER, RATE, HALL_ID) VALUES('2018-04-08 00:00:00', 'Coach', 10, 2) ");


        db.execSQL("INSERT INTO ATTENDEE (CUSTOMER_ID, MEETING_ID, DISCOUNT_PERCENT) VALUES(1, 1, 0) ");
        db.execSQL("INSERT INTO ATTENDEE (CUSTOMER_ID, MEETING_ID, DISCOUNT_PERCENT) VALUES(2, 2, 10) ");


        db.execSQL("INSERT INTO PAYMENT (ATTENDEE_ID, DATE, AMOUNT) VALUES(1, '2018-01-04 00:00:00', 24) ");


        db.execSQL("INSERT INTO COACH (NAME, RATE) VALUES('Tom Coughlin', 14) ");
        db.execSQL("INSERT INTO COACH (NAME, RATE) VALUES('Bobby Bowden', 13) ");


        db.execSQL("INSERT INTO USER (EMAIL, PASSWORD, ROLE, ASSOCIATED_ID) VALUES('jsmith@mail.com', 'password', 'CUSTOMER', 1) ");
        db.execSQL("INSERT INTO USER (EMAIL, PASSWORD, ROLE, ASSOCIATED_ID) VALUES('peter@mail.com', 'hello', 'CUSTOMER', 2) ");
        db.execSQL("INSERT INTO USER (EMAIL, PASSWORD, ROLE, ASSOCIATED_ID) VALUES('tom@mail.com', 'ohwee', 'COACH', 1) ");

    }

    public void getCustomer( int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT NAME FROM CUSTOMER WHERE _ID = " + id, null);
        cursor.moveToFirst();
    }


    public String authenticate(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER WHERE EMAIL = ? AND PASSWORD = ? ", new String[] {email, password});
        cursor.moveToFirst();

        return cursor.getString(3);
    }

    //Inserts new user into the database
    public void addNewUser(String email, String password, String role, int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMContract.User.EMAIL, email);
        values.put(MEMContract.User.PASSWORD, password);
        values.put(MEMContract.User.ROLE, role);
        values.put(MEMContract.User.ASSOCIATED_ID, id);

        db.insert(MEMContract.User.TABLE_NAME, null, values);
        db.close();
    }

    ///Executes the provided query directly
    public void executeSQL(String sql){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    //Returns a list of all customer names
    public ArrayList<String> getCustomers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> names = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT NAME FROM CUSTOMER", null);
        while (cursor.moveToNext()) names.add(cursor.getString(0));

        return names;
    }

    //Returns a list of all coach names
    public ArrayList<String> getCoaches() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> names = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT NAME FROM COACH", null);
        while (cursor.moveToNext()) names.add(cursor.getString(0));

        return names;
    }

    //Returns a list of customer names and emails who owe money
    public ArrayList<String> CustomerDebts(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> info = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT NAME, EMAIL FROM CUSTOMER WHERE CONSECUTIVE_PAYMENT < 0", null);
        while (cursor.moveToNext()) info.add(cursor.getString(0) + " " + cursor.getString(1));

        return info;
    }

    //Returns the money owed
    public String MoneyOwed(String EarlyDate, String LaterDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String OwedMoney = "";

        Cursor cursor = db.rawQuery("SELECT SUM(COACH.rate + HALLS.Rate) as AmountOwed FROM MEETINGS JOIN COACH ON MEETINGS.Organizer = COACH.ID JOIN HALLS ON MEETINGS.Hall_ID WHERE date BETWEEN " + EarlyDate + " AND " + LaterDate, null);
        cursor.moveToFirst();
        OwedMoney = cursor.getString(0);

        return OwedMoney;
    }
    //Returns the money earned
    public String MoneyEarned(String EarlyDate, String LaterDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String EarnedMoney = "";

        Cursor cursor = db.rawQuery("SELECT SUM(AMOUNT) as AmountEarned FROM PAYMENT WHERE date BETWEEN " + EarlyDate + " AND " + LaterDate, null);
        cursor.moveToFirst();
        EarnedMoney = cursor.getString(0);

        return EarnedMoney;
    }







    //Inserts new customer into the database
    public void addNewCustomer(String name, String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMContract.Customer.NAME, name);
        values.put(MEMContract.Customer.EMAIL, email);
        values.put(MEMContract.Customer.CONSECUTIVE_PAYMENT, "");

        db.insert(MEMContract.Customer.TABLE_NAME, null, values);
        db.close();
    }

    //Inserts customer info in the database
    public boolean updateCustomer(int id, String name, String email, String consecutivePayment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMContract.Customer.NAME, name);
        values.put(MEMContract.Customer.EMAIL, email);
        values.put(MEMContract.Customer.CONSECUTIVE_PAYMENT, consecutivePayment);

        return db.update(MEMContract.Customer.TABLE_NAME, values, MEMContract.Customer._ID + "=" + id, null) > 0;
    }

    //Deletes customer from the database by id
    public boolean deleteCustomer(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(MEMContract.Customer.TABLE_NAME, MEMContract.Customer._ID + "=" + id, null) > 0;
    }

    //Inserts new Coach into the database
    public void addNewCoach(String name, double rate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMContract.Coach.NAME, name);
        values.put(MEMContract.Coach.RATE, rate);

        db.insert(MEMContract.Coach.TABLE_NAME, null, values);
        db.close();
    }

    //Inserts new Hall into the database
    public void addNewHall(String name, double rate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMContract.Hall.NAME, name);
        values.put(MEMContract.Hall.RATE, rate);

        db.insert(MEMContract.Hall.TABLE_NAME, null, values);
        db.close();
    }

    //Inserts new meeting into the database
    public void MeetingCreation(int coach_ID, int hall_ID, String date, int rate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEMContract.Meeting.DATE, date);
        values.put(MEMContract.Meeting.HALL_ID, hall_ID);
        values.put(MEMContract.Meeting.ORGANIZER, coach_ID);
        values.put(MEMContract.Meeting.RATE, rate);

        db.insert(MEMContract.Attendee.TABLE_NAME, null, values);
        db.close();
    }

    //Inserts new Attendee info in and checks if a discount is needed
    public void MeetingSignUp(int customer_ID, int meeting_ID){
        int discount = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        Cursor cursor = db.rawQuery("SELECT CONSECUTIVE_PAYMENT FROM CUSTOMER WHERE _ID = " + customer_ID, null);
        cursor.moveToFirst();
        if (cursor.getString(0) == "3") { discount = 10;}

        values.put(MEMContract.Attendee.CUSTOMER_ID, customer_ID);
        values.put(MEMContract.Attendee.MEETING_ID, meeting_ID);
        values.put(MEMContract.Attendee.DISCOUNT_PERCENT, discount);

        db.insert(MEMContract.Attendee.TABLE_NAME, null, values);
        db.close();
    }

    //When a customer pays it updates their last consecutive payment time
    //It also checks the rate for the meeting they are paying for, and if they had a discount
    //If so it changes the total payment based on the discount then multiplies the final number
    public boolean CustomerPaid(int id, String date, int attendee_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        boolean Updated = false;
        values.put(MEMContract.Customer.CONSECUTIVE_PAYMENT, date);

        Updated = db.update(MEMContract.Customer.TABLE_NAME, values, MEMContract.Customer._ID + "=" + id, null) > 0;

        double Payment = 0;
        int meetingID;
        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT MEETING_ID FROM ATTENDEE WHERE _ID = " + attendee_ID, null);
        cursor.moveToFirst();
        meetingID = cursor.getInt(0);

        cursor = db.rawQuery("SELECT RATE FROM MEETINGS WHERE _ID = " + meetingID, null);
        cursor.moveToFirst();
        Payment = cursor.getInt(0);

        db = this.getWritableDatabase();
        cursor = db.rawQuery("SELECT DISCOUNT_PERCENT FROM ATTENDEE WHERE _ID = " + attendee_ID, null);
        cursor.moveToFirst();
        Payment = Payment * (1 - (cursor.getDouble(0)/100));

        db = this.getWritableDatabase();
        values = new ContentValues();

        values.put(MEMContract.Payment.AMOUNT, Payment);
        values.put(MEMContract.Payment.ATTENDEE_ID, attendee_ID);
        values.put(MEMContract.Payment.DATE, date);

        db.insert(MEMContract.Payment.TABLE_NAME, null, values);
        db.close();

        return Updated;
    }
}
