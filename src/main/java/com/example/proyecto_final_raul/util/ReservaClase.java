package com.example.proyecto_final_raul.util;

/**
 * Clase que representa una reserva de clase en la aplicación de gimnasio.
 * Contiene información sobre la clase reservada y el usuario que hizo la reserva.
 * @author Raul Lopez
 */
public class ReservaClase {
    private int id_reserva;
    private int id_clase;
    private int id_usuario;
    private String titulo;
    private String nombre_usuario;
    private String dia;
    private String hora;
    private String reservado_en;

    /**
     * Constructor por defecto de ReservaClase.
     */
    public ReservaClase() {}

    /**
     * Constructor de ReservaClase con parámetros.
     * @param id_reserva ID de la reserva.
     * @param id_clase ID de la clase reservada.
     * @param id_usuario ID del usuario que hizo la reserva.
     * @param titulo Título de la clase reservada.
     * @param nombre_usuario Nombre del usuario que hizo la reserva.
     * @param dia Día de la reserva.
     * @param hora Hora de la reserva.
     * @param reservado_en Fecha y hora en la que se realizó la reserva.
     */
    public ReservaClase(int id_reserva, int id_clase, int id_usuario, String titulo, String nombre_usuario, String dia, String hora, String reservado_en) {
        this.id_reserva = id_reserva;
        this.id_clase = id_clase;
        this.id_usuario = id_usuario;
        this.titulo = titulo;
        this.nombre_usuario = nombre_usuario;
        this.dia = dia;
        this.hora = hora;
        this.reservado_en = reservado_en;
    }

    // Getters y Setters
    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getReservado_en() {
        return reservado_en;
    }

    public void setReservado_en(String reservado_en) {
        this.reservado_en = reservado_en;
    }

    @Override
    public String toString() {
        return "ReservaClase{" +
                "id_reserva=" + id_reserva +
                ", id_clase=" + id_clase +
                ", id_usuario=" + id_usuario +
                ", titulo='" + titulo + '\'' +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", dia='" + dia + '\'' +
                ", hora='" + hora + '\'' +
                ", reservado_en='" + reservado_en + '\'' +
                '}';
    }
}
