/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.enums.TipoTarjetas;
import backend.data.AutorizacionTarjetaDB;
import backend.enums.EstadosTarjeta;
import java.util.ArrayList;

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
    private TipoTarjetas tipoTarjeta;
    private String numeroTarjeta;
    private ArrayList<String> numerosRegistrados;
    private Tarjeta tarjeta;

    public Autorizacion(double salarioCliente, double limiteCreditoTarjeta, TipoTarjetas tipoTarjeta) {
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

    public TipoTarjetas getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjetas tipoTarjeta) {
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

    public boolean exitoEnAutorizarTarjeta() {
        if (this.isLimiteCreditoAprobado()) {
            this.crearNumeroTarjeta();
            this.tarjeta = new Tarjeta(this.numeroTarjeta, this.tipoTarjeta, EstadosTarjeta.AUTORIZADA, 0, (this.salarioCliente * this.PORCENTAJE_BANCARIO), 0);
            System.out.println("Autorizacion Aceptada");
            return true;
        }
        System.out.println("Autorizacion Rechazada por Limite de Credito");
        return false;
    }

    private void crearNumeroTarjeta() {
        switch (this.tipoTarjeta) {
            case TipoTarjetas.NACIONAL:
                this.numeroTarjeta = this.crearNumero(this.NUMERO_TARJETA_NACIONAL);
                break;
            case TipoTarjetas.REGIONAL:
                this.numeroTarjeta = this.crearNumero(this.NUMERO_TARJETA_REGIONAL);
                break;
            case TipoTarjetas.INTERNACIONAL:
                this.numeroTarjeta = this.crearNumero(this.NUMERO_TARJETA_INTERNACIONAL);
                break;
            default:
                System.out.println("Tipo de Tarjeta NO encontrada");
        }
    }

    private String crearNumero(String numero) {
        AutorizacionTarjetaDB datos = new AutorizacionTarjetaDB();
        this.numerosRegistrados = datos.getNumeroTarjetas(numero);
        if (this.numerosRegistrados.isEmpty()) {
            numero += "0 0000";            
            return numero;
        } else {
            String ultimoNumeroRegistrado = this.numerosRegistrados.getLast();
            String[] numerosEnRegistro = ultimoNumeroRegistrado.split(" ");
            String tercerGrupoCifras = numerosEnRegistro[2];
            String cuartoGrupoCifras = numerosEnRegistro[3];
            String[] numerosGrupo3 = tercerGrupoCifras.split("");
            int penultimaCifra = Integer.parseInt(tercerGrupoCifras);
            int ultimaCifra = Integer.parseInt(cuartoGrupoCifras);
            if (ultimaCifra == 9999) {
                cuartoGrupoCifras = "0000";
                if (numerosGrupo3[3].equals("9")) {
                    numerosGrupo3[3] = "0";
                    tercerGrupoCifras = "";
                    for (int i = 0; i < numerosGrupo3.length; i++) {
                        tercerGrupoCifras += numerosGrupo3[i];
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
            return numerosEnRegistro[0] + " " + numerosEnRegistro[1] + " " + tercerGrupoCifras + " " + cuartoGrupoCifras;
        }
    }

}
