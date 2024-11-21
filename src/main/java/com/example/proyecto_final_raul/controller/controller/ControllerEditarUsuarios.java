package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.app.view.AppAdmin;
import com.example.proyecto_final_raul.model.UsuarioDAO;
import com.example.proyecto_final_raul.util.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controlador para la edición de usuarios.
 * Esta clase gestiona la vista y la lógica para editar los datos de los usuarios.
 * Incluye funcionalidades para agregar, actualizar, eliminar, importar y exportar usuarios.
 *
 * Este controlador se integra con la vista de administración de la aplicación
 * y proporciona métodos para interactuar con la base de datos de usuarios.
 *
 * @Autor: Raúl López
 */
public class ControllerEditarUsuarios {

    private AppAdmin appAdmin;
    private VBox root;
    private TableView<Usuario> tablaUsuarios;
    private UsuarioDAO usuarioDAO;

    // Define the application colors
    private final String primaryColor = "#400000";
    private final String secondaryColor = "#500000";
    private final String backgroundColor = "#EAEAEA";

    /**
     * Constructor del controlador que inicializa los componentes y crea la vista.
     * @param appAdmin Instancia de la aplicación principal.
     */
    public ControllerEditarUsuarios(AppAdmin appAdmin) {
        this.appAdmin = appAdmin;
        this.usuarioDAO = new UsuarioDAO();
        this.root = new VBox(20);
        crearVista();
    }

