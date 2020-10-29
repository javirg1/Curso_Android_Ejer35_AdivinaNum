package com.example.ejer35_adivinanum;

import android.util.Log;

import java.util.Random;

public class JAdivina {
    //Atributos
    private final String TAG="Ejer35_JAdivina";
    private final int LIMITE_MIN = 1;
    private final int LIMITE_MAX = 100;
    //Variables para controlar max y min durante una partida concreta:
    private int partida_min = LIMITE_MIN;
    private int partida_max = LIMITE_MAX;
    private int num_aleatorio;
    private int num_intentos;

    //Necesito dos variables para llevar la cuenta de puntos de
    // una partida, y el record:
    private int partida_puntos;
    private int record_puntos;

    //Propiedades
    public int getIntentos() {
        return num_intentos;
    }

    public int getPartidaPuntos() {
        return partida_puntos;
    }

    public int getRecordPuntos() {
        return record_puntos;
    }

    public int getPartidaMin() {
        return partida_min;
    }

    public int getPartidaMax() {
        return partida_max;
    }

    public void setRecordPuntos(int valor) {
        record_puntos = valor;
    }


    //Funciones
    public void iniciarJuego() {
        //Aqui ya puedo programar lo que hay que hacer cuando el usuario pulsa el botón nueva partida:
        Random aleatorio = new Random();
        num_aleatorio = aleatorio.nextInt(LIMITE_MAX) + LIMITE_MIN;
        Log.e(TAG,"Num secreto: "+num_aleatorio);
        //Inicializo los valores min y max de la partida:
        partida_max = LIMITE_MAX;
        partida_min = LIMITE_MIN;
        //Pongo intentos a 0
        num_intentos = 0;
    }

    public boolean estaEnLosLimites(int num_jugador) {
        if (num_jugador>=partida_min && num_jugador<=partida_max) {
            //Tengo número válido, ha hecho un intento:
            num_intentos++; //Es lo mismo que num_intentos = num_intentos + 1;
            return true;
        } else {
            return false;
        }
    }

    public boolean haAcertado(int num_jugador) {
        if (num_jugador == num_aleatorio) {
            //Obtengo los puntos de la partida:
            partida_puntos = partida_max - partida_min;
            return true;
        } else {
            //Compruebo si ha cambiado el minimo o el máximo:
            if (num_aleatorio < num_jugador) {
                //Cambia el máximo:
                partida_max = num_jugador;
            } else {
                //Ya no hay alternativa, cambia el mínimo
                partida_min = num_jugador;
            }
            return false;
        }
    }

    public boolean hayNuevoRecord() {
        if (partida_puntos > record_puntos) {
            record_puntos = partida_puntos;
            return true;
        } else {
            return false;
        }
    }
}
