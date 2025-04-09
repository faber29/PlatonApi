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
        try {
            Curso curso = gson.fromJson(request.getReader(), Curso.class);
            cursoService.agregarCurso(curso);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\":\"Curso creado exitosamente\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String facultad = request.getParameter("nombre");
        PrintWriter out = response.getWriter();

        if (facultad != null) {
            List<Curso> cursosPorFacultad = cursoService.buscarCursosPorFacultad(facultad);
            out.write(gson.toJson(cursosPorFacultad));
        } else {
            List<Curso> todosLosCursos = cursoService.listarCursos();
            out.write(gson.toJson(todosLosCursos));
        }
    }
}