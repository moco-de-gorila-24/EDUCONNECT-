/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

/**
 * Contrato para paneles que pueden refrescar su contenido
 * cuando vuelven a mostrarse.
 *
 * @author Jorge
 */
public interface IRefrescable {
    /**
     * Recarga la información del panel.
     *
     * @return true si el refresco se completó correctamente.
     */
    boolean refrescar();
}
