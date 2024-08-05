/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class FiltroListadoSolicitudes {
    
    private String fechaInicio;
    private String fechaFin;
    private String tipoTarjeta;
    private double salarioMinimo;
    private String estadoSolicitud;

    public FiltroListadoSolicitudes(String fechaInicio, String fechaFin, String tipoTarjeta, double salarioMinimo, String estadoSolicitud) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoTarjeta = tipoTarjeta;
        this.salarioMinimo = salarioMinimo;
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getSalarioMinimo() {
        return salarioMinimo;
    }

    public void setSalarioMinimo(int salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }        
    
}
