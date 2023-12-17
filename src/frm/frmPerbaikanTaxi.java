/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frm;

import ctr.ctrArmada;
import ctr.ctrJnsPerbaikan;
import ctr.ctrMekanik;
import db.dbConnection;
import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author ACER
 */
public class frmPerbaikanTaxi extends javax.swing.JFrame {
    ctrMekanik oMekanik = new ctrMekanik();
    ctrJnsPerbaikan oJnsPerbaikan = new ctrJnsPerbaikan();
    ctrArmada oArmada = new ctrArmada();
    
    private Map<JPanel, JPanel> tabContentMap = new HashMap<>();
    
    public Statement stat;
    public ResultSet res;
    public DefaultTableModel tabModel;
    dbConnection db = new dbConnection(); 
    Connection con = db.koneksiDB();
    
    public frmPerbaikanTaxi() {
        setAppInformation();
        initComponents();
        
        tabContentMap.put(tabDashboard, contentDashboard);
        tabContentMap.put(tabFakturPerbaikan, contentFakturPerbaikan);
        tabContentMap.put(tabMasterArmada, contentMArmada);
        tabContentMap.put(tabMasterMekanik, contentMMekanik);
        tabContentMap.put(tabMasterJnsPerbaikan, contentMJnsPerbaikan);
        
        showTab(tabDashboard);
    }
    
