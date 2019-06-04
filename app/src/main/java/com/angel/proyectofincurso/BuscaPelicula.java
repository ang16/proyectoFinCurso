package com.angel.proyectofincurso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;

public class BuscaPelicula extends AppCompatActivity {
    ArrayList<Pelicula> peliculas= new ArrayList<>();
    SearchView buscador;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_pelicula);
        buscador=findViewById(R.id.buscador);
        buscador.onActionViewExpanded();
        recyclerView=findViewById(R.id.listaPeliculas);

        PeliculaAdapter peliculaAdapter= new PeliculaAdapter(this,peliculas,R.layout.item_pelicula);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(peliculaAdapter);

    }
}
