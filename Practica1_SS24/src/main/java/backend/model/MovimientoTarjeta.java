/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class MovimientoTarjeta {
    
    private String numeroTarjeta;
    private String fechaOperacion;
    private String tipoMovimiento;
    private String descripcion;
    private String establecimiento;
    private double montoTransferido;

    public MovimientoTarjeta(String numeroTarjeta, String fechaOperacion, String tipoMovimiento, String descripcion, String establecimiento, double montoTransferido) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaOperacion = fechaOperacion;
        this.tipoMovimiento = tipoMovimiento;
        this.descripcion = descripcion;
        this.establecimiento = establecimiento;
        this.montoTransferido = montoTransferido;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(String fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public double getMontoTransferido() {
        return montoTransferido;
    }

    public void setMontoTransferido(double montoTransferido) {
        this.montoTransferido = montoTransferido;
    }
    
    
}
