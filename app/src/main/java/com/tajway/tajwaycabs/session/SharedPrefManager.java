package com.tajway.tajwaycabs.session;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

/*
 * A Singleton for managing your SharedPreferences.
 *
 *
 * IMPORTANT: The class is not thread safe. It should work fine in most
 * circumstances since the write and read operations are fast. However
 * if you call edit for bulk updates and do not commit your changes
 * there is a possibility of data loss if a background thread has modified
 * preferences at the same time.
 *
 * Usage:
 *
 * -> GET
 * int sampleInt = SharedPrefManager.getInstance(context).getInt(SharedPrefManager.Key.FIRST_TIME, defaultValue);
 *
 * -> SET
 * SharedPrefManager.getInstance(context).set(SharedPrefManager.Key.FIRST_TIME, value);
 *
 * If SharedPrefManager.getInstance(Context) has been called once, you can
 * simple use SharedPrefManager.getInstance() to save some precious line space.
 */

public class SharedPrefManager {

    private static final String SETTINGS_NAME = "houseQuarter12Book";
    private static SharedPrefManager sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;

    private SharedPrefManager(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefManager getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new SharedPrefManager(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    public static SharedPrefManager getInstance() {
        if (sSharedPrefs != null) {
            return sSharedPrefs;
        }

        //Option 1:
        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");

        //Option 2:
        // Alternatively, you can create a new instance here
        // with something like this:
        // getInstance(MyCustomApplication.getAppContext());
    }

    public void set(Key key, String val) {
        doEdit();
        mEditor.putString(key.name(), val);
        doCommit();
    }

    public void set(Key key, int val) {
        doEdit();
        mEditor.putInt(key.name(), val);
        doCommit();
    }

    public void set(Key key, boolean val) {
        doEdit();
        mEditor.putBoolean(key.name(), val);
        doCommit();
    }

    public void set(Key key, float val) {
        doEdit();
        mEditor.putFloat(key.name(), val);
        doCommit();
    }

    /**
     * Convenience method for storing doubles.
     * <p/>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to store.
     * @param val The new value for the preference.
     */
    public void set(Key key, double val) {
        doEdit();
        mEditor.putString(key.name(), String.valueOf(val));
        doCommit();
    }

    public void set(Key key, long val) {
        doEdit();
        mEditor.putLong(key.name(), val);
        doCommit();
    }

    public String get(Key key, String defaultValue) {
        return mPref.getString(key.name(), defaultValue);
    }


    public int get(Key key, int defaultValue) {
        return mPref.getInt(key.name(), defaultValue);
    }


    public long get(Key key, long defaultValue) {
        return mPref.getLong(key.name(), defaultValue);
    }


    public float get(Key key, float defaultValue) {
        return mPref.getFloat(key.name(), defaultValue);
    }


    /**
     * Convenience method for retrieving doubles.
     * <p/>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so they have to
     * cast to and from String.
     *
     * @param key The enum of the preference to fetch.
     */
    public double get(Key key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key.name(), String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public boolean get(Key key, boolean defaultValue) {
        return mPref.getBoolean(key.name(), defaultValue);
    }


    /**
     * Remove keys from SharedPreferences.
     *
     * @param keys The enum of the key(s) to be removed.
     */
    public void remove(Key... keys) {
        doEdit();
        for (Key key : keys) {
            mEditor.remove(key.name());
        }
        doCommit();
    }

    /**
     * Remove all keys from SharedPreferences.
     */
    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    public void commit() {
        mBulkUpdate = false;
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

    /**
     * Enum representing your setting names or key for your setting.
     * define your all shared pref keys here, in order to use it
     */
    public enum Key {
        /* Recommended naming convention:
         * ints, floats, doubles, longs:
         * SAMPLE_NUM or SAMPLE_COUNT or SAMPLE_INT, SAMPLE_LONG etc.
         *
         * boolean: IS_SAMPLE, HAS_SAMPLE, CONTAINS_SAMPLE
         *
         * String: SAMPLE_KEY, SAMPLE_STR or just SAMPLE
         */
        TOTAL_AMOUNT

    }

}
