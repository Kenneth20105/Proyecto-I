package gui;

import model.Documento;
import model.Prestamo;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private GestorBiblioteca gestor;
    private String rolUsuario;
    private JTextArea areaSalida;

    public VentanaPrincipal(String rolUsuario) {
        this.rolUsuario = rolUsuario;
        try {
            gestor = new GestorBiblioteca();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setTitle("Sistema Biblioteca Don Bosco - [" + rolUsuario.toUpperCase() + "]");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        construirUI();
    }

    public VentanaPrincipal() {

    }

    private void construirUI() {
        JPanel panel = new JPanel(new BorderLayout());

        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaSalida);

        JButton btnUsuarios = new JButton("Mostrar Usuarios");
        JButton btnDocumentos = new JButton("Mostrar Documentos");
        JButton btnPrestamos = new JButton("Prestamos Activos");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        // Configurar visibilidad según el rol
        if (!"administrador".equalsIgnoreCase(rolUsuario)) {
            btnUsuarios.setEnabled(false);
        }

        btnUsuarios.addActionListener(e -> mostrarUsuarios());
        btnDocumentos.addActionListener(e -> mostrarDocumentos());
        btnPrestamos.addActionListener(e -> mostrarPrestamos());
        btnCerrarSesion.addActionListener(e -> {
            try {
                cerrarSesion();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel botones = new JPanel(new FlowLayout());
        botones.add(btnUsuarios);
        botones.add(btnDocumentos);
        botones.add(btnPrestamos);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(botones, BorderLayout.CENTER);
        panelInferior.add(btnCerrarSesion, BorderLayout.EAST);

        panel.add(panelInferior, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        add(panel);
    }

    private void mostrarUsuarios() {
        try {
            List<Usuario> lista = gestor.obtenerUsuarios();
            areaSalida.setText("Usuarios:\n");
            for (Usuario u : lista) {
                areaSalida.append(u.toString() + "\n");
            }
        } catch (SQLException ex) {
            mostrarError(ex);
        }
    }

    private void mostrarDocumentos() {
        try {
            List<Documento> lista = gestor.obtenerDocumentos();
            areaSalida.setText("Documentos:\n");
            for (Documento d : lista) {
                areaSalida.append(d.toString() + "\n");
            }
        } catch (SQLException ex) {
            mostrarError(ex);
        }
    }

    private void mostrarPrestamos() {
        try {
            List<Prestamo> lista = gestor.obtenerPrestamosActivos();
            areaSalida.setText("Préstamos activos:\n");
            for (Prestamo p : lista) {
                areaSalida.append(p.toString() + "\n");
            }
        } catch (SQLException ex) {
            mostrarError(ex);
        }
    }

    private void cerrarSesion() throws SQLException {
        dispose(); // Cierra esta ventana
        try {
            new LoginWindow().setVisible(true); // Muestra la ventana de login
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void mostrarError(Exception e) {
        areaSalida.setText("Error: " + e.getMessage());
        e.printStackTrace();
    }
}
