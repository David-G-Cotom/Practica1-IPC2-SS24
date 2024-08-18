/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.enums.EstadosTarjeta;
import backend.enums.TipoTarjetas;

/**
 *
 * @author Carlos Cotom
 */
public class Tarjeta {
    
    private String numeroTarjeta;
    private TipoTarjetas tipo;
    private EstadosTarjeta estado;
    private double saldo;
    private double limiteCredito;
    private double intereses;

    public Tarjeta(String numeroTarjeta, TipoTarjetas tipo, EstadosTarjeta estado, double saldo, double limiteCredito, double intereses) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipo = tipo;
        this.estado = estado;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
        this.intereses = intereses;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public TipoTarjetas getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarjetas tipo) {
        this.tipo = tipo;
    }

    public EstadosTarjeta isEstado() {
        return estado;
    }

    public void setEstado(EstadosTarjeta estado) {
        this.estado = estado;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public double getIntereses() {
        return intereses;
    }

    public void setIntereses(double intereses) {
        this.intereses = intereses;
    }
    
}
