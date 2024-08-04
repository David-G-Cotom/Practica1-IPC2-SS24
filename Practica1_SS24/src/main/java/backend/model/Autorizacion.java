/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class Autorizacion {
    
    private final double porcentajeBancario = 0.6;
    private double salarioCliente;
    private double limiteCreditoTarjeta;
    private String numeroTarjetaNacional = "4256 3102 654";
    private String numeroTarjetaRegional = "4256 3102 656";
    private String numeroTarjetaInternacional = "4256 3102 658";

    public Autorizacion(double salarioCliente, double limiteCreditoTarjeta) {
        this.salarioCliente = salarioCliente;
        this.limiteCreditoTarjeta = limiteCreditoTarjeta;
    }

    public double getSalarioCliente() {
        return salarioCliente;
    }

    public void setSalarioCliente(double salarioCliente) {
        this.salarioCliente = salarioCliente;
    }

    public double getLimiteCreditoTarjeta() {
        return limiteCreditoTarjeta;
    }

    public void setLimiteCreditoTarjeta(double limiteCreditoTarjeta) {
        this.limiteCreditoTarjeta = limiteCreditoTarjeta;
    }

    public String getNumeroTarjetaNacional() {
        return numeroTarjetaNacional;
    }

    public void setNumeroTarjetaNacional(String numeroTarjetaNacional) {
        this.numeroTarjetaNacional = numeroTarjetaNacional;
    }

    public String getNumeroTarjetaRegional() {
        return numeroTarjetaRegional;
    }

    public void setNumeroTarjetaRegional(String numeroTarjetaRegional) {
        this.numeroTarjetaRegional = numeroTarjetaRegional;
    }

    public String getNumeroTarjetaInternacional() {
        return numeroTarjetaInternacional;
    }

    public void setNumeroTarjetaInternacional(String numeroTarjetaInternacional) {
        this.numeroTarjetaInternacional = numeroTarjetaInternacional;
    }
    
}
