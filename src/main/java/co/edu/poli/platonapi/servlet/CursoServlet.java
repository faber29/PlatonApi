package co.edu.poli.platonapi.servlet;

import co.edu.poli.platonapi.model.Curso;
import co.edu.poli.platonapi.Servicio.CursoServicio;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(name = "CursoServlet", urlPatterns = {"/cursos", "/cursos/facultad"})
public class CursoServlet extends HttpServlet {
    private final CursoServicio cursoService = new CursoServicio();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        if (request.getContentType() != null && request.getContentType().contains("application/json")) {
            Curso curso = gson.fromJson(request.getReader(), Curso.class);
            cursoService.agregarCurso(curso);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\":\"Curso creado exitosamente\"}");
        } else {

            Curso curso = new Curso(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("nombre"),
                    request.getParameter("codigo"),
                    request.getParameter("profesor"),
                    Integer.parseInt(request.getParameter("cupoMaximo")),
                    Integer.parseInt(request.getParameter("estudiantesInscritos")),
                    request.getParameter("facultad")
            );
            cursoService.agregarCurso(curso);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\":\"Curso creado exitosamente\"}");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String path = request.getServletPath();

        try {
            if ("/cursos/facultad".equals(path)) {
                String facultad = request.getParameter("nombre"); // ✅ Solo se declara una vez
                if (facultad == null || facultad.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"error\":\"Parámetro 'nombre' es requerido\"}");
                    return;
                }
                List<Curso> cursosPorFacultad = cursoService.buscarCursosPorFacultad(facultad);
                out.write(gson.toJson(cursosPorFacultad));
            } else if ("/cursos".equals(path)) {
                List<Curso> todosLosCursos = cursoService.listarCursos();
                out.write(gson.toJson(todosLosCursos));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"error\":\"Ruta no encontrada\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}