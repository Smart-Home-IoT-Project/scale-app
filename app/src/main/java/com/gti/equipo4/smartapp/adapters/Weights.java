package com.gti.equipo4.smartapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gti.equipo4.smartapp.R;
import com.gti.equipo4.smartapp.model.Weight;

import androidx.recyclerview.widget.RecyclerView;

public class Weights extends
        RecyclerView.Adapter<Weights.ViewHolder> {
    protected com.gti.equipo4.smartapp.interfaces.Weights lugares;
    // Lista de lugares a mostrar
    protected LayoutInflater inflador; // Crea Layouts a partir del XML
    protected Context contexto;

    // Lo necesitamos para el inflador
    public Weights(Context contexto, com.gti.equipo4.smartapp.interfaces.Weights lugares) {
        this.contexto = contexto;
        this.lugares = lugares;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.weightText);
        }
    }
    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_lista, parent, false);
        return new ViewHolder(v);
    }
    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Weight lugar = lugares.elemento(posicion);
        personalizaVista(holder, lugar);
    }
// Personalizamos un ViewHolder a partir de un lugar
public static void personalizaVista(ViewHolder holder, Weight lugar) {
    holder.nombre.setText(lugar.getNombre());

}
    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return lugares.tamanyo();
    }
}