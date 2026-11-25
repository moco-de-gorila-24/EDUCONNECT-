/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Jorge
 */
public class MongoDBConexion {
    private static final String URI = "mongodb://localhost:27017";
    private static final String NOMBRE_BD = "educonnect";
    private static MongoClient cliente;

    /**
     * Obtiene la conexión con MongoDB.
     * @return Cliente de MongoDB.
     */
    public static MongoClient obtenerCliente() {
        if (cliente == null) {
            cliente = MongoClients.create(URI);
        }

        return cliente;
    }

    /**
     * Obtiene la base de datos EduConnect.
     * @return Base de datos.
     */
    public static MongoDatabase obtenerBaseDatos() {
        return obtenerCliente().getDatabase(NOMBRE_BD);
    }

    /**
     * Cierra la conexión con MongoDB.
     * @return true si se cerró la conexión.
     */
    public static boolean cerrarConexion() {
        if (cliente != null) {
            cliente.close();
            cliente = null;

            return true;
        }

        return false;
    }
}
