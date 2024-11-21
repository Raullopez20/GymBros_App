package com.example.proyecto_final_raul.controller;

import com.example.proyecto_final_raul.app.view.AppAcceso;
import com.example.proyecto_final_raul.app.view.AppUsuario;
import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.model.UsuarioDAO;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controlador para manejar las acciones relacionadas con el usuario.
 * Permite el manejo de la sesión del usuario.
 *
 * @author Raul Lopez
 */
public class UsuarioControlador {

    private AppUsuario appUsuario;
    private UsuarioDAO usuarioDAO;

    /**
     * Constructor de la clase UsuarioControlador.
     *
     * @param appUsuario La vista de la aplicación de usuario.
     */
    public UsuarioControlador(AppUsuario appUsuario) {
        this.appUsuario = appUsuario;
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Maneja el cierre de sesión del usuario.
     * Cierra la ventana actual y abre la ventana de acceso.
     */
    public void handleCerrarSesion() {
        ExceptionHandler.handle(() -> {
            Stage stageAppUsuario = appUsuario.getStageAppUsuario();
            AppAcceso appAcceso = new AppAcceso();
            appAcceso.start(new Stage());
            stageAppUsuario.close();
            return null;
        }, Exception.class);
    }
}