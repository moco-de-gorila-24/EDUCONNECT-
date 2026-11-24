/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Dialogos;

import Presentacion.DatosSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Diálogo para crear o editar un curso.
 *
 * @author Jorge
 */
public class DialogCurso extends JDialog {

    private static final Font ETIQUETA = new Font("Tahoma", Font.BOLD, 12);
    private static final Font CAMPO = new Font("Tahoma", Font.PLAIN, 13);

    private final JTextField txtNombre = new JTextField();
    private final JTextArea txtDescripcion = new JTextArea(3, 20);
    private final JTextField txtArea = new JTextField();
    private final JTextField txtPeriodo = new JTextField();

    private final DatosSistema.Curso original;
    private boolean exitoso = false;

    /** Constructor para crear un curso nuevo. */
    public DialogCurso(Dialog propietario) {
        this(propietario, null);
    }

    /** Constructor para editar un curso existente. */
    public DialogCurso(Dialog propietario, DatosSistema.Curso curso) {
        super(propietario, true);
        this.original = curso;
        setTitle(curso == null ? "Nuevo curso" : "Editar curso");
        setSize(460, 460);
        setLocationRelativeTo(propietario);
        setResizable(false);
        inicializar();
        if (curso != null) precargar(curso);
    }

    private void inicializar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));

        panel.add(campo("Nombre del curso", txtNombre));
        panel.add(campoTexto("Descripción", txtDescripcion));
        panel.add(campo("Área académica", txtArea));
        panel.add(campo("Periodo escolar", txtPeriodo));

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        estilizar(btnGuardar, new Color(31, 78, 121));
        estilizar(btnCancelar, new Color(120, 130, 145));
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setBackground(Color.WHITE);
        acciones.add(btnCancelar);
        acciones.add(btnGuardar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(acciones);

        setContentPane(panel);
    }

    private JPanel campo(String etiqueta, JTextField campo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(ETIQUETA);
        lbl.setForeground(new Color(60, 70, 85));
        campo.setFont(CAMPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(6, 10, 6, 10)));

        p.add(lbl);
        p.add(Box.createVerticalStrut(4));
        p.add(campo);
        p.add(Box.createVerticalStrut(10));
        return p;
    }

    private JPanel campoTexto(String etiqueta, JTextArea area) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(ETIQUETA);
        lbl.setForeground(new Color(60, 70, 85));
        area.setFont(CAMPO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        p.add(lbl);
        p.add(Box.createVerticalStrut(4));
        p.add(new JScrollPane(area));
        p.add(Box.createVerticalStrut(10));
        return p;
    }

    private void estilizar(JButton b, Color color) {
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Tahoma", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
    }

    private void precargar(DatosSistema.Curso c) {
        txtNombre.setText(c.nombre);
        txtDescripcion.setText(c.descripcion);
        txtArea.setText(c.area);
        txtPeriodo.setText(c.periodo);
    }

    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String area = txtArea.getText().trim();
        String periodo = txtPeriodo.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || area.isEmpty() || periodo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (nombre.length() < 3) {
            JOptionPane.showMessageDialog(this, "El nombre debe tener al menos 3 caracteres.");
            return;
        }

        if (original == null) {
            DatosSistema.cursos.add(new DatosSistema.Curso(
                    nombre, descripcion, area, periodo,
                    DatosSistema.usuarioActual.correo));
        } else {
            original.nombre = nombre;
            original.descripcion = descripcion;
            original.area = area;
            original.periodo = periodo;
        }
        exitoso = true;
        dispose();
    }

    /** Indica si el usuario guardó los cambios. */
    public boolean isExitoso() { return exitoso; }
}
