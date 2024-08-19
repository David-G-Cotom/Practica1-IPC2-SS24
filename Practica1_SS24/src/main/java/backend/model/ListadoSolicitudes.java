/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.enums.EstadosSolicitud;
import backend.enums.TipoTarjetas;
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
    private TipoTarjetas tipoTarjeta;
    private double salarioMinimo;
    private EstadosSolicitud estadoSolicitud;
    private ArrayList<ListadoSolicitudes> datosSolicitudes;
    private int numeroSolicitud;
    private String fechaCambioEstado;
    private String nombreCliente;
    private double salarioCliente;
    private String direccionCliente;
    private File file;
    private boolean hayTipoTarjeta;
    private boolean hayEstadoSolicitud;

    //---------------------------------------- CONSTRUCTORES ----------------------------------------//
    public ListadoSolicitudes(String fechaInicio, String fechaFin, double salarioMinimo) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.salarioMinimo = salarioMinimo;
    }

    public ListadoSolicitudes(TipoTarjetas tipoTarjeta, int numeroSolicitud, String fechaCambioEstado, String nombreCliente, double salarioCliente, String direccionClietne) {
        this.tipoTarjeta = tipoTarjeta;
        this.numeroSolicitud = numeroSolicitud;
        this.fechaCambioEstado = fechaCambioEstado;
        this.nombreCliente = nombreCliente;
        this.salarioCliente = salarioCliente;
        this.direccionCliente = direccionClietne;
    }

    //---------------------------------------- GETERS AND SETERS ----------------------------------------//
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

    public TipoTarjetas getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjetas tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getSalarioMinimo() {
        return salarioMinimo;
    }

    public void setSalarioMinimo(int salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public EstadosSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadosSolicitud estadoSolicitud) {
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

    public boolean isHayTipoTarjeta() {
        return hayTipoTarjeta;
    }

    public void setHayTipoTarjeta(boolean hayTipoTarjeta) {
        this.hayTipoTarjeta = hayTipoTarjeta;
    }

    public boolean isHayEstadoSolicitud() {
        return hayEstadoSolicitud;
    }

    public void setHayEstadoSolicitud(boolean hayEstadoSolicitud) {
        this.hayEstadoSolicitud = hayEstadoSolicitud;
    }

    //---------------------------------------- METODOS PROPIOS ----------------------------------------//
    /**
     * Metodo que genera una query SQL que dependera de los datos que se tengan
     *
     * @return el resto de la consulta para la Base de Datos
     */
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
        if (hayTipoTarjeta) {
            if (!hayPrimerCondicion) {
                query += " WHERE tipo = '" + this.tipoTarjeta.toString() + "'";
                hayPrimerCondicion = true;
            } else {
                query += " AND tipo = '" + this.tipoTarjeta.toString() + "'";
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
        if (hayEstadoSolicitud) {
            boolean estado = this.estadoSolicitud.toString().equals("AUTORIZADA") || this.estadoSolicitud.toString().equals("APROBADA");
            if (!hayPrimerCondicion) {
                query += " WHERE estado = " + estado;
            } else {
                query += " AND estado = " + estado;
            }
        }
        return query;
    }

    /**
     * Metodo para exportar el reporte del Listado de Solicitudes en un formato
     * HTML para su posterior visualizacion en la WEB
     *
     * @param pathCarpeta es la ruta de carpeta en donde se guardara el reporte
     */
    public void exportarReportes(String pathCarpeta) {
        String data = generarArchivo(pathCarpeta);
        data = generarContenido(data);
        generarHTML(data);
    }

    /**
     * Metodo que verifica si existe el archivo HTML para poder establecer la
     * cabecer de etiquetas que tendra el mismo
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

    /**
     * Metodo que le agrega a la data recibida como parametro el contenido que
     * se debe tener para el respectivo Listado de Solicitudes
     *
     * @param data son las etiqeutas de cabecera que tendra el archivo HTML
     * @return el contenido que tendra el archivo HTML
     */
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
                    + "\n<td>" + datosSolicitudes.get(i).getNumeroSolicitud() + "</td>"
                    + "\n<td>" + datosSolicitudes.get(i).getFechaCambioEstado() + "</td>"
                    + "\n<td>" + datosSolicitudes.get(i).getTipoTarjeta() + "</td>"
                    + "\n<td>" + datosSolicitudes.get(i).getNombreCliente() + "</td>"
                    + "\n<td>" + datosSolicitudes.get(i).getSalarioCliente() + "</td>"
                    + "\n<td>" + datosSolicitudes.get(i).getDireccionCliente() + "</td>";
            if (datosSolicitudes.get(i).hayEstadoSolicitud) {
                data += "\n<td>" + datosSolicitudes.get(i).getEstadoSolicitud().toString() + "</td>"
                        + "\n</tr>";
            } else {
                data += """                        
                        <td></td>
                        </tr>""";
            }
        }
        data += "\n</table>";
        return data;
    }

    /**
     * Metodo que escribe el contenido recibido como parametro ademas de agregar
     * las etiquetas de cierre en un archivo HTML
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
