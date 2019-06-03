package com.angel.proyectofincurso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {


    TextView registro;
    TextView textinicio;
    EditText edtusuario;
    EditText edtcontrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        registro = findViewById(R.id.textinicioregistro);
        textinicio= findViewById(R.id.textinicio);
        edtusuario= findViewById(R.id.edtusuario);
        edtcontrasena= findViewById(R.id.edtcontrasena);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Registro.class);
                startActivity(intent);
            }
        });


    }

    public void compruebaUsuario(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<Usuario> puppies = realm.where(Usuario.class).equalTo("usuario",String.valueOf(edtusuario.getText())).findAll();
        realm.commitTransaction();
        if(puppies.size()>0){
            Toast.makeText(this, "Usuario icorrecto", Toast.LENGTH_LONG).show();
            //textinicio.setText(puppies);
        }/*else if(){

        }*/
    }


}

