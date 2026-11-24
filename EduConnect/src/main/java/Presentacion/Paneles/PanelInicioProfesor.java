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
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Panel de inicio para el rol Profesor.
 *
 * @author Jorge
 */
public class PanelInicioProfesor extends JPanel implements IRefrescable {

    private static final Font TITULO = new Font("Verdana", Font.BOLD, 22);
    private static final Font SUBTITULO = new Font("Tahoma", Font.BOLD, 14);
    private static final Font TEXTO = new Font("Tahoma", Font.PLAIN, 13);
    private static final Color AZUL = new Color(31, 78, 121);
    private static final Color FONDO = new Color(245, 247, 250);

    public PanelInicioProfesor() {
        setLayout(new BorderLayout());
        setBackground(FONDO);
        setBorder(new EmptyBorder(35, 35, 35, 35));
        refrescar();
    }

    @Override
    public boolean refrescar() {
        removeAll();
        construir();
        revalidate();
        repaint();
        return true;
    }

    private void construir() {
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(FONDO);

        String nombre = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.nombre : "profesor";
        String correo = DatosSistema.usuarioActual != null ? DatosSistema.usuarioActual.correo : "";

        JLabel lblBienvenida = new JLabel("Bienvenido, " + nombre);
        lblBienvenida.setFont(TITULO);
        lblBienvenida.setForeground(new Color(35, 45, 60));
        lblBienvenida.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDesc = new JLabel("Administra tus cursos, registra actividades y califica entregas.");
        lblDesc.setFont(TEXTO);
        lblDesc.setForeground(Color.GRAY);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenido.add(lblBienvenida);
        contenido.add(Box.createVerticalStrut(8));
        contenido.add(lblDesc);
        contenido.add(Box.createVerticalStrut(25));

        int numCursos = DatosSistema.cursosDelProfesor(correo).size();
        int numActividades = 0;
        int numEntregas = 0;
        for (DatosSistema.Curso c : DatosSistema.cursosDelProfesor(correo)) {
            for (DatosSistema.Actividad a : DatosSistema.actividadesDelCurso(c.id)) {
                numActividades++;
                numEntregas += DatosSistema.entregasDeActividad(a.id).size();
            }
        }

        contenido.add(tarjeta("Cursos creados", String.valueOf(numCursos),
                "Gestiona tus cursos desde la sección Gestionar cursos."));
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(tarjeta("Actividades registradas", String.valueOf(numActividades),
                "Administra actividades desde la sección Gestionar actividades."));
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(tarjeta("Entregas recibidas", String.valueOf(numEntregas),
                "Revísalas y califícalas en Revisar entregas."));

        add(contenido, BorderLayout.NORTH);
    }

    private JPanel tarjeta(String titulo, String valor, String descripcion) {
        JPanel p = new JPanel(new BorderLayout(15, 5));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 230, 235)),
                new EmptyBorder(18, 20, 18, 20)));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Verdana", Font.BOLD, 26));
        lblValor.setForeground(AZUL);
        lblValor.setPreferredSize(new Dimension(70, 50));
        lblValor.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(SUBTITULO);
        lblTitulo.setForeground(AZUL);

        JLabel lblDesc = new JLabel(descripcion);
        lblDesc.setFont(TEXTO);
        lblDesc.setForeground(new Color(100, 110, 125));

        textos.add(lblTitulo);
        textos.add(Box.createVerticalStrut(3));
        textos.add(lblDesc);

        p.add(lblValor, BorderLayout.WEST);
        p.add(textos, BorderLayout.CENTER);
        return p;
    }
}
