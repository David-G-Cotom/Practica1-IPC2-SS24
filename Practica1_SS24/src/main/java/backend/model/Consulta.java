/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Carlos Cotom
 */
public class Consulta {
    
    private String numeroTarjeta;
    private String tipoTarjeta;
    private double limiteCredito;
    private String nombreCliente;
    private String direccionCliente;
    private boolean estadoTarjeta;
    private String pathCarpeta;
    private File file;

    public Consulta(String numeroTarjeta, String tipoTarjeta, double limiteCredito, String nombreCliente, String direccionCliente, boolean estadoTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.limiteCredito = limiteCredito;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.estadoTarjeta = estadoTarjeta;        
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
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

    public boolean isEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(boolean estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    public String getPathCarpeta() {
        return pathCarpeta;
    }

    public void setPathCarpeta(String pathCarpeta) {
        this.pathCarpeta = pathCarpeta;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }    
    
    /**
     * Metodo para exportar el reportes de la consulta de tarjeta a un formato
     * HTML para su posterior visualizacion en la WEB
     *
     */
    public void exportarReportes() {
        String data = obtenerDataActual();
        data = generarContenido(data);
        generarHTML(data);
    }
    
    /**
     * Metodo que verifica si existe el archivo HTML para poder establecer la
     * cabecer de etiquetas que tendra el mismo
     *
     * @return la cabecera de etiquetas que tendra el archivo HTML
     */
    private String obtenerDataActual() {
        this.file = new File(pathCarpeta + "\\Consulta de Tarjeta " + this.numeroTarjeta + ".html");
        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            return """
                   <html>
                   <head>
                   	<title>Bitacora Escapa del Laberinto</title>
                   </head>
                   <body>
                   """;
        }
        return null;
    }
    
    private String generarContenido(String data) {
        data += "\n<h3>Consulta de Tarjeta con Numero: " + numeroTarjeta + "</h3>";
        String estado;
        if (estadoTarjeta) {
            estado = "ACTIVA";
        } else {
            estado = "CANCELADA";
        }
        data += "\n<p>Numero de Tarjeta: " + numeroTarjeta + "</p>"
                + "\n<p>Tipo de Tarjeta: " + tipoTarjeta + "</p>"
                + "\n<p>Limite de Credito: " + limiteCredito + "</p>"
                + "\n<p>Nombre de Propieteario: " + nombreCliente + "</p>"
                + "\n<p>Direccion del Propoietario: " + direccionCliente + " </p>"
                + "\n<p>Estado de la Tarjeta: " + estado + " </p>";
        return data;
    }
    
    /**
     * Metodo que escribe el contenido recibido como parametro ademas de agregar
     * las etiquetas de cierre en un archivo HTML
     *
     * @param contenido es el contenido que tendra todo el cuerpo que se
     * escribira en el archivo HTML
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
