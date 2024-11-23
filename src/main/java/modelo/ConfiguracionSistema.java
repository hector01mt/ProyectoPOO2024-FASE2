/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


public class ConfiguracionSistema {
    private double moraDiaria;
    private int maxEjemplares;

    public ConfiguracionSistema(double moraDiaria, int maxEjemplares) {
        this.moraDiaria = moraDiaria;
        this.maxEjemplares = maxEjemplares;
    }

    public double getMoraDiaria() {
        return moraDiaria;
    }

    public void setMoraDiaria(double moraDiaria) {
        this.moraDiaria = moraDiaria;
    }

    public int getMaxEjemplares() {
        return maxEjemplares;
    }

    public void setMaxEjemplares(int maxEjemplares) {
        this.maxEjemplares = maxEjemplares;
    }
    
}
