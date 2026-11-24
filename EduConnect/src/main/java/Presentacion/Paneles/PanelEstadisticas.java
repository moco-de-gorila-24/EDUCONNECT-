/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Paneles;

import Interfaces.IRefrescable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Presentacion.DatosSistema;

/**
 * Panel de estadísticas generales por curso para el rol Profesor.
 *
 * @author Jorge
 */
public class PanelEstadisticas extends JPanel implements IRefrescable {

    private static final Font TITULO = new Font("Verdana", Font.BOLD, 20);
    private static final Font SUBTITULO = new Font("Tahoma", Font.PLAIN, 13);
    private static final Color FONDO = new Color(245, 247, 250);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Curso", "Periodo", "Inscritos", "Actividades", "Entregas", "Promedio"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    public PanelEstadisticas() {
        setLayout(new BorderLayout(0, 15));
        setBackground(FONDO);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel lblTitulo = new JLabel("Estadísticas");
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(new Color(35, 45, 60));

        JLabel lblSub = new JLabel("Resumen general de tus cursos.");
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

        refrescar();
    }

    @Override
    public boolean refrescar() {
        modelo.setRowCount(0);
        String correo = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.correo : "";
        for (DatosSistema.Curso c : DatosSistema.cursosDelProfesor(correo)) {
            int inscritos = DatosSistema.contarInscritos(c.id);
            int actividades = 0;
            int entregas = 0;
            double suma = 0;
            int conCalif = 0;
            for (DatosSistema.Actividad a : DatosSistema.actividadesDelCurso(c.id)) {
                actividades++;
                entregas += DatosSistema.entregasDeActividad(a.id).size();
                for (DatosSistema.Calificacion cal : DatosSistema.calificaciones) {
                    if (cal.idActividad.equals(a.id)) {
                        suma += cal.calificacion;
                        conCalif++;
                    }
                }
            }
            String promedio = conCalif == 0 ? "—" : String.format("%.2f", suma / conCalif);
            modelo.addRow(new Object[]{
                    c.nombre, c.periodo, inscritos, actividades, entregas, promedio
            });
        }
        return true;
    }
}
