/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.model.Consulta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class ConsultaTarjetaDB {
    
    private Connection connection = ConexionDB.getConnection();
    
    public Consulta getConsulta(String numeroTarjeta) {        
        String query = "SELECT * FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON tarjeta.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_tarjeta = '" + numeroTarjeta + "'";
        Consulta tarjetaConsultada = null;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                String numero_tarjeta = resulConsulta.getString("numero_tarjeta");
                String tipo_tarjeta = resulConsulta.getString("tipo");
                double limite_credito = resulConsulta.getDouble("tarjeta.limite_credito");
                String nombre = resulConsulta.getString("nombre");
                String direccion = resulConsulta.getString("direccion");
                boolean estado = resulConsulta.getBoolean("estado");
                tarjetaConsultada = new Consulta(numero_tarjeta, tipo_tarjeta, limite_credito, nombre, direccion, estado);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los numeros de Tarjetas en la BD");
        }        
        return tarjetaConsultada;
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
