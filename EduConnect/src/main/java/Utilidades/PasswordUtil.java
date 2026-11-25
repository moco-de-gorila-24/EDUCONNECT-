/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Jorge
 */
public class PasswordUtil {

    /**
     * Genera un hash BCrypt de una contraseña.
     * @param contrasena Contraseña original.
     * @return Hash de la contraseña.
     */
    public static String hash(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt(12));
    }

    /**
     * Verifica una contraseña contra su hash.
     * @param contrasena Contraseña ingresada.
     * @param hash Hash almacenado.
     * @return true si coincide.
     */
    public static boolean verificar(String contrasena, String hash) {
        if (contrasena == null || hash == null) {
            return false;
        }

        return BCrypt.checkpw(contrasena, hash);
    }
}
