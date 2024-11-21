package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.app.view.AppAdmin;
import com.example.proyecto_final_raul.model.EjercicioDAO;
import com.example.proyecto_final_raul.model.ProgresoUsuarioDAO;
import com.example.proyecto_final_raul.model.UsuarioDAO;
import com.example.proyecto_final_raul.util.Ejercicio;
import com.example.proyecto_final_raul.util.ProgresoUsuario;
import com.example.proyecto_final_raul.util.Usuario;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controlador para gestionar la creación y gestión de progresos de usuarios.
 * Esta clase se encarga de manejar la interfaz de usuario y las interacciones relacionadas con los progresos.
 *
 * @author Raul Lopez
 */
public class ControllerCrearProgresos {
    private AppAdmin appAdmin;
    private final String primaryColor = "#400000";
    private final String secondaryColor = "#500000";
    private static BorderPane root;
    VBox layout = new VBox(20);
    private Usuario usuarioActual;

    /**
     * Constructor para inicializar el controlador con la instancia dada de AppAdmin.
     * También configura la vista inicial.
     *
     * @param appAdmin la instancia de la aplicación administrativa
     */
    public ControllerCrearProgresos(AppAdmin appAdmin) {
        this.appAdmin = appAdmin;
        this.root = new BorderPane();
        crearVista();
    }

    /**
     * Crea el diseño principal de la vista y los componentes de la interfaz de usuario.
     */
    private void crearVista() {
        layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Crear el Label y aplicarle estilo
        Label tituloTabla = new Label("Tabla Progresos");
        tituloTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + primaryColor + ";");

        // Crear un contenedor para el Label y alinearlo a la izquierda
        HBox hboxTitulo = new HBox(tituloTabla);
        hboxTitulo.setAlignment(Pos.TOP_LEFT);
        hboxTitulo.setPadding(new Insets(10));

        // Añadir el contenedor al layout principal
        layout.getChildren().add(0, hboxTitulo);

        mostrarVentanaUsuario();

        // Mostrar la lista de ejercicios
        mostrarListaEjercicios(layout);

        // Botones para las acciones
        Button botonAgregar = new Button("Agregar Progreso");
        Button botonActualizar = new Button("Actualizar Progreso");
        Button botonEliminar = new Button("Eliminar Progreso");
        Button botonListar = new Button("Listar Progresos");
        Button botonCambiarUsuario = new Button("Cambiar Usuario");

        // Establecer estilo para los botones
        botonAgregar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonEliminar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonActualizar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonListar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonCambiarUsuario.setStyle("-fx-background-color: " + secondaryColor + "; -fx-text-fill: white;");

        // Asignar acciones a los botones utilizando el manejador de excepciones
        botonAgregar.setOnAction(event -> ExceptionHandler.handle(this::mostrarFormularioAgregar));
        botonActualizar.setOnAction(event -> ExceptionHandler.handle(this::mostrarFormularioActualizar));
        botonEliminar.setOnAction(event -> ExceptionHandler.handle(this::mostrarFormularioEliminar));
        botonListar.setOnAction(event -> ExceptionHandler.handle(this::mostrarListaProgresos));
        botonCambiarUsuario.setOnAction(event -> mostrarVentanaUsuario());

        HBox botones = new HBox(10, botonAgregar, botonActualizar, botonEliminar, botonListar, botonCambiarUsuario);
        botones.setAlignment(Pos.CENTER);
        layout.getChildren().add(botones);

