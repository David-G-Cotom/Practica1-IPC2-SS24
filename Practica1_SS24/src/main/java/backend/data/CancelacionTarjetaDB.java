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
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class CancelacionTarjetaDB {
    
    private Connection connection = ConexionDB.getConnection();
    
    public Cancelacion getCancelacion(String numeroTarjeta) {
        //SELECT * FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente WHERE numero_tarjeta = '4256 3102 6546 1055';
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
        String query = "UPDATE tarjeta SET estado = " + false + " WHERE numero_tarjeta = '" + numeroTarjeta + "'";
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
