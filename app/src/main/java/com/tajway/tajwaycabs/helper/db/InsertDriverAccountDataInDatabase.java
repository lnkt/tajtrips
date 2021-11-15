package com.tajway.tajwaycabs.helper.db;

import android.os.AsyncTask;
import android.util.Log;

import com.tajway.tajwaycabs.Activities.DriverAccounting;
import com.tajway.tajwaycabs.helper.DriverApplication;
import com.tajway.tajwaycabs.helper.callback.InsertDriverAccountDataCallback;

public class InsertDriverAccountDataInDatabase extends AsyncTask<Void, Void, Boolean> {
    public InsertDriverAccountDataCallback callback;
    String mobile, particular, cardType, credit, debit, none, totalAmount, remarks;

    public InsertDriverAccountDataInDatabase(String mobile, String particular, String cardType, String credit, String debit, String none, String totalAmount, String remarks, InsertDriverAccountDataCallback callback) {
        this.mobile = mobile;
        this.particular = particular;
        this.cardType = cardType;
        this.credit = credit;
        this.debit = debit;
        this.none = none;
        this.totalAmount = totalAmount;
        this.remarks = remarks;
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean status = false;
        Long id = DriverApplication.dbManagerQuery.insertDriverAccount(status, mobile, particular, cardType, credit, debit, none, totalAmount, remarks);
        if (id != null) {
            status = true;
        }
        return status;
    }

    @Override
    public void onPostExecute(Boolean status) {
        super.onPostExecute(status);
        Log.e("status", "" + status);
        callback.isDriverAccountDataInserted(status);
    }


}
