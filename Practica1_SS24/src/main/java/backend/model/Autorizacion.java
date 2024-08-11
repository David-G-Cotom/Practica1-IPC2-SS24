/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.data.AutorizacionTarjetaDB;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Carlos Cotom
 */
public class Autorizacion {

    private final double PORCENTAJE_BANCARIO = 0.6;
    private final String NUMERO_TARJETA_NACIONAL = "4256 3102 654";
    private final String NUMERO_TARJETA_REGIONAL = "4256 3102 656";
    private final String NUMERO_TARJETA_INTERNACIONAL = "4256 3102 658";
    private double salarioCliente;
    private double limiteCreditoTipo;
    private String tipoTarjeta;
    private String numeroTarjeta;
    private ArrayList<String> numerosRegistrados;
    private Tarjeta tarjeta;

    public Autorizacion(double salarioCliente, double limiteCreditoTarjeta, String tipoTarjeta) {
        this.salarioCliente = salarioCliente;
        this.limiteCreditoTipo = limiteCreditoTarjeta;
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getSalarioCliente() {
        return salarioCliente;
    }

    public void setSalarioCliente(double salarioCliente) {
        this.salarioCliente = salarioCliente;
    }

    public double getLimiteCreditoTipo() {
        return limiteCreditoTipo;
    }

    public void setLimiteCreditoTipo(double limiteCreditoTipo) {
        this.limiteCreditoTipo = limiteCreditoTipo;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }        

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public boolean isLimiteCreditoAprobado() {
        return (this.salarioCliente * this.PORCENTAJE_BANCARIO) > this.limiteCreditoTipo;
    }

    public boolean autorizarTarjeta() {
        if (this.isLimiteCreditoAprobado()) {
            this.crearNumeroTarjeta();
            this.tarjeta = new Tarjeta(this.numeroTarjeta, this.tipoTarjeta, true, 0, (this.salarioCliente * this.PORCENTAJE_BANCARIO), 0);
            return true;
        }
        return false;
    }

    private void crearNumeroTarjeta() {
        switch (this.tipoTarjeta) {
            case "NACIONAL":
                this.numeroTarjeta = this.crearNumero(this.NUMERO_TARJETA_NACIONAL);
                break;
            case "REGIONAL":
                this.numeroTarjeta = this.crearNumero(this.NUMERO_TARJETA_REGIONAL);
                break;
            case "INTERNACIONAL":
                this.numeroTarjeta = this.crearNumero(this.NUMERO_TARJETA_INTERNACIONAL);
                break;
            default:
                throw new AssertionError();
        }
    }

    private String crearNumero(String numero) {
        AutorizacionTarjetaDB datos = new AutorizacionTarjetaDB();
        this.numerosRegistrados = datos.getNumeroTarjetas(numero);
        if (this.numerosRegistrados.isEmpty()) {
            Random random = new Random();
            int cifraAleatoria;
            for (int i = 0; i < 6; i++) {
                cifraAleatoria = random.nextInt(10);
                if (i == 1) {
                    numero += " ";
                } else {
                    numero += cifraAleatoria;                    
                }
            }
            return numero;
        } else {
            String ultimoNumeroRegistrado = this.numerosRegistrados.getLast();
            String[] numerosRegistro = ultimoNumeroRegistrado.split(" ");
            String primeroGrupoCifras = numerosRegistro[0];
            String segundoGrupoCifras = numerosRegistro[1];
            String tercerGrupoCifras = numerosRegistro[2];
            String cuartoGrupoCifras = numerosRegistro[3];
            String[] subNumeros3 = numerosRegistro[2].split("");
            String[] subNumeros4 = numerosRegistro[3].split("");
            int penultimaCifra = Integer.parseInt(numerosRegistro[2]);
            int ultimaCifra = Integer.parseInt(numerosRegistro[3]);
            if (ultimaCifra == 9999) {
                cuartoGrupoCifras = "0000";
                if (subNumeros3[3].equals("9")) {
                    subNumeros3[3] = "0";
                    tercerGrupoCifras = "";
                    for (int i = 0; i < subNumeros3.length; i++) {
                        tercerGrupoCifras += subNumeros3[i];
                    }
                } else {
                    penultimaCifra++;
                    tercerGrupoCifras = penultimaCifra + "";
                }
            } else {
                ultimaCifra++;           
                if (ultimaCifra < 10) { //0001
                    cuartoGrupoCifras = "000" + ultimaCifra;
                } else if (ultimaCifra < 100) { //0010
                    cuartoGrupoCifras = "00" + ultimaCifra;
                } else if (ultimaCifra < 1000) { //0100
                    cuartoGrupoCifras = "0" + ultimaCifra;
                } else {
                    cuartoGrupoCifras = ultimaCifra + "";
                }
            }
            return primeroGrupoCifras + " " + segundoGrupoCifras + " " + tercerGrupoCifras + " " + cuartoGrupoCifras;
        }
    }

}
