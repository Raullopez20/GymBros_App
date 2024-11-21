package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.ReservaClase;
import com.example.proyecto_final_raul.Exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar las operaciones relacionadas con las reservas de clases en la base de datos.
 *
 * @author Raul Lopez
 */
public class ReservaClaseDAO {

    /**
     * Método para obtener una reserva de clase por su ID.
     *
     * @param id El ID de la reserva de clase.
     * @return Un objeto ReservaClase si se encuentra, de lo contrario, null.
     */
    public ReservaClase getReservaClaseById(int id) {
        return ExceptionHandler.handle(() -> {
            ReservaClase reservaClase = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM reservas_clases WHERE id_reserva = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Crear objeto ReservaClase a partir de los datos obtenidos
                    reservaClase = new ReservaClase(
                            resultSet.getInt("id_reserva"),
                            resultSet.getInt("id_clase"),
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("titulo"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("dia"),
                            resultSet.getString("hora"),
                            resultSet.getString("reservado_en")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return reservaClase;
        });
    }

    /**
     * Método para añadir una nueva reserva de clase.
     *
     * @param reservaClase El objeto ReservaClase a añadir.
     * @return true si la reserva se añadió correctamente, de lo contrario false.
     */
    public boolean addReservaClase(ReservaClase reservaClase) {
        String insertQuery = "INSERT INTO reservas_clases (id_clase, id_usuario, titulo, nombre_usuario, dia, hora, reservado_en) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Obtener ID de usuario por su nombre de usuario
            int idUsuario = obtenerIdUsuarioPorNombre(reservaClase.getNombre_usuario());

            insertStmt.setInt(1, reservaClase.getId_clase());
            insertStmt.setInt(2, idUsuario);
            insertStmt.setString(3, reservaClase.getTitulo());
            insertStmt.setString(4, reservaClase.getNombre_usuario());
            insertStmt.setString(5, reservaClase.getDia());
            insertStmt.setString(6, reservaClase.getHora());
            insertStmt.setString(7, reservaClase.getReservado_en());

            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para obtener el ID de usuario por su nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario.
     * @return El ID del usuario.
     * @throws SQLException Si no se encuentra el usuario.
     */
    private int obtenerIdUsuarioPorNombre(String nombreUsuario) throws SQLException {
        String selectQuery = "SELECT id_usuario FROM usuarios WHERE nombre_usuario = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            selectStmt.setString(1, nombreUsuario);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usuario");
            } else {
                throw new SQLException("Usuario no encontrado");
            }
        }
    }

    /**
     * Método para obtener todas las reservas de clases.
     *
     * @return Una lista de objetos ReservaClase.
     */
    public List<ReservaClase> getAllReservasClase() {
        return ExceptionHandler.handle(() -> {
            List<ReservaClase> reservasClase = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM reservas_clases";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Crear objeto ReservaClase a partir de los datos obtenidos y añadirlo a la lista
                    ReservaClase reservaClase = new ReservaClase(
                            resultSet.getInt("id_reserva"),
                            resultSet.getInt("id_clase"),
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("titulo"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("dia"),
                            resultSet.getString("hora"),
                            resultSet.getString("reservado_en")
                    );
                    reservasClase.add(reservaClase);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return reservasClase;
        });
    }

    /**
     * Método para actualizar una reserva de clase existente.
     *
     * @param reservaClase El objeto ReservaClase a actualizar.
     * @return true si la actualización fue exitosa, de lo contrario false.
     */
    public boolean updateReservaClase(ReservaClase reservaClase) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "UPDATE reservas_clases SET id_clase = ?, id_usuario = ?, titulo = ?, nombre_usuario = ?, dia = ?, hora = ?, reservado_en = ? WHERE id_reserva = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, reservaClase.getId_clase());
                statement.setInt(2, reservaClase.getId_usuario());
                statement.setString(3, reservaClase.getTitulo());
                statement.setString(4, reservaClase.getNombre_usuario());
                statement.setString(5, reservaClase.getDia());
                statement.setString(6, reservaClase.getHora());
                statement.setString(7, reservaClase.getReservado_en());
                statement.setInt(8, reservaClase.getId_reserva());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Método para eliminar una reserva de clase por su ID.
     *
     * @param id El ID de la reserva de clase a eliminar.
     * @return true si la eliminación fue exitosa, de lo contrario false.
     */
    public boolean deleteReservaClase(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "DELETE FROM reservas_clases WHERE id_reserva = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Método redundante para obtener todas las reservas de clases (ya implementado como getAllReservasClase).
     *
     * @return Una lista de objetos ReservaClase.
     */
    public List<ReservaClase> obtenerReservas() {
        return ExceptionHandler.handle(() -> {
            List<ReservaClase> reservasClase = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM reservas_clases";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    ReservaClase reservaClase = new ReservaClase(
                            resultSet.getInt("id_reserva"),
                            resultSet.getInt("id_clase"),
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("titulo"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("dia"),
                            resultSet.getString("hora"),
                            resultSet.getTimestamp("reservado_en").toString() // Convertir a String si necesario
                    );
                    reservasClase.add(reservaClase);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return reservasClase;
        });
    }
}
