package com.example.proyecto_final_raul.util;

/**
 * Representa el detalle de una rutina de ejercicios.
 *
 * @autor Raul Lopez
 */
public class DetalleRutina {
    private int id_detalle_rutina;
    private int id_rutina;
    private int id_ejercicio;
    private int series;
    private int repeticiones;
    private int duracion;

    /**
     * Constructor por defecto.
     */
    public DetalleRutina() {}

    /**
     * Constructor con parámetros.
     *
     * @param id_detalle_rutina El ID del detalle de la rutina.
     * @param id_rutina El ID de la rutina.
     * @param id_ejercicio El ID del ejercicio.
     * @param series El número de series.
     * @param repeticiones El número de repeticiones.
     * @param duracion La duración del ejercicio en minutos.
     */
    public DetalleRutina(int id_detalle_rutina, int id_rutina, int id_ejercicio, int series, int repeticiones, int duracion) {
        this.id_detalle_rutina = id_detalle_rutina;
        this.id_rutina = id_rutina;
        this.id_ejercicio = id_ejercicio;
        this.series = series;
        this.repeticiones = repeticiones;
        this.duracion = duracion;
    }

    // Getters y setters para cada campo
    public int getId_detalle_rutina() {
        return id_detalle_rutina;
    }

    public void setId_detalle_rutina(int id_detalle_rutina) {
        this.id_detalle_rutina = id_detalle_rutina;
    }

    public int getId_rutina() {
        return id_rutina;
    }

    public void setId_rutina(int id_rutina) {
        this.id_rutina = id_rutina;
    }

    public int getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(int id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "DetalleRutina{" +
                "id_detalle_rutina=" + id_detalle_rutina +
                ", id_rutina=" + id_rutina +
                ", id_ejercicio=" + id_ejercicio +
                ", series=" + series +
                ", repeticiones=" + repeticiones +
                ", duracion=" + duracion +
                '}';
    }
}
