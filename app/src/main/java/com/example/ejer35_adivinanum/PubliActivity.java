package com.example.ejer35_adivinanum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PubliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publi);

        Button btnGualapop = findViewById(R.id.btn_Gualapop);

        //Creamos una variable de tipo string
        String url="https://www.google.com/";

        btnGualapop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creamos el objeto web de la clase Uri y le pasamos la variable url
                Uri web = Uri.parse(url);
                // Saltamos a la página web
                Intent publi = new Intent(Intent.ACTION_VIEW, web);
                startActivity(publi);
                finish();

            }
        });
    }
}