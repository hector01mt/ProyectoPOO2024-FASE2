/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Conexion;
import modelo.ConfiguracionSistema;


public class ConfiguracionSistemaDao {
    
     public ConfiguracionSistema obtenerConfiguracion() {
        String query = "SELECT maxEjemplares, moraDiaria FROM configuracionSistema LIMIT 1";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ConfiguracionSistema(rs.getInt("maxEjemplares"), (int) rs.getDouble("moraDiaria"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener configuración del sistema: " + e.getMessage());
        }
        return null; // Retorna null si no hay configuración
    }

    public boolean guardarConfiguracion(ConfiguracionSistema configuracion) {
        String sql = "UPDATE configuracionSistema SET moraDiaria = ?, maxEjemplares = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, configuracion.getMoraDiaria());
            stmt.setInt(2, configuracion.getMaxEjemplares());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
