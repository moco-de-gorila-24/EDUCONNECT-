/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Jorge
 */
public class Curso {
    private final String id;
    private final String nombre;
    private final String descripcion;
    private final String areaAcademica;
    private final String periodoEscolar;
    private final String profesorId;
    private final boolean activo;

    public Curso(String id, String nombre, String descripcion, String areaAcademica, String periodoEscolar, String profesorId, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.areaAcademica = areaAcademica;
        this.periodoEscolar = periodoEscolar;
        this.profesorId = profesorId;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAreaAcademica() {
        return areaAcademica;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public String getProfesorId() {
        return profesorId;
    }

    public boolean isActivo() {
        return activo;
    }
}
