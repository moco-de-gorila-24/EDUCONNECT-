/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Paneles;

import Interfaces.IRefrescable;
import Presentacion.DatosSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Panel de cursos para el rol Estudiante.
 * Contiene dos pestañas: cursos disponibles para inscribirse y cursos ya inscritos.
 *
 * @author Jorge
 */
public class PanelCursos extends JPanel implements IRefrescable {

    private static final Font TITULO = new Font("Verdana", Font.BOLD, 20);
    private static final Font SUBTITULO = new Font("Tahoma", Font.PLAIN, 13);
    private static final Font BOTON = new Font("Tahoma", Font.BOLD, 13);
    private static final Color AZUL = new Color(31, 78, 121);
    private static final Color FONDO = new Color(245, 247, 250);

    private final JTabbedPane tabs = new JTabbedPane();

    private final DefaultTableModel modDisponibles = modelo(
            new String[]{"Nombre", "Área", "Periodo", "Profesor"});
    private final DefaultTableModel modInscritos = modelo(
            new String[]{"Nombre", "Área", "Periodo", "Profesor"});

    private final JTable tblDisponibles = new JTable(modDisponibles);
    private final JTable tblInscritos = new JTable(modInscritos);

    private final List<DatosSistema.Curso> listaDisponibles = new ArrayList<>();

    public PanelCursos() {
        setLayout(new BorderLayout(0, 15));
        setBackground(FONDO);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel lblTitulo = new JLabel("Cursos");
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(new Color(35, 45, 60));

        JLabel lblSub = new JLabel("Consulta los cursos disponibles e inscríbete o revisa los que ya cursas.");
        lblSub.setFont(SUBTITULO);
        lblSub.setForeground(Color.GRAY);

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(FONDO);
        encabezado.add(lblTitulo);
        encabezado.add(Box.createVerticalStrut(6));
        encabezado.add(lblSub);

        tabs.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tabs.addTab("Cursos disponibles", construirDisponibles());
        tabs.addTab("Mis cursos", construirInscritos());

        add(encabezado, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);

        refrescar();
    }

    private JPanel construirDisponibles() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(tblDisponibles);
        p.add(scroll, BorderLayout.CENTER);

        JButton btnInscribirse = new JButton("Inscribirme en el curso seleccionado");
        estilizarBoton(btnInscribirse);
        btnInscribirse.addActionListener(e -> inscribirse());
        p.add(btnInscribirse, BorderLayout.SOUTH);
        return p;
    }

    private JPanel construirInscritos() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(12, 12, 12, 12));
        p.add(new JScrollPane(tblInscritos), BorderLayout.CENTER);
        return p;
    }

    private void estilizarBoton(JButton b) {
        b.setFont(BOTON);
        b.setBackground(AZUL);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
    }

    private static DefaultTableModel modelo(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
    }

    /**
     * Cambia la pestaña visible.
     *
     * @param indice 0 = disponibles, 1 = inscritos.
     */
    public void mostrarPestania(int indice) {
        if (indice >= 0 && indice < tabs.getTabCount()) {
            tabs.setSelectedIndex(indice);
        }
    }

    @Override
    public boolean refrescar() {
        cargarDisponibles();
        cargarInscritos();
        return true;
    }

    private void cargarDisponibles() {
        listaDisponibles.clear();
        modDisponibles.setRowCount(0);
        String correo = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.correo : "";
        for (DatosSistema.Curso c : DatosSistema.cursos) {
            if (DatosSistema.estaInscrito(correo, c.id)) continue;
            listaDisponibles.add(c);
            modDisponibles.addRow(new Object[]{
                    c.nombre, c.area, c.periodo,
                    nombreProfesor(c.correoProfesor)
            });
        }
    }

    private void cargarInscritos() {
        modInscritos.setRowCount(0);
        String correo = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.correo : "";
        for (DatosSistema.Curso c : DatosSistema.cursosInscritos(correo)) {
            modInscritos.addRow(new Object[]{
                    c.nombre, c.area, c.periodo,
                    nombreProfesor(c.correoProfesor)
            });
        }
    }

    private String nombreProfesor(String correo) {
        DatosSistema.Usuario u = DatosSistema.buscarUsuarioPorCorreo(correo);
        return u != null ? u.nombre : correo;
    }

    private void inscribirse() {
        int fila = tblDisponibles.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un curso de la lista.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        DatosSistema.Curso curso = listaDisponibles.get(fila);
        String correo = DatosSistema.usuarioActual.correo;
        if (DatosSistema.estaInscrito(correo, curso.id)) {
            JOptionPane.showMessageDialog(this,
                    "Ya estás inscrito en este curso.",
                    "Duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DatosSistema.inscripciones.add(correo + "|" + curso.id);
        JOptionPane.showMessageDialog(this, "Inscripción realizada.");
        refrescar();
    }
}
