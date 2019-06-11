package com.angel.proyectofincurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.angel.proyectofincurso.Data.ActorDTO;
import com.angel.proyectofincurso.Data.InformacionActorDTO;
import com.angel.proyectofincurso.Data.ListaActoresDTO;
import com.angel.proyectofincurso.Data.RestClient;
import com.angel.proyectofincurso.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformacionActorActivity extends AppCompatActivity {

    ImageView ivInformacionActor;
    TextView tvNombreActor;
    TextView tvBiografiaActor;
    TextView tvFechaNacimiento;
    TextView tvLugarNacimiento;
    RecyclerView rvPeliculasActor;
    RestClient restClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_actor);
        ivInformacionActor=findViewById(R.id.ivInformacionActor);
        tvNombreActor=findViewById(R.id.tvNombreActor);
        tvBiografiaActor=findViewById(R.id.tvBiografiaActor);
        tvFechaNacimiento=findViewById(R.id.tvFechaNacimiento);
        tvLugarNacimiento=findViewById(R.id.tvLugarNacimiento);
        rvPeliculasActor=findViewById(R.id.rvPeliculasActor);
        restClient = new RestClient();

        Bundle bundle = getIntent().getExtras();
        buscaActor(Long.parseLong(bundle.get("id").toString()));
    }

    public void buscaActor(Long query) {

        Call<InformacionActorDTO> call = restClient.getInformacionActorService().getActor(query,RestClient.apiKey,RestClient.language);
        call.enqueue(new Callback<InformacionActorDTO>() {
            @Override
            public void onResponse(Call<InformacionActorDTO> call, Response<InformacionActorDTO> response) {
                InformacionActorDTO informacionActorDTO = response.body();
                Glide.with(InformacionActorActivity.this).load(RestClient.imageBaseUrl+informacionActorDTO.getProfile_path()).into(ivInformacionActor);


            }

            @Override
            public void onFailure(Call<InformacionActorDTO> call, Throwable t) {

            }

        });
    }
}
