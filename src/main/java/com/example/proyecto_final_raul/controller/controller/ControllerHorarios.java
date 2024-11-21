package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.app.view.AppAdmin;
import com.example.proyecto_final_raul.app.view.AppUsuario;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.model.ReservaClaseDAO;
import com.example.proyecto_final_raul.util.ReservaClase;
import com.example.proyecto_final_raul.util.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class ControllerHorarios {

    private final String primaryColor = "#400000";
    private final String secondaryColor = "#500000";
    private AppUsuario appUsuario;
    private static BorderPane root;
    private TableView<ObservableList<String>> tablaHorarios;
    private TableView<ReservaClase> tablaReservas;
    private boolean datosCargados = false;

    public ControllerHorarios(AppUsuario appUsuario) {
        this.appUsuario = appUsuario;
        this.root = new BorderPane();
        crearVista();
    }

    private void crearVista() {
        tablaHorarios = new TableView<>();
        tablaHorarios.setEditable(false); // Deshabilitar la edición

        String[] diasSemana = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        String[] coloresColumnas = {"#EAEAEA", "#D3D3D3", "#EAEAEA", "#D3D3D3", "#EAEAEA", "#D3D3D3", "#EAEAEA", "#D3D3D3"};

        for (int i = 0; i < diasSemana.length; i++) {
            TableColumn<ObservableList<String>, String> columna = new TableColumn<>(diasSemana[i]);
            int colIndex = i;
            columna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(colIndex)));
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
        tablaHorarios.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");

        VBox layoutPrincipal = new VBox(10, tablaHorarios);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setAlignment(Pos.TOP_CENTER);
        layoutPrincipal.setPrefHeight(800);

        // Crear el formulario de reserva
        VBox formularioReserva = crearFormularioReserva();

        // Crear la tabla de reservas
        VBox tablaReservasLayout = crearTablaReservas();

        HBox layoutTablaReservas = new HBox(20, formularioReserva, tablaReservasLayout);
        layoutTablaReservas.setPadding(new Insets(20));

        HBox layoutInferior = new HBox(20,layoutTablaReservas);
        layoutInferior.setPadding(new Insets(20));
        layoutInferior.setAlignment(Pos.CENTER);

        VBox layoutCompleto = new VBox(20, layoutPrincipal, layoutInferior);
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

        VBox layout = new VBox(10, tablaReservas);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.TOP_LEFT);

        layout.setStyle("-fx-border-color: " + primaryColor + ";" + "-fx-border-width: 2px;");

        return layout;
    }

    private void actualizarTablaReservas() {
        ReservaClaseDAO reservaDAO = new ReservaClaseDAO();
        List<ReservaClase> reservas = reservaDAO.getAllReservasClase();
        ObservableList<ReservaClase> reservasList = FXCollections.observableArrayList(reservas);
        tablaReservas.setItems(reservasList);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean usuarioExiste(String nombreUsuario) {
        try (Connection conn = ConexionBD.getConnection()) {
            String query = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, nombreUsuario);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ObservableList<ObservableList<String>> obtenerDatosHorarios() throws SQLException {
        ObservableList<ObservableList<String>> datos = FXCollections.observableArrayList();

        try (Connection conn = ConexionBD.getConnection()) {
            String query = "SELECT hora, lunes, martes, miercoles, jueves, viernes, sabado, domingo FROM horarios";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    ObservableList<String> fila = FXCollections.observableArrayList();
                    for (int i = 1; i <= 8; i++) {
                        fila.add(rs.getString(i));
                    }
                    datos.add(fila);
                }
            }
        }
        return datos;
    }

    public BorderPane getRoot() {
        return root;
    }

    public static void setRoot(BorderPane root) {
        ControllerHorarios.root = root;
    }

    public AppUsuario getAppUsuario() {
        return appUsuario;
    }

    public void setAppAdmin(AppUsuario appUsuario) {
        this.appUsuario = appUsuario;
    }
}
