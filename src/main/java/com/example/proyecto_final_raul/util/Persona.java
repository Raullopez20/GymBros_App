package com.example.proyecto_final_raul.util;

/**
 * Representa una persona en el sistema.
 *
 * @autor Raul Lopez
 */
public class Persona {
    private String nombre;
    private String correo;
    private String nombre_usuario;
    private String contrasena;

    /**
     * Constructor con parámetros.
     *
     * @param nombre El nombre de la persona.
     * @param correo El correo electrónico de la persona.
     * @param nombre_usuario El nombre de usuario de la persona.
     * @param contrasena La contraseña de la persona.
     */
    public Persona(String nombre, String correo, String nombre_usuario, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
    }

    /**
     * Constructor por defecto.
     */
    public Persona() {
    }

    // Getters y setters para cada campo
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
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
