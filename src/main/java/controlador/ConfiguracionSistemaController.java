/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.dao.ConfiguracionSistemaDao;
import modelo.ConfiguracionSistema;


public class ConfiguracionSistemaController {
    
     private final ConfiguracionSistemaDao dao;

    public ConfiguracionSistemaController() {
        this.dao = new ConfiguracionSistemaDao();
    }

    public ConfiguracionSistema obtenerConfiguracion() {
        return dao.obtenerConfiguracion();
    }

    public boolean guardarConfiguracion(double moraDiaria, int maxEjemplares) {
        ConfiguracionSistema configuracion = new ConfiguracionSistema(moraDiaria, maxEjemplares);
        return dao.guardarConfiguracion(configuracion);
    }
    
}
