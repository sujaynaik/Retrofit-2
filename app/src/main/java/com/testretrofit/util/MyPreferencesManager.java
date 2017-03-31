package com.testretrofit.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferencesManager {

    private static final String PREF_NAME = "TestPrefs";
    private static final String KEY_IS_LOGIN = "isLoggedIn";
    private static final String KEY_SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ID = "id";
    private static final String KEY_BRANCH_ID = "branch_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_RECORD = "record";

    public static boolean saveLoginState(Context context, boolean state) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEY_IS_LOGIN, state);
        return editor.commit();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }

    public static boolean logOutUser(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        return editor.commit();
    }

    public static boolean saveLanguage(Context context, String language) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_SELECTED_LANGUAGE, language);
        return editor.commit();
    }

    public static String getLanguage(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_SELECTED_LANGUAGE, "");
    }

    public static boolean saveToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, token);
        return editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_TOKEN, "");
    }

    public static boolean saveRecord(Context context, float record) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putFloat(KEY_RECORD, record);
        return editor.commit();
    }

    public static float getRecord(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getFloat(KEY_RECORD, 0);
    }

    public static boolean saveId(Context context, String id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_ID, id);
        return editor.commit();
    }

    public static String getId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_ID, "");
    }

    public static boolean saveBranchId(Context context, int id) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(KEY_BRANCH_ID, id);
        return editor.commit();
    }

    public static int getBranchId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt(KEY_BRANCH_ID, 0);
    }

    public static boolean saveImage(Context context, String image) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_IMAGE, image);
        return editor.commit();
    }

    public static String getImage(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_IMAGE, "");
    }

    public static boolean saveEmail(Context context, String email) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_EMAIL, email);
        return editor.commit();
    }

    public static String getEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_EMAIL, "");
    }

    public static boolean saveName(Context context, String name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_NAME, name);
        return editor.commit();
    }

    public static String getName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_NAME, "");
    }

    public static boolean saveLocation(Context context, String city) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_LOCATION, city);
        return editor.commit();
    }

    public static String getLocation(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_LOCATION, "");
    }

    public static boolean saveLatitude(Context context, double latitude) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(KEY_LATITUDE, Double.doubleToRawLongBits(latitude));
        return editor.commit();
    }

    public static double getLatitude(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return Double.longBitsToDouble(pref.getLong(KEY_LATITUDE, Double.doubleToLongBits(0)));
    }

    public static boolean saveLongitude(Context context, double longitude) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(KEY_LONGITUDE, Double.doubleToRawLongBits(longitude));
        return editor.commit();
    }

    public static double getLongitude(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return Double.longBitsToDouble(pref.getLong(KEY_LONGITUDE, Double.doubleToLongBits(0)));
    }
}
