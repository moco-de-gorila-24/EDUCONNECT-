/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Curso;
import Interfaces.ICursoDAO;
import Mappers.CursoMapper;
import Persistencia.MongoDBConexion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.bson.Document;

/**
 *
 * @author Jorge
 */
public class CursoDAO implements ICursoDAO {
    private final MongoCollection<Document> coleccion;

    public CursoDAO() {
        coleccion = MongoDBConexion.obtenerBaseDatos().getCollection("cursos");
    }

    @Override
    public boolean registrar(Curso curso) {
        Document documento = CursoMapper.toDocument(curso);
        documento.append("fechaCreacion", new Date());
        coleccion.insertOne(documento);

        return true;
    }

    @Override
    public Optional<Curso> buscarPorId(String id) {
        Document documento = coleccion.find(Filters.and(Filters.eq("_id", id), Filters.eq("activo", true))).first();

        if (documento == null) {
            return Optional.empty();
        }

        return Optional.of(CursoMapper.toEntity(documento));
    }

    @Override
    public List<Curso> consultarPorProfesor(String profesorId) {
        List<Curso> cursos = new ArrayList<>();
        List<Document> documentos = coleccion.find(Filters.and(Filters.eq("profesorId", profesorId), Filters.eq("activo", true))).into(new ArrayList<>());

        for (Document documento : documentos) {
            cursos.add(CursoMapper.toEntity(documento));
        }

        return cursos;
    }

    @Override
    public List<Curso> consultarTodos() {
        List<Curso> cursos = new ArrayList<>();
        List<Document> documentos = coleccion.find(Filters.eq("activo", true)).into(new ArrayList<>());

        for (Document documento : documentos) {
            cursos.add(CursoMapper.toEntity(documento));
        }

        return cursos;
    }

    @Override
    public boolean actualizar(Curso curso) {
        return coleccion.updateOne(Filters.eq("_id", curso.getId()), Updates.combine(Updates.set("nombre", curso.getNombre()), Updates.set("descripcion", curso.getDescripcion()), Updates.set("areaAcademica", curso.getAreaAcademica()), Updates.set("periodoEscolar", curso.getPeriodoEscolar()), Updates.set("profesorId", curso.getProfesorId()), Updates.set("activo", curso.isActivo()))).getMatchedCount() > 0;
    }

    @Override
    public boolean eliminar(String id) {
        return coleccion.updateOne(Filters.eq("_id", id), Updates.set("activo", false)).getMatchedCount() > 0;
    }
}
