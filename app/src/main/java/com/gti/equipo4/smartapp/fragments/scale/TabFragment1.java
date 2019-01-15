package com.gti.equipo4.smartapp.fragments.scale;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.gti.equipo4.smartapp.R;
import com.gti.equipo4.smartapp.activities.MainActivity;
import com.gti.equipo4.smartapp.activities.Notifications;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class TabFragment1 extends Fragment {
    FirebaseUser usuario;
    String uidUsuario;
    boolean isFirstStart = true;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_1, container, false);
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        uidUsuario = usuario.getUid();

        final View view = inflater.inflate(R.layout.tab_fragment_1, container, false);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Casa_1213") // TODO: Coger id de la casa dinamicamente
                .document("bascula")
                .collection(uidUsuario) // Documento del usuario
                .orderBy("hora", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        //Peso
                        List<Double> measures = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("peso") != null) {
                                measures.add(doc.getDouble("peso"));
                                break;
                            }
                        }
                        //Log.d(TAG, "Current measures: " + measures.toArray()[0].toString());
                        TextView lastWeightMeasure =(TextView) view.findViewById(R.id.progress_circle_text);
                        if (measures.isEmpty()){
                            lastWeightMeasure.setText("-");
                        }else {
                            lastWeightMeasure.setText(measures.toArray()[0].toString()+"kg");

                            if (isFirstStart == false){
                                Notifications newNot = new Notifications(TabFragment1.super.getContext());
                                newNot.createNotification("Nueva medida",measures.toArray()[0].toString()+"kg");
                            }
                        }

                        //Altura
                        List<Double> alturas = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("altura") != null) {
                                alturas.add(doc.getDouble("altura"));
                                break;
                            }
                        }
                        //Log.d(TAG, "Current measures: " + measures.toArray()[0].toString());
                        TextView lastAltura =(TextView) view.findViewById(R.id.textView4);
                        if (alturas.isEmpty()){
                            lastAltura.setText("-");
                        }else {
                            double alturaMetros = alturas.get(0) / 100 ;
                            lastAltura.setText(alturaMetros +"m");
                        }


                        isFirstStart = false;
                    }
                });




        return view;

    }
}