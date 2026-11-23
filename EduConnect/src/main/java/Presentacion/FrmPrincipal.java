/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jorge
 */
public class FrmPrincipal extends JFrame {
    private final String nombreUsuario;
    private final String tipoUsuario;
    private JPanel panelContenido;
    private CardLayout cardLayout;
    private JLabel lblNombreUsuario;

    private final Color AZUL = new Color(31, 78, 121);
    private final Color FONDO = new Color(245, 247, 250);

    public FrmPrincipal(String nombreUsuario, String tipoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.tipoUsuario = tipoUsuario;

        setTitle("EduConnect - Plataforma académica");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(950, 600));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(FONDO);

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));

        panelMenu.setBackground(AZUL);
        panelMenu.setPreferredSize(new Dimension(240, 750));
        panelMenu.setBorder(new EmptyBorder(25, 15, 20, 15));

        JLabel lblLogo = new JLabel("EduConnect");
        lblLogo.setFont(new Font("SansSerif", Font.BOLD, 25));
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTipo = new JLabel(tipoUsuario);
        lblTipo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTipo.setForeground(new Color(210, 225, 240));
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelMenu.add(lblLogo);
        panelMenu.add(Box.createVerticalStrut(8));
        panelMenu.add(lblTipo);
        panelMenu.add(Box.createVerticalStrut(35));

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(FONDO);

        agregarPanel("inicio", crearPanelInicio());

        if (tipoUsuario.equalsIgnoreCase("Estudiante")) {
            agregarPanel("cursos", crearPanelSeccion("Cursos disponibles", "Consulta los cursos disponibles e inscríbete."));
            agregarPanel("misCursos", crearPanelSeccion("Mis cursos", "Aquí aparecerán los cursos en los que estás inscrito."));
            agregarPanel("actividades", crearPanelSeccion("Actividades", "Consulta y entrega tus actividades académicas."));
            agregarPanel("calificaciones", crearPanelSeccion("Calificaciones", "Consulta tus calificaciones y observaciones."));
            agregarPanel("historial", crearPanelSeccion("Historial académico", "Consulta tu historial de cursos y calificaciones."));

            agregarBotonMenu(panelMenu, "Inicio", "inicio");
            agregarBotonMenu(panelMenu, "Cursos disponibles", "cursos");
            agregarBotonMenu(panelMenu, "Mis cursos", "misCursos");
            agregarBotonMenu(panelMenu, "Actividades", "actividades");
            agregarBotonMenu(panelMenu, "Calificaciones", "calificaciones");
            agregarBotonMenu(panelMenu, "Historial académico", "historial");
        } else {
            agregarPanel("cursos", crearPanelSeccion("Gestionar cursos", "Crea, consulta, modifica y elimina tus cursos."));
            agregarPanel("actividades", crearPanelSeccion("Gestionar actividades", "Registra y administra las actividades de tus cursos."));
            agregarPanel("entregas", crearPanelSeccion("Revisar entregas", "Consulta las entregas y asigna calificaciones."));
            agregarPanel("estadisticas", crearPanelSeccion("Estadísticas", "Consulta las estadísticas generales de tus cursos."));

            agregarBotonMenu(panelMenu, "Inicio", "inicio");
            agregarBotonMenu(panelMenu, "Gestionar cursos", "cursos");
            agregarBotonMenu(panelMenu, "Gestionar actividades", "actividades");
            agregarBotonMenu(panelMenu, "Revisar entregas", "entregas");
            agregarBotonMenu(panelMenu, "Estadísticas", "estadisticas");
        }

        panelMenu.add(Box.createVerticalGlue());

        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setBackground(new Color(20, 55, 90));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCerrarSesion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        panelMenu.add(btnCerrarSesion);

        JPanel panelEncabezado = new JPanel(new BorderLayout());
        panelEncabezado.setBackground(Color.WHITE);
        panelEncabezado.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Plataforma académica");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(35, 45, 60));

        lblNombreUsuario = new JLabel(nombreUsuario);
        lblNombreUsuario.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNombreUsuario.setForeground(new Color(80, 90, 105));

        panelEncabezado.add(lblTitulo, BorderLayout.WEST);
        panelEncabezado.add(lblNombreUsuario, BorderLayout.EAST);

        JPanel panelCentral = new JPanel(new BorderLayout());

        panelCentral.setBackground(FONDO);
        panelCentral.add(panelEncabezado, BorderLayout.NORTH);
        panelCentral.add(panelContenido, BorderLayout.CENTER);

        panelPrincipal.add(panelMenu, BorderLayout.WEST);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
        cardLayout.show(panelContenido, "inicio");
    }

    private void agregarPanel(String nombre, JPanel panel) {
        panelContenido.add(panel, nombre);
    }

    private void agregarBotonMenu(JPanel panelMenu, String texto, String nombrePanel) {
        JButton boton = new JButton(texto);

        boton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(AZUL);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setBorder(new EmptyBorder(12, 10, 12, 10));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        boton.addActionListener(e -> {
            cardLayout.show(panelContenido, nombrePanel);
        });

        panelMenu.add(boton);
        panelMenu.add(Box.createVerticalStrut(5));
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(35, 35, 35, 35));

        JPanel panelContenidoInicio = new JPanel();

        panelContenidoInicio.setLayout(new BoxLayout(panelContenidoInicio, BoxLayout.Y_AXIS));
        panelContenidoInicio.setBackground(FONDO);

        JLabel lblBienvenida = new JLabel("¡Bienvenido a EduConnect!");

        lblBienvenida.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(35, 45, 60));

        JLabel lblDescripcion = new JLabel("Tu espacio para aprender y crecer.");

        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblDescripcion.setForeground(Color.GRAY);

        panelContenidoInicio.add(lblBienvenida);
        panelContenidoInicio.add(Box.createVerticalStrut(12));
        panelContenidoInicio.add(lblDescripcion);
        panelContenidoInicio.add(Box.createVerticalStrut(30));

        if (tipoUsuario.equalsIgnoreCase("Estudiante")) {
            panelContenidoInicio.add(crearTarjeta("Mis cursos", "Consulta tus cursos inscritos."));
            panelContenidoInicio.add(Box.createVerticalStrut(15));
            panelContenidoInicio.add(crearTarjeta("Actividades pendientes", "Revisa las actividades que debes entregar."));
            panelContenidoInicio.add(Box.createVerticalStrut(15));
            panelContenidoInicio.add(crearTarjeta("Calificaciones", "Consulta tus resultados académicos."));
        } else {
            panelContenidoInicio.add(crearTarjeta("Mis cursos", "Administra los cursos que has creado."));
            panelContenidoInicio.add(Box.createVerticalStrut(15));
            panelContenidoInicio.add(crearTarjeta("Actividades", "Registra y administra las actividades académicas."));
            panelContenidoInicio.add(Box.createVerticalStrut(15));
            panelContenidoInicio.add(crearTarjeta("Entregas", "Consulta los trabajos de tus estudiantes."));
        }

        panel.add(panelContenidoInicio, BorderLayout.NORTH);
        return panel;
    }

    private JPanel crearTarjeta(String titulo, String descripcion) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(225, 230, 235)), new EmptyBorder(20, 20, 20, 20)));
        tarjeta.setAlignmentX(Component.LEFT_ALIGNMENT);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblTitulo.setForeground(AZUL);

        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescripcion.setForeground(new Color(100, 110, 125));

        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblDescripcion);

        return tarjeta;
    }

    private JPanel crearPanelSeccion(String titulo, String descripcion) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(FONDO);
        panel.setBorder(new EmptyBorder(35, 35, 35, 35));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(FONDO);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 27));
        lblTitulo.setForeground(new Color(35, 45, 60));

        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblDescripcion.setForeground(Color.GRAY);

        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createVerticalStrut(10));
        panelSuperior.add(lblDescripcion);

        JPanel panelContenidoSeccion = new JPanel(new BorderLayout());
        panelContenidoSeccion.setBackground(Color.WHITE);
        panelContenidoSeccion.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel lblMensaje = new JLabel("Esta sección está preparada para agregar sus componentes.");
        lblMensaje.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblMensaje.setForeground(new Color(100, 110, 125));

        panelContenidoSeccion.add(lblMensaje, BorderLayout.NORTH);

        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelContenidoSeccion, BorderLayout.CENTER);

        return panel;
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Deseas cerrar sesión?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            new FrmLogin().setVisible(true);
            dispose();
        }
    }
}
