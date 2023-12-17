/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctr;

import ent.entArmada;
import ent.entMekanik;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class ctrArmada {
    entArmada o = new entArmada();
    
    public void setNoArmada(String pNoArmada){
        o.setNoArmada(pNoArmada);
        o.searchArmada();
    }
    
    public String[] getDataArmada(){
        String[] vAArmada = new String[5];
        vAArmada[0]=o.getNoArmada();
        vAArmada[1]=o.atNoPlat;
        vAArmada[2]=o.atMerk;
        vAArmada[3]=o.atModel;
        vAArmada[4]=String.valueOf(o.atTahun);
        return vAArmada;
    }
    
    public void setDataArmada(ArrayList<String> p){
        o.setNoArmada(p.get(0));
        o.atNoPlat=p.get(1);
        o.atMerk=p.get(2);
        o.atModel=p.get(3);
        o.atTahun=Integer.valueOf(p.get(4));
    }
    
    public void save(){
        o.insertArmada();
    }
    
    public void edit(){
        o.updateArmada();
    }
    
    public void delete(){
        o.deleteArmada();
    }
}
