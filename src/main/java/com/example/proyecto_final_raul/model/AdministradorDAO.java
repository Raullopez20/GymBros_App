package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase proporciona métodos para acceder y manipular los datos de la tabla 'administradores' en la base de datos.
 * Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los administradores.
 *
 * @author Raul Lopez
 */

public class AdministradorDAO {

    /**
     * Recupera un objeto Administrador de la base de datos dado su ID.
     *
     * @param id El ID del administrador a recuperar.
     * @return El objeto Administrador correspondiente al ID especificado, o null si no se encuentra.
     */
    public Administrador getAdministradorById(int id) {
        return ExceptionHandler.handle(() -> {
            Administrador administrador = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM administradores WHERE id_admin = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    administrador = new Administrador(
                            resultSet.getInt("id_admin"),
                            resultSet.getString("nombre"),
                            resultSet.getString("correo"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("contrasena")
                    );
                }
            }
            return administrador;
        }, SQLException.class);
    }

    /**
     * Agrega un nuevo administrador a la base de datos.
     *
     * @param administrador El objeto Administrador a agregar.
     * @return true si se agregó correctamente, false si no se pudo agregar.
     */
    public boolean addAdministrador(Administrador administrador) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "INSERT INTO administradores (nombre, correo, nombre_usuario, contrasena) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, administrador.getNombre());
                statement.setString(2, administrador.getCorreo());
                statement.setString(3, administrador.getNombre_usuario());
                statement.setString(4, administrador.getContrasena());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        }, SQLException.class);
    }

    /**
     * Recupera una lista de todos los administradores de la base de datos.
     *
     * @return Una lista de objetos Administrador que representan a todos los administradores en la base de datos.
     */
    public List<Administrador> getAllAdministradores() {
        return ExceptionHandler.handle(() -> {
            List<Administrador> administradores = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM administradores";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Administrador administrador = new Administrador(
                            resultSet.getInt("id_admin"),
                            resultSet.getString("nombre"),
                            resultSet.getString("correo"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("contrasena")
                    );
                    administradores.add(administrador);
                }
            }
            return administradores;
        }, SQLException.class);
    }

    /**
     * Actualiza un administrador existente en la base de datos.
     *
     * @param administrador El objeto Administrador con los nuevos datos a actualizar.
     * @return true si se actualizó correctamente, false si no se pudo actualizar.
     */
    public boolean updateAdministrador(Administrador administrador) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "UPDATE administradores SET nombre = ?, correo = ?, nombre_usuario = ?, contrasena = ? WHERE id_admin = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, administrador.getNombre());
                statement.setString(2, administrador.getCorreo());
                statement.setString(3, administrador.getNombre_usuario());
                statement.setString(4, administrador.getContrasena());
                statement.setInt(5, administrador.getId_admin());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        }, SQLException.class);
    }

    /**
     * Elimina un administrador de la base de datos dado su ID.
     *
     * @param id El ID del administrador a eliminar.
     * @return true si se eliminó correctamente, false si no se pudo eliminar.
     */
    public boolean deleteAdministrador(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "DELETE FROM administradores WHERE id_admin = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        }, SQLException.class);
    }

    /**
     * Recupera un objeto Administrador de la base de datos dado su nombre de usuario y contraseña.
     *
     * @param nombre_usuario El nombre de usuario del administrador.
     * @param contrasena La contraseña del administrador.
     * @return El objeto Administrador correspondiente al nombre de usuario y contraseña especificados, o null si no se encuentra.
     */
    public Administrador getAdministradorByUsernameAndPassword(String nombre_usuario, String contrasena) {
        return ExceptionHandler.handle(() -> {
            Administrador administrador = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM administradores WHERE nombre_usuario = ? AND contrasena = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, nombre_usuario);
                statement.setString(2, contrasena);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    administrador = new Administrador(
                            resultSet.getInt("id_admin"),
                            resultSet.getString("nombre"),
                            resultSet.getString("correo"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("contrasena")
                    );
                }
            }
            return administrador;
        }, SQLException.class);
    }

    /**
     * Elimina un administrador de la base de datos y actualiza los IDs de los administradores restantes.
     *
     * @param idAdmin El ID del administrador a eliminar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void borrarAdministrador(int idAdmin) throws SQLException {
        try (Connection connection = ConexionBD.getConnection()) {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Eliminar el administrador especificado
            String deleteQuery = "DELETE FROM Administradores WHERE id_admin = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, idAdmin);
                int filasBorradas = deleteStatement.executeUpdate();

                if (filasBorradas > 0) {
                    System.out.println("Administrador borrado correctamente de la base de datos.");

                    // Actualizar los IDs de los administradores restantes
                    String updateQuery = "UPDATE Administradores SET id_admin = id_admin - 1 WHERE id_admin > ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, idAdmin);
                        updateStatement.executeUpdate();
                    }

                    // Confirmar transacción
                    connection.commit();
                } else {
                    System.out.println("No se encontró ningún administrador con ese ID.");
                    // Revertir transacción si no se borra ninguna fila
                    connection.rollback();
                }
            } catch (SQLException e) {
                // Revertir transacción en caso de error
                connection.rollback();
                e.printStackTrace();
            } finally {
                // Restaurar auto-commit a verdadero
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
