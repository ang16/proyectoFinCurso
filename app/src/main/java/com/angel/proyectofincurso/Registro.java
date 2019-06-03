package com.angel.proyectofincurso;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        usuario=findViewById(R.id.edtcontrasenaregistro);
        email=findViewById(R.id.edtemailregistro);
        contrasena=findViewById(R.id.edtcontrasenaregistro);
        btnseleccionimagen=findViewById(R.id.btnseleccionimagen);
        ivAvatar=findViewById(R.id.ivAvatar);
        btninicioregistro=findViewById(R.id.btninicioregistro);

        btnseleccionimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);


                startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"),SELECT_IMAGE);
            }
        });

        btninicioregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    insertaUsuario(usuario,contrasena,email,imagepath);
                    Toast.makeText(Registro.this, "Uusario creado", Toast.LENGTH_LONG).show();

                }catch (Exception e){

                }

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

    public void insertaUsuario(EditText nombreUsuario, EditText contrasena,EditText email, String ivAvatar) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Usuario usuario = new Usuario();
        usuario.setUsuario(String.valueOf(nombreUsuario.getText()));
        usuario.setEmail(String.valueOf(email.getText()));
        usuario.setContrasena(new String(Hex.encodeHex(DigestUtils.md5(String.valueOf(contrasena.getText())))));
        usuario.setAvatar(ivAvatar);
        realm.copyToRealm(usuario);
        realm.commitTransaction();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    imagepath=selectedImage.toString();
                    ivAvatar.setImageURI(selectedImage);
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

