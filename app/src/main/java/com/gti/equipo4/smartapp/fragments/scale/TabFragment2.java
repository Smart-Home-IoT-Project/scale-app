package com.gti.equipo4.smartapp.fragments.scale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.gti.equipo4.smartapp.R;
import com.gti.equipo4.smartapp.adapters.WeigthsFirestoreUI;
import com.gti.equipo4.smartapp.model.Weight;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TabFragment2 extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    public static WeigthsFirestoreUI adaptador2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.tab_fragment_2, container, false);

        final View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);


        Query query = FirebaseFirestore.getInstance()
                .collection("Bascula")
                .limit(50);
        FirestoreRecyclerOptions<Weight> opciones = new FirestoreRecyclerOptions
                .Builder<Weight>().setQuery(query, Weight.class).build();
        adaptador2 = new WeigthsFirestoreUI(opciones);

        recyclerView.setAdapter(adaptador2);
        adaptador2.startListening();


        return view;
    }
}