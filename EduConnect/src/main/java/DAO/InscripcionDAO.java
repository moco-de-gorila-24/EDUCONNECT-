/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Inscripcion;
import Interfaces.IInscripcionDAO;
import Mappers.InscripcionMapper;
import Persistencia.MongoDBConexion;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.Document;

/**
 *
 * @author Jorge
 */
public class InscripcionDAO implements IInscripcionDAO {
    private final MongoCollection<Document> coleccion;
   
    public InscripcionDAO() {
        coleccion = MongoDBConexion.obtenerBaseDatos().getCollection("inscripciones");
    }

    @Override
    public boolean inscribir(String estudianteId, String cursoId) {
        if (estaInscrito(estudianteId, cursoId)) {
            return false;
        }

        Inscripcion inscripcion = new Inscripcion(UUID.randomUUID().toString(), estudianteId, cursoId, new Date(), true);
        Document documento = InscripcionMapper.toDocument(inscripcion);

        try {
            coleccion.insertOne(documento);
            return true;

        } catch (MongoWriteException e) {
            // el codigo 11000 significa indice duplicado
            if (e.getError().getCode() == 11000) {
                return false;
            }

            throw e;
        }
    }

    @Override
    public boolean estaInscrito(String estudianteId, String cursoId) {
        Document documento = coleccion.find(Filters.and(Filters.eq("estudianteId", estudianteId), Filters.eq("cursoId", cursoId), Filters.eq("activo", true))).first();
        return documento != null;
    }

    @Override
    public List<Inscripcion> consultarPorEstudiante(String estudianteId) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        List<Document> documentos = coleccion.find(Filters.and(Filters.eq("estudianteId", estudianteId), Filters.eq("activo", true))).into(new ArrayList<>());

        for (Document documento : documentos) {
            inscripciones.add(InscripcionMapper.toEntity(documento));
        }

        return inscripciones;
    }

    @Override
    public List<Inscripcion> consultarPorCurso(String cursoId) {
        List<Inscripcion> inscripciones = new ArrayList<>();
        List<Document> documentos = coleccion.find(Filters.and(Filters.eq("cursoId", cursoId), Filters.eq("activo", true))).into(new ArrayList<>());

        for (Document documento : documentos) {
            inscripciones.add(InscripcionMapper.toEntity(documento));
        }

        return inscripciones;
    }

    @Override
    public Optional<Inscripcion> buscarPorId(String id) {
        Document documento = coleccion.find(Filters.and(Filters.eq("_id", id), Filters.eq("activo", true))).first();

        if (documento == null) {
            return Optional.empty();
        }

        return Optional.of(InscripcionMapper.toEntity(documento));
    }

    @Override
    public boolean cancelarInscripcion(String estudianteId, String cursoId) {
        return coleccion.updateOne(Filters.and(Filters.eq("estudianteId", estudianteId), Filters.eq("cursoId", cursoId), Filters.eq("activo", true)), Updates.set("activo", false)).getMatchedCount() > 0;
    }
}
