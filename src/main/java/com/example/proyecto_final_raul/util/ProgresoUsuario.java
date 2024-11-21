package com.example.proyecto_final_raul.util;

/**
 * Representa el progreso de un usuario en su rutina de ejercicios.
 *
 * @autor Raul Lopez
 */
public class ProgresoUsuario {
    private int id_progreso;
    private int id_usuario;
    private int id_ejercicio;
    private String nombre;
    private String apellido;
    private int series_completadas;
    private int repeticiones_completadas;
    private int duracion;
    private String registrado_en;

    /**
     * Constructor por defecto.
     */
    public ProgresoUsuario() {}

    /**
     * Constructor con parámetros.
     *
     * @param id_progreso El ID del progreso.
     * @param id_usuario El ID del usuario.
     * @param id_ejercicio El ID del ejercicio.
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param series_completadas Las series completadas.
     * @param repeticiones_completadas Las repeticiones completadas.
     * @param duracion La duración del ejercicio.
     * @param registrado_en La fecha de registro del progreso.
     */
    public ProgresoUsuario(int id_progreso, int id_usuario, int id_ejercicio, String nombre, String apellido, int series_completadas, int repeticiones_completadas, int duracion, String registrado_en) {
        this.id_progreso = id_progreso;
        this.id_usuario = id_usuario;
        this.id_ejercicio = id_ejercicio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.series_completadas = series_completadas;
        this.repeticiones_completadas = repeticiones_completadas;
        this.duracion = duracion;
        this.registrado_en = registrado_en;
    }

    // Getters y setters para cada campo
    public int getId_progreso() {
        return id_progreso;
    }

    public void setId_progreso(int id_progreso) {
        this.id_progreso = id_progreso;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(int id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getSeries_completadas() {
        return series_completadas;
    }

    public void setSeries_completadas(int series_completadas) {
        this.series_completadas = series_completadas;
    }

    public int getRepeticiones_completadas() {
        return repeticiones_completadas;
    }

    public void setRepeticiones_completadas(int repeticiones_completadas) {
        this.repeticiones_completadas = repeticiones_completadas;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getRegistrado_en() {
        return registrado_en;
    }

    public void setRegistrado_en(String registrado_en) {
        this.registrado_en = registrado_en;
    }

    @Override
    public String toString() {
        return "ProgresoUsuario{" +
                "id_progreso=" + id_progreso +
                ", id_usuario=" + id_usuario +
                ", id_ejercicio=" + id_ejercicio +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", series_completadas=" + series_completadas +
                ", repeticiones_completadas=" + repeticiones_completadas +
                ", duracion=" + duracion +
                ", registrado_en='" + registrado_en + '\'' +
                '}';
    }
}
