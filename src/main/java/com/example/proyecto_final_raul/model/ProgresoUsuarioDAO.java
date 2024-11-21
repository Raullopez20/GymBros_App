package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.ProgresoUsuario;
import com.example.proyecto_final_raul.Exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad ProgresoUsuario.
 * Esta clase proporciona métodos para interactuar con la base de datos y
 * realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la tabla progreso_usuario.
 *
 * @autor Raul Lopez
 */
public class ProgresoUsuarioDAO {

    /**
     * Método para obtener el progreso de un usuario por su ID.
     * @param id El ID del progreso del usuario.
     * @return Un objeto ProgresoUsuario con la información del progreso.
     */
    public ProgresoUsuario getProgresoUsuarioById(int id) {
        return ExceptionHandler.handle(() -> {
            ProgresoUsuario progresoUsuario = null;
            try (Connection conn = ConexionBD.getConnection()) {
                // Consulta SQL para obtener el progreso del usuario junto con su nombre y apellido
                String sql = "SELECT pu.*, u.nombre, u.apellido FROM progreso_usuario pu JOIN usuarios u ON pu.id_usuario = u.id_usuario WHERE pu.id_progreso = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Crear un objeto ProgresoUsuario con los datos obtenidos
                    progresoUsuario = new ProgresoUsuario(
                            resultSet.getInt("id_progreso"),
                            resultSet.getInt("id_usuario"),
                            resultSet.getInt("id_ejercicio"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getInt("series_completadas"),
                            resultSet.getInt("repeticiones_completadas"),
                            resultSet.getInt("duracion"),
                            resultSet.getString("registrado_en")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return progresoUsuario;
        });
    }

    /**
     * Método para eliminar todos los progresos de usuario.
     * @return true si los progresos se eliminaron con éxito, false en caso contrario.
     */
    public boolean deleteAllProgresosUsuario() {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                // Consulta SQL para eliminar todos los registros de la tabla progreso_usuario
                String sql = "DELETE FROM progreso_usuario";
                PreparedStatement statement = conn.prepareStatement(sql);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Método para añadir un nuevo progreso de usuario.
     * @param progresoUsuario El objeto ProgresoUsuario a añadir.
     * @return true si el progreso se añadió con éxito, false en caso contrario.
     */
    public boolean addProgresoUsuario(ProgresoUsuario progresoUsuario) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                // Verificar que el id_ejercicio existe en la tabla ejercicios
                String checkSql = "SELECT COUNT(*) FROM ejercicios WHERE id_ejercicio = ?";
                PreparedStatement checkStatement = conn.prepareStatement(checkSql);
                checkStatement.setInt(1, progresoUsuario.getId_ejercicio());
                ResultSet checkResultSet = checkStatement.executeQuery();
                if (checkResultSet.next() && checkResultSet.getInt(1) == 0) {
                    throw new SQLException("El id_ejercicio no existe en la tabla ejercicios.");
                }

                // Consulta SQL para insertar un nuevo registro en progreso_usuario
                String sql = "INSERT INTO progreso_usuario (id_usuario, id_ejercicio, nombre, apellido, series_completadas, repeticiones_completadas, duracion, registrado_en) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, progresoUsuario.getId_usuario());
                statement.setInt(2, progresoUsuario.getId_ejercicio());
                statement.setString(3, progresoUsuario.getNombre());
                statement.setString(4, progresoUsuario.getApellido());
                statement.setInt(5, progresoUsuario.getSeries_completadas());
                statement.setInt(6, progresoUsuario.getRepeticiones_completadas());
                statement.setInt(7, progresoUsuario.getDuracion());
                statement.setString(8, progresoUsuario.getRegistrado_en());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Método para obtener todos los progresos de usuario.
     * @return Una lista de objetos ProgresoUsuario.
     */
    public List<ProgresoUsuario> getAllProgresosUsuario() {
        return ExceptionHandler.handle(() -> {
            List<ProgresoUsuario> progresosUsuario = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                // Consulta SQL para obtener todos los progresos de usuario junto con nombre y apellido
                String sql = "SELECT pu.*, u.nombre, u.apellido FROM progreso_usuario pu JOIN usuarios u ON pu.id_usuario = u.id_usuario";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Crear un objeto ProgresoUsuario para cada registro obtenido y añadirlo a la lista
                    ProgresoUsuario progresoUsuario = new ProgresoUsuario(
                            resultSet.getInt("id_progreso"),
                            resultSet.getInt("id_usuario"),
                            resultSet.getInt("id_ejercicio"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getInt("series_completadas"),
                            resultSet.getInt("repeticiones_completadas"),
                            resultSet.getInt("duracion"),
                            resultSet.getString("registrado_en")
                    );
                    progresosUsuario.add(progresoUsuario);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return progresosUsuario;
        });
    }

    /**
     * Método para actualizar un progreso de usuario existente.
     * @param progresoUsuario El objeto ProgresoUsuario con la información actualizada.
     * @return true si el progreso se actualizó con éxito, false en caso contrario.
     */
    public boolean updateProgresoUsuario(ProgresoUsuario progresoUsuario) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                // Verificar que el id_ejercicio existe en la tabla ejercicios
                String checkSql = "SELECT COUNT(*) FROM ejercicios WHERE id_ejercicio = ?";
                PreparedStatement checkStatement = conn.prepareStatement(checkSql);
                checkStatement.setInt(1, progresoUsuario.getId_ejercicio());
                ResultSet checkResultSet = checkStatement.executeQuery();
                if (checkResultSet.next() && checkResultSet.getInt(1) == 0) {
                    throw new SQLException("El id_ejercicio no existe en la tabla ejercicios.");
                }

                // Consulta SQL para actualizar un registro en progreso_usuario
                String sql = "UPDATE progreso_usuario SET id_usuario = ?, id_ejercicio = ?, nombre = ?, apellido = ?, series_completadas = ?, repeticiones_completadas = ?, duracion = ?, registrado_en = ? WHERE id_progreso = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, progresoUsuario.getId_usuario());
                statement.setInt(2, progresoUsuario.getId_ejercicio());
                statement.setString(3, progresoUsuario.getNombre());
                statement.setString(4, progresoUsuario.getApellido());
                statement.setInt(5, progresoUsuario.getSeries_completadas());
                statement.setInt(6, progresoUsuario.getRepeticiones_completadas());
                statement.setInt(7, progresoUsuario.getDuracion());
                statement.setString(8, progresoUsuario.getRegistrado_en());
                statement.setInt(9, progresoUsuario.getId_progreso());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Método para eliminar un progreso de usuario por su ID.
     * @param id El ID del progreso a eliminar.
     * @return true si el progreso se eliminó con éxito, false en caso contrario.
     */
    public boolean deleteProgresoUsuario(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                // Consulta SQL para eliminar un registro en progreso_usuario por su ID
                String sql = "DELETE FROM progreso_usuario WHERE id_progreso = ?";
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
     * Método para verificar si un usuario existe por su ID.
     * @param idUsuario El ID del usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean usuarioExists(int idUsuario) {
        String query = "SELECT 1 FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idUsuario);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para obtener el nombre y apellido de un usuario por su ID.
     * @param idUsuario El ID del usuario.
     * @return Un arreglo de Strings donde la primera posición es el nombre y la segunda el apellido.
     */
    public String[] obtenerNombreYApellidoPorId(int idUsuario) {
        return ExceptionHandler.handle(() -> {
            String[] nombreYApellido = new String[2];
            try (Connection conn = ConexionBD.getConnection()) {
                // Consulta SQL para obtener el nombre y apellido del usuario
                String sql = "SELECT nombre, apellido FROM usuarios WHERE id_usuario = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, idUsuario);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    nombreYApellido[0] = resultSet.getString("nombre");
                    nombreYApellido[1] = resultSet.getString("apellido");
                } else {
                    nombreYApellido[0] = "";
                    nombreYApellido[1] = "";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return nombreYApellido;
        });
    }

    /**
     * Método para obtener el progreso de un usuario por su ID de usuario.
     * @param idUsuario El ID del usuario.
     * @return Un objeto ProgresoUsuario con la información del usuario.
     */
    public ProgresoUsuario getProgresoUsuarioPorId(int idUsuario) {
        ProgresoUsuario progresoUsuario = null;
        String query = "SELECT nombre, apellido FROM Usuarios WHERE id_usuario = ?";

        try (Connection connection = ConexionBD.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Crear un objeto ProgresoUsuario y establecer nombre y apellido
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                progresoUsuario = new ProgresoUsuario();
                progresoUsuario.setNombre(nombre);
                progresoUsuario.setApellido(apellido);
            }

        } catch (SQLException e) {
            ExceptionHandler.handle((Runnable) e);
        }

        return progresoUsuario;
    }

    /**
     * Método para obtener todos los progresos de un usuario específico por su ID de usuario.
     * @param idUsuario El ID del usuario.
     * @return Una lista de objetos ProgresoUsuario.
     */
    public List<ProgresoUsuario> getProgresosByUsuario(int idUsuario) {
        return ExceptionHandler.handle(() -> {
            List<ProgresoUsuario> progresosUsuario = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                // Consulta SQL para obtener los progresos de un usuario específico junto con nombre y apellido
                String sql = "SELECT pu.*, u.nombre, u.apellido FROM progreso_usuario pu JOIN usuarios u ON pu.id_usuario = u.id_usuario WHERE pu.id_usuario = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, idUsuario);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    // Crear un objeto ProgresoUsuario para cada registro obtenido y añadirlo a la lista
                    ProgresoUsuario progresoUsuario = new ProgresoUsuario(
                            resultSet.getInt("id_progreso"),
                            resultSet.getInt("id_usuario"),
                            resultSet.getInt("id_ejercicio"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getInt("series_completadas"),
                            resultSet.getInt("repeticiones_completadas"),
                            resultSet.getInt("duracion"),
                            resultSet.getString("registrado_en")
                    );
                    progresosUsuario.add(progresoUsuario);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return progresosUsuario;
        });
    }

}
