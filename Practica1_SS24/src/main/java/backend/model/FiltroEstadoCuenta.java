/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class FiltroEstadoCuenta {
    
    private String numeroTarjeta;
    private String TipoTarjeta;
    private double saldoMinimo;
    private double iteresesMinimo;

    public FiltroEstadoCuenta(String numeroTarjeta, String TipoTarjeta, double saldoMinimo, double iteresesMinomo) {
        this.numeroTarjeta = numeroTarjeta;
        this.TipoTarjeta = TipoTarjeta;
        this.saldoMinimo = saldoMinimo;
        this.iteresesMinimo = iteresesMinomo;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return TipoTarjeta;
    }

    public void setTipoTarjeta(String TipoTarjeta) {
        this.TipoTarjeta = TipoTarjeta;
    }

    public double getSaldoMinimo() {
        return saldoMinimo;
    }

    public void setSaldoMinimo(double saldoMinimo) {
        this.saldoMinimo = saldoMinimo;
    }

    public double getIteresesMinimo() {
        return iteresesMinimo;
    }

    public void setIteresesMinimo(double iteresesMinimo) {
        this.iteresesMinimo = iteresesMinimo;
    }        
    
}
