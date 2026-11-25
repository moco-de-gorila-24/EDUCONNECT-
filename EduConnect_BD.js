
const dbEdu = db.getSiblingDB("educonnect");

// Crear colecciones si todavía no existen
const colecciones = [
    "usuarios",
    "cursos",
    "inscripciones",
    "actividades",
    "entregas",
    "calificaciones"
];

colecciones.forEach(nombre => {
    if (!dbEdu.getCollectionNames().includes(nombre)) {
        dbEdu.createCollection(nombre);
    }
});

// No permitir correos duplicados
dbEdu.usuarios.createIndex(
    { correo: 1 },
    { unique: true, name: "uq_usuario_correo" }
);

// Consultar cursos activos de un profesor
dbEdu.cursos.createIndex(
    { profesorId: 1, activo: 1 },
    { name: "ix_cursos_profesor_activo" }
);

// Evitar que un estudiante se inscriba dos veces en el mismo curso
dbEdu.inscripciones.createIndex(
    { estudianteId: 1, cursoId: 1 },
    { unique: true, name: "uq_inscripcion_estudiante_curso" }
);

// Consultar actividades de un curso
dbEdu.actividades.createIndex(
    { cursoId: 1, activo: 1 },
    { name: "ix_actividades_curso" }
);

// Evitar dos entregas de la misma actividad por el mismo estudiante
dbEdu.entregas.createIndex(
    { actividadId: 1, estudianteId: 1 },
    { unique: true, name: "uq_entrega_actividad_estudiante" }
);

// Una calificación por entrega
dbEdu.calificaciones.createIndex(
    { entregaId: 1 },
    { unique: true, name: "uq_calificacion_entrega" }
);

print("Base de datos EduConnect preparada.");
print("Colecciones e índices creados correctamente.");