package com.angel.proyectofincurso.Data;

public class ActorDTO {
    Long id;
    String character;
    String name;
    String profile_path;

    public Long getId() {
        return id;
    }

    public void setId(Long cast_id) {
        this.id = cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
