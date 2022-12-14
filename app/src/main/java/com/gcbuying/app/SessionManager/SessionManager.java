package com.gcbuying.app.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.gcbuying.app.Activities.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "taxiapp";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_MOBILE = "mobile";
    public static final String AVATAR = "avatar";
    public static final String GCM_TOKEN = "gcm_token";




    public static final String KEY_WORK = "work";
    public static final String KEY_EDUCATION = "education";
    public static final String KEY_ABOUTME = "about_me";
    public static final String KEY_NICK_NAME = "nick_name";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_SMOKING = "smoking";
    public static final String KEY_DRINKING = "drinking";
    public static final String KEY_LANGUAGES = "languages";
    public static final String KEY_SPORTS = "sports";
    public static final String KEY_BOOKS = "books";
    public static final String KEY_MUSIC = "music";
    public static final String KEY_FOOD = "food";
    public static final String KEY_MOVIES = "movie";
    public static final String KEY_TRAVEL = "travel";
    public static final String KEY_GENDER = "gender";



    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String FARE_UNIT = "unit";
    public static final String LOGIN_AS = "login_as";
    public static final String USER_IDD = "user_id";
    public static final String USER_PHOTO = "user_photo";





    public static final String IS_ONLINE = "false";
    public String KEY = "key";


    public void setKEY(String k) {
        editor.putString(KEY, k);
        editor.commit();
    }

    public void setGcmToken(String gcmToken) {
        editor.putString(GCM_TOKEN, gcmToken);
        editor.commit();
    }

    public void setUnit(String unit) {
        editor.putString(FARE_UNIT, unit);
        editor.commit();
    }

    public String getUnit() {

        if (pref != null) {
            String status = pref.getString(FARE_UNIT, null);
            if (status != null) {
                return status;
            } else {
                return "";
            }

        }
        return "";
    }

    public String getGcmToken() {
        if (pref != null) {
            String gcmToken = pref.getString(GCM_TOKEN, null);
            if (gcmToken != null) {
                return gcmToken;
            } else {
                return "";
            }

        }
        return "";
    }


    public void setStatus(String staus) {
        editor.putString(IS_ONLINE, staus);
        editor.commit();
    }

    public String getStatus() {

        if (pref != null) {
            String status = pref.getString(IS_ONLINE, null);
            if (status != null) {
                return status;
            } else {
                return "";
            }

        }
        return "";
    }

    public String getKEY() {

        if (pref != null) {
            String k = pref.getString(KEY, null);
            if (k != null) {

                return k;
            } else {
                return "";
            }

        }

        return "";
    }

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String name, String email, int userid) {
        // Storing login value as TRUE




        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putInt(USER_IDD, userid);
        // commit changes
        editor.commit();
    }
    public void createLoginSession1(String name, String email, String userid) {
        // Storing login value as TRUE




        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        editor.putString(USER_IDD, userid);
        // commit changes
        editor.commit();
    }
    /**
     * Check login method wil setting user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
//    public void checkLogin() {
//        // Check login status
//        if (!this.isLoggedIn()) {
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, LoginActivity.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//        }
//
//    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(USER_IDD, pref.getString(USER_IDD, null));

        //user.put(AVATAR, pref.getString(AVATAR, null));


        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        /*i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick setting for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
