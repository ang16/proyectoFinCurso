package com.angel.proyectofincurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.angel.proyectofincurso.Data.ListaActoresDTO;
import com.angel.proyectofincurso.Data.ListaPeliculasDTO;
import com.angel.proyectofincurso.Data.RestClient;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeliculaActivity extends AppCompatActivity {

    ImageView ivPortadaPleicula;
    TextView tvTituloPelicula;
    TextView tvSipnosisPelicula;
    RecyclerView rvActor;
    RestClient restClient = new RestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);
        ivPortadaPleicula = findViewById(R.id.ivPortadaPleicula);
        tvTituloPelicula = findViewById(R.id.tvTituloPelicula);
        tvSipnosisPelicula = findViewById(R.id.tvSipnosisPelicula);
        rvActor = findViewById(R.id.rvActor);

        Bundle bundle = getIntent().getExtras();
        buscaPeliculas(Long.parseLong(bundle.get("id").toString()));
        buscaActores(Long.parseLong(bundle.get("id").toString()));


    }

    public void buscaPeliculas(Long query) {

        Call<Pelicula> call = restClient.getPeliculasServices().getPelicula(query, RestClient.apiKey, RestClient.language, 1);
        call.enqueue(new Callback<Pelicula>() {
            @Override
            public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                Pelicula pelicula = response.body();
                Glide.with(PeliculaActivity.this).load(RestClient.imageBaseUrl + pelicula.getPoster_path()).into(ivPortadaPleicula);
                tvTituloPelicula.setText(pelicula.getTitle());
                tvSipnosisPelicula.setText(pelicula.getOverview());
            }

            @Override
            public void onFailure(Call<Pelicula> call, Throwable t) {

            }


        });
    }

    public void buscaActores(Long query) {

        Call<ListaActoresDTO> call = restClient.getActorServices().getActores(query,RestClient.apiKey);
        call.enqueue(new Callback<ListaActoresDTO>() {
            @Override
            public void onResponse(Call<ListaActoresDTO> call, Response<ListaActoresDTO> response) {
                ListaActoresDTO listaActoresDTO = response.body();
                final ArrayList<Actor> results = listaActoresDTO.getCast();
                final ActorAdapter actorAdapter = new ActorAdapter(PeliculaActivity.this, results, R.layout.item_actor);
                LinearLayoutManager layoutManager = new LinearLayoutManager(PeliculaActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rvActor.setLayoutManager(layoutManager);
                rvActor.setAdapter(actorAdapter);
            }

            @Override
            public void onFailure(Call<ListaActoresDTO> call, Throwable t) {

            }

        });
    }
}

