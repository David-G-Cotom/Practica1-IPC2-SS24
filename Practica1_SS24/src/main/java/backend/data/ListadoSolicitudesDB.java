/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.enums.EstadosSolicitud;
import backend.model.ListadoSolicitudes;
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
public class ListadoSolicitudesDB {

    private Connection connection = ConexionDB.getConnection();

    /**
     * Metodo que obtiene de la Base de Datos los valores necesarios para
     * Instancias de Listado de Solicitudes y agregarlos al Arreglo de Retorno
     *
     * @param restoQuery es el resto de cadena para la Consulta
     * @return un Arreglo de Listado Solicitud por cada Solicitud que cumpla con
     * la Consulta
     */
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
                ListadoSolicitudes registroSolicitud = new ListadoSolicitudes(TipoTarjetas.valueOf(tipoSolicitud), numeroSolicitud, fechaCambioEstado, nombreCliente, salarioCliente, direccionCliente);
                switch (estadoSolicitud) {
                    case "1":
                        registroSolicitud.setEstadoSolicitud(EstadosSolicitud.APROBADA);
                        registroSolicitud.setHayEstadoSolicitud(true);
                        break;
                    case "0":
                        registroSolicitud.setEstadoSolicitud(EstadosSolicitud.RECHAZADA);
                        registroSolicitud.setHayEstadoSolicitud(true);
                        break;
                    default:
                        registroSolicitud.setHayEstadoSolicitud(false);
                        break;
                }
                listadoSolicitudes.add(registroSolicitud);
            }
        } catch (SQLException e) {
            System.out.println("Error al hacer la consulta para el Listado de Tarjetas en la BD " + e);
        }
        return listadoSolicitudes;
    }

}
