package com.tajway.tajwaycabs.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "TajTrip.db";


    public static final String TABLE_DRIVER_ACCOUNT = "driver_account";
    public static final String TABLE_DRIVER_ACCOUNT_VIEW = "view_all_account";


    public static final String KEY_ID = "id";
    public static final String MOBILE = "mobile";
    public static final String IS_UPLOADED = "is_uploaded";
    public static final String PARTICULAR = "particular";
    public static final String CARD_TYPE = "card_type";
    public static final String CREDIT = "credit";
    public static final String DEBIT = "debit";
    public static final String NONE = "none";
    public static final String AMOUNT = "amount";
    public static final String TOTAL_AMOUNT = "total_amount";
    public static final String REMARKS = "remarks";
    public static final String KEY_CREATED_AT = "created_date_time";
    public static final String KEY_UPDATED_AT = "updated_date_time";

    public static final String KEY_DA_ID = "da_id";
    public static final String KEY_BOOKING_ID = "booking_id";
    public static final String KEY_SOURCE = "Source";
    public static final String KEY_DESTINATION = "Destination";
    public static final String KEY_DATE = "date";


    ///////////////////////////////////////////////////////////////


    private static final String CREATE_DRIVER_ACCOUNT = "CREATE TABLE "
            + TABLE_DRIVER_ACCOUNT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + IS_UPLOADED + " INTEGER DEFAULT 0,"
            + MOBILE + " TEXT,"
            + PARTICULAR + " TEXT,"
            + CARD_TYPE + " TEXT,"
            + CREDIT + " TEXT,"
            + DEBIT + " TEXT,"
            + NONE + " TEXT,"
            + TOTAL_AMOUNT + " TEXT,"
            + REMARKS + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_DRIVER_ACCOUNT_VIEW = "CREATE TABLE "
            + TABLE_DRIVER_ACCOUNT_VIEW + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_DA_ID + " TEXT,"
            + KEY_BOOKING_ID + " TEXT,"
            + KEY_SOURCE + " TEXT,"
            + KEY_DESTINATION + " TEXT,"
            + KEY_DATE + " TEXT,"
            + REMARKS + " TEXT,"
            + TOTAL_AMOUNT + " TEXT,"
            + CARD_TYPE + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DRIVER_ACCOUNT);
        db.execSQL(CREATE_DRIVER_ACCOUNT_VIEW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRIVER_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRIVER_ACCOUNT_VIEW);
        // create new tables
        onCreate(db);
    }


    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}
