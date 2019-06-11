package com.angel.proyectofincurso.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InformacionActorService {

    @GET("person/{person_id}")
    Call<InformacionActorDTO> getActor(@Path("person_id") Long person_id, @Query("api_key") String apiKey, @Query("language") String language);

}
