/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import backend.model.Cliente;
import backend.model.SolicitudTarjeta;
import backend.model.Tarjeta;
import backend.enums.TipoTarjetas;
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
public class AutorizacionTarjetaDB {

    private Cliente cliente;
    private SolicitudTarjeta solicitud;
    private int numeroSolicitud;
    private int limiteCreditoTipoTarjeta;
    private double interesTipoTarjeta;
    private int tipoTarjeta;
    private Connection connection = ConexionDB.getConnection();

    //---------------------------------------- CONSTRUCTORES ----------------------------------------//
    public AutorizacionTarjetaDB(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
        this.contruirCliente();
        this.contruirSolicitud();
        this.getDatosTipoTarjeta();
    }

    public AutorizacionTarjetaDB() {
    }

    //---------------------------------------- GETERS AND SETERS ----------------------------------------//
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

    //---------------------------------------- METODOS PROPIOS ----------------------------------------//
    /**
     * Metodo que actualiza en la Base de Datos el valor del Estado y la Fecha
     * en que se cambio el Estado de la Solicitud que cumpla con el numero de
     * Solicitud ingresado como parametro
     *
     * @param numeroSolicitud es el Numero de la Solicitud a la que se le quiere
     * Actualizar el Estado
     * @param autorizado es el nuevo valor de Estado que tendra la Solicitud
     */
    public void actualizarEstadoSolicitud(int numeroSolicitud, boolean autorizado) {
        Date fechaSistema = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaAcctual = formatoFecha.format(fechaSistema);
        String query = "UPDATE solicitud SET estado = " + autorizado + ", fecha_cambio_estado = '" + fechaAcctual + "' WHERE numero_solicitud = " + numeroSolicitud;
        try (Statement statementInsert = this.connection.createStatement()) {
            statementInsert.execute(query);
            System.out.println("Cambio de Estado de la Solicitud realizada con Exito");
        } catch (SQLException e) {
            System.out.println("Error en la Actualizacion de Estado de la Solicitud");
        }
    }

    /**
     * Metodo que obtiene de la Base de Datos el Estado de la Solicitud que
     * cumpla con el numero ingresado como parametro
     *
     * @param numeroSolicitud es el numero de la Solicitud a la que se le quiere
     * Actualizar el Estado
     * @return verdadero si la Solicitud ya estaba Autorizada, de los contrario
     * retorna falso
     */
    public boolean isSolicitudAutorizada(int numeroSolicitud) {
        String query = "SELECT estado FROM solicitud WHERE numero_solicitud = " + numeroSolicitud;
        boolean estado = false;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                estado = resulConsulta.getBoolean("estado");
            }
            System.out.println("Obtencion de Estado de la Solicitud realizada con Exito");
        } catch (SQLException e) {
            System.out.println("Error al Obtener el Estado de la Solicitud");
        }
        return estado;
    }

    /**
     * Metodo que Inserta un nuevo Registro de Tipo Tarjeta en la Base de Datos
     *
     * @param tarjeta es el Objeto Tarjeta que se Creara en la Base de Datos
     */
    public void crearTarjeta(Tarjeta tarjeta) {
        Date fechaSistema = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = formatoFecha.format(fechaSistema);
        boolean estado = !tarjeta.isEstado().toString().equals("CANCELADA");
        String query = "INSERT INTO tarjeta (numero_tarjeta, numero_solicitud, estado, saldo, tipo_tarjeta, fecha_cambio_estado, limite_credito, id_cliente) VALUES ('" + tarjeta.getNumeroTarjeta() + "', " + this.numeroSolicitud + ", " + estado + ", " + tarjeta.getSaldo() + ", " + this.tipoTarjeta + ", '" + fechaActual + "', " + tarjeta.getLimiteCredito() + ", " + this.cliente.getIdCliente() + ")";
        try (Statement statementInsert = this.connection.createStatement()) {
            statementInsert.executeUpdate(query);
            System.out.println("Registro de Tarjeta Creada Exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al crear un registro de Tarjeta en la BD " + e);
        }

    }

    /**
     * Metodo que obtiene de la Base de Datos los valores necesarios para poder
     * Instanciar un Objeto del Tipo Cliente
     */
    private void contruirCliente() {
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
            System.out.println("Error al construir un Cliente para la Autorizacion " + e);
        }
    }

    /**
     * Metodo que obtiene de la Base de Datos los valores necesarios para poder
     * Instanciar un Objeto del Tipo Solicitud
     */
    private void contruirSolicitud() {
        String query = "SELECT * FROM cliente INNER JOIN solicitud ON cliente.id_cliente = solicitud.id_cliente INNER JOIN tipo_tarjeta ON solicitud.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_solicitud = " + this.numeroSolicitud;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                int numero_solicitud = resulConsulta.getInt("numero_solicitud");
                String fecha_solicitud = resulConsulta.getString("fecha_solicitud");
                String tipo_tarjeta = resulConsulta.getString("tipo");
                this.solicitud = new SolicitudTarjeta(numero_solicitud, fecha_solicitud, TipoTarjetas.valueOf(tipo_tarjeta), this.cliente.getNombre(), this.cliente.getSalario(), this.cliente.getDireccion());
            }
        } catch (SQLException e) {
            System.out.println("Error al construir una Solicitud para la Autorizacion " + e);
        }
    }

    /**
     * Metodo que obtiene de la Base de Datos los valores del Limite de Credito,
     * Intereses y el Tipo de Tarjeta en base al numero de Solicitud que se
     * tiene
     */
    private void getDatosTipoTarjeta() {
        String query = "SELECT * FROM cliente INNER JOIN solicitud ON cliente.id_cliente = solicitud.id_cliente INNER JOIN tipo_tarjeta ON solicitud.tipo_tarjeta = tipo_tarjeta.id_tipo WHERE numero_solicitud = " + this.numeroSolicitud;
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                this.limiteCreditoTipoTarjeta = resulConsulta.getInt("limite_credito");
                this.interesTipoTarjeta = resulConsulta.getDouble("interes");
                this.tipoTarjeta = resulConsulta.getInt("id_tipo");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener datos para el Tipo de Tarjeta en Autorizacion " + e);
        }
    }

    /**
     * Metodo que obtienen de la Base de Datos todos los Numeros de Tarjetas que
     * cumplan con la consicion del Numero Clave ingresado como parametro
     *
     * @param numeroClave es el numero clave de Tarjeta que identifica a cada
     * Tipo de Tarjeta
     * @return un Arreglo de String que contiene todos los numeros de Tarjetas
     * obtenidos de la consulta
     */
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
            System.out.println("Error al consultar todos los numeros de Tarjetas en la BD para Autorizacion " + e);
        }
        return listaNumerosTarjetas;
    }

    /**
     * Metodo que obtiene de la Base de Datos el Numero de Tarjeta que se Asocia
     * con el Numero de Solicitud ingresado como parametro
     *
     * @param numeroSOlicitud es el numero de la Solicitud asociada a la Tarjeta
     * @return el numero de Tarjeta
     */
    public String getNumeroTarjeta(int numeroSOlicitud) {
        String query = "SELECT numero_tarjeta FROM tarjeta WHERE numero_solicitud = " + numeroSOlicitud;
        String numeroTarjeta = "";
        try (Statement statementConsulta = this.connection.createStatement();
                ResultSet resulConsulta = statementConsulta.executeQuery(query)) {
            while (resulConsulta.next()) {
                numeroTarjeta = resulConsulta.getString("numero_tarjeta");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los numeros de Tarjetas en la BD");
        }
        return numeroTarjeta;
    }

}
