/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.Dialogos;

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import Presentacion.DatosSistema;

/**
 * Diálogo para que el estudiante realice la entrega de una actividad
 * mediante un archivo o una URL.
 *
 * @author Jorge
 */
public class DialogEntrega extends JDialog {

    private static final Font ETIQUETA = new Font("Tahoma", Font.BOLD, 12);
    private static final Font CAMPO = new Font("Tahoma", Font.PLAIN, 13);

    private final DatosSistema.Actividad actividad;
    private final JRadioButton rbArchivo = new JRadioButton("Archivo");
    private final JRadioButton rbUrl = new JRadioButton("URL");
    private final JTextField txtContenido = new JTextField();
    private final JButton btnExaminar = new JButton("Examinar...");
    private boolean exitoso = false;

    public DialogEntrega(Dialog propietario, DatosSistema.Actividad actividad) {
        super(propietario, true);
        this.actividad = actividad;
        setTitle("Entregar actividad");
        setSize(500, 420);
        setLocationRelativeTo(propietario);
        setResizable(false);
        inicializar();
    }

    private void inicializar() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel("Actividad");
        lblTitulo.setFont(new Font("Verdana", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(31, 78, 121));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblNombre = new JLabel(actividad.titulo);
        lblNombre.setFont(ETIQUETA);
        lblNombre.setForeground(new Color(35, 45, 60));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDetalle = new JLabel("Fecha límite: " + actividad.fechaLimite
                + "    |    Puntaje máximo: " + actividad.puntuacionMaxima);
        lblDetalle.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDetalle.setForeground(Color.GRAY);
        lblDetalle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblNombre);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblDetalle);
        panel.add(Box.createVerticalStrut(20));

        JLabel lblTipo = new JLabel("Tipo de entrega");
        lblTipo.setFont(ETIQUETA);
        lblTipo.setForeground(new Color(60, 70, 85));
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbArchivo);
        grupo.add(rbUrl);
        rbArchivo.setSelected(true);
        rbArchivo.setBackground(Color.WHITE);
        rbUrl.setBackground(Color.WHITE);
        rbArchivo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rbUrl.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JPanel tipos = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        tipos.setBackground(Color.WHITE);
        tipos.setAlignmentX(Component.LEFT_ALIGNMENT);
        tipos.add(rbArchivo);
        tipos.add(rbUrl);

        panel.add(lblTipo);
        panel.add(Box.createVerticalStrut(4));
        panel.add(tipos);
        panel.add(Box.createVerticalStrut(12));

        JLabel lblContenido = new JLabel("Archivo o URL");
        lblContenido.setFont(ETIQUETA);
        lblContenido.setForeground(new Color(60, 70, 85));
        lblContenido.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtContenido.setFont(CAMPO);
        txtContenido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtContenido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(6, 10, 6, 10)));

        btnExaminar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnExaminar.addActionListener(e -> elegirArchivo());

        JPanel filaContenido = new JPanel(new BorderLayout(8, 0));
        filaContenido.setBackground(Color.WHITE);
        filaContenido.setAlignmentX(Component.LEFT_ALIGNMENT);
        filaContenido.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        filaContenido.add(txtContenido, BorderLayout.CENTER);
        filaContenido.add(btnExaminar, BorderLayout.EAST);

        panel.add(lblContenido);
        panel.add(Box.createVerticalStrut(4));
        panel.add(filaContenido);
        panel.add(Box.createVerticalStrut(20));

        JButton btnEnviar = new JButton("Enviar entrega");
        JButton btnCancelar = new JButton("Cancelar");
        estilizar(btnEnviar, new Color(31, 78, 121));
        estilizar(btnCancelar, new Color(120, 130, 145));
        btnEnviar.addActionListener(e -> enviar());
        btnCancelar.addActionListener(e -> dispose());

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setBackground(Color.WHITE);
        acciones.add(btnCancelar);
        acciones.add(btnEnviar);
        panel.add(acciones);

        setContentPane(panel);
    }

    private void estilizar(JButton b, Color color) {
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Tahoma", Font.BOLD, 12));
        b.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
    }

    private void elegirArchivo() {
        JFileChooser chooser = new JFileChooser();
        int opcion = chooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            txtContenido.setText(chooser.getSelectedFile().getAbsolutePath());
            rbArchivo.setSelected(true);
        }
    }

    private void enviar() {
        String contenido = txtContenido.getText().trim();
        if (contenido.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Indica un archivo o una URL para entregar.",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String tipo = rbArchivo.isSelected() ? "Archivo" : "URL";
        if (tipo.equals("URL") && !contenido.matches("^https?://.+")) {
            JOptionPane.showMessageDialog(this,
                    "La URL debe iniciar con http:// o https://");
            return;
        }
        String correo = DatosSistema.usuarioActual.correo;
        if (DatosSistema.entregaDeEstudiante(actividad.id, correo) != null) {
            JOptionPane.showMessageDialog(this,
                    "Ya realizaste una entrega para esta actividad.",
                    "Entrega duplicada", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DatosSistema.entregas.add(new DatosSistema.Entrega(
                actividad.id, correo, tipo, contenido, DatosSistema.ahora()));
        exitoso = true;
        dispose();
    }

    public boolean isExitoso() { return exitoso; }
}
