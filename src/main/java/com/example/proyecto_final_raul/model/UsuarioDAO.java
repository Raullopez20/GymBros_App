package com.example.proyecto_final_raul.model;

import com.example.proyecto_final_raul.Exception.ExceptionHandler;
import com.example.proyecto_final_raul.mysql.ConexionBD;
import com.example.proyecto_final_raul.util.Usuario;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Usuario.
 * Esta clase proporciona métodos para interactuar con la base de datos y
 * realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la tabla usuarios.
 * También incluye métodos para importar y exportar usuarios desde/hacia un archivo XML.
 *
 * @autor Raul Lopez
 */
public class UsuarioDAO {

    /**
     * Método para obtener un usuario por su ID.
     * @param id El ID del usuario.
     * @return Un objeto Usuario con la información del usuario.
     */
    public Usuario getUsuarioById(int id) {
        return ExceptionHandler.handle(() -> {
            Usuario usuario = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    usuario = new Usuario(
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getString("correo"),
                            resultSet.getString("peso"),
                            resultSet.getString("altura"),
                            resultSet.getString("edad"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("contrasena"),
                            resultSet.getString("detalles_contacto"),
                            resultSet.getString("preferencias_entrenamiento")
                    );
                }
            }
            return usuario;
        }, SQLException.class);
    }

    /**
     * Método para verificar si un correo electrónico ya existe en la base de datos.
     * @param correo El correo electrónico a verificar.
     * @return true si el correo existe, false en caso contrario.
     */
    public boolean correoExiste(String correo) {
        return ExceptionHandler.handle(() -> {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, correo);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
            return false;
        }, SQLException.class);
    }

    /**
     * Método para verificar si un nombre de usuario ya existe en la base de datos.
     * @param nombreUsuario El nombre de usuario a verificar.
     * @return true si el nombre de usuario existe, false en caso contrario.
     */
    public boolean nombreUsuarioExiste(String nombreUsuario) {
        return ExceptionHandler.handle(() -> {
            String sql = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
            try (Connection conn = ConexionBD.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, nombreUsuario);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
            return false;
        }, SQLException.class);
    }

    /**
     * Método para añadir un nuevo usuario.
     * @param usuario El objeto Usuario a añadir.
     * @return true si el usuario se añadió con éxito, false en caso contrario.
     */
    public boolean addUsuario(Usuario usuario) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "INSERT INTO usuarios (nombre, apellido, correo, peso, altura, edad, nombre_usuario, contrasena, detalles_contacto, preferencias_entrenamiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, usuario.getNombre());
                statement.setString(2, usuario.getApellido());
                statement.setString(3, usuario.getCorreo());
                statement.setString(4, usuario.getPeso());
                statement.setString(5, usuario.getAltura());
                statement.setString(6, usuario.getEdad());
                statement.setString(7, usuario.getNombre_usuario());
                statement.setString(8, usuario.getContrasena());
                statement.setString(9, usuario.getDetalles_contacto());
                statement.setString(10, usuario.getPreferencias_entrenamiento());
                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        }, SQLException.class);
    }

    /**
     * Método para obtener todos los usuarios.
     * @return Una lista de objetos Usuario.
     */
    public List<Usuario> getAllUsuarios() {
        return ExceptionHandler.handle(() -> {
            List<Usuario> usuarios = new ArrayList<>();
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM usuarios";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Usuario usuario = new Usuario(
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getString("correo"),
                            resultSet.getString("peso"),
                            resultSet.getString("altura"),
                            resultSet.getString("edad"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("contrasena"),
                            resultSet.getString("detalles_contacto"),
                            resultSet.getString("preferencias_entrenamiento")
                    );
                    usuarios.add(usuario);
                }
            }
            return usuarios;
        }, SQLException.class);
    }

    /**
     * Método para actualizar un usuario existente.
     * @param usuario El objeto Usuario con la información actualizada.
     * @return true si el usuario se actualizó con éxito, false en caso contrario.
     */
    public boolean updateUsuario(Usuario usuario) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, peso = ?, altura = ?, edad = ?, nombre_usuario = ?, contrasena = ?, detalles_contacto = ?, preferencias_entrenamiento = ? WHERE id_usuario = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, usuario.getNombre());
                statement.setString(2, usuario.getApellido());
                statement.setString(3, usuario.getCorreo());
                statement.setString(4, usuario.getPeso());
                statement.setString(5, usuario.getAltura());
                statement.setString(6, usuario.getEdad());
                statement.setString(7, usuario.getNombre_usuario());
                statement.setString(8, usuario.getContrasena());
                statement.setString(9, usuario.getDetalles_contacto());
                statement.setString(10, usuario.getPreferencias_entrenamiento());
                statement.setInt(11, usuario.getId_usuario());
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        }, SQLException.class);
    }

    /**
     * Método para eliminar un usuario por su ID.
     * @param id El ID del usuario a eliminar.
     * @return true si el usuario se eliminó con éxito, false en caso contrario.
     */
    public boolean deleteUsuario(int id) {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        }, SQLException.class);
    }

    /**
     * Método para obtener un usuario por su nombre de usuario y contraseña.
     * @param nombre_usuario El nombre de usuario.
     * @param contrasena La contraseña.
     * @return Un objeto Usuario con la información del usuario.
     */
    public Usuario getUsuarioByUsernameAndPassword(String nombre_usuario, String contrasena) {
        return ExceptionHandler.handle(() -> {
            Usuario usuario = null;
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, nombre_usuario);
                statement.setString(2, contrasena);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    usuario = new Usuario(
                            resultSet.getInt("id_usuario"),
                            resultSet.getString("nombre"),
                            resultSet.getString("apellido"),
                            resultSet.getString("correo"),
                            resultSet.getString("peso"),
                            resultSet.getString("altura"),
                            resultSet.getString("edad"),
                            resultSet.getString("nombre_usuario"),
                            resultSet.getString("contrasena"),
                            resultSet.getString("detalles_contacto"),
                            resultSet.getString("preferencias_entrenamiento")
                    );
                }
            }
            return usuario;
        }, SQLException.class);
    }

    /**
     * Elimina un usuario de la base de datos y actualiza los IDs de los usuarios restantes.
     *
     * @param idUsuario El ID del usuario a eliminar.
     * @throws SQLException Si ocurre un error al interactuar con la base de datos.
     */
    public void borrarUsuario(int idUsuario) throws SQLException {
        try (Connection connection = ConexionBD.getConnection()) {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Eliminar el usuario especificado
            String deleteQuery = "DELETE FROM Usuarios WHERE id_usuario = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.setInt(1, idUsuario);
                int filasBorradas = deleteStatement.executeUpdate();

                if (filasBorradas > 0) {
                    System.out.println("Usuario borrado correctamente de la base de datos.");

                    // Actualizar los IDs de los usuarios restantes
                    String updateQuery = "UPDATE Usuarios SET id_usuario = id_usuario - 1 WHERE id_usuario > ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, idUsuario);
                        updateStatement.executeUpdate();
                    }

                    // Confirmar transacción
                    connection.commit();
                } else {
                    System.out.println("No se encontró ningún usuario con ese ID.");
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


    /**
     * Método para obtener el ID máximo de los usuarios en la base de datos.
     * @return El ID máximo.
     */
    private int getMaxId() {
        return ExceptionHandler.handle(() -> {
            try (Connection conn = ConexionBD.getConnection()) {
                String sql = "SELECT MAX(id_usuario) FROM usuarios";
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
            return 0; // Si no hay usuarios, empezamos desde 0
        }, SQLException.class);
    }

    /**
     * Método para importar usuarios desde un archivo XML.
     * @param file El archivo XML que contiene los usuarios a importar.
     * @return Una lista de objetos Usuario importados.
     */
    public List<Usuario> importarUsuariosDesdeXML(File file) {
        List<Usuario> usuarios = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("usuario");

            // Obtener el último ID usado en la base de datos
            int currentMaxId = getMaxId();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    Usuario usuario = new Usuario();

                    // Asignar un nuevo ID automáticamente
                    usuario.setId_usuario(++currentMaxId);

                    usuario.setNombre(elem.getElementsByTagName("nombre").item(0).getTextContent());
                    usuario.setApellido(elem.getElementsByTagName("apellido").item(0).getTextContent());
                    usuario.setCorreo(elem.getElementsByTagName("correo").item(0).getTextContent());
                    usuario.setPeso(elem.getElementsByTagName("peso").item(0).getTextContent());
                    usuario.setAltura(elem.getElementsByTagName("altura").item(0).getTextContent());
                    usuario.setEdad(elem.getElementsByTagName("edad").item(0).getTextContent());
                    usuario.setNombre_usuario(elem.getElementsByTagName("nombre_usuario").item(0).getTextContent());
                    usuario.setContrasena(elem.getElementsByTagName("contrasena").item(0).getTextContent());
                    usuario.setDetalles_contacto(elem.getElementsByTagName("detalles_contacto").item(0).getTextContent());
                    usuario.setPreferencias_entrenamiento(elem.getElementsByTagName("preferencias_entrenamiento").item(0).getTextContent());

                    usuarios.add(usuario);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Método para exportar una lista de usuarios a un archivo XML.
     * @param file El archivo XML de destino.
     * @param usuarios La lista de usuarios a exportar.
     * @return true si la exportación fue exitosa, false en caso contrario.
     */
    public boolean exportarUsuariosA_XML(File file, List<Usuario> usuarios) {
        try {
            // Asegurarse de que el archivo tenga la extensión .xml
            String filePath = file.getPath();
            if (!filePath.endsWith(".xml")) {
                filePath += ".xml";
                file = new File(filePath);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("usuarios");
            doc.appendChild(rootElement);

            for (Usuario usuario : usuarios) {
                Element usuarioElement = doc.createElement("usuario");

                Element idElement = doc.createElement("id_usuario");
                idElement.appendChild(doc.createTextNode(String.valueOf(usuario.getId_usuario())));
                usuarioElement.appendChild(idElement);

                Element nombreElement = doc.createElement("nombre");
                nombreElement.appendChild(doc.createTextNode(usuario.getNombre()));
                usuarioElement.appendChild(nombreElement);

                Element apellidoElement = doc.createElement("apellido");
                apellidoElement.appendChild(doc.createTextNode(usuario.getApellido()));
                usuarioElement.appendChild(apellidoElement);

                Element correoElement = doc.createElement("correo");
                correoElement.appendChild(doc.createTextNode(usuario.getCorreo()));
                usuarioElement.appendChild(correoElement);

                Element pesoElement = doc.createElement("peso");
                pesoElement.appendChild(doc.createTextNode(usuario.getPeso()));
                usuarioElement.appendChild(pesoElement);

                Element alturaElement = doc.createElement("altura");
                alturaElement.appendChild(doc.createTextNode(usuario.getAltura()));
                usuarioElement.appendChild(alturaElement);

                Element edadElement = doc.createElement("edad");
                edadElement.appendChild(doc.createTextNode(usuario.getEdad()));
                usuarioElement.appendChild(edadElement);

                Element nombreUsuarioElement = doc.createElement("nombre_usuario");
                nombreUsuarioElement.appendChild(doc.createTextNode(usuario.getNombre_usuario()));
                usuarioElement.appendChild(nombreUsuarioElement);

                Element contrasenaElement = doc.createElement("contrasena");
                contrasenaElement.appendChild(doc.createTextNode(usuario.getContrasena()));
                usuarioElement.appendChild(contrasenaElement);

                Element detallesElement = doc.createElement("detalles_contacto");
                detallesElement.appendChild(doc.createTextNode(usuario.getDetalles_contacto()));
                usuarioElement.appendChild(detallesElement);

                Element preferenciasElement = doc.createElement("preferencias_entrenamiento");
                preferenciasElement.appendChild(doc.createTextNode(usuario.getPreferencias_entrenamiento()));
                usuarioElement.appendChild(preferenciasElement);

                rootElement.appendChild(usuarioElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileWriter(file));
            transformer.transform(source, result);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
