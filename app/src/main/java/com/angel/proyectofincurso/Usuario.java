package com.angel.proyectofincurso;

public class Usuario {
    int id;
    String usuario;
    String email;
    String contrasena;
    String avatar;

    public Usuario(int id, String usuario, String email, String contrasena, String avatar) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.contrasena = contrasena;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
