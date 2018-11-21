package com.gti.equipo4.smartapp.fragments.scale;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.gti.equipo4.smartapp.R;
import com.gti.equipo4.smartapp.adapters.WeigthsFirestoreUI;
import com.gti.equipo4.smartapp.model.Weight;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class TabFragment2 extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    public static WeigthsFirestoreUI adaptador2;
    ArrayList<Entry> entries = new ArrayList<>();
    Context context = super.getContext();


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
                .orderBy("hora", Query.Direction.DESCENDING)
                .limit(50);
        FirestoreRecyclerOptions<Weight> opciones = new FirestoreRecyclerOptions
                .Builder<Weight>().setQuery(query, Weight.class).build();
        adaptador2 = new WeigthsFirestoreUI(opciones);

        recyclerView.setAdapter(adaptador2);
        adaptador2.startListening();

        final LineChart chart = (LineChart) view.findViewById(R.id.charto);

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

                        entries = new ArrayList<>();
                        int i = 0;
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("peso") != null) {
                                double measure = doc.getDouble("peso");
                                entries.add(new Entry(i, Float.valueOf(String.valueOf(measure))));
                                i++;
                            }
                        }
                        //Log.d(TAG, "Current measures: " + measures.toArray()[0].toString());


                        LineDataSet dataSet = new LineDataSet(entries, "Customized values");
                        //dataSet.setColor(ContextCompat.getColor(context, R.color.black));
                        //dataSet.setValueTextColor(ContextCompat.getColor(context, R.color.white));

                        //****
                        // Controlling X axis
                        XAxis xAxis = chart.getXAxis();
                        // Set the xAxis position to bottom. Default is top
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        //Customizing x axis value
                        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr"};

                        IAxisValueFormatter formatter = new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return months[(int) value];
                            }
                        };
                        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                        xAxis.setValueFormatter(formatter);

                        //***
                        // Controlling right side of y axis
                        YAxis yAxisRight = chart.getAxisRight();
                        yAxisRight.setEnabled(false);

                        //***
                        // Controlling left side of y axis
                        YAxis yAxisLeft = chart.getAxisLeft();
                        yAxisLeft.setGranularity(1f);

                        chart.getAxisLeft().setDrawGridLines(false);
                        chart.getXAxis().setDrawGridLines(false);

                        // Setting Data
                        LineData data = new LineData(dataSet);
                        chart.setData(data);
                        chart.animateX(2500);
                        //refresh
                        chart.invalidate();

                    }
                });






        return view;
    }
}