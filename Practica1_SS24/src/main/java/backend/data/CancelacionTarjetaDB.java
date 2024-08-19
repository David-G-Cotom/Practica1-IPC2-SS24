/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.enums.EstadosTarjeta;
import backend.model.Cancelacion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Carlos Cotom
 */
public class CancelacionTarjetaDB {

    private Connection connection = ConexionDB.getConnection();

    /**
     * Metodo que obtiene de la Base de Datos los valores para Instanciar una
     * Cancelacion referente al Numero de Tarjeta recibido de parametro que se
     * quiere cancelar
     *
     * @param numeroTarjeta es el Numero de Tarjeta que se quiere Cancelar
     * @return una Instancia de Cancelacion
     */
    public Cancelacion getCancelacion(String numeroTarjeta) {
        String query = "SELECT * FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente WHERE numero_tarjeta = '" + numeroTarjeta + "'";
        Cancelacion tarjetaCancelada = null;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                String numero_tarjeta = resulConsulta.getString("numero_tarjeta");
                boolean estado = resulConsulta.getBoolean("estado");
                String nombre = resulConsulta.getString("nombre");
                String salario = resulConsulta.getString("salario");
                String direccion = resulConsulta.getString("direccion");
                double saldo = resulConsulta.getDouble("saldo");
                EstadosTarjeta estadoTarjeta;
                if (estado) {
                    estadoTarjeta = EstadosTarjeta.ACTIVA;
                } else {
                    estadoTarjeta = EstadosTarjeta.CANCELADA;
                }
                tarjetaCancelada = new Cancelacion(numero_tarjeta, estadoTarjeta, nombre, salario, direccion, saldo);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los numeros de Tarjetas en la BD");
        }
        return tarjetaCancelada;
    }

    /**
     * Metodo que actualiza en la Base de Datos el valor del Estado y la Fecha
     * en que se cambio el Estado de la Tarjeta que cumpla con el Numero de
     * Tarjeta ingresado como parametro
     *
     * @param numeroTarjeta es el Numero de Tarjeta a la que se quiere
     * Actualizar el Estado
     */
    public void actualizarEstadoTarjeta(String numeroTarjeta) {
        Date fechaSistema = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = formatoFecha.format(fechaSistema);
        String query = "UPDATE tarjeta SET estado = " + false + ", fecha_cambio_estado = '" + fechaActual + "' WHERE numero_tarjeta = '" + numeroTarjeta + "'";
        try (Statement statementInsert = this.connection.createStatement()) {
            statementInsert.execute(query);
            System.out.println("Cambio de Estado de la Tarjeta realizada con Exito");
        } catch (SQLException e) {
            System.out.println("Error en la Actualizacion de Estado de la Tarjeta" + e);
        }
    }

    /**
     * Metodo que obtiene de la Base de Datos todos los Numeros de Tarjetas que
     * estan Registradas en el Sistema
     *
     * @return un Arreglo de String que contiene los Numeros de Tarjetas
     * Registradas
     */
    public ArrayList<String> getNumeroTarjetas() {
        String query = "SELECT numero_tarjeta FROM tarjeta";
        ArrayList<String> listaNumerosTarjetas = new ArrayList<>();
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                String registro = resulConsulta.getString("numero_tarjeta");
                listaNumerosTarjetas.add(registro);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los numeros de Tarjetas en la BD");
        }
        return listaNumerosTarjetas;
    }

}
