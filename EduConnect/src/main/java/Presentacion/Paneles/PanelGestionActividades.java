/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Paneles;

import Interfaces.IRefrescable;
import Presentacion.DatosSistema;
import Presentacion.Dialogos.DialogActividad;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
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
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Panel para que el profesor administre actividades de sus cursos.
 *
 * @author Jorge
 */
public class PanelGestionActividades extends JPanel implements IRefrescable {

    private static final Font TITULO = new Font("Verdana", Font.BOLD, 20);
    private static final Font SUBTITULO = new Font("Tahoma", Font.PLAIN, 13);
    private static final Font BOTON = new Font("Tahoma", Font.BOLD, 12);
    private static final Color AZUL = new Color(31, 78, 121);
    private static final Color ROJO = new Color(160, 60, 60);
    private static final Color FONDO = new Color(245, 247, 250);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Curso", "Título", "Publicación", "Límite", "Puntaje máx."}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private List<DatosSistema.Actividad> lista = new ArrayList<>();

    public PanelGestionActividades() {
        setLayout(new BorderLayout(0, 15));
        setBackground(FONDO);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel lblTitulo = new JLabel("Gestionar actividades");
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(new Color(35, 45, 60));

        JLabel lblSub = new JLabel("Registra, edita o elimina las actividades de tus cursos.");
        lblSub.setFont(SUBTITULO);
        lblSub.setForeground(Color.GRAY);

        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setBackground(FONDO);
        encabezado.add(lblTitulo);
        encabezado.add(Box.createVerticalStrut(6));
        encabezado.add(lblSub);

        add(encabezado, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(barraAcciones(), BorderLayout.SOUTH);
        refrescar();
    }

    private JPanel barraAcciones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        p.setBackground(FONDO);
        JButton btnNuevo = crearBoton("Nueva actividad", AZUL);
        JButton btnEditar = crearBoton("Editar", AZUL);
        JButton btnEliminar = crearBoton("Eliminar", ROJO);
        btnNuevo.addActionListener(e -> abrirDialogo(null));
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        p.add(btnNuevo);
        p.add(btnEditar);
        p.add(btnEliminar);
        return p;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton b = new JButton(texto);
        b.setFont(BOTON);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        return b;
    }

    @Override
    public boolean refrescar() {
        modelo.setRowCount(0);
        lista.clear();
        String correo = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.correo : "";
        for (DatosSistema.Curso c : DatosSistema.cursosDelProfesor(correo)) {
            for (DatosSistema.Actividad a : DatosSistema.actividadesDelCurso(c.id)) {
                lista.add(a);
                modelo.addRow(new Object[]{
                        c.nombre, a.titulo, a.fechaPublicacion, a.fechaLimite,
                        String.format("%.1f", a.puntuacionMaxima)
                });
            }
        }
        return true;
    }

    private void abrirDialogo(DatosSistema.Actividad actividad) {
        DialogActividad dlg = new DialogActividad(
                (Dialog) SwingUtilities.getWindowAncestor(this), actividad);
        dlg.setVisible(true);
        if (dlg.isExitoso()) refrescar();
    }

    private void editar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una actividad.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        abrirDialogo(lista.get(fila));
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una actividad.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        DatosSistema.Actividad a = lista.get(fila);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar la actividad \"" + a.titulo + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        DatosSistema.entregas.removeIf(e -> e.idActividad.equals(a.id));
        DatosSistema.calificaciones.removeIf(c -> c.idActividad.equals(a.id));
        DatosSistema.actividades.remove(a);
        refrescar();
    }
}
