/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Paneles;

import Interfaces.IRefrescable;
import Presentacion.DatosSistema;
import Presentacion.Dialogos.DialogEntrega;
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
 * Panel de actividades para el rol Estudiante.
 * Muestra las actividades de los cursos inscritos y permite entregarlas.
 *
 * @author Jorge
 */
public class PanelActividades extends JPanel implements IRefrescable {

    private static final Font TITULO = new Font("Verdana", Font.BOLD, 20);
    private static final Font SUBTITULO = new Font("Tahoma", Font.PLAIN, 13);
    private static final Font BOTON = new Font("Tahoma", Font.BOLD, 12);
    private static final Color AZUL = new Color(31, 78, 121);
    private static final Color FONDO = new Color(245, 247, 250);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Curso", "Actividad", "Fecha límite", "Puntaje máx.", "Estado"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private List<DatosSistema.Actividad> lista = new ArrayList<>();

    public PanelActividades() {
        setLayout(new BorderLayout(0, 15));
        setBackground(FONDO);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel lblTitulo = new JLabel("Actividades");
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(new Color(35, 45, 60));

        JLabel lblSub = new JLabel("Consulta y entrega las actividades de tus cursos.");
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

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setBackground(FONDO);
        JButton btnEntregar = new JButton("Entregar actividad seleccionada");
        btnEntregar.setFont(BOTON);
        btnEntregar.setBackground(AZUL);
        btnEntregar.setForeground(Color.WHITE);
        btnEntregar.setFocusPainted(false);
        btnEntregar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnEntregar.addActionListener(e -> entregar());
        acciones.add(btnEntregar);
        add(acciones, BorderLayout.SOUTH);

        refrescar();
    }

    @Override
    public boolean refrescar() {
        modelo.setRowCount(0);
        lista.clear();
        String correo = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.correo : "";
        for (DatosSistema.Curso c : DatosSistema.cursosInscritos(correo)) {
            for (DatosSistema.Actividad a : DatosSistema.actividadesDelCurso(c.id)) {
                lista.add(a);
                boolean entregada = DatosSistema.entregaDeEstudiante(a.id, correo) != null;
                modelo.addRow(new Object[]{
                        c.nombre, a.titulo, a.fechaLimite,
                        String.format("%.1f", a.puntuacionMaxima),
                        entregada ? "Entregada" : "Pendiente"
                });
            }
        }
        return true;
    }

    private void entregar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una actividad.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        DatosSistema.Actividad a = lista.get(fila);
        DialogEntrega dlg = new DialogEntrega(
                (Dialog) SwingUtilities.getWindowAncestor(this), a);
        dlg.setVisible(true);
        if (dlg.isExitoso()) {
            JOptionPane.showMessageDialog(this, "Entrega registrada.");
            refrescar();
        }
    }
}