    /**
     * Método principal para crear la vista de edición de usuarios.
     * Configura la tabla de usuarios, sus columnas y los botones de acción.
     */
    private void crearVista() {
        // Crear la tabla de usuarios
        tablaUsuarios = new TableView<>();

        // Definir las columnas de la tabla
        TableColumn<Usuario, String> columnaIdUsuario = new TableColumn<>("ID Usuario");
        columnaIdUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId_usuario())));
        columnaIdUsuario.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaIdUsuario.setPrefWidth(100);

        TableColumn<Usuario, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnaNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaNombre.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setNombre(event.getNewValue());
            usuarioDAO.updateUsuario(usuario);
        });
        columnaNombre.setPrefWidth(100);

        TableColumn<Usuario, String> columnaApellido = new TableColumn<>("Apellido");
        columnaApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        columnaApellido.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaApellido.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setApellido(event.getNewValue());
            usuarioDAO.updateUsuario(usuario);
        });
        columnaApellido.setPrefWidth(100);

        TableColumn<Usuario, String> columnaCorreo = new TableColumn<>("Correo");
        columnaCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        columnaCorreo.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaCorreo.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setCorreo(event.getNewValue());
            usuarioDAO.updateUsuario(usuario);
        });
        columnaCorreo.setPrefWidth(150);

        TableColumn<Usuario, String> columnaPeso = new TableColumn<>("Peso");
        columnaPeso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPeso() + " kg"));
        columnaPeso.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaPeso.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            String newPeso = event.getNewValue();
            if (newPeso.endsWith(" kg")) {
                newPeso = newPeso.substring(0, newPeso.length() - 3).trim();
            }
            usuario.setPeso(newPeso);
            usuarioDAO.updateUsuario(usuario);
        });
        columnaPeso.setPrefWidth(100);

        TableColumn<Usuario, String> columnaAltura = new TableColumn<>("Altura");
        columnaAltura.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAltura() + " cm"));
        columnaAltura.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaAltura.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            String newAltura = event.getNewValue();
            if (newAltura.endsWith(" cm")) {
                newAltura = newAltura.substring(0, newAltura.length() - 3).trim();
            }
            usuario.setAltura(newAltura);
            usuarioDAO.updateUsuario(usuario);
        });
        columnaAltura.setPrefWidth(100);

        TableColumn<Usuario, String> columnaEdad = new TableColumn<>("Edad");
        columnaEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdad() + " años"));
        columnaEdad.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaEdad.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            String newEdad = event.getNewValue();
            if (newEdad.endsWith(" años")) {
                newEdad = newEdad.substring(0, newEdad.length() - 5).trim();
            }
            usuario.setEdad(newEdad);
            usuarioDAO.updateUsuario(usuario);
        });
        columnaEdad.setPrefWidth(100);



        TableColumn<Usuario, String> columnaNombreUsuario = new TableColumn<>("Nombre de Usuario");
        columnaNombreUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre_usuario()));
        columnaNombreUsuario.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaNombreUsuario.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setNombre_usuario(event.getNewValue());
            usuarioDAO.updateUsuario(usuario);
        });
        columnaNombreUsuario.setPrefWidth(150);

        TableColumn<Usuario, String> columnaContrasena = new TableColumn<>("Contraseña");
        columnaContrasena.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContrasena()));
        columnaContrasena.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaContrasena.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setContrasena(event.getNewValue());
            usuarioDAO.updateUsuario(usuario);
        });
        columnaContrasena.setPrefWidth(150);

        TableColumn<Usuario, String> columnaDetalles = new TableColumn<>("Detalles_Contacto");
        columnaDetalles.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetalles_contacto()));
        columnaDetalles.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaDetalles.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setDetalles_contacto(event.getNewValue());
            usuarioDAO.updateUsuario(usuario);
        });
        columnaDetalles.setPrefWidth(200);

        TableColumn<Usuario, String> columnaPreferencias = new TableColumn<>("Preferencias_Entrenamiento");
        columnaPreferencias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPreferencias_entrenamiento()));
        columnaPreferencias.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaPreferencias.setOnEditCommit(event -> {
            Usuario usuario = event.getRowValue();
            usuario.setPreferencias_entrenamiento(event.getNewValue());
            usuarioDAO.updateUsuario(usuario);
        });
        columnaPreferencias.setPrefWidth(200);

        tablaUsuarios.getColumns().addAll(
                columnaIdUsuario, columnaNombre, columnaApellido, columnaCorreo,
                columnaPeso, columnaAltura, columnaEdad, columnaNombreUsuario,
                columnaContrasena, columnaDetalles, columnaPreferencias
        );
        tablaUsuarios.setEditable(true);
        tablaUsuarios.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");

        // Asegurar que la tabla ocupa todo el espacio disponible
        VBox.setVgrow(tablaUsuarios, Priority.ALWAYS);

        actualizarTablaUsuarios();

        // Botón para eliminar usuario
        Button botonEliminarUsuario = new Button("Eliminar Usuario");
        botonEliminarUsuario.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        botonEliminarUsuario.setOnAction(event -> {
            Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
            if (usuarioSeleccionado != null) {
                if (usuarioDAO.getAllUsuarios().size() > 1) { // Asegúrate de que no sea el único usuario
                    ExceptionHandler.handle(() -> {
                        try {
                            usuarioDAO.borrarUsuario(usuarioSeleccionado.getId_usuario());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        actualizarTablaUsuarios();
                    }, SQLException.class);
                } else {
                    mostrarMensaje(Alert.AlertType.WARNING, "Advertencia", "No se puede eliminar el único usuario.");
                }
            } else {
                mostrarMensaje(Alert.AlertType.WARNING, "Advertencia", "Seleccione un usuario para eliminar.");
            }
        });


        Button botonGuardarUsuarios = new Button("Guardar Cambios");
        botonGuardarUsuarios.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        botonGuardarUsuarios.setOnAction(event -> {
            for (Usuario usuario : tablaUsuarios.getItems()) {
                usuarioDAO.updateUsuario(usuario);
            }
            mostrarMensaje(Alert.AlertType.INFORMATION, "Información", "Cambios guardados exitosamente.");
        });

        Button botonAgregarUsuario = new Button("Agregar Usuario");
        botonAgregarUsuario.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        botonAgregarUsuario.setOnAction(event -> mostrarFormularioAgregarUsuario());

        Button botonActualizarUsuarios = new Button("Actualizar Tabla");
        botonActualizarUsuarios.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        botonActualizarUsuarios.setOnAction(event -> actualizarTablaUsuarios());

        // Nuevos botones para importar y exportar datos XML
        Button botonImportarXML = new Button("Importar XML");
        botonImportarXML.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        botonImportarXML.setOnAction(event -> importarDatosXML());

        Button botonExportarXML = new Button("Exportar XML");
        botonExportarXML.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");
        botonExportarXML.setOnAction(event -> exportarDatosXML());

        HBox botonesUsuarios = new HBox(10, botonEliminarUsuario, botonGuardarUsuarios, botonAgregarUsuario, botonActualizarUsuarios, botonImportarXML, botonExportarXML);
        botonesUsuarios.setAlignment(Pos.CENTER);
        VBox.setVgrow(botonesUsuarios, Priority.NEVER); // Los botones no necesitan crecer

        Label tituloTabla = new Label("Tabla Usuarios");
        tituloTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + primaryColor + ";");


        VBox vboxUsuarios = new VBox(10, tituloTabla,tablaUsuarios, botonesUsuarios);
        vboxUsuarios.setPadding(new Insets(10));
        vboxUsuarios.setStyle("-fx-background-color: " + backgroundColor + ";");
        VBox.setVgrow(vboxUsuarios, Priority.ALWAYS);

        // Agregar secciones al root
        root.getChildren().add(vboxUsuarios);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: " + backgroundColor + ";");
        VBox.setVgrow(root, Priority.ALWAYS);
    }

    /**
     * Importa datos de usuarios desde un archivo XML.
     */
    private void importarDatosXML() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo XML para importar");
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            List<Usuario> usuariosNoValidos = new ArrayList<>();
            List<Usuario> usuariosImportados = usuarioDAO.importarUsuariosDesdeXML(file);

            for (Usuario usuario : usuariosImportados) {
                if (usuarioDAO.correoExiste(usuario.getCorreo()) || usuarioDAO.nombreUsuarioExiste(usuario.getNombre_usuario())) {
                    usuariosNoValidos.add(usuario);
                } else {
                    usuarioDAO.addUsuario(usuario);
                }
            }

            if (!usuariosNoValidos.isEmpty()) {
                FileChooser saveFileChooser = new FileChooser();
                saveFileChooser.setTitle("Guardar archivo XML con usuarios no válidos");
                saveFileChooser.setInitialFileName("usuarios_no_validos.xml");
                File invalidUsersFile = saveFileChooser.showSaveDialog(new Stage());

                if (invalidUsersFile != null) {
                    boolean exportResult = usuarioDAO.exportarUsuariosA_XML(invalidUsersFile, usuariosNoValidos);
                    if (exportResult) {
                        mostrarMensaje(Alert.AlertType.WARNING, "Advertencia", "Algunos usuarios no se importaron. Se han guardado en " + invalidUsersFile.getName());
                    } else {
                        mostrarMensaje(Alert.AlertType.ERROR, "Error", "Error al guardar los usuarios no válidos.");
                    }
                }
            }

            actualizarTablaUsuarios();
            mostrarMensaje(Alert.AlertType.INFORMATION, "Éxito", "Datos importados exitosamente.");
        }
    }

    /**
     * Exporta los datos de usuarios a un archivo XML.
     */
    private void exportarDatosXML() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo XML");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            List<Usuario> todosLosUsuarios = usuarioDAO.getAllUsuarios();
            boolean resultado = usuarioDAO.exportarUsuariosA_XML(file, todosLosUsuarios);
            if (resultado) {
                mostrarMensaje(Alert.AlertType.INFORMATION, "Éxito", "Datos exportados exitosamente.");
            } else {
                mostrarMensaje(Alert.AlertType.ERROR, "Error", "Error al exportar datos.");
            }
        }
    }

    /**
     * Actualiza los datos mostrados en la tabla de usuarios.
     */
    private void actualizarTablaUsuarios() {
        List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("No se encontraron usuarios.");
        } else {
            System.out.println("Usuarios cargados: " + usuarios.size());
        }
        ObservableList<Usuario> usuariosData = FXCollections.observableArrayList(usuarios);
        tablaUsuarios.setItems(usuariosData);
    }

    /**
     * Muestra un formulario para agregar un nuevo usuario.
     */
    private void mostrarFormularioAgregarUsuario() {
        Dialog<Usuario> dialogo = new Dialog<>();
        dialogo.setTitle("Agregar Usuario");
        dialogo.setHeaderText("Ingrese los detalles del nuevo usuario");

        ButtonType botonGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialogo.getDialogPane().getButtonTypes().addAll(botonGuardar, ButtonType.CANCEL);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        GridPane leftGrid = new GridPane();
        leftGrid.setHgap(10);
        leftGrid.setVgap(10);
        leftGrid.setPadding(new Insets(10));

        GridPane rightGrid = new GridPane();
        rightGrid.setHgap(10);
        rightGrid.setVgap(10);
        rightGrid.setPadding(new Insets(10));

        TextField campoNombre = new TextField();
        campoNombre.setPromptText("Nombre");
        TextField campoApellido = new TextField();
        campoApellido.setPromptText("Apellido");
        TextField campoCorreo = new TextField();
        campoCorreo.setPromptText("Correo");
        TextField campoPeso = new TextField();
        campoPeso.setPromptText("Peso");
        TextField campoAltura = new TextField();
        campoAltura.setPromptText("Altura");
        TextField campoEdad = new TextField();
        campoEdad.setPromptText("Edad");
        TextField campoNombreUsuario = new TextField();
        campoNombreUsuario.setPromptText("Nombre de Usuario");
        PasswordField campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Contraseña");
        TextField campoDetalles = new TextField();
        campoDetalles.setPromptText("Detalles de Contacto");
        TextField campoPreferencias = new TextField();
        campoPreferencias.setPromptText("Preferencias de Entrenamiento");

        leftGrid.add(new Label("Nombre:"), 0, 0);
        leftGrid.add(campoNombre, 1, 0);
        leftGrid.add(new Label("Apellido:"), 0, 1);
        leftGrid.add(campoApellido, 1, 1);
        leftGrid.add(new Label("Correo:"), 0, 2);
        leftGrid.add(campoCorreo, 1, 2);
        leftGrid.add(new Label("Peso:"), 0, 3);
        leftGrid.add(campoPeso, 1, 3);
        leftGrid.add(new Label("Altura:"), 0, 4);
        leftGrid.add(campoAltura, 1, 4);
        leftGrid.add(new Label("Edad:"), 0, 5);
        leftGrid.add(campoEdad, 1, 5);

        rightGrid.add(new Label("Nombre de Usuario:"), 0, 0);
        rightGrid.add(campoNombreUsuario, 1, 0);
        rightGrid.add(new Label("Contraseña:"), 0, 1);
        rightGrid.add(campoContrasena, 1, 1);
        rightGrid.add(new Label("Detalles de Contacto:"), 0, 2);
        rightGrid.add(campoDetalles, 1, 2);
        rightGrid.add(new Label("Preferencias de Entrenamiento:"), 0, 3);
        rightGrid.add(campoPreferencias, 1, 3);

        String gridBackgroundColorLeft = "-fx-background-color: #EAEAEA;";
        String gridBackgroundColorRight = "-fx-background-color: #EAEAEA;";
        String dialogBackgroundColor = "-fx-background-color: #EAEAEA;";
        String textStyle = "-fx-font-weight: bold; -fx-text-fill: #EAEAEA;";
        String buttonStyle = "-fx-background-color: #800000; -fx-text-fill: #EAEAEA;";

        leftGrid.setStyle(gridBackgroundColorLeft + textStyle);
        rightGrid.setStyle(gridBackgroundColorRight + textStyle);

        dialogo.getDialogPane().setStyle(dialogBackgroundColor);
        dialogo.getDialogPane().lookupButton(botonGuardar).setStyle(buttonStyle);
        dialogo.getDialogPane().lookupButton(ButtonType.CANCEL).setStyle(buttonStyle);

        root.setLeft(leftGrid);
        root.setCenter(rightGrid);

        dialogo.getDialogPane().setContent(root);

        Node botonGuardarNode = dialogo.getDialogPane().lookupButton(botonGuardar);
        botonGuardarNode.setDisable(true);

        campoNombreUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            botonGuardarNode.setDisable(campoNombreUsuario.getText().trim().isEmpty() || campoContrasena.getText().trim().isEmpty());
        });

        campoContrasena.textProperty().addListener((observable, oldValue, newValue) -> {
            botonGuardarNode.setDisable(campoNombreUsuario.getText().trim().isEmpty() || campoContrasena.getText().trim().isEmpty());
        });

        dialogo.setResultConverter(dialogButton -> {
            if (dialogButton == botonGuardar) {
                // Validaciones de datos
                if (!ExceptionHandler.esAlfabetico(campoNombre.getText()) || campoNombre.getText().isEmpty()) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Nombre inválido"));
                    return null;
                }
                if (!ExceptionHandler.esAlfabetico(campoApellido.getText()) || campoApellido.getText().isEmpty()) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Apellido inválido"));
                    return null;
                }
                if (!ExceptionHandler.esCorreoValido(campoCorreo.getText()) || campoCorreo.getText().isEmpty()) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Correo inválido"));
                    return null;
                }
                if (!ExceptionHandler.esNumerico(campoPeso.getText()) || campoPeso.getText().isEmpty()) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Peso inválido"));
                    return null;
                }
                double peso = Double.parseDouble(campoPeso.getText());
                if (peso < 30 || peso > 300) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Peso fuera de rango (30-300 kg)"));
                    return null;
                }
                if (!ExceptionHandler.esNumerico(campoAltura.getText()) || campoAltura.getText().isEmpty()) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Altura inválida"));
                    return null;
                }
                double altura = Double.parseDouble(campoAltura.getText());
                if (altura < 50 || altura > 250) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Altura fuera de rango (50-250 cm)"));
                    return null;
                }
                if (!ExceptionHandler.esNumerico(campoEdad.getText()) || campoEdad.getText().isEmpty()) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Edad inválida"));
                    return null;
                }
                int edad = Integer.parseInt(campoEdad.getText());
                if (edad < 0 || edad > 120) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Edad fuera de rango (0-120 años)"));
                    return null;
                }
                if (campoNombreUsuario.getText().isEmpty()) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Nombre de Usuario es requerido"));
                    return null;
                }
                if (campoContrasena.getText().isEmpty()) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("Contraseña es requerida"));
                    return null;
                }

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                if (usuarioDAO.correoExiste(campoCorreo.getText())) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("El correo ya está en uso"));
                    return null;
                }
                if (usuarioDAO.nombreUsuarioExiste(campoNombreUsuario.getText())) {
                    ExceptionHandler.mostrarMensajeError(new IllegalArgumentException("El nombre de usuario ya está en uso"));
                    return null;
                }

                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre(campoNombre.getText());
                nuevoUsuario.setApellido(campoApellido.getText());
                nuevoUsuario.setCorreo(campoCorreo.getText());
                nuevoUsuario.setPeso(campoPeso.getText());
                nuevoUsuario.setAltura(campoAltura.getText());
                nuevoUsuario.setEdad(campoEdad.getText());
                nuevoUsuario.setNombre_usuario(campoNombreUsuario.getText());
                nuevoUsuario.setContrasena(campoContrasena.getText());
                nuevoUsuario.setDetalles_contacto(campoDetalles.getText());
                nuevoUsuario.setPreferencias_entrenamiento(campoPreferencias.getText());

                try {
                    usuarioDAO.addUsuario(nuevoUsuario);
                    actualizarTablaUsuarios();
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Éxito", "Usuario agregado exitosamente.");
                } catch (Exception e) {
                    ExceptionHandler.mostrarMensajeError(e);
                    return null;
                }

                return nuevoUsuario;
            }
            return null;
        });

        dialogo.showAndWait();
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * @param tipo Tipo de alerta.
     * @param titulo Título del mensaje.
     * @param mensaje Contenido del mensaje.
     */
    private void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Obtiene el contenedor raíz de la vista.
     * @return Contenedor VBox con la vista principal.
     */
    public VBox getRoot() {
        return root;
    }
}