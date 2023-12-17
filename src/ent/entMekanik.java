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
public class entMekanik {
    private String atNoMekanik = "";
    public String atNamaMekanik = "";
    public String atAlamat = "";
    public String atNoTelp = "";
    
    public void setNoMekanik(String pNoMekanik){
        this.atNoMekanik = pNoMekanik;
    }
    
    public String getNoMekanik(){
        return this.atNoMekanik;
    }
    
    public void getAllMekanik(){
        String vSql =   "SELECT * FROM mekanik";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                this.atNoMekanik=rs.getString("NoMekanik");
                this.atNamaMekanik=rs.getString("NamaMekanik");
                this.atAlamat=rs.getString("Alamat");
                this.atNoTelp=rs.getString("NoTelp");    
            }else{
                this.atNamaMekanik = "";
                this.atAlamat = "";
                this.atNoTelp = "";
            }
                      
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }
    }
    
    public void searchMekanik(){
        String vSql =   "SELECT mekanik.NoMekanik, mekanik.NamaMekanik, mekanik.Alamat, mekanik.NoTelp " +
                        "FROM mekanik WHERE mekanik.NoMekanik = '"+ this.atNoMekanik +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                this.atNoMekanik=rs.getString("NoMekanik");
                this.atNamaMekanik=rs.getString("NamaMekanik");
                this.atAlamat=rs.getString("Alamat");
                this.atNoTelp=rs.getString("NoTelp");    
            }else{
                this.atNamaMekanik = "";
                this.atAlamat = "";
                this.atNoTelp = "";
            }
                      
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }
    }
    
    public void insertMekanik(){
        String vSql =   "INSERT INTO mekanik (mekanik.NoMekanik, mekanik.NamaMekanik, mekanik.Alamat, mekanik.NoTelp) " +
                        "VALUES ('"+ this.atNoMekanik +"', '"+ this.atNamaMekanik +"', '"+ this.atAlamat +"', '"+ this.atNoTelp +"')";
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
    
    public void updateMekanik() {
        String vSql =   "UPDATE mekanik " +
                        "SET mekanik.NamaMekanik='"+ this.atNamaMekanik +"', mekanik.Alamat='"+ this.atAlamat +"', mekanik.NoTelp='"+ this.atNoTelp +"' " +
                        "WHERE mekanik.NoMekanik = '"+ this.atNoMekanik +"'";
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
    
    public void deleteMekanik(){
        String vSql =   "DELETE FROM mekanik " +
                        "WHERE mekanik.NoMekanik = '"+ this.atNoMekanik +"'";
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
