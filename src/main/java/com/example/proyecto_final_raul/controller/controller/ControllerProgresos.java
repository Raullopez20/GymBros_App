package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.app.view.AppUsuario;
import com.example.proyecto_final_raul.model.EjercicioDAO;
import com.example.proyecto_final_raul.model.ProgresoUsuarioDAO;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerProgresos {
    private final String primaryColor = "#400000";
    private final String secondaryColor = "#500000";
    private AppUsuario appUsuario;
    private VBox root;
    private Usuario currentUser;
    private EjercicioDAO ejercicioDAO;
    private ProgresoUsuarioDAO progresoUsuarioDAO;

    // Nueva tabla y botón
    private TableView<Ejercicio> ejercicioTable;
    private Button toggleTableButton;
    private boolean isTableVisible = false;

    public ControllerProgresos(AppUsuario appUsuario) {
        this.appUsuario = appUsuario;
        if (this.appUsuario != null) {
            this.currentUser = appUsuario.getCurrentUser();
        } else {
            throw new IllegalArgumentException("AppUsuario no puede ser null");
        }

        if (this.currentUser == null) {
            throw new IllegalArgumentException("El usuario actual no puede ser null");
        }

        this.ejercicioDAO = new EjercicioDAO();
        this.progresoUsuarioDAO = new ProgresoUsuarioDAO();
        this.root = new VBox(20);
        inicializarVista();
    }

    private void inicializarVista() {
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Vista de Progresos");
        titleLabel.setFont(new Font("Arial", 24));
        root.getChildren().add(titleLabel);

        Label userLabel = new Label("Usuario: " + currentUser.getNombre() + " " + currentUser.getApellido());
        userLabel.setFont(new Font("Arial", 18));
        root.getChildren().add(userLabel);

        // Botón para calcular si eres parte del 5% más fuerte
        Button strengthButton = new Button("Calcular Fuerza");
        strengthButton.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        strengthButton.setOnAction(event -> calcularFortaleza());
        root.getChildren().add(strengthButton);

        // Botón para calcular IMC
        Button calculateBMIButton = new Button("Calcular IMC");
        calculateBMIButton.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        calculateBMIButton.setOnAction(event -> calcularIMC());
        root.getChildren().add(calculateBMIButton);

        // Botón para mostrar/ocultar tabla de ejercicios
        toggleTableButton = new Button("Mostrar Ejercicios");
        toggleTableButton.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        toggleTableButton.setOnAction(event -> toggleTablaEjercicios());
        root.getChildren().add(toggleTableButton);

        // Crear la tabla de ejercicios
        crearTablaEjercicios();
        root.getChildren().add(ejercicioTable);

        // Inicialmente oculta la tabla
        ejercicioTable.setVisible(false);

        // Display progress list
        mostrarProgresos(null);
    }

    private void crearTablaEjercicios() {
        ejercicioTable = new TableView<>();

        TableColumn<Ejercicio, Integer> colIdEjercicio = new TableColumn<>("ID Ejercicio");
        TableColumn<Ejercicio, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Ejercicio, String> colDescripcion = new TableColumn<>("Descripción");
        TableColumn<Ejercicio, String> colGrupoMuscular = new TableColumn<>("Grupo Muscular");

        colIdEjercicio.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_ejercicio()).asObject());
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colGrupoMuscular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));

        ejercicioTable.getColumns().addAll(colIdEjercicio, colNombre, colDescripcion, colGrupoMuscular);

        List<Ejercicio> ejercicios = ExceptionHandler.handle(() -> ejercicioDAO.getAllEjercicios());
        ejercicioTable.getItems().addAll(ejercicios);
    }

    private void toggleTablaEjercicios() {
        isTableVisible = !isTableVisible;
        ejercicioTable.setVisible(isTableVisible);
        toggleTableButton.setText(isTableVisible ? "Ocultar Ejercicios" : "Mostrar Ejercicios");
    }

    private void mostrarProgresos(String filterDate) {
        VBox progressListLayout = new VBox(20);
        progressListLayout.setPadding(new Insets(20));
        progressListLayout.setAlignment(Pos.CENTER);

        List<ProgresoUsuario> progresos = ExceptionHandler.handle(() -> progresoUsuarioDAO.getProgresosByUsuario(currentUser.getId_usuario()));

        if (filterDate != null && !filterDate.isEmpty()) {
            progresos = progresos.stream()
                    .filter(progreso -> progreso.getRegistrado_en().contains(filterDate))
                    .collect(Collectors.toList());
        }

        if (progresos == null || progresos.isEmpty()) {
            progressListLayout.getChildren().add(new Label("No hay progresos registrados."));
        } else {
            TableView<ProgresoUsuario> table = new TableView<>();
            TableColumn<ProgresoUsuario, Integer> colIdProgreso = new TableColumn<>("ID Progreso");
            TableColumn<ProgresoUsuario, Integer> colIdEjercicio = new TableColumn<>("ID Ejercicio");
            TableColumn<ProgresoUsuario, String> colNombreEjercicio = new TableColumn<>("Nombre Ejercicio");
            TableColumn<ProgresoUsuario, Integer> colSeriesCompletadas = new TableColumn<>("Series Completadas");
            TableColumn<ProgresoUsuario, Integer> colRepeticionesCompletadas = new TableColumn<>("Repeticiones Completadas");
            TableColumn<ProgresoUsuario, Integer> colDuracion = new TableColumn<>("Duración");
            TableColumn<ProgresoUsuario, String> colRegistradoEn = new TableColumn<>("Registrado En");

            table.getColumns().addAll(colIdProgreso, colIdEjercicio, colNombreEjercicio, colSeriesCompletadas, colRepeticionesCompletadas, colDuracion, colRegistradoEn);

            colIdProgreso.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_progreso()).asObject());
            colIdEjercicio.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId_ejercicio()).asObject());
            colNombreEjercicio.setCellValueFactory(cellData -> new SimpleStringProperty(getNombreEjercicio(cellData.getValue().getId_ejercicio())));
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
            progressListLayout.getChildren().add(table);
        }

        root.getChildren().add(progressListLayout);
    }


    private String getNombreEjercicio(int idEjercicio) {
        return ExceptionHandler.handle(() -> ejercicioDAO.getEjercicioById(idEjercicio).getNombre());
    }

    private void calcularFortaleza() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Calcular Fuerza");
        titleLabel.setFont(new Font("Arial", 18));
        layout.getChildren().add(titleLabel);

        TextField weightField = new TextField();
        weightField.setPromptText("Peso (kg)");
        weightField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                weightField.setText(oldValue);
            }
        });

        TextField benchPressField = new TextField();
        benchPressField.setPromptText("Peso en Press de Banca (kg)");
        benchPressField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                benchPressField.setText(oldValue);
            }
        });

        TextField backSquatField = new TextField();
        backSquatField.setPromptText("Peso en Sentadilla (kg)");
        backSquatField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                backSquatField.setText(oldValue);
            }
        });

        TextField deadLiftField = new TextField();
        deadLiftField.setPromptText("Peso en Peso Muerto (kg)");
        deadLiftField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                deadLiftField.setText(oldValue);
            }
        });

        Button calculateButton = new Button("Calcular");
        calculateButton.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        calculateButton.setOnAction(event -> {
            try {
                double weight = parseAndValidateDouble(weightField.getText(), 700); // Límite de 700 kg
                double benchPressWeight = parseAndValidateDouble(benchPressField.getText(), 700); // Límite de 700 kg
                double backSquatWeight = parseAndValidateDouble(backSquatField.getText(), 700); // Límite de 700 kg
                double deadLiftWeight = parseAndValidateDouble(deadLiftField.getText(), 700); // Límite de 700 kg

                StringBuilder message = new StringBuilder("No eres parte del 5% más fuerte.\n");
                if (benchPressWeight >= weight && backSquatWeight >= 1.5 * weight && deadLiftWeight >= 2 * weight) {
                    message = new StringBuilder("¡Eres parte del 5% más fuerte!\n");
                }
                message.append("Para estar en el 5% más fuerte:\n")
                        .append("Press de Banca: ").append(benchPressWeight).append(" kg >= ").append(weight).append(" kg\n")
                        .append("Sentadilla: ").append(backSquatWeight).append(" kg >= ").append(1.5 * weight).append(" kg\n")
                        .append("Peso Muerto: ").append(deadLiftWeight).append(" kg >= ").append(2 * weight).append(" kg");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resultado de la Fortaleza");
                alert.setHeaderText(null);
                alert.setContentText(message.toString());
                alert.showAndWait();
            } catch (NumberFormatException e) {
                mostrarMensaje(Alert.AlertType.ERROR, "Error", "Por favor, ingresa números válidos en los campos de peso.");
            }
        });

        layout.getChildren().addAll(new Label("Peso (kg):"), weightField,
                new Label("Peso en Press de Banca (kg):"), benchPressField,
                new Label("Peso en Sentadilla (kg):"), backSquatField,
                new Label("Peso en Peso Muerto (kg):"), deadLiftField,
                calculateButton);

        Scene scene = new Scene(layout, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Calcular Fortaleza");
        stage.show();
    }

    private void calcularIMC() {
        Stage stage = new Stage();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Calcular IMC");
        titleLabel.setFont(new Font("Arial", 18));
        layout.getChildren().add(titleLabel);

        TextField weightField = new TextField();
        weightField.setPromptText("Peso (kg)");
        weightField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                weightField.setText(oldValue);
            }
        });

        TextField heightField = new TextField();
        heightField.setPromptText("Altura (cm)");
        heightField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}")) {
                heightField.setText(oldValue);
            }
        });

        Button calculateButton = new Button("Calcular");
        calculateButton.setStyle("-fx-background-color: " + primaryColor + "; -fx-text-fill: white;");
        calculateButton.setOnAction(event -> {
            try {
                double weight = parseAndValidateDouble(weightField.getText(), 700); // Límite de 700 kg
                double height = parseAndValidateDouble(heightField.getText(), 230); // Límite de 230 cm
                height /= 100; // Convertir cm a metros
                double bmi = weight / (height * height);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resultado IMC");
                alert.setHeaderText(null);
                alert.setContentText("Tu IMC es: " + String.format("%.2f", bmi) + "%");
                alert.showAndWait();
            } catch (NumberFormatException e) {
                mostrarMensaje(Alert.AlertType.ERROR, "Error", "Por favor, ingresa números válidos en los campos de peso y altura.");
            }
        });

        layout.getChildren().addAll(new Label("Peso (kg):"), weightField,
                new Label("Altura (cm):"), heightField,
                calculateButton);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Calcular IMC");
        stage.show();
    }

    private double parseAndValidateDouble(String value, double maxLimit) throws NumberFormatException {
        double parsedValue = Double.parseDouble(value);
        if (parsedValue < 0 || parsedValue > maxLimit) {
            throw new NumberFormatException();
        }
        return parsedValue;
    }

    public void mostrarMensaje(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public VBox getRoot() {
        return root;
    }
}
