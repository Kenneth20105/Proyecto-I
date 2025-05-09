package persistence;

import model.Documento;
import model.Libro;
import model.Revista;
import model.DVD;
import model.CD;

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

                // Instanciamos el tipo de documento adecuado según el tipo
                Documento doc = null;
                switch (tipo) {
                    case "libro":
                        doc = new Libro(id, titulo, autor, estado);
                        break;
                    case "revista":
                        doc = new Revista(id, titulo, autor, estado);
                        break;
                    case "dvd":
                        doc = new DVD(id, titulo, autor, estado);
                        break;
                    case "cd":
                        doc = new CD(id, titulo, autor, estado);
                        break;
                    // Si tienes más tipos, añádelos aquí
                    default:
                        doc = new Documento(id, titulo, autor, tipo, estado);
                        break;
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
}
