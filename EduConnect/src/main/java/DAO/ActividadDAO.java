/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Actividad;
import Interfaces.IActividadDAO;
import Mappers.ActividadMapper;
import Persistencia.MongoDBConexion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.Document;

/**
 *
 * @author Jorge
 */
public class ActividadDAO implements IActividadDAO {
    private final MongoCollection<Document> coleccion;

    public ActividadDAO() {
        coleccion = MongoDBConexion.obtenerBaseDatos().getCollection("actividades");
    }

    @Override
    public boolean registrar(Actividad actividad) {
        Document documento = ActividadMapper.toDocument(actividad);
        coleccion.insertOne(documento);
        return true;
    }

    @Override
    public Optional<Actividad> buscarPorId(String id) {
        Document documento = coleccion.find(Filters.and(Filters.eq("_id", id), Filters.eq("activo", true))).first();

        if (documento == null) {
            return Optional.empty();
        }

        return Optional.of(ActividadMapper.toEntity(documento));
    }

    @Override
    public List<Actividad> consultarPorCurso(String idCurso) {
        List<Actividad> actividades = new ArrayList<>();
        List<Document> documentos = coleccion.find(Filters.and(Filters.eq("cursoId", idCurso), Filters.eq("activo", true))).into(new ArrayList<>());

        for (Document documento : documentos) {
            actividades.add(ActividadMapper.toEntity(documento));
        }

        return actividades;
    }

    @Override
    public List<Actividad> consultarTodas() {
        List<Actividad> actividades = new ArrayList<>();
        List<Document> documentos = coleccion.find(Filters.eq("activo", true)).into(new ArrayList<>());

        for (Document documento : documentos) {
            actividades.add(ActividadMapper.toEntity(documento));
        }

        return actividades;
    }

    @Override
    public boolean actualizar(Actividad actividad) {
        return coleccion.updateOne(Filters.eq("_id", actividad.getId()), Updates.combine(Updates.set("cursoId", actividad.getIdCurso()), Updates.set("titulo", actividad.getTitulo()), Updates.set("descripcion", actividad.getDescripcion()), Updates.set("fechaPublicacion", actividad.getFechaPublicacion()), Updates.set("fechaLimite", actividad.getFechaLimite()), Updates.set("puntuacionMaxima", actividad.getPuntuacionMaxima()), Updates.set("activo", actividad.isActivo()))).getMatchedCount() > 0;
    }

    @Override
    public boolean eliminar(String id) {
        return coleccion.updateOne(Filters.eq("_id", id), Updates.set("activo", false)).getMatchedCount() > 0;
    }
}
