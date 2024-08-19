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

    //---------------------------------------- CONSTRUCTORES ----------------------------------------//
    public SolicitudTarjetaDB(SolicitudTarjeta solicitud) {
        this.solicitud = solicitud;
        this.cliente = new Cliente(this.solicitud.getNombreSolicitante(), this.solicitud.getSalarioSolicitante(), this.solicitud.getDireccionSolicitante());
    }

    public SolicitudTarjetaDB() {
    }

    //---------------------------------------- METODOS PROPIOS ----------------------------------------//
    /**
     * Metodo que Inserta un nuevo Registro de Tipo Solicitud en la Base de
     * Datos
     */
    public void crearSolicitud() {
        String query = "INSERT INTO solicitud (numero_solicitud, fecha_solicitud, tipo_tarjeta, id_cliente) VALUES (" + this.solicitud.getNumeroSolicitud() + ", '" + this.solicitud.getFechaSolicitud() + "', " + this.getIdTipoTarjeta() + ", " + this.getIdCliente() + ")";
        try (Statement statementInsert = this.connection.createStatement()) {
            statementInsert.executeUpdate(query);
            System.out.println("Registro de Solicitud Creada Exxitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear un registro de Solicitud en la BD " + e);
        }
    }

    /**
     * Metodo que Inserta un nuevo Registro de Tipo Cliente en la Base de Datos
     */
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

    /**
     * Metodo que obtiene de la Base de Datos todos los Numeros de Solicitudes
     * que estan Registradas en el Sistema
     *
     * @return un Arreglo de Integer que contiene los Numeros de Solicitudes
     * Registradas
     */
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

    /**
     * Metodo que obtiene de la Base de Datos el Cliente que cumpla con el
     * Nombre, Salario y Direccion que se tiene
     *
     * @return verdadero si se encuentra un Cliente que cumpla las condiciones,
     * de los contariro se retorna falso
     */
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

    /**
     * Metodo que obtiene de la Base de Datos el ID del Clietne que cumpla con
     * el Nombre, Salario y Direccion que se tiene
     *
     * @return el numero de ID del Cliente que cumpla las condiciones
     */
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

    /**
     * Metodo que obtiene de la Base de Datos el ID del Tipo de Tarjeta que
     * cumpla con el Tipo de Tarjeta Solicitada
     *
     * @return el numero de ID del Tipo de Tarjeta que cumpla las condiciones
     */
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
