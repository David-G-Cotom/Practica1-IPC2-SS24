/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.model;

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
public class FiltroEstadoCuenta {
    
    private String numeroTarjeta;
    private TipoTarjetas tipoTarjeta;
    private double saldoMinimo;
    private double interesesMinimo;
    private ArrayList<EstadoCuenta> datosEstadosCuenta;
    private File file;
    private boolean hayTipoTarjeta;

    public FiltroEstadoCuenta(String numeroTarjeta, TipoTarjetas TipoTarjeta, double saldoMinimo, double iteresesMinomo) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipoTarjeta = TipoTarjeta;
        this.saldoMinimo = saldoMinimo;
        this.interesesMinimo = iteresesMinomo;
        this.hayTipoTarjeta = true;
    }
    
    public FiltroEstadoCuenta(String numeroTarjeta, double saldoMinimo, double iteresesMinomo) {
        this.numeroTarjeta = numeroTarjeta;
        this.saldoMinimo = saldoMinimo;
        this.interesesMinimo = iteresesMinomo;
        this.hayTipoTarjeta = false;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public TipoTarjetas getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjetas tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public double getSaldoMinimo() {
        return saldoMinimo;
    }

    public void setSaldoMinimo(double saldoMinimo) {
        this.saldoMinimo = saldoMinimo;
    }

    public double getInteresesMinimo() {
        return interesesMinimo;
    }

    public void setInteresesMinimo(double interesesMinimo) {
        this.interesesMinimo = interesesMinimo;
    }

    public ArrayList<EstadoCuenta> getDatosEstadosCuenta() {
        return datosEstadosCuenta;
    }

    public void setDatosEstadosCuenta(ArrayList<EstadoCuenta> datosEstadosCuenta) {
        this.datosEstadosCuenta = datosEstadosCuenta;
    }    
    
    public String filtrarDatos() {
        String query = "";
        if (!this.numeroTarjeta.equals("")) {
            query += " AND tarjeta.numero_tarjeta = '" + this.numeroTarjeta + "'";
        }
        if (hayTipoTarjeta) {
            query += " AND tipo = '" + this.tipoTarjeta.toString() + "'";
        }        
        if (!(this.saldoMinimo < 0)) {
            query += " AND saldo > " + this.saldoMinimo;
        }
        if (!(this.interesesMinimo < 0)) {
            query += " AND intereses > " + this.interesesMinimo;
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
            String nombreArchivo = "Reporte de Estado de Cuentas " + numeroReporte + ".html";
            for (int i = 0; i < contenidoCarpeta.length; i++) {
                if (contenidoCarpeta[i].getName().equals(nombreArchivo)) {
                    numeroReporte++;
                    nombreArchivo = "Reporte de Estado de Cuentas " + numeroReporte + ".html";
                    i = 0;
                }
            }
            this.file = new File(pathCarpeta + "\\" + nombreArchivo);
        } else {
            this.file = new File(pathCarpeta + "\\Reporte de Estado de Cuentas 0.html");
        }
        System.out.println(file.getAbsolutePath());
        if (file.exists()) {
            file.delete();
        }
        if (!file.exists()) {
            return """
                   <html>                   
                   <head>
                   	<title>Reporte de los Estados de Cuenta de cada Tarjeta Activa en el Sistema</title>
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
        filtrarEstadosCuenta();
        data += "\n<h3>Listado de los Estados de Cuenta de cada Tarjetas Activa en el Sistema</h3>";
        for (int i = 0; i < this.datosEstadosCuenta.size(); i++) {
            data += "\n<hr>"
                    + "\n<p>Numero de Tarjeta: " + this.datosEstadosCuenta.get(i).getNumeroTarjeta() + "</p>"
                    + "\n<p>Tipo de Tarjeta: " + this.datosEstadosCuenta.get(i).getTipoTarjeta() + "</p>"
                    + "\n<p>Nombre del Cliente: " + this.datosEstadosCuenta.get(i).getNombreCliente() + "</p>"
                    + "\n<p>Direccion del Cliente: " + this.datosEstadosCuenta.get(i).getDireccionCliente() + "</p>";
            data += """
                    <table>
                        <tr>
                            <th>Fecha de Movimientos</th>
                            <th>Tipo de Movimiento</th>
                            <th>Descripcion</th>
                            <th>Establecimiento</th>
                            <th>Monto Ejecutado</th>
                        </tr>
                    """;       
            for (int j = 0; j < this.datosEstadosCuenta.get(i).getMovimientos().size(); j++) {
                data += "\n<tr>"
                    + "\n<td>" + datosEstadosCuenta.get(i).getMovimientos().get(j).getFechaOperacion() + "</td>"
                    + "\n<td>" + datosEstadosCuenta.get(i).getMovimientos().get(j).getTipoMovimiento()+ "</td>"
                    + "\n<td>" + datosEstadosCuenta.get(i).getMovimientos().get(j).getDescripcion()+ "</td>"
                    + "\n<td>" + datosEstadosCuenta.get(i).getMovimientos().get(j).getEstablecimiento()+ "</td>"
                    + "\n<td>" + datosEstadosCuenta.get(i).getMovimientos().get(j).getMontoTransferido()+ "</td>"
                    + "\n</tr>";
            }
            data += "\n</table>";
            data += "\n<p>Monto Total: " + this.datosEstadosCuenta.get(i).getMontoTotalEjecutado() + "</p>"
                    + "\n<p>Intereses: " + this.datosEstadosCuenta.get(i).getIntereses()+ "</p>"
                    + "\n<p>Saldo Total: " + this.datosEstadosCuenta.get(i).getSaldoTotal()+ "</p>";
        }                
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
    
    public void filtrarEstadosCuenta() {
        ArrayList<EstadoCuenta> estadosFiltrados = new ArrayList<>();
        for (int i = 0; i < this.datosEstadosCuenta.size(); i++) {
            double montoTotal = 0;
            for (int j = 0; j < this.datosEstadosCuenta.get(i).getMovimientos().size(); j++) {
                if (this.datosEstadosCuenta.get(i).getMovimientos().get(j).getTipoMovimiento().toString().equals("ABONO")) {
                    montoTotal += this.datosEstadosCuenta.get(i).getMovimientos().get(j).getMontoTransferido();
                } else {
                    montoTotal -= this.datosEstadosCuenta.get(i).getMovimientos().get(j).getMontoTransferido();
                }
            }
            System.out.println(montoTotal);
            this.datosEstadosCuenta.get(i).setMontoTotalEjecutado(montoTotal);
            this.datosEstadosCuenta.get(i).setIntereses(this.datosEstadosCuenta.get(i).getInteresTipoTarjeta()*this.datosEstadosCuenta.get(i).getMontoTotalEjecutado());
            this.datosEstadosCuenta.get(i).setSaldoTotal(this.datosEstadosCuenta.get(i).getIntereses()+this.datosEstadosCuenta.get(i).getMontoTotalEjecutado());
            if (!(this.interesesMinimo < 0)) {
                if (this.datosEstadosCuenta.get(i).getIntereses() < this.interesesMinimo) {
                    continue;
                }
            }
            if (!(this.saldoMinimo < 0)) {
                if (this.datosEstadosCuenta.get(i).getSaldoTotal() < this.saldoMinimo) {
                    continue;
                }
            }
            estadosFiltrados.add(this.datosEstadosCuenta.get(i));
        }        
        this.datosEstadosCuenta = estadosFiltrados;
    }
    
}
