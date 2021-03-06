package com.gti.equipo4.smartapp.fragments.scale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gti.equipo4.smartapp.R;
import com.gti.equipo4.smartapp.activities.ConfigScaleActivity;
import com.gti.equipo4.smartapp.activities.LoginActivity;
import com.gti.equipo4.smartapp.activities.MainActivity;
import com.gti.equipo4.smartapp.activities.PreferenciasActivity;
import com.gti.equipo4.smartapp.adapters.WeigthsFirestoreUI;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class TabFragment3 extends Fragment {
    private RecyclerView.LayoutManager layoutManager;
    public static WeigthsFirestoreUI adaptador2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Mostrar perfil de usuario
        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        TextView nombre =(TextView) view.findViewById(R.id.nombre);
        nombre.setText(usuario.getDisplayName());

        TextView correo = (TextView) view.findViewById(R.id.correo);
        correo.setText(usuario.getEmail());

        CardView card_view = (CardView) view.findViewById(R.id.cardView); // creating a CardView and assigning a value.
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
                launchPreferences();
            }
        });

        CardView card_view5 = (CardView) view.findViewById(R.id.cardView5); // creating a CardView and assigning a value.
        card_view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Quiere cerrar la sesión?")
                        .setNegativeButton("cancelar", null)
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                AuthUI.getInstance().signOut(getActivity()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent i = new Intent(getActivity(), LoginActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        getActivity().finish();
                                    }
                                });
                            }})
                        .show();

            }
        });

        CardView card_view4 = (CardView) view.findViewById(R.id.cardView4); // creating a CardView and assigning a value.
        card_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do whatever you want to do on click (to launch any fragment or activity you need to put intent here.)
                Intent i = new Intent(getActivity(), ConfigScaleActivity.class);
                startActivity(i);
            }
        });

        // Inicialización Volley (Hacer solo una vez en Singleton o Applicaction)
        RequestQueue colaPeticiones = Volley.newRequestQueue(super.getContext());
        ImageLoader lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });

        //Foto de usuario
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            NetworkImageView fotoUsuario = (NetworkImageView) view.findViewById(R.id.imagen);
            fotoUsuario.setImageUrl(urlImagen.toString(), lectorImagenes);
        }

        //return inflater.inflate(R.layout.tab_fragment_3, container, false);
        return view;

    }

    public void launchPreferences (){
        Intent intent = new Intent(getActivity(), PreferenciasActivity.class);
        startActivity(intent);
    }
}