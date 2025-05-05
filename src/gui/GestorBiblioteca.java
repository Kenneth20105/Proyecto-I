package gui;

import model.Documento;
import model.Prestamo;
import model.Usuario;
import persistence.DocumentoDAO;
import persistence.PrestamoDAO;
import persistence.UsuarioDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GestorBiblioteca {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DocumentoDAO documentoDAO = null;

    private final PrestamoDAO prestamoDAO = new PrestamoDAO();

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
}
