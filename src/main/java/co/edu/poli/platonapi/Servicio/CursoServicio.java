package co.edu.poli.platonapi.Servicio;

import co.edu.poli.platonapi.model.Curso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoServicio {

    private final List<Curso> cursos = new ArrayList<>();

    // Método para agregar un curso
    public boolean agregarCurso(Curso curso) {
        if (curso.getCupoMaximo() <= 0) {
            throw new IllegalArgumentException("El cupo máximo debe ser mayor que cero.");
        }
        if (cursos.stream().anyMatch(c -> c.getCodigo().equals(curso.getCodigo()))) {
            throw new IllegalArgumentException("Ya existe un curso con el mismo código.");
        }
        return cursos.add(curso);
    }

    // Método para listar todos los cursos
    public List<Curso> listarCursos() {
        return new ArrayList<>(cursos);
    }

    // Método para buscar cursos por facultad
    public List<Curso> buscarCursosPorFacultad(String facultad) {
        return cursos.stream()
                .filter(curso -> curso.getFacultad().equalsIgnoreCase(facultad))
                .collect(Collectors.toList());
    }
}