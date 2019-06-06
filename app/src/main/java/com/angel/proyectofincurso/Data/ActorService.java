package com.angel.proyectofincurso.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ActorService {
    @GET("movie/{movie_id}/credits")
    Call<ListaActoresDTO> getActores(@Path("movie_id") Long id , @Query("api_key") String apiKey);
}
