package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.Ejercicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para acceder y manipular los datos de la tabla 'ejercicios' en la base de datos.
 * Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los ejercicios.
 *
 * @author Raul Lopez
 */
public class EjercicioDAO {

    /**
     * Recupera un objeto Ejercicio de la base de datos dado su ID.
     *
     * @param id El ID del ejercicio a recuperar.
     * @return El objeto Ejercicio correspondiente al ID especificado, o null si no se encuentra.
     */
    public Ejercicio getEjercicioById(int id) {
        return ExceptionHandler.handle(() -> {
            Ejercicio ejercicio = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM ejercicios WHERE id_ejercicio = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    ejercicio = new Ejercicio(
                            resultSet.getInt("id_ejercicio"),
                            resultSet.getString("nombre"),
                            resultSet.getString("descripcion"),
                            resultSet.getString("tipo")
                    );
                }
            }
            return ejercicio;
        }, SQLException.class);
    }

    /**
     * Agrega un nuevo ejercicio a la base de datos.
     *
     * @param ejercicio El objeto Ejercicio a agregar.
     * @return true si se agregó correctamente, false si no se pudo agregar.
     */
    public boolean addEjercicio(Ejercicio ejercicio) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "INSERT INTO ejercicios (nombre, descripcion, tipo) VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, ejercicio.getNombre());
                statement.setString(2, ejercicio.getDescripcion());
                statement.setString(3, ejercicio.getTipo());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        }, SQLException.class);
    }

    /**
     * Recupera una lista de todos los ejercicios de la base de datos.
     *
     * @return Una lista de objetos Ejercicio que representan a todos los ejercicios en la base de datos.
     */
    public List<Ejercicio> getAllEjercicios() {
        return ExceptionHandler.handle(() -> {
            List<Ejercicio> ejercicios = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM ejercicios";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Ejercicio ejercicio = new Ejercicio(
                            resultSet.getInt("id_ejercicio"),
                            resultSet.getString("nombre"),
                            resultSet.getString("descripcion"),
                            resultSet.getString("tipo")
                    );
                    ejercicios.add(ejercicio);
                }
            }
            return ejercicios;
        }, SQLException.class);
    }

    /**
     * Actualiza un ejercicio existente en la base de datos.
     *
     * @param ejercicio El objeto Ejercicio con los nuevos datos a actualizar.
     * @return true si se actualizó correctamente, false si no se pudo actualizar.
     */
    public boolean updateEjercicio(Ejercicio ejercicio) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "UPDATE ejercicios SET nombre = ?, descripcion = ?, tipo = ? WHERE id_ejercicio = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, ejercicio.getNombre());
                statement.setString(2, ejercicio.getDescripcion());
                statement.setString(3, ejercicio.getTipo());
                statement.setInt(4, ejercicio.getId_ejercicio());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        }, SQLException.class);
    }

    /**
     * Elimina un ejercicio de la base de datos dado su ID.
     *
     * @param id El ID del ejercicio a eliminar.
     * @return true si se eliminó correctamente, false si no se pudo eliminar.
     */
    public boolean deleteEjercicio(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "DELETE FROM ejercicios WHERE id_ejercicio = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        }, SQLException.class);
    }
}
