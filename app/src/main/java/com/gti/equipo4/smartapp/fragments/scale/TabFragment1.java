package com.gti.equipo4.smartapp.fragments.scale;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.gti.equipo4.smartapp.R;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class TabFragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_1, container, false);

        final View view = inflater.inflate(R.layout.tab_fragment_1, container, false);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Bascula")
                .orderBy("hora", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<Double> measures = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("peso") != null) {
                                measures.add(doc.getDouble("peso"));
                                break;
                            }
                        }
                        //Log.d(TAG, "Current measures: " + measures.toArray()[0].toString());
                        TextView lastWeightMeasure =(TextView) view.findViewById(R.id.progress_circle_text);
                        lastWeightMeasure.setText(measures.toArray()[0].toString()+"kg");
                    }
                });




        return view;

    }
}