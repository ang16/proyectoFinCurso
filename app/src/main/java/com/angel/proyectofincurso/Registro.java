package com.angel.proyectofincurso;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Registro extends AppCompatActivity {

    TextView registro;
    EditText usuario;
    EditText contrasena;
    EditText  email;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        registro = findViewById(R.id.textregistroinicio);
        usuario=findViewById(R.id.edtcontrasenaregistro);
        email=findViewById(R.id.edtemailregistro);
        contrasena=findViewById(R.id.edtcontrasenaregistro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        insertaUsuario();


    }

    public void insertaUsuario() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Usuario usuario = new Usuario();
        usuario.setUsuario("aaa");
        usuario.setEmail("aaa");
        usuario.setContrasena(new String(Hex.encodeHex(DigestUtils.md5("aaa"))));
        usuario.setAvatar("aaa");
        Usuario user = realm.copyToRealm(usuario);
        realm.commitTransaction();
    }
}

