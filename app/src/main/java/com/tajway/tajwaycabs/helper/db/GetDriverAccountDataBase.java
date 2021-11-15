package com.tajway.tajwaycabs.helper.db;

import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.tajway.tajwaycabs.helper.DriverApplication;
import com.tajway.tajwaycabs.helper.callback.GetDriverAccountDataCallback;

public class GetDriverAccountDataBase extends AsyncTask<Void, Void, Cursor> {
    public GetDriverAccountDataCallback callback;


    public GetDriverAccountDataBase(GetDriverAccountDataCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        return DriverApplication.dbManagerQuery.getDataFetch();
    }

    @Override
    public void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        Log.e("Count", "" + cursor);
        callback.onDriverAccountDataFetch(cursor);
    }


}
