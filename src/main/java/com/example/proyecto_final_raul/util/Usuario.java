package com.example.proyecto_final_raul.util;

/**
 * Clase que representa un usuario en la aplicación de gimnasio.
 * Contiene información personal del usuario, como nombre, correo y preferencias de entrenamiento.
 * @author Raul Lopez
 */
public class Usuario extends Persona {
    private int id_usuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String peso;
    private String altura;
    private String edad;
    private String nombre_usuario;
    private String contrasena;
    private String detalles_contacto;
    private String preferencias_entrenamiento;

    /**
     * Constructor por defecto de Usuario.
     */
    public Usuario() {}

    /**
     * Constructor de Usuario con parámetros.
     * @param id_usuario ID del usuario.
     * @param nombre Nombre del usuario.
     * @param apellido Apellido del usuario.
     * @param correo Correo electrónico del usuario.
     * @param peso Peso del usuario.
     * @param altura Altura del usuario.
     * @param edad Edad del usuario.
     * @param nombre_usuario Nombre de usuario del usuario.
     * @param contrasena Contraseña del usuario.
     * @param detalles_contacto Detalles de contacto del usuario.
     * @param preferencias_entrenamiento Preferencias de entrenamiento del usuario.
     */
    public Usuario(int id_usuario, String nombre, String apellido, String correo, String peso, String altura, String edad, String nombre_usuario, String contrasena, String detalles_contacto, String preferencias_entrenamiento) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.peso = peso;
        this.altura = altura;
        this.edad = edad;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
        this.detalles_contacto = detalles_contacto;
        this.preferencias_entrenamiento = preferencias_entrenamiento;
    }

    /**
     * Constructor de Usuario con parámetros simplificados.
     * @param id_usuario ID del usuario.
     * @param nombre_usuario Nombre de usuario del usuario.
     * @param correo Correo electrónico del usuario.
     */
    public Usuario(int id_usuario, String nombre_usuario, String correo) {
        this.id_usuario = id_usuario;
        this.nombre = nombre_usuario;
        this.correo = correo;
    }

    // Getters y Setters

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
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

    public String getDetalles_contacto() {
        return detalles_contacto;
    }

    public void setDetalles_contacto(String detalles_contacto) {
        this.detalles_contacto = detalles_contacto;
    }

    public String getPreferencias_entrenamiento() {
        return preferencias_entrenamiento;
    }

    public void setPreferencias_entrenamiento(String preferencias_entrenamiento) {
        this.preferencias_entrenamiento = preferencias_entrenamiento;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", peso='" + peso + '\'' +
                ", altura='" + altura + '\'' +
                ", edad='" + edad + '\'' +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", detalles_contacto='" + detalles_contacto + '\'' +
                ", preferencias_entrenamiento='" + preferencias_entrenamiento + '\'' +
                '}';
    }
}
