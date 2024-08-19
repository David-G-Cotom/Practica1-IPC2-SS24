/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.model.MovimientoTarjeta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class MovimientoTarjetaDB {

    private int saldoTarjeta;
    private MovimientoTarjeta movimiento;
    private Connection connection = ConexionDB.getConnection();

    //---------------------------------------- CONSTRUCTORES ----------------------------------------//
    public MovimientoTarjetaDB(MovimientoTarjeta movimiento) {
        this.movimiento = movimiento;
    }

    public MovimientoTarjetaDB() {
    }

    //---------------------------------------- METODOS PROPIOS ----------------------------------------//
    /**
     * Metodo que Inserta un nuevo Registro de Tipo Movimiento en la Base de
     * Datos
     */
    public void crearRegistro() {
        String query = "INSERT INTO movimiento (fecha_movimiento, numero_tarjeta, descripcion, establecimiento, monto, tipo_movimiento) VALUES ('" + movimiento.getFechaOperacion() + "', '" + movimiento.getNumeroTarjeta() + "', '" + movimiento.getDescripcion() + "', '" + movimiento.getEstablecimiento() + "', " + movimiento.getMontoTransferido() + ", '" + movimiento.getTipoMovimiento() + "')";
        try (Statement statementInsert = this.connection.createStatement()) {
            statementInsert.executeUpdate(query);
            System.out.println("Registro de Movimiento Creada Exxitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear un registro de Movimiento en la BD " + e);
        }
    }

    /**
     * Metodo que Actualiza el Saldo de la Tarjeta en base al Numero de Tarjeta
     * que se tiene
     */
    public void actualizarSaldoTarjeta() {
        if ("ABONO".equals(this.movimiento.getTipoMovimiento().toString())) {
            this.saldoTarjeta -= this.movimiento.getMontoTransferido();
        } else if ("CARGO".equals(this.movimiento.getTipoMovimiento().toString())) {
            this.saldoTarjeta += this.movimiento.getMontoTransferido();
        }
        String query = "UPDATE tarjeta SET saldo = " + this.saldoTarjeta + " WHERE numero_tarjeta = '" + this.movimiento.getNumeroTarjeta() + "'";
        try (Statement statementInsert = this.connection.createStatement()) {
            statementInsert.execute(query);
            System.out.println("Cambio de Saldo de la Tarjeta realizada con Exito");
        } catch (Exception e) {
            System.out.println("Error en la Actualizacion de Saldo de la Tarjeta");
        }
    }

    /**
     * Metodo que obtiene de la base de datos el saldo y el limite de credito de
     * la Tarjeta, y evalua qu la Cantidad que se Ejecuto en el Movimiento junto
     * con el saldo actual no sobrepase el limite de la Tarjeta
     *
     * @return verdadero si la Cantidad Ejecutada en el Movimiento no sobrepasa
     * el Limite de Credito, de lo contrario retorna falso
     */
    public boolean isMovimientoEnLimite() {
        String query = "SELECT saldo, limite_credito FROM tarjeta WHERE numero_tarjeta = '" + this.movimiento.getNumeroTarjeta() + "'";
        double limiteCredito = 0;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                this.saldoTarjeta = resulConsulta.getInt("saldo");
                limiteCredito = resulConsulta.getDouble("limite_credito");
            }
            System.out.println("Obtencion de Saldo de la Tarjeta realizada con Exito");
        } catch (SQLException e) {
            System.out.println("Error al Obtener Saldo e Interes de la Tarjeta en Movimiento " + e);
        }
        if (this.saldoTarjeta >= 0) {
            if (this.movimiento.getTipoMovimiento().toString().equals("CARGO")) {
                return (this.saldoTarjeta + this.movimiento.getMontoTransferido()) <= limiteCredito;                
            } else {
                return (this.saldoTarjeta - this.movimiento.getMontoTransferido()) >= (limiteCredito * (-1));
            }
        } else {
            if (this.movimiento.getTipoMovimiento().toString().equals("ABONO")) {
                return (this.saldoTarjeta - this.movimiento.getMontoTransferido()) >= (limiteCredito * (-1));
            } else {
                return (this.saldoTarjeta + this.movimiento.getMontoTransferido()) <= limiteCredito;
            }            
        }
    }

    /**
     * Metodo que obtiene de la Base de Datos el Estado de la Tarjeta que cumpla
     * con el Numero de Tarjeta que se tiene
     *
     * @return verdadero si la Tarjeta esta Activa de los contrario retorna
     * falso
     */
    public boolean isTarjetaActiva() {
        String query = "SELECT estado FROM tarjeta WHERE numero_tarjeta = '" + this.movimiento.getNumeroTarjeta() + "'";
        boolean estado = false;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                estado = resulConsulta.getBoolean("estado");
            }
            System.out.println("Obtencion de Estado de la Tarjeta realizada con Exito");
        } catch (SQLException e) {
            System.out.println("Error al Obtener el Estado de la Tarjeta en Movimiento " + e);
        }
        return estado;
    }

    /**
     * Metodo que obtiene de la Base de Datos todos los Numeros de Tarjetas que
     * estan Registradas en el Sistema
     *
     * @return un Arreglo de String que contiene los Numeros de Tarjetas
     * Registradas
     */
    public ArrayList<String> getNumerosTarjeta() {
        String query = "SELECT numero_tarjeta FROM tarjeta";
        ArrayList<String> listaNumerosTarjetas = new ArrayList<>();
        try (Statement statementConsulta = this.connection.createStatement(); ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                String numeroTarjeta = resulConsulta.getString("numero_tarjeta");
                listaNumerosTarjetas.add(numeroTarjeta);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los Numeros de Tarjetas en la BD");
        }
        return listaNumerosTarjetas;
    }

}
