/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class dbConnection {
    private Connection conn;
    
    public Connection koneksiDB(){
        if(this.conn == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                String vDriver = "jdbc:mysql://localhost/perbaikan_taxi";
                this.conn = (Connection) DriverManager.getConnection(vDriver, "root", "");
            } catch(ClassNotFoundException e){
                System.out.println(e);
            } catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Koneksi gagal");
            }
        }
        return conn;
    }
}
