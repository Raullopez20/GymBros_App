package com.example.proyecto_final_raul.util;

/**
 * Representa una instalación de gimnasio.
 *
 * @autor Raul Lopez
 */
public class Instalacion {
    private int idInstalacion;
    private String nombre;
    private String descripcion;
    private String rutaImagen;

    /**
     * Constructor con parámetros.
     *
     * @param idInstalacion El ID de la instalación.
     * @param nombre El nombre de la instalación.
     * @param descripcion La descripción de la instalación.
     * @param rutaImagen La ruta de la imagen de la instalación.
     */
    public Instalacion(int idInstalacion, String nombre, String descripcion, String rutaImagen) {
        this.idInstalacion = idInstalacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
    }

    // Getters y setters para cada campo
    public int getIdInstalacion() {
        return idInstalacion;
    }

    public void setIdInstalacion(int idInstalacion) {
        this.idInstalacion = idInstalacion;
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

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    @Override
    public String toString() {
        return "Instalacion{" +
                "idInstalacion=" + idInstalacion +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", rutaImagen='" + rutaImagen + '\'' +
                '}';
    }
}