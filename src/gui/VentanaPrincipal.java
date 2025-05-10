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
            mostrarError("Error al conectar con la base de datos: " + e.getMessage());
            return;
        }
        setTitle("Sistema Biblioteca Don Bosco - [" + rolUsuario.toUpperCase() + "]");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        construirUI();
    }

    private void construirUI() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel botones = new JPanel(new GridLayout(0, 1));

        JButton btnUsuarios = new JButton("Mostrar Usuarios");
        JButton btnDocumentos = new JButton("Mostrar Documentos");
        JButton btnPrestamos = new JButton("Prestamos Activos");

        JButton btnAgregarUsuario = new JButton("Agregar Usuario");
        if (!rolUsuario.equalsIgnoreCase("administrador") && !rolUsuario.equalsIgnoreCase("maestro")) {
            btnAgregarUsuario.setEnabled(false);
        }

        JButton btnAgregarDocumento = new JButton("Agregar Documento");
        if (!rolUsuario.equalsIgnoreCase("administrador") && !rolUsuario.equalsIgnoreCase("maestro")) {
            btnAgregarDocumento.setEnabled(false);
        }

        JButton btnPrestarDocumento = new JButton("Prestar Documento");
        JButton btnDevolverDocumento = new JButton("Devolver Documento");

        botones.add(btnUsuarios);
        botones.add(btnDocumentos);
        botones.add(btnPrestamos);
        botones.add(btnAgregarUsuario);
        botones.add(btnAgregarDocumento);
        botones.add(btnPrestarDocumento);
        botones.add(btnDevolverDocumento);

        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaSalida);

        panel.add(botones, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);
        add(panel);

        btnUsuarios.addActionListener(e -> mostrarUsuarios());
        btnDocumentos.addActionListener(e -> mostrarDocumentos());
        btnPrestamos.addActionListener(e -> mostrarPrestamos());
        btnAgregarUsuario.addActionListener(e -> agregarUsuario());
        btnAgregarDocumento.addActionListener(e -> agregarDocumento());
        btnPrestarDocumento.addActionListener(e -> prestarDocumento());
        btnDevolverDocumento.addActionListener(e -> devolverDocumento());
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarUsuarios() {
        try {
            List<Usuario> usuarios = gestor.obtenerUsuarios();
            areaSalida.setText("Usuarios:\n");
            for (Usuario u : usuarios) {
                areaSalida.append(u.toString() + "\n");
            }
        } catch (SQLException e) {
            mostrarError("Error al obtener usuarios: " + e.getMessage());
        }
    }

    private void mostrarDocumentos() {
        try {
            List<Documento> documentos = gestor.obtenerDocumentos();
            areaSalida.setText("Documentos:\n");
            for (Documento d : documentos) {
                areaSalida.append(d.toString() + "\n");
            }
        } catch (SQLException e) {
            mostrarError("Error al obtener documentos: " + e.getMessage());
        }
    }

    private void mostrarPrestamos() {
        try {
            List<Prestamo> prestamos = gestor.obtenerPrestamos();
            areaSalida.setText("Préstamos:\n");
            for (Prestamo p : prestamos) {
                areaSalida.append(p.toString() + "\n");
            }
        } catch (SQLException e) {
            mostrarError("Error al obtener préstamos: " + e.getMessage());
        }
    }

    private void agregarUsuario() {
        JDialog dialogo = new JDialog(this, "Agregar Usuario", true);
        dialogo.setSize(300, 200);
        dialogo.setLayout(new GridLayout(4, 2));
        dialogo.setLocationRelativeTo(this);

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();

        JLabel lblRol = new JLabel("Rol:");
        String[] roles = {"administrador", "maestro", "alumno"};
        JComboBox<String> comboRol = new JComboBox<>(roles);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String rol = (String) comboRol.getSelectedItem();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El nombre no puede estar vacío.");
                return;
            }

            try {
                gestor.agregarUsuario(nombre, rol);
                JOptionPane.showMessageDialog(dialogo, "Usuario agregado correctamente.");
                dialogo.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al agregar usuario: " + ex.getMessage());
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        dialogo.add(lblNombre);
        dialogo.add(txtNombre);
        dialogo.add(lblRol);
        dialogo.add(comboRol);
        dialogo.add(btnGuardar);
        dialogo.add(btnCancelar);

        dialogo.setVisible(true);
    }

    private void agregarDocumento() {
        JOptionPane.showMessageDialog(this, "Función para agregar documento aún no implementada.");
    }

    private void prestarDocumento() {
        JOptionPane.showMessageDialog(this, "Función para prestar documento aún no implementada.");
    }

    private void devolverDocumento() {
        JOptionPane.showMessageDialog(this, "Función para devolver documento aún no implementada.");
    }
}
