/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Entidades.Usuario;
import Excepciones.DatosDuplicadosException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ACER
 */
public interface IUsuarioDAO {
    boolean registrar(Usuario usuario) throws DatosDuplicadosException;
    Optional<Usuario> buscarPorId(String id);
    Optional<Usuario> buscarPorCorreo(String correo);
    List<Usuario> consultarTodos();
    boolean actualizar(Usuario usuario) throws DatosDuplicadosException;
    boolean eliminar(String id);
}
