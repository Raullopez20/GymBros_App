package com.example.proyecto_final_raul.app.view;

import com.example.proyecto_final_raul.controller.AdminControlador;
import com.example.proyecto_final_raul.controller.controller.ControllerEditarAdmin;
import com.example.proyecto_final_raul.controller.controller.ControllerEditarUsuarios;
import com.example.proyecto_final_raul.controller.controller.ControllerEditarHorario;
import com.example.proyecto_final_raul.controller.controller.ControllerEditarInstalaciones;
import com.example.proyecto_final_raul.controller.controller.ControllerCrearProgresos;
import javafx.application.Application;
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
 * Clase principal para la aplicación de administrador.
 * Esta clase maneja la interfaz de usuario y las interacciones en la aplicación de administración del gimnasio.
 * Extiende la clase {@link javafx.application.Application} de JavaFX.
 *
 * @autor Raul Lopez
 */
public class AppAdmin extends Application {

    private AdminControlador adminControlador;  // Controlador para manejar la lógica de administración
    private Stage stageAppAdmin;  // Ventana principal de la aplicación
    private BorderPane root;  // Contenedor principal para la disposición de la UI
    private VBox centroContenido;  // Contenedor para el contenido central

    /**
     * Método de inicio de la aplicación JavaFX.
     * Inicializa la ventana principal y configura la interfaz de usuario.
     *
     * @param stage La ventana principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar los recursos.
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("APP_ADMIN - GymBros");  // Título de la ventana

        adminControlador = new AdminControlador(this);  // Inicializa el controlador de administración

        this.stageAppAdmin = stage;

        // Cargar el icono de la aplicación
        File iconFile = new File("src/main/resources/images/logo0.png");
        try {
            Image icon = new Image(new FileInputStream(iconFile));
            stage.getIcons().add(icon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Layout principal
        root = new BorderPane();

        // Crear el menú superior
        HBox menuSuperior = new HBox(10);
        menuSuperior.setPadding(new Insets(20));
        menuSuperior.setStyle("-fx-background-color: #2d0a0a;");
        menuSuperior.setAlignment(Pos.CENTER);

        // Información del administrador
        Label info = new Label("Admin:    Administrador                        ");
        info.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        info.setTextFill(Color.web("white"));
        info.setWrapText(true);

        menuSuperior.getChildren().addAll(info);
        menuSuperior.setAlignment(Pos.TOP_RIGHT);

        root.setTop(menuSuperior);

        // Panel izquierdo para foto de usuario y botones
        VBox izquierdaPanel = new VBox(20);
        izquierdaPanel.setPadding(new Insets(20));
        izquierdaPanel.setStyle("-fx-background-color: #2D0A0A;");
        izquierdaPanel.setAlignment(Pos.TOP_CENTER);

        // Imagen de usuario
        Image imagenUsuario = new Image(getClass().getResourceAsStream("/images/logoCorto.png"));
        ImageView vistaImagenUsuario = new ImageView(imagenUsuario);
        vistaImagenUsuario.setFitHeight(100);
        vistaImagenUsuario.setFitWidth(200);

        // Contenedor para botones
        VBox contenedorBotones = new VBox(10);
        contenedorBotones.setAlignment(Pos.CENTER);

        // Botón para editar usuarios
        Button botonEditarUsuario = new Button("Editar Usuarios");
        botonEditarUsuario.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonEditarUsuario.setPrefSize(180, 50);
        botonEditarUsuario.setOnAction(event -> mostrarVistaEditarUsuarios());

        // Botón para editar administrador
        Button botonEditarAdmin = new Button("Editar Admin");
        botonEditarAdmin.setStyle("-fx-background-color: #600000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonEditarAdmin.setPrefSize(180, 50);
        botonEditarAdmin.setOnAction(event -> mostrarVistaEditarAdmin());

        // Botón para editar horarios
        Button botonEditarHorarios = new Button("Editar Horarios");
        botonEditarHorarios.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonEditarHorarios.setPrefSize(180, 50);
        botonEditarHorarios.setOnAction(event -> mostrarVistaEditarHorarios());

        // Botón para editar instalaciones
        Button botonEditarInstalaciones = new Button("Editar Instalaciones");
        botonEditarInstalaciones.setStyle("-fx-background-color: #600000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonEditarInstalaciones.setPrefSize(180, 50);
        botonEditarInstalaciones.setOnAction(event -> mostrarVistaEditarInstalaciones());

        // Botón para crear progresos
        Button botonCrearProgreso = new Button("Crear Progresos");
        botonCrearProgreso.setStyle("-fx-background-color: #500000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonCrearProgreso.setPrefSize(180, 50);
        botonCrearProgreso.setOnAction(event -> mostrarVistaCrearProgresos());

        // Botón para cerrar sesión
        Button botonCerrarSesion = new Button("Cerrar Sesion");
        botonCerrarSesion.setStyle("-fx-background-color: #600000; -fx-text-fill: white; -fx-font-size: 16px;");
        botonCerrarSesion.setPrefSize(180, 50);
        botonCerrarSesion.setOnAction(event -> adminControlador.handleCerrarSesion());

        contenedorBotones.getChildren().addAll(botonEditarUsuario, botonEditarAdmin, botonEditarHorarios, botonEditarInstalaciones, botonCrearProgreso, botonCerrarSesion);

        // Añadir margen entre imagen y botones
        VBox.setMargin(vistaImagenUsuario, new Insets(0, 0, 250, 0));
        VBox.setMargin(contenedorBotones, new Insets(250, 0, 0, 0));

        izquierdaPanel.getChildren().addAll(vistaImagenUsuario, contenedorBotones);
        izquierdaPanel.setAlignment(Pos.TOP_CENTER);

        root.setLeft(izquierdaPanel);

        // Área de contenido central
        centroContenido = new VBox(20);
        centroContenido.setPadding(new Insets(20));
        centroContenido.setAlignment(Pos.CENTER);

        Label etiquetaBienvenida = new Label("¡Bienvenido a GymBros App!");
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
     * Obtiene el escenario principal de la aplicación.
     *
     * @return El escenario principal.
     */
    public Stage getStageAppAdmin() {
        return stageAppAdmin;
    }

    /**
     * Muestra la vista para editar usuarios.
     */
    public void mostrarVistaEditarUsuarios() {
        ControllerEditarUsuarios vistaEditarUsuarios = new ControllerEditarUsuarios(this);
        root.setCenter(vistaEditarUsuarios.getRoot());
    }

    /**
     * Muestra la vista para editar administrador.
     */
    public void mostrarVistaEditarAdmin() {
        ControllerEditarAdmin vistaEditarAdmin = new ControllerEditarAdmin(this);
        root.setCenter(ControllerEditarAdmin.getRoot());
    }

    /**
     * Muestra la vista para editar horarios.
     */
    public void mostrarVistaEditarHorarios() {
        ControllerEditarHorario controllerEditarHorario = new ControllerEditarHorario(this);
        root.setCenter(ControllerEditarHorario.getRoot());
    }

    /**
     * Muestra la vista para editar instalaciones.
     */
    public void mostrarVistaEditarInstalaciones() {
        ControllerEditarInstalaciones vistaEditarInstalaciones = new ControllerEditarInstalaciones(this);
        root.setCenter(ControllerEditarInstalaciones.getRoot());
    }

    /**
     * Muestra la vista para crear progresos.
     */
    public void mostrarVistaCrearProgresos() {
        ControllerCrearProgresos controllerCrearProgresos = new ControllerCrearProgresos(this);
        root.setCenter(controllerCrearProgresos.getRoot());
    }
}
