/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.enums.EstadosTarjeta;
import backend.model.ListadoTarjetas;
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
public class ListadoTarjetasDB {
    
    private Connection connection = ConexionDB.getConnection();
    
    public ArrayList<ListadoTarjetas> getListadoTarjetas(String restoQuery) {
        String query = "SELECT * FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON id_tipo = tipo_tarjeta" + restoQuery;
        System.out.println(query);
        ArrayList<ListadoTarjetas> listadoTarjetas = new ArrayList<>();
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {            
            while (resulConsulta.next()) {                
                String tipoTarjeta = resulConsulta.getString("tipo");
                boolean estadoTarjeta = resulConsulta.getBoolean("estado");
                String numeroTarjeta = resulConsulta.getString("numero_tarjeta");
                double limiteCredito = resulConsulta.getDouble("tarjeta.limite_credito");
                String nombreCliente = resulConsulta.getString("nombre");
                String direccionCliente = resulConsulta.getString("direccion");
                String fechaCambioEstado = resulConsulta.getString("fecha_cambio_estado");
                EstadosTarjeta estado;
                if (estadoTarjeta) {
                    estado = EstadosTarjeta.ACTIVA;
                } else {
                    estado = EstadosTarjeta.CANCELADA;
                }
                ListadoTarjetas registroTarjeta = new ListadoTarjetas(TipoTarjetas.valueOf(tipoTarjeta), estado, numeroTarjeta, limiteCredito, nombreCliente, direccionCliente, fechaCambioEstado);
                listadoTarjetas.add(registroTarjeta);            
            }
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta para el Listado de Tarjetas en la BD " + e);
        }
        return listadoTarjetas;
    }
    
}
