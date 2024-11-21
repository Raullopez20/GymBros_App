package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.app.view.AppUsuario;
import com.example.proyecto_final_raul.model.InstalacionDAO;
import com.example.proyecto_final_raul.util.Instalacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class ControllerInstalaciones {
    private AppUsuario appUsuario;
    private static BorderPane root;
    private InstalacionDAO instalacionDAO;
    private final String secondaryColor = "#500000";
    private ObservableList<Instalacion> instalaciones;
    private final String DEFAULT_IMAGE_PATH = "file:src/main/resources/images/default.jpg";

    public ControllerInstalaciones(AppUsuario appUsuario) {
        this.appUsuario = appUsuario;
        this.instalacionDAO = new InstalacionDAO();
        this.root = new BorderPane();
        this.instalaciones = FXCollections.observableArrayList(ExceptionHandler.handle(
                () -> instalacionDAO.getAllInstalaciones(),
                SQLException.class
        ));
        crearVista();
    }

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

        VBox layout = new VBox(20, gridPane);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        root.setCenter(scrollPane);
    }

    private VBox crearInstalacionVBox(Instalacion instalacion) {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView(new Image(instalacion.getRutaImagen() != null ? instalacion.getRutaImagen() : DEFAULT_IMAGE_PATH));
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        Label nombreLabel = new Label(instalacion.getNombre());
        nombreLabel.setStyle("-fx-font-size: 20px;" + "-fx-font-weight: bold;");

        Label descripcionLabel = new Label(instalacion.getDescripcion());
        descripcionLabel.setStyle("-fx-font-size: 20px;" + "-fx-font-weight: bold;");

        vbox.getChildren().addAll(imageView, nombreLabel, descripcionLabel);

        return vbox;
    }

    public BorderPane getRoot() {
        return root;
    }
}
