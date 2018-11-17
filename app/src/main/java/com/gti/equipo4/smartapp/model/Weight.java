package com.gti.equipo4.smartapp.model;

import java.util.Date;

public class Weight {
    private double peso;
    private int altura;
    private Date hora;

    public Weight() {}

    public Weight(double peso,int altura,Date hora) {
        this.peso = peso;
        this.hora = hora;
        this.altura = altura;
    }


    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getHora() {
        return hora.toString();
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "peso='" + peso + '\'' +
                ", altura='" + altura + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}