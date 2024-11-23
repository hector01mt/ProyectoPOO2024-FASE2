/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Conexion;
import modelo.Usuarios;

/**
 *
 * @author Hector Marquez
 */
public class UsuariosDAO {
    // Método para insertar un nuevo usuario
    public boolean insertarUsuario(Usuarios usuario) {
        String sql = "INSERT INTO usuarios (nombreUsuario, email, contrasena, tipoUsuario, activo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getTipoUsuario());
            stmt.setBoolean(5, usuario.isActivo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar usuarios por nombre o email
    public List<Usuarios> buscarUsuarios(String criterioBusqueda) {
        List<Usuarios> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE nombreUsuario LIKE ? OR email LIKE ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + criterioBusqueda + "%");
            stmt.setString(2, "%" + criterioBusqueda + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapResultSetToUsuario(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para eliminar un usuario por ID
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "UPDATE usuarios SET activo = FALSE WHERE idUsuario = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un usuario
    public boolean actualizarUsuario(Usuarios usuario) {
        String sql = "UPDATE usuarios SET nombreUsuario = ?, email = ?, contrasena = ?, tipoUsuario = ? WHERE idUsuario = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getTipoUsuario());
            stmt.setInt(5, usuario.getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para listar todos los usuarios
    public List<Usuarios> listarTodos() {
        List<Usuarios> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE activo = TRUE";
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(mapResultSetToUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para autenticar un usuario
    public Usuarios autenticarUsuario(String email, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND contrasena = ? AND activo = TRUE";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, contrasena);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUsuario(rs);
                }
            }
        } catch (SQLException e) {
        System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean restablecerContrasena(int idUsuario, String nuevaContrasena) {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE idUsuario = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevaContrasena);
            stmt.setInt(2, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String obtenerNombreUsuario(int idUsuario) {
        String nombreUsuario = "";
        String sql = "SELECT nombreUsuario FROM usuarios WHERE idUsuario = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nombreUsuario = rs.getString("nombreUsuario");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombreUsuario;
    }
    
    public boolean tienePrestamosActivos(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE idUsuario = ? AND devuelto = FALSE"; // Verificar préstamos no devueltos
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si tiene préstamos activos
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Si ocurre un error o no hay préstamos activos
    }

    public boolean desactivarUsuario(int idUsuario) {
        String query = "UPDATE usuarios SET activo = FALSE WHERE idUsuario = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método auxiliar para mapear un ResultSet a un objeto Usuarios
    private Usuarios mapResultSetToUsuario(ResultSet rs) throws SQLException {
        return new Usuarios(
                rs.getInt("idUsuario"),
                rs.getString("nombreUsuario"),
                rs.getString("email"),
                rs.getString("contrasena"),
                rs.getString("tipoUsuario"),
                rs.getBoolean("activo")
        );
    }
}
