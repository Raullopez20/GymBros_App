package com.example.proyecto_final_raul.util;

/**
 * Representa una clase de gimnasio.
 *
 * @autor Raul Lopez
 */
public class Clase {
    private int id_clase;
    private String titulo;
    private int id_entrenador;
    private int max_participantes;
    private String ubicacion;
    private String inicio;
    private String fin;

    /**
     * Constructor por defecto.
     */
    public Clase() {}

    /**
     * Constructor con parámetros.
     *
     * @param id_clase El ID de la clase.
     * @param titulo El título de la clase.
     * @param id_entrenador El ID del entrenador.
     * @param max_participantes El número máximo de participantes.
     * @param ubicacion La ubicación de la clase.
     * @param inicio La hora de inicio de la clase.
     * @param fin La hora de fin de la clase.
     */
    public Clase(int id_clase, String titulo, int id_entrenador, int max_participantes, String ubicacion, String inicio, String fin) {
        this.id_clase = id_clase;
        this.titulo = titulo;
        this.id_entrenador = id_entrenador;
        this.max_participantes = max_participantes;
        this.ubicacion = ubicacion;
        this.inicio = inicio;
        this.fin = fin;
    }

    // Getters y setters para cada campo
    public int getId_clase() {
        return id_clase;
    }

    public void setId_clase(int id_clase) {
        this.id_clase = id_clase;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId_entrenador() {
        return id_entrenador;
    }

    public void setId_entrenador(int id_entrenador) {
        this.id_entrenador = id_entrenador;
    }

    public int getMax_participantes() {
        return max_participantes;
    }

    public void setMax_participantes(int max_participantes) {
        this.max_participantes = max_participantes;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Clase{" +
                "id_clase=" + id_clase +
                ", titulo='" + titulo + '\'' +
                ", id_entrenador=" + id_entrenador +
                ", max_participantes=" + max_participantes +
                ", ubicacion='" + ubicacion + '\'' +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                '}';
    }
}
