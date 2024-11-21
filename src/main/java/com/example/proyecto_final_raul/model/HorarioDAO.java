package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.Horario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Horario.
 * Proporciona métodos para interactuar con la base de datos.
 *
 * @author Raúl López
 */
public class HorarioDAO {

    /**
     * Obtiene un Horario por su ID.
     *
     * @param id El ID del Horario.
     * @return El objeto Horario correspondiente, o null si no se encuentra.
     */
    public Horario getHorarioById(int id) {
        return ExceptionHandler.handle(() -> {
            Horario horario = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM horarios WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    horario = new Horario(
                            resultSet.getInt("id"),
                            resultSet.getString("hora"),
                            resultSet.getString("lunes"),
                            resultSet.getString("martes"),
                            resultSet.getString("miercoles"),
                            resultSet.getString("jueves"),
                            resultSet.getString("viernes"),
                            resultSet.getString("sabado"),
                            resultSet.getString("domingo")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return horario;
        }, SQLException.class);
    }

    /**
     * Añade un nuevo Horario a la base de datos.
     *
     * @param horario El Horario a añadir.
     * @return true si el Horario fue añadido exitosamente, false en caso contrario.
     */
    public boolean addHorario(Horario horario) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "INSERT INTO horarios (hora, lunes, martes, miercoles, jueves, viernes, sabado, domingo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, horario.getHora());
                statement.setString(2, horario.getLunes());
                statement.setString(3, horario.getMartes());
                statement.setString(4, horario.getMiercoles());
                statement.setString(5, horario.getJueves());
                statement.setString(6, horario.getViernes());
                statement.setString(7, horario.getSabado());
                statement.setString(8, horario.getDomingo());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, SQLException.class);
    }

    /**
     * Obtiene todos los Horarios de la base de datos.
     *
     * @return Una lista de todos los Horarios.
     */
    public List<Horario> getAllHorarios() {
        return ExceptionHandler.handle(() -> {
            List<Horario> horarios = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM horarios";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Horario horario = new Horario(
                            resultSet.getInt("id"),
                            resultSet.getString("hora"),
                            resultSet.getString("lunes"),
                            resultSet.getString("martes"),
                            resultSet.getString("miercoles"),
                            resultSet.getString("jueves"),
                            resultSet.getString("viernes"),
                            resultSet.getString("sabado"),
                            resultSet.getString("domingo")
                    );
                    horarios.add(horario);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return horarios;
        }, SQLException.class);
    }

    /**
     * Actualiza un Horario existente en la base de datos.
     *
     * @param horario El Horario con la información actualizada.
     * @return true si el Horario fue actualizado exitosamente, false en caso contrario.
     */
    public boolean updateHorario(Horario horario) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "UPDATE horarios SET hora = ?, lunes = ?, martes = ?, miercoles = ?, jueves = ?, viernes = ?, sabado = ?, domingo = ? WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, horario.getHora());
                statement.setString(2, horario.getLunes());
                statement.setString(3, horario.getMartes());
                statement.setString(4, horario.getMiercoles());
                statement.setString(5, horario.getJueves());
                statement.setString(6, horario.getViernes());
                statement.setString(7, horario.getSabado());
                statement.setString(8, horario.getDomingo());
                statement.setInt(9, horario.getId());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, SQLException.class);
    }

    /**
     * Elimina un Horario por su ID.
     *
     * @param id El ID del Horario a eliminar.
     * @return true si el Horario fue eliminado exitosamente, false en caso contrario.
     */
    public boolean deleteHorario(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "DELETE FROM horarios WHERE id = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, SQLException.class);
    }
}
