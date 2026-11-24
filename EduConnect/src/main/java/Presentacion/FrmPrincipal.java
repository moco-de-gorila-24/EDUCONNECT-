/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import Interfaces.IRefrescable;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
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
import Presentacion.Paneles.*;

/**
 * Ventana principal que muestra el menú y los paneles según el rol.
 *
 * @author Jorge
 */
public class FrmPrincipal extends JFrame {

    private final String nombreUsuario;
    private final String tipoUsuario;
    private JPanel panelContenido;
    private CardLayout cardLayout;
    private JLabel lblNombreUsuario;
    private PanelCursos panelCursos;

    private final Map<String, JPanel> paneles = new HashMap<>();

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

        if (tipoUsuario.equalsIgnoreCase("Estudiante")) {
            agregarPanel("inicio", new PanelInicioEstudiante());
            panelCursos = new PanelCursos();
            agregarPanel("cursos", panelCursos);
            agregarPanel("actividades", new PanelActividades());
            agregarPanel("calificaciones", new PanelCalificaciones());
            agregarPanel("historial", new PanelHistorialAcademico());

            agregarBotonMenu(panelMenu, "Inicio", "inicio");
            agregarBotonMenu(panelMenu, "Cursos disponibles", "cursos_disponibles");
            agregarBotonMenu(panelMenu, "Mis cursos", "cursos_inscritos");
            agregarBotonMenu(panelMenu, "Actividades", "actividades");
            agregarBotonMenu(panelMenu, "Calificaciones", "calificaciones");
            agregarBotonMenu(panelMenu, "Historial académico", "historial");
        } else {
            agregarPanel("inicio", new PanelInicioProfesor());
            agregarPanel("cursos", new PanelGestionCursos());
            agregarPanel("actividades", new PanelGestionActividades());
            agregarPanel("entregas", new PanelRevisarEntregas());
            agregarPanel("estadisticas", new PanelEstadisticas());

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
        mostrarPanel("inicio");
    }

    private void agregarPanel(String nombre, JPanel panel) {
        paneles.put(nombre, panel);
        panelContenido.add(panel, nombre);
    }

    private void agregarBotonMenu(JPanel panelMenu, String texto, String accion) {
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
        boton.addActionListener(e -> manejarAccionMenu(accion));
        panelMenu.add(boton);
        panelMenu.add(Box.createVerticalStrut(5));
    }

    private void manejarAccionMenu(String accion) {
        switch (accion) {
            case "cursos_disponibles" -> {
                mostrarPanel("cursos");
                if (panelCursos != null) panelCursos.mostrarPestania(0);
            }
            case "cursos_inscritos" -> {
                mostrarPanel("cursos");
                if (panelCursos != null) panelCursos.mostrarPestania(1);
            }
            default -> mostrarPanel(accion);
        }
    }

    private void mostrarPanel(String nombre) {
        JPanel p = paneles.get(nombre);
        if (p == null) return;
        cardLayout.show(panelContenido, nombre);
        if (p instanceof IRefrescable) ((IRefrescable) p).refrescar();
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Deseas cerrar sesión?", "Cerrar sesión",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            DatosSistema.usuarioActual = null;
            new FrmLogin().setVisible(true);
            dispose();
        }
    }
}
