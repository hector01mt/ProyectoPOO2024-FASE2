/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Usuarios;
import modelo.dao.UsuariosDAO;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Hector Marquez
 */
@WebServlet("/usuarios")
public class UsuariosServlet extends HttpServlet {
    
    private final UsuariosDAO usuariosDAO = new UsuariosDAO(); // Instancia del DAO

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Usuarios> usuarios = usuariosDAO.listarTodos();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/jsp/usuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {
            String idUsuarioParam = request.getParameter("idUsuario");
            int idUsuario = (idUsuarioParam != null && !idUsuarioParam.isEmpty()) ? Integer.parseInt(idUsuarioParam) : 0;

            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String contrasena = request.getParameter("contrasena");
            String tipoUsuario = request.getParameter("tipoUsuario");

            Usuarios usuario = new Usuarios(idUsuario, nombre, email, contrasena, tipoUsuario);
            if (idUsuario == 0) {
                usuariosDAO.insertarUsuario(usuario);
            } else {
                usuariosDAO.actualizarUsuario(usuario);
            }
        } else if ("editar".equals(accion)) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

            // Usamos listarTodos y filtramos el usuario por ID
            Usuarios usuario = usuariosDAO.listarTodos().stream()
                    .filter(u -> u.getIdUsuario() == idUsuario)
                    .findFirst()
                    .orElse(null);

            if (usuario != null) {
                request.setAttribute("usuario", usuario);
            }

            // Cargar nuevamente los datos para la página
            List<Usuarios> usuarios = usuariosDAO.listarTodos();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/jsp/usuarios.jsp").forward(request, response);
        } else if ("desactivar".equals(accion)) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            usuariosDAO.desactivarUsuario(idUsuario);
        } else if ("restablecer".equals(accion)) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            String nuevaContrasena = "123456"; // Restablecer a una contraseña predeterminada
            usuariosDAO.restablecerContrasena(idUsuario, nuevaContrasena);
        }

        response.sendRedirect("usuarios");
    }
    
    
}
