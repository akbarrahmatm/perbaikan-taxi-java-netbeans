/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectperbaikantaxi;

import ent.entArmada;
import ent.entMekanik;


/**
 *
 * @author ACER
 */
public class ProjectPerbaikanTaxi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ent.entMekanik b = new entMekanik();
        entArmada o = new entArmada();
//        o.setNoMekanik("002");
//        o.searchMekanik();
//        System.out.println("Nama : " + o.atNamaMekanik);
//        System.out.println("Alamat : " + o.atAlamat);
//        System.out.println("NoTelp : " + o.atNoTelp);


//        o.setNoMekanik("004");
//        o.atNamaMekanik = "Muhammad Fadli Isyarqi";
//        o.atAlamat = "Jalan Tangerang Raya";
//        o.atNoTelp = "087125362323";
//        o.insertMekanik();
        
//        o.setNoMekanik("004");
//        o.atNamaMekanik = "Muhammad Fadli Isyarqi";
//        o.atAlamat = "Jalan Tangerang Raya";
//        o.atNoTelp = "087125362323";
//        o.updateMekanik();

//        o.setNoMekanik("005");
//        o.deleteMekanik();

//        o.setNoArmada("001");
//        o.searchArmada();
//        System.out.println("No Armada : " + o.getNoArmada());
//        System.out.println("No Plat : " + o.atNoPlat);
//        System.out.println("Merk : " + o.atMerk);
//        System.out.println("Model : " + o.atModel);
//        System.out.println("Tahun : " + o.atTahun);

        o.setNoArmada("002");
        o.atNoPlat = "B 4384 UAN";
        o.atMerk = "Toyota";
        o.atModel = "Mobilio";
        o.atTahun = 2020;
        o.insertArmada();

          
    }
    
}
