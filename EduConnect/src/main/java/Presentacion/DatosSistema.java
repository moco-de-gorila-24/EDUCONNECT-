/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repositorio en memoria para la capa de presentación.
 * Se sustituirá por los DAO cuando se integre MongoDB.
 *
 * @author Jorge
 */
public class DatosSistema {

    public static final List<Usuario> usuarios = new ArrayList<>();
    public static final List<Curso> cursos = new ArrayList<>();
    public static final List<Actividad> actividades = new ArrayList<>();
    public static final List<Entrega> entregas = new ArrayList<>();
    public static final List<Calificacion> calificaciones = new ArrayList<>();
    public static final List<String> inscripciones = new ArrayList<>();

    public static Usuario usuarioActual;

    /** Usuario registrado en la plataforma. */
    public static class Usuario {
        public String nombre, correo, telefono, contrasenaHash, fechaNacimiento, tipoUsuario;

        public Usuario(String nombre, String correo, String telefono,
                       String contrasenaHash, String fechaNacimiento, String tipoUsuario) {
            this.nombre = nombre;
            this.correo = correo;
            this.telefono = telefono;
            this.contrasenaHash = contrasenaHash;
            this.fechaNacimiento = fechaNacimiento;
            this.tipoUsuario = tipoUsuario;
        }
    }

    /** Curso creado por un profesor. */
    public static class Curso {
        public String id, nombre, descripcion, area, periodo, correoProfesor;

        public Curso(String nombre, String descripcion, String area, String periodo, String correoProfesor) {
            this.id = UUID.randomUUID().toString();
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.area = area;
            this.periodo = periodo;
            this.correoProfesor = correoProfesor;
        }
    }

    /** Actividad académica de un curso. */
    public static class Actividad {
        public String id, idCurso, titulo, descripcion, fechaPublicacion, fechaLimite;
        public double puntuacionMaxima;

        public Actividad(String idCurso, String titulo, String descripcion,
                         String fechaPublicacion, String fechaLimite, double puntuacionMaxima) {
            this.id = UUID.randomUUID().toString();
            this.idCurso = idCurso;
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.fechaPublicacion = fechaPublicacion;
            this.fechaLimite = fechaLimite;
            this.puntuacionMaxima = puntuacionMaxima;
        }
    }

    /** Entrega de una actividad por parte de un estudiante. */
    public static class Entrega {
        public String id, idActividad, correoEstudiante, tipo, contenido, fechaHoraEntrega;

        public Entrega(String idActividad, String correoEstudiante, String tipo,
                       String contenido, String fechaHoraEntrega) {
            this.id = UUID.randomUUID().toString();
            this.idActividad = idActividad;
            this.correoEstudiante = correoEstudiante;
            this.tipo = tipo;
            this.contenido = contenido;
            this.fechaHoraEntrega = fechaHoraEntrega;
        }
    }

    /** Calificación emitida por el profesor sobre una entrega. */
    public static class Calificacion {
        public String id, idEntrega, idActividad, correoEstudiante, observaciones;
        public double calificacion;

        public Calificacion(String idEntrega, String idActividad, String correoEstudiante,
                            double calificacion, String observaciones) {
            this.id = UUID.randomUUID().toString();
            this.idEntrega = idEntrega;
            this.idActividad = idActividad;
            this.correoEstudiante = correoEstudiante;
            this.calificacion = calificacion;
            this.observaciones = observaciones;
        }
    }

    /** Aplica SHA-256 a una cadena (uso temporal mientras se integra la persistencia). */
    public static String hashear(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return texto;
        }
    }

    public static Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario u : usuarios) if (u.correo.equalsIgnoreCase(correo)) return u;
        return null;
    }

    public static Curso buscarCursoPorId(String id) {
        for (Curso c : cursos) if (c.id.equals(id)) return c;
        return null;
    }

    public static Actividad buscarActividadPorId(String id) {
        for (Actividad a : actividades) if (a.id.equals(id)) return a;
        return null;
    }

    public static boolean estaInscrito(String correo, String idCurso) {
        return inscripciones.contains(correo + "|" + idCurso);
    }

    public static int contarInscritos(String idCurso) {
        int n = 0;
        for (String s : inscripciones) if (s.endsWith("|" + idCurso)) n++;
        return n;
    }

    public static List<Curso> cursosDelProfesor(String correo) {
        List<Curso> r = new ArrayList<>();
        for (Curso c : cursos) if (c.correoProfesor.equalsIgnoreCase(correo)) r.add(c);
        return r;
    }

    public static List<Curso> cursosInscritos(String correo) {
        List<Curso> r = new ArrayList<>();
        for (String s : inscripciones) {
            if (s.startsWith(correo + "|")) {
                Curso c = buscarCursoPorId(s.substring(correo.length() + 1));
                if (c != null) r.add(c);
            }
        }
        return r;
    }

    public static List<Actividad> actividadesDelCurso(String idCurso) {
        List<Actividad> r = new ArrayList<>();
        for (Actividad a : actividades) if (a.idCurso.equals(idCurso)) r.add(a);
        return r;
    }

    public static List<Entrega> entregasDeActividad(String idActividad) {
        List<Entrega> r = new ArrayList<>();
        for (Entrega e : entregas) if (e.idActividad.equals(idActividad)) r.add(e);
        return r;
    }

    public static Entrega entregaDeEstudiante(String idActividad, String correo) {
        for (Entrega e : entregas)
            if (e.idActividad.equals(idActividad) && e.correoEstudiante.equalsIgnoreCase(correo)) return e;
        return null;
    }

    public static Calificacion calificacionDeEntrega(String idEntrega) {
        for (Calificacion c : calificaciones) if (c.idEntrega.equals(idEntrega)) return c;
        return null;
    }

    public static String ahora() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
