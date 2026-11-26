/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Entidades.Actividad;
import org.bson.Document;

/**
 *
 * @author Jorge
 */
public class ActividadMapper {
    public static Document toDocument(Actividad actividad) {
        return new Document("_id", actividad.getId()).append("cursoId", actividad.getIdCurso()).append("titulo", actividad.getTitulo()).append("descripcion", actividad.getDescripcion()).append("fechaPublicacion", actividad.getFechaPublicacion()).append("fechaLimite", actividad.getFechaLimite()).append("puntuacionMaxima", actividad.getPuntuacionMaxima()).append("activo", actividad.isActivo());
    }

    public static Actividad toEntity(Document documento) {
        return new Actividad(documento.getString("_id"), documento.getString("cursoId"), documento.getString("titulo"), documento.getString("descripcion"), documento.getString("fechaPublicacion"), documento.getString("fechaLimite"), documento.getDouble("puntuacionMaxima"), documento.getBoolean("activo", true));
    }
}
