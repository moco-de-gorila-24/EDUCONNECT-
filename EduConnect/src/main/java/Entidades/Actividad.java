/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Jorge
 */
public class Actividad {
    private String id;
    private String idCurso;
    private String titulo;
    private String descripcion;
    private String fechaPublicacion;
    private String fechaLimite;
    private double puntuacionMaxima;
    private boolean activo;

    public Actividad(String id, String idCurso, String titulo, String descripcion, String fechaPublicacion, String fechaLimite, double puntuacionMaxima, boolean activo) {
        this.id = id;
        this.idCurso = idCurso;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaLimite = fechaLimite;
        this.puntuacionMaxima = puntuacionMaxima;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public String getIdCurso() {
        return idCurso;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public double getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setIdCurso(String idCurso) {
        this.idCurso = idCurso;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public void setPuntuacionMaxima(double puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
