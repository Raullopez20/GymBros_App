package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.app.view.AppAdmin;
import com.example.proyecto_final_raul.model.InstalacionDAO;
import com.example.proyecto_final_raul.util.Instalacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

/**
 * Controlador para editar instalaciones.
 * Gestiona la vista y las interacciones para la edición de instalaciones.
 *
 * @author Raúl López
 */
public class ControllerEditarInstalaciones {
    private AppAdmin appAdmin;
    private static BorderPane root;
    private InstalacionDAO instalacionDAO;
    private final String primaryColor = "#000080";  // Define el color primario
    private final String secondaryColor = "#500000";
    private ObservableList<Instalacion> instalaciones;
    private final String DEFAULT_IMAGE_PATH = "file:src/main/resources/images/default.jpg";

    /**
     * Constructor del controlador.
     *
     * @param appAdmin la instancia de la aplicación principal
     */
    public ControllerEditarInstalaciones(AppAdmin appAdmin) {
        this.appAdmin = appAdmin;
        this.instalacionDAO = new InstalacionDAO();
        this.root = new BorderPane();
        this.instalaciones = FXCollections.observableArrayList(ExceptionHandler.handle(
                () -> instalacionDAO.getAllInstalaciones(),
                SQLException.class
        ));
        crearVista();
    }

    /**
     * Crea la vista inicial del panel de edición de instalaciones.
     */
    private void crearVista() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        int row = 0;
        int col = 0;

        for (Instalacion instalacion : instalaciones) {
            VBox vbox = crearInstalacionVBox(instalacion);
            gridPane.add(vbox, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        Button añadirInstalacionButton = new Button("Añadir Instalación");
        añadirInstalacionButton.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        añadirInstalacionButton.setOnAction(event -> {
            Instalacion nuevaInstalacion = new Instalacion(0, "", "", DEFAULT_IMAGE_PATH);
            Integer newId = ExceptionHandler.handle(
                    () -> instalacionDAO.addInstalacion(nuevaInstalacion),
                    SQLException.class
            );
            if (newId != null && newId != -1) {
                nuevaInstalacion.setIdInstalacion(newId);
                instalaciones.add(nuevaInstalacion);
                crearVista();
            }
        });

        Button actualizarCambiosButton = new Button("Actualizar Cambios");
        actualizarCambiosButton.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        actualizarCambiosButton.setOnAction(event -> {
            for (Instalacion instalacion : instalaciones) {
                ExceptionHandler.handle(() -> {
                    if (instalacion.getIdInstalacion() == 0) {
                        int newId = instalacionDAO.addInstalacion(instalacion);
                        instalacion.setIdInstalacion(newId);
                    } else {
                        instalacionDAO.updateInstalacion(instalacion);
                    }
                    return null;
                }, SQLException.class);
            }
            crearVista();
        });

        VBox layout = new VBox(20, gridPane, añadirInstalacionButton, actualizarCambiosButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Crear el título y agregarlo a la parte superior del BorderPane
        Label tituloTabla = new Label("Instalaciones");
        tituloTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + secondaryColor + ";");

        HBox topContainer = new HBox(tituloTabla);
        topContainer.setAlignment(Pos.TOP_LEFT);
        topContainer.setPadding(new Insets(10));

        root.setTop(topContainer);
        root.setCenter(scrollPane);
    }

    /**
     * Crea un VBox para una instalación específica.
     *
     * @param instalacion la instalación para la que se creará el VBox
     * @return un VBox que representa la instalación
     */
    private VBox crearInstalacionVBox(Instalacion instalacion) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(new Image(instalacion.getRutaImagen() != null ? instalacion.getRutaImagen() : DEFAULT_IMAGE_PATH));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        TextField nombreField = new TextField(instalacion.getNombre());
        nombreField.setStyle("-fx-border-color: " + secondaryColor);
        nombreField.setPromptText("Nombre");

        TextArea descripcionField = new TextArea(instalacion.getDescripcion());
        descripcionField.setStyle("-fx-border-color: " + secondaryColor);
        descripcionField.setPromptText("Descripción");
        descripcionField.setPrefRowCount(3);

        Button cambiarFotoButton = new Button("Cambiar Foto");
        cambiarFotoButton.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        cambiarFotoButton.setOnAction(event -> cambiarImagen(instalacion, imageView));

        Button guardarButton = new Button("Guardar");
        guardarButton.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        guardarButton.setOnAction(event -> {
            instalacion.setNombre(nombreField.getText());
            instalacion.setDescripcion(descripcionField.getText());
            ExceptionHandler.handle(() -> {
                instalacionDAO.updateInstalacion(instalacion);
                return null;
            }, SQLException.class);
        });

        Button eliminarButton = new Button("Eliminar");
        eliminarButton.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        eliminarButton.setOnAction(event -> {
            ExceptionHandler.handle(() -> {
                instalacionDAO.deleteInstalacion(instalacion.getIdInstalacion());
                instalaciones.remove(instalacion);
                crearVista();
                return null;
            }, SQLException.class);
        });

        vbox.getChildren().addAll(imageView, nombreField, descripcionField, cambiarFotoButton, guardarButton, eliminarButton);

        return vbox;
    }

    /**
     * Cambia la imagen de una instalación.
     *
     * @param instalacion la instalación para la que se cambiará la imagen
     * @param imageView el ImageView que mostrará la nueva imagen
     */
    private void cambiarImagen(Instalacion instalacion, ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            instalacion.setRutaImagen(imagePath);
            imageView.setImage(new Image(imagePath));
            ExceptionHandler.handle(() -> {
                instalacionDAO.updateInstalacion(instalacion);
                return null;
            }, SQLException.class);
        }
    }

    /**
     * Devuelve el layout principal del controlador.
     *
     * @return el layout principal
     */
    public static BorderPane getRoot() {
        return root;
    }
}
