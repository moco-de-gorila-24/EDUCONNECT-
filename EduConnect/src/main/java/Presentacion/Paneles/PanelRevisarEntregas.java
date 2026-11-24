/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Paneles;

import Interfaces.IRefrescable;
import Presentacion.DatosSistema;
import Presentacion.Dialogos.DialogCalificacion;
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
 * Panel para que el profesor consulte y califique las entregas de sus estudiantes.
 *
 * @author Jorge
 */
public class PanelRevisarEntregas extends JPanel implements IRefrescable {

    private static final Font TITULO = new Font("Verdana", Font.BOLD, 20);
    private static final Font SUBTITULO = new Font("Tahoma", Font.PLAIN, 13);
    private static final Font BOTON = new Font("Tahoma", Font.BOLD, 12);
    private static final Color AZUL = new Color(31, 78, 121);
    private static final Color FONDO = new Color(245, 247, 250);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Curso", "Actividad", "Estudiante", "Tipo", "Fecha y hora", "Calificación"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private final List<DatosSistema.Entrega> lista = new ArrayList<>();

    public PanelRevisarEntregas() {
        setLayout(new BorderLayout(0, 15));
        setBackground(FONDO);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel lblTitulo = new JLabel("Revisar entregas");
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(new Color(35, 45, 60));

        JLabel lblSub = new JLabel("Consulta las entregas de tus estudiantes y asígnales una calificación.");
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
        JButton btnCalificar = new JButton("Calificar entrega seleccionada");
        btnCalificar.setFont(BOTON);
        btnCalificar.setBackground(AZUL);
        btnCalificar.setForeground(Color.WHITE);
        btnCalificar.setFocusPainted(false);
        btnCalificar.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btnCalificar.addActionListener(e -> calificar());
        acciones.add(btnCalificar);
        add(acciones, BorderLayout.SOUTH);

        refrescar();
    }

    @Override
    public boolean refrescar() {
        modelo.setRowCount(0);
        lista.clear();
        String correo = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.correo : "";
        for (DatosSistema.Curso c : DatosSistema.cursosDelProfesor(correo)) {
            for (DatosSistema.Actividad a : DatosSistema.actividadesDelCurso(c.id)) {
                for (DatosSistema.Entrega e : DatosSistema.entregasDeActividad(a.id)) {
                    DatosSistema.Calificacion cal = DatosSistema.calificacionDeEntrega(e.id);
                    String valor = cal == null ? "Sin calificar" : String.format("%.1f", cal.calificacion);
                    lista.add(e);
                    modelo.addRow(new Object[]{
                            c.nombre, a.titulo, nombreEstudiante(e.correoEstudiante),
                            e.tipo, e.fechaHoraEntrega, valor
                    });
                }
            }
        }
        return true;
    }

    private String nombreEstudiante(String correo) {
        DatosSistema.Usuario u = DatosSistema.buscarUsuarioPorCorreo(correo);
        return u != null ? u.nombre : correo;
    }

    private void calificar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una entrega.",
                    "Sin selección", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        DatosSistema.Entrega e = lista.get(fila);
        DatosSistema.Actividad a = DatosSistema.buscarActividadPorId(e.idActividad);
        if (a == null) {
            JOptionPane.showMessageDialog(this,
                    "La actividad asociada no existe.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        DialogCalificacion dlg = new DialogCalificacion(
                (Dialog) SwingUtilities.getWindowAncestor(this), e, a);
        dlg.setVisible(true);
        if (dlg.isExitoso()) refrescar();
    }
}
