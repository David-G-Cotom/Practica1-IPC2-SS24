/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

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
                tarjetaCancelada = new Cancelacion(numero_tarjeta, estado, nombre, salario, direccion, saldo);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los numeros de Tarjetas en la BD");
        }        
        return tarjetaCancelada;
    }
    
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
