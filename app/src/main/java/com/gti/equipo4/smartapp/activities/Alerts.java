package com.gti.equipo4.smartapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gti.equipo4.smartapp.R;

public class Alerts {

    // Preferences
    SharedPreferences sharedPreferences;

    // Max and min values
    double maxPesoValue;
    double minPesoValue;


    // Notification manager
    Notifications newNot;



    public Alerts (Activity activity, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        PreferenceManager.setDefaultValues(context, R.xml.preferencias, false);

        newNot = new Notifications(context);



    }

    public void checkWeigh(double value){
        // Read preferences
        maxPesoValue = getPreferences("maxPesoValue");
        minPesoValue = getPreferences("minPesoValue");


        // Check
        if (value < minPesoValue){
            newNot.createNotification("Alerta peso", "Pesas "+value+"kg, estas por debajo de tu peso ("+minPesoValue+"kg)");
        }

        if(value > maxPesoValue){
            newNot.createNotification("Alerta peso", "Pesas "+value+"kg, estas por arriba de tu peso ("+maxPesoValue+"kg)");
        }
    }


    private int getPreferences(String key){
        String value = sharedPreferences.getString(key, "0");

        int intValue = Integer.parseInt(value);
        //Log.d("paco",value);

        return intValue;
    }
}

