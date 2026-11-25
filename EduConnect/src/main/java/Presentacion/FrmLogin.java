/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jorge
 */
public class FrmLogin extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JComboBox<String> cmbTipoUsuario;

    public FrmLogin() {
        setTitle("EduConnect - Iniciar sesión");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));

        panelIzquierdo.setBackground(new Color(31, 78, 121));
        panelIzquierdo.setPreferredSize(new Dimension(350, 550));
        panelIzquierdo.setBorder(new EmptyBorder(60, 35, 40, 35));

        JLabel lblLogo = new JLabel("EduConnect");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel("<html>Conecta con tu<br>futuro académico</html>");

        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDescripcion = new JLabel("<html>Consulta tus cursos, entrega actividades y mantén un seguimiento de tu progreso académico.</html>");

        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblDescripcion.setForeground(new Color(220, 230, 240));
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelIzquierdo.add(lblLogo);
        panelIzquierdo.add(Box.createVerticalStrut(65));
        panelIzquierdo.add(lblTitulo);
        panelIzquierdo.add(Box.createVerticalStrut(20));
        panelIzquierdo.add(lblDescripcion);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));

        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new EmptyBorder(65, 65, 50, 65));

        JLabel lblBienvenida = new JLabel("Bienvenido");
        lblBienvenida.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(35, 45, 60));

        JLabel lblSubtitulo = new JLabel("Inicia sesión para continuar");

        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.GRAY);

        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();

        cmbTipoUsuario = new JComboBox<>(new String[]{"Estudiante", "Profesor"});

        configurarCampo(txtCorreo, "Correo electrónico");
        configurarCampo(txtContrasena, "Contraseña");
        configurarCombo(cmbTipoUsuario);

        JButton btnIniciar = crearBoton("Iniciar sesión", new Color(31, 78, 121));
        JButton btnRegistro = new JButton("Crear una cuenta");

        btnRegistro.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnRegistro.setForeground(new Color(31, 78, 121));
        btnRegistro.setBackground(Color.WHITE);
        btnRegistro.setBorderPainted(false);
        btnRegistro.setFocusPainted(false);
        btnRegistro.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelFormulario.add(lblBienvenida);
        panelFormulario.add(Box.createVerticalStrut(10));
        panelFormulario.add(lblSubtitulo);
        panelFormulario.add(Box.createVerticalStrut(30));

        panelFormulario.add(crearEtiqueta("Correo electrónico"));
        panelFormulario.add(Box.createVerticalStrut(8));
        panelFormulario.add(txtCorreo);

        panelFormulario.add(Box.createVerticalStrut(18));
        panelFormulario.add(crearEtiqueta("Contraseña"));
        panelFormulario.add(Box.createVerticalStrut(8));
        panelFormulario.add(txtContrasena);

        panelFormulario.add(Box.createVerticalStrut(18));
        panelFormulario.add(crearEtiqueta("Tipo de usuario"));
        panelFormulario.add(Box.createVerticalStrut(8));
        panelFormulario.add(cmbTipoUsuario);

        panelFormulario.add(Box.createVerticalStrut(28));
        panelFormulario.add(btnIniciar);

        panelFormulario.add(Box.createVerticalStrut(12));
        panelFormulario.add(btnRegistro);

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        btnIniciar.addActionListener(e -> iniciarSesion());

        btnRegistro.addActionListener(e -> {
            new FrmRegistro().setVisible(true);
            dispose();
        });
    }

    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        String tipoUsuario = cmbTipoUsuario.getSelectedItem().toString();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.",
                    "Campos obligatorios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Ingresa un correo electrónico válido.",
                    "Correo incorrecto", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DatosSistema.Usuario u = DatosSistema.buscarUsuarioPorCorreo(correo);
        if (u == null) {
            JOptionPane.showMessageDialog(this,
                    "No existe una cuenta con ese correo. Regístrate primero.",
                    "Usuario no encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!u.tipoUsuario.equalsIgnoreCase(tipoUsuario)) {
            JOptionPane.showMessageDialog(this,
                    "El tipo de usuario no coincide con la cuenta registrada.",
                    "Tipo de usuario incorrecto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!u.contrasenaHash.equals(DatosSistema.hashear(contrasena))) {
            JOptionPane.showMessageDialog(this,
                    "Contraseña incorrecta.", "Acceso denegado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DatosSistema.usuarioActual = u;
        JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
        new FrmPrincipal(u.nombre, u.tipoUsuario).setVisible(true);
        dispose();
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, 13));
        etiqueta.setForeground(new Color(60, 70, 85));
        etiqueta.setAlignmentX(Component.LEFT_ALIGNMENT);
        return etiqueta;
    }

    private void configurarCampo(JTextField campo, String texto) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        campo.setPreferredSize(new Dimension(300, 42));
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)), new EmptyBorder(8, 12, 8, 12)));
        campo.setToolTipText(texto);
    }

    private void configurarCombo(JComboBox<String> combo) {
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        combo.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        boton.setPreferredSize(new Dimension(300, 45));
        return boton;
    }
}
