package com.gti.equipo4.smartapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.gti.equipo4.smartapp.R;
import com.gti.equipo4.smartapp.adapters.Weights;
import com.gti.equipo4.smartapp.adapters.WeigthsFirestoreUI;
import com.gti.equipo4.smartapp.fragments.scale.SampleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {
    public boolean isFirstStart;
    private RecyclerView recyclerView;
    public Weights adaptador;
    public static WeigthsFirestoreUI adaptador2;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    final Intent i = new Intent(MainActivity.this, IntroActivity.class);

                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            startActivity(i);
                        }
                    });

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SampleFragmentPagerAdapter adapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(), this);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }
    

    /*            new AlertDialog.Builder(this)
                    .setTitle("Cerrar sesión")
                    .setMessage("¿Quiere cerrar la sesión?")
                    .setNegativeButton("cancelar", null)
                    .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_NEW_TASK
                                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }})
                    .show();*/

    /*@Override public void onDestroy() {
        super.onDestroy();
        adaptador2.stopListening();
    }*/
}
