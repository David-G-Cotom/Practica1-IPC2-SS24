/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class FiltroListadoTarjetas {
    
    private String tipoTarjeta;
    private double limiteMinimo;
    private String fechaInicio;
    private String fechaFinal;
    private String estadoTarjeta;

    public FiltroListadoTarjetas(String tipoTarjeta, double limiteMinimo, String fechaInicio, String fechaFinal, String estado) {
        this.tipoTarjeta = tipoTarjeta;
        this.limiteMinimo = limiteMinimo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.estadoTarjeta = estado;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getLimiteMinimo() {
        return limiteMinimo;
    }

    public void setLimiteMinimo(double limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(String estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }        
    
}
