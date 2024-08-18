/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.enums.TipoTarjetas;

/**
 *
 * @author Carlos Cotom
 */
public class SolicitudTarjeta {
    
    private int numeroSolicitud;
    private String fechaSolicitud;
    private TipoTarjetas tipoTarjetaSolicitada;
    private String nombreSolicitante;
    private double salarioSolicitante;
    private String direccionSolicitante;

    public SolicitudTarjeta(int numeroSolicitud, String fechaSolicitud, TipoTarjetas tipoTarjetaSolicitada, String nombreSolicitante, double salarioSolicitante, String direccionSolicitante) {
        this.numeroSolicitud = numeroSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.tipoTarjetaSolicitada = tipoTarjetaSolicitada;
        this.nombreSolicitante = nombreSolicitante;
        this.salarioSolicitante = salarioSolicitante;
        this.direccionSolicitante = direccionSolicitante;
    }

    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public TipoTarjetas getTipoTarjetaSolicitada() {
        return tipoTarjetaSolicitada;
    }

    public void setTipoTarjetaSolicitada(TipoTarjetas tipoTarjetaSolicitada) {
        this.tipoTarjetaSolicitada = tipoTarjetaSolicitada;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public double getSalarioSolicitante() {
        return salarioSolicitante;
    }

    public void setSalarioSolicitante(double salarioSolicitante) {
        this.salarioSolicitante = salarioSolicitante;
    }

    public String getDireccionSolicitante() {
        return direccionSolicitante;
    }

    public void setDireccionSolicitante(String direccionSolicitante) {
        this.direccionSolicitante = direccionSolicitante;
    }
    
}
