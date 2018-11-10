/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.inbo.datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BODEGA
 */
public class DataBaseInbo {
    public Connection connection = null;

    public Connection MySQLConnect(){
        Configuration configuration = new Configuration();
        Properties configurationProperties;
        try {
            configurationProperties = configuration.loadConfiguration();
            String url = "jdbc:mysql://" + configurationProperties.getProperty("server") + "/";
            String databaseName = configurationProperties.getProperty("database");
            String userName = configurationProperties.getProperty("username");
            String password = configurationProperties.getProperty("password");
            connection = DriverManager.getConnection(url + databaseName, userName, password);

            System.out.println("Conexion exitosa");
            
        } catch (SQLException | IOException ex) {
            connection = null;
            System.out.println("Exception " + ex.getMessage());
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataBaseInbo.class.getName()).log(Level.SEVERE, null, ex);
                throw new SQLException(ex);
            }
        }
    }
}
