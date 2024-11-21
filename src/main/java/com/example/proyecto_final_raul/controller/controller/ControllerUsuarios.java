package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.app.view.AppUsuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ControllerUsuarios {
    private AppUsuario appUsuario;
    private VBox root;

    public ControllerUsuarios(AppUsuario appUsuario) {
        this.appUsuario = appUsuario;
        this.root = new VBox();
        inicializarVista();
    }

    private void inicializarVista() {
        // Layout principal
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #ffffff;");

        // VBox para contener todo el contenido desplazable
        VBox contenidoVBox = new VBox();
        contenidoVBox.setPadding(new Insets(20));
        contenidoVBox.setSpacing(20);
        contenidoVBox.setAlignment(Pos.TOP_CENTER);

        // Título y mensaje de bienvenida
        Label titulo = new Label("¡Bienvenido a GYMBROS APP!");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setStyle("-fx-text-fill: #800000;");

        Label subtitulo = new Label("Todo lo que necesitas para ponerte en forma");
        subtitulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        subtitulo.setStyle("-fx-text-fill: #000000;");

        // Imagen principal
        ImageView imagenPrincipal = new ImageView(new Image("file:src/main/resources/images/logo2.png"));
        imagenPrincipal.setFitHeight(500);
        imagenPrincipal.setFitWidth(500);

        // Servicios
        VBox serviciosVBox = new VBox(20);
        serviciosVBox.setAlignment(Pos.CENTER);
        serviciosVBox.setPadding(new Insets(20));
        serviciosVBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label serviciosTitulo = new Label("Servicios");
        serviciosTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        serviciosTitulo.setStyle("-fx-text-fill: #000000;");

        HBox serviciosHBox = new HBox(20);
        serviciosHBox.setAlignment(Pos.CENTER);

        String[] servicios = {"Sala de Musculación", "Clases Dirigidas", "Entrenamientos Personales", "Área Funcional"};
        String[] serviciosImages = {
                "file:src/main/resources/images/sala_musculacion.jpg",
                "file:src/main/resources/images/clases_dirigidas.jpg",
                "file:src/main/resources/images/entrenamientos_personales.jpg",
                "file:src/main/resources/images/area_funcional.jpg"
        };
        String[] serviciosDescripciones = {
                "Equipos de última generación para todos los niveles.",
                "Diversas clases para todos los gustos y niveles.",
                "Entrenadores certificados a tu disposición.",
                "Espacio dedicado para entrenamiento funcional."
        };

        for (int i = 0; i < servicios.length; i++) {
            VBox servicioBox = new VBox(10);
            servicioBox.setAlignment(Pos.CENTER);
            servicioBox.setStyle("-fx-padding: 10px; -fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            ImageView servicioImagen = new ImageView(new Image(serviciosImages[i]));
            servicioImagen.setFitHeight(150);
            servicioImagen.setFitWidth(300);

            Label servicioLabel = new Label(servicios[i]);
            servicioLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            servicioLabel.setWrapText(true);
            servicioLabel.setStyle("-fx-text-fill: #800000;");

            Label servicioDesc = new Label(serviciosDescripciones[i]);
            servicioDesc.setFont(Font.font("Arial", 14));
            servicioDesc.setWrapText(true);

            servicioBox.getChildren().addAll(servicioImagen, servicioLabel, servicioDesc);
            serviciosHBox.getChildren().add(servicioBox);
        }

        serviciosVBox.getChildren().addAll(serviciosTitulo, serviciosHBox);

        // Entrenadores Personales
        VBox entrenadoresVBox = new VBox(20);
        entrenadoresVBox.setAlignment(Pos.CENTER);
        entrenadoresVBox.setPadding(new Insets(20));
        entrenadoresVBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label entrenadoresTitulo = new Label("Entrenadores Personales");
        entrenadoresTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        entrenadoresTitulo.setStyle("-fx-text-fill: #000000;");

        HBox entrenadoresHBox = new HBox(20);
        entrenadoresHBox.setAlignment(Pos.CENTER);

        String[] entrenadores = {"Ana García", "Luis Pérez", "Ruben Sánchez"};
        String[] entrenadoresImages = {
                "file:src/main/resources/images/entrenador1.jpg",
                "file:src/main/resources/images/entrenador2.jpg",
                "file:src/main/resources/images/entrenador3.jpg"
        };
        String[] entrenadoresDescripciones = {
                "Especialista en entrenamiento funcional.",
                "Experto en musculación y fuerza.",
                "Nutricionista deportivo y entrenador personal."
        };

        for (int i = 0; i < entrenadores.length; i++) {
            VBox entrenadorBox = new VBox(10);
            entrenadorBox.setAlignment(Pos.CENTER);
            entrenadorBox.setStyle("-fx-padding: 10px; -fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            ImageView entrenadorImagen = new ImageView(new Image(entrenadoresImages[i]));
            entrenadorImagen.setFitHeight(200);
            entrenadorImagen.setFitWidth(200);

            Label entrenadorLabel = new Label(entrenadores[i]);
            entrenadorLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            entrenadorLabel.setWrapText(true);
            entrenadorLabel.setStyle("-fx-text-fill: #800000;");

            Label entrenadorDesc = new Label(entrenadoresDescripciones[i]);
            entrenadorDesc.setFont(Font.font("Arial", 14));
            entrenadorDesc.setWrapText(true);

            entrenadorBox.getChildren().addAll(entrenadorImagen, entrenadorLabel, entrenadorDesc);
            entrenadoresHBox.getChildren().add(entrenadorBox);
        }

        entrenadoresVBox.getChildren().addAll(entrenadoresTitulo, entrenadoresHBox);

        // Nutrición y Suplementación
        VBox nutricionVBox = new VBox(20);
        nutricionVBox.setAlignment(Pos.CENTER);
        nutricionVBox.setPadding(new Insets(20));
        nutricionVBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label nutricionTitulo = new Label("Nutrición y Suplementación");
        nutricionTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        nutricionTitulo.setStyle("-fx-text-fill: #000000;");

        HBox nutricionHBox = new HBox(20);
        nutricionHBox.setAlignment(Pos.CENTER);

        String[] nutricion = {"Planes de Nutrición", "Suplementos Deportivos", "Asesoramiento Nutricional"};
        String[] nutricionImages = {
                "file:src/main/resources/images/planes_nutricion.jpg",
                "file:src/main/resources/images/suplementos.jpg",
                "file:src/main/resources/images/asesoramiento.jpg"
        };
        String[] nutricionDescripciones = {
                "Planes personalizados para cada objetivo.",
                "Suplementos de la mejor calidad.",
                "Asesoría profesional para mejorar tu dieta."
        };

        for (int i = 0; i < nutricion.length; i++) {
            VBox itemBox = new VBox(10);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.setStyle("-fx-padding: 10px; -fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

            ImageView itemImagen = new ImageView(new Image(nutricionImages[i]));
            itemImagen.setFitHeight(200);
            itemImagen.setFitWidth(400);

            Label itemLabel = new Label(nutricion[i]);
            itemLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            itemLabel.setWrapText(true);
            itemLabel.setStyle("-fx-text-fill: #800000;");

            Label itemDesc = new Label(nutricionDescripciones[i]);
            itemDesc.setFont(Font.font("Arial", 14));
            itemDesc.setWrapText(true);

            itemBox.getChildren().addAll(itemImagen, itemLabel, itemDesc);
            nutricionHBox.getChildren().add(itemBox);
        }

        nutricionVBox.getChildren().addAll(nutricionTitulo, nutricionHBox);

        // Testimonios
        VBox testimoniosVBox = new VBox(20);
        testimoniosVBox.setAlignment(Pos.CENTER);
        testimoniosVBox.setPadding(new Insets(20));
        testimoniosVBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label testimoniosTitulo = new Label("Testimonios");
        testimoniosTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        testimoniosTitulo.setStyle("-fx-text-fill: #000000;");

        String[] testimonios = {
                "“¡Excelente gimnasio! Las instalaciones son de primera y el personal muy amable.” – Juan Pérez",
                "“Las clases son muy dinámicas y los entrenadores están siempre dispuestos a ayudar.” – María García",
                "“Me encanta la variedad de equipos y el ambiente motivador.” – Carlos López"
        };

        for (String testimonio : testimonios) {
            Label testimonioLabel = new Label(testimonio);
            testimonioLabel.setFont(Font.font("Arial", 14));
            testimonioLabel.setWrapText(true);
            testimoniosVBox.getChildren().add(testimonioLabel);
        }

        testimoniosVBox.getChildren().add(0, testimoniosTitulo);

        // Galería de imágenes
        HBox galeriaHBox = new HBox(20);
        galeriaHBox.setAlignment(Pos.CENTER);
        galeriaHBox.setPadding(new Insets(20));
        galeriaHBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        ImageView img1 = new ImageView(new Image("file:src/main/resources/images/image1.jpg"));
        ImageView img2 = new ImageView(new Image("file:src/main/resources/images/image2.jpg"));
        ImageView img3 = new ImageView(new Image("file:src/main/resources/images/image3.jpg"));
        img1.setFitHeight(200);
        img1.setFitWidth(300);
        img2.setFitHeight(200);
        img2.setFitWidth(350);
        img3.setFitHeight(200);
        img3.setFitWidth(300);

        galeriaHBox.getChildren().addAll(img1, img2, img3);

        // Información adicional
        VBox infoAdicionalVBox = new VBox(20);
        infoAdicionalVBox.setAlignment(Pos.CENTER);
        infoAdicionalVBox.setPadding(new Insets(20));
        infoAdicionalVBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label infoAdicionalTitulo = new Label("Más Información");
        infoAdicionalTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        infoAdicionalTitulo.setStyle("-fx-text-fill: #000000;");

        Label infoGeneral = new Label("Ofrecemos una variedad de clases y actividades para ayudarte a alcanzar tus objetivos de fitness.");
        infoGeneral.setFont(Font.font("Arial", 16));
        infoGeneral.setWrapText(true);

        Button botonContacto = new Button("Contáctanos");
        botonContacto.setStyle("-fx-background-color: #800000; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        botonContacto.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        botonContacto.setOnAction(event -> appUsuario.mostrarVistaContacto());

        infoAdicionalVBox.getChildren().addAll(infoAdicionalTitulo, infoGeneral, botonContacto);

        // Añadir todo al contenido VBox
        contenidoVBox.getChildren().addAll(titulo, subtitulo, imagenPrincipal, serviciosVBox, entrenadoresVBox, nutricionVBox, testimoniosVBox, galeriaHBox, infoAdicionalVBox);

        // ScrollPane para hacer la interfaz desplazable
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(contenidoVBox);
        scrollPane.setFitToWidth(true);

        // Añadir el ScrollPane al layout principal
        root.getChildren().add(scrollPane);
    }

    public VBox getRoot() {
        return root;
    }
}
