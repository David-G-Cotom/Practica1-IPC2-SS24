/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.model.ListadoSolicitudes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class ListadoSolicitudesDB {
    
    private Connection connection = ConexionDB.getConnection();
    
    public ArrayList<ListadoSolicitudes> getListadoSolicitudes(String restoQuery) {
        String query = "SELECT * FROM solicitud INNER JOIN tipo_tarjeta ON tipo_tarjeta = id_tipo INNER JOIN cliente ON solicitud.id_cliente = cliente.id_cliente" + restoQuery;
        System.out.println(query);
        ArrayList<ListadoSolicitudes> listadoSolicitudes = new ArrayList<>();
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {            
            while (resulConsulta.next()) {                
                String tipoSolicitud = resulConsulta.getString("tipo");
                String estadoSolicitud = resulConsulta.getString("estado");
                int numeroSolicitud = resulConsulta.getInt("numero_solicitud");
                String fechaCambioEstado = resulConsulta.getString("fecha_cambio_estado");
                String nombreCliente = resulConsulta.getString("nombre");
                double salarioCliente = resulConsulta.getDouble("salario");
                String direccionCliente = resulConsulta.getString("direccion");
                ListadoSolicitudes registroTarjeta = new ListadoSolicitudes(tipoSolicitud, estadoSolicitud, numeroSolicitud, fechaCambioEstado, nombreCliente, salarioCliente, direccionCliente);
                listadoSolicitudes.add(registroTarjeta);            
            }
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta para el Listado de Tarjetas en la BD " + e);
        }
        return listadoSolicitudes;
    }
    
}
