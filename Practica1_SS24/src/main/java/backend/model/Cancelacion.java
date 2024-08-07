/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class Cancelacion {
    
    private String numeroTarjeta;
    private String estadoTarjeta;
    private String nombrePropietario;
    private String salarioPropietario;
    private String direccionPropietario;
    private double saldoTarjeta;

    public Cancelacion(String numeroTarjeta, String estadoTarjeta, String nombrePropietario, String salarioPropietario, String direccionPropietario, double saldoTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.estadoTarjeta = estadoTarjeta;
        this.nombrePropietario = nombrePropietario;
        this.salarioPropietario = salarioPropietario;
        this.direccionPropietario = direccionPropietario;
        this.saldoTarjeta = saldoTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(String estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public String getSalarioPropietario() {
        return salarioPropietario;
    }

    public void setSalarioPropietario(String salarioPropietario) {
        this.salarioPropietario = salarioPropietario;
    }

    public String getDireccionPropietario() {
        return direccionPropietario;
    }

    public void setDireccionPropietario(String direccionPropietario) {
        this.direccionPropietario = direccionPropietario;
    }

    public double getSaldoTarjeta() {
        return saldoTarjeta;
    }

    public void setSaldoTarjeta(double saldoTarjeta) {
        this.saldoTarjeta = saldoTarjeta;
    }    
    
}
