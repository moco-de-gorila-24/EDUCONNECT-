/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDate;

/**
 *
 * @author Jorge
 */
public class Usuario {
    private final String id;
    private final String nombre;
    private final String correo;
    private final String telefono;
    private final String passwordHash;
    private final LocalDate fechaNacimiento;
    private final TipoUsuario tipoUsuario;
    private final boolean activo;

    public Usuario(String id, String nombre, String correo, String telefono, String passwordHash, LocalDate fechaNacimiento, TipoUsuario tipoUsuario, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.passwordHash = passwordHash;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public boolean isActivo() {
        return activo;
    }
}
