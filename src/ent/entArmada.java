/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ent;

import db.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class entArmada {
    private String atNoArmada = "";
    public String atNoPlat = "";
    public String atMerk = "";
    public String atModel = "";
    public int atTahun = 0;
    
    public void setNoArmada(String pNoArmada){
        this.atNoArmada = pNoArmada;
    }
    
    public String getNoArmada(){
        return this.atNoArmada;
    }
    
    public void searchArmada(){
        String vSql =   "SELECT armada.NoArmada, armada.NoPlat, armada.Merk, armada.Model, armada.Tahun " +
                        "FROM armada WHERE armada.NoArmada = '"+ this.atNoArmada +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                this.atNoArmada=rs.getString("NoArmada");
                this.atNoPlat=rs.getString("NoPlat");
                this.atMerk=rs.getString("Merk");
                this.atModel=rs.getString("Model");
                this.atTahun=rs.getInt("Tahun");
            }else{
                this.atNoPlat="";
                this.atMerk="";
                this.atModel="";
                this.atTahun=0;
            }
                      
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }
    }
    
    public void insertArmada(){
        String vSql =   "INSERT INTO armada (armada.NoArmada, armada.NoPlat, armada.Merk, armada.Model, armada.Tahun) " +
                        "VALUES ('"+ this.atNoArmada +"', '"+ this.atNoPlat +"', '"+ this.atMerk +"', '"+ this.atModel +"', '"+ this.atTahun +"')";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);           
            Stat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error -> "+ex.toString());
        }
    }
    
    public void updateArmada(){
        String vSql =   "UPDATE armada " +
                        "SET armada.NoPlat='"+ this.atNoPlat +"', armada.Merk='"+ this.atMerk +"', armada.Model='"+ this.atModel +"', armada.Tahun='"+ this.atTahun +"' " +
                        "WHERE armada.NoArmada='"+ this.atNoArmada +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);                                                
            Stat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error -> "+ex.toString());
        }
    }
    
    public void deleteArmada(){
        String vSql =   "DELETE FROM armada " +
                        "WHERE armada.NoArmada='"+ this.atNoArmada +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);                                                
            Stat.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error -> "+ex.toString());
        }
    }
}
