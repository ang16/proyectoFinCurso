package com.angel.proyectofincurso.Data;

import java.util.ArrayList;

public class ListaPeliculasDTO {
    ArrayList<PeliculaDTO> results;
    ArrayList<PeliculaDTO> cast;

    public ArrayList<PeliculaDTO> getCast() {
        return cast;
    }

    public void setCast(ArrayList<PeliculaDTO> cast) {
        this.cast = cast;
    }

    public ArrayList<PeliculaDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<PeliculaDTO> results) {
        this.results = results;
    }
}
