package com.angel.proyectofincurso;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.angel.proyectofincurso.Data.ActorDTO;
import com.angel.proyectofincurso.Data.DirectorDTO;
import com.angel.proyectofincurso.Data.ListaActoresDTO;
import com.angel.proyectofincurso.Data.ListaDirectoresDTO;
import com.angel.proyectofincurso.Data.ListaTrailersDTO;
import com.angel.proyectofincurso.Data.PeliculaDTO;
import com.angel.proyectofincurso.Data.RestClient;
import com.angel.proyectofincurso.Data.TrailerDTO;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeliculaActivity extends YouTubeBaseActivity {

    ImageView ivPortadaPleicula;
    TextView tvTituloPelicula;
    TextView tvSipnosisPelicula;
    RecyclerView rvActor;
    RecyclerView rvDirector;
    RestClient restClient = new RestClient();
    YouTubePlayerView youtubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);
        ivPortadaPleicula = findViewById(R.id.ivPortadaPleicula);
        tvTituloPelicula = findViewById(R.id.tvTituloPelicula);
        tvSipnosisPelicula = findViewById(R.id.tvSipnosisPelicula);
        rvActor = findViewById(R.id.rvActor);
        rvDirector = findViewById(R.id.rvDirector);
        youtubePlayerView = findViewById(R.id.youtubePlayerView);

        Bundle bundle = getIntent().getExtras();
        buscaPeliculas(Long.parseLong(bundle.get("id").toString()));
        buscaActores(Long.parseLong(bundle.get("id").toString()));
        buscaDirector(Long.parseLong(bundle.get("id").toString()));
        buscaTrailer(Long.parseLong(bundle.get("id").toString()));


    }

    public void buscaPeliculas(Long query) {

        Call<PeliculaDTO> call = restClient.getPeliculasServices().getPelicula(query, RestClient.apiKey, RestClient.language, 1);
        call.enqueue(new Callback<PeliculaDTO>() {
            @Override
            public void onResponse(Call<PeliculaDTO> call, Response<PeliculaDTO> response) {
                PeliculaDTO peliculaDTO = response.body();
                Glide.with(PeliculaActivity.this).load(RestClient.imageBaseUrl + peliculaDTO.getPoster_path()).into(ivPortadaPleicula);
                tvTituloPelicula.setText(peliculaDTO.getTitle());
                tvSipnosisPelicula.setText(peliculaDTO.getOverview());
            }

            @Override
            public void onFailure(Call<PeliculaDTO> call, Throwable t) {

            }


        });
    }

    public void buscaActores(Long query) {

        Call<ListaActoresDTO> call = restClient.getActorServices().getActores(query,RestClient.apiKey);
        call.enqueue(new Callback<ListaActoresDTO>() {
            @Override
            public void onResponse(Call<ListaActoresDTO> call, Response<ListaActoresDTO> response) {
                ListaActoresDTO listaActoresDTO = response.body();
                final ArrayList<ActorDTO> results = listaActoresDTO.getCast();
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

    public void buscaDirector(Long query) {

        Call<ListaDirectoresDTO> call = restClient.getDirectorService().getDirector(query,RestClient.apiKey);
        call.enqueue(new Callback<ListaDirectoresDTO>() {

            @Override
            public void onResponse(Call<ListaDirectoresDTO> call, Response<ListaDirectoresDTO> response) {
                ListaDirectoresDTO listaDirectorDTO = response.body();
                final ArrayList<DirectorDTO> results = listaDirectorDTO.getCrew();
                final ArrayList<DirectorDTO> results2= new ArrayList<>();
                for (DirectorDTO directorDTO:results){
                    if (directorDTO.getJob().equals("Director")){
                        results2.add(directorDTO);

                    }
                }
                final DirectorAdapter directorAdapter = new DirectorAdapter(PeliculaActivity.this, results2, R.layout.item_director);
                LinearLayoutManager layoutManager = new LinearLayoutManager(PeliculaActivity.this, LinearLayoutManager.HORIZONTAL, false);
                rvDirector.setLayoutManager(layoutManager);
                rvDirector.setAdapter(directorAdapter);
            }

            @Override
            public void onFailure(Call<ListaDirectoresDTO> call, Throwable t) {

            }
        });
    }

    public void buscaTrailer(Long query) {

        Call<ListaTrailersDTO> call = restClient.getTrailerService().getTrailers(query,RestClient.apiKey,"es-ES");
        call.enqueue(new Callback<ListaTrailersDTO>() {
            @Override
            public void onResponse(Call<ListaTrailersDTO> call, Response<ListaTrailersDTO> response) {
                ListaTrailersDTO listaTrailersDTO = response.body();
                final ArrayList<TrailerDTO> results = listaTrailersDTO.getResults();
                Boolean video=false;
                for (TrailerDTO trailerDTO: results){
                    if(trailerDTO.getType().equals("Trailer")){
                        playVideo(trailerDTO.getKey(),youtubePlayerView);
                        video=true;
                    }
                }
                if(!video){
                    playVideo(results.get(0).getKey(),youtubePlayerView);
                }


            }

            @Override
            public void onFailure(Call<ListaTrailersDTO> call, Throwable t) {

            }

        });
    }
    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        youTubePlayerView.initialize("AIzaSyDv5sON5QSkHILRLww5SYS6Tbi2BilR7gA",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

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
                Intent intent= new Intent(PeliculaActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

