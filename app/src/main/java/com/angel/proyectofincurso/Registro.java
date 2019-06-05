package com.angel.proyectofincurso;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class Registro extends AppCompatActivity {

    private static final int SELECT_IMAGE = 100;
    TextView registro;
    EditText usuario;
    EditText contrasena;
    EditText email;
    Button btnseleccionimagen;
    ImageView ivAvatar;
    String imagepath;
    Button btninicioregistro;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        registro = findViewById(R.id.textregistroinicio);
        usuario = findViewById(R.id.edtcontrasenaregistro);
        email = findViewById(R.id.edtemailregistro);
        contrasena = findViewById(R.id.edtcontrasenaregistro);
        btnseleccionimagen = findViewById(R.id.btnseleccionimagen);
        ivAvatar = findViewById(R.id.ivAvatar);
        btninicioregistro = findViewById(R.id.btninicioregistro);

        btnseleccionimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);


                startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), SELECT_IMAGE);
            }
        });

        btninicioregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertaUsuario(usuario, contrasena, email, imagepath);


            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    public void insertaUsuario(EditText nombreUsuario, EditText contrasena, EditText email, String ivAvatar) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("usuario", String.valueOf(nombreUsuario.getText())).findAll();
        if (usuarios.size() == 0) {
            Number puppies = realm.where(Usuario.class).max("id");
            long longId;
            final RealmResults<Usuario> puppies2 = realm.where(Usuario.class).findAll();
            Usuario usuario = new Usuario();
            usuario.setUsuario(nombreUsuario.getText().toString());
            usuario.setEmail(String.valueOf(email.getText()));
            usuario.setContrasena(new String(Hex.encodeHex(DigestUtils.md5(String.valueOf(contrasena.getText())))));
            usuario.setAvatar(ivAvatar);
            if(puppies==null){
                longId= 1;
            }else{
                longId=puppies.longValue()+1;
            }
            usuario.setId(longId);
            realm.copyToRealm(usuario);
            realm.commitTransaction();
            Toast.makeText(Registro.this, "Uusario creado", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_LONG).show();
            usuarios.deleteFromRealm(0);
            realm.commitTransaction();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    imagepath = selectedImage.toString();
                    ivAvatar.setImageURI(selectedImage);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

