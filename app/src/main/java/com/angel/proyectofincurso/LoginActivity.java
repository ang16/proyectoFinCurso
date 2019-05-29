package com.angel.proyectofincurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        onBackPressed();

        registro=findViewById(R.id.textinicioregistro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this, Registro.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        moveTaskToBack(false);
    }
}
