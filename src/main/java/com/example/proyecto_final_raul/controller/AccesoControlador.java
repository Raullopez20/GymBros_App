package com.example.proyecto_final_raul.controller;

import com.example.proyecto_final_raul.app.view.AppAcceso;
import com.example.proyecto_final_raul.app.view.AppRegistro;
import com.example.proyecto_final_raul.app.view.AppUsuario;
import com.example.proyecto_final_raul.app.view.AppAdmin;
import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.model.AdministradorDAO;
import com.example.proyecto_final_raul.model.UsuarioDAO;
import com.example.proyecto_final_raul.util.Administrador;
import com.example.proyecto_final_raul.util.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

/**
 * Controlador para manejar las acciones relacionadas con el acceso a la aplicación.
 * Permite el inicio de sesión y la redirección a las vistas correspondientes.
 *
 * @autor Raul Lopez
 */
public class AccesoControlador {

    private AppAcceso appAcceso;
    private UsuarioDAO usuarioDAO;
    private AdministradorDAO administradorDAO;

    /**
     * Constructor de la clase AccesoControlador.
     *
     * @param appAcceso La vista de la aplicación de acceso.
     */
    public AccesoControlador(AppAcceso appAcceso) {
        this.appAcceso = appAcceso;
        this.usuarioDAO = new UsuarioDAO();
        this.administradorDAO = new AdministradorDAO();
    }

    /**
     * Maneja el proceso de inicio de sesión.
     *
     * @param nombre_usuario El nombre de usuario.
     * @param contrasena La contraseña del usuario.
     */
    public void handleLogin(String nombre_usuario, String contrasena) {
        ExceptionHandler.handle(() -> {
            Administrador administrador = administradorDAO.getAdministradorByUsernameAndPassword(nombre_usuario, contrasena);
            if (administrador != null) {
                // Es un administrador
                redirigirAVistaAdministrador(administrador);
            } else {
                Usuario usuario = usuarioDAO.getUsuarioByUsernameAndPassword(nombre_usuario, contrasena);
                if (usuario != null) {
                    // Es un usuario
                    redirigirAVistaUsuario(usuario);
                } else {
                    // Credenciales inválidas
                    appAcceso.mostrarMensaje(Alert.AlertType.ERROR, "Error de acceso", "Usuario o contraseña incorrectos.");
                }
            }
            return null;
        }, Exception.class);
    }

    /**
     * Redirige a la vista de usuario.
     *
     * @param usuario El usuario que ha iniciado sesión.
     */
    private void redirigirAVistaUsuario(Usuario usuario) {
        ExceptionHandler.handle(() -> {
            Stage stageAppAcceso = appAcceso.getStageAppRegistro();
            AppUsuario appUsuario = new AppUsuario();
            appUsuario.setCurrentUser(usuario); // Pasar el usuario autenticado a la vista de usuario
            appUsuario.start(new Stage());
            // Cierra la ventana principal
            stageAppAcceso.close();
            return null;
        }, Exception.class);
    }

    /**
     * Redirige a la vista de administrador.
     *
     * @param administrador El administrador que ha iniciado sesión.
     */
    private void redirigirAVistaAdministrador(Administrador administrador) {
        ExceptionHandler.handle(() -> {
            Stage stageAppAcceso = appAcceso.getStageAppRegistro();
            AppAdmin appAdmin = new AppAdmin();
            appAdmin.start(new Stage());
            // Cierra la ventana principal
            stageAppAcceso.close();
            return null;
        }, Exception.class);
    }

    /**
     * Maneja el proceso de registro.
     * Abre la ventana de registro y cierra la ventana de acceso.
     */
    public void handleRegistro() {
        ExceptionHandler.handle(() -> {
            Stage stageAppAcceso = appAcceso.getStageAppRegistro();
            AppRegistro appRegistro = new AppRegistro();
            appRegistro.start(new Stage());
            // Cierra la ventana principal
            stageAppAcceso.close();
            return null;
        }, Exception.class);
    }

    /**
     * Registra manejadores de eventos de teclado para los campos de texto y el botón de acceso.
     *
     * @param usuarioTexto El campo de texto para el nombre de usuario.
     * @param contraseñaTexto El campo de texto para la contraseña.
     * @param accesoBoton El botón de acceso.
     */
    public void registrarManejadoresDeEventosDeTeclado(TextField usuarioTexto, PasswordField contraseñaTexto, Button accesoBoton) {
        usuarioTexto.setOnKeyPressed(event -> manejarEventoTeclado(event, accesoBoton));
        contraseñaTexto.setOnKeyPressed(event -> manejarEventoTeclado(event, accesoBoton));
    }

    /**
     * Maneja el evento de teclado para activar el botón de acceso al presionar Enter.
     *
     * @param event El evento de teclado.
     * @param accesoBoton El botón de acceso.
     */
    private void manejarEventoTeclado(KeyEvent event, Button accesoBoton) {
        switch (event.getCode()) {
            case ENTER:
                accesoBoton.fire();
                break;
            default:
                break;
        }
    }
}

