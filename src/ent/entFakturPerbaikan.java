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
public class entFakturPerbaikan {

    private String atNoFakturPerbaikan = "";
    public String atNoArmada = "";
    public String atNoMekanik = "";
    public String atTglFakturPerbaikan = "";
    
    public void setNoFakturPerbaikan(String pNoFakturPerbaikan){
        this.atNoFakturPerbaikan = pNoFakturPerbaikan;
    }
    
    public String getNoFakturPerbaikan(){
        return this.atNoFakturPerbaikan;
    }
    
    
    public void searchFakturPerbaikan(){
        String vSql =   "SELECT fakturperbaikan.NoFakturPerbaikan, fakturperbaikan.NoArmada, fakturperbaikan.NoMekanik, fakturperbaikan.TglFakturPerbaikan " +
                        "FROM fakturperbaikan WHERE fakturperbaikan.NoFakturPerbaikan='"+ this.atNoFakturPerbaikan +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                this.atNoFakturPerbaikan=rs.getString("NoFakturPerbaikan");
                this.atNoArmada=rs.getString("NoArmada");
                this.atNoMekanik=rs.getString("NoMekanik");
                this.atTglFakturPerbaikan=rs.getString("TglFakturPerbaikan");
            }else{
                this.atNoArmada = "";
                this.atNoMekanik = "";
                this.atTglFakturPerbaikan = "";
            }
                      
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }
    }
    
    public void insertFakturPerbaikan(){
        String vSql =   "INSERT INTO fakturperbaikan (fakturperbaikan.NoFakturPerbaikan, fakturperbaikan.NoArmada, fakturperbaikan.NoMekanik, fakturperbaikan.TglFakturPerbaikan) " +
                        "VALUES ('"+ this.atNoFakturPerbaikan +"', '"+ this.atNoArmada +"', '"+ this.atNoMekanik +"', '"+ this.atTglFakturPerbaikan +"')";
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
    
    public void updateFakturPerbaikan() {
        String vSql =   "UPDATE fakturperbaikan " +
                        "SET fakturperbaikan.NoArmada='"+ this.atNoArmada +"', fakturperbaikan.NoMekanik='"+ this.atNoMekanik +"', fakturperbaikan.TglFakturPerbaikan='"+ this.atTglFakturPerbaikan +"' " +
                        "WHERE fakturperbaikan.NoFakturPerbaikan='"+ this.atNoFakturPerbaikan +"'";
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
    
    public void deleteFakturPerbaikan(){
        String vSql =   "DELETE FROM fakturperbaikan " +
                        "WHERE fakturperbaikan.NoFakturPerbaikan='"+ this.atNoFakturPerbaikan +"'";
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
