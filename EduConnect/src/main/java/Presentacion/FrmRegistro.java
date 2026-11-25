/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import DAO.UsuarioDAO;
import Entidades.TipoUsuario;
import Entidades.Usuario;
import Excepciones.DatosDuplicadosException;
import Interfaces.IUsuarioDAO;
import Utilidades.PasswordUtil;
import com.github.lgooddatepicker.components.DatePicker;
import com.mongodb.MongoException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jorge
 */
public class FrmRegistro extends JFrame {
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JPasswordField txtContrasena;
    private DatePicker dateFechaNacimiento;
    private JComboBox<String> cmbTipoUsuario;
    

    public FrmRegistro() {
        setTitle("EduConnect - Registro");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        JPanel panelEncabezado = new JPanel();
        panelEncabezado.setLayout(new BoxLayout(panelEncabezado, BoxLayout.Y_AXIS));

        panelEncabezado.setBackground(new Color(31, 78, 121));
        panelEncabezado.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel lblTitulo = new JLabel("Crear cuenta");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Regístrate para utilizar EduConnect");

        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(225, 235, 245));

        panelEncabezado.add(lblTitulo);
        panelEncabezado.add(Box.createVerticalStrut(8));
        panelEncabezado.add(lblSubtitulo);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));

        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new EmptyBorder(25, 45, 25, 45));

        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtTelefono = new JTextField();
        txtContrasena = new JPasswordField();
        dateFechaNacimiento = new DatePicker();
        dateFechaNacimiento.getSettings().setLocale(new Locale("es", "MX"));

        cmbTipoUsuario = new JComboBox<>(new String[]{"Estudiante", "Profesor"});

        agregarCampo(panelFormulario, "Nombre completo", txtNombre);
        agregarCampo(panelFormulario, "Correo electrónico", txtCorreo);
        agregarCampo(panelFormulario, "Teléfono", txtTelefono);
        agregarCampo(panelFormulario, "Contraseña", txtContrasena);
        agregarCampo(panelFormulario, "Fecha de nacimiento", dateFechaNacimiento);
        agregarCampo(panelFormulario, "Tipo de usuario", cmbTipoUsuario);

        JButton btnRegistrar = crearBoton("Registrar cuenta");
        JButton btnRegresar = new JButton("Volver al inicio de sesión");

        btnRegresar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnRegresar.setForeground(new Color(31, 78, 121));
        btnRegresar.setBackground(Color.WHITE);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelFormulario.add(Box.createVerticalStrut(15));
        panelFormulario.add(btnRegistrar);
        panelFormulario.add(Box.createVerticalStrut(8));
        panelFormulario.add(btnRegresar);

        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnRegresar.addActionListener(e -> regresarLogin());
    }

    private void agregarCampo(JPanel panel, String etiqueta, JComponent campo) {
        JLabel lbl = new JLabel(etiqueta);

        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(new Color(60, 70, 85));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setPreferredSize(new Dimension(400, 38));

        if (campo instanceof JTextField) {
            ((JTextField) campo).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)), new EmptyBorder(5, 10, 5, 10)));
        }

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(12));
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(31, 78, 121));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        return boton;
    }

    private void registrarUsuario() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim().toLowerCase();
        String telefono = txtTelefono.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        LocalDate fecha = dateFechaNacimiento.getDate();
        String tipoUsuario = cmbTipoUsuario.getSelectedItem().toString();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasena.isEmpty() || fecha == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (nombre.length() < 3) {
            JOptionPane.showMessageDialog(this, "El nombre debe tener al menos 3 caracteres.");
            return;
        }

        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Ingresa un correo válido.");
            return;
        }

        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener 10 dígitos.");
            return;
        }

        if (contrasena.length() < 8) {
            JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres.");
            return;
        }
      
        if (!validarFecha(fecha)) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento no es válida.");
            return;
        }

        try {
            String passwordHash = PasswordUtil.hash(contrasena);
            Usuario usuario = new Usuario(UUID.randomUUID().toString(), nombre, correo, telefono, passwordHash, fecha, TipoUsuario.valueOf(tipoUsuario.toUpperCase()), true);
            IUsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean registrado = usuarioDAO.registrar(usuario);

            if (registrado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                regresarLogin();
            }
        } catch (DatosDuplicadosException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Correo duplicado", JOptionPane.WARNING_MESSAGE);
        } catch (MongoException e) {
            JOptionPane.showMessageDialog(this, "No fue posible conectar con MongoDB.\nVerifica que el servidor esté iniciado.", "Error de persistencia", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarFecha(LocalDate fecha) {
        if (fecha == null) {
            return false;
        }

        return !fecha.isAfter(LocalDate.now());
    }

    private void regresarLogin() {
        new FrmLogin().setVisible(true);
        dispose();
    }
}
