/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappers;

import Entidades.TipoUsuario;
import Entidades.Usuario;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.bson.Document;

/**
 *
 * @author Jorge
 */
public class UsuarioMapper {

    /**
     * Convierte una entidad Usuario en Document.
     * @param usuario Usuario que se convertirá.
     * @return Documento MongoDB.
     */
    public static Document toDocument(Usuario usuario) {
        Date fechaNacimiento = Date.from(usuario.getFechaNacimiento().atStartOfDay(ZoneOffset.UTC).toInstant());
        return new Document("_id", usuario.getId()).append("nombre", usuario.getNombre()).append("correo", usuario.getCorreo()).append("telefono", usuario.getTelefono()).append("passwordHash", usuario.getPasswordHash()).append("fechaNacimiento", fechaNacimiento).append("tipoUsuario", usuario.getTipoUsuario().name()).append("activo", usuario.isActivo());
    }

    /**
     * Convierte un documento MongoDB en Usuario.
     * @param documento Documento de MongoDB.
     * @return Entidad Usuario.
     */
    public static Usuario toEntity(Document documento) {
        Date fecha = documento.getDate("fechaNacimiento");
        LocalDate fechaNacimiento = fecha.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        TipoUsuario tipo = TipoUsuario.valueOf(documento.getString("tipoUsuario"));

        return new Usuario(documento.getString("_id"), documento.getString("nombre"), documento.getString("correo"), documento.getString("telefono"), documento.getString("passwordHash"), fechaNacimiento, tipo, documento.getBoolean("activo", true));
    }
}
