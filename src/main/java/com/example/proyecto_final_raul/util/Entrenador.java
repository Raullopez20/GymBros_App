package com.example.proyecto_final_raul.util;

/**
 * Representa un entrenador de gimnasio.
 *
 * @autor Raul Lopez
 */
public class Entrenador {
    private int id_entrenador;
    private String nombre;
    private String correo;
    private String especializacion;
    private String detalles_contacto;

    /**
     * Constructor por defecto.
     */
    public Entrenador() {}

    /**
     * Constructor con parámetros.
     *
     * @param id_entrenador El ID del entrenador.
     * @param nombre El nombre del entrenador.
     * @param correo El correo electrónico del entrenador.
     * @param especializacion La especialización del entrenador.
     * @param detalles_contacto Los detalles de contacto del entrenador.
     */
    public Entrenador(int id_entrenador, String nombre, String correo, String especializacion, String detalles_contacto) {
        this.id_entrenador = id_entrenador;
        this.nombre = nombre;
        this.correo = correo;
        this.especializacion = especializacion;
        this.detalles_contacto = detalles_contacto;
    }

    // Getters y setters para cada campo
    public int getId_entrenador() {
        return id_entrenador;
    }

    public void setId_entrenador(int id_entrenador) {
        this.id_entrenador = id_entrenador;
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

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public String getDetalles_contacto() {
        return detalles_contacto;
    }

    public void setDetalles_contacto(String detalles_contacto) {
        this.detalles_contacto = detalles_contacto;
    }

    @Override
    public String toString() {
        return "Entrenador{" +
                "id_entrenador=" + id_entrenador +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", especializacion='" + especializacion + '\'' +
                ", detalles_contacto='" + detalles_contacto + '\'' +
                '}';
    }
}
