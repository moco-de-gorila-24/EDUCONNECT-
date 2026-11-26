/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Inscripcion;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jorge
 */
public interface IInscripcionDAO {
    boolean inscribir(String estudianteId, String cursoId);
    boolean estaInscrito(String estudianteId, String cursoId);
    List<Inscripcion> consultarPorEstudiante(String estudianteId);
    List<Inscripcion> consultarPorCurso(String cursoId);
    Optional<Inscripcion> buscarPorId(String id);
    boolean cancelarInscripcion(String estudianteId, String cursoId);
}
