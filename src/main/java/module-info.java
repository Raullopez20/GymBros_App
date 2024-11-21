module com.example.proyecto_final_raul {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.proyecto_final_raul to javafx.fxml;
    exports com.example.proyecto_final_raul.app;
    opens com.example.proyecto_final_raul.app to javafx.fxml;
    exports com.example.proyecto_final_raul.controller;
    opens com.example.proyecto_final_raul.controller to javafx.fxml;
    exports com.example.proyecto_final_raul.controller.controller;
    opens com.example.proyecto_final_raul.controller.controller to javafx.fxml;
    exports com.example.proyecto_final_raul.app.view;
    opens com.example.proyecto_final_raul.app.view to javafx.fxml;

    // Añadido para Horario y HorarioDAO
    exports com.example.proyecto_final_raul.util;
    opens com.example.proyecto_final_raul.util to javafx.fxml;
    exports com.example.proyecto_final_raul.model;
    opens com.example.proyecto_final_raul.model to javafx.fxml;
}
