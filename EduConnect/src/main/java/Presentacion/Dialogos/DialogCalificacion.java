/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Dialogos;

import Presentacion.DatosSistema;
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
 * Diálogo para que el profesor califique una entrega.
 *
 * @author Jorge
 */
public class DialogCalificacion extends JDialog {

    private static final Font ETIQUETA = new Font("Tahoma", Font.BOLD, 12);
    private static final Font CAMPO = new Font("Tahoma", Font.PLAIN, 13);

    private final DatosSistema.Entrega entrega;
    private final DatosSistema.Actividad actividad;
    private final JTextField txtCalificacion = new JTextField();
    private final JTextArea txtObservaciones = new JTextArea(4, 20);
    private boolean exitoso = false;

    public DialogCalificacion(Dialog propietario, DatosSistema.Entrega entrega,
                              DatosSistema.Actividad actividad) {
        super(propietario, true);
        this.entrega = entrega;
        this.actividad = actividad;
        setTitle("Calificar entrega");
        setSize(500, 500);
        setLocationRelativeTo(propietario);
        setResizable(false);
        inicializar();
        precargar();
    }

    private void inicializar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));

        panel.add(info("Actividad", actividad.titulo));
        panel.add(info("Estudiante", nombreEstudiante()));
        panel.add(info("Tipo de entrega", entrega.tipo));
        panel.add(info("Contenido", entrega.contenido));
        panel.add(info("Fecha y hora de entrega", entrega.fechaHoraEntrega));
        panel.add(info("Puntaje máximo", String.valueOf(actividad.puntuacionMaxima)));
        panel.add(Box.createVerticalStrut(10));

        JLabel lblCal = new JLabel("Calificación");
        lblCal.setFont(ETIQUETA);
        lblCal.setForeground(new Color(60, 70, 85));
        lblCal.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCalificacion.setFont(CAMPO);
        txtCalificacion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtCalificacion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(6, 10, 6, 10)));

        JLabel lblObs = new JLabel("Observaciones");
        lblObs.setFont(ETIQUETA);
        lblObs.setForeground(new Color(60, 70, 85));
        lblObs.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtObservaciones.setFont(CAMPO);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        panel.add(lblCal);
        panel.add(Box.createVerticalStrut(4));
        panel.add(txtCalificacion);
        panel.add(Box.createVerticalStrut(12));
        panel.add(lblObs);
        panel.add(Box.createVerticalStrut(4));
        panel.add(new JScrollPane(txtObservaciones));
        panel.add(Box.createVerticalStrut(15));

        JButton btnGuardar = new JButton("Guardar calificación");
        JButton btnCancelar = new JButton("Cancelar");
        estilizar(btnGuardar, new Color(31, 78, 121));
        estilizar(btnCancelar, new Color(120, 130, 145));
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setBackground(Color.WHITE);
        acciones.add(btnCancelar);
        acciones.add(btnGuardar);
        panel.add(acciones);

        setContentPane(new JScrollPane(panel));
    }

    private JLabel info(String etiqueta, String valor) {
        JLabel lbl = new JLabel(etiqueta + ": " + valor);
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbl.setForeground(new Color(60, 70, 85));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(2, 0, 2, 0));
        return lbl;
    }

    private String nombreEstudiante() {
        DatosSistema.Usuario u = DatosSistema.buscarUsuarioPorCorreo(entrega.correoEstudiante);
        return u != null ? u.nombre : entrega.correoEstudiante;
    }

    private void estilizar(JButton b, Color color) {
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Tahoma", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
    }

    private void precargar() {
        DatosSistema.Calificacion existente = DatosSistema.calificacionDeEntrega(entrega.id);
        if (existente != null) {
            txtCalificacion.setText(String.valueOf(existente.calificacion));
            txtObservaciones.setText(existente.observaciones);
        }
    }

    private void guardar() {
        String texto = txtCalificacion.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa una calificación.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double valor;
        try {
            valor = Double.parseDouble(texto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La calificación debe ser numérica.");
            return;
        }
        if (valor < 0 || valor > actividad.puntuacionMaxima) {
            JOptionPane.showMessageDialog(this,
                    "La calificación debe estar entre 0 y " + actividad.puntuacionMaxima + ".");
            return;
        }
        String obs = txtObservaciones.getText().trim();

        DatosSistema.Calificacion existente = DatosSistema.calificacionDeEntrega(entrega.id);
        if (existente == null) {
            DatosSistema.calificaciones.add(new DatosSistema.Calificacion(
                    entrega.id, entrega.idActividad, entrega.correoEstudiante,
                    valor, obs));
        } else {
            existente.calificacion = valor;
            existente.observaciones = obs;
        }
        exitoso = true;
        dispose();
    }

    public boolean isExitoso() { return exitoso; }
}
