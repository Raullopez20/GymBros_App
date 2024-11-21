package com.example.proyecto_final_raul.app.view;

import com.example.proyecto_final_raul.controller.RegistroControlador;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Clase principal para la aplicación de registro.
 * Esta clase maneja la interfaz de usuario y las interacciones para el registro de nuevos usuarios.
 * Extiende la clase {@link javafx.application.Application} de JavaFX.
 *
 * @autor Raul Lopez
 */
public class AppRegistro extends Application {

    private RegistroControlador registroControlador;  // Controlador para manejar la lógica del registro
    private Stage stageAppAcceso2;  // Ventana principal de la aplicación

    /**
     * Método de inicio de la aplicación JavaFX.
     * Inicializa la ventana principal y configura la interfaz de usuario para el registro.
     *
     * @param stage La ventana principal de la aplicación.
     */
    @Override
    public void start(Stage stage) {
        this.stageAppAcceso2 = stage;

        registroControlador = new RegistroControlador(this);  // Inicializa el controlador de registro

        stage.setTitle("REGISTRO - GymBros");  // Título de la ventana

        // Cargar el icono de la aplicación
        File iconFile = new File("src/main/resources/images/logo0.png");
        try {
            Image icon = new Image(new FileInputStream(iconFile));
            stage.getIcons().add(icon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Layout principal
        BorderPane root = new BorderPane();

        // Panel izquierdo - descripción y logo
        VBox panelIzquierdo = new VBox(20);
        panelIzquierdo.setStyle("-fx-background-color: #2D0A0A; -fx-padding: 40px;");
        panelIzquierdo.setAlignment(Pos.CENTER);

        // Título de la aplicación
        Label tituloApp = new Label("GymBros App");
        tituloApp.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        tituloApp.setTextFill(Color.web("#FFFFFF"));

        // Descripciones de la aplicación
        Label descripcionApp = new Label("Una aplicación potente pero fácil");
        descripcionApp.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        descripcionApp.setTextFill(Color.web("#FFFFFF"));
        descripcionApp.setWrapText(true);

        Label descripcionApp2 = new Label("de usar para gestionar tus entrenamientos");
        descripcionApp2.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        descripcionApp2.setTextFill(Color.web("#FFFFFF"));
        descripcionApp2.setWrapText(true);

        Label descripcionApp3 = new Label("en el gimnasio");
        descripcionApp3.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        descripcionApp3.setTextFill(Color.web("#FFFFFF"));
        descripcionApp3.setWrapText(true);

        // Logo de la aplicación
        Image logoImagen = new Image(getClass().getResourceAsStream("/images/logo.png"));
        ImageView logoImagenView = new ImageView(logoImagen);
        logoImagenView.setFitHeight(200);
        logoImagenView.setFitWidth(200);

        // Añadir elementos al panel izquierdo
        panelIzquierdo.getChildren().addAll(logoImagenView, tituloApp, descripcionApp, descripcionApp2, descripcionApp3);

        // Panel derecho - formulario de registro
        VBox panelDerecho = new VBox(20);
        panelDerecho.setStyle("-fx-background-color: #EAEAEA; -fx-padding: 40px;");
        panelDerecho.setAlignment(Pos.CENTER);

        // Título del formulario de registro
        Label tituloRegistro = new Label("REGISTRARSE");
        tituloRegistro.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        tituloRegistro.setTextFill(Color.web("#2D0A0A"));

        // Campos del formulario de registro
        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Introduce tu nombre");
        campoNombre.setMaxWidth(300);

        TextField campoApellido = new TextField();
        campoApellido.setPromptText("Introduce tu apellido");
        campoApellido.setMaxWidth(300);

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Introduce tu email");
        campoEmail.setMaxWidth(300);

        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Introduce tu usuario");
        campoUsuario.setMaxWidth(300);

        PasswordField campoContraseña = new PasswordField();
        campoContraseña.setPromptText("Introduce tu contraseña");
        campoContraseña.setMaxWidth(300);

        PasswordField campoConfirmarContraseña = new PasswordField();
        campoConfirmarContraseña.setPromptText("Confirma tu contraseña");
        campoConfirmarContraseña.setMaxWidth(300);

        // Contenedor para el botón de registro
        HBox contenedorBoton = new HBox(10);
        contenedorBoton.setAlignment(Pos.CENTER);

        // Botón de registro
        Button botonRegistrarse = new Button("Registrarse");
        botonRegistrarse.setStyle("-fx-background-color: #400000; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");
        botonRegistrarse.setOnAction(event -> registroControlador.registrarse(
                event,
                campoNombre.getText(),
                campoApellido.getText(),
                campoEmail.getText(),
                null,
                null,
                null,
                campoUsuario.getText(),
                campoContraseña.getText(),
                campoConfirmarContraseña.getText()
        ));

        contenedorBoton.getChildren().add(botonRegistrarse);

        // Enlace para iniciar sesión
        Hyperlink enlaceIniciarSesion = new Hyperlink("¿Ya tienes una cuenta? Inicia sesión aquí");
        enlaceIniciarSesion.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        enlaceIniciarSesion.setTextFill(Color.web("#0000EE"));
        enlaceIniciarSesion.setOnAction(event -> registroControlador.botonInicioSesion());

        // Añadir elementos al panel derecho
        panelDerecho.getChildren().addAll(tituloRegistro, campoNombre, campoApellido, campoEmail, campoUsuario, campoContraseña, campoConfirmarContraseña, contenedorBoton, enlaceIniciarSesion);

        // Añadir paneles izquierdo y derecho al layout principal
        root.setLeft(panelIzquierdo);
        root.setCenter(panelDerecho);

        // Crear la escena
        Scene escena = new Scene(root, 800, 500);
        stage.setScene(escena);
        stage.show();
    }

    /**
     * Muestra un mensaje en pantalla.
     *
     * @param tipo El tipo de alerta.
     * @param titulo El título de la alerta.
     * @param mensaje El mensaje de la alerta.
     */
    public void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Método principal que lanza la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Obtiene el escenario principal de la aplicación.
     *
     * @return El escenario principal.
     */
    public Stage getStageAppAcceso2() {
        return stageAppAcceso2;
    }
}
