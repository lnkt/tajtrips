package com.tajway.tajwaycabs.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManagerQuery {


    private Context context;
    private SQLiteDatabase database;

    public DBManagerQuery(Context c) {
        context = c;
    }

    public void open() throws SQLException {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insertDriverAccount(Boolean status, String mobile, String particular, String cardType, String credit, String debit, String none, String totalAmount, String remarks) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.IS_UPLOADED, status);
        contentValue.put(DatabaseHelper.MOBILE, mobile);
        contentValue.put(DatabaseHelper.PARTICULAR, particular);
        contentValue.put(DatabaseHelper.CARD_TYPE, cardType);
        contentValue.put(DatabaseHelper.CREDIT, credit);
        contentValue.put(DatabaseHelper.DEBIT, debit);
        contentValue.put(DatabaseHelper.NONE, none);
        contentValue.put(DatabaseHelper.TOTAL_AMOUNT, totalAmount);
        contentValue.put(DatabaseHelper.REMARKS, remarks);
        contentValue.put(DatabaseHelper.KEY_CREATED_AT, DatabaseHelper.getDateTime());
        return database.insert(DatabaseHelper.TABLE_DRIVER_ACCOUNT, null, contentValue);
    }


    public long insertViewDriverAccount(String accountId, String bookingId, String source, String destination, String date, String remarks, String totalAmount, String cardType) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.KEY_DA_ID, accountId);
        contentValue.put(DatabaseHelper.KEY_BOOKING_ID, bookingId);
        contentValue.put(DatabaseHelper.KEY_SOURCE, source);
        contentValue.put(DatabaseHelper.KEY_DESTINATION, destination);
        contentValue.put(DatabaseHelper.KEY_DATE, date);
        contentValue.put(DatabaseHelper.REMARKS, remarks);
        contentValue.put(DatabaseHelper.TOTAL_AMOUNT, totalAmount);
        contentValue.put(DatabaseHelper.CARD_TYPE, cardType);
        contentValue.put(DatabaseHelper.KEY_CREATED_AT, DatabaseHelper.getDateTime());
        return database.insert(DatabaseHelper.TABLE_DRIVER_ACCOUNT_VIEW, null, contentValue);
    }


    /*  public Cursor updateDriverAccount(String id, String mobile) {
          String[] columns = new String[]{
                  DatabaseHelper.KEY_ID,
                  DatabaseHelper.MOBILE,
                  DatabaseHelper.PARTICULAR,
                  DatabaseHelper.CARD_TYPE,
                  DatabaseHelper.CREDIT,
                  DatabaseHelper.DEBIT,
                  DatabaseHelper.NONE,
                  DatabaseHelper.REMARKS,
                  DatabaseHelper.KEY_CREATED_AT};
          String whereClause = DatabaseHelper.KEY_ID + "=? AND " + DatabaseHelper.MOBILE + "=? ";
          String[] whereArgs = new String[]{id, mobile};
          Cursor csr;
          csr = database.query(DatabaseHelper.TABLE_DRIVER_ACCOUNT, columns, whereClause, whereArgs, null, null, null);
          if (csr.moveToFirst()) {
          }
          return csr;
      }
  */
    @SuppressLint("Recycle")
    public Cursor getDataFetch() {
        String[] columns = new String[]{
                DatabaseHelper.KEY_ID,
                DatabaseHelper.MOBILE,
                DatabaseHelper.PARTICULAR,
                DatabaseHelper.CARD_TYPE,
                DatabaseHelper.CREDIT,
                DatabaseHelper.DEBIT,
                DatabaseHelper.NONE,
                DatabaseHelper.TOTAL_AMOUNT,
                DatabaseHelper.REMARKS,
                DatabaseHelper.KEY_CREATED_AT};
        Cursor csr = database.query(DatabaseHelper.TABLE_DRIVER_ACCOUNT, columns, null, null, null, null, null);
        return csr;
    }

    public Cursor getViewAllAccountFetch() {
        String[] columns = new String[]{
                DatabaseHelper.KEY_ID,
                DatabaseHelper.KEY_DA_ID,
                DatabaseHelper.KEY_BOOKING_ID,
                DatabaseHelper.KEY_SOURCE,
                DatabaseHelper.KEY_DESTINATION,
                DatabaseHelper.KEY_DATE,
                DatabaseHelper.REMARKS,
                DatabaseHelper.TOTAL_AMOUNT,
                DatabaseHelper.CARD_TYPE,
                DatabaseHelper.KEY_CREATED_AT};
        Cursor csr = database.query(DatabaseHelper.TABLE_DRIVER_ACCOUNT_VIEW, columns, null, null, null, null, null);
        return csr;
    }

    public void removeAllLocalDriverAccount() {
        database.delete(DatabaseHelper.TABLE_DRIVER_ACCOUNT, null, null);
        //database.delete(tablename, null,null);
    }


}
