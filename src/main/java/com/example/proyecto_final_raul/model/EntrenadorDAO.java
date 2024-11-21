package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.Entrenador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para acceder y manipular los datos de la tabla 'entrenadores' en la base de datos.
 * Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los entrenadores.
 *
 * @author Raul Lopez
 */
public class EntrenadorDAO {

    /**
     * Recupera un objeto Entrenador de la base de datos dado su ID.
     *
     * @param id El ID del entrenador a recuperar.
     * @return El objeto Entrenador correspondiente al ID especificado, o null si no se encuentra.
     */
    public Entrenador getEntrenadorById(int id) {
        return ExceptionHandler.handle(() -> {
            Entrenador entrenador = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM entrenadores WHERE id_entrenador = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    entrenador = new Entrenador(
                            resultSet.getInt("id_entrenador"),
                            resultSet.getString("nombre"),
                            resultSet.getString("correo"),
                            resultSet.getString("especializacion"),
                            resultSet.getString("detalles_contacto")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return entrenador;
        }, SQLException.class);
    }

    /**
     * Agrega un nuevo entrenador a la base de datos.
     *
     * @param entrenador El objeto Entrenador a agregar.
     * @return true si se agregó correctamente, false si no se pudo agregar.
     */
    public boolean addEntrenador(Entrenador entrenador) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "INSERT INTO entrenadores (nombre, correo, especializacion, detalles_contacto) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, entrenador.getNombre());
                statement.setString(2, entrenador.getCorreo());
                statement.setString(3, entrenador.getEspecializacion());
                statement.setString(4, entrenador.getDetalles_contacto());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, SQLException.class);
    }

    /**
     * Recupera una lista de todos los entrenadores de la base de datos.
     *
     * @return Una lista de objetos Entrenador que representan a todos los entrenadores en la base de datos.
     */
    public List<Entrenador> getAllEntrenadores() {
        return ExceptionHandler.handle(() -> {
            List<Entrenador> entrenadores = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM entrenadores";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Entrenador entrenador = new Entrenador(
                            resultSet.getInt("id_entrenador"),
                            resultSet.getString("nombre"),
                            resultSet.getString("correo"),
                            resultSet.getString("especializacion"),
                            resultSet.getString("detalles_contacto")
                    );
                    entrenadores.add(entrenador);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return entrenadores;
        }, SQLException.class);
    }

    /**
     * Actualiza un entrenador existente en la base de datos.
     *
     * @param entrenador El objeto Entrenador con los nuevos datos a actualizar.
     * @return true si se actualizó correctamente, false si no se pudo actualizar.
     */
    public boolean updateEntrenador(Entrenador entrenador) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "UPDATE entrenadores SET nombre = ?, correo = ?, especializacion = ?, detalles_contacto = ? WHERE id_entrenador = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, entrenador.getNombre());
                statement.setString(2, entrenador.getCorreo());
                statement.setString(3, entrenador.getEspecializacion());
                statement.setString(4, entrenador.getDetalles_contacto());
                statement.setInt(5, entrenador.getId_entrenador());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, SQLException.class);
    }

    /**
     * Elimina un entrenador de la base de datos dado su ID.
     *
     * @param id El ID del entrenador a eliminar.
     * @return true si se eliminó correctamente, false si no se pudo eliminar.
     */
    public boolean deleteEntrenador(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "DELETE FROM entrenadores WHERE id_entrenador = ?";
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
