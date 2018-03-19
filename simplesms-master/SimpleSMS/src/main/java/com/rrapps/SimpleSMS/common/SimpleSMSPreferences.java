package com.rrapps.SimpleSMS.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.rrapps.SimpleSMS.enums.SimpleSMSPreference;

public abstract class SimpleSMSPreferences {

    private static SharedPreferences sPrefs;

    public static void init(Context context) {
        sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean getBoolean(SimpleSMSPreference preference) {
        return sPrefs.getBoolean(preference.getKey(), (boolean) preference.getDefaultValue());
    }

    public static void setBoolean(SimpleSMSPreference preference, boolean newValue) {
        sPrefs.edit().putBoolean(preference.getKey(), newValue).apply();
    }

    public static int getInt(SimpleSMSPreference preference) {
        return sPrefs.getInt(preference.getKey(), (int) preference.getDefaultValue());
    }

    public static void setInt(SimpleSMSPreference preference, int newValue) {
        sPrefs.edit().putInt(preference.getKey(), newValue).apply();
    }

    public static long getLong(SimpleSMSPreference preference) {
        return sPrefs.getLong(preference.getKey(), (int) preference.getDefaultValue());
    }

    public static void setLong(SimpleSMSPreference preference, long newValue) {
        sPrefs.edit().putLong(preference.getKey(), newValue).apply();
    }

    public static String getString(SimpleSMSPreference preference) {
        return sPrefs.getString(preference.getKey(), (String) preference.getDefaultValue());
    }

    public static void setString(SimpleSMSPreference preference, String newValue) {
        sPrefs.edit().putString(preference.getKey(), newValue).apply();
    }
}