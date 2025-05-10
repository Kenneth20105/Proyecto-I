package persistence;

import model.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private final Connection conn;

    public PrestamoDAO() throws SQLException {
        this.conn = Conexion.getConnection();
    }

    public void prestarDocumento(int idUsuario, int idDocumento) throws SQLException {
        String sql = "INSERT INTO prestamos (id_usuario, id_documento, fecha_prestamo, devuelto) VALUES (?, ?, CURRENT_DATE, false)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idDocumento);
            stmt.executeUpdate();
        }
    }

    public void devolverDocumento(int idPrestamo) throws SQLException {
        String sql = "UPDATE prestamos SET devuelto = true, fecha_devolucion = CURRENT_DATE WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPrestamo);
            stmt.executeUpdate();
        }
    }

    public List<Prestamo> obtenerTodos() throws SQLException {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo p = new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_documento"),
                        rs.getDate("fecha_prestamo").toLocalDate(),
                        rs.getBoolean("devuelto"),
                        rs.getDate("fecha_devolucion") != null ? rs.getDate("fecha_devolucion").toLocalDate() : null
                );
                lista.add(p);
            }
        }
        return lista;
    }
}
