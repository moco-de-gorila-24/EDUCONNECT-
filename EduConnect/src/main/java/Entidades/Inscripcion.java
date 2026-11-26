/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.Date;

/**
 *
 * @author Jorge
 */
public class Inscripcion {
    private final String id;
    private final String estudianteId;
    private final String cursoId;
    private final Date fechaInscripcion;
    private final boolean activo;

    public Inscripcion(String id, String estudianteId, String cursoId, Date fechaInscripcion, boolean activo) {
        this.id = id;
        this.estudianteId = estudianteId;
        this.cursoId = cursoId;
        this.fechaInscripcion = fechaInscripcion;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public String getCursoId() {
        return cursoId;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean isActivo() {
        return activo;
    }
}