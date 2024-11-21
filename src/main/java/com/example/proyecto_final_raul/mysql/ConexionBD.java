package com.example.proyecto_final_raul.mysql;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos en la aplicación de gimnasio.
 * Permite establecer la conexión y ejecutar scripts SQL para inicializar la base de datos.
 * @author Raul Lopez
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "gymbros_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String SQL_FILE_PATH = "src/main/java/com/example/proyecto_final_raul/mysql/gymbros_db.sql";

    /**
     * Constructor por defecto de ConexionBD.
     */
    public ConexionBD() {

    }

    /**
     * Método para establecer la conexión a la base de datos.
     * @return La conexión establecida.
     */
    public static Connection getConnection() {
        return ExceptionHandler.handle(() -> {
            return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
        }, SQLException.class);
    }

    /**
     * Método para crear la base de datos si no existe.
     */
    public void createDatabaseIfNotExists() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            connection.createStatement().execute(createDBQuery);
            System.out.println("Database created or already exists.");
        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }

    /**
     * Método para ejecutar un script SQL almacenado en un archivo.
     */
    public void executeSQLFile() {
        createDatabaseIfNotExists();
        Connection connection = getConnection();
        if (connection == null) {
            System.out.println("Not connected to the database.");
            return;
        }

        try {
            // Verificar si la base de datos ya contiene tablas
            boolean tablasExistentes = checkIfTablesExist(connection);

            // Leer el archivo SQL desde la ruta especificada
            String sql = new String(Files.readAllBytes(Paths.get(SQL_FILE_PATH)));
            String[] statements = sql.split(";");
            java.sql.Statement stmt = connection.createStatement();

            for (String statement : statements) {
                // Omitir el comando DROP DATABASE si las tablas ya existen
                if (tablasExistentes && statement.trim().toUpperCase().startsWith("DROP DATABASE")) {
                    continue;
                }
                if (statement.trim().length() > 0) {
                    stmt.execute(statement);
                }
            }
            System.out.println("SQL file executed successfully.");
        } catch (IOException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para verificar si ya existen tablas en la base de datos.
     * @param connection Conexión a la base de datos.
     * @return true si existen tablas, false en caso contrario.
     * @throws SQLException si ocurre un error en la consulta SQL.
     */
    private boolean checkIfTablesExist(Connection connection) throws SQLException {
        java.sql.Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'usuarios'"); // Ajusta esta consulta según tus tablas
        return rs.next();
    }
}
