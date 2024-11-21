package com.example.proyecto_final_raul.app.view;

import com.example.proyecto_final_raul.controller.UsuarioControlador;
import com.example.proyecto_final_raul.controller.controller.*;
import com.example.proyecto_final_raul.util.Usuario;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.io.IOException;

/**
 * Clase principal para la aplicación de usuario.
 * Esta clase maneja la interfaz de usuario y las interacciones para la vista de usuario.
 * Extiende la clase {@link javafx.application.Application} de JavaFX.
 *
 * @autor Raul Lopez
 */
public class AppUsuario extends Application {

    private UsuarioControlador usuarioControlador;  // Controlador para manejar la lógica del usuario
    private Stage stageAppUsuario;  // Ventana principal de la aplicación de usuario
    private BorderPane root;  // Layout principal
    private VBox centroContenido;  // Contenido central de la aplicación
    private HostServices hostServices;  // Servicios del host
    private Usuario currentUser;  // Usuario actual

    /**
     * Método de inicio de la aplicación JavaFX.
     * Inicializa la ventana principal y configura la interfaz de usuario para la vista de usuario.
     *
     * @param stage La ventana principal de la aplicación.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.stageAppUsuario = stage;
        this.hostServices = getHostServices();

        // Configura el icono de la aplicación
        File iconFile = new File("src/main/resources/images/logo0.png");
        try {
            Image icon = new Image(new FileInputStream(iconFile));
            stage.getIcons().add(icon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Inicializa el controlador de usuario
        usuarioControlador = new UsuarioControlador(this);

        if (currentUser == null) {
            throw new IllegalStateException("El usuario actual no está establecido.");
        }

        stage.setTitle("APP_USER - GymBros");

        // Inicializar el layout principal
        root = new BorderPane();

        // Menú superior con información
        HBox menuSuperior = new HBox(10);
        menuSuperior.setPadding(new Insets(20));
        menuSuperior.setStyle("-fx-background-color: #2d0a0a;");
        menuSuperior.setAlignment(Pos.CENTER);

        Label info = new Label("Email: ac.gymbros@gmail.com               Atencion al Cliente: +34 622 58 28 43 ");
        info.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        info.setTextFill(Color.web("white"));
        info.setWrapText(true);

        menuSuperior.getChildren().addAll(info);
        menuSuperior.setAlignment(Pos.TOP_RIGHT);

        root.setTop(menuSuperior);

        // Panel izquierdo - información del usuario
        VBox izquierdaPanel = new VBox(20);
        izquierdaPanel.setPadding(new Insets(20));
        izquierdaPanel.setStyle("-fx-background-color: #2D0A0A;");
        izquierdaPanel.setAlignment(Pos.TOP_CENTER);

        // Imagen del usuario
        Image imagenUsuario = new Image(getClass().getResourceAsStream("/images/logoCorto.png"));
        ImageView vistaImagenUsuario = new ImageView(imagenUsuario);
        vistaImagenUsuario.setFitHeight(100);
        vistaImagenUsuario.setFitWidth(200);

        // Capitalizar la primera letra del nombre y apellido del usuario
        String nombre = currentUser.getNombre().substring(0, 1).toUpperCase() + currentUser.getNombre().substring(1).toLowerCase();
        String apellido = currentUser.getApellido().substring(0, 1).toUpperCase() + currentUser.getApellido().substring(1).toLowerCase();

        Label userLabel = new Label("Hola: " + nombre + " " + apellido);
        userLabel.setFont(new Font("Arial", 20));
        userLabel.setTextFill(Color.web("white"));

        // Contenedor de botones
        VBox contenedorBotones = new VBox(10);
        contenedorBotones.setAlignment(Pos.CENTER);

        // Botón de inicio
        Button botonInicio = new Button("Inicio");
        botonInicio.setStyle("-fx-background-color: #600000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonInicio.setPrefSize(180, 50);
        botonInicio.setOnAction(event -> mostrarVistaInicio());

        // Botón de horarios
        Button botonHorarios = new Button("Horarios");
        botonHorarios.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonHorarios.setPrefSize(180, 50);
        botonHorarios.setOnAction(event -> mostrarVistaHorarios());

        // Botón de instalaciones
        Button botonInstalaciones = new Button("Instalaciones");
        botonInstalaciones.setStyle("-fx-background-color: #600000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonInstalaciones.setPrefSize(180, 50);
        botonInstalaciones.setOnAction(event -> mostrarVistaInstalaciones());

        // Botón de progreso
        Button botonProgreso = new Button("Progreso");
        botonProgreso.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonProgreso.setPrefSize(180, 50);
        botonProgreso.setOnAction(event -> mostrarVistaProgresos());

        // Botón de contacto
        Button botonContacto = new Button("Contacto");
        botonContacto.setStyle("-fx-background-color: #600000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonContacto.setPrefSize(180, 50);
        botonContacto.setOnAction(event -> mostrarVistaContacto());

        // Botón de cerrar sesión
        Button botonCerrarSesion = new Button("Cerrar Sesion");
        botonCerrarSesion.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonCerrarSesion.setPrefSize(180, 50);
        botonCerrarSesion.setOnAction(event -> usuarioControlador.handleCerrarSesion());

        contenedorBotones.getChildren().addAll(botonInicio, botonHorarios, botonInstalaciones, botonProgreso, botonContacto, botonCerrarSesion);

        // Espacios entre imagen, etiqueta y botones
        VBox.setMargin(vistaImagenUsuario, new Insets(0, 0, 2, 0));
        VBox.setMargin(userLabel, new Insets(0, 0, 220, 0));
        VBox.setMargin(contenedorBotones, new Insets(220, 0, 0, 0));

        izquierdaPanel.getChildren().addAll(vistaImagenUsuario, userLabel, contenedorBotones);
        izquierdaPanel.setAlignment(Pos.TOP_CENTER);

        root.setLeft(izquierdaPanel);

        // Área central de contenido
        centroContenido = new VBox(20);
        centroContenido.setPadding(new Insets(20));
        centroContenido.setAlignment(Pos.CENTER);

        Label etiquetaBienvenida = new Label("¡Bienvenido a GymBros App, " + nombre + "!");
        etiquetaBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        etiquetaBienvenida.setTextFill(Color.web("#2D0A0A"));

        centroContenido.getChildren().add(etiquetaBienvenida);
        root.setCenter(centroContenido);

        // Crear la escena
        Scene escena = new Scene(root, 1800, 1000);
        stage.setScene(escena);
        //stage.setFullScreen(true);
        stage.show();
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
     * Obtiene el escenario principal de la aplicación de usuario.
     *
     * @return El escenario principal.
     */
    public Stage getStageAppUsuario() {
        return stageAppUsuario;
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
     * Muestra la vista de inicio.
     */
    public void mostrarVistaInicio() {
        ControllerUsuarios vistaUsuarios = new ControllerUsuarios(this);
        root.setCenter(vistaUsuarios.getRoot());
    }

    /**
     * Muestra la vista de horarios.
     */
    public void mostrarVistaHorarios() {
        ControllerHorarios vistaHorarios = new ControllerHorarios(this);
        root.setCenter(vistaHorarios.getRoot());
    }

    /**
     * Muestra la vista de instalaciones.
     */
    public void mostrarVistaInstalaciones() {
        ControllerInstalaciones vistaInstalaciones = new ControllerInstalaciones(this);
        root.setCenter(vistaInstalaciones.getRoot());
    }

    /**
     * Muestra la vista de progresos del usuario.
     * Si no hay un usuario actual establecido, muestra un mensaje de error.
     */
    public void mostrarVistaProgresos() {
        if (currentUser != null) {
            ControllerProgresos vistaProgresos = new ControllerProgresos(this);
            root.setCenter(vistaProgresos.getRoot());
        } else {
            mostrarMensaje(Alert.AlertType.ERROR, "Error", "No se ha establecido el usuario actual.");
        }
    }

    /**
     * Muestra la vista de contacto.
     */
    public void mostrarVistaContacto() {
        ControllerContacto vistaContacto = new ControllerContacto(this, hostServices);
        root.setCenter(vistaContacto.getRoot());
    }

    /**
     * Obtiene el usuario actual.
     *
     * @return El usuario actual.
     */
    public Usuario getCurrentUser() {
        return currentUser;
    }

    /**
     * Establece el usuario actual.
     *
     * @param user El usuario a establecer.
     */
    public void setCurrentUser(Usuario user) {
        this.currentUser = user;
    }
}
