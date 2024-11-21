package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.Clase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para acceder y manipular los datos de la tabla 'clases' en la base de datos.
 * Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las clases.
 *
 * @author Raul Lopez
 */
public class ClaseDAO {

    // todo instancia de la conexion

    /**
     * Recupera un objeto Clase de la base de datos dado su ID.
     *
     * @param id El ID de la clase a recuperar.
     * @return El objeto Clase correspondiente al ID especificado, o null si no se encuentra.
     */
    public Clase getClaseById(int id) {
        Clase clase = null;
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM clases WHERE id_clase = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                clase = new Clase(
                        resultSet.getInt("id_clase"),
                        resultSet.getString("titulo"),
                        resultSet.getInt("id_entrenador"),
                        resultSet.getInt("max_participantes"),
                        resultSet.getString("ubicacion"),
                        resultSet.getString("inicio"),
                        resultSet.getString("fin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clase;
    }

    /**
     * Agrega una nueva clase a la base de datos.
     *
     * @param clase El objeto Clase a agregar.
     * @return true si se agregó correctamente, false si no se pudo agregar.
     */
    public boolean addClase(Clase clase) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO clases (titulo, id_entrenador, max_participantes, ubicacion, inicio, fin) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, clase.getTitulo());
            statement.setInt(2, clase.getId_entrenador());
            statement.setInt(3, clase.getMax_participantes());
            statement.setString(4, clase.getUbicacion());
            statement.setString(5, clase.getInicio());
            statement.setString(6, clase.getFin());
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Recupera una lista de todas las clases de la base de datos.
     *
     * @return Una lista de objetos Clase que representan a todas las clases en la base de datos.
     */
    public List<Clase> getAllClases() {
        List<Clase> clases = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM clases";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Clase clase = new Clase(
                        resultSet.getInt("id_clase"),
                        resultSet.getString("titulo"),
                        resultSet.getInt("id_entrenador"),
                        resultSet.getInt("max_participantes"),
                        resultSet.getString("ubicacion"),
                        resultSet.getString("inicio"),
                        resultSet.getString("fin")
                );
                clases.add(clase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clases;
    }

    /**
     * Actualiza una clase existente en la base de datos.
     *
     * @param clase El objeto Clase con los nuevos datos a actualizar.
     * @return true si se actualizó correctamente, false si no se pudo actualizar.
     */
    public boolean updateClase(Clase clase) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "UPDATE clases SET titulo = ?, id_entrenador = ?, max_participantes = ?, ubicacion = ?, inicio = ?, fin = ? WHERE id_clase = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, clase.getTitulo());
            statement.setInt(2, clase.getId_entrenador());
            statement.setInt(3, clase.getMax_participantes());
            statement.setString(4, clase.getUbicacion());
            statement.setString(5, clase.getInicio());
            statement.setString(6, clase.getFin());
            statement.setInt(7, clase.getId_clase());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina una clase de la base de datos dado su ID.
     *
     * @param id El ID de la clase a eliminar.
     * @return true si se eliminó correctamente, false si no se pudo eliminar.
     */
    public boolean deleteClase(int id) {
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "DELETE FROM clases WHERE id_clase = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
