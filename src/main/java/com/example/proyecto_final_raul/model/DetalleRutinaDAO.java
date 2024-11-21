package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.DetalleRutina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para acceder y manipular los datos de la tabla 'detalles_rutina' en la base de datos.
 * Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los detalles de las rutinas.
 *
 * @author Raul Lopez
 */
public class DetalleRutinaDAO {

    /**
     * Recupera un objeto DetalleRutina de la base de datos dado su ID.
     *
     * @param id El ID del detalle de la rutina a recuperar.
     * @return El objeto DetalleRutina correspondiente al ID especificado, o null si no se encuentra.
     */
    public DetalleRutina getDetalleRutinaById(int id) {
        return ExceptionHandler.handle(() -> {
            DetalleRutina detalleRutina = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM detalles_rutina WHERE id_detalle_rutina = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    detalleRutina = new DetalleRutina(
                            resultSet.getInt("id_detalle_rutina"),
                            resultSet.getInt("id_rutina"),
                            resultSet.getInt("id_ejercicio"),
                            resultSet.getInt("series"),
                            resultSet.getInt("repeticiones"),
                            resultSet.getInt("duracion")
                    );
                }
            }
            return detalleRutina;
        }, SQLException.class);
    }

    /**
     * Agrega un nuevo detalle de rutina a la base de datos.
     *
     * @param detalleRutina El objeto DetalleRutina a agregar.
     * @return true si se agregó correctamente, false si no se pudo agregar.
     */
    public boolean addDetalleRutina(DetalleRutina detalleRutina) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "INSERT INTO detalles_rutina (id_rutina, id_ejercicio, series, repeticiones, duracion) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, detalleRutina.getId_rutina());
                statement.setInt(2, detalleRutina.getId_ejercicio());
                statement.setInt(3, detalleRutina.getSeries());
                statement.setInt(4, detalleRutina.getRepeticiones());
                statement.setInt(5, detalleRutina.getDuracion());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        }, SQLException.class);
    }

    /**
     * Recupera una lista de todos los detalles de rutina de la base de datos.
     *
     * @return Una lista de objetos DetalleRutina que representan a todos los detalles de rutina en la base de datos.
     */
    public List<DetalleRutina> getAllDetallesRutina() {
        return ExceptionHandler.handle(() -> {
            List<DetalleRutina> detallesRutina = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM detalles_rutina";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    DetalleRutina detalleRutina = new DetalleRutina(
                            resultSet.getInt("id_detalle_rutina"),
                            resultSet.getInt("id_rutina"),
                            resultSet.getInt("id_ejercicio"),
                            resultSet.getInt("series"),
                            resultSet.getInt("repeticiones"),
                            resultSet.getInt("duracion")
                    );
                    detallesRutina.add(detalleRutina);
                }
            }
            return detallesRutina;
        }, SQLException.class);
    }

    /**
     * Actualiza un detalle de rutina existente en la base de datos.
     *
     * @param detalleRutina El objeto DetalleRutina con los nuevos datos a actualizar.
     * @return true si se actualizó correctamente, false si no se pudo actualizar.
     */
    public boolean updateDetalleRutina(DetalleRutina detalleRutina) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "UPDATE detalles_rutina SET id_rutina = ?, id_ejercicio = ?, series = ?, repeticiones = ?, duracion = ? WHERE id_detalle_rutina = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, detalleRutina.getId_rutina());
                statement.setInt(2, detalleRutina.getId_ejercicio());
                statement.setInt(3, detalleRutina.getSeries());
                statement.setInt(4, detalleRutina.getRepeticiones());
                statement.setInt(5, detalleRutina.getDuracion());
                statement.setInt(6, detalleRutina.getId_detalle_rutina());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        }, SQLException.class);
    }

    /**
     * Elimina un detalle de rutina de la base de datos dado su ID.
     *
     * @param id El ID del detalle de la rutina a eliminar.
     * @return true si se eliminó correctamente, false si no se pudo eliminar.
     */
    public boolean deleteDetalleRutina(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "DELETE FROM detalles_rutina WHERE id_detalle_rutina = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        }, SQLException.class);
    }
}
