package com.freakydevs.kolkatalocal.connection;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.annotation.NonNull;

import com.freakydevs.kolkatalocal.interfaces.MainActivityInterface;
import com.freakydevs.kolkatalocal.resources.MyDataBaseHandler;
import com.freakydevs.kolkatalocal.utils.Logger;
import com.freakydevs.kolkatalocal.utils.SharedPreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by PURUSHOTAM on 11/23/2017.
 */

public class Updater {

    public FirebaseStorage storage = FirebaseStorage.getInstance();
    boolean isProgress;
    int whichUpdate;
    JSONObject jsonObject;
    private Context context;
    private MainActivityInterface mainActivityInterface;

    public Updater(Context context, boolean isProgress, int whichUpdate) {
        this.context = context;
        this.isProgress = isProgress;
        this.whichUpdate = whichUpdate;
        mainActivityInterface = (MainActivityInterface) context;
        if (isProgress) {

            mainActivityInterface.showUpdateLoader();
        }
        getJSON();
    }

    private void getJSON() {

        StorageReference dbRef = storage.getReference().child("kolkata_sub/update.json");
        dbRef.getBytes(1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {


                try {
                    InputStream inputStream = new ByteArrayInputStream(bytes);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    StringBuilder responseStrBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        responseStrBuilder.append(line);
                    }
                    inputStream.close();

                    Logger.d(responseStrBuilder.toString());

                    jsonObject = new JSONObject(responseStrBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                checkForUpdate(jsonObject);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                if (isProgress) {
                    mainActivityInterface.hideUpdateLoader();
                }
            }
        });
    }

    private void checkForUpdate(JSONObject jsonObject) {

        boolean isDBupdateav, isAppUpdateav;
        if (isProgress) {
            mainActivityInterface.hideUpdateLoader();
        }
        try {
            int appVersion = jsonObject.getInt("a");
            int newdbVersion = jsonObject.getInt("d");
            int isRemovedAd = jsonObject.getInt("r");
            if (isRemovedAd == 0) {
                SharedPreferenceManager.setRemoveAd(context.getApplicationContext(), false);
            }

            int olddbVersion = new MyDataBaseHandler(context).getDbVersion();
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int oldappVersion = pInfo.versionCode;

            isDBupdateav = olddbVersion < newdbVersion;
            isAppUpdateav = oldappVersion < appVersion;

            if (whichUpdate == 1 && isAppUpdateav) {
                mainActivityInterface.showAppUpdate();
            } else if (whichUpdate == 1) {
                mainActivityInterface.noAppUpdate();
            }

            if (whichUpdate == 2 && isDBupdateav) {
                mainActivityInterface.showDatabaseUpdate();
            } else if (whichUpdate == 2) {
                mainActivityInterface.noDbUpdate();
            }

            if (whichUpdate == 0 && isAppUpdateav && isDBupdateav) {
                mainActivityInterface.showAppUpdate();
                mainActivityInterface.showDatabaseUpdate();
            } else if (whichUpdate == 0 && isAppUpdateav) {
                mainActivityInterface.showAppUpdate();
            } else if (whichUpdate == 0 && isDBupdateav) {
                mainActivityInterface.showDatabaseUpdate();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
