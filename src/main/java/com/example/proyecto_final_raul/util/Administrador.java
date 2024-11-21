package com.example.proyecto_final_raul.util;

/**
 * Representa un administrador del sistema.
 *
 * @autor Raul Lopez
 */
public class Administrador extends Persona {
    private int id_admin;
    private String nombre;
    private String correo;
    private String nombre_usuario;
    private String contrasena;

    /**
     * Constructor por defecto.
     */
    public Administrador() {}

    /**
     * Constructor con parámetros.
     *
     * @param id_admin El ID del administrador.
     * @param nombre El nombre del administrador.
     * @param correo El correo electrónico del administrador.
     * @param nombre_usuario El nombre de usuario del administrador.
     * @param contrasena La contraseña del administrador.
     */
    public Administrador(int id_admin, String nombre, String correo, String nombre_usuario, String contrasena) {
        this.id_admin = id_admin;
        this.nombre = nombre;
        this.correo = correo;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
    }

    // Getters y setters para cada campo
    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id_admin=" + id_admin +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
