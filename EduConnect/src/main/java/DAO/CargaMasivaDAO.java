/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Persistencia.MongoDBConexion;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.bson.Document;

/**
 *
 * @author Jorge
 */
public class CargaMasivaDAO {
    private final MongoCollection<Document> cursos;

    public CargaMasivaDAO() {
        cursos = MongoDBConexion.obtenerBaseDatos().getCollection("cursos");
    }

    /**
     * Inserta cursos de prueba para un profesor.
     * @param profesorId Identificador del profesor.
     * @return Cantidad de cursos insertados.
     */
    public int insertarCursosPrueba(String profesorId) {
        List<Document> documentos = new ArrayList<>();
        documentos.add(crearCurso("Programación Java", "Introducción a Java y programación orientada a objetos", "Tecnologías", "2026-2", profesorId));
        documentos.add(crearCurso("Bases de datos", "Diseño y administración de bases de datos", "Tecnologías", "2026-2", profesorId));
        documentos.add(crearCurso("Métodos numéricos", "Resolución de problemas mediante métodos numéricos", "Matemáticas", "2026-2", profesorId));
        cursos.insertMany(documentos);

        return documentos.size();
    }

    private Document crearCurso(String nombre, String descripcion, String area, String periodo, String profesorId) {
        return new Document("_id", UUID.randomUUID().toString()).append("nombre", nombre).append("descripcion", descripcion).append("areaAcademica", area).append("periodoEscolar", periodo).append("profesorId", profesorId).append("activo", true).append("fechaCreacion", new Date());
    }
}
