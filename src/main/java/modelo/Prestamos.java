/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Date;

/**
 *
 * @author Hector Marquez
 */
public class Prestamos {
    private int idPrestamo;
    private int idUsuario;
    private int idItem;
    private String nombreUsuario; 
    private String tituloEjemplar;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private boolean devuelto;
    private double mora; 

    // Constructor vacío
    public Prestamos() {}
    
    public Prestamos(int idPrestamo, int idUsuario, int idItem, String nombreUsuario, String tituloEjemplar,
                     java.sql.Date fechaPrestamo, java.sql.Date fechaDevolucion, boolean devuelto, double mora) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idItem = idItem;
        this.nombreUsuario = nombreUsuario;
        this.tituloEjemplar = tituloEjemplar;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
        this.mora = mora;
    }

    // Constructor completo
    public Prestamos (int idPrestamo, int idUsuario, int idItem, Date fechaPrestamo, Date fechaDevolucion, boolean devuelto, double mora) {
        this.idPrestamo = idPrestamo;
        this.idUsuario = idUsuario;
        this.idItem = idItem;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
        this.mora = mora;
    }

    // Getters y Setters
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTituloEjemplar() {
        return tituloEjemplar;
    }

    public void setTituloEjemplar(String tituloEjemplar) {
        this.tituloEjemplar = tituloEjemplar;
    }
    
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    public double getMora() {
        return mora;
    }

    public void setMora(double mora) {
        this.mora = mora;
    }
    
}
