/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import modelo.Usuarios;
import modelo.dao.UsuariosDAO;

/**
 *
 * @author Hector Marquez
 */
public class UsuariosController {
    private final UsuariosDAO usuariosDAO;

    // Constructor para inicializar el DAO
    public UsuariosController() {
        this.usuariosDAO = new UsuariosDAO();
    }

    // Método para agregar un nuevo usuario
    public boolean agregarUsuario(Usuarios usuario) {
        return usuariosDAO.insertarUsuario(usuario);
    }

    // Método para buscar usuarios por nombre o email
    public List<Usuarios> buscarUsuarios(String criterioBusqueda) {
        return usuariosDAO.buscarUsuarios(criterioBusqueda);
    }

    // Método para eliminar un usuario por ID
    public boolean eliminarUsuario(int idUsuario) {
        return usuariosDAO.eliminarUsuario(idUsuario);
    }

    // Método para actualizar la información de un usuario
    public boolean actualizarUsuario(Usuarios usuario) {
        return usuariosDAO.actualizarUsuario(usuario);
    }

    // Método para listar todos los usuarios
    public List<Usuarios> listarUsuarios() {
        return usuariosDAO.listarTodos();
    }

    // Método para autenticar un usuario
    public Usuarios autenticarUsuario(String email, String contrasena) {
        return usuariosDAO.autenticarUsuario(email, contrasena);
    }
    
    public boolean restablecerContrasena(int idUsuario, String nuevaContrasena) {
        return usuariosDAO.restablecerContrasena(idUsuario, nuevaContrasena);
    }
    
    public boolean desactivarUsuario(int idUsuario) {
        if (usuariosDAO.tienePrestamosActivos(idUsuario)) {
            JOptionPane.showMessageDialog(null, "El usuario tiene préstamos activos y no puede ser desactivado.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return usuariosDAO.desactivarUsuario(idUsuario);
    }
    
}
