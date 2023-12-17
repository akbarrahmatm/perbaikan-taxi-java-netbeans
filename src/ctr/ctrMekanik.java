/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctr;

import ent.entMekanik;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class ctrMekanik {
    entMekanik o = new entMekanik();
    
    public void setNoMekanik(String pNoMekanik){
        o.setNoMekanik(pNoMekanik);
        o.searchMekanik();
    }
    
    public String[] getDataMekanik(){
        String[] vAMekanik = new String[5];
        vAMekanik[0]=o.getNoMekanik();
        vAMekanik[1]=o.atNamaMekanik;
        vAMekanik[2]=o.atAlamat;
        vAMekanik[3]=o.atNoTelp;
        return vAMekanik;
    }
    
    public void setDataMekanik(ArrayList<String> p){
        o.setNoMekanik(p.get(0));
        o.atNamaMekanik=p.get(1);
        o.atAlamat=p.get(2);
        o.atNoTelp=p.get(3);
    }
    
    public void save(){
        o.insertMekanik();
    }
    
    public void edit(){
        o.updateMekanik();
    }
    
    public void delete(){
        o.deleteMekanik();
    }
}