    public void setAppInformation(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("optimizing.png")));
        this.setTitle(" - Aplikasi Perbaikan Taxi");
    } 

    private void showTab(JPanel selectedTab) {
        tabContentMap.values().forEach(panel -> panel.setVisible(false));

        tabContentMap.get(selectedTab).setVisible(true);

        resetTabColors();

        selectedTab.setBackground(new Color(214, 140, 30));
    }
    
    private void resetTabColors() {
        tabContentMap.keySet().forEach(tab -> tab.setBackground(new Color(241, 174, 75)));
    }
    
    
    // Start Module Mekanik    
    private void tableMekanik(){
        Object[] judul = {
            "Kode Mekanik", "Nama Mekanik", "Alamat", "No Telepon"
        };
        tabModel = new DefaultTableModel(null, judul);
        tblMekanik.setModel(tabModel);
        
        try {
            stat = con.createStatement();
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            res = stat.executeQuery("SELECT * FROM mekanik ORDER BY mekanik.NoMekanik DESC");

            while (res.next()) {
                Object[] data = {
                    res.getString("NoMekanik"),
                    res.getString("NamaMekanik"),
                    res.getString("Alamat"),
                    res.getString("NoTelp"),
                };

                tabModel.addRow(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setKdMekanik(String p){
        oMekanik.setNoMekanik(p);
        txtNoMekanik.setText(p);
        if(oMekanik.getDataMekanik()[1].isEmpty() || oMekanik.getDataMekanik()[2].isEmpty() || oMekanik.getDataMekanik()[3].isEmpty()){
            btnUpdateMekanik.setVisible(false);
            btnDeleteMekanik.setVisible(false);

        } else{
            txtNamaMekanik.setText(oMekanik.getDataMekanik()[1]);
            txtAlamat.setText(oMekanik.getDataMekanik()[2]);
            txtNoTelp.setText(oMekanik.getDataMekanik()[3]);
            
            txtNoMekanik.setEnabled(false);
            btnSaveMekanik.setVisible(false);
            
            btnUpdateMekanik.setVisible(true);
            btnDeleteMekanik.setVisible(true);
        }
    }
    
    public void saveMekanik(){
        if(txtNoMekanik.getText().isEmpty() || txtNamaMekanik.getText().isEmpty() || txtAlamat.getText().isEmpty() || txtNoTelp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua Kolom Harus Diisi", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            ArrayList<String> vLst = new ArrayList<>();

            vLst.add(txtNoMekanik.getText());
            vLst.add(txtNamaMekanik.getText());
            vLst.add(txtAlamat.getText());
            vLst.add(txtNoTelp.getText());
        
            oMekanik.setDataMekanik(vLst);
            oMekanik.save();
            JOptionPane.showMessageDialog(null, "Simpan Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        }
        
        tableMekanik();
    }
    
    public void editMekanik(){
        if(txtNoMekanik.getText().isEmpty() || txtNamaMekanik.getText().isEmpty() || txtAlamat.getText().isEmpty() || txtNoTelp.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua Kolom Harus Diisi", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            ArrayList<String> vLst = new ArrayList<>();

            vLst.add(txtNoMekanik.getText());
            vLst.add(txtNamaMekanik.getText());
            vLst.add(txtAlamat.getText());
            vLst.add(txtNoTelp.getText());
        
            oMekanik.setDataMekanik(vLst);
            oMekanik.edit();
            JOptionPane.showMessageDialog(null, "Edit Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        }
        
        tableMekanik();
    }
    
    public void deleteMekanik(){
        oMekanik.delete();
        JOptionPane.showMessageDialog(null, "Hapus Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void clearFormMekanik(){
        txtNoMekanik.setText("");
        txtNamaMekanik.setText("");
        txtAlamat.setText("");
        txtNoTelp.setText("");
        
        txtNoMekanik.setEnabled(true);
        
        btnSaveMekanik.setVisible(true);
        btnUpdateMekanik.setVisible(true);
        btnDeleteMekanik.setVisible(true);
        btnClearMekanik.setVisible(true);
    }
    // End Module Mekanik
    
    // Start Module Jenis Perbaikan
    
    private void tableJenisPerbaikan(){
        Object[] judul = {
            "Nomor Jenis Perbaikan", "Nama Jenis Perbaikan"
        };
        tabModel = new DefaultTableModel(null, judul);
        tblJnsPerbaikan.setModel(tabModel);
        
        try {
            stat = con.createStatement();
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            res = stat.executeQuery("SELECT * FROM jenisperbaikan ORDER BY jenisperbaikan.NoJenisPerbaikan DESC");

            while (res.next()) {
                Object[] data = {
                    res.getString("NoJenisPerbaikan"),
                    res.getString("NamaJenisPerbaikan"),
                };

                tabModel.addRow(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setKdJnsPerbaikan(String p){
        oJnsPerbaikan.setNoJenisPerbaikan(p);
        txtNoJenisPerbaikan.setText(p);
        if(oJnsPerbaikan.getDataJenisPerbaikan()[1].isEmpty()){
            btnUpdateJnsPerbaikan.setVisible(false);
            btnDeleteJnsPerbaikan.setVisible(false);

        } else{
            txtNamaJenisPerbaikan.setText(oJnsPerbaikan.getDataJenisPerbaikan()[1]);
            
            txtNoJenisPerbaikan.setEnabled(false);
            btnSaveJnsPerbaikan.setVisible(false);
            
            btnUpdateJnsPerbaikan.setVisible(true);
            btnDeleteJnsPerbaikan.setVisible(true);
        }
    }
    
    public void saveJnsPerbaikan(){
        if(txtNoJenisPerbaikan.getText().isEmpty() || txtNamaJenisPerbaikan.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua Kolom Harus Diisi", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            ArrayList<String> vLst = new ArrayList<>();

            vLst.add(txtNoJenisPerbaikan.getText());
            vLst.add(txtNamaJenisPerbaikan.getText());
        
            oJnsPerbaikan.setDataJenisPerbaikan(vLst);
            oJnsPerbaikan.save();
            JOptionPane.showMessageDialog(null, "Simpan Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        }
        
        tableJenisPerbaikan();
    }
    
    public void editJnsPerbaikan(){
        if(txtNoJenisPerbaikan.getText().isEmpty() || txtNamaJenisPerbaikan.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua Kolom Harus Diisi", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            ArrayList<String> vLst = new ArrayList<>();

            vLst.add(txtNoJenisPerbaikan.getText());
            vLst.add(txtNamaJenisPerbaikan.getText());
        
            oJnsPerbaikan.setDataJenisPerbaikan(vLst);
            oJnsPerbaikan.edit();
            JOptionPane.showMessageDialog(null, "Update Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        }
        
        tableJenisPerbaikan();
    }
    
    public void deleteJnsPerbaikan(){
        oJnsPerbaikan.delete();
        JOptionPane.showMessageDialog(null, "Hapus Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void clearFormJnsPerbaikan(){
        txtNoJenisPerbaikan.setText("");
        txtNamaJenisPerbaikan.setText("");
        
        txtNoJenisPerbaikan.setEnabled(true);
        
        btnSaveJnsPerbaikan.setVisible(true);
        btnUpdateJnsPerbaikan.setVisible(true);
        btnDeleteJnsPerbaikan.setVisible(true);
        btnClearJnsPerbaikan.setVisible(true);
    }
    
    // End Module Jenis Perbaikan
    
    // Start Module Armada
    
    private void tableArmada(){
        Object[] judul = {
            "Nomor Armada", "Nomor Plat", "Merk", "Model", "Tahun Pembuatan"
        };
        tabModel = new DefaultTableModel(null, judul);
        tblArmada.setModel(tabModel);
        
        try {
            stat = con.createStatement();
            tabModel.getDataVector().removeAllElements();
            tabModel.fireTableDataChanged();
            res = stat.executeQuery("SELECT * FROM armada ORDER BY armada.NoArmada DESC");

            while (res.next()) {
                Object[] data = {
                    res.getString("NoArmada"),
                    res.getString("NoPlat"),
                    res.getString("Merk"),
                    res.getString("Model"),
                    res.getString("Tahun"),
                };

                tabModel.addRow(data);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setKdArmada(String p){
        oArmada.setNoArmada(p);
        txtNoArmada.setText(p);
        if(oArmada.getDataArmada()[1].isEmpty() || oArmada.getDataArmada()[2].isEmpty() || oArmada.getDataArmada()[3].isEmpty() || oArmada.getDataArmada()[4].isEmpty()){
            btnUpdateArmada.setVisible(false);
            btnDeleteArmada.setVisible(false);

        } else{
            txtNoPlat.setText(oArmada.getDataArmada()[1]);
            txtMerk.setText(oArmada.getDataArmada()[2]);
            txtModel.setText(oArmada.getDataArmada()[3]);
            txtTahun.setText(oArmada.getDataArmada()[4]);
            
            txtNoArmada.setEnabled(false);
            btnSaveArmada.setVisible(false);
            
            btnUpdateArmada.setVisible(true);
            btnDeleteArmada.setVisible(true);
        }
    }
    
    public void saveArmada(){
        if(txtNoArmada.getText().isEmpty() || txtNoPlat.getText().isEmpty() || txtMerk.getText().isEmpty() || txtModel.getText().isEmpty() || txtTahun.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua Kolom Harus Diisi", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            ArrayList<String> vLst = new ArrayList<>();

            vLst.add(txtNoArmada.getText());
            vLst.add(txtNoPlat.getText());
            vLst.add(txtMerk.getText());
            vLst.add(txtModel.getText());
            vLst.add(txtTahun.getText());
        
            oArmada.setDataArmada(vLst);
            oArmada.save();
            JOptionPane.showMessageDialog(null, "Simpan Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        }
        
        tableArmada();
    }
    
    public void editArmada(){
        if(txtNoArmada.getText().isEmpty() || txtNoPlat.getText().isEmpty() || txtMerk.getText().isEmpty() || txtModel.getText().isEmpty() || txtTahun.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua Kolom Harus Diisi", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            ArrayList<String> vLst = new ArrayList<>();

            vLst.add(txtNoArmada.getText());
            vLst.add(txtNoPlat.getText());
            vLst.add(txtMerk.getText());
            vLst.add(txtModel.getText());
            vLst.add(txtTahun.getText());
        
            oArmada.setDataArmada(vLst);
            oArmada.edit();
            JOptionPane.showMessageDialog(null, "Edit Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        }
        
        tableArmada();
    }
    
    public void deleteArmada(){
        oArmada.delete();
        JOptionPane.showMessageDialog(null, "Hapus Data Berhasil", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void clearFormArmada(){
        txtNoArmada.setText("");
        txtNoPlat.setText("");
        txtMerk.setText("");
        txtModel.setText("");
        txtTahun.setText("");
        
        txtNoArmada.setEnabled(true);
        
        btnSaveArmada.setVisible(true);
        btnUpdateArmada.setVisible(true);
        btnDeleteArmada.setVisible(true);
        btnClearArmada.setVisible(true);
    }
    
    // End Module Armada
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tabDashboard = new javax.swing.JPanel();
        lblTabDashboard = new javax.swing.JLabel();
        tabFakturPerbaikan = new javax.swing.JPanel();
        lblTabFakturPembelian = new javax.swing.JLabel();
        tabMasterArmada = new javax.swing.JPanel();
        lblTabMasterArmada = new javax.swing.JLabel();
        tabMasterMekanik = new javax.swing.JPanel();
        lblTabMasterMekanik = new javax.swing.JLabel();
        tabMasterJnsPerbaikan = new javax.swing.JPanel();
        lblTabMasterJnsPerbaikan = new javax.swing.JLabel();
        containerContent = new javax.swing.JPanel();
        contentDashboard = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        contentFakturPerbaikan = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        contentMArmada = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNoArmada = new javax.swing.JTextField();
        txtNoPlat = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtMerk = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtModel = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btnSearchArmada = new javax.swing.JButton();
        txtTahun = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblArmada = new javax.swing.JTable();
        btnClearArmada = new javax.swing.JButton();
        btnDeleteArmada = new javax.swing.JButton();
        btnUpdateArmada = new javax.swing.JButton();
        btnSaveArmada = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        contentMMekanik = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNoMekanik = new javax.swing.JTextField();
        txtNamaMekanik = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNoTelp = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnSearchMekanik = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMekanik = new javax.swing.JTable();
        btnClearMekanik = new javax.swing.JButton();
        btnDeleteMekanik = new javax.swing.JButton();
        btnUpdateMekanik = new javax.swing.JButton();
        btnSaveMekanik = new javax.swing.JButton();
        contentMJnsPerbaikan = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtNoJenisPerbaikan = new javax.swing.JTextField();
        txtNamaJenisPerbaikan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnSearchJenisPerbaikan = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblJnsPerbaikan = new javax.swing.JTable();
        btnClearJnsPerbaikan = new javax.swing.JButton();
        btnDeleteJnsPerbaikan = new javax.swing.JButton();
        btnUpdateJnsPerbaikan = new javax.swing.JButton();
        btnSaveJnsPerbaikan = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/frm/optimizing.png"))); // NOI18N
        jLabel1.setText("Aplikasi Perbaikan Taxi");
        jLabel1.setIconTextGap(20);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tabDashboard.setBackground(new java.awt.Color(241, 174, 75));
        tabDashboard.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTabDashboard.setFont(new java.awt.Font("Yu Gothic", 1, 16)); // NOI18N
        lblTabDashboard.setForeground(new java.awt.Color(255, 255, 255));
        lblTabDashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTabDashboard.setText("Dashboard");
        lblTabDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTabDashboardMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tabDashboardLayout = new javax.swing.GroupLayout(tabDashboard);
        tabDashboard.setLayout(tabDashboardLayout);
        tabDashboardLayout.setHorizontalGroup(
            tabDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDashboardLayout.createSequentialGroup()
                .addComponent(lblTabDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        tabDashboardLayout.setVerticalGroup(
            tabDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabFakturPerbaikan.setBackground(tabDashboard.getBackground());
        tabFakturPerbaikan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTabFakturPembelian.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        lblTabFakturPembelian.setForeground(new java.awt.Color(255, 255, 255));
        lblTabFakturPembelian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTabFakturPembelian.setText("Faktur Perbaikan");
        lblTabFakturPembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTabFakturPembelianMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tabFakturPerbaikanLayout = new javax.swing.GroupLayout(tabFakturPerbaikan);
        tabFakturPerbaikan.setLayout(tabFakturPerbaikanLayout);
        tabFakturPerbaikanLayout.setHorizontalGroup(
            tabFakturPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabFakturPembelian, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );
        tabFakturPerbaikanLayout.setVerticalGroup(
            tabFakturPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabFakturPembelian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabMasterArmada.setBackground(tabDashboard.getBackground());
        tabMasterArmada.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabMasterArmada.setPreferredSize(new java.awt.Dimension(150, 26));

        lblTabMasterArmada.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        lblTabMasterArmada.setForeground(new java.awt.Color(255, 255, 255));
        lblTabMasterArmada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTabMasterArmada.setText("Master Armada");
        lblTabMasterArmada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTabMasterArmadaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tabMasterArmadaLayout = new javax.swing.GroupLayout(tabMasterArmada);
        tabMasterArmada.setLayout(tabMasterArmadaLayout);
        tabMasterArmadaLayout.setHorizontalGroup(
            tabMasterArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabMasterArmada, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );
        tabMasterArmadaLayout.setVerticalGroup(
            tabMasterArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabMasterArmada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabMasterMekanik.setBackground(tabFakturPerbaikan.getBackground());
        tabMasterMekanik.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabMasterMekanik.setPreferredSize(new java.awt.Dimension(150, 0));
        tabMasterMekanik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMasterMekanikMouseClicked(evt);
            }
        });

        lblTabMasterMekanik.setBackground(new java.awt.Color(255, 255, 255));
        lblTabMasterMekanik.setFont(new java.awt.Font("Yu Gothic", 1, 16)); // NOI18N
        lblTabMasterMekanik.setForeground(new java.awt.Color(255, 255, 255));
        lblTabMasterMekanik.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTabMasterMekanik.setText("Master Mekanik");

        javax.swing.GroupLayout tabMasterMekanikLayout = new javax.swing.GroupLayout(tabMasterMekanik);
        tabMasterMekanik.setLayout(tabMasterMekanikLayout);
        tabMasterMekanikLayout.setHorizontalGroup(
            tabMasterMekanikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabMasterMekanik, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
        );
        tabMasterMekanikLayout.setVerticalGroup(
            tabMasterMekanikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabMasterMekanik, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        tabMasterJnsPerbaikan.setBackground(tabMasterArmada.getBackground());
        tabMasterJnsPerbaikan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabMasterJnsPerbaikan.setPreferredSize(new java.awt.Dimension(150, 4));

        lblTabMasterJnsPerbaikan.setFont(new java.awt.Font("Yu Gothic UI", 1, 16)); // NOI18N
        lblTabMasterJnsPerbaikan.setForeground(new java.awt.Color(255, 255, 255));
        lblTabMasterJnsPerbaikan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTabMasterJnsPerbaikan.setText("Master Jenis Perbaikan");
        lblTabMasterJnsPerbaikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTabMasterJnsPerbaikanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout tabMasterJnsPerbaikanLayout = new javax.swing.GroupLayout(tabMasterJnsPerbaikan);
        tabMasterJnsPerbaikan.setLayout(tabMasterJnsPerbaikanLayout);
        tabMasterJnsPerbaikanLayout.setHorizontalGroup(
            tabMasterJnsPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabMasterJnsPerbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
        );
        tabMasterJnsPerbaikanLayout.setVerticalGroup(
            tabMasterJnsPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTabMasterJnsPerbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setText("Dashboard");

        javax.swing.GroupLayout contentDashboardLayout = new javax.swing.GroupLayout(contentDashboard);
        contentDashboard.setLayout(contentDashboardLayout);
        contentDashboardLayout.setHorizontalGroup(
            contentDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentDashboardLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        contentDashboardLayout.setVerticalGroup(
            contentDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentDashboardLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 643, Short.MAX_VALUE))
        );

        jLabel3.setText("FakturPerbaikan");

        javax.swing.GroupLayout contentFakturPerbaikanLayout = new javax.swing.GroupLayout(contentFakturPerbaikan);
        contentFakturPerbaikan.setLayout(contentFakturPerbaikanLayout);
        contentFakturPerbaikanLayout.setHorizontalGroup(
            contentFakturPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentFakturPerbaikanLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 1076, Short.MAX_VALUE))
        );
        contentFakturPerbaikanLayout.setVerticalGroup(
            contentFakturPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentFakturPerbaikanLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 643, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("No. Armada :");

        txtNoArmada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNoArmadaFocusLost(evt);
            }
        });
        txtNoArmada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoArmadaActionPerformed(evt);
            }
        });

        txtNoPlat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoPlatActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("No. Plat :");

        txtMerk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMerkActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Merk :");

        txtModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModelActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Model :");

        btnSearchArmada.setText("Cari");
        btnSearchArmada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchArmadaMouseClicked(evt);
            }
        });

        txtTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTahunActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Tahun :");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNoArmada, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchArmada))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNoPlat, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtModel, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(521, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNoArmada, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnSearchArmada, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoPlat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtMerk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtModel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTahun, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblArmada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblArmada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblArmadaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblArmada);

        btnClearArmada.setText("Bersih");
        btnClearArmada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearArmadaMouseClicked(evt);
            }
        });

        btnDeleteArmada.setText("Hapus");
        btnDeleteArmada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteArmadaMouseClicked(evt);
            }
        });

        btnUpdateArmada.setText("Ubah");
        btnUpdateArmada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateArmadaMouseClicked(evt);
            }
        });

        btnSaveArmada.setText("Simpan");
        btnSaveArmada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveArmadaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveArmada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdateArmada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteArmada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearArmada)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearArmada)
                    .addComponent(btnDeleteArmada)
                    .addComponent(btnUpdateArmada)
                    .addComponent(btnSaveArmada))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel17.setText("Master Armada");

        javax.swing.GroupLayout contentMArmadaLayout = new javax.swing.GroupLayout(contentMArmada);
        contentMArmada.setLayout(contentMArmadaLayout);
        contentMArmadaLayout.setHorizontalGroup(
            contentMArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentMArmadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentMArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentMArmadaLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentMArmadaLayout.setVerticalGroup(
            contentMArmadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentMArmadaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel7.setText("Master Mekanik");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("No. Mekanik :");

        txtNoMekanik.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNoMekanikFocusLost(evt);
            }
        });
        txtNoMekanik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoMekanikActionPerformed(evt);
            }
        });

        txtNamaMekanik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaMekanikActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Nama Mekanik :");

        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Alamat :");

        txtNoTelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoTelpActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Nomor Telepon :");

        btnSearchMekanik.setText("Cari");
        btnSearchMekanik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMekanikMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNoMekanik, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchMekanik))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNamaMekanik, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(521, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearchMekanik, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtNoMekanik, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNamaMekanik, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblMekanik.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblMekanik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMekanikMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMekanik);

        btnClearMekanik.setText("Bersih");
        btnClearMekanik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMekanikMouseClicked(evt);
            }
        });

        btnDeleteMekanik.setText("Hapus");
        btnDeleteMekanik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMekanikMouseClicked(evt);
            }
        });

        btnUpdateMekanik.setText("Ubah");
        btnUpdateMekanik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMekanikMouseClicked(evt);
            }
        });

        btnSaveMekanik.setText("Simpan");
        btnSaveMekanik.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMekanikMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveMekanik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdateMekanik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteMekanik)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearMekanik)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearMekanik)
                    .addComponent(btnDeleteMekanik)
                    .addComponent(btnUpdateMekanik)
                    .addComponent(btnSaveMekanik))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout contentMMekanikLayout = new javax.swing.GroupLayout(contentMMekanik);
        contentMMekanik.setLayout(contentMMekanikLayout);
        contentMMekanikLayout.setHorizontalGroup(
            contentMMekanikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentMMekanikLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentMMekanikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentMMekanikLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentMMekanikLayout.setVerticalGroup(
            contentMMekanikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentMMekanikLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel11.setText("Master Jenis Perbaikan");

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("No. Jenis Perbaikan :");

        txtNoJenisPerbaikan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNoJenisPerbaikanFocusLost(evt);
            }
        });
        txtNoJenisPerbaikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoJenisPerbaikanActionPerformed(evt);
            }
        });

        txtNamaJenisPerbaikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaJenisPerbaikanActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Nama Jenis Perbaikan :");

        btnSearchJenisPerbaikan.setText("Cari");
        btnSearchJenisPerbaikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchJenisPerbaikanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNoJenisPerbaikan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearchJenisPerbaikan))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNamaJenisPerbaikan, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(521, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearchJenisPerbaikan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(txtNoJenisPerbaikan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtNamaJenisPerbaikan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(123, 123, 123))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblJnsPerbaikan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblJnsPerbaikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblJnsPerbaikanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblJnsPerbaikan);

        btnClearJnsPerbaikan.setText("Bersih");
        btnClearJnsPerbaikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearJnsPerbaikanMouseClicked(evt);
            }
        });

        btnDeleteJnsPerbaikan.setText("Hapus");
        btnDeleteJnsPerbaikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteJnsPerbaikanMouseClicked(evt);
            }
        });

        btnUpdateJnsPerbaikan.setText("Ubah");
        btnUpdateJnsPerbaikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateJnsPerbaikanMouseClicked(evt);
            }
        });

        btnSaveJnsPerbaikan.setText("Simpan");
        btnSaveJnsPerbaikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveJnsPerbaikanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveJnsPerbaikan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdateJnsPerbaikan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteJnsPerbaikan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearJnsPerbaikan)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClearJnsPerbaikan)
                    .addComponent(btnDeleteJnsPerbaikan)
                    .addComponent(btnUpdateJnsPerbaikan)
                    .addComponent(btnSaveJnsPerbaikan))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout contentMJnsPerbaikanLayout = new javax.swing.GroupLayout(contentMJnsPerbaikan);
        contentMJnsPerbaikan.setLayout(contentMJnsPerbaikanLayout);
        contentMJnsPerbaikanLayout.setHorizontalGroup(
            contentMJnsPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentMJnsPerbaikanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentMJnsPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentMJnsPerbaikanLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        contentMJnsPerbaikanLayout.setVerticalGroup(
            contentMJnsPerbaikanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentMJnsPerbaikanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout containerContentLayout = new javax.swing.GroupLayout(containerContent);
        containerContent.setLayout(containerContentLayout);
        containerContentLayout.setHorizontalGroup(
            containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contentFakturPerbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contentMArmada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contentMMekanik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contentMJnsPerbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        containerContentLayout.setVerticalGroup(
            containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contentFakturPerbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contentMArmada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contentMMekanik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(containerContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(contentMJnsPerbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(containerContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tabDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabFakturPerbaikan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabMasterArmada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabMasterMekanik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabMasterJnsPerbaikan, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 366, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabFakturPerbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tabMasterArmada, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(tabMasterMekanik, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(tabMasterJnsPerbaikan, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(containerContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblTabDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTabDashboardMouseClicked
        // TODO add your handling code here:
        showTab(tabDashboard);
    }//GEN-LAST:event_lblTabDashboardMouseClicked

    private void lblTabFakturPembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTabFakturPembelianMouseClicked
        // TODO add your handling code here:
        showTab(tabFakturPerbaikan);
    }//GEN-LAST:event_lblTabFakturPembelianMouseClicked

    private void lblTabMasterArmadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTabMasterArmadaMouseClicked
        // TODO add your handling code here:
        showTab(tabMasterArmada);
        tableArmada();
    }//GEN-LAST:event_lblTabMasterArmadaMouseClicked

    private void tabMasterMekanikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMasterMekanikMouseClicked
        // TODO add your handling code here:
        showTab(tabMasterMekanik);
        tableMekanik();
    }//GEN-LAST:event_tabMasterMekanikMouseClicked

    private void lblTabMasterJnsPerbaikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTabMasterJnsPerbaikanMouseClicked
        // TODO add your handling code here:
        showTab(tabMasterJnsPerbaikan);
        tableJenisPerbaikan();
    }//GEN-LAST:event_lblTabMasterJnsPerbaikanMouseClicked

    // Start Mekanik Event
    
    private void txtNoMekanikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoMekanikActionPerformed
        
    }//GEN-LAST:event_txtNoMekanikActionPerformed

    private void txtNamaMekanikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaMekanikActionPerformed
        
    }//GEN-LAST:event_txtNamaMekanikActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void txtNoTelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoTelpActionPerformed
        
    }//GEN-LAST:event_txtNoTelpActionPerformed

    private void btnClearMekanikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMekanikMouseClicked
        clearFormMekanik();
        
    }//GEN-LAST:event_btnClearMekanikMouseClicked

    private void tblMekanikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMekanikMouseClicked
        clearFormMekanik();
        
        txtNoMekanik.setText(tblMekanik.getValueAt(tblMekanik.getSelectedRow(), 0).toString());
        txtNamaMekanik.setText(tblMekanik.getValueAt(tblMekanik.getSelectedRow(), 1).toString());
        txtAlamat.setText(tblMekanik.getValueAt(tblMekanik.getSelectedRow(), 2).toString());
        txtNoTelp.setText(tblMekanik.getValueAt(tblMekanik.getSelectedRow(), 3).toString());
        
        btnSaveMekanik.setVisible(false);
        txtNoMekanik.setEnabled(false);
    }//GEN-LAST:event_tblMekanikMouseClicked

    private void btnSearchMekanikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMekanikMouseClicked
        setKdMekanik(txtNoMekanik.getText());
    }//GEN-LAST:event_btnSearchMekanikMouseClicked

    private void txtNoMekanikFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNoMekanikFocusLost
        setKdMekanik(txtNoMekanik.getText());
    }//GEN-LAST:event_txtNoMekanikFocusLost

    private void btnSaveMekanikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMekanikMouseClicked
        this.saveMekanik();
        clearFormMekanik();
        txtNoMekanik.requestFocus();
    }//GEN-LAST:event_btnSaveMekanikMouseClicked

    private void btnUpdateMekanikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMekanikMouseClicked
        this.editMekanik();
        clearFormMekanik();
        txtNoMekanik.requestFocus();
    }//GEN-LAST:event_btnUpdateMekanikMouseClicked

    private void btnDeleteMekanikMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMekanikMouseClicked
        oMekanik.setNoMekanik(txtNoMekanik.getText());
        this.deleteMekanik();
        clearFormMekanik();
        tableMekanik();
    }//GEN-LAST:event_btnDeleteMekanikMouseClicked

    
    // End Mekanik Event
    
    // Start Jenis Perbaikan Event
    
    private void txtNoJenisPerbaikanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNoJenisPerbaikanFocusLost
        setKdJnsPerbaikan(txtNoJenisPerbaikan.getText());
    }//GEN-LAST:event_txtNoJenisPerbaikanFocusLost

    private void txtNoJenisPerbaikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoJenisPerbaikanActionPerformed
    }//GEN-LAST:event_txtNoJenisPerbaikanActionPerformed

    private void txtNamaJenisPerbaikanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaJenisPerbaikanActionPerformed
    }//GEN-LAST:event_txtNamaJenisPerbaikanActionPerformed

    private void btnSearchJenisPerbaikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchJenisPerbaikanMouseClicked
        setKdJnsPerbaikan(txtNoJenisPerbaikan.getText());
    }//GEN-LAST:event_btnSearchJenisPerbaikanMouseClicked

    private void tblJnsPerbaikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblJnsPerbaikanMouseClicked
        clearFormJnsPerbaikan();
        
        txtNoJenisPerbaikan.setText(tblJnsPerbaikan.getValueAt(tblJnsPerbaikan.getSelectedRow(), 0).toString());
        txtNamaJenisPerbaikan.setText(tblJnsPerbaikan.getValueAt(tblJnsPerbaikan.getSelectedRow(), 1).toString());
        
        btnSaveJnsPerbaikan.setVisible(false);
        txtNoJenisPerbaikan.setEnabled(false);
    }//GEN-LAST:event_tblJnsPerbaikanMouseClicked

    private void btnClearJnsPerbaikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearJnsPerbaikanMouseClicked
        // TODO add your handling code here:
        clearFormJnsPerbaikan();
    }//GEN-LAST:event_btnClearJnsPerbaikanMouseClicked

    private void btnDeleteJnsPerbaikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteJnsPerbaikanMouseClicked
        // TODO add your handling code here:
        oJnsPerbaikan.setNoJenisPerbaikan(txtNoJenisPerbaikan.getText());
        this.deleteJnsPerbaikan();
        clearFormJnsPerbaikan();
        tableJenisPerbaikan();
    }//GEN-LAST:event_btnDeleteJnsPerbaikanMouseClicked

    private void btnUpdateJnsPerbaikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateJnsPerbaikanMouseClicked
        // TODO add your handling code here:
        this.editJnsPerbaikan();
        clearFormJnsPerbaikan();
        txtNoJenisPerbaikan.requestFocus();
    }//GEN-LAST:event_btnUpdateJnsPerbaikanMouseClicked

    private void btnSaveJnsPerbaikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveJnsPerbaikanMouseClicked
        // TODO add your handling code here:
        this.saveJnsPerbaikan();
        clearFormJnsPerbaikan();
        txtNoJenisPerbaikan.requestFocus();
    }//GEN-LAST:event_btnSaveJnsPerbaikanMouseClicked

    // End Jenis Perbaikan Event
    
    // Start Armada Event
    
    private void txtNoArmadaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNoArmadaFocusLost
        // TODO add your handling code here:
        setKdArmada(txtNoArmada.getText());
    }//GEN-LAST:event_txtNoArmadaFocusLost

    private void txtNoArmadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoArmadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoArmadaActionPerformed

    private void txtNoPlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoPlatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoPlatActionPerformed

    private void txtMerkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMerkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMerkActionPerformed

    private void txtModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtModelActionPerformed

    private void btnSearchArmadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchArmadaMouseClicked
        // TODO add your handling code here:
        setKdArmada(txtNoArmada.getText());
    }//GEN-LAST:event_btnSearchArmadaMouseClicked

    private void tblArmadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblArmadaMouseClicked
        // TODO add your handling code here:
        clearFormArmada();
        
        txtNoArmada.setText(tblArmada.getValueAt(tblArmada.getSelectedRow(), 0).toString());
        txtNoPlat.setText(tblArmada.getValueAt(tblArmada.getSelectedRow(), 1).toString());
        txtMerk.setText(tblArmada.getValueAt(tblArmada.getSelectedRow(), 2).toString());
        txtModel.setText(tblArmada.getValueAt(tblArmada.getSelectedRow(), 3).toString());
        txtTahun.setText(tblArmada.getValueAt(tblArmada.getSelectedRow(), 4).toString());

        
                
        
        btnSaveArmada.setVisible(false);
        txtNoArmada.setEnabled(false);
    }//GEN-LAST:event_tblArmadaMouseClicked

    private void btnClearArmadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearArmadaMouseClicked
        // TODO add your handling code here:
        clearFormArmada();
    }//GEN-LAST:event_btnClearArmadaMouseClicked

    private void btnDeleteArmadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteArmadaMouseClicked
        // TODO add your handling code here:
        oArmada.setNoArmada(txtNoArmada.getText());
        this.deleteArmada();
        clearFormArmada();
        tableArmada();
        
    }//GEN-LAST:event_btnDeleteArmadaMouseClicked

    private void btnUpdateArmadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateArmadaMouseClicked
        // TODO add your handling code here:
        this.editArmada();
        clearFormArmada();
        txtNoArmada.requestFocus();
    }//GEN-LAST:event_btnUpdateArmadaMouseClicked

    private void btnSaveArmadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveArmadaMouseClicked
        // TODO add your handling code here:
        this.saveArmada();
        clearFormArmada();
        txtNoArmada.requestFocus();
    }//GEN-LAST:event_btnSaveArmadaMouseClicked

    private void txtTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTahunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTahunActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPerbaikanTaxi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPerbaikanTaxi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPerbaikanTaxi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPerbaikanTaxi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPerbaikanTaxi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearArmada;
    private javax.swing.JButton btnClearJnsPerbaikan;
    private javax.swing.JButton btnClearMekanik;
    private javax.swing.JButton btnDeleteArmada;
    private javax.swing.JButton btnDeleteJnsPerbaikan;
    private javax.swing.JButton btnDeleteMekanik;
    private javax.swing.JButton btnSaveArmada;
    private javax.swing.JButton btnSaveJnsPerbaikan;
    private javax.swing.JButton btnSaveMekanik;
    private javax.swing.JButton btnSearchArmada;
    private javax.swing.JButton btnSearchJenisPerbaikan;
    private javax.swing.JButton btnSearchMekanik;
    private javax.swing.JButton btnUpdateArmada;
    private javax.swing.JButton btnUpdateJnsPerbaikan;
    private javax.swing.JButton btnUpdateMekanik;
    private javax.swing.JPanel containerContent;
    private javax.swing.JPanel contentDashboard;
    private javax.swing.JPanel contentFakturPerbaikan;
    private javax.swing.JPanel contentMArmada;
    private javax.swing.JPanel contentMJnsPerbaikan;
    private javax.swing.JPanel contentMMekanik;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTabDashboard;
    private javax.swing.JLabel lblTabFakturPembelian;
    private javax.swing.JLabel lblTabMasterArmada;
    private javax.swing.JLabel lblTabMasterJnsPerbaikan;
    private javax.swing.JLabel lblTabMasterMekanik;
    private javax.swing.JPanel tabDashboard;
    private javax.swing.JPanel tabFakturPerbaikan;
    private javax.swing.JPanel tabMasterArmada;
    private javax.swing.JPanel tabMasterJnsPerbaikan;
    private javax.swing.JPanel tabMasterMekanik;
    private javax.swing.JTable tblArmada;
    private javax.swing.JTable tblJnsPerbaikan;
    private javax.swing.JTable tblMekanik;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtMerk;
    private javax.swing.JTextField txtModel;
    private javax.swing.JTextField txtNamaJenisPerbaikan;
    private javax.swing.JTextField txtNamaMekanik;
    private javax.swing.JTextField txtNoArmada;
    private javax.swing.JTextField txtNoJenisPerbaikan;
    private javax.swing.JTextField txtNoMekanik;
    private javax.swing.JTextField txtNoPlat;
    private javax.swing.JTextField txtNoTelp;
    private javax.swing.JTextField txtTahun;
    // End of variables declaration//GEN-END:variables
}
