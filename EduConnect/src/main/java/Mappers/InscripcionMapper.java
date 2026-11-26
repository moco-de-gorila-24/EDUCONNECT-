/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Entidades.Inscripcion;
import org.bson.Document;

/**
 *
 * @author Jorge
 */
public class InscripcionMapper {
    public static Document toDocument(Inscripcion inscripcion) {
        return new Document("_id", inscripcion.getId()).append("estudianteId", inscripcion.getEstudianteId()).append("cursoId", inscripcion.getCursoId()).append("fechaInscripcion", inscripcion.getFechaInscripcion()).append("activo", inscripcion.isActivo());
    }

    public static Inscripcion toEntity(Document documento) {
        return new Inscripcion(documento.getString("_id"), documento.getString("estudianteId"), documento.getString("cursoId"), documento.getDate("fechaInscripcion"), documento.getBoolean("activo", true));
    }
}
