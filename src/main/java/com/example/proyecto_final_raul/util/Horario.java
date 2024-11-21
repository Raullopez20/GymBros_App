package com.example.proyecto_final_raul.util;

/**
 * Representa un horario semanal.
 *
 * @autor Raul Lopez
 */
public class Horario {
    private int id;
    private String hora;
    private String lunes;
    private String martes;
    private String miercoles;
    private String jueves;
    private String viernes;
    private String sabado;
    private String domingo;

    /**
     * Constructor con parámetros.
     *
     * @param id El ID del horario.
     * @param hora La hora del horario.
     * @param lunes Actividad del lunes.
     * @param martes Actividad del martes.
     * @param miercoles Actividad del miércoles.
     * @param jueves Actividad del jueves.
     * @param viernes Actividad del viernes.
     * @param sabado Actividad del sábado.
     * @param domingo Actividad del domingo.
     */
    public Horario(int id, String hora, String lunes, String martes, String miercoles, String jueves, String viernes, String sabado, String domingo) {
        this.id = id;
        this.hora = hora;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
    }

    // Getters y setters para cada campo
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLunes() {
        return lunes;
    }

    public void setLunes(String lunes) {
        this.lunes = lunes;
    }

    public String getMartes() {
        return martes;
    }

    public void setMartes(String martes) {
        this.martes = martes;
    }

    public String getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(String miercoles) {
        this.miercoles = miercoles;
    }

    public String getJueves() {
        return jueves;
    }

    public void setJueves(String jueves) {
        this.jueves = jueves;
    }

    public String getViernes() {
        return viernes;
    }

    public void setViernes(String viernes) {
        this.viernes = viernes;
    }

    public String getSabado() {
        return sabado;
    }

    public void setSabado(String sabado) {
        this.sabado = sabado;
    }

    public String getDomingo() {
        return domingo;
    }

    public void setDomingo(String domingo) {
        this.domingo = domingo;
    }
}