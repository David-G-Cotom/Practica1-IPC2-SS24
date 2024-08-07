/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class EstadoCuenta {
    
    private String numeroTarjeta;
    private String nombreClietne;
    private String direccionCliente;
    private String fechaMovimiento;
    private String tipoMovimiento;
    private String desscripcionMovimiento;
    private String establecimiento;
    private double montoEjecutado;
    private double montoTotalEjecutado;
    private double intereses;
    private double saldoTotal;

    public EstadoCuenta(String numeroTarjeta, String nombreClietne, String direccionCliente, String fechaMovimiento, String tipoMovimiento, String desscripcionMovimiento, String establecimiento, double montoEjecutado, double montoTotalEjecutado, double intereses, double saldoTotal) {
        this.numeroTarjeta = numeroTarjeta;
        this.nombreClietne = nombreClietne;
        this.direccionCliente = direccionCliente;
        this.fechaMovimiento = fechaMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.desscripcionMovimiento = desscripcionMovimiento;
        this.establecimiento = establecimiento;
        this.montoEjecutado = montoEjecutado;
        this.montoTotalEjecutado = montoTotalEjecutado;
        this.intereses = intereses;
        this.saldoTotal = saldoTotal;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNombreClietne() {
        return nombreClietne;
    }

    public void setNombreClietne(String nombreClietne) {
        this.nombreClietne = nombreClietne;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(String fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getDesscripcionMovimiento() {
        return desscripcionMovimiento;
    }

    public void setDesscripcionMovimiento(String desscripcionMovimiento) {
        this.desscripcionMovimiento = desscripcionMovimiento;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public double getMontoEjecutado() {
        return montoEjecutado;
    }

    public void setMontoEjecutado(double montoEjecutado) {
        this.montoEjecutado = montoEjecutado;
    }

    public double getMontoTotalEjecutado() {
        return montoTotalEjecutado;
    }

    public void setMontoTotalEjecutado(double montoTotalEjecutado) {
        this.montoTotalEjecutado = montoTotalEjecutado;
    }

    public double getIntereses() {
        return intereses;
    }

    public void setIntereses(double intereses) {
        this.intereses = intereses;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }
    
    
    
}
