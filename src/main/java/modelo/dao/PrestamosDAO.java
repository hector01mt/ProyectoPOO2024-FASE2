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
import modelo.Prestamos;

/**
 *
 * @author Hector Marquez
 */
public class PrestamosDAO {
    
     // Crear un nuevo préstamo
    public boolean insertarPrestamo(Prestamos prestamo) {
        String sql = "INSERT INTO prestamos (idUsuario, idItem, fechaPrestamo, fechaDevolucion, devuelto, mora) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getIdUsuario());
            stmt.setInt(2, prestamo.getIdItem());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(4, new java.sql.Date(prestamo.getFechaDevolucion().getTime()));
            stmt.setBoolean(5, prestamo.isDevuelto());
            stmt.setDouble(6, prestamo.getMora());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar estado del préstamo (devolución)
    public boolean marcarDevuelto(int idPrestamo, double mora) {
        String sql = "UPDATE prestamos SET devuelto = ?, mora = ? WHERE idPrestamo = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, true);
            stmt.setDouble(2, mora);
            stmt.setInt(3, idPrestamo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Prestamos> obtenerTodosLosPrestamos() {
    List<Prestamos> prestamos = new ArrayList<>();
    try (Connection conn = Conexion.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM prestamos")) {
        while (rs.next()) {
            Prestamos prestamo = new Prestamos(
                rs.getInt("idPrestamo"),
                rs.getInt("idUsuario"),
                rs.getInt("idItem"),
                rs.getDate("fechaPrestamo"),
                rs.getDate("fechaDevolucion"),
                rs.getBoolean("devuelto"),
                rs.getDouble("mora")
            );
            prestamos.add(prestamo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return prestamos;
}

    // Listar préstamos por usuario
    /*public List<Prestamos> listarPrestamosPorUsuario(int idUsuario) {
        List<Prestamos> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE idUsuario = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Prestamos prestamo = mapResultSetToPrestamo(rs);
                    prestamos.add(prestamo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    */
    
    public List<Prestamos> listarPrestamosPorUsuario(int idUsuario) {
    List<Prestamos> prestamos = new ArrayList<>();
    String sql = "SELECT p.*, i.titulo AS tituloEjemplar " +
                 "FROM prestamos p " +
                 "JOIN items i ON p.idItem = i.idItem " +
                 "WHERE p.idUsuario = ?";
    try (Connection conn = Conexion.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, idUsuario);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Prestamos prestamo = new Prestamos(
                    rs.getInt("idPrestamo"),
                    rs.getInt("idUsuario"),
                    rs.getInt("idItem"),
                    rs.getDate("fechaPrestamo"),
                    rs.getDate("fechaDevolucion"),
                    rs.getBoolean("devuelto"),
                    rs.getDouble("mora")
                );
                prestamo.setTituloEjemplar(rs.getString("tituloEjemplar")); // Asignar el título
                prestamos.add(prestamo);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return prestamos;
}
    public List<Prestamos> listarPrestamosConDetalles() {
        List<Prestamos> prestamos = new ArrayList<>();
        String sql = "SELECT p.idPrestamo, u.nombreUsuario, i.titulo, p.fechaPrestamo, "
                + "p.fechaDevolucion, p.devuelto, p.mora "
                + "FROM prestamos p "
                + "JOIN usuarios u ON p.idUsuario = u.idUsuario "
                + "JOIN items i ON p.idItem = i.idItem";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Prestamos prestamo = new Prestamos(
                        rs.getInt("idPrestamo"),
                        0, // idUsuario no necesario en este contexto
                        0, // idItem no necesario en este contexto
                        rs.getString("nombreUsuario"),
                        rs.getString("titulo"),
                        rs.getDate("fechaPrestamo"),
                        rs.getDate("fechaDevolucion"),
                        rs.getBoolean("devuelto"),
                        rs.getDouble("mora")
                );
                prestamos.add(prestamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    
    public List<Prestamos> listarPendientes() {
        List<Prestamos> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE devuelto = false";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                prestamos.add(mapResultSetToPrestamo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }
    
     public int contarPrestamosActivosPorUsuario(int idUsuario) {
        String query = "SELECT COUNT(*) FROM prestamos WHERE idUsuario = ? AND devuelto = false";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al contar préstamos activos: " + e.getMessage());
        }
        return 0;
    }

    // Mapear ResultSet a un objeto Prestamo
    private Prestamos mapResultSetToPrestamo(ResultSet rs) throws SQLException {
        return new Prestamos(
                rs.getInt("idPrestamo"),
                rs.getInt("idUsuario"),
                rs.getInt("idItem"),
                rs.getDate("fechaPrestamo"),
                rs.getDate("fechaDevolucion"),
                rs.getBoolean("devuelto"),
                rs.getDouble("mora")
        );
    }
    
}
