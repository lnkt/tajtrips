package com.tajway.tajwaycabs.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessonManager {

    private static SessonManager pref;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    public static final String NAME = "MY_PREFERENCES";
    public static final String Token = "token";
    public static final String DRIVER_TYPE = "driver_type";
    public static final String NotificationQty = "notification";



    public static final String Firebase_Token = "firebase_token";




    public SessonManager(Context ctx) {
        sharedPreference = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }


    public static SessonManager getInstance(Context ctx) {
        if (pref == null) {
            pref = new SessonManager(ctx);
        }
        return pref;
    }


    public void setToken(String token) {
        editor.putString(Token, token);
        editor.commit();
    }

    public void setDriverType(String type) {
        editor.putString(DRIVER_TYPE, type);
        editor.commit();
    }

    public String getDriverType() {
        return sharedPreference.getString(DRIVER_TYPE, "");
    }


    public String getToken() {
        return sharedPreference.getString(Token, "");
    }

    public void setNotificationQty(int qty) {
        editor.putInt(NotificationQty, qty);
        editor.commit();
    }

    public int getNotificationQty() {
        return sharedPreference.getInt(NotificationQty,0);
    }

    public static String getFirebase_Token() {
        return Firebase_Token;
    }

    public void setFirebase_Token(String firebase_Token) {
        editor.putString(Token, firebase_Token);
        editor.commit();
    }





    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }


    public String getString(String key, String defValue) {
        return sharedPreference.getString(key, defValue);
    }

}
