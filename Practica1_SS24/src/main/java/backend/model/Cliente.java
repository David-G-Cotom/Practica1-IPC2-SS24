/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

/**
 *
 * @author Carlos Cotom
 */
public class Cliente {
    
    private int idCliente;
    private String nombre;
    private double salario;
    private String direccion;
    private String numeroTarjeta;

    public Cliente(int idCliente, String nombre, double salario, String direccion, String numeroTarjeta) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.salario = salario;
        this.direccion = direccion;
        this.numeroTarjeta = numeroTarjeta;
    }

    public Cliente(String nombre, double salario, String direccion) {
        this.nombre = nombre;
        this.salario = salario;
        this.direccion = direccion;
    }        
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

}
