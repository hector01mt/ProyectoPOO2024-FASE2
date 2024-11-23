/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Date;
import java.util.List;
import modelo.ConfiguracionSistema;
import modelo.Prestamos;
import modelo.dao.PrestamosDAO;

/**
 *
 * @author Hector Marquez
 */
public class PrestamosController {
    
    private final PrestamosDAO prestamoDAO;
    private final ConfiguracionSistemaController configuracionController;


    public PrestamosController() {
        this.prestamoDAO = new PrestamosDAO();
        this.configuracionController = new ConfiguracionSistemaController();
    }

    public boolean registrarPrestamo(Prestamos prestamo) {
        return prestamoDAO.insertarPrestamo(prestamo);
    }

//    public boolean devolverPrestamo(int idPrestamo) {
//        return prestamoDAO.marcarDevuelto(idPrestamo);
//    }
    
    public List<Prestamos> listarPrestamosPorUsuario(int idUsuario) {
        // Si idUsuario == 0, retornar todos los préstamos
        if (idUsuario == 0) {
            return prestamoDAO.obtenerTodosLosPrestamos(); // Verifica este método en el DAO
        } else {
            return prestamoDAO.listarPrestamosPorUsuario(idUsuario);
        }
    }
    
    public List<Prestamos> listarPrestamosConDetalles() {
        PrestamosDAO prestamosDAO = new PrestamosDAO();
        return prestamosDAO.listarPrestamosConDetalles();
    }

    /*public List<Prestamos> listarPrestamosPorUsuario(int idUsuario) {
        return prestamoDAO.listarPrestamosPorUsuario(idUsuario);
    }*/
    
    public boolean marcarDevuelto(int idPrestamo, double mora) {
    return prestamoDAO.marcarDevuelto(idPrestamo, mora);
    }
    
    public List<Prestamos> listarPendientes() {
        return prestamoDAO.listarPendientes();
    }
    
    public boolean validarLimitePrestamos(int idUsuario) {

        int prestamosActivos = prestamoDAO.contarPrestamosActivosPorUsuario(idUsuario);
        ConfiguracionSistema configuracion = configuracionController.obtenerConfiguracion();
        if (configuracion == null) {
            System.out.println("No se pudo obtener la configuración del sistema.");
            return false; // Devuelve falso por seguridad
        }
        System.out.println("Préstamos activos para el usuario " + idUsuario + ": " + prestamosActivos);
        System.out.println("Límite máximo permitido: " + configuracion.getMaxEjemplares());
        return prestamosActivos < configuracion.getMaxEjemplares();
    }

    public double calcularMora(Date fechaDevolucion) {
        ConfiguracionSistema configuracion = configuracionController.obtenerConfiguracion();
        double moraDiaria = configuracion.getMoraDiaria();

        long diasRetraso = (System.currentTimeMillis() - fechaDevolucion.getTime()) / (1000 * 60 * 60 * 24);
        return diasRetraso > 0 ? diasRetraso * moraDiaria : 0.0;
    }
    
}
