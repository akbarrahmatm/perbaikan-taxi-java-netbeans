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
public class entDetilFakturPerbaikan {
    private String atNoFakturPerbaikan = "";
    private String atNoJenisPerbaikan = "";
    public String atDtlMasalah = "";
    public String atNoJenisPerbaikanNew = "";
    
    
    
    public void setNoFakturPerbaikan(String pNoFakturPerbaikan){
        this.atNoFakturPerbaikan = pNoFakturPerbaikan;
    }
    
    public String getNoFakturPerbaikan(){
        return this.atNoFakturPerbaikan;
    }
    
    public void setNoJenisPerbaikan(String pNoJenisPerbaikan){
        this.atNoJenisPerbaikan = pNoJenisPerbaikan;
    }
    
    public String getNoJenisPerbaikan(){
        return this.atNoJenisPerbaikan;
    }
    
    public void searchDetilFakturPerbaikan(){
        String vSql =   "SELECT detilfakturperbaikan.NoFakturPerbaikan, detilfakturperbaikan.NoJenisPerbaikan, detilfakturperbaikan.DtlMasalah " +
                        "FROM detilfakturperbaikan " +
                        "WHERE detilfakturperbaikan.NoFakturPerbaikan = '"+ this.atNoFakturPerbaikan +"' AND detilfakturperbaikan.NoJenisPerbaikan='"+ this.atNoJenisPerbaikan +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(vSql);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                this.atNoFakturPerbaikan=rs.getString("NoFakturPerbaikan");
                this.atNoJenisPerbaikan=rs.getString("NoJenisPerbaikan");
                this.atDtlMasalah=rs.getString("DtlMasalah");
            }else{
                this.atNoFakturPerbaikan="";
                this.atNoJenisPerbaikan="";
                this.atDtlMasalah="";
            }
                      
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }
    }

    public void insertDetilFakturPerbaikan(){
        String vSql =   "INSERT INTO detilfakturperbaikan (detilfakturperbaikan.NoFakturPerbaikan, detilfakturperbaikan.NoJenisPerbaikan, detilfakturperbaikan.DtlMasalah) " +
                        "VALUES ('"+ this.atNoFakturPerbaikan +"', '"+ this.atNoJenisPerbaikan +"', '"+ this.atDtlMasalah +"')";
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
    
    public void updateDetilFakturPerbaikan() {
        String vSql =   "UPDATE detilfakturperbaikan " +
                        "SET detilfakturperbaikan.NoJenisPerbaikan='"+ this.atNoJenisPerbaikanNew +"', detilfakturperbaikan.DtlMasalah='"+ this.atDtlMasalah +"' " +
                        "WHERE detilfakturperbaikan.NoFakturPerbaikan='"+ this.atNoFakturPerbaikan +"' AND detilfakturperbaikan.NoJenisPerbaikan='"+ this.atNoJenisPerbaikan +"'";
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
    
    public void deleteDetilFakturPerbaikan(){
        String vSql =   "DELETE FROM detilfakturperbaikan " +
                        "WHERE detilfakturperbaikan.NoFakturPerbaikan='"+ this.atNoFakturPerbaikan +"' AND detilfakturperbaikan.NoJenisPerbaikan='"+ this.atNoJenisPerbaikan +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);                                                
            Stat.executeUpdate();
            System.err.println(vSql);
        } catch (SQLException ex) {
            System.out.println("Error -> "+ex.toString());
        }
    }
    
    public void deleteDetilFakturPerbaikanByNoFaktur(){
        String vSql =   "DELETE FROM detilfakturperbaikan WHERE detilfakturperbaikan.NoFakturPerbaikan='"+ this.atNoFakturPerbaikan +"'";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();
        PreparedStatement Stat = null;
        try{
            Stat = con.prepareStatement(vSql);                                                
            Stat.executeUpdate();
            System.err.println(vSql);
        } catch (SQLException ex) {
            System.out.println("Error -> "+ex.toString());
        }
    }
    
    public boolean isExistItem(String noFakturPerbaikan, String noJenisPerbaikan){
        boolean exist = false;
        String query = "SELECT COUNT(*) AS count FROM detilfakturperbaikan WHERE NoFakturPerbaikan = "+noFakturPerbaikan+" AND NoJenisPerbaikan = "+noJenisPerbaikan+"";
        dbConnection db = new dbConnection();
        Connection con = db.koneksiDB();

        PreparedStatement stat = null; // Sesuaikan penanganan kesalahan sesuai kebutuhan
        try{
            stat = con.prepareStatement(query);
            ResultSet rs = stat.executeQuery();
            if(rs.next()){
                int count = rs.getInt("count");
                exist = count > 0;
            }
            
        } catch (SQLException e){
            System.out.println("error----->"+e.toString());
        }
        

        return exist;
    }
}
