/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctr;

import ent.entDetilFakturPerbaikan;
import java.util.ArrayList;


/**
 *
 * @author ACER
 */
public class ctrDetilFakturPerbaikan {
    entDetilFakturPerbaikan o = new entDetilFakturPerbaikan();
    
    public void setNoFakturPerbaikan(String pNoFakturPerbaikan, String pNoJenisPerbaikan){
        o.setNoFakturPerbaikan(pNoFakturPerbaikan);
        o.setNoJenisPerbaikan(pNoJenisPerbaikan);
        o.searchDetilFakturPerbaikan();
    }
    
    public String[] getDataFakturPerbaikan(){
        String[] vADetilFakturPerbaikan = new String[5];
        vADetilFakturPerbaikan[0]=o.getNoFakturPerbaikan();
        vADetilFakturPerbaikan[1]=o.getNoJenisPerbaikan();
        vADetilFakturPerbaikan[2]=o.atDtlMasalah;
        return vADetilFakturPerbaikan;
    }
    
    public void setDataFakturPerbaikan(ArrayList<String> p){
        o.setNoFakturPerbaikan(p.get(0));
        o.setNoJenisPerbaikan(p.get(1));
        o.atDtlMasalah=p.get(2);
    }
    
    public void save(){
        o.insertDetilFakturPerbaikan();
    }
    
    public void edit(){
        o.updateDetilFakturPerbaikan();
    }
    
    public void delete(){
        o.deleteDetilFakturPerbaikan();
    }
    
    public boolean isExist(String noFakturPerbaikan, String noJenisPerbaikan){
        return o.isExistItem(noFakturPerbaikan, noJenisPerbaikan);
    }

}
