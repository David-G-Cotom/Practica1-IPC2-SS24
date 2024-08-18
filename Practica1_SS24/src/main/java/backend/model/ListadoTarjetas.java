/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import backend.enums.EstadosTarjeta;
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
public class ListadoTarjetas {

    private TipoTarjetas tipoTarjeta;
    private double limiteMinimo;
    private String fechaInicio;
    private String fechaFinal;
    private EstadosTarjeta estadoTarjeta;
    private ArrayList<ListadoTarjetas> datosTarjetas;
    private String numeroTarjeta;
    private double limiteCreditoTarjeta;
    private String nombreCliente;
    private String direccionCliente;
    private String fechaCambioEstado;
    private File file;
    private boolean hayTipoTarjeta;
    private boolean hayEstadoTarjeta;

    public ListadoTarjetas(double limiteMinimo, String fechaInicio, String fechaFinal) {
        this.limiteMinimo = limiteMinimo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public ListadoTarjetas(TipoTarjetas tipoTarjeta, EstadosTarjeta estadoTarjeta, String numeroTarjeta, double limiteCreditoTarjeta, String nombreCliente, String direccionCliente, String fechaCambioEstado) {
        this.tipoTarjeta = tipoTarjeta;
        this.estadoTarjeta = estadoTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.limiteCreditoTarjeta = limiteCreditoTarjeta;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.fechaCambioEstado = fechaCambioEstado;
    }

    public TipoTarjetas getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjetas tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getLimiteMinimo() {
        return limiteMinimo;
    }

    public void setLimiteMinimo(double limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public EstadosTarjeta getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(EstadosTarjeta estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    public ArrayList<ListadoTarjetas> getDatosTarjetas() {
        return datosTarjetas;
    }

    public void setDatosTarjetas(ArrayList<ListadoTarjetas> datosTarjetas) {
        this.datosTarjetas = datosTarjetas;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public double getLimiteCreditoTarjeta() {
        return limiteCreditoTarjeta;
    }

    public void setLimiteCreditoTarjeta(double limiteCreditoTarjeta) {
        this.limiteCreditoTarjeta = limiteCreditoTarjeta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getFechaCambioEstado() {
        return fechaCambioEstado;
    }

    public void setFechaCambioEstado(String fechaCambioEstado) {
        this.fechaCambioEstado = fechaCambioEstado;
    }

    public boolean isHayTipoTarjeta() {
        return hayTipoTarjeta;
    }

    public void setHayTipoTarjeta(boolean hayTipoTarjeta) {
        this.hayTipoTarjeta = hayTipoTarjeta;
    }

    public boolean isHayEstadoTarjeta() {
        return hayEstadoTarjeta;
    }

    public void setHayEstadoTarjeta(boolean hayEstadoTarjeta) {
        this.hayEstadoTarjeta = hayEstadoTarjeta;
    }

    public String filtrarDatos() {
        String query = "";
        boolean hayPrimerCondicion = false;
        if (hayTipoTarjeta) {
            query += " WHERE tipo = '" + this.tipoTarjeta.toString() + "'";
            hayPrimerCondicion = true;
        }
        if (!(this.limiteMinimo < 0)) {
            if (!hayPrimerCondicion) {
                query += " WHERE tarjeta.limite_credito > " + this.limiteMinimo;
                hayPrimerCondicion = true;
            } else {
                query += " AND tarjeta.limite_credito > " + this.limiteMinimo;
            }
        }
        if (!this.fechaInicio.equals("")) {
            if (!hayPrimerCondicion) {
                query += " WHERE DATEDIFF(day, '" + this.fechaInicio + "', (SELECT fecha_cambio_estado FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON id_tipo = tipo_tarjeta" + query + ")) >= 0";
                hayPrimerCondicion = true;
            } else {
                query += " AND DATEDIFF(day, '" + this.fechaInicio + "', (SELECT fecha_cambio_estado FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON id_tipo = tipo_tarjeta" + query + ")) >= 0";
            }
        }
        if (!this.fechaFinal.equals("")) {
            if (!hayPrimerCondicion) {
                query += " WHERE DATEDIFF(day, (SELECT fecha_cambio_estado FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON id_tipo = tipo_tarjeta" + query + "), '" + this.fechaFinal + "') >= 0";
                hayPrimerCondicion = true;
            } else {
                query += " AND DATEDIFF(day, (SELECT fecha_cambio_estado FROM cliente INNER JOIN tarjeta ON cliente.id_cliente = tarjeta.id_cliente INNER JOIN tipo_tarjeta ON id_tipo = tipo_tarjeta" + query + "), '" + this.fechaFinal + "') >= 0";
            }
        }
        if (hayEstadoTarjeta) {
            boolean estado = this.estadoTarjeta.toString().equals("AUTORIZADA") || this.estadoTarjeta.toString().equals("ACTIVA");
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
            String nombreArchivo = "Reporte de Listado de Tarjetas " + numeroReporte + ".html";
            for (int i = 0; i < contenidoCarpeta.length; i++) {
                if (contenidoCarpeta[i].getName().equals(nombreArchivo)) {
                    numeroReporte++;
                    nombreArchivo = "Reporte de Listado de Tarjetas " + numeroReporte + ".html";
                    i = 0;
                }
            }
            //int numeroUltimoReporte = Integer.parseInt(contenidoCarpeta[contenidoCarpeta.length-1].getName().substring(contenidoCarpeta.length-6, contenidoCarpeta.length-5));
            this.file = new File(pathCarpeta + "\\" + nombreArchivo);
        } else {
            this.file = new File(pathCarpeta + "\\Reporte de Listado de Tarjetas 0.html");
        }
        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            return """
                   <html>                   
                   <head>
                   	<title>Reporte de el Listado de Tarjetas en el Sistema</title>
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
                <h3>Listado de Tarjetas Registradas en el Sistema</h3>
                <table>
                    <tr>
                        <th>Numero Tarjeta</th>
                        <th>Tipo</th>
                        <th>Limite Credito</th>
                        <th>Nombre Cliente</th>
                        <th>Direccion Cliente</th>
                        <th>Fecha Cambio de Estado</th>
                        <th>Estado</th>
                    </tr>
                """;        
        for (int i = 0; i < this.datosTarjetas.size(); i++) {
            data += "\n<tr>"
                + "\n<td>" + datosTarjetas.get(i).getNumeroTarjeta() + "</td>"
                + "\n<td>" + datosTarjetas.get(i).getTipoTarjeta().toString() + "</td>"
                + "\n<td>" + datosTarjetas.get(i).getLimiteCreditoTarjeta() + "</td>"
                + "\n<td>" + datosTarjetas.get(i).getNombreCliente() + "</td>"
                + "\n<td>" + datosTarjetas.get(i).getDireccionCliente() + "</td>"
                + "\n<td>" + datosTarjetas.get(i).getFechaCambioEstado() + "</td>"
                + "\n<td>" + datosTarjetas.get(i).getEstadoTarjeta() + "</td>"
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
            System.out.println("No se pudo escribir el archivo " + numeroTarjeta + ".html en la carpeta seleccionada");
        }
    }

}
