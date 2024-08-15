/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Carlos Cotom
 */
public class ListadoSolicitudes {
    
    private String fechaInicio;
    private String fechaFin;
    private String tipoTarjeta;
    private double salarioMinimo;
    private String estadoSolicitud;
    private ArrayList<ListadoSolicitudes> datosSolicitudes;
    private int numeroSolicitud;
    private String fechaCambioEstado;    
    private String nombreCliente;
    private double salarioCliente;
    private String direccionCliente;
    private File file;

    public ListadoSolicitudes(String fechaInicio, String fechaFin, String tipoTarjeta, double salarioMinimo, String estadoSolicitud) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoTarjeta = tipoTarjeta;
        this.salarioMinimo = salarioMinimo;
        this.estadoSolicitud = estadoSolicitud;
    }

    public ListadoSolicitudes(String tipoTarjeta, String estadoSolicitud, int numeroSolicitud, String fechaCambioEstado, String nombreCliente, double salarioCliente, String direccionClietne) {
        this.tipoTarjeta = tipoTarjeta;
        this.estadoSolicitud = estadoSolicitud;
        this.numeroSolicitud = numeroSolicitud;
        this.fechaCambioEstado = fechaCambioEstado;
        this.nombreCliente = nombreCliente;
        this.salarioCliente = salarioCliente;
        this.direccionCliente = direccionClietne;
    }        

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getSalarioMinimo() {
        return salarioMinimo;
    }

    public void setSalarioMinimo(int salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public ArrayList<ListadoSolicitudes> getDatosSolicitudes() {
        return datosSolicitudes;
    }

    public void setDatosSolicitudes(ArrayList<ListadoSolicitudes> datosSolicitudes) {
        this.datosSolicitudes = datosSolicitudes;
    }

    public int getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(int numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getFechaCambioEstado() {
        return fechaCambioEstado;
    }

    public void setFechaCambioEstado(String fechaCambioEstado) {
        this.fechaCambioEstado = fechaCambioEstado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getSalarioCliente() {
        return salarioCliente;
    }

    public void setSalarioCliente(double salarioCliente) {
        this.salarioCliente = salarioCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String filtrarDatos() {
        String query = "";
        boolean hayPrimerCondicion = false;
        if (!this.fechaInicio.equals("")) {
            query += " WHERE DATEDIFF(day, '" + this.fechaInicio + "', (SELECT fecha_cambio_estado FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON id_tipo = tipo_tarjeta" + query + ")) >= 0";
            hayPrimerCondicion = true;
        }
        if (!this.fechaFin.equals("")) {
            if (!hayPrimerCondicion) {
                query += " WHERE DATEDIFF(day, (SELECT fecha_cambio_estado FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON id_tipo = tipo_tarjeta" + query + "), '" + this.fechaFin + "') >= 0";
                hayPrimerCondicion = true;
            } else {
                query += " AND DATEDIFF(day, (SELECT fecha_cambio_estado FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON id_tipo = tipo_tarjeta" + query + "), '" + this.fechaFin + "') >= 0";
            }
        }        
        if (!this.tipoTarjeta.equals("")) {
            if (!hayPrimerCondicion) {
                query += " WHERE tipo = '" + this.tipoTarjeta + "'";
                hayPrimerCondicion = true;
            } else {
                query += " AND tipo = '" + this.tipoTarjeta + "'";
            }            
        }        
        if (!(this.salarioMinimo < 0)) {
            if (!hayPrimerCondicion) {
                query += " WHERE salario > " + this.salarioMinimo;
                hayPrimerCondicion = true;
            } else {
                query += " AND salario > " + this.salarioMinimo;
            }
        }
        if (!this.estadoSolicitud.equals("")) {
            boolean estado;
            if (this.estadoSolicitud.equals("AUTORIZADA") || this.estadoSolicitud.equals("APROBADA")) {
                estado = true;
            } else {
                estado = false;
            }
            if (!hayPrimerCondicion) {
                query += " WHERE estado = " + estado;
            } else {
                query += " AND estado = " + estado;
            }
        }
        return query;
    }

    /**
     * Metodo para exportar todos los reportes de la aplicacion a un formato
     * HTML para su posterior visualizacion en la WEB
     *
     * @param pathCarpeta
     */
    public void exportarReportes(String pathCarpeta) {
        String data = generarArchivo(pathCarpeta);
        data = generarContenido(data);
        generarHTML(data);
    }

    /**
     * Metodo que verifica si existe el archivo HTML para poder establecer la
     * cabecer que tendra el mismo
     *
     * @return la cabecera que tendra el archivo HTML
     */
    private String generarArchivo(String pathCarpeta) {
        File carpeta = new File(pathCarpeta);
        File[] contenidoCarpeta = carpeta.listFiles();
        if (contenidoCarpeta.length > 0) {
            int numeroReporte = 0;
            String nombreArchivo = "Reporte de Listado de Solicitudes " + numeroReporte + ".html";
            for (int i = 0; i < contenidoCarpeta.length; i++) {
                if (contenidoCarpeta[i].getName().equals(nombreArchivo)) {
                    numeroReporte++;
                    nombreArchivo = "Reporte de Listado de Solicitudes " + numeroReporte + ".html";
                    i = 0;
                }
            }
            this.file = new File(pathCarpeta + "\\" + nombreArchivo);
        } else {
            this.file = new File(pathCarpeta + "\\Reporte de Listado de Solicitudes 0.html");
        }
        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            return """
                   <html>                   
                   <head>
                   	<title>Reporte de el Listado de Solicitudes en el Sistema</title>
                   </head>
                   <style>
                        table, th, td {
                            border:1px solid black;
                            border-collapse: collapse;
                        }
                        th, td {
                            padding: 10px;
                        }
                   </style>
                   <body>
                   """;
        }
        return null;
    }

    private String generarContenido(String data) {
        data += """                
                <h3>Listado de Solicitudes Registradas en el Sistema</h3>
                <table>
                    <tr>
                        <th>Numero Solicitud</th>
                        <th>Fecha Cambio de Estado</th>
                        <th>Tipo</th>
                        <th>Nombre Cliente</th>
                        <th>Salario Cliente</th>
                        <th>Direccion Cliente</th>
                        <th>Estado</th>
                    </tr>
                """;        
        for (int i = 0; i < this.datosSolicitudes.size(); i++) {
            data += "\n<tr>"
                + "\n<td>" + datosSolicitudes.get(i).getNumeroSolicitud()+ "</td>"
                + "\n<td>" + datosSolicitudes.get(i).getFechaCambioEstado() + "</td>"
                + "\n<td>" + datosSolicitudes.get(i).getTipoTarjeta() + "</td>"
                + "\n<td>" + datosSolicitudes.get(i).getNombreCliente() + "</td>"
                + "\n<td>" + datosSolicitudes.get(i).getSalarioCliente()+ "</td>"
                + "\n<td>" + datosSolicitudes.get(i).getDireccionCliente()+ "</td>"
                + "\n<td>" + datosSolicitudes.get(i).getEstadoSolicitud()+ "</td>"
                + "\n</tr>";
        }
        data += "\n</table>";
        return data;
    }

    /**
     * Metodo que escribe el contenido recibido como parametro en un archivo
     * HTML
     *
     * @param contenido es el contenido final que se escribira en el archivo
     * HTML
     */
    private void generarHTML(String contenido) {
        try (FileWriter fileWriter = new FileWriter(file); BufferedWriter writer = new BufferedWriter(fileWriter);) {
            writer.append(contenido);
            writer.append("\n</body>");
            writer.append("\n</html>");
            System.out.println("Archivo Creado Exitosamente");
        } catch (IOException e) {
            System.out.println("No se pudo escribir el archivo HTML en la carpeta seleccionada");
        }
    }
    
}
