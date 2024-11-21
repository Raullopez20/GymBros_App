package com.example.proyecto_final_raul.controller;

import com.example.proyecto_final_raul.app.view.AppAcceso;
import com.example.proyecto_final_raul.app.MainApp;
import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Controlador principal de la aplicación.
 * Maneja la transición entre la ventana principal y la ventana de usuario.
 *
 * @autor Raul Lopez
 */
public class MainControlador {

    private MainApp mainApp;

    /**
     * Constructor de la clase MainControlador.
     *
     * @param mainApp La instancia principal de la aplicación.
     */
    public MainControlador(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Abre la ventana de usuario y cierra la ventana principal.
     */
    public void ventanaUser() {
        ExceptionHandler.handle(() -> {
            Stage primaryStage = mainApp.getStageAppAcceso();
            AppAcceso appAcceso = new AppAcceso();
            appAcceso.start(new Stage());
            primaryStage.close();
            return null;
        }, Exception.class);
    }
}
