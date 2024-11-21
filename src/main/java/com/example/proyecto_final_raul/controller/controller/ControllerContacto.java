package com.example.proyecto_final_raul.controller.controller;

import com.example.proyecto_final_raul.app.view.AppUsuario;
import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Controlador para la vista de contacto.
 *
 * Este controlador se encarga de inicializar y manejar la vista de contacto
 * en la aplicación.
 *
 * @autor Raul Lopez
 */
public class ControllerContacto {
    private AppUsuario appUsuario;
    private VBox root;
    private HostServices hostServices;

    /**
     * Constructor de la clase ControllerContacto.
     *
     * @param appUsuario    La instancia de la aplicación del usuario.
     * @param hostServices  Los servicios del host para abrir enlaces externos.
     */
    public ControllerContacto(AppUsuario appUsuario, HostServices hostServices) {
        this.appUsuario = appUsuario;
        this.hostServices = hostServices;
        this.root = new VBox();
        inicializarVista();
    }

    /**
     * Inicializa la vista de contacto.
     *
     * Este método configura todos los elementos de la interfaz gráfica
     * para la vista de contacto.
     */
    private void inicializarVista() {
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #ffffff;");

        // Información de contacto
        Label titulo = new Label("Contacto");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titulo.setTextFill(Color.web("#800000"));

        Label direccion = new Label("Estamos en C/ País Valenciano, en el centro de Orihuela. Frente al parque nuevo y junto a la cafetería Madeira.");
        direccion.setFont(Font.font("Arial", 16));
        direccion.setWrapText(true);

        Label telefono = new Label("Teléfono: +34 695 93 52 81");
        telefono.setFont(Font.font("Arial", 16));

        Label email = new Label("Email: gymbros@gmail.com");
        email.setFont(Font.font("Arial", 16));

        Label horario = new Label("Nuestro horario:\nDe lunes a viernes: de 6:00 a 23:00 h.\nSábados: de 10:00 a 14:00 h.\nDomingos: de 10:00 a 13:00 h.");
        horario.setFont(Font.font("Arial", 16));
        horario.setWrapText(true);

        // Mensaje de promoción
        Label promo = new Label("¡Prueba la primera sesión totalmente gratis! Y recuerda que todo incluido por 25€/mes.");
        promo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        promo.setTextFill(Color.WHITE);
        promo.setStyle("-fx-background-color: #800000; -fx-padding: 10px; -fx-background-radius: 10px;");

        // Enlace a Google Maps
        Hyperlink mapLink = new Hyperlink("Ver en Google Maps");
        mapLink.setFont(Font.font("Arial", 14));
        mapLink.setTextFill(Color.web("#0000FF"));
        mapLink.setOnAction(e -> abrirEnlace("https://www.google.es/maps/place/Gym+bros/@10.762222,-136.7666555,3.71z/data=!4m10!1m2!2m1!1sgymbros!3m6!1s0x85c7239a54e357eb:0xb6389f8eb9b35318!8m2!3d17.047916!4d-96.7207099!15sCgdneW1icm9zWgkiB2d5bWJyb3OSARFneW1uYXN0aWNzX2NlbnRlcpoBI0NoWkRTVWhOTUc5blMwVkpRMEZuU1VRMFoxcDVYMUpSRUFF4AEA!16s%2Fg%2F11c59xwbrf?hl=es&entry=ttu/"));

        // Testimonios
        VBox testimonios = new VBox();
        testimonios.setSpacing(10);
        testimonios.setPadding(new Insets(10));
        testimonios.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        Label testimoniosTitulo = new Label("Testimonios");
        testimoniosTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label testimonio1 = new Label("“¡Excelente gimnasio! Las instalaciones son de primera y el personal muy amable.” – Juan Pérez");
        testimonio1.setFont(Font.font("Arial", 16));

        Label testimonio2 = new Label("“Las clases son muy dinámicas y los entrenadores están siempre dispuestos a ayudar.” – María García");
        testimonio2.setFont(Font.font("Arial", 16));

        testimonios.getChildren().addAll(testimoniosTitulo, testimonio1, testimonio2);

        // Galería de fotos
        HBox galeria = new HBox();
        galeria.setSpacing(10);
        galeria.setPadding(new Insets(10));
        galeria.setAlignment(Pos.CENTER);
        galeria.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ddd; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        ImageView img1 = new ImageView(new Image(getClass().getResourceAsStream("/images/image1.jpg")));
        ImageView img2 = new ImageView(new Image(getClass().getResourceAsStream("/images/image2.jpg")));
        ImageView img3 = new ImageView(new Image(getClass().getResourceAsStream("/images/image3.jpg")));
        img1.setFitHeight(300);
        img1.setFitWidth(400);
        img2.setFitHeight(300);
        img2.setFitWidth(450);
        img3.setFitHeight(300);
        img3.setFitWidth(400);

        galeria.getChildren().addAll(img1, img2, img3);

        // Redes sociales
        HBox redesSociales = new HBox();
        redesSociales.setSpacing(10);
        redesSociales.setAlignment(Pos.CENTER);

        Hyperlink facebookLink = new Hyperlink("Facebook");
        facebookLink.setFont(Font.font("Arial", 14));
        facebookLink.setTextFill(Color.web("#3b5998"));
        facebookLink.setOnAction(e -> abrirEnlace("https://www.facebook.com/gymbros"));

        Hyperlink instagramLink = new Hyperlink("Instagram");
        instagramLink.setFont(Font.font("Arial", 14));
        instagramLink.setTextFill(Color.web("#E1306C"));
        instagramLink.setOnAction(e -> abrirEnlace("https://www.instagram.com/gymbros"));

        Hyperlink twitterLink = new Hyperlink("Twitter");
        twitterLink.setFont(Font.font("Arial", 14));
        twitterLink.setTextFill(Color.web("#1DA1F2"));
        twitterLink.setOnAction(e -> abrirEnlace("https://www.twitter.com/gymbros"));

        redesSociales.getChildren().addAll(facebookLink, instagramLink, twitterLink);

        // Agregar componentes al root
        root.getChildren().addAll(titulo, promo, direccion, telefono, email, horario, mapLink, testimonios, galeria, redesSociales);
    }

    /**
     * Abre un enlace en el navegador predeterminado del sistema.
     *
     * @param url La URL que se desea abrir.
     */
    private void abrirEnlace(String url) {
        hostServices.showDocument(url);
    }

    /**
     * Obtiene el nodo raíz de la vista de contacto.
     *
     * @return El nodo VBox que contiene la vista de contacto.
     */
    public VBox getRoot() {
        return root;
    }
}