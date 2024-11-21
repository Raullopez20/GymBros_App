package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.app.view.AppAdmin;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.model.ReservaClaseDAO;
import com.example.proyecto_final_raul.util.ReservaClase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class ControllerEditarHorario {

    private final String primaryColor = "#400000";
    private final String secondaryColor = "#500000";
    private AppAdmin appAdmin;
    private static BorderPane root;
    private TableView<ObservableList<String>> tablaHorarios;
    private ComboBox<String> comboBoxClase;
    private TableView<ReservaClase> tablaReservas;
    private boolean datosCargados = false;

    public ControllerEditarHorario(AppAdmin appAdmin) {
        this.appAdmin = appAdmin;
        this.root = new BorderPane();
        inicializarDatosHorarios();
        crearVista();
    }

    private void crearVista() {
        tablaHorarios = new TableView<>();
        tablaHorarios.setEditable(true);


        String[] diasSemana = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        String[] coloresColumnas = {"#EAEAEA", "#D3D3D3", "#EAEAEA", "#D3D3D3", "#EAEAEA", "#D3D3D3", "#EAEAEA", "#D3D3D3"};

        for (int i = 0; i < diasSemana.length; i++) {
            TableColumn<ObservableList<String>, String> columna = new TableColumn<>(diasSemana[i]);
            int colIndex = i;
            columna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(colIndex)));
            columna.setCellFactory(TextFieldTableCell.forTableColumn());
            columna.setOnEditCommit(event -> event.getRowValue().set(colIndex, event.getNewValue()));
            columna.setPrefWidth(150);
            columna.setStyle("-fx-background-color: " + coloresColumnas[i] + ";");
            tablaHorarios.getColumns().add(columna);
        }

        if (!datosCargados) {
            ObservableList<ObservableList<String>> datos = ExceptionHandler.handle(this::obtenerDatosHorarios, SQLException.class);
            if (datos != null) {
                tablaHorarios.setItems(datos);
                datosCargados = true;
            }
        }

        tablaHorarios.setStyle("-fx-background-color: #F0F0F0; -fx-control-inner-background: #F0F0F0;");

        Button botonGuardar = new Button("Guardar");
        botonGuardar.setOnAction(event -> ExceptionHandler.handle(() -> {
            guardarDatosHorarios();
            return null;
        }, SQLException.class));

        Button botonAgregar = new Button("Agregar Fila");
        botonAgregar.setOnAction(event -> agregarFila());

        Button botonEliminar = new Button("Eliminar Fila");
        botonEliminar.setOnAction(event -> eliminarFila());

        Button botonRestablecerBase = new Button("Restablecer Horario Base");
        botonRestablecerBase.setOnAction(event -> ExceptionHandler.handle(() -> {
            restablecerHorarioBase();
            return null;
        }, SQLException.class));

        botonGuardar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonAgregar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonEliminar.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonRestablecerBase.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");

        HBox layoutBotones = new HBox(10, botonGuardar, botonAgregar, botonEliminar, botonRestablecerBase);
        layoutBotones.setPadding(new Insets(10));
        layoutBotones.setAlignment(Pos.CENTER);

        Label tituloTabla = new Label("Tabla Horario");
        tituloTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + primaryColor + ";");


        tablaHorarios.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");
        VBox layoutPrincipal = new VBox(10, tituloTabla,tablaHorarios, layoutBotones);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setPrefHeight(800);

        // Crear el formulario de reserva
        VBox formularioReserva = crearFormularioReserva();

        // Crear la tabla de reservas
        VBox tablaReservasLayout = crearTablaReservas();

        // Crear el formulario para agregar una nueva clase
        VBox formularioAgregarClase = crearFormularioAgregarClase();

        Button botonGuardarReservas = new Button("Guardar");
        botonGuardarReservas.setOnAction(event -> {
            try {
                guardarDatosReservas();
            } catch (SQLException e) {
                mostrarAlerta("Error al guardar las reservas: " + e.getMessage());
                e.printStackTrace();
            }
        });

        Button botonAgregarReservas = new Button("Agregar Fila");
        botonAgregarReservas.setOnAction(event -> agregarFilaReserva());

        Button botonEliminarReservas = new Button("Eliminar Fila");
        botonEliminarReservas.setOnAction(event -> eliminarFilaReserva());

        Button botonRestablecerBaseReservas = new Button("Limpiar Tabla de Reservas");
        botonRestablecerBaseReservas.setOnAction(event -> ExceptionHandler.handle(() -> {
            limpiarTablaReservas();
            return null;
        }, SQLException.class));

        botonGuardarReservas.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonAgregarReservas.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonEliminarReservas.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        botonRestablecerBaseReservas.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");

        HBox layoutTablaReservas = new HBox(20, formularioReserva, tablaReservasLayout, formularioAgregarClase);
        layoutTablaReservas.setPadding(new Insets(20));

        HBox layoutBotonesReservas = new HBox(10, botonGuardarReservas, botonAgregarReservas, botonEliminarReservas, botonRestablecerBaseReservas);
        layoutBotonesReservas.setPadding(new Insets(10));
        layoutBotonesReservas.setAlignment(Pos.BOTTOM_CENTER);

        HBox layoutInferior = new HBox(20,layoutTablaReservas);
        layoutInferior.setPadding(new Insets(20));
        layoutInferior.setAlignment(Pos.CENTER);

        VBox layoutCompleto = new VBox(20, layoutPrincipal, layoutInferior, layoutBotonesReservas);
        layoutCompleto.setPadding(new Insets(20));

        root.setCenter(layoutCompleto);
    }

    private VBox crearFormularioReserva() {
        Label labelUsuario = new Label("Nombre de Usuario:");
        TextField textFieldUsuario = new TextField();

        Label labelClase = new Label("Nombre de la Clase:");
        ComboBox<String> comboBoxClase = new ComboBox<>();
        comboBoxClase.getItems().addAll(
                "Yoga", "Pilates", "HIIT", "Spinning", "CrossFit", "Zumba", "Gimnasio Abierto", "Cerrado"
        );

        Label labelHora = new Label("Hora de la clase:");
        ComboBox<String> comboBoxHora = new ComboBox<>();
        comboBoxHora.getItems().addAll(
                "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00"
        );

        Label labelDia = new Label("Día de la clase:");
        ComboBox<String> comboBoxDia = new ComboBox<>();
        comboBoxDia.getItems().addAll(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
        );

        Button botonReservar = new Button("Reservar Clase");
        botonReservar.setOnAction(event -> {
            String nombreUsuario = textFieldUsuario.getText();
            String nombreClase = comboBoxClase.getValue();
            String horaClase = comboBoxHora.getValue();
            String diaClase = comboBoxDia.getValue();

            if (!usuarioExiste(nombreUsuario)) {
                mostrarAlerta("El usuario especificado no existe.");
                return;
            }

            if (nombreUsuario.isEmpty() || nombreClase == null || nombreClase.isEmpty() || horaClase == null || horaClase.isEmpty() || diaClase == null || diaClase.isEmpty()) {
                mostrarAlerta("Todos los campos son obligatorios.");
                return;
            }

            ReservaClaseDAO reservaDAO = new ReservaClaseDAO();
            ReservaClase reservaClase = new ReservaClase();
            reservaClase.setNombre_usuario(nombreUsuario);
            reservaClase.setTitulo(nombreClase);
            reservaClase.setHora(horaClase);
            reservaClase.setDia(diaClase);
            reservaClase.setReservado_en(new java.sql.Timestamp(new java.util.Date().getTime()).toString());

            try (Connection conn = ConexionBD.getConnection()) {
                String selectQuery = "SELECT id_clase FROM clases WHERE titulo = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                    selectStmt.setString(1, nombreClase);
                    ResultSet rs = selectStmt.executeQuery();
                    if (rs.next()) {
                        int idClase = rs.getInt("id_clase");
                        reservaClase.setId_clase(idClase);

                        if (reservaDAO.addReservaClase(reservaClase)) {
                            mostrarAlerta("Clase reservada con éxito.");
                            actualizarTablaReservas();
                        } else {
                            mostrarAlerta("No se pudo reservar la clase. Inténtalo de nuevo.");
                        }
                    } else {
                        mostrarAlerta("La clase especificada no existe.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error al reservar la clase.");
            }
        });

        VBox layout = new VBox(10, labelUsuario, textFieldUsuario, labelClase, comboBoxClase, labelHora, comboBoxHora, labelDia, comboBoxDia, botonReservar);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);

        layout.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");

        return layout;
    }


    private VBox crearFormularioAgregarClase() {
        Label labelTituloClase = new Label("Título de la Clase:");
        TextField textFieldTituloClase = new TextField();

        Label labelHoraInicioClase = new Label("Hora de Inicio de la Clase:");
        ComboBox<String> comboBoxHoraInicioClase = new ComboBox<>();
        comboBoxHoraInicioClase.getItems().addAll(
                "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00"
        );

        Button botonAgregarClase = new Button("Agregar Clase");
        botonAgregarClase.setOnAction(event -> {
            String tituloClase = textFieldTituloClase.getText();
            String inicioClase = comboBoxHoraInicioClase.getValue();

            if (tituloClase.isEmpty() || inicioClase == null || inicioClase.isEmpty()) {
                mostrarAlerta("Todos los campos son obligatorios.");
                return;
            }

            try (Connection conn = ConexionBD.getConnection()) {
                String insertQuery = "INSERT INTO clases (titulo, inicio, id_entrenador) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    stmt.setString(1, tituloClase);
                    stmt.setString(2, inicioClase);
                    stmt.setInt(3, obtenerIdEntrenador()); // Aquí deberías proporcionar el id del entrenador adecuado
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        mostrarAlerta("Clase agregada con éxito.");
                    } else {
                        mostrarAlerta("No se pudo agregar la clase. Inténtalo de nuevo.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error al agregar la clase.");
            }
        });

        VBox layout = new VBox(10, labelTituloClase, textFieldTituloClase, labelHoraInicioClase, comboBoxHoraInicioClase, botonAgregarClase);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);
        layout.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");

        return layout;
    }

    private int obtenerIdEntrenador() {
        // Lista de ids de entrenadores disponibles
        int[] idsEntrenadores = {1, 2, 3, 4, 5, 6};

        // Generar un índice aleatorio para seleccionar un id de entrenador
        Random random = new Random();
        int indiceAleatorio = random.nextInt(idsEntrenadores.length);

        // Retornar el id de entrenador correspondiente al índice aleatorio
        return idsEntrenadores[indiceAleatorio];
    }

    // Método para verificar si un usuario existe en la base de datos
    private boolean usuarioExiste(String nombreUsuario) {
        try (Connection conn = ConexionBD.getConnection()) {
            String selectQuery = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, nombreUsuario);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    return rs.next(); // Devuelve true si se encuentra al menos un usuario con el nombre especificado
                }
            }
        } catch (SQLException e) {
            mostrarAlerta("Error al verificar la existencia del usuario.");
            e.printStackTrace();
            return false;
        }
    }

    private VBox crearTablaReservas() {
        tablaReservas = new TableView<>();

        TableColumn<ReservaClase, String> columnaId = new TableColumn<>("ID");
        columnaId.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId_reserva())));
        columnaId.setPrefWidth(150);

        TableColumn<ReservaClase, String> columnaUsuario = new TableColumn<>("Usuario");
        columnaUsuario.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre_usuario()));
        columnaUsuario.setPrefWidth(150);

        TableColumn<ReservaClase, String> columnaClase = new TableColumn<>("Clase");
        columnaClase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        columnaClase.setPrefWidth(150);

        TableColumn<ReservaClase, String> columnaHora = new TableColumn<>("Hora");
        columnaHora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHora()));
        columnaHora.setPrefWidth(150);

        TableColumn<ReservaClase, String> columnaDia = new TableColumn<>("Día");
        columnaDia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDia()));
        columnaDia.setPrefWidth(150);

        TableColumn<ReservaClase, String> columnaReserva = new TableColumn<>("Reservado_en");
        columnaReserva.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReservado_en()));
        columnaReserva.setPrefWidth(200);

        tablaReservas.getColumns().addAll(columnaId, columnaUsuario, columnaClase, columnaHora, columnaDia, columnaReserva);

        actualizarTablaReservas();

        Label tituloTabla = new Label("Reservas");
        tituloTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + primaryColor + ";");


        VBox layout = new VBox(10, tituloTabla,tablaReservas);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");

        return layout;
    }

    private void actualizarTablaReservas() {
        ReservaClaseDAO reservaDAO = new ReservaClaseDAO();
        List<ReservaClase> reservas = reservaDAO.getAllReservasClase();
        ObservableList<ReservaClase> reservasObservable = FXCollections.observableArrayList(reservas);
        tablaReservas.setItems(reservasObservable);
    }

    private void inicializarDatosHorarios() {
        ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String selectQuery = "SELECT valor FROM configuracion WHERE clave = 'datos_horarios_inicializados'";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                    ResultSet rs = selectStmt.executeQuery();
                    if (rs.next() && "true".equals(rs.getString("valor"))) {
                        return null;
                    }
                }

                insertarDatosBase(conn);

                String updateQuery = "UPDATE configuracion SET valor = 'true' WHERE clave = 'datos_horarios_inicializados'";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.executeUpdate();
                }
            }
            return null;
        }, SQLException.class);
    }

    private void insertarDatosBase(Connection conn) throws SQLException {
        String[][] datos = {
                {"6:00", "Yoga", "Pilates", "HIIT", "Yoga", "Pilates", "Spinning", "Cerrado"},
                {"7:00", "Spinning", "CrossFit", "Spinning", "CrossFit", "Spinning", "Yoga", "Cerrado"},
                {"8:00", "CrossFit", "Zumba", "CrossFit", "Zumba", "CrossFit", "HIIT", "Cerrado"},
                {"9:00", "HIIT", "Yoga", "HIIT", "Pilates", "HIIT", "Spinning", "Cerrado"},
                {"10:00", "Pilates", "Spinning", "Pilates", "HIIT", "Pilates", "Zumba", "Cerrado"},
                {"11:00", "Zumba", "HIIT", "Zumba", "CrossFit", "Zumba", "Yoga", "Cerrado"},
                {"12:00 - 16:00", "Gimnasio Abierto", "Gimnasio Abierto", "Gimnasio Abierto", "Gimnasio Abierto", "Gimnasio Abierto", "Gimnasio Abierto", "Cerrado"},
                {"16:00", "Spinning", "Yoga", "Spinning", "Yoga", "Spinning", "HIIT", "Cerrado"},
                {"17:00", "CrossFit", "Pilates", "CrossFit", "Pilates", "CrossFit", "Spinning", "Cerrado"},
                {"18:00", "HIIT", "Zumba", "HIIT", "Zumba", "HIIT", "Yoga", "Cerrado"},
                {"19:00", "Pilates", "Spinning", "Pilates", "Spinning", "Pilates", "CrossFit", "Cerrado"},
                {"20:00", "Yoga", "CrossFit", "Yoga", "CrossFit", "Yoga", "HIIT", "Cerrado"},
                {"21:00", "Cerrado", "Cerrado", "Cerrado", "Cerrado", "Cerrado", "Cerrado", "Cerrado"},
                {"", "", "", "", "", "", "", ""},
                {"", "", "", "", "", "", "", ""}
        };

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO horarios (hora, lunes, martes, miercoles, jueves, viernes, sabado, domingo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            for (String[] fila : datos) {
                for (int i = 0; i < fila.length; i++) {
                    stmt.setString(i + 1, fila[i]);
                }
                stmt.executeUpdate();
            }
        }
    }

    private ObservableList<ObservableList<String>> obtenerDatosHorarios() throws SQLException {
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT hora, lunes, martes, miercoles, jueves, viernes, sabado, domingo FROM horarios")) {

            while (rs.next()) {
                ObservableList<String> fila = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    fila.add(rs.getString(i));
                }
                datos.add(fila);
            }
        }
        return datos;
    }

    private void guardarDatosHorarios() throws SQLException {
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM horarios");

            try (PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO horarios (hora, lunes, martes, miercoles, jueves, viernes, sabado, domingo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                for (ObservableList<String> fila : tablaHorarios.getItems()) {
                    for (int i = 0; i < fila.size(); i++) {
                        insertStmt.setString(i + 1, fila.get(i));
                    }
                    insertStmt.executeUpdate();
                }
            }
        }
    }

    private void agregarFila() {
        ObservableList<String> nuevaFila = FXCollections.observableArrayList("", "", "", "", "", "", "", "");
        tablaHorarios.getItems().add(nuevaFila);
    }

    private void eliminarFila() {
        ObservableList<String> filaSeleccionada = tablaHorarios.getSelectionModel().getSelectedItem();
        if (filaSeleccionada != null) {
            tablaHorarios.getItems().remove(filaSeleccionada);
        } else {
            mostrarAlerta("Seleccione una fila para eliminar.");
        }
    }

    private void restablecerHorarioBase() throws SQLException {
        try (Connection conn = ConexionBD.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("DELETE FROM horarios");
            }

            insertarDatosBase(conn);

            ObservableList<ObservableList<String>> datos = obtenerDatosHorarios();
            tablaHorarios.setItems(datos);
        }
    }

    private ObservableList<ReservaClase> obtenerDatosReservas() throws SQLException {
        ObservableList<ReservaClase> reservas = FXCollections.observableArrayList();
        ReservaClaseDAO reservaDAO = new ReservaClaseDAO();
        List<ReservaClase> listaReservas = reservaDAO.getAllReservasClase();
        reservas.addAll(listaReservas);
        return reservas;
    }

    private void agregarFilaReserva() {
        ReservaClase nuevaReserva = new ReservaClase();
        tablaReservas.getItems().add(nuevaReserva);
    }

    private void eliminarFilaReserva() {
        ReservaClase reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (reservaSeleccionada != null) {
            tablaReservas.getItems().remove(reservaSeleccionada);
        } else {
            mostrarAlerta("Selecciona una fila para eliminar.");
        }
    }

    private void limpiarTablaReservas() throws SQLException {
        ReservaClaseDAO reservaDAO = new ReservaClaseDAO();
        List<ReservaClase> reservas = tablaReservas.getItems();
        for (ReservaClase reserva : reservas) {
            reservaDAO.deleteReservaClase(reserva.getId_clase());
        }
        tablaReservas.getItems().clear();
    }

    // El método guardarDatosReservas:
    private void guardarDatosReservas() throws SQLException {
        ReservaClaseDAO reservaDAO = new ReservaClaseDAO();
        ObservableList<ReservaClase> reservas = tablaReservas.getItems();
        for (ReservaClase reserva : reservas) {
            if (reserva.getId_reserva() == 0) {
                // Se añade una nueva reserva si la ID es 0
                if (reservaDAO.addReservaClase(reserva)) {
                    mostrarAlerta("Reserva agregada con éxito.");
                } else {
                    mostrarAlerta("No se pudo agregar la reserva. Inténtalo de nuevo.");
                }
            } else {
                // Se actualiza una reserva existente si la ID no es 0
                if (reservaDAO.updateReservaClase(reserva)) {
                    mostrarAlerta("Reserva actualizada con éxito.");
                } else {
                    mostrarAlerta("No se pudo actualizar la reserva. Inténtalo de nuevo.");
                }
            }
        }
        actualizarTablaReservas(); // Actualizamos la tabla de reservas después de guardar los datos
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static BorderPane getRoot() {
        return root;
    }
}