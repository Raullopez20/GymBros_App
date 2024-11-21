package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.Rutina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Rutina.
 * Esta clase proporciona métodos para interactuar con la base de datos y
 * realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la tabla rutinas.
 *
 * @autor Raul Lopez
 */
public class RutinaDAO {

    /**
     * Método para obtener una rutina por su ID.
     * @param id El ID de la rutina.
     * @return Un objeto Rutina con la información de la rutina.
     */
    public Rutina getRutinaById(int id) {
        return ExceptionHandler.handle(() -> {
            Rutina rutina = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM rutinas WHERE id_rutina = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    rutina = new Rutina(
                            resultSet.getInt("id_rutina"),
                            resultSet.getInt("id_entrenador"),
                            resultSet.getString("titulo"),
                            resultSet.getString("descripcion"),
                            resultSet.getString("creado_en")
                    );
                }
            }
            return rutina;
        }, SQLException.class);
    }

    /**
     * Método para añadir una nueva rutina.
     * @param rutina El objeto Rutina a añadir.
     * @return true si la rutina se añadió con éxito, false en caso contrario.
     */
    public boolean addRutina(Rutina rutina) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "INSERT INTO rutinas (id_entrenador, titulo, descripcion, creado_en) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, rutina.getId_entrenador());
                statement.setString(2, rutina.getTitulo());
                statement.setString(3, rutina.getDescripcion());
                statement.setString(4, rutina.getCreado_en());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        }, SQLException.class);
    }

    /**
     * Método para obtener todas las rutinas.
     * @return Una lista de objetos Rutina.
     */
    public List<Rutina> getAllRutinas() {
        return ExceptionHandler.handle(() -> {
            List<Rutina> rutinas = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM rutinas";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Rutina rutina = new Rutina(
                            resultSet.getInt("id_rutina"),
                            resultSet.getInt("id_entrenador"),
                            resultSet.getString("titulo"),
                            resultSet.getString("descripcion"),
                            resultSet.getString("creado_en")
                    );
                    rutinas.add(rutina);
                }
            }
            return rutinas;
        }, SQLException.class);
    }

    /**
     * Método para actualizar una rutina existente.
     * @param rutina El objeto Rutina con la información actualizada.
     * @return true si la rutina se actualizó con éxito, false en caso contrario.
     */
    public boolean updateRutina(Rutina rutina) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "UPDATE rutinas SET id_entrenador = ?, titulo = ?, descripcion = ?, creado_en = ? WHERE id_rutina = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, rutina.getId_entrenador());
                statement.setString(2, rutina.getTitulo());
                statement.setString(3, rutina.getDescripcion());
                statement.setString(4, rutina.getCreado_en());
                statement.setInt(5, rutina.getId_rutina());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        }, SQLException.class);
    }

    /**
     * Método para eliminar una rutina por su ID.
     * @param id El ID de la rutina a eliminar.
     * @return true si la rutina se eliminó con éxito, false en caso contrario.
     */
    public boolean deleteRutina(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "DELETE FROM rutinas WHERE id_rutina = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        }, SQLException.class);
    }
}
