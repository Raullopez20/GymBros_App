package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.app.view.AppAdmin;
import com.example.proyecto_final_raul.model.AdministradorDAO;
import com.example.proyecto_final_raul.util.Administrador;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador para editar administradores.
 * Gestiona la vista y las interacciones para la edición de administradores.
 *
 * @author  Raúl López
 */
public class ControllerEditarAdmin {
    // Define the application colors
    private final String primaryColor = "#400000";
    private final String secondaryColor = "#500000";


    private AppAdmin appAdmin;
    private static BorderPane root;
    private TableView<Administrador> tablaAdministradores;
    private AdministradorDAO administradorDAO;

    /**
     * Constructor del controlador.
     *
     * @param appAdmin la instancia de la aplicación principal
     */
    public ControllerEditarAdmin(AppAdmin appAdmin) {
        this.appAdmin = appAdmin;
        this.administradorDAO = new AdministradorDAO();
        this.root = new BorderPane();
        crearVista();
    }

    /**
     * Crea la vista inicial del panel de edición de administradores.
     */
    private void crearVista() {
        // Crear la tabla de administradores
        tablaAdministradores = new TableView<>();

        // Definir las columnas de la tabla
        TableColumn<Administrador, String> columnaIdAdmin = new TableColumn<>("ID Admin");
        columnaIdAdmin.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId_admin())));
        columnaIdAdmin.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaIdAdmin.setPrefWidth(150);

        TableColumn<Administrador, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaNombre.setOnEditCommit(event -> {
            Administrador administrador = event.getRowValue();
            administrador.setNombre(event.getNewValue());
            ExceptionHandler.handle(() -> administradorDAO.updateAdministrador(administrador), SQLException.class);
        });
        columnaNombre.setPrefWidth(150);

        TableColumn<Administrador, String> columnaCorreo = new TableColumn<>("Correo");
        columnaCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        columnaCorreo.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaCorreo.setOnEditCommit(event -> {
            Administrador administrador = event.getRowValue();
            administrador.setCorreo(event.getNewValue());
            ExceptionHandler.handle(() -> administradorDAO.updateAdministrador(administrador), SQLException.class);
        });
        columnaCorreo.setPrefWidth(150);

        TableColumn<Administrador, String> columnaNombreUsuario = new TableColumn<>("Nombre de Usuario");
        columnaNombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre_usuario()));
        columnaNombreUsuario.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaNombreUsuario.setOnEditCommit(event -> {
            Administrador administrador = event.getRowValue();
            administrador.setNombre_usuario(event.getNewValue());
            ExceptionHandler.handle(() -> administradorDAO.updateAdministrador(administrador), SQLException.class);
        });
        columnaNombreUsuario.setPrefWidth(150);

        TableColumn<Administrador, String> columnaContrasena = new TableColumn<>("Contraseña");
        columnaContrasena.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContrasena()));
        columnaContrasena.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaContrasena.setOnEditCommit(event -> {
            Administrador administrador = event.getRowValue();
            administrador.setContrasena(event.getNewValue());
            ExceptionHandler.handle(() -> administradorDAO.updateAdministrador(administrador), SQLException.class);
        });
        columnaContrasena.setPrefWidth(150);

        // Añadir las columnas a la tabla
        tablaAdministradores.getColumns().addAll(columnaIdAdmin, columnaNombre, columnaCorreo, columnaNombreUsuario, columnaContrasena);
        tablaAdministradores.setEditable(true);
        tablaAdministradores.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");

        // Asegurar que la tabla ocupa todo el espacio disponible
        VBox.setVgrow(tablaAdministradores, Priority.ALWAYS);

        // Cargar los datos de los administradores en la tabla
        actualizarTablaAdministradores();

        // Botón para eliminar administrador
        Button botonEliminar = new Button("Eliminar Administrador");
        botonEliminar.setOnAction(event -> {
            Administrador administradorSeleccionado = tablaAdministradores.getSelectionModel().getSelectedItem();
            if (administradorSeleccionado != null) {
                if (administradorDAO.getAllAdministradores().size() > 1) {
                    ExceptionHandler.handle(() -> {
                        try {
                            administradorDAO.borrarAdministrador(administradorSeleccionado.getId_admin());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        actualizarTablaAdministradores();
                    }, SQLException.class);
                } else {
                    mostrarMensaje(Alert.AlertType.WARNING, "Advertencia", "No se puede eliminar el único administrador.");
                }
            } else {
                mostrarMensaje(Alert.AlertType.WARNING, "Advertencia", "Seleccione un administrador para eliminar.");
            }
        });

        // Botón para agregar un nuevo administrador
        Button botonAgregar = new Button("Agregar Administrador");
        botonAgregar.setOnAction(event -> mostrarFormularioAgregarAdministrador());

        // Botón para actualizar la tabla
        Button botonActualizar = new Button("Actualizar Tabla");
        botonActualizar.setOnAction(event -> actualizarTablaAdministradores());

        botonActualizar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonAgregar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonEliminar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        // Layout para los botones en la parte inferior
        HBox botonesInferiores = new HBox(10, botonEliminar, botonAgregar, botonActualizar);
        botonesInferiores.setAlignment(Pos.CENTER);
        botonesInferiores.setPadding(new Insets(10));

        Label tituloTabla = new Label("Tabla Administradores");
        tituloTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + primaryColor + ";");


        // Añadir la tabla y los botones al layout principal
        VBox centroContenido = new VBox(10, tituloTabla,tablaAdministradores, botonesInferiores);
        centroContenido.setPadding(new Insets(20));
        root.setCenter(centroContenido);
    }

    /**
     * Actualiza la tabla de administradores con los datos más recientes.
     */
    private void actualizarTablaAdministradores() {
        List<Administrador> listaAdministradores = ExceptionHandler.handle(administradorDAO::getAllAdministradores, SQLException.class);
        ObservableList<Administrador> administradoresObservableList = FXCollections.observableArrayList(listaAdministradores);
        tablaAdministradores.setItems(administradoresObservableList);
    }

    /**
     * Muestra un formulario para agregar un nuevo administrador.
     */
    private void mostrarFormularioAgregarAdministrador() {
        Dialog<Administrador> dialog = new Dialog<>();
        dialog.setTitle("Agregar Administrador");
        dialog.setHeaderText("Ingrese los datos del nuevo administrador");

        ButtonType botonAgregarTipo = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(botonAgregarTipo, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombre = new TextField();
        nombre.setPromptText("Nombre");
        TextField correo = new TextField();
        correo.setPromptText("Correo");
        TextField nombreUsuario = new TextField();
        nombreUsuario.setPromptText("Nombre de Usuario");
        PasswordField contrasena = new PasswordField();
        contrasena.setPromptText("Contraseña");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombre, 1, 0);
        grid.add(new Label("Correo:"), 0, 1);
        grid.add(correo, 1, 1);
        grid.add(new Label("Nombre de Usuario:"), 0, 2);
        grid.add(nombreUsuario, 1, 2);
        grid.add(new Label("Contraseña:"), 0, 3);
        grid.add(contrasena, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == botonAgregarTipo) {
                if (nombre.getText().isEmpty() || correo.getText().isEmpty() || nombreUsuario.getText().isEmpty() || contrasena.getText().isEmpty()) {
                    mostrarMensaje(Alert.AlertType.WARNING, "Advertencia", "Todos los campos son obligatorios.");
                    return null;
                }
                Administrador nuevoAdministrador = new Administrador(0, nombre.getText(), correo.getText(), nombreUsuario.getText(), contrasena.getText());
                if (ExceptionHandler.handle(() -> administradorDAO.addAdministrador(nuevoAdministrador), SQLException.class)) {
                    actualizarTablaAdministradores();
                    return nuevoAdministrador;
                } else {
                    mostrarMensaje(Alert.AlertType.ERROR, "Error", "No se pudo agregar el administrador. Verifique que el correo y el nombre de usuario sean únicos.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    /**
     * Devuelve el layout principal del controlador.
     *
     * @return el layout principal
     */
    public static BorderPane getRoot() {
        return root;
    }
    /**
     * Muestra un mensaje en una ventana de diálogo.
     *
     * @param mensaje el mensaje a mostrar
     */
    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
