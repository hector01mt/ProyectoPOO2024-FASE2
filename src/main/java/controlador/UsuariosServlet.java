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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cargar lista de usuarios
        request.setAttribute("usuarios", usuariosDAO.listarTodos());
        request.getRequestDispatcher("/jsp/usuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accion = request.getParameter("accion");
        String mensaje;

        try {
            if ("guardar".equals(accion) || "editar".equals(accion)) {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                String nombre = request.getParameter("nombre");
                String email = request.getParameter("email");
                String contrasena = request.getParameter("contrasena");
                String tipoUsuario = request.getParameter("tipoUsuario");

                Usuarios usuario = new Usuarios(idUsuario, nombre, email, contrasena, tipoUsuario);

                if ("guardar".equals(accion)) {
                    usuariosDAO.insertarUsuario(usuario);
                    mensaje = "Usuario guardado exitosamente.";
                } else {
                    usuariosDAO.actualizarUsuario(usuario);
                    mensaje = "Usuario actualizado exitosamente.";
                }
            } else if ("desactivar".equals(accion)) {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                usuariosDAO.desactivarUsuario(idUsuario);
                mensaje = "Usuario desactivado exitosamente.";
            } else if ("restablecer".equals(accion)) {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                String nuevaContrasena = request.getParameter("contrasena");
                usuariosDAO.restablecerContrasena(idUsuario, nuevaContrasena);
                mensaje = "Contraseña restablecida exitosamente.";
            } else {
                mensaje = "Acción desconocida.";
            }

            // Redirigir tras acción
            response.sendRedirect(request.getContextPath() + "/usuarios?mensaje=" + mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/usuarios?error=Ocurrió un error al procesar la solicitud.");
        }
    }
    
}