        VBox.setVgrow(root, Priority.ALWAYS);
        root.setCenter(layout);
    }

    /**
     * Muestra una ventana modal para seleccionar un usuario.
     * Permite buscar un usuario por su ID.
     */
    private void mostrarVentanaUsuario() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Seleccionar Usuario");

        VBox usuarioLayout = new VBox(10);
        usuarioLayout.setPadding(new Insets(20));
        usuarioLayout.setAlignment(Pos.CENTER);

        TextField campoIdUsuario = new TextField();
        campoIdUsuario.setPromptText("ID Usuario");

        Button botonBuscar = new Button("Buscar");
        botonBuscar.setOnAction(event -> {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            ExceptionHandler.handle(() -> {
                int idUsuario = Integer.parseInt(campoIdUsuario.getText());
                Usuario usuario = usuarioDAO.getUsuarioById(idUsuario);
                if (usuario != null) {
                    usuarioActual = usuario;
                    mostrarMensaje("Usuario seleccionado: " + usuario.getNombre() + " " + usuario.getApellido());
                    dialog.close();
                } else {
                    mostrarMensaje("El usuario con ID " + idUsuario + " no existe.");
                }
                return null;
            });
        });

        usuarioLayout.getChildren().addAll(new Label("ID Usuario:"), campoIdUsuario, botonBuscar);
        Scene scene = new Scene(usuarioLayout);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     * Muestra la lista de ejercicios en una tabla.
     * Esta tabla muestra varios detalles de los ejercicios.
     *
     * @param layout el diseño al que se añade la tabla
     */
    private void mostrarListaEjercicios(VBox layout) {
        VBox ejerciciosLayout = new VBox(10);
        ejerciciosLayout.setPadding(new Insets(10));
        ejerciciosLayout.setAlignment(Pos.CENTER);
        ejerciciosLayout.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");

        EjercicioDAO ejercicioDAO = new EjercicioDAO();
        List<Ejercicio> ejercicios = ExceptionHandler.handle(ejercicioDAO::getAllEjercicios);

        if (ejercicios == null || ejercicios.isEmpty()) {
            ejerciciosLayout.getChildren().add(new Label("No hay ejercicios registrados."));
        } else {
            TableView<Ejercicio> table = new TableView<>();
            TableColumn<Ejercicio, Integer> colIdEjercicio = new TableColumn<>("ID Ejercicio");
            TableColumn<Ejercicio, String> colNombre = new TableColumn<>("Nombre");
            TableColumn<Ejercicio, String> colDescripcion = new TableColumn<>("Descripción");
            TableColumn<Ejercicio, String> colGrupoMuscular = new TableColumn<>("Grupo Muscular");

            table.getColumns().addAll(colIdEjercicio, colNombre, colDescripcion, colGrupoMuscular);

            colIdEjercicio.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_ejercicio()).asObject());
            colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
            colGrupoMuscular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));

            table.getItems().addAll(ejercicios);

            VBox.setVgrow(table, Priority.ALWAYS);
            ejerciciosLayout.getChildren().add(table);
        }

        VBox.setVgrow(ejerciciosLayout, Priority.ALWAYS);
        layout.getChildren().add(ejerciciosLayout);
        VBox.setVgrow(layout, Priority.ALWAYS);
    }

    /**
     * Muestra un formulario para agregar una nueva entrada de progreso.
     * Este formulario recopila varios detalles sobre el progreso.
     */
    private void mostrarFormularioAgregar() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Agregar Progreso");

        GridPane formulario = new GridPane();
        formulario.setPadding(new Insets(20));
        formulario.setHgap(10);
        formulario.setVgap(10);

        TextField campoIdEjercicio = new TextField();
        campoIdEjercicio.setPromptText("ID Ejercicio");
        TextField campoSeriesCompletadas = new TextField();
        campoSeriesCompletadas.setPromptText("Series Completadas");
        TextField campoRepeticionesCompletadas = new TextField();
        campoRepeticionesCompletadas.setPromptText("Repeticiones Completadas");
        TextField campoDuracion = new TextField();
        campoDuracion.setPromptText("Duración");
        TextField campoRegistradoEn = new TextField();
        campoRegistradoEn.setPromptText("Registrado En");

        formulario.add(new Label("ID Ejercicio:"), 0, 0);
        formulario.add(campoIdEjercicio, 1, 0);
        formulario.add(new Label("Series Completadas:"), 0, 1);
        formulario.add(campoSeriesCompletadas, 1, 1);
        formulario.add(new Label("Repeticiones Completadas:"), 0, 2);
        formulario.add(campoRepeticionesCompletadas, 1, 2);
        formulario.add(new Label("Duración:"), 0, 3);
        formulario.add(campoDuracion, 1, 3);
        formulario.add(new Label("Registrado En:"), 0, 4);
        formulario.add(campoRegistradoEn, 1, 4);

        Button botonGuardar = new Button("Guardar");
        botonGuardar.setOnAction(event -> {
            ProgresoUsuarioDAO progresoUsuarioDAO = new ProgresoUsuarioDAO();

            ExceptionHandler.handle(() -> {
                int idEjercicio = Integer.parseInt(campoIdEjercicio.getText());

                if (usuarioActual != null) {
                    ProgresoUsuario progresoUsuario = new ProgresoUsuario(
                            0,
                            usuarioActual.getId_usuario(),
                            idEjercicio,
                            usuarioActual.getNombre(),
                            usuarioActual.getApellido(),
                            Integer.parseInt(campoSeriesCompletadas.getText()),
                            Integer.parseInt(campoRepeticionesCompletadas.getText()),
                            Integer.parseInt(campoDuracion.getText()),
                            campoRegistradoEn.getText()
                    );
                    progresoUsuarioDAO.addProgresoUsuario(progresoUsuario);
                    mostrarMensaje("Progreso guardado con éxito.");
                } else {
                    mostrarMensaje("Por favor, selecciona un usuario primero.");
                }
                return null;
            });
        });

        formulario.add(botonGuardar, 1, 5);

        Scene scene = new Scene(formulario);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     * Muestra un formulario para actualizar una entrada de progreso existente.
     * Este formulario recopila el ID del progreso a actualizar y los nuevos detalles.
     */
    private void mostrarFormularioActualizar() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Actualizar Progreso");

        GridPane formulario = new GridPane();
        formulario.setPadding(new Insets(20));
        formulario.setHgap(10);
        formulario.setVgap(10);

        TextField campoIdProgreso = new TextField();
        campoIdProgreso.setPromptText("ID Progreso");
        TextField campoSeriesCompletadas = new TextField();
        campoSeriesCompletadas.setPromptText("Series Completadas");
        TextField campoRepeticionesCompletadas = new TextField();
        campoRepeticionesCompletadas.setPromptText("Repeticiones Completadas");
        TextField campoDuracion = new TextField();
        campoDuracion.setPromptText("Duración");

        formulario.add(new Label("ID Progreso:"), 0, 0);
        formulario.add(campoIdProgreso, 1, 0);
        formulario.add(new Label("Series Completadas:"), 0, 1);
        formulario.add(campoSeriesCompletadas, 1, 1);
        formulario.add(new Label("Repeticiones Completadas:"), 0, 2);
        formulario.add(campoRepeticionesCompletadas, 1, 2);
        formulario.add(new Label("Duración:"), 0, 3);
        formulario.add(campoDuracion, 1, 3);

        Button botonGuardar = new Button("Guardar");
        botonGuardar.setOnAction(event -> {
            ProgresoUsuarioDAO progresoUsuarioDAO = new ProgresoUsuarioDAO();

            ExceptionHandler.handle(() -> {
                int idProgreso = Integer.parseInt(campoIdProgreso.getText());

                ProgresoUsuario progresoUsuario = progresoUsuarioDAO.getProgresoUsuarioById(idProgreso);

                if (progresoUsuario != null) {
                    progresoUsuario.setSeries_completadas(Integer.parseInt(campoSeriesCompletadas.getText()));
                    progresoUsuario.setRepeticiones_completadas(Integer.parseInt(campoRepeticionesCompletadas.getText()));
                    progresoUsuario.setDuracion(Integer.parseInt(campoDuracion.getText()));

                    progresoUsuarioDAO.updateProgresoUsuario(progresoUsuario);
                    mostrarMensaje("Progreso actualizado con éxito.");
                } else {
                    mostrarMensaje("Progreso con ID " + idProgreso + " no encontrado.");
                }
                return null;
            });
        });

        formulario.add(botonGuardar, 1, 4);

        Scene scene = new Scene(formulario);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     * Muestra un formulario para eliminar una entrada de progreso existente.
     * Este formulario recopila el ID del progreso a eliminar.
     */
    private void mostrarFormularioEliminar() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Eliminar Progreso");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        TextField campoIdProgreso = new TextField();
        campoIdProgreso.setPromptText("ID Progreso");

        Button botonEliminar = new Button("Eliminar");
        botonEliminar.setOnAction(event -> {
            ProgresoUsuarioDAO progresoUsuarioDAO = new ProgresoUsuarioDAO();

            ExceptionHandler.handle(() -> {
                int idProgreso = Integer.parseInt(campoIdProgreso.getText());

                if (progresoUsuarioDAO.deleteProgresoUsuario(idProgreso)) {
                    mostrarMensaje("Progreso eliminado con éxito.");
                } else {
                    mostrarMensaje("Progreso con ID " + idProgreso + " no encontrado.");
                }
                return null;
            });
        });

        layout.getChildren().addAll(new Label("ID Progreso:"), campoIdProgreso, botonEliminar);

        Scene scene = new Scene(layout);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     * Muestra una lista de todas las entradas de progreso para el usuario actual.
     * Esta lista muestra varios detalles de cada entrada de progreso.
     */
    private void mostrarListaProgresos() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        ProgresoUsuarioDAO progresoUsuarioDAO = new ProgresoUsuarioDAO();
        List<ProgresoUsuario> progresos = ExceptionHandler.handle(progresoUsuarioDAO::getAllProgresosUsuario);

        if (progresos == null || progresos.isEmpty()) {
            layout.getChildren().add(new Label("No hay progresos registrados."));
        } else {
            TableView<ProgresoUsuario> table = new TableView<>();
            TableColumn<ProgresoUsuario, Integer> colIdProgreso = new TableColumn<>("ID Progreso");
            TableColumn<ProgresoUsuario, Integer> colIdUsuario = new TableColumn<>("ID Usuario");
            TableColumn<ProgresoUsuario, Integer> colIdEjercicio = new TableColumn<>("ID Ejercicio");
            TableColumn<ProgresoUsuario, String> colNombre = new TableColumn<>("Nombre");
            TableColumn<ProgresoUsuario, String> colApellido = new TableColumn<>("Apellido");
            TableColumn<ProgresoUsuario, Integer> colSeriesCompletadas = new TableColumn<>("Series Completadas");
            TableColumn<ProgresoUsuario, Integer> colRepeticionesCompletadas = new TableColumn<>("Repeticiones Completadas");
            TableColumn<ProgresoUsuario, Integer> colDuracion = new TableColumn<>("Duración");
            TableColumn<ProgresoUsuario, String> colRegistradoEn = new TableColumn<>("Registrado En");

            table.getColumns().addAll(colIdProgreso, colIdUsuario, colIdEjercicio, colNombre, colApellido, colSeriesCompletadas, colRepeticionesCompletadas, colDuracion, colRegistradoEn);

            colIdProgreso.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_progreso()).asObject());
            colIdUsuario.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_usuario()).asObject());
            colIdEjercicio.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_ejercicio()).asObject());
            colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
            colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
            colSeriesCompletadas.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSeries_completadas()).asObject());
            colRepeticionesCompletadas.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRepeticiones_completadas()).asObject());
            colDuracion.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDuracion()).asObject());
            colRegistradoEn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegistrado_en()));


            colSeriesCompletadas.setCellFactory(column -> new TableCell<ProgresoUsuario, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item + " series");
                    }
                }
            });

            colRepeticionesCompletadas.setCellFactory(column -> new TableCell<ProgresoUsuario, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item + " repeticiones");
                    }
                }
            });

            colDuracion.setCellFactory(column -> new TableCell<ProgresoUsuario, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item + " min");
                    }
                }
            });

            table.getItems().addAll(progresos);

            VBox.setVgrow(table, Priority.ALWAYS);
            layout.getChildren().add(table);

            Button botonAtras = new Button("Atrás");
            botonAtras.setOnAction(event -> crearVista()); // Cambia de pestaña al valor anterior (puede ser otro contenido)

            // Botón para eliminar una fila
            Button botonEliminarFila = new Button("Eliminar Fila");
            botonEliminarFila.setOnAction(event -> {
                ProgresoUsuario progresoSeleccionado = table.getSelectionModel().getSelectedItem();
                if (progresoSeleccionado != null) {
                    ExceptionHandler.handle(() -> {
                        if (progresoUsuarioDAO.deleteProgresoUsuario(progresoSeleccionado.getId_progreso())) {
                            table.getItems().remove(progresoSeleccionado);
                            mostrarMensaje("Progreso eliminado exitosamente");
                        } else {
                            mostrarMensaje("Error al eliminar el progreso");
                        }
                        return null;
                    });
                } else {
                    mostrarMensaje("Seleccione un progreso para eliminar.");
                }
            });

            // Botón para limpiar la lista
            Button botonLimpiarLista = new Button("Limpiar Lista");
            botonLimpiarLista.setOnAction(event -> table.getItems().clear());

            HBox botones = new HBox(10, botonEliminarFila, botonLimpiarLista, botonAtras);
            botones.setAlignment(Pos.CENTER);

            layout.getChildren().add(botones);
        }

        VBox.setVgrow(layout, Priority.ALWAYS);
        root.setCenter(layout);
    }


    /**
     * Muestra un mensaje en una ventana emergente.
     *
     * @param mensaje el mensaje a mostrar
     */
    private void mostrarMensaje(String mensaje) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Mensaje");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label etiquetaMensaje = new Label(mensaje);

        Button botonCerrar = new Button("Cerrar");
        botonCerrar.setOnAction(event -> dialog.close());

        layout.getChildren().addAll(etiquetaMensaje, botonCerrar);

        Scene scene = new Scene(layout);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     * Devuelve el diseño principal de la vista.
     *
     * @return el diseño principal
     */
    public static BorderPane getRoot() {
        return root;
    }
}
