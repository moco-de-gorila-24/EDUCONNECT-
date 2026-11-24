/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Dialogos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import Presentacion.DatosSistema;

/**
 * Diálogo para crear o editar una actividad académica.
 *
 * @author Jorge
 */
public class DialogActividad extends JDialog {

    private static final Font ETIQUETA = new Font("Tahoma", Font.BOLD, 12);
    private static final Font CAMPO = new Font("Tahoma", Font.PLAIN, 13);

    private final JComboBox<String> cmbCurso = new JComboBox<>();
    private final JTextField txtTitulo = new JTextField();
    private final JTextArea txtDescripcion = new JTextArea(3, 20);
    private final JTextField txtFechaPub = new JTextField();
    private final JTextField txtFechaLim = new JTextField();
    private final JTextField txtPuntuacion = new JTextField();

    private final DatosSistema.Actividad original;
    private final List<DatosSistema.Curso> cursos;
    private boolean exitoso = false;

    public DialogActividad(Dialog propietario) {
        this(propietario, null);
    }

    public DialogActividad(Dialog propietario, DatosSistema.Actividad actividad) {
        super(propietario, true);
        this.original = actividad;
        this.cursos = DatosSistema.cursosDelProfesor(DatosSistema.usuarioActual.correo);
        setTitle(actividad == null ? "Nueva actividad" : "Editar actividad");
        setSize(500, 620);
        setLocationRelativeTo(propietario);
        setResizable(false);
        inicializar();
        if (actividad != null) precargar(actividad);
    }

    private void inicializar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));

        for (DatosSistema.Curso c : cursos) {
            cmbCurso.addItem(c.nombre + " | " + c.periodo);
        }
        cmbCurso.setFont(CAMPO);

        panel.add(campoComponente("Curso", cmbCurso));
        panel.add(campo("Título", txtTitulo));
        panel.add(campoTexto("Descripción", txtDescripcion));
        panel.add(campo("Fecha de publicación (dd/MM/yyyy)", txtFechaPub));
        panel.add(campo("Fecha límite (dd/MM/yyyy)", txtFechaLim));
        panel.add(campo("Puntuación máxima", txtPuntuacion));

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

        setContentPane(new JScrollPane(panel));
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

    private JPanel campoComponente(String etiqueta, javax.swing.JComponent campo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(ETIQUETA);
        lbl.setForeground(new Color(60, 70, 85));

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

    private void precargar(DatosSistema.Actividad a) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).id.equals(a.idCurso)) {
                cmbCurso.setSelectedIndex(i);
                break;
            }
        }
        txtTitulo.setText(a.titulo);
        txtDescripcion.setText(a.descripcion);
        txtFechaPub.setText(a.fechaPublicacion);
        txtFechaLim.setText(a.fechaLimite);
        txtPuntuacion.setText(String.valueOf(a.puntuacionMaxima));
    }

    private void guardar() {
        if (cursos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Primero debes crear un curso.", "Sin cursos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String titulo = txtTitulo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String fechaPub = txtFechaPub.getText().trim();
        String fechaLim = txtFechaLim.getText().trim();
        String puntuacion = txtPuntuacion.getText().trim();

        if (titulo.isEmpty() || descripcion.isEmpty() || fechaPub.isEmpty()
                || fechaLim.isEmpty() || puntuacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarFecha(fechaPub) || !validarFecha(fechaLim)) {
            JOptionPane.showMessageDialog(this,
                    "Las fechas deben ser válidas (dd/MM/yyyy).");
            return;
        }
        double max;
        try {
            max = Double.parseDouble(puntuacion);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La puntuación máxima debe ser numérica.");
            return;
        }
        if (max <= 0 || max > 100) {
            JOptionPane.showMessageDialog(this,
                    "La puntuación máxima debe estar entre 1 y 100.");
            return;
        }

        String idCurso = cursos.get(cmbCurso.getSelectedIndex()).id;

        if (original == null) {
            DatosSistema.actividades.add(new DatosSistema.Actividad(
                    idCurso, titulo, descripcion, fechaPub, fechaLim, max));
        } else {
            original.idCurso = idCurso;
            original.titulo = titulo;
            original.descripcion = descripcion;
            original.fechaPublicacion = fechaPub;
            original.fechaLimite = fechaLim;
            original.puntuacionMaxima = max;
        }
        exitoso = true;
        dispose();
    }

    private boolean validarFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false);
        try {
            Date d = formato.parse(fecha);
            return formato.format(d).equals(fecha);
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean isExitoso() { return exitoso; }
}
