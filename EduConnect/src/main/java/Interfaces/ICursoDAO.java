/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Curso;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jorge
 */
public interface ICursoDAO {
    boolean registrar(Curso curso);
    Optional<Curso> buscarPorId(String id);
    List<Curso> consultarPorProfesor(String profesorId);
    List<Curso> consultarTodos();
    boolean actualizar(Curso curso);
    boolean eliminar(String id);
}
