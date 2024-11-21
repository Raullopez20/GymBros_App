package com.example.proyecto_final_raul.Exception;

import javafx.scene.control.Alert;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ExceptionHandler {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());
    private static final Map<Class<? extends Exception>, Consumer<Exception>> exceptionHandlers = new HashMap<>();

    static {
        // Registrar manejadores de excepciones predeterminados
        registerHandler(SQLException.class, e -> logError("Database error", e));
        registerHandler(IOException.class, e -> logError("I/O error", e));
        registerHandler(IllegalArgumentException.class, e -> logError("Illegal argument", e));
        // Registrar un manejador por defecto
        registerHandler(Exception.class, e -> logError("Unexpected error", e));
    }

    public static <T> T handle(Callable<T> callable, Class<? extends Exception>... expectedExceptions) {
        try {
            return callable.call();
        } catch (Exception e) {
            for (Class<? extends Exception> exceptionClass : expectedExceptions) {
                if (exceptionClass.isInstance(e)) {
                    handleException(e);
                    return null;  // o cualquier valor predeterminado
                }
            }
            // Si la excepción no es esperada, volver a lanzarla
            throw new RuntimeException(e);
        }
    }

    public static void registerHandler(Class<? extends Exception> exceptionClass, Consumer<Exception> handler) {
        exceptionHandlers.put(exceptionClass, handler);
    }

    private static void handleException(Exception e) {
        exceptionHandlers.getOrDefault(e.getClass(), exceptionHandlers.get(Exception.class)).accept(e);
    }

    private static void logError(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }

    // Expresiones regulares comunes
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("\\d+");
    private static final Pattern ALPHABETIC_PATTERN = Pattern.compile("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
    private static final Pattern ALPHANUMERIC_PATTERN = Pattern.compile("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]+");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");  // formato: yyyy-mm-dd
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}");

    static {
        // Registrar manejadores de excepciones predeterminados
        registrarManejador(SQLException.class, e -> logError("Error de base de datos", e));
        registrarManejador(IOException.class, e -> logError("Error de entrada/salida", e));
        registrarManejador(IllegalArgumentException.class, e -> logError("Argumento ilegal", e));
        registrarManejador(NullPointerException.class, e -> logError("Referencia nula", e));
        registrarManejador(ArrayIndexOutOfBoundsException.class, e -> logError("Índice de matriz fuera de límites", e));
        registrarManejador(NumberFormatException.class, e -> logError("Formato de número incorrecto", e));
        // Registrar un manejador por defecto
        registrarManejador(Exception.class, e -> logError("Error inesperado", e));
    }

    public static void registrarManejador(Class<? extends Exception> excepcionClase, Consumer<Exception> manejador) {
        exceptionHandlers.put(excepcionClase, manejador);
    }

    private static void manejarExcepcion(Exception e) {
        exceptionHandlers.getOrDefault(e.getClass(), exceptionHandlers.get(Exception.class)).accept(e);
    }

    public static boolean esNumerico(String entrada) {
        return NUMERIC_PATTERN.matcher(entrada).matches();
    }

    public static boolean esAlfabetico(String entrada) {
        return ALPHABETIC_PATTERN.matcher(entrada).matches();
    }

    public static boolean esAlfanumerico(String entrada) {
        return ALPHANUMERIC_PATTERN.matcher(entrada).matches();
    }

    public static boolean esCorreoValido(String entrada) {
        return EMAIL_PATTERN.matcher(entrada).matches();
    }

    public static boolean esFechaValida(String entrada) {
        return DATE_PATTERN.matcher(entrada).matches();
    }

    public static boolean esNumeroTelefonoValido(String entrada) {
        return PHONE_NUMBER_PATTERN.matcher(entrada).matches();
    }

    public static void main(String[] args) {
        // Ejemplos de uso de los métodos de validación
        System.out.println("12345 es numérico: " + esNumerico("12345"));
        System.out.println("abcde es alfabético: " + esAlfabetico("abcde"));
        System.out.println("abc123 es alfanumérico: " + esAlfanumerico("abc123"));
        System.out.println("test@example.com es un correo válido: " + esCorreoValido("test@example.com"));
        System.out.println("2024-06-03 es una fecha válida: " + esFechaValida("2024-06-03"));
        System.out.println("+34 600 123 456 es un número de teléfono válido: " + esNumeroTelefonoValido("+34 600 123 456"));
    }

    public static void handle(Runnable action) {
        try {
            action.run();
        } catch (Exception e) {
            // Manejar la excepción aquí, por ejemplo, mostrar un mensaje de error
            mostrarMensajeError(e);
        }
    }

    public static <T> T handle(Supplier<T> action) {
        try {
            return action.get();
        } catch (Exception e) {
            // Manejar la excepción aquí, por ejemplo, mostrar un mensaje de error
            mostrarMensajeError(e);
            return null;
        }
    }

    public static void handle(Runnable action, Class<? extends Exception>... expectedExceptions) {
        try {
            action.run();
        } catch (Exception e) {
            for (Class<? extends Exception> exceptionClass : expectedExceptions) {
                if (exceptionClass.isInstance(e)) {
                    handleException(e);
                    return;  // Salir del método
                }
            }
            // Si la excepción no es esperada, volver a lanzarla
            throw new RuntimeException(e);
        }
    }

    public static void mostrarMensajeError(Exception e) {
        // Aquí se puede personalizar el manejo de errores, por ejemplo, mostrar un mensaje en un cuadro de diálogo
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
