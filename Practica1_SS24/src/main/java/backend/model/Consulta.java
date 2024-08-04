/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class Consulta {
    
    private String numeroTarjeta;
    private String tipoTarjeta;
    private double limiteCredito;
    private String nombreCliente;
    private String direccionCliente;
    private boolean estadoTarjeta;

    public Consulta(String numeroTarjeta, String tipoTarjeta, double limiteCredito, String nombreCliente, String direccionCliente, boolean estadoTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.limiteCredito = limiteCredito;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.estadoTarjeta = estadoTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public boolean isEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(boolean estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }    
    
}
