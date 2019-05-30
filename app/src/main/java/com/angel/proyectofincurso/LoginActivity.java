package com.angel.proyectofincurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    TextView registro;
    TextView textinicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        registro = findViewById(R.id.textinicioregistro);
        textinicio= findViewById(R.id.textinicio);

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
        final RealmResults<Usuario> puppies = realm.where(Usuario.class).findAll();
        realm.commitTransaction();
        if(puppies.size()>0){
            Toast.makeText(this, "Usuario icorrecto", Toast.LENGTH_LONG).show();
            //textinicio.setText(puppies);
        }/*else if(){

        }*/
    }
}

