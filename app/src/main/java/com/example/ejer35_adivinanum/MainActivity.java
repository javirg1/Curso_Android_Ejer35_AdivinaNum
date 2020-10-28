package com.example.ejer35_adivinanum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "Ejer35_MainActivity";
    //Defino dos constantes para los limites maximo y minimo del num a adivinar:
    private final int LIMITE_MIN = 1;
    private final int LIMITE_MAX = 100;
    //Variables para controlar max y min durante una partida concreta:
    private int partida_min = LIMITE_MIN;
    private int partida_max = LIMITE_MAX;
    private int num_aleatorio;
    private int num_intentos;
    //Necesito dos variables para llevar la cuenta de puntos de una partida, y el record:
    private int record_puntos;

    private TextView tvIntentos;
    private Button btnJugar;
    private EditText etNumJugador;
    private TextView tvResultado;
    private TextView tvInstrucciones;
    private ImageView ivResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Esta sería la forma por código de bloquear la pantalla en vertical (lo comento y dejo lo mismo en el manifest)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_main);

        //Enlazo con los controles del interfaz:
        btnJugar = findViewById(R.id.btnJugar);
        Button btnNuevaPartida = findViewById(R.id.btnNuevaPartida);
        etNumJugador = findViewById(R.id.etNumJugador);
        tvResultado = findViewById(R.id.tvResultado);
        tvIntentos = findViewById(R.id.tvIntentos);
        tvInstrucciones = findViewById(R.id.tvInstrucciones);
        TextView tvRecord = findViewById(R.id.tvRecord);
        ivResultado = findViewById(R.id.ivResultado);

        //Cuando arranca la app, leo de las preferencias a ver si tengo guardado un record:
        SharedPreferences taquilla = MainActivity.this.getSharedPreferences("Datos", 0);
        record_puntos = taquilla.getInt("record_puntos",0);
        tvRecord.setText(String.format(getString(R.string.msg_record),record_puntos));

        //Inicializo las variables para comenzar una partida (y además esta función refresca la pantalla):
        nuevaPartida();

        //Control del evento click sobre botón jugar:
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Aqui dentro es donde doy la respuesta al evento:
                //Leer de la caja de texto el número escrito por el jugador:
                String contenido;
                int num_jugador;
                try {
                    contenido = etNumJugador.getText().toString();
                    //Paso el numero del jugador de formato String a entero:
                    num_jugador = Integer.valueOf(contenido);
                } catch (Exception ex) {
                    //Aquí salta cuando en el bloque de arriba (try) se produce un error de ejecución:
                    Log.e(TAG, "btnJugar - onClick - catch: " + ex.getMessage());
                    //Mensaje al usuario indicandole el fallo (en forma de mensaje emergente):
                    Toast.makeText(MainActivity.this, R.string.msg_error_numero, Toast.LENGTH_SHORT).show();
                    return;
                }
                //Si llego hasta aquí, ya tengo un número, comprobamos si está en los límites:
                if (num_jugador>=partida_min && num_jugador<=partida_max) {
                    //Tengo número válido, ha hecho un intento:
                    num_intentos++; //Es lo mismo que num_intentos = num_intentos + 1;
                    //Refrescar en pantalla los intentos:
                    tvIntentos.setText(getString(R.string.msg_intentos) + num_intentos);
                    //tvIntentos.setText(""+num_intentos); //Otra forma válida


                    //Comprobar is ha acertado:
                    if (num_jugador == num_aleatorio) {
                        //Hacemos el botón jugar invisible, para obligarle a hacer click en nueva partida:
                        btnJugar.setVisibility(View.INVISIBLE);
                        //Obtengo los puntos de la partida:
                        int partida_puntos = partida_max - partida_min;
                        //Mostrar en pantalla:
                        String msg_acierto = String.format(getString(R.string.msg_acierto),partida_puntos);
                        tvResultado.setText(msg_acierto);
                        //Compruebo si tengo un nuevo record:
                        if (partida_puntos > record_puntos) {
                            //Tengo nuevo record, actualizo la variable y también la pantalla:
                            record_puntos = partida_puntos;
                            tvRecord.setText(String.format(getString(R.string.msg_record),record_puntos));
                            //Además lo guardo en las preferencias para que persista:
                            SharedPreferences taquilla = MainActivity.this.getSharedPreferences("Datos", 0);
                            // 0 - for private mode
                            SharedPreferences.Editor editor = taquilla.edit();
                            //Ahora ya guardo variable - valor:
                            editor.putInt("record_puntos",record_puntos);
                            //Aplicamos los cambios:
                            editor.apply(); //Equivalente a editor.commit();
                        }
                        //Cambio el icono por el de exito:
                        ivResultado.setImageResource(R.drawable.ic_exito);
                        //Reproduzco sonido de acierto:
                        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.sonido_exito);
                        mPlayer.start();
                    } else {
                        tvResultado.setText(R.string.msg_fallo);
                        //Compruebo si ha cambiado el minimo o el máximo:
                        if (num_aleatorio < num_jugador) {
                            //Cambia el máximo:
                            partida_max = num_jugador;
                        } else {
                            //Ya no hay alternativa, cambia el mínimo
                            partida_min = num_jugador;
                        }
                        //Actualizar en pantalla los nuevos limites:
                        refrescarLayout();
                        //Cambio el icono por el de fallo:
                        ivResultado.setImageResource(R.drawable.ic_fallo);
                        //Reproduzco sonido de fallo:
                        MediaPlayer mPlayer = MediaPlayer.create(MainActivity.this, R.raw.sonido_fallo);
                        mPlayer.start();
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.msg_numero_no_valido, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Cobntrolo el evento click sobre el botón nueva partida:
        btnNuevaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaPartida();
            }
        });
    }


    //Aquí acaba el onCreate, a partir de aqui puedo dfinir mas funciones de la clase:
    private void nuevaPartida() {
        //Aqui ya puedo programar lo que hay que hacer cuando el usuario pulsa el botón nueva partida:
        Random aleatorio = new Random();
        num_aleatorio = aleatorio.nextInt(LIMITE_MAX) + LIMITE_MIN;
        Log.e(TAG,"Num secreto: "+num_aleatorio);
        //Inicializo los valores min y max de la partida:
        partida_max = LIMITE_MAX;
        partida_min = LIMITE_MIN;
        //Llamo a la función que refresca la pantalla:
        refrescarLayout();

        //Pongo intentos a 0 y actualizo la pantalla:
        num_intentos = 0;
        tvIntentos.setText(getString(R.string.msg_intentos) + num_intentos);

        //Limpio el contenido de la caja del jugador y el resultado:
        etNumJugador.setText("");
        tvResultado.setText("");

        //Vuelvo a hacer visible el botón de jugar:
        btnJugar.setVisibility(View.VISIBLE);
        //Pongo el icono por defecto:
        ivResultado.setImageResource(R.drawable.ic_adivina);

    }

    //Esta función sirve para refrescar los limites en el layout:
    private void refrescarLayout() {
        //Refresco en pantalla los limites:
        String texto = String.format(getString(R.string.instrucciones),partida_min,partida_max);
        tvInstrucciones.setText(texto);
    }

}