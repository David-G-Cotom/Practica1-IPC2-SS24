/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.model.Cliente;
import backend.model.SolicitudTarjeta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class SolicitudTarjetaDB {
    
    private SolicitudTarjeta solicitud;
    private Cliente cliente;
    private Connection connection = ConexionDB.getConnection();

    public SolicitudTarjetaDB(SolicitudTarjeta solicitud) {
        this.solicitud = solicitud;
        this.cliente = new Cliente(this.solicitud.getNombreSolicitante(), this.solicitud.getSalarioSolicitante(), this.solicitud.getDireccionSolicitante());
    }

    public SolicitudTarjetaDB() {
    }        
    
    public void crearSolicitud() {
        String query = "INSERT INTO solicitud (fecha_solicitud, tipo_tarjeta, id_cliente) VALUES ('" + this.solicitud.getFechaSolicitud() + "', " + this.getIdTipoTarjeta() + ", " + this.getIdCliente() + ")";
        try (Statement statementInsert = this.connection.createStatement()) {            
                statementInsert.executeUpdate(query);
            System.out.println("Registro de Solicitud Creada Exxitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear un registro de Solicitud en la BD " + e);
        }
    }
    
    public void crearCliente() {
        if (!this.isClienteExistente()) {
            String query = "INSERT INTO cliente (nombre, salario, direccion) VALUES ('" + this.cliente.getNombre() + "', " + this.cliente.getSalario() + ", '" + this.cliente.getDireccion() + "')";
            try (Statement statementInsert = this.connection.createStatement()) {            
                statementInsert.executeUpdate(query);
                System.out.println("Registro de Cliente Creado Exitosametne");
            } catch (SQLException ex) {
                System.out.println("Error al crea registro de Cliente en la BD");
            }            
        }
    }
    
    public ArrayList<Integer> getNumeroSolicitud() {
        String query = "SELECT numero_solicitud FROM solicitud";
        ArrayList<Integer> listaNumerosSolicitudes = new ArrayList<>();
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                int numeroSolicitud = resulConsulta.getInt("numero_solicitud");
                listaNumerosSolicitudes.add(numeroSolicitud);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los numeros de solicitudes para tarjetas en la BD");
        }
        return listaNumerosSolicitudes;
    }
    
    public boolean isClienteExistente() {
        String query = "SELECT * FROM cliente WHERE nombre = '" + this.cliente.getNombre() + "' AND salario = " + this.cliente.getSalario() + " AND direccion = '" + this.cliente.getDireccion() + "'";
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar la existencia de un cliente en la BD" + e);
        }
        return false;
    }
    
    public int getIdCliente() {
        String query = "SELECT * FROM cliente WHERE nombre = '" + this.cliente.getNombre() + "' AND salario = " + this.cliente.getSalario() + " AND direccion = '" + this.cliente.getDireccion() + "'";        
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                this.cliente.setIdCliente(resulConsulta.getInt("id_cliente"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el ID de un cliente en la BD");
        }
        return this.cliente.getIdCliente();
    }
    
    public int getIdTipoTarjeta() {
        String query = "SELECT * FROM tipo_tarjeta WHERE tipo = '" + this.solicitud.getTipoTarjetaSolicitada() + "'";
        int id_tipo_tarjeta = 0;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                id_tipo_tarjeta = resulConsulta.getInt("id_tipo");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el ID de un tipo de tarjeta en la BD");
        }
        return id_tipo_tarjeta;
    }
    
}
