package gui;

import model.Documento;
import model.Prestamo;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private final GestorBiblioteca gestor;

    private JTextArea areaSalida;

    public VentanaPrincipal() {
        gestor = new GestorBiblioteca();
        setTitle("Sistema Biblioteca Don Bosco");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        construirUI();
    }

    private void construirUI() {
        JPanel panel = new JPanel(new BorderLayout());

        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaSalida);

        JButton btnUsuarios = new JButton("Mostrar Usuarios");
        JButton btnDocumentos = new JButton("Mostrar Documentos");
        JButton btnPrestamos = new JButton("Prestamos Activos");

        btnUsuarios.addActionListener(e -> mostrarUsuarios());
        btnDocumentos.addActionListener(e -> mostrarDocumentos());
        btnPrestamos.addActionListener(e -> mostrarPrestamos());

        JPanel botones = new JPanel();
        botones.add(btnUsuarios);
        botones.add(btnDocumentos);
        botones.add(btnPrestamos);

        panel.add(botones, BorderLayout.NORTH);
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

    private void mostrarError(Exception e) {
        areaSalida.setText("Error: " + e.getMessage());
        e.printStackTrace();
    }
}
