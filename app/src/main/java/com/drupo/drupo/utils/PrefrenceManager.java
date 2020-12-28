package com.drupo.drupo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceManager {

    private static final String PREF_NAME = "Drupo";
    private static final String LOG_IN_OUT = "session";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public PrefrenceManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public void saveLoginResponseDetails(String name, String siteURL, String siteCode) {

        editor = pref.edit();
        editor.putString("name", name);
        editor.putString("siteURL", siteURL);
        editor.putString("siteCode", siteCode);
        editor.commit();

    }

    public void saveRegisterResponseDetails(String name, String email) {

        editor = pref.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.commit();

    }

    public String fetchLoginName() {
        return pref.getString("name", "");
    }

    public String fetchSiteURL() {
        return pref.getString("siteURL", "");
    }

    public String fetchSiteCode() {
        return pref.getString("siteCode", "");
    }

    public String fetchemail() {
        return pref.getString("email", "");
    }


    public boolean validateSession() {
        if (pref.getBoolean(LOG_IN_OUT, false) == true) {
            return true;
        } else {
            return false;
        }
    }

    public void saveSessionLogin() {
        editor.putBoolean(LOG_IN_OUT, true);
        editor.commit();
    }

    public void isUserLogedOut() {
        editor = pref.edit();
        editor.putString("siteURL", "");
        editor.putString("name", "");
        editor.putString("siteCode", "");
        editor.putString("email", "");
        editor.clear();
        editor.commit();

    }
}
