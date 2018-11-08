package com.gti.equipo4.smartapp.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.gti.equipo4.smartapp.R;

public class PreferenciasFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
