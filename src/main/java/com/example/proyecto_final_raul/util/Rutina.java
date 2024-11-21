package com.example.proyecto_final_raul.util;

/**
 * Clase que representa una rutina de ejercicio en la aplicación de gimnasio.
 * Contiene información sobre la rutina, su creador y su descripción.
 * @author Raul Lopez
 */
public class Rutina {
    private int id_rutina;
    private int  id_entrenador;
    private String titulo;
    private String descripcion;
    private String creado_en;

    /**
     * Constructor por defecto de Rutina.
     */
    public Rutina() {}

    /**
     * Constructor de Rutina con parámetros.
     * @param id_rutina ID de la rutina.
     * @param id_entrenador ID del entrenador que creó la rutina.
     * @param titulo Título de la rutina.
     * @param descripcion Descripción de la rutina.
     * @param creado_en Fecha y hora en la que se creó la rutina.
     */
    public Rutina(int id_rutina, int id_entrenador, String titulo, String descripcion, String creado_en) {
        this.id_rutina = id_rutina;
        this.id_entrenador = id_entrenador;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.creado_en = creado_en;
    }

    // Getters y Setters

    public int getId_rutina() {
        return id_rutina;
    }

    public void setId_rutina(int id_rutina) {
        this.id_rutina = id_rutina;
    }

    public int getId_entrenador() {
        return id_entrenador;
    }

    public void setId_entrenador(int id_entrenador) {
        this.id_entrenador = id_entrenador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreado_en() {
        return creado_en;
    }

    public void setCreado_en(String creado_en) {
        this.creado_en = creado_en;
    }

    @Override
    public String toString() {
        return "Rutina{" +
                "id_rutina=" + id_rutina +
                ", id_entrenador=" + id_entrenador +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", creado_en='" + creado_en + '\'' +
                '}';
    }
}
