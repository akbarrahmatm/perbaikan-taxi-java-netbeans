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
public class entJenisPerbaikan {    
    private String atNoJenisPerbaikan = "";
    public String atNamaJenisPerbaikan = "";
    
    
    public void setNoJenisPerbaikan(String pNoJenisPerbaikan){
        this.atNoJenisPerbaikan = pNoJenisPerbaikan;
    }
    
    public String getNoJenisPerbaikan(){
        return this.atNoJenisPerbaikan;
    }
    
    public void searchJenisPerbaikan(){
        String vSql =   "SELECT jenisperbaikan.NoJenisPerbaikan, jenisperbaikan.NamaJenisPerbaikan FROM jenisperbaikan WHERE jenisperbaikan.NoJenisPerbaikan='"+ this.atNoJenisPerbaikan +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                this.atNoJenisPerbaikan=rs.getString("NoJenisPerbaikan");
                this.atNamaJenisPerbaikan=rs.getString("NamaJenisPerbaikan");
            }else{
                this.atNamaJenisPerbaikan = "";
            }
                      
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }
    }
    
    public void insertJenisPerbaikan(){
        String vSql =   "INSERT INTO jenisperbaikan (jenisperbaikan.NoJenisPerbaikan, jenisperbaikan.NamaJenisPerbaikan) " +
                        "VALUES ('"+ this.atNoJenisPerbaikan +"', '"+ this.atNamaJenisPerbaikan +"')";
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
    
    public void updateJenisPerbaikan() {
        String vSql =   "UPDATE jenisperbaikan " +
                        "SET jenisperbaikan.NamaJenisPerbaikan='"+this.atNamaJenisPerbaikan+"' " +
                        "WHERE jenisperbaikan.NoJenisPerbaikan='"+this.atNoJenisPerbaikan+"'";
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
    
    public void deleteJenisPerbaikan(){
        String vSql =   "DELETE FROM jenisperbaikan " +
                        "WHERE jenisperbaikan.NoJenisPerbaikan='"+ this.atNoJenisPerbaikan +"'";
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
