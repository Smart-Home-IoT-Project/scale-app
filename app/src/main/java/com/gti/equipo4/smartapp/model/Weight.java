package com.gti.equipo4.smartapp.model;

public class Weight {
    private long peso;
    private long altura;
    private String hora;

    public Weight() {}

    public Weight(long peso,long altura,String hora) {
        this.peso = peso;
        this.hora = hora;
        this.altura = altura;
    }


    public long getPeso() {
        return peso;
    }

    public void setPeso(long peso) {
        this.peso = peso;
    }

    public long getAltura() {
        return altura;
    }

    public void setAltura(long altura) {
        this.altura = altura;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
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