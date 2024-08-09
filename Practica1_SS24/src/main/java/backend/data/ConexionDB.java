/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Carlos Cotom
 */
public class ConexionDB {
    
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/bd_banco";
    private static final String USER = "root";
    private static final String PASSWORD = "uipc2w8e9u1ner";
    private static Connection connection;

    public ConexionDB() {
        try {
            connection = DriverManager.getConnection(USER, USER, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Error al conectarse con MySQL");
        }
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            new ConexionDB();
            System.out.println("Conexion Exitosa");
        }
        return connection;
    }
    
}
