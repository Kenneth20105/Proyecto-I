package persistence;

import model.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    public void registrarPrestamo(Prestamo p) throws SQLException {
        String sql = "INSERT INTO prestamos(id_documento, id_usuario, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdDocumento());
            stmt.setInt(2, p.getIdUsuario());
            stmt.setDate(3, p.getFechaPrestamo());
            stmt.setDate(4, p.getFechaDevolucion());
            stmt.setBoolean(5, p.isDevuelto());
            stmt.executeUpdate();
        }
    }

    public List<Prestamo> obtenerPrestamosActivos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE devuelto = false";
        try (Connection conn = Conexion.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_documento"),
                        rs.getInt("id_usuario"),
                        rs.getDate("fecha_prestamo"),
                        rs.getDate("fecha_devolucion"),
                        rs.getBoolean("devuelto")
                );
                prestamos.add(p);
            }
        }
        return prestamos;
    }

    public void marcarComoDevuelto(int idPrestamo) throws SQLException {
        String sql = "UPDATE prestamos SET devuelto = true WHERE id = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPrestamo);
            stmt.executeUpdate();
        }
    }
}
