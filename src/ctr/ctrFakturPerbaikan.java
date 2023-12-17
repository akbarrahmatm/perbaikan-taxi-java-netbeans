/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctr;

import ent.entFakturPerbaikan;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class ctrFakturPerbaikan {
    entFakturPerbaikan o = new entFakturPerbaikan();
    
    public void setNoFakturPerbaikan(String pFakturPerbaikan){
        o.setNoFakturPerbaikan(pFakturPerbaikan);
        o.searchFakturPerbaikan();
    }
    
    public String[] getDataFakturPerbaikan(){
        String[] vAFakturPerbaikan = new String[5];
        vAFakturPerbaikan[0]=o.getNoFakturPerbaikan();
        vAFakturPerbaikan[1]=o.atNoArmada;
        vAFakturPerbaikan[2]=o.atNoMekanik;
        vAFakturPerbaikan[3]=o.atTglFakturPerbaikan;
        return vAFakturPerbaikan;
    }
    
    public void setDataFakturPerbaikan(ArrayList<String> p){
        o.setNoFakturPerbaikan(p.get(0));
        o.atNoArmada=p.get(1);
        o.atNoMekanik=p.get(2);
        o.atTglFakturPerbaikan=p.get(3);
    }
    
    public void save(){
        o.insertFakturPerbaikan();
    }
    
    public void edit(){
        o.updateFakturPerbaikan();
    }
    
    public void delete(){
        o.deleteFakturPerbaikan();
    }
}
