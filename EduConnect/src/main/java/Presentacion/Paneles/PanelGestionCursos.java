/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Paneles;

import Interfaces.IRefrescable;
import Presentacion.DatosSistema;
import Presentacion.Dialogos.DialogCurso;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
 * Panel para que el profesor administre sus cursos.
 *
 * @author Jorge
 */
public class PanelGestionCursos extends JPanel implements IRefrescable {

    private static final Font TITULO = new Font("Verdana", Font.BOLD, 20);
    private static final Font SUBTITULO = new Font("Tahoma", Font.PLAIN, 13);
    private static final Font BOTON = new Font("Tahoma", Font.BOLD, 12);
    private static final Color AZUL = new Color(31, 78, 121);
    private static final Color ROJO = new Color(160, 60, 60);
    private static final Color FONDO = new Color(245, 247, 250);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Nombre", "Área", "Periodo", "Inscritos"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(modelo);
    private List<DatosSistema.Curso> lista;

    public PanelGestionCursos() {
        setLayout(new BorderLayout(0, 15));
        setBackground(FONDO);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel lblTitulo = new JLabel("Gestionar cursos");
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(new Color(35, 45, 60));

        JLabel lblSub = new JLabel("Crea, edita o elimina los cursos que impartes.");
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

        JButton btnNuevo = crearBoton("Nuevo curso", AZUL);
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
        String correo = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.correo : "";
        lista = DatosSistema.cursosDelProfesor(correo);
        for (DatosSistema.Curso c : lista) {
            modelo.addRow(new Object[]{
                    c.nombre, c.area, c.periodo,
                    DatosSistema.contarInscritos(c.id)
            });
        }
        return true;
    }

    private void abrirDialogo(DatosSistema.Curso curso) {
        DialogCurso dlg = new DialogCurso((Dialog) SwingUtilities.getWindowAncestor(this), curso);
        dlg.setVisible(true);
        if (dlg.isExitoso()) refrescar();
    }

    private void editar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un curso.", "Sin selección",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        abrirDialogo(lista.get(fila));
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un curso.", "Sin selección",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        DatosSistema.Curso curso = lista.get(fila);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el curso \"" + curso.nombre + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        DatosSistema.actividades.removeIf(a -> a.idCurso.equals(curso.id));
        DatosSistema.inscripciones.removeIf(s -> s.endsWith("|" + curso.id));
        DatosSistema.cursos.remove(curso);
        refrescar();
    }
}
