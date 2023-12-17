/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctr;

import ent.entJenisPerbaikan;
import ent.entMekanik;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class ctrJnsPerbaikan {
    entJenisPerbaikan o = new entJenisPerbaikan();
    
    public void setNoJenisPerbaikan(String pNoJenisPerbaikan){
        o.setNoJenisPerbaikan(pNoJenisPerbaikan);
        o.searchJenisPerbaikan();
    }
    
    public String[] getDataJenisPerbaikan(){
        String[] vAJenisPerbaikan = new String[5];
        vAJenisPerbaikan[0]=o.getNoJenisPerbaikan();
        vAJenisPerbaikan[1]=o.atNamaJenisPerbaikan;
        return vAJenisPerbaikan;
    }
    
    public void setDataJenisPerbaikan(ArrayList<String> p){
        o.setNoJenisPerbaikan(p.get(0));
        o.atNamaJenisPerbaikan=p.get(1);
    }
    
    public void save(){
        o.insertJenisPerbaikan();
    }
    
    public void edit(){
        o.updateJenisPerbaikan();
    }
    
    public void delete(){
        o.deleteJenisPerbaikan();
    }
}
