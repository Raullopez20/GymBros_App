package com.example.proyecto_final_raul.util;

/**
 * Representa un ejercicio individual en una rutina.
 *
 * @autor Raul Lopez
 */
public class Ejercicio {
    private int id_ejercicio;
    private String nombre;
    private String descripcion;
    private String tipo;

    /**
     * Constructor por defecto.
     */
    public Ejercicio() {}

    /**
     * Constructor con parámetros.
     *
     * @param id_ejercicio El ID del ejercicio.
     * @param nombre El nombre del ejercicio.
     * @param descripcion La descripción del ejercicio.
     * @param tipo El tipo de ejercicio (e.g., fuerza, cardio).
     */
    public Ejercicio(int id_ejercicio, String nombre, String descripcion, String tipo) {
        this.id_ejercicio = id_ejercicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    // Getters y setters para cada campo
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Ejercicio{" +
                "id_ejercicio=" + id_ejercicio +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
