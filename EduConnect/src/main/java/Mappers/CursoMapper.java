/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Entidades.Curso;
import org.bson.Document;

/**
 *
 * @author Jorge
 */
public class CursoMapper {
    public static Document toDocument(Curso curso) {
        return new Document("_id", curso.getId()).append("nombre", curso.getNombre()).append("descripcion", curso.getDescripcion()).append("areaAcademica", curso.getAreaAcademica()).append("periodoEscolar", curso.getPeriodoEscolar()).append("profesorId", curso.getProfesorId()).append("activo", curso.isActivo());
    }

    public static Curso toEntity(Document documento) {
        return new Curso(documento.getString("_id"), documento.getString("nombre"), documento.getString("descripcion"), documento.getString("areaAcademica"), documento.getString("periodoEscolar"), documento.getString("profesorId"), documento.getBoolean("activo", true));
    }
}