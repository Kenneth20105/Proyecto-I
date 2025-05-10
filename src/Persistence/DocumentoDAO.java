package persistence;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDAO {
    private final Connection connection;

    public DocumentoDAO() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca_donbosco", "root", "kenny3.01");
    }

    public void agregarDocumento(Documento documento) throws SQLException {
        String sql = "INSERT INTO documentos (titulo, autor, tipo, estado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, documento.getTitulo());
            stmt.setString(2, documento.getAutor());
            stmt.setString(3, documento.getTipo());
            stmt.setString(4, documento.getEstado());
            stmt.executeUpdate();
        }
    }

    public List<Documento> obtenerTodos() throws SQLException {
        List<Documento> documentos = new ArrayList<>();
        String sql = "SELECT * FROM documentos";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String autor = rs.getString("autor");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");

                Documento doc = null;
                if (tipo.equalsIgnoreCase("libro")) {
                    doc = new Libro(id, titulo, autor, estado);
                } else if (tipo.equalsIgnoreCase("revista")) {
                    doc = new Revista(id, titulo, autor, estado);
                } else if (tipo.equalsIgnoreCase("dvd")) {
                    doc = new DVD(id, titulo, autor, estado);
                } else if (tipo.equalsIgnoreCase("cd")) {
                    doc = new CD(id, titulo, autor, estado);
                } else {
                    doc = new Documento(id, titulo, autor, tipo, estado);
                }

                documentos.add(doc);
            }
        }
        return documentos;
    }

    public void actualizarEstado(int idDocumento, String estado) throws SQLException {
        String sql = "UPDATE documentos SET estado = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, idDocumento);
            stmt.executeUpdate();
        }
    }

    public void modificarDocumento(Documento documento) throws SQLException {
        String sql = "UPDATE documentos SET titulo = ?, autor = ?, tipo = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, documento.getTitulo());
            stmt.setString(2, documento.getAutor());
            stmt.setString(3, documento.getTipo());
            stmt.setInt(4, documento.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarDocumento(int id) throws SQLException {
        String sql = "DELETE FROM documentos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Documento obtenerPorId(int idDocumento) throws SQLException {
        String sql = "SELECT * FROM documentos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDocumento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Documento(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                );
            }
        }
        return null;
    }
}
