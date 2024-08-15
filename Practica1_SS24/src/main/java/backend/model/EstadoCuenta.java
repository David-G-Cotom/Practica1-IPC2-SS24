/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class EstadoCuenta {
    
    private String numeroTarjeta;
    private String tipoTarjeta;
    private String nombreCliente;
    private String direccionCliente;
    private double interesTipoTarjeta;
    private ArrayList<MovimientoTarjeta> movimientos;
    private double montoTotalEjecutado;
    private double intereses;
    private double saldoTotal;

    public EstadoCuenta(String numeroTarjeta, String tipoTarjeta, String nombreClietne, String direccionCliente, ArrayList<MovimientoTarjeta> movimientos, double interesTipoTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.nombreCliente = nombreClietne;
        this.direccionCliente = direccionCliente;
        this.movimientos = movimientos;
        this.interesTipoTarjeta = interesTipoTarjeta;
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

    public double getInteresTipoTarjeta() {
        return interesTipoTarjeta;
    }

    public void setInteresTipoTarjeta(double interesTipoTarjeta) {
        this.interesTipoTarjeta = interesTipoTarjeta;
    }

    public ArrayList<MovimientoTarjeta> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(ArrayList<MovimientoTarjeta> movimientos) {
        this.movimientos = movimientos;
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
