/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Interfaces.IUsuarioDAO;
import Entidades.Usuario;
import Excepciones.DatosDuplicadosException;
import Mappers.UsuarioMapper;
import Persistencia.MongoDBConexion;
import com.mongodb.MongoWriteException;
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
public class UsuarioDAO implements IUsuarioDAO {
    private final MongoCollection<Document> coleccion;

    public UsuarioDAO() {
        coleccion = MongoDBConexion.obtenerBaseDatos().getCollection("usuarios");
    }

    @Override
    public boolean registrar(Usuario usuario) throws DatosDuplicadosException {
        Document documento = UsuarioMapper.toDocument(usuario);
        documento.append("fechaRegistro", new java.util.Date());

        try {
            coleccion.insertOne(documento);
            return true;
        } catch (MongoWriteException e) {
            if (e.getError().getCode() == 11000) {
                throw new DatosDuplicadosException("Ya existe un usuario con ese correo.");
            }

            throw e;
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        Document documento = coleccion.find(Filters.and(Filters.eq("_id", id), Filters.eq("activo", true))).first();

        if (documento == null) {
            return Optional.empty();
        }

        return Optional.of(UsuarioMapper.toEntity(documento)
        );
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        String correoNormalizado = correo.trim().toLowerCase();
        Document documento = coleccion.find(Filters.and(Filters.eq("correo", correoNormalizado), Filters.eq("activo", true))).first();

        if (documento == null) {
            return Optional.empty();
        }

        return Optional.of(UsuarioMapper.toEntity(documento));
    }

    @Override
    public List<Usuario> consultarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        List<Document> documentos = coleccion.find(Filters.eq("activo", true)).into(new ArrayList<>());

        for (Document documento : documentos) {
            usuarios.add(UsuarioMapper.toEntity(documento));
        }

        return usuarios;
    }

    @Override
    public boolean actualizar(Usuario usuario) throws DatosDuplicadosException {
        Document documento = UsuarioMapper.toDocument(usuario);
        documento.remove("_id");

        try {
            return coleccion.updateOne(Filters.eq("_id", usuario.getId()), Updates.combine(Updates.set("nombre", documento.get("nombre")), Updates.set("correo", documento.get("correo")), Updates.set("telefono", documento.get("telefono")), Updates.set("passwordHash", documento.get("passwordHash")), Updates.set("fechaNacimiento", documento.get("fechaNacimiento")), Updates.set("tipoUsuario", documento.get("tipoUsuario")), Updates.set("activo", documento.get("activo")))).getMatchedCount() > 0;
        } catch (MongoWriteException e) {
            if (e.getError().getCode() == 11000) {
                throw new DatosDuplicadosException("El correo ya pertenece a otro usuario.");
            }

            throw e;
        }
    }

    @Override
    public boolean eliminar(String id) {
        return coleccion.updateOne(Filters.eq("_id", id), Updates.set("activo", false)).getMatchedCount() > 0;
    }
}
