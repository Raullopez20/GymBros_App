package com.example.proyecto_final_raul.app;

import com.example.proyecto_final_raul.controller.MainControlador;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Clase principal de la aplicación de gimnasio.
 * Extiende la clase Application de JavaFX para manejar la interfaz gráfica.
 *
 * @autor Raul Lopez
 */
public class MainApp extends Application {

    private MainControlador mainControlador;
    private Stage stageAppAcceso;
    private ConexionBD conexionBD;

    /**
     * Método de inicio de la aplicación JavaFX.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        this.stageAppAcceso = primaryStage;

        mainControlador = new MainControlador(this);
        conexionBD = new ConexionBD();

        File iconFile = new File("src/main/resources/images/logo0.png");
        try {
            Image icon = new Image(new FileInputStream(iconFile));
            primaryStage.getIcons().add(icon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Crear un botón
        Button button = new Button();

        // Cargar la imagen
        Image logo = new Image(getClass().getResource("/images/logo0.png").toExternalForm());
        ImageView imageView = new ImageView(logo);

        // Ajustar el tamaño de la imagen
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        // Establecer la acción del botón para llamar al método executeSQLFile
        button.setOnAction(event -> {
            mainControlador.ventanaUser();
            conexionBD.executeSQLFile();
        });

        // Añadir la imagen al botón
        button.setGraphic(imageView);

        // Crear el contenedor y añadir el botón
        StackPane root = new StackPane(button);

        // Crear la escena y añadir el contenedor
        Scene scene = new Scene(root, 400, 400);

        // Configurar y mostrar el escenario
        primaryStage.setTitle("GYMBROS - APP");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Método principal que lanza la aplicación.
     *
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Obtiene el escenario principal de la aplicación.
     *
     * @return El escenario principal.
     */
    public Stage getStageAppAcceso() {
        return stageAppAcceso;
    }

    /**
     * Muestra un mensaje de alerta en la aplicación.
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
}
