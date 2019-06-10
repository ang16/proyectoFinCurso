package com.angel.proyectofincurso.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrailerService {

    @GET("movie/{movie_id}/videos")
    Call<ListaTrailersDTO> getTrailers(@Path("movie_id") Long id , @Query("api_key") String apiKey,@Query("language") String language);
}
