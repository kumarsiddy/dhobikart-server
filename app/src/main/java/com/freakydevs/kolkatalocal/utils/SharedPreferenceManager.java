package com.freakydevs.kolkatalocal.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

import static com.freakydevs.kolkatalocal.utils.Constants.CURRENTTIMEINMS;
import static com.freakydevs.kolkatalocal.utils.Constants.FROMCODE;
import static com.freakydevs.kolkatalocal.utils.Constants.FROMSTATION;
import static com.freakydevs.kolkatalocal.utils.Constants.ISFIRSTTIME;
import static com.freakydevs.kolkatalocal.utils.Constants.REMOVE_AD_ON;
import static com.freakydevs.kolkatalocal.utils.Constants.REMOVE_AD_TIME;
import static com.freakydevs.kolkatalocal.utils.Constants.SHARED_PREFS_FILE;
import static com.freakydevs.kolkatalocal.utils.Constants.SHARED_PREFS_FILE_HISTORY;
import static com.freakydevs.kolkatalocal.utils.Constants.SHARED_PREFS_FILE_HISTORY_PNR;
import static com.freakydevs.kolkatalocal.utils.Constants.SHARED_PREFS_FILE_HISTORY_TRAINROUTE;
import static com.freakydevs.kolkatalocal.utils.Constants.TOCODE;
import static com.freakydevs.kolkatalocal.utils.Constants.TOSTATION;

/**
 * Created by PURUSHOTAM on 11/1/2017.
 */

public class SharedPreferenceManager {

    private static SharedPreferences historySharedPrefs;
    private static SharedPreferences.Editor historySharedPrefsEditor;
    private static SharedPreferences appSharedPrefs;
    private static SharedPreferences.Editor appSharedPrefsEditor;
    private static SharedPreferences trainROuteSharedPrefs;
    private static SharedPreferences.Editor trainRouteSharedPrefsEditor;
    private static SharedPreferences pnrSharedPrefs;
    private static SharedPreferences.Editor pnrSharedPrefsEditor;


    public static SharedPreferences.Editor getSearchHistoryEditor(Context context) {

        if (historySharedPrefsEditor != null) {
            return historySharedPrefsEditor;
        } else {
            historySharedPrefsEditor = SharedPreferenceManager.getSearchHistorySharedPreference(context).edit();
            return historySharedPrefsEditor;
        }
    }

    public static SharedPreferences getSearchHistorySharedPreference(Context context) {
        if (historySharedPrefs != null) {
            return historySharedPrefs;
        } else {
            historySharedPrefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE_HISTORY, Context.MODE_PRIVATE);
            return historySharedPrefs;
        }
    }

    public static SharedPreferences.Editor getAppEditor(Context context) {
        if (appSharedPrefsEditor != null) {
            return appSharedPrefsEditor;
        } else {
            appSharedPrefsEditor = SharedPreferenceManager.getAppSharedPrefs(context).edit();
            return appSharedPrefsEditor;
        }
    }

    public static SharedPreferences getAppSharedPrefs(Context context) {
        if (appSharedPrefs != null) {
            return appSharedPrefs;
        } else {
            appSharedPrefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
            return appSharedPrefs;
        }
    }

    public static SharedPreferences getTrainROuteSharedPrefs(Context context) {
        if (trainROuteSharedPrefs != null) {
            return trainROuteSharedPrefs;
        } else {
            trainROuteSharedPrefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE_HISTORY_TRAINROUTE, Context.MODE_PRIVATE);
            return trainROuteSharedPrefs;
        }
    }

    public static SharedPreferences.Editor getTrainRouteSharedPrefsEditor(Context context) {
        if (trainRouteSharedPrefsEditor != null) {
            return trainRouteSharedPrefsEditor;
        } else {
            trainRouteSharedPrefsEditor = getTrainROuteSharedPrefs(context).edit();
            return trainRouteSharedPrefsEditor;
        }
    }

    public static SharedPreferences getPnrSharedPrefs(Context context) {
        if (pnrSharedPrefs != null) {
            return pnrSharedPrefs;
        } else {
            pnrSharedPrefs = context.getApplicationContext().getSharedPreferences(SHARED_PREFS_FILE_HISTORY_PNR, Context.MODE_PRIVATE);
            return pnrSharedPrefs;
        }
    }

    public static SharedPreferences.Editor getPnrSharedPrefsEditor(Context context) {
        if (pnrSharedPrefsEditor != null) {
            return pnrSharedPrefsEditor;
        } else {
            pnrSharedPrefsEditor = getPnrSharedPrefs(context).edit();
            return pnrSharedPrefsEditor;
        }
    }

    public static boolean saveAppFromTo(Context context, String fromCode, String fromStation, String toCode, String toStation) {
        try {
            SharedPreferenceManager.getAppEditor(context)
                    .putString(FROMCODE, fromCode)
                    .putString(FROMSTATION, fromStation)
                    .putString(TOCODE, toCode)
                    .putString(TOSTATION, toStation)
                    .apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setData() {

    }

    public static String[] getAppFromTo(Context context) {

        String[] data = new String[4];

        try {

            SharedPreferenceManager.getAppSharedPrefs(context);
            data[0] = appSharedPrefs.getString(FROMCODE, "");
            data[1] = appSharedPrefs.getString(FROMSTATION, "");
            data[2] = appSharedPrefs.getString(TOCODE, "");
            data[3] = appSharedPrefs.getString(TOSTATION, "");

            Logger.d(data[0] + data[1] + data[2] + data[3]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean isFirstTime(Context context) {
        return getAppSharedPrefs(context).getBoolean(ISFIRSTTIME, true);
    }

    public static void setFirstTime(Context context) {
        getAppEditor(context).putBoolean(ISFIRSTTIME, false).apply();
    }

    public static boolean isupdateTime(Context context) {
        Calendar cal = Calendar.getInstance();
        long now = cal.getTimeInMillis();
        long lastCheckedMillis = getAppSharedPrefs(context).getLong(CURRENTTIMEINMS, 0);
        long diffMillis = now - lastCheckedMillis;
        return diffMillis >= (3600000 * 12);
    }

    public static void setCurrentTime(Context context) {
        getAppEditor(context).putLong(CURRENTTIMEINMS, Calendar.getInstance().getTimeInMillis()).apply();
    }

    public static boolean isShowAd(Context context) {
        Calendar cal = Calendar.getInstance();
        long now = cal.getTimeInMillis();
        long lastCheckedMillis = getAppSharedPrefs(context).getLong(REMOVE_AD_TIME, 0);
        long diffMillis = now - lastCheckedMillis;
        return diffMillis >= (3600000 * 24 * 3);
    }

    public static void setAdTime(Context context) {
        getAppEditor(context).putLong(REMOVE_AD_TIME, Calendar.getInstance().getTimeInMillis()).apply();
    }

    public static boolean isRemoveAd(Context context) {
        return getAppSharedPrefs(context).getBoolean(REMOVE_AD_ON, true);
    }

    public static void setRemoveAd(Context context, boolean isRemoveAd) {
        getAppEditor(context).putBoolean(REMOVE_AD_ON, isRemoveAd).apply();
    }

}
