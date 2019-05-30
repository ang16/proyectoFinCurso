package com.angel.proyectofincurso;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Registro extends AppCompatActivity {

    TextView registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        registro = findViewById(R.id.textregistroinicio);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    public void insertaUsuario() {
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuarioDbHelper usdbh =
                new UsuarioDbHelper(this, "ProyectoFinCurso", 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if (db != null) {
            //Insertamos 5 usuarios de ejemplo
            for (int i = 1; i <= 5; i++) {
                //Generamos los datos
                int codigo = i;
                String nombre = "Usuario" + i;

                //Insertamos los datos en la tabla Usuarios
                db.execSQL("INSERT INTO Usuarios (codigo, nombre) " +
                        "VALUES (" + codigo + ", '" + nombre + "')");
            }

            //Cerramos la base de datos
            db.close();

        }
    }
}
