/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Usuarios;
import modelo.dao.UsuariosDAO;


public class LoginController {
     private final UsuariosDAO usuariosDAO;

    // Constructor
    public LoginController() {
        this.usuariosDAO = new UsuariosDAO();
    }

    // Método para autenticar un usuario
    public Usuarios autenticar(String email, String contrasena) {
        return usuariosDAO.autenticarUsuario(email, contrasena);
    }

    // Método para redirigir según el tipo de usuario
    public String redirigirVista(Usuarios usuario) {
        if (usuario == null) {
            return "Error: Usuario no encontrado o credenciales incorrectas";
        }

        switch (usuario.getTipoUsuario()) {
            case "Admin":
                return "AdminDashboard";
            case "Profesor":
                return "UsuarioDashboard";
            case "Alumno":
                return "UsuarioDashboard";
            default:
                return "Error: Tipo de usuario no reconocido";
        }
    }
    
}
