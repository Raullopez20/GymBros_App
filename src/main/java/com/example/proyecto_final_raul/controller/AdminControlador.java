package com.example.proyecto_final_raul.controller;

import com.example.proyecto_final_raul.app.view.AppAcceso;
import com.example.proyecto_final_raul.app.view.AppAdmin;
import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.model.UsuarioDAO;
import com.example.proyecto_final_raul.model.AdministradorDAO;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controlador para manejar las acciones relacionadas con el administrador.
 * Permite el manejo de la sesión del administrador.
 *
 * @autor Raul Lopez
 */
public class AdminControlador {

    private AppAdmin appAdmin;
    private UsuarioDAO usuarioDAO;
    private AdministradorDAO administradorDAO;

    /**
     * Constructor de la clase AdminControlador.
     *
     * @param appAdmin La vista de la aplicación de administrador.
     */
    public AdminControlador(AppAdmin appAdmin) {
        this.appAdmin = appAdmin;
        this.usuarioDAO = new UsuarioDAO();
        this.administradorDAO = new AdministradorDAO();
    }

    /**
     * Maneja el cierre de sesión del administrador.
     * Cierra la ventana actual y abre la ventana de acceso.
     */
    public void handleCerrarSesion() {
        ExceptionHandler.handle(() -> {
            Stage stageAppAdmin = appAdmin.getStageAppAdmin();
            AppAcceso appAcceso = new AppAcceso();
            appAcceso.start(new Stage());
            stageAppAdmin.close();
            return null;
        }, Exception.class);
    }
}