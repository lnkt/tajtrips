package com.tajway.tajwaycabs.helper;

import android.app.Application;

import com.tajway.tajwaycabs.retrofitModel.InfoStatusModel;

import java.util.List;

public class DriverApplication extends Application {
    public static DriverApplication mInstance;

    //our app database object
    // public static AppDatabase db;
    public static DBManagerQuery dbManagerQuery;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        dbManagerQuery = new DBManagerQuery(this);
        dbManagerQuery.open();

    }

    private List<InfoStatusModel.Data> list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

  /*  public static DriverApplication getInstance() {
        if (mInstance == null) {
            mInstance = new DriverApplication();
        }
        return mInstance;
    }*/
}
