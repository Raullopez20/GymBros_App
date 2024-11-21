package com.example.proyecto_final_raul.controller;

import com.example.proyecto_final_raul.app.view.AppAcceso;
import com.example.proyecto_final_raul.app.view.AppRegistro;
import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.model.UsuarioDAO;
import com.example.proyecto_final_raul.util.Usuario;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.regex.Pattern;

/**
 * Controlador para manejar las acciones relacionadas con el registro de usuarios.
 * Valida la información de registro y crea nuevos usuarios.
 *
 * @autor Raul Lopez
 */
public class RegistroControlador {

    private AppRegistro appRegistro;
    private UsuarioDAO usuarioDAO;

    /**
     * Constructor de la clase RegistroControlador.
     *
     * @param appRegistro La vista de la aplicación de registro.
     */
    public RegistroControlador(AppRegistro appRegistro) {
        this.appRegistro = appRegistro;
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Maneja el registro de un nuevo usuario.
     *
     * @param event El evento de acción.
     * @param nombre El nombre del usuario.
     * @param apellido El apellido del usuario.
     * @param correo El correo electrónico del usuario.
     * @param peso El peso del usuario.
     * @param altura La altura del usuario.
     * @param edad La edad del usuario.
     * @param nombre_usuario El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     * @param confirmarContrasena La confirmación de la contraseña.
     */
    public void registrarse(ActionEvent event, String nombre, String apellido, String correo, String peso, String altura, String edad, String nombre_usuario, String contrasena, String confirmarContrasena) {
        ExceptionHandler.handle(() -> {
            if (!contrasena.equals(confirmarContrasena)) {
                appRegistro.mostrarMensaje(Alert.AlertType.ERROR, "Error de Registro", "Las contraseñas no coinciden.");
                return null;
            }

            if (!esCorreoValido(correo)) {
                appRegistro.mostrarMensaje(Alert.AlertType.ERROR, "Error de Registro", "El correo electrónico no es válido.");
                return null;
            }

            if (!esNombreValido(nombre)) {
                appRegistro.mostrarMensaje(Alert.AlertType.ERROR, "Error de Registro", "El nombre no es válido. No debe contener números.");
                return null;
            }

            if (!esApellidoValido(apellido)) {
                appRegistro.mostrarMensaje(Alert.AlertType.ERROR, "Error de Registro", "El apellido no es válido. No debe contener números.");
                return null;
            }

            if (usuarioDAO.correoExiste(correo)) {
                appRegistro.mostrarMensaje(Alert.AlertType.ERROR, "Error de Registro", "El correo electrónico ya está registrado.");
                return null;
            }

            Usuario nuevoUsuario = new Usuario(0, nombre, apellido, correo, peso, altura, edad, nombre_usuario, contrasena, "", "");
            boolean exito = usuarioDAO.addUsuario(nuevoUsuario);

            if (exito) {
                appRegistro.mostrarMensaje(Alert.AlertType.INFORMATION, "Registro Exitoso", "Usuario registrado con éxito.");
            } else {
                appRegistro.mostrarMensaje(Alert.AlertType.ERROR, "Error de Registro", "No se pudo registrar el usuario.");
            }
            return null;
        }, Exception.class);
    }

    /**
     * Valida el formato del correo electrónico.
     *
     * @param correo El correo electrónico a validar.
     * @return true si el correo es válido, false en caso contrario.
     */
    private boolean esCorreoValido(String correo) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(correo).matches();
    }

    /**
     * Valida el formato del nombre.
     *
     * @param nombre El nombre a validar.
     * @return true si el nombre es válido, false en caso contrario.
     */
    private boolean esNombreValido(String nombre) {
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(nombre).matches();
    }

    /**
     * Valida el formato del apellido.
     *
     * @param apellido El apellido a validar.
     * @return true si el apellido es válido, false en caso contrario.
     */
    private boolean esApellidoValido(String apellido) {
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(apellido).matches();
    }

    /**
     * Maneja la acción de volver a la ventana de inicio de sesión.
     */
    public void botonInicioSesion() {
        ExceptionHandler.handle(() -> {
            Stage stageAppAcceso2 = appRegistro.getStageAppAcceso2();
            AppAcceso appAcceso = new AppAcceso();
            appAcceso.start(new Stage());
            stageAppAcceso2.close();
            return null;
        }, Exception.class);
    }
}