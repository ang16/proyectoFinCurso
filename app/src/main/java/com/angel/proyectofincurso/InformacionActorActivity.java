package com.angel.proyectofincurso;

import android.content.Intent;
import android.nfc.Tag;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Button btnVerMas;

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
        btnVerMas=findViewById(R.id.btnVerMas);

        Bundle bundle = getIntent().getExtras();
        buscaActor(Long.parseLong(bundle.get("id").toString()));


    }

    @Override
    protected void onStart(){
        super.onStart();

        Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called


    }

    public void buscaActor(final Long query) {

        Call<InformacionActorDTO> call = restClient.getInformacionActorService().getActor(query,RestClient.apiKey,RestClient.language);
        call.enqueue(new Callback<InformacionActorDTO>() {
            @Override
            public void onResponse(Call<InformacionActorDTO> call, Response<InformacionActorDTO> response) {
                InformacionActorDTO informacionActorDTO = response.body();
                if(informacionActorDTO.getBiography().isEmpty()){
                    Call<InformacionActorDTO> call2 = restClient.getInformacionActorService().getActor(query,RestClient.apiKey,"en-US");
                    call2.enqueue(new Callback<InformacionActorDTO>() {
                        @Override
                        public void onResponse(Call<InformacionActorDTO> call, Response<InformacionActorDTO> response) {
                            InformacionActorDTO informacionActorDTO = response.body();
                            Glide.with(InformacionActorActivity.this).load(RestClient.imageBaseUrl+informacionActorDTO.getProfile_path()).into(ivInformacionActor);
                            tvNombreActor.setText(informacionActorDTO.getName().toString());
                            tvBiografiaActor.setText(informacionActorDTO.getBiography().toString());
                            System.out.println("");
                        }

                        @Override
                        public void onFailure(Call<InformacionActorDTO> call, Throwable t) {

                        }

                    });

                }
                Glide.with(InformacionActorActivity.this).load(RestClient.imageBaseUrl+informacionActorDTO.getProfile_path()).into(ivInformacionActor);
                tvNombreActor.setText(informacionActorDTO.getName().toString());
                tvBiografiaActor.setText(informacionActorDTO.getBiography().toString());
                System.out.println("");

                if (tvBiografiaActor.getLineCount()>10) {
                    tvBiografiaActor.setMaxLines(Integer.MAX_VALUE);//your TextView
                    btnVerMas.setText("Ver más");
                }


                btnVerMas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (tvBiografiaActor.getMaxLines()>10) {
                            tvBiografiaActor.setMaxLines(Integer.MAX_VALUE);//your TextView
                            btnVerMas.setText("Ver más");
                        } else {
                            tvBiografiaActor.setMaxLines(Integer.MAX_VALUE);//your TextView
                            btnVerMas.setText("Ver menos");
                        }
                    }
                });


            }

            @Override
            public void onFailure(Call<InformacionActorDTO> call, Throwable t) {

            }

        });

        if (call.isExecuted()){
            System.out.println(tvBiografiaActor.getText().toString());
            System.out.println(tvBiografiaActor.getText().toString());
        }
    }

}
