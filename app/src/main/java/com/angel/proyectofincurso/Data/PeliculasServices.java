package com.angel.proyectofincurso.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PeliculasServices {
    @GET("movie/upcoming")
    Call<ListaPeliculasDTO> getUpcomingMovies(@Query("api_key") String apiKey,@Query("language") String language, @Query("page") int page);
    @GET("search/movie")
    Call<ListaPeliculasDTO> getPeliculas(@Query("api_key") String apiKey,@Query("language") String language,@Query("query") String query, @Query("page") int page);

    @GET("search/tv")
    Call<ListaPeliculasDTO> getSeries(@Query("api_key") String apiKey,@Query("language") String language,@Query("query") String query, @Query("page") int page);
}
