/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.model.Cliente;
import backend.model.SolicitudTarjeta;
import backend.model.Tarjeta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class AutorizacionTarjetaDB {
    
    private Cliente cliente;
    private SolicitudTarjeta solicitud;
    private int numeroSolicitud;
    private int limiteCreditoTipoTarjeta;
    private double interesTipoTarjeta;
    private int tipo_tarjeta;
    private Connection connection = ConexionDB.getConnection();

    public AutorizacionTarjetaDB(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
        this.contruirCliente();
        this.contruirSolicitud();
        this.getDatosTipoTarjeta();
    }

    public AutorizacionTarjetaDB() {
    }    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public SolicitudTarjeta getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudTarjeta solicitud) {
        this.solicitud = solicitud;
    }

    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public int getLimiteCreditoTipoTarjeta() {
        return limiteCreditoTipoTarjeta;
    }

    public void setLimiteCreditoTipoTarjeta(int limiteCreditoTipoTarjeta) {
        this.limiteCreditoTipoTarjeta = limiteCreditoTipoTarjeta;
    }

    public double getInteresTipoTarjeta() {
        return interesTipoTarjeta;
    }

    public void setInteresTipoTarjeta(double interesTipoTarjeta) {
        this.interesTipoTarjeta = interesTipoTarjeta;
    }        
    
    public void actualizarEstadoTarjeta(String numeroTarjeta) {
        
    }
    
    public void actualizarEstadoSolicitud(int numeroSolicitud, boolean autorizado) {
        String query = "UPDATE solicitud SET estado = " + autorizado + " WHERE numero_solicitud = " + numeroSolicitud;
        try (Statement statementInsert = this.connection.createStatement()) {
            statementInsert.execute(query);
            System.out.println("Cambio de Estado de la Solicitud realizada con Exito");
        } catch (Exception e) {
            System.out.println("Error en la Actualizacion de Estado de la Solicitud");
        }
    }
    
    public boolean isSolicitudAutorizada(int numeroSolicitud) {
        String query = "SELECT estado FROM solicitud WHERE numero_solicitud = " + numeroSolicitud;
        boolean estado = false;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                estado = resulConsulta.getBoolean("estado");
            }
            System.out.println("Obtencion de Estado de la Solicitud realizada con Exito");
        } catch (Exception e) {
            System.out.println("Error al Obtener el Estado de la Solicitud");
        }
        return estado;
    }
    
    public void crearTarjeta(Tarjeta tarjeta) {
        String query = "INSERT INTO tarjeta (numero_tarjeta, numero_solicitud, estado, saldo, tipo_tarjeta, limite_credito, id_cliente) VALUES ('" + tarjeta.getNumeroTarjeta() + "', " + this.numeroSolicitud + ", " + tarjeta.isEstado() + ", " + tarjeta.getSaldo() + ", " + this.tipo_tarjeta + ", " + tarjeta.getLimiteCredito() + ", " + this.cliente.getIdCliente() + ")";
        try (Statement statementInsert = this.connection.createStatement()) {            
                statementInsert.executeUpdate(query);
            System.out.println("Registro de Tarjeta Creada Exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear un registro de Tarjeta en la BD " + e);
        }
    
    }
    
    private void contruirCliente() {
        //SELECT * FROM cliente INNER JOIN solicitud ON cliente.id_cliente = solicitud.id_cliente INNER JOIN tipo_tarjeta ON solicitud.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_solicitud = 6;
        String query = "SELECT * FROM cliente INNER JOIN solicitud ON cliente.id_cliente = solicitud.id_cliente INNER JOIN tipo_tarjeta ON solicitud.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_solicitud = " + this.numeroSolicitud;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                int id_cliente = resulConsulta.getInt("id_cliente");
                String nombre = resulConsulta.getString("nombre");
                double salario = resulConsulta.getDouble("salario");
                String direccion = resulConsulta.getString("direccion");
                this.cliente = new Cliente(id_cliente, nombre, salario, direccion);
            }
        } catch (SQLException e) {
            System.out.println("Error al construir un Cliente para la Autorizacion");
        }
    }
    
    private void contruirSolicitud() {
        //SELECT * FROM cliente INNER JOIN solicitud ON cliente.id_cliente = solicitud.id_cliente INNER JOIN tipo_tarjeta ON solicitud.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_solicitud = 6;
        String query = "SELECT * FROM cliente INNER JOIN solicitud ON cliente.id_cliente = solicitud.id_cliente INNER JOIN tipo_tarjeta ON solicitud.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_solicitud = " + this.numeroSolicitud;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                int numero_solicitud = resulConsulta.getInt("numero_solicitud");
                String fecha_solicitud = resulConsulta.getString("fecha_solicitud");
                String tipo_tarjeta = resulConsulta.getString("tipo");
                this.solicitud = new SolicitudTarjeta(numero_solicitud, fecha_solicitud, tipo_tarjeta, this.cliente.getNombre(), this.cliente.getSalario(), this.cliente.getDireccion());
            }
        } catch (SQLException e) {
            System.out.println("Error al construir una Solicitud para la Autorizacion");
        }
    }
    
    private void getDatosTipoTarjeta() {
        //SELECT * FROM cliente INNER JOIN solicitud ON cliente.id_cliente = solicitud.id_cliente INNER JOIN tipo_tarjeta ON solicitud.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_solicitud = 6;
        String query = "SELECT * FROM cliente INNER JOIN solicitud ON cliente.id_cliente = solicitud.id_cliente INNER JOIN tipo_tarjeta ON solicitud.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_solicitud = " + this.numeroSolicitud;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                this.limiteCreditoTipoTarjeta = resulConsulta.getInt("limite_credito");
                this.interesTipoTarjeta = resulConsulta.getDouble("interes");
                this.tipo_tarjeta = resulConsulta.getInt("id_tipo");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos para el Tipo de Tarjeta");
        }
    }
    
    public ArrayList<String> getNumeroTarjetas(String numeroClave) {
        String query = "SELECT numero_tarjeta FROM tarjeta WHERE numero_tarjeta LIKE '" + numeroClave + "%'";
        ArrayList<String> listaNumerosTarjetas = new ArrayList<>();
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                String numeroTarjeta = resulConsulta.getString("numero_tarjeta");
                listaNumerosTarjetas.add(numeroTarjeta);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los numeros de Tarjetas en la BD");
        }
        return listaNumerosTarjetas;
    }
    
}
