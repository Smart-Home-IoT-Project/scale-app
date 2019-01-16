package com.gti.equipo4.smartapp.fragments.scale;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gti.equipo4.smartapp.R;
import com.gti.equipo4.smartapp.activities.PreferenciasActivity;
import com.gti.equipo4.smartapp.adapters.WeigthsFirestoreUI;

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