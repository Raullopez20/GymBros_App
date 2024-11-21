package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.Instalacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Instalacion.
 * Proporciona métodos para interactuar con la base de datos.
 *
 * @autor Raúl López
 */
public class InstalacionDAO {

    /**
     * Obtiene una Instalacion por su ID.
     *
     * @param id El ID de la Instalacion.
     * @return La Instalacion correspondiente, o null si no se encuentra.
     */
    public Instalacion getInstalacionById(int id) {
        Instalacion instalacion = null;
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM instalaciones WHERE id_instalacion = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                instalacion = new Instalacion(
                        resultSet.getInt("id_instalacion"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("ruta_imagen")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instalacion;
    }

    /**
     * Añade una nueva Instalacion a la base de datos.
     *
     * @param instalacion La Instalacion a añadir.
     * @return El ID generado para la Instalacion, o -1 si no se pudo añadir.
     */
    public int addInstalacion(Instalacion instalacion) {
        int generatedId = -1;
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "INSERT INTO instalaciones (nombre, descripcion, ruta_imagen) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, instalacion.getNombre());
            statement.setString(2, instalacion.getDescripcion());
            statement.setString(3, instalacion.getRutaImagen());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    /**
     * Obtiene todas las Instalaciones de la base de datos.
     *
     * @return Una lista de todas las Instalaciones.
     */
    public List<Instalacion> getAllInstalaciones() {
        List<Instalacion> instalaciones = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM instalaciones";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Instalacion instalacion = new Instalacion(
                        resultSet.getInt("id_instalacion"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion"),
                        resultSet.getString("ruta_imagen")
                );
                instalaciones.add(instalacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instalaciones;
    }

    /**
     * Actualiza una Instalacion existente en la base de datos.
     *
     * @param instalacion La Instalacion con la información actualizada.
     * @return true si la Instalacion fue actualizada exitosamente, false en caso contrario.
     */
    public boolean updateInstalacion(Instalacion instalacion) {
        boolean rowUpdated = false;
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "UPDATE instalaciones SET nombre = ?, descripcion = ?, ruta_imagen = ? WHERE id_instalacion = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, instalacion.getNombre());
            statement.setString(2, instalacion.getDescripcion());
            statement.setString(3, instalacion.getRutaImagen());
            statement.setInt(4, instalacion.getIdInstalacion());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    /**
     * Elimina una Instalacion por su ID.
     *
     * @param id El ID de la Instalacion a eliminar.
     * @return true si la Instalacion fue eliminada exitosamente, false en caso contrario.
     */
    public boolean deleteInstalacion(int id) {
        boolean rowDeleted = false;
        try (Connection conn = ConexionBD.getConnection()) {
            String sql = "DELETE FROM instalaciones WHERE id_instalacion = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
