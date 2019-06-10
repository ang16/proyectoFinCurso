package com.angel.proyectofincurso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {


    TextView registro;
    TextView textinicio;
    EditText edtusuario;
    EditText edtcontrasena;
    Button btninicio;
    FirebaseAuth aut;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().setTitle(R.string.loging_tittle);
        registro = findViewById(R.id.textinicioregistro);
        textinicio= findViewById(R.id.textinicio);
        edtusuario= findViewById(R.id.edtusuario);
        edtcontrasena= findViewById(R.id.edtcontrasena);
        btninicio= findViewById(R.id.btninicio);
        aut = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if (aut.getCurrentUser() != null ) {
            if (aut.getCurrentUser().isEmailVerified()){
                finish();
                startActivity(new Intent(getApplicationContext(), BuscaPelicula.class));
            }else {
                btninicio.setText("Enviar correo de verificación");
                Toast.makeText(getApplicationContext(), "Verifica tu correo", Toast.LENGTH_SHORT).show();
                btninicio.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        aut.getInstance().getCurrentUser().sendEmailVerification();
                        btninicio.setText("Registrar");
                        btninicio.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                logeo();
                            }
                        });

                    }
                });
            }

        }else{
            btninicio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logeo();
                }
            });

        }

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Registro.class);
                startActivity(intent);
            }
        });

        /*btninicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compruebaUsuario();
            }
        });*/


    }

    private void logeo() {
        String emailr = edtusuario.getText().toString().trim();
        String contraseñar = edtcontrasena.getText().toString().trim();
        if (TextUtils.isEmpty(emailr)) {
            Toast.makeText(this, "Escribe el email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contraseñar)) {
            Toast.makeText(this, "Escribe la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();
        aut.signInWithEmailAndPassword(emailr, contraseñar).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if (aut.getInstance().getCurrentUser().isEmailVerified()) {
                        progressDialog.dismiss();
                        finish();
                        startActivity(new Intent(LoginActivity.this, BuscaPelicula.class));
                    } else {
                        btninicio.setText("Enviar correo de verificación");
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Verifica tu correo", Toast.LENGTH_SHORT).show();
                        btninicio.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                aut.getInstance().getCurrentUser().sendEmailVerification();
                                btninicio.setText("Iniciar sesión");
                                btninicio.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        logeo();
                                    }
                                });
                            }
                        });

                    }


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Fallo al iniciar sesioón,vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*public void compruebaUsuario(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<Usuario> usuarios = realm.where(Usuario.class).equalTo("usuario",String.valueOf(edtusuario.getText())).findAll();
        String s= new String(Hex.encodeHex(DigestUtils.md5(String.valueOf(edtcontrasena.getText()))));
        final RealmResults<Usuario> contrasenas = realm.where(Usuario.class).equalTo("contrasena", new String(Hex.encodeHex(DigestUtils.md5(String.valueOf(edtcontrasena.getText()))))).findAll();
        realm.commitTransaction();
        if(usuarios.size()==0){
            Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_LONG).show();

        }else if(contrasenas.size()==0){
            Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();

        }else{
            Intent intent = new Intent(LoginActivity.this, BuscaPelicula.class);
            startActivity(intent);

        }
    }*/


}

