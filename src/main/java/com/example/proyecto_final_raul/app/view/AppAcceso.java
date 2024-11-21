package com.example.proyecto_final_raul.app.view;

import com.example.proyecto_final_raul.controller.AccesoControlador;
import com.example.proyecto_final_raul.util.Usuario;
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
import java.io.IOException;

/**
 * Clase principal para la aplicación de acceso (login).
 * Muestra la interfaz gráfica para el login y el registro.
 * Extiende de Application para utilizar JavaFX.
 *
 * @autor Raul Lopez
 */
public class AppAcceso extends Application {

    private AccesoControlador accesoControlador;
    private Stage stageAppRegistro;

    /**
     * Método start que se ejecuta al iniciar la aplicación.
     * Configura la ventana principal y los controles de la interfaz gráfica.
     *
     * @param stage La ventana principal de la aplicación.
     */
    @Override
    public void start(Stage stage) {
        this.stageAppRegistro = stage;
        accesoControlador = new AccesoControlador(this);

        stage.setTitle("LOGIN - GymBros");

        // Cargar el ícono de la aplicación
        File iconFile = new File("src/main/resources/images/logo0.png");
        try {
            Image icon = new Image(new FileInputStream(iconFile));
            stage.getIcons().add(icon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Configurar el layout principal
        BorderPane root = new BorderPane();

        // Panel izquierdo con descripción y logo
        VBox izqPanel = new VBox(20);
        izqPanel.setStyle("-fx-background-color: #2D0A0A; -fx-padding: 40px;");
        izqPanel.setAlignment(Pos.CENTER);

        Label appTitulo = new Label("GymBros App");
        appTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        appTitulo.setTextFill(Color.web("#FFFFFF"));

        Label appDescripcion = new Label("Una aplicación potente pero fácil");
        appDescripcion.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        appDescripcion.setTextFill(Color.web("#FFFFFF"));
        appDescripcion.setWrapText(true);

        Label appDescripcion2 = new Label("de usar para gestionar tus entrenamientos");
        appDescripcion2.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        appDescripcion2.setTextFill(Color.web("#FFFFFF"));
        appDescripcion2.setWrapText(true);

        Label appDescripcion3 = new Label("en el gimnasio");
        appDescripcion3.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        appDescripcion3.setTextFill(Color.web("#FFFFFF"));
        appDescripcion3.setWrapText(true);

        Image logoImagen = new Image(getClass().getResourceAsStream("/images/logo.png"));
        ImageView logoImagenView = new ImageView(logoImagen);
        logoImagenView.setFitHeight(200);
        logoImagenView.setFitWidth(200);

        izqPanel.getChildren().addAll(logoImagenView, appTitulo, appDescripcion, appDescripcion2, appDescripcion3);

        // Panel derecho con formulario de login
        VBox dchaPanel = new VBox(20);
        dchaPanel.setStyle("-fx-background-color: #EAEAEA; -fx-padding: 40px;");
        dchaPanel.setAlignment(Pos.CENTER);

        Label accesoTitulo = new Label("ACCESO APP");
        accesoTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        accesoTitulo.setTextFill(Color.web("#2D0A0A"));

        TextField usuarioTexto = new TextField();
        usuarioTexto.setPromptText("Introduce tu usuario");
        usuarioTexto.setMaxWidth(300);

        PasswordField contraseñaTexto = new PasswordField();
        contraseñaTexto.setPromptText("Introduce tu contraseña");
        contraseñaTexto.setMaxWidth(300);

        Hyperlink olvidarContraseña = new Hyperlink("¿Olvidaste tu contraseña?");
        olvidarContraseña.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        olvidarContraseña.setTextFill(Color.web("#0000EE"));

        HBox boton = new HBox(10);
        boton.setAlignment(Pos.CENTER);

        Button accesoBoton = new Button("Iniciar Sesión");
        accesoBoton.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");

        Button registrarBoton = new Button("Registrarse");
        registrarBoton.setStyle("-fx-background-color: #400000; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");

        boton.getChildren().addAll(accesoBoton, registrarBoton);

        dchaPanel.getChildren().addAll(accesoTitulo, usuarioTexto, contraseñaTexto, olvidarContraseña, boton);

        // Añadir paneles izquierdo y derecho al layout principal
        root.setLeft(izqPanel);
        root.setCenter(dchaPanel);

        // Crear la escena y mostrarla
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();

        // Registrar manejadores de eventos de teclado
        accesoControlador.registrarManejadoresDeEventosDeTeclado(usuarioTexto, contraseñaTexto, accesoBoton);

        // Manejo de eventos para los botones
        accesoBoton.setOnAction(e -> accesoControlador.handleLogin(usuarioTexto.getText(), contraseñaTexto.getText()));
        registrarBoton.setOnAction(e -> accesoControlador.handleRegistro());
    }

    /**
     * Método principal para lanzar la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param tipo El tipo de alerta.
     * @param titulo El título del cuadro de diálogo.
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Devuelve la ventana principal de la aplicación de registro.
     *
     * @return La ventana principal.
     */
    public Stage getStageAppRegistro() {
        return stageAppRegistro;
    }

    /**
     * Abre la aplicación principal con el usuario autenticado.
     *
     * @param username El nombre de usuario del usuario autenticado.
     */
    private void openMainApp(String username) {
        // Crear una instancia de Usuario con la información del usuario autenticado
        Usuario usuarioAutenticado = new Usuario(1, username, "correo@example.com");

        // Abrir la aplicación principal y pasar el usuario autenticado
        AppUsuario appUsuario = new AppUsuario();
        appUsuario.setCurrentUser(usuarioAutenticado);
        try {
            appUsuario.start(stageAppRegistro);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
