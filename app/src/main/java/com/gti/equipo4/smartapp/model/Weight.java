package com.gti.equipo4.smartapp.model;

public class Weight {
    private String nombre;

    public Weight(String nombre) {
        this.nombre = nombre;
    }
    public Weight() {

    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Datos{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}