package gui;

import model.Documento;
import model.Prestamo;
import model.Usuario;
import persistence.DocumentoDAO;
import persistence.PrestamoDAO;
import persistence.UsuarioDAO;

import java.sql.SQLException;
import java.util.List;

public class GestorBiblioteca {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO;
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();

    public GestorBiblioteca() throws SQLException {
        documentoDAO = new DocumentoDAO();
    }

    // Agregar usuario con contraseña personalizada
    public void agregarUsuario(String nombre, String tipo, String contrasena) throws SQLException {
        // Necesitas mapear 'tipo' (nombre del rol) a su id_tipo_usuario antes de insertarlo
        // Esto requiere una consulta a tipo_usuario (no incluida en este fragmento aún)
        // Por ahora se deja en forma incompleta a propósito.
        throw new UnsupportedOperationException("Agregar usuario requiere mapeo de tipo a ID");
    }

    // Versión con contraseña por defecto
    public void agregarUsuario(String nombre, String tipo) throws SQLException {
        agregarUsuario(nombre, tipo, "1234");
    }

    public List<Usuario> obtenerUsuarios() throws SQLException {
        return usuarioDAO.obtenerTodos();
    }

    public void modificarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.modificarUsuario(usuario);
    }

    public void eliminarUsuario(int id) throws SQLException {
        usuarioDAO.eliminarUsuario(id);
    }

    public List<Documento> obtenerDocumentos() throws SQLException {
        return documentoDAO.obtenerTodos();
    }

    public void agregarDocumento(String titulo, String autor, String tipo, String estado) throws SQLException {
        Documento d = new Documento(titulo, autor, tipo, estado);
        documentoDAO.agregarDocumento(d);
    }

    public void prestarDocumento(int idUsuario, int idDocumento) throws SQLException {
        prestamoDAO.prestarDocumento(idUsuario, idDocumento);
    }

    public void devolverDocumento(int idPrestamo) throws SQLException {
        prestamoDAO.devolverDocumento(idPrestamo);
    }

    public List<Prestamo> obtenerPrestamos() throws SQLException {
        return prestamoDAO.obtenerTodos();
    }

    // ✅ Ahora retorna el nombre del tipo de usuario (ej. "administrador")
    public String validarCredenciales(String username, String password) throws SQLException {
        Usuario usuario = usuarioDAO.validarCredenciales(username, password);
        return (usuario != null) ? usuario.getRol() : null;
    }
}
