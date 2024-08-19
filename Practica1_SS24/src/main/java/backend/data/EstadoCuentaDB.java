/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.model.EstadoCuenta;
import backend.model.MovimientoTarjeta;
import backend.enums.TipoMovimientos;
import backend.enums.TipoTarjetas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class EstadoCuentaDB {

    private Connection connection = ConexionDB.getConnection();

    /**
     * Metodo que obtiene de la Base de Datos los valores necesarios para
     * Instancias de Estados de Cuenta y agregarlos al Arreglo de Retorno
     *
     * @param restoQuery es el resto de cadena para la Consulta
     * @return un Arreglo de Estado Cuenta por cada Tarjeta que cumpla con la
     * Consulta
     */
    public ArrayList<EstadoCuenta> getEstadosCuenta(String restoQuery) {
        String query = "SELECT * FROM tarjeta INNER JOIN tipo_tarjeta ON tipo_tarjeta = id_tipo INNER JOIN cliente ON tarjeta.id_cliente = cliente.id_cliente INNER JOIN movimiento ON tarjeta.numero_tarjeta = movimiento.numero_tarjeta  WHERE estado = true" + restoQuery;
        System.out.println(query);
        String numeroTarjetaAnterior = "";
        ArrayList<EstadoCuenta> estadosCuenta = new ArrayList<>();
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                String numeroTarjeta = resulConsulta.getString("tarjeta.numero_tarjeta");
                if (numeroTarjetaAnterior.equals(numeroTarjeta)) {
                    continue;
                }
                numeroTarjetaAnterior = numeroTarjeta;
                String tipoTarjeta = resulConsulta.getString("tipo");
                double interesTipoTarjeta = resulConsulta.getDouble("interes");
                String nombreClietne = resulConsulta.getString("nombre");
                String direccionCliente = resulConsulta.getString("direccion");
                ArrayList<MovimientoTarjeta> movimientos = getMovimienotsTarjeta(numeroTarjeta);
                EstadoCuenta registroCuenta = new EstadoCuenta(numeroTarjeta, TipoTarjetas.valueOf(tipoTarjeta), nombreClietne, direccionCliente, movimientos, interesTipoTarjeta);
                estadosCuenta.add(registroCuenta);
            }
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta para el Estado de Cuenta en la BD " + e);
        }
        return estadosCuenta;
    }

    /**
     * Metodo que obtiene de la Base de Datos los valores necesarios para
     * Instanciar Movimientos de Tarjeta para la que tiene el Numero de Tarjeta
     * ingresado como parametro y agregarlos al Arreglo de Retorno
     *
     * @param numeroTarjeta es el Numero de la Tarjeta a la que se le quiere
     * obtener los Mivimientos
     * @return un Arreglo de Movimiento para el Numero de Tarjeta recibido
     */
    public ArrayList<MovimientoTarjeta> getMovimienotsTarjeta(String numeroTarjeta) {
        String query = "SELECT * FROM movimiento WHERE numero_tarjeta = '" + numeroTarjeta + "'";
        ArrayList<MovimientoTarjeta> movimientos = new ArrayList<>();
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                String fechaMovimiento = resulConsulta.getString("fecha_movimiento");
                String tipoMovimiento = resulConsulta.getString("tipo_movimiento");
                String descripcion = resulConsulta.getString("descripcion");
                String establecimiento = resulConsulta.getString("establecimiento");
                double montoEjecutado = resulConsulta.getDouble("monto");
                MovimientoTarjeta registroCuenta = new MovimientoTarjeta(numeroTarjeta, fechaMovimiento, TipoMovimientos.valueOf(tipoMovimiento), descripcion, establecimiento, montoEjecutado);
                movimientos.add(registroCuenta);
            }
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta de Movimientos para el Estado de Cuenta en la BD " + e);
        }
        return movimientos;
    }

}
