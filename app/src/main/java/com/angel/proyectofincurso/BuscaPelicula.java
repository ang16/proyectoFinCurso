package com.angel.proyectofincurso;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.angel.proyectofincurso.Data.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaPelicula extends AppCompatActivity {
    ArrayList<PeliculaDTO> peliculas = new ArrayList<>();
    SearchView buscador;
    RecyclerView recyclerView;
    RestClient restClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_pelicula);
        buscador = findViewById(R.id.buscador);
        buscador.onActionViewExpanded();
        recyclerView = findViewById(R.id.listaPeliculas);
        restClient = new RestClient();

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                buscaPeliculas(s.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s != null && !s.isEmpty()) {
                    buscaPeliculas(s.toString());
                }
                return false;
            }
        });




    }



    public void buscaPeliculas(String query) {

        Call<ListaPeliculasDTO> call = restClient.getPeliculasServices().getPeliculas(RestClient.apiKey, RestClient.language, query, 1);
        call.enqueue(new Callback<ListaPeliculasDTO>() {
            @Override
            public void onResponse(Call<ListaPeliculasDTO> call, Response<ListaPeliculasDTO> response) {
                ListaPeliculasDTO listaPeliculasDTO = response.body();
                final ArrayList<PeliculaDTO> results = listaPeliculasDTO.getResults();
                final PeliculaAdapter peliculaAdapter = new PeliculaAdapter(BuscaPelicula.this, results, R.layout.item_pelicula);
                GridLayoutManager layoutManager = new GridLayoutManager(BuscaPelicula.this, 2);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(peliculaAdapter);

                peliculaAdapter.setClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(BuscaPelicula.this, PeliculaActivity.class);
                        intent.putExtra("id",peliculaAdapter.getItemFromView(view).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ListaPeliculasDTO> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(BuscaPelicula.this,LoginActivity.class);
                startActivity(intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
