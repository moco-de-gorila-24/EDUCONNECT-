/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Actividad;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Jorge
 */
public interface IActividadDAO {
    boolean registrar(Actividad actividad);
    Optional<Actividad> buscarPorId(String id);
    List<Actividad> consultarPorCurso(String idCurso);
    List<Actividad> consultarTodas();
    boolean actualizar(Actividad actividad);
    boolean eliminar(String id);
}
