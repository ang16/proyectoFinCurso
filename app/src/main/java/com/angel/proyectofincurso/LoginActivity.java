package com.angel.proyectofincurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {


    TextView registro;
    TextView textinicio;
    EditText edtusuario;
    EditText edtcontrasena;
    Button btninicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        registro = findViewById(R.id.textinicioregistro);
        textinicio= findViewById(R.id.textinicio);
        edtusuario= findViewById(R.id.edtusuario);
        edtcontrasena= findViewById(R.id.edtcontrasena);
        btninicio= findViewById(R.id.btninicio);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Registro.class);
                startActivity(intent);
            }
        });

        btninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compruebaUsuario();
            }
        });


    }

    public void compruebaUsuario(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("usuario",String.valueOf(edtusuario.getText())).findAll();
        final RealmResults<Usuario> contrasenas = realm.where(Usuario.class).equalTo("contrasena",String.valueOf(edtusuario.getText())).findAll();
        realm.commitTransaction();
        if(usuarios.size()==0){
            Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_LONG).show();

        }else if(contrasenas.size()==0){
            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();

        }else{

        }
    }


}

