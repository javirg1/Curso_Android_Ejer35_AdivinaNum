package com.example.ejer35_adivinanum;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = "Ejer35_SplashActivity";
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Reproduzco la musica:
        //Reproduzco sonido de acierto:
        mPlayer = MediaPlayer.create(SplashActivity.this, R.raw.splash_musica);
        mPlayer.start();

        /*
        //Para mantener la versión inicial con botón:

        //Enlazar con los controles del layout (interfaz del usuario)
        Button btnComenzar = findViewById(R.id.btnComenzar);
        //Registro el evento click sobre el botón:
        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Aqui es donde damos respueta al evento:
                //Saltar a la pantalla Main (La del juego)
                Intent nueva_pantalla = new Intent(SplashActivity.this, MainActivity.class);
                //Para ejecutar el salto de pantalla:
                startActivity(nueva_pantalla);
                //Además que esta pantalla ya no vuelva a salir:
                finish();
            }
        });
         */

        //Vamos a esperar 3 segundos y automaticamente que salte a la pantalla MainActivity:
        Handler retardo = new Handler();
        retardo.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Saltar a la pantalla Main (La del juego)
                Intent nueva_pantalla = new Intent(SplashActivity.this, MainActivity.class);
                //Para ejecutar el salto de pantalla:
                startActivity(nueva_pantalla);
                //Y además cierro la pantalla actual, para que no se quede en espera:
                finish();
            }
        },2500);

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            //Paro la musica, y por si hubiera un error, lo meto try - catch
            mPlayer.stop();
        } catch (IllegalStateException e) {
        }
    }
}
