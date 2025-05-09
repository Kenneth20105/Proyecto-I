package gui;

import model.Documento;
import model.Prestamo;
import model.Usuario;
import persistence.DocumentoDAO;
import persistence.PrestamoDAO;
import persistence.UsuarioDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class GestorBiblioteca {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO;

    private final PrestamoDAO prestamoDAO = new PrestamoDAO();

    public GestorBiblioteca() throws SQLException {
        documentoDAO = new DocumentoDAO();
    }

    public void agregarUsuario(String nombre, String tipo) throws SQLException {
        Usuario u = new Usuario(nombre, tipo);
        usuarioDAO.agregarUsuario(u);
    }

    public void agregarDocumento(String titulo, String autor, String tipo, String estado) throws SQLException {
        Documento d = new Documento(titulo, autor, tipo, estado);
        documentoDAO.agregarDocumento(d);
    }

    public List<Usuario> obtenerUsuarios() throws SQLException {
        return usuarioDAO.obtenerTodos();
    }

    public List<Documento> obtenerDocumentos() throws SQLException {
        return documentoDAO.obtenerTodos();
    }

    public void prestarDocumento(int idUsuario, int idDocumento) throws SQLException {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaDev = hoy.plusDays(7);
        Prestamo p = new Prestamo(0, idDocumento, idUsuario, Date.valueOf(hoy), Date.valueOf(fechaDev), false);
        prestamoDAO.registrarPrestamo(p);
        documentoDAO.actualizarEstado(idDocumento, "prestado");
    }

    public void devolverDocumento(int idPrestamo, int idDocumento) throws SQLException {
        prestamoDAO.marcarComoDevuelto(idPrestamo);
        documentoDAO.actualizarEstado(idDocumento, "disponible");
    }

    public List<Prestamo> obtenerPrestamosActivos() throws SQLException {
        return prestamoDAO.obtenerPrestamosActivos();
    }
    public String validarCredenciales(String usuario, String contrasena) throws SQLException {
        String sql = "SELECT rol FROM usuarios WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca_donbosco", "root", "kenny3.01")) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, usuario);
                stmt.setString(2, contrasena);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("rol");
                    }
                }
            }
        }
        return null;
    }
}
